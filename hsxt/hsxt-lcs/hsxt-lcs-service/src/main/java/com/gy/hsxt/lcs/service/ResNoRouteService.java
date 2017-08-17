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

import com.gy.hsxt.lcs.bean.ResNoRoute;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IResNoRouteService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.ResNoRouteMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : ResNoRouteService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 个人路由接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("resNoRouteService")
public class ResNoRouteService implements IResNoRouteService {
	
	@Autowired
	private IVersionService veresionService;
	
	@Autowired
	private ResNoRouteMapper resNoRouteMapper;


	@Override
	public ResNoRoute queryResNoRouteWithPK(String resNo) {
		return resNoRouteMapper.queryResNoRouteWithPK(resNo.substring(0,5));
	}

	@Override
	@Transactional
	public int addOrUpdateResNoRoute(List<ResNoRoute> list, Long version) {
		veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_RESNO_ROUTE,version,true));
		List<ResNoRoute> resNoRouteListAdd = new ArrayList<ResNoRoute>();
		List<ResNoRoute> resNoRouteListUpdate = new ArrayList<ResNoRoute>();
		for(ResNoRoute obj : list){
			if("1".equals(resNoRouteMapper.existResNoRoute(obj.getResNo()))){
				resNoRouteListUpdate.add(obj);
			}else{
				resNoRouteListAdd.add(obj);
			}
		}
		if(resNoRouteListUpdate.size()>0){
			resNoRouteMapper.batchUpdate(resNoRouteListUpdate);
		}
		
		if(resNoRouteListAdd.size()>0){
			resNoRouteMapper.batchInsert(resNoRouteListAdd);
		}
		return 1;
	}

    @Override
    public List<ResNoRoute> getResNoRouteList() {
        return resNoRouteMapper.getResNoRouteList();
    }
}
