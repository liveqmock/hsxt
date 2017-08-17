/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.mapper;

import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.mapper
 * @ClassName : LocalNotifyMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 19:45
 * @Version V3.0.0.0
 */
@Repository("localNotifyMapper")
public interface LocalNotifyMapper extends BaseMapper<LocalNotify> {

    /**
     * 根据查询实体进行列表查询
     *
     * @param queryNotify 查询实体
     * @return 列表
     */
    List<LocalNotify> selectByOther(QueryNotify queryNotify);

    /**
     * 更新上个月所有再增值通知
     *
     * @param queryNotify 查询实体
     * @return 记录影响数
     */
    int updateForBmlm(QueryNotify queryNotify);

    /**
     * 查询记录总数
     * @param queryNotify 查询实体
     * @return 总数
     */
    int selectCountByOther(QueryNotify queryNotify);
}
