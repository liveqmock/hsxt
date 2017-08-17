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
 * 身故保障金申请实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: OthersDieSecurityApply
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午8:03:58
 * @version V3.0
 */
public class OthersDieSecurityApply extends BaseApply implements Serializable {
	private static final long serialVersionUID = -5954987962965036023L;
	/** 身故用户（被保障人）互生号 **/
	private String deathResNo;
	/** 身故用户（被保障人）姓名 */
	private String diePeopleName;

	/** 身故人（被保障人）死亡证明 图片路径 */
	private List<String> deathProvePath;
	/** 身故人（被保障人）户籍注销证明 */
	private List<String> hrcPath;
	/** 身故人 （被保障人）法定身份证明 图片路径 */
	private List<String> diePeopleCerPath;

	/** 直系亲属证明书图片路径 */
	private List<String> ifpPath;
	/** 申请保障代理人身份证明图片路径图片路径 */
	private List<String> aipPath;
	/** 代理人委托授权书图片路径 */
	private List<String> agentAccreditPath;
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

	@Override
	public void basicValid() {
		super.basicValid();
		if (isBlank(deathResNo)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[deathResNo]为空");
		}
		if (isBlank(diePeopleName)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[diePeopleName]为空");
		}
		if (deathProvePath == null || deathProvePath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
					"必传参数[deathProvePath]为null或空");
		}
		if (hrcPath == null || hrcPath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[hrcPath]为null或空");
		}
		if (ifpPath == null || ifpPath.isEmpty()) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[ifpPath]为null或空");
		}
	}

}
