/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.bean;

/**
 * 银行转帐查询条件包装类
 * 
 * @Package: com.gy.hsxt.ao.bean
 * @ClassName: TransOutQueryParam
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午3:17:34
 * @version V3.0.0
 */
public class TransOutQueryParam extends BaseParam {

    private static final long serialVersionUID = -5138298367503747305L;

    /** 转帐结果 **/
    private Integer transResult;

    /** 转账状态 **/
    private Integer transStatus;

    /** 转账原因 **/
    private Integer transReason;

    public Integer getTransReason() {
        return transReason;
    }

    public void setTransReason(Integer transReason) {
        this.transReason = transReason;
    }

    /**
     * @return the 转帐结果
     */
    public Integer getTransResult() {
        return transResult;
    }

    /**
     * @param 转帐结果
     *            the transResult to set
     */
    public void setTransResult(Integer transResult) {
        this.transResult = transResult;
    }

    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }
}
