/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbPayCancelAction 
 * @Description: 互生币支付 退货
 *
 * @author: wucl 
 * @date: 2015-10-16 下午3:49:13
 * @version V1.0
 */
@Service("hsbPayReturnAction")
public class HsbPayReturnAction implements IBasePos {

	public static final String reqType = PosConstant.REQ_HSB_PAY_RETURN_ID;
	
    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;
    
    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
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
        SystemLog.info("HsbPayReturnAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        commonValidation.reqParamValid(cmd);
        
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.LOCAL_CARD_LOCAL_HSB_BACK.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.REMOTE_CARD_LOCAL_HSB_BACK.getCode();

        SystemLog.debug("HsbPayReturnAction", "action()", "custCardNo：" + custCardNo + 
        		"; transType：" + transType);
        
        //4 组装交易参数
        Back back = new Back();
        back.setEntResNo(reqParam.getEntNo());
		back.setEquipmentNo(reqParam.getPosNo());
		back.setOperNo(reqParam.getOperNo());
		back.setSourceBatchNo(((TradeType)reqParam.getTradeTypeInfoBean()).getBatNo());
		back.setSourceTransDate(DateUtil.getCurrentDateTime());
		//本交易流水号
		back.setSourceTransNo(cmd.getPointId());
		//原交易流水号
		back.setOldSourceTransNo(reqParam.getOriginalNo());
		back.setTransType(transType);
		back.setTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));	
		back.setSourceTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));
		back.setTermRunCode(reqParam.getPosRunCode());
		//start--added by liuzh on 2016-04-26
		back.setTermTradeCode(reqParam.getTermTradeCode());
		back.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());
		//end--added by liuzh on 2016-04-26
		//start--added by liuzh on 2016-05-06
		back.setPerResNo(custCardNo);
		//end--added by liuzh on 2016-05-06
		
		BackResult result;
		
		
		//5 先处理本地平台部分 第一步
		try{
            result = psApiService.backPoint(back);
        }catch (Exception e){
        	/*ps可能抛出的互生异常有：
        	 * RespCode.PS_PARAM_ERROR:ps校验请求参数不通过
        	 * RespCode.PS_ORDER_NOT_FOUND:ps找不到原订单
        	 * RespCode.PS_HAS_THE_BACKATION(11025):订单已经是撤单或退货状态，不能再次退货
        	 * 退货金额不能大于原订单金额,无法退货  PS_BACK_AMOUNT_ERROR(11026),
        	 * ps若检索到多于一条的原交易，则抛出非互生异常
        	 */
            if (!e.getClass().equals(HsException.class)){
                cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
            }
            SystemLog.error("HsbPayReturnAction", "action()","互生币支付退货异常。\r\n 请求参数：" + 
            		JSON.toJSONString(back) ,e);
            throw e;
        }
		CommonUtil.checkState(null == result, "消费积分系统异常，退货返回结果为空。", PosRespCode.POS_CENTER_FAIL);
		
		
		//6 若为异地卡交易，则继续转发卡平台处理 第二步
		if(TransType.REMOTE_CARD_LOCAL_HSB_BACK.equals(transType) && "result.getRetCode()".equals("10002:本地平台第一步处理完成")){
			//对发卡平台而言，是本地卡异地交易。发起异地平台处理请求。第二步 异地
        	back.setTransType(TransType.LOCAL_CARD_REMOTE_HSB_BACK.getCode());
        	Set<String> checkItem = new HashSet<String>();
        	checkItem.add("card");checkItem.add("payPwd");
        	JSONObject p = commonValidation.assembleRemoteValidParm(cmd, checkItem);
        	p.put("transType", TransType.LOCAL_CARD_REMOTE_HSB_BACK.getCode());
        	//back.setCardValidParm(p);
        	result = (BackResult) commonService.sendCrossPlatformIndicate(back,custCardNo,"hsbBack");
        }
		
        //异地ps返回交易处理第二步的结果
        //7 本地交易平台继续处理 第三步
        if(TransType.REMOTE_CARD_LOCAL_HSB_BACK.equals(transType) && "result.getRetCode() 异地ps返回结果".equals("10002:发卡平台处理完成") ){
        	back.setTransType(transType);
        	SystemLog.debug(this.getClass().getName(), "action()", "发起第三阶段互生币支付请求。back：" +  JSON.toJSONString(back));
            try{
                result = psApiService.backPoint(back);
            }catch (Exception e){
                if (!e.getClass().equals(HsException.class)){
                    cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
                }
                SystemLog.error("POS HsbPayAction","互生币支付","请求消费积分参数：" + JSON.toJSONString(back),e);
                throw e;
            }
            CommonUtil.checkState(null == result, "积分系统处理异常", PosRespCode.POS_CENTER_FAIL);
        }
		
		
        //8 组装返回报文各域
  		//填充2、4、15、42域 交易额、清算日期、企业标识
        //2域 消费者卡号  手输、刷卡有，扫码无
        boolean exist2area = false;
  		for (int i = 0; i < cmd.getIn().size(); i++) {
  			BitMap BitMap = cmd.getIn().get(i);
  			if(BitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
  			
  			if(BitMap.getBit() == 15){
  				cmd.getIn().set(i, new BitMap(15, "0000"));
  			}	
  			if (BitMap.getBit() == 42) {
  				cmd.getIn().set(i, new BitMap(42, result.getEntNo()));
  			}
  		}
  		
  		if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
  		
  		//填充48域
  		cmd.getIn().add(new BitMap(48, reqId, new Poi48(new BigDecimal(result.getPointRate()), 
  				new BigDecimal(result.getAssureOutValue()), 
  				new BigDecimal(result.getTransAmount())), 
  				cmd.getPartVersion()));
  		
  		//填充63域
  		cmd.getIn().add(new BitMap(63, reqId, new Poi63(PosConstant.ICC_CODE_GYT, 
  					new BigDecimal(result.getPerPoint())), cmd.getPartVersion()));
		
		//kend test 触发冲正
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
  		
		return cmd;
	}

}

	
