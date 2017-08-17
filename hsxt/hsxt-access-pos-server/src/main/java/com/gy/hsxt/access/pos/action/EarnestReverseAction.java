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
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.ReturnResult;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsbPayReverseAction
 * @Description:互生币支付 冲正类业务
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:54:46
 * @version V1.0
 */
@Service("earnestReverseAction")
public class EarnestReverseAction implements IBasePos {
	public static final String reqType = PosConstant.REQ_EARNEST_REVERSE_ID;

    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;
    
    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
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
    public String getReqType() {

        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("EarnestReverseAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        //1 发起冲正业务参数校验(冲正交易只有mac、签到、企业信息的校验)
        commonValidation.reqParamValid(cmd);
        
        //按照协议，pos机发起请求时，需要提供卡号
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        
        
        //2 组装冲正请求报文
        Correct correct = new Correct();
        correct.setChannelType(Channel.POS.getCode());
        correct.setEntResNo(reqParam.getEntNo());
        correct.setEquipmentNo(reqParam.getPosNo());
        correct.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        correct.setInitiate("POS");
        correct.setReturnReason(reqParam.getReverseCode());
        correct.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        correct.setSourceTransNo(cmd.getPointId());
        correct.setTermRunCode(reqParam.getPosRunCode());
        correct.setTransDate(DateUtil.getCurrentDate2String());
        //correct.setTransType(transType);//此时只知是支付、撤单、退货类，无法识别本地异地
        
        //3 冲正交易类型初步识别（冲正无卡片信息，无法识别本地异地。后续新版pos报文协议调整时考虑优化。）
        String transType = null;
        if(reqId.equalsIgnoreCase(PosConstant.REQ_EARNEST_REVERSE_ID))
            transType = TransType.LOCAL_CARD_LOCAL_EARNEST_REVERSE.getCode();
        else if(reqId.equalsIgnoreCase(PosConstant.REQ_EARNEST_CANCEL_REVERSE_ID))
            transType = TransType.LOCAL_CARD_LOCAL_EARNEST_CANCEL_REVERSE.getCode();
        else if(reqId.equalsIgnoreCase(PosConstant.REQ_EARNEST_SETTLE_REVERSE_ID))
        	transType = TransType.LOCAL_CARD_LOCAL_EARNEST_SETTLE_REVERSE.getCode();
        
        correct.setTransType(transType);//TransType中增加业务类型
            
        //4 发起本地冲正 第一步
        ReturnResult result = psApiService.reversePoint(correct);
        CommonUtil.checkState(null == result, "冲正失败，ps返回冲正结果为空，需再次冲正。", PosRespCode.AGAIN_REVERSE);
        //5 根据处理结果判定是否为异地卡交易（本地ps可识别并返回原交易是否为异地卡，且能够返回卡号）
        if("retMsg:异地卡交易".equals("result.getRetCode()")){
//        if(true){//测试用 是异地卡
        	//发起异地请求 第二步
        	if(TransType.LOCAL_CARD_LOCAL_EARNEST_REVERSE.getCode().equals(transType))
        		correct.setTransType(TransType.LOCAL_CARD_REMOTE_EARNEST.getCode());
        	else if(TransType.LOCAL_CARD_LOCAL_EARNEST_CANCEL_REVERSE.getCode().equals(transType))
        		correct.setTransType(TransType.LOCAL_CARD_REMOTE_EARNEST_CANCEL_REVERSE.getCode());
        	else if(TransType.LOCAL_CARD_LOCAL_EARNEST_SETTLE_REVERSE.getCode().equals(transType))
        		correct.setTransType(TransType.LOCAL_CARD_REMOTE_EARNESTT_SETTLE_REVERSE.getCode());
        	
        	result = (ReturnResult) commonService.sendCrossPlatformIndicate(correct,"result.get消费者互生号","earnestCorrect");
        	CommonUtil.checkState(null == result, "冲正失败，ps返回冲正结果为空，需再次冲正。", PosRespCode.AGAIN_REVERSE);
        	//6 继续处理本地平台任务 第三步（撤单冲正无，支付、退货有）
        	if("发卡平台处理完成".equals("result.getRetCode()")){
        		if(TransType.LOCAL_CARD_LOCAL_EARNEST_REVERSE.getCode().equals(transType))
        			correct.setTransType(TransType.REMOTE_CARD_LOCAL_EARNEST_REVERSE.getCode());
        		else if(TransType.LOCAL_CARD_LOCAL_EARNEST_CANCEL_REVERSE.getCode().equals(transType))
        			correct.setTransType(TransType.REMOTE_CARD_LOCAL_EARNEST_CANCEL_REVERSE.getCode());
        		else if(TransType.LOCAL_CARD_LOCAL_EARNEST_SETTLE_REVERSE.getCode().equals(transType))
        			correct.setTransType(TransType.REMOTE_CARD_LOCAL_EARNESTT_SETTLE_REVERSE.getCode());
        		
        		result = psApiService.reversePoint(correct);
        	}else {
        		//发卡平台执行冲正失败，引发再次冲正（pos机中，只有登记的冲正项执行完成（或连续执行三次后），才允许继续进行其它业务。）
        		cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
        		CommonUtil.checkState(true, "异地交易，发卡平台执行冲正失败 retCode：" + 
        				"result.getRetCode()", PosRespCode.AGAIN_REVERSE);
        	}
        		
        }else if("冲正处理失败".equals("result.getRetCode()")){
        	cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
    		CommonUtil.checkState(true, "交易平台执行冲正失败 retCode：" + 
    				"result.getRetCode()", PosRespCode.AGAIN_REVERSE);
        }
        
        //7 组装返回报文各域
        /*
         * 2域：消费者互生号 
         * 4域：交易金额，同原交易；
		 * 41域：受卡机终端标识码 同原交易
		 * 42域：受卡方标识码 同原交易
         */
        BitMap bitMap = null;
        boolean exist2area = false;
        boolean exist4area = false;
        
        if(null == custCardNo) custCardNo = result.getPerResNo();
        
        for (int i = 0; i < cmd.getIn().size(); i++){
            bitMap = cmd.getIn().get(i);
            if(bitMap.getBit() == 2 && null != custCardNo) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
            if (bitMap.getBit() == 4) exist4area = true;
            if (bitMap.getBit() == 41){
                cmd.getIn().set(i, new BitMap(41, StringUtils.right(result.getEquipmentNo(), 4)));
            }
            if (bitMap.getBit() == 42){
                cmd.getIn().set(i, new BitMap(42, result.getEntResNo()));
            }
        }
        
        if(!exist2area && null != custCardNo)cmd.getIn().add(new BitMap(2, custCardNo));
        
        if(!exist4area){
        	String soureceTAStr = result.getSourceTransAmount();
    		BigDecimal sourceTransAmout = null == soureceTAStr ? BigDecimal.ZERO : new BigDecimal(soureceTAStr);
    		cmd.getIn().add(new BitMap(4, reqId, sourceTransAmout, 
    						platformParamService.getLocalCurrencyCode().equals(result.getSourceCurrencyCode())?
    								PosConstant.ISLOCAL_CURRENCY:0, cmd.getPartVersion()));
        }
				
        
        //63域 处理完成，返回归一标记
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));

        return cmd;
    }
}
