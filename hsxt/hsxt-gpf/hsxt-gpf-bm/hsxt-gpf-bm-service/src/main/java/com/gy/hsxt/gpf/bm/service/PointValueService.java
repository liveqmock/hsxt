package com.gy.hsxt.gpf.bm.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;

import java.util.List;

/**
 * 积分互生分配业务接口
 *
 * @Package :com.gy.hsxt.gpf.bm.service
 * @ClassName : PointValueService
 * @Description : 积分互生分配业务接口
 * @Author : chenm
 * @Date : 2015/9/30 13:17
 * @Version V3.0.0.0
 */
public interface PointValueService extends BaseService<PointValue> {

    /**
     * 根据时间段查询增值积分列表:按周统计
     *
     * @param startRowKey 起始key
     * @param endRowKey   结束key
     * @return List   返回类型
     * @throws HsException
     */
    List<PointValue> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException;

    /**
     * 条件查询互生积分分配详情列表
     *
     * @param query 查询实体
     * @return List<PointValue>
     * @throws HsException
     */
    List<PointValue> queryPointValueList(Query query) throws HsException;

    /**
     * 分页条件查询积分互生分配详情列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return data
     */
    GridData<PointValue> queryPointValueListPage(GridPage page, Query query);
}

	