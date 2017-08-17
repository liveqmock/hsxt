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
 * @ClassName: ManageEnt 
 * @Description: 管理公司信息
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:23:45 
 * @version V1.0 
 

 */ 
public class ManageEnt implements Serializable {

    private static final long serialVersionUID = 223851078100436439L;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 **/
    private String entName;

    public ManageEnt(String entResNo, String entName) {
        super();
        this.entResNo = entResNo;
        this.entName = entName;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
