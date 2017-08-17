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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

/**
 * 消费者资源对象
 * @author 作者 : 毛粲
 * @ClassName: 类名:ReportsCardholderResource
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:35:52
 * @version 1.0
 */
public class ReportsCardholderResource implements Serializable{


	private static final long serialVersionUID = -7919797944981742586L;

	/** 客户全局编号 **/
	private String     custId;
	
	/** 互生号  **/
	private String     hsResNo;
	
	/** 开始卡号  **/
    private String     beginCard;
    
    /** 结束卡号 **/
    private String    endCard;
	
	/** 客户类型 **/
	private Integer    custType;
	
	/** 姓名 **/
	private String		perName;
	
	/** 证件类型1：身份证 2：护照 3：营业执照 **/
	private Integer		idType;
	
	/** 证件号 **/
	private String		idNo;
	
	/** 国籍 **/
	private String		countryName;
	
	/** 手机号 **/
	private String		mobile;
	
	/** 户籍地址 **/
	private String		residentAddr;
	
	/** 实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 **/
	private Integer		realnameAuth;
	
	/** 实名状态更新时间 **/
	private String		realnameAuthDate;
	
	/** 基本状态 1：不活跃、2：活跃、3：休眠、4：沉淀 **/
	private Integer		baseStatus;
	
	/** 隶属托管企业(资源号) **/
	private String		entResNo;
	
	/** 上级服务公司资源号 **/
	private String		serviceEntResNo;
	
	/** 上级管理公司资源号 **/
	private String		adminEntResNo;
	
	/** 上级地方平台资源号 **/
	private String		platformEntResNo;
	
	/** 实名状态更新时间开始 **/
	private String		realnameAuthDateBegin;
	
	/** 实名状态更新时间结束 **/
	private String		realnameAuthDateEnd;
	
	/** 实名状态更新时间开始Timestamp **/
	private Timestamp	realnameAuthDateBeginTim;
	
	/** 实名状态更新时间结束Timestamp **/
	private Timestamp	realnameAuthDateEndTim;

	/** 实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证   用于报表导出**/
    private String     exportRealnameAuth;
	
    /** 基本状态 1：不活跃、2：活跃、3：休眠、4：沉淀  用于报表导出**/
    private String     exportBaseStatus;
	
    /** 证件类型1：身份证 2：护照 3：营业执照 **/
    private String     exportIdType;
    
    
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
     * @return the 开始卡号
     */
    public String getBeginCard() {
        return beginCard;
    }

    /**
     * @param 开始卡号 the beginCard to set
     */
    public void setBeginCard(String beginCard) {
        this.beginCard = beginCard;
    }

    /**
     * @return the 结束卡号
     */
    public String getEndCard() {
        return endCard;
    }

