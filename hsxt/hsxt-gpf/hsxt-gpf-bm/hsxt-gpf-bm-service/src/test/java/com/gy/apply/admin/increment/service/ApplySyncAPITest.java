package com.gy.apply.admin.increment.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**   
 * Simple to Introduction
 * @category      Simple to Introduction
 * @projectName   apply-incurement
 * @package       com.gy.apply.admin.increment.service.ApplySyncAPITest.java
 * @className     ApplySyncAPITest
 * @description   一句话描述该类的功能 
 * @author        cheny
 * @createDate    2014-11-12 下午12:05:28  
 * @updateUser    cheny
 * @updateDate    2014-11-12 下午12:05:28
 * @updateRemark  说明本次修改内容
 * @version       v0.0.1
 */
public class ApplySyncAPITest {
/*	private static final String targetSysNo = Constants.SUB_SYS_NO_APPLY;
	
	@Test
	public void getSltdApplySumInfo(){
		Message message = new Message();
		message.setMessageType("服务公司月申报企业数量统计");
		Pack<Map<String, String>> pack = new Pack<Map<String, String>>();
		pack.setBusiCode(APPLYBusiCode.F_APPLY_SLTD_DECLARE_INFO.name());
		Map<String, String> params = new HashMap<>();
		params.put("year", "2015");
		params.put("month", "02");
		//type取值:json,返回示例
//		{
//		    "result": {
//		        "06186000000": 3,
//		        "01001000000": "2",
//		        "01002000000": "1"
//		    },
//		    "respCode": "SUCCESS"
//		}
		//type取值:csv,返回示例
//		{
//		    "respCode": "SUCCESS",
//		    "result": "/apply/2015/02/1427710162324.csv"
//		}
		params.put("dataType", "json");//json、csv 不区别大小写
		pack.setParamBody(params);
		message.setMessageData(pack);
		try {
			RespInfo<?> response = (RespInfo<?>)Ecms.getSyncMessager().sendSyncMessage("apply",message);
			System.out.println(JSON.toJSONString(response));
			if(params.get("dataType").equals("json")){
				Map<String, Object> result = (Map<String, Object>) response.getResult();
				for(Map.Entry<String, Object> entry:result.entrySet()){    

				} 
			}
		} catch (EcmsSyncMessageException e) {System.out.println("同步消息发送时发生异常：" + e.getErrorMsg());
		}
	}
	
//	@Test
	public void applyPayNotify(){
		Message message = new Message();
		message.setMessageType("支付/核款通知");
		Pack<NotifiyParam> pack = new Pack<NotifiyParam>();
		pack.setBusiCode(APPLYBusiCode.F_APPLY_PAY_NOTIFY.name());
		NotifiyParam param = new NotifiyParam();
		param.setAmount("0000000000023000");//付款金额
		param.setBankBillno("");//银行订单号，若为内部现金余额支付则为空
		param.setBankType("");//付款银行类型
		param.setCurrency("CNY");//CNY-人民币,HKD-港币，USD-美元；国际标准码
		param.setPayInfo("支付成功");//支付结果信息
		param.setReqSequNo("GY014PAYN242511091112117906");//请求方流水号
		param.setSequNo("ZHCNCNY1411120000152");//支付系统交易流水号
		param.setSign("75eadd906e4a98a4b054f26c2705031f");//加密印鉴
		param.setSignModel("MD5");//创建印鉴方法,MID5, AES,DES,RSA等，目前只支持MID5
		param.setSubSysId(targetSysNo);//子系统号,参见T_PAY_SUB_SYS_USED.SUB_SYS_ID,标明支付请求来源于哪儿，在系统中是否注册过
		param.setTimeEnd("20141112100640");//支付完成时间，格式为yyyyMMddhhmmss，如2009年12月27日9点10分10秒表示为20091227091010。时区为GMT+8 beijing。该时间取自支付系统服务器
		param.setTradeMode("1");//交易模式,1—即时到账,2-不实时到账
		param.setTradeState("0");//支付结果：0—成功 1—失败
		pack.setParamBody(param);
		message.setMessageData(pack);
		try {
			@SuppressWarnings("unchecked")
			RespInfo<MayApplySResponse> response = (RespInfo<MayApplySResponse>)Ecms.getSyncMessager().sendSyncMessage(targetSysNo,message);
			if(RespCode.SUCCESS == response.getRespCode()){
				System.out.println(response.getResult());
			}else{
				String respMsg = RespMsgBundle.getRespText(response.getRespCode());
				if(respMsg==null){
					respMsg = response.getErrorMsg();
				}
				System.out.println(""+response.getRespCode()+":"+response.getRespCode().getCode()+"\t"+respMsg);
			}
		} catch (EcmsSyncMessageException e) {System.out.println("同步消息发送时发生异常：" + e.getErrorMsg());
		}
	}*/
}