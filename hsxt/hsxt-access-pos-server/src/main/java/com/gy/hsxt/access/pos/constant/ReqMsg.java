/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.constant;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.system.constant
 * 
 *  File Name       : RequestMessageIdEnum.java
 * 
 *  Creation Date   : 2014-6-16
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : 请求消息ID:消息类型ID-3域-25域-60域 
 *   消费积分通用   point-sever  pss
 * 
 * </PRE>
 ***************************************************************************/
public enum ReqMsg {
	
	
	POINT(PosConstant.REQ_POINT_ID, PosConstant.RESP_POINT_ID,PosConstant.POINT_TYPE,"消费积分"), //积分
	POINTCANCLE(PosConstant.REQ_POINT_CANCEL_ID, PosConstant.RESP_POINT_ID,PosConstant.POI_CANCEL_TYPE,"消费积分撤单"), //积分撤单
	POINTREVERSE(PosConstant.REQ_POINT_REVERSE_ID, PosConstant.POINTREVERSETYPEREP,PosConstant.POI_REVERSE_TYPE,"消费积分冲正"), //积分冲正
	POINTCANCLEREVERSE(PosConstant.REQ_POINT_CANCEL_REVERSE_ID, PosConstant.POINTREVERSETYPEREP,PosConstant.POI_CANCEL_REVERSE_TYPE,"消费积分撤单冲正"), //积分撤单冲正
	POINTDAYSEARCH(PosConstant.REQ_POINT_DAY_SEARCH_ID, PosConstant.POINTSEARCHTYPEREP,PosConstant.POI_DAY_SEARCH_TYPE,"积分当日查询"), //积分当日查询
	POINTORDERSEARCH(PosConstant.REQ_POINT_ORDER_SEARCH_ID, PosConstant.POINTSEARCHTYPEREP,PosConstant.POI_RUN_ACC_SEARCH_TYPE,"刷卡终端单笔查询"), //积分流水查询
	BATCHSETTLE(PosConstant.REQ_BATCH_SETTLE_ID, PosConstant.BATCHSETTLETYPEREP,PosConstant.BAT_STL_TYPE,"批结算"), //批结算
	BATCHUPLOAD(PosConstant.REQ_BATCH_UPLOAD_ID, PosConstant.BATCHUPLOADTYPEREP,PosConstant.BAT_UPLOAD_TYPE,"批上送"), //批上送
	SIGNIN(PosConstant.REQ_SIGNIN_ID, PosConstant.SIGNINTYPEREP,PosConstant.SIGNIN_TYPE,"签到"), //签到
	SIGNOFF(PosConstant.REQ_SIGNOFF_ID, PosConstant.SIGNOFFTYPEREP,PosConstant.SIGNOFF_TYPE,"签退"), //签退
	SYNCPARAM(PosConstant.REQ_PARAM_SYNC_ID, PosConstant.PARAMTYPEREP,PosConstant.SYNC_PARAM_TYPE,"同步参数"), //同步参数
	UPLOADPARAM(PosConstant.REQ_PARAM_UPLOAD_ID, PosConstant.PARAMTYPEREP,PosConstant.UPLOAD_PARAM_TYPE,"上传参数"), //上传参数
	POINTCARDCHECK(PosConstant.REQ_CARD_CHREQ_ID, PosConstant.CARDCHECKTYPEREP,PosConstant.POI_CARD_CHK_TYPE,"积分卡密码验证"),//积分卡密码验证
	POSUPGRADECHECK(PosConstant.REQ_POS_UPGRADE_CHECK_ID, PosConstant.POSUPGRADECHECKREP,PosConstant.POS_UPGRADE_CHECK_TYPE,"固件升级检测"),//固件升级检测
	
	
	/**
	 * 互生币支付 默认  终端  流通
	 */
	HSBPAY(PosConstant.REQ_HSB_PAY_CCY_ID, PosConstant.HSPAYTYPEREP,PosConstant.HSB_PAY_TYPE,"互生币消费"),
			
