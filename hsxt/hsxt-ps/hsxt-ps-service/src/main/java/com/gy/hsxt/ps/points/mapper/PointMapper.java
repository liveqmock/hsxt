package com.gy.hsxt.ps.points.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.points.bean.BackDetail;
import com.gy.hsxt.ps.points.bean.CorrectDetail;
import com.gy.hsxt.ps.points.bean.PointDetail;
import com.gy.hsxt.ps.points.bean.CancellationDetail;

/**
 * @description 消费积分接口
 * @author chenhongzhi
 * @createDate 2015-7-27 下午3:16:09
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public interface PointMapper
{

	/** 消费者积分 */
	void point(PointDetail pointDetail) throws SQLException;

	/** 查询预留单(电商) */
	PointDetail queryReserveOrder(@Param("point") Point point, @Param("status") int status) throws SQLException;

	/** 批量查询预扣单(电商) */
	List<PointDetail> batchQueryReserveOrder() throws SQLException;

	/** 批量预扣结单(电商) */
	void batchUpdateReserveOrder() throws SQLException;

	/** 消费者退货积分 */
	void backPoint(BackDetail backDetail) throws SQLException;

	/** 更新积分表交易金额(电商部分退货) */
	void updatePoint(PointDetail pointDetail) throws SQLException;

	/** 消费者撤单积分 */
	void cancelPoint(CancellationDetail cancellationDetail) throws SQLException;

	/** 冲正积分 */
	void returnPoint(CorrectDetail correctDetail) throws SQLException;

	/** 查询原单,通过原始交易流水号与企业互生号查询积分明细原交易单信息 */
	PointDetail queryOldOrder(@Param("sourceTransNo") String sourceTransNo, @Param("entResNo") String entResNo,@Param("perResNo") String perResNo) throws SQLException;
	
	/** 查询原单,通过交易流水号查询原交易单信息 */
    PointDetail queryPoint(@Param("transNo") String transNo) throws SQLException;

	/** 查询扫描原单,积分明细,退货明细,撤单明细的订单信息 */
	PointDetail queryOldOrderAll(@Param("correct") Correct correct, @Param("tableName") String tableName);

	/** 更新原订单状态包括预留单 */
	void updateStatus(@Param("transStatus") int transStatus, @Param("transNo") String transNo, @Param("tableName") String tableName)
			throws SQLException;

	/** 根据原订单更新积分中订单状态 */
	void updatePointStatus(@Param("status") int status, @Param("transNo") String transNo, @Param("tableName") String tableName)
			throws SQLException;

	/** 更新消费积分表支付状态 */
	//start--modified by liuzh on 2016-05-19 增加返回值
    //void updatePointPayStatus(PointDetail pointDetail) throws SQLException;
    int updatePointPayStatus(PointDetail pointDetail) throws SQLException;
    //end--modified by liuzh on 2016-05-19
}
