/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.ao.bean.PosBuyHsbCancel;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbReChargeReverseAction 
 * @Description: 兑换互生币，代兑互生币冲正业务
 *
 * @author: wucl 
 * @date: 2015-10-16 下午5:09:01
 * @version V1.0
 */
@Service("hsbRechargeReverseAction")
public class HsbRechargeReverseAction implements IBasePos {

	public static final String reqType = PosConstant.REQ_HSB_PROXY_RECHARGE_REVERSE_ID;
	
	@Override
	public String getReqType() {

		return reqType;
	}
	
	@Autowired
    @Qualifier("aoApiService")
    private AoApiService aoApiService;
	
	@Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;
	
	@Override
	public Object action(Cmd cmd) throws Exception {
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("HsbReChargeReverseAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        //1 发起冲正业务参数校验(冲正交易只有mac、签到、企业信息的校验)
        commonValidation.reqParamValid(cmd);
		
		//2  组装冲正请求报文
        PosBuyHsbCancel buyHsbCancel  = new PosBuyHsbCancel();
        buyHsbCancel.setHsResNo(reqParam.getEntNo());
        buyHsbCancel.setTermNo(reqParam.getPosNo());
        buyHsbCancel.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        buyHsbCancel.setTermRuncode(reqParam.getPosRunCode());
        buyHsbCancel.setOptCustId(reqParam.getOperNo());
        buyHsbCancel.setRemark("代兑冲正");
        //3 发起本地冲正请求 第一步
        JSONObject result = null;
        if(reqId.equalsIgnoreCase(PosConstant.REQ_HSB_PROXY_RECHARGE_REVERSE_ID)){
            buyHsbCancel.setRemark("代兑互生币冲正");
            aoApiService.reversePosProxyBuyHsb(buyHsbCancel); 
            
            //ao自身完成了异地卡交易的跨平台访问，这里就不用考虑，只需在本平台发起即可。

//            result = aoApiService.reversePosProxyBuyHsb(buyHsbCancel);            
//            //4 代兑业务可能有异地，根据返回值识别并作后续发卡平台处理  第二步
//            if("异地".equals(result.getInt("retCode"))){
//            	buyHsbCancel.setRemark("异地交易代兑冲正");
//            	result = (JSONObject) commonService.dealCrossPlatformBiz(buyHsbCancel,"result.get消费者互生号","hsbProxyRechargeCorrect");
//            	CommonUtil.checkState("冲正失败".equals("result.getRetCode"), "异地交易，发卡平台执行冲正失败 retCode：" + 
//        				"result.getRetCode()", PosRespCode.AGAIN_REVERSE);
//            	
//            	//5 继续执行本地交易 第三步
//            	buyHsbCancel.setRemark("异地卡代兑冲正");
//            	result = aoApiService.reversePosProxyBuyHsb(buyHsbCancel);
//            	CommonUtil.checkState("冲正失败".equals("result.getRetCode"), "异地卡交易，交易平台执行冲正失败 retCode：" + 
//                        "result.getRetCode()", PosRespCode.AGAIN_REVERSE);
//            }
            
            
 
        }else if(reqId.equals(PosConstant.REQ_HSB_ENT_RECHARGE_REVERSE_ID)){
            buyHsbCancel.setRemark("兑换互生币冲正");
            aoApiService.reversePosBuyHsb(buyHsbCancel);
        }else
        	CommonUtil.checkState(true, "交易类型异常 reqId：" + reqId, PosRespCode.REQUEST_PACK_FORMAT);
        
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
        
        return cmd;
	}

}

	
