package com.gy.hsxt.rabbitmq.common.config;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.common.util.StringUtils;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.util.FileTools;
/**
 * 
* @ClassName: PersistentPolicyConfigurer 
* @Description: 参数类，缓存全局日志策略参数及缓存队列中的自增长number 
* @author tianxh 
* @date 2015-8-6 下午3:08:09 
*
 */
public class PersistentPolicyConfigurer {
	private static final Logger log = LoggerFactory
			.getLogger(PersistentPolicyConfigurer.class);
	private Map<String, String> configMap;//全局配置文件参数Map(logCenter.properties)
	public Map<String,Integer> sequenceNumberMap;
	private static PersistentPolicyConfigurer singleton;

	private PersistentPolicyConfigurer() {
	};

	public Map<String, String> getConfigMap() {
		if (null == configMap)
			configMap = new HashMap<String, String>();
		loadParamsFromPropsFile();
		return configMap;
	}
/**
 * 
* @Title: getSingleton 
* @Description: 获取单例 
* @param @return    设定文件 
* @return PersistentPolicyConfigurer    返回类型 
* @throws
 */
	public static PersistentPolicyConfigurer getSingleton() {
		if (null == singleton) {
			singleton = new PersistentPolicyConfigurer();
		}
		return singleton;
	}
	/**
	 * 
	* @Title: getUniqueFileNameFromMap 
	* @Description:  获取生成的日志文件名称
	* @param @param pattern
	* @param @param targetFilePath
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getUniqueFileNameFromMap(String pattern,String targetFilePath) {
		int sequenceNumber = 0;
		if (null == sequenceNumberMap){
			sequenceNumberMap = new HashMap<String, Integer>();
		}
		if(!sequenceNumberMap.containsKey(targetFilePath)){
			sequenceNumber = 1;
		}else{
			sequenceNumber = sequenceNumberMap.remove(targetFilePath);
			sequenceNumber++;
		}
		sequenceNumberMap.put(targetFilePath, sequenceNumber);
		String activeFileName = createUniqueUniqueFileName(pattern,sequenceNumber);
		configMap.put(targetFilePath, activeFileName);
		return activeFileName;
	}
	/**
	 * 
	* @Title: createUniqueGenerateKey 
	* @Description: 根据当前时间和自增长sequenceNumber生成日志文件名称
	* @param @param pattern
	* @param @param sequenceNumber
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String createUniqueUniqueFileName(String pattern,int sequenceNumber) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		StringBuffer uniqueFileName = new StringBuffer();
		uniqueFileName.append(sdf.format(new Date())).append(ConfigConstant.UNDERLINE).append(sequenceNumber)
				.append(ConfigConstant.LOGFILE_TYPE);
		return uniqueFileName.toString();
	}
	/**
	 * 
	* @Title: loadParamsFromPropsFile 
	* @Description: 加载全局配置参数
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void loadParamsFromPropsFile() {
		try {
			Properties props = FileTools.getProperties(FileTools.PROPERTIESP_PATH);
			String activeFileName = StringUtils.nullToEmpty(props
					.getProperty("activeFileName"));
			String maxFileSize = StringUtils.nullToZero(props
					.getProperty("maxFileSize"));
			String append = StringUtils.nullToEmpty(props.getProperty("append"));
			String pattern = StringUtils.nullToEmpty(props.getProperty("pattern"));
			String filterLevel = StringUtils.nullToEmpty(props.getProperty("filterLevel"));
			configMap.put("activeFileName", activeFileName);
			configMap.put("maxFileSize", maxFileSize);
			configMap.put("append", append);
			configMap.put("pattern", pattern);
			configMap.put("filterLevel", filterLevel);
		} catch (Exception e) {
			log.error(ConfigConstant.MOUDLENAME, "[PersistentPolicyConfigurer]",
					ConfigConstant.FUNNAME, "[loadParamsFromPropsFile],code:", e.getCause(),",message:",
					e.getMessage());
		}
	}
	
	public String getActiveFileName(Map<String, String> fileInfoMap,Map<String, String> paramsMap,boolean overMaxFile) {
		String prefixFileName = StringUtils.nullToEmpty(fileInfoMap
				.get("prefixFileName"));// 获取日志文件名称的前缀部分
		String activeFilePath = StringUtils.nullToEmpty(fileInfoMap
				.get("activeFilePath"));// 获取当前活动的文件路径
		String pattern = StringUtils.nullToEmpty(configMap.get("pattern"));// 获取文件名称格式化信息
		String activeFileName = StringUtils.nullToEmpty(configMap
				.get("activeFileName"));// 获取当前活动的文件名称
		String targetFilePath = StringUtils.nullToEmpty(fileInfoMap
				.get("targetFilePath"));// 获取目标文件的文件路径
		StringBuffer source = new StringBuffer();
		String today = configMap.get(targetFilePath);
		if (overMaxFile || StringUtils.isEmpty(today)) {
			today = getUniqueFileNameFromMap(// 获取生成的日志文件名称
					pattern, targetFilePath);
		}
		paramsMap.put("prefixFileName", prefixFileName);
		paramsMap.put("activeFilePath", activeFilePath);
		paramsMap.put("activeFileName", activeFileName);
		paramsMap.put("targetFilePath", targetFilePath);
		paramsMap.put("pattern", pattern);
		source.append(activeFilePath).append(prefixFileName).append(today)
				.append(activeFileName);// 生成当前活动文件的全路径
		return source.toString();
	}
}
