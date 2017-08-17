/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.exception.HsException;

import java.io.Serializable;
import java.util.List;

/**
 * 服务层基础接口
 *
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : BaseService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 16:55
 * @Version V3.0.0.0
 */
public interface BaseService<T> {

    /**
     * 保存单个实体
     *
     * @param t
     * @return
     * @throws HsException
     */
    int save(T t) throws HsException;

    /**
     * 移除单个实体
     *
     * @param t
     * @throws HsException
     */
    void remove(T t) throws HsException;

    /**
     * 通过主键移除单个实体
     *
     * @param id
     * @throws HsException
     */
    void removeById(Serializable id) throws HsException;

    /**
     * 更新单个实体
     *
     * @param t
     * @return count
     * @throws HsException
     */
    int modify(T t) throws HsException;

    /**
     * 查询单个实体
     *
     * @param t
     * @return
     * @throws HsException
     */
    T query(T t) throws HsException;

    /**
     * 根据主键查询单个实体
     *
     * @param id
     * @return
     * @throws HsException
     */
    T queryById(Serializable id) throws HsException;

    /**
     * 根据条件查询
     *
     * @param t
     * @return
     * @throws HsException
     */
    List<T> queryByCondition(T t) throws HsException;
}
