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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.bp.api.IBusinessCustParamService;
import com.gy.hsxt.bp.bean.BusinessCustParamItems;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * 
 * @Package: com.gy.hsxt.access.web.os.bp.service
 * @ClassName: BusinessCustParamController
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-23 下午2:45:29
 * @version V1.0
 */

@Controller
@RequestMapping("/businessCustParamController")
public class BusinessCustParamController {

    @Autowired
    private IBusinessCustParamService iBusinessCustParamService;

    /**
     * 
     * 方法名称：新增客户业务参数 方法描述：把客户业务参数插入数据库
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusinessCustParam")
    public HttpRespEnvelope addBusinessCustParam(HttpServletRequest request) {

        String khqjbm = request.getParameter("khywcsgl_opt_khqjbm");// 客户全局编码
        String khmc = request.getParameter("khywcsgl_opt_khmc");// 系统名称
        String hsh = request.getParameter("khywcsgl_opt_hsh");// 互生号
        String zdm = request.getParameter("khywcsgl_opt_zdm");// 系统参数组代码
        String csxjm = request.getParameter("khywcsgl_opt_csxjm");// 系统参数项键名
        String csxmc = request.getParameter("khywcsgl_opt_csxmc");// 系统参数项名称
        String csxz = request.getParameter("khywcsgl_opt_csxz");// 系统参数项值
        String jhzt = request.getParameter("khywcsgl_opt_jhzt");// 激活状态
       
        String createdby = request.getParameter("custId");// 客户ID

        BusinessCustParamItems businessCustParam = new BusinessCustParamItems();
        businessCustParam.setCustId(khqjbm);
        businessCustParam.setCustName(khmc);
        businessCustParam.setHsResNo(hsh);
        businessCustParam.setSysGroupCode(zdm);
        businessCustParam.setSysItemsKey(csxjm);
        businessCustParam.setSysItemsName(csxmc);
        businessCustParam.setSysItemsValue(csxz);
        businessCustParam.setIsActive(jhzt);
        businessCustParam.setCreatedby(createdby);
        iBusinessCustParamService.addCustParamItems(businessCustParam);
        return new HttpRespEnvelope();
    }

    /**
     * 
     * 方法名称：查询客户业务参数 方法描述：查询客户业务参数数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchBusinessCustParam")
    public HttpRespEnvelope searchBusinessCustParam(HttpServletRequest request) {

        String custId = request.getParameter("custId");// 客户全局编码
        if ("".equals(custId) || "null".equals(custId) || "undefined".equals(custId))
        {
            custId = null;
        }
        String hsReNo = request.getParameter("hsReNo");// 互生号
        if ("".equals(hsReNo) || "null".equals(hsReNo) || "undefined".equals(hsReNo))
        {
            hsReNo = null;
        }
        String csxmc = request.getParameter("csxmc");// 系统参数项名称
        if ("".equals(csxmc) || "null".equals(csxmc) || "undefined".equals(csxmc))
        {
            csxmc = null;
        }
        String isActive = request.getParameter("isActive");// 激活状态
        if ("".equals(isActive) || "null".equals(isActive) || "undefined".equals(isActive))
        {
            isActive = null;
        }
        int pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request
                .getParameter("pageSize"));// 每页大小
        int curPage = request.getParameter("curPage") == null ? 1 : Integer.parseInt(request.getParameter("curPage"));// 当前页

        BusinessCustParamItems businessCustParam = new BusinessCustParamItems();
        businessCustParam.setCustId(custId);
        businessCustParam.setHsResNo(hsReNo);
        businessCustParam.setSysItemsName(csxmc);
        businessCustParam.setIsActive(isActive);
        Page page = new Page(curPage, pageSize);
        
        PageData<BusinessCustParamItems> pd = iBusinessCustParamService.searchCustParamItemsPage(businessCustParam, page);
        System.out.println("count:" + pd.getCount() + "results.size:" + pd.getResult().size());

        HttpRespEnvelope hre = new HttpRespEnvelope();
        if (pd != null)
        {
            hre.setData(pd.getResult());
            hre.setTotalRows(pd.getCount());
        }
        hre.setCurPage(page.getCurPage());
        System.out.println("data====" + hre.getData());
        return hre;
    }

    /**
     * 
     * 方法名称：删除客户业务参数 方法描述：删除客户业务参数数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusinessCustParam")
    public HttpRespEnvelope deleteBusinessCustParam(HttpServletRequest request) {

        String custItemsId = request.getParameter("custItemsId");// 客户参数ID
        String custId = request.getParameter("custId");// 客户全局编码
        String sysItemsKey = request.getParameter("sysItemsKey");// 系统参数项键名
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        System.out.println("====" + sysItemsKey);
        iBusinessCustParamService.deleteCustParamItems(custItemsId, custId, sysItemsKey);

        BusinessCustParamItems businessCustParamItems = new BusinessCustParamItems();
        Page page = new Page(curPage, pageSize);

        PageData<BusinessCustParamItems> pd = iBusinessCustParamService.searchCustParamItemsPage(businessCustParamItems, page);
        System.out.println("count:" + pd.getCount() + "results.size:" + pd.getResult().size());

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
     * 方法名称：更新客户业务参数 方法描述：更新客户业务参数数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBusinessCustParam")
    public HttpRespEnvelope updateBusinessCustParam(HttpServletRequest request) {

        String custItemsId = request.getParameter("custItemsId");// 客户ID
        String khqjbm = request.getParameter("khywcsgl_opt_khqjbm");// 客户全局编码
        String khmc = request.getParameter("khywcsgl_opt_khmc");// 系统名称
        String hsh = request.getParameter("khywcsgl_opt_hsh");// 互生号
        String zdm = request.getParameter("khywcsgl_opt_zdm");// 系统参数组代码
        String csxjm = request.getParameter("khywcsgl_opt_csxjm");// 系统参数项键名
        String csxmc = request.getParameter("khywcsgl_opt_csxmc");// 系统参数项名称
        String csxz = request.getParameter("khywcsgl_opt_csxz");// 系统参数项值
        String jhzt = request.getParameter("khywcsgl_opt_jhzt");// 激活状态
       
        String updatedby = request.getParameter("custId");// 客户ID

        BusinessCustParamItems businessCustParam = new BusinessCustParamItems();
        businessCustParam.setCustItemsId(custItemsId);
        businessCustParam.setCustId(khqjbm);
        businessCustParam.setCustName(khmc);
        businessCustParam.setHsResNo(hsh);
        businessCustParam.setSysGroupCode(zdm);
        businessCustParam.setSysItemsKey(csxjm);
        businessCustParam.setSysItemsName(csxmc);
        businessCustParam.setSysItemsValue(csxz);
        businessCustParam.setIsActive(jhzt);
        businessCustParam.setUpdatedby(updatedby);

        iBusinessCustParamService.updateCustParamItems(businessCustParam);
        return new HttpRespEnvelope();
    }

}
