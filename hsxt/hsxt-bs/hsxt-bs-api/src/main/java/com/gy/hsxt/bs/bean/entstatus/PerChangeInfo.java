/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: PerChangeInfo
 * @Description: 消费者重要信息变更信息
 * 
 * @author: xiaofl
 * @date: 2015-9-6 下午12:15:09
 * @version V1.0
 */
public class PerChangeInfo extends OptInfo implements Serializable {

    private static final long serialVersionUID = 6659444376810321486L;

    /** 申请编号 **/
    private String applyId;

    /** 消费者客户号 **/
    private String perCustId;

    /** 消费者互生号 **/
    private String perResNo;

    /** 消费者名称 **/
    private String perCustName;

    /** 消费者手机 **/
    private String mobile;

    /** 变更信息 **/
    private String changeItem;

    /** 户籍变更证明 **/
    private String residenceAddrPic;

    // private List<ChangeItem> changeItems;

    /** 变更前的姓名 **/
    private String nameOld;

    /** 变更前的性别 **/
    private Integer sexOld;

    /** 变更前的国籍 **/
    private String nationalityOld;

    /** 变更前的证件类型 **/
    private Integer creTypeOld;

    /** 变更前的证件号码 **/
    private String creNoOld;

    /** 变更前的证件有效期 **/
    private String creExpireDateOld;

    /** 变更前的发证机关 **/
    private String creIssueOrgOld;

    /** 变更前的户籍地址 **/
    private String registorAddressOld;

    /** 变更前的职业 **/
    private String professionOld;

    /** 变更前的证件正面照 **/
    private String creFacePicOld;

    /** 变更前的证件反面照 **/
    private String creBackPicOld;

    /** 变更前的手持证件半身照 **/
    private String creHoldPicOld;

    /** 变更前的签发地点 **/
    private String issuePlaceOld;

    /** 变更前的企业名称 **/
    private String entNameOld;

    /** 变更前的企业注册地址 **/
    private String entRegAddrOld;

    /** 变更前的企业类型 **/
    private String entTypeOld;

    /** 变更前的企业成立日期 **/
    private String entBuildDateOld;

    /** 变更后的姓名 **/
    private String nameNew;

    /** 变更后的性别 **/
    private Integer sexNew;

    /** 变更后的国籍 **/
    private String nationalityNew;

    /** 变更后的证件类型 **/
    private Integer creTypeNew;

    /** 变更后的证件号码 **/
    private String creNoNew;

    /** 变更后的证件有效期 **/
    private String creExpireDateNew;

    /** 变更后的发证机关 **/
    private String creIssueOrgNew;

    /** 变更后的户籍地址 **/
    private String registorAddressNew;

    /** 变更后的职业 **/
    private String professionNew;

    /** 变更后的证件正面照 **/
    private String creFacePicNew;

    /** 变更后的证件反面照 **/
    private String creBackPicNew;

    /** 变更后的手持证件半身照 **/
    private String creHoldPicNew;

    /** 变更后的签发地点 **/
    private String issuePlaceNew;

    /** 变更后的企业名称 **/
    private String entNameNew;

    /** 变更后的企业注册地址 **/
    private String entRegAddrNew;

    /** 变更后的企业类型 **/
    private String entTypeNew;

    /** 变更后的企业成立日期 **/
    private String entBuildDateNew;

    /** 申请变更原因 **/
    private String applyReason;

    /** 申请状态 **/
    private Integer status;

    /** 审批意见 **/
    private String apprRemark;

    /** 申请变更时间 **/
    private String applyDate;

    /** 创建日期 */
    private String createdDate;

