/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.points.service;

import com.gy.hsxt.common.constant.RespCode;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.points.handle.CheckCardHandle;
import com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService;

/**
 * @Package: com.gy.hsxt.ps.points.service
 * @ClassName: CheckCardService
 * @Description: 验证互生卡
 * 
 * @author: chenhz
 * @date: 2016-1-26 上午9:38:31
 * @version V3.0
 */
@Service
public class CheckCardService
{

	// 用户中心服务
	@Autowired
	private IUCAsDeviceSignInService ucAsDeviceSignInService;

	/**
	 * 跨境交易
	 * 
	 * @param cardParams
	 * @throws HsException
	 */
	public void checkCard(JSONObject cardParams) throws HsException
	{
		// 卡号输入方式
		String inputWay = cardParams.getString("inputWay");
		// 是否有密码
		String isPin = cardParams.getString("isPin");
		// pos终端编码
		String posNo = cardParams.getString("posNo");
		// 企业互生号
		String entNo = cardParams.getString("entNo");
		// 消费者互生号
		String custCardNo = cardParams.getString("custCardNo");
		// 消费者互生卡暗码
		String custCardCode = cardParams.getString("custCardCode");
		// 密码密文
		byte[] pinData = Base64.decodeBase64(cardParams.getString("pinData"));
		// 密码长度
		String servicePinLen = cardParams.getString("servicePinLen");
		// 交易类型
		String transType = cardParams.getString("transType");

		String pwd = null;
		if ("1".equals(isPin))
		{
			byte[] arrGyPin = ucAsDeviceSignInService.getDecrypt(posNo, entNo, pinData);
			if (arrGyPin == null || arrGyPin.length != 8)
				// throw new PosException(PosRespCode.POS_CENTER_FAIL,
				// "调用key server解密接口返回密钥格式异常！arrGyPin:" + arrGyPin);
				pwd = CheckCardHandle.decryptWithANSIFormat(arrGyPin, custCardNo, servicePinLen);
			if (TransType.LOCAL_CARD_REMOTE_HSB.getCode().equals(transType)
			// || TransType.HS_ORDER_PAY.equals(transType) //后续增加电商订单的跨地区交易
			)
			{
				CheckCardHandle.checkState(pwd == null || pwd.length() != 8, "密码长度有误",
						RespCode.CHECK_CARDORPASS_FAIL);
			} else
			{// 其余的都是登录密码
				CheckCardHandle.checkState(pwd == null || pwd.length() != 6, "密码长度有误",
						RespCode.CHECK_CARDORPASS_FAIL);
			}
		}

		// 开始校验
		if ("01".equals(inputWay))
		{// 手动输入
			ucAsDeviceSignInService.checkResNoAndLoginPwd(custCardNo,
					CheckCardHandle.string2MD5(pwd));
		} else if ("02".equals(inputWay))
		{// 刷卡
		 // 不是撤单退货的，输入的是交易密码，所以只校验 卡号 + 暗码（支付时的校验另有5.3完成）
			if (!TransType.LOCAL_CARD_REMOTE_POINT_CANCEL.getCode().equals(transType)
					&& !TransType.LOCAL_CARD_REMOTE_HSB_CANCEL.getCode().equals(transType)
					&& !TransType.LOCAL_CARD_REMOTE_HSB_BACK.getCode().equals(transType))
			{
				ucAsDeviceSignInService.checkResNoAndCode(custCardNo, custCardCode);
			} else
			{// 撤单或退货类业务 输入的是登录密码
				ucAsDeviceSignInService.checkResNoPwdAndCode(custCardNo,
						CheckCardHandle.string2MD5(pwd), custCardCode);
			}
		}

		// 5.3 有要求则进行支付密码校验
		if ("1".equals(cardParams.getString("payPwd")))
		{
			ucAsDeviceSignInService.checkResNoAndTradePwd(custCardNo,
					CheckCardHandle.string2MD5(pwd));
		}
	}

}
