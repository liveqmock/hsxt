/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.api.ILCSRouteRuleService;
import com.gy.hsxt.lcs.bean.BizRoute;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.ResNoRoute;
import com.gy.hsxt.lcs.bean.RouteTarget;
import com.gy.hsxt.lcs.interfaces.IBizRouteService;
import com.gy.hsxt.lcs.interfaces.IPlatService;
import com.gy.hsxt.lcs.interfaces.IResNoRouteService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : RouteRuleService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 获取路由信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("routeRuleService")
public class RouteRuleService implements ILCSRouteRuleService {

    @Autowired
    IPlatService platService;

    @Autowired
    IBizRouteService bizRouteService;

    @Autowired
    IResNoRouteService resNoRouteService;

    @Override
    public RouteTarget getRouteTarget(String platNo, String bizCode) throws HsException {
        if (platNo == null || platNo.isEmpty() || bizCode == null || bizCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "平台代码或业务代码不应该为空");
        }

        String targetIP = "";
        String targetPort = "";
        String targetSubsys = "";

        Plat plat = platService.getPlat(platNo);
        if (null != plat)
        {
            targetIP = plat.getPlatEntryIP();
            targetPort = plat.getPlatEntryPort() + "";
        }

        BizRoute bizRoute = bizRouteService.queryBizRouteWithPK(bizCode);
        if (null != bizRoute)
        {
            targetSubsys = bizRoute.getSubSysCode();
        }

        RouteTarget routeTarget = new RouteTarget(targetIP, targetPort, targetSubsys, platNo);
        return routeTarget;
    }

    @Override
    public RouteTarget getRouteTargetByResNo(String resNo, String bizCode) throws HsException {
        if (resNo == null || resNo.isEmpty() || bizCode == null || bizCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "服务资源号或业务代码不应该为空");
        }

        String targetIP = "";
        String targetPort = "";
        String targetSubsys = "";

        String platNo = "";
        ResNoRoute resNoRoute = resNoRouteService.queryResNoRouteWithPK(resNo);
        if (null != resNoRoute)
        {
            platNo = resNoRoute.getPlatNo();
        }

        Plat plat = platService.getPlat(platNo);
        if (null != plat)
        {
            targetIP = plat.getPlatEntryIP();
            targetPort = plat.getPlatEntryPort() + "";
        }

        BizRoute bizRoute = bizRouteService.queryBizRouteWithPK(bizCode);
        if (null != bizRoute)
        {
            targetSubsys = bizRoute.getSubSysCode();
        }

        RouteTarget routeTarget = new RouteTarget(targetIP, targetPort, targetSubsys, platNo);

        return routeTarget;
    }
}
