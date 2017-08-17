/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tempacct;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 账户名称查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.tempacct
 * @ClassName : AccountNameQuery
 * @Description : 账户名称查询实体
 * @Author : chenm
 * @Date : 2015/12/19 12:20
 * @Version V3.0.0.0
 */
public class AccountNameQuery extends Query {

    private static final long serialVersionUID = -1178973882293040240L;
    /**
     * 收款账户类型
     * <p/>
     * 1：收款账户 2：节余不动款账户
     **/
    private Integer receiveAccountType;
    /**
     * 收款账户名称
     **/
    private String receiveAccountName;
    /**
     * 简称
     **/
    private String abbreviation;

    public Integer getReceiveAccountType() {
        return receiveAccountType;
    }

    public void setReceiveAccountType(Integer receiveAccountType) {
        this.receiveAccountType = receiveAccountType;
    }

    public String getReceiveAccountName() {
        return receiveAccountName;
    }

    public void setReceiveAccountName(String receiveAccountName) {
        this.receiveAccountName = receiveAccountName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * 构建收款账户查询实体
     *
     * @param accountName 收款账户
     * @return 查询实体
     */
    public static AccountNameQuery bulid(AccountName accountName) {
        AccountNameQuery query = new AccountNameQuery();
        query.setReceiveAccountName(accountName.getReceiveAccountName());
        query.setReceiveAccountType(accountName.getReceiveAccountType());
        query.setAbbreviation(accountName.getAbbreviation());
        return query;
    }
}
