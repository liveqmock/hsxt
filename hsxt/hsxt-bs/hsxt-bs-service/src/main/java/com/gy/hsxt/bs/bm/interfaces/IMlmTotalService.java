/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.interfaces;

import com.gy.hsxt.bs.bean.bm.MlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 增值汇总业务接口
 *
 * @Package :com.gy.hsxt.bs.bm.interfaces
 * @ClassName : IMlmTotalService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 18:25
 * @Version V3.0.0.0
 */
public interface IMlmTotalService {

    /**
     * 保存单个增值汇总实例
     *
     * @param mlmTotal
     * @return
     * @throws HsException
     */
    int save(MlmTotal mlmTotal) throws HsException;

    /**
     * 根据业务流水号查询实体
     *
     * @param totalId
     * @return
     * @throws HsException
     */
    MlmTotal queryById(String totalId) throws HsException;

    /**
     * 分页查询互生积分分配
     *
     * @param page     分页对象
     * @param mlmQuery 查询实体
     * @return list
     * @throws HsException
     */
    List<MlmTotal> queryMlmListPage(Page page, MlmQuery mlmQuery) throws HsException;

}
