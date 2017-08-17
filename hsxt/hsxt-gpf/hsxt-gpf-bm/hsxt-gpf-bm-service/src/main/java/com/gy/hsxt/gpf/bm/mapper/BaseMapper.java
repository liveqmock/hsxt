package com.gy.hsxt.gpf.bm.mapper;


import com.gy.hsxt.common.exception.HsException;

/**
 * 抽象数据库接口
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper
 * @ClassName : BaseMapper
 * @Description : 抽象数据库接口
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public interface BaseMapper<T> {
    /**
     * 根据key 获取value
     *
     * @param key 键
     * @return 值
     */
    T getValueByKey(String key) throws HsException;

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    void save(String key, T value) throws HsException;
}

	