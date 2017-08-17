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
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.ps.bean.QueryDetail;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: PointSearchAction 
 * @Description: 消费积分查询（当日查询 目前没有该功能 不用实现）
 *
 * @author: wucl 
 * @date: 2015-10-16 下午3:39:32
 * @version V1.0
 */
@Service("pointSearchAction")
public class PointSearchAction implements IBasePos {
	
	public static final String reqType = PosConstant.REQ_POINT_DAY_SEARCH_ID;
	
	@Autowired
	@Qualifier("signinValid")
	private SigninValidation signinValid;
	
	@Autowired
	@Qualifier("macValid")
	private MacValidation macValid;
	
	@Autowired
	@Qualifier("cardValid")
	private CardValidation cardValid;
	
	@Autowired
	@Qualifier("entInfoValid")
	private EntInfoValidation entInfoValid;
	
	@Autowired
	@Qualifier("psApiService")
	private PsApiService psApiService;
	
	@Autowired
	private UcApiService ucApiService;
	
	@Override
	public String getReqType() {

		return reqType;
	}

	@SuppressWarnings("unchecked")
    @Override
	public Object action(Cmd cmd) throws Exception {//kend 未用？
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("PointSearchAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
		Context ctx1 = new ContextBase();
		ctx1.put("cmd", cmd);
		Command process = new BaseActionChain(macValid, cardValid, signinValid, entInfoValid);
		process.execute(ctx1);
		
		QueryDetail qd = new QueryDetail();
//		qd.setBatchNo();
		qd.setCount(PosConstant.DAYSEARCH_SIZE);
//		qd.setCustId(custId);
//		qd.setNumber(number);
//		qd.setResNo(resNo);
//		qd.setTransDate(reqParam.getTradeTimestamp());
//		qd.setTransNo(transNo);
//		QueryDetailResult qdr = psApiService.queryPointDetail(qd);
		
		return cmd;
	}

}

	