	/**
	 * 互生币支付  终端  定向
	 */
	HSBPAY1(PosConstant.REQ_HSB_PAY_DC_ID, PosConstant.HSPAYTYPEREP,PosConstant.HSB_PAY_TYPE1,"互生币消费"),
	
	
	
	
	
	/**
	 * 2015/04/16 虽然目前PSS不需要，但TestCases很需要。相关的功能也就几行代码，顺便加上了。
	 * 游客网银支付
	 */
	VISITORONBANKPAY("",new byte[0],"","游客网银支付"),
	
	/**
	 * 游客现金支付  2015/04/16 添加。
	 */
	VISITORCASHPAY("",new byte[0],"","游客现金支付"),
	
	
	
	
	
	/**
	 * 互生币支付撤单
	 */
	HSBPAYCANCLE(PosConstant.REQ_HSB_PAY_CANCEL_ID, PosConstant.HSCANCLETYPEREP,PosConstant.HSB_PAY_CANCEL_TYPE,"互生币撤单"),
	/**
	 * 互生币支付退货		
	 */
	HSBPAYRETURN(PosConstant.REQ_HSB_PAY_RETURN_ID, PosConstant.HSRETURNTYPEREP,PosConstant.HSB_PAY_RETURN_TYPE,"互生币退货"),

		
			
			
	/**
	 * 30
	 * 互生订单现金支付  业务接口
	 */
	HSORDERCASHPAY(PosConstant.REQ_HSORDER_CASH_PAY_ID,  PosConstant.HSORDERTYPEREP,PosConstant.HSORDER_CASH_PAY_TYPE,"互生订单现金支付"),
	/**
	 * 互生订单互生币支付  业务接口
	 */
	HSORDERHSBPAY(PosConstant.REQ_HSORDER_HSB_PAY_ID, PosConstant.HSORDERTYPEREP,PosConstant.HSORDER_HSB_PAY_TYPE,"互生订单互生币支付"),
			
	/**
	 * 互生订单现金支付冲正  业务接口
	 */
	HSORDERCASHPAYREVERSE(PosConstant.REQ_HSORDER_CASH_PAY_REVERSE_ID, PosConstant.POINTREVERSETYPEREP,PosConstant.HSORDER_CASH_PAY_REVERSE_TYPE,"互生订单现金支付冲正"),
	/**
	 * 互生订单互生币支付冲正  业务接口
	 */
	HSORDERHSBPAYREVERSE(PosConstant.REQ_HSORDER_HSB_PAY_REVERSE_ID, PosConstant.POINTREVERSETYPEREP,PosConstant.HSORDER_HSB_PAY_REVERSE_TYPE,"互生订单互生币支付冲正"),			
	
			
	
	/**
	 * 互商订单通知可结算
	 */
	HSECORDERSETTLENOTIFY("",new byte[0],"","互商订单通知可结算"),
	HSEC_ORDER_CASH_SETTLE_NOTIFY("",new byte[0],"","互商订单现金通知可结算"),
	
	/**
	 * 互商结算查询
	 */
	HSECSETTLESEARCH("",new byte[0],"","互商结算查询"),
	/**
	 * 互商流水查询
	 */
	HSECRUNCODESEARCH("",new byte[0],"","互商流水查询"),	
	
	
	/**
	 * 代兑互生币(HSBC)
	 */
	HSBPROXYRECHARGE(PosConstant.REQ_HSB_PROXY_RECHARGE_ID, PosConstant.HSBCRECTYPEREP,PosConstant.HSB_C_RECHARGE_TYPE,"代兑互生币"),
	/**
	 * 兑换互生币(HSBB)
	 */
	HSBENTRECHARGE(PosConstant.REQ_HSB_ENT_RECHARGE_ID, PosConstant.HSBBRECTYPEREP,PosConstant.HSB_B_RECHARGE_TYPE,"兑换互生币"),	
	
	
	/**
	 * 积分预付款查询(企业积分)  账务
	 */
	ASSURESEARCH(PosConstant.REQ_BALANCE_SEARCH_ID, PosConstant.POINTSEARCHTYPEREP,PosConstant.ASSURE_SEARCH_TYPE,"积分预付款查询"),
			
			
//	/**
//	 * 货币账户余额查询    账务
//	 */
//	CASHSEARCH(PosConstant.CASH_SEARCH_ID, 
//			PosConstant.POINTSEARCHTYPEREP,CASH_SEARCH_TYPE,"货币账户余额查询"),
//	/**
//	 * 互生币账户余额查询(经营收入账户余额和慈善救助基金账户余额) 账务
//	 */
//	SALESEARCH(PosConstant.SALE_SEARCH_ID, 
//			PosConstant.POINTSEARCHTYPEREP,SALE_SEARCH_TYPE," 互生币账户余额查询"),
			
			
	/**
	 * 积分卡姓名查询
	 */
	CARDNAMESEARCH(PosConstant.REQ_CARDNAME_SEARCH_ID, PosConstant.POINTSEARCHTYPEREP,PosConstant.CARDNAME_SEARCH_TYPE,"积分卡姓名查询"),
	
