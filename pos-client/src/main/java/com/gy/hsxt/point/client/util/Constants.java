package com.gy.hsxt.point.client.util;

/**
 * 
 * POS通讯协议常量
 * 
 * @Package: com.gy.point.client.util
 * @ClassName: Constants
 * 
 * @author: liyh
 * @date: 2015-12-18 上午11:23:40
 * @version V3.0
 */
public class Constants {

	public static final String POSCHARSET = "gbk";

	public static final String CONFIGNAME = "pos_client_server";

	public static final String MYBATIS_CONFIGNAME = "mybatis-config.xml";

	public static final byte LOGINANDGETORDERSREQTYPE = 0x01;// 客户端登录

	public static final byte LOGINANDGETORDERSREPTYPE = (byte) 0x81;// 应答类型

	public static final byte GETPOSLISTREQTYPE = 0x02;// 客户端向服务端申请所有新订单编号列表

	public static final byte GETPOSLISTREPTYPE = (byte) 0x82;// 应答类型

	public static final byte GETPMKANDSYNCPARAMREQTYPE = 0x03;// 客户端向服务端申请PMK及企业信息

	public static final byte GETPMKANDSYNCPARAMREPTYPE = (byte) 0x83;// 应答类型

	public static final byte SENDOVERREQTYPE = 0x04;// 客户端向服务端发送写成功消息

	public static final byte SENDOVERREPTYPE = (byte) 0x84;// 应答类型

	public static final byte REPAIRGETPMKANDSYNCPARAMREQTYPE = (byte) 0x05;//

	public static final byte REPAIRGETPMKANDSYNCPARAMREPTYPE = (byte) 0x85;// 成功应答类型

	public static final byte REPAIRSENDOVERREQTYPE = (byte) 0x06;// 应答类型

	public static final byte REPAIRSENDOVERREPTYPE = (byte) 0x86;// 成功应答类型

	public static final byte GETREPAIRPOSLISTREQTYPE = 0x07;// 客户端向服务端申请所有维护订单编号列表返回使用GETPMKANDSYNCPARAMREPTYPE
															// =
															// (byte)0x83;//应答类型

	public static final byte SUCCESSREPTYPE = (byte) 0x00;// 成功应答类型

	public static final byte ERRORREPTYPE = (byte) 0x99;// 失败应答类型

	public static final byte[] STX = { 0x02 };

	public static final byte[] ETX = { 0x03 };

	public static final int ERRORCODELEN = 1;// 错误码byte长度

	public static final int PAGESIZELEN = 2;// byte长度

	public static final int PAGENOLEN = 2;// byte长度

	public static final int POSNOLEN = 4;// byte长度

	public static final int USERNAMELEN = 16;// byte长度

	public static final int USERPASSLEN = 16;// byte长度

	public static final int ORDERNOLEN = 32;// byte长度

	public static final int ENTNAMELEN = 128;// byte长度

	public static final int ENTTYPELEN = 1;// byte长度

	public static final int ENTNOLEN = 11;// byte长度//lyh修改过

	public static final int PHYCODELEN = 11;// byte长度

	public static final int POS_NO_LEN = 15;// byte长度

	public static final int CURRENCYIDLEN = 3;//

	public static final int CURRENCYCODELEN = 10;//

	public static final int COUNTRYINDEXLEN = 3;// byte长度

	public static final int COUNTRYCODELEN = 3;// byte长度

	public static final int POINTRATELEN = 4;// 积分bili

	public static final int EXCHANGERATELEN = 12;// 积分转现比率

	public static final int HSBEXCHANGERATELEN = 12;// 互生币积分转现比率

	public static final String ORDERINFOSKEY = "orderInfos";

	public static final String POSINFOKEY = "posInfo";

	public static final byte NOREGISTER_ERRORCODE = (byte) 0x08;

	public static final byte OTHER_ERRORCODE = (byte) 0x99;

	public static final int INCREASEMULTI = 10000;// 放大倍数

}