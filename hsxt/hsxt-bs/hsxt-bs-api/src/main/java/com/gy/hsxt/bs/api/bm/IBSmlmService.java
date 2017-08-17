/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.api.bm;

import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.BmlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.api.bm
 * @ClassName : IBSmlmService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 18:14
 * @Version V3.0.0.0
 */
public interface IBSmlmService {

    /**
     * 再增值积分详情查询
     *
     * @param bizNo 业务编号
     * @return bean
     * @throws HsException
     */
    BmlmDetail queryBmlmByBizNo(String bizNo) throws HsException;

    /**
     * 分页查询再增值积分列表
     *
     * @param page      分页对象
     * @param bmlmQuery 查询实体
     * @return list
     * @throws HsException
     */
    PageData<BmlmDetail> queryBmlmListPage(Page page, BmlmQuery bmlmQuery) throws HsException;

    /**
     * 增值积分详情查询
     *
     * @param bizNo 业务编号
     * @return bean
     * @throws HsException
     */
    MlmTotal queryMlmByBizNo(String bizNo) throws HsException;

    /**
     * 分页查询互生积分分配
     *
     * @param page     分页对象
     * @param mlmQuery 查询实体
     * @return list
     * @throws HsException
     */
    PageData<MlmTotal> queryMlmListPage(Page page, MlmQuery mlmQuery) throws HsException;
}
