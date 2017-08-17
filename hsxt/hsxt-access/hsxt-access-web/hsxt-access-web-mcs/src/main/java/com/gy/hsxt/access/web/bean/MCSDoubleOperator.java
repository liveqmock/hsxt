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
 * @ClassName: MCSDoubleOperator
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-3-10 下午8:22:02
 * @version V1.0
 */
public class MCSDoubleOperator extends AbstractMCSBase implements Serializable {
    private static final long serialVersionUID = 9059631621453564244L;

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

        /*
         * try { // 获取对应的枚举类型
         * super.setChannelType(ChannelTypeEnum.get(Integer.parseInt
         * (this.checkType))); } catch (NumberFormatException e) { throw new
         * HsException(RespCode.AS_VERIFY_CHECKTYPE_INVALID); }
         */
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
