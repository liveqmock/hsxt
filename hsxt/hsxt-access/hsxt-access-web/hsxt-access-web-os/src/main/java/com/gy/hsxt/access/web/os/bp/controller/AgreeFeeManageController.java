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

package com.gy.hsxt.access.web.os.bp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.bp.api.IBusinessAgreeService;
import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * 
 * @Package: com.gy.hsxt.access.web.os.bp.service
 * @ClassName: AgreeFeeManageController
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-23 下午2:45:29
 * @version V1.0
 */

@Controller
@RequestMapping("/agreeFeeManageController")
public class AgreeFeeManageController {

    @Autowired
    private IBusinessAgreeService iBusinessAgreeService;

    /**
     * 
     * 方法名称：新增协议费用管理 方法描述
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addAgreeFeeManage")
    public HttpRespEnvelope addAgreeFeeManage(HttpServletRequest request) {

        String xydm = request.getParameter("xyfygl_opt_xydm");// 协议代码
        String jflx = request.getParameter("xyfygl_opt_jflx");// 计费类型
        String kflx = request.getParameter("xyfygl_opt_kflx");// 扣费类型
        String fyfw_min = request.getParameter("xyfygl_opt_fyfw_min");// 最小金额
        String fyfw_max = request.getParameter("xyfygl_opt_fyfw_max");// 最大金额
        String fybl = request.getParameter("xyfygl_opt_fybl");// 费用比例
        String fymc = request.getParameter("xyfygl_opt_fymc");// 费用名称
        String fydm = request.getParameter("xyfygl_opt_fydm");// 费用代码
        String jfqx = request.getParameter("xyfygl_opt_jfqx");// 缴费期限
        int jfqxInt = Integer.parseInt(jfqx);
        String fylx = request.getParameter("xyfygl_opt_fylx");// 费用类型
        String fyje = request.getParameter("xyfygl_opt_fyje");// 费用金额
        String bz = request.getParameter("xyfygl_opt_bz");// 费用币种
        String jhzt = request.getParameter("xyfygl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID

        BusinessAgreeFee businessAgreeFee = new BusinessAgreeFee();
        businessAgreeFee.setAgreeCode(xydm);
        businessAgreeFee.setAgreeFeeCode(fydm);
        businessAgreeFee.setAgreeFeeName(fymc);
        businessAgreeFee.setBillingType(jflx);
        businessAgreeFee.setChargingType(kflx);
        businessAgreeFee.setFeeType(fylx);
        businessAgreeFee.setCurrencyCode(bz);
        businessAgreeFee.setPayPeriod(jfqxInt);
        businessAgreeFee.setFeeAmount(fyje);
        businessAgreeFee.setFeeRatio(fybl);
        businessAgreeFee.setFromAmount(fyfw_min);
        businessAgreeFee.setToAmount(fyfw_max);
        businessAgreeFee.setIsActive(jhzt);
        businessAgreeFee.setCreatedby(custId);
        iBusinessAgreeService.addAgreeFee(businessAgreeFee);
        return new HttpRespEnvelope();
    }

    /**
     * 
     * 方法名称：查询协议费用管理 方法
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchAgreeFeeManage")
    public HttpRespEnvelope searchAgreeFeeManage(HttpServletRequest request) {

        String xymc = request.getParameter("xyfygl_xymc");// 协议名称
        if ("".equals(xymc) || "null".equals(xymc) || "undefined".equals(xymc))
        {
            xymc = null;
        }
        String xydm = request.getParameter("xyfygl_xydm");// 协议代码
        if ("".equals(xydm) || "null".equals(xydm) || "undefined".equals(xydm))
        {
            xydm = null;
        }

        String fymc = request.getParameter("xyfygl_fymc");// 费用名称
        if ("".equals(fymc) || "null".equals(fymc) || "undefined".equals(fymc))
        {
            fymc = null;
        }
        String fydm = request.getParameter("xyfygl_fydm");// 费用代码
        if ("".equals(fydm) || "null".equals(fydm) || "undefined".equals(fydm))
        {
            fydm = null;
        }
        String isActive = request.getParameter("xygl_jhzt");// 激活状态
        if ("".equals(isActive) || "null".equals(isActive) || "undefined".equals(isActive))
        {
            isActive = null;
        }
        String noPageSearchFlag = request.getParameter("noPageSearchFlag");// 不做分页查询的标识

        int pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request
                .getParameter("pageSize"));// 每页大小
        int curPage = request.getParameter("curPage") == null ? 1 : Integer.parseInt(request.getParameter("curPage"));// 当前页

        BusinessAgreeFee businessAgreeFee = new BusinessAgreeFee();
        businessAgreeFee.setAgreeCode(xydm);
        businessAgreeFee.setAgreeFeeName(fymc);
        businessAgreeFee.setAgreeFeeCode(fydm);
        businessAgreeFee.setIsActive(isActive);
        Page page = new Page(curPage, pageSize);

        List<BusinessAgreeFee> pd = null;
        int totalRows = 0;
        if (!"".equals(noPageSearchFlag) && !"null".equals(noPageSearchFlag) && !"undefined".equals(noPageSearchFlag)
                && null != noPageSearchFlag)
        {
            pd = iBusinessAgreeService.searchAgreeFeeList(xydm);
            totalRows = pd.size();
        }
        else
        {
            PageData<BusinessAgreeFee> pagedata = iBusinessAgreeService.searchAgreeFeePage(businessAgreeFee, page);
            totalRows = pagedata.getCount();
            pd = pagedata.getResult();
        }

        HttpRespEnvelope hre = new HttpRespEnvelope();
        if (pd != null)
        {
            hre.setData(pd);
            hre.setTotalRows(totalRows);
        }
        hre.setCurPage(page.getCurPage());
        return hre;
    }

    /**
     * 
     * 方法名称：删除协议费用管理 方法描述
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAgreeFeeManage")
    public HttpRespEnvelope deleteAgreeFeeManage(HttpServletRequest request) {

        String agreeFeeId = request.getParameter("agreeFeeId");// 系统参数项编号
        String agreeCode = request.getParameter("agreeCode");// 系统参数项编号
        String agreeFeeCode = request.getParameter("agreeFeeCode");// 系统参数项编号
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        iBusinessAgreeService.deleteAgreeFee(agreeFeeId, agreeCode, agreeFeeCode);

        BusinessAgreeFee businessAgreeFee = new BusinessAgreeFee();
        Page page = new Page(curPage, pageSize);

        PageData<BusinessAgreeFee> pd = iBusinessAgreeService.searchAgreeFeePage(businessAgreeFee, page);

        HttpRespEnvelope hre = new HttpRespEnvelope();
        if (pd != null)
        {
            hre.setData(pd.getResult());
            hre.setTotalRows(pd.getCount());
        }
        hre.setCurPage(page.getCurPage());
        return hre;
    }

    /**
     * 
     * 方法名称：更新协议费用管理
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAgreeFeeManage")
    public HttpRespEnvelope updateAgreeFeeManage(HttpServletRequest request) {

        String agreeFeeId = request.getParameter("agreeFeeId");// 协议费用编号
        String xydm = request.getParameter("xyfygl_opt_xydm");// 协议代码
        String jflx = request.getParameter("xyfygl_opt_jflx");// 计费类型
        String kflx = request.getParameter("xyfygl_opt_kflx");// 扣费类型
        String fyfw_min = request.getParameter("xyfygl_opt_fyfw_min");// 最小金额
        String fyfw_max = request.getParameter("xyfygl_opt_fyfw_max");// 最大金额
        String fybl = request.getParameter("xyfygl_opt_fybl");// 费用比例
        String fymc = request.getParameter("xyfygl_opt_fymc");// 费用名称
        String fydm = request.getParameter("xyfygl_opt_fydm");// 费用代码
        String jfqx = request.getParameter("xyfygl_opt_jfqx");// 缴费期限
        int jfqxInt = Integer.parseInt(jfqx);
        String fylx = request.getParameter("xyfygl_opt_fylx");// 费用类型
        String fyje = request.getParameter("xyfygl_opt_fyje");// 费用金额
        String bz = request.getParameter("xyfygl_opt_bz");// 费用币种
        String jhzt = request.getParameter("xyfygl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID

        BusinessAgreeFee businessAgreeFee = new BusinessAgreeFee();
        businessAgreeFee.setAgreeFeeId(agreeFeeId);
        businessAgreeFee.setAgreeCode(xydm);
        businessAgreeFee.setAgreeFeeCode(fydm);
        businessAgreeFee.setAgreeFeeName(fymc);
        businessAgreeFee.setBillingType(jflx);
        businessAgreeFee.setChargingType(kflx);
        businessAgreeFee.setFeeType(fylx);
        businessAgreeFee.setCurrencyCode(bz);
        businessAgreeFee.setPayPeriod(jfqxInt);
        businessAgreeFee.setFeeAmount(fyje);
        businessAgreeFee.setFeeRatio(fybl);
        businessAgreeFee.setFromAmount(fyfw_min);
        businessAgreeFee.setToAmount(fyfw_max);
        businessAgreeFee.setIsActive(jhzt);
        businessAgreeFee.setCreatedby(custId);
        iBusinessAgreeService.updateAgreeFee(businessAgreeFee);
        return new HttpRespEnvelope();
    }

}
