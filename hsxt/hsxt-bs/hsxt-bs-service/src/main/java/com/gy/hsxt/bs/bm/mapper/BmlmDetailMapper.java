/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.mapper;

import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.BmlmQuery;
import com.gy.hsxt.bs.bm.bean.BmlmSum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Package :com.gy.hsxt.bs.bm.mapper
 * @ClassName : BmlmDetailMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/7 8:51
 * @Version V3.0.0.0
 */
@Repository
public interface BmlmDetailMapper {

    /**
     * 保存单个再增值积分实体
     *
     * @param bmlmDetail
     * @return
     */
    int insert(BmlmDetail bmlmDetail);

    /**
     * 根据业务流水号查询增值积分实体
     *
     * @param bmlmId
     * @return
     */
    BmlmDetail selectOneById(@Param("bmlmId") String bmlmId);

    /**
     * 统计查询本平台各个企业的申报数量
     *
     * @param params
     * @return
     */
    List<BmlmSum> selectBmlmSum(Map<String, Object> params);

    /**
     * 分页查询再增值积分列表
     *
     * @param bmlmQuery 查询实体
     * @return list
     */
    List<BmlmDetail> selectBmlmListPage(BmlmQuery bmlmQuery);
}
