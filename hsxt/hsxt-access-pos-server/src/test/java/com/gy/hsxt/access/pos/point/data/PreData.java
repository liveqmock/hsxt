package com.gy.hsxt.access.pos.point.data;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.access.pos.action.SigninAction;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosUtil;



/**
 * 
 * @author wucl
 *
 */
public class PreData {
	
	Logger log = LoggerFactory.getLogger(SigninAction.class);
	
	/**
	 * 终端类型码
	 */
	String termTypeCode = String.valueOf(PosRespCode.POS_CENTER_FAIL.getCode()).substring(2);
	
	/**
	 * 终端流水号
	 */
	String posRunCode = "000001";
	
	/**
	 * 操作员号  跟新key server mac校验有关系
	 */
	public String operNo = "0001";//
	String operNoAsc = Hex.encodeHexString(operNo.getBytes());
	
	/**
	 * 批次号
	 */
	String batchNo = "000001";
	
	/**
	 * 手输卡号 密码明文
	 * 2015/04/29  259132
	 * 2015/05/09  111111
	 */
	public String pin = "666666";
	/**
	 * 交易密码
	 */
	public String tradePwd = "88888888";
	
	/**
	 * 刷卡 暗码  从  11561839
	 * 0618601004811565793  失效的
	 * 0618601004811562014补办的卡
	 * 
	 * 0618601000611560964
	 * 
	 */
	String cardCode = "11560964";//0618601002211566691    0618601002511568167
	
	private static String HEADSTR = "6085838583643100600002";
	
	public static byte[] PVERSION = getPversion();
	
	
	/**
	 * 06186010000		0003
	 * 06186630000		0003
	 * 
	 * 
	 */
	public String entNo = "06186630000";//StringUtils.left(POS_NO, 11);
	
	public String posNo = "0001";
	
	/**
	 * 8位左补零
	 */
	String posNoStr = StringUtils.leftPad(posNo, 8, '0');
	String posNoAsc = Hex.encodeHexString(posNoStr.getBytes());
	
	
	/**
	 * 15位左补零
	 */
	String entNoStr = StringUtils.leftPad(entNo, 15, '0');
	String entNoAsc = Hex.encodeHexString(entNoStr.getBytes());
	
	/**
	 * 卡号(消费者资源号)05001010001 05001010005
	 * 由于GCS/ACCT经常清空DB，导致有些TC会失败。06186010001
	 */
	public String cardNo = "06186010006";//06186010022  06186010025
	String cardNoStr = StringUtils.rightPad(cardNo, 12, "0");//12位右补零
	
	/**
	 * 货币代号
	 */
	String currency = "156";
	String currencyAsc = Hex.encodeHexString(currency.getBytes());

	
	
	
	public void setCardNoStr(String cardNo) {
		this.cardNoStr = StringUtils.rightPad(cardNo, 12, "0");;
	}

	//报文信息
	String[] bodyArr;
	


	
	/**
	 * 签到 参数
	 */
	public String[] prepareSignIn() {
		termTypeCode = "00";//终端类型码
		//
		bodyArr = new String[64];
		bodyArr[11] = posRunCode;
		bodyArr[41] = posNoAsc;
		bodyArr[42] = entNoAsc;
		bodyArr[60] = "0011" + termTypeCode + batchNo + "0030";
		bodyArr[62] = "002853657175656e6365204e6f3135333038394b33373032433037353339";
		bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;
		
		return bodyArr;
	}
	
	/**
	 * 签退
	 * @return
	 */
	public String[] prepareSignOff() {
		//		posNo = "01";// POS终端号
		//		entNo = "05198010000";//企业管理号 better: 07005010010
		termTypeCode = "00";//终端类型码
		//
		bodyArr = new String[64];
		bodyArr[11] = posRunCode;
		bodyArr[41] = posNoAsc;
		bodyArr[42] = entNoAsc;
		bodyArr[60] = "0011" + termTypeCode + batchNo + "0020";
		
		return bodyArr;
	}
	