    /**
     * @param 结束卡号 the endCard to set
     */
    public void setEndCard(String endCard) {
        this.endCard = endCard;
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
	 * @return the 姓名
	 */
	public String getPerName() {
		return perName;
	}

	/**
	 * @param 姓名 the perName to set
	 */
	public void setPerName(String perName) {
		this.perName = perName;
	}

	/**
	 * @return the 国籍
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param 国籍 the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the 手机号
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param 手机号 the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the 户籍地址
	 */
	public String getResidentAddr() {
		return residentAddr;
	}

	/**
	 * @param 户籍地址 the residentAddr to set
	 */
	public void setResidentAddr(String residentAddr) {
		this.residentAddr = residentAddr;
	}

	/**
	 * @return the 实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 */
	public Integer getRealnameAuth() {
		return realnameAuth;
	}

	/**
	 * @param 实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 the realnameAuth to set
	 */
	public void setRealnameAuth(Integer realnameAuth) {
		this.realnameAuth = realnameAuth;
	}

	/**
	 * @return the 实名状态更新时间
	 */
	public String getRealnameAuthDate() {
		return realnameAuthDate;
	}

	/**
	 * @param 实名状态更新时间 the realnameAuthDate to set
	 */
	public void setRealnameAuthDate(String realnameAuthDate) {
	    if(null == realnameAuthDate || "".equals(realnameAuthDate)){
	        this.realnameAuthDate = realnameAuthDate;
	    }else{
	        String[] str = realnameAuthDate.split("\\.");
	        if(str == null || str.length == 0){
	            this.realnameAuthDate = realnameAuthDate;
	        }else{
	            this.realnameAuthDate = str[0];
	        }
	    }
	}

	/**
	 * @return the 基本状态1：不活跃、2：活跃、3：休眠、4：沉淀
	 */
	public Integer getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 基本状态1：不活跃、2：活跃、3：休眠、4：沉淀 the baseStatus to set
	 */
	public void setBaseStatus(Integer baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 隶属托管企业(资源号)
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 隶属托管企业(资源号) the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
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
	 * @return the 实名状态更新时间开始
	 */
	public String getRealnameAuthDateBegin() {
		return realnameAuthDateBegin;
	}

	/**
	 * @param 实名状态更新时间开始 the realnameAuthDateBegin to set
	 */
	public void setRealnameAuthDateBegin(String realnameAuthDateBegin) {
		this.realnameAuthDateBegin = realnameAuthDateBegin;
	}

	/**
	 * @return the 实名状态更新时间结束
	 */
	public String getRealnameAuthDateEnd() {
		return realnameAuthDateEnd;
	}

	/**
	 * @param 实名状态更新时间结束 the realnameAuthDateEnd to set
	 */
	public void setRealnameAuthDateEnd(String realnameAuthDateEnd) {
		this.realnameAuthDateEnd = realnameAuthDateEnd;
	}

	/**
	 * @return the 实名状态更新时间开始Timestamp
	 */
	public Timestamp getRealnameAuthDateBeginTim() {
		return realnameAuthDateBeginTim;
	}

	/**
	 * @param 实名状态更新时间开始Timestamp the realnameAuthDateBeginTim to set
	 */
	public void setRealnameAuthDateBeginTim(Timestamp realnameAuthDateBeginTim) {
		this.realnameAuthDateBeginTim = realnameAuthDateBeginTim;
	}

	/**
	 * @return the 实名状态更新时间结束Timestamp
	 */
	public Timestamp getRealnameAuthDateEndTim() {
		return realnameAuthDateEndTim;
	}

	/**
	 * @param 实名状态更新时间结束Timestamp the realnameAuthDateEndTim to set
	 */
	public void setRealnameAuthDateEndTim(Timestamp realnameAuthDateEndTim) {
		this.realnameAuthDateEndTim = realnameAuthDateEndTim;
	}

	/**
	 * @return the 证件类型1：身份证2：护照3：营业执照
	 */
	public Integer getIdType() {
		return idType;
	}

	/**
	 * @param 证件类型1：身份证2：护照3：营业执照 the idType to set
	 */
	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	/**
	 * @return the 证件号
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param 证件号 the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

    /**
     * @return the 实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证用于报表导出
     */
    public String getExportRealnameAuth() {
        return exportRealnameAuth;
    }

    /**
     * @param 实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证用于报表导出 the exportRealnameAuth to set
     */
    public void setExportRealnameAuth(String exportRealnameAuth) {
        this.exportRealnameAuth = exportRealnameAuth;
    }

    /**
     * @return the 基本状态1：不活跃、2：活跃、3：休眠、4：沉淀用于报表导出
     */
    public String getExportBaseStatus() {
        return exportBaseStatus;
    }

    /**
     * @param 基本状态1：不活跃、2：活跃、3：休眠、4：沉淀用于报表导出 the exportBaseStatus to set
     */
    public void setExportBaseStatus(String exportBaseStatus) {
        this.exportBaseStatus = exportBaseStatus;
    }

    /**
     * @return the 证件类型1：身份证2：护照3：营业执照
     */
    public String getExportIdType() {
        return exportIdType;
    }

    /**
     * @param 证件类型1：身份证2：护照3：营业执照 the exportIdType to set
     */
    public void setExportIdType(String exportIdType) {
        this.exportIdType = exportIdType;
    }

	
}
