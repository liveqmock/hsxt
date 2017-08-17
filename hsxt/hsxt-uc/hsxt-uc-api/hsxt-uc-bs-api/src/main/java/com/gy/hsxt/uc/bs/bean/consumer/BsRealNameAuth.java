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

package com.gy.hsxt.uc.bs.bean.consumer;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.bs.enumerate.CerTypeEnum;
import com.gy.hsxt.uc.bs.enumerate.ErrorCodeEnum;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.api.consumer
 * @ClassName: BsRealNameAuthInfo
 * @Description: 实名认证信息bean 
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午3:47:01
 * @version V1.0
 */
public class BsRealNameAuth implements Serializable {

	private static final long serialVersionUID = -6814718417497483638L;
	
	/** 持卡人客户号 */
	private String custId;
	
	/** 姓名 */
	private String userName;
	
	 /** 性别1：男 0：女 */
	private Integer sex;

	/** 证件类型  1：身份证 2：护照:3：营业执照 */
	private Integer cerType;
	
	/** 证件号码 */
	private String cerNo;
	
	/** 证件有效期 */
	private String validDate;
	
	/** 发证机关 */
	private String issuingOrg;
	
	/** 户籍地址 */
	private String birthAddress;
	
	/** 国家代码 */
	private String countryCode;
	
	/** 国家名称 */
	private String countryName;
	
	/** 证件正面照 */
	private String cerPica;
	
	/** 证件背面照 */
	private String cerPicb;
	
	/** 手持证件照 */
	private String cerPich;
	
	/** 实名状态 */
	private String realNameStatus;
	
	 /** 职业 */
    private String job;

    /** 出生地 */
    private String birthPlace;

    /** 签发地点  */
    private String issuePlace;

    /** 企业名称 */
    private String entName;

    /** 企业注册地址 */
    private String entRegAddr;

    /** 企业类型 */
    private String entType;

    /** 企业成立日期 */
    private String entBuildDate;

    /** 认证附言 */
    private String authRemark;
    
    /**
     * @return the 职业
     */
    public String getJob() {
        return job;
    }
    
    /**
     * @param 职业
     *            the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }
    /**
     * @return the 出生地
     */
    public String getBirthPlace() {
        return birthPlace;
    }
    
    /**
     * @param 出生地
     *            the birthPlace to set
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
    /**
     * @return the 签发地点
     */
    public String getIssuePlace() {
        return issuePlace;
    }
    
