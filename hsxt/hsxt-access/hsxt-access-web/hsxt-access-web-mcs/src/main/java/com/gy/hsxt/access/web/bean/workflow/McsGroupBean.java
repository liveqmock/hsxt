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
 * 值班组信息实体类
 * @Package: com.gy.hsxt.access.web.bean.workflow  
 * @ClassName: McsGroupBean 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-29 下午6:43:17 
 * @version V1.0
 */
public class McsGroupBean extends Group implements Serializable {
    private static final long serialVersionUID = -360973914800548365L;

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
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { super.getGroupId(), ASRespCode.MW_GROUP_ID_NOT_NULL });
        checkBaseData();
    }

    /**
     * 验证基础数据
     */
    public void checkBaseData() throws HsException {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { super.getGroupName(), ASRespCode.MW_GROUP_NAME_NOT_NULL }, // 值班组名称
                new Object[] { super.getGroupType(), ASRespCode.MW_GROUP_TYPE_NOT_NULL }, // 值班组类型
                new Object[] { this.memberJson, ASRespCode.MW_MEMBER_NOT_NULL }           // 组员信息
                );

        // 值班组组员集合不能少于一条
        if (super.getOperators() == null || super.getOperators().size() < 1)
        {
            throw new HsException(ASRespCode.MW_MEMBER_NOT_NULL);
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

    public static void main(String[] args) {
        String abcString = "%5B%7B%22optCustId%22%3A%2200000000156000220160105%22%2C%22operatorName%22%3A%22%E6%9D%8E%E6%98%8E%22%2C%22chief%22%3Afalse%7D%2C%7B%22optCustId%22%3A%2200000000156163438271051776%22%2C%22operatorName%22%3A%22%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98%22%2C%22chief%22%3Atrue%7D%5D";
        try
        {
            List<Operator> olist = JSON.parseArray(URLDecoder.decode(abcString, "UTF-8"), Operator.class);
            System.out.println("=================" + JSON.toJSONString(olist));
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
