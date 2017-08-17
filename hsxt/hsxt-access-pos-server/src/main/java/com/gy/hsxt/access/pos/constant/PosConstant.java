/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.constant;

import java.io.File;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 
 * Simple to Introduction
 * @category        Simple to Introduction
 * @projectName     hsxt-access-pos-common
 * @package         com.gy.hsxt.access.pos.common.constant.PosConstant.java
 * @className       PosConstant
 * @description     定义pos交互的相关常量及参数。
 * @author          wucl
 * @createDate      2015-9-24 上午10:23:47  
 * @updateUser      zoukun
 * @updateDate      2015-12-03
 * @updateRemark    
 * @version         
 */
public class PosConstant {
	
	
	/**  通用   真   1**/
	public static final String POS_TRUE = "1";
	
	/**  通用   假   0**/
	public static final String POS_FALSE = "0";
	
	/**  通用   真   1**/
	public static final int POS_1 = 1;
	
	/**  通用   假   0**/
	public static final int POS_0 = 0;
	
    public static final String DATE_FORMAT = "yyMMddHHmmss";
    
    public static final String TIME_FORMAT = "HHmmss";
    
    public static final String TIME_FORMAT_NUMBER_YMDHMS = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT_DATE_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    
	//-------------------------上下文状态-----------------------
	/**
	 * 是否有超时 1有超时，其他永不超时
	 */
	public static final int IS_TIMEOUT = 1;	
	
	public static final String POS_SIGIN_TIME_OUT_KEY = "posSiginTimeOutKey";
	
	public static final String POS_MAC_VALID_KEY = "posMacValidKey";
	
	public static final String GBK_ENCODE = "gbk";
	
	
	
	
	  /**
     * POS机，互生订单，刷卡，终端交易码，互生币。
     */
    public static final String HS_ORDER_POS_TERM_TRADE_CODE_HSB = "211040";
    /**
     * POS机，互生订单，刷卡，终端交易码，现金。
     */
    public static final String HS_ORDER_POS_TERM_TRADE_CODE_CASH = "211020";
    /**
     * 互生订单，订单列表查询。
     */
    public static final String HS_ORDER_QUERY_LIST = "740000";
    /**
     * 互生订单，单笔订单查询。
     */
    public static final String HS_ORDER_QUERY_SINGLE = "750000";
    
    public static final String NO_ORIG_RECORD_DEAULT_SUCCESS = "没找到原交易记录，默认成功";
    
    public static final File FILE_FINAL_ERR_TX_MSG = new File("ErrTx.txt");
    
    
    
    public static final int FD_04 = 4;
    public static final int FD_11 = 11;
    public static final int FD_12 = 12;
    public static final int FD_13 = 13;
    public static final int FD_22 = 22;
    public static final int FD_32 = 32;
    public static final int FD_35 = 35;
    public static final int FD_37 = 37;
    public static final int FD_39 = 39;
    public static final int FD_41 = 41;
    public static final int FD_42 = 42;
    public static final int FD_48 = 48;
    public static final int FD_60 = 60;
    public static final int FD_62 = 62;
    public static final int FD_63 = 63;
    
    /** 分版本号 1.0版本*/
    public static byte[] POSOLDVERSION = {0x60,0x00,0x01};
    /** 分版本号 2.0版本*/
    public static byte[] POSVERSION20 = {0x60,0x00,0x02};
    /** 分版本号 3.0版本*/
    public static byte[] POSVERSION30 = {0x60,0x00,0x03};
    //start--added by liuzh on 2016-06-22
    /** 分版本号 3.01版本*/
    public static byte[] POSVERSION301 = {0x60,0x03,0x01};
    //end--added by liuzh on 2016-06-22
    
    //pos请求唯一标识：msg + 3域 + 60.1域   
    /**业务类型：积分*/
  	public static final String REQ_POINT_ID = "090021000061";
  	/**业务类型：积分冲正*/
  	public static final String REQ_POINT_REVERSE_ID = "040021000061";
  	/**业务类型：积分撤单*/
  	public static final String REQ_POINT_CANCEL_ID = "090020000062";
  	/**业务类型：积分撤单冲正*/
  	public static final String REQ_POINT_CANCEL_REVERSE_ID = "040020000062";
  	/**业务类型：查询当日积分交易*/
  	public static final String REQ_POINT_DAY_SEARCH_ID = "092070000022";
  	/**业务类型：查询单笔交易*/
  	public static final String REQ_POINT_ORDER_SEARCH_ID = "092071000003";
  	
