package com.baidu.disconf.client.core.filetype.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.baidu.disconf.client.core.filetype.DisconfFileTypeProcessor;
import com.github.knightliao.apollo.utils.config.ConfigLoaderUtils;
import com.gy.hsi.ds.common.utils.FileUtils;

/**
 * Properties 处理器
 *
 * @author knightliao
 */
public class DisconfPropertiesProcessorImpl implements DisconfFileTypeProcessor {

	@Override
	public Map<String, Object> getKvMap(String fileName) throws Exception {
		// 读取配置
		Properties properties = null;

		try {
			properties = ConfigLoaderUtils.loadConfig(fileName);
		} catch (Exception e) {
			String absolutePath = FileUtils.assembleFilePathByUserDir(fileName);

			properties = ConfigLoaderUtils.loadConfig(absolutePath);
		}

		if (null == properties) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		for (Object object : properties.keySet()) {

			String key = String.valueOf(object);
			Object value = properties.get(object);

			map.put(key, value);
		}

		return map;
	}
}
