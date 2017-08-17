/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.controllers.resoucemanage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.resoucemanage.IResGlanceService;
import com.gy.hsxt.bs.bean.quota.result.CompanyRes;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.resoucemanage
 * @className     : ResGlanceController.java
 * @description   : 资源配额一览表
 * @author        : maocy
 * @createDate    : 2016-02-17
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("resGlanceController")
public class ResGlanceController extends BaseController {
	
    /** 资源配额一览表 */
    @Resource
    private IResGlanceService iResGlanceService;
    
    /**
     * 
     * 方法名称：统计管理公司下的资源数据
     * 方法描述：统计管理公司下的资源数据
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statResDetailOfManager")
    public HttpRespEnvelope statResDetailOfManager(HttpServletRequest request) {
        try {
        	String mEntResNo = super.verifyPointNo(request);//管理公司互生号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {mEntResNo, ASRespCode.AS_POINTNO_INVALID}
            );
            QuotaStatOfManager obj = this.iResGlanceService.statResDetailOfManager(mEntResNo);
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：统计二级区域下的资源数据
     * 方法描述：统计二级区域下的资源数据
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statResDetailOfProvince")
    public HttpRespEnvelope statResDetailOfProvince(HttpServletRequest request) {
        try {
        	String sprovinceCode = request.getParameter("sprovinceCode");//省份代码
        	String scityCode = request.getParameter("scityCode");//城市代码
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {sprovinceCode, ASRespCode.MW_BANKINFO_PROVINCENO_INVALID}
            );
            QuotaDetailOfProvince obj = this.iResGlanceService.statResDetailOfProvince(sprovinceCode, scityCode);
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：三级区域(城市)下的资源列表
     * 方法描述：三级区域(城市)下的资源列表
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listResInfoOfCity")
    public HttpRespEnvelope listResInfoOfCity(HttpServletRequest request) {
        try {
        	String scityCode = request.getParameter("scityCode");//城市代码
        	String[] status = this.cleanArrayEmpty(request.getParameterValues("status[]")); //状态数组字符串
        	
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {scityCode, ASRespCode.MW_BANKINFO_CITYNO_INVALID}
            );
            
            List<ResInfo> list = this.iResGlanceService.listResInfoOfCity(scityCode, status);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
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
     * 
     * 方法名称：统计管理公司下的企业资源
     * 方法描述：统计管理公司下的企业资源
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statResCompanyResM")
    public HttpRespEnvelope statResCompanyResM(HttpServletRequest request) {
        try {
        	String mEntResNo = super.verifyPointNo(request);//管理公司互生号
        	CompanyRes obj = this.iResGlanceService.statResCompanyResM(mEntResNo);
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }

    @Override
    protected IBaseService getEntityService() {
        return iResGlanceService;
    }

}
