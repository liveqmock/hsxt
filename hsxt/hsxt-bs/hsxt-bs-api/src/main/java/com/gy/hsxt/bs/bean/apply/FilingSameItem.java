/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: FilingSameItem
 * @Description: 报备企业相同项信息
 * 
 * @author: xiaofl
 * @date: 2015-8-31 下午4:56:17
 * @version V1.0
 */
public class FilingSameItem implements Serializable {

    private static final long serialVersionUID = 3337587566527929489L;

    /** 被报备企业名称 */
    private String entName;

    /** 国家代码 */
    private String countryNo;

    /** 省代码 */
    private String provinceNo;

    /** 城市代码 */
    private String cityNo;

    /** 企业地址 */
    private String address;

    /** 企业名称 String[0]为企业名称，String[1]为提交报备企业管理号 */
    private List<String[]> sameEntNames;

    /** 营业执照号 String[0]为企业名称，String[1]为提交报备企业管理号,String[2]为营业执照号 */
    private List<String[]> sameLicenses;

    /**
     * 法人代表信息 String[0]为企业名称，String[1]为提交报备企业管理号,
     * String[2]为法人代表名,String[3]为法人证件号,String[4]为证件类型
     */
    private List<String[]> sameLegalReps;

    /**
     * 联系人信息 String[0]为企业名称，String[1]为提交报备企业管理号, String[2]为联系人姓名,String[3]为联系人电话
     */
    private List<String[]> sameLinkmans;

    /**
     * 股东信息 String[0]为企业名称，String[1]为提交报备企业管理号
     * String[2]为股东姓名,String[3]为股东证件号,String[4]为证件类型
     */
    private List<String[]> sameShareHolders;

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String[]> getSameEntNames() {
        return sameEntNames;
    }

    public void setSameEntNames(List<String[]> sameEntNames) {
        this.sameEntNames = sameEntNames;
    }

    public List<String[]> getSameLicenses() {
        return sameLicenses;
    }

    public void setSameLicenses(List<String[]> sameLicenses) {
        this.sameLicenses = sameLicenses;
    }

    public List<String[]> getSameLegalReps() {
        return sameLegalReps;
    }

    public void setSameLegalReps(List<String[]> sameLegalReps) {
        this.sameLegalReps = sameLegalReps;
    }

    public List<String[]> getSameLinkmans() {
        return sameLinkmans;
    }

    public void setSameLinkmans(List<String[]> sameLinkmans) {
        this.sameLinkmans = sameLinkmans;
    }

    public List<String[]> getSameShareHolders() {
        return sameShareHolders;
    }

    public void setSameShareHolders(List<String[]> sameShareHolders) {
        this.sameShareHolders = sameShareHolders;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
