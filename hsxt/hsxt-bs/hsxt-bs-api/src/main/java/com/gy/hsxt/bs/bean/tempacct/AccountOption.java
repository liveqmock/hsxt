/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tempacct;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 收款账户信息下拉项实体
 * 
 * @Package :com.gy.hsxt.bs.bean.tempacct
 * @ClassName : AccountOption
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/25 10:01
 * @Version V3.0.0.0
 */
public final class AccountOption implements Serializable {

    private static final long serialVersionUID = -926768437720564373L;

    /**
     * 选项值(收款账户信息ID)
     */
    private String value;

    /**
     * 选项名称
     */
    private String name;

    /**
     * 收款账户信息
     */
    private AccountInfo accountInfo;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    /**
     * 装配账户选项
     * 
     * @param accountInfo
     *            收款账户信息
     * @return 账户选项
     */
    public static AccountOption valueOf(AccountInfo accountInfo) {
        HsAssert.notNull(accountInfo, RespCode.PARAM_ERROR, "收款账户信息[accounting]为空");
        AccountOption option = new AccountOption();
        option.setAccountInfo(accountInfo);
        option.setValue(accountInfo.getReceiveAccountInfoId());
        option.setName(generateName(accountInfo));
        return option;
    }

    /**
     * 选项名称形成规则 [ + 收款账户名称 + ] + 银行名称 + 收款账号
     * 
     * @param accountInfo
     *            收款账户信息
     * @return the name of option
     */
    private static String generateName(AccountInfo accountInfo) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(accountInfo.getReceiveAccountName());
        builder.append("] ");
        builder.append(accountInfo.getBankName()).append(" ");
        builder.append(StringUtils.left(accountInfo.getReceiveAccountNo(), 4));
        builder.append("****");
        builder.append(StringUtils.right(accountInfo.getReceiveAccountNo(), 4));
        return builder.toString();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