	/**
	 * 互生订单列表查询
	 * 
	 *  必需刷卡查询
	 */
	HSORDERLISTSEARCH(PosConstant.REQ_HSORDERS_SEARCH_ID, PosConstant.POINTSEARCHTYPEREP,PosConstant.HSORDERS_SEARCH_TYPE,"互生订单列表查询"),		
	/**
	 * 互生订单单笔查询
	 * 
	 * 手输订单查询
	 */
	HSORDERSEARCH(PosConstant.REQ_HSORDER_SEARCH_ID, PosConstant.POINTSEARCHTYPEREP,PosConstant.HSORDER_SEARCH_TYPE,"互生订单单笔查询"),

	/**
	 * 互生币支付冲正
	 */
	HSBPAYREVERSE(PosConstant.REQ_HSB_PAY_REVERSE_ID, PosConstant.POINTREVERSETYPEREP,PosConstant.HSB_PAY_REVERSE_TYPE,"互生币支付冲正"),
	/**
	 * 互生币支付撤单冲正
	 */
	HSBPAYCANCLEREVERSE(PosConstant.REQ_HSB_PAY_CANCEL_REVERSE_ID, PosConstant.POINTREVERSETYPEREP, PosConstant.HSB_PAY_CANCEL_REVERSE_TYPE,"互生币撤单冲正"),
	/**
	 * 互生币支付退货冲正	
	 */
	HSBPAYRETURNREVERSE(PosConstant.REQ_HSB_PAY_RETURN_REVERSE_ID, PosConstant.POINTREVERSETYPEREP, PosConstant.HSB_PAY_RETURN_REVERSE_TYPE,"互生币退货冲正"),

			
			
	/**
	 * 互生订单现金支付冲正
	 */
	HSORDERACCTCASHPAYREVERSE(PosConstant.REQ_HSORDER_CASH_PAY_REVERSE_ID, PosConstant.POINTREVERSETYPEREP, PosConstant.HSORDER_CASH_PAY_REVERSE_TYPE,"互生订单现金支付冲正"),
	/**
	 * 互生订单互生币支付冲正
	 */
	HSORDERACCTHSBPAYREVERSE(PosConstant.REQ_HSORDER_HSB_PAY_REVERSE_ID, PosConstant.POINTREVERSETYPEREP, PosConstant.HSORDER_HSB_PAY_REVERSE_TYPE,"互生订单互生币支付冲正"),
	/**
	 * 互生订单现金支付退款冲正
	 */
	HSORDERACCTCASHPAYRETURNREVERSE("", new byte[0], PosConstant.HSORDER_CASH_PAY_REVERSE_TYPE,"互生订单现金支付退款冲正"),
	/**
	 * 互生订单互生币支付退款冲正
	 */
	HSORDERACCTHSBPAYRETURNREVERSE("", new byte[0], PosConstant.HSORDER_HSB_PAY_REVERSE_TYPE,"互生订单互生币支付退款冲正"),	
			
			
	/**
	 * 代兑互生币冲正
	 */
	HSBCRECHARGEREVERSE(PosConstant.REQ_HSB_PROXY_RECHARGE_REVERSE_ID, PosConstant.POINTREVERSETYPEREP, PosConstant.HSB_PROXY_RECHARGE_REVERSE_TYPE,"代兑互生币冲正"),
			
