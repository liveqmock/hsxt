/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.workflow;

import java.io.Serializable;

import com.gy.hsxt.access.web.bean.AbstractMCSBase;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/***
 * 工单状态更新实体类
 * @Package: com.gy.hsxt.access.web.bean.workflow  
 * @ClassName: McsStatusUpdateBean 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-29 下午6:39:40 
 * @version V1.0
 */
public class McsStatusUpdateBean extends AbstractMCSBase implements Serializable {
    private static final long serialVersionUID = -668984115915868893L;

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
    //private Integer checkType;

    /**
     * 复核帐号
     */
    //private String checkAccount;

    /**
     * 复核密码
     */
   // private String checkPwd;

    /**
     * 验证有效数据
     */
    public void checkData() {
        checkBizNo();

        RequestUtil.verifyParamsIsNotEmpty(
        new Object[] { this.reason, ASRespCode.MW_REASON_NOT_NULL } /*,
       new Object[] { this.checkAccount, ASRespCode.MW_CHECK_ACCOUNT_NOT_NULL},
        new Object[] { this.checkPwd, ASRespCode.MW_CHECK_PWD_NOT_NULL }*/);

    }
    
    /**
     * 创建操作员登录信息
     * @return
     */
  /*  public AsOperatorLoginInfo createOperatorLoginInfo() {
        AsOperatorLoginInfo aoli = new AsOperatorLoginInfo();
        aoli.setResNo(this.getResNo());
        aoli.setChannelType(ChannelTypeEnum.WEB);
        aoli.setRandomToken(this.getToken());
        aoli.setUserName(this.getCheckAccount());
        aoli.setLoginPwd(this.getCheckPwd());
        return aoli;
    }*/

    /**
     * 验证工单号
     */
    public void checkBizNo() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.taskId, ASRespCode.MW_SYSNO_NOT_NULL });
    }

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

}
