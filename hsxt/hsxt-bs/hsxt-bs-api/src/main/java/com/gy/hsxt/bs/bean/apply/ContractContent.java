/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: ContractContent
 * @Description: 合同内容
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午12:15:25
 * @version V1.0
 */
public class ContractContent implements Serializable {

    private static final long serialVersionUID = -8777432353544024394L;

    /** 客户类型 **/
    private Integer custType;

    /** 盖章状态 **/
    private Integer sealStatus;

    /** 印章url **/
    private String sealFileUrl;

    /** 替换占位符后合同的内容 **/
    private String content;

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    public String getSealFileUrl() {
        return sealFileUrl;
    }

    public void setSealFileUrl(String sealFileUrl) {
        this.sealFileUrl = sealFileUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
