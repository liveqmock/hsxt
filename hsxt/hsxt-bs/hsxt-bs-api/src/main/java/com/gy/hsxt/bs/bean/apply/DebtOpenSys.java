/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.; LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DebtOpenSys
 * @Description: 开系统欠款审核信息
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:24:03
 * @version V1.0
 */
public class DebtOpenSys extends OptInfo{

    private static final long serialVersionUID = -6093655789510409766L;

    /** 申请编号 **/
    private String applyId;

    /** 欠费、免费开系统: 1.欠费 2.免费 **/
    private Integer feeFlag;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getFeeFlag() {
        return feeFlag;
    }

    public void setFeeFlag(Integer feeFlag) {
        this.feeFlag = feeFlag;
    }

}
