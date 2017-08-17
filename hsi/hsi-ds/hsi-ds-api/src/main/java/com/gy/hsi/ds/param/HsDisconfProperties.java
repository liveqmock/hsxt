package com.gy.hsi.ds.param;

import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import com.baidu.disconf.client.config.DisClientConfig;
import com.gy.hsi.common.utils.StringUtils;

public class HsDisconfProperties {
	
    protected static final Logger log = Logger.getLogger(HsDisconfProperties.class);
	
	@SuppressWarnings("resource")
	public void setLocation(String fileName) {
		try {
			if (StringUtils.isEmpty(fileName)) {
				return;
			}

			String path = SystemPropertyUtils.resolvePlaceholders(fileName);

			URL url = ResourceUtils.getURL(path);

			String fullPath = url.toURI().toString()
					.replaceFirst("^file\\:", "");

			DisClientConfig.getInstance().loadConfig(fullPath);

			// 将app转换为大写, 方便配置, added by:zhangysh 2016-02-16
			if (null != DisClientConfig.getInstance().APP) {
				DisClientConfig.getInstance().APP = DisClientConfig
						.getInstance().APP.toUpperCase();
			}
		} catch (Exception e) {
			log.error("Failed to load the disconf.properties file: ", e);
		}

		new ClassPathXmlApplicationContext(
				new String[] { "classpath*:spring/spring-hsi-ds.xml" }).start();
	}
}
