package com.gy.hsxt.gpf.bm.mapper.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.mapper.BmlmMapper;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 再增值积分Mapper接口实现
 *
 * @Package : com.gy.hsxt.gpf.bm.mapper.impl
 * @ClassName : BmlmMapperImpl
 * @Description : 再增值积分Mapper接口实现
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
@Repository("bmlmMapper")
public class BmlmMapperImpl implements BmlmMapper {

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(BmlmMapperImpl.class);

	/**
	 * 数据库链接
	 */
	@Resource
	private HBaseDB hbaseDb;
	
	@Override
	public Bmlm getValueByKey(String key) throws HsException {
		try {
			return hbaseDb.queryByRowKey(Constants.T_APP_BMLM, key, Bmlm.class);
		} catch (Exception e) {
			logger.error("=======再增值积分key[{}]查询错误=======", key, e);
			throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "查询再增值积分异常");
		}
	}

	/**
	 * 保存数据
	 *
	 * @param key   键
	 * @param value 值
	 */
	@Override
	public void save(String key, Bmlm value) throws HsException {
		logger.info("=========保存再增值积分[{}]=========", value);
		try {
			hbaseDb.insertRow(Constants.T_APP_BMLM, key, value,Bmlm.class);
		} catch (Exception e) {
			logger.error("==========保存再增值积分key[{}]错误========", key, e);
			throw new HsException(BMRespCode.BM_SAVE_BMLM_ERROR.getCode(), "保存再增值积分异常");
		}
	}

	/**
	 * 根据时间段查询再增值积分列表:按月统计
	 *
	 * @param startRowKey 起始key
	 * @param endRowKey   结束key
	 * @return list
	 * @throws HsException
	 */
	@Override
	public List<Bmlm> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException {
		logger.info("========根据时间段查询再增值积分列表---startRowKey[{}],endRowKey[{}]------========", startRowKey, endRowKey);
		try {
			return hbaseDb.queryScanList(Constants.T_APP_BMLM, startRowKey, endRowKey, Bmlm.class);
		} catch (Exception e) {
			logger.error("=========根据时间段查询再增值积分列表错误=========", e);
			throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "根据时间段查询再增值积分列表异常");
		}
	}

	/**
	 * 条件查询再增值分配详情列表
	 *
	 * @param query 查询实体
	 * @return {@code List<Bmlm>}
	 * @throws HsException
	 */
	@Override
	public List<Bmlm> findBmlmListByQuery(Query query) throws HsException {
		logger.info("========条件查询再增值分配详情列表,参数[{}]------========",query);
		try {
			return hbaseDb.queryScanList(Constants.T_APP_BMLM, query.getStartDate(), query.getEndDate(), query.bulidMap(),Bmlm.class);
		} catch (Exception e) {
			logger.error("=========条件查询再增值分配详情列表错误=========", e);
			throw new HsException(BMRespCode.BM_QUERY_DATA_ERROR.getCode(), "条件查询再增值分配详情列表异常，原因:"+e.getMessage());
		}
	}

	/**
	 * 分页条件查询再增值分配详情列表
	 *
	 * @param page  分页对象
	 * @param query 查询实体
	 * @return data
	 */
	@Override
	public GridData<Bmlm> findBmlmListPage(GridPage page, Query query) {
		logger.info("========分页条件查询再增值分配详情列表,参数[{}]------========",query);
		return hbaseDb.queryPageFilterList(Constants.T_APP_BMLM,Bmlm.class,page,query);
	}
}

	