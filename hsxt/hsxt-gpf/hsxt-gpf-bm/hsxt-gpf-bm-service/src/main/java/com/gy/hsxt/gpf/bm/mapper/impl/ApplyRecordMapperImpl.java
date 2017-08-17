package com.gy.hsxt.gpf.bm.mapper.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.mapper.ApplyRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 申报记录Mapper接口实现
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : ApplyRecordMapperImpl
 * @Description : 申报记录Mapper接口实现
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
@Repository("applyRecordMapper")
public class ApplyRecordMapperImpl implements ApplyRecordMapper {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(ApplyRecordMapperImpl.class);

    /**
     * 数据库连接
     */
    @Resource
    private HBaseDB hbaseDb;

    /**
     * 根据key 获取value
     *
     * @param key 键
     * @return 值
     */
    @Override
    public ApplyRecord getValueByKey(String key) throws HsException {
        try {
            return hbaseDb.queryByRowKey(Constants.T_APP_TEMP_RECORD, key, ApplyRecord.class);
        } catch (Exception e) {
            logger.error("=========申报记录key[{}]查询错误=========",key, e);
            throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "查询申报记录异常");
        }
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, ApplyRecord value) throws HsException {
        logger.info("==========保存申报记录[applyRecord]:{}===========", value);
        try {
            hbaseDb.insertRow(Constants.T_APP_TEMP_RECORD, key, value, ApplyRecord.class);
        } catch (Exception e) {
            logger.error("=========申报记录key[{}]保存错误=========",key, e);
            throw new HsException(BMRespCode.BM_SAVE_APPLY_RECORD_ERROR.getCode(), "保存申报记录异常");
        }
    }
}

	