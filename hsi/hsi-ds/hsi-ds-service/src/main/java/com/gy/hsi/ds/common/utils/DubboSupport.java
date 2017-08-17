/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.job.beans.DubboComsumerConfig;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-param
 * 
 *  Package Name    : com.gy.hsi.ds.common.utils
 * 
 *  File Name       : DubboSuppor.java
 * 
 *  Creation Date   : 2016年1月29日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DubboSupport {

	private static final Logger logger = Logger.getLogger(DubboSupport.class);

	/** 缓存远程Service接口对象 **/
	private static final Map<String, IDSBatchService> DUBBO_REMOTE_SERVICE_MAP = new ConcurrentHashMap<String, IDSBatchService>(
			20);

	/** 获取Service同步锁 **/
	private static final Lock GET_REMOTE_SERVICE_LOCK = new ReentrantLock();

	/**
	 * 取得消费者Service对象
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @param serviceVersion
	 * @param dubboConsumerConfig
	 * @return
	 */
	public static IDSBatchService getDSBatchService(String serviceName,
			String serviceGroup, String serviceVersion,
			DubboComsumerConfig dubboConsumerConfig) {

		Class<?> targetInterfClass = IDSBatchService.class;

		String key = assembleMapKey(targetInterfClass, serviceName,
				serviceGroup, serviceVersion);

		try {
			GET_REMOTE_SERVICE_LOCK.lock();

			IDSBatchService service = DUBBO_REMOTE_SERVICE_MAP.get(key);

			if (null == service) {
				service = (IDSBatchService) buildDubboConsumer(
						targetInterfClass, serviceGroup, serviceVersion,
						dubboConsumerConfig);

				DUBBO_REMOTE_SERVICE_MAP.put(key, service);
			}

			return service;
		} finally {
			GET_REMOTE_SERVICE_LOCK.unlock();
		}
	}

	/**
	 * 创建用于Dubbo Service实例
	 * 
	 * @param targetInterfClass
	 * @param serviceGroup
	 * @param serviceVersion
	 * @param dubboConsumerConfig
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object buildDubboConsumer(Class<?> targetInterfClass,
			String serviceGroup, String serviceVersion,
			DubboComsumerConfig dubboConsumerConfig) {

		try {
			// zookeeper地址
			String zookeeperAddr = dubboConsumerConfig.getRegAddr();

			// consumer
			String appConsumer = dubboConsumerConfig.getAppName().concat("-c");

			// 当前应用配置
			ApplicationConfig application = new ApplicationConfig();
			application.setName(appConsumer);

			// 连接注册中心配置
			RegistryConfig registry = new RegistryConfig();
			registry.setAddress(zookeeperAddr);

			// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
			// 引用远程服务
			ReferenceConfig reference = new ReferenceConfig();

			// 该类很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和 连接泄漏
			reference.setApplication(application);
			reference.setRegistry(registry);

			// 多个注册中心可以用setRegistries()
			reference.setInterface(targetInterfClass);
			reference.setTimeout(dubboConsumerConfig.getRefTimeout());
			reference.setRetries(dubboConsumerConfig.getRefRetries());
			reference.setVersion(serviceVersion);
			reference.setCheck(false);

			// ----- DS调度增加基于异步方式实现, added by zhangysh, 2016年1月26日 ----
			reference.setAsync(true);
			reference.setSent(true);

			if (!StringUtils.isEmpty(serviceGroup)) {
				reference.setGroup(serviceGroup);
			}

			// 获得远程接口服务对象
			return reference.get();
		} catch (Exception ex) {
			String regEx = "^(Failed to check the status of the service).{1,}(No provider available for the service).{1,}$";
			String regEx2 = "^(The interface class).{1,}(is not a interface).{1,}$";

			String exMsg = ex.getMessage().trim();

			if (exMsg.matches(regEx) || exMsg.matches(regEx2)) {
				logger.error("没有找到服务提供者: service class:"
						+ targetInterfClass.getName() + ", group:"
						+ serviceGroup);
			} else {
				logger.error("获取服务提供者发生异常:", ex);
			}

			throw ex;
		}
	}

	/**
	 * 组装缓存RPC对象的Map容器key
	 * 
	 * @param clasz
	 * @param serviceName
	 * @param serviceGroup
	 * @param serviceVersion
	 * @return
	 */
	private static String assembleMapKey(Class<?> clasz, String serviceName,
			String serviceGroup, String serviceVersion) {

		// RPC 对象缓存map key
		StringBuilder rpcMapKey = new StringBuilder();
		rpcMapKey.append(clasz.getName());
		rpcMapKey.append(serviceName);
		rpcMapKey.append(serviceGroup);

		if (!StringUtils.isEmpty(serviceVersion)) {
			rpcMapKey.append(serviceVersion);
		}

		return rpcMapKey.toString();
	}

}
