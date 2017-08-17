package com.gy.hsxt.es.points.mapper;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.es.distribution.bean.EntryAllot;
import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.bean.Correct;
import com.gy.hsxt.es.bean.Point;
import com.gy.hsxt.es.bean.QuetyPaying;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.CancellationDetail;
import com.gy.hsxt.es.points.bean.CorrectDetail;
import com.gy.hsxt.es.points.bean.PointDetail;

/**
 * @description 消费积分接口
 * @author chenhongzhi
 * @createDate 2015-7-27 下午3:16:09
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public interface PointMapper
{

	/** 货到付款、退货预扣 */
	public void point(PointDetail pointDetail) throws HsException;
	
	/** 消费者积分(预留、网上积分登记、预付定金) */
	public void points(List<PointDetail> pointDetail) throws HsException;

	/** 查询预留单(电商) */
	public PointDetail queryReserveOrder(@Param("point") Point point) throws HsException;

	/** 批量查询预扣单(电商)*/
	public List<PointDetail> batchQueryReserveOrder() throws HsException;
	
	/** 批量预扣结单(电商)*/
	public void batchUpdateReserveOrder() throws HsException;
	
	/** 消费者退货积分 */
	public void backPoint(BackDetail backDetail) throws HsException;

	/** 更新积分表交易金额(电商部分退货) */
	public void updatePoint(PointDetail pointDetail) throws HsException;

	/** 消费者撤单积分 */
	public void cancelPoint(CancellationDetail cancellationDetail) throws HsException;

	/** 冲正积分 */
	public void returnPoint(CorrectDetail correctDetail) throws HsException;

	/** 查询原单,通过原交易流水号查询积分明细原订单信息 */
	// public PointDetail queryOldOrder(String transNo) throws HsException;
	
	/** 查询原单,通过原交易流水号查询积分明细原订单信息 */
	public PointDetail queryOldOrder(@Param("transNo") String transNo) throws HsException;


	/** 查询原单,通过原交易流水号查询积分明细原订单信息 */
	public List<PointDetail> queryOldByOrderNo(@Param("orderNo") String orderNo, @Param("entResNo") String entResNo) throws HsException;


	/** 查询扫描原单,积分明细,退货明细,撤单明细的订单信息 */
	public PointDetail queryOldOrderAll(@Param("correct") Correct correct, @Param("tableName") String tableName);

	
	/** 更新原订单状态包括预留单 */
	public void updateStatus(@Param("transStatus") int transStatus, @Param("transNo") String transNo, @Param("tableName") String tableName)
			throws HsException;
	
	/** 批量更新原订单状态包括预留单 */
	public void batUpdateStatus(List<PointDetail> pointDetail) throws HsException;

	/** 根据原订单更新积分中订单状态 */
	public void updatePointStatus(@Param("status") int status, @Param("transNo") String transNo, @Param("tableName") String tableName)
			throws HsException;

	/** 查询需要冲正的订单 暂留 */
	public String querySerial(@Param("transNo") String transNo, @Param("tableName") String tableName) throws HsException;

	/**
     * 查询支付订单是否存在
     */
	public List<QuetyPaying> queryPaying(Map<String,String> queryPayingMap) throws HsException;
}
