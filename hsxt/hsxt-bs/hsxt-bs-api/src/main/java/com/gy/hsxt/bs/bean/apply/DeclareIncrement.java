/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareIncrement
 * @Description: 申报增值点信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 上午9:31:28
 * @version V1.0
 */
public class DeclareIncrement extends OptInfo implements Serializable {

    private static final long serialVersionUID = 3059240477835906491L;

    /** 申请编号 */
    private String applyId;

    /** 被申报增值节点父节点客户号 */
    private String toPnodeCustId;

    /** 被申报增值节点父节点资源号 */
    private String toPnodeResNo;

    /** 被申报选择增值节点 */
    private String toInodeResNo;

    /** 被申报选择增值节点对应区域 */
    private Integer toInodeLorR;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getToPnodeCustId() {
        return toPnodeCustId;
    }

    public void setToPnodeCustId(String toPnodeCustId) {
        this.toPnodeCustId = toPnodeCustId;
    }

    public String getToPnodeResNo() {
        return toPnodeResNo;
    }

    public void setToPnodeResNo(String toPnodeResNo) {
        this.toPnodeResNo = toPnodeResNo;
    }

    public String getToInodeResNo() {
        return toInodeResNo;
    }

    public void setToInodeResNo(String toInodeResNo) {
        this.toInodeResNo = toInodeResNo;
    }

    public Integer getToInodeLorR() {
        return toInodeLorR;
    }

    public void setToInodeLorR(Integer toInodeLorR) {
        this.toInodeLorR = toInodeLorR;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