  	//start--added by liuzh on 2016-06-23
  	/**业务类型：查询积分卡交易单 */
  	public static final String REQ_POINT_ORDERS_SEARCH_ID = "0920700010";
  	/**业务类型：查询积分卡可撤单积分交易单*/
  	public static final String REQ_POINT_ORDER_CANCEL_SEARCH_ID = "092070001062";
  	/**业务类型：查询积分卡可退货积分交易单*/
  	public static final String REQ_POINT_ORDER_BACK_SEARCH_ID = "092070001065";
  	//end--added by liuzh on 2016-06-23
  	
  	//管理类
  	/**业务类型：签到  消息类型 + 60.1*/
  	public static final String REQ_SIGNIN_ID = "080000";
  	/**业务类型：签退*/
  	public static final String REQ_SIGNOFF_ID = "082000";
  	/**业务类型：参数同步到pos机*/
  	public static final String REQ_PARAM_SYNC_ID = "0950720000";
  	/**业务类型：参数上传到pos中心（as-pos）*/
  	public static final String REQ_PARAM_UPLOAD_ID = "0950730000";

  	
  	//HSB支付
  	/**业务类型：默认  流通  账务需要*/
  	public static final String REQ_HSB_PAY_CCY_ID = "090221104063";
  	/**业务类型：定向 账务需要 */
  	public static final String REQ_HSB_PAY_DC_ID = "090221104062";
  	/**业务类型：互生币支付冲正 */
  	public static final String REQ_HSB_PAY_REVERSE_ID = "040021104063";
  	/**业务类型：互生币支付撤单 */
  	public static final String REQ_HSB_PAY_CANCEL_ID = "090420104064";
  	/**业务类型：互生币支付撤单冲正 */
  	public static final String REQ_HSB_PAY_CANCEL_REVERSE_ID = "040020104064";
  	/**业务类型：互生币支付退货 */
  	public static final String REQ_HSB_PAY_RETURN_ID = "090620104065";
  	/**业务类型：互生币支付退货冲正 */
  	public static final String REQ_HSB_PAY_RETURN_REVERSE_ID = "040020104065";

  	
  	//HS 订单
  	/**业务类型：互生订单现金支付 */
  	public static final String REQ_HSORDER_CASH_PAY_ID = "092221102069";
  	/**业务类型：互生订单现金支付冲正 */
  	public static final String REQ_HSORDER_CASH_PAY_REVERSE_ID = "040021102069";
  	/**业务类型：互生订单互生币支付 */
  	public static final String REQ_HSORDER_HSB_PAY_ID = "092221104069";
  	/**业务类型：互生订单互生币支付冲正 */
  	public static final String REQ_HSORDER_HSB_PAY_REVERSE_ID = "040021104069";
  	/**业务类型：根据消费者卡号查询互生订单清单列表 */
  	public static final String REQ_HSORDERS_SEARCH_ID = "0920740000";
  	/**业务类型：根据互生订单号查询该笔订单详情 */
  	public static final String REQ_HSORDER_SEARCH_ID = "0920750000";
  	
  	//HSB 充值
  	/**业务类型：代（消费者）兑互生币 */
  	public static final String REQ_HSB_PROXY_RECHARGE_ID = "092440000070";
  	/**业务类型：代（消费者）兑互生币冲正 */
  	public static final String REQ_HSB_PROXY_RECHARGE_REVERSE_ID = "040040000070";
  	/**业务类型：企业兑换互生币 */
  	public static final String REQ_HSB_ENT_RECHARGE_ID = "092602104071";
  	/**业务类型：企业兑换互生币冲正 */
  	public static final String REQ_HSB_ENT_RECHARGE_REVERSE_ID = "040002104071";	
  	
