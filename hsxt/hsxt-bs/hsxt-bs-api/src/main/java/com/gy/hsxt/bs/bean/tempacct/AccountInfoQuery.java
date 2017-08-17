/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tempacct;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 账户信息查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.tempacct
 * @ClassName : AccountingQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/23 18:19
 * @Version V3.0.0.0
 */
public class AccountInfoQuery extends Query {

    private static final long serialVersionUID = -7128282148185486490L;

    /**
     * 账户户名编号
     **/
    private String receiveAccountId;
    /**
     * 收款账户名称
     */
    private String receiveAccountName;

    /**
     * 银行代码
     */
    private String bankId;

    /**
     * 收款账号
     */
    private String receiveAccountNo;

    /**
     * 是否有效 Y-有效 N-无效
     */
    private String isActive;

    public String getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(String receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }

    public String getReceiveAccountName() {
        return receiveAccountName;
    }

    public void setReceiveAccountName(String receiveAccountName) {
        this.receiveAccountName = receiveAccountName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getReceiveAccountNo() {
        return receiveAccountNo;
    }

    public void setReceiveAccountNo(String receiveAccountNo) {
        this.receiveAccountNo = receiveAccountNo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * 构建账户信息查询实体
     *
     * @param accountInfo 账户信息
     * @return 查询实体
     */
    public static AccountInfoQuery bulid(AccountInfo accountInfo) {
        AccountInfoQuery query = new AccountInfoQuery();
        query.setReceiveAccountName(accountInfo.getReceiveAccountName());
        query.setBankId(accountInfo.getBankId());
        query.setReceiveAccountId(accountInfo.getReceiveAccountId());
        query.setReceiveAccountNo(accountInfo.getReceiveAccountNo());
        return query;
    }

}
