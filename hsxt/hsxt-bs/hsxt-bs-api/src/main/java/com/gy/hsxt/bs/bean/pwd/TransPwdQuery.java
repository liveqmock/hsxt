/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.pwd;

import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 交易密码查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.pwd
 * @ClassName : TransPwdQuery
 * @Description : 交易密码查询实体
 * @Author : chenm
 * @Date : 2015/12/2 15:13
 * @Version V3.0.0.0
 */
public class TransPwdQuery extends Query {

    private static final long serialVersionUID = 517867833313370841L;
    /**
     * 企业管理号
     **/
    private String entResNo;
    /**
     * 客户号
     **/
    private String entCustId;
    /**
     * 客户类型·
     * <p/>
     * 2：成员3：托管4：服务
     **/
    private int custType;
    /**
     * 企业名称
     **/
    private String entCustName;
    /**
     * 企业联系人
     **/
    private String linkman;
    /**
     * 联系人手机
     **/
    private String mobile;
    /**
     * 状态
     * <p/>
     * 0：待审批W 1：通过Y 2：驳回N
     **/
    private Integer status;
    /**
     * 授权码
     **/
    private String validateCode;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    /**
     * 校验审核状态类型
     *
     * @return this
     * @throws HsException
     */
    public TransPwdQuery checkStatus() throws HsException {
        if (null != this.getStatus()) {
            //后校验是否符合审核状态类型
            HsAssert.isTrue(ApprStatus.checkStatus(this.getStatus()), RespCode.PARAM_ERROR, "审核状态[status]类型错误");
        }
        return this;
    }
}
