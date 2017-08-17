package com.gy.hsxt.access.pos.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.QrCodeCredential;
import com.gy.hsxt.access.pos.model.QrCodeTrans;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.UFApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
    @Qualifier("uFApiService")
    private UFApiService uFApiService;
	
	/**
	 * 从请求报文中识别交易卡号输入方式，用相应方法提取消费者互生卡号，对于兑换互生币，则提取企业互生号
	 * @param cmd
	 * @return Map<String, String> cardNoAndCode
	 * 	其中有至多两个值.
	 * 		key = "cardNo":消费者互生卡号,11位；
	 * 		key = "cardCode":互生卡暗码,11位。只有刷卡交易有。
	 * @throws Exception 卡号和暗码长度校验不通过则抛出异常
	 */
	public Map<String, String> getCustomerHscardNo(Cmd cmd) throws Exception {
		
		PosReqParam param = cmd.getPosReqParam();
		//22.1+2 输入方式码
		String inputWay = param.getInputWay();
		//22.3 是否有pin
		String isPin = param.getIsPin();
		
		//若要获取卡号，则22域的输入方式码必须有值
		CommonUtil.checkState(StringUtils.isEmpty(inputWay) || StringUtils.isEmpty(isPin), 
				"8583报文异常, 非法的22域服务点输入方式码。inputWay： " + inputWay +
				" ; isPin: " + isPin, PosRespCode.REQUEST_PACK_FORMAT);

		//检索并返回消费者互生号
		String cardNo = null;
		Map<String, String> cardNoAndCode= new HashMap<String, String>();
		switch(inputWay){
			//刷卡
			case PosConstant.INPUT_WAY_STRIPE:
				cardNo = param.getStripe2();
				CommonUtil.checkState(cardNo.length() != PosConstant.CARDNO_CIPHER_LENGTH, 
						"8583报文异常, 刷卡长度不符合,cardNo：" + cardNo, PosRespCode.REQUEST_PACK_FORMAT);
				cardNoAndCode.put("cardCode", StringUtils.right(cardNo, PosConstant.CIPHER_LENGTH));//后8位是暗码
				cardNo = StringUtils.left(cardNo, PosConstant.CARDNO_LENGTH);//前11位是互生卡号
				break;
			//键盘输入
			case PosConstant.INPUT_WAY_MANUAL:
				cardNo = param.getCardNo();//手工输入没有暗码
				break;
			//扫二维码
			case PosConstant.INPUT_WAY_QR://从二维码中获取，62域，11位互生号
				cardNo = getCardNoFromQr((String)param.getCustom2());
				break;
			//其它类型
			case PosConstant.INPUT_WAY_0://企业兑换互生币
				break;
			default:
				CommonUtil.checkState(true, "8583报文异常, 服务点输入方式码不匹配已知项。inputWay： " + 
						inputWay, PosRespCode.REQUEST_PACK_FORMAT);
		}
		CommonUtil.checkState(cardNo.length() != PosConstant.CARDNO_LENGTH,
				"8583报文异常, 手输卡长度不符合,cardNo：" + cardNo, PosRespCode.REQUEST_PACK_FORMAT);
		cardNoAndCode.put("cardNo", cardNo);
		return cardNoAndCode;
	}
	
	/**
	 * 根据支付二维码字串定义取其中的消费者互生卡号
	 * @param _62area
	 * @return
	 * @throws PosException 
	 */
	private static String getCardNoFromQr(String _62area) throws PosException{
		SystemLog.debug("CommonServiceImpl", "getCardNoFromQr()", 
							"entering method. 提取的二维码原始串为： " + _62area);
		//正确的消费者互生号二维码格式为 2位类型&11位互生号
		String[] s = _62area.split("&");
		
		//这个校验意义不大，增加二维码类型主要便于管理
//		CommonUtil.checkState(!PosConstant.CUSTOMER_ID.equals(s[0]), "二维码类型错误。期望：" + 
//				PosConstant.CUSTOMER_ID + "，实际：" + s[0], PosRespCode.QR_TYPE_ERROR);
		
		CommonUtil.checkState(null == s[1] || s[1].length() != 11, 
				"消费者互生卡号错误。期望11位数字，实际：" + s[1], PosRespCode.QR_ERROR);
		
		return s[1];
	}
	
	
	/** 
	 * pos机扫码得到的交易凭据（交易流水号）写入62域
	 * “交易凭据二维码”字符串原始定义：(2位字母数字)二维码类型&(2位字母数字)交易类别&12位数字的交易流水号明文。
	 * “交易单据二维码”：（2位字母数字）类型&11位企业互生号&4位pos终端编号&6位批次号&6位pos机凭证号
	 * &14位日期时间（YYYYMMDDhh24mmss）&3位货币代码（49）&12位交易金额（4）&4位积分比例（48用法六）
	 * &12企业承兑积分额（48用法六）&12互生币金额（48用法六）&8位随机扰码（数字型字符串）&8位mac校验位
	 * 
	 * 为便于以后扩展，这里不做严格校验
	 * @param _62area 
	 * @return Map中有两个值：
	 * 						BizType：业务类型
	 * 						BizSeq：业务序列号(均已做非空校验)
	 * @throws PosException
	 */
	public Map<String, String> getSeqNTradeTypeFromQr(String _62area) throws PosException{
		final String[] s = _62area.split("&");
		Map<String, String> qrElements = new HashMap<String, String>();
		CommonUtil.checkState(null == s[0] || "".equals(s[0]),"二维码类型为空", PosRespCode.QR_ERROR);
		qrElements.put("type", s[0]);
		
		if(PosConstant.QRTYPE_BILL_SEQ.equals(s[0]))
			qrElements.put("BizSeq", s[1]);
		else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(s[0])){
			/*  （2位字母数字）类型
				11位企业互生号
				4位pos终端编号
				6位批次号
				6位pos机凭证号
				14位日期时间（YYYYMMDDhh24mmss）
				3位货币代码（49）
				12位交易金额（4）
				4位积分比例（48用法六）
				12企业承兑积分额（48用法六）
				12互生币金额（48用法六）
				8位随机扰码（数字型字符串）
				8位mac校验位
			 */
			qrElements.put("EntHsNo", s[1]);
			qrElements.put("PosTermNo", s[2]);
			qrElements.put("PosBatchNo", s[3]);
			qrElements.put("PosSeqNo", s[4]);
			qrElements.put("TransDateTime", s[5]);
			qrElements.put("CurrencyCode", s[6]);
			qrElements.put("CurrencyTransAmount", s[7]);
			qrElements.put("PointRate", s[8]);
			qrElements.put("EntPointAmt", s[9]);
			qrElements.put("HsbTransAmt", s[10]);
			qrElements.put("RandomScrambling", s[11]);
			qrElements.put("Mac", s[12]);
		}else
			CommonUtil.checkState(true,"二维码类型错误，当前类型是：" + s[0], PosRespCode.QR_ERROR);
		return qrElements;
		
	}
	
	/** added by liuzh on 2016-04-19
	 *  此方法仅仅用于pos3.0版本 commented by liuzh on 2016-06-24 
	 * 获取二维码数据 (BH:交易单据; BS:交易凭据)
	 * @param _62area
	 * @return
	 * @throws PosException
	 */
	public Object getQrCode(String _62area) throws PosException{
		final String[] s = _62area.split("&");
		//二维码类型
		String qrType = s[0];
		
		CommonUtil.checkState(StringUtils.isEmpty(qrType),"二维码类型为空", PosRespCode.QR_ERROR);
		
		if(PosConstant.QRTYPE_BILL_SEQ.equals(qrType)) {
			//交易凭据二维码 
			String tradeRunCode = s[1];
			QrCodeCredential qrCode = new QrCodeCredential();	
			qrCode.setQrType(qrType);
			qrCode.setTradeRunCode(tradeRunCode);
			
			return qrCode;
			
		}else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)){
			//交易单据二维码
			String entResNo = s[1];
			String posTermNo = s[2];
			String posBatchNo = s[3];
			String posSeqNo = s[4];
			String transDateTime = s[5];
			String currencyCode = s[6];
			String currencyTransAmount = s[7];
			String pointRate = s[8];
			String entPointAmt = s[9];
			String hsbTransAmt = s[10];
			String randomScrambling = s[11];
			String mac = s[12];
			
			QrCodeTrans qrCode = new QrCodeTrans();
			qrCode.setQrType(qrType);			
			qrCode.setEntResNo(entResNo);
			qrCode.setPosTermNo(posTermNo);
			qrCode.setPosBatchNo(posBatchNo);
			qrCode.setPosTermRunCode(posSeqNo);
			qrCode.setTransTime(transDateTime);
			qrCode.setCurrencyCode(currencyCode);
			qrCode.setTransAmount(currencyTransAmount);
			qrCode.setPointRate(pointRate);
			qrCode.setEntPointAmount(entPointAmt);
			qrCode.setHsbTransAmount(hsbTransAmt);
			qrCode.setRandomScrambling(randomScrambling);
			qrCode.setMac(mac);
			
			return qrCode;
			
		}else{
			CommonUtil.checkState(true,"二维码类型错误，当前类型是：" + qrType, PosRespCode.QR_ERROR);
		}
		return null;	
	}
	
	/** added by liuzh on 2016-06-22
	 * 新增: 兼容3.0和3.01
	 * 获取二维码数据 (BH:交易单据; BS:交易凭据)
	 * @param _62area
	 * @return
	 * @throws PosException
	 */
	public Object getQrCode(String _62area,byte[] pversion) throws PosException{
		final String[] s = _62area.split("&");
		//二维码类型
		String qrType = s[0];
		
		CommonUtil.checkState(StringUtils.isEmpty(qrType),"二维码类型为空", PosRespCode.QR_ERROR);
		
		if(PosConstant.QRTYPE_BILL_SEQ.equals(qrType)) {
			//交易凭据二维码 
			String tradeRunCode = s[1];
			QrCodeCredential qrCode = new QrCodeCredential();	
			qrCode.setQrType(qrType);
			qrCode.setTradeRunCode(tradeRunCode);
			
			return qrCode;
			
		}else if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)
				|| PosConstant.QRTYPE_BILL_PAY_HANG_301.equals(qrType)){
			//交易单据二维码
			String entResNo = s[1];
			String posTermNo = s[2];
			String posBatchNo = s[3];
			String posSeqNo = s[4];
			String transDateTime = s[5];
			String currencyCode = s[6];
			String currencyTransAmount = s[7];
			String pointRate = s[8];
			String entPointAmt = s[9];
			String hsbTransAmt = s[10];
			//实付金额
			String actualAmount = null;
			//抵扣券张数
			String deductionVoucherCount = null;
			
			String randomScrambling = null;
			String mac = null;
						
			if(PosConstant.QRTYPE_BILL_PAY_HANG.equals(qrType)) {	
				//前缀是BH
				randomScrambling = s[11];
				mac = s[12];
				actualAmount = "0";
				deductionVoucherCount = "0";
			}else{
				//pos3.0以后版本增加12互生币金额和抵扣券张数. &12互生币金额之后增加实付金额和抵扣券张数
				//前缀是B1
				actualAmount = s[11];
				deductionVoucherCount = s[12];
				randomScrambling = s[13];
				mac = s[14];
			}
			
			QrCodeTrans qrCode = new QrCodeTrans();
			qrCode.setQrType(qrType);			
			qrCode.setEntResNo(entResNo);
			qrCode.setPosTermNo(posTermNo);
			qrCode.setPosBatchNo(posBatchNo);
			qrCode.setPosTermRunCode(posSeqNo);
			qrCode.setTransTime(transDateTime);
			qrCode.setCurrencyCode(currencyCode);
			qrCode.setTransAmount(currencyTransAmount);
			qrCode.setPointRate(pointRate);
			qrCode.setEntPointAmount(entPointAmt);
			qrCode.setHsbTransAmount(hsbTransAmt);
			qrCode.setRandomScrambling(randomScrambling);
			qrCode.setMac(mac);
			qrCode.setActualAmount(actualAmount);
			qrCode.setDeductionVoucherCount(deductionVoucherCount);
			
			return qrCode;
			
		}else{
			CommonUtil.checkState(true,"二维码类型错误，当前类型是：" + qrType, PosRespCode.QR_ERROR);
		}
		return null;	
	}
	
	/**
	 * 调用综合前置服务，发起跨平台交易请求
	 * @param param 交易参数类，如Point、Cancel等
	 * @param custHscardNo 消费者互生号，用于地方平台路由识别
	 * @param instructCode 交易指令，需与异地平台的对应响应系统设置保持一致
	 * @return 接受请求的系统职能模块定义的响应类
	 * 可能抛出的互生异常：
	 	* 错误码	说明<br>
		* 17000	请求参数无效<br>
		* 17001	非跨地区平台报文,不得通过UF转发<br>
		* 17100	综合前置内部处理异常<br>
		* 17101	综合前置查询路由目标地址失败<br>
		* 17102	综合前置没有找到路由目标地址<br>
		* 17103	综合前置没有找到目标业务子系统<br>
		* 17201	综合前置处理失败<br>
		* 17202	内部系统与综合前置通信失败<br>
		* 17203	综合前置与目标平台综合前置通信失败<br>
		* 17204	该报文无法被目标平台综合前置识别<br>
		* 17205	该报文不属于目标平台<br>
		* 17206	目标平台处理该报文时发生错误<br>
	 * @throws PosException
	 */
	public Object sendCrossPlatformIndicate(Object param, String custHscardNo, String instructCode) throws Exception {
		SystemLog.info(this.getClass().getName(), "dealCrossPlatformBiz()","entering method. param:" + 
				JSON.toJSONString(param) + "  custHscardNo:" + custHscardNo + "  instructCode:" + 
				instructCode);
		//组装报文头
		RegionPacketHeader packetHeader = RegionPacketHeader.build()
				.setDestResNo(custHscardNo).setDestBizCode(instructCode);
		//组装报文体
		RegionPacketBody packetBody = RegionPacketBody.build(param);
		
		return uFApiService.sendSyncRegionPacket(packetHeader, packetBody);

	}

	
	/**
	 * 根据互生异常编码识别隶属的分系统
	 * @param code
	 * @return
	 */
	public String checkSubSysByHsErrCode(Integer code){
		
		if(code.intValue() < 10000)
			return "common";
		int c = Integer.parseInt(code.toString().substring(0, 1));
		switch(c) {
		case 10 :
			return "reserve";
		case 11 :
			return "ps";
		case 12 :
			return "bs";
		case 13 :
			return "ac";
		case 14 :
			return "bm";
		case 15 :
			return "gp";
		case 16 :
			return "uc";
		case 17 :
			return "uf";
		case 18 :
			return "nt";
		case 19 :
			return "fs";
		case 20 :
			return "dc";
		case 21 :
			return "lc";
		case 22 :
			return "as";
		case 23 :
			return "lcs";
		case 24 :
			return "ws";
		case 25 :
			return "mb";
		case 26 :
			return "pp";
		case 27 :
			return "mp";
		case 28 :
			return "cr";
		case 29 :
			return "tp";
		case 30 :
			return "pw";
		case 31 :
			return "ew";
		case 32 :
			return "sw";
		case 33 :
			return "mw";
		case 34 :
			return "tc";
		case 35 :
			return "pg";
		case 36 :
			return "ds";
		case 37 :
			return "rw";
		case 38 :
			return "cw";
		case 39 :
			return "rp";
		case 40 :
			return "ss";
		case 41 :
			return "fss";
		case 42 :
			return "tm";
		case 43 :
			return "ao";
		case 44 :
			return "bp";
		case 45 :
			return "gcs";
		case 46 :
			return "res";
		case 47 :
			return "um";
		default :
			return "none";
		}
		
	}

}
