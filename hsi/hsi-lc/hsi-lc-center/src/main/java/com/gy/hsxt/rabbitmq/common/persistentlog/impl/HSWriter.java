package com.gy.hsxt.rabbitmq.common.persistentlog.impl;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.persistentlog.IPolicy;
import com.gy.hsxt.rabbitmq.common.persistentlog.IWriter;
import com.gy.hsxt.rabbitmq.common.util.FileTools;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;
import com.gy.hsxt.rabbitmq.common.util.StringUtils;

/**
 * 
 * @ClassName: HSWriter
 * @Description: 日志文件持久化类
 * @author tianxh
 * @date 2015-8-6 下午3:46:51
 * 
 */
public class HSWriter implements IWriter {
	private static final Logger log = LoggerFactory.getLogger(HSWriter.class);
	private IPolicy hspolicy;
	private Map<String, String> fileInfoMap;

	public HSWriter() {
	};

	public HSWriter(IPolicy hspolicy, Map<String, String> fileInfoMap) {
		this.hspolicy = hspolicy;
		this.fileInfoMap = fileInfoMap;
	}

	/**
	 * 
	 * <p>
	 * Title: write
	 * </p>
	 * <p>
	 * Description: 持久化日志文件到磁盘
	 * </p>
	 * 
	 * @param obj
	 * @see com.gy.hsi.lc.common.persistentlog.IWriter#write(java.lang.Object)
	 */
	@Override
	public String write(Object obj) {
		try {
			if (null != obj) {
				if (null != fileInfoMap && fileInfoMap.size() > 0) {
					boolean needColumnsName = hspolicy.excutePolicy();// 执行完当前活动文件是否存在或者已超过规定大小的检查
					String message = getMessage(needColumnsName, obj);
					Map<String, String> paramsMap = LogCenterTools.getFileParamsMap(fileInfoMap);
					boolean append = Boolean.valueOf(StringUtils
							.nullToEmpty(paramsMap.get("append")));
					if (paramsMap.size() > 0) {
						String source = paramsMap.get("source");// 源文件
						File sourceFile = new File(source);
						persistentLogtoDisk(sourceFile, message, append);// 持久化日志到磁盘
						return source;
					}
				}
			}
		} catch (Exception e) {
			log.error(ConfigConstant.MOUDLENAME, "[HSWriter]",
					ConfigConstant.FUNNAME, "[write],code:", e.getCause(),
					",message:", e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @Title: getMessage
	 * @Description: 获取需要持久化的日志内容
	 * @param @param needColumnsName
	 * @param @param obj
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private String getMessage(boolean needColumnsName, Object obj) {
		String message = (String) obj;// 日志文件对象
		StringBuffer columnsName = new StringBuffer();
		if (needColumnsName && fileInfoMap.containsKey("columns")) {// 判断是否是业务日志对象
			String cname = StringUtils.nullToEmpty(fileInfoMap.get("columns"));// 增加首行的列名
			columnsName.append(cname).append(ConfigConstant.CR).append(message);
			message = columnsName.toString();
		}
		return message;
	}

	/**
	 * 
	 * @Title: persistentLogtoDisk
	 * @Description: 持久化日志到磁盘
	 * @param @param sourceFile
	 * @param @param message
	 * @param @param append 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void persistentLogtoDisk(File sourceFile, String message,
			boolean append) {
		RecoveryOutputStreamImpl resilientFos = null;// 输出流类，在原有流的基础上增加在写文件失败的情况下重新恢复写文件的能力
		try {
			resilientFos = new RecoveryOutputStreamImpl(sourceFile, append);
		} catch (FileNotFoundException e1) {
			log.error(ConfigConstant.MOUDLENAME, "[HSWriter]",
					ConfigConstant.FUNNAME, "[write]  FileNotFound ,code:",
					e1.getCause(), ",message:", e1.getMessage());
		}
		byte[] b = message.getBytes();
		try {
			if (null != b) {
				resilientFos.write(b, 0, b.length);// 持久化日志到磁盘
				resilientFos.flush();
			}
		} catch (Exception e) {
			log.error(ConfigConstant.MOUDLENAME, "[HSWriter]",
					ConfigConstant.FUNNAME, "[write] 持久化文件失败 ,code:",
					e.getCause(), ",message:", e.getMessage());
		}
		FileTools.freeResource(null, resilientFos);
	}
}
