/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractB2cWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.B2cParamCheckHelper;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.CpRespCode;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.OrderStateReqKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cFieldsKey.OrderStateRespKey;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayHttpHelper;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker
 * 
 *  File Name       : OderStateWorker.java
 * 
 *  Creation Date   : 2014-10-27
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C查询订单状态 (此类代码由lijiabei的代码中摘取)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class OrderStateWorker extends AbstractB2cWorker {
	// 银联B2C对账接口地址
	// (1) 测试 http://payment-test.chinapay.com/QueryWeb/processQuery.jsp?
	// (2) 生产 http://console.chinapay.com/QueryWeb/processQuery.jsp?
	protected String bankGetOrderAddress;

	/**
	 * 构造函数
	 */
	public OrderStateWorker() {
	}

	/**
	 * 主动请求银联获取订单的状态信息
	 * 
	 * @param translateType 交易类型(0001 支付交易, 0002退款交易)
	 * @param orderNo 订单号
	 * @param orderDate 订单日期
	 * @return
	 * @throws Exception
	 * @exception 异常
	 */
	public ChinapayOrderResultDTO doQueryOrderState(String translateType, String orderNo,
			Date orderDate) throws Exception {
		// 参数校验
		B2cParamCheckHelper.checkOrderStateParam(orderNo, orderDate);

		return this.queryOrderState(translateType,
				ChinapayB2cConst.CHINAPAY_QRY_VERSION, orderNo, orderDate);
	}
	
	/**
	 * 主动请求银行获取订单的状态信息
	 * 
	 * @param translateType 交易类型(0001 支付交易, 0002退款交易)
	 * @param version   版本
	 * @param orderNo   订单号
	 * @param orderDate 订单日期
	 * @return 
	 * @throws Exception
	 * @exception 异常
	 * demo:
	 * <html>...<body>ResponeseCode=0&merid=808080201303096&orderno=0013111900123401&amount=000000000001&currencycode=156&transdate=20131121&transtype=0001&status=1001&checkvalue=207173A1D611B200DC06F9B0F07831A7F5BEF15C1D4DC142F7523242F31F65FDC31712822846770E5A3DC32AD0012244642E2C2AED4CE6A6C7EBC44E7AC5BEC2C84359AC718B937E0267D63D3D3A6D6A5CF96F1062D2ADA57E26E53AA50C7CCCD3BAF0AD589721FC23F5BD28C4A98C3657B2317216831297B7E75C3D90ABDA5B&GateId=0005&Priv1=dfsdf</body></html>
	 * <html>...<body>ResponeseCode=0&merid=808080201303096&orderno=0013112199999901&amount=000000000001&currencycode=156&transdate=20131121&transtype=0001&status=1001&checkvalue=4F0886A898B82820B97552702DF46D5E32C35E6F7C91EB30B3166E47DA671380BDB33A676508A21B19A3D618F8216B8C70D331F9630A0EB13960D5CDFC38735E69D8C9DA6ED9B4F7F627F337AAAFA7AF07DBD9147783B4130D312D9A9BC67F4907EEECAD908CA2A5B4173C0F5F76A50578E41F8C9CC2CF9C9F8EAAA80A52ACF5&GateId=0005&Priv1=dfsdf</body></html>
	 */
	private ChinapayOrderResultDTO queryOrderState(String translateType,
			String version, String orderNo, Date orderDate) throws Exception {
		if (BankTransType.REFUND.equals(translateType)) {
			throw new BankAdapterException("查询订单状态失败，原因：中国银联暂不支持退款状态的查询!");
		}

		// 查询订单数据准备
		String transDate = DateUtils.format2yyyyMMddDate(orderDate);
		PrivateKey privateKey = new PrivateKey();

		if (!privateKey.buildKey(mechantNo, 0, privateKeyFilePath)) {
			logger.error("queryPayOrderState: 中国银联私钥错误! mechantNo=" + mechantNo
					+ ", privateKeyFilePath =" + privateKeyFilePath);

			throw new BankAdapterException("查询订单状态失败，原因：中国银联私钥错误!");
		}

		String checkSrcStr = StringHelper.joinString(mechantNo, transDate,
				orderNo, translateType); // 0001是交易类型为消费交易; 0002是退款类型;

		// 生成商务的签名信息
		String chkValue = new SecureLink(privateKey).Sign(checkSrcStr);

		// 参数信息，同时也是数字签名源数据
		Map<String, String> map = new HashMap<String, String>();
		map.put(OrderStateReqKey.MER_ID, mechantNo);
		map.put(OrderStateReqKey.ORDER_ID, orderNo);
		map.put(OrderStateReqKey.TRANS_DATE, transDate);
		map.put(OrderStateReqKey.TRANS_TYPE, translateType);
		map.put(OrderStateReqKey.VERSION, version);
		map.put(OrderStateReqKey.RESV, "");
		map.put(OrderStateReqKey.CHK_VALUE, chkValue);

		// 返回的html内容
		String returnHtmlContent = ChinapayHttpHelper.sendHttpSSLMsg(
				bankGetOrderAddress, map);

		return this.parseReturnedResult(translateType, orderNo,
				returnHtmlContent);
	}
	
	/**
	 * 解析查询订单状态返回的html结果
	 * 
	 * @param bankTransType
	 * @param orderNo
	 * @param returnStr
	 * @return
	 */
	private ChinapayOrderResultDTO parseReturnedResult(String bankTransType,
			String orderNo, String returnStr) {
		// 将返回键值数据填充到Map对象中，方便后续处理
		Map<String, String> returnMap = ChinapayHttpHelper.parseReturnStr2Map(returnStr);
		
		if(null == returnMap) {
			throw new BankAdapterException("查询订单状态失败，原因：中国银联返回报文格式异常，无法解析!");
		}
		
		String respCode = returnMap.get(OrderStateRespKey.RESP_CODE);

		// 通过ResponeseCode可以判断查询是否成功, 查询成功ResponseCode的值为0
		if (CpRespCode.REQ_SUCCESS_0.equals(respCode)) {
			// 创建公钥对象，用其验证银联的签名信息
			PrivateKey publicKey = new PrivateKey();

			if (!publicKey.buildKey(ChinapayB2cConst.PUB_MERID, 0, bankPubKeyPath)) {
				logger.error("parseReturnedResult: 中国银联的公钥错误! mechantNo="
						+ mechantNo + ", bankPubKeyPath =" + bankPubKeyPath);

				throw new BankAdapterException("查询订单状态失败，原因：中国银联公钥错误!");
			}
			
			String merId = returnMap.get(OrderStateRespKey.MER_ID);// 商户号
			String currencyCode = returnMap.get(OrderStateRespKey.CCY_CODE);// 币种
			String bankStatus = returnMap.get(OrderStateRespKey.STATUS);// 银行响应状态码
			String bankRespOrderNo = returnMap.get(OrderStateRespKey.ORDER_NO);// 银行响应的订单号
			String strTransDate = returnMap.get(OrderStateRespKey.TRANS_DATE);// 订单日期
			String amount = returnMap.get(OrderStateRespKey.AMOUNT);// 订单金额
			String returnTransType = returnMap.get(OrderStateRespKey.TRANS_TYPE);// 返回的交易类型
			String checkValue = returnMap.get(OrderStateRespKey.CHK_VALUE);// 签名

			// 验证签名
			boolean isVerifySuccess = new SecureLink(publicKey).verifyTransResponse(merId,
							returnMap.get(OrderStateRespKey.ORDER_NO), amount, currencyCode, 
							strTransDate, returnTransType, bankStatus, checkValue);

			// 验证签名成功
			if (!isVerifySuccess) {
				logger.info("parseReturnedResult: 签名验证失败! returnStr="	+ returnStr);

				throw new BankAdapterException("查询订单状态失败，原因：验证中国银联签名失败!");
			}

			// 订单日期
			Date transDate = DateUtils.parse2yyyyMMddDate(strTransDate);

			ChinapayOrderStatus orderStatus = this.getDefaultStatus(bankTransType);

			ChinapayOrderResultDTO returnValue = new ChinapayOrderResultDTO(
					orderNo, orderStatus);
			returnValue.setCrrrency(currencyCode);// 人民币
			returnValue.setPayAmount(amount);
			returnValue.setTranTime(transDate);
			returnValue.setTransType(bankTransType);

			// 1001消费交易成功
			if (BankTransStatus.PAY_SUCCESS.equals(bankStatus)
					&& orderNo.equals(bankRespOrderNo)) {
				returnValue.setOrderStatus(ChinapayOrderStatus.PAY_SUCCESS);
			}
			// 1003退款提交成功
			else if (BankTransStatus.REFUND_SUCCESS.equals(bankStatus)
					&& orderNo.equals(bankRespOrderNo)) {
				returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_PROCESSING);
			}
			// 1005退款撤销成功
			else if (BankTransStatus.REFUND_CANCEL_SUCCESS.equals(bankStatus)
					&& orderNo.equals(bankRespOrderNo)) {
				returnValue.setOrderStatus(ChinapayOrderStatus.REFUND_CANCEL_SUCCESS);
			} else {
				if (orderNo.equals(bankRespOrderNo)) {
					logger.info("parseReturnedResult: 该订单状态为失败! 返回的信息为：" + returnStr);
					returnValue.setErrorInfo("该订单状态为失败，银行状态码：" + bankStatus);
				} else {
					logger.info("parseReturnedResult: 中国银联返回报文格式异常，无法解析! 返回的信息为：" + returnStr);
					throw new BankAdapterException("查询订单状态失败，原因：中国银联返回报文格式异常，无法解析！");
				}
			}
			
			return returnValue;
		}
		// {ResponeseCode=305, Message=超出流量控制范围!}
		else if (CpRespCode.EXCEED_FLOW_LIMIT_305.equals(respCode)) {
			throw new BankAdapterException(ErrorCode.TOO_FREQUENT,
					"查询订单状态失败, 原因：中国银联启动了流量控制, 不能查询过于频繁, 默认30秒内只能查询一次！");
		}
		// {ResponeseCode=307, Message=未查询到匹配数据}
		else if (CpRespCode.DATA_NOT_EXIST_307.equals(respCode)) {
			throw new BankAdapterException(ErrorCode.DATA_NOT_EXIST, "查询订单状态失败, 原因：没有查询到对应数据！");
		}
		else {
			// 打印返回的字符串
			logger.info("parseReturnedResult: ResponeseCode 返回码为不成功: returnStr=" + returnStr);

			// 解析错误信息
			String failReason = returnStr.replaceAll(".*Message=", "").replaceFirst("^\\r\\n", "");

			if (StringHelper.isEmpty(failReason)) {
				failReason = "中国银联返回报文格式异常，无法解析!";
			}

			throw new BankAdapterException("查询订单状态失败, 原因：" + failReason);
		}
	}
	
	/**
	 * 获取默认值
	 * 
	 * @param translateType
	 * @return
	 */
	private ChinapayOrderStatus getDefaultStatus(String translateType) {
		// 支付交易
		if (BankTransType.PAY.equals(translateType)) {
			return ChinapayOrderStatus.PAY_FAILED;
		}
		// 退款交易
		else if (BankTransType.REFUND.equals(translateType)) {
			return ChinapayOrderStatus.REFUND_FAILED;
		}
		
		return ChinapayOrderStatus.UNKNOWN;
	}

	public String getBankGetOrderAddress() {
		return bankGetOrderAddress;
	}

	public void setBankGetOrderAddress(String bankGetOrderAddress) {
		this.bankGetOrderAddress = bankGetOrderAddress;
	}
}
