/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.apply.mapper;

import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模版审核映射
 *
 * @Package : com.gy.hsxt.bs.apply.mapper
 * @ClassName : TempletApprMapper
 * @Description : 模版审核映射
 * @Author : chenm
 * @Date : 2016/3/14 12:36
 * @Version V3.0.0.0
 */
@Repository
public interface TemplateApprMapper {

    /**
     * 插入模版审核
     *
     * @param templateAppr 实体
     * @return {@code int}
     */
    int insertBean(TemplateAppr templateAppr);

    /**
     * 修改模版审核
     *
     * @param templateAppr 实体
     * @return {@code int}
     */
    int updateBean(TemplateAppr templateAppr);

    /**
     * 查询审核记录列表
     * @param templetId 模版ID
     * @return {@code List}
     */
    List<TemplateAppr> selectBeanList(@Param("templetId") String templetId);

    /**
     * 查询最新审核记录
     * @param templetId 模版ID
     * @return {@code List}
     */
    TemplateAppr selectLastBeanByTemplateId(@Param("templetId") String templetId);
}
