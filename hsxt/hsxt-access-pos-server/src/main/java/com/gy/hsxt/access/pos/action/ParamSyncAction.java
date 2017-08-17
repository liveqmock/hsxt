/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

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
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.SyncParamPosIn;
import com.gy.hsxt.access.pos.model.SyncParamPosOut;
import com.gy.hsxt.access.pos.service.impl.PosServiceImpl;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: ParamSyncAction 
 * @Description: 参数同步
 *
 * @author: wucl 
 * @date: 2015-10-16 下午3:48:19
 * @version V1.0
 */
@Service("paramSyncAction")
public class ParamSyncAction implements IBasePos {

	public static final String reqType = PosConstant.REQ_PARAM_SYNC_ID;
	
	
	@Autowired
	@Qualifier("entInfoValid")
	private EntInfoValidation entInfoValid;
	
	@Autowired
	@Qualifier("posService")
	private PosServiceImpl posService;
	

	@Override
	public String getReqType() {

		return reqType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object action(Cmd cmd) throws Exception {
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("ParamSyncAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
		
		Context ctx1 = new ContextBase();
		ctx1.put("cmd", cmd);
		Command process = new BaseActionChain(entInfoValid);
		process.execute(ctx1);
		
		
		byte[] partVer = cmd.getPartVersion();
		SystemLog.debug("ParamSyncAction", "action()", "partVer = " + Hex.encodeHexString(partVer));//kend test
		
		SyncParamPosIn serviceIn = (SyncParamPosIn) reqParam.getCustom2();//基础信息版本号+货币信息版本号+国家代码版本号
		
		SyncParamPosOut serviceOut = posService.doPosSyncParamWork(reqParam, cmd.getPartVersion());
		
		cmd.getIn().add(new BitMap(62, reqId, serviceOut, serviceIn,cmd.getPartVersion()));
		//国际信用卡公司代码 M/C
		cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
		
		return cmd;
	}

}

	