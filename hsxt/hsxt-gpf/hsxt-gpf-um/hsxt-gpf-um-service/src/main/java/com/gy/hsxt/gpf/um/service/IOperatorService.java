/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.*;

/**
 * 操作员业务接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IOperatorService
 * @Description : 操作员业务接口
 * @Author : chenm
 * @Date : 2016/1/27 9:58
 * @Version V3.0.0.0
 */
public interface IOperatorService extends IBaseService<Operator> {

    /**
     * 分页查询操作员
     *
     * @param gridPage      分页对象
     * @param operatorQuery 查询对象
     * @return {@link GridData}
     * @throws HsException
     */
    GridData<Operator> queryBeanListForPage(GridPage gridPage, OperatorQuery operatorQuery) throws HsException;


    /**
     * 修改操作员
     *
     * @param operator      操作员
     * @param operatorGroup 关系实体
     * @return boolean
     * @throws HsException
     */
    boolean editOperator(Operator operator, OperatorGroup operatorGroup) throws HsException;

    /**
     * 修改登录密码
     *
     * @param operator 操作者
     * @param oldPassword 旧密码
     * @return boolean
     * @throws HsException
     */
    boolean editLoginPassword(Operator operator,String oldPassword) throws HsException;
}
