package com.gy.hsxt.kafka.common.persistent.impl;



import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.kafka.common.config.PersistentPolicyConfigurer;
import com.gy.hsxt.kafka.common.persistent.IPolicy;
import com.gy.hsxt.kafka.common.util.StringUtils;
import com.gy.hsxt.kafka.util.FileTools;
import com.gy.hsxt.kafka.util.LogCenterTools;

/**
 * 
 * @ClassName: HSPolicy
 * @Description: 持久化日志策略类
 * @author tianxh
 * @date 2015-8-6 下午3:17:59
 * 
 */
public class HSPolicy implements IPolicy {
	private static final Logger log = LoggerFactory.getLogger(HSPolicy.class);
	private Map<String, String> fileInfoMap;

	public HSPolicy() {
	};

	public HSPolicy(Map<String, String> fileInfoMap) {
		this.fileInfoMap = fileInfoMap;
	};

	public Map<String, String> getFileInfoMap() {
		return fileInfoMap;
	}

	public void setFileInfoMap(Map<String, String> fileInfoMap) {
		this.fileInfoMap = fileInfoMap;
	}

	/**
	 * 
	 * <p>
	 * Title: excutePolicy
	 * </p>
	 * <p>
	 * Description:写文件之前先执行日志策略处理
	 * </p>
	 * 
	 * @return boolean true 是首次写文件或者当前文件已经写满
	 * @see com.gy.hsi.lc.common.persistentlog.IPolicy#excutePolicy()
	 */
	@Override
	public boolean excutePolicy() {
		boolean noFile = noActiveFileHandlePolicy();// 首次写文件自动生成日志文件（空文件）
		boolean isFullFile = overMaxFileSizeHadlePolicy();// 日志文件超过指定大小，重命名并重新生成空的日志文件
		return noFile || isFullFile;
	}

	/**
	 * 
	 * @Title: overMaxFileSizeHadlePolicy
	 * @Description: 日志文件超过指定大小，重命名并重新生成空的日志文件
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean overMaxFileSizeHadlePolicy() {
		boolean isFullFile = false;
		try {
			Map<String, String> paramsMap = LogCenterTools.getFileParamsMap(fileInfoMap);
			if (paramsMap.size() > 0) {
				String source = paramsMap.get("source");// 源文件
				File sourceFile = new File(source);
				int maxFileSize = Integer
						.parseInt(paramsMap.get("maxFileSize"));// 规定文件大小
				if (sourceFile.exists() && sourceFile.length() >= maxFileSize) {// 当前文件存在且文件大小超过规定的大小
					String target = "";
					if(source.contains(".doing")){
						target = source.substring(0,source.indexOf(".doing"));
					}
					FileTools.rename(source.toString(), target.toString());// 将超过规定的当前活动的文件重新命名存放到目标文件路径下
					String newSource = PersistentPolicyConfigurer.getSingleton().getActiveFileName(paramsMap,paramsMap, true);
					if(!StringUtils.isEmpty(newSource)){
						File newSourceFile = new File(newSource);
						isFullFile = FileTools
								.createMissingParentDirectories(newSourceFile);// 创建空的活动文件
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFullFile;
	}

	/**
	 * 
	 * @Title: getPolicyParams
	 * @Description: 当前活动文件是否存在，如不存在，则创建
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean noActiveFileHandlePolicy() {
		boolean isExistsNot = false;
			Map<String, String> paramsMap = LogCenterTools.getFileParamsMap(fileInfoMap);
			if (paramsMap.size() > 0) {
				isExistsNot = Boolean.valueOf(paramsMap.get("noFile"));
			}
		return isExistsNot;
	}
}