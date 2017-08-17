/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.beans;

import java.io.Serializable;

import com.gy.hsi.nt.api.common.MsgType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 发送自定义内容互动业务消息
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: SelfDynamicBizMsgBean
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月7日 下午5:23:49
 * @company: gyist
 * @version V3.0.0
 */
public class SelfDynamicBizMsgBean extends SelfMsgBase implements Serializable {

	private static final long serialVersionUID = 1403158728047555862L;

	/**
	 * 消息大类(参照互信消息大类定义)
	 */
	@NotEmpty(message = "消息大类不能为空")
	private String msgCode;

	/**
	 * 消息子类(参照互信消息大类定义)
	 */
	@NotEmpty(message = "消息子类不能为空")
	private String subMsgCode;

	/**
	 * 消息主题
	 */
	private String msgTitle;

	/**
	 * 消息发送方式 [20(私信)
	 */
	private int msgType = MsgType.PRIVATE_LETTER.getMsgType();

	public String getMsgCode()
	{
		return msgCode;
	}

	public void setMsgCode(String msgCode)
	{
		this.msgCode = msgCode;
	}

	public String getSubMsgCode()
	{
		return subMsgCode;
	}

	public void setSubMsgCode(String subMsgCode)
	{
		this.subMsgCode = subMsgCode;
	}

	public String getMsgTitle()
	{
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle)
	{
		this.msgTitle = msgTitle;
	}

	public int getMsgType()
	{
		return msgType;
	}
}
