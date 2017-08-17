/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.os.bs.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendRate;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 投资分红Controller
 * 
 * @Package: com.gy.hsxt.access.web.os.bs.controller
 * @ClassName: InvestDividendController
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-5-16 上午11:30:17
 * @version V3.0.0
 */
@Controller
@RequestMapping("/investDividend")
public class InvestDividendController {

    /**
     * 业务服务：投资分红服务
     */
    @Autowired
    private IBSInvestProfitService bsInvestProfitService;

    /**
     * 获取历史分红列表
     * 
     * @param pageSize
     *            页大小
     * @param curPage
     *            当前页
     * @return 历史分红列表
     */
    @ResponseBody
    @RequestMapping(value = "/getHisInvestDividendList", method = { RequestMethod.POST, RequestMethod.GET })
    public HttpRespEnvelope getHistInvestDividendList(
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = true, defaultValue = "1") Integer curPage) {
        // 声明分页对象
        Page page = new Page(curPage, pageSize);
        // 声明封装请求响应对象
        HttpRespEnvelope hre = new HttpRespEnvelope();
        try
        {
            // 调用业务服务投资分红服务查询历史分红数据
            PageData<CustomPointDividend> dividendListPage = bsInvestProfitService.getHisDividendList(page);
            // 未查询到数据
            if (dividendListPage == null)
            {
                // 封装一个空的分红列表对象
                hre.setData(new ArrayList<CustomPointDividend>());
            }
            else
            {
                hre.setData(dividendListPage.getResult());
                hre.setTotalRows(dividendListPage.getCount());
                hre.setCurPage(curPage);
            }
            hre.setSuccess(true);
            return hre;
        }
        catch (HsException hse)
        {
            hse.printStackTrace();
            hre.setRetCode(hse.getErrorCode());
            hre.setResultDesc(hse.getMessage());
            hre.setSuccess(false);
            return hre;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            hre.setRetCode(RespCode.UNKNOWN.getCode());
            hre.setResultDesc("未知错误，请查看日志");
            hre.setSuccess(false);
            return hre;
        }
    }

    /**
     * 添加调账申请的审批
     * 
     * @param result
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addYearDividendRate", method = { RequestMethod.POST, RequestMethod.GET })
    public HttpRespEnvelope addCheckBalanceResult(String investDividendYear, String dividendRate) {
        HttpRespEnvelope hre = new HttpRespEnvelope();
        try
        {
            // 声明年度投资分红对象
            DividendRate rate = new DividendRate();
            // 设置分红年份
            rate.setDividendYear(investDividendYear);
            // 设置年度分红比率
            rate.setDividendRate(dividendRate);
            // 调用业务服务积分投资服务保存年度分红比率
            bsInvestProfitService.saveDividendRate(rate);
            return hre;
        }
        catch (HsException hse)
        {
            hse.printStackTrace();
            hre.setRetCode(hse.getErrorCode());
            hre.setResultDesc(hse.getMessage());
            hre.setSuccess(false);
            return hre;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            hre.setRetCode(RespCode.UNKNOWN.getCode());
            hre.setResultDesc("未知错误，请查看日志");
            hre.setSuccess(false);
            return hre;
        }
    }
}
