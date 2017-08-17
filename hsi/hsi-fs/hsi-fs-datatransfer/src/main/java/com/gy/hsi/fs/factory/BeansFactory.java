/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.factory;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gy.hsi.fs.common.bean.SystemConfig;
import com.gy.hsi.fs.service.IBSDataTransferService;
import com.gy.hsi.fs.service.IFSDataTransferService;
import com.gy.hsi.fs.service.ITaobaoTfsClientService;
import com.gy.hsi.fs.service.IUCDataTransferService;
import com.gy.hsi.fs.service.IWSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.factory
 * 
 *  File Name       : BeansFactory.java
 * 
 *  Creation Date   : 2015-9-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 调用Service的工厂类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component(value = "beansFactory")
@Scope("singleton")
public class BeansFactory {

	@Resource
	private IBSDataTransferService bsDataTransferService;

	@Resource
	private IFSDataTransferService fsDataTransferService;

	@Resource
	private IUCDataTransferService ucDataTransferService;

	@Resource
	private IWSDataTransferService wsDataTransferService;
	
	@Resource
	private ITaobaoTfsClientService taobaoTfsClientService;

	@Resource(name = "uploadExecutor")
	private ThreadPoolTaskExecutor uploadExecutor;

	@Resource
	private SystemConfig systemConfig;

	public IBSDataTransferService getBsDataTransferService() {
		return bsDataTransferService;
	}

	public IFSDataTransferService getFsDataTransferService() {
		return fsDataTransferService;
	}

	public IUCDataTransferService getUcDataTransferService() {
		return ucDataTransferService;
	}

	public IWSDataTransferService getWsDataTransferService() {
		return wsDataTransferService;
	}

	public ThreadPoolTaskExecutor getUploadExecutor() {
		return uploadExecutor;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public ITaobaoTfsClientService getTaobaoTfsClientService() {
		return taobaoTfsClientService;
	}

}
