/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.chain.Command;
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
import com.gy.hsxt.access.pos.model.VersionAdapter;
import com.gy.hsxt.access.pos.service.AcApiService;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.LcsClientService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UFApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.PayPwdValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbPayAction
 * @Description: 互生币支付
 * 
 * @author: wucl
 * @modified by zoukun 增加异地交易、pos扫码等功能
 * @date: 2015-12-07
 * @version V1.0
 */
@Service("hsbPayAction")
public class HsbPayAction implements IBasePos {
	//请求类型，在《（POS）终端应用规范 消息域-消息交换说明》中定义
    public static final String reqType = PosConstant.REQ_HSB_PAY_CCY_ID;

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
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

    @Autowired
    @Qualifier("acApiService")
    private AcApiService acApiService;

    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;

    @Autowired
    @Qualifier("payPwdValidation")
    private PayPwdValidation payPwdValid;
    
    
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
        SystemLog.info("HsbPayAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.LOCAL_CARD_LOCAL_HSB.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.REMOTE_CARD_LOCAL_HSB.getCode();
        		
        //4 提取支付参数并作基础校验
        //48域 归一自定义信息
        final Poi48 poi = (Poi48) reqParam.getGyBean();
        //商户企业承兑的积分，已保留小数点后2位。
        BigDecimal amt = poi.getAmt();
        // 互生币金额  48.4域
        BigDecimal hsbAmount = poi.getHsbAmount();
        BigDecimal rate = poi.getRate();

        // 积分比率上下限阀值 
        //若为 免费成员企业，最小积分比例有特定要求。
        int startResType = (int) cmd.getTmpMap().get("startResType");
        BigDecimal poiRateMax = platformParamService.getPointRatMax();
        BigDecimal poiRateMin = null;
        //5为 免费成员企业
        if(startResType == 5) poiRateMin = platformParamService.getFreeEntPointRatMin();
        	else poiRateMin = platformParamService.getPointRatMin();
        CommonUtil.checkState(rate.compareTo(poiRateMin) < 0 || rate.compareTo(poiRateMax) > 0, 
        		startResType == 5?"免费成员企业":"非免费成员企业" +
        		" rate:" + rate + ",poiRateMin:" + poiRateMin
                + ",poiRateMax:" + poiRateMax, PosRespCode.OUT_POINTRATE_SCOPE);

		//以交易金额（4域）计算消费者应得积分，用于校验pos机计算的积分的正确性。
        BigDecimal transAmount = reqParam.getTransAmount();
        BigDecimal checkAmt = transAmount.multiply(rate).setScale(2,RoundingMode.UP);
        //pos机计算的积分需要和pos server计算的积分结果一致
        CommonUtil.checkState(!amt.equals(checkAmt), "请求消费金额:" + reqParam.getTransAmount() + 
        			",请求积分比例:" + rate + ",请求积分额:" + amt + "与server计算积分额:" + checkAmt + "不一致", 
        			PosRespCode.CHECK_ASSUREOUTVALUE);
        CommonUtil.checkState(0 == hsbAmount.compareTo(BigDecimal.ZERO), "交易的互生币金额不能为0", 
        				PosRespCode.HSB_PAY_ZERO_ERROR);
        CommonUtil.checkState(reqParam.getTransAmount().doubleValue() < PosConstant.MIN_CURRENCY_AMT, 
        			"交易金额不能小于" + PosConstant.MIN_CURRENCY_AMT, PosRespCode.HSB_PAY_LESS_ERROR);

        
        //5 组装支付参数
        // 企业与客户信息
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");

        Point point = new Point();
        point.setChannelType(Channel.POS.getCode());
        point.setEntCustId(entStatusInfo.getEntCustId());
        point.setEntResNo(reqParam.getEntNo());
        point.setEntName(entStatusInfo.getEntCustName());
        point.setEquipmentNo(reqParam.getPosNo());
        point.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        point.setOperNo(reqParam.getOperNo());
        point.setPerCustId((String)cmd.getTmpMap().get("custId"));
        point.setPerResNo(custCardNo);
        point.setPointRate(String.valueOf(rate));
        point.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        point.setSourceCurrencyCode(reqParam.getCurrencyCode());
        //本币金额（不考虑外币）
        point.setSourceTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));
        point.setSourceTransDate(DateUtil.getCurrentDateTime());
        
        //start--added by liuzh on 2016-05-25 二维码支付要送二维码的时间 sourcePosDate
        if(!StringUtils.isEmpty(reqParam.getTradeDate()) 
        		&&!StringUtils.isEmpty(reqParam.getTradeTime())
        		&& reqParam.getTradeTimestamp()!=null)
        {
        	point.setSourcePosDate(reqParam.getTradeTimestamp().toString());
        }
        //end--added by liuzh on 2016-05-25
        
        point.setSourceTransNo(cmd.getPointId());
        //折算成互生币的金额
        point.setTransAmount(String.format("%1$.2f", hsbAmount));
        point.setPointSum(String.format("%1$.2f", amt));
        point.setTermRunCode(reqParam.getPosRunCode());
        point.setTermTradeCode(reqParam.getTermTradeCode());
        point.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());
        point.setTransType(transType);
        //point.setPosToPsCount = 1;
        
        //start--added by liuzh on 2016-06-23
        VersionAdapter versionAdapter = new VersionAdapter(cmd.getPartVersion());
        versionAdapter.buildHsb(cmd,point, poi);
        //end--added by liuzh on 2016-06-23
        
        PointResult result = null;
        
        //6 发起支付请求 第一步 本地
        SystemLog.debug("HsbPayAction", "action()", "发起互生币支付请求。point：" +  JSON.toJSONString(point));
        try{
            result = psApiService.point(point);
        }catch (Exception e){
        	if (!e.getClass().equals(HsException.class)){
        		CommonUtil.checkState(true, "互生币支付失败，调用ps服务发生非互生异常。请求消费积分参数：" + JSON.toJSONString(point), 
                		PosRespCode.AGAIN_REVERSE, e);
            }
        	SystemLog.error("HsbPayAction", "action()", "互生币支付失败，调用ps服务发生互生异常。请求消费积分参数：" + 
        						JSON.toJSONString(point), e);
        	/*
        	 * ps可能抛出的异常：
        	 * 互生币单笔支付超限PS_HSB_CONSUMER_PAYMENT_MAX(11027),
        	 * 互生币支付每日超限PS_HSB_CONSUMER_PAYMENT_DAY_MAX(11028),
        	 */
        	throw (HsException)e; 
        }
        CommonUtil.checkState(null == result, "ps异常，执行point()返回null", PosRespCode.POS_CENTER_FAIL);
