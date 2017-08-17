/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.order.service.runnable;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.order.interfaces.IAutoDividendService;
import com.gy.hsxt.bs.order.service.InvestProfitTaskService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 生成年度投资分红记录定时任务
 * 
 * @Package: com.gy.hsxt.bs.order.service.runnable
 * @ClassName: AutoDividendService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-25 下午8:14:22
 * @version V3.0.0
 */
@Service
public class AutoDividendService implements IAutoDividendService {
    @Resource
    InvestProfitTaskService investProfitTaskService;

    /**
     * 调度监听器
     */
    @Resource
    private IDSBatchServiceListener batchServiceListener;

    /**
     * 调度执行方法
     * 
     * @param executeId
     *            任务执行编号
     * @param param
     *            参数
     * @return
     * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.lang.String,
     *      java.util.Map)
     */
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> param) {
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "params[" + param + "]", "业务服务系统：生成年度投资分红记录");
        // 设置执行状态
        batchServiceListener.reportStatus(executeId, 2, "业务服务系统：开始执行生成年度投资分红记录");

        try
        {
            // 获取分红年份
            String dividendYear = param.get("dividend_year");
            investProfitTaskService.genPointDividendRecord(dividendYear);
            // 返回调度状态
            batchServiceListener.reportStatus(executeId, 0, "业务服务系统：生成年度投资分红记录成功");
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + "："
                    + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_AUTO_GEN_POINT_DIVIDEND_RECORD_ERROR.getCode() + ":业务服务系统：生成年度投资分红记录异常", e);
            batchServiceListener.reportStatus(executeId, 1, "业务服务系统：生成年度投资分红记录异常" + e);
            throw new HsException(BSRespCode.BS_AUTO_GEN_POINT_DIVIDEND_RECORD_ERROR.getCode(), "业务服务系统：生成年度投资分红记录异常"
                    + e);
        }
        return true;
    }
}
