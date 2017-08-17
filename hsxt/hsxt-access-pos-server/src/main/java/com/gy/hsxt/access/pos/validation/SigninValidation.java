/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.validation;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: SigninValidation 
 * @Description: 签到校验
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:45:54 
 * @version V1.0
 */
@Service("signinValid")
public class SigninValidation implements Command {
	
	@Value(value = "${pos.signinTimeOut}")
	public int signinTimeOut;
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	/**
	 * return false 会继续执行chain中后续的command，return true就不会了。
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		
		SystemLog.info("Pos","SigninValidation","validation signin" + signinTimeOut);
		
		if(signinTimeOut != PosConstant.IS_TIMEOUT){
			return false;
		}
		
		Cmd cmd = (Cmd)context.get("cmd");
		
		PosReqParam param = cmd.getPosReqParam();
		
		String entNo = param.getEntNo();
		String posNo = param.getPosNo();
		String operNo = param.getOperNo();
		String reqId = cmd.getReqId();
		
	
		// 所有交易业务 都要判断是否签到， 或者是否超时  
		if (!ReqMsg.SIGNIN.getReqId().equals(reqId) && !ReqMsg.SIGNOFF.getReqId().equals(reqId)) {//签到
		
			// 设备鉴权， 判断是否签到 或 过期
			ucApiService.checkAuth(entNo, posNo, operNo, AsDeviceTypeEumn.POS.getType());
		} 
				
		return false;
	}

}

	