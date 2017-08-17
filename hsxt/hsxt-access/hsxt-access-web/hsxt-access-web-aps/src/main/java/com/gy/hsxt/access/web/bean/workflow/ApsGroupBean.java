/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.workflow;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.tm.bean.Operator;

/***
 * 地区平台值班组信息实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.workflow
 * @ClassName: ApsGroupBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-23 下午5:40:56
 * @version V1.0
 */
public class ApsGroupBean extends Group implements Serializable {
    private static final long serialVersionUID = -2751746441717681674L;

    /**
     * 客户操作号
     */
    private String custId;
    
    /**
     * 员工Json拼接
     */
    private String memberJson;

    /**
     * 值班计划编号
     */
    private String scheduleId;

    /**
     * 新增数据
     */
    public void checkAddData() {
        checkBaseData();
    }

    /**
     * 修改验证
     */
    public void checkUpdateData() {
        // 值班组编号
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { super.getGroupId(), ASRespCode.APS_GROUP_ID_NOT_NULL });

        checkBaseData();
    }

    /**
     * 验证基础数据
     */
    public void checkBaseData() throws HsException {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { super.getGroupName(), ASRespCode.APS_GROUP_NAME_NOT_NULL },      // 值班组名称
                new Object[] { super.getGroupType(), ASRespCode.APS_GROUP_TYPE_NOT_NULL },      // 值班组类型
                new Object[] { this.memberJson, ASRespCode.APS_MEMBER_NOT_NULL });              // 组员信息

        // 值班组组员集合不能少于一条
        if (super.getOperators() == null || super.getOperators().size() < 1) {
            throw new HsException(ASRespCode.APS_MEMBER_NOT_NULL);
        }
    }

    /**
     * @return the 员工Json拼接
     */
    public String getMemberJson() {
        return memberJson;
    }

    /**
     * @param 员工Json拼接
     *            the memberJson to set
     */
    public void setMemberJson(String memberJson) {
        this.memberJson = memberJson;
        if (!StringUtils.isEmptyTrim(this.memberJson))
        {
            try
            {
                super.setOperators(JSON.parseArray(URLDecoder.decode(this.memberJson, "UTF-8"), Operator.class));
            }
            catch (Exception e)
            {
                SystemLog.error("workflow", "setMemberJson", "值班组组员转换异常", e);
            }
        }
    }

    /**
     * @return the 值班计划编号
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * @param 值班计划编号
     *            the scheduleId to set
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the 客户操作号
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param 客户操作号 the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
        super.setOptCustId(this.custId);
    }
    
}
