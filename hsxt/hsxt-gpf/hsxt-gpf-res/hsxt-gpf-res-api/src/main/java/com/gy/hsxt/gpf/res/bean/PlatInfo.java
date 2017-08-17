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
 * @ClassName: PlatInfo
 * @Description: 平台信息
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:52:13
 * @version V1.0
 */
public class PlatInfo extends BaseBean implements Serializable {

    private static final long serialVersionUID = -5498109800598465043L;

    /** 地区平台代码 **/
    private String platNo;

    /** 平台企业互生号 **/
    private String entResNo;

    /** 平台企业名称 **/
    private String entCustName;

    /** 管理员A邮箱 **/
    private String emailA;

    /** 管理员B邮箱 **/
    private String emailB;

    /** 同步标志 **/
    private Boolean syncFlag;

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
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

    public String getEmailA() {
        return emailA;
    }

    public void setEmailA(String emailA) {
        this.emailA = emailA;
    }

    public String getEmailB() {
        return emailB;
    }

    public void setEmailB(String emailB) {
        this.emailB = emailB;
    }

    public Boolean getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Boolean syncFlag) {
        this.syncFlag = syncFlag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
