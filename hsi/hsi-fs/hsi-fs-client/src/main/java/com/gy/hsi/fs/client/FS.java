/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client;

import com.gy.hsi.fs.client.common.framework.spring.SpringContextLoader;
import com.gy.hsi.fs.client.factory.BeansFactory;
import com.gy.hsi.fs.client.service.IFsClientService;
import com.gy.hsi.fs.client.service.IFsInnerShareClientService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client
 * 
 *  File Name       : Fs.java
 * 
 *  Creation Date   : 2015-5-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统客户端操作入口, 本类是文件系统客户端api调用门面类, 
 *                    <br/><br/>所有调用形式均为：FS.getClient().接口方法名称(...)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class FS {

	/** beansFactory对象 **/
	private static BeansFactory beansFactory = null;

	/** 同步锁 **/
	private static final Object syncLock = new Object();

	/**
	 * 隐藏构造函数
	 */
	private FS() {
	}
	
	/**
	 * 由spring调用, 初始化FS客户端相关参数初始化
	 */
	public void init() {
		getBeanFactory();
	}

	/**
	 * 获得IFsClientService实现类对象, 该类中封装了FS文件系统所有调用API接口。
	 * 
	 * @return IFsClientService实现类对象
	 */
	public static IFsClientService getClient() {
		return getBeanFactory().getFsClientService();
	}

	/**
	 * 获得IFsInnerShareClientService实现类对象, 该类中封装了FS文件系统内部子系统间共享文件API接口。
	 * 
	 * @return IFsInnerShareClientService实现类对象
	 */
	public static IFsInnerShareClientService getInnerShareClient() {
		return getBeanFactory().getFsInnerShareClientService();
	}

	/**
	 * 获取BeansFactory
	 * 
	 * @return
	 */
	private static BeansFactory getBeanFactory() {
		if (null == beansFactory) {
			synchronized (syncLock) {
				SpringContextLoader.init();

				beansFactory = (BeansFactory) SpringContextLoader
						.getApplicationContext().getBean("beansFactory");
			}
		}

		return beansFactory;
	}
}