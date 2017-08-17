package com.gy.hsxt.pg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.pg.bean.PGTransCashOrderState;
import com.gy.hsxt.pg.mapper.vo.TPgPinganOrder;

public interface TPgPinganOrderMapper {

	/** 
	 * 根据bankOrderNo是否存在
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	int isExistBankOrderNo(@Param("bankOrderNo") String bankOrderNo);

	/** 
	 * 根据bankOrderNo查询merType
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	int selectMerTypeByBankOrderNo(@Param("bankOrderNo") String bankOrderNo);

	/** 
	 * 根据bankOrderNo查询
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	TPgPinganOrder selectByBankOrderNo(String bankOrderNo);

	/** 
	 * 根据bankOrderNo查询
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	PGTransCashOrderState selectStateByBankOrderNo(String bankOrderNo);

	/** 
	 * 根据批次号更新订单状态
	 * 
	 * @param bankBatchNo
	 * @param transStatus
	 * @param failReason
	 * @return
	 */
	int updateByBatchNo(@Param("bankBatchNo") String bankBatchNo,
			@Param("transStatus") Integer transStatus,
			@Param("failReason") String failReason);

	/** 
	 * 根据批次号查询所有订单
	 * 
	 * @param bankBatchNo
	 * @return
	 */
	List<TPgPinganOrder> selectByBatchNo(String bankBatchNo);

	/** 
	 * 根据批次号查询所有订单的bankBatchNo
	 * 
	 * @param bankBatchNo
	 * @return
	 */
	List<PGTransCashOrderState> selectStateByBatchNo(String bankBatchNo);
	
	/**
	 * 插入
	 * 
	 * @param record
	 * @return
	 */
	int insert(TPgPinganOrder record);

	/**
	 * 更新支付单状态
	 * 
	 * @param record
	 * @return
	 */
	int updateStatusByBankOrderNo(TPgPinganOrder record);
}