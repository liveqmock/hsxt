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
import com.gy.hsxt.bp.bean.BusinessAgree;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * 
 * @Package: com.gy.hsxt.access.web.os.bp.service
 * @ClassName: SysParamItemsController
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-23 下午2:45:29
 * @version V1.0
 */

@Controller
@RequestMapping("/agreeManageController")
public class AgreeManageController {

    @Autowired
    private IBusinessAgreeService iBusinessAgreeService;

    /**
     * 
     * 方法名称：新增协议管理 方法描述：把协议管理插入数据库
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addAgreeManage")
    public HttpRespEnvelope addAgreeManage(HttpServletRequest request) {

        String xymc = request.getParameter("xygl_opt_xymc");// 协议名称
        String xydm = request.getParameter("xygl_opt_xydm");// 协议代码
        String jhzt = request.getParameter("xygl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID

        BusinessAgree agreeParamManager = new BusinessAgree();
        agreeParamManager.setAgreeCode(xydm);
        agreeParamManager.setAgreeName(xymc);
        agreeParamManager.setIsActive(jhzt);
        agreeParamManager.setCreatedby(custId);
        iBusinessAgreeService.addAgree(agreeParamManager);
        return new HttpRespEnvelope();
    }

    /**
     * 
     * 方法名称：查询协议管理 方法描述：查询协议管理数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchAgreeManage")
    public HttpRespEnvelope searchAgreeManage(HttpServletRequest request) {

        String mc = request.getParameter("xygl_mc");// 协议名称
        if ("".equals(mc) || "null".equals(mc) || "undefined".equals(mc))
        {
            mc = null;
        }
        String dm = request.getParameter("xygl_dm");// 协议代码
        if ("".equals(dm) || "null".equals(dm) || "undefined".equals(dm))
        {
            dm = null;
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

        BusinessAgree businessAgree = new BusinessAgree();
        businessAgree.setAgreeCode(dm);
        businessAgree.setAgreeName(mc);
        businessAgree.setIsActive(isActive);
        Page page = new Page(curPage, pageSize);

        List<BusinessAgree> pd = null;
        int totalRows = 0;
        if (!"".equals(noPageSearchFlag) && !"null".equals(noPageSearchFlag) && !"undefined".equals(noPageSearchFlag)
                && null != noPageSearchFlag)
        {
            pd = iBusinessAgreeService.searchAgreeList();
            totalRows = pd.size();
        }
        else
        {
            PageData<BusinessAgree> pagedata = iBusinessAgreeService.searchAgreePage(businessAgree, page);
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
     * 方法名称：删除协议管理 方法描述：删除协议管理数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAgreeManage")
    public HttpRespEnvelope deleteAgreeManage(HttpServletRequest request) {

        String agreeId = request.getParameter("agreeId");// 系统参数项编号
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        iBusinessAgreeService.deleteAgree(agreeId);

        BusinessAgree businessAgree = new BusinessAgree();
        Page page = new Page(curPage, pageSize);

        PageData<BusinessAgree> pd = iBusinessAgreeService.searchAgreePage(businessAgree, page);

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
     * 方法名称：更新协议管理 方法描述：更新协议管理数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAgreeManage")
    public HttpRespEnvelope updateAgreeManage(HttpServletRequest request) {

        String agreeId = request.getParameter("agreeId");// 协议ID
        String agreeName = request.getParameter("xygl_opt_xymc");// 协议名称
        String agreeCode = request.getParameter("xygl_opt_xydm");// 协议代码
        String jhzt = request.getParameter("xygl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID
        System.out.println("jhzt====="+jhzt);
        BusinessAgree businessAgree = new BusinessAgree();
        businessAgree.setAgreeId(agreeId);
        businessAgree.setAgreeCode(agreeCode);
        businessAgree.setAgreeName(agreeName);
        businessAgree.setIsActive(jhzt);
        businessAgree.setCreatedby(custId);

        iBusinessAgreeService.updateAgree(businessAgree);
        return new HttpRespEnvelope();
    }

}
