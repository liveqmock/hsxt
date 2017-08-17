/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.mapper;

import java.util.List;

import com.gy.hsxt.tc.bean.CheckBalanceResult;
import com.gy.hsxt.tc.bean.TcCheckBalanceResult;

/**
 * 调账审批结果mapper
 * @author lixuan
 *
 */
public interface CheckBalanceResultMapper {

    int deleteByPrimaryKey(String id);

    /**
     * 添加调账审批结果
     * @param record
     * @return
     */
    int insertSelective(TcCheckBalanceResult record);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    CheckBalanceResult selectByPrimaryKey(String id);

    /**
     * 更新审批
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CheckBalanceResult record);

    /**
     * 根据调账ID查询审批结果
     * @param balanceId 调账ID
     * @return
     */
    List<CheckBalanceResult> selectByBalanceId(String balanceId);
}