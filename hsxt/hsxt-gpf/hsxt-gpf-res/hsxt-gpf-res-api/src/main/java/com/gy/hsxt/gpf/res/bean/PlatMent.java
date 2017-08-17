/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.bean
 * @ClassName: PlatMent
 * @Description: 管理公司与平台关系
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:52:33
 * @version V1.0
 */
public class PlatMent extends BaseBean implements Serializable {

    private static final long serialVersionUID = 8308863185569590807L;

    /** 地区平台代码 **/
    private String platNo;

    /** 地区平台名称 **/
    private String platName;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 **/
    private String entCustName;

    /** 管理公司在该地区平台上的邮箱 **/
    private String email;

    /** 管理公司在该地区平台上的配额 **/
    private Integer initQuota;

    /** 管理公司在地区平台用户中心开户同步标志 **/
    private Boolean ucSync;

    /** 管理公司在地区平台业务系统同步标志 **/
    private Boolean bsSync;

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getInitQuota() {
        return initQuota;
    }

    public void setInitQuota(Integer initQuota) {
        this.initQuota = initQuota;
    }

    public Boolean getUcSync() {
        return ucSync;
    }

    public void setUcSync(Boolean ucSync) {
        this.ucSync = ucSync;
    }

    public Boolean getBsSync() {
        return bsSync;
    }

    public void setBsSync(Boolean bsSync) {
        this.bsSync = bsSync;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
