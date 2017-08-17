/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IRoleMapper
 * @Description : 角色数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 15:49
 * @Version V3.0.0.0
 */
@Repository("roleMapper")
public interface IRoleMapper extends IBaseMapper<Role> {

    /**
     * 查询符合条件的总记录数
     *
     * @param roleQuery 查询对象
     * @return int
     */
    int selectCountForPage(RoleQuery roleQuery);

    /**
     * 分页查询符合条件的记录
     *
     * @param gridPage      分页对象
     * @param roleQuery 查询对象
     * @return list
     */
    List<Role> selectBeanListForPage(@Param("gridPage") GridPage gridPage, @Param("roleQuery") RoleQuery roleQuery);
}