  	/**批结算 只有“消息类型+60.1域”*/
  	public static final String REQ_BATCH_SETTLE_ID = "050000";
  	/**批上传 只有“消息类型+60.1域”*/
  	public static final String REQ_BATCH_UPLOAD_ID = "032000";
  	
  	/**业务类型：企业预付款账户余额查询 */
  	public static final String REQ_BALANCE_SEARCH_ID = "0920311010";
  	/**业务类型：？ */
  	public static final String REQ_CARD_CHREQ_ID = "010038000001";
  	/**业务类型：互生卡姓名查询 */
  	public static final String REQ_CARDNAME_SEARCH_ID = "0920330000";

  	
  	//定金交易
  	/**业务类型：预付定金*/
  	public static final String REQ_EARNEST_PREPAY_ID = "092176104410";
  	/**业务类型：预付定金冲正*/
  	public static final String REQ_EARNEST_REVERSE_ID = "040076104410";
  	/**业务类型：定金交易检索*/
  	public static final String REQ_EARNEST_SEARCH_ID = "092176104406";
  	/**业务类型：定金交易结算*/
  	public static final String REQ_EARNEST_SETTLE_ACC_ID = "092176104420";
  	/**业务类型：定金结算冲正*/
  	public static final String REQ_EARNEST_SETTLE_REVERSE_ID = "040076104420";
  	/**业务类型：定金撤销*/
  	public static final String REQ_EARNEST_CANCEL_ID = "092176104411";
  	/**业务类型：定金撤销冲正*/
  	public static final String REQ_EARNEST_CANCEL_REVERSE_ID = "040076104411";
  	
  	//固件版本升级
  	/**固件版本检测*/
  	public static final String REQ_POS_UPGRADE_CHECK_ID = "094000";
  	
  	/**
  	 * 响应类型
  	 */
  	public static final byte[] CARDCHECKTYPEREQ = { 0x01, 0x00 };
	public static final byte[] CARDCHECKTYPEREP = { 0x01, 0x10 };
	public static final byte[] POINTTYPEREQ = { 0x09, 0x00 };
	public static final byte[] RESP_POINT_ID = { 0x09, 0x10 };
	//冲正 -- 所有业务
	public static final byte[] POINTREVERSETYPEREQ = { 0x04, 0x00 };
	public static final byte[] POINTREVERSETYPEREP = { 0x04, 0x10 };
	public static final byte[] POINTSEARCHTYPEREQ = { 0x09, 0x20 };
	public static final byte[] POINTSEARCHTYPEREP = { 0x09, 0x30 };
	public static final byte[] PARAMTYPEREQ = { 0x09, 0x50 };
	public static final byte[] PARAMTYPEREP = { 0x09, 0x60 };
	public static final byte[] SIGNINTYPEREQ = { 0x08, 0x00 };
	public static final byte[] SIGNINTYPEREP = { 0x08, 0x10 };
	public static final byte[] SIGNOFFTYPEREQ = { 0x08, 0x20 };
	public static final byte[] SIGNOFFTYPEREP = { 0x08, 0x30 };
	public static final byte[] BATCHSETTLETYPEREQ = { 0x05, 0x00 };
	public static final byte[] BATCHSETTLETYPEREP = { 0x05, 0x10 };
	public static final byte[] BATCHUPLOADTYPEREQ = { 0x03, 0x20 };
	public static final byte[] BATCHUPLOADTYPEREP = { 0x03, 0x30 };
	//互生币支付
	public static final byte[] HSPAYTYPEREQ = { 0x09, 0x02 };
	public static final byte[] HSPAYTYPEREP = { 0x09, 0x03 };
	public static final byte[] HSCANCLETYPEREQ = { 0x09, 0x04 };
	public static final byte[] HSCANCLETYPEREP = { 0x09, 0x05 };
	public static final byte[] HSRETURNTYPEREQ = { 0x09, 0x06 };
	public static final byte[] HSRETURNTYPEREP = { 0x09, 0x07 };
	public static final byte[] HSORDERTYPEREQ = { 0x09, 0x22 };
	public static final byte[] HSORDERTYPEREP = { 0x09, 0x23 };
	//代兑互生币（代消费者购互生币）
	public static final byte[] HSBCRECTYPEREQ = { 0x09, 0x24 };
	public static final byte[] HSBCRECTYPEREP = { 0x09, 0x25 };
	//兑换互生币
	public static final byte[] HSBBRECTYPEREQ = { 0x09, 0x26 };
	public static final byte[] HSBBRECTYPEREP = { 0x09, 0x27 };
	
