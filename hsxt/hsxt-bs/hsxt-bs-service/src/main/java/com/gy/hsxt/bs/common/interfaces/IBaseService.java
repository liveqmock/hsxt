/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.interfaces;

import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.common.interfaces
 * @ClassName : IBaseService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/10 11:46
 * @Version V3.0.0.0
 */
public interface IBaseService<T> {

    /**
     * 保存实体
     *
     * @param t 实体
     * @return string
     * @throws HsException
     */
    String saveBean(T t) throws HsException;

    /**
     * 更新实体
     *
     * @param t 实体
     * @return int
     * @throws HsException
     */
    int modifyBean(T t) throws HsException;

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    T queryBeanById(String id) throws HsException;

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    List<T> queryListByQuery(Query query) throws HsException;

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    List<T> queryListForPage(Page page, Query query) throws HsException;
}
