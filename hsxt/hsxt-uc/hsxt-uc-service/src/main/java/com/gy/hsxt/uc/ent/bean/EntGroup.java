package com.gy.hsxt.uc.ent.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;

/**
 * 企业用户组信息
 * 
 * @Package: com.gy.hsxt.uc.ent.bean
 * @ClassName: EntGroup
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-24 下午3:40:05
 * @version V1.0
 */
public class EntGroup implements Serializable {
	/** 用户组ID */
	private Long groupId;

	/** 用户组名称 */
	private String groupName;

	/** 企业客户号 */
	private String entCustId;

	/** 用户组描述 */
	private String groupDesc;

	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;

	/** 创建时间创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;

	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;

	/** 更新时间更新时间，取记录更新时的系统时间 */
	private Timestamp updateDate;

	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;

	private static final long serialVersionUID = 1L;

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
	 * @return the 标记此条记录的状态Y:可用N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	/**
	 * @return the 创建时间创建时间，取记录创建时的系统时间
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param 创建时间创建时间
	 *            ，取记录创建时的系统时间 the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the 创建人由谁创建，值为用户的伪键ID
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param 创建人由谁创建
	 *            ，值为用户的伪键ID the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the 更新时间更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param 更新时间更新时间
	 *            ，取记录更新时的系统时间 the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the 更新人由谁更新，值为用户的伪键ID
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 更新人由谁更新
	 *            ，值为用户的伪键ID the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public static List<AsEntGroup> buildAsEntGroup(List<EntGroup> list) {
		ArrayList<AsEntGroup> result = new ArrayList<AsEntGroup>();
		for (EntGroup group : list) {
			AsEntGroup asGroup = new AsEntGroup();
			BeanUtils.copyProperties(group, asGroup);
			result.add(asGroup);
		}
		return result;
	}

	public static EntGroup generateEntGroup(AsEntGroup asGroup) {
		EntGroup group = new EntGroup();
		BeanUtils.copyProperties(asGroup, group);
		return group;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}