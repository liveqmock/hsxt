/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;


import com.gy.hsxt.gpf.um.bean.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库层基础接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IBaseMapper
 * @Description : 数据库层基础接口
 * @Author : chenm
 * @Date : 2016/1/26 10:15
 * @Version V3.0.0.0
 */
public interface IBaseMapper<T> {

    /**
     * 保存单个bean
     *
     * @param bean 实体
     * @return int 影响条数
     */
    int insertBean(T bean);

    /**
     * 根据主键移除bean
     *
     * @param id 主键
     * @return int 影响条数
     */
    int deleteBeanById(@Param("id") String id);

    /**
     * 修改bean
     *
     * @param bean 实体
     * @return int 影响条数
     */
    int updateBean(T bean);

    /**
     * 根据主键查询bean
     *
     * @param id 主键
     * @return bean
     */
    T selectBeanById(@Param("id") String id);

    /**
     * 根据唯一查询条件查询单个Bean
     *
     * @param query 查询实体
     * @return bean
     */
    T selectBeanByQuery(Query query);

    /**
     * 根据查询实体查询bean列表
     *
     * @param query 查询实体
     * @return list<T>
     */
    List<T> selectBeanListByQuery(Query query);
}
