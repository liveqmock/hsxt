package com.gy.hsxt.ps.distribution.mapper;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;
import com.gy.hsxt.ps.distribution.bean.PointAllot;
import com.gy.hsxt.ps.distribution.bean.PointPage;
import com.gy.hsxt.ps.points.bean.PointDetail;
import org.apache.ibatis.annotations.Param;
import java.sql.SQLException;
import java.util.List;

/**
 * @description 积分分配处理接口
 * @author chenhz
 * @createDate 2015-7-27 下午3:16:09
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public interface PointAllocMapper
{

	// 获取订单总数
	int queryPointSum(@Param("runDate") String runDate) throws SQLException;

	// 修改订单总数
	void updateRelTaxSumNo(@Param("relTaxSumNo") String relTaxSumNo,
						   @Param("custId") String custId, @Param("accType") String accType) throws SQLException;

	// 获取订单汇总总数
	int queryCollectCount(@Param("runDate") String runDate) throws SQLException;

	// 分组查询日终汇总
	List<Alloc> queryCollect(PointPage pointPage) throws SQLException;

	// 获取订单汇总总数
	int queryPointAllotCount(@Param("runDate") String runDate) throws SQLException;

	// 分组查询日终汇总
	List<PointAllot> queryPointAllot(PointPage pointPage) throws SQLException;

	// 查询消费积分待分配积分订单
	List<PointDetail> queryPoint(PointPage pointPage) throws SQLException;

	// 获取退货日终结算数据
	List<PointDetail> queryBack() throws SQLException;


	// 实时批量插入分配后的积分数据
	void insertEntry(EntryAllot entryAllot) throws SQLException;

	// 插入积分分配的数据
	void insertPointAllotDaily(List<PointAllot> pointAllot) throws SQLException;

	// 插入日终汇总
	void insertCollect(List<Alloc> list) throws SQLException;

	// 日终汇总扣税后批量插入城市税收
	void insertTax(List<Alloc> list) throws SQLException;

	// 查询税收
	List<Alloc> queryTax(String runDate) throws SQLException;

	// 日终退税查询统计
	int queryTaxEntryCount(@Param("runDate") String runDate) throws SQLException;

	// 日终退税查询
	List<PointAllot> queryTaxEntry(@Param("pointPage") PointPage pointPage) throws SQLException;

	// 批量插入商业服务费(日终、月终)
	void insertCsc(List<Alloc> list) throws SQLException;

	// 查询当月的商业服务费
	List<Alloc> queryMonthCsc() throws SQLException;

	// 月终批量插入商业服务费
	void insertMonthCsc(List<Alloc> list) throws SQLException;

	// 批量修改消费积分状态（是否已结算）
	void batchUpdateNdetail(List<PointAllot> list) throws SQLException;

	// 更新消费积分订单是否已结算状态
	void updatePointSettle(String transNo) throws SQLException;

	// 更新消费积分分配是否已结算状态
	void updatePointAllocSettle(String runDate) throws SQLException;

	// 更新消费积分汇总是否已结算状态
	void updatePointSumSettle(String runDate) throws SQLException;

	// 更新隔日退退货表状态(是否已分配结算）
	void updateBPointSettle(String runDate) throws SQLException;

	// 查询原已分配积分记录
    public PointAllot getPointAllot(@Param("relTransNo") String relTransNo) throws SQLException;
}
