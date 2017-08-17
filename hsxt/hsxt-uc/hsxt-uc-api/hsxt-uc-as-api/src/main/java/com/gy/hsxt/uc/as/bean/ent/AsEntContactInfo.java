/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;
import java.util.List;

import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;

public class AsEntContactInfo implements Serializable {

	private static final long serialVersionUID = 1214631954874075356L;
	
	/** 联系人 */
	private String contactPerson;
	/** 联系人电话 */
	private String contactPhone;
	/** 企业注册地址 */
	private String entRegAddr;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业客户类型 */
	private Integer entCustType;
	/** 企业注册名 */
	private String entName;
	/** 企业英文名称 */
	private String entNameEn;
	
	
	/** 邮政编码 */
	private String postCode;
	
	/** 企业联系地址 */
	private String contactAddr;
	/** 企业收货地址 */
	private List<AsReceiveAddr> receiveAddrList;

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getEntRegAddr() {
		return entRegAddr;
	}

	public void setEntRegAddr(String entRegAddr) {
		this.entRegAddr = entRegAddr;
	}

	public String getEntCustId() {
		return entCustId;
	}

	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	public String getEntResNo() {
		return entResNo;
	}

	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	public Integer getEntCustType() {
		return entCustType;
	}

	public void setEntCustType(Integer entCustType) {
		this.entCustType = entCustType;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getEntNameEn() {
		return entNameEn;
	}

	public void setEntNameEn(String entNameEn) {
		this.entNameEn = entNameEn;
	}

	public List<AsReceiveAddr> getReceiveAddrList() {
		return receiveAddrList;
	}

	public void setReceiveAddrList(List<AsReceiveAddr> receiveAddrList) {
		this.receiveAddrList = receiveAddrList;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}
	
	
}
