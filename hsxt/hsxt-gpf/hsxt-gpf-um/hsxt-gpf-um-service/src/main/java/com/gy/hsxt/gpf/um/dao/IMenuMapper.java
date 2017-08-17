/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 菜单数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IMenuMapper
 * @Description : 菜单数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 15:46
 * @Version V3.0.0.0
 */
@Repository("menuMapper")
public interface IMenuMapper extends IBaseMapper<Menu> {

    /**
     * 查询相邻菜单编号
     *
     * @param parentNo 父节点编号
     * @return {@code String}
     */
    String selectAdjacentNo(@Param("parentNo") String parentNo);

}
