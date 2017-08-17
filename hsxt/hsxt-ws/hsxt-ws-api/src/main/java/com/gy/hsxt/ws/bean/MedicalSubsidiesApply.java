/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.io.Serializable;
import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.common.WsErrorCode;

/**
 * 医疗补贴申请 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: MedicalSubsidiesApply
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午8:02:39
 * @version V3.0
 */
public class MedicalSubsidiesApply extends BaseApply implements Serializable {
	private static final long serialVersionUID = -6599932096337381141L;

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

	/** 互生卡正面图片路径 **/
	private List<String> hscPositivePath;
	/** 互生卡背面图片路径 **/
	private List<String> hscReversePath;
	/** 身份证正面图片路径 **/
	private List<String> cerPositivePath;
	/** 身份证背面图片路径 **/
	private List<String> cerReversePath;
	/** 本人社保卡复印件图片路径 **/
	private List<String> sscPath;
	/** 原始收费收据复印件图片路径 **/
	private List<String> ofrPath;
	/** 费用明细清单复印件图片路径 **/
	private List<String> cdlPath;
	/** 门诊病历复印件图片路径 **/
	private List<String> omrPath;
	/** 住院病历复印件图片路径 **/
	private List<String> imrPath;
	/** 疾病诊断证明书复印件图片路径 **/
	private List<String> ddcPath;
	/** 医保中心的受理回执复印件图片路径 **/
	private List<String> medicalAcceptPath;
	/** 医疗费用报销计算表复印件 图片路径 **/
	private List<String> costCountPath;
	/** 其他证明图片路径 **/
	private List<String> otherProvePath;

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
	 * @return the 本人社保卡复印件图片路径
	 */
	public List<String> getSscPath() {
		return sscPath;
	}

	/**
	 * @param 本人社保卡复印件图片路径
	 *            the sscPath to set
	 */
	public void setSscPath(List<String> sscPath) {
		this.sscPath = sscPath;
	}

	/**
	 * @return the 原始收费收据复印件图片路径
	 */
	public List<String> getOfrPath() {
		return ofrPath;
	}

	/**
	 * @param 原始收费收据复印件图片路径
	 *            the ofrPath to set
	 */
	public void setOfrPath(List<String> ofrPath) {
		this.ofrPath = ofrPath;
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
	 * @return the 疾病诊断证明书复印件图片路径
	 */
	public List<String> getDdcPath() {
		return ddcPath;
	}

	/**
	 * @param 疾病诊断证明书复印件图片路径
	 *            the ddcPath to set
	 */
	public void setDdcPath(List<String> ddcPath) {
		this.ddcPath = ddcPath;
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

	@Override
	public void basicValid() {
		super.basicValid();
		if (isBlank(startDate)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[startDate]为空");
		}
		if (isBlank(endDate)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[endDate]为空");
		}
		if (isBlank(city)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[city]为空");
		}
		if (isBlank(hospital)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[hospital]为空");
		}
		if (hscPositivePath == null || hscPositivePath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
					"必传参数[hscPositivePath]为null或空");
		}
		if (hscReversePath == null || hscReversePath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
					"必传参数[hscReversePath]为null或空");
		}
		if (cerPositivePath == null || cerPositivePath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
					"必传参数[cerPositivePath]为null或空");
		}
		if (cerReversePath == null || cerReversePath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
					"必传参数[cerReversePath]为null或空");
		}
		if (isBlank(this.getProposerPhone())) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[proposerPhone]为空");
		}
		if (isBlank(this.getProposerPapersNo())) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[proposerPapersNo]为空");
		}

	}

}
