package com.gy.hsxt.gpf.bm.mapper;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;

import java.util.List;


/**
 * 互生积分分配Mapper接口
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : PointValueMapperImpl
 * @Description : 互生积分分配Mapper接口
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public interface PointValueMapper extends BaseMapper<PointValue> {

    /**
     * 根据主键范围查询互生积分分配数据
     *
     * @param startRowKey 起始键
     * @param endRowKey   结束键
     * @return list
     * @throws HsException
     */
    List<PointValue> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException;

    /**
     * 根据条件查询积分互生分配信息列表
     *
     * @param query 查询实体
     * @return {@code List<PointValue>}
     * @throws HsException
     */
    List<PointValue> findRowsByQuery(Query query) throws HsException;

    /**
     * 分页条件查询积分互生分配详情列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return data
     */
    GridData<PointValue> findPointValueListPage(GridPage page, Query query);
}

	