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
 * @ClassName: MentInfo
 * @Description: 管理公司信息
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:51:57
 * @version V1.0
 */
public class MentInfo extends BaseBean implements Serializable {

    private static final long serialVersionUID = -5928764324820730081L;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 **/
    private String entCustName;

    /** 管理公司邮箱 **/
    private String email;

    public MentInfo() {
        super();
    }

    public MentInfo(String entResNo, String entCustName) {
        super();
        this.entResNo = entResNo;
        this.entCustName = entCustName;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
