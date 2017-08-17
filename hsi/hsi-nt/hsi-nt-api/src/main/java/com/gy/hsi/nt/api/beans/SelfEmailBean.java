/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.beans;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 自定义邮件发送Bean
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: SelfEmailBean
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月6日 下午3:59:20
 * @company: gyist
 * @version V3.0.0
 */
public class SelfEmailBean extends SelfMsgBase implements Serializable {

	private static final long serialVersionUID = 4042798887352459651L;

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
