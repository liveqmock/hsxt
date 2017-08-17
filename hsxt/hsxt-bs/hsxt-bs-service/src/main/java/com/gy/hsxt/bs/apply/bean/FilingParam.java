/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.apply.FilingQueryParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.bean
 * @ClassName: FilingParam
 * @Description: 报备查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-16 上午10:29:19
 * @version V1.0
 */
public class FilingParam extends FilingQueryParam {

    private static final long serialVersionUID = 2173181697566527987L;

    /** 不包含的状态，如"(1,2,3)" **/
    private String notInStatus;

    public String getNotInStatus() {
        return notInStatus;
    }

    public void setNotInStatus(String notInStatus) {
        this.notInStatus = notInStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
