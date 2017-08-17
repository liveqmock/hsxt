/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;
import java.util.List;

import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 企业用户组信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.ent
 * @ClassName: AsEntGroup
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-24 上午10:05:58
 * @version V1.0
 */
public class AsEntGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 用户组ID */
	private Long groupId;

	/** 用户组名称 */
	private String groupName;

	/** 企业客户号 */
	private String entCustId;

	/** 用户组描述 */
	private String groupDesc;

	/** 用户组组员（操作员）列表 */
	private List<AsOperator> opers;

	/**
	 * @return the 用户组ID
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param 用户组ID
	 *            the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the 用户组名称
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param 用户组名称
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 用户组描述
	 */
	public String getGroupDesc() {
		return groupDesc;
	}

	/**
	 * @param 用户组描述
	 *            the groupDesc to set
	 */
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	/**
	 * @return the 用户组组员（操作员）列表
	 */
	public List<AsOperator> getOpers() {
		return opers;
	}

	/**
	 * @param 用户组组员
	 *            （操作员）列表 the opers to set
	 */
	public void setOpers(List<AsOperator> opers) {
		this.opers = opers;
	}

}
