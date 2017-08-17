/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 积分福利申请详情
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: WelfareApplyDetail
 * @Description: 积分福利申请详情
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午8:06:43
 * @version V3.0
 */
public class WelfareApplyDetail extends WelfareApplyRecord implements Serializable {

	private static final long serialVersionUID = -8885340008868080029L;

	/** 客户ID **/
	private String custId;
	/** 互生号 **/
	private String hsResNo;
	/** 申请人姓名 **/
	private String proposerName;
	/** 申请人电话 **/
	private String proposerPhone;
	/** 申请人证件号码 **/
	private String proposerPapersNo;
	/** 申请说明 **/
	private String explain;

	/** 批复金额 **/
	private String replyAmount;
	/** 医保卡号 **/
	private String healthCardNo;
	/** 诊疗开始日期 **/
	private String startDate;
	/** 诊疗结束日期 **/
	private String endDate;
	/** 所在城市/地址 **/
	private String city;
	/** 所在医院 **/
	private String hospital;

	/** 身故用户（被保障人）互生号 **/
	private String deathResNo;
	/** 身故用户（被保障人）姓名 */
	private String diePeopleName;

	/** 审批说明 **/
	private String approvalReason;
	/** 审批时间 **/
	private String approvalDate;
	/** 审批人 **/
	private String approvalPerson;

	/** 互生卡正面图片路径 **/
	private List<String> hscPositivePath;
	/** 互生卡背面图片路径 **/
	private List<String> hscReversePath;
	/** 身份证正面图片路径 **/
	private List<String> cerPositivePath;
	/** 身份证背面图片路径 **/
	private List<String> cerReversePath;
	/** 医疗证明图片路径 **/
	private List<String> medicalProvePath;
	/** 医保中心的受理回执复印件图片路径 **/
	private List<String> medicalAcceptPath;
	/** 医疗费用报销计算表复印件 图片路径 **/
	private List<String> costCountPath;
	/** 消费者本人的社会保障卡复印件 **/
	private List<String> sscPath;
	/** 住院病历复印件 图片路径 **/
	private List<String> imrPath;
	/** 原始收费收据复印件 图片路径) **/
	private List<String> ofrPath;
	/** 诊断证明书复印件 图片路径 **/
	private List<String> ddcPath;
	/** 费用明细清单复印件图片路径 **/
	private List<String> cdlPath;
	/** 门诊病历复印件图片路径 **/
	private List<String> omrPath;
	/** 身故人（被保障人）死亡证明 图片路径 */
	private List<String> deathProvePath;
	/** 身故人（被保障人）户籍注销证明 */
	private List<String> hrcPath;
	/** 身故人 （被保障人）法定身份证明 图片路径 */
	private List<String> diePeopleCerPath;
	/** 直系亲属证明书图片路径 */
	private List<String> ifpPath;
	/** 代理人授权委托书图片路径 */
	private List<String> agentAccreditPath;
	/** 申请保障代理人身份证明图片路径图片路径 */
	private List<String> aipPath;
	/** 其他证明图片路径 */
	private List<String> otherProvePath;

	/**
	 * @return the 申请保障代理人身份证明图片路径图片路径
	 */
	public List<String> getAipPath() {
		return aipPath;
	}

