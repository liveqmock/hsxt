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
 * @ClassName: ResNo
 * @Description: 互生号
 * 
 * @author: xiaofl
 * @date: 2016-1-5 下午7:49:54
 * @version V1.0
 */
public class ResNo implements Serializable {

    private static final long serialVersionUID = -3022468200772438920L;

    /** 互生号 **/
    private String entResNo;

    /**
     * 国家代码
     */
    private String countryNo;

    /**
     * 省份代码
     */
    private String provinceNo;

    /**
     * 城市代码
     */
    private String cityNo;

    /**
     * 互生号使用状态
     */
    private String resStatus;

    /**
     * 企业客户号
     */
    private String entCustId;

    /**
     * 企业名称
     */
    private String entCustName;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
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

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
