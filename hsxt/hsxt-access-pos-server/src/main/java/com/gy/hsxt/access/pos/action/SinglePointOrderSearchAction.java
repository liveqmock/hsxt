/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.HexBin;
import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.QrCodeCredential;
import com.gy.hsxt.access.pos.model.QrCodeTrans;
import com.gy.hsxt.access.pos.model.VersionAdapter;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosDecoder;
import com.gy.hsxt.access.pos.validation.CardValidation;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.QueryPosSingle;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.QuerySingle;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: SinglePointOrderSearchAction
 * @Description:消费积分查询（根据订单号查询）
 * 				对应pos机2.0的交易流水号查询,报文的“查询单笔交易详情单”
 * 				   pos机3.0 在2.0基础上增加根据单据二维码原始信息查询支付状态。
 * @author: wucl
 * @date: 2015-10-16 下午3:46:03
 * @version V1.0
 */
@Service("singlePointOrderSearchAction")
public class SinglePointOrderSearchAction implements IBasePos {
	
	/*查询单笔交易详情单 	0920/0930	710000   03  */
    public static final String reqType = PosConstant.REQ_POINT_ORDER_SEARCH_ID;

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
    @Qualifier("aoApiService")
    protected AoApiService aoApiService;

    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;

    @Override
    public String getReqType() {

        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("SinglePointOrderSearchAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);

        //原业务交易流水
        String transNo = "";
        //二维码解密后字符串
    	String qrDecryptFd62 = null;
        //Map<String, String> qrElement1 = new HashMap<String, String>();
        //二维码类型
        String qrType = null;
        //交易单据二维码
        QrCodeTrans qrCodeTrans = null;
        
        QueryPosSingle qps = new QueryPosSingle();
        if(PosConstant.INPUT_WAY_QR.equals(reqParam.getInputWay())){
        	//start-modified by liuzh on 2016-04-16   
        	//单据二维码字符串
        	String qrStr = (String)reqParam.getCustom2();
        	SystemLog.debug("SinglePointOrderSearchAction", "action()", "查询交易单据二维码,62域:" + qrStr);
        	
        	//start--added by liuzh on 2016-05-30
        	String secretKey = null;
        	try{
	        	//密钥
	        	secretKey = PosDecoder.getSecretKey(qrStr);
        	}catch(Exception ex) {
        		throw new PosException(PosRespCode.QR_TRANS_ERROR, "二维码信息错误,交易单据二维码解密异常");
        	}
        	//end--added by liuzh on 2016-05-30
        	try{
	        	//密钥
        		//start--commented by liuzh on 2016-05-30
	        	//String secretKey = PosDecoder.getSecretKey(qrStr);
        		//end--commented by liuzh on 2016-05-30
        		
	        	/**
	        	 * 调用uc接口解密二维码及校验 .
	        	 * parseQrTransBill接口只返回时间戳之后的解密字符串,需要拼接前面的字符串(格式:BH&06031990000&0001&000004&000099&20160419101532)
	        	 * parseQrTransBill接口返回数据 格式如下: &156&000000010000&3000&000000003000&000000010000&C372A7F6&359D924E\u0000\u0000\u0000\u0000\u0000\u0000&0603199000020160416&开发POS专用
	        	 */
	        	qrDecryptFd62 = qrStr.substring(0,48) + ucApiService.parseQrTransBill(HexBin.decode(secretKey), qrStr);
	        	
	        	SystemLog.debug("SinglePointOrderSearchAction", "action()", "查询交易单据二维码,decryptFd62:" + qrDecryptFd62);
        	}catch(HsException he) {
        		throw he;
        	}catch(Exception e) {
            	SystemLog.error("SinglePointOrderSearchAction", "action()", 
        				"调用uc接口解密二维码及校验发生异常 .请求参数:" + qrStr, e);
            	//throw e;
            	throw new PosException(PosRespCode.QR_TRANS_ERROR, "二维码信息错误");
        	}
            CommonUtil.checkState(qrDecryptFd62==null, "交易单据二维码解密及校验失败,uc接口返回结果为空.", PosRespCode.REQUEST_PACK_FORMAT);
            
        	//qrElement = commonService.getSeqNTradeTypeFromQr(qrDecryptFd62);

        	//qrElement = commonService.getSeqNTradeTypeFromQr(qrStr);
        	//end-modified by liuzh on 2016-04-16
        	
            //获取二维码对象
            //start--added by liuzh on 2016-06-23 pos3.01版本二维码增加抵扣券业务支持
            //Object oQrCode = commonService.getQrCode(qrDecryptFd62);
            Object oQrCode = commonService.getQrCode(qrDecryptFd62,cmd.getPartVersion());
            //end--added by liuzh on 2016-06-23 
            
            CommonUtil.checkState(oQrCode==null, "二维码数据解析为空.", PosRespCode.REQUEST_PACK_FORMAT);
            //获取交易类型
            qrType = StringUtils.left(qrDecryptFd62,2);
            
        	if(PosConstant.QRTYPE_BILL_SEQ.equals(qrType)) {
        		//交易凭据二维码
        		QrCodeCredential  qrCodeCredential = (QrCodeCredential)oQrCode;
        		transNo = qrCodeCredential.getTradeRunCode();
        	//start--modified by liuzh on 2016-06-24 pos3.01版本的前缀由BH修改为B1	
        	//}else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)){
        	}else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)
        			|| PosConstant.QRTYPE_BILL_PAY_HANG_301.equals(qrType)){        		
        	//end--modified by liuzh on 2016-06-24
        		//交易单据二维码
        		/*
        		qps.setBatchNo((String) qrElement.get("PosBatchNo"));
        		qps.setEntResNo((String) qrElement.get("EntHsNo"));
        		qps.setEquipmentNo((String) qrElement.get("PosTermNo"));
        		qps.setSourcePosDate((String) qrElement.get("TransDateTime"));
        		qps.setTermRunCode((String) qrElement.get("PosSeqNo"));
        		*/
        		qrCodeTrans = (QrCodeTrans)oQrCode;
        		
        		if(qrCodeTrans!=null) {
                    CommonUtil.checkState(StringUtils.isEmpty(qrCodeTrans.getPointRate()), "二维码数据的积分比例为空.", PosRespCode.QR_TRANS_DATA_ERROR);       			
        		}
                
        		qps.setBatchNo(qrCodeTrans.getPosBatchNo());
        		qps.setEntResNo(qrCodeTrans.getEntResNo());
        		qps.setEquipmentNo(qrCodeTrans.getPosTermNo());
        		qps.setSourcePosDate(qrCodeTrans.getTransTime());
        		qps.setTermRunCode(qrCodeTrans.getPosTermRunCode());
        	}
        }else{
			transNo = reqParam.getOriginalNo();
        }
        
