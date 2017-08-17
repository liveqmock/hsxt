package com.gy.hsxt.access.web.person.controllers.hsc;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.services.consumer.ICardholderService;
import com.gy.hsxt.access.web.person.services.hsc.ICardHolderAuthInfoService;
import com.gy.hsxt.access.web.person.services.hsc.ICardLossRegService;
import com.gy.hsxt.common.constant.CardLossType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-access-web-person
 * 
 *  Package Name    : com.gy.hsxt.access.web.person.hsc.controller
 * 
 *  File Name       : HsCardLossRegController.java
 * 
 *  Creation Date   : 2015-9-17 上午09:07:45  
 *  
 *  updateUse
 * 
 *  Author          : zhangcy
 * 
 *  Purpose         : 互生卡挂失、挂失查询等操作 <br/>
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("cardLossReg")
public class CardLossRegController extends BaseController {
    
	@Resource private ICardLossRegService cardLossRegService;      //互生卡挂失操作服务类
	
	@Resource private ICardholderService cardholderService;        //持卡人操作服务类
	
	/**
     * 获取互生卡挂失状态
     * 
     * @param custId
     *            客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findHsCardStatusInfoBycustId" }, method = {RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findHsCardStatusInfoBycustId(String custId, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 获取互生卡挂失状态
            Map<String,String> map = this.cardLossRegService.findHsCardStatusInfoBycustId(custId);

            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }catch (Exception e)
        {
            hre = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
        }
        return hre;
    }
    
	
	
	
	
	/**
	 * 互生卡挂失
	 * @param custId       客户编号
	 * @param loginPwd     密码
	 * @param randomToken  随机登录token
	 * @param lossReason   挂失原因
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"hscReportLoss"},method = { RequestMethod.GET,RequestMethod.POST },produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope hscReportLoss(String custId, String loginPwd, String randomToken, String lossReason,HttpServletRequest request){
		//验证Token
		HttpRespEnvelope httpRespEnvelope = null;
		try
        {
		    //token 数据验证
		    super.checkSecureToken(request);
		    
		    // 数据非空验证
	         RequestUtil.verifyParamsIsNotEmpty(
	                new Object[] { loginPwd,RespCode.PW_LOGINPWD_INVALID.getCode(),RespCode.PW_LOGINPWD_INVALID.getDesc()},        // 登录密码
	                new Object[] { randomToken,RespCode.AS_SECURE_TOKEN_NULL.getCode(),RespCode.AS_SECURE_TOKEN_NULL.getDesc()},   // 随机token
	                new Object[] { lossReason,RespCode.PW_LOSS_REASON_NULL.getCode(),RespCode.PW_LOSS_REASON_NULL.getDesc()}       // 挂失原因
	                );
	         
	         //执行互生卡挂失
	         this.cardLossRegService.modifyCardLossStatus(custId, loginPwd, randomToken, CardLossType.LOSS.getCode(), lossReason);
	         
	         httpRespEnvelope = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            httpRespEnvelope = handleModifyPasswordException(e);
        }catch (Exception e)
        {
            httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
        }
		return httpRespEnvelope;
	}
	
	 private HttpRespEnvelope handleModifyPasswordException(HsException e){
	    	HttpRespEnvelope hre = new HttpRespEnvelope();
	    	int errorCode = e.getErrorCode();
//	    	String errorMsg = e.getMessage();
//	    	String funName = "handleModifyPasswordException";
//	    	StringBuffer msg = new StringBuffer();
//	    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] "+NEWLINE);
	    	if(160359 == errorCode || 160108 == errorCode){
	    		/**保留
	     		try {
	        			if(!StringUtils.isEmptyTrim(errorMsg) && errorMsg.contains(",")){
	            			String[] msgInfo = errorMsg.split(",");
	            			if(msgInfo.length > 1){
	            				int loginFailTimes = Integer.parseInt(msgInfo[0]);
	            				int maxFailTimes = Integer.parseInt(msgInfo[1]);
	            			}
	            		}
	            		
	    			} catch (Exception e2) {
	    				SystemLog.error(MOUDLENAME, funName, msg.toString(), e2);
	    			}
	        		*/
	    		hre = new HttpRespEnvelope(e.getErrorCode(),"登录密码不正确");
	    	}else if(160467 == errorCode){
	    		hre = new HttpRespEnvelope(e.getErrorCode(),"账户被锁定");
	    	}else{
	    		hre = new HttpRespEnvelope(e);
	    	}
	    	return hre;
	 }
	/**
     * 互生卡解挂
     * @param custId       客户编号
     * @param loginPwd     密码
     * @param randomToken  随机登录token
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"hscSolutionLinked"},method = { RequestMethod.POST },produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope hscSolutionLinked(String custId, String loginPwd, String randomToken, HttpServletRequest request){
        //验证Token
        HttpRespEnvelope httpRespEnvelope = null;
        try
        {
            //token 数据验证
            super.checkSecureToken(request);
            
            // 数据非空验证
             RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { loginPwd,RespCode.PW_LOGINPWD_INVALID.getCode(),RespCode.PW_LOGINPWD_INVALID.getDesc()},        // 登录密码
                    new Object[] { randomToken,RespCode.AS_SECURE_TOKEN_NULL.getCode(),RespCode.AS_SECURE_TOKEN_NULL.getDesc()}   // 随机token
                    );
             
             //执行互生卡解挂
             this.cardLossRegService.modifyCardLossStatus(custId, loginPwd, randomToken, CardLossType.START_UP.getCode(), null);
             
             httpRespEnvelope = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            httpRespEnvelope = handleModifyPasswordException(e);
        }catch (Exception e)
        {
            httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
        }
        return httpRespEnvelope;
    }
	
	@Override
	protected IBaseService getEntityService() {
		
		return cardLossRegService;
	}

}
