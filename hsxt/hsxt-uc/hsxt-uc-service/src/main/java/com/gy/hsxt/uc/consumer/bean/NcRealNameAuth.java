package com.gy.hsxt.uc.consumer.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;

public class NcRealNameAuth implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6949574690410020276L;

	private String perCustId;
	

    private String countryName;

    private Integer idType;

    private String idNo;

    private Integer sex;

    private String perName;

    private String idValidDate;

    private String idIssueOrg;

    private String residentAddr;

    private String certificateFront;

    private String certificateBack;

    private String certificateHandhold;

    private String job;

    private String birthPlace;

    private String issuePlace;

    private String entName;

    private String entRegAddr;

    private String entType;

    private String entBuildDate;

    private String authRemark;

    private String isactive;

    private Timestamp createDate;

    private String createdby;

    private Timestamp updateDate;

    private String updatedby;

    public String getPerCustId() {
        return perCustId;
    }

    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId == null ? null : perCustId.trim();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName == null ? null : perName.trim();
    }

    public String getIdValidDate() {
        return idValidDate;
    }

    public void setIdValidDate(String idValidDate) {
        this.idValidDate = idValidDate == null ? null : idValidDate.trim();
    }

    public String getIdIssueOrg() {
        return idIssueOrg;
    }

    public void setIdIssueOrg(String idIssueOrg) {
        this.idIssueOrg = idIssueOrg == null ? null : idIssueOrg.trim();
    }

    public String getResidentAddr() {
        return residentAddr;
    }

    public void setResidentAddr(String residentAddr) {
        this.residentAddr = residentAddr == null ? null : residentAddr.trim();
    }

    public String getCertificateFront() {
        return certificateFront;
    }

    public void setCertificateFront(String certificateFront) {
        this.certificateFront = certificateFront == null ? null : certificateFront.trim();
    }

    public String getCertificateBack() {
        return certificateBack;
    }

    public void setCertificateBack(String certificateBack) {
        this.certificateBack = certificateBack == null ? null : certificateBack.trim();
    }

    public String getCertificateHandhold() {
        return certificateHandhold;
    }

    public void setCertificateHandhold(String certificateHandhold) {
        this.certificateHandhold = certificateHandhold == null ? null : certificateHandhold.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace == null ? null : birthPlace.trim();
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace == null ? null : issuePlace.trim();
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName == null ? null : entName.trim();
    }

    public String getEntRegAddr() {
        return entRegAddr;
    }

    public void setEntRegAddr(String entRegAddr) {
        this.entRegAddr = entRegAddr == null ? null : entRegAddr.trim();
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType == null ? null : entType.trim();
    }

    public String getEntBuildDate() {
        return entBuildDate;
    }

    public void setEntBuildDate(String entBuildDate) {
        this.entBuildDate = entBuildDate == null ? null : entBuildDate.trim();
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark == null ? null : authRemark.trim();
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby == null ? null : updatedby.trim();
    }
    
    /**
	 * 入库实名注册信息
	 * 
	 * @param asRealNameRegInfo
	 *            实名注册信息
	 */
	public void setAsRealNameRegInfoParams(AsRealNameReg regInfo) {
		this.setAuthRemark(regInfo.getComment());
		this.setBirthPlace(regInfo.getBirthPlace());
		this.setCertificateBack(regInfo.getLicenceBackPic());
		this.setCertificateFront(regInfo.getLicenceFrontPic());
		this.setCertificateHandhold(regInfo.getLicenceHandHoldPic());
		String country=regInfo.getCountryCode();
		if(StringUtils.isBlank(country)){
			country=regInfo.getCountryName();
		}
		this.setCountryName(country);
		this.setCreatedby(regInfo.getCreator());
		this.setEntBuildDate(regInfo.getEntCreateDate());
		this.setEntName(regInfo.getEntName());
		this.setEntRegAddr(regInfo.getEntRegAddr());
		this.setEntType(regInfo.getEntType());
		this.setIdIssueOrg(regInfo.getLicenceIssuingAuth());
		this.setIdNo(regInfo.getCerNo());
		this.setIdType(Integer.parseInt(regInfo.getCertype()));
		this.setIdValidDate(regInfo.getExpiredDate());
		this.setIssuePlace(regInfo.getIssuingPlace());
		this.setJob(regInfo.getJob());
		this.setPerName(regInfo.getRealName());
		this.setResidentAddr(regInfo.getResidentAddr());
		this.setSex(regInfo.getSex());
		this.setPerCustId(regInfo.getCustId());
	}
	
	/**
	 * 填充 实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息()
	 */
	public void fillAsRealNameAuthInfo(AsRealNameAuth asRealNameAuth) {
		asRealNameAuth.setBirthAddress(StringUtils
				.nullToEmpty(this.residentAddr));
		asRealNameAuth.setCerNo(StringUtils.nullToEmpty(this.idNo));
		asRealNameAuth.setCerPica(StringUtils
				.nullToEmpty(this.certificateFront));
		asRealNameAuth
				.setCerPicb(StringUtils.nullToEmpty(this.certificateBack));
		asRealNameAuth.setCerPich(StringUtils
				.nullToEmpty(this.certificateHandhold));
		if (null != this.idType) {
			asRealNameAuth.setCerType(String.valueOf(this.idType));
		}
		asRealNameAuth
				.setCountryCode(StringUtils.nullToEmpty(this.countryName));
		asRealNameAuth.setCustId(StringUtils.nullToEmpty(this.perCustId));
		asRealNameAuth.setIssuingOrg(StringUtils.nullToEmpty(this.idIssueOrg));
		if (null != this.sex) {
			asRealNameAuth.setSex(String.valueOf(this.sex));
		}
		asRealNameAuth.setUserName(StringUtils.nullToEmpty(this.perName));
		asRealNameAuth.setValidDate(StringUtils.nullToEmpty(this.idValidDate));
		
		//add by lvyan
		asRealNameAuth.setAuthRemark(authRemark);
//		asRealNameAuth.setBirthAddress(residentAddr);//
		asRealNameAuth.setBirthPlace(birthPlace);
//		asRealNameAuth.setCerNo(idNo);//
//		asRealNameAuth.setCerPica(certificateFront);//
//		asRealNameAuth.setCerPicb(certificateBack);//
//		asRealNameAuth.setCerPich(certificateHandhold);//
//		asRealNameAuth.setCerType(idType.toString());//
//		asRealNameAuth.setCountryCode(countryName);//
//		asRealNameAuth.setCustId(perCustId);//
		asRealNameAuth.setEntBuildDate(entBuildDate);
		asRealNameAuth.setEntName(entName);
		asRealNameAuth.setEntRegAddr(entRegAddr);
		asRealNameAuth.setEntType(entType);
		asRealNameAuth.setIssuePlace(issuePlace);
//		asRealNameAuth.setIssuingOrg(idIssueOrg);//
		asRealNameAuth.setJob(job);
		//asRealNameAuth.setRealNameStatus(realNameStatus);//实名验证状态在持卡人信息表
//		asRealNameAuth.setSex(String.valueOf(sex));//
//		asRealNameAuth.setUserName(perName);//
//		asRealNameAuth.setValidDate(idValidDate);
		
	}
	
	/**
	 * 填充 实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息()
	 */
	public void fillBsRealNameAuthInfo(BsRealNameAuth bsRealNameAuth) {
		bsRealNameAuth.setBirthAddress(StringUtils
				.nullToEmpty(this.residentAddr));
		bsRealNameAuth.setCerNo(StringUtils.nullToEmpty(this.idNo));
		bsRealNameAuth.setCerPica(StringUtils
				.nullToEmpty(this.certificateFront));
		bsRealNameAuth
				.setCerPicb(StringUtils.nullToEmpty(this.certificateBack));
		bsRealNameAuth.setCerPich(StringUtils
				.nullToEmpty(this.certificateHandhold));
		if (null != this.idType) {
			bsRealNameAuth.setCerType(this.idType);
		}
		bsRealNameAuth
				.setCountryCode(StringUtils.nullToEmpty(this.countryName));
		bsRealNameAuth.setCustId(StringUtils.nullToEmpty(this.perCustId));
		bsRealNameAuth.setIssuingOrg(StringUtils.nullToEmpty(this.idIssueOrg));
		if (null != this.sex) {
			bsRealNameAuth.setSex(this.sex);
		}
		bsRealNameAuth.setUserName(StringUtils.nullToEmpty(this.perName));
		bsRealNameAuth.setValidDate(StringUtils.nullToEmpty(this.idValidDate));
		
		//add by lvyan
		bsRealNameAuth.setAuthRemark(authRemark);
//				bsRealNameAuth.setBirthAddress(residentAddr);//
		bsRealNameAuth.setBirthPlace(birthPlace);
//				bsRealNameAuth.setCerNo(idNo);//
//				bsRealNameAuth.setCerPica(certificateFront);//
//				bsRealNameAuth.setCerPicb(certificateBack);//
//				bsRealNameAuth.setCerPich(certificateHandhold);//
//				bsRealNameAuth.setCerType(idType.toString());//
//				bsRealNameAuth.setCountryCode(countryName);//
//				bsRealNameAuth.setCustId(perCustId);//
		bsRealNameAuth.setEntBuildDate(entBuildDate);
		bsRealNameAuth.setEntName(entName);
		bsRealNameAuth.setEntRegAddr(entRegAddr);
		bsRealNameAuth.setEntType(entType);
		bsRealNameAuth.setIssuePlace(issuePlace);
//				bsRealNameAuth.setIssuingOrg(idIssueOrg);//
		bsRealNameAuth.setJob(job);
				//bsRealNameAuth.setRealNameStatus(realNameStatus);//实名验证状态在持卡人信息表
//				bsRealNameAuth.setSex(String.valueOf(sex));//
//				bsRealNameAuth.setUserName(perName);//
//				bsRealNameAuth.setValidDate(idValidDate);
	}

	/**
	 * 入库实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 */
	public void setBsRealNameAuthInfo(BsRealNameAuth bsRealNameAuthInfo) {
		String birthAddress = bsRealNameAuthInfo.getBirthAddress();
		if (!StringUtils.isBlank(birthAddress)) {
			this.setResidentAddr(birthAddress.trim());
		}
		String cerNo = bsRealNameAuthInfo.getCerNo();
		if (!StringUtils.isBlank(cerNo)) {
			this.setIdNo(cerNo.trim());
		}
		String cerPica = bsRealNameAuthInfo.getCerPica();
		if (!StringUtils.isBlank(cerPica)) {
			this.setCertificateFront(cerPica.trim());
		}
		String cerPicb = bsRealNameAuthInfo.getCerPicb();
		if (!StringUtils.isBlank(cerPicb)) {
			this.setCertificateBack(cerPicb.trim());
		}
		String cerPich = bsRealNameAuthInfo.getCerPich();
		if (!StringUtils.isBlank(cerPich)) {
			this.setCertificateHandhold(cerPich.trim());
		}
		Integer cerType = bsRealNameAuthInfo.getCerType();
		if (null != cerType) {
			this.setIdType(cerType);
		}
		String countryCode = bsRealNameAuthInfo.getCountryCode();
		if (!StringUtils.isBlank(countryCode)) {
			this.setCountryName(countryCode.trim());
		}
		String custId = bsRealNameAuthInfo.getCustId();
		if (!StringUtils.isBlank(custId)) {
			this.setPerCustId(custId.trim());
		}
		String issuingOrg = bsRealNameAuthInfo.getIssuingOrg();
		if (!StringUtils.isBlank(issuingOrg)) {
			this.setIdIssueOrg(issuingOrg.trim());
		}
		Integer sex = bsRealNameAuthInfo.getSex();
		if (null != sex) {
			this.setSex(sex);
		}
		String userName = bsRealNameAuthInfo.getUserName();
		if (!StringUtils.isBlank(userName)) {
			this.setPerName(userName.trim());
		}
		String validDate = bsRealNameAuthInfo.getValidDate();
		if (!StringUtils.isBlank(validDate)) {
			this.setIdValidDate(validDate.trim());
		}
		String job = bsRealNameAuthInfo.getJob();
		if (!StringUtils.isBlank(job)) {
			this.setJob(job.trim());
		}
		String birthPlace = bsRealNameAuthInfo.getBirthPlace();
		if (!StringUtils.isBlank(birthPlace)) {
			this.setBirthPlace(birthPlace.trim());
		}
		String issuePlace = bsRealNameAuthInfo.getIssuePlace();
		if (!StringUtils.isBlank(issuePlace)) {
			this.setIssuePlace(issuePlace.trim());
		}
		String entName = bsRealNameAuthInfo.getEntName();
		if (!StringUtils.isBlank(entName)) {
			this.setEntName(entName.trim());
		}
		String entRegAddr = bsRealNameAuthInfo.getEntRegAddr();
		if (!StringUtils.isBlank(entRegAddr)) {
			this.setEntRegAddr(entRegAddr.trim());
		}
		String entType = bsRealNameAuthInfo.getEntType();
		if (!StringUtils.isBlank(entType)) {
			this.setEntType(entType.trim());
		}
		String entBuildDate = bsRealNameAuthInfo.getEntBuildDate();
		if (!StringUtils.isBlank(entBuildDate)) {
			this.setEntBuildDate(entBuildDate.trim());
		}
		String authRemark = bsRealNameAuthInfo.getAuthRemark();
		if (!StringUtils.isBlank(authRemark)) {
			this.setAuthRemark(authRemark.trim());
		}
	}
}