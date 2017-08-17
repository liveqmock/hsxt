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

/*** 
 * 工作组，组员查询实体类
 * @Package: com.gy.hsxt.access.web.bean.workflow  
 * @ClassName: McsMembersScheduleBean 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-29 下午6:43:36 
 * @version V1.0
 */
public class McsMembersScheduleBean extends AbstractMCSBase implements Serializable {
    private static final long serialVersionUID = -3511583520739527490L;

    /**
     * 值班组名称
     */
    private String groupName;

    /**
     * 值班组编号
     */
    private String groupId;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 验证有效数据
     */
    public void checkData() {
        RequestUtil.verifyParamsIsNotEmpty(
        new Object[] { this.groupId, ASRespCode.MW_GROUP_ID_NOT_NULL },
        new Object[] { this.year, ASRespCode.MW_SELECT_YEAR },
        new Object[] { this.month, ASRespCode.MW_SELECT_MONTH }
        );
    }

    /**
     * 导出参数验证
     */
    public void checkExportData() {
        checkData();
        // 组名验证
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.groupName, ASRespCode.MW_GROUP_ID_NOT_NULL });
    }

    /**
     * @return the 值班组编号
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param 值班组编号
     *            the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the 年
     */
    public String getYear() {
        return year;
    }

    /**
     * @param 年
     *            the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the 月
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param 月
     *            the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
