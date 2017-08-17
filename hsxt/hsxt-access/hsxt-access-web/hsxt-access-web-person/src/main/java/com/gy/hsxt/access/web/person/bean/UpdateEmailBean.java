/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.bean;

import java.io.Serializable;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***
 * 修改邮箱绑定bean
 * 
 * @Package: com.gy.hsxt.access.web.person.bean
 * @ClassName: UpdateEmailBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-2-4 下午5:39:41
 * @version V1.0
 */
public class UpdateEmailBean extends AbstractPersonBase implements Serializable {
    private String random;

    private String email;

    private String userType;

    /**
     * @return the random
     */
    public String getRandom() {
        return random;
    }

    /**
     * @param random
     *            the random to set
     */
    public void setRandom(String random) {
        this.random = random;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType
     *            the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

}
