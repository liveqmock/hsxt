/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.factory;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gy.hsi.fs.client.service.IFsClientService;
import com.gy.hsi.fs.client.service.IFsInnerShareClientService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.factory
 * 
 *  File Name       : BeansFactory.java
 * 
 *  Creation Date   : 2014-9-26
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
	private IFsClientService fsClientService;

	@Resource
	private IFsInnerShareClientService fsInnerShareClientService;

	public IFsClientService getFsClientService() {
		return fsClientService;
	}

	public IFsInnerShareClientService getFsInnerShareClientService() {
		return fsInnerShareClientService;
	}

}
