/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.param;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import com.baidu.disconf.client.config.DisClientConfig;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.contants.DSContants;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-api
 * 
 *  Package Name    : com.gy.hsi.common.framework
 * 
 *  File Name       : HsPropertiesConfigurer.java
 * 
 *  Creation Date   : 2015年11月18日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 继承了baidu的ReloadablePropertiesFactoryBean类,
 *  
 *                    用于拦截子系统properties属性文件key-value配置, 
 *                    
 *                    如果其他子系统要自行进行拦截, 必须是该类的子类。
 *                    
 *                    【特别注意】：为了达到跟DS系统实现无缝整合, 本class必须继承disconf的ReloadablePropertiesFactoryBean类。
 *                    
 *                             不得随意修改该类代码, 会引起不可预期错误!!!!!!!!!!!!!!!!!!!!!!!!! (Marked by: zhangysh)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HsPropertiesConfigurer extends HsReloadablePropertiesFactoryBean {

	/** Property values */
	protected final static Map<String, String> propertiesMap = new ConcurrentHashMap<String, String>();
	
	/** Resource list */
	private static List<Resource> resourceList = new ArrayList<Resource>(5);

	/** Flag of inited */
	private static boolean isInited = false;

	/** Flag of started */
	private static boolean isStarted = false;
	
	public HsPropertiesConfigurer() {
		this(null);
	}

	public HsPropertiesConfigurer(String disconfFile) {
		
		// 设置disconf.properties属性文件路径
		if(!StringUtils.isEmpty(disconfFile)) {
			new HsDisconfProperties().setLocation(disconfFile);
			
			// 启动扫描变更定时器
			this.startScanTimer();
		}
	}

	/**
	 * 不得随意修改该方法代码, 会引起不可预期错误!!!!!!!!!!!!!!!!!!!!!!!!! (Marked by: zhangysh)
	 * 
	 * @param fileNames
	 * @return
	 */
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		this.doCacheProperties(props);

		// 覆盖掉spring自身扫描属性列表
		try {
			super.processProperties(beanFactoryToProcess, props);
		} catch (Exception e) {
			logger.error(e);
		}

		try {
			super.createInstance();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * 子类如果覆盖该方法, 必须在方法的结尾加上super.setLocations(fileNames);
	 * 
	 * 不得随意修改该方法代码, 会引起不可预期错误!!!!!!!!!!!!!!!!!!!!!!!!! (Marked by: zhangysh)
	 * 
	 * @param fileNames
	 * @return
	 */
	@Override
	public void setRemoteLocations(List<String> fileNames) {
		if ((null == fileNames) || (0 >= fileNames.size())) {
			return;
		}

		List<String> propertiesFileNames = this.formatFilePath(fileNames);

		super.setRemoteLocations(propertiesFileNames);

		// 注意：setLocations(Resource... locations)与本方法互斥执行
		if (!isInited) {
			isInited = true;
			this.setLocations(change2Resource(propertiesFileNames));
		}
	}

	/**
	 * 子类如果覆盖该方法, 必须在方法的结尾加上super.setLocations(locations);
	 * 
	 * 不得随意修改该方法代码, 会引起不可预期错误!!!!!!!!!!!!!!!!!!!!!!!!! (Marked by: zhangysh)
	 * 
	 * @param locations
	 * @return
	 */
	@Override
	public void setLocations(Resource... locations) {
		if ((null == locations) || (0 >= locations.length)) {
			return;
		}

		try {
			for(Resource location : locations) {
				if(!isContainResource(location)) {
					resourceList.add(location);
				}
			}
			
			super.setLocations(resourceList.toArray(new Resource[]{}));
		} catch (Exception e) {
			logger.fatal("Failed to inject locations: ", e);
		}

		// 必须注释掉 ！！！！！！！！！！！！！！！！！！！！
		// 注意：setLocations(List<String> fileNames)与本方法互斥执行
		// if (!isInited) {
		// isInited = true;
		//
		// List<String> fileNames = new ArrayList<String>();
		//
		// for (Resource res : locations) {
		// try {
		// fileNames.add(res.getURL().toString());
		// } catch (IOException e) {
		// logger.error(e);
		// }
		// }
		//
		// this.setRemoteLocations(fileNames);
		// }
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		Properties props = super.mergeProperties();

		// 缓存属性文件key-value值
		this.doCacheProperties(props);

		return props;
	}

	/**
	 * 处理属性文件值<br/>
	 * 
	 * 不得随意修改该方法代码, 会引起不可预期错误!!!!!!!!!!!!!!!!!!!!!!!!! (Marked by: zhangysh)
	 * 
	 * @param props
	 */
	protected void doCacheProperties(Properties props) {
		String key;
		String value;

		for (Object keyObj : props.keySet()) {
			key = keyObj.toString();
			value = props.getProperty(key, "");

			if (StringUtils.isEmpty(key)) {
				continue;
			}

			propertiesMap.put(key, value);
		}

		// 从服务端获取系统实例编号
		fetchSysInstNoFromDSServer();
	}

	/**
	 * 转换为Resource对象列表<br/>
	 * 
	 * 不得随意修改该方法代码, 会引起不可预期错误!!!!!!!!!!!!!!!!!!!!!!!!! (Marked by: zhangysh)
	 * 
	 * @param fileNames
	 * @return
	 */
	private Resource[] change2Resource(List<String> fileNames) {
		List<Resource> resources = new ArrayList<Resource>(5);

		for (String filename : fileNames) {
			filename = filename.trim();

			if (!FilenameUtils.getExtension(filename).equals("properties")) {
				continue;
			}

			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

			try {
				Resource[] resourceList = resolver.getResources(filename);

				for (Resource resource : resourceList) {
					resources.add(resource);
				}
			} catch (IOException e) {
			}
		}

		return resources.toArray(new Resource[resources.size()]);
	}

	/**
	 * 格式化文件路径占位符
	 * 
	 * @param fileNames
	 * @return
	 */
	private List<String> formatFilePath(List<String> fileNames) {
		List<String> propertiesFileNames = new ArrayList<String>(5);

		for (String fileName : fileNames) {
			try {
				String path = SystemPropertyUtils.resolvePlaceholders(fileName);

				if (StringUtils.isEmpty(path)
						|| "file:null".equalsIgnoreCase(path)) {
					continue;
				}

				URL url = ResourceUtils.getURL(path);

				String fullPath = url.toURI().toString().trim();

				propertiesFileNames.add(fullPath);
			} catch (Exception e) {
				logger.fatal("Failed to format file path: ", e);
			}
		}

		return propertiesFileNames;
	}

	/**
	 * 启动扫描变更定时器
	 */
	private void startScanTimer() {

		if (isStarted) {
			return;
		}

		if (DisClientConfig.getInstance().ENABLE_DISCONF) {
			new Timer().scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					// 与DS服务端进行握手
					HsSystemInstanceNoWorker.doHandshakeWithDSServer();

					if (!isInited) {
						return;
					}

					try {
						reload(false);
					} catch (IOException e) {
					}
				}

			}, (long) 10 * 1000, (long) 10 * 1000);
		}

		isStarted = true;

		logger.info("The DS scan timer is started successfully :)");
	}
	
	/**
	 * 是否包含指定资源文件
	 * 
	 * @param location
	 * @return
	 */
	private boolean isContainResource(Resource location) {
		for (Resource res : resourceList) {
			try {
				if (res.getURL().toString()
						.equals(location.getURL().toString())) {
					return true;
				}
			} catch (IOException e) {
			}
		}

		return false;
	}
	
	/**
	 * 从DS服务端获取系统实例编号
	 */
	private void fetchSysInstNoFromDSServer() {

		// 从服务端获取系统实例编号
		if (DisClientConfig.getInstance().ENABLE_DISCONF) {
			// 如果本地没有配置system.instance.no, 则从服务端进行分配
			if (StringUtils.isEmpty(getProperty(DSContants.SYS_INST_NO))) {
				setProperties(DSContants.SYS_INST_NO,
						HsSystemInstanceNoWorker.fetchSysInstNoFromDSServer());
			}
		}
	}
	
	/**
	 * 获取存储属性key-value的MAP对象
	 * 
	 * @return
	 */
	public static Map<String, String> getPropertyMap() {
		return propertiesMap;
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		String value = propertiesMap.get(key);

		if (null != value) {
			return value.trim();
		}

		return "";
	}

	/**
	 * 获取UTF-8编码的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertyByUTF8(String key) {
		try {
			String value = getProperty(key);

			if (!StringUtils.isEmpty(value)) {
				return new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (Exception e) {
		}

		return "";
	}

	/**
	 * 获取属性值int类型
	 * 
	 * @param key
	 * @return
	 */
	public static int getPropertyIntValue(String key) {
		return StringUtils.str2Int(getProperty(key));
	}

	/**
	 * 设置key-value
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperties(String key, String value) {

		if (StringUtils.isExistEmpty(key, value)) {
			return;
		}

		propertiesMap.put(key, value);
	}
}
