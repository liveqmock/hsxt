/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.mapper;

import com.gy.hsxt.bs.bean.bm.MlmDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.bm.mapper
 * @ClassName : MlmDetailMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/7 8:51
 * @Version V3.0.0.0
 */
@Repository
public interface MlmDetailMapper {

    /**
     * 保存单个增值详情
     * @param mlmDetail
     * @return
     */
    int insert(MlmDetail mlmDetail);

    /**
     * 根据汇总ID查询增值详情列表
     *
     * @param totalId
     * @return
     */
    List<MlmDetail> selectListByTotalId(String totalId);
}
