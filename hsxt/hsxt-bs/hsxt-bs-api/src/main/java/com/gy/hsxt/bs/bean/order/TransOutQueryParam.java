/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import com.gy.hsxt.bs.bean.base.BaseParam;

/**
 * 银行转帐查询条件包装类，包装所有可能用到的条件，接口各方法使用时按需传参
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: TransOutQueryParam
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-25 下午2:08:28
 * @version V1.0
 */
public class TransOutQueryParam extends BaseParam {

    private static final long serialVersionUID = -5138298367503747305L;

    /** 转帐结果 **/
    private Integer transResult;

    /** 客户名称 **/
    private String custName;

    /** 企业名称 **/
    private String entCustNmae;

    /** 客户类别 **/
    private Integer custType;

    public Integer getTransResult() {
        return transResult;
    }

    public void setTransResult(Integer transResult) {
        this.transResult = transResult;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntCustNmae() {
        return entCustNmae;
    }

    public void setEntCustNmae(String entCustNmae) {
        this.entCustNmae = entCustNmae;
    }
}
