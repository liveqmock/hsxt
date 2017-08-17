package com.gy.hsxt.gpf.bm.mapper;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.IncNode;

import java.util.List;
import java.util.Map;


/**
 * 增值点Mapper接口
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper
 * @ClassName : IncNodeMapper
 * @Description : 增值点Mapper接口
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public interface IncNodeMapper extends BaseMapper<IncNode> {

    /**
     * 获取全部增值点
     *
     * @return list
     */
    List<IncNode> getAllRows() throws HsException;

    /**
     * 根据资源号查询数据
     *
     * @param resNo
     * @return
     * @throws HsException
     */
    List<IncNode> getRowByResNo(String resNo) throws HsException;

    /**
     * 根据层级查询数据
     *
     * @param level
     * @return
     * @throws HsException
     */
    Map<String, IncNode> getRowByLevelMap(String level) throws HsException;

    /**
     * 通过行键删除记录
     *
     * @param rowKey 行键
     * @throws HsException
     */
    void deleteByRowKey(String rowKey) throws HsException;
}

	