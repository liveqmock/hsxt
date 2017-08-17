/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.pos.constant.EntTypeEnum;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.AcApiService;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbEntRechargeAction 
 * @Description: 企业兑换互生币
 *
 * @author: wucl 
 * @date: 2015-10-23 上午10:59:13
 * @version V1.0
 */
@Service("hsbEntRechargeAction")
public class HsbEntRechargeAction implements IBasePos {
    public static final String reqType = PosConstant.REQ_HSB_ENT_RECHARGE_ID;
    
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
    @Qualifier("aoApiService")
    protected AoApiService aoApiService;
	
    @Autowired
    @Qualifier("acApiService")
    private AcApiService acApiService;
    
    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;
    
    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Override
	public String getReqType() {

		return reqType;
	}
	
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("HsbEntRechargeAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        commonValidation.reqParamValid(cmd);
        
		
		//充值金额 为4域的交易额 折合本地币种
		BigDecimal transAmount = reqParam.getTransAmount();

		String entNo = reqParam.getEntNo();
		AsEntStatusInfo entInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
		String entCustId = entInfo.getEntCustId();
		AsEntBaseInfo baseInfo = ucApiService.searchEntBaseInfo(entCustId);
		
		BigDecimal reChargeSingleMin ; 
        BigDecimal reChargeSingleMax ;
        if(entInfo.getCustType().equals(EntTypeEnum.CYQY.getCustTypeId())){
            reChargeSingleMin = platformParamService.getReChargeBSingleMin();
            reChargeSingleMax = platformParamService.getReChargeBSingleMax();
        }else if(entInfo.getCustType().equals(EntTypeEnum.TGQY.getCustTypeId())){
            reChargeSingleMin = platformParamService.getReChargeTSingleMin();
            reChargeSingleMax = platformParamService.getReChargeTSingleMax();
        }else{
            HsException e = new HsException();
            SystemLog.error("HsbReChargeBAction", "action()", "Pos 企业兑换互生币出错 企业类型是"+entInfo.getCustType(), e);
            throw e;
        }
        CommonUtil.checkState(transAmount.compareTo(reChargeSingleMin)<0, "企业充值金额最大限额:"+ 
        		reChargeSingleMax, PosRespCode.OUT_SINGLE_LIMIT);
        CommonUtil.checkState(transAmount.compareTo(reChargeSingleMax)>0, "企业充值金额最小限额:"+ 
        		reChargeSingleMin, PosRespCode.OUT_SINGLE_LIMIT);
		
		//企业现金查询
		AccountBalance balance = acApiService.searchAccBalance(entCustId, 
										AccountType.ACC_TYPE_POINT30110.getCode());
		CommonUtil.checkState(balance==null||balance.getAccBalance()==null, "企业货币账户余额信息为空" + 
				entNo, PosRespCode.INSUF_BALANCE_ENT_CASH);
        
		// 查询账务现金账户 余额是否支持本次消费
		BigDecimal cashBalance = new BigDecimal(balance.getAccBalance());
			
	    CommonUtil.checkState(transAmount.compareTo(cashBalance)>0, "企业货币账户余额不足" + 
	    											entNo, PosRespCode.INSUF_BALANCE_ENT_CASH);
	    String entResNo = reqParam.getEntNo();
	    BuyHsb buyHsb = new BuyHsb();
	    
	    //客户号
	    buyHsb.setCustId(entCustId);
	    //客户互生号
	    buyHsb.setHsResNo(entResNo);
	    //客户名称
	    buyHsb.setCustName(baseInfo.getEntName());
	    //客户类型
	    buyHsb.setCustType(baseInfo.getEntCustType());
	    //实收货币金额
	    buyHsb.setCashAmt(String.format("%1$.2f", reqParam.getTransAmount()));
	    //操作员编号
	    buyHsb.setOptCustId(reqParam.getOperNo());
	    //终端编号
	    buyHsb.setTermNo(reqParam.getPosNo());
	    //批次编号
	    buyHsb.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
	    //POS流水号
	    buyHsb.setOriginNo(cmd.getPointId());
	    //小票凭证号
	    buyHsb.setTermRuncode(reqParam.getPosRunCode());
	    buyHsb.setRemark("企业兑换互生币");
		
		aoApiService.savePosBuyHsb(buyHsb);
        AccountBalance accBalance = acApiService.searchAccBalance(entCustId, AccountType.ACC_TYPE_POINT20110.getCode());
		cmd.getIn().add(new BitMap(48, reqId, new Poi48(null, null, null, 
				new BigDecimal(accBalance.getAccBalance()), null), cmd.getPartVersion()));
		// 国际信用卡公司代码+积分0.12 M/C
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
		
      
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);//kend test
        
		return cmd;
	}
	
	
}

	
