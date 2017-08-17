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
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: CardCheckAction 
 * @Description: 卡校验
 *
 * @author: wucl 
 * @date: 2015-10-16 下午6:30:10
 * @version V1.0
 */
@Service("cardCheckAction")
public class CardCheckAction implements IBasePos {

	public static final String reqType = PosConstant.REQ_CARD_CHREQ_ID;
	
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
	@Qualifier("cardValid")
	private CardValidation cardValid;

	@Override
	public String getReqType() {

		return reqType;
	}

	/**
	 * 此业务，是卡密码及暗码信息校验 同CardValidation 功能一样。
	 * @param cmd
	 * @return
	 * @throws Exception 
	 * @see com.gy.hsxt.access.pos.action.IBasePos#action(com.gy.hsxt.access.pos.model.Cmd)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object action(Cmd cmd) throws Exception {
	    
		PosReqParam reqParam = cmd.getPosReqParam();
		
		SystemLog.debug("POS CardCheckAction", "POS 请求参数", JSON.toJSONString(reqParam));
		
		Context ctx1 = new ContextBase();
		ctx1.put("cmd", cmd);
		Command process = new BaseActionChain(signinValid, macValid, entInfoValid, cardValid);
		
		process.execute(ctx1);
		return cmd;
		
	}

}

	