	/**
	 * 兑换互生币冲正
	 */
	HSBBRECHARGEREVERSE(PosConstant.REQ_HSB_ENT_RECHARGE_REVERSE_ID, PosConstant.POINTREVERSETYPEREP, PosConstant.HSB_ENT_RECHARGE_REVERSE_TYPE, "兑换互生币冲正"),
	
	
	//定金业务
	/** 预付定金  kend*/
	EARNESTPREPAY(PosConstant.REQ_EARNEST_PREPAY_ID, PosConstant.EARNESTPREPAYREP,PosConstant.EARNEST_PREPAY_TYPE,"预付定金"),
	/** 定金查询  kend*/
	EARNESTSEARCH(PosConstant.REQ_EARNEST_SEARCH_ID, PosConstant.EARNESTSEARCHREP,PosConstant.EARNEST_SEARCH_TYPE,"定金查询"),
	/** 定金结算  kend*/
	EARNESTSETTLEACC(PosConstant.REQ_EARNEST_SETTLE_ACC_ID, PosConstant.EARNESTSETTLEACCREP,PosConstant.EARNEST_SETTLEACC_TYPE,"定金结算"),
	/** 定金撤单  kend*/
	EARNESTCANCEL(PosConstant.REQ_EARNEST_CANCEL_ID, PosConstant.EARNESTCANCELREP,PosConstant.EARNEST_CANCEL_TYPE,"定金撤销"),
	/** 定金冲正  kend*/
	EARNESTREVERSE(PosConstant.REQ_EARNEST_REVERSE_ID, PosConstant.EARNESTPREPAYREVERSEREP,PosConstant.EARNEST_PREPAY_REVERSE_TYPE,"定金冲正"),
	/** 定金结算冲正  kend*/
	EARNESTSETTLEACCREVERSE(PosConstant.REQ_EARNEST_SETTLE_REVERSE_ID, PosConstant.EARNESTSETTLEREVERSEREP,PosConstant.EARNEST_SETTLEACC_REVERSE_TYPE,"预付定金结算冲正"),
	/** 定金撤销冲正  kend*/
	EARNESTCANCELREVERSE(PosConstant.REQ_EARNEST_CANCEL_REVERSE_ID, PosConstant.EARNESTCANCELREVERSEREP,PosConstant.EARNEST_CANCEL_REVERSE_TYPE,"定金撤销冲正"),
	
	//start--added by liuzh on 2016-06-23
	/** 查询可撤单积分交易*/
	POINTORDERSCANCELSEARCH(PosConstant.REQ_POINT_ORDER_CANCEL_SEARCH_ID, PosConstant.POINTORDERSSEARCHREP,PosConstant.POINT_ORDERS_SEARCH_TYPE,"查询可撤单积分交易记录"),	
	/** 查询可退货积分交易*/
	POINTORDERSBACKSEARCH(PosConstant.REQ_POINT_ORDER_BACK_SEARCH_ID, PosConstant.POINTORDERSSEARCHREP,PosConstant.POINT_ORDERS_SEARCH_TYPE,"查询可撤单积分交易记录"),	
	//end--added by liuzh on 2016-06-23
	
	
		