    //定金业务---------------kend 消息类型码定义
	//预付定金
	public static final byte[] EARNESTPREPAYREQ = { 0x09, 0x21 };
	public static final byte[] EARNESTPREPAYREP = { 0x09, 0x31 };
	//定金结算
	public static final byte[] EARNESTSETTLEACCREQ = { 0x09, 0x21 };
	public static final byte[] EARNESTSETTLEACCREP = { 0x09, 0x31 };
	//定金撤销
	public static final byte[] EARNESTCANCELREQ = { 0x09, 0x21 };
	public static final byte[] EARNESTCANCELREP = { 0x09, 0x31 };
	//定金详情检索
	public static final byte[] EARNESTSEARCHREQ = { 0x09, 0x21 };
	public static final byte[] EARNESTSEARCHREP = { 0x09, 0x31 };
	//预付定金冲正
	public static final byte[] EARNESTPREPAYREVERSEREQ = { 0x04, 0x00 };
	public static final byte[] EARNESTPREPAYREVERSEREP = { 0x04, 0x10 };
	//定金结算冲正
	public static final byte[] EARNESTSETTLEREVERSEREQ = { 0x04, 0x00 };
	public static final byte[] EARNESTSETTLEREVERSEREP = { 0x04, 0x10 };
	//定金撤销冲正
	public static final byte[] EARNESTCANCELREVERSEREQ = { 0x04, 0x00 };
	public static final byte[] EARNESTCANCELREVERSEREP = { 0x04, 0x10 };
	//固件版本升级
	public static final byte[] POSUPGRADECHECKREQ = { 0x09, 0x40 };
	public static final byte[] POSUPGRADECHECKREP = { 0x09, 0x41 };
	//start--added by liuzh on 2016-06-23
	//查询积分卡积分交易记录
	public static final byte[] POINTORDERSSEARCHREQ = { 0x09, 0x20 };
	public static final byte[] POINTORDERSSEARCHREP = { 0x09, 0x30 };
	//end--added by liuzh on 2016-06-23
    
  	//业务类型
	//消费积分
    public static final String POINT_TYPE = "01";
    //消费积分撤单
	public static final String POI_CANCEL_TYPE = "02";
	//退货
	public static final String POI_RETURN_TYPE = "03";
	//消费积分冲正
	public static final String POI_REVERSE_TYPE = "09";
	public static final String POI_CANCEL_REVERSE_TYPE = "09";
	public static final String POI_DAY_SEARCH_TYPE = "0";
	public static final String POI_RUN_ACC_SEARCH_TYPE = "0";
	public static final String POI_CARD_CHK_TYPE = "0";
	public static final String BAT_STL_TYPE = "0";
	public static final String BAT_UPLOAD_TYPE = "0";
	public static final String SIGNIN_TYPE = "0";
	public static final String SIGNOFF_TYPE = "0";
	public static final String SYNC_PARAM_TYPE = "0";
	public static final String UPLOAD_PARAM_TYPE = "0";
	
