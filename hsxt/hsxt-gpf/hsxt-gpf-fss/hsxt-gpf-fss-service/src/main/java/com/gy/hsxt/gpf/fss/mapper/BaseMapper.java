/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.mapper
 * @ClassName : BaseMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 17:40
 * @Version V3.0.0.0
 */
public interface BaseMapper<T> {

    /**
     * 保存单个实体
     *
     * @param t
     */
    int insert(T t);

    /**
     * 移除单个实体
     *
     * @param t
     */
    void delete(T t);

    /**
     * 通过主键移除单个实体
     *
     * @param id
     */
    void deleteById(Serializable id);

    /**
     * 更新单个实体
     *
     * @param t
     * @return count
     */
    int update(T t);

    /**
     * 查询单个实体
     *
     * @param t
     * @return
     */
    T selectOne(T t);

    /**
     * 根据主键查询单个实体
     *
     * @param id
     * @return
     */
    T selectOneById(Serializable id);

    /**
     * 根据条件查询
     *
     * @param t
     * @return
     */
    List<T> selectByCondition(T t);
}
