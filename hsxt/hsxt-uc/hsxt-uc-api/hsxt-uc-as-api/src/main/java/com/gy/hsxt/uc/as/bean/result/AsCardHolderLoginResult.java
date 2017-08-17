package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;

/**
 * 持卡人登录结果信息，登录成功后返回持卡人信息
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.bean.common.CardHolderLoginResult.java
 * @className CardHolderLoginResult
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午10:25:07
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午10:25:07
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class AsCardHolderLoginResult extends LoginResult implements
		Serializable {

	private static final long serialVersionUID = -2368893661447485856L;
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
	private String resNo;
	private String entResNo;
	private String isAuthMobile;
	private String isRealnameAuth;
	private String creType;
	private String creNo;
	private String creExpiryDate;
	private String birthAddress;
	private String mainInfoStatus;
	private String baseStatus;
	
	/**
	 * 是否是持卡人
	 * @return 返回true
	 */
	public boolean isCardHolder(){
		return true;
	} 
	
	/**
	 * 本地平台持卡人门户网址
	 */
	private String webUrl;
		
	/**
	 * @return the 本地平台持卡人门户网址
	 */
	public String getWebUrl() {
		return webUrl;
	}

	/**
	 * @param 本地平台持卡人门户网址
	 *            the webUrl to set
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	/**
	 * 互生号
	 * 
	 * @return
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * 互生号
	 * 
	 * @param resNo
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * 隶属托管企业资源号
	 * 
	 * @return
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * 隶属托管企业资源号
	 * 
	 * @param entResNo
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * 是否验证手机, 1:已验证 0: 未验证
	 * 
	 * @return
	 */
	public String getIsAuthMobile() {
		return isAuthMobile;
	}

	/**
	 * 是否验证手机, 1:已验证 0: 未验证
	 * 
	 * @param isAuthMobile
	 */
	public void setIsAuthMobile(String isAuthMobile) {
		this.isAuthMobile = isAuthMobile;
	}

	/**
	 * 实名状态,1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 * 
	 * @return
	 */
	public String getIsRealnameAuth() {
		return isRealnameAuth;
	}

	/**
	 * 实名状态,1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 * 
	 * @param isRealnameAuth
	 */
	public void setIsRealnameAuth(String isRealnameAuth) {
		this.isRealnameAuth = isRealnameAuth;
	}

	/**
	 * 证件类型,1：身份证 2：护照:3：营业执照
	 * 
	 * @return
	 */
	public String getCreType() {
		return creType;
	}

	/**
	 * 证件类型,1：身份证 2：护照:3：营业执照
	 * 
	 * @param creType
	 */
	public void setCreType(String creType) {
		this.creType = creType;
	}

	/**
	 * 证件号码
	 * 
	 * @return
	 */
	public String getCreNo() {
		return creNo;
	}

	/**
	 * 证件号码
	 * 
	 * @param creNo
	 */
	public void setCreNo(String creNo) {
		this.creNo = creNo;
	}

	/**
	 * 证件有效期
	 * 
	 * @return
	 */
	public String getCreExpiryDate() {
		return creExpiryDate;
	}

	/**
	 * 证件有效期
	 * 
	 * @param creExpiryDate
	 */
	public void setCreExpiryDate(String creExpiryDate) {
		this.creExpiryDate = creExpiryDate;
	}

	/**
	 * 户籍地址
	 * 
	 * @return
	 */
	public String getBirthAddress() {
		return birthAddress;
	}

	/**
	 * 户籍地址
	 * 
	 * @param birthAddress
	 */
	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}

	/**
	 * 是否在重要信息变更期间,1:是0：否
	 * 
	 * @return
	 */
	public String getMainInfoStatus() {
		return mainInfoStatus;
	}

	/**
	 * 是否在重要信息变更期间,1:是0：否
	 * 
	 * @param mainInfoStatus
	 */
	public void setMainInfoStatus(String mainInfoStatus) {
		this.mainInfoStatus = mainInfoStatus;
	}

	/**
	 * 基本状态,1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销
	 * 
	 * @return
	 */
	public String getBaseStatus() {
		return baseStatus;
	}

	/**
	 * 基本状态,1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销
	 * 
	 * @param baseStatus
	 */
	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
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
