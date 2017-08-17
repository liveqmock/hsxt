/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.interfaces;

import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.BmlmQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 再增值积分详情业务层接口
 *
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : IBmlmDetailService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 16:30
 * @Version V3.0.0.0
 */
public interface IBmlmDetailService {

    /**
     * 保存单个再增值详情
     *
     * @param detail
     * @return
     * @throws HsException
     */
    int save(BmlmDetail detail) throws HsException;

    /**
     * 根据业务流水号查询再增值详情
     *
     * @param bmlmId
     * @return
     * @throws HsException
     */
    BmlmDetail queryById(String bmlmId) throws HsException;

    /**
     * 分页查询再增值积分列表
     *
     * @param page      分页对象
     * @param bmlmQuery 查询实体
     * @return list
     * @throws HsException
     */
    List<BmlmDetail> queryBmlmListPage(Page page, BmlmQuery bmlmQuery) throws HsException;

}
