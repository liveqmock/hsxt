/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.mapper.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.OperRecord;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.mapper.OperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 操作记录Dao层实现
 *
 * @Package :com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : OperMapperImpl
 * @Description : 操作记录Dao层实现
 * @Author : chenm
 * @Date : 2015/10/16 10:38
 * @Version V3.0.0.0
 */
@Repository("operMapper")
public class OperMapperImpl implements OperMapper {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(OperMapperImpl.class);

    /**
     * 数据库链接
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
    public OperRecord getValueByKey(String key) throws HsException {
        try {
            return hbaseDb.queryByRowKey(Constants.T_APP_OPER_RECORD, String.valueOf(key), OperRecord.class);
        } catch (Exception e) {
            logger.error("=====查询操作记录key[{}]错误==========", key, e);
            throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(),"查询操作记录异常");
        }
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, OperRecord value) throws HsException {
        logger.info("======保存操作记录key[{}],value[{}]=======", key, value);
        try {
            hbaseDb.insertRow(Constants.T_APP_OPER_RECORD, key, value, OperRecord.class);
        } catch (Exception e) {
            logger.error("=========保存操作记录key[{}]错误======", key, e);
            throw new HsException(BMRespCode.BM_SAVE_OPER_ERROR.getCode(),"保存操作记录");
        }
    }
}
