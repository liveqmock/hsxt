/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.mapper;

import java.util.List;

import com.gy.hsxt.tc.bean.CheckBalance;

/**
 * 调账mapper
 * @author lixuan
 *
 */
public interface CheckBalanceMapper {

    /**
     * 添加调账申请
     * @param record
     * @return
     */
    int insertSelective(CheckBalance record);

    /**
     * 根据ID查询调账申请
     * @param id
     * @return
     */
    CheckBalance selectByPrimaryKey(String id);

    /**
     * 根据ID修改调账申请
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CheckBalance record);
    
    /**
     * 根据条件查询调账申请
     * @param balance
     * @return
     */
    List<CheckBalance> selectByCondition(CheckBalance balance);
    
    /**
     * 根据条件查询调账申请记录数
     * @param balance
     * @return
     */
    int countCheckBalances(CheckBalance balance);
}