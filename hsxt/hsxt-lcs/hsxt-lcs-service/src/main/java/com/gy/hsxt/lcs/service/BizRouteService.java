/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.BizRoute;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IBizRouteService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.BizRouteMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
	public int addOrUpdateBizRoute(List<BizRoute> list,Long version) {
		versionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_BIZ_ROUTE,version,true));
		List<BizRoute> bizRouteListAdd = new ArrayList<BizRoute>();
		List<BizRoute> bizRouteListUpdate = new ArrayList<BizRoute>();
		for(BizRoute obj : list){
			if("1".equals(bizRouteMapper.existBizRoute(obj.getBizCode()))){
				bizRouteListUpdate.add(obj);
			}else{
				bizRouteListAdd.add(obj);
			}
		}
		if(bizRouteListUpdate.size()>0){
			bizRouteMapper.batchUpdate(bizRouteListUpdate);
		}
		
		if(bizRouteListAdd.size()>0){
			bizRouteMapper.batchInsert(bizRouteListAdd);
		}
		return 1;
	}

	@Override
	public BizRoute queryBizRouteWithPK(String bizCode) {
		return bizRouteMapper.queryBizRouteWithPK(bizCode);
	}
}
