/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.entstatus.PointActivityQueryParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.bean
 * @ClassName: PointActivityParam
 * @Description: 积分活动参数
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:22:19
 * @version V1.0
 */
public class PointActivityParam extends PointActivityQueryParam {

    private static final long serialVersionUID = 5851514192573739803L;

    /**
     * 包含的状态，如"(1,2,3)"
     */
    private String inStatus;

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
