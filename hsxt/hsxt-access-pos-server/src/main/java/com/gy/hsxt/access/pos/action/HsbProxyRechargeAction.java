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
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.AcApiService;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.enumtype.ProxyTransMode;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbProxyRechargeAction 
 * @Description: 代兑互生币 企业代个人充值
 *
 * @author: wucl 
 * @date: 2015-10-23 上午10:59:28
 * @version V1.0
 * 需先进行第一步 CardInfoSearchAction。
 */
@Service("hsbProxyRechargeAction")
public class HsbProxyRechargeAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_HSB_PROXY_RECHARGE_ID;
    
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
    
    
    @Autowired
    @Qualifier("acApiService")
    private AcApiService acApiService;
    
    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;
    
    @Autowired
    @Qualifier("aoApiService")
    protected AoApiService aoApiService;
    
    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Override
    public String getReqType() {
        
        return null;
    }
    
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("HsbProxyRechargeAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
       
        commonValidation.reqParamValid(cmd);
    	
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.C_HSB_P_HSB_RECHARGE.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.C_HSB_P_ACROSS_RECHARGE_FOR_C.getCode();

        SystemLog.debug("HsbProxyRechargeAction", "action()", "custCardNo：" + custCardNo + 
        		"; transType：" + transType);
    	
        //4 交易前基础参数校验
        // 根据企业类别（是否实名注册）选择不同限额规则并做校验
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo)cmd.getTmpMap().get("entStatusInfo");
        //充值金额 为4域的交易额
      	BigDecimal transAmount = reqParam.getTransAmount();
      	
      	//start--modified by liuzh on 2016-05-11  业务已改动,企业不需要实名认证.
      	/*
      	//经过实名认证的企业和非实名认证企业，代兑限额范围不同
  		if(!entStatusInfo.getIsRealnameAuth().equals(PosConstant.POS_1)){
  		//if(entStatusInfo.getIsRealnameAuth().equals(PosConstant.POS_1)){
  		    BigDecimal reChargeNotAuthSingleMin = platformParamService.getReChargeNotAuthSingleMin();
  		    BigDecimal reChargeNotAuthSingleMax = platformParamService.getReChargeNotAuthSingleMax();
  			CommonUtil.checkState(transAmount.compareTo(reChargeNotAuthSingleMin) < 0 , "非实名充值金额最小限额:"+ 
  					reChargeNotAuthSingleMin, PosRespCode.OUT_NOREG_SINGLE_LIMIT);
  			CommonUtil.checkState(transAmount.compareTo(reChargeNotAuthSingleMax) > 0 , "非实名充值金额最大限额:"+ 
  					reChargeNotAuthSingleMax, PosRespCode.OUT_NOREG_SINGLE_LIMIT);
  		}else{
  			
  		    BigDecimal reChargeAuthSingleMin = platformParamService.getReChargeAuthSingleMin();
  		    BigDecimal reChargeAuthSingleMax = platformParamService.getReChargeAuthSingleMax();
  			CommonUtil.checkState(transAmount.compareTo(reChargeAuthSingleMin) < 0, "实名充值金额最小限额:"+ 
  					reChargeAuthSingleMin, PosRespCode.OUT_NOREG_SINGLE_LIMIT);
  			CommonUtil.checkState(transAmount.compareTo(reChargeAuthSingleMax) > 0, "实名充值金额最大限额:"+ 
  					reChargeAuthSingleMax, PosRespCode.OUT_NOREG_SINGLE_LIMIT);
  		}
  		*/
      	//end--modified by liuzh on 2016-05-11
      	
  		//企业欠年费、重要信息变更期间，不得进行代兑业务
  		//start--modified by liuzh on 2016-04-27
