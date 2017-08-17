/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.workflow;

import java.io.Serializable;

import com.gy.hsxt.access.web.bean.AbstractAPSBase;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/***
 * 工单状态更新实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.workflow
 * @ClassName: ApsStatusUpdateBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-21 下午3:27:12
 * @version V1.0
 */
public class ApsStatusUpdateBean extends AbstractAPSBase implements Serializable {
    private static final long serialVersionUID = -1058920722925844691L;

    /**
     * 工单编号
     */
    private String taskId;

    /**
     * 工单状态
     */
    private Integer status;

    /**
     * 工单号
     */
    // private String bizNo;

    /**
     * 原因
     */
    private String reason;

    /**
     * 验证类型 1:密码 、2:指纹 、3:刷卡
     */
    private Integer checkType;

    /**
     * 复核帐号
     */
    private String checkAccount;

    /**
     * 复核密码
     */
    private String checkPwd;

    /**
     * 验证有效数据
     */
    public void checkData() {
        checkBizNo();

        RequestUtil.verifyParamsIsNotEmpty(
        new Object[] { this.reason, ASRespCode.APS_REASON_NOT_NULL}/*,
        new Object[] { this.checkAccount, ASRespCode.APS_CHECK_ACCOUNT_NOT_NULL},
        new Object[] { this.checkPwd, ASRespCode.APS_CHECK_PWD_NOT_NULL}*/);

    }
    
    /**
     * 创建操作员登录信息
     * @return
     */
    public AsOperatorLoginInfo createOperatorLoginInfo() {
        AsOperatorLoginInfo aoli = new AsOperatorLoginInfo();
        aoli.setResNo(this.getResNo());
        aoli.setChannelType(ChannelTypeEnum.WEB);
        aoli.setRandomToken(this.getToken());
        aoli.setUserName(this.getCheckAccount());
        aoli.setLoginPwd(this.getCheckPwd());
        return aoli;
    }

    /**
     * 验证工单号
     */
    public void checkBizNo() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.taskId, ASRespCode.APS_SYSNO_NOT_NULL });
    }

    /*
     * public String getBizNo() { return bizNo; }
     * 
     * public void setBizNo(String bizNo) { this.bizNo = bizNo; }
     */

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getCheckAccount() {
        return checkAccount;
    }

    public void setCheckAccount(String checkAccount) {
        this.checkAccount = checkAccount;
    }

    public String getCheckPwd() {
        return checkPwd;
    }

    public void setCheckPwd(String checkPwd) {
        this.checkPwd = checkPwd;
    }

}
