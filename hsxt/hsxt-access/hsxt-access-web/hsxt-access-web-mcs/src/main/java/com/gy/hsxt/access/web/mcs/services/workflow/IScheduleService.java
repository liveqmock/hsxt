/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.workflow;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.workflow.McsBizTypeAuthBean;
import com.gy.hsxt.access.web.bean.workflow.McsCustomScheduleOpt;
import com.gy.hsxt.access.web.bean.workflow.McsGroupBean;
import com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.McsMembersScheduleBean;
import com.gy.hsxt.access.web.bean.workflow.McsScheduleBean;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.Group;

/***
 * 日程安排接口类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.workflow
 * @ClassName: IScheduleService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-18 下午4:12:11
 * @version V1.0
 */
public interface IScheduleService extends IBaseService {

    /**
     * 获取值班组信息
     * 
     * @param apsBase
     * @return
     */
    public List<Map<String, Object>> getGroupList(MCSBase mcsBase) throws HsException;

    /***
     * 获取工作组下组员日程安排
     * 
     * @param agub
     * @return
     */
    public Map<String, Object> getMembersSchedule(McsMembersScheduleBean amsb) throws HsException;;

    /**
     * 查询值班组信息
     * 
     * @param groupId
     * @return
     */
    public List<Group> getGroupInfo(McsMembersScheduleBean amsb) throws HsException;

    /**
     * 值班组新增
     * 
     * @param apsGroup
     */
    public void groupAdd(McsGroupBean ag, MCSBase mcsBase) throws HsException;

    /**
     * 值班组更新
     * 
     * @param ag
     * @throws HsException
     */
    public void groupUpdate(McsGroupBean ag, MCSBase mcsBase) throws HsException;

    /**
     * 新增值班员业务节点
     * 
     * @param apsBizTypeAuthBean
     * @throws HsException
     */
    public void addBizType(McsBizTypeAuthBean abtab, MCSBase mcsBase) throws HsException;

    /**
     * 删除值班员业务节点
     * 
     * @param apsBizTypeAuthBean
     * @throws HsException
     */
    public void deleteBizType(McsBizTypeAuthBean abtab, MCSBase mcsBase) throws HsException;

    /**
     * 移除值班组组员
     * 
     * @param groupId
     * @param optCustId
     * @throws HsException
     */
    public void removeOperator(String groupId, String optCustId, MCSBase mcsBase) throws HsException;

    /**
     * 保存值班计划
     * 
     * @param asb
     * @return
     * @throws HsException
     */
    public Map<String, String> saveSchedule(McsScheduleBean asb, MCSBase mcsBase) throws HsException;

    /**
     * 执行值班计划
     * 
     * @param acso
     * @return
     * @throws HsException
     */
    public Map<String, String> executeSchedule(McsCustomScheduleOpt acso, MCSBase mcsBase) throws HsException;

    /**
     * 暂停值班组计划
     * 
     * @param agub
     * @throws HsException
     */
    public void pauseSchedule(McsGroupUpdateBean agub, MCSBase mcsBase) throws HsException;

    /**
     * 值班组开启或关闭
     * 
     * @param groupId
     * @param opened
     * @throws HsException
     */
    public void udpateGroupOpenedStatus(McsGroupUpdateBean agub, MCSBase mcsBase) throws HsException;

    /**
     * 获取值班员明细
     * 
     * @param optCustId
     * @param groupId
     * @param scheduleId
     * @return
     * @throws HsException
     */
    public Map<String, Object> getAttendantInfo(String optCustId, String groupId, String scheduleId) throws HsException;

    /**
     * 获取企业下操作员
     * 
     * @param apsBase
     * @return
     */
    public List<Map<String, String>> getListOperByEntCustId(MCSBase mcsBase) throws HsException;

    /**
     * 导出
     * @param amsb
     * @return
     */
    public McsScheduleExprotDataInit export(McsMembersScheduleBean amsb) throws HsException;
    
    
    /**
     * 获取操作员对应的业务类型
     * @param optCustId
     * @return
     */
    public List<BizType> getBizTypeList(String optCustId)  throws HsException;
    
    /**
     * 判断操作员是否为值班主任。
     * 值班员(操纵员)为主任时，才能操作转入待办功能。
     * @param mcsBase
     * @return
     * @throws HsException
     */
    public boolean isChief(MCSBase mcsBase) throws HsException;
}
