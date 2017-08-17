/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.utils.HsResNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.gcs.bean.BizRoute;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.bean.ResNoRoute;
import com.gy.hsxt.gpf.gcs.bean.RouteTarget;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IBizRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;
import com.gy.hsxt.gpf.gcs.interfaces.IResNoRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : RouteRuleService.java
 * 
 *  Creation Date   : 2015-8-5
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 资源号与平台路由关系接口服务
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class RouteRuleService implements IGCSRouteRuleService {
    
    private Logger logger = LoggerFactory.getLogger(RouteRuleService.class);

    @Autowired
    IPlatService platService;

    @Autowired
    IBizRouteService bizRouteService;

    @Autowired
    IResNoRouteService resNoRouteService;
    
    @Autowired
    private IVersionService veresionService;
    
    @Autowired
    IUFRegionPacketService ufRegionPacketService;

    @Override
    public RouteTarget getRouteTarget(String platNo, String bizCode) throws HsException {
        HsAssert.notNull(platNo, RespCode.PARAM_ERROR, "平台代码为空");
        HsAssert.notNull(bizCode, RespCode.PARAM_ERROR, "业务代码为空");

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
        HsAssert.hasText(resNo, RespCode.PARAM_ERROR, "互生号为空");
        HsAssert.notNull(bizCode, RespCode.PARAM_ERROR, "业务代码为空");

        HsAssert.isTrue(HsResNoUtils.isHsResNo(resNo),RespCode.PARAM_ERROR, "服务资源号应为11位数字，resNo=" + resNo);

        String targetIP = "";
        String targetPort = "";
        String targetSubsys = "";

        String platNo = "";
        ResNoRoute resNoRoute = resNoRouteService.queryResNoRouteWithPK(StringUtils.left(resNo,5));
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

        return new RouteTarget(targetIP, targetPort, targetSubsys, platNo);
    }

    @Override
    public Map<String, String> getResNoRouteMap() {
        Map<String, String> map = new HashMap<String, String>();
        List<ResNoRoute> list = resNoRouteService.getResNoRouteList();
        for (ResNoRoute resNoRoute : list)
        {
            map.put(resNoRoute.getResNo(), resNoRoute.getPlatNo());
        }
        return map;
    }

    @Override
    public String getPlatByResNo(String resNo) throws HsException {
        HsAssert.notNull(resNo, RespCode.PARAM_ERROR, "互生号为空");
        ResNoRoute resNoRoute = resNoRouteService.queryResNoRouteWithPK(resNo.substring(0, 5));
        if (null == resNoRoute || resNoRoute.isDelFlag())
        {
            return null;
        }
        return resNoRoute.getPlatNo();
    }

    @Override
    public void syncAddRouteRule(List<String> resNoList, String platNo) throws HsException {
        HsAssert.notNull(platNo, RespCode.PARAM_ERROR, "平台代码为空");

        if (resNoList == null || resNoList.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR, "互生号列表为空");
        }
        List<String> resNoPrefixList = new ArrayList<String>();
        for (String resNo : resNoList)
        {
            // 同步过来的资源号只能取前5位，资源路由规则只保存前5位
            resNoPrefixList.add(resNo.substring(0, 5));
        }
        resNoRouteService.syncAddRouteRule(resNoPrefixList, platNo);
        
        //同步资源号路由关系表后立即通知地区平台更新
        try {
            Version version = veresionService.queryVersion(Constant.GL_RESNO_ROUTE);
            final JSONObject  param = new JSONObject();
            param.put("tableCode", Constant.GL_RESNO_ROUTE);
            param.put("version", version.getVersion());
            Thread t = new Thread(){
                @Override
                public void run() {
                    // 组装报文体
                    RegionPacketBody packetBody = RegionPacketBody.build(param.toString());
                    for(Plat plat : platService.findAllPlat()){
                        if(!GlobalConstant.CENTER_PLAT_NO.equals(plat.getPlatNo())){
                            // 组装报文头
                            RegionPacketHeader packetHeader = RegionPacketHeader.build()
                                    .setDestPlatformId(plat.getPlatNo())
                                    .setDestBizCode(AcrossPlatBizCode.TO_REGION_NOTIFY_UPDATE.name());
                            try {
                                Object resultObj =  ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
                                if("ok".equals(resultObj)){
                                    logger.debug("通知平台"+plat.getPlatNo()+"成功！"); 
                                }else{
                                    logger.warn("通知平台"+plat.getPlatNo()+"失败！"); 
                                }
                            } catch (HsException e) {
                                logger.error("调用通知变更远程接口异常：", e);
                            }
                        }
                    }
                }
            };
            
            t.start();   
        } catch (Exception e) {
            logger.error("获取记录列表异常：", e);
        }
    }

    @Override
    public void syncDelRouteRule(List<String> resNoList) throws HsException {
        if (resNoList == null || resNoList.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR, "互生号列表为空");
        }
        List<String> list = new ArrayList<String>();
        for (String resNo : resNoList)
        {
            list.add(resNo.substring(0, 5));
        }
        resNoRouteService.batchDelResNoRoute(list);
    }

    /**
     * 获取全部有效地区平台代码
     * 
     * @return
     * @throws HsException
     * @see com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService#findRegionPlats()
     */
    @Override
    public List<Plat> findRegionPlats() throws HsException {
        List<Plat> list = platService.findAllPlat();
        for (Plat plat : list)
        {
            if (GlobalConstant.CENTER_PLAT_NO.equals(plat.getPlatNo()))
            {
                // 只有一条总平台信息，总平台代码为000，移除总平台信息剩下的就是所有地区平台列表
                list.remove(plat);
                break;
            }
        }
        return list;
    }

}
