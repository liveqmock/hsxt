/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;

import com.gy.hsxt.tm.bean.WorkType;

/**
 * 值班状态mapper dao映射
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: WorkTypeMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午9:17:17
 * @version V3.0.0
 */
public interface WorkTypeMapper {

    /**
     * @param workType
     * @return
     */
    public int inserWorkType(WorkType workType);

    /**
     * 获取值班状态列表
     * 
     * @return 值班状态列表
     */
    public List<WorkType> findWorkTypeList();

}
