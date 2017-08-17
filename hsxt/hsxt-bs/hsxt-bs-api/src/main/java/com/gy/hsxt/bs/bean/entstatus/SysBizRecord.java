/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: SysBizRecord
 * @Description: 系统业务记录
 * 
 * @author: xiaofl
 * @date: 2016-1-21 下午6:32:31
 * @version V1.0
 */
public class SysBizRecord implements Serializable {

    private static final long serialVersionUID = 7955189563368766562L;

    /** 业务流水号 */
    private String no;

    /** 日期 */
    private String bsDate;

    /** 受理结果 */
    private Object bsResult;

    /** 备注 */
    private String remark;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBsDate() {
        return bsDate;
    }

    public void setBsDate(String bsDate) {
        this.bsDate = bsDate;
    }

    public Object getBsResult() {
        return bsResult;
    }

    public void setBsResult(Object bsResult) {
        this.bsResult = bsResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
