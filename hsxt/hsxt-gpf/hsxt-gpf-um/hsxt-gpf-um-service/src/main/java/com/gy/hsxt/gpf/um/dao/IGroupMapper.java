/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.GridPage;
import com.gy.hsxt.gpf.um.bean.Group;
import com.gy.hsxt.gpf.um.bean.GroupQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户组数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IGroupMapper
 * @Description : 用户组数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 15:48
 * @Version V3.0.0.0
 */
@Repository("groupMapper")
public interface IGroupMapper extends IBaseMapper<Group> {

    /**
     * 查询记录总数
     *
     * @param groupQuery 查询实体
     * @return int
     */
    int selectCountForPage(GroupQuery groupQuery);

    /**
     * 分页查询用户组信息列表
     *
     * @param gridPage   分页对象
     * @param groupQuery 查询实体
     * @return {@code List}
     */
    List<Group> selectBeanListForPage(@Param("gridPage") GridPage gridPage,@Param("groupQuery") GroupQuery groupQuery);

    /**
     * 查询用户组下的操作员名称
     *
     * @param groupId 用户组ID
     * @return {@code List}
     */
    List<String> selectOperatorsByGroupId(@Param("groupId") String groupId);
}
