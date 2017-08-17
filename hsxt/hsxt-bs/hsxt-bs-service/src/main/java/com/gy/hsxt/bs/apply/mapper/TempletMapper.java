/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模板Mapper
 *
 * @Package : com.gy.hsxt.bs.apply.mapper
 * @ClassName : TempletMapper
 * @Description : 模板Mapper
 * @Author : xiaofl
 * @Date : 2015-12-15 下午4:33:46
 * @Version V1.0
 */
@Repository
public interface TempletMapper {

    /**
     * 创建模板
     *
     * @param templet 模板内容
     */
    void createTemplet(Templet templet);

    /**
     * 更新模板
     *
     * @param templet 模板内容
     */
    void updateTemplet(Templet templet);

    /**
     * 通过ID查询模板
     *
     * @param templetId 模板ID
     * @return 模板内容
     */
    Templet queryTempletById(String templetId);

    /**
     * 分页查询模板
     *
     * @param templetQuery 查询实体
     * @return 模板列表
     */
    List<Templet> queryTempletListPage(TempletQuery templetQuery);

    /**
     * 分页查询待复核模版
     *
     * @param templetQuery 查询实体
     * @return 模板列表
     */
    List<Templet> queryTempletTaskListPage(TempletQuery templetQuery);

    /**
     * 更新模板状态
     *
     * @param templet 模版实体
     */
    void updateTplStatus(Templet templet);

    /**
     * 查询当前模板
     *
     * @param templetQuery 模板查询实体
     * @return 当前模板
     */
    Templet queryCurrentTplByQuery(TempletQuery templetQuery);
}
