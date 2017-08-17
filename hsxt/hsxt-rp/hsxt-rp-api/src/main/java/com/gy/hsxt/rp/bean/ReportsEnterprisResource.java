/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 企业资源对象
 * @author 作者 : 毛粲
 * @ClassName: 类名:ReportsEnterprisResource
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:35:52
 * @version 1.0
 */
public class ReportsEnterprisResource implements Serializable{


	private static final long serialVersionUID = -7919797944981742586L;

	/** 客户全局编号 **/
	private String     custId;
	
	/** 互生号  **/
	private String     hsResNo;
	
	/** 客户类型 **/
	private Integer    custType;
	
	/** 企业名称 **/
	private String		entCustName;
	
	/** 企业注册地址 **/
	private String		entRegAddr;
	
	/** 联系人 **/
	private String		contactPerson;
	
	/** 联系人电话 **/
	private String		contactPhone;
	
	/** 系统开启时间 **/
	private String		openDate;
	
	/** 实名认证状态：1已认证、0未认证 **/
	private Integer		realnameAuth;
	
	/** 参与积分状态: 2参与积分、1停止积分 **/
	private Integer		participationScore;
	
	/** 基本状态 **/
	private Integer		baseStatus;
	
	/** 已启用消费者数 **/
	private Integer		enabledCardholderNumber;
	
	/** 所在国家 **/
	private String		countryNo;
	
	/** 所在省份 **/
	private String		provinceNo;
	
	/** 所在城市 **/
	private String		cityNo;
	
	/** 推荐企业（平台、服务公司）申报企业的服务公司资源号 **/
	private String		applyEntResNo;
	
	/** 上级服务公司资源号 **/
	private String		serviceEntResNo;
	
	/** 上级管理公司资源号 **/
	private String		adminEntResNo;
	
	/** 上级地方平台资源号 **/
	private String		platformEntResNo;
	
	/** 系统开启时间开始 **/
	private String		openDateBegin;
	
	/** 系统开启时间结束 **/
	private String		openDateEnd;
	
	/** 系统开启时间开始Timestamp **/
	private Timestamp	openDateBeginTim;
	
	/** 系统开启时间结束Timestamp **/
	private Timestamp	openDateEndTim;
	
	/** 系统销售费收费方式:1：全额(默认) 2：全免 3：暂欠 **/
    private Integer     resFeeChargeMode;
    
    /** 企业资源类型 1：首段资源  2：创业资源  3：全部资源 4：正常成员企业 5：免费成员企业 **/
    private Integer     artResType;
    
    /** 经营类型：0-普通；1-连锁企业 **/
    private Integer     businessType;
	
    /** 实名认证状态：1已认证、0未认证  用于报表导出 **/
    private String     exportRealnameAuth;
    
    /** 参与积分状态: 2参与积分、1停止积分  用于报表导出**/
    private String     exportBaseStatus;
    
    /** 系统销售费收费方式:1：全额(默认) 2：全免 3：暂欠  用于报表导出**/
    private String     exportResFeeChargeMode;
    
    /** 企业资源类型 1：首段资源  2：创业资源  3：全部资源 4：正常成员企业 5：免费成员企业 用于报表导出 **/
    private String     exportArtResType;
    
    /** 经营类型：0-普通；1-连锁企业  用于报表导出**/
    private String     exportBusinessType;
    
	
	/**
	 * @return the 客户全局编号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户全局编号 the custId to set
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
	 * @param 互生号 the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * @return the 客户类型
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * @param 客户类型 the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	/**
	 * @return the 企业名称
	 */
	public String getEntCustName() {
		return entCustName;
	}

	/**
	 * @param 企业名称 the entCustName to set
	 */
	public void setEntCustName(String entCustName) {
		this.entCustName = entCustName;
	}

	/**
	 * @return the 企业注册地址
	 */
	public String getEntRegAddr() {
		return entRegAddr;
	}

