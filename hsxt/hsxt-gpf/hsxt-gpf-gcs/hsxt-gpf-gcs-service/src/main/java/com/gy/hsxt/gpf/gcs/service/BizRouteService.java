/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.BizRoute;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IBizRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.BizRouteMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : BizRouteService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 业务路由接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("bizRouteService")
public class BizRouteService implements IBizRouteService {

	@Autowired
	private IVersionService versionService;
	
	@Autowired
	private BizRouteMapper bizRouteMapper;
	
	
	@Override
	@Transactional
	public int addBizRoute(BizRoute bizRoute) {
		long version = versionService.addOrUpdateVersion(Constant.GL_BIZ_ROUTE);
		bizRoute.setVersion(version);
		return bizRouteMapper.addBizRoute(bizRoute);
	}

	@Override
	@Transactional
	public boolean deleteBizRoute(String bizCode) {
		long version = versionService.addOrUpdateVersion(Constant.GL_BIZ_ROUTE);
		BizRoute bizRoute = new BizRoute(bizCode);
		bizRoute.setVersion(version);
		return bizRouteMapper.deleteBizRoute(bizRoute);
	}

	@Override
	@Transactional
	public boolean updateBizRoute(BizRoute bizRoute) {
		long version = versionService.addOrUpdateVersion(Constant.GL_BIZ_ROUTE);
		bizRoute.setVersion(version);
		return bizRouteMapper.updateBizRoute(bizRoute);
	}

	@Override
	public List<BizRoute> queryBizRoute(BizRoute bizRoute) {
		String bizNameCn = bizRoute.getBizNameCn();
		if(null != bizNameCn && !"".equals(bizNameCn)){
			bizNameCn = bizNameCn.replaceAll("_", "/_").replaceAll("%", "/%");
		}
		bizRoute.setBizNameCn(bizNameCn);
		return bizRouteMapper.queryBizRouteListPage(bizRoute);
	}

	@Override
	public boolean existBizRoute(String bizCode) {
		boolean isExist = "1".equals(bizRouteMapper.existBizRoute(bizCode));
		return isExist;
	}

	@Override
	public BizRoute queryBizRouteWithPK(String bizCode) {
		return bizRouteMapper.queryBizRouteWithPK(bizCode);
	}

	@Override
	public List<BizRoute> queryBizRoute4SyncData(long version) {
		return bizRouteMapper.queryBizRoute4SyncData(version);
	}

}
