/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.uc.as.bean.common.AsSignInInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action  
 * @ClassName: SigninAction 
 * @Description: 签到业务
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:59:32 
 * @version V1.0
 */
@Service("signinAction")
public class SigninAction implements IBasePos {
	
	public static final String reqType = PosConstant.REQ_SIGNIN_ID;
	
	@Autowired
	@Qualifier("entInfoValid")
	private EntInfoValidation entInfoValid;

	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	@Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;

	@Override
	public Object action(Cmd cmd) throws Exception{
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("SigninAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);
        
        byte[] key = (byte[]) cmd.getTmpMap().get("pikmak");
		if(key == null || key.length == 0){
			throw new PosException(PosRespCode.POS_CENTER_FAIL,"keyserver getKey null");
		}
		SystemLog.debug("SigninAction","action()","key:" + Hex.encodeHexString(key));
		cmd.getIn().add(new BitMap(62, key));
		
		return cmd;
	}


	@Override
	public String getReqType() {
		return reqType;
	}
}