	//互生币支付 定向  为了区分账务是定向还是流通
	public static final String HSB_PAY_TYPE1 = "10";
	//互生币支付 流通
	public static final String HSB_PAY_TYPE = "11";
	//互生币撤单
	public static final String HSB_PAY_CANCEL_TYPE = "12";
	//互生币退货
	public static final String HSB_PAY_RETURN_TYPE = "13";
	//互生订单支付
	public static final String HSORDER_PAY_TYPE = "30";
	//选现金
	public static final String HSORDER_CASH_PAY_TYPE = "31";
	//选互生币支付
	public static final String HSORDER_HSB_PAY_TYPE = "32";
	//代兑互生币
	public static final String HSB_C_RECHARGE_TYPE = "41";
	//兑换互生币
	public static final String HSB_B_RECHARGE_TYPE = "42";
	//企业预付款账户余额
	public static final String ASSURE_SEARCH_TYPE = "51";
	//企业货币账户余额
	public static final String CASH_SEARCH_TYPE = "52";
	//企业互生币账户余额
	public static final String SALE_SEARCH_TYPE = "53";
	//互生卡姓名查询
	public static final String CARDNAME_SEARCH_TYPE = "55";
	//互生订单列表查询
	public static final String HSORDERS_SEARCH_TYPE = "56";
	//互生订单单笔查询
	public static final String HSORDER_SEARCH_TYPE = "57";
	//互生币支付冲正
	public static final String HSB_PAY_REVERSE_TYPE = "19";
	//互生币支付撤单冲正
	public static final String HSB_PAY_CANCEL_REVERSE_TYPE = "18";
	//互生币支付退货冲正
	public static final String HSB_PAY_RETURN_REVERSE_TYPE = "17";
	//互生订单现金冲正
	public static final String HSORDER_CASH_PAY_REVERSE_TYPE = "39";
	//互生订单互生币冲正
	public static final String HSORDER_HSB_PAY_REVERSE_TYPE = "39";
	//代兑互生币冲正
	public static final String HSB_PROXY_RECHARGE_REVERSE_TYPE = "49";
	//兑换互生币冲正
	public static final String HSB_ENT_RECHARGE_REVERSE_TYPE = "48";
	
	//定金类业务  kend
	//预付定金 
	public static final String EARNEST_PREPAY_TYPE = "50";
	//定金查询
	public static final String EARNEST_SEARCH_TYPE = "51";
	//定金结算
	public static final String EARNEST_SETTLEACC_TYPE = "52";
	//定金撤销
	public static final String EARNEST_CANCEL_TYPE = "53";
	//预付定金冲正
	public static final String EARNEST_PREPAY_REVERSE_TYPE = "54";
	//定金结算冲正
	public static final String EARNEST_SETTLEACC_REVERSE_TYPE = "55";
	//定金撤销冲正
	public static final String EARNEST_CANCEL_REVERSE_TYPE = "56";
	
	//固件升级版本检测
	public static final String POS_UPGRADE_CHECK_TYPE = "57";
	
	//start--added by liuzh on 2016-06-23
	//查询积分卡积分交易记录
	public static final String POINT_ORDERS_SEARCH_TYPE = "58";
	//end--added by liuzh on 2016-06-23	
	
	public static final byte POS_HEADER_MAC_VERSION_NEW = 0x22;
	public static final byte POS_HEADER_MAC_VERSION_OLD = 0x12;

	public static final byte POS_HEADER_KEY_VERSION_NEW = 0x21;
	public static final byte POS_HEADER_KEY_VERSION_OLD = 0x11;

	public static final byte POS_HEADER_ENCRYPT_VERSION_NEW = 0x23;
	public static final byte POS_HEADER_ENCRYPT_VERSION_OLD = 0x13;

	public static final byte POS_HEADER_DECRYPT_VERSION_NEW = 0x24;
	public static final byte POS_HEADER_DECRYPT_VERSION_OLD = 0x14;
	
	
	
	
    /**
     * it comes from StatusAndProcessEnum. only for 8583 protocol.
     */
    public static final byte AGAINSIGNIN = (byte) 0x03;
    
    
    
    //-------------------------编码转换-------------------------------------------------
    /**
     * 48（0x30）是数字0，49是数字1……
     */
    public static final int NUMBER_ASCII_DISTANCE = 48;
	
	
	/**
	 * most frequently used constant.
	 */
	public static final Integer ZERO = NumberUtils.INTEGER_ZERO;
	public static final String ZERO_STR = ZERO.toString();
	public static final char ZERO_CHAR = ZERO_STR.charAt(0);
	public static final Integer ONE = NumberUtils.INTEGER_ONE;
	public static final String ONE_STR = ONE.toString();
	public static final char ONE_CHAR = ONE_STR.charAt(0);
	
	
	
