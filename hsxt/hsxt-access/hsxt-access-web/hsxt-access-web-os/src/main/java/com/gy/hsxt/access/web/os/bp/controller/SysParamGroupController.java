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
import com.gy.hsxt.bp.bean.BusinessSysParamGroup;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * 
 * @Package: com.gy.hsxt.access.web.os.bp.service
 * @ClassName: SysParamGroupController
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-23 下午2:45:29
 * @version V1.0
 */

@Controller
@RequestMapping("/sysParamGroupController")
public class SysParamGroupController {

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
    @RequestMapping(value = "/addSysParamGroup")
    public HttpRespEnvelope addSysParamGroup(HttpServletRequest request) {

        String zdm = request.getParameter("xtcszgl_opt_zdm");// 组代码
        String zmc = request.getParameter("xtcszgl_opt_zmc");// 组名称
        String cslx = request.getParameter("xtcszgl_opt_cslx");// 参数类型
        String jhzt = request.getParameter("xtcszgl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID

        BusinessSysParamGroup sysParamGroup = new BusinessSysParamGroup();
        sysParamGroup.setSysGroupCode(zdm);
        sysParamGroup.setSysGroupName(zmc);
        sysParamGroup.setIsPublic(cslx);
        sysParamGroup.setIsActive(jhzt);
        sysParamGroup.setCreatedby(custId);
        System.out.println("zdm:" + zdm + ",zmc:" + zmc + ",cslx:" + cslx + ",jhzt:" + jhzt + ",custId:" + custId);
        iBusinessSysParamService.addSysParamGroup(sysParamGroup);
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
    @RequestMapping(value = "/searchSysParamGroup")
    public HttpRespEnvelope searchSysParamGroup(HttpServletRequest request) {

        String zdm = request.getParameter("xtcszgl_opt_zdm");// 组代码
        if("".equals(zdm) || "null".equals(zdm) || "undefined".equals(zdm)){
            zdm = null;
        }
        String zmc = request.getParameter("xtcszgl_opt_zmc");// 组名称
        if("".equals(zmc) || "null".equals(zmc) || "undefined".equals(zmc)){
            zmc = null;
        }
        String cslx = request.getParameter("xtcszgl_opt_cslx");// 参数类型
        if("".equals(cslx) || "null".equals(cslx) || "undefined".equals(cslx)){
            cslx = null;
        }
        String jhzt = request.getParameter("xtcszgl_opt_jhzt");// 激活状态
        if("".equals(jhzt) || "null".equals(jhzt) || "undefined".equals(jhzt)){
            jhzt = null;
        }
        
        String noPageSearchFlag = request.getParameter("noPageSearchFlag");// 不做分页查询的标识
        
        int pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request
                .getParameter("pageSize"));// 每页大小
        int curPage = request.getParameter("curPage") == null ? 1 : 
            Integer.parseInt(request.getParameter("curPage"));// 当前页

        System.out.println("zdm:" + zdm + ",zmc:" + zmc + ",cslx:" + cslx + ",jhzt:" + jhzt + ",curPage:"+curPage+",pageSize:" + pageSize);
        
        BusinessSysParamGroup sysParamGroup = new BusinessSysParamGroup();
        sysParamGroup.setSysGroupCode(zdm);
        sysParamGroup.setSysGroupName(zmc);
        sysParamGroup.setIsPublic(cslx);
        sysParamGroup.setIsActive(jhzt);
        Page page = new Page(curPage, pageSize);

        List<BusinessSysParamGroup> pd = null;
        int totalRows = 0;
        if (!"".equals(noPageSearchFlag) && !"null".equals(noPageSearchFlag) && !"undefined".equals(noPageSearchFlag)
                && null != noPageSearchFlag)
        {
            pd = iBusinessSysParamService.searchSysParamGroupList();
            totalRows = pd.size();
        }
        else
        {
            PageData<BusinessSysParamGroup> pagedata = iBusinessSysParamService.searchSysParamGroupPage(sysParamGroup, page);
            totalRows = pagedata.getCount();
            pd = pagedata.getResult();
        }
        System.out.println("totalRows:" + totalRows);
        
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
    @RequestMapping(value = "/deleteSysParamGroup")
    public HttpRespEnvelope deleteSysParamGroup(HttpServletRequest request) {

        String sysGroupCode = request.getParameter("xtcszgl_opt_zdm");// 组代码
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        System.out.println("====" + sysGroupCode);
        iBusinessSysParamService.deleteSysParamGroup(sysGroupCode);

        BusinessSysParamGroup sysParamGroup = new BusinessSysParamGroup();
        Page page = new Page(curPage, pageSize);

        PageData<BusinessSysParamGroup> pd = iBusinessSysParamService.searchSysParamGroupPage(sysParamGroup, page);
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
    @RequestMapping(value = "/updateSysParamGroup")
    public HttpRespEnvelope updateSysParamGroup(HttpServletRequest request) {

        String zdm = request.getParameter("xtcszgl_opt_zdm");// 组代码
        String zmc = request.getParameter("xtcszgl_opt_zmc");// 组名称
        String cslx = request.getParameter("xtcszgl_opt_cslx");// 参数类型
        String jhzt = request.getParameter("xtcszgl_opt_jhzt");// 激活状态
        String custId = request.getParameter("custId");// 客户ID

        BusinessSysParamGroup sysParamGroup = new BusinessSysParamGroup();
        sysParamGroup.setSysGroupCode(zdm);
        sysParamGroup.setSysGroupName(zmc);
        sysParamGroup.setIsPublic(cslx);
        sysParamGroup.setIsActive(jhzt);
        sysParamGroup.setUpdatedby(custId);
        System.out.println("zdm:" + zdm + ",zmc:" + zmc + ",cslx:" + cslx + ",jhzt:" + jhzt + ",custId:" + custId);
        iBusinessSysParamService.updateSysParamGroup(sysParamGroup);
        return new HttpRespEnvelope();
    }
    
}