        String entNo = reqParam.getEntNo();
        QueryResult qr = new QueryResult();
        
        //start--added by liuzh on 2016-05-21
    	SystemLog.debug("SinglePointOrderSearchAction", "action()", "transNo:" + transNo);
    	//end--added by liuzh on 2016-05-21
    	
        if(!"".equals(transNo)){
        	//根据交易流水号的第一位路由到对应核心业务系统（ao中兑换和代兑在两张表，为避免轮询，暂用两个接口，这里要区分开。故占用两个枚举值。）
            //第一位路由定义，在PosSeqGenerator：getRouteIdByReqId()中。
            String transNoPrefix = transNo.substring(0, 1);
            if ("1".equals(transNoPrefix)){//兑换
                // 调用AO系统的查询
                BuyHsb result = aoApiService.getPosBuyHsbInfo(entNo, transNo);
                CommonUtil.checkState(null == result, "根据单号未检索到兑换互生币", PosRespCode.NO_POS_ORDER);
                qr.setTermTypeCode("71");//原交易的 写死即可
                qr.setTermTradeCode("021040");//原交易的 写死即可
                qr.setTermRunCode(result.getTermRuncode());//原交易的 写死即可
                qr.setOperNo(result.getOptCustId());//原交易的 写死即可
                qr.setSourceTransDate(result.getReqTime());//原交易的 写死即可
                qr.setSourceTransNo(result.getOriginNo());//原交易的 写死即可
                qr.setEquipmentNo(result.getTermNo());
                qr.setSourceTransAmount(result.getCashAmt());
                qr.setPointRate("0.0");
                qr.setEntPoint("0.0");
                qr.setPerPoint("0.0");
                qr.setSourceBatchNo(result.getBatchNo());
                qr.setRemark("entExchangeHsb");
            }else if ("2".equals(transNoPrefix)){//代兑
                // 调用AO系统的查询
                ProxyBuyHsb result = aoApiService.getPosProxyBuyHsbInfo(entNo, transNo);
                CommonUtil.checkState(null == result, "根据单号未检索到兑换互生币", PosRespCode.NO_POS_ORDER);
                qr.setTermTypeCode("70");//原交易的 写死即可
                qr.setTermTradeCode("400000");//原交易的 写死即可
                qr.setTermRunCode(result.getTermRuncode());//原交易的 写死即可
                qr.setOperNo(result.getOptCustId());//原交易的 写死即可
                qr.setSourceTransDate(result.getReqTime());
                qr.setSourceTransNo(result.getOriginNo());
                qr.setEquipmentNo(result.getTermNo());
                qr.setSourceTransAmount(result.getCashAmt());
                qr.setPointRate("0.0");//不涉及，解析时有非空约束，写死。
                qr.setEntPoint("0.0");//不涉及，解析时有非空约束，写死。
                qr.setPerPoint("0.0");//不涉及，解析时有非空约束，写死。
                qr.setSourceBatchNo(result.getBatchNo());
                qr.setRemark("proxyExchangeHsb");
            }else{
            	//企业互生号
                String entResno = reqParam.getEntNo();
                // 企业客户信息
                AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
                String entCustId = entStatusInfo.getEntCustId();
                
                //组装查询条件
                QuerySingle qs = new QuerySingle();
                qs.setEntCustId(entCustId);
                qs.setEntResNo(entResno);
                qs.setEquipmentNo(reqParam.getPosNo());
                qs.setSourceTransNo(reqParam.getOriginalNo());
                qr = psApiService.singlePosQuery(qs);
                if(qr!=null) {
                	qr.setRemark("point");
                	//start--added by liuzh on 2016-06-16
                	//设置终端交易类型码, 网银支付 手机app未填写 term_type_code; pos端扫码选互生币支付填写为63;选现金支付填写为61
                	if(StringUtils.isEmpty(qr.getTermTypeCode())) {            		
                		qr.setTermTypeCode(StringUtils.leftPad(PosConstant.TRANS_TYPE_HSP_PAY,4,PosConstant.ZERO_CHAR)); 
                	}
                	//end----added by liuzh on 2016-06-16
                }
                
            }
            
            //start--added by liuzh on 2016-04-26
        	SystemLog.debug("SinglePointOrderSearchAction", "action()", "transNo:" + transNo
        			+ ",transNoPrefix:" + transNoPrefix
        			+ ",qr:" + JSON.toJSONString(qr));
        	//end--added by liuzh on 2016-04-26
        
        //start--added by liuzh on 2016-06-24	
        //}else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)){
        }else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)
        		|| PosConstant.QRTYPE_BILL_PAY_HANG_301.equals(qrType)){
        //end--added by liuzh on 2016-06-24	
        	SystemLog.debug("SinglePointOrderSearchAction", "action()", "单据二维码，交易信息查询。"
        			+ ",qps:" + JSON.toJSONString(qps));
        	
        	qr = psApiService.singlePosQuery(qps);
        	//start--added by liuzh on 2016-05-24
            if(qr!=null) {
            	qr.setRemark("point");
            	//设置终端交易类型码, ios扫码未填写 term_type_code; pos端扫码选互生币支付填写为63;选现金支付填写为61
            	if(StringUtils.isEmpty(qr.getTermTypeCode())) {            		
            		qr.setTermTypeCode(StringUtils.leftPad(PosConstant.TRANS_TYPE_HSP_PAY,4,PosConstant.ZERO_CHAR)); 
            	}
            }
            //end--added by liuzh on 2016-05-24
        }else
        	CommonUtil.checkState(true, "请求报文非法。单笔插叙要求或者提供原交易流水，或者有二维码单据要素。", 
        													PosRespCode.REQUEST_PACK_FORMAT);
        final List<BitMap> list = cmd.getIn();
        
        //start--added by liuzh on 2016-05-21
    	SystemLog.debug("SinglePointOrderSearchAction", "action()", "transNo:" + transNo
    			+ ",qrType:" + qrType
    			+ ",qr:" + JSON.toJSONString(qr));    	
    	SystemLog.debug("SinglePointOrderSearchAction", "action()", "transNo:" + transNo
    			+ ",qrType:" + qrType
    			+ ",qrCodeTrans:" + JSON.toJSONString(qrCodeTrans));     	
    	//end--added by liuzh on 2016-05-21
    	

		/**
		 * qr为空且查询交易单据二维码 不为空是,用二维码信息填充qr
		 */
		//start--added by liuzh on 2016-04-20

		if(qr==null && qrCodeTrans!=null) {
            qr = new QueryResult();
            qr.setBatchNo(qrCodeTrans.getPosBatchNo());
            qr.setEntResNo(qrCodeTrans.getEntResNo());
            qr.setEquipmentNo(qrCodeTrans.getPosTermNo());
            qr.setTermRunCode(qrCodeTrans.getPosTermRunCode());
                        
            //交易时间-转换时间格式
            String transTime = qrCodeTrans.getTransTime();
            transTime = String.format("%s-%s-%s %s:%s:%s",
            		transTime.substring(0,4),
            		transTime.substring(4,6),
            		transTime.substring(6,8),
            		transTime.substring(8,10),
            		transTime.substring(10,12),
            		transTime.substring(12)
        		);
            BigDecimal transAmount = CommonUtil.changePackDataToMoney(qrCodeTrans.getTransAmount());
            BigDecimal pointRate = CommonUtil.changePackDataToRate(qrCodeTrans.getPointRate());
            //分配的积分数
            BigDecimal perPoint = transAmount.multiply(pointRate).multiply(new BigDecimal(PosConstant.PER_POINT_RATE));
            //积分金额
            BigDecimal entPoint = transAmount.multiply(pointRate).setScale(2,RoundingMode.UP);
            
            qr.setSourceTransDate(transTime);
            qr.setSourceTransAmount(String.valueOf(transAmount));	
            qr.setTransAmount(String.valueOf(transAmount));	
            qr.setPointRate(String.valueOf(pointRate));
            qr.setOperNo(reqParam.getOperNo());
            qr.setSourceBatchNo(qrCodeTrans.getPosBatchNo());
            //二维码单据未支付,原始交易号和个人积分为空
            qr.setSourceTransNo(StringUtils.leftPad("",12,PosConstant.ZERO_CHAR));
            qr.setEntPoint(CommonUtil.formatMoney(entPoint.doubleValue()));
            qr.setPerPoint(CommonUtil.formatMoney(perPoint.doubleValue()));

            //qr.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());//终端类型码            
            qr.setTermTypeCode(StringUtils.leftPad(PosConstant.TRANS_TYPE_POINT,4,PosConstant.ZERO_CHAR)); 
            qr.setTermTradeCode(reqParam.getTermTradeCode());//终端交易码
            qr.setPayStatus("0"); //支付结果 //支付状态  0失败 1成功 2非支付类业务 //
            //start--added by liuzh on 2016-06-27
            VersionAdapter versionAdapter = new VersionAdapter(cmd.getPartVersion());
            versionAdapter.build(qr, qrCodeTrans);
            //end--added by liuzh on 2016-06-27
            
            SystemLog.debug("SinglePointOrderSearchAction", "action()", "qr:" + JSON.toJSONString(qr));
		}

		//end -- added by liuzh on 2016-04-20
		
        //填充48域  用法八 交易流水 M
        if (null != qr) {
        	list.add(new BitMap(48, reqId, qr, cmd.getPartVersion()));
        } else { 
        	if(!"".equals(transNo)) { 
        		cmd.setRespCode(PosRespCode.NO_POS_ORDER);
        	}else{
        		cmd.setRespCode(PosRespCode.QR_BILL_NO_TRADE);
        	}
        }
        
        return cmd;
        
    }

}
