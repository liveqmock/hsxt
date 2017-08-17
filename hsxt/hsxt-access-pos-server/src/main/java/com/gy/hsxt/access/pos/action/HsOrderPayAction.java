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
import com.gy.hsec.external.bean.OrderResult;
import com.gy.hsec.external.bean.QueryParam;
import com.gy.hsec.external.bean.QueryResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.EtErrorCode;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.Poi63;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.PosTransNote;
import com.gy.hsxt.access.pos.service.EtApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsOrderPayAction
 * @Description:互生订单支付
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:52:29
 * @version V1.0
 */
@Service("hsOrderPayAction")
public class HsOrderPayAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_HSORDER_HSB_PAY_ID;
    
    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
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
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Override
    public String getReqType() {

        return reqType;
    }

    @Autowired
    @Qualifier("etApiService")
    private EtApiService etApiService;

	@Override
    public Object action(Cmd cmd) throws Exception {
    	
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.debug("POS HsbPayAction", "pos 请求参数", JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);
        
        
        final PosTransNote hsOrder = (PosTransNote) reqParam.getGyBean();
        QueryParam qp = new QueryParam();

        String tradeType = null;
        String orderNo = hsOrder.getPosBizSeq();
        BigDecimal orderAmount = hsOrder.getTransAmount();
        BigDecimal tradeAmount = reqParam.getTransAmount();
        String curCode = reqParam.getCurrencyCode();
        String cardNo = reqParam.getCardNo();
        
        String entNo = reqParam.getEntNo();
        String posNo = reqParam.getPosNo();
        String batchNo = reqParam.getTradeTypeInfoBean().getBatNo();
        String posBizSeq = reqParam.getPosRunCode();
        String acPosNo = cmd.getPointId();
        String operNo = reqParam.getOperNo();
        
        /* 现金支付 48域长32位，只有订单，无互生币金额。
         * 互生币支付，48域长44位， 32位订单号（17位前补零）+12位互生币金额
         */
        SystemLog.debug("HsOrderPayAction","action()","互生订单号:" + orderNo + 
        		",订单互生币金额:" + orderAmount + ",原币种金额:" + tradeAmount);

        if (reqId.equalsIgnoreCase(reqType)){
            // 刷卡 互生币支付
            tradeType = "32";
            qp.setTradePass(CommonUtil.string2MD5((String)cmd.getTmpMap().get("pwd")));
            qp.setCardNo(cardNo);//现金支付，报文中无卡号
            qp.setOrderAmount(orderAmount);
        }else if (reqId.equalsIgnoreCase(PosConstant.REQ_HSORDER_CASH_PAY_ID))
	        // 现金支付
            tradeType = "31";
        else
        	throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "支付方式错误");
        
        qp.setTradeType(tradeType);
        qp.setOrderId(orderNo);
        qp.setTradeAmount(tradeAmount);
        qp.setCurrCode(curCode);
        qp.setEntNo(entNo);
        qp.setPosNo(posNo);
        qp.setBatchNo(batchNo);
        qp.setPosBizSeq(posBizSeq);
        qp.setAcposNo(acPosNo);
        qp.setOperNo(operNo);
        
        QueryResult qr = etApiService.netOrderPay(qp);
        OrderResult or = null;
        if(qr.getRetCode()==EtErrorCode.SUCCESS.getErrorCode()) {
        	or = qr.getOrderResult();
        }else if(qr.getRetCode()==EtErrorCode.BALANCE_NOT_ENOUGH.getErrorCode()) {
        	CommonUtil.checkState(true, "网上订单支付，消费者互生币账户余额不足", PosRespCode.INSUF_BALANCE_CUST_HSB);
        }
        //start--added by liuzh
        else if(qr.getRetCode()==EtErrorCode.NOT_SUPPORT_CASH.getErrorCode()) {
        	CommonUtil.checkState(true, "网上订单支付，餐饮尾款不支持现金支付", PosRespCode.NOT_SUPPORT_CASH);        	
        }else if(qr.getRetCode()==EtErrorCode.ORDER_PAY_PROCESS.getErrorCode()) {
        	CommonUtil.checkState(true, "网上订单支付，订单支付业务处理中", PosRespCode.ORDER_PAY_PROCESS);        	
        }else if(qr.getRetCode()==EtErrorCode.ORDER_PAID.getErrorCode()) {
        	CommonUtil.checkState(true, "网上订单支付，订单已付款", PosRespCode.ORDER_PAID);        	
        }else if(qr.getRetCode()==EtErrorCode.ORDER_NOT_EXIST.getErrorCode()) {
        	CommonUtil.checkState(true, "网上订单支付，订单不存在", PosRespCode.NO_FOUND_HSEC_ORDER);        	
        }else if(qr.getRetCode()==EtErrorCode.USER_NOT_EXIST.getErrorCode()) {
        	CommonUtil.checkState(true, "网上订单支付，用户不存在", PosRespCode.NO_FOUND_ACCT);        	
        }else if(qr.getRetCode()==RespCode.PS_HSB_CONSUMER_PAYMENT_MAX.getCode()) {
        	CommonUtil.checkState(true, "网上订单支付失败,互生币单笔支付超限", PosRespCode.HSB_CONSUMER_PAYMENT_MAX);
        }else if(qr.getRetCode()==RespCode.PS_HSB_CONSUMER_PAYMENT_DAY_MAX.getCode()) {
        	CommonUtil.checkState(true, "网上订单支付失败,互生币支付每日超限", PosRespCode.HSB_CONSUMER_PAYMENT_DAY_MAX);
        }else if(qr.getRetCode()==ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue()) {
        	CommonUtil.checkState(true, "网上订单支付失败,交易密码未设置", PosRespCode.TRANS_PWD_NOT_SET);
        }else if(qr.getRetCode()==ErrorCodeEnum.TRADEPWD_USER_LOCKED.getValue()) {
        	CommonUtil.checkState(true, "网上订单支付失败,交易密码连续出错导致用户锁定", PosRespCode.TRADEPWD_USER_LOCKED);
        }else if(qr.getRetCode()==ErrorCodeEnum.PWD_TRADE_ERROR.getValue()) {
        	CommonUtil.checkState(true, "网上订单支付失败,交易密码错误", PosRespCode.CHECK_PASS_FAIL);
        }
        //end--added by liuzh
        else {
        	CommonUtil.checkState(true, "网上订单支付失败", PosRespCode.AGAIN_REVERSE);
        }
        
        cmd.getIn().add(new BitMap(48, reqId, new Poi48(null, or.getPointValue(), or.getOrderAmount()), 
        						cmd.getPartVersion()));
        cmd.getIn().add(new BitMap(63, reqId, new Poi63(PosConstant.ICC_CODE_GYT, or.getPointValue()), 
        						cmd.getPartVersion()));
         
         
       //冲正测试 kend test
//       cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
         
        return cmd;
    }

}
