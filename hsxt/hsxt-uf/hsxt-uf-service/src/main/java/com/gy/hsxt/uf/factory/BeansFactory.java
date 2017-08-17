/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.factory;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.lcs.api.ILCSRouteRuleService;
import com.gy.hsxt.uf.service.IRegionPacketLogService;

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
	private TaskExecutor logTaskExecutor;

	@Resource
	private IRegionPacketLogService packetLogService;
	
	@Resource(name="lcsRouteRuleService")
	private ILCSRouteRuleService lcsRouteRuleService;
	
	@Resource(name="gcsRouteRuleService")
	private IGCSRouteRuleService gcsRouteRuleService;

	public TaskExecutor getLogTaskExecutor() {
		return logTaskExecutor;
	}

	public IRegionPacketLogService getPacketLogService() {
		return packetLogService;
	}

	public ILCSRouteRuleService getLcsRouteRuleService() {
		return lcsRouteRuleService;
	}
	
	public IGCSRouteRuleService getGcsRouteRuleService() {
		return gcsRouteRuleService;
	}

}
