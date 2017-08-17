/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.mapper.vo.GPBSPayBalanceVo;
import com.gy.hsxt.gp.mapper.vo.GPCHPayBalanceVo;
import com.gy.hsxt.gp.mapper.vo.GPPSPayBalanceVo;
import com.gy.hsxt.gp.mapper.vo.RefundVo;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.mapper.vo
 * 
 *  File Name       : TGpPaymentOrderMapper.java
 * 
 *  Creation Date   : 2015-9-30
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付单表操作接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface TGpPaymentOrderMapper {

	/** 
	 * 根据条件查询订单的所有状态中的一条
	 * 
	 * @param merId
	 * @param orderNo
	 * @return
	 */
	TGpPaymentOrder selectOneBySelective(@Param("merId") String merId,
			@Param("orderNo") String orderNo);

	/**
	 * 根据条件查询订单的所有状态中的一条
	 * 
	 * @param merId
	 * @param orderNo
	 * @return
	 */
	PaymentOrderState selectPaymentOrderState(@Param("merId") String merId,
			@Param("orderNo") String orderNo);

	/** 
	 * 根据条件查询订单的支付成功或者处理中状态中的一条
	 * 
	 * @param merId
	 * @param orderNo
	 * @return
	 */
	Integer selectOneBySucHanding(@Param("merId") String merId,
			@Param("orderNo") String orderNo);

	/** 
	 * 根据条件查询最新的待支付(废弃不用了)
	 * 
	 * @param merId
	 * @param orderNo
	 * @return
	 */
	@Deprecated
	TGpPaymentOrder selectNearReadyOrder(@Param("merId") String merId,
			@Param("orderNo") String orderNo);

	/** 
	 * 根据银行支付单号查询
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	TGpPaymentOrder selectByBankOrderNo(
			@Param("bankOrderNo") String bankOrderNo);

	/** 
	 * 根据银行支付单号更新
	 * 
	 * @param paymentOrder
	 * @return
	 */
	int updateStatusByBankOrderNo(TGpPaymentOrder paymentOrder);

	/** 
	 * 新增
	 * 
	 * @param tGpPaymentOrder
	 * @return
	 */
	int insert(TGpPaymentOrder tGpPaymentOrder);

	/** 
	 * 根据transSeqId查询支付单通知内容 
	 * 
	 * @param transSeqId
	 * @return
	 */
	PaymentOrderState selectOrderStateByPrimary(
			@Param("transSeqId") String transSeqId);

	/** 
	 * 查询 支付表中重复的支付单 
	 * 
	 * @return
	 */
	List<TGpPaymentOrder> listSameOrder();

	/** 
	 * 根据transSeqId更新支付单位无效状态
	 * 
	 * @param transSeqId
	 * @return
	 */
	int update2InvalidByPrimary(String transSeqId);

	/** 
	 * 根据transSeqId查询退款请求
	 * 
	 * @param transSeqId
	 * @return
	 */
	RefundVo selectRefundByPrimary(String transSeqId);

	/** 
	 * [对账文件生成]: 查询与银行的对账数据
	 * 
	 * @param parasMap
	 * @return
	 */
	List<GPCHPayBalanceVo> selectByTransDate(Map<String, Object> parasMap);

	/** 
	 * [对账文件生成]: 查询与银行的对账数据条数
	 * 
	 * @param date
	 * @return
	 */
	int getcountByTransDate(Map<String, String> date);

	/** 
	 * [对账文件生成]: 查询GP-BS、GP-AO系统的对账数据
	 * 
	 * @param parasMap
	 * @return
	 */
	List<GPBSPayBalanceVo> selectByOrderDate$GPBS(Map<String, Object> parasMap);

	/** 
	 * [对账文件生成]: 查询GP-PS系统的对账数据 
	 * 
	 * @param parasMap
	 * @return
	 */
	List<GPPSPayBalanceVo> selectByOrderDate$GPPS(Map<String, Object> parasMap);

	/** 
	 * [对账文件生成]: 查询GP-BS、GP-AO系统的对账数据条数
	 * 
	 * @param date
	 * @return
	 */
	int getcountByOrderDate$GPBS(Map<String, String> date);

	/** 
	 * [对账文件生成]: 查询GP-PS系统的对账数据条数 
	 * 
	 * @param date
	 * @return
	 */
	int getcountByOrderDate$GPPS(Map<String, String> date);

}