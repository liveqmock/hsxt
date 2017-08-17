/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.GridPage;
import com.gy.hsxt.gpf.um.bean.Operator;
import com.gy.hsxt.gpf.um.bean.OperatorQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作员数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IOperatorMapper
 * @Description : 操作员数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 10:04
 * @Version V3.0.0.0
 */
@Repository("operatorMapper")
public interface IOperatorMapper extends IBaseMapper<Operator> {

    /**
     * 查询符合条件的总记录数
     *
     * @param operatorQuery 查询对象
     * @return int
     */
    int selectCountForPage(OperatorQuery operatorQuery);

    /**
     * 分页查询符合条件的记录
     *
     * @param gridPage      分页对象
     * @param operatorQuery 查询对象
     * @return list
     */
    List<Operator> selectBeanListForPage(@Param("gridPage") GridPage gridPage, @Param("operatorQuery") OperatorQuery operatorQuery);

    /**
     * 修改登录密码
     *
     * @param operator 操作者
     * @return int
     */
    int updateLoginPassword(Operator operator);
}
