package com.gy.hsxt.ws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.ws.bean.ClaimsAccountingInfo;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;

/**
 * 理赔核算记录 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: ClaimsAccountingInfoMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 上午11:33:25
 * @version V1.0
 */
public interface ClaimsAccountingInfoMapper {

	/**
	 * 生成理赔核算单
	 * 
	 * @param record
	 *            理赔核算单
	 * @return
	 */
	int insertSelective(ClaimsAccountingInfo record);

	/**
	 * 查询理赔核算单记录 通过核算单编号(主键)
	 * 
	 * @param accountingId
	 *            核算单编号(主键)
	 * @return
	 */
	ClaimsAccountingInfo selectByPrimaryKey(String accountingId);

	/**
	 * 更新核算单记录
	 * 
	 * @param record
	 *            核算单
	 * @return
	 */
	int updateByPrimaryKeySelective(ClaimsAccountingInfo record);

	/**
	 * 更新核算单 通过工单编号
	 * 
	 * @param record
	 *            核算单
	 * @return
	 */
	int updateByTaskId(ClaimsAccountingInfo record);

	/**
	 * 分页查询核算单 统计总记录
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @param status
	 *            核算单状态 0待核算 1 核算中 2 已核算
	 * @return
	 */
	int countClaimsAccounting(
			@Param("condition") ClaimsAccountingQueryCondition queryCondition,
			@Param("status") Integer... status);

	/**
	 * 分页查询核算单
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @param page
	 *            分页参数
	 * @param status
	 *            核算单状态 0待核算 1 核算中 2 已核算
	 * @return
	 */
	List<ClaimsAccountingInfo> pageClaimsAccounting(
			@Param("condition") ClaimsAccountingQueryCondition queryCondition,
			@Param("page") Page page, @Param("status") Integer... status);
}