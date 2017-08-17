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
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.aps.services.infomanage.RelateCompanyManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLog;

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
@RequestMapping("relateCompanyManage")
public class RelateCompanyManageController extends BaseController {

    @Resource
    private RelateCompanyManageService relateCompanyManageService;
    
    @Resource
    private RedisUtil<String> changeRedisUtil;
    
    /**用户类型*/
    private final static String USER_TYPE = "4";
    
    
    /**
     * 查询企业信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/searchEntExtInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope searchEntExtInfo(HttpServletRequest request,String belongEntCustId) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

        if (httpRespEnvelope == null)
        {
            try
            {
                super.verifySecureToken(request);
                // 校验
                RequestUtil.verifyParamsIsNotEmpty(
                        new Object[] { belongEntCustId,ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getCode()});
                // 修改企业一般信息
                AsEntExtendInfo info = relateCompanyManageService.searchEntExtInfo(belongEntCustId);

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
     * 查看企业的修改记录信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryEntInfoBak" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryEntInfoBakList(HttpServletRequest request,HttpServletResponse response) {
        // Token验证
        HttpRespEnvelope hre = super.checkSecureToken(request);
        if (hre == null)
        {
            try
            {
                // Token验证
                super.verifySecureToken(request);
                // 分页查询
                request.setAttribute("serviceName", relateCompanyManageService);
                request.setAttribute("methodName", "queryEntInfoBakList");
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
     * 查询企业信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findBanksByBelongCustId" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findBanksByBelongCustId(HttpServletRequest request,String belongEntCustId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {belongEntCustId, RespCode.APS_SMRZSP_ENTCUSTID_NOT_NULL}
            );
            List<AsBankAcctInfo> list = this.relateCompanyManageService.findBanksByCustId(belongEntCustId,USER_TYPE);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
        
    }
    
    /**
     * 修改企业一般信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/updateEntBaseInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateEntBaseInfo(HttpServletRequest request,@ModelAttribute AsEntExtendInfo asEntExtendInfo) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

        if (httpRespEnvelope == null)
        {
            try
            {
                super.verifySecureToken(request);
                
                String userName = request.getParameter("dbUserName");//复核员用户名
                String loginPwd = request.getParameter("loginPwd");//复核员密码
                String secretKey = request.getParameter("secretKey");//秘钥
                String operCustId = request.getParameter("custId");//操作员id
                String regionalResNo = request.getParameter("pointNo");//操作员互生号
                String entCustId = request.getParameter("belongEntCustId");//企业id
                String changeContent = request.getParameter("changeContent");//变化的内容
                List<AsEntUpdateLog> list = null;
                if(!StringUtils.isBlank(changeContent)){
                    list = JSON.parseArray(changeContent, AsEntUpdateLog.class);
                }
                String smsCode = request.getParameter("smsCode");//地区平台企业修改上传资料的验证码
                String codeType = request.getParameter("codeType");//地区平台企业修改上传资料的验证码
                if(!StringUtils.isBlank(smsCode)){
                    this.verificationCode(operCustId, smsCode, codeType);//校验验证码
                }
                
                // 校验
                RequestUtil.verifyParamsIsNotEmpty(
                        new Object[] { operCustId,ASRespCode.AS_OPRATOR_OPTCUSTID.getCode(),ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc()},
                        new Object[] { entCustId,ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getCode(),ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getDesc()},
                        new Object[] { userName,ASRespCode.MW_DOULBE_USERNAME_INVALID.getCode(),ASRespCode.MW_DOULBE_USERNAME_INVALID.getDesc()},
                        new Object[] { loginPwd,ASRespCode.MW_DOULBE_PASSWORD_INVALID.getCode(),ASRespCode.MW_DOULBE_PASSWORD_INVALID.getDesc()},
                        new Object[] { secretKey,ASRespCode.MW_DOULBE_PASSWORD_INVALID.getCode(),ASRespCode.MW_DOULBE_PASSWORD_INVALID.getDesc()},
                        new Object[] { regionalResNo,ASRespCode.ASP_DOC_ENTRESNO_INVALID.getCode(),ASRespCode.ASP_DOC_ENTRESNO_INVALID.getDesc()});

                Map<String, Object> conditoanMap = new HashMap<String, Object>();
                asEntExtendInfo.setEntCustId(entCustId);
                conditoanMap.put("logList", list);
                conditoanMap.put("updateLog", asEntExtendInfo);
                conditoanMap.put("operCustId", operCustId);
                conditoanMap.put("userName", userName);
                conditoanMap.put("secretKey", secretKey);
                conditoanMap.put("regionalResNo", regionalResNo);
                conditoanMap.put("loginPwd", loginPwd);
                
                // 修改企业一般信息
                relateCompanyManageService.UpdateEntAndLog(conditoanMap);

                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @ResponseBody
    @RequestMapping(value = { "/validateSmsCode" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope validateSmsCode(HttpServletRequest request){

        String custId = request.getParameter("custId");//客户ID
        String smsCode = request.getParameter("smsCode");//验证码
        String codesType = request.getParameter("codesType");//验证码类别
        String key = this.changeRedisUtil.get(custId+"_"+codesType, String.class);//获取验证码
        
        //判断为空
        if(StringUtils.isEmpty(smsCode)){
            return new HttpRespEnvelope(new HsException(ASRespCode.VERIFICATION_CODE_INVALID));
        }
        //判断过期
        if(StringUtils.isEmpty(key)){
            return new HttpRespEnvelope(new HsException(ASRespCode.AS_VERIFICATION_PASSED_INVALID));
        }
        //判断相等
        if(!smsCode.toUpperCase().equals(key.toUpperCase())){
            return new HttpRespEnvelope(new HsException(ASRespCode.VERIFICATION_CODE_ERROR));
        }
        return new HttpRespEnvelope();
    }
    
    /**
     * 验证码验证
     */
    public void verificationCode(String custId,String smsCode,String codeType) {
        // 获取验证码
        String vCode = changeRedisUtil.get(custId+"_"+codeType, String.class);
        // 判断为空
        if (StringUtils.isEmpty(vCode))
        {
            throw new HsException(RespCode.PW_VERIFICATION_CODE_NULL.getCode(), RespCode.PW_VERIFICATION_CODE_NULL
                    .getDesc());
        }
        // 判断相等
        if (!smsCode.toUpperCase().equals(vCode.toUpperCase()))
        {
            throw new HsException(RespCode.PW_VERIFICATION_CODE_ERROR.getCode(), RespCode.PW_VERIFICATION_CODE_ERROR
                    .getDesc());
        }
    }
    