//  		CommonUtil.checkState(1 == entStatusInfo.getIsOweFee() || 1 == entStatusInfo.getIsMaininfoChanged(), 
//  					"企业状态异常，不能进行该业务。", PosRespCode.CHECK_ENT_STATUS_FAIL);
  		CommonUtil.checkState(1 == entStatusInfo.getIsOweFee(), 
					"企业欠年费，不可进行本交易。", PosRespCode.ENT_STATUS_OWE_FEE_INFO);  	
  		CommonUtil.checkState(1 == entStatusInfo.getIsMaininfoChanged(), 
				"企业重要信息变更期，不可进行本交易。", PosRespCode.AO_CHANGING_IMPORTENT_INFO);    		
    	//end--modified by liuzh on 2016-04-27
  		
  		//企业流通币查询 余额不能少于充值金额 （不做此校验：1 pos中心自身无法完成，需调用ac服务；2 后续ps、ac处理时会有此校验。   	
  		//当日充值限额校验 ？	1 企业代兑限额；2 消费者单次兑换限额。  在ao中校验，这里不做处理。
  		//若考虑到要减缓核心业务压力，则需要在这里校验。后续考虑。
    	
    	//5 组装交易参数
  		String cardCustId = (String) cmd.getTmpMap().get("custId");
  		String cardName = ucApiService.searchCardNameByCustId(cardCustId);//要优化
  		ProxyBuyHsb proxyBuyHsb = new ProxyBuyHsb();
		proxyBuyHsb.setEntCustId(entStatusInfo.getEntCustId());
		proxyBuyHsb.setEntResNo(entStatusInfo.getEntResNo());
		proxyBuyHsb.setEntCustName(entStatusInfo.getEntCustName());
		proxyBuyHsb.setEntCustType(entStatusInfo.getCustType());
		proxyBuyHsb.setPerCustId(cardCustId);
		proxyBuyHsb.setPerResNo(reqParam.getCardNo());
		proxyBuyHsb.setPerCustName(cardName);
		proxyBuyHsb.setPerCustType(1);
		proxyBuyHsb.setCashAmt(String.format("%1$.2f", reqParam.getTransAmount()));
		proxyBuyHsb.setOptCustId(reqParam.getOperNo());
		proxyBuyHsb.setTermNo(reqParam.getPosNo());
		proxyBuyHsb.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
		proxyBuyHsb.setOriginNo(cmd.getPointId());
		proxyBuyHsb.setTermRuncode(reqParam.getPosRunCode());
		proxyBuyHsb.setRemark("代兑互生币");
		if(TransType.C_HSB_P_HSB_RECHARGE.getCode().equals(transType))
			proxyBuyHsb.setTransMode(ProxyTransMode.LOCAL_ENT_TO_LOCAL_CUST.getCode());
		else
			proxyBuyHsb.setTransMode(ProxyTransMode.LOCAL_ENT_TO_DIFF_CUST.getCode());
		String result;
  		
		
		//6 发起代兑请求 先执行本地任务 第一步
		try{
			//获得兑换的互生币数量，但对异地而言，因业务未完成，还是需要在所有交易完成后，最后再由OA提供该值
			result= aoApiService.savePosProxyBuyHsb(proxyBuyHsb);
        }
        catch (Exception e){
            if (!e.getClass().equals(HsException.class)){
                cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
            }
            SystemLog.error("HsbProxyRechargeAction", "action()","代兑互生币失败",e);
            throw e;
        }
		
		//7 若为异地卡，还需再发一起次异地交易 第二步
		JSONObject remoteResult = null;
		if(TransType.C_HSB_P_ACROSS_RECHARGE_FOR_C.getCode().equals(transType) ){
			proxyBuyHsb.setTransMode(ProxyTransMode.DIFF_ENT_TO_LOCAL_CUST.getCode());
			proxyBuyHsb.setBuyHsbAmt(result);
//			checkItem.add("card");
//        	JSONObject p = commonValidation.assembleRemoteValidParm(cmd, checkItem);
//        	p.put("transType", ProxyTransMode.DIFF_ENT_TO_LOCAL_CUST.getCode());
//        	//proxyBuyHsb.setCardValidParm(p);
			//代兑不校验卡信息，更无密码。
			remoteResult = (JSONObject) commonService.sendCrossPlatformIndicate(proxyBuyHsb,custCardNo,"proxyRechargeHsb");
	    	CommonUtil.checkState(RespCode.FAIL.getCode() == remoteResult.getIntValue("respCode"), "发卡平台oa执行异常 respDesc：" + 
	    			remoteResult.get("respDesc"), PosRespCode.AGAIN_REVERSE);
		}
		
		
		//8 组装返回报文各域
		//48域 用法十三 代充额、消费者互生流通账户余额
		
		
		BigDecimal hsbRecMoney = TransType.C_HSB_P_ACROSS_RECHARGE_FOR_C.getCode().equals(transType) ? 
				new BigDecimal(remoteResult.getString("rechargeHsbAmt")) : new BigDecimal(result);
		
		AccountBalance accBalance = acApiService.searchAccBalance(cardCustId, AccountType.ACC_TYPE_POINT20110.getCode());
		cmd.getIn().add(new BitMap(48, reqId, new Poi48(null, null, hsbRecMoney, 
					new BigDecimal(accBalance.getAccBalance()), null), cmd.getPartVersion()));
		
		
		//63域 归一标记 国际信用卡公司代码+积分0.12 M/C
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
		
        
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);// kend test 触发冲正
		
        return cmd;
		
	}

    

}

	
