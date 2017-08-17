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

import com.gy.hsxt.access.web.aps.services.resourcesQuota.IResDataQueryService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * 资源数据查询类
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.resourcesQuota
 * @ClassName: ResDataQueryController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-16 下午2:18:20
 * @version V1.0
 */
@Controller
@RequestMapping("res_data_query")
public class ResDataQueryController extends BaseController {

    @Resource
    private IResDataQueryService resDataQueryService;

    /**
     * 数据资源一览表
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="list_table", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getListTable(HttpServletRequest request,String mEntResNo) {
        HttpRespEnvelope hre = null;// 返回值

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证空数据
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { mEntResNo, RespCode.AS_PARAM_INVALID.getCode(),RespCode.AS_PARAM_INVALID.getDesc() }
                    );

            // 3、获取结果
            QuotaStatOfManager qsom = resDataQueryService.getListTable(mEntResNo);
            hre= new HttpRespEnvelope(qsom);
        }
        catch (HsException e)
        {
            hre= new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 资源配额查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="res_quota_query", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope resQuotaQuery(HttpServletRequest request, String provinceNo, String cityNo) {
        HttpRespEnvelope hre = null;// 返回值

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证空数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { provinceNo, RespCode.AS_PARAM_INVALID.getCode(),
                    RespCode.AS_PARAM_INVALID.getDesc() });

            // 3、获取结果
            QuotaDetailOfProvince qdop = resDataQueryService.resQuotaQuery(provinceNo, cityNo);
            return new HttpRespEnvelope(qdop);
        }
        catch (HsException e)
        {
            return new HttpRespEnvelope(e);
        }
    }

    @Override
    protected IBaseService getEntityService() {
        return resDataQueryService;
    }
}
