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
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: PayPwdValidation 
 * @Description: 交易密码校验
 *
 * @author: wucl 
 * @date: 2015-11-10 下午6:26:36 
 * @version V1.0
 */
@Service("payPwdValidation")
public class PayPwdValidation implements Command {
	
	@Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

	/**
	 * return false 会继续执行chain中后续的command，return true就不会了。
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		SystemLog.info("Pos","PayPwdValidation","validation payPwd");
		
		Cmd cmd = (Cmd)context.get("cmd");
		
		PosReqParam param = cmd.getPosReqParam();
		
		String cardNo = param.getCardNo();
		String cardPwd = param.getCardPwd();
		
		// 调用用户中心 校验 支付密码
		ucApiService.checkResNoAndTradePwd(cardNo, CommonUtil.string2MD5(cardPwd));
		
		return false;
	}

}

	