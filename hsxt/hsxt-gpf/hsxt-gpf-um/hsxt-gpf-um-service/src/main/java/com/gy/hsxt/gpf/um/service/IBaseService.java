/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.Query;

import java.util.List;

/**
 * 业务层基础接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IBaseService
 * @Description : 业务层基础接口
 * @Author : chenm
 * @Date : 2016/1/26 9:25
 * @Version V3.0.0.0
 */
public interface IBaseService<T> {

    /**
     * 保存单个bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    int saveBean(T bean) throws HsException;

    /**
     * 根据主键移除bean
     *
     * @param id 主键
     * @return int 影响条数
     * @throws HsException
     */
    int removeBeanById(String id) throws HsException;

    /**
     * 修改bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    int modifyBean(T bean) throws HsException;

    /**
     * 根据主键查询bean
     *
     * @param id 主键
     * @return bean
     * @throws HsException
     */
    T queryBeanById(String id) throws HsException;

    /**
     * 根据唯一查询条件查询单个Bean
     *
     * @param query 查询实体
     * @return bean
     * @throws HsException
     */
    T queryBeanByQuery(Query query) throws HsException;

    /**
     * 根据查询实体查询bean列表
     *
     * @param query 查询实体
     * @return list<T>
     * @throws HsException
     */
    List<T> queryBeanListByQuery(Query query) throws HsException;
}