	/**
	 * @param 申请保障代理人身份证明图片路径图片路径
	 *            the aipPath to set
	 */
	public void setAipPath(List<String> aipPath) {
		this.aipPath = aipPath;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the 客户ID
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户ID
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 互生号
	 */
	public String getHsResNo() {
		return hsResNo;
	}

	/**
	 * @param 互生号
	 *            the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * @return the 申请人姓名
	 */
	public String getProposerName() {
		return proposerName;
	}

	/**
	 * @param 申请人姓名
	 *            the proposerName to set
	 */
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	/**
	 * @return the 申请人电话
	 */
	public String getProposerPhone() {
		return proposerPhone;
	}

	/**
	 * @param 申请人电话
	 *            the proposerPhone to set
	 */
	public void setProposerPhone(String proposerPhone) {
		this.proposerPhone = proposerPhone;
	}

	/**
	 * @return the 申请人证件号码
	 */
	public String getProposerPapersNo() {
		return proposerPapersNo;
	}

	/**
	 * @param 申请人证件号码
	 *            the proposerPapersNo to set
	 */
	public void setProposerPapersNo(String proposerPapersNo) {
		this.proposerPapersNo = proposerPapersNo;
	}

	/**
	 * @return the 说明
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * @param 说明
	 *            the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the 批复金额
	 */
	public String getReplyAmount() {
		return replyAmount;
	}

	/**
	 * @param 批复金额
	 *            the replyAmount to set
	 */
	public void setReplyAmount(String replyAmount) {
		this.replyAmount = replyAmount;
	}

	/**
	 * @return the 医保卡号
	 */
	public String getHealthCardNo() {
		return healthCardNo;
	}

	/**
	 * @param 医保卡号
	 *            the healthCardNo to set
	 */
	public void setHealthCardNo(String healthCardNo) {
		this.healthCardNo = healthCardNo;
	}

	/**
	 * @return the 诊疗开始日期
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param 诊疗开始日期
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the 诊疗结束日期
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 诊疗结束日期
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 所在城市地址
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param 所在城市地址
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the 所在医院
	 */
	public String getHospital() {
		return hospital;
	}

	/**
	 * @param 所在医院
	 *            the hospital to set
	 */
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	/**
	 * @return the 身故用户（被保障人）互生号
	 */
	public String getDeathResNo() {
		return deathResNo;
	}

	/**
	 * @param 身故用户
	 *            （被保障人）互生号 the deathResNo to set
	 */
	public void setDeathResNo(String deathResNo) {
		this.deathResNo = deathResNo;
	}

	/**
	 * @return the 身故用户（被保障人）姓名
	 */
	public String getDiePeopleName() {
		return diePeopleName;
	}

	/**
	 * @param 身故用户
	 *            （被保障人）姓名 the diePeopleName to set
	 */
	public void setDiePeopleName(String diePeopleName) {
		this.diePeopleName = diePeopleName;
	}

	/**
	 * @return the 审批说明
	 */
	public String getApprovalReason() {
		return approvalReason;
	}

	/**
	 * @param 审批说明
	 *            the approvalReason to set
	 */
	public void setApprovalReason(String approvalReason) {
		this.approvalReason = approvalReason;
	}

	/**
	 * @return the 审批时间
	 */
	public String getApprovalDate() {
		return approvalDate;
	}

	/**
	 * @param 审批时间
	 *            the approvalDate to set
	 */
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * @return the 互生卡正面图片路径
	 */
	public List<String> getHscPositivePath() {
		return hscPositivePath;
	}

	/**
	 * @param 互生卡正面图片路径
	 *            the hscPositivePath to set
	 */
	public void setHscPositivePath(List<String> hscPositivePath) {
		this.hscPositivePath = hscPositivePath;
	}

	/**
	 * @return the 互生卡背面图片路径
	 */
	public List<String> getHscReversePath() {
		return hscReversePath;
	}

	/**
	 * @param 互生卡背面图片路径
	 *            the hscReversePath to set
	 */
	public void setHscReversePath(List<String> hscReversePath) {
		this.hscReversePath = hscReversePath;
	}

	/**
	 * @return the 身份证正面图片路径
	 */
	public List<String> getCerPositivePath() {
		return cerPositivePath;
	}

	/**
	 * @param 身份证正面图片路径
	 *            the cerPositivePath to set
	 */
	public void setCerPositivePath(List<String> cerPositivePath) {
		this.cerPositivePath = cerPositivePath;
	}

	/**
	 * @return the 身份证背面图片路径
	 */
	public List<String> getCerReversePath() {
		return cerReversePath;
	}

	/**
	 * @param 身份证背面图片路径
	 *            the cerReversePath to set
	 */
	public void setCerReversePath(List<String> cerReversePath) {
		this.cerReversePath = cerReversePath;
	}

	/**
	 * @return the 医疗证明图片路径
	 */
	public List<String> getMedicalProvePath() {
		return medicalProvePath;
	}

	/**
	 * @param 医疗证明图片路径
	 *            the medicalProvePath to set
	 */
	public void setMedicalProvePath(List<String> medicalProvePath) {
		this.medicalProvePath = medicalProvePath;
	}

	/**
	 * @return the 医保中心的受理回执复印件图片路径
	 */
	public List<String> getMedicalAcceptPath() {
		return medicalAcceptPath;
	}

	/**
	 * @param 医保中心的受理回执复印件图片路径
	 *            the medicalAcceptPath to set
	 */
	public void setMedicalAcceptPath(List<String> medicalAcceptPath) {
		this.medicalAcceptPath = medicalAcceptPath;
	}

	/**
	 * @return the 医疗费用报销计算表复印件图片路径
	 */
	public List<String> getCostCountPath() {
		return costCountPath;
	}

	/**
	 * @param 医疗费用报销计算表复印件图片路径
	 *            the costCountPath to set
	 */
	public void setCostCountPath(List<String> costCountPath) {
		this.costCountPath = costCountPath;
	}

	/**
	 * @return the 消费者本人的社会保障卡复印件
	 */
	public List<String> getSscPath() {
		return sscPath;
	}

	/**
	 * @param 消费者本人的社会保障卡复印件
	 *            the sscPath to set
	 */
	public void setSscPath(List<String> sscPath) {
		this.sscPath = sscPath;
	}

	/**
	 * @return the 住院病历复印件图片路径
	 */
	public List<String> getImrPath() {
		return imrPath;
	}

	/**
	 * @param 住院病历复印件图片路径
	 *            the imrPath to set
	 */
	public void setImrPath(List<String> imrPath) {
		this.imrPath = imrPath;
	}

	/**
	 * @return the 原始收费收据复印件图片路径)
	 */
	public List<String> getOfrPath() {
		return ofrPath;
	}

	/**
	 * @param 原始收费收据复印件图片路径
	 *            ) the ofrPath to set
	 */
	public void setOfrPath(List<String> ofrPath) {
		this.ofrPath = ofrPath;
	}

	/**
	 * @return the 诊断证明书复印件图片路径
	 */
	public List<String> getDdcPath() {
		return ddcPath;
	}

	/**
	 * @param 诊断证明书复印件图片路径
	 *            the ddcPath to set
	 */
	public void setDdcPath(List<String> ddcPath) {
		this.ddcPath = ddcPath;
	}

	/**
	 * @return the 费用明细清单复印件图片路径
	 */
	public List<String> getCdlPath() {
		return cdlPath;
	}

	/**
	 * @param 费用明细清单复印件图片路径
	 *            the cdlPath to set
	 */
	public void setCdlPath(List<String> cdlPath) {
		this.cdlPath = cdlPath;
	}

	/**
	 * @return the 门诊病历复印件图片路径
	 */
	public List<String> getOmrPath() {
		return omrPath;
	}

	/**
	 * @param 门诊病历复印件图片路径
	 *            the omrPath to set
	 */
	public void setOmrPath(List<String> omrPath) {
		this.omrPath = omrPath;
	}

	/**
	 * @return the 身故人（被保障人）死亡证明图片路径
	 */
	public List<String> getDeathProvePath() {
		return deathProvePath;
	}

	/**
	 * @param 身故人
	 *            （被保障人）死亡证明图片路径 the deathProvePath to set
	 */
	public void setDeathProvePath(List<String> deathProvePath) {
		this.deathProvePath = deathProvePath;
	}

	/**
	 * @return the 身故人（被保障人）户籍注销证明
	 */
	public List<String> getHrcPath() {
		return hrcPath;
	}

	/**
	 * @param 身故人
	 *            （被保障人）户籍注销证明 the hrcPath to set
	 */
	public void setHrcPath(List<String> hrcPath) {
		this.hrcPath = hrcPath;
	}

	/**
	 * @return the 身故人（被保障人）法定身份证明图片路径
	 */
	public List<String> getDiePeopleCerPath() {
		return diePeopleCerPath;
	}

	/**
	 * @param 身故人
	 *            （被保障人）法定身份证明图片路径 the diePeopleCerPath to set
	 */
	public void setDiePeopleCerPath(List<String> diePeopleCerPath) {
		this.diePeopleCerPath = diePeopleCerPath;
	}

	/**
	 * @return the 直系亲属证明书图片路径
	 */
	public List<String> getIfpPath() {
		return ifpPath;
	}

	/**
	 * @param 直系亲属证明书图片路径
	 *            the ifpPath to set
	 */
	public void setIfpPath(List<String> ifpPath) {
		this.ifpPath = ifpPath;
	}

	/**
	 * @return the 代理人法定身份证明图片路径
	 */
	public List<String> getAgentAccreditPath() {
		return agentAccreditPath;
	}

	/**
	 * @param 代理人法定身份证明图片路径
	 *            the agentAccreditPath to set
	 */
	public void setAgentAccreditPath(List<String> agentAccreditPath) {
		this.agentAccreditPath = agentAccreditPath;
	}

	/**
	 * @return the 其他证明图片路径
	 */
	public List<String> getOtherProvePath() {
		return otherProvePath;
	}

	/**
	 * @param 其他证明图片路径
	 *            the otherProvePath to set
	 */
	public void setOtherProvePath(List<String> otherProvePath) {
		this.otherProvePath = otherProvePath;
	}

	public String getApprovalPerson() {
		return approvalPerson;
	}

	public void setApprovalPerson(String approvalPerson) {
		this.approvalPerson = approvalPerson;
	}
	
	

}
