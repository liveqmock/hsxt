/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.res.RESErrorCode;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import com.gy.hsxt.gpf.res.bean.QuotaApp;
import com.gy.hsxt.gpf.res.interfaces.ISyncDataService;
import com.gy.hsxt.gpf.res.mapper.InitMapper;
import com.gy.hsxt.gpf.res.mapper.QuotaMapper;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.service
 * @ClassName: SyncDataService
 * @Description: 同步数据
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:37:02
 * @version V1.0
 */
@Service
public class SyncDataService implements ISyncDataService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private QuotaMapper quotaMapper;

    @Resource
    private IUFRegionPacketService ufRegionPacketService;

    @Resource
    private IGCSRouteRuleService iGCSRouteRuleService;

    @Resource
    private InitMapper initMapper;

    /**
     * 同步配额分配到子平台
     * 
     * @param quotaApp
     *            配额分配
     * @throws HsException
     */
    @Override
    @Transactional
    public void syncQuotaAllotData(QuotaApp quotaApp) throws HsException {
        try
        {
            String applyId = quotaApp.getApplyId();// 申请编号
            quotaApp.setApplyId(applyId.substring(3));// 截去平台代码
            quotaApp.setReqOperator(quotaApp.getReqOptName());
            quotaApp.setApprOperator(quotaApp.getApprOptName());
            // 组装报文体
            RegionPacketBody packetBody = RegionPacketBody.build(quotaApp.toString());
            // 组装报文头
            RegionPacketHeader packetHeader = RegionPacketHeader.build().setDestPlatformId(quotaApp.getPlatNo())
                    .setDestBizCode(AcrossPlatBizCode.TO_REGION_ALLOT_QUOTA.name());

            Object resultObj = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
            if ((int) resultObj == RespCode.SUCCESS.getCode())
            {// 同步成功
                quotaMapper.updateQuotaAllotSyncFlag(applyId, false, true);
            }
            else
            {
                log.error("同步配额分配数据到地区平台失败[param=" + quotaApp + "],地区平台返回码：" + resultObj);
            }
        }
        catch (Exception e)
        {
            log.error("同步配额分配数据到地区平台失败[param=" + quotaApp + "]", e);
            throw new HsException(RESErrorCode.RES_SYSC_QUOTA_ALLOT_DATA_ERROR.getCode(), "同步资源配额分配数据到地区平台失败[param="
                    + quotaApp + "]" + e);
        }
    }

    /**
     * 同步路由数据到总平台
     * 
     * @param applyId
     *            申请编号
     * @param platNo
     *            平台代码
     * @param resNoList
     *            批复互生号list
     * @throws HsException
     */
    @Override
    @Transactional
    public void syncRouteData(String applyId, String platNo, List<String> resNoList) throws HsException {
        try
        {
            // 调用总平台全局配置系统，增加资源号关联平台的路由规则，一级区域资源分配同步
            iGCSRouteRuleService.syncAddRouteRule(resNoList, platNo);

            // 更新路由数据同步标识
            quotaMapper.updateQuotaAllotSyncFlag(applyId, true, false);
        }
        catch (Exception e)
        {
            log.error("同步路由数据到总平台失败[applyId=" + applyId + ",platNo=" + platNo + ",resNoList=" + resNoList + "]", e);
            throw new HsException(RESErrorCode.RES_SYSC_ROUTE_DATA_TO_CENTER_PLAT_ERROR.getCode(),
                    "同步路由数据到总平台失败[applyId=" + applyId + ",platNo=" + platNo + ",resNoList=" + resNoList + "]" + e);
        }
    }

    /**
     * 同步平台公司信息到地区平台用户中心
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    @Override
    public void syncPlatInfo(PlatInfo platInfo) throws HsException {
        try
        {
            // 组装报文体
            JSONObject param = new JSONObject();
            param.put("entResNo", platInfo.getEntResNo());
            param.put("entCustName", platInfo.getEntCustName());
            param.put("emailA", platInfo.getEmailA());
            param.put("emailB", platInfo.getEmailB());
            param.put("optCustId", platInfo.getCreatedOptId());
            RegionPacketBody packetBody = RegionPacketBody.build(param.toString());
            // 组装报文头
            RegionPacketHeader packetHeader = RegionPacketHeader.build().setDestPlatformId(platInfo.getPlatNo())
                    .setDestBizCode(AcrossPlatBizCode.TO_REGION_INIT_PLAT_ENT.name());

            Object resultObj = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
            if ("ok".equals(resultObj))
            {// 同步成功
                initMapper.updatePlatInfoSync(platInfo.getPlatNo());
            }
            else
            {
                log.error("同步平台公司信息到地区平台用户中心失败[param=" + platInfo + "],地区平台返回信息：" + JSON.toJSONString(resultObj));
            }
        }
        catch (Exception e)
        {
            log.error("同步平台公司信息到地区平台用户中心失败[param=" + platInfo + "]", e);
            throw new HsException(RESErrorCode.RES_SYSC_PLAT_INFO_TO_AREA_PLAT_ERROR.getCode(),
                    "同步平台公司信息到地区平台用户中心失败[param=" + platInfo + "]" + e);
        }
    }

    @Override
    public void syncManageToUc(PlatMent platMent) throws HsException {
        try
        {
            // 组装报文体
            JSONObject param = new JSONObject();
            param.put("entResNo", platMent.getEntResNo());
            param.put("entCustName", platMent.getEntCustName());
            param.put("email", platMent.getEmail());
            param.put("optCustId", platMent.getCreatedOptId());

            RegionPacketBody packetBody = RegionPacketBody.build(param.toString());
            // 组装报文头
            RegionPacketHeader packetHeader = RegionPacketHeader.build().setDestPlatformId(platMent.getPlatNo())
                    .setDestBizCode(AcrossPlatBizCode.TO_REGION_INIT_M_ENT.name());

            Object resultObj = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
            if ("ok".equals(resultObj))
            {// 同步成功
                initMapper.updatePlatMentSync(platMent.getPlatNo(), platMent.getEntResNo(), true, false);
            }
            else
            {
                log.error("同步管理公司信息到地区平台用户中心失败[platMent=" + platMent + "],用户中心返回信息：" + JSON.toJSONString(resultObj));
            }
        }
        catch (Exception e)
        {
            log.error("同步管理公司信息到地区平台用户中心失败[platMent=" + platMent + "]", e);
            throw new HsException(RESErrorCode.RES_SYSC_MENT_INFO_TO_UC_ERROR.getCode(),
                    "同步管理公司信息到地区平台用户中心失败[platMent=" + platMent + "]" + e);
        }

    }

    @Override
    public void syncManageToBs(PlatMent platMent) throws HsException {
        try
        {
            // 组装报文体
            JSONObject param = new JSONObject();
            param.put("entResNo", platMent.getEntResNo());
            param.put("entCustName", platMent.getEntCustName());
            param.put("initQuota", platMent.getInitQuota());
            RegionPacketBody packetBody = RegionPacketBody.build(param.toString());
            // 组装报文头
            RegionPacketHeader packetHeader = RegionPacketHeader.build().setDestPlatformId(platMent.getPlatNo())
                    .setDestBizCode(AcrossPlatBizCode.TO_REGION_INIT_MAX_QUOTA.name());

            Object resultObj = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
            if ((int) resultObj == RespCode.SUCCESS.getCode())
            {// 同步成功
                initMapper.updatePlatMentSync(platMent.getPlatNo(), platMent.getEntResNo(), false, true);
            }
            else
            {
                log.error("同步管理公司信息到地区平台业务系统失败[platMent=" + platMent + "],业务系统返回信息：" + JSON.toJSONString(resultObj));
            }
        }
        catch (Exception e)
        {
            log.error("同步管理公司信息到地区平台业务系统失败[platMent=" + platMent + "]", e);
            throw new HsException(RESErrorCode.RES_SYSC_MENT_INFO_TO_BS_ERROR.getCode(),
                    "同步管理公司信息到地区平台业务系统失败[platMent=" + platMent + "]" + e);
        }

    }

}
