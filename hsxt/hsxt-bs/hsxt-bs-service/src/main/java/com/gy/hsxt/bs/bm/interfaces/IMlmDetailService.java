/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.interfaces;

import com.gy.hsxt.bs.bean.bm.MlmDetail;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 增值详情业务层接口
 *
 * @Package :com.gy.hsxt.bs.bm.interfaces
 * @ClassName : IMlmDetailService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 18:24
 * @Version V3.0.0.0
 */
public interface IMlmDetailService {

    /**
     * 保存单个增值详情实体
     *
     * @param mlmDetail
     * @return
     * @throws HsException
     */
    int save(MlmDetail mlmDetail) throws HsException;

    /**
     * 根据汇总ID查询增值详情列表
     *
     * @param totalId
     * @return
     * @throws HsException
     */
    List<MlmDetail> queryByTotalId(String totalId) throws HsException;
}
