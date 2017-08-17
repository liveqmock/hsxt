/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsec.external.bean.OrderResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosDateUtil;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.PointRecordResult;
import com.gy.hsxt.ps.bean.QueryResult;




/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd48 
 * @Description: 48域  bcd LLL 附加数据  应答：批结算结算总额 批上送结束总数 积分撤单积分比例积分金额 交易明细 积分明细    新版本新增互生订单列表查询和余额查询
 *  请求:积分交易积分比例积分金额  批结算结算总额 批上送明细   上传参数    新版本新增互生订单支付
 *  2015/05/25 为“互生订单x支付冲正”新增了48域和63.2域的功能。
 *  耿炼：之前全部的冲正，都没有用过49域。华工调查后，给出结论：49域，没有用处。
 *  华工：39域的冲正原因码，跟这些无关。
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:06:43 
 * @version V1.0
 */
public class Fd48 extends Afd {
	/**
	 * 终端流水号长度 n6
	 */
    private static final int POSRUNCODELEN = 6;
    /**
     * pos中心流水号长度 n12
     */
    private static final int ORIGINNOLEN = 12;
    /**
     * 交易类型码(终端类型码？)长度 n4
     */
    private static final int TERMTYPECODELEN = 4;
    /**
     * 交易处理码长度
     */
    private static final int TERMTRADECODELEN = 6;
    /**
     * 卡号长度 (卡号右对齐，长度为奇数时需要左补0) n11
     */
    private static final int CARDNOLEN = 11;
    /**
     * 交易金额长度 n12
     */
    private static final int MONEYLEN = 12;
    /**
     * 积分比例长度 n4 
     */
    private static final int POINTRATELEN = 4;

    //报文实际字节长度
    private static final int RATE_PACK_BYTE_LEN = 2;
    //报文实际字节长度
    private static final int MONEY_PACK_BYTE_LEN = 6;
    //start--added by liuzh on 2016-06-22
    //抵扣券张数的报文实际字节长度
    private static final int DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN = 3;
    //支付方式长度(01互生币支付，02网银支付，03现金支付)
    private static final int TRANS_WAY_PACKLEN = 2;
    //end--added by liuzh on 2016-06-22
    
    /**定金业务交易流水号 使用pos中心生成的12位交易号*/
    private static final int EARNEST_SEQ_BYTE_LEN = 6;
    //协议posNo长度
    private static final int POSNO_PACKLEN = 8;
    private static final int TERMTYPECODE_PACKLEN = 4;
    //新版本
    private static final int N_OPERNO_PACKLEN = 4;
    //老版本
    private static final int OPERNO_PACKLEN = 3;
    //最大长度
    private static final int HSORDER_PACKLEN = 17;
    private static final int MONEY_PACKLEN = 12;
    private static final int COUNTLEN = 3;

	//不区分新老版本，按长度判断，老版本一定没有原始币种金额，新版本他币或互生币消费一定有，本币没有
	@Override
	public Object doRequestProcess(String messageId, byte[] data,byte[] pversion) throws Exception {
		SystemLog.debug("Fd48", "doRequestProcess(s,b,b)", "entering method");
		
		//start--added by liuzh on 2016-05-23
		//SystemLog.debug("Fd48", "doRequestProcess(s,b,b)", "messageId:" + messageId + ",data hex:" + Hex.encodeHexString(data));		
		//end--added by liuzh on 2016-05-23
		
		if (data != null && data.length > 0) {
			//kend 根据长度判断业务类型？放着messageId不用？怎么想的？？？？
			
			//老版本或新版本本币支付。无原始币种金额， 积分交易本币支付，比例+积分数
			if (data.length == RATE_PACK_BYTE_LEN+MONEY_PACK_BYTE_LEN) {
				byte[] rateByte = new byte[RATE_PACK_BYTE_LEN];
				System.arraycopy(data, 0, rateByte, 0, RATE_PACK_BYTE_LEN);
				String rateStr = Hex.encodeHexString(rateByte);
				BigDecimal rate = CommonUtil.changePackDataToRate(rateStr);
				
				int count = RATE_PACK_BYTE_LEN;
				
				byte[] assureByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, assureByte, 0, MONEY_PACK_BYTE_LEN);
				String assureStr = Hex.encodeHexString(assureByte);
				BigDecimal assure = CommonUtil.changePackDataToMoney(assureStr);
				
				return new Poi48(rate, assure);
				
			//1 积分交易他币支付；或 2 互生币支付 ， 比例+积分数+互生币计的交易额/（积分交易）外币金额
			}else if(data.length == RATE_PACK_BYTE_LEN+MONEY_PACK_BYTE_LEN+MONEY_PACK_BYTE_LEN){
				byte[] rateByte = new byte[RATE_PACK_BYTE_LEN];
				System.arraycopy(data, 0, rateByte, 0, RATE_PACK_BYTE_LEN);
				String rateStr = Hex.encodeHexString(rateByte);
				BigDecimal rate = CommonUtil.changePackDataToRate(rateStr);
				int count = RATE_PACK_BYTE_LEN;
				
				byte[] assureByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, assureByte, 0, MONEY_PACK_BYTE_LEN);
				String assureStr = Hex.encodeHexString(assureByte);
				BigDecimal assure = CommonUtil.changePackDataToMoney(assureStr);
				count += MONEY_PACK_BYTE_LEN;
				
				byte[] cashAmountByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, cashAmountByte, 0, MONEY_PACK_BYTE_LEN);
				String cashAmountStr = Hex.encodeHexString(cashAmountByte);
				BigDecimal cashAmount = CommonUtil.changePackDataToMoney(cashAmountStr);

				return new Poi48(rate, assure, cashAmount);
				
