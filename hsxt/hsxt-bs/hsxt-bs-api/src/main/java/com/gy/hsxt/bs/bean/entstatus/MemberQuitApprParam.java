/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.entstatus;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: MemberQuitApprParam
 * @Description: 成员企业注销审批参数
 * 
 * @author: xiaofl
 * @date: 2016-1-25 下午6:18:31
 * @version V1.0
 */
public class MemberQuitApprParam extends ApprParam {

    private static final long serialVersionUID = 5074953666362545955L;

    /** 企业实地考察报告 */
    private String reportFile;

    /** 企业双方声明文件 */
    private String statementFile;

    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    public String getStatementFile() {
        return statementFile;
    }

    public void setStatementFile(String statementFile) {
        this.statementFile = statementFile;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
