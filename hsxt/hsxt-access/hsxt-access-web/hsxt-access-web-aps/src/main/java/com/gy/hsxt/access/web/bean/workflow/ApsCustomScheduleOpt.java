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
import com.gy.hsxt.tm.bean.CustomScheduleOpt;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.enumtype.WorkTypeStatus;

/**
 * 地区平台值班计划扩展类
 * 
 * @Package: com.gy.hsxt.access.web.bean.workflow
 * @ClassName: ApsCustomScheduleOpt
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-4 下午8:39:31
 * @version V1.0
 */
public class ApsCustomScheduleOpt extends CustomScheduleOpt implements Serializable {
    private static final long serialVersionUID = 3713772768353965300L;

    /**
     * 临时值班计划Json
     */
    private String scheduleOptsJson;

    /**
     * 值班计划
     */
    private CustomScheduleOpt cso;

    /**
     * 验证参数
     */
    public void checkData() {
        
        //执行计划对象不为空
        if (cso == null)
        {
            throw new HsException(ASRespCode.AS_PARAM_INVALID);
        }

        RequestUtil.verifyParamsIsNotEmpty(
                // 值班组编号
                new Object[] { cso.getGroupId(), ASRespCode.APS_GROUP_ID_NOT_NULL },
                // 排班年份
                new Object[] { cso.getPlanYear(), ASRespCode.APS_SELECT_YEAR },
                // 排班月份
                new Object[] { cso.getPlanMonth(), ASRespCode.APS_SELECT_MONTH }
        );
    }

    /**
     * @return the scheduleOptsJson
     */
    public String getScheduleOptsJson() {
        return scheduleOptsJson;
    }

    /**
     * @param scheduleOptsJson
     *            the scheduleOptsJson to set
     */
    public void setScheduleOptsJson(String scheduleOptsJson) {
        this.scheduleOptsJson = scheduleOptsJson;
        if (!StringUtils.isEmpty(this.scheduleOptsJson))
        {
            try
            {
                List<CustomScheduleOpt> csoList = JSON.parseArray(URLDecoder.decode(this.scheduleOptsJson, "UTF-8"),
                        CustomScheduleOpt.class);
                if (csoList != null)
                {
                    this.setCso(csoList.get(0));
                }

            }
            catch (UnsupportedEncodingException e)
            {
                SystemLog.error("workflow", "setScheduleOptsJson", "值班员计划转换异常", e);
            }
        }
    }

    /**
     * 值班类型转换
     */
    void convertType() {
        if (this.cso != null && cso.getScheduleOptList() != null)
        {
            for (ScheduleOpt so : this.cso.getScheduleOptList())
            {
                so.setWorkTypeTemp(WorkTypeStatus.getCode(so.getInputWorkName()));
                so.setWorkType(so.getWorkTypeTemp());
            }
        }
    }

    /**
     * @return the cso
     */
    public CustomScheduleOpt getCso() {
        return cso;
    }

    /**
     * @param cso
     *            the cso to set
     */
    public void setCso(CustomScheduleOpt cso) {
        this.cso = cso;
        this.convertType();
    }
}
