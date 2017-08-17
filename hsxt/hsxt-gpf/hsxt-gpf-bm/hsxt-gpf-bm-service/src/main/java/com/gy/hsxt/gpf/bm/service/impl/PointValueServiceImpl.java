package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.mapper.PointValueMapper;
import com.gy.hsxt.gpf.bm.service.PointValueService;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分互生分配业务接口
 *
 * @Package :com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : PointValueServiceImpl
 * @Description : 积分互生分配业务实现
 * @Author : chenm
 * @Date : 2015/9/30 13:17
 * @Version V3.0.0.0
 */
@Service("pointValueService")
public class PointValueServiceImpl implements PointValueService {

    @Resource
    private PointValueMapper pointValueMapper;

    @Override
    public void save(String key, PointValue value) throws HsException {
        pointValueMapper.save(key, value);
    }

    @Override
    public PointValue getValueByKey(String key) throws HsException {
        return pointValueMapper.getValueByKey(key);
    }

    /**
     * 根据时间段查询增值积分列表:按周统计
     *
     * @param startRowKey 起始key
     * @param endRowKey   结束key
     * @return List   返回类型
     * @throws HsException
     */
    @Override
    public List<PointValue> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException {
        return pointValueMapper.findByRowKeyRange(startRowKey, endRowKey);
    }

    /**
     * 条件查询互生积分分配详情列表
     *
     * @param query 查询实体
     * @return List<PointValue>
     * @throws HsException
     */
    @Override
    public List<PointValue> queryPointValueList(Query query) throws HsException {
        return pointValueMapper.findRowsByQuery(query);
    }

    /**
     * 分页条件查询积分互生分配详情列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return data
     */
    @Override
    public GridData<PointValue> queryPointValueListPage(GridPage page, Query query) {
        return pointValueMapper.findPointValueListPage(page,query);
    }
}

	