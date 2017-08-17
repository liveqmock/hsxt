package com.gy.hsxt.gpf.bm.mapper.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.mapper.IncNodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 增值点Mapper接口实现
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : IncNodeMapperImpl
 * @Description : 增值点Mapper接口实现
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
@Repository("incNodeMapper")
public class IncNodeMapperImpl implements IncNodeMapper {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(IncNodeMapperImpl.class);

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
    public IncNode getValueByKey(String key) throws HsException {
        try {
            return hbaseDb.queryByRowKey(Constants.T_APP_INCREMENT, key, IncNode.class);
        } catch (Exception e) {
            logger.error("==========查询增值节点key[{}]错误=======", key, e);
            throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "查询增值节点异常");
        }
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, IncNode value) throws HsException {
        logger.info("========保存增值节点参数[key]:{} ,[value]:{}=======", key, value);
        try {
            hbaseDb.insertRow(Constants.T_APP_INCREMENT, key, value, IncNode.class);
        } catch (Exception e) {
            logger.error("======保存增值节点错误key[{}]======", key, e);
            throw new HsException(BMRespCode.BM_SAVE_MLM_ERROR.getCode(), "保存增值节点异常");
        }
    }

    /**
     * 根据资源号查询增值节点
     *
     * @param resNo 资源号
     * @return inc
     * @throws HsException
     */
    @Override
    public List<IncNode> getRowByResNo(String resNo) throws HsException {
        try {
            return hbaseDb.queryFilterList(Constants.T_APP_INCREMENT, "resNo", null, resNo, IncNode.class);
        } catch (Exception e) {
            logger.error("==========根据资源号查询增值节点resNo[{}]错误=======", resNo, e);
            throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "根据资源号查询增值节点异常");
        }
    }

    /**
     * 根据层级查询数据
     *
     * @param level 层级
     * @return map
     * @throws HsException
     */
    public Map<String, IncNode> getRowByLevelMap(String level) throws HsException {
        try {
            return hbaseDb.queryFilterMap(Constants.T_APP_INCREMENT, "level", null, level, IncNode.class);
        } catch (Exception e) {
            logger.error("=========根据层级查询数据level[{}]错误=======", level, e);
            throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "根据层级查询数据异常");
        }
    }

    /**
     * 获取全部增值点
     *
     * @return list
     */
    @Override
    public List<IncNode> getAllRows() throws HsException {
        try {
            return hbaseDb.queryAllRows(Constants.T_APP_INCREMENT, IncNode.class);
        } catch (Exception e) {
            logger.error("=======查询所有增值节点错误========", e);
            throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "查询所有增值节点异常");
        }
    }


    /**
     * 通过行键删除记录
     *
     * @param rowKey 行键
     * @throws HsException
     */
    @Override
    public void deleteByRowKey(String rowKey) throws HsException {
        try {
            hbaseDb.deleteByRowKey(Constants.T_APP_INCREMENT, rowKey);
        } catch (Exception e) {
            throw new HsException(BMRespCode.BM_DELETE_DATA_ERROR.getCode(), e.getMessage());
        }
    }
}

	