/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.bean.base;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 操作员对象，用于传递当前操作员信息
 * 
 * @Package: com.gy.hsxt.bs.bean.base
 * @ClassName: Operator
 * @author: yangjianguo
 * @date: 2016-2-27 下午5:50:17
 * @version V1.0
 */
public class Operator implements Serializable {
    private static final long serialVersionUID = -914577723937449837L;

    /**
     * 操作员编号
     */
    @NotEmpty(message = "操作员编号不能为空")
    private String optId;

    /**
     * 操作员名称
     */
    @NotEmpty(message = "操作员名称不能为空")
    private String optName;

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
