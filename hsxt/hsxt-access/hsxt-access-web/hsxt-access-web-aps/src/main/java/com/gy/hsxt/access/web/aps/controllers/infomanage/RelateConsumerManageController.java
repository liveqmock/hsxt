/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.infomanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.aps.services.infomanage.RelateConsumerManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLog;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;

/**
 * 关联消费者信息管理
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.infomanage  
 * @ClassName: PerImportantInfoChangeController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-10 下午3:52:53 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("relateConsumerManage")
public class RelateConsumerManageController extends BaseController {

    @Resource
    private RelateConsumerManageService relateConsumerManageService;
    
    /**
     * 查询消费者信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/searchConsumerAllInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope searchAllInfo(HttpServletRequest request,String perCustId) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

        if (httpRespEnvelope == null)
        {
            try
            {
                super.verifySecureToken(request);
                // 校验
                RequestUtil.verifyParamsIsNotEmpty(
                        new Object[] { perCustId,ASRespCode.EW_EXCHANGE_HSB_PERCUSTID.getCode()});
                // 查询企业全部信息
                AsCardHolderAllInfo info = relateConsumerManageService.searchAllInfo(perCustId);

                httpRespEnvelope = new HttpRespEnvelope(info);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 查看消费者的修改记录信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryConsumberInfoBakList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryConsumberInfoBakList(HttpServletRequest request,HttpServletResponse response) {
        // Token验证
        HttpRespEnvelope hre = super.checkSecureToken(request);
        if (hre == null)
        {
            try
            {
                // Token验证
                super.verifySecureToken(request);
                // 分页查询
                request.setAttribute("serviceName", relateConsumerManageService);
                request.setAttribute("methodName", "queryConsumberInfoBakList");
                hre = super.doList(request, response);
            }
            catch (HsException e)
            {
                hre = new HttpRespEnvelope(e);
            }
        }
        return hre;
    }
    
    /**
     * 修改消费者信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/updateConsumerInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateConsumerInfo(HttpServletRequest request,@ModelAttribute AsRealNameAuth asRealNameAuth) {
     // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

        if (httpRespEnvelope == null)
        {
            try
            {
                super.verifySecureToken(request);
                
                String userName = request.getParameter("dbuserName");//复核员用户名
                String loginPwd = request.getParameter("loginPwd");//复核员密码
                String secretKey = request.getParameter("secretKey");//秘钥
                String operCustId = request.getParameter("custId");//操作员id
                String regionalResNo = request.getParameter("pointNo");//操作员互生号
                String perCustId = request.getParameter("belongPerCustId");//消费者id
                String changeContent = request.getParameter("changeContent");//变化的内容
                List<AsCustUpdateLog > list = null;
                if(!StringUtils.isBlank(changeContent)){
                    list = JSON.parseArray(changeContent, AsCustUpdateLog .class);
                }
                Map<String,String> resultMap = getNewMobile(list);
                String status = resultMap.get("status");
                String mobile = resultMap.get("mobile");
                // 校验
                RequestUtil.verifyParamsIsNotEmpty(
                        new Object[] { operCustId,ASRespCode.AS_OPRATOR_OPTCUSTID.getCode()},
                        new Object[] { perCustId,ASRespCode.EW_EXCHANGE_HSB_PERCUSTID.getCode()},
                        new Object[] { userName,ASRespCode.MW_DOULBE_USERNAME_INVALID.getDesc() },
                        new Object[] { loginPwd,ASRespCode.MW_DOULBE_PASSWORD_INVALID.getDesc() },
                        new Object[] { secretKey,ASRespCode.MW_DOULBE_PASSWORD_INVALID.getCode()},
                        new Object[] { regionalResNo ,ASRespCode.ASP_DOC_ENTRESNO_INVALID.getDesc()});

                Map<String, Object> conditoanMap = new HashMap<String, Object>();
       //       asRealNameAuth.setCustId(perCustId);
                
                conditoanMap.put("logList", list);
                conditoanMap.put("updateLog", "0".equals(status) ? null : asRealNameAuth);
                conditoanMap.put("operCustId", operCustId);
                conditoanMap.put("userName", userName);
                conditoanMap.put("secretKey", secretKey);
                conditoanMap.put("regionalResNo", regionalResNo);
                conditoanMap.put("loginPwd", loginPwd);
                conditoanMap.put("perCustId", perCustId);
                if(StringUtils.isNotBlank(mobile)){
                	conditoanMap.put("mobile", mobile);
                }
                
                // 修改消费者一般信息
                relateConsumerManageService.UpdateConsumerAndLog(conditoanMap);

                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    private Map<String,String> getNewMobile(List<AsCustUpdateLog > list){
         String field = null;
         String fieldValue = null;
         Map<String,String> resultMap = new HashMap<>();
         String status = "0";
         resultMap.put("status", status);
         for (AsCustUpdateLog custInfo : list) {
         	field = custInfo.getUpdateField();
         	fieldValue = custInfo.getUpdateValueNew();
         	if("mobile".equals(field)){
         		resultMap.put("mobile", fieldValue);
         		continue;
         	}
         	if(resultMap.containsKey("mobile") && "1".equals(status)){
     			break;
     		}
         	if(StringUtils.isNotBlank(fieldValue)){
         		status = "1";
         		resultMap.put("status", status);
         	}
		}
         return resultMap;
    }
    
    @Override
    protected IBaseService getEntityService() {
        return relateConsumerManageService;
    }
    

}
