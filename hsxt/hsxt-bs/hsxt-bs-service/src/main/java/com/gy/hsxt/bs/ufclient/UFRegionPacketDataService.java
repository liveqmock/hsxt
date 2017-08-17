/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.ufclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.quota.interfaces.ISyncManageQuotaService;
import com.gy.hsxt.bs.tool.service.InsideInvokeCall;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataHeader;

/**
 * 
 * @Package: com.gy.hsxt.bs.ufclient
 * @ClassName: UFRegionPacketDataService
 * @Description: 综合前置接口本地实现类
 * 
 * @author: yangjianguo
 * @date: 2015-11-20 下午2:14:58
 * @version V1.0
 */
@Service("regionPacketDataService")
public class UFRegionPacketDataService implements IUFRegionPacketDataService {

    /** 内部实现类 **/
    @Autowired
    private InsideInvokeCall insideInvokeCall;

    @Autowired
    private ISyncManageQuotaService syncManageQuotaService;

    /**
     * 业务系统接收并处理UF接收到的跨地区平台报文数据
     * 
     * @param arg0
     * @return
     * @see com.gy.hsxt.uf.api.IUFRegionPacketDataService#handleReceived(com.gy.hsxt.uf.bean.packet.data.RegionPacketData)
     */
    @Override
    public Object handleReceived(RegionPacketData regionPacketData) {
        // UF报文头对象
        RegionPacketDataHeader header = regionPacketData.getHeader();

        // UF报文体内容对象
        Object bodyData = regionPacketData.getBody().getBodyContent();

        // 跨平台业务代码
        String bizCode = header.getBizCode();

        // 根据不同的业务代码进行相应处理
        if (AcrossPlatBizCode.TO_REGION_ALLOT_QUOTA.name().equals(bizCode))
        {
            try
            {
                insideInvokeCall.apprPlatQuota(JSON.parseObject(bodyData.toString(), PlatQuotaApp.class));
            }
            catch (HsException ex)
            {
                SystemLog.error(this.getClass().getName(), "handleReceived", ex.getErrorCode() + ":平台配额审批失败", ex);
                return ex.getErrorCode();
            }
        }
        else if (AcrossPlatBizCode.TO_REGION_INIT_MAX_QUOTA.name().equals(bizCode))
        {
            try
            {
                JSONObject jsonObj = JSONObject.parseObject((String) bodyData);
                String entResNo = jsonObj.getString("entResNo");
                String entCustName = jsonObj.getString("entCustName");
                Integer totalNum = jsonObj.getInteger("initQuota");
                syncManageQuotaService.initManageQuota(entResNo, entCustName, totalNum);
            }
            catch (HsException ex)
            {
                SystemLog.error(this.getClass().getName(), "handleReceived", ex.getErrorCode() + ":同步平台管理公司配额总数失败", ex);
                return ex.getErrorCode();
            }
        }
        return RespCode.SUCCESS.getCode();
    }
}
