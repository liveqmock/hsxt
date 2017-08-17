/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean;

import java.io.Serializable;

import com.gy.hsxt.common.exception.HsException;

/***
 * 双签操作用户类
 * 
 * @Package: com.gy.hsxt.access.web.bean
 * @ClassName: ApsDoubleOperator
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-3-11 上午9:51:24
 * @version V1.0
 */
public class ApsDoubleOperator extends AbstractAPSBase implements Serializable {
    private static final long serialVersionUID = -5625607343249577625L;

    /**
     * 双签用户名
     */
    private String doubleUserName;

    /**
     * 双签登录密码
     */
    private String doublePwd;

    /**
     * 验证类型
     */
    private String checkType;

    /**
     * @return the 验证类型
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * @param 验证类型
     *            the checkType to set
     */
    public void setCheckType(String checkType) throws HsException {
        this.checkType = checkType;
    }

    /**
     * @return the 双签用户名
     */
    public String getDoubleUserName() {
        return doubleUserName;
    }

    /**
     * @param 双签用户名
     *            the doubleUserName to set
     */
    public void setDoubleUserName(String doubleUserName) {
        this.doubleUserName = doubleUserName;
    }

    /**
     * @return the 双签登录密码
     */
    public String getDoublePwd() {
        return doublePwd;
    }

    /**
     * @param 双签登录密码
     *            the doublePwd to set
     */
    public void setDoublePwd(String doublePwd) {
        this.doublePwd = doublePwd;
    }

}
