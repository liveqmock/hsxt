/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.CustomOperator;
import com.gy.hsxt.tm.bean.Operator;

/**
 * 值班员mapper dao映射类
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: OperatorMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-11 下午8:06:54
 * @version V3.0.0
 */
public interface OperatorMapper {

    /**
     * 新增值班员
     * 
     * @param operator
     *            值班员信息
     * @return 成功记录数
     */
    public int insertOperator(Operator operator);

    /**
     * 修改值班员
     * 
     * @param operator
     *            值班员信息
     * @return 成功记录数
     */
    public int updateOperator(Operator operator);

    /**
     * 更新值班员的值班组
     * 
     * @param optCustId
     *            值班员编号
     * @param groupId
     *            值班组编号
     * @param isChief
     *            是否值班主任
     * @return 成功记录数
     */
    public int updateOperatorGroup(@Param("optCustId") String optCustId, @Param("groupId") String groupId,
            @Param("isChief") boolean isChief);

    /**
     * 更新操作员名称
     * 
     * @param optCustId
     *            操作员编号
     * @param optName
     *            操作员名称
     * @return 成功记录数
     */
    public int updateOperatorName(@Param("optCustId") String optCustId, @Param("optName") String optName);

    /**
     * 重置值班员的组为空
     * 
     * @param groupId
     *            值班组编号
     * @return 成功记录数
     */
    public int resetOperatorGroup(@Param("groupId") String groupId);

    /**
     * 修改值班主任
     * 
     * @param isChief
     *            是否主任
     * @param optCustId
     *            值班员编号
     * @return 成功记录数
     */
    public int updateChief(@Param("chief") boolean isChief, @Param("optCustId") String optCustId);

    /**
     * 查询值班员列表
     * 
     * @param groupId
     *            值班组编号
     * @return 值班员列表
     * @throws HsException
     */
    public List<Operator> findOperatorList(@Param("groupId") String groupId);

    /**
     * 查询值班员列表
     * 
     * @param entCustId
     *            企业客户号
     * @param bizTypeList
     *            业务类型列表
     * @param bizTypeCount
     *            业务类型个数
     * @return 值班员列表
     * @throws HsException
     */
    public List<Operator> findOperatorListByEntCustId(@Param("entCustId") String entCustId,
            @Param("bizTypeList") List<String> bizTypeList, @Param("bizTypeCount") int bizTypeCount);

    /**
     * 查询值班员信息
     * 
     * @param optCustId
     *            值班员编号
     * @return 值班员信息
     * @throws HsException
     */
    public Operator findOperatorInfo(@Param("optCustId") String optCustId);

    /**
     * 查询值班员信息
     * 
     * @param optCustId
     *            值班员编号
     * @return 记录数
     */
    public int findOperatorById(@Param("optCustId") String optCustId);

    /**
     * 查询值班员信息
     * 
     * @param optCustId
     *            值班员编号
     * @return 值班员信息
     * @throws HsException
     */
    public Operator findGroupOperatorInfo(@Param("groupId") String groupId, @Param("optCustId") String optCustId);

    /**
     * 获取当前值班员列表
     * 
     * @param entCustId
     *            企业客户号
     * @param workType
     *            值班状态
     * @return 当前值班员列表
     */
    public List<Operator> findOnLineOperator(@Param("entCustId") String entCustId, @Param("workType") Integer workType);

    /**
     * 获取当前值班员列表
     * 
     * @param entCustId
     *            企业客户号
     * @param workType
     *            值班状态
     * @return 当前值班员列表
     */
    public List<String> findWorkOnOperatorByEntCustId(@Param("entCustId") String entCustId);

    /**
     * 获取当前授权值班员列表
     * 
     * @param bizType
     *            业务类型
     * @param optCustId
     *            值班员编号
     * @return 当前授权值班员列表
     */
    public Operator findAuthOperator(@Param("bizType") String bizType, @Param("optCustId") String optCustId);

    /**
     * 查询值班员详细信息
     * 
     * @param optCustId
     *            值班员编号
     * @return 值班员信息
     */
    public List<CustomOperator> findOperatorInfoResMap(@Param("optCustId") String optCustId);

    /**
     * 查询值班员详细信息
     * 
     * @param optCustId
     *            值班员编号
     * @param groupId
     *            值班组编号
     * @return 值班员信息
     */
    public List<CustomOperator> findOperator(@Param("optCustId") String optCustId, @Param("groupId") String groupId);

    /**
     * 查询值班员关联值班计划
     * 
     * @param groupId
     *            值班组编号
     * @param planYear
     *            计划年份
     * @param planMonth
     *            计划月份
     * @return 值班员信息
     */
    public List<Operator> findOperatorsAndScheduleOptsResMap(@Param("groupId") String groupId,
            @Param("planYear") String planYear, @Param("planMonth") String planMonth);

    /**
     * 查询值班员关联值班计划
     * 
     * @param groupId
     *            值班组编号
     * @return 值班员信息
     */
    public List<Operator> findOperatorsNoScheduleOpts(@Param("groupId") String groupId);

    /**
     * 查询操作员是否是值班主任
     * 
     * @param optCustId
     *            操作员编号
     * @return >=1:是 0:否
     */
    public int findOperatorIsChief(@Param("optCustId") String optCustId);

    /**
     * 查询值班员在值班组中是否值班主任
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @return >=1:是 0:否
     */
    public int findOperatorInGroupIsChief(@Param("groupId") String groupId, @Param("optCustId") String optCustId);

    /**
     * 移除值班员
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @return 成功记录数
     * @throws HsException
     */
    public int deleteOperator(@Param("groupId") String groupId, @Param("optCustId") String optCustId);

    /**
     * 移除值班员
     * 
     * @param optCustId
     *            值班员编号
     * @return 成功记录数
     * @throws HsException
     */
    public int deleteOperatorById(@Param("optCustId") String optCustId);

    /**
     * @param groupId
     * @param optCustIds
     * @return
     */
    public int deleteOperatorByOptCustId(@Param("groupId") String groupId, @Param("optCustIds") Set<String> optCustIds);

}
