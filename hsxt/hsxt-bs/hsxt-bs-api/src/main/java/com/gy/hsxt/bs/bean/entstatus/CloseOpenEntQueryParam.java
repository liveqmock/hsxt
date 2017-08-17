/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus  
 * @ClassName: CloseOpenEntParam 
 * @Description: 关闭开启系统查询参数
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:38:19 
 * @version V1.0 
 

 */ 
public class CloseOpenEntQueryParam implements Serializable {

    private static final long serialVersionUID = -7009206046485215659L;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entCustName;

    /** 联系人姓名 **/
    private String linkman;

    /** 企业类别 **/
    private Integer custType;

    /** 复核类别 **/
    private Integer applyType;

    /** 操作员客户号 **/
    private String optCustId;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
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

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