			//pos机3.0增加定金业务，只存放互生币计量的定金金额       kend
			}else if(data.length == MONEY_PACK_BYTE_LEN){
				byte[] hsbAmountByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, 0, hsbAmountByte, 0, MONEY_PACK_BYTE_LEN);
				String hsbAmountStr = Hex.encodeHexString(hsbAmountByte);
				BigDecimal cashAmount = CommonUtil.changePackDataToMoney(hsbAmountStr);
				
				return new Poi48(cashAmount);
				
			//pos机3.0增加定金结算业务，存放积分比例+积分额+互生币计量的交易额+定金交易序号    kend
			}else if(data.length == RATE_PACK_BYTE_LEN+MONEY_PACK_BYTE_LEN+
						MONEY_PACK_BYTE_LEN+EARNEST_SEQ_BYTE_LEN){
				//积分比例
				byte[] rateByte = new byte[RATE_PACK_BYTE_LEN];
				System.arraycopy(data, 0, rateByte, 0, RATE_PACK_BYTE_LEN);
				String rateStr = Hex.encodeHexString(rateByte);
				BigDecimal rate = CommonUtil.changePackDataToRate(rateStr);
				int count = RATE_PACK_BYTE_LEN;
				//企业承兑积分
				byte[] assureByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, assureByte, 0, MONEY_PACK_BYTE_LEN);
				String assureStr = Hex.encodeHexString(assureByte);
				BigDecimal assure = CommonUtil.changePackDataToMoney(assureStr);
				count += MONEY_PACK_BYTE_LEN;
				//互生币计的交易金额
				byte[] hsbAmountByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, hsbAmountByte, 0, MONEY_PACK_BYTE_LEN);
				String hsbAmountStr = Hex.encodeHexString(hsbAmountByte);
				BigDecimal hsbAmount = CommonUtil.changePackDataToMoney(hsbAmountStr);
				count += MONEY_PACK_BYTE_LEN;
				//定金交易序号
				byte[] earnestSeqB = new byte[EARNEST_SEQ_BYTE_LEN];
				System.arraycopy(data, count, earnestSeqB, 0, EARNEST_SEQ_BYTE_LEN);
				String earnestSeqStr = Hex.encodeHexString(earnestSeqB);
				
				return new Poi48(rate, assure, hsbAmount, earnestSeqStr);
					
			}else{
				//start--added by liuzh on 2016-06-22
				Object reqData = doRequestProcessVer301(messageId, data, pversion);
				if(reqData!=null){
					return reqData;
				}
				//end--added by liuzh on 2016-06-22
				
				SystemLog.info("Fd48", "doRequestProcess(s,b,b)","报文解析,48域数据:" + 
							Hex.encodeHexString(data));
				CommonUtil.checkState(true, "解析48域，数据长度异常!，无匹配业务类型。", PosRespCode.REQUEST_PACK_FORMAT);
			}
		} 
		
		return null;
	}
	
	@Override
	public Object doRequestProcess(String messageId, String data,byte[] pversion) throws Exception {
		SystemLog.debug("Fd48", "doRequestProcess(s,s,b)", "entering method");
		
		CommonUtil.checkState(data == null || data.length() <= 0, "48域,:" + data + "格式异常!", 
						PosRespCode.REQUEST_PACK_FORMAT);
				
				if (ReqMsg.BATCHSETTLE.getReqId().equals(messageId)) {
					int end = 0;
					String inDebitSum = data.substring(end,end+=MONEYLEN);
					String inDebitCount = data.substring(end,end+=COUNTLEN);
					String inCreditSum = data.substring(end,end+=MONEYLEN);
					String inCreditCount = data.substring(end,end+=COUNTLEN);
					String inPointSum = data.substring(end,end+=MONEYLEN);
					String inPointCount = data.substring(end,end+=COUNTLEN);
					String inPointCancelSum = data.substring(end,end+=MONEYLEN);
					String inPointCancelCount = data.substring(end,end+=COUNTLEN);
					
					String inHsbPaySum = data.substring(end,end+=MONEYLEN);
					String inHsbPayCount = data.substring(end,end+=COUNTLEN);
					String inHsbCancelSum = data.substring(end,end+=MONEYLEN);
					String inHsbCancelCount = data.substring(end,end+=COUNTLEN);
					String inHsbReturnSum = data.substring(end,end+=MONEYLEN);
					String inHsbReturnCount = data.substring(end,end+=COUNTLEN);
					String inHsOrderSum = data.substring(end,end+=MONEYLEN);
					String inHsOrderCount = data.substring(end,end+=COUNTLEN);
					String inHsbCReChargeSum = data.substring(end,end+=MONEYLEN);
					String inHsbCReChargeCount = data.substring(end,end+=COUNTLEN);
					String inHsbBReChargeSum = data.substring(end,end+=MONEYLEN);
					String inHsbBReChargeCount = data.substring(end,end+=COUNTLEN);
					
					end++;
					//输入跳过内卡对账应答代码
					String inDebitSum1 = data.substring(end,end+=MONEYLEN);
					String inDebitCount1 = data.substring(end,end+=COUNTLEN);
					String inCreditSum1 = data.substring(end,end+=MONEYLEN);
					String inCreditCount1 = data.substring(end,end+=COUNTLEN);
					String inPointSum1 = data.substring(end,end+=MONEYLEN);
					String inPointCount1 = data.substring(end,end+=COUNTLEN);
					String inCancelSum1 = data.substring(end,end+=MONEYLEN);
					String inCancelCount1 = data.substring(end,end+=COUNTLEN);
					
					String inHsbPaySum1 = data.substring(end,end+=MONEYLEN);
					String inHsbPayCount1 = data.substring(end,end+=COUNTLEN);
					String inHsbCancelSum1 = data.substring(end,end+=MONEYLEN);
					String inHsbCancelCount1 = data.substring(end,end+=COUNTLEN);
					String inHsbReturnSum1 = data.substring(end,end+=MONEYLEN);
					String inHsbReturnCount1 = data.substring(end,end+=COUNTLEN);
                   
					String inHsOrderSum1 = data.substring(end,end+=MONEYLEN);
					String inHsOrderCount1 = data.substring(end,end+=COUNTLEN);
                  
					
					
					
					BatchSettle bs = new BatchSettle(
						Integer.parseInt(inDebitCount),CommonUtil.changePackDataToMoney(inDebitSum),
						Integer.parseInt(inCreditCount),CommonUtil.changePackDataToMoney(inCreditSum),
						Integer.parseInt(inPointCount),CommonUtil.changePackDataToMoney(inPointSum),
						Integer.parseInt(inPointCancelCount),CommonUtil.changePackDataToMoney(inPointCancelSum),
						Integer.parseInt(inHsbPayCount),CommonUtil.changePackDataToMoney(inHsbPaySum),
						Integer.parseInt(inHsbCancelCount),CommonUtil.changePackDataToMoney(inHsbCancelSum),
						Integer.parseInt(inHsbReturnCount),CommonUtil.changePackDataToMoney(inHsbReturnSum),
						Integer.parseInt(inHsOrderCount),CommonUtil.changePackDataToMoney(inHsOrderSum),
						Integer.parseInt(inHsbCReChargeCount),CommonUtil.changePackDataToMoney(inHsbCReChargeSum),
						Integer.parseInt(inHsbBReChargeCount),CommonUtil.changePackDataToMoney(inHsbBReChargeSum),
						Integer.parseInt(inDebitCount1),CommonUtil.changePackDataToMoney(inDebitSum1),
						Integer.parseInt(inCreditCount1),CommonUtil.changePackDataToMoney(inCreditSum1),
						Integer.parseInt(inPointCount1),CommonUtil.changePackDataToMoney(inPointSum1),
						Integer.parseInt(inCancelCount1),CommonUtil.changePackDataToMoney(inCancelSum1),
						Integer.parseInt(inHsbPayCount1),CommonUtil.changePackDataToMoney(inHsbPaySum1),
						Integer.parseInt(inHsbCancelCount1),CommonUtil.changePackDataToMoney(inHsbCancelSum1),
						Integer.parseInt(inHsbReturnCount1),CommonUtil.changePackDataToMoney(inHsbReturnSum1),
						Integer.parseInt(inHsOrderCount1),CommonUtil.changePackDataToMoney(inHsOrderSum1));
					return bs;
							
				} else if (ReqMsg.UPLOADPARAM.getReqId().equals(messageId)) {
					
					String pointRateCountStr = data.substring(0, 2);//积分比例个数
					String pointRatesStr = data.substring(2);
					int pointRateCount = Integer.parseInt(pointRateCountStr);
					BigDecimal[] pointRates = new BigDecimal[pointRateCount];
					
					String rateStr;
					for (int i = 0; i < pointRateCount; i++) {
						
						rateStr = pointRatesStr.substring(i*4, 4*(i+1));
						pointRates[i] = CommonUtil.changePackDataToRate(rateStr);
					}
					SystemLog.debug("Pos", "UPLOADPARAM", "");
					/*
					log.debug("上传积分比例1:{0},2:{1},3:{2},4:{3},5:{4}",
							pointRates[0], pointRates[1], pointRates[2],
							pointRates[3], pointRates[4]);
					*/
					return new UploadParamPosIn(pointRateCount, pointRates);
					
				} else if (ReqMsg.BATCHUPLOAD.getReqId().equals(messageId)) {//交易记录批上传
				    BatchUploadPosIn batchUploadPosIn = new BatchUploadPosIn();
                    List<PointDetail> list = new LinkedList<PointDetail>();
                    PointDetail bud;
                    //本次上传笔数
                    String batchUploadCountStr = data.substring(0, 4);
                    //每笔详细内容
                    String details = data.substring(4);
                    SystemLog.debug("POS","","批上送明细count:"+batchUploadCountStr+",details:"+details);
                    int batchUploadCount = Integer.parseInt(batchUploadCountStr);
                    if (batchUploadCount > 0 && StringUtils.isNotEmpty(details)) {//有明细
                        data = details;
                        for (int i = 0; i < batchUploadCount; i++) {//每笔79
                            int endLen = 0;
                            bud = new PointDetail();
                            //pos终端流水号
                            String posRunCode = data.substring(endLen,endLen+=POSRUNCODELEN);//6
                            //pos中心流水号
                            String originNo = data.substring(endLen,endLen+=ORIGINNOLEN);//12
                            //交易类型码（终端类型码）60.1域内容 前面补充两个0
                            String termTypeCode = data.substring(endLen,endLen+=TERMTYPECODELEN);//可能没用 4
                            //交易处理码
                            String termTradeCode = data.substring(endLen, endLen+=TERMTRADECODELEN);//可能没用  6
                            //卡号
                            String cardNo = data.substring(endLen, endLen+=CARDNOLEN);//11
                            //交易金额
                            String tradeMoneyStr = data.substring(endLen, endLen+=MONEYLEN);//12
                            //积分比例
                            String pointRateStr = data.substring(endLen, endLen+=POINTRATELEN);//4
                            //积分金额
                            String pointMoneyStr = data.substring(endLen, endLen+=MONEYLEN);//12
                            //积分数
                            String pointValueStr = data.substring(endLen, endLen+=MONEYLEN);//12
                            bud.setTermRunCode(posRunCode);
                            bud.setOriginNo(originNo);
                            bud.setTermTradeCode(termTradeCode);
                            bud.setTermTypeCode(termTypeCode);
                            bud.setCardNo(cardNo);
                            bud.setOrderAmount(CommonUtil.changePackDataToMoney(tradeMoneyStr));
                            bud.setPointRate(CommonUtil.changePackDataToRate(pointRateStr));
                            bud.setAssureOutValue(CommonUtil.changePackDataToMoney(pointMoneyStr));
                            bud.setPointsValue(CommonUtil.changePackDataToMoney(pointValueStr));
/*
                            log.debug("批上送每条明细,posRunCode:{0},originNo:{1},termTypeCode:{2}"+//
                                    ",termTradeCode:{3},cardNo:{4},tradeMoneyStr:{5}"+//
                                    ",pointRateStr:{6},pointMoneyStr:{7},pointValueStr:{8}",posRunCode,//
                                    originNo,termTypeCode,termTradeCode,cardNo,tradeMoneyStr,pointRateStr,//
                                    pointMoneyStr,pointValueStr);*/
                            list.add(bud);

                            data = data.substring(endLen);
                        }
                        batchUploadPosIn.setBatchUploadDetails(list);
                        
                    }else{
                        SystemLog.debug("POS","报文解析","批上送结束："+batchUploadCount);
                    }
                        
                    batchUploadPosIn.setBatchUploadCount(batchUploadCount);
                    return batchUploadPosIn;
				
				}else if (ReqMsg.HSORDERCASHPAY.getReqId().equals(messageId) || 
						ReqMsg.HSORDERHSBPAY.getReqId().equals(messageId)) {
					
					//kend test
					SystemLog.debug("Fd48", "", "---------------------------------互生订单支付 48域 data = " +
								data + ";长 = " + data.length());
					
					if (data.length() == PosConstant.HSORDER_MAXPACKLEN) {
						String originNo = data.substring(PosConstant.HSORDER_MAXPACKLEN-HSORDER_PACKLEN, 
								PosConstant.HSORDER_MAXPACKLEN);
						return new PosTransNote(originNo);
					} else if(data.length() == PosConstant.HSORDER_MAXPACKLEN+MONEY_PACKLEN){//互生支付一定有原始币种金额
						String originNo = data.substring(PosConstant.HSORDER_MAXPACKLEN-HSORDER_PACKLEN, 
																PosConstant.HSORDER_MAXPACKLEN);
						String cashAmountStr = data.substring(PosConstant.HSORDER_MAXPACKLEN);
						BigDecimal cashAmount = CommonUtil.changePackDataToMoney(cashAmountStr);
						return new PosTransNote(originNo, cashAmount);
					}else{
						throw new PosException(PosRespCode.REQUEST_PACK_FORMAT,"互生订单数据:" + data + "格式异常！");
					}
				}else if(ReqMsg.HSBPROXYRECHARGE.getReqId().equals(messageId)//代兑 选外币
						|| ReqMsg.HSBENTRECHARGE.getReqId().equals(messageId)){//兑换互生币
					return CommonUtil.changePackDataToMoney(data.substring(0, 12));
				}else if(ReqMsg.HSORDERSEARCH.getReqId().equals(messageId)){
					return new PosTransNote(data.substring(PosConstant.HSORDER_MAXPACKLEN-HSORDER_PACKLEN, 
												PosConstant.HSORDER_MAXPACKLEN));
				}else if(ReqMsg.EARNESTCANCEL.getReqId().equals(messageId)){//定金撤单
					return new PosTransNote(data);
				}
				//start--added by liuzh on 2016-06-23
				else if(ReqMsg.POINTORDERSCANCELSEARCH.getReqId().equals(messageId)
						|| ReqMsg.POINTORDERSBACKSEARCH.getReqId().equals(messageId)) {
					//可撤单积分记录查询或可退货积分记录查询. pos3.01版本开始支持
					return new PointOrdersSearchReq(data);					
				}
				//end--added by liuzh on 2016-06-23
				else
					CommonUtil.checkState(true, "解析48域，业务类型不匹配。当前类型：" + messageId, 
															PosRespCode.REQUEST_PACK_PARAM_ERR);
			
			return null;
		
	}
	
	@Override
	public String doResponseProcess(String messageId, Object data,byte[] pversion) throws PosException {
		// String returnStr;
		int capLen = 122;
		StringBuilder buf = new StringBuilder(capLen);
		//新版本
		if(!Arrays.equals(pversion, PosConstant.POSOLDVERSION)){
		  //批结算
			if (ReqMsg.BATCHSETTLE.getReqId().equals(messageId)) {
				BatchSettle bs = (BatchSettle) data;
				//内卡
				String settleResult = bs.getSettleResult();
				buf.append(CommonUtil.fill0InMoney(bs.getInDebitSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInDebitCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInCreditSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCreditCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInPointSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInPointCancelSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCancelCount()), 3, PosConstant.ZERO_CHAR));
				
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbPaySum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbPayCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbCancelSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbCancelCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbReturnSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbReturnCount()), 3, PosConstant.ZERO_CHAR));
       
				buf.append(CommonUtil.fill0InMoney(bs.getInHsOrderSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsOrderCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbCReChargeSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbCReChargeCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbBReChargeSum()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbBReChargeCount()), 3, PosConstant.ZERO_CHAR));
				buf.append(settleResult);
	
				//外卡
				String settleResult1 = bs.getSettleResult_wild();
				buf.append(CommonUtil.fill0InMoney(bs.getInDebitSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInDebitCount_wild()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInCreditSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCreditCount_wild()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInPointSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCount_wild()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInCancelSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCancelCount_wild()), 3, PosConstant.ZERO_CHAR));
				
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbPaySum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbPayCount_wild()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbCancelSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbCancelCount_wild()), 3, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(bs.getInHsbReturnSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsbReturnCount_wild()), 3, PosConstant.ZERO_CHAR));

				buf.append(CommonUtil.fill0InMoney(bs.getInHsOrderSum_wild()));
				buf.append(StringUtils.leftPad(String.valueOf(bs.getInHsOrderCount_wild()), 3, PosConstant.ZERO_CHAR));
				buf.append(settleResult1);
	
			} else if (ReqMsg.BATCHUPLOAD.getReqId().equals(messageId)) {//批上送
				buf.append(StringUtils.leftPad(String.valueOf(data), 4, PosConstant.ZERO_CHAR));
			} else if (ReqMsg.POINTCANCLE.getReqId().equals(messageId)
					 || ReqMsg.HSBPAYCANCLE.getReqId().equals(messageId)
					 || ReqMsg.HSBPAYRETURN.getReqId().equals(messageId)) {//积分撤单/互生币撤单/互生币退货/互生钱包撤单/互生钱包退货
				Poi48 poi = (Poi48) data;
				BigDecimal pointRate = poi.getRate();
				BigDecimal pointMoney = poi.getAmt();
				BigDecimal cashAmount = poi.getHsbAmount();
				buf.append(CommonUtil.fill0InRate(pointRate));
				buf.append(CommonUtil.fill0InMoney(pointMoney));
				if(cashAmount != null){
					buf.append(CommonUtil.fill0InMoney(cashAmount));
				}
				SystemLog.info("POS","应答报文","应答,撤单或退货pointRate:"+pointRate+",pointMoney:"+pointMoney+",cashAmount:"+cashAmount);
			} else if (ReqMsg.POINTDAYSEARCH.getReqId().equals(messageId)) {//当日查询
//				PointDaySearchPosOut pointDetailVo = (PointDaySearchPosOut) data;
//				List<PointDetail> pointDetails = pointDetailVo.getPointDetails();
//	
//				buf.append(pointDetailVo.getCardNo());//注意卡号为空
//				buf.append(StringUtils.leftPad(String.valueOf(pointDetailVo.getTradeCount()), 2, PosConstant.ZERO_CHAR));
				
				if (null != null) {//null != pointDetails
//					log.debug("应答,当日查询数据列表");
//					for (PointDetail pointDetail : pointDetails) {
//						log.debug("TermTypeCode:{0},TermTradeCode:{1},TermRunCode:{2},OrderDate:{3}"+//
//						",OriginNo:{4},TermReverseCode:{5},PosNo:{6},OrderAmount:{7},PointRate:{8}"+//
//						",AssureOutValue:{9},OperNo:{10},PointsValue:{11},BatchNo:{12},HsOrder:{13}"//
//						,pointDetail.getTermTypeCode(),pointDetail.getTermTradeCode(),//
//						pointDetail.getTermRunCode(),pointDetail.getOrderDate(),pointDetail.getOriginNo(),//
//						pointDetail.getTermReverseCode(),pointDetail.getPosNo(),pointDetail.getOrderAmount(),//
//						pointDetail.getPointRate(),pointDetail.getAssureOutValue(),pointDetail.getOperNo(),//
//						pointDetail.getPointsValue(),pointDetail.getBatchNo(),pointDetail.getHsOrder());//
//						this.pointDetailNewAppend(pointDetail, buf);
//					}
				}
			
			//pos交易单笔查询 
			} else if (ReqMsg.POINTORDERSEARCH.getReqId().equals(messageId)) {
			    QueryResult pointDetail = (QueryResult) data;
				String cardNo = pointDetail.getPerResNo();
				if(StringUtils.isBlank(cardNo)){//企业兑换互生币，无消费者互生号。这段不严谨 kend
					cardNo = "00000000000";//StringUtils.repeat(PosConstant.ZERO_CHAR, 11);
				}
				buf.append(cardNo);
				/*
				log.debug("应答,单笔查询数据");
				log.debug("TermTypeCode:{0},TermTradeCode:{1},TermRunCode:{2},OrderDate:{3}"+//
				",OriginNo:{4},TermReverseCode:{5},PosNo:{6},OrderAmount:{7},PointRate:{8}"+//
				",AssureOutValue:{9},OperNo:{10},PointsValue:{11},BatchNo:{12},HsOrder:{13},CardNo:{14}"//
				,pointDetail.getTermTypeCode(),pointDetail.getTermTradeCode(),/
				pointDetail.getTermRunCode(),pointDetail.getSourceTransDate(),pointDetail.getSourceTransNo(),//
				pointDetail.getReturnReason(),pointDetail.getEquipmentNo(),pointDetail.getSourceTransAmount(),//
				pointDetail.getPointRate(),pointDetail.getTransAmount(),pointDetail.getOperNo(),//
				pointDetail.getPerPoint(),pointDetail.getBatchNo(),pointDetail.getTermRunCode(),cardNo);//
				*/
				this.pointDetailNewAppend(pointDetail, buf, pversion);
			}else if (ReqMsg.ASSURESEARCH.getReqId().equals(messageId)) {
				Poi48 poi = (Poi48) data;
				BigDecimal amt = poi.getAmt();
				if(amt.compareTo(new BigDecimal(0))>=0){
					buf.append("00");//正
				}else{
					buf.append("01");//负
				}				
				buf.append(CommonUtil.fill0InMoney(amt.abs()));
				buf.append(CommonUtil.fill0InMoney(poi.getHsbAmount().abs()));
				SystemLog.info("Pos","应答报文","应答,企业预付金额:" + 
						amt.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+",企业积分:" + 
						poi.getHsbAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}else if (ReqMsg.HSORDERLISTSEARCH.getReqId().equals(messageId)) {
			    @SuppressWarnings("unchecked")
				List<PosTransNote> hsOrders = (List<PosTransNote>) data;//订单数9
			    buf.append(StringUtils.leftPad(String.valueOf(hsOrders.size()), 2, PosConstant.ZERO_CHAR));
				SystemLog.debug("Pos","应答报文","应答,互生订单查询数据 订单数:"+hsOrders.size());
				for (PosTransNote hsOrder : hsOrders) {
					buf.append(StringUtils.leftPad(hsOrder.getPosBizSeq(), PosConstant.HSORDER_MAXPACKLEN, PosConstant.ZERO_CHAR));
					buf.append(CommonUtil.fill0InMoney(hsOrder.getTransAmount()));
				}
				
			}else if (ReqMsg.HSORDERSEARCH.getReqId().equals(messageId)) {
				
//			    HsOrder hsOrder = (HsOrder) data;
			    OrderResult hsOrder = (OrderResult)data;
				buf.append("01");//单笔返回一个
				buf.append(StringUtils.leftPad(hsOrder.getOrderId(), PosConstant.HSORDER_MAXPACKLEN, PosConstant.ZERO_CHAR));
				buf.append(CommonUtil.fill0InMoney(hsOrder.getOrderAmount()));
				SystemLog.debug("Pos","应答报文","应答,互生订单查询数据 OriginNo:"+hsOrder.getOrderId()+",OrderAmount:"+hsOrder.getOrderAmount());
				
			}else if (ReqMsg.HSORDERCASHPAY.getReqId().equals(messageId)
					 || ReqMsg.HSORDERHSBPAY.getReqId().equals(messageId)
                     // || ReqMsg.HSORDERHSWPAY.getId().equals(messageId)
					) {
				Poi48 poi = (Poi48) data;
				BigDecimal amt = poi.getAmt();//积分金额
				buf.append(CommonUtil.fill0InMoney(amt));
//				log.info("应答,订单积分余额:{0}",amt);
			}else if(ReqMsg.HSBPROXYRECHARGE.getReqId().equals(messageId)){
				Poi48 poi = (Poi48) data;
				buf.append(CommonUtil.fill0InMoney(poi.getHsbAmount()));//互生币代兑金额
				buf.append(CommonUtil.fill0InMoney(poi.getSurplusHsbAmount()));//互生币余额
//				log.info("应答,代兑充值互生币:{0},互生币余额:{1}",CommonUtil.formatMoney(hsbRecMoney), CommonUtil.formatMoney(hsbTotal));
			}else if(ReqMsg.HSBENTRECHARGE.getReqId().equals(messageId)){
				Poi48 poi = (Poi48) data;
				buf.append(CommonUtil.fill0InMoney(poi.getSurplusHsbAmount()));//互生币余额
//				log.info("应答,兑换互生币余额:{0}",hsbTotal);
			}else if(ReqMsg.HSORDERACCTCASHPAYREVERSE.getReqId().equals(messageId)){
				Poi48 poi = (Poi48) data;
				BigDecimal assureOutValue = poi.getAmt();//互生币余额
				buf.append(CommonUtil.fill0InMoney(assureOutValue));
//				log.info("应答,互生币冲正预付积分:{0}",CommonUtil.formatMoney(assureOutValue));
			}else if(ReqMsg.HSORDERACCTHSBPAYREVERSE.getReqId().equals(messageId)){
				Poi48 poi = (Poi48) data;
				BigDecimal assureOutValue = poi.getAmt();//互生币余额
				buf.append(CommonUtil.fill0InMoney(assureOutValue));
//				log.info("应答,互生币冲正预付积分:{0}",CommonUtil.formatMoney(assureOutValue));
				
			}else if (ReqMsg.EARNESTSEARCH.getReqId().equals(messageId)) {//定金查询  用法十五    kend
			    @SuppressWarnings("unchecked")
				List<PosTransNote> earnestList = (List<PosTransNote>) data;
				buf.append(StringUtils.leftPad(String.valueOf(earnestList.size()), 2, PosConstant.ZERO_CHAR));
				SystemLog.debug("","","定金查询，应答消息中的记录数:"+earnestList.size());//kend test
				for (PosTransNote earnest : earnestList) {
					buf.append(StringUtils.leftPad(earnest.getPosBizSeq(), PosConstant.POSSERVER_SEQ, PosConstant.ZERO_CHAR));
					buf.append(CommonUtil.fill0InMoney(earnest.getTransAmount()));
					buf.append(earnest.getTransDateTime());
				}
			//定金撤销
			}else if (ReqMsg.EARNESTCANCEL.getReqId().equals(messageId)) {
				Poi48 poi = (Poi48) data;
				BigDecimal hsbAmt = poi.getHsbAmount();
				buf.append(CommonUtil.fill0InMoney(hsbAmt));
			//定金结算	
			}else if (ReqMsg.EARNESTSETTLEACC.getReqId().equals(messageId)) {
				BigDecimal earnestAmt = (BigDecimal)data;
				buf.append(CommonUtil.fill0InMoney(earnestAmt));
			}
			//start--added by liuzh on 2016-06-23
			else if(ReqMsg.POINTORDERSCANCELSEARCH.getReqId().equals(messageId)
					|| ReqMsg.POINTORDERSBACKSEARCH.getReqId().equals(messageId)) {
				//可撤单积分记录查询或可退货积分记录查询. pos3.01版本开始支持
				@SuppressWarnings("unchecked")
				List<PointRecordResult> pointOrders = (List<PointRecordResult>) data;
				StringBuilder ordersBuf = queryPointOrders(pointOrders,pversion);
				buf.append(ordersBuf);
				/*
			    buf.append(StringUtils.leftPad(String.valueOf(pointOrders.size()), 2, PosConstant.ZERO_CHAR));
				SystemLog.debug("Pos","应答报文","应答,积分交易查询数据  记录数:" + pointOrders.size());
				String orderAmount ="0";
				for (PointRecordResult pointOrder : pointOrders) {
					buf.append(StringUtils.leftPad("0", TERMTYPECODE_PACKLEN, PosConstant.ZERO_CHAR));// 终端类型码	
					buf.append(PosDateUtil.getYmdhms(pointOrder.getSourceTransDate())); //交易时间
					buf.append(pointOrder.getSourceTransNo());  //POS中心参考号 
					if(StringUtils.isEmpty(pointOrder.getOrderAmount())) {
						orderAmount = pointOrder.getSourceTransAmount();
					}else{
						orderAmount = pointOrder.getOrderAmount();
					}
					buf.append(CommonUtil.fill0InMoney(new BigDecimal(orderAmount)));  //消费金额  
					buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointOrder.getSourceTransAmount())));  //实付金额
					buf.append(CommonUtil.fill0InRate(new BigDecimal(pointOrder.getPointRate())));  //积分比例
					buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointOrder.getEntPoint())));   //积分金额
					buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointOrder.getPerPoint())));   //分配积分数
				}	
				*/				
			}
			//end--added by liuzh on 2016-06-23
			else {
				CommonUtil.checkState(true,"Fd48:doResponseProcess(s,o,b),应答未知消息ID:" + messageId, 
								PosRespCode.POS_CENTER_FAIL);
			}
		}else{
			//老版本
			//start--added by liuzh on 2016-06-23 优化代码,把原有对 v1版本代码处理挪到另一函数
			buf = doResponseProcessVer10(messageId, data, pversion);
			//end--added by liuzh on 2016-06-23
			
			//following code commented by liuzh on 2016-06-23
			//start--原来代码 commented by liuzh on 2016-06-23 
			/*
			if (ReqMsg.BATCHSETTLE.getReqId().equals(messageId)) {//批结算
//				BatSettle bs = (BatSettle) data;
//				//内卡
//				buf.append(CommonUtil.fill0InMoney(bs.getInDebitSum()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInDebitCount()), 3, PosConstant.ZERO_CHAR));
//				buf.append(CommonUtil.fill0InMoney(bs.getInCreditSum()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCreditCount()), 3, PosConstant.ZERO_CHAR));
//				buf.append(CommonUtil.fill0InMoney(bs.getInPointSum()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCount()), 3, PosConstant.ZERO_CHAR));
//				buf.append(CommonUtil.fill0InMoney(bs.getInCancelSum()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCancelCount()), 3, PosConstant.ZERO_CHAR));
//				buf.append(bs.getSettleResult());//内卡结果
//	
//				//外卡
//				buf.append(CommonUtil.fill0InMoney(bs.getInDebitSum_wild()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInDebitCount_wild()), 3, PosConstant.ZERO_CHAR));
//				buf.append(CommonUtil.fill0InMoney(bs.getInCreditSum_wild()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCreditCount_wild()), 3, PosConstant.ZERO_CHAR));
//				buf.append(CommonUtil.fill0InMoney(bs.getInPointSum_wild()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCount_wild()), 3, PosConstant.ZERO_CHAR));
//				buf.append(CommonUtil.fill0InMoney(bs.getInCancelSum_wild()));
//				buf.append(StringUtils.leftPad(String.valueOf(bs.getInCancelCount_wild()), 3, PosConstant.ZERO_CHAR));
//				buf.append(bs.getSettleResult_wild());//外卡结果
//	
			} else if (ReqMsg.BATCHUPLOAD.getReqId().equals(messageId)) {//批上送
				// returnStr = String.valueOf(data);
				// returnStr = StringUtils.leftPad(returnStr, 4, PosConstant.ZERO_CHAR);
			} else if (ReqMsg.POINTCANCLE.getReqId().equals(messageId)) {//积分撤单
//				Poi48 poi = (Poi48) data;
//				double pointRate = poi.getRate();
//				double pointMoney = poi.getAmt();
//				buf.append(CommonUtil.fill0InRate(pointRate));
//				buf.append(CommonUtil.fill0InMoney(pointMoney));
//				log.info("应答,撤单pointRate:{0},pointMoney:{1}",pointRate,pointMoney);
			} else if (ReqMsg.POINTDAYSEARCH.getReqId().equals(messageId)) {//当日查询
//				PointDaySearchPosOut pointDetailVo = (PointDaySearchPosOut) data;
//				List<PointDetail> pointDetails = pointDetailVo.getPointDetails();
//	
//				buf.append(pointDetailVo.getCardNo());//注意卡号为空
//				buf.append(StringUtils.leftPad(String.valueOf(pointDetailVo.getTradeCount()), 2, PosConstant.ZERO_CHAR));
//	
//				if (pointDetails != null) {
//					log.debug("应答,当日查询数据列表");
//					for (PointDetail pointDetail : pointDetails) {
//						log.debug("TermTypeCode:{0},TermTradeCode:{1},TermRunCode:{2},OrderDate:{3}"+//
//						",OriginNo:{4},TermReverseCode:{5},PosNo:{6},OrderAmount:{7},PointRate:{8}"+//
//						",AssureOutValue:{9},OperNo:{10},PointsValue:{11},BatchNo:{12}"//
//						,pointDetail.getTermTypeCode(),pointDetail.getTermTradeCode(),//
//						pointDetail.getTermRunCode(),pointDetail.getOrderDate(),pointDetail.getOriginNo(),//
//						pointDetail.getTermReverseCode(),pointDetail.getPosNo(),pointDetail.getOrderAmount(),//
//						pointDetail.getPointRate(),pointDetail.getAssureOutValue(),pointDetail.getOperNo(),//
//						pointDetail.getPointsValue(),pointDetail.getBatchNo());//
//						this.pointDetailAppend(pointDetail, buf);
//					}
//				}
			} else if (ReqMsg.POINTORDERSEARCH.getReqId().equals(messageId)) {//单笔查询
			    QueryResult pointDetail = (QueryResult) data;
				String cardNo = pointDetail.getPerResNo();
				if(StringUtils.isBlank(cardNo)){
					cardNo = StringUtils.repeat(PosConstant.ZERO_CHAR, 11);
				}
				buf.append(cardNo);
				
				this.pointDetailAppend(pointDetail, buf);
			}else {
//				throw new PosException(PosRespCode.POS_CENTER_FAIL,"应答未知消息ID:"+messageId);
			}
			*///end--原来代码 commented by liuzh on 2016-06-23 
			
		}
		
		
		return buf.toString();
	}
		
	/**
	 * added by liuzh on 2016-6-23 原函数太长,细分
	 * 处理
	 * @param messageId
	 * @param data
	 * @param pversion
	 * @return
	 * @throws PosException
	 */
	private StringBuilder doResponseProcessVer10(String messageId, Object data,byte[] pversion) throws PosException {
		
		int capLen = 122;
		StringBuilder buf = new StringBuilder(capLen);
		//老版本
		if (ReqMsg.BATCHSETTLE.getReqId().equals(messageId)) {//批结算
//			BatSettle bs = (BatSettle) data;
//			//内卡
//			buf.append(CommonUtil.fill0InMoney(bs.getInDebitSum()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInDebitCount()), 3, PosConstant.ZERO_CHAR));
//			buf.append(CommonUtil.fill0InMoney(bs.getInCreditSum()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInCreditCount()), 3, PosConstant.ZERO_CHAR));
//			buf.append(CommonUtil.fill0InMoney(bs.getInPointSum()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCount()), 3, PosConstant.ZERO_CHAR));
//			buf.append(CommonUtil.fill0InMoney(bs.getInCancelSum()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInCancelCount()), 3, PosConstant.ZERO_CHAR));
//			buf.append(bs.getSettleResult());//内卡结果
//
//			//外卡
//			buf.append(CommonUtil.fill0InMoney(bs.getInDebitSum_wild()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInDebitCount_wild()), 3, PosConstant.ZERO_CHAR));
//			buf.append(CommonUtil.fill0InMoney(bs.getInCreditSum_wild()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInCreditCount_wild()), 3, PosConstant.ZERO_CHAR));
//			buf.append(CommonUtil.fill0InMoney(bs.getInPointSum_wild()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInPointCount_wild()), 3, PosConstant.ZERO_CHAR));
//			buf.append(CommonUtil.fill0InMoney(bs.getInCancelSum_wild()));
//			buf.append(StringUtils.leftPad(String.valueOf(bs.getInCancelCount_wild()), 3, PosConstant.ZERO_CHAR));
//			buf.append(bs.getSettleResult_wild());//外卡结果
//
		} else if (ReqMsg.BATCHUPLOAD.getReqId().equals(messageId)) {//批上送
			// returnStr = String.valueOf(data);
			// returnStr = StringUtils.leftPad(returnStr, 4, PosConstant.ZERO_CHAR);
		} else if (ReqMsg.POINTCANCLE.getReqId().equals(messageId)) {//积分撤单
//			Poi48 poi = (Poi48) data;
//			double pointRate = poi.getRate();
//			double pointMoney = poi.getAmt();
//			buf.append(CommonUtil.fill0InRate(pointRate));
//			buf.append(CommonUtil.fill0InMoney(pointMoney));
//			log.info("应答,撤单pointRate:{0},pointMoney:{1}",pointRate,pointMoney);
		} else if (ReqMsg.POINTDAYSEARCH.getReqId().equals(messageId)) {//当日查询
//			PointDaySearchPosOut pointDetailVo = (PointDaySearchPosOut) data;
//			List<PointDetail> pointDetails = pointDetailVo.getPointDetails();
//
//			buf.append(pointDetailVo.getCardNo());//注意卡号为空
//			buf.append(StringUtils.leftPad(String.valueOf(pointDetailVo.getTradeCount()), 2, PosConstant.ZERO_CHAR));
//
//			if (pointDetails != null) {
//				log.debug("应答,当日查询数据列表");
//				for (PointDetail pointDetail : pointDetails) {
//					log.debug("TermTypeCode:{0},TermTradeCode:{1},TermRunCode:{2},OrderDate:{3}"+//
//					",OriginNo:{4},TermReverseCode:{5},PosNo:{6},OrderAmount:{7},PointRate:{8}"+//
//					",AssureOutValue:{9},OperNo:{10},PointsValue:{11},BatchNo:{12}"//
//					,pointDetail.getTermTypeCode(),pointDetail.getTermTradeCode(),//
//					pointDetail.getTermRunCode(),pointDetail.getOrderDate(),pointDetail.getOriginNo(),//
//					pointDetail.getTermReverseCode(),pointDetail.getPosNo(),pointDetail.getOrderAmount(),//
//					pointDetail.getPointRate(),pointDetail.getAssureOutValue(),pointDetail.getOperNo(),//
//					pointDetail.getPointsValue(),pointDetail.getBatchNo());//
//					this.pointDetailAppend(pointDetail, buf);
//				}
//			}
		} else if (ReqMsg.POINTORDERSEARCH.getReqId().equals(messageId)) {//单笔查询
		    QueryResult pointDetail = (QueryResult) data;
			String cardNo = pointDetail.getPerResNo();
			if(StringUtils.isBlank(cardNo)){
				cardNo = StringUtils.repeat(PosConstant.ZERO_CHAR, 11);
			}
			buf.append(cardNo);
			
			this.pointDetailAppend(pointDetail, buf);
		}else {
//			throw new PosException(PosRespCode.POS_CENTER_FAIL,"应答未知消息ID:"+messageId);
		}
		return buf;			
	}
	
	//老版本
	private StringBuilder pointDetailAppend(QueryResult pointDetail, StringBuilder buf) {
		String termTypeCode = pointDetail.getTermTypeCode();
		if(StringUtils.isNotBlank(termTypeCode)){//老版本数据可能为空
			buf.append(StringUtils.leftPad(termTypeCode, TERMTYPECODE_PACKLEN, PosConstant.ZERO_CHAR));
            //}else{
            //    termTypeCode = "0000";
		}
		String termTradeCode = pointDetail.getTermTradeCode();
		if(StringUtils.isBlank(termTradeCode)){//老版本数据可能为空
			termTradeCode = "000000";
		}
		buf.append(termTradeCode);
		buf.append(pointDetail.getTermRunCode());
		buf.append(DateUtil.getFormatHms(Timestamp.valueOf(pointDetail.getSourceTransDate())));
		//原来的交易流水号
		buf.append(pointDetail.getSourceTransNo());
		String recode = pointDetail.getReturnReason();
		if(StringUtils.isBlank(recode)){//老版本数据可能为空
			recode = "00";//默认为成功
		}else{
			recode = StringUtils.right(recode, 2);
		}
		buf.append(recode);
		String posNo = StringUtils.right(pointDetail.getEquipmentNo(), 4);
		buf.append(StringUtils.leftPad(posNo, POSNO_PACKLEN, PosConstant.ZERO_CHAR));
		buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getSourceTransAmount())));
		buf.append(CommonUtil.fill0InRate(new BigDecimal(pointDetail.getPointRate())));
		buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getEntPoint())));
		String operNo = pointDetail.getOperNo();
		if(operNo.length() >=0 && operNo.length() <= 2){
			buf.append(StringUtils.leftPad(operNo, OPERNO_PACKLEN, PosConstant.ZERO_CHAR));
		}else if(operNo.length() == OPERNO_PACKLEN){
			buf.append(operNo);
		}else if(operNo.length() > OPERNO_PACKLEN){
			buf.append(StringUtils.right(operNo, 3));
		}
		buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getPerPoint())));
		buf.append(pointDetail.getBatchNo());
		return buf;
	}
	
	//3.0增加支付结果 48域用法八，不能影响2.0
	private StringBuilder pointDetailNewAppend(QueryResult pointDetail, StringBuilder buf, byte[] pversion) throws PosException {
		String termTypeCode = pointDetail.getTermTypeCode();
		//start--added by liuzh on 2016-07-05
		String termRunCode = pointDetail.getTermRunCode();
		//end--added by liuzh on 2016-07-05
		if(StringUtils.isNotBlank(termTypeCode))//老版本数据可能为空
			buf.append(StringUtils.leftPad(termTypeCode, TERMTYPECODE_PACKLEN, PosConstant.ZERO_CHAR));		
		String termTradeCode = pointDetail.getTermTradeCode();
		if(StringUtils.isBlank(termTradeCode))//老版本数据可能为空
			termTradeCode = "000000";
		buf.append(termTradeCode);		
		//start--modified by liuzh on 2016-07-05 
		//pos终端交易流水号 网上积分登记的交易流水号为空
		//buf.append(pointDetail.getTermRunCode());
		if(StringUtils.isBlank(termRunCode)) { 
			termRunCode = "000000";
		}
		buf.append(termRunCode);
		//end--modified by liuzh on 2016-07-05
		
		//start--modified on liuzh on 2016-07-02 单笔查询 交易时间长度 n6->n14 传 年月日时分秒
		//buf.append(DateUtil.getFormatHms(Timestamp.valueOf(pointDetail.getSourceTransDate())));
		if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
			//buf.append(DateUtil.getFormatHms(Timestamp.valueOf(pointDetail.getSourceTransDate())));
			buf.append(PosDateUtil.getYmdhms(pointDetail.getSourceTransDate()));
		}else{
			buf.append(DateUtil.getFormatHms(Timestamp.valueOf(pointDetail.getSourceTransDate())));
		}
		//end--modified on liuzh on 2016-07-02
		buf.append(pointDetail.getSourceTransNo());
		//start--modified by liuzh 2016-05-21
		//String recode = StringUtils.right("9023"/*pointDetail.getReturnReason()*/, 2);
		String recode = StringUtils.right(PosRespCode.SUCCESS.getCodeStr(),2);
		//end--modified by liuzh 2016-05-21
		
		buf.append(recode);
		String posNo = StringUtils.right(pointDetail.getEquipmentNo(), 4);
		//start--added by liuzh on 2016-07-05
		if(StringUtils.isBlank(posNo)) {
			posNo = "0000";
		}
		//end--added by liuzh on 2016-07-05
		buf.append(StringUtils.leftPad(posNo, POSNO_PACKLEN, PosConstant.ZERO_CHAR));
		//start--commented by liuzh on 2016-06-27
		//buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getSourceTransAmount())));//100
		//end--commented by liuzh on 2016-06-27
		//start--added by liuzh on 2016-06-27
		//消费金额
		if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
			//消费金额
			String orderAmount = pointDetail.getOrderAmount();
			if(StringUtils.isEmpty(orderAmount)){
				orderAmount = pointDetail.getSourceTransAmount();
			}
			buf.append(CommonUtil.fill0InMoney(new BigDecimal(orderAmount)));
		}else{
			//消费金额  原有版本消费金额
			buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getSourceTransAmount())));
		}
		//end--added by liuzh on 2016-06-27
		
		buf.append(CommonUtil.fill0InRate(new BigDecimal(pointDetail.getPointRate())));//0.1
		buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getEntPoint())));//10.0
		String operNo = pointDetail.getOperNo();
		//start--added by liuzh on 2016-05-17
		if(operNo==null) {
			operNo = "0000";
		}
		//end--added by liuzh on 2016-05-17
		if(operNo.length() >=0 && operNo.length() <= 3){//暂定补成三位，不确定究竟操作员编号实际有多长
			buf.append(StringUtils.leftPad(operNo, N_OPERNO_PACKLEN, PosConstant.ZERO_CHAR));
		}else if(operNo.length() == N_OPERNO_PACKLEN){
			buf.append(operNo);
		}else if(operNo.length() > N_OPERNO_PACKLEN){
			buf.append(StringUtils.right(operNo, 4));
		}
		buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getPerPoint())));//5.0
		//start--modified by liuzh on 2016-07-05
		//buf.append(pointDetail.getSourceBatchNo());
		String sourceBatchNo = pointDetail.getSourceBatchNo();
		if(StringUtils.isBlank(sourceBatchNo)) {
			sourceBatchNo = "000000";
		}
		buf.append(sourceBatchNo);
		//end--modified by liuzh on 2016-07-05
		String hsOrder = "";//pointDetail.getHsOrder()==null?"":pointDetail.getHsOrder();
		
		buf.append(StringUtils.leftPad(hsOrder,32,PosConstant.ZERO_CHAR));//没有互生订单号，全补零
		
		//pos机3.0，增加对支付结果的获取
		//start--modified by liuzh on 2016-06-24
		//if(Arrays.equals(pversion, PosConstant.POSVERSION30) ){
		if(Arrays.equals(pversion, PosConstant.POSVERSION30) || Arrays.equals(pversion, PosConstant.POSVERSION301) ){
		//end--modified by liuzh on 2016-06-24
			if("point".equals(pointDetail.getRemark())){//来自ps的都要有此项
				String payStatus = pointDetail.getPayStatus();
				if(null == payStatus || payStatus.length() != 1)
					CommonUtil.checkState(true, "单笔交易查询，支付结果项值异常。该值不可为空且长度只能有1位", 
																PosRespCode.SINGLE_TRADE_INFO_ERROR);
				buf.append(payStatus);
			}else {
				buf.append("2");//兑换代兑不涉及支付结果（暂定）
			}
			//start--added by liuzh on 2016-06-27
			if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
				//实付金额
				buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointDetail.getSourceTransAmount())));
				//抵扣券张数
				buf.append(StringUtils.leftPad(String.valueOf(pointDetail.getDeductionVoucher()),PosConstant.DEDUCTION_VOUCHER_COUNT_LEN,PosConstant.ZERO_CHAR));
				//支付方式
				if(StringUtils.isEmpty(pointDetail.getTransType())) {
					buf.append("00");
				}else{
					buf.append(StringUtils.leftPad(CommonUtil.getTransWay(pointDetail.getTransType()),TRANS_WAY_PACKLEN,PosConstant.ZERO_CHAR));//支付方式
				}
			}	
			//end--added by liuzh on 2016-06-27
		}
		//start--added by liuzh on 2016-05-21
		SystemLog.debug("Fd48", "pointDetailNewAppend()", "pointDetail:" + JSON.toJSONString(pointDetail) + ",buf:" + buf.toString());
		//end--added by liuzh on 2016-05-21		
		
		return buf;
	}
	
	
	/**
	 * added by liuzh on 2016-06-22
	 * @param messageId
	 * @param data
	 * @param pversion
	 * @return
	 * @throws Exception
	 */
	public Object doRequestProcessVer301(String messageId, byte[] data,byte[] pversion) throws Exception {
		if(Arrays.equals(pversion,PosConstant.POSVERSION301)) {
			
			//抵扣券请求报文长度
			int deductionVoucherDataLength = MONEY_PACK_BYTE_LEN + DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN;
			
			//积分交易--现金支付请求报文长度
			int reqPointCashDataLength = RATE_PACK_BYTE_LEN + MONEY_PACK_BYTE_LEN
					+ deductionVoucherDataLength;
			//积分交易--互生币支付请求报文长度
			int reqPointHsbDataLength = RATE_PACK_BYTE_LEN + MONEY_PACK_BYTE_LEN + MONEY_PACK_BYTE_LEN
					+ deductionVoucherDataLength;
			
			//预付定金结算请求报文长度
			int reqEarnestDataLength = RATE_PACK_BYTE_LEN + MONEY_PACK_BYTE_LEN + MONEY_PACK_BYTE_LEN + EARNEST_SEQ_BYTE_LEN
					 + deductionVoucherDataLength;
			if(data.length==reqPointCashDataLength) {
				//积分比例
				byte[] rateByte = new byte[RATE_PACK_BYTE_LEN];
				System.arraycopy(data, 0, rateByte, 0, RATE_PACK_BYTE_LEN);
				String rateStr = Hex.encodeHexString(rateByte);
				BigDecimal rate = CommonUtil.changePackDataToRate(rateStr);
				
				int count = RATE_PACK_BYTE_LEN;
				
				//企业承兑积分额
				byte[] assureByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, assureByte, 0, MONEY_PACK_BYTE_LEN);
				String assureStr = Hex.encodeHexString(assureByte);
				BigDecimal assure = CommonUtil.changePackDataToMoney(assureStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//实付金额 
				byte[] actualPayByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, actualPayByte, 0, MONEY_PACK_BYTE_LEN);
				String actualPayStr = Hex.encodeHexString(actualPayByte);
				BigDecimal actualPay = CommonUtil.changePackDataToMoney(actualPayStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//抵扣劵张数
				byte[] deductVoucherByte = new byte[DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN];
				System.arraycopy(data, count, deductVoucherByte, 0, DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN);
				String deductVoucherStr = Hex.encodeHexString(deductVoucherByte);
				BigDecimal deductVoucherCount = new BigDecimal(deductVoucherStr);
				
				//组48域数据
				Poi48 reqData = new Poi48(rate, assure);
				reqData.setActualPayAmount(actualPay);
				reqData.setDeductVoucherCount(deductVoucherCount.intValue());		
				
				return reqData;
				
			}else if(data.length==reqPointHsbDataLength) {
				//积分比例
				byte[] rateByte = new byte[RATE_PACK_BYTE_LEN];
				System.arraycopy(data, 0, rateByte, 0, RATE_PACK_BYTE_LEN);
				String rateStr = Hex.encodeHexString(rateByte);
				BigDecimal rate = CommonUtil.changePackDataToRate(rateStr);
				
				int count = RATE_PACK_BYTE_LEN;
				
				//企业承兑积分额
				byte[] assureByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, assureByte, 0, MONEY_PACK_BYTE_LEN);
				String assureStr = Hex.encodeHexString(assureByte);
				BigDecimal assure = CommonUtil.changePackDataToMoney(assureStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//互生币金额
				byte[] cashAmountByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, cashAmountByte, 0, MONEY_PACK_BYTE_LEN);
				String cashAmountStr = Hex.encodeHexString(cashAmountByte);
				BigDecimal cashAmount = CommonUtil.changePackDataToMoney(cashAmountStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//实付金额 
				byte[] actualPayByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, actualPayByte, 0, MONEY_PACK_BYTE_LEN);
				String actualPayStr = Hex.encodeHexString(actualPayByte);
				BigDecimal actualPay = CommonUtil.changePackDataToMoney(actualPayStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//抵扣劵张数
				byte[] deductVoucherByte = new byte[DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN];
				System.arraycopy(data, count, deductVoucherByte, 0, DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN);
				String deductVoucherStr = Hex.encodeHexString(deductVoucherByte);
				BigDecimal deductVoucherCount = new BigDecimal(deductVoucherStr);
				
				//组48域数据
				Poi48 reqData = new Poi48(rate, assure,cashAmount);
				reqData.setActualPayAmount(actualPay);
				reqData.setDeductVoucherCount(deductVoucherCount.intValue());		
				
				return reqData;
				
			}else if(data.length==reqEarnestDataLength) {
				//积分比例
				byte[] rateByte = new byte[RATE_PACK_BYTE_LEN];
				System.arraycopy(data, 0, rateByte, 0, RATE_PACK_BYTE_LEN);
				String rateStr = Hex.encodeHexString(rateByte);
				BigDecimal rate = CommonUtil.changePackDataToRate(rateStr);
				int count = RATE_PACK_BYTE_LEN;
				
				//企业承兑积分
				byte[] assureByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, assureByte, 0, MONEY_PACK_BYTE_LEN);
				String assureStr = Hex.encodeHexString(assureByte);
				BigDecimal assure = CommonUtil.changePackDataToMoney(assureStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//互生币计的交易金额
				byte[] hsbAmountByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, hsbAmountByte, 0, MONEY_PACK_BYTE_LEN);
				String hsbAmountStr = Hex.encodeHexString(hsbAmountByte);
				BigDecimal hsbAmount = CommonUtil.changePackDataToMoney(hsbAmountStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//定金交易序号
				byte[] earnestSeqB = new byte[EARNEST_SEQ_BYTE_LEN];
				System.arraycopy(data, count, earnestSeqB, 0, EARNEST_SEQ_BYTE_LEN);
				String earnestSeqStr = Hex.encodeHexString(earnestSeqB);
				count += EARNEST_SEQ_BYTE_LEN;
				
				//实付金额 
				byte[] actualPayByte = new byte[MONEY_PACK_BYTE_LEN];
				System.arraycopy(data, count, actualPayByte, 0, MONEY_PACK_BYTE_LEN);
				String actualPayStr = Hex.encodeHexString(actualPayByte);
				BigDecimal actualPay = CommonUtil.changePackDataToMoney(actualPayStr);
				count += MONEY_PACK_BYTE_LEN;
				
				//抵扣劵张数
				byte[] deductVoucherByte = new byte[DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN];
				System.arraycopy(data, count, deductVoucherByte, 0, DEDUCTION_VOUCHER_COUNT_PACK_BYTE_LEN);
				String deductVoucherStr = Hex.encodeHexString(deductVoucherByte);
				BigDecimal deductVoucherCount = new BigDecimal(deductVoucherStr);
				
				Poi48 reqData = new Poi48(rate, assure, hsbAmount, earnestSeqStr);
				reqData.setActualPayAmount(actualPay);
				reqData.setDeductVoucherCount(deductVoucherCount.intValue());	
				
				return reqData;
			}
		}
		return null;
	}	

	
	/**
	 * added by liuzh on 2016-06-27
	 * 3.01增加支付结果 48域用法十七. 查询可撤单或可退货的积分记录.
	 * @param pointOrders
	 * @param pversion
	 * @return
	 * @throws PosException
	 */
	private StringBuilder queryPointOrders(List<PointRecordResult> pointOrders, byte[] pversion) throws PosException {
		//可撤单积分记录查询或可退货积分记录查询. pos3.01版本开始支持		
		SystemLog.debug("Fd48","queryPointOrders","应答,积分交易查询数据  记录数:" + pointOrders.size());
		
		StringBuilder buf = new StringBuilder();
	    buf.append(StringUtils.leftPad(String.valueOf(pointOrders.size()), 2, PosConstant.ZERO_CHAR));
		String orderAmount ="0";
		for (PointRecordResult pointOrder : pointOrders) {
			buf.append(StringUtils.leftPad("0", TERMTYPECODE_PACKLEN, PosConstant.ZERO_CHAR));// 终端类型码	
			buf.append(PosDateUtil.getYmdhms(pointOrder.getSourceTransDate())); //交易时间
			buf.append(pointOrder.getSourceTransNo());  //POS中心参考号 
			if(StringUtils.isEmpty(pointOrder.getOrderAmount())) {
				orderAmount = pointOrder.getSourceTransAmount();
			}else{
				orderAmount = pointOrder.getOrderAmount();
			}
			buf.append(CommonUtil.fill0InMoney(new BigDecimal(orderAmount)));  //消费金额  
			buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointOrder.getSourceTransAmount())));  //实付金额
			buf.append(CommonUtil.fill0InRate(new BigDecimal(pointOrder.getPointRate())));  //积分比例
			buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointOrder.getEntPoint())));   //积分金额
			buf.append(CommonUtil.fill0InMoney(new BigDecimal(pointOrder.getPerPoint())));   //分配积分数			
			buf.append(StringUtils.leftPad(CommonUtil.getTransWay(pointOrder.getTransType()),TRANS_WAY_PACKLEN,PosConstant.ZERO_CHAR));//支付方式
		}
		return buf;
	}
}
