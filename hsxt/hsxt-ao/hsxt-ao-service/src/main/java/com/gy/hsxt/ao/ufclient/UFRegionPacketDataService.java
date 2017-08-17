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

package com.gy.hsxt.ao.ufclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.ProxyBuyHsbCancel;
import com.gy.hsxt.ao.enumtype.ProxyTransMode;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataHeader;

/**
 * @Description: 处理跨平台代兑互生币业务
 * 
 * @Package: com.gy.hsxt.ao.ufclient
 * @ClassName: UFRegionPacketDataService
 * @author: yangjianguo
 * @date: 2016-1-16 上午11:29:49
 * @version V1.0
 */
@Service("regionPacketDataService")
public class UFRegionPacketDataService implements IUFRegionPacketDataService {

    @Autowired
    IAOExchangeHsbService exchangeHsbService;

    /**
     * 业务系统接收并处理UF接收到的跨地区平台报文数据
     * 
     * @param regionPacketData
     *            消息报文
     * @return
     * @see com.gy.hsxt.uf.api.IUFRegionPacketDataService#handleReceived(com.gy.hsxt.uf.bean.packet.data.RegionPacketData)
     */
    @Override
    public Object handleReceived(RegionPacketData regionPacketData) {
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "AO处理跨平台代兑业务", "param=" + JSON.toJSONString(regionPacketData));
        JSONObject result = new JSONObject();
        try
        {
            // UF报文头对象
            RegionPacketDataHeader header = regionPacketData.getHeader();
            // UF报文体内容对象
            Object bodyData = regionPacketData.getBody().getBodyContent();

            // 跨平台业务代码
            String bizCode = header.getBizCode();
            // 根据不同的业务代码进行相应处理
            if (AcrossPlatBizCode.PROXY_HSB_FOR_REMOTE_P.name().equals(bizCode))
            { // 本地消费者异地代兑互生币
                ProxyBuyHsb proxyBuyHsb = JSON.parseObject(bodyData.toString(), ProxyBuyHsb.class);
                proxyBuyHsb.setTransMode(ProxyTransMode.DIFF_ENT_TO_LOCAL_CUST.getCode());
                String buyHsbAmt = exchangeHsbService.savePosProxyBuyHsb(proxyBuyHsb);
                result.put("respDesc", buyHsbAmt);
            }
            else if (AcrossPlatBizCode.PROXY_HSB_FOR_REMOTE_P_REVERSE.name().equals(bizCode))
            { // 本地消费者异地代兑互生币冲正
                ProxyBuyHsbCancel proxyBuyHsbCancel = JSON.parseObject(bodyData.toString(), ProxyBuyHsbCancel.class);
                exchangeHsbService.reverseProxyBuyHsb(proxyBuyHsbCancel, "");
                result.put("respDesc", "ok");
            }
            result.put("respCode", RespCode.SUCCESS.getCode());

        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode()
                    + ":处理跨平台代兑业务出现异常,param=" + JSON.toJSONString(regionPacketData), e);
            result.put("respCode", RespCode.FAIL.getCode());
            result.put("respDesc", e.getErrorCode() + ":" + e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), "处理跨平台代兑业务出现异常,param="
                    + JSON.toJSONString(regionPacketData), e);
            result.put("respCode", RespCode.FAIL.getCode());
            result.put("respDesc", e.getMessage());
        }
        return result;
    }
}