    /**
     * @param 签发地点
     *            the issuePlace to set
     */
    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }
    
    /**
     * @return the 企业名称
     */
    public String getEntName() {
        return entName;
    }
    
    /**
     * @param 企业名称
     *            the entName to set
     */
    public void setEntName(String entName) {
        this.entName = entName;
    }
    
    /**
     * @return the 企业注册地址
     */
    public String getEntRegAddr() {
        return entRegAddr;
    }
    
    /**
     * @param 企业注册地址
     *            the entRegAddr to set
     */
    public void setEntRegAddr(String entRegAddr) {
        this.entRegAddr = entRegAddr;
    }
    
    /**
     * @return the 企业类型
     */
    public String getEntType() {
        return entType;
    }
    
    /**
     * @param 客户号
     *            the entType to set
     */
    public void setEntType(String entType) {
        this.entType = entType;
    }
    
    /**
     * @return the 客户号
     */
    public String getEntBuildDate() {
        return entBuildDate;
    }
    
    /**
     * @param 客户号
     *            the entBuildDate to set
     */
    public void setEntBuildDate(String entBuildDate) {
        this.entBuildDate = entBuildDate;
    }
    
    /**
     * @return the 认证附言
     */
    public String getAuthRemark() {
        return authRemark;
    }
    
    /**
     * @param 客户号
     *            the authRemark to set
     */
    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }
    
    /**
     * @return the 国籍
     */
    public String getCountryName() {
        return countryName;
    }
    /**
     * @param 国籍
     *            the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
    }
  
    
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getCerType() {
		return cerType;
	}
	public void setCerType(Integer cerType) {
		this.cerType = cerType;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getIssuingOrg() {
		return issuingOrg;
	}
	public void setIssuingOrg(String issuingOrg) {
		this.issuingOrg = issuingOrg;
	}
	public String getBirthAddress() {
		return birthAddress;
	}
	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCerPica() {
		return cerPica;
	}
	public void setCerPica(String cerPica) {
		this.cerPica = cerPica;
	}
	public String getCerPicb() {
		return cerPicb;
	}
	public void setCerPicb(String cerPicb) {
		this.cerPicb = cerPicb;
	}
	public String getCerPich() {
		return cerPich;
	}
	public void setCerPich(String cerPich) {
		this.cerPich = cerPich;
	}
	public String getRealNameStatus() {
		return realNameStatus;
	}
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}

    public Integer getSex() {
        return sex;
    }
	
    /**
     * 检查实名认证的信息完整性
     * @param bsRealNameAuth   实名认证信息
     * @param operCustId       操作员客户号
     */
    public void checkRealNameInfoComplete(BsRealNameAuth bsRealNameAuth,String operCustId){
        if(null == bsRealNameAuth){
            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数实名认证信息不能为空");
        }
        if(StringUtils.isBlank(operCustId)){
            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数操作员客户号不能为空");
        }
        if(StringUtils.isBlank(bsRealNameAuth.getCustId())){
            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数客户号不能为空");
        }
        if(null == bsRealNameAuth.getCerType()){
            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件类型不能为空");
        }
        if(StringUtils.isBlank(bsRealNameAuth.getCerNo())){
            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件号码不能为空");
        }
        
//        if(StringUtils.isBlank(bsRealNameAuth.getCountryCode())){
//            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数国籍不能为空");
//        }
//        if(StringUtils.isBlank(bsRealNameAuth.getCerPica())){
//            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件正面不能为空");
//        }
//        if(StringUtils.isBlank(bsRealNameAuth.getCerPich())){
//            throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数手持证件照不能为空");
//        }
        int cerType = bsRealNameAuth.getCerType().intValue();
        if(CerTypeEnum.IDCARD.getType().equals( cerType)){//证件类型是身份证
        	if(StringUtils.isBlank(bsRealNameAuth.getUserName())){
                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数姓名不能为空");
            }
//            if(null == bsRealNameAuth.getSex()){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数性别不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getValidDate())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件有效期不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getBirthAddress())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数户籍地址不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getIssuingOrg())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数发证机关不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getJob())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数职业不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getCerPicb())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件反面不能为空");
//            }
        }else if(CerTypeEnum.PASSPORT.getType() == cerType){//证件类型是护照
        	if(StringUtils.isBlank(bsRealNameAuth.getUserName())){
                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数姓名不能为空");
            }
//            if(null == bsRealNameAuth.getSex()){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数性别不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getValidDate())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数证件有效期不能为空");
//            }
//  
//            if(StringUtils.isBlank(bsRealNameAuth.getIssuingOrg())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数发证机关不能为空");
//            }
////            if(StringUtils.isBlank(bsRealNameAuth.getJob())){
////                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数职业不能为空");
////            }
//            if(StringUtils.isBlank(bsRealNameAuth.getBirthPlace())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数出生地点不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getIssuePlace())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数签发地点不能为空");
//            }
        }else if(CerTypeEnum.BUSILICENCE.getType() == cerType){//证件类型为营业执照
            if(StringUtils.isBlank(bsRealNameAuth.getEntName())){
                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数企业名称不能为空");
            }
//            if(StringUtils.isBlank(bsRealNameAuth.getEntRegAddr())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数注册地址不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getEntType())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数公司类型不能为空");
//            }
//            if(StringUtils.isBlank(bsRealNameAuth.getEntBuildDate())){
//                throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "传入参数成立日期不能为空");
//            }
        }
    }
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
