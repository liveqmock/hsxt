/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BizTypeAuth;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.tm.bean.Operator;

/**
 * 值班管理接口
 * 
 * @Package: com.gy.hsxt.tm.api
 * @ClassName: ITMOnDutyService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-11 下午8:44:56
 * @version V3.0.0
 */
public interface ITMOnDutyService {
    /**
     * 保存值班组
     * 
     * @param group
     *            值班组信息
     * @param operators
     *            值班员列表
     * @throws HsException
     */
    public void saveGroup(Group group, List<Operator> operators) throws HsException;

    /**
     * 修改值班组
     * 
     * @param group
     *            值班组信息
     * @param operators
     *            值班员列表
     * @param scheduleId
     *            值班计划编号
     * @throws HsException
     */
    public void modifyGroup(Group group, List<Operator> operators, String scheduleId) throws HsException;

    /**
     * 获取值班组信息，关联查询出值班员
     * 
     * @param groupId
     *            值班组编号
     * @return 值班组信息
     * @throws HsException
     */
    public List<Group> getGroupInfo(String groupId) throws HsException;

    /**
     * 获取企业值班组信息
     * 
     * @param entCustId
     *            企业客户号
     * @param isAll
     *            是否查询所有
     * @param isOpen
     *            是否启用
     * @return 值班组信息
     * @throws HsException
     */
    public List<Group> getTaskGroupInfo(String entCustId, boolean isAll, boolean isOpen) throws HsException;

    /**
     * 获取全部开启的值班组列表
     * 
     * @param entCustId
     *            企业客户号
     * @return 值班组列表
     * @throws HsException
     */
    public List<Group> getOpenedGroupList(String entCustId) throws HsException;

    /**
     * 获取值班组列表
     * 
     * @param entCustId
     *            企业客户号
     * @return 值班组列表
     * @throws HsException
     */
    public List<Group> getGroupList(String entCustId) throws HsException;

    /**
     * 修改值班主任
     * 
     * @param isChief
     *            是否主任
     * @param optCustId
     *            值班员编号
     * @throws HsException
     */
    public void updateChief(boolean isChief, String optCustId) throws HsException;

    /**
     * 同步操作员名称
     * 
     * @param optCustId
     *            操作员编号
     * @param optName
     *            操作员名称
     * @throws HsException
     */
    public void synOperatorName(String optCustId, String optName) throws HsException;

    /**
     * 获取值班员列表
     * 
     * @param groupId
     *            值班组编号
     * @return 值班员列表
     * @throws HsException
     */
    public List<Operator> getOperatorList(String groupId) throws HsException;

    /**
     * 获取值班员列表
     * 
     * @param entCustId
     *            企业客户号
     * @param bizTypeList
     *            业务类型列表
     * @return 值班员列表
     * @throws HsException
     */
    public List<Operator> getOperatorListByCustId(String entCustId, List<String> bizTypeList) throws HsException;

    /**
     * 获取值班员信息
     * 
     * @param optCustId
     *            值班员编号
     * @param groupId
     *            值班组编号
     * @param scheduleId
     *            值班计划编号
     * @return 值班员信息
     * @throws HsException
     */
    public Map<String, Object> getOperatorInfo(String optCustId, String groupId, String scheduleId) throws HsException;

    /**
     * 获取值班计划
     * 
     * @param groupId
     *            值班组编号
     * @param planYear
     *            计划年份
     * @param planMonth
     *            计划月份
     * @return 值班员关联计划
     * @throws HsException
     */
    public Map<String, Object> getOperatorSchedule(String groupId, String planYear, String planMonth)
            throws HsException;

    /**
     * 移除值班员
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @throws HsException
     */
    public void removeOperator(String groupId, String optCustId) throws HsException;

    /**
     * 删除值班员
     * 
     * @param optCustId
     *            值班员编号
     * @throws HsException
     */
    public void removeOperator(String optCustId) throws HsException;

    /**
     * 添加值班员授权
     * 
     * @param operatorAuths
     *            授权列表
     * @throws HsException
     */
    public void addOperatorBizType(List<BizTypeAuth> operatorAuths) throws HsException;

    /**
     * 删除值班员授权
     * 
     * @param bizTypeCode
     *            业务类型代码集合
     * @param optCustId
     *            值班员编号
     */
    public void deleteOptCustAuth(Set<String> bizTypeCode, String optCustId) throws HsException;

    /**
     * 更新值班组开关状态
     * 
     * @param groupId
     *            值班组编号
     * @param opened
     *            开关状态
     * @throws HsException
     */
    public void udpateGroupOpenedStatus(String groupId, Integer opened) throws HsException;

    /**
     * 查询是否值班主任
     * 
     * @param optCustId
     *            操作员编号
     * @return true:是 false:否
     * @throws HsException
     */
    public boolean isChief(String optCustId) throws HsException;

    /**
     * 查询值班员是否在值班级中为值班主任
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @return true:是 false:否
     * @throws HsException
     */
    public boolean isChief(String groupId, String optCustId) throws HsException;
}
