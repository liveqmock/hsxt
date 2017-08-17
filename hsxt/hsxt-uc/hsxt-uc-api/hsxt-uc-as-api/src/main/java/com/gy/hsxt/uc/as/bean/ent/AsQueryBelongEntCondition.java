/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.ent;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.io.Serializable;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;

/**
 * 查询隶属企业条件对象
 * 
 * @Package: com.gy.hsxt.uc.as.bean.ent
 * @ClassName: AsQueryBelongEntCondition
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-9 下午4:57:23
 * @version V1.0
 */
public class AsQueryBelongEntCondition implements Serializable {
	
	public static void main(String[] args) {
		AsQueryBelongEntCondition condi = new AsQueryBelongEntCondition();
		System.out.println("show["+condi.getNoMcs()+"]");
	}
	private static final long serialVersionUID = 1L;
	/** 查询企业的互生号 必传 */
	private String entResNo;
	/** 隶属企业的客户类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司; 必传 */
	private Integer blongEntCustType;
	/** 隶属企业的互生号 选传 */
	private String belongEntResNo;
	/** 隶属企业的企业名称 选传 */
	private String belongEntName;
	/** 隶属企业联系人名称 选传 */
	private String belongEntContacts;
	/** 隶属企业状态 选传 */
	private Integer belongEntBaseStatus;
	/** 隶属企业开通时间>查询开始日期 格式：2015-10-10 选传 */
	private String beginDate;
	/** 隶属企业开通时间>查询结束日期 格式：2016-10-10 选传 */
	private String endDate;

	/** 企业是否关闭（冻结）1:是 0：否 */
	private Integer isClosedEnt;
	/** 父企业互生号前缀+% */
	private String pidLike;
	
	/** 国家*/
	private String countryNo ;//       国家        String        否
	/** 省份*/
	private String provinceNo ;//       省份        String        否
	/** 城市*/
	private String cityNo ;//
	/** 非注销状态,1 只查询非注销状态的数据;null 则查询全部状态*/
	private String noCancel ;
	/** 不包含管理公司    1：不包含  0或者空：包含*/
	private String noMcs;
	
	/**
	 * @return the 查询企业的互生号必传
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 查询企业的互生号必传
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 隶属企业的客户类型2成员企业;3托管企业;4服务公司;5管理公司;必传
	 */
	public Integer getBlongEntCustType() {
		return blongEntCustType;
	}

	/**
	 * @param 隶属企业的客户类型2成员企业
	 *            ;3托管企业;4服务公司;5管理公司;必传 the blongEntCustType to set
	 */
	public void setBlongEntCustType(Integer blongEntCustType) {
		this.blongEntCustType = blongEntCustType;
	}

	/**
	 * @return the 隶属企业的互生号选传
	 */
	public String getBelongEntResNo() {
		return belongEntResNo;
	}

	/**
	 * @param 隶属企业的互生号选传
	 *            the belongEntResNo to set
	 */
	public void setBelongEntResNo(String belongEntResNo) {
		this.belongEntResNo = belongEntResNo;
	}

	/**
	 * @return the 隶属企业的企业名称选传
	 */
	public String getBelongEntName() {
		return belongEntName;
	}

	/**
	 * @param 隶属企业的企业名称选传
	 *            the belongEntName to set
	 */
	public void setBelongEntName(String belongEntName) {
		this.belongEntName = belongEntName;
	}

	/**
	 * @return the 隶属企业联系人名称选传
	 */
	public String getBelongEntContacts() {
		return belongEntContacts;
	}

	/**
	 * @param 隶属企业联系人名称选传
	 *            the belongEntContacts to set
	 */
	public void setBelongEntContacts(String belongEntContacts) {
		this.belongEntContacts = belongEntContacts;
	}

	/**
	 * @return the 隶属企业状态选传
	 */
	public Integer getBelongEntBaseStatus() {
		return belongEntBaseStatus;
	}

	/**
	 * @param 隶属企业状态选传
	 *            the belongEntBaseStatus to set
	 */
	public void setBelongEntBaseStatus(Integer belongEntBaseStatus) {
		this.belongEntBaseStatus = belongEntBaseStatus;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the 隶属企业开通时间>查询开始日期选传
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 隶属企业开通时间
	 *            >查询开始日期选传 the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the 隶属企业开通时间>查询结束日期选传
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 隶属企业开通时间
	 *            >查询结束日期选传 the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 企业是否关闭（冻结）1:是0：否
	 */
	public Integer getIsClosedEnt() {
		return isClosedEnt;
	}

	/**
	 * @param 企业是否关闭
	 *            （冻结）1:是0：否 the isClosedEnt to set
	 */
	public void setIsClosedEnt(Integer isClosedEnt) {
		this.isClosedEnt = isClosedEnt;
	}

	/**
	 * @return the 父企业互生号前缀+%
	 */
	public String getPidLike() {
		return pidLike;
	}

	/**
	 * @param 父企业互生号前缀
	 *            +% the pidLike to set
	 */
	public void setPidLike(String pidLike) {
		this.pidLike = pidLike;
	}
	
	

	/**
	 * @return the 国家
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 国家 the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 省份
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 省份 the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 城市
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 城市 the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getNoMcs() {
		return noMcs;
	}

	public void setNoMcs(String noMcs) {
		this.noMcs = noMcs;
	}

	/**
	 * @return the 非注销状态.1只查询非注销状态的数据;null则查询全部状态
	 */
	public String getNoCancel() {
		return noCancel;
	}

	/**
	 * @param 非注销状态.1只查询非注销状态的数据;null则查询全部状态 the noCancel to set
	 */
	public void setNoCancel(String noCancel) {
		this.noCancel = noCancel;
	}

	public void validParam() {
		// 验证数据
		if (isBlank(entResNo)) {

			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entResNo操作员所属企业互生号为空");
		}
//		if (isBlank(blongEntCustType)) {
//			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
//					"blongEntCustType企业类型不能 为空");
//		}
		if (isBlank(belongEntResNo)) {
			belongEntResNo = null;
		} else {
			belongEntResNo = "%" + belongEntResNo + "%";
		}
		if (isBlank(belongEntName)) {
			belongEntName = null;
		} else {
			belongEntName = "%" + belongEntName + "%";
		}
		if (isBlank(belongEntContacts)) {
			belongEntContacts = null;
		} else {
			belongEntContacts = "%" + belongEntContacts + "%";
		}
		if (isBlank(beginDate)) {
			beginDate = null;
		}
		if (isBlank(endDate)) {
			endDate = null;
		}
		if (isBlank(isClosedEnt)) {
			isClosedEnt = null;
		}
		if (isBlank(countryNo)) {
			countryNo = null;
		}
		if (isBlank(provinceNo)) {
			provinceNo = null;
		}
		if (isBlank(cityNo)) {
			cityNo = null;
		}
		if (isBlank(noCancel)) {
			noCancel = null;
		}

	}

	public void setPidLikeByType(Integer entCustType) {
		// 企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;
		switch (entCustType) {
		case 4: // 4 服务公司
			this.pidLike = entResNo.substring(0, 5) + "%";
			break;
		case 5: // 5 管理公司
			this.pidLike = entResNo.substring(0, 2) + "%";
			break;

		default: // 6 地区平台;

			break;
		}
	}
}
