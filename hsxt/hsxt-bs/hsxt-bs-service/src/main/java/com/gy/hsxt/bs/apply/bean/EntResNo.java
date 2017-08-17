/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.bean  
 * @ClassName: EntResNo 
 * @Description: 企业互生号
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:47:25 
 * @version V1.0 
 

 */ 
public class EntResNo implements Serializable {

    private static final long serialVersionUID = -6317566032321739534L;

    /** 企业互生号 **/
    private String entResNo;

    /** 资源类型 **/
    private Integer custType;

    /** 资源状态 **/
    private Integer resStatus;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getResStatus() {
        return resStatus;
    }

    public void setResStatus(Integer resStatus) {
        this.resStatus = resStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
