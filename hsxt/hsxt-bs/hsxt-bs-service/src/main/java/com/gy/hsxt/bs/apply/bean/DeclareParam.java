/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.bean
 * @ClassName: DeclareParam
 * @Description: 申报查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午7:54:41
 * @version V1.0
 */
public class DeclareParam extends DeclareQueryParam {

    private static final long serialVersionUID = -2891311528856661850L;

    /** 不包含的状态，如"(1,2,3)" **/
    private String notInStatus;

    /** 是否是平台申报的 **/
    private Boolean isPlatQuery;

    public String getNotInStatus() {
        return notInStatus;
    }

    public void setNotInStatus(String notInStatus) {
        this.notInStatus = notInStatus;
    }

    public Boolean getIsPlatQuery() {
        return isPlatQuery;
    }

    public void setIsPlatQuery(Boolean isPlatQuery) {
        this.isPlatQuery = isPlatQuery;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
