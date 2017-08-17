/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.service.runnable;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.interfaces.IUpdateBuyHsbExpireTimeService;
import com.gy.hsxt.ao.mapper.BuyHsbMapper;
import com.gy.hsxt.common.exception.HsException;

/**
 * 更新兑换互生币超时状态定时任务
 * 
 * @Package: com.gy.hsxt.ao.service.runnable
 * @ClassName: UpdateBuyHsbExpireTimeService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-26 上午9:25:26
 * @version V3.0.0
 */
@Service
public class UpdateBuyHsbExpireTimeService implements IUpdateBuyHsbExpireTimeService {
    /**
     * 调度监听器
     */
    @Autowired
    private IDSBatchServiceListener batchServiceListener;

    @Autowired
    BuyHsbMapper buyHsbMapper;

    @Override
    public boolean excuteBatch(String executeId, Map<String, String> arg0) {
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "params[" + arg0 + "]", "帐户操作系统：扫描过期且未支付兑换互生币订单");
        // 设置执行状态
        batchServiceListener.reportStatus(executeId, 2, "帐户操作系统：开始扫描过期且未支付兑换互生币订单");
        try
        {
            buyHsbMapper.updateExpireAndNoPayList();
            // 返回调度状态
            batchServiceListener.reportStatus(executeId, 0, "帐户操作系统：更新过期且未支付兑换互生币订单成功");
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_UPDATE_EXPIRE_BUY_HSB_ERROR.getCode() + ":帐户操作系统：更新过期且未支付兑换互生币订单异常", e);
            batchServiceListener.reportStatus(executeId, 1, "帐户操作系统：更新过期且未支付兑换互生币订单失败" + e.getMessage());
            throw new HsException(AOErrorCode.AO_UPDATE_EXPIRE_BUY_HSB_ERROR.getCode(), "帐户操作系统：更新过期且未支付兑换互生币订单异常" + e);
        }
        return true;
    }
}
