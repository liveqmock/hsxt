/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.util.CommonUtil;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd63 
 * @Description: 63.1用法一：国际信用卡公司代码
 *                      63.1用法二：操作员代码
 *                      63.2 LL 积分金额
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:07:40 
 * @version V1.0
 */
public class Fd63 extends Afd {

	@Override
	public String doRequestProcess(String data,byte[] pversion) throws Exception {
		
		String returnStr = data.trim();//空格去掉  签到带空格
		int len = returnStr.length();
		
		CommonUtil.checkState(len < 2, "63域,操作员编号::" + data + "格式异常!", PosRespCode.REQUEST_PACK_FORMAT);
		
		//操作员编号 变长截取最后2位数
		if(Arrays.equals(pversion, PosConstant.POSOLDVERSION)){//新版本
			
			returnStr = returnStr.substring(len - 2);
		}

		return returnStr;
	}

	/**
	 * 63域：0006475954023530      0.5
	 * 63域：00084759540439343032  94.02
	 * 积分和积分撤单成功，返回国际代码+积分数
	 */
	@Override
	public String doResponseProcess(String messageId, Object data,byte[] pversion) throws Exception {
		String returnStr = null;
		
		Poi63 pointNumOutVo = (Poi63) data;
		String iccCode = pointNumOutVo.getIccCode();
		BigDecimal pointValue = pointNumOutVo.getVal();
		long pointValueInt = pointValue.multiply(new BigDecimal(PosConstant.POINTVALUEMULTI)).longValue();//积分数，组包时放大100倍

		returnStr = iccCode + pointValueInt;
		
		if(!Arrays.equals(pversion, PosConstant.POSOLDVERSION)){//新版本
			if (ReqMsg.POINT.getReqId().equals(messageId)
					|| ReqMsg.POINTCANCLE.getReqId().equals(messageId)
					|| ReqMsg.HSBPAY.getReqId().equals(messageId) 
					|| ReqMsg.HSBPAYCANCLE.getReqId().equals(messageId)
					|| ReqMsg.HSBPAYRETURN.getReqId().equals(messageId) 
					|| ReqMsg.HSORDERCASHPAY.getReqId().equals(messageId)
					|| ReqMsg.HSORDERHSBPAY.getReqId().equals(messageId)
					|| ReqMsg.HSORDERACCTCASHPAYREVERSE.getReqId().equals(messageId)
					|| ReqMsg.HSORDERACCTHSBPAYREVERSE.getReqId().equals(messageId)
					|| ReqMsg.EARNESTCANCEL.getReqId().equals(messageId)
					|| ReqMsg.EARNESTSETTLEACC.getReqId().equals(messageId)
					) {			
				
				//跟结构有关，待删除
				returnStr = new String(Hex.encodeHex(returnStr.getBytes(PosConstant.GBK_ENCODE)));
			}else {
				CommonUtil.checkState(true, "组织应答报文，63域拼装异常：请求业务类型错误:" + messageId, 
										PosRespCode.POS_CENTER_FAIL);
			}
		}else{//老版本
			if (ReqMsg.POINT.getReqId().equals(messageId) || ReqMsg.POINTCANCLE.getReqId().equals(messageId)) {
				returnStr = new String(Hex.encodeHex(returnStr.getBytes(PosConstant.GBK_ENCODE)));
				//最少有GYT(475954)+积分
				String codeAsc = returnStr.substring(0, 6);
				String pointAsc = returnStr.substring(6);
				String pointLen = StringUtils.leftPad(String.valueOf(pointValueInt).length() + "", 2, PosConstant.ZERO_CHAR);
				returnStr = codeAsc + pointLen + pointAsc;
			}else {
				CommonUtil.checkState(true, "63域应答未知消息ID:" + messageId, PosRespCode.POS_CENTER_FAIL);
			}
		}
		SystemLog.debug("Fd63", "doResponseProcess()", "处理结果信息返回给pos（63域），returnStr: " + returnStr);
		return returnStr;
	}

	//失败或其他业务都是只有国际代码
	@Override
	public String doResponseProcess(Object data) throws Exception {
		
		String returnStr = (String) data;
		returnStr = new String(Hex.encodeHex(returnStr.getBytes(PosConstant.GBK_ENCODE)));
		return returnStr;
	}
}
