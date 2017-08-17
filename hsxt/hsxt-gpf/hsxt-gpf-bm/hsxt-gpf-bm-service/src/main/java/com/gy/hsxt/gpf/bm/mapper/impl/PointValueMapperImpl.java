package com.gy.hsxt.gpf.bm.mapper.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.mapper.PointValueMapper;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 互生积分分配Mapper接口实现
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : PointValueMapperImpl
 * @Description : 互生积分分配Mapper接口实现
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
@Repository("pointValueMapper")
public class PointValueMapperImpl implements PointValueMapper {

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(PointValueMapperImpl.class);

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
	public PointValue getValueByKey(String key) throws HsException {
		try {
			return hbaseDb.queryByRowKey(Constants.T_APP_POINT_VALUE, key,PointValue.class);
		} catch (Exception e) {
			logger.error("======查询互生积分分配key[{}]错误======", e);
			throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "查询互生积分分配异常");
		}
	}

	/**
	 * 保存数据
	 *
	 * @param key   键
	 * @param value 值
	 */
	@Override
	public void save(String key, PointValue value) throws HsException {
		logger.info("==========保存互生积分分配参数key[{}],value[{}]======", key, value);
		try {
			hbaseDb.insertRow(Constants.T_APP_POINT_VALUE, key, value,PointValue.class);
		} catch (Exception e) {
			logger.error("=========保存互生积分分配key[{}]错误=======", key);
			throw new HsException(BMRespCode.BM_SAVE_MLM_ERROR.getCode(), "保存互生积分分配异常");
		}
	}

	/**
	 * 根据主键范围查询互生积分分配数据
	 *
	 * @param startRowKey 起始键
	 * @param endRowKey   结束键
	 * @return list
	 * @throws HsException
	 */
	@Override
	public List<PointValue> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException {
		logger.info("========根据主键范围查询互生积分分配列表---startRowKey[{}],endRowKey[{}]------========", startRowKey, endRowKey);
		try {
			return hbaseDb.queryScanList(Constants.T_APP_POINT_VALUE, startRowKey, endRowKey, PointValue.class);
		} catch (Exception e) {
			logger.error("=======根据主键范围查询互生积分分配列表错误========", e);
			throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "根据主键范围查询互生积分分配列表异常");
		}
	}

	/**
	 * 根据条件查询积分互生分配信息列表
	 *
	 * @param query 查询实体
	 * @return {@code List<PointValue>}
	 * @throws HsException
	 */
	@Override
	public List<PointValue> findRowsByQuery(Query query) throws HsException {
		logger.info("========根据条件查询积分互生分配信息列表参数:[{}]------========",query);
		try {
			return hbaseDb.queryScanList(Constants.T_APP_POINT_VALUE, query.getStartDate(), query.getEndDate(),query.bulidMap(), PointValue.class);
		} catch (Exception e) {
			logger.error("=======根据条件查询积分互生分配信息列表错误========");
			throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "根据条件查询积分互生分配信息列表异常");
		}
	}

	/**
	 * 分页条件查询积分互生分配详情列表
	 *
	 * @param page  分页对象
	 * @param query 查询实体
	 * @return data
	 */
	@Override
	public GridData<PointValue> findPointValueListPage(GridPage page, Query query) {
		logger.info("========分页条件查询积分互生分配详情列表参数:[{}]------========",query);
		return hbaseDb.queryPageFilterList(Constants.T_APP_POINT_VALUE,PointValue.class,page,query);
	}
}

	