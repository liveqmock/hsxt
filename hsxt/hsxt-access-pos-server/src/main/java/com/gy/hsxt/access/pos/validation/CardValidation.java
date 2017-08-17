/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.validation;



import java.util.Arrays;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosUtil;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: CardValidation 
 * @Description: 卡信息校验 预处理
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:43:18 
 * @version V1.0
 */
@Service("cardValid")
public class CardValidation implements Command {

	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	/**
	 * return false 会继续执行chain中后续的command，return true就不会了。
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		SystemLog.info("CardValidation","execute()","validation card");
		
		Cmd cmd = (Cmd)context.get("cmd");
		
		String reqId = cmd.getReqId();
		
		PosReqParam param = cmd.getPosReqParam();
		
		String entNo = param.getEntNo();
		String posNo = param.getPosNo();
		
		String inputWay = param.getInputWay();
		String isPin = param.getIsPin();
		
		if(StringUtils.isEmpty(inputWay) || StringUtils.isEmpty(isPin)){
			CommonUtil.checkState(true, "8583报文异常, 服务点输入方式码为空", PosRespCode.REQUEST_PACK_FORMAT);
		}
		
		String accNo = null;//账号
		String cardCode = null;//暗码
		String pwd = null;
		if(PosConstant.INPUT_WAY_STRIPE.equals(inputWay)){
			String cardNo = param.getStripe2();
			
			SystemLog.debug("", "", "------------二磁道 卡号 cardNo = " + cardNo);
			
			CommonUtil.checkState(null == cardNo || cardNo.length() != PosConstant.CARDNO_CIPHER_LENGTH, 
					"8583报文异常, 刷卡长度不符合,cardNo：" + cardNo, PosRespCode.REQUEST_PACK_FORMAT);
			accNo = StringUtils.left(cardNo, PosConstant.CARDNO_LENGTH);
			cardCode = StringUtils.right(cardNo, PosConstant.CIPHER_LENGTH);
			param.setCardNo(accNo);
		}else if (PosConstant.INPUT_WAY_MANUAL.equals(inputWay)) {
			String cardNo = param.getCardNo();
			
			CommonUtil.checkState(cardNo.length() != PosConstant.CARDNO_LENGTH,"8583报文异常, 手输卡长度不符合,cardNo：" + cardNo, PosRespCode.REQUEST_PACK_FORMAT);
			accNo = cardNo;
			
		}else if (PosConstant.INPUT_WAY_0.equals(inputWay)) {//未指明  企业兑换用
			accNo = entNo;
		}else {// 其他日志记录异常
			CommonUtil.checkState(true, "8583报文异常", PosRespCode.REQUEST_PACK_FORMAT);
		}
		
		if(PosConstant.PIN_1.equals(isPin)){
			
			//每次签到生成的pik/mak不一样，生成的pin码和mac码会发生改变
			byte[] pinData = param.getPinDat();
			
			CommonUtil.checkState(null == pinData || pinData.length != 8,"8583报文异常, 交易中包含PIN格式异常！pinDat: " + pinData, PosRespCode.PIN_FORMAT);
			
			// 调用 uc 解密 pin
//			byte[] arrGyPin = this.decrypt(entNo, posNo, operNo, pinData, cmd.getPartVersion());
			byte[] arrGyPin = ucApiService.getDecrypt(posNo, entNo, pinData);
			
			CommonUtil.checkState(arrGyPin == null || arrGyPin.length != 8, "调用key server解密接口返回密钥格式异常！arrGyPin:" + arrGyPin, PosRespCode.POS_CENTER_FAIL);
			String servicePinLen = param.getServicePinLen();//密码最大明文长度
			
			if (StringUtils.isEmpty(servicePinLen)) {
			    SystemLog.warn("Pos","CardValidation","servicePinLen isEmpty..");
			}
			
			
			SystemLog.debug("CardValidation", "decryptWithANSIFormat", "---------------arrGyPin:" + 
					Arrays.toString(arrGyPin) + "; accNo:" + accNo + "; servicePinLen:"+ servicePinLen);//kend test
			
			pwd = PosUtil.decryptWithANSIFormat(arrGyPin, accNo, servicePinLen);
			if (ReqMsg.HSBPAY.getReqId().equals(reqId)
                    || ReqMsg.HSORDERHSBPAY.getReqId().equals(reqId)
                    || ReqMsg.HSBENTRECHARGE.getReqId().equals(reqId)) {//交易密码放到action上，不能漏过
                CommonUtil.checkState(pwd == null || pwd.length() != 8, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
            } else {
                CommonUtil.checkState(pwd == null || pwd.length() != 6, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
            }
			
		} else if (PosConstant.PIN_2.equals(isPin)) {
		    //交易中不包含PIN
		} else {
			throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "无效的其他服务点输入方式码异常！");
		}
		
		param.setCardPwd(pwd);
		
		/**
		 * @author wucl create_date 2015-11-11 下午6:51:28
		 * 老版本1.0的pos 不用支持了
		 * 这里没对兑换互生币做密码校验！！！
		 */
//		if (PosConstant.INPUT_WAY_MANUAL.equals(inputWay) || Arrays.equals(pversion, PosConstant.POSOLDVERSION)) {
		
		//手动输入
		if (PosConstant.INPUT_WAY_MANUAL.equals(inputWay)){
			
			// 调用uc 参数 卡号 密码  校验
			ucApiService.checkResNoAndLoginPwd(accNo, CommonUtil.string2MD5(param.getCardPwd()));
		}else if(PosConstant.INPUT_WAY_STRIPE.equals(inputWay)){
			
			if(!ReqMsg.POINTCANCLE.getReqId().equals(reqId)
					&& !ReqMsg.HSBPAYCANCLE.getReqId().equals(reqId) 
					&& !ReqMsg.HSBPAYRETURN.getReqId().equals(reqId)){
				
				// 调用uc 参数 卡号 + 暗码  校验
				// 此类交易输入的是交易密码，所以只校验	卡号 + 暗码
				ucApiService.checkResNoAndCode(accNo, cardCode);
			}else{
				
				// 调用uc 参数 卡号 + 暗码  校验  和  卡号 + 密码  校验 二种
				// 撤单或退货类业务 输入的是登录密码， 所以需要校验
			    ucApiService.checkResNoPwdAndCode(accNo, CommonUtil.string2MD5(param.getCardPwd()), cardCode);
			}
		}
		
		return false;
	}
}

	