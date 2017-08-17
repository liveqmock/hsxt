/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptHisInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.bean
 * @ClassName: OptHis
 * @Description: 操作历史
 * 
 * @author: xiaofl
 * @date: 2015-12-28 下午5:01:43
 * @version V1.0
 */
public class OptHis extends OptHisInfo {

    private static final long serialVersionUID = 115675736915714966L;

    /** 申请编号 **/
    private String applyId;

    /** 双签操作员 **/
    private String dblOptId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getDblOptId() {
        return dblOptId;
    }

    public void setDblOptId(String dblOptId) {
        this.dblOptId = dblOptId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