//        SystemLog.debug(this.getClass().getName(), "action()", "本地ps处理完成。retCode：" +  result.getRetCode() +
//        			"retMsg：" +  result.getRetMsg());
        
        
        //7 转异地发卡平台处理
        if(TransType.REMOTE_CARD_LOCAL_HSB.equals(transType)){
//        if(true){//测试用  
        	//需明示前一步执行正确，必须返回唯一期望结果。
//        	CommonUtil.checkState(10010 != result.getRetCode(), "异地卡交易，交易平台ps异常，执行point()返回码期望“10010”，实际“" + 
//        				result.getRetCode() + "”", PosRespCode.POS_CENTER_FAIL);
        	//对发卡平台而言，是本地卡异地交易。发起异地平台处理请求。第二步 异地
        	point.setTransType(TransType.LOCAL_CARD_REMOTE_HSB.getCode());
        	//发卡平台需验卡及支付密码
        	Set<String> checkItem = new HashSet<String>();
        	checkItem.add("card");checkItem.add("TP");
        	//为发卡平台验卡提供参数
        	JSONObject p = commonValidation.assembleRemoteValidParm(cmd, checkItem);
        	p.put("transType", TransType.LOCAL_CARD_REMOTE_HSB.getCode());
        	//point.setCardValidParm(p);//待ps更新bean
        	result = null;
        	try{
        		result = (PointResult) commonService.sendCrossPlatformIndicate(point,custCardNo,"hsbPay");
        	}catch(HsException he){
        		String subSys = commonService.checkSubSysByHsErrCode(he.getErrorCode());
        		//因为已经执行了第一步，这里的任何执行失败，都需要为第一步冲正。即便第一步中ps可能并未有实际转账动作，但为了不涉及ps的业务，这里仍然发起。
        		CommonUtil.checkState(true, "异地卡交易，互生币支付，发卡平台处理失败，调用 "+ subSys +" 服务发生互生异常。异常码：" + 
        				he.getErrorCode() + " 异常信息：" + he.getLocalizedMessage(), PosRespCode.AGAIN_REVERSE, he);
        	}catch(Exception e){
        		CommonUtil.checkState(true, "异地卡交易，互生币支付，发卡平台处理失败，调用综合前置服务抛出非互生异常。" , PosRespCode.AGAIN_REVERSE, e);
        	}
        	//第二步执行失败，则需要回滚第一步。
        	CommonUtil.checkState(null == result, "异地卡交易，发卡平台ps异常，执行point()返回null", PosRespCode.AGAIN_REVERSE);
//        	CommonUtil.checkState(10020 != result.getRetCode(), "异地卡交易，发卡平台ps异常，执行point()返回码期望“10011”，实际“" + 
//        				result.getRetCode() + "”", PosRespCode.AGAIN_REVERSE);
//            SystemLog.info(this.getClass().getName(), "action()", "发卡平台ps处理完成。retCode：" +  result.getRetCode() +
//            			"retMsg：" +  result.getRetMsg());
        	
        	//8 本地交易平台继续处理 第三步
            point.setTransType(transType);
//            point.setPosToPsCount = 2;
            result = null;
        	try{
                result = psApiService.point(point);
            }catch(HsException he){
        		CommonUtil.checkState(true, "异地卡交易，互生币支付，交易平台ps第二次处理失败。异常码：" + 
        				he.getErrorCode() + " 异常信息：" + he.getLocalizedMessage(), PosRespCode.AGAIN_REVERSE, he);
        	}catch(Exception e){
        		CommonUtil.checkState(true, "异地卡交易，互生币支付，交易平台ps第二次处理抛出非互生异常。" , PosRespCode.AGAIN_REVERSE, e);
        	}
        	CommonUtil.checkState(null == result, "异地卡交易，交易平台ps第二次处理时发生异常，执行point()返回null", PosRespCode.AGAIN_REVERSE);
//        	CommonUtil.checkState(10020 != result.getRetCode(), "异地卡交易，交易平台ps第二次处理时发生异常，执行point()返回码期望“10012”，实际“" + 
//        				result.getRetCode() + "”", PosRespCode.AGAIN_REVERSE);
        }
        	
        
        //9 组装返回报文各域
        //2域 消费者卡号  手输、刷卡有，扫码无
        boolean exist2area = false;
        for (int i = 0; i < cmd.getIn().size(); i++) {
			BitMap BitMap = cmd.getIn().get(i);
			if(BitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
		}
        if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
        
        //63域 国际信用卡公司代码+积分0.12 M/C
        cmd.getIn().add(
                new BitMap(63, reqId, new Poi63(PosConstant.ICC_CODE_GYT, new BigDecimal(result.getPerPoint())),
                        cmd.getPartVersion()));
        
        
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);// kend test 触发冲正
        return cmd;
    }
     


}
