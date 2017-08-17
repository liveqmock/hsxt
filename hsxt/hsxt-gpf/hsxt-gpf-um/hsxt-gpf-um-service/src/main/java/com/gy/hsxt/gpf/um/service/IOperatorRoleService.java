/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.OperatorRole;

/**
 * 操作员角色关系业务接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IOperatorRoleService
 * @Description : 操作员角色关系业务接口
 * @Author : chenm
 * @Date : 2016/2/16 17:40
 * @Version V3.0.0.0
 */
public interface IOperatorRoleService {


    /**
     * 处理操作员角色关系列表
     *
     * @param operatorRole 关系实体
     * @return boolean
     * @throws HsException
     */
    Boolean dealOperRoleList(OperatorRole operatorRole) throws HsException;

}
