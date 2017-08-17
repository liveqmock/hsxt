/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;

public class Plat implements Serializable {

    private static final long serialVersionUID = 3400427325302711773L;

    private String platNo;

    private int initQuota;

    private String email;

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public int getInitQuota() {
        return initQuota;
    }

    public void setInitQuota(int initQuota) {
        this.initQuota = initQuota;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