	/**
	 * 2015/05/14 根据EC的要求新增。
	 */
	POINT_SEARCH("",new byte[0],"","某项积分的查询"),
	/**
	 * 终端列表分页查询
	 */
	TERMLISTSEARCH("",new byte[0],"","终端列表分页查询"),
	/**
	 * 通过实体店ID查询终端列表
	 */
	TERMSHOPIDSEARCH("",new byte[0],"","通过实体店ID查询终端列表"),
	/**
	 * 终端设备更新状态
	 */
	TERMSTATUSUPDATE("",new byte[0],"","终端设备更新状态"),	
	/**
	 * 实体店终端设备关联
	 */
	POSBINDSHOP("",new byte[0],"","实体店终端设备关联"),
	/**
	 * 实体店信息修改
	 */
	SHOPUPDATE("",new byte[0],"","实体店信息修改"),	
	/**
	 * 单台终端积分比例查询
	 */
	TERMPOINTRATESEARCH("",new byte[0],"","单台终端积分比例查询"),
	/**
	 * 企业积分比例注册
	 */
	POINTRATESREGISTERED("",new byte[0],"","企业积分比例注册"),
	/**
	 * 企业积分比例修改
	 */
	POINTRATESUPDATE("",new byte[0],"","企业积分比例修改"),
	
	
	//  电商调用参数
	
	/**
	 * 企业积分登记（2015/05/14 网上积分登记，也用这个）
	 * 
	 * 持卡人 
	 */
	BUSPOINT("",new byte[0], PosConstant.POINT_TYPE,"企业积分登记"),
	
	/**
	 * 企业积分登记撤销
	 * 持卡人 退积分
	 */
	BUSPOINTCANCEL("",new byte[0], PosConstant.POI_CANCEL_TYPE,"企业积分登记撤销"),
	
	/**
	 * 企业积分登记冲正
	 */
	BUSPOINTREVERSE("",new byte[0], PosConstant.POI_REVERSE_TYPE,"企业积分登记冲正"),	
	
	
	/**
	 * 企业积分登记撤销冲正
	 */
	BUSPOINTCANCELREVERSE("",new byte[0], PosConstant.POI_REVERSE_TYPE,"企业积分登记撤销冲正"),
	
	
	/**
	 * 游客消费积分
	 */
	VISITORPOINT("",new byte[0], PosConstant.POINT_TYPE,"游客消费积分"),
	
	/**
	 * 游客消费积分撤单
	 * 非持卡人 退积分
	 */
	VISITORPOINTCANCEL("",new byte[0], PosConstant.POI_CANCEL_TYPE,"游客消费积分撤单"),	
	
	
	/**
	 * 游客消费积分冲正
	 */
	VISITORPOINTREVERSE("",new byte[0], PosConstant.POI_REVERSE_TYPE,"游客消费积分冲正"),	
	/**
	 * 游客消费积分撤单冲正
	 */
	VISITORPOINTCANCELREVERSE("",new byte[0], PosConstant.POI_CANCEL_REVERSE_TYPE,"游客消费积分撤单冲正"),	
	
	
	
	//支付业务
	/**
	 * 
	 * 互生订单现金支付  账务支付接口
	 */
	HSORDERACCTCASHPAY("",new byte[0],"","互生订单现金账务支付"),
	/**
	 * 互生订单互生币支付  账务支付接口
	 */
	HSORDERACCTHSBPAY("",new byte[0],"","互生订单互生币账务支付"),
	/**
	 * 互生订单网银支付   账务支付接口
	 */
	HSORDERACCTONBANKPAY("",new byte[0],"","互生订单网银支付"),
	
	
	//持卡人 撤单   游客无撤单
	/**
	 * 互生订单互生币支付 定向消费币撤单  账务接口，对应了T_ACT_HSEC_HSB_DC_EMALL_CANCEL_PAY
	 * 2015/03/30 新加的，让HSEC用1条指令完成撤单，而不是2条。
	 */
	HS_ORDER_ACCT_HSB_PAY_CANCEL_DC("",new byte[0],"","互生订单互生币账务支付 定向消费币撤单"),
	/**
	 * 互生订单互生币支付  流通币撤单 账务接口，对应了T_ACT_HSEC_HSB_CCY_EMALL_CANCEL_PAY
	 * 2015/03/30 新加的，让HSEC用1条指令完成撤单，而不是2条。
	 */
	HS_ORDER_ACCT_HSB_PAY_CANCEL_CCY("",new byte[0],"","互生订单互生币账务支付 流通币撤单"),
	