    /**
     * 企业新增、删除银行信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/UpdateEntBankLog" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope UpdateEntBankLog(HttpServletRequest request,@ModelAttribute AsBankAcctInfo asBankAcctInfo) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

        if (httpRespEnvelope == null)
        {
            try
            {
                super.verifySecureToken(request);
                String userName = request.getParameter("dbUserName");//复核员用户名
                String loginPwd = request.getParameter("loginPwd");//复核员密码
                String secretKey = request.getParameter("secretKey");//秘钥
                String operCustId = request.getParameter("custId");//操作员id
                String regionalResNo = request.getParameter("pointNo");//操作员互生号
                String entCustId = request.getParameter("belongEntCustId");//企业id
                String typeEnum = request.getParameter("typeEum");//类型
                String changeContent = request.getParameter("changeContent");//变化的内容
                List<AsEntUpdateLog> list = null;
                if(!StringUtils.isBlank(changeContent)){
                    list = JSON.parseArray(changeContent, AsEntUpdateLog.class);
                }
                // 校验
                RequestUtil.verifyParamsIsNotEmpty(
                        new Object[] { operCustId,ASRespCode.AS_OPRATOR_OPTCUSTID.getCode(),ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc()},
                        new Object[] { entCustId,ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getCode(),ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getDesc()},
                        new Object[] { userName,ASRespCode.MW_DOULBE_USERNAME_INVALID.getCode(),ASRespCode.MW_DOULBE_USERNAME_INVALID.getDesc()},
                        new Object[] { loginPwd,ASRespCode.MW_DOULBE_PASSWORD_INVALID.getCode(),ASRespCode.MW_DOULBE_PASSWORD_INVALID.getDesc()},
                        new Object[] { secretKey,ASRespCode.MW_DOULBE_PASSWORD_INVALID.getCode(),ASRespCode.MW_DOULBE_PASSWORD_INVALID.getDesc()},
                        new Object[] { regionalResNo,ASRespCode.ASP_DOC_ENTRESNO_INVALID.getCode(),ASRespCode.ASP_DOC_ENTRESNO_INVALID.getDesc()});

                Map<String, Object> conditoanMap = new HashMap<String, Object>();
                
                asBankAcctInfo.setCustId(entCustId);//修改的企业ID
                conditoanMap.put("logList", list);
                conditoanMap.put("updateLog", asBankAcctInfo);
                conditoanMap.put("operCustId", operCustId);
                conditoanMap.put("userName", userName);
                conditoanMap.put("secretKey", secretKey);
                conditoanMap.put("regionalResNo", regionalResNo);
                conditoanMap.put("loginPwd", loginPwd);
                conditoanMap.put("typeEnum", typeEnum);
                
                // 修改企业一般信息
                relateCompanyManageService.UpdateEntBankLog(conditoanMap);

                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @Override
    protected IBaseService getEntityService() {
        return relateCompanyManageService;
    }
    
}
