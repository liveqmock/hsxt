/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action  
 * @ClassName: SignOffAction 
 * @Description: 签退业务
 *
 * @author: wucl 
 * @date: 2015-11-10 下午4:41:06 
 * @version V1.0
 */
@Service("signOffAction")
public class SignOffAction implements IBasePos {


	public static final String reqType = PosConstant.REQ_SIGNOFF_ID;
	
	@Autowired
	@Qualifier("signinValid")
	private SigninValidation signinValid;
	
	@Autowired
	@Qualifier("macValid")
	private MacValidation macValid;
	
	@Autowired
	@Qualifier("entInfoValid")
	private EntInfoValidation entInfoValid;
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	@Override
	public String getReqType() {

		return reqType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object action(Cmd cmd) throws Exception {
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("SignOffAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
		
		Context ctx1 = new ContextBase();
		ctx1.put("cmd", cmd);
		Command process = new BaseActionChain(entInfoValid);
		
		process.execute(ctx1);
		
		String entNo = reqParam.getEntNo();
		String posNo = reqParam.getPosNo();
		String operNo = reqParam.getOperNo();
		
		
		// 可能调用用户中心修改签到标识为签退标识
		ucApiService.deviceLogout(entNo, posNo, operNo, AsDeviceTypeEumn.POS.getType());

		return cmd;
	}

}
