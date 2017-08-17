/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.resourcesQuota;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.resourcesQuota.ICommonService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/***
 * 资源配额管公共类
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.resourcesQuota
 * @ClassName: CommonController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-18 上午9:44:05
 * @version V1.0
 */
@Controller
@RequestMapping("res_quota_common")
public class CommonController extends BaseController {

    @Resource
    private ICommonService resQuotaComService;

    /**
     * 获取地区平台下的管理公司
     * 
     * @param request
     * @param response
     * @param custId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_ent_res_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getMEntResList(HttpServletRequest request, ApsBase apsBase) {
        HttpRespEnvelope hre = null;// 返回值

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、获取结果
            List<Map<String, Object>> mapList = resQuotaComService.getMEntResList(apsBase);
            hre = new HttpRespEnvelope(mapList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取管理公司详情
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_ent_res_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getMEntResDetail(HttpServletRequest request, String mEntResNo) {
        HttpRespEnvelope hre = null;// 返回值

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证空数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { mEntResNo, RespCode.AS_PARAM_INVALID });

            // 3、获取结果
            Map<String, Object> map = resQuotaComService.getMEntResDetail(mEntResNo);
            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 城市资源状态详情
     * 
     * @param request
     * @param cityNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "city_res_status_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope cityResStatusDetail(HttpServletRequest request, String cityNo) {
        HttpRespEnvelope hre = null;// 返回值

        try
        {
            // 验证token
            super.verifySecureToken(request);

            // 验证空数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { cityNo, RespCode.AS_PARAM_INVALID.getCode(),
                    RespCode.AS_PARAM_INVALID.getDesc() });
            
            //状态数组字符串
            String[] status = this.cleanArrayEmpty(request.getParameterValues("status[]")); 
            
            // 获取结果
            List<ResInfo> riList = resQuotaComService.cityResStatusDetail(cityNo,status);
            hre = new HttpRespEnvelope(riList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 清除为空的数组集合
     * @param status
     * @return
     */
    String[] cleanArrayEmpty(String[] status) {
        if (status == null || status.length == 0)
        {
            return null;
        }

        // 临时存储满足条件数据
        List<String> temList = new ArrayList<String>();

        // 遍历判断
        for (String s : status)
        {
            if (StringUtils.isNumer(s))
            {
                temList.add(s);
            }
        }

        // 无满足条件 返回null
        if (temList.size() == 0)
        {
            return null;
        }

        return temList.toArray(new String[temList.size()]);
    }
    
    /**
     * 管理公司配额分配详情
     * 
     * @param request
     * @param mEntResNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "manage_allot_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope manageAllotDetail(HttpServletRequest request, String mEntResNo) {
        HttpRespEnvelope hre = null;// 返回值

        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            
            // 2、验证空数据
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { mEntResNo, RespCode.AS_PARAM_INVALID.getCode(), RespCode.AS_PARAM_INVALID.getDesc() }
            );

            // 3、获取结果
            PlatAppManage pam = resQuotaComService.manageAllotDetail(mEntResNo);
            hre = new HttpRespEnvelope(pam);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }
}
