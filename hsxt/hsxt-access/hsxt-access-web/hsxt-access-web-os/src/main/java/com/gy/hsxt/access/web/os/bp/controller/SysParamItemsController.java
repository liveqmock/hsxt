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
import com.gy.hsxt.bp.api.IBusinessSysParamService;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;
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
@RequestMapping("/sysParamItemsController")
public class SysParamItemsController {

    @Autowired
    private IBusinessSysParamService iBusinessSysParamService;

    /**
     * 
     * 方法名称：新增系统参数组 方法描述：把系统参数组插入数据库
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSysParamItems")
    public HttpRespEnvelope addSysParamItems(HttpServletRequest request,String custId) {

        String zdm = request.getParameter("zdm");// 系统参数组编号
        String csxdm = request.getParameter("csxdm");// 系统参数项键
        String csxmc = request.getParameter("csxmc");// 系统参数项名称
        String csxz = request.getParameter("csxz");// 系统参数项键值
        String fwjb = request.getParameter("fwjb");// 访问级别
        int fwjbInt = Integer.parseInt(fwjb);
        String jhzt = request.getParameter("jhzt");// 激活状态

        BusinessSysParamItems sysParamItems = new BusinessSysParamItems();
        sysParamItems.setSysGroupCode(zdm);
        sysParamItems.setSysItemsKey(csxdm);
        sysParamItems.setSysItemsName(csxmc);
        sysParamItems.setSysItemsValue(csxz);
        sysParamItems.setAccessLevel(fwjbInt);
        sysParamItems.setIsActive(jhzt);
        sysParamItems.setCreatedby(custId);
        iBusinessSysParamService.addSysParamItems(sysParamItems);
        return new HttpRespEnvelope();
    }

    /**
     * 
     * 方法名称：查询系统参数组 方法描述：查询系统参数组数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchSysParamItems")
    public HttpRespEnvelope searchSysParamItems(HttpServletRequest request) {

        String sysGroupCode = request.getParameter("sysGroupCode");// 系统参数组编号
        if ("".equals(sysGroupCode) || "null".equals(sysGroupCode) || "undefined".equals(sysGroupCode))
        {
            sysGroupCode = null;
        }
        String sysItemsKey = request.getParameter("sysItemsKey");// 系统参数项键
        if ("".equals(sysItemsKey) || "null".equals(sysItemsKey) || "undefined".equals(sysItemsKey))
        {
            sysItemsKey = null;
        }
        String isActive = request.getParameter("isActive");// 激活状态
        if ("".equals(isActive) || "null".equals(isActive) || "undefined".equals(isActive))
        {
            isActive = null;
        }
        String noPageSearchFlag = request.getParameter("noPageSearchFlag");// 不做分页查询的标识

        int pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request
                .getParameter("pageSize"));// 每页大小
        int curPage = request.getParameter("curPage") == null ? 1 : Integer.parseInt(request.getParameter("curPage"));// 当前页

        BusinessSysParamItems sysParamItems = new BusinessSysParamItems();
        sysParamItems.setSysGroupCode(sysGroupCode);
        sysParamItems.setSysItemsKey(sysItemsKey);
        sysParamItems.setIsActive(isActive);
        Page page = new Page(curPage, pageSize);
        List<BusinessSysParamItems> pd = null;
        int totalRows = 0;
        if (!"".equals(noPageSearchFlag) && !"null".equals(noPageSearchFlag) && !"undefined".equals(noPageSearchFlag)
                && null != noPageSearchFlag)
        {
            pd = iBusinessSysParamService.searchSysParamItemsList(sysGroupCode);
            totalRows = pd.size();
        }
        else
        {
            PageData<BusinessSysParamItems> pagedata = iBusinessSysParamService.searchSysParamItemsPage(sysParamItems, page);
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
     * 方法名称：删除系统参数组 方法描述：删除系统参数组数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteSysParamItems")
    public HttpRespEnvelope deleteSysParamItems(HttpServletRequest request) {

        String sysItemsCode = request.getParameter("sysItemsCode");// 系统参数项编号
        String sysGroupCode = request.getParameter("sysGroupCode");// 系统参数组编号
        String sysItemsKey = request.getParameter("sysItemsKey");// 系统参数项键名
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        System.out.println("====" + sysItemsCode);
        iBusinessSysParamService.deleteSysParamItems(sysItemsCode, sysGroupCode, sysItemsKey);

        BusinessSysParamItems sysParamItems = new BusinessSysParamItems();
        Page page = new Page(curPage, pageSize);

        PageData<BusinessSysParamItems> pd = iBusinessSysParamService.searchSysParamItemsPage(sysParamItems, page);
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
     * 方法名称：更新系统参数组 方法描述：更新系统参数组数据库数据
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateSysParamItems")
    public HttpRespEnvelope updateSysParamItems(HttpServletRequest request) {

        String sysItemsCode = request.getParameter("sysItemsCode");// 系统参数组编号
        String zdm = request.getParameter("xtcsxgl_opt_zdm");// 系统参数组编号
        String csxdm = request.getParameter("xtcsxgl_opt_csxdm");// 系统参数项键
        String csxmc = request.getParameter("xtcsxgl_opt_csxmc");// 系统参数项名称
        String csxz = request.getParameter("xtcsxgl_opt_csxz");// 系统参数项键值

        String fwjb = request.getParameter("xtcsxgl_opt_fwjb");// 访问级别
        System.out.println("fwjb=============" + fwjb);

        String jhzt = request.getParameter("xtcsxgl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID
        System.out.println("jhzt=====" + jhzt);
        BusinessSysParamItems sysParamItems = new BusinessSysParamItems();
        sysParamItems.setSysItemsCode(sysItemsCode);
        sysParamItems.setSysGroupCode(zdm);
        sysParamItems.setSysItemsKey(csxdm);
        sysParamItems.setSysItemsName(csxmc);
        sysParamItems.setSysItemsValue(csxz);
        if (!"undefined".equals(fwjb) && null != fwjb && !"null".equals(fwjb) && !"".equals(fwjb))
        {
            int fwjbInt = Integer.parseInt(fwjb);
            sysParamItems.setAccessLevel(fwjbInt);
        }

        sysParamItems.setIsActive(jhzt);
        sysParamItems.setCreatedby(custId);

        iBusinessSysParamService.updateSysParamItems(sysParamItems);
        return new HttpRespEnvelope();
    }

}