	//-------------------------8583 字段长度 相关-------------------------------------------------
	/** **/
//	public static final int KEY_SERVER_BASE_LEN = 29;//entNo+posNo+operNo总长度(含最后占位)
	
//	public static final int KEY_SERVER_BASE_LEN_NEWVER = 31;//新版本posNo 4位，entNo+posNo+operNo总长度(含最后占位)
	
	/** 金额最大位数 **/
	public static final int AMOUNT_12_LENGTH = 12;
	/** 金额保留位数 **/
	public static final int AMOUNT_2_DECIMAL_LENGTH = 2;
	
	 /**  位图长度   **/
    public static final int BIT_MAP_LENGTH = 65;
    
    /**2.0版同步参数 版本标记 总长度**/
    public static final int SYNC_NEW_VERSION_TOTLEN = 16;
    /**3.0版同步参数 版本标记 总长度**/
    public static final int SYNC_VERSION30_TOTLEN = 20;
    
    //start--added by liuzh on 2016-06-24
    /**3.01版同步参数 版本标记 总长度**/
    public static final int SYNC_VERSION301_TOTLEN = 24;
    //end--added by liuzh on 2016-06-24
    
    /**  卡号长度 + 暗码   **/
    public static final int CARDNO_CIPHER_LENGTH = 19;
    
    /**  卡号长度    **/
    public static final int CARDNO_LENGTH = 11;
    
    /**  暗码长度    **/
    public static final int CIPHER_LENGTH = 8;
    
    /**
     * 积分比率，长度。0001--0300
     */
    public static final int RATE_LENGTH = 4;
    
    /**
	 * PosRunCode、BatchNo，都是6位的数字。
	 */
	public static final int POS_RUNCODE_LENGTH = 6;
		
	
	public static final int CARD_NO_LEN = 11;
	
	/**
	 * pos机设备型号
	 */
	public static final int POS_DEVICE_TYPE_LEN = 30;
	
	/**
	 * pos机机器号
	 */
	public static final int POS_MACHINE_NO_LEN = 30;
	
	/**
	 * pos当前固件版本号
	 */
	public static final int POS_UPGRADE_VER_NO_LENGTH = 20;
	
    /**
     * 2014/12/18 谢益武确定的数目。
     */
    public static final double MIN_POINT_NUM = 0.1;
    /**
     * 对reqData的3个值做个误差检查，使用任何货币计算，超过了0.01，就认为3个值有错误。
     * 凡是跟0.010000009比较的，也算匹配，所以0.01-->0.011d
     * 当然也可以作为交易金额最低值了。
     */
    public static final double MIN_CURRENCY_AMT_DELTA = 0.011d;
    
    /**
     * 8583协议里，把1.23*100，作为000000000123，所以，0.01是最小的值。
     */
    public static final double MIN_CURRENCY_AMT = 0.01;

    /*
     * posRunCode、batchNo，最大值是6个9
     */
    public static final int POS_RUN_CODE_AND_BATCH_NO_MAX = 999999;
    
    /**
     * 	基础信息版本号+货币版本号+国家版本号+积分信息版本号  同步参数老版本号总长度
     */
	public static final int SYNC_VERSION_TOTLEN = 12;
	
	

	
	public static final int POINTVALUEMULTI = 100;//积分数放大倍数
	public static final int RATEMULTI = 10000;//转现比率放大倍数
	
   //-------------------------8583 状态类-------------------------------------------------
    /** 本币1 **/
	public static final int ISLOCAL_CURRENCY = 1;
	
	/**
	 * 归一国际信用卡公司代码 内卡  暂时没区分是内卡还是外卡，所有卡进来默认都是GYT
	 */
	public static final String ICC_CODE_GYT = "GYT";
	/**
	 * 受理方标识码（32域响应）
	 */
	public static final String ACCEPT_CODE = "05000000";
	
	/**手动输入 卡号**/
	public static final String INPUT_WAY_MANUAL = "01";
	
	/**刷卡**/
	public static final String INPUT_WAY_STRIPE = "02";
	
	/**扫二维码                    kend**/
	public static final String INPUT_WAY_QR = "20";
	/**无卡号交易 企业兑换互生币适用 **/
	public static final String INPUT_WAY_0 = "00";
	
