/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.workflow;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.AbstractMCSBase;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.enumtype.SendOrderPattern;

/***
 * 工单转入待办实体类
 * @Package: com.gy.hsxt.access.web.bean.workflow  
 * @ClassName: McsToDoBean 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-29 下午6:39:20 
 * @version V1.0
 */
public class McsToDoBean extends AbstractMCSBase implements Serializable {

    private static final long serialVersionUID = 1473855133748071939L;

    /**
     * 派单模式
     */
    private Integer assignedMode;

    /**
     * 工单编号
     */
    private List<String> taskIds;

    /**
     * 值班员编号列表
     */
    private List<String> optCustIds;

    /**
     * 工单编号字符串
     */
    private String taskIdStr;

    /**
     * 值班员编号列表字符串
     */
    private String optCustIdStr;

    /**
     * 有效数据验证
     */
    public void checkData() {
        // 验证字符串空
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.getAssignedMode(),ASRespCode.MW_ASSIGNEDMODE_NOT_NULL},
                new Object[] { this.taskIdStr, ASRespCode.MW_BIZNO_NOT_NULL}
                );

        // 验证工单list空、长度
        if (null == this.taskIds || this.taskIds.size() == 0)
        {
            throw new HsException(ASRespCode.MW_BIZNO_NOT_NULL);
        }

        // 手动指派时，验证值班人员数据
        if (SendOrderPattern.MANUAL_SEND.getCode().intValue() == this.getAssignedMode())
        {
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.optCustIdStr,ASRespCode.MW_OPTCUST_NOT_NULL});
            // 验证值班员list空、长度
            if (null == this.optCustIds || this.optCustIds.size() == 0)
            {
                throw new HsException(ASRespCode.MW_OPTCUST_NOT_NULL);
            }
        }

    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public List<String> getOptCustIds() {
        return optCustIds;
    }

    public void setOptCustIds(List<String> optCustIds) {
        this.optCustIds = optCustIds;
    }

    public String getTaskIdStr() {
        return taskIdStr;
    }

    public void setTaskIdStr(String taskIdStr) {
        this.taskIdStr = taskIdStr;
        if (!StringUtils.isEmpty(taskIdStr))
        {
            try
            {
                this.taskIds = (List<String>) JSON.parse(URLDecoder.decode(this.taskIdStr, "UTF-8"));
            }
            catch (Exception e)
            {
                SystemLog.error("workflow", "setTaskIdStr", "工单系统编号转换异常", e);
            }
        }
    }

    public String getOptCustIdStr() {
        return optCustIdStr;
    }

    public void setOptCustIdStr(String optCustIdStr) {
        this.optCustIdStr = optCustIdStr;
        if (!StringUtils.isEmpty(optCustIdStr))
        {
            try
            {
                this.optCustIds = (List<String>) JSON.parse(URLDecoder.decode(this.optCustIdStr, "UTF-8"));
            }
            catch (Exception e)
            {
                SystemLog.error("workflow", "setTaskIdStr", "值班员列表转换异常", e);
            }
        }
    }

    /*
     * public static void main(String[] args) { SystemLog.error("workflow",
     * "setTaskIdStr", "工单系统编号转换异常", new Exception()); }
     */

    public Integer getAssignedMode() {
        return assignedMode;
    }

    public void setAssignedMode(Integer assignedMode) {
        this.assignedMode = assignedMode;
    }

}
