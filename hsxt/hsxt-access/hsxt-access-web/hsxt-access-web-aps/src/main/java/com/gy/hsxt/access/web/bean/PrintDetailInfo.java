/**
 * 
 */
package com.gy.hsxt.access.web.bean;

import java.io.Serializable;

import com.gy.hsxt.bs.bean.tool.Shipping;

/**
 * @author weiyq
 *
 */
public class PrintDetailInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Shipping content;
	private String hsResNo;
	private String entName;
	private String phoneNo;
	private String contact;
	private String addr;
	private String postCode;
	public Shipping getContent() {
		return content;
	}
	public void setContent(Shipping content) {
		this.content = content;
	}
	public String getHsResNo() {
		return hsResNo;
	}
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
}
