/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.dubbo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.RpcException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.exception.PortBindedException;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.UfUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.common.remote
 * 
 *  File Name       : DubboServiceSupport.java
 * 
 *  Creation Date   : 2015-9-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 远程调用dubbo服务入口类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class DubboServiceSupport {
	/** 记录日志对象 **/
	private static final Logger logger = Logger
			.getLogger(DubboServiceSupport.class);

	/** 此类单例对象 **/
	private static final DubboServiceSupport INSTANCE = new DubboServiceSupport();

	/** 缓存远程Service接口对象 **/
	private static final Map<String, Object> DUBBO_REMOTE_SERVICE_MAP = new ConcurrentHashMap<String, Object>(
			20);

	/** 缓存已经暴露的本地Service接口 **/
	private static final List<Class<?>> EXPORTED_LOCAL_SERVICES = new Vector<Class<?>>(
			20);

	/** 版本 **/
	private static final String VERSION = "1.0.0";

	/** 最大循环次数 **/
	private static final int MAX_LOOP_COUNTS = 59;

	/** 蜂窝默认端口:20940 **/
	private static final int DEFAULT_BEEHIVE_PORT = 20940;

	/** 同步锁 **/
	private static final Lock SYNC_LOCK = new ReentrantLock();

	/**
	 * 私有构造函数
	 */
	private DubboServiceSupport() {
	}

	/**
	 * 获得单例对象
	 * 
	 * @return
	 */
	public static DubboServiceSupport getInstance() {
		return INSTANCE;
	}

	/**
	 * 取得消费者Service对象
	 * 
	 * @param targetInterfClass
	 * @return
	 * @throws HsException
	 */
	public Object getDubboServiceForSending(Class<?> targetInterfClass)
			throws HsException {
		return getDubboServiceForSending(targetInterfClass, null, null);
	}

	/**
	 * 取得消费者Service对象
	 * 
	 * @param targetInterfClass
	 * @param serviceGroup
	 * @param timeoutMills
	 * @return
	 * @throws HsException
	 */
	public Object getDubboServiceForSending(Class<?> targetInterfClass,
			String serviceGroup, Integer timeoutMills) throws HsException {
		if ((null == timeoutMills) || (0 >= timeoutMills)) {
			timeoutMills = DubboConfiger.getServTimeout30000();
		}

		String key = assembleMapKey(targetInterfClass, serviceGroup,
				timeoutMills);

		try {
			SYNC_LOCK.lock();

			Object service = DUBBO_REMOTE_SERVICE_MAP.get(key);

			if (null == service) {
				service = this.buildDubboConsumer(targetInterfClass,
						serviceGroup, timeoutMills);

				DUBBO_REMOTE_SERVICE_MAP.put(key, service);
			}

			return service;
		} finally {
			SYNC_LOCK.unlock();
		}
	}

	/**
	 * 创建用于Dubbo Service实例
	 * 
	 * @param targetInterfClass
	 * @param serviceGroup
	 * @param timeoutMills
	 * @return
	 * @throws HsException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object buildDubboConsumer(Class<?> targetInterfClass,
			String serviceGroup, Integer timeoutMills) throws HsException {
		try {
			// zookeeper地址
			String zookeeperAddr = DubboConfiger.getRegAddress();

			// consumer
			String appConsumer = DubboConfiger.getAppName().concat("-c");

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
			reference.setTimeout(timeoutMills);
			reference.setVersion(VERSION);
			reference.setRetries(0);
			reference.setCheck(false);

			if (!StringUtils.isEmpty(serviceGroup)) {
				reference.setGroup(serviceGroup);
			}

			// 设置优化参数
			this.initConsumerTuningParams(application, registry, reference);

			// 获得远程接口服务对象
			return reference.get();
		} catch (Exception ex) {
			String regEx = "^(Failed to check the status of the service).{1,}(No provider available for the service).{1,}$";
			String regEx2 = "^(The interface class).{1,}(is not a interface).{1,}$";

			String exMsg = ex.getMessage().trim();

			if (exMsg.matches(regEx) || exMsg.matches(regEx2)) {
				throw new HsException(UFResultCode.INNERNAL_COMM_ERR, "没有找到"
						+ targetInterfClass.getName() + "服务提供者！");
			}

			throw new HsException(UFResultCode.SYS_INNERNAL_ERR,
					UfUtils.getStackTraceInfo(ex));
		}
	}

	/**
	 * 向Dubbo Server订阅服务
	 * 
	 * @param interfClass
	 * @param serviceInstance
	 * @param serviceGroup
	 */
	public void registerSyncServiceToServer(final Class<?> interfClass,
			final Object serviceInstance, final String serviceGroup) {
		new Thread() {
			@Override
			public void run() {
				try {
					SYNC_LOCK.lock();

					doRegisterSyncServiceToServer(interfClass, serviceInstance,
							serviceGroup);
				} catch (Exception e) {
					logger.error("严重！注册dubbo服务发生异常：", e);
				} finally {
					SYNC_LOCK.unlock();
				}

				try {
					System.in.read();
				} catch (IOException e) {
				}
			}
		}.start();
	}

	/**
	 * 向Dubbo Server订阅
	 * 
	 * @param interfClass
	 * @param serviceInstance
	 * @param serviceGroup
	 * @throws Exception
	 */
	private synchronized void doRegisterSyncServiceToServer(
			Class<?> interfClass, Object serviceInstance, String serviceGroup)
			throws Exception {
		if (EXPORTED_LOCAL_SERVICES.contains(interfClass)) {
			return;
		}

		EXPORTED_LOCAL_SERVICES.add(interfClass);

		// zookeeper地址
		String zookeeperAddr = DubboConfiger.getRegAddress();

		// provider
		String appProvider = DubboConfiger.getAppName();

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName(appProvider);

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(zookeeperAddr);

		// 默认端口
		int beehivePort = DEFAULT_BEEHIVE_PORT;
		int loopCounts = 0;

		while (true) {
			try {
				boolean success = this.actionSyncServiceRegister(application,
						registry, beehivePort++, interfClass, serviceInstance,
						serviceGroup);

				if (success) {
					break;
				}
			} catch (PortBindedException ex) {
				this.suspendAmoment();

				// 循环次数大于最大的次数, 必须停止, 不能无限下去
				if (MAX_LOOP_COUNTS <= loopCounts) {
					break;
				}

				loopCounts++;

				continue;
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	/**
	 * 执行同步服务订阅
	 * 
	 * @param application
	 * @param registry
	 * @param defaultPort
	 * @param interfClass
	 * @param serviceInstance
	 * @param serviceGroup
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean actionSyncServiceRegister(ApplicationConfig application,
			RegistryConfig registry, int defaultPort, Class<?> interfClass,
			Object serviceInstance, String serviceGroup) throws Exception {
		// 超时时间
		int timeoutMills = DubboConfiger.getServTimeout60000();

		try {
			// 服务提供者协议配置
			ProtocolConfig protocol = new ProtocolConfig();
			protocol.setPort(defaultPort);

			// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口 ??
			// 服务提供者暴露服务配置
			ServiceConfig service = new ServiceConfig();
			service.setTimeout(timeoutMills);

			// 该类很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
			service.setApplication(application);
			service.setRegistry(registry);

			// 多个注册中心可以用setRegistries()
			service.setProtocol(protocol);

			// 多个协议可以用setProtocols()
			service.setInterface(interfClass);
			service.setRef(serviceInstance);
			service.setGroup(serviceGroup);
			service.setVersion(VERSION);

			// 设置优化参数
			this.initProviderTuningParams(application, registry, protocol,
					service);

			service.export();

			// 订阅成功！！！！
			printRegisterSuccessLog(true);

			return true;
		} catch (Exception ex) {

			// 端口已经被占用, 需要重新寻找端口进行订阅
			if (ex instanceof RpcException) {
				String exStackInfo = UfUtils.getStackTraceInfo(ex);

				String regExp1 = "java.net.BindException:Address already in use";
				String regExp2 = "java.net.BindException:地址已在使用";
				String regExp3 = "java.net.BindException:";
				String regExp4 = "com.alibaba.dubbo.rpc.RpcException:Fail to start server";

				if (exStackInfo.contains(regExp1)
						|| exStackInfo.contains(regExp2)
						|| (exStackInfo.contains(regExp3) && exStackInfo
								.contains(regExp4))) {
					throw new PortBindedException("Address already in use!");
				}
			}

			// 订阅失败！！！！
			printRegisterSuccessLog(false);

			throw ex;
		}
	}

	/**
	 * 初始化Consumer优化参数设置
	 * 
	 * @param dubboElements
	 */
	@SuppressWarnings({ "rawtypes" })
	private void initConsumerTuningParams(Object... dubboElements) {

		for (Object dubboElm : dubboElements) {
			if (dubboElm instanceof ApplicationConfig) {
				ApplicationConfig application = ((ApplicationConfig) dubboElm);
				application.setEnvironment("product");
			}

			if (dubboElm instanceof RegistryConfig) {
				@SuppressWarnings("unused")
				RegistryConfig registry = ((RegistryConfig) dubboElm);
			}

			if (dubboElm instanceof ReferenceConfig) {
				ReferenceConfig reference = ((ReferenceConfig) dubboElm);
				reference.setConnections(DubboConfiger.getReferConnections());
			}
		}
	}

	/**
	 * 初始化Provider优化参数设置
	 * 
	 * @param dubboElements
	 */
	@SuppressWarnings({ "rawtypes" })
	private void initProviderTuningParams(Object... dubboElements) {

		for (Object dubboElm : dubboElements) {
			if (dubboElm instanceof ApplicationConfig) {
				ApplicationConfig application = ((ApplicationConfig) dubboElm);
				application.setEnvironment("product");
			}

			if (dubboElm instanceof RegistryConfig) {
				@SuppressWarnings("unused")
				RegistryConfig registry = ((RegistryConfig) dubboElm);
			}

			if (dubboElm instanceof ProtocolConfig) {
				ProtocolConfig protocol = ((ProtocolConfig) dubboElm);

				protocol.setName(DubboConfiger.getProtocol());
				protocol.setThreadpool(DubboConfiger.getProThreadpoolType());
				protocol.setThreads(DubboConfiger.getProThreadpoolThreads());
				protocol.setQueues(0);
				protocol.setBuffer(819200);
				protocol.setAccesslog("false");
				protocol.setServer("netty");
			}

			if (dubboElm instanceof ServiceConfig) {
				ServiceConfig service = ((ServiceConfig) dubboElm);

				service.setActives(0);
				service.setExecutes(0);
			}
		}
	}

	/**
	 * 打印订阅日志
	 * 
	 * @param success
	 */
	private void printRegisterSuccessLog(boolean success) {
		if (success) {
			logger.info(" --> UF has register dubbo service successfully.");
		} else {
			logger.info(" --> UF failed to register to dubbo service!");
		}
	}

	/**
	 * 组装缓存RPC对象的Map容器key
	 * 
	 * @param clasz
	 * @param serviceGroup
	 * @param timeoutMills
	 * @return
	 */
	private String assembleMapKey(Class<?> clasz, String serviceGroup,
			Integer timeoutMills) {
		// 使用指定的这个临时超时时长
		boolean isUseTempTimeout = ((null != timeoutMills) && (0 < timeoutMills));

		// RPC 对象缓存map key
		String rpcMapKey = (clasz.getName() + serviceGroup);

		return (isUseTempTimeout ? (rpcMapKey + String.valueOf(timeoutMills))
				: rpcMapKey);
	}

	/**
	 * 悬起一会
	 */
	private void suspendAmoment() {
		try {
			Thread.sleep(200);
		} catch (IllegalArgumentException | InterruptedException e) {
		}
	}
}