	/**
	 * 交易允许的输入方式 手动输入
	 */
	public static final String INPUT_WAY_MANUAL_CONTENT= "0";
	/**
     * 交易允许的输入方式 刷卡输入
     */
    public static final String INPUT_WAY_STRIPE_CONTENT= "1";
    /**
     * 交易允许的输入方式 无卡号
     */
    public static final String INPUT_WAY_0_CONTENT= "2";
	
	/**
	 * 交易中包含PIN
	 */
	public static final String PIN_1 = "1";
	/** 交易中不包含PIN **/
	public static final String PIN_2 = "2";
	
	
	/**
     * 交易允许的密码方式 交易密码
     */
	public static final String TRADE_PIN = "0";
	/**
     * 交易允许的密码方式 登录密码
     */
	public static final String LOGIN_PIN = "1";
	/**
     * 交易允许的密码方式 无密码
     */
	public static final String NO_PIN = "2";
	//8583协议最大长度
	public static final int HSORDER_MAXPACKLEN = 32;
	
	//pos中心交易流水号固定为12位
	public static final int POSSERVER_SEQ = 12;
	
	/**
	 * 当日查询，最多9条
	 */
	public static final int DAYSEARCH_SIZE = 9;
	
	/**
     * 冲正原因码。用于：积分冲正/积分撤单冲正/互生币撤单冲正/互生币退货/互生币退货冲正。
     */
    public static final String RECODE_A0 = "A0";
    /**
     * 冲正原因码。用于：互生币支付冲正/互生订单互生币冲正/互生订单现金冲正/代兑互生币冲正/兑换互生币冲正。
     */
    public static final String RECODE_B0 = "B0";
    
    
    /**
     * 中国国家ID
     */
    public static final String COUNTRYID = "239";
    
    /**
     * 批结算 对帐平 1
     */
    public static final String BAT_SETTLE_SUCCESS = "1";

    /**
     * 批结算 对帐不平 2
     */
    public static final String BAT_SETTLE_FAILE = "2";

    //批上送 201
    public static final String BATCH_UPLOAD_NETCODE = "201";

    //批上送 202
    public static final String BATCH_UPLOAD_NETCODE_END_TWO = "202";

    //批上送 结束207
    public static final String BATCH_UPLOAD_NETCODE_END = "207";
    
    /*二维码类型定义*/
    /**
     * 消费者身份二维码  表征消费者互生卡号
     */
    public static final String QRTYPE_CUSTOMER_ID = "ID";
    
    /**
     * 交易单据二维码  一笔包含了完整待交易单据信息的字符串
     */
    public static final String QRTYPE_BILL_PAY_HANG = "BH";
    
    //start--added by liuzh on 2016-06-24    
    /**
     * 3.01 交易单据二维码  一笔包含了完整待交易单据信息的字符串
     */
    public static final String QRTYPE_BILL_PAY_HANG_301 = "B1";    
    //end--added by liuzh on 2016-06-24
    
    /**
     * 交易凭据二维码  表征一笔交易的流水号
     */
    public static final String QRTYPE_BILL_SEQ = "BS";
    
    
    //start--added by liuzh on 2016-05-21
    /*交易类型*/
    //积分
    public static final String TRANS_TYPE_POINT = "61";
    //互生币支付
    public static final String TRANS_TYPE_HSP_PAY = "63";
    
    //消费者积分比例
    public static final String PER_POINT_RATE = "0.5";
    //end--added by liuzh on 2016-05-21
    
    //start--added by liuzh on 2016-06-23
	/** 交易流水号 可撤单积分交易 */
	public static final String TRANS_NO_CANCEL11 = "11";
	/** 交易流水号 可退货积分交易 */
	public static final String TRANS_NO_BACK12 = "12";
	/**抵扣券张数长度**/
	public static final int DEDUCTION_VOUCHER_COUNT_LEN = 6;
	/**抵扣券面值长度**/
	public static final int DEDUCTION_VOUCHER_PAR_VALUE_LEN = 6;
	/**抵扣劵金额比例 长度**/
	public static final int DEDUCTION_VOUCHER_RATE_LEN = 4;
    //end--added by liuzh on 2016-06-23

}
