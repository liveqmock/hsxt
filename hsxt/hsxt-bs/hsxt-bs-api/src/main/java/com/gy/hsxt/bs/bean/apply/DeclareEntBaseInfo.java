/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareEntBaseInfo
 * @Description: 申报企业基本信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 下午2:22:13
 * @version V1.0
 */
public class DeclareEntBaseInfo implements Serializable {

    private static final long serialVersionUID = -9059770047735106449L;

    /** 申请编号 */
    private String applyId;

    /** 企业互生号 */
    private String entResNo;

    /** 客户类型 **/
    private Integer custType;

    /** 企业名称 */
    private String entName;

    /** 企业地址 */
    private String entAddress;

    /** 联系人 */
    private String linkman;

    /** 联系人手机 */
    private String linkmanMobile;

    /** 申报日期 */
    private String declareDate;

    /** 审批日期 */
    private String apprDate;

    /** 状态 */
    private Integer status;

    /** 国家代码 */
    private String countryNo;

    /** 省代码 */
    private String provinceNo;

    /** 城市代码 */
    private String cityNo;

    /**
     * 重新申报标识 true：已重新申报 false：未重新申报
     */
    private boolean redoFlag;

    /**
     * UC是否开启
     */
    private boolean openUC;

    /**
     * BM是否开启
     */
    private boolean openBM;

    /**
     * 是否参与增值
     */
    private boolean joinBM;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(String declareDate) {
        this.declareDate = declareDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public boolean isRedoFlag() {
        return redoFlag;
    }

    public void setRedoFlag(boolean redoFlag) {
        this.redoFlag = redoFlag;
    }

    public boolean isOpenUC() {
        return openUC;
    }

    public void setOpenUC(boolean openUC) {
        this.openUC = openUC;
    }

    public boolean isOpenBM() {
        return openBM;
    }

    public void setOpenBM(boolean openBM) {
        this.openBM = openBM;
    }

    public boolean isJoinBM() {
        return joinBM;
    }

    public void setJoinBM(boolean joinBM) {
        this.joinBM = joinBM;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
