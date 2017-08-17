package com.gy.hsi.nt.server.entity.dynamic;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className:Header
 * @author:likui
 * @date:2015年7月29日
 * @desc:互动消息头部实体
 * @company:
 */
public class Header implements Serializable {

	private static final long serialVersionUID = 4031120761125331864L;

	private static final String VERSION = "1.0";

	private static final String SRC_SYS_TYPE = "2";

	private static final String TERMINAL_TYPE = "0";

	/**
	 * 事物ID
	 */
	private String transactionID;
	/**
	 * 版本信息，不填，为1.0
	 */
	private String version = VERSION;
	/**
	 * 发起操作的网元访问账号
	 */
	private String accessID;

	/**
	 * 发起操作的网元访问密码
	 */
	private String accessPwd;
	/**
	 * 推送目标终端类型1.WEB 2.手机3.PC客户端 4.所有 如果要推送多个类型以逗号分隔例如：1,2
	 */
	private String terminalType = TERMINAL_TYPE;
	/**
	 * 访问令牌
	 */
	private String appKey;
	/**
	 * 调用参数签名值，与appKey成对出现
	 */
	private String sign;
	/**
	 * 用户发起请求的时间戳
	 */
	private String timeStamp;
	/**
	 * 本次发送的报文失效时间。格式为unix时间戳形式
	 */
	private String expires;
	/**
	 * 推送类型，取值范围为：1～4 1：单个人 2：多人 3：所有人，无需指定desUids、desGroupId 4：群组
	 */
	private String pushType;
	/**
	 * 源用户id
	 */
	private String srcUid;
	/**
	 * 源网元系统类别，取值范围：1~2 1:电商 2:互生
	 */
	private String srcSysType = SRC_SYS_TYPE;
	/**
	 * 目标用户群，一个或者多个用户
	 */
	private String desUids;
	/**
	 * 目标组id
	 */
	private String desGroupId;

	public Header()
	{
		super();
	}

	public Header(String version, String accessID, String accessPwd, String terminalType, String appKey, String sign,
			String timeStamp, String expires, String pushType, String srcUid, String srcSysType, String desUids,
			String desGroupId)
	{
		super();
		this.version = version;
		this.accessID = accessID;
		this.accessPwd = accessPwd;
		this.terminalType = terminalType;
		this.appKey = appKey;
		this.sign = sign;
		this.timeStamp = timeStamp;
		this.expires = expires;
		this.pushType = pushType;
		this.srcUid = srcUid;
		this.srcSysType = srcSysType;
		this.desUids = desUids;
		this.desGroupId = desGroupId;
	}

	public String getTransactionID()
	{
		return transactionID;
	}

	public void setTransactionID(String transactionID)
	{
		this.transactionID = transactionID;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getAccessID()
	{
		return accessID;
	}

	public void setAccessID(String accessID)
	{
		this.accessID = accessID;
	}

	public String getAccessPwd()
	{
		return accessPwd;
	}

	public void setAccessPwd(String accessPwd)
	{
		this.accessPwd = accessPwd;
	}

	public String getTerminalType()
	{
		return terminalType;
	}

	public void setTerminalType(String terminalType)
	{
		this.terminalType = terminalType;
	}

	public String getAppKey()
	{
		return appKey;
	}

	public void setAppKey(String appKey)
	{
		this.appKey = appKey;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getExpires()
	{
		return expires;
	}

	public void setExpires(String expires)
	{
		this.expires = expires;
	}

	public String getPushType()
	{
		return pushType;
	}

	public void setPushType(String pushType)
	{
		this.pushType = pushType;
	}

	public String getSrcUid()
	{
		return srcUid;
	}

	public void setSrcUid(String srcUid)
	{
		this.srcUid = srcUid;
	}

	public String getSrcSysType()
	{
		return srcSysType;
	}

	public void setSrcSysType(String srcSysType)
	{
		this.srcSysType = srcSysType;
	}

	public String getDesUids()
	{
		return desUids;
	}

	public void setDesUids(String desUids)
	{
		this.desUids = desUids;
	}

	public String getDesGroupId()
	{
		return desGroupId;
	}

	public void setDesGroupId(String desGroupId)
	{
		this.desGroupId = desGroupId;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
