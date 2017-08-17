/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.mapper;

import com.gy.hsxt.bs.bean.bm.MlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.bm.mapper
 * @ClassName : MlmTotalMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/7 8:51
 * @Version V3.0.0.0
 */
@Repository
public interface MlmTotalMapper {

    /**
     * 保存增值积分汇总记录
     *
     * @param mlmTotal 互生积分分配实体
     * @return int
     */
    int insert(MlmTotal mlmTotal);

    /**
     * 根据业务流水查询增值积分汇总记录
     *
     * @param totalId 业务编号
     * @return bean
     */
    MlmTotal selectOneById(@Param("totalId") String totalId);

    /**
     * 分页查询互生积分分配
     *
     * @param mlmQuery 查询实体
     * @return list
     */
    List<MlmTotal> selectMlmListPage(MlmQuery mlmQuery);
}
