package com.gy.hsxt.kafka.util;



import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.kafka.common.bean.BizLogInfo;
import com.gy.hsxt.kafka.common.bean.SysLogInfo;
import com.gy.hsxt.kafka.common.config.PersistentPolicyConfigurer;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;

/**
 * 
 * @ClassName: BizLogTools
 * @Description: 写业务日志的工具类
 * @author tianxh
 * @date 2015-8-6 下午2:14:09
 * 
 */
public class LogCenterTools {
	private static final Logger log = LoggerFactory
			.getLogger(LogCenterTools.class);

	/**
	 * 
	 * @Title: getBizActiveFilePath
	 * @Description: 获取写业务日志的路径
	 * @param @param bizLogInfo 业务日志对象
	 * @param @return 写业务日志的路径
	 * @return String 返回类型
	 * @throws
	 */
	public static String getBizActiveFilePath(BizLogInfo bizLogInfo) {
		StringBuffer activeFilePath = null;
		try {
			Properties props = FileTools
					.getProperties(FileTools.PROPERTIESP_PATH);// 获取配置文件（logCenter.properties）
			if (null == props) {
				return StringUtils.EMPTY;
			}
			String dishPath = props.getProperty(ConfigConstant.BIZLOG_PATH);
			if (StringUtils.isBlank(dishPath)) {
				return StringUtils.EMPTY;
			}
			activeFilePath = new StringBuffer(dishPath);
			activeFilePath.append(StringUtils.nullToEmpty(bizLogInfo.getPlatformName()))
					.append(File.separator).append(StringUtils.nullToEmpty(bizLogInfo.getSystemName()))
					.append(File.separator);
			String dateStr = StringUtils.nullToEmpty(bizLogInfo.getTimeStamp());// yyyy-MM-dd HH:mm:ss
			StringBuffer year = new StringBuffer();
			StringBuffer month = new StringBuffer();
			StringBuffer day = new StringBuffer();
			if (dateStr.length() > 7) {
				String y = dateStr.substring(0, 4);
				year.append(Integer.parseInt(y)).append("year");
				String m = dateStr.substring(5, 7);
				month.append(Integer.parseInt(m)).append("month");
				String d = dateStr.substring(8, 10);
				day.append(Integer.parseInt(d)).append("day");
			}
			activeFilePath.append(year).append(File.separator).append(month)
					.append(File.separator).append(day).append(File.separator);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ConfigConstant.MOUDLENAME, "[LogCenterTools]",
					ConfigConstant.FUNNAME, "[getBizActiveFilePath],code:",
					e.getCause(), ",message:", e.getMessage());
		}
		return activeFilePath.toString();
	}

	/**
	 * 
	 * @Title: getBizPrefixFileName
	 * @Description: 获取要写的业务日志文件名的前缀部分
	 * @param @param bizLogInfo
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getBizPrefixFileName(BizLogInfo bizLogInfo) {
		StringBuffer prefixFileName = new StringBuffer();
		prefixFileName.append(StringUtils.nullToEmpty(bizLogInfo.getPlatformName()))
				.append(ConfigConstant.WHIPPTREE)
				.append(StringUtils.nullToEmpty(bizLogInfo.getSystemName())).append(ConfigConstant.UNDERLINE);
		return prefixFileName.toString();
	}

	/**
	 * 
	 * @Title: getFileParamsMap
	 * @Description: 加载全局配置参数及日志对象中的持久化的相关信息（包括文件生成的位置、文件名称的前缀部分、格式化信息等等）
	 * @param @param fileInfoMap
	 * @param @param needTarget
	 * @param @return 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	public static Map<String, String> getFileParamsMap(//加载全局配置参数及日志对象中的持久化的相关信息（目标对象的相关参数除外）
			Map<String, String> fileInfoMap) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		try {
			PersistentPolicyConfigurer persistentPolicyConfigurer = PersistentPolicyConfigurer
					.getSingleton();// 获取全局参数实例
			Map<String, String> configMap = persistentPolicyConfigurer
					.getConfigMap();// 获取全局配置参数
			if ((null == fileInfoMap || fileInfoMap.size() == 0)) {
				log.error(ConfigConstant.MOUDLENAME, "[LogCenterTools]",
						ConfigConstant.FUNNAME,
						"[getFileParamsMap]，请检查线程初始化参数是否有存放");
				return paramsMap;
			}
			if (null == configMap || configMap.size() == 0) {
				log.error(ConfigConstant.MOUDLENAME, "[LogCenterTools]",
						ConfigConstant.FUNNAME,
						"[getFileParamsMap],请检查路径：config/logcenter/logCenter.properties是否存在，参数是否配置 ");
				return paramsMap;
			}
			
			String append = StringUtils.nullToEmpty(configMap.get("append"));
			long maxFileSize = Long.valueOf(StringUtils.nullToZero(configMap
					.get("maxFileSize")));// 获取策略中规定的最大文件大小
			paramsMap.put("maxFileSize", String.valueOf(maxFileSize));
			paramsMap.put("append", String.valueOf(append));
			String source = persistentPolicyConfigurer.getActiveFileName(fileInfoMap,paramsMap,false);
			paramsMap.put("source", source);
			File sourceFile = new File(source);
			if (!sourceFile.exists()) {// 当前活动文件是否存在，不存在则创建一个
				boolean isExistsNot = FileTools
						.createMissingParentDirectories(sourceFile);
				paramsMap.put("noFile", String.valueOf(isExistsNot));
			}
		} catch (Exception e) {
			log.error(ConfigConstant.MOUDLENAME, "[HSPolicy]",
					ConfigConstant.FUNNAME, "[noActiveFileHandlePolicy],code:",
					e.getCause(), ",message:", e.getMessage());
		}
		return paramsMap;
	}

	/**
	 * 
	 * @Title: getSystemActiveFilePath
	 * @Description: 获取写业务日志的路径
	 * @param @param syslog 系统日志对象
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getSystemActiveFilePath(SysLogInfo syslog) {
		StringBuffer activeFilePath = null;
		try {
			Properties props = FileTools
					.getProperties(FileTools.PROPERTIESP_PATH);// 获取配置文件（logCenter.properties）
			if (null == props) {
				return StringUtils.EMPTY;
			}
			String dishPath = StringUtils.nullToEmpty(props.getProperty(ConfigConstant.SYSLOG_PATH));
			if (StringUtils.isBlank(dishPath)) {
				return StringUtils.EMPTY;
			}
			activeFilePath = new StringBuffer(dishPath);
			activeFilePath.append(StringUtils.nullToEmpty(syslog.getPlatformName()))
					.append(File.separator).append(syslog.getSystemName()).append(File.separator);
			String dateStr = StringUtils.nullToEmpty(syslog.getTimeStamp());// yyyy-MM-dd HH:mm:ss
			StringBuffer year = new StringBuffer();
			StringBuffer month = new StringBuffer();
			StringBuffer day = new StringBuffer();
			if (dateStr.length() > 7) {
				String y = dateStr.substring(0, 4);
				year.append(Integer.parseInt(y)).append("year");
				String m = dateStr.substring(5, 7);
				month.append(Integer.parseInt(m)).append("month");
				String d = dateStr.substring(8, 10);
				day.append(Integer.parseInt(d)).append("day");
			}
			activeFilePath.append(year).append(File.separator).append(month)
					.append(File.separator).append(day).append(File.separator);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(ConfigConstant.MOUDLENAME, "[LogCenterTools]",
					ConfigConstant.FUNNAME, "[getSystemActiveFilePath],code:",
					e.getCause(), ",message:", e.getMessage());
		}
		return activeFilePath.toString();
	}

	public static void main(String[] args) {
		BizLogInfo syslog = new BizLogInfo();
		syslog.setColumns("123");
		syslog.setFunName("method");
		syslog.setContents("456");
		syslog.setMoudleName("kkkzz");
		syslog.setOrder(1L);
		syslog.setPlatformName("hsxt");
		syslog.setSystemName("uc");
		syslog.setSystemInstanceName("uc01");
		syslog.setTimeStamp("2015-01-01");
		String path =LogCenterTools.getBizActiveFilePath(syslog) +  LogCenterTools.getBizPrefixFileName(syslog);
		System.out.println("path==>["+path+"]");
	}
	/**
	 * 
	 * @Title: getSystemPrefixFileName
	 * @Description: 获取要写的系统日志文件名的前缀部分
	 * @param @param syslog
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getSystemPrefixFileName(SysLogInfo syslog) {
		StringBuffer prefixFileName = new StringBuffer();
		prefixFileName.append(StringUtils.nullToEmpty(syslog.getPlatformName()))
				.append(ConfigConstant.WHIPPTREE)
				.append(StringUtils.nullToEmpty(syslog.getSystemName()));
		return prefixFileName.toString();
	}
}
