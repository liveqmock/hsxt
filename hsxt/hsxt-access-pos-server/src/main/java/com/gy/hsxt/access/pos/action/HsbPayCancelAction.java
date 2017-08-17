/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
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
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.Poi63;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.TradeType;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.LcsClientService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UFApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbPayCancelAction 
 * @Description: 互生币支付 返回积分  撤单业务
 *
 * @author: wucl 
 * @date: 2015-10-16 下午3:49:13
 * @version V1.0
 */
@Service("hsbPayCancelAction")
public class HsbPayCancelAction implements IBasePos {
	public static final String reqType = PosConstant.REQ_HSB_PAY_CANCEL_ID;
	
    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;
    
    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
    @Autowired
    @Qualifier("lcsClientService")
    private LcsClientService lcsClientService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("uFApiService")
    private UFApiService uFApiService;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;
	   
	@Override
	public String getReqType() {

		return reqType;
	}

	@Override
	public Object action(Cmd cmd) throws Exception {
		PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("HsbPayCancelAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

		
//        Context ctx1 = new ContextBase();
//		ctx1.put("cmd", cmd);
//		baseCheck.execute(ctx1);
		
        
        commonValidation.reqParamValid(cmd);
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.LOCAL_CARD_LOCAL_HSB_CANCEL.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.REMOTE_CARD_LOCAL_HSB_CANCEL.getCode();

        SystemLog.debug("HsbPayCancelAction", "action()", "custCardNo：" + custCardNo + 
        		"; transType：" + transType);
        
        
        
        //4 组装交易参数
		Cancel cancel = new Cancel();
		cancel.setEquipmentNo(reqParam.getPosNo());
		cancel.setOperNo(reqParam.getOperNo());
		cancel.setSourceBatchNo(((TradeType)reqParam.getTradeTypeInfoBean()).getBatNo());
		cancel.setSourceTransDate(DateUtil.getCurrentDateTime());
		//本交易流水号
		cancel.setSourceTransNo(cmd.getPointId());
		//原交易流水号
		cancel.setOldSourceTransNo(reqParam.getOriginalNo());
		cancel.setTransType(transType);
		cancel.setEntResNo(reqParam.getEntNo());
		cancel.setTermRunCode(reqParam.getPosRunCode());
		//start--added by liuzh on 2016-04-26
		cancel.setTermTradeCode(reqParam.getTermTradeCode());
		cancel.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());
		//end--added by liuzh on 2016-04-26
		//start--added by liuzh on 2016-05-06
		cancel.setPerResNo(custCardNo);
		//end--added by liuzh on 2016-05-06
		CancelResult result = null;
		
		//5 若为异地卡交易，则 发起发卡平台处理 第一步
		if(TransType.REMOTE_CARD_LOCAL_HSB_CANCEL.equals(transType)){
			cancel.setTransType(TransType.LOCAL_CARD_REMOTE_HSB_CANCEL.getCode());
			Set<String> checkItem = new HashSet<String>();
			checkItem.add("card");checkItem.add("payPwd");
        	JSONObject p = commonValidation.assembleRemoteValidParm(cmd, checkItem);
        	p.put("transType", TransType.LOCAL_CARD_REMOTE_HSB_CANCEL.getCode());
        	//cancel.setCardValidParm(p);
			result = (CancelResult) commonService.sendCrossPlatformIndicate(cancel,custCardNo,"hsbCancel");
        	CommonUtil.checkState(null == result || !"result.getRetCode()".equals("10001: 发卡平台ps处理完成"), 
        			"消费积分系统异常,发卡平台处理不成功", PosRespCode.POS_CENTER_FAIL);
		}
		
		
		
		//6 发起本地交易 第二步
		
		//交易平台继续处理 不管是本地卡还是异地卡，ps都可根据transType识别请求所属的步骤
		cancel.setTransType(transType);
		try{
			result = psApiService.cancelpoint(cancel);
		}catch(Exception e){
			/*ps可能抛出的互生异常有：
        	 * RespCode.PS_PARAM_ERROR:ps校验请求参数不通过
        	 * RespCode.PS_ORDER_NOT_FOUND:ps找不到原订单
        	 * RespCode.PS_HAS_THE_CANCELLATION(11013):订单已经是撤单或退货状态，不能再次撤单
        	 * ps若检索到多于一条的原交易，则抛出非互生异常
        	 */
			if (!e.getClass().equals(HsException.class)){
                cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
            }
            SystemLog.error("HsbPayCancelAction", "action()","互生币支付撤单异常。\r\n 请求参数：" + 
            		JSON.toJSONString(cancel) ,e);
            throw e;
		}
		CommonUtil.checkState(null == result, "消费积分系统异常,本平台ps返回null", PosRespCode.POS_CENTER_FAIL);
		
//		if(!"result.getRetCode()".equals("10002: 本平台ps处理完成")){
//			CommonUtil.checkState(true, "本地ps处理失败。返回提示：" + "result.getRetMsg()", PosRespCode.POS_CENTER_FAIL);//待ps补充
//			cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
//		}
    	
		
		
		//7 组装返回报文各域
		//4域 交易金额
		String soureceTAStr = result.getSourceTransAmount();
		BigDecimal sourceTransAmout = null == soureceTAStr ? BigDecimal.ZERO : new BigDecimal(soureceTAStr);
		cmd.getIn().add(new BitMap(4, reqId, sourceTransAmout, 
						platformParamService.getLocalCurrencyCode().equals(result.getSourceCurrencyCode())?
								PosConstant.ISLOCAL_CURRENCY:0, cmd.getPartVersion()));

		//增加15域 原cmd.getIn()中没有15域，这里添加
        cmd.getIn().add(new BitMap(15, "0000"));
		
        //2域 消费者卡号  手输、刷卡有，扫码无
        boolean exist2area = false;
        
		//2、15、42域处理 
		for (int i = 0; i < cmd.getIn().size(); i++) {
			BitMap BitMap = cmd.getIn().get(i);
			
			if(BitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
			
			//售卡方标识码
			if (BitMap.getBit() == 42) {
				cmd.getIn().set(i, new BitMap(42, result.getEntResNo()));
			}
		}
		
		if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
		
		//48域 用法六 携带 积分比例、积分金额、原币种消费金额三项。
		cmd.getIn().add(new BitMap(48, reqId, new Poi48(new BigDecimal(result.getPointRate()), 
				new BigDecimal(result.getAssureOutValue()), new BigDecimal(result.getTransAmount())), 
				cmd.getPartVersion()));
		
		
		//63域 自定义域 携带 卡标识GYT、消费者获得积分、副版本号三项。
		cmd.getIn().add(new BitMap(63, reqId, new Poi63(PosConstant.ICC_CODE_GYT, 
					new BigDecimal(result.getPerPoint())), cmd.getPartVersion()));
        
		
		//设置格式异常  让终端触发冲正 调试程序用  kend test
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
        
		return cmd;
	}

}

	
