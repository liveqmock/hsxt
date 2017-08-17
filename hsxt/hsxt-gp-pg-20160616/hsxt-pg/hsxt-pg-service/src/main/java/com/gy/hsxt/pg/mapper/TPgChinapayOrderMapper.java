package com.gy.hsxt.pg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayOrder;

public interface TPgChinapayOrderMapper {

	/** 
	 * 根据订单号查询是否存在
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
	 * 根据订单号、交易状态查询是否存在
	 * 
	 * @param bankOrderNo
	 * @param transStatus
	 * @return
	 */
	int isExistOrder(@Param("bankOrderNo") String bankOrderNo,
			@Param("transStatus") int transStatus);

	/** 
	 * 根据订单号查询订单数据
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	TPgChinapayOrder selectByBankOrderNo(String bankOrderNo);

	/** 
	 * 根据订单号查询订单状态
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	PGPaymentOrderState selectStateByBankOrderNo(String bankOrderNo);

	/**
	 * 根据状态查询
	 * 
	 * @param transStatus
	 * @return
	 */
	List<TPgChinapayOrder> listByStatus(int transStatus);

	/**
	 * 插入
	 * 
	 * @param record
	 * @return
	 */
	int insert(TPgChinapayOrder record);

	/**
	 * 更新支付单状态
	 * 
	 * @param record
	 * @return
	 */
	int updateStatusByBankOrderNo(TPgChinapayOrder record);

	/**
	 * 获取跳转地址
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	String getJumpUrl(String bankOrderNo);
}