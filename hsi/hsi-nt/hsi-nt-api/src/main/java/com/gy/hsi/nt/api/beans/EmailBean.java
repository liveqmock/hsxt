package com.gy.hsi.nt.api.beans;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 邮件接收实体bean
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: EmailBean
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月25日 下午5:14:18
 * @company: gyist
 * @version V3.0.0
 */
public final class EmailBean extends MsgBase implements Serializable {

	private static final long serialVersionUID = -109904731809438352L;
	/**
	 * 邮件标题
	 */
	@NotEmpty(message = "邮件标题不能为空")
	@Length(max = 150, message = "邮件标题最大长度150")
	private String mailTitle;

	/**
	 * 附件列表
	 */
	private List<String> mailAttachFileIds;

	public String getMailTitle()
	{
		return mailTitle;
	}

	public void setMailTitle(String mailTitle)
	{
		this.mailTitle = mailTitle;
	}

	public List<String> getMailAttachFileIds()
	{
		return mailAttachFileIds;
	}

	public void setMailAttachFileIds(List<String> mailAttachFileIds)
	{
		this.mailAttachFileIds = mailAttachFileIds;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
