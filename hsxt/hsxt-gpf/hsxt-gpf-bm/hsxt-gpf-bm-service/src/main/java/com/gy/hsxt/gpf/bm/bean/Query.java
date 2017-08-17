/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 增值系统查询实体
 *
 * @Package : com.gy.hsxt.gpf.bm.bean
 * @ClassName : Query
 * @Description : 增值系统查询实体
 * @Author : chenm
 * @Date : 2016/3/4 11:40
 * @Version V3.0.0.0
 */
public class Query {

    /**
     * 起始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 企业资源号
     */
    private String resNo;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = StringUtils.replace(startDate,"-","");
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = StringUtils.replace(endDate,"-","");
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    /**
     * 构建参数map
     *
     * @return {@code Map<String, String>}
     */
    public Map<String, String> bulidMap() {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(this.resNo)) {
            map.put("resNo", this.resNo);
        }
        return map;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
