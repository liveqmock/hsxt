package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.mapper.ApplyRecordMapper;
import com.gy.hsxt.gpf.bm.service.ApplyRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 申报记录业务接口实现
 *
 * @Package : com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : ApplyRecordServiceImpl
 * @Description : 申报记录业务接口实现
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
@Service("applyRecordService")
public class ApplyRecordServiceImpl implements ApplyRecordService {

    /**
     * 申报记录Mapper接口
     */
    @Resource(name = "applyRecordMapper")
    private ApplyRecordMapper applyRecordMapper;

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, ApplyRecord value) throws HsException {
        applyRecordMapper.save(key, value);
    }

    /**
     * 根据key 获取value
     *
     * @param key 键
     * @return 值
     */
    @Override
    public ApplyRecord getValueByKey(String key) throws HsException {
        return applyRecordMapper.getValueByKey(key);
    }
}

	