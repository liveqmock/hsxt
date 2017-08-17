/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tempacct;

import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 临账查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.tempacct
 * @ClassName : DebitQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/23 18:48
 * @Version V3.0.0.0
 */
public class DebitQuery extends Query {

    private static final long serialVersionUID = -7370441216266962163L;

    /**
     * 付款企业名称
     */
    private String payEntCustName;

    /**
     * 拟用企业名称
     */
    private String useEntCustName;

    /**
     * 临账状态： 0：待审核 1：待关联 2：关联待审核 3：已关联 4：审核驳回 5：待退款 6：已退款 7：不动款
     *
     * @see com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus
     */
    private Integer debitStatus;

    /**
     * 收款账户信息ID
     */
    private String accountInfoId;

    /**
     * 收款账户名称
     */
    private String accountName;

    public String getPayEntCustName() {
        return payEntCustName;
    }

    public void setPayEntCustName(String payEntCustName) {
        this.payEntCustName = payEntCustName;
    }

    public String getUseEntCustName() {
        return useEntCustName;
    }

    public void setUseEntCustName(String useEntCustName) {
        this.useEntCustName = useEntCustName;
    }

    public Integer getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(Integer debitStatus) {
        this.debitStatus = debitStatus;
    }

    public String getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(String accountInfoId) {
        this.accountInfoId = accountInfoId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 校验临账状态类型
     *
     * @return this
     * @throws HsException
     */
    public DebitQuery checkDebitStatus() throws HsException {
        //不为空的情况下，进行校验
        if (this.getDebitStatus() != null) {
            //校验临账状态类型
            HsAssert.isTrue(DebitStatus.checkStatus(this.debitStatus), RespCode.PARAM_ERROR, "临账状态[debitStatus]类型错误");
        }
        return this;
    }

}
