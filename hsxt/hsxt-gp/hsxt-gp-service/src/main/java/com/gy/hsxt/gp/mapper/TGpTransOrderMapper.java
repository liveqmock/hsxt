package com.gy.hsxt.gp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.gp.mapper.vo.TGpTransOrder;

public interface TGpTransOrderMapper {

	/**
	 * 新增
	 * 
	 * @param record
	 * @return
	 */
	int insert(TGpTransOrder record);

	/**
	 * 根据条件查询订单的所有状态中的一条
	 * 
	 * @param merId
	 * @param orderNo
	 * @return
	 */
	TGpTransOrder selectOneBySelective(@Param("merId") String merId,
			@Param("orderNo") String orderNo);

	/**
	 * 根据批次号查询批量提现结果
	 * 
	 * @param merId
	 * @param batchNo
	 * @return
	 */
	List<TGpTransOrder> selectbyBatchNoMerId(@Param("merId") String merId,
			@Param("batchNo") String batchNo);

	/**
	 * 根据条件查询订单的提现成功或者处理中状态中的一条
	 * 
	 * @param merId
	 * @param orderNo
	 * @return
	 */
	Integer selectOneBySucHanding(@Param("merId") String merId,
			@Param("orderNo") String orderNo);

	/**
	 * 根据银行支付单号查询
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	TGpTransOrder selectByBankOrderNo(@Param("bankOrderNo") String bankOrderNo);

	/**
	 * 根据支付流水号transSeqId查询
	 * 
	 * @param transSeqId
	 * @return
	 */
	TransCashOrderState selectOrderStateByPrimary(
			@Param("transSeqId") String transSeqId);

	/**
	 * 判断批次号在数据库中是否有重复
	 * 
	 * @param merId
	 * @param batchNo
	 * @return
	 */
	int countBatchNo(@Param("merId") String merId,
			@Param("batchNo") String batchNo);

	/**
	 * 根据订单号查询批量提现结果
	 * 
	 * @param params
	 * @return
	 */
	List<TGpTransOrder> selectbyOrderNoMerId(Map<String, Object> params);

	/**
	 * 根据银行支付单号更新
	 * 
	 * @param transOrder
	 * @return
	 */
	int updateStatusByBankOrderNo(TGpTransOrder transOrder);

	/**
	 * [对账文件生成]: 查询与其它系统的对账数据
	 * 
	 * @param parasMap
	 * @return
	 */
	// List<GPBSTransBalanceVo> selectByOrderDate(Map<String, Object> parasMap);

	/**
	 * [对账文件生成]: 查询与其它系统的对账数据条数
	 * 
	 * @param date
	 * @return
	 */
	// int getcountByOrderDate(Map<String, String> date);

}