    /** 修改日期 */
    private String updateDate;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getPerCustId() {
        return perCustId;
    }

    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }

    public String getPerResNo() {
        return perResNo;
    }

    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    public String getPerCustName() {
        return perCustName;
    }

    public void setPerCustName(String perCustName) {
        this.perCustName = perCustName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChangeItem() {
        return changeItem;
    }

    public void setChangeItem(String changeItem) {
        this.changeItem = changeItem;
    }

    public String getResidenceAddrPic() {
        return residenceAddrPic;
    }

    public void setResidenceAddrPic(String residenceAddrPic) {
        this.residenceAddrPic = residenceAddrPic;
    }

    public String getNameOld() {
        return nameOld;
    }

    public void setNameOld(String nameOld) {
        this.nameOld = nameOld;
    }

    public Integer getSexOld() {
        return sexOld;
    }

    public void setSexOld(Integer sexOld) {
        this.sexOld = sexOld;
    }

    public String getNationalityOld() {
        return nationalityOld;
    }

    public void setNationalityOld(String nationalityOld) {
        this.nationalityOld = nationalityOld;
    }

    public Integer getCreTypeOld() {
        return creTypeOld;
    }

    public void setCreTypeOld(Integer creTypeOld) {
        this.creTypeOld = creTypeOld;
    }

    public String getCreNoOld() {
        return creNoOld;
    }

    public void setCreNoOld(String creNoOld) {
        this.creNoOld = creNoOld;
    }

    public String getCreExpireDateOld() {
        return creExpireDateOld;
    }

    public void setCreExpireDateOld(String creExpireDateOld) {
        this.creExpireDateOld = creExpireDateOld;
    }

    public String getCreIssueOrgOld() {
        return creIssueOrgOld;
    }

    public void setCreIssueOrgOld(String creIssueOrgOld) {
        this.creIssueOrgOld = creIssueOrgOld;
    }

    public String getRegistorAddressOld() {
        return registorAddressOld;
    }

    public void setRegistorAddressOld(String registorAddressOld) {
        this.registorAddressOld = registorAddressOld;
    }

    public String getProfessionOld() {
        return professionOld;
    }

    public void setProfessionOld(String professionOld) {
        this.professionOld = professionOld;
    }

    public String getCreFacePicOld() {
        return creFacePicOld;
    }

    public void setCreFacePicOld(String creFacePicOld) {
        this.creFacePicOld = creFacePicOld;
    }

    public String getCreBackPicOld() {
        return creBackPicOld;
    }

    public void setCreBackPicOld(String creBackPicOld) {
        this.creBackPicOld = creBackPicOld;
    }

    public String getCreHoldPicOld() {
        return creHoldPicOld;
    }

    public void setCreHoldPicOld(String creHoldPicOld) {
        this.creHoldPicOld = creHoldPicOld;
    }

    public String getNameNew() {
        return nameNew;
    }

    public void setNameNew(String nameNew) {
        this.nameNew = nameNew;
    }

    public Integer getSexNew() {
        return sexNew;
    }

    public void setSexNew(Integer sexNew) {
        this.sexNew = sexNew;
    }

    public String getNationalityNew() {
        return nationalityNew;
    }

    public void setNationalityNew(String nationalityNew) {
        this.nationalityNew = nationalityNew;
    }

    public Integer getCreTypeNew() {
        return creTypeNew;
    }

    public void setCreTypeNew(Integer creTypeNew) {
        this.creTypeNew = creTypeNew;
    }

    public String getCreNoNew() {
        return creNoNew;
    }

    public void setCreNoNew(String creNoNew) {
        this.creNoNew = creNoNew;
    }

    public String getCreExpireDateNew() {
        return creExpireDateNew;
    }

    public void setCreExpireDateNew(String creExpireDateNew) {
        this.creExpireDateNew = creExpireDateNew;
    }

    public String getCreIssueOrgNew() {
        return creIssueOrgNew;
    }

    public void setCreIssueOrgNew(String creIssueOrgNew) {
        this.creIssueOrgNew = creIssueOrgNew;
    }

    public String getRegistorAddressNew() {
        return registorAddressNew;
    }

    public void setRegistorAddressNew(String registorAddressNew) {
        this.registorAddressNew = registorAddressNew;
    }

    public String getProfessionNew() {
        return professionNew;
    }

    public void setProfessionNew(String professionNew) {
        this.professionNew = professionNew;
    }

    public String getCreFacePicNew() {
        return creFacePicNew;
    }

    public void setCreFacePicNew(String creFacePicNew) {
        this.creFacePicNew = creFacePicNew;
    }

    public String getCreBackPicNew() {
        return creBackPicNew;
    }

    public void setCreBackPicNew(String creBackPicNew) {
        this.creBackPicNew = creBackPicNew;
    }

    public String getCreHoldPicNew() {
        return creHoldPicNew;
    }

    public void setCreHoldPicNew(String creHoldPicNew) {
        this.creHoldPicNew = creHoldPicNew;
    }

    // public List<ChangeItem> getChangeItems() {
    // return changeItems;
    // }
    //
    // public void setChangeItems(List<ChangeItem> changeItems) {
    // this.changeItems = changeItems;
    // }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getIssuePlaceOld() {
        return issuePlaceOld;
    }

    public void setIssuePlaceOld(String issuePlaceOld) {
        this.issuePlaceOld = issuePlaceOld;
    }

    public String getEntNameOld() {
        return entNameOld;
    }

    public void setEntNameOld(String entNameOld) {
        this.entNameOld = entNameOld;
    }

    public String getEntRegAddrOld() {
        return entRegAddrOld;
    }

    public void setEntRegAddrOld(String entRegAddrOld) {
        this.entRegAddrOld = entRegAddrOld;
    }

    public String getEntTypeOld() {
        return entTypeOld;
    }

    public void setEntTypeOld(String entTypeOld) {
        this.entTypeOld = entTypeOld;
    }

    public String getEntBuildDateOld() {
        return entBuildDateOld;
    }

    public void setEntBuildDateOld(String entBuildDateOld) {
        this.entBuildDateOld = entBuildDateOld;
    }

    public String getIssuePlaceNew() {
        return issuePlaceNew;
    }

    public void setIssuePlaceNew(String issuePlaceNew) {
        this.issuePlaceNew = issuePlaceNew;
    }

    public String getEntNameNew() {
        return entNameNew;
    }

    public void setEntNameNew(String entNameNew) {
        this.entNameNew = entNameNew;
    }

    public String getEntRegAddrNew() {
        return entRegAddrNew;
    }

    public void setEntRegAddrNew(String entRegAddrNew) {
        this.entRegAddrNew = entRegAddrNew;
    }

    public String getEntTypeNew() {
        return entTypeNew;
    }

    public void setEntTypeNew(String entTypeNew) {
        this.entTypeNew = entTypeNew;
    }

    public String getEntBuildDateNew() {
        return entBuildDateNew;
    }

    public void setEntBuildDateNew(String entBuildDateNew) {
        this.entBuildDateNew = entBuildDateNew;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