	/**
	 * 同步参数
	 * @param versions
	 * @return
	 * @throws Exception
	 */
	public String[] prepareSyncParam(String versions)
			throws Exception {
		String versionsAsc = Hex.encodeHexString(versions.getBytes());

		String termTradeCode = "720000";//终端交易码

		bodyArr = new String[64];
		bodyArr[3] = termTradeCode;//3
		bodyArr[11] = posRunCode;//11POS终端交易流水号
		bodyArr[41] = posNoAsc;//41
		bodyArr[42] = entNoAsc;//42
		bodyArr[62] = StringUtils.leftPad(versions.length()+"", 4, "0") + versionsAsc;//63
		
		return bodyArr;
	}
	
	
	/**
	 * 互生币支付 刷卡积分  输入8位交易密码
	 * @param batchNo
	 * @param cashAmount
	 * @param isHsb
	 * @return   应答折算后的本币
	 * @throws Exception
	 */
	public String[] prepareHsbPay(BigDecimal cashAmount, BigDecimal pointRate, String packPin) throws Exception {
		
		PosReqBuilderHandler sender = new PosReqBuilderHandler();
		String posRunCode = sender.getPosRunCode(entNo+posNo);
		
		BigDecimal tradeMoney = cashAmount.multiply(new BigDecimal(1)) ;//互生币*互生币转现比率=现金
		BigDecimal pointMoney = tradeMoney.multiply( pointRate);//积分金额
		
		//pointMoney = CommonUtil.roundPoiAmt(pointMoney);
		
		String tradeMoneyStr = CommonUtil.fill0InMoney(tradeMoney);
		String pointMoneyStr = CommonUtil.fill0InMoney(pointMoney);
		String pointRateStr = CommonUtil.fill0InRate(pointRate);
		
		String cashAmountStr = CommonUtil.fill0InMoney(cashAmount);
		
		String termTradeCode = PosConstant.HS_ORDER_POS_TERM_TRADE_CODE_HSB;//终端交易码
		String termTypeCode = "63";//终端类型码
		
		String pointStr = pointRateStr + pointMoneyStr + cashAmountStr;//48域拼接

		bodyArr = new String[64];
//		bodyArr[2] = "11" + cardNoStr;//2
		bodyArr[3] = termTradeCode;//3
		bodyArr[4] = tradeMoneyStr;//4交易金额
		bodyArr[11] = posRunCode;//11POS终端交易流水号
		bodyArr[22] = "0210";
		bodyArr[26] = "08";//26
		bodyArr[35] = "20" + cardNo + "d" + cardCode;//35
		bodyArr[41] = posNoAsc;//41
		bodyArr[42] = entNoAsc;//42
		bodyArr[48] = StringUtils.leftPad(pointStr.length()+"", 4, "0") + pointStr;//
		
		bodyArr[52] = packPin;//pin码		
		bodyArr[53] = "2600000000000000";//53
		bodyArr[49] = currencyAsc;//49货币代号
		bodyArr[60] = "0008" + termTypeCode + batchNo;//60
		bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;//63

		return bodyArr;
	}
	
	
	/**
	 * 
	 *
	 * @author HuangFuHua
	 * @throws Exception 
	 */
	public BitMap[] checkResult(byte[] result0, String expectedResult)  {
		try{
			final byte[] typeId = Arrays.copyOfRange(result0, 0, 2);
			
			if(Arrays.equals(PosConstant.SIGNINTYPEREP, typeId)){
				assertEquals(110, result0.length);//签到应答字节数
			}
			
			final byte[] body = Arrays.copyOfRange(result0, 2, result0.length);		
			BitMap[] bitMaps = PosUtil.unpackReq(body);
			String field39Str = bitMaps[39].getStr();
			
			assertEquals(expectedResult, field39Str);
			
			if(bitMaps[63] != null){
				String field63Str = bitMaps[63].getStr();
				String GYT = StringUtils.left(field63Str, 3);
				assertEquals(GYT, PosConstant.ICC_CODE_GYT);
	
			}
			return bitMaps;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
		this.posNoStr = StringUtils.leftPad(posNo, 8, '0');
		this.posNoAsc = Hex.encodeHexString(posNoStr.getBytes());
	}

	public static byte[] getPversion(){
		
		try {
			return Hex.decodeHex(StringUtils.right(HEADSTR, 6).toCharArray());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * just for the evaluation of silent field.
	 */
	public static byte[] fixHeadByteValue() {
		try {
			return Hex.decodeHex(HEADSTR.toCharArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
     * 手输积分 登录密码
     * @param tradeAmount 交易金额
     * @param pointRate
     * @param cashAmount  他币金额   0 表示本币支付
     * @param serviceWay true 表示刷交易，反之手输卡号交易
     * @return  积分金额
     * @throws Exception
     */
    public String[] preparePoint(BigDecimal tradeAmount, BigDecimal pointRate, BigDecimal cashAmount, boolean serviceWay,String packPin) throws Exception {
        PosReqBuilderHandler sender = new PosReqBuilderHandler();
        String posRunCode = sender.getPosRunCode(entNo + posNo);//终端流水号
        
        BigDecimal tradeMoney = tradeAmount;//交易金额
        BigDecimal pointMoney = tradeMoney.multiply( pointRate);//积分金额
        //pointMoney = CommonUtil.roundPoiAmt(pointMoney);
        
        String tradeMoneyStr = CommonUtil.fill0InMoney(tradeMoney);
        String pointMoneyStr = CommonUtil.fill0InMoney(pointMoney);
        
        String pointRateStr = CommonUtil.fill0InRate(pointRate);
        
        String pointStr = null;
        
        pointStr = pointRateStr + pointMoneyStr;//48域拼接
        
        if(cashAmount == null){
            String cashAmountStr = CommonUtil.fill0InMoney(cashAmount);
            pointStr += cashAmountStr;//48域拼接
        }

        String termTradeCode = "210000";//终端交易码
        String termTypeCode = "61";//终端类型码

        bodyArr = new String[64];
        bodyArr[2] = "11" + cardNoStr;//2
        bodyArr[3] = termTradeCode;//3
        bodyArr[4] = tradeMoneyStr;//4交易金额
        bodyArr[11] = posRunCode;//11POS终端交易流水号
        if(serviceWay){
            bodyArr[22] = "0220";//
        }else{
            bodyArr[22] = "0110";//
        }
        bodyArr[25] = "00";//25
        bodyArr[26] = "06";//26
        if(serviceWay){
            bodyArr[35] = "20" + cardNo + "d" + cardCode;//35
        }
        
        bodyArr[41] = posNoAsc;//41
        bodyArr[42] = entNoAsc;//42
        bodyArr[48] = StringUtils.leftPad(pointStr.length()+"", 4, "0") + pointStr;
        bodyArr[49] = currencyAsc;//49货币代号
        
//        String packPin = getPackPin(svc, pin);
        
        bodyArr[52] = packPin;//pin码
        //
        bodyArr[53] = "2600000000000000";//53
        //      bodyArr[60] = "0008" + termTypeCode + batchNo;//60
        //终端14位
        bodyArr[60] = "0014" + termTypeCode + batchNo + "000001";//60
        bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;//63
        
        return bodyArr;
    }
    
    /**
     * 冲正   积分
     * @param posRunCode
     * @param tradeMoneyStr
     * @throws Exception
     */
    public String[] preparePointReverse(String posRunCode, String tradeMoneyStr) throws Exception {
        
        String termTradeCode = "210000";//终端交易码
        String termTypeCode = "61";//终端类型码

        // Map<Integer, String> messageBody = new LinkedHashMap<>();
        bodyArr = new String[64];
        bodyArr[2] = "11" + cardNoStr;//2
        bodyArr[3] = termTradeCode;//3
        bodyArr[4] = tradeMoneyStr;
        bodyArr[11] = posRunCode;//11POS终端交易流水号
        bodyArr[25] = "00";//25
        bodyArr[39] = Hex.encodeHexString(PosConstant.RECODE_A0.getBytes());
        bodyArr[41] = posNoAsc;//41
        bodyArr[42] = entNoAsc;//42
        bodyArr[60] = "0008" + termTypeCode + batchNo;//60
        bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;//63
        
        return bodyArr;
    }
    
    /**
     * 刷卡撤单  积分
     * @param orderNo
     * @param serviceWay true表示刷卡                   false 表示手输
     *            22域前二位 02表示                       22域前二位  01表示手输
     * @throws Exception
     */
    public String[] preparePointCancle(String orderNo, boolean serviceWay, boolean invalidCard,String packPin) throws Exception {
        PosReqBuilderHandler sender = new PosReqBuilderHandler();
        String posRunCode = sender.getPosRunCode(entNo+posNo);

        String orderNoAsc = Hex.encodeHexString(orderNo.getBytes());

        String termTradeCode = "200000";//终端交易码
        String termTypeCode = "62";//终端类型码

        bodyArr = new String[64];
        bodyArr[2] = "11" + cardNoStr;//2
        bodyArr[3] = termTradeCode;//3
        bodyArr[11] = posRunCode;//11POS终端交易流水号
        if(serviceWay){
            bodyArr[22] = "0210";//
        }else{
            bodyArr[22] = "0110";//
        }
        bodyArr[25] = "00";//25
        bodyArr[26] = "06";//26
        
        bodyArr[35] = "20" + cardNo + "d" + cardCode;//35卡号+暗码 当22域为02时存在
        
        if(invalidCard){
            bodyArr[35] = "20" + cardNo + "d" + "11565793";//35卡号+暗码 当22域为02时存在
        }
        
        
        bodyArr[37] = orderNoAsc;
        bodyArr[41] = posNoAsc;//41
        bodyArr[42] = entNoAsc;//42
        
//        String packPin = getPackPin(svc, "111112");
        bodyArr[52] = packPin;//pin码
        //
        bodyArr[53] = "2600000000000000";//53
        bodyArr[60] = "0008" + termTypeCode + batchNo;//60
        bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;//63

        return bodyArr;
    }

    /**
     * 
     * 积分 撤单  冲正
     * 
     * @param posRunCode
     * @param tradeMoney
     * @return
     * @throws Exception
     */
    public String[] preparePointCancleReverse(String posRunCode,
            BigDecimal tradeMoney) throws Exception {
        
        String termTradeCode = "200000";//终端交易码
        String termTypeCode = "62";//终端类型码

        // Map<Integer, String> messageBody = new LinkedHashMap<>();
        bodyArr = new String[64];
        bodyArr[2] = "11" + cardNoStr;//2
        bodyArr[3] = termTradeCode;//3
        
        if (tradeMoney != null) {
            String tradeMoneyStr = CommonUtil.fill0InMoney(tradeMoney);
            bodyArr[4] = tradeMoneyStr;
        }
        bodyArr[11] = posRunCode;//11POS终端交易流水号
        bodyArr[25] = "00";//25
        bodyArr[39] = Hex.encodeHexString(PosConstant.RECODE_A0.getBytes());
        bodyArr[41] = posNoAsc;//41
        bodyArr[42] = entNoAsc;//42
        bodyArr[60] = "0008" + termTypeCode + batchNo;//60
        bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;//63
        
        return bodyArr;
    }
    
    /**
     *   订单查询
     * @param orderNo
     * @throws Exception
     */
    public String[] prepareOrderSearch(String orderNo) throws Exception {
        String orderNoAsc = Hex.encodeHexString(orderNo.getBytes());

        String termTradeCode = "710000";//终端交易码
        termTypeCode = "03";

        bodyArr = new String[64];
        bodyArr[3] = termTradeCode;//3
        bodyArr[11] = posRunCode;//11POS终端交易流水号
        bodyArr[37] = orderNoAsc;
        bodyArr[41] = posNoAsc;//41
        bodyArr[42] = entNoAsc;//42
        bodyArr[60] = "0008" + termTypeCode + batchNo;//60
        bodyArr[63] = StringUtils.leftPad(operNo.length()+"", 4, "0") + operNoAsc;//63
        
        return bodyArr;
    }
}

