package com.gy.hsxt.es.distribution.mapper;

import java.util.List;

import com.gy.hsxt.es.distribution.bean.EntryAllot;
import com.gy.hsxt.es.distribution.bean.PointAllot;
import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.PointPage;
import com.gy.hsxt.es.points.bean.PointDetail;

/**
 * @description 积分分配处理接口
 * @author chenhz
 * @createDate 2015-7-27 下午3:16:09
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public interface PointAllocMapper {
	
	// 电商结算数据(用于对账)
	public void ecSettleDataTemp(List<PointDetail> list) throws HsException;
	
	// 查询原已分配积分记录
	public PointAllot getPointAllot(@Param("relTransNo") String relTransNo) throws HsException;
	
	// 对账获取失败的单
	public List<PointDetail> getFailData() throws HsException;
	
	// 获取订单总数
	public int queryPointSum() throws HsException;

	// 修改订单总数
	public void updateRelTaxSumNo(@Param("relTaxSumNo") String relTaxSumNo, @Param("custId") String custId, @Param("accType") String accType) throws HsException;

	// 获取订单汇总总数
	public int queryCollectCount() throws HsException;

	// 获取消费积分日终结算数据
	public List<PointDetail> queryPoint(PointPage pointPage) throws HsException;

	// 获取退货日终结算数据
	public List<PointDetail> queryBack() throws HsException;

	// 实时批量插入分配后的积分数据
	public void insertAlloc(List<Alloc> list) throws HsException;

	// 实时插入分配后的积分数据
	public void insertEntry(EntryAllot entryAllot) throws HsException;
	
	/**
	 * 实时批量插入分配后的积分数据
	 * @param entryAllotList
	 * @throws HsException
	 */
    public void insertEntrys(List<EntryAllot> entryAllotList) throws HsException;

	// 日终批量插入分配后的积分数据
	public void insertDailyBatAlloc(List<Alloc> list) throws HsException;

	// 分组查询日终汇总
	public List<Alloc> queryCollect() throws HsException;

	// 插入日终汇总
	public void insertCollect(List<Alloc> list) throws HsException;

	// 日终批量插入城市税收
	public void insertTax(List<Alloc> list) throws HsException;
	
	// 日终分组合计由电商产生的企业销售额 
	public List<Alloc> queryEntry() throws HsException;

	// 批量插入商业服务费(日终、月终)
	public void insertCsc(List<Alloc> list) throws HsException;

	// 查询当月的商业服务费
	public List<Alloc> queryMonthCsc() throws HsException;

	// 月终批量插入商业服务费
	public void insertMonthCsc(List<Alloc> list) throws HsException;

	// 查询每天企业退货统计
	public List<PointDetail> queryDailyBackTax() throws HsException;

	// 获取冲正详单
	public List<PointDetail> queryCorrect(PointPage pointPage) throws HsException;

	//  线上交易分录信息结算修改
	public void updateEntryDetail(EntryAllot entryAllot) throws HsException;
	
	/**
	 *  批量线上交易分录信息结算修改
	 * @param entryAllotList
	 * @throws HsException
	 */
    public void updateEntryDetails(List<EntryAllot> entryAllotList) throws HsException;

	//  线上交易分录信息结算修改
	public void updateEntryByEntryNo(EntryAllot entryAllot) throws HsException;

	// 插入积分分配的数据
	public void insertPointAllotDaily(List<PointAllot> pointAllot) throws HsException;
}
