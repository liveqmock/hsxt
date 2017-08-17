/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import com.gy.hsxt.gpf.um.bean.Group;
import com.gy.hsxt.gpf.um.bean.GroupQuery;

import java.util.List;

/**
 * 用户组业务层接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IGroupService
 * @Description : 用户组业务层接口
 * @Author : chenm
 * @Date : 2016/1/27 15:54
 * @Version V3.0.0.0
 */
public interface IGroupService extends IBaseService<Group> {

    /**
     * 分页查询用户组信息列表
     *
     * @param gridPage   分页对象
     * @param groupQuery 查询对象
     * @return {@code GridData}
     */
    GridData<Group> queryBeanListForPage(GridPage gridPage, GroupQuery groupQuery) throws HsException;

}
