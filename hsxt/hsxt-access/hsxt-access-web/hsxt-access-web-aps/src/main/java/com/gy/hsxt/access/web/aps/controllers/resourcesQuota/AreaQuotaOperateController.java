/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.resourcesQuota;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsCityQuotaApp;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsPlatQuotaApp;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsProvinceQuotaApp;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/***
 * 区域配额操作
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.resourcesQuota
 * @ClassName: AreaQuotaQueryController
 * @Description: TODO 一级、 二级 、三级,区域配额配置申请、修改、初始化、审批
 * @author: wangjg
 * @date: 2015-11-18 下午3:57:08
 * @version V1.0
 */
@Controller
@RequestMapping("area_quota_operate")
public class AreaQuotaOperateController extends BaseController {

    /**
     * 区域配额数据操作服务类
     */
    @Resource
    private IAreaQuotaOperateService areaQuotaOperateService;

    /**
     * 一级区域配额申请
     * 
     * @param request
     * @param apqa
     * @return
     */
    @ResponseBody
    @RequestMapping(value="apply_plat_quota", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyPlatQuota(HttpServletRequest request, ApsPlatQuotaApp apqa) {
        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            apqa.chekcData();

            // 3、提交申请
            areaQuotaOperateService.applyPlatQuota(apqa);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 二级区域配额申请
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="province_quota_apply", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope provinceQuotaApply(HttpServletRequest request, ApsProvinceQuotaApp apqa) {

        HttpRespEnvelope hre = null;    //返回值

        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            
            // 2、空数据验证
            apqa.checkData();

            // 3、提交申请
            areaQuotaOperateService.provinceQuotaApply(apqa);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 二级区域配额审批
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="province_quota_approve", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope provinceQuotaApprove(HttpServletRequest request, ApsProvinceQuotaApp apqa) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            
            // 2、空数据验证
            apqa.apprCheckData();

            // 3、审批
            areaQuotaOperateService.provinceQuotaApprove(apqa);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 三级区域配额修改
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="city_quota_update", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityQuotaUpdate(HttpServletRequest request, ApsCityQuotaApp acqa, ApsBase apsBase) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            //1、 验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            acqa.chekcUpdateData();

            // 3、配额修改
            areaQuotaOperateService.cityQuotaUpdate(acqa, apsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 三级区域配额初始化
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="city_quota_init", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityQuotaInit(HttpServletRequest request, ApsCityQuotaApp acqa,ApsBase apsBase) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            
            // 2、空数据验证
            acqa.chekcInitData();

            // 3、配额初始化
            areaQuotaOperateService.cityQuotaInit(acqa,apsBase);

            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 三级区域配额审批
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="city_quota_approve", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityQuotaApprove(HttpServletRequest request, ApsCityQuotaApp acqa,ApsBase apsBase) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            acqa.approveCheck();

            // 3、配额审批
            areaQuotaOperateService.cityQuotaApprove(acqa,apsBase);

            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        return null;
    }

}
