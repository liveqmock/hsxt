package com.gy.hsxt.access.web.person.common;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;

/***
 * 
 * @author lixy
 *
 */
public class MessageController {
	/**
	 * 获得发送到手机的验码
	 * @param custId
	 * @param token
	 * @param mobile
	 * @param request
	 * @return
	 */
	public HttpRespEnvelope getPhoneVerifyCode(String custId , String token,String mobile,HttpServletRequest request){
		//变量声明
				HttpRespEnvelope hre =  null;
				
				// 数据非空验证
		        hre = RequestUtil.checkParamIsNotEmpty(
		                new String[] { custId, "common.tonkNonEmptyPrompt" }, // 用户id非空验证
		                new String[] { token, "common.tonkNonEmptyPrompt" } // 安全令牌非空验证
		                );

				if(hre != null)
				{
					return hre;
				}
				
				try {
					//发送消息，得到验证码
				} catch (HsException e) {
					hre = new HttpRespEnvelope(e);
				}
				
				return hre;
	}
}
