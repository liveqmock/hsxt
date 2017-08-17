/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.bean;

import java.io.Serializable;

/**
 * @Package :com.gy.hsxt.gpf.bm.bean
 * @ClassName : OperRecord
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/16 10:48
 * @Version V3.0.0.0
 */
public class OperRecord implements Serializable {

    private static final long serialVersionUID = 6089554747694347105L;
    /**
     * 操作类型
     */
    private String type;

    /**
     * 是否成功 0-失败 1-成功
     */
    private Integer success = 0;

    /**
     * 失败次数
     */
    private Integer recount = 0;

    /**
     * 最后一次失败原因
     */
    private String reason;

    public OperRecord() {
    }

    public OperRecord(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getRecount() {
        return recount;
    }

    public void setRecount(Integer recount) {
        this.recount = recount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
