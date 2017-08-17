/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.platformDebit;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.platformDebit.ApsHsbDeduction;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.common.exception.HsException;

/***
 * 平台扣款业务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.platformDebit  
 * @ClassName: PlatformDebitController 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-4-20 上午11:26:08 
 * @version V1.0
 */
@Controller
@RequestMapping("platformDebit")
public class PlatformDebitController extends BaseController {

    @Resource
    private IPlatformDebitService platformDebitService;

    /**
     * 平台扣款申请
     * 
     * @param request
     * @param apsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "apply_hsb_deduction", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyHsbDeduction(HttpServletRequest request, ApsHsbDeduction ahd, ApsBase apsBase) {
        HttpRespEnvelope hre = null;
        try
        {
            SystemLog.info(this.getClass().getName(), "applyHsbDeduction", "扣款申请入参：" + JSON.toJSONString(ahd));

            // 验证token
            super.verifySecureToken(request);

            // 参数有效性验证
            ahd.checkData();

            // 申请扣款
            platformDebitService.applyHsbDeduction(ahd, apsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "applyHsbDeduction", "扣款申请异常", e);
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 平台互生币扣款申请分页查询
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "hsb_deduction_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope hsbDeductionPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            SystemLog.info(this.getClass().getName(), "hsbDeductionPage", "平台互生币扣款申请分页查询入参："
                    + JSON.toJSONString(request.getParameterMap()));

            // 验证token
            super.verifySecureToken(request);

            // 分页查询
            request.setAttribute("serviceName", platformDebitService);
            request.setAttribute("methodName", "queryHsbDeductionListPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "hsbDeductionPage", "平台互生币扣款申请分页查询异常", e);
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 平台互生币扣款申请复核分页查询
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deduct_review_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope deductReviewPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            SystemLog.info(this.getClass().getName(), "deductReviewPage", "平台互生币扣款申请复核分页查询入参："
                    + JSON.toJSONString(request.getParameterMap()));

            // 验证token
            super.verifySecureToken(request);

            // 分页查询
            request.setAttribute("serviceName", platformDebitService);
            request.setAttribute("methodName", "queryTaskListPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "deductReviewPage", "平台互生币扣款申请复核分页查询异常", e);
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 通过互生号查找详情
     * 
     * @param request
     * @param queryResNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_resNo_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getResNoInfo(HttpServletRequest request, String queryResNo) {
        HttpRespEnvelope hre = null;
        try
        {
            SystemLog.info(this.getClass().getName(), "getResNoInfo", "通过互生号查找详情入参："
                    + JSON.toJSONString(request.getParameterMap()));

            // 验证token
            super.verifySecureToken(request);

            // 数据验证
            RequestUtil.checkParamIsNotEmpty(new Object[] { queryResNo, ASRespCode.PW_HSRESNO_INVALID }); // 互生号

            // 获取互生号关联的企业或持卡人明细
            Map<String, Object> infoMap = platformDebitService.getResNoInfo(queryResNo);
            hre = new HttpRespEnvelope(infoMap);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "getResNoInfo", "通过互生号查找详情异常", e);
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    /**
     * 扣款申请复核
     * @param request
     * @param hd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "appr_hsbDeduction", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope apprHsbDeduction(HttpServletRequest request, HsbDeduction hd, ApsBase apsBase) {
        HttpRespEnvelope hre = null;
        try
        {
            SystemLog.info(this.getClass().getName(), "apprHsbDeduction", "扣款申请复核入参：" + JSON.toJSONString(hd));

            // 验证token
            super.verifySecureToken(request);

            // 数据验证
            RequestUtil.checkParamIsNotEmpty(new Object[] { hd.getApplyId(), ASRespCode.AS_PARAM_INVALID } ); // 申请编号
                   
            // 状态格式验证
            if (null == hd.getStatus()) {
                throw new HsException(ASRespCode.AS_PARAM_INVALID);
            }

            // 复核
            platformDebitService.apprHsbDeduction(hd, apsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "apprHsbDeduction", "扣款申请复核异常", e);
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    
    /**
     * 获取扣款申请详情
     * @param request
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "query_deductDetail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryDeductDetail(HttpServletRequest request, String applyId) {
        HttpRespEnvelope hre = null;
        try
        {
            SystemLog.info(this.getClass().getName(), "queryDeductDetail", "获取扣款申请详情入参：" + JSON.toJSONString(applyId));

            // 验证token
            super.verifySecureToken(request);

            // 数据验证
            RequestUtil.checkParamIsNotEmpty(new Object[] { applyId, ASRespCode.AS_PARAM_INVALID } ); // 申请编号
                   
            // 获取详情
            HsbDeduction hDeduction = platformDebitService.queryDetailById(applyId);
            hre = new HttpRespEnvelope(hDeduction);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "queryDeductDetail", "获取扣款申请详情异常", e);
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    

    @Override
    protected IBaseService getEntityService() {
        return platformDebitService;
    }

    public static void main(String[] args) {
        String[] a = { "1", "2", "3" };

        String[] b = new String[a.length + 10];
        System.out.println("=======" + b.length);
        b = a;
        System.out.println("--------------------------------");
        System.out.println("=======" + b.length);
        System.out.println(b.length);
    }

}