	/**
	 * 互生订单网银支付撤单。2015/03/30 新增的。
	 */
	HSORDERACCTONBANKPAYCANCEL("",new byte[0],"","互生订单网银支付撤单"),
	
	
	
	
	//持卡人 退款
	/**
	 * 互生订单现金支付退款
	 */
	HSORDERACCTCASHRETURN("",new byte[0],"","互生订单现金支付退款"),
	/**
	 * 互生订单互生币支付退款
	 */
	HSORDERACCTHSBRETURN("",new byte[0],"","互生订单互生币支付退款"),
	/**
	 * 互生订单网银支付退款
	 */
	HSORDERACCTONBANKPAYRETURN("",new byte[0],"","互生订单网银支付退款"),
	
	
	//游客 退款
	/**
	 * 游客现金退货  2015/04/07 添加。
	 */
	VISITORCASHPAYRETURN("",new byte[0],"","游客现金退货"),

	/**
	 * 游客网银支付退款
	 */
	VISITORONBANKPAYRETURN("",new byte[0],"","游客网银支付退款"),
	//------------------------------------电商  end   

	
	//查询
	
	/**
	 * 积分明细分页查询。只有雷亚涛那边才用过这个功能，只针对网上企业积分登记。
	 */
	POINTLISTSEARCH("",new byte[0],"","积分明细分页查询"),

	
	/**
	 * 2015/04/25
	 */
	TERMINAL_TX_LIST(StringUtils.EMPTY, ArrayUtils.EMPTY_BYTE_ARRAY, StringUtils.EMPTY, "终端交易列表查询"),
	
	
	TERMINAL_TX_CANCEL(StringUtils.EMPTY, ArrayUtils.EMPTY_BYTE_ARRAY, StringUtils.EMPTY, "终端交易可撤单列表查询"),
	
	/**
	 * 终端ID查询
	 */
	TERMIDSEARCH("",new byte[0],"","终端ID查询"),
	
	/**
	 * 终端ID查询
	 */
	TERMSTATUSSEARCH("",new byte[0],"","终端状态查询"),
	
	/**
	 * 非终端单笔查询
	 */
	ORIGINSEARCH("",new byte[0],"","单笔查询"),
	
	/**
	 * 公共的通用查询
	 */
	COMMBASEQUERY("",new byte[0],"","公共的通用查询"),
	/**
	 * 终端设备状态更新并绑定实体店
	 */
	TERMSTATUSUPDATE_BINDSHOP("",new byte[0],"","终端设备状态更新并绑定实体店");
	
	
	
	
	private String reqId;
	private byte[] respId;//应答消息类型
	private String originType;//订单类型
	private String originTypeName;//订单类型名称
	
	ReqMsg(String reqId, byte[] respId, String originType,String originTypeName) {
		
		this.reqId = reqId;
		this.respId = respId;
		
		this.originType = originType;
		this.originTypeName = originTypeName;
	}

	public String getReqId() {
		return reqId;
	}


	public byte[] getRespId() {
		return respId;
	}


	public String getOriginType() {
		return originType;
	}

	public String getOriginTypeName() {
		return originTypeName;
	}


	public static byte[] getRespIdByReqId(String reqId) {
		for (ReqMsg c : ReqMsg.values()) {
			if (reqId.equals(c.getReqId())) {
			
				return c.getRespId();
			}
		}
		// return null;
		throw new IllegalArgumentException("rspMsgType is null. msgId= " + reqId);
	}

	public static String getOriginTypeById(String reqId) {
		for (ReqMsg c : ReqMsg.values()) {
			if (reqId.equals(c.getReqId())) {
				return c.getOriginType();
			}
		}
		return null;
	}
	
	public static String getOriginTypeNameById(String reqId) {
		for (ReqMsg c : ReqMsg.values()) {
			if (reqId.equals(c.getReqId())) {
				return c.getOriginTypeName();
			}
		}
		return null;
	}
	
	public static ReqMsg getById(String reqId) {
		for (ReqMsg c : ReqMsg.values()) {
			if (reqId.equals(c.getReqId())) {
				return c;
			}
		}
		return null;
	}

}
