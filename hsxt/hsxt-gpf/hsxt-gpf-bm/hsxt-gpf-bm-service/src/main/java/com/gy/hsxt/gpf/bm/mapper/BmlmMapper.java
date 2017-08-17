package com.gy.hsxt.gpf.bm.mapper;


import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;

import java.util.List;

/**
 * 再增值积分Mapper接口
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : ApplyRecordMapperImpl
 * @Description : 再增值积分Mapper接口
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public interface BmlmMapper extends BaseMapper<Bmlm> {


    /**
     * 根据时间段查询再增值积分列表:按月统计
     *
     * @param startRowKey 起始key
     * @param endRowKey 结束key
     * @return list
     * @throws HsException
     */
    List<Bmlm> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException;

    /**
     * 条件查询再增值分配详情列表
     *
     * @param query 查询实体
     * @return {@code List<Bmlm>}
     * @throws HsException
     */
    List<Bmlm> findBmlmListByQuery(Query query) throws HsException;

    /**
     * 分页条件查询再增值分配详情列表
     * @param page 分页对象
     * @param query 查询实体
     * @return data
     */
    GridData<Bmlm> findBmlmListPage(GridPage page, Query query);
}

	