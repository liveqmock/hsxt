/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 公共查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : Query
 * @Description : 公共查询实体
 * @Author : chenm
 * @Date : 2016/1/26 9:49
 * @Version V3.0.0.0
 */
public abstract class Query implements Serializable{

    private static final long serialVersionUID = -6327458539559499692L;
    /**
     * 起始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
