/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.resourcesQuota;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.quota.CityQuotaApp;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.quota.ProvinceQuotaApp;
import com.gy.hsxt.bs.bean.quota.result.AllotProvince;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfCity;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.client.LcsClient;

/***
 * 区域配额查询
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.resourcesQuota
 * @ClassName: AreaQuotaQueryController
 * @Description: TODO 一级、 二级 、三级，区域配额配置查询接口
 * @author: wangjg
 * @date: 2015-11-18 下午3:57:08
 * @version V1.0
 */
@Controller
@RequestMapping("area_quota_query")
public class AreaQuotaQueryController extends BaseController {

    /**
     * 区域配置查询接口
     */
    @Resource
    private IAreaQuotaQueryService areaQuotaQueryService;
    
    @Autowired
    private LcsClient ilcsBaseDataService;

    /**
     * 一级区域配额申请分页查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="apply_plat_quota_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyPlatQuotaPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            
            // 2、查询结果
            List<PlatAppManage> oList = areaQuotaQueryService.applyPlatQuotaPage();
            hre = new HttpRespEnvelope(oList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 一级区域配额申请查询分页查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="apply_plat_quota_query_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyPlatQuotaQueryPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、动态调用分页方法
            request.setAttribute("serviceName", areaQuotaQueryService);
            request.setAttribute("methodName", "applyPlatQuotaQueryPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 查询一级区域(地区平台)配额分配详情
     * 
     * @param request
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="apply_plat_quota_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyPlatQuotaDetail(HttpServletRequest request, String applyId) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.AS_PARAM_INVALID});

            // 3、获取结果
            PlatQuotaApp pqa = areaQuotaQueryService.applyPlatQuotaDetail(applyId);
            hre = new HttpRespEnvelope(pqa);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;

    }

    /**
     * 查询管理公司可以进行分配或调整配额的二级区域
     * 
     * @param request
     * @param mEntResNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="get_province_no_allot", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getProvinceNoAllot(HttpServletRequest request, String mEntResNo) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { mEntResNo, RespCode.AS_PARAM_INVALID});

            // 3、获取结果
            List<String> retList = areaQuotaQueryService.getProvinceNoAllot(mEntResNo);
            hre = new HttpRespEnvelope(retList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 二级区域配额配置查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="province_quota_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope provinceQuotaPage(HttpServletRequest request, HttpServletResponse response) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、动态调用分页方法
            request.setAttribute("serviceName", areaQuotaQueryService);
            request.setAttribute("methodName", "provinceQuotaPage");
            hre = super.doList(request, response);
            
            //3、分页数据不能有空，否则分页js一直显示加载
            if (hre.getData() == null)
            {
                hre.setData(new ArrayList<>());
            }
            
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 二级区域配额配置详情
     * 
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="province_quota_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope provinceQuotaDetail(HttpServletRequest request, String applyId) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.AS_PARAM_INVALID});

            // 3、获取结果
            ProvinceQuotaApp pqa = areaQuotaQueryService.provinceQuotaDetail(applyId);
            hre = new HttpRespEnvelope(pqa);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;

    }

    /**
     * 查询地区平台分配了配额的省
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="allocated_quota_province_query", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope allocatedQuotaProvinceQuery(HttpServletRequest request) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、获取结果
            List<AllotProvince> apList = areaQuotaQueryService.allocatedQuotaProvinceQuery();
            hre = new HttpRespEnvelope(apList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 三级区域配额配置申请分页查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="city_quota_apply_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityQuotaApplyPage(HttpServletRequest request, String provinceNo, String cityNo) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { provinceNo, RespCode.AS_PARAM_INVALID});

            // 3、获取结果
            List<QuotaStatOfCity> qsocList = areaQuotaQueryService.cityQuotaApplyPage(provinceNo);
            hre = new HttpRespEnvelope(qsocList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 三级区域配额配置查询分页查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="city_quota_apply_qery_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityQuotaApplyQueryPage(HttpServletRequest request, HttpServletResponse response) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、动态调用分页方法
            request.setAttribute("serviceName", areaQuotaQueryService);
            request.setAttribute("methodName", "cityQuotaApplyQueryPage");
            hre = super.doList(request, response);
            
            //3、分页数据不能有空，否则分页js一直显示加载
            if (hre.getData() == null)
            {
                hre.setData(new ArrayList<>());
            }
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 三级区域配额配置详情
     * 
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="city_quota_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityQuotaDetail(HttpServletRequest request, String applyId) {

        HttpRespEnvelope hre = null;// 返回值
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.AS_PARAM_INVALID });

            // 3、获取详情
            CityQuotaApp cop = areaQuotaQueryService.cityQuotaDetail(applyId);
            hre = new HttpRespEnvelope(cop);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    /**
     * 统计城市配额分配使用情况
     * @param request
     * @param provinceNo
     * @param cityNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="stat_quota_by_city", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope statQuotaByCity(HttpServletRequest request, String provinceNo, String cityNo) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { provinceNo, RespCode.AS_PARAM_INVALID }, 
                    new Object[] { cityNo, RespCode.AS_PARAM_INVALID });

            // 3、获取详情
            QuotaStatOfCity qsoc = areaQuotaQueryService.statQuotaByCity(provinceNo, cityNo);
            hre = new HttpRespEnvelope(qsoc);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    

    @Override
    protected IBaseService getEntityService() {
        return areaQuotaQueryService;
    }

}