	/**
	 * @param 企业注册地址 the entRegAddr to set
	 */
	public void setEntRegAddr(String entRegAddr) {
		this.entRegAddr = entRegAddr;
	}

	/**
	 * @return the 联系人
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param 联系人 the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the 联系人电话
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param 联系人电话 the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the 系统开启时间
	 */
	public String getOpenDate() {
		return openDate;
	}

	/**
	 * @param 系统开启时间 the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the 实名认证状态：1已认证、0未认证
	 */
	public Integer getRealnameAuth() {
		return realnameAuth;
	}

	/**
	 * @param 实名认证状态：1已认证、0未认证 the realnameAuth to set
	 */
	public void setRealnameAuth(Integer realnameAuth) {
		this.realnameAuth = realnameAuth;
	}

	/**
	 * @return the 参与积分状态:1参与积分、0停止积分
	 */
	public Integer getParticipationScore() {
		return participationScore;
	}

	/**
	 * @param 参与积分状态:1参与积分、0停止积分 the participationScore to set
	 */
	public void setParticipationScore(Integer participationScore) {
		this.participationScore = participationScore;
	}

	/**
	 * @return the 基本状态
	 */
	public Integer getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 基本状态 the baseStatus to set
	 */
	public void setBaseStatus(Integer baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 已启用消费者数
	 */
	public Integer getEnabledCardholderNumber() {
		return enabledCardholderNumber;
	}

	/**
	 * @param 已启用消费者数 the enabledCardholderNumber to set
	 */
	public void setEnabledCardholderNumber(Integer enabledCardholderNumber) {
		this.enabledCardholderNumber = enabledCardholderNumber;
	}

	/**
	 * @return the 所在国家
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 所在国家 the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 所在省份
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 所在省份 the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 所在城市
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 所在城市 the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * @return the 推荐企业（平台、服务公司）申报企业的服务公司资源号
	 */
	public String getApplyEntResNo() {
		return applyEntResNo;
	}

	/**
	 * @param 推荐企业（平台、服务公司）申报企业的服务公司资源号 the applyEntResNo to set
	 */
	public void setApplyEntResNo(String applyEntResNo) {
		this.applyEntResNo = applyEntResNo;
	}

	/**
	 * @return the 上级服务公司资源号
	 */
	public String getServiceEntResNo() {
		return serviceEntResNo;
	}

	/**
	 * @param 上级服务公司资源号 the serviceEntResNo to set
	 */
	public void setServiceEntResNo(String serviceEntResNo) {
		this.serviceEntResNo = serviceEntResNo;
	}

	/**
	 * @return the 上级管理公司资源号
	 */
	public String getAdminEntResNo() {
		return adminEntResNo;
	}

	/**
	 * @param 上级管理公司资源号 the adminEntResNo to set
	 */
	public void setAdminEntResNo(String adminEntResNo) {
		this.adminEntResNo = adminEntResNo;
	}

	/**
	 * @return the 上级地方平台资源号
	 */
	public String getPlatformEntResNo() {
		return platformEntResNo;
	}

	/**
	 * @param 上级地方平台资源号 the platformEntResNo to set
	 */
	public void setPlatformEntResNo(String platformEntResNo) {
		this.platformEntResNo = platformEntResNo;
	}

	/**
	 * @return the 系统开启时间开始
	 */
	public String getOpenDateBegin() {
		return openDateBegin;
	}

	/**
	 * @param 系统开启时间开始 the openDateBegin to set
	 */
	public void setOpenDateBegin(String openDateBegin) {
		this.openDateBegin = openDateBegin;
	}

	/**
	 * @return the 系统开启时间结束
	 */
	public String getOpenDateEnd() {
		return openDateEnd;
	}

	/**
	 * @param 系统开启时间结束 the openDateEnd to set
	 */
	public void setOpenDateEnd(String openDateEnd) {
		this.openDateEnd = openDateEnd;
	}

	/**
	 * @return the 系统开启时间开始Timestamp
	 */
	public Timestamp getOpenDateBeginTim() {
		return openDateBeginTim;
	}

	/**
	 * @param 系统开启时间开始Timestamp the openDateBeginTim to set
	 */
	public void setOpenDateBeginTim(Timestamp openDateBeginTim) {
		this.openDateBeginTim = openDateBeginTim;
	}

	/**
	 * @return the 系统开启时间结束Timestamp
	 */
	public Timestamp getOpenDateEndTim() {
		return openDateEndTim;
	}

	/**
	 * @param 系统开启时间结束Timestamp the openDateEndTim to set
	 */
	public void setOpenDateEndTim(Timestamp openDateEndTim) {
		this.openDateEndTim = openDateEndTim;
	}

    /**
     * @return the 系统销售费收费方式:1：全额(默认)2：全免3：暂欠
     */
    public Integer getResFeeChargeMode() {
        return resFeeChargeMode;
    }

    /**
     * @param 系统销售费收费方式:1：全额(默认)2：全免3：暂欠 the resFeeChargeMode to set
     */
    public void setResFeeChargeMode(Integer resFeeChargeMode) {
        this.resFeeChargeMode = resFeeChargeMode;
    }

    /**
     * @return the 企业资源类型1：首段资源2：创业资源3：全部资源4：正常成员企业5：免费成员企业
     */
    public Integer getArtResType() {
        return artResType;
    }

    /**
     * @param 企业资源类型1：首段资源2：创业资源3：全部资源4：正常成员企业5：免费成员企业 the artResType to set
     */
    public void setArtResType(Integer artResType) {
        this.artResType = artResType;
    }

    /**
     * @return the 经营类型：0-普通；1-连锁企业
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * @param 经营类型：0-普通；1-连锁企业 the businessType to set
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * @return the 实名认证状态：1已认证、0未认证用于报表导出
     */
    public String getExportRealnameAuth() {
        return exportRealnameAuth;
    }

    /**
     * @param 实名认证状态：1已认证、0未认证用于报表导出 the exportRealnameAuth to set
     */
    public void setExportRealnameAuth(String exportRealnameAuth) {
        this.exportRealnameAuth = exportRealnameAuth;
    }

    /**
     * @return the 参与积分状态:2参与积分、1停止积分用于报表导出
     */
    public String getExportBaseStatus() {
        return exportBaseStatus;
    }

    /**
     * @param 参与积分状态:2参与积分、1停止积分用于报表导出 the exportBaseStatus to set
     */
    public void setExportBaseStatus(String exportBaseStatus) {
        this.exportBaseStatus = exportBaseStatus;
    }

    /**
     * @return the 系统销售费收费方式:1：全额(默认)2：全免3：暂欠用于报表导出
     */
    public String getExportResFeeChargeMode() {
        return exportResFeeChargeMode;
    }

    /**
     * @param 系统销售费收费方式:1：全额(默认)2：全免3：暂欠用于报表导出 the exportResFeeChargeMode to set
     */
    public void setExportResFeeChargeMode(String exportResFeeChargeMode) {
        this.exportResFeeChargeMode = exportResFeeChargeMode;
    }

    /**
     * @return the 企业资源类型1：首段资源2：创业资源3：全部资源4：正常成员企业5：免费成员企业用于报表导出
     */
    public String getExportArtResType() {
        return exportArtResType;
    }

    /**
     * @param 企业资源类型1：首段资源2：创业资源3：全部资源4：正常成员企业5：免费成员企业用于报表导出 the exportArtResType to set
     */
    public void setExportArtResType(String exportArtResType) {
        this.exportArtResType = exportArtResType;
    }

    /**
     * @return the 经营类型：0-普通；1-连锁企业用于报表导出
     */
    public String getExportBusinessType() {
        return exportBusinessType;
    }

    /**
     * @param 经营类型：0-普通；1-连锁企业用于报表导出 the exportBusinessType to set
     */
    public void setExportBusinessType(String exportBusinessType) {
        this.exportBusinessType = exportBusinessType;
    }
	
	
	
}
