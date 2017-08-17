package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;

/**
 * 非持卡人登录返回信息，包括非持卡人的个人信息
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.bean.common.AsNoCardHolderLoginResult.java
 * @className AsNoCardHolderLoginResult
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午10:46:41
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午10:46:41
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class AsNoCardHolderLoginResult extends LoginResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6207260951738278585L;
	private String custId;
	private String custName;
	private String mobile;
	private String email;
	private String isAuthEmail;
	private String reserveInfo;
	private String isBindBank;
	private String homeAddress;
	private String job;
	private String sex;
	private String headPic;
	private String userName;
	private String nickName;

	/**
	 * 是否是持卡人
	 * @return 返回false
	 */
	public boolean isCardHolder(){
		return false;
	} 
	
	/**
	 * 客户号
	 * 
	 * @return
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 客户号
	 * 
	 * @param custId
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 消费者名称
	 * 
	 * @return
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * 消费者名称
	 * 
	 * @param custName
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 手机号
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 是否验证邮件， 1:已验证 0: 未验证
	 * 
	 * @return
	 */
	public String getIsAuthEmail() {
		return isAuthEmail;
	}

	/**
	 * 是否验证邮件， 1:已验证 0: 未验证
	 * 
	 * @param isAuthEmail
	 */
	public void setIsAuthEmail(String isAuthEmail) {
		this.isAuthEmail = isAuthEmail;
	}

	/**
	 * 预留信息
	 * 
	 * @return
	 */
	public String getReserveInfo() {
		return reserveInfo;
	}

	/**
	 * 预留信息
	 * 
	 * @param reserveInfo
	 */
	public void setReserveInfo(String reserveInfo) {
		this.reserveInfo = reserveInfo;
	}

	/**
	 * 是否绑定银行卡 ，1是，0否
	 * 
	 * @return
	 */
	public String getIsBindBank() {
		return isBindBank;
	}

	/**
	 * 是否绑定银行卡 ，1是，0否
	 * 
	 * @param isBindBank
	 */
	public void setIsBindBank(String isBindBank) {
		this.isBindBank = isBindBank;
	}

	/**
	 * 常住地址
	 * 
	 * @return
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * 常住地址
	 * 
	 * @param homeAddress
	 */
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
	 * 职业
	 * 
	 * @return
	 */
	public String getJob() {
		return job;
	}

	/**
	 * 职业
	 * 
	 * @param job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * 性别，1：男 2：女
	 * 
	 * @return
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 性别，1：男 2：女
	 * 
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 头像
	 * 
	 * @return
	 */
	public String getHeadPic() {
		return headPic;
	}

	/**
	 * 头像
	 * 
	 * @param headPic
	 */
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	/**
	 * 姓名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 姓名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 昵称，为空则返回互生号
	 * 
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 昵称
	 * 
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
