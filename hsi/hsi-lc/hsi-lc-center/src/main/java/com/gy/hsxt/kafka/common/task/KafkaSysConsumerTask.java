package com.gy.hsxt.kafka.common.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.common.TempNo;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.kafka.enums.MonitorEnum;
import com.gy.hsxt.kafka.util.LevelSwitchEnum;
import com.gy.hsxt.kafka.util.RandomGuidAgent;
import com.gy.hsxt.rabbitmq.common.bean.SysLogInfo;
import com.gy.hsxt.rabbitmq.common.config.PersistentPolicyConfigurer;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSPolicy;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSWriter;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;
import com.gy.hsxt.rabbitmq.common.util.StringUtils;

public class KafkaSysConsumerTask implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(KafkaSysConsumerTask.class);
	boolean isWrite = true;
	private INtService ntService;
	private String filterLevel;// 全局日志过滤级别
	private String threadName;
	private Map<String, String> configMap;

	public KafkaSysConsumerTask(String threadName, Map<String, String> configMap,INtService ntService) {
		this.threadName = threadName;
		filterLevel = StringUtils.nullToEmpty(getFilterSwitch());
		this.configMap = configMap;
		this.ntService = ntService;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = BuildConsumer(configMap);
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);// 每100毫秒从kafka服务器上获取消息
			if (null != records && records.count() > 0) {
				persisteMessage(records);// 持久化这些消息
			}
		}
	}

	public void persisteMessage(ConsumerRecords<String, String> records) {
		Map<Long, SysLogInfo> msgMap = new TreeMap<Long, SysLogInfo>();
		for (ConsumerRecord<String, String> record : records) {
			String msg = record.value();
			SysLogInfo sysLogInfo = null;
			
			try {
				sysLogInfo = JSON.parseObject(msg,SysLogInfo.class);
			} catch (Exception e) {
				System.out.println("消息格式不合法，消息内容：["+msg+"]");
				isWrite = false;
			}
			if(isWrite){
				msgMap.put(sysLogInfo.getOrder(), sysLogInfo);
			}
		}
		if (msgMap.size() > 0) {
			writeMessageToFile(msgMap);
		}
	}

	private KafkaConsumer<String, String> BuildConsumer(
			Map<String, String> valueMap) {
		Properties props = new Properties();
		props.put(ConfigConstant.BOOTSTRAPSERVERS,
				valueMap.get(ConfigConstant.BOOTSTRAPSERVERS));
		props.put(ConfigConstant.GROUPID, valueMap.get(ConfigConstant.GROUPID));
		props.put(ConfigConstant.ENABLEAUTOCOMMIT,
				valueMap.get(ConfigConstant.ENABLEAUTOCOMMIT));
		props.put(ConfigConstant.AUTOCOMMITINTERVALMS,
				valueMap.get(ConfigConstant.AUTOCOMMITINTERVALMS));
		props.put(ConfigConstant.SESSIONTIMEOUTMS,
				valueMap.get(ConfigConstant.SESSIONTIMEOUTMS));
		props.put(ConfigConstant.KEYDESERIALIZER,
				valueMap.get(ConfigConstant.KEYDESERIALIZER));
		props.put(ConfigConstant.VALUEDESERIALIZER,
				valueMap.get(ConfigConstant.VALUEDESERIALIZER));
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		List<String> toplicList = getTopsParams(valueMap
				.get(ConfigConstant.TOPICS));
		consumer.subscribe(toplicList);
		return consumer;
	}

	private List<String> getTopsParams(String str) {
		List<String> resultList = new ArrayList<String>();
		if (StringUtils.isBlank(str)) {
			return resultList;
		}
		if (str.length() > 0) {
			if (!str.contains(",")) {
				resultList.add(str);
			} else {
				String[] arrays = str.split(",");
				resultList = Arrays.asList(arrays);
			}
		}
		return resultList;
	}

	private void writeMessageToFile(Map<Long, SysLogInfo> treeMap) {
		if (null == treeMap) {
			return;
		}
		int count = 0;// 缓存系统日志对象个数
		StringBuffer logContent = new StringBuffer();// 系统日志内容
		HSPolicy policy = null;// 写文件策略执行类
		HSWriter writer = null;// 写文件到硬盘的执行类
		String filePath = "";
		for (long key : treeMap.keySet()) {
			SysLogInfo sysLogInfo = treeMap.get(key);
			System.out.println("日志内容：["+sysLogInfo.getLogContent()+"]");
			String activeFilePath = LogCenterTools
					.getSystemActiveFilePath(sysLogInfo);// 获取当前正在写的文件的路径
			String prefixFileName = LogCenterTools
					.getSystemPrefixFileName(sysLogInfo);// 获取当前正在写的文件名的前缀部分（例如：平台名-系统名-业务名_模块名_功能名_）
			Map<String, String> map = new HashMap<String, String>();
			map.put("activeFilePath", activeFilePath);
			map.put("targetFilePath", activeFilePath);
			map.put("prefixFileName", prefixFileName);
			if (isContinue(sysLogInfo,activeFilePath,prefixFileName )) {
				continue;
			}
			policy = new HSPolicy(map);
			writer = new HSWriter(policy, map);
			count++;
			logContent
					.append(sysLogInfo.getPlatformName())
					.append(ConfigConstant.VERTICAL)
					.append(sysLogInfo.getSystemName())
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo
							.getSystemInstanceName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getTimeStamp()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getLevel()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getHostName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getIpAddress()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getMoudleName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getFunName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getLogContent()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo
							.getMonitorLevel()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo
							.getExceptionMessage()))

					.append(ConfigConstant.CR);
			if (count % ConfigConstant.FIXEDLENGTH == 0) {// 每
															// LogConstant.FIXEDLENGTH
															// 笔写一次。
				filePath = writer.write(logContent.toString());
				count = 0;
				logContent.setLength(0);
			}
		}
		if (0 != count && logContent.length() > 0) {// 最后余下的日志内容写完
			filePath = writer.write(logContent.toString());
		}
		
	}

	private boolean isContinue(SysLogInfo sysLogInfo,String activeFilePath,String prefixFileName ) {
		if (null == sysLogInfo) {
			return true;
		}
		if (StringUtils.isBlank(sysLogInfo.getLevel())
				|| StringUtils.isBlank(sysLogInfo.getLogContent())) {
			return true;
		}
		if (LevelSwitchEnum.getType(sysLogInfo.getLevel()) < LevelSwitchEnum
				.getType(filterLevel)) {
			return true;
		}
		if (!StringUtils.isBlank(sysLogInfo.getMonitorLevel())) {// 将告警日志重发到告警队列里
			String mobile = sysLogInfo.getMobile();
			String email = sysLogInfo.getEmail();
			String filePath = activeFilePath + prefixFileName;
			if(null == ntService){
				System.out.println("告警日志["+JSON.toJSONString(sysLogInfo)+"] is not send email and mobile,通知服务未开启或者未注入成功");
				return true;
			}else{
				sendSmsCodeForValidMobile(mobile,sysLogInfo);
				sendMailForValidEmail(email, sysLogInfo, filePath);
			}
			return false;
		}
		return false;
	}

	private String getFilterSwitch() {// 获取过滤日志级别
		String fileterLevel = "debug";
		try {
			PersistentPolicyConfigurer persistentPolicyConfigurer = PersistentPolicyConfigurer
					.getSingleton();// 获取全局参数实例
			Map<String, String> configMap = persistentPolicyConfigurer
					.getConfigMap();// 获取全局配置参数
			if (null == configMap || configMap.size() == 0) {
				log.error(ConfigConstant.MOUDLENAME, "[LogCenterTools]",
						ConfigConstant.FUNNAME,
						"[getFileParamsMap],请检查路径：config/logcenter/logCenter.properties是否存在，参数是否配置 ");
			}

			fileterLevel = StringUtils
					.nullToEmpty(configMap.get("filterLevel"));
		} catch (Exception e) {
			log.error(ConfigConstant.MOUDLENAME, "[HSPolicy]",
					ConfigConstant.FUNNAME, "[noActiveFileHandlePolicy],code:",
					e.getCause(), ",message:", e.getMessage());
		}
		return fileterLevel;
	}

	/**
	 * 
	 * @param mobile
	 * @throws HsException
	 */
	private void sendSmsCodeForValidMobile(String mobile, SysLogInfo sysLogInfo) {
		
//		try {
//			NoteBean note = new NoteBean();
//			String[] params = new String[2];
//			// 生成验证码
////			String content = getMonitorMsg(sysLogInfo);
//			String content = "kk123";
//			params[0] = content;
//			params[1] = "300";// 有效期时间
//			// 组装短信内容
//			note.setTemplateId(TempNo.MOBILE_VERIFY_CODE.getTemoNo());
//			note.setPriority(Priority.HIGH.getPriority());
//			note.setSender(sysLogInfo.getSystemName());// 系统名称
//			note.setReceivePhones(new String[] { mobile });
//			note.setNoteContent(params);
//			note.setNoteId(RandomGuidAgent.getStringGuid("1"));
//			ntService.sendNote(note);
//			// 存入缓存，并设置定时
//		} catch (NoticeException e) {
//			e.printStackTrace();
//			System.out.println("手机短信发送失败");
//			try {
//				Thread.currentThread().sleep(5000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
	}

	public static void main(String[] args) {
		
	}
	private String getMonitorMsg(SysLogInfo sysLogInfo) {
		String platformName = sysLogInfo.getPlatformName();
		String systemName = sysLogInfo.getSystemName();
		String moudleName = sysLogInfo.getMoudleName();
		String funName = sysLogInfo.getFunName();
		String level = sysLogInfo.getMonitorLevel();
		StringBuffer msg = new StringBuffer();
		msg.append(platformName).append("平台").append(systemName).append("系统")
				.append(moudleName).append("业务模块").append(funName)
				.append("功能点出故障！").append("告警级别为")
				.append(MonitorEnum.getName(level)).append("，详情查看邮件处理！");
		// {platformName}平台{systemName}系统{moudleName}业务模块{funName}功能点出故障！
		// 告警级别为高，详情查看邮件处理！
		return msg.toString();
	}

	public void sendMailForValidEmail(String email, SysLogInfo sysLogInfo,String filePath) {
//		String name = sysLogInfo.getSystemInstanceName();
//		String content = buildMailContent(sysLogInfo, filePath);
//		EmailBean note = new EmailBean();
//		try {
//			String[] params = new String[7];
//			// 生成验证码
//			params[0] = name;
//			params[1] = Calendar.getInstance().toString();
//			params[2] = content;
//			params[3] = params[2];
//			params[4] = String.valueOf("300");// 有效期时间
//			params[5] = sysLogInfo.getSystemName();
//			params[6] = Calendar.getInstance().toString();
//
//			// 组装内容
//			note.setEmailId(UUID.randomUUID().toString());
//			note.setTemplateId(TempNo.BINDED_EMAIL_VERIFICATION.getTemoNo());
//			note.setPriority(Priority.HIGH.getPriority());
//			note.setSender(sysLogInfo.getSystemName());
//			String mailTitle = sysLogInfo.getSystemName();
//			note.setMailContent(params);
//			note.setMailTitle(mailTitle);
//			note.setMailReceivers(new String[] { email.trim() });
//			ntService.sendEmail(note);
//			// 存入缓存，并设置定时
//		} catch (NoticeException e) {
//			e.printStackTrace();
//			System.out.println("邮件通知服务失败");
//			try {
//				Thread.currentThread().sleep(5000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
	}
	private String buildMailContent(SysLogInfo sysLogInfo,String filePath){
		String[] titles= new String[12];
		String[] columns= new String[12];
		titles[0] = ConfigConstant.DATE;
		titles[1] = ConfigConstant.PLATFORMNAME;
		titles[2] = ConfigConstant.SYSTEMNAME;
		titles[3] = ConfigConstant.SYSTEMINSTANCENAME;
		titles[4] = ConfigConstant.IPADDRESS;
		titles[5] = ConfigConstant.HOSTNAME;
		titles[6] = ConfigConstant.MONITOR_MOUDLENAME;
		titles[7] = ConfigConstant.MONITOR_FUNNAME;
		titles[8] =  ConfigConstant.MONITORLEVEL;
		titles[9] = ConfigConstant.LOGCONTENT;
		titles[10] = ConfigConstant.EXCEPTIONMESSAGE;
		titles[11] = ConfigConstant.FILEPATH;
		columns[0] =sysLogInfo.getTimeStamp();
		columns[1] =sysLogInfo.getPlatformName();
		columns[2] =sysLogInfo.getSystemName();
		columns[3] =sysLogInfo.getSystemInstanceName();
		columns[4] =sysLogInfo.getIpAddress();
		columns[5] =sysLogInfo.getHostName();
		columns[6] =sysLogInfo.getMoudleName();
		columns[7] =sysLogInfo.getFunName();
		columns[8] =sysLogInfo.getMonitorLevel();
		columns[9] =sysLogInfo.getLogContent();
		columns[10] =sysLogInfo.getExceptionMessage();
		columns[11] =filePath;
		String title = "";
		String column = "";
		for (int i = 0; i < titles.length; i++) {
			title = titles[i];
			column = columns[i];
			if(title.length() <= column.length()){
				title = rightPad(title, column.length() - title.length());
			}else{
				column =  rightPad(column, title.length() - column.length());
			}
			titles[i] = title + "     ";
			columns[i] = column + "     ";
		}
		StringBuffer emailContent = new StringBuffer();
		for (int i = 0; i < titles.length; i++) {
			emailContent.append(titles[i]);
		}
		emailContent.append(ConfigConstant.CR);
		for (int i = 0; i < columns.length; i++) {
			emailContent.append(columns[i]);
		}
		emailContent.append(ConfigConstant.CR);
		return emailContent.toString();
	}
	
	
	public String rightPad(String str,int count){
		return org.apache.commons.lang.StringUtils.rightPad(str, count);
	}
	
}
