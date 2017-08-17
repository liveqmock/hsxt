package com.gy.hsxt.ps.distribution.mapper;

import java.sql.SQLException;
import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import org.apache.ibatis.annotations.Param;

public interface HsbAllocMapper
{

	// 查询互生币交易订单
	List<Alloc> queryOfflineEntry(String runDate) throws SQLException;

	// 日终查询电商的交易订单
	List<Alloc> queryOnlineEntry(String runDate) throws SQLException;

	// 日终汇总线下交易销售额紧退货，没有做正常单
	List<Alloc> queryOfflineEntryOnlyBc(String runDate) throws SQLException;

	// 日终汇总线上交易销售额紧退货，没有做正常单
	List<Alloc> queryOnlineEntryOnlyBc(String runDate) throws SQLException;

	// 查询当月每天的商业服务费
	List<Alloc> queryMonthCsc(String batchNo) throws SQLException;

	// 插入商业服务费(日终暂存、月结)
	void insertDailyCsc(List<Alloc> list) throws SQLException;
	
	// 插入互生币汇总(减商业服务费后)
	void insertHsbSum(List<Alloc> list)throws SQLException;
	
	// 更新商业服务费结算状态
	void updateCscIssettle() throws SQLException;
	
	// 更新商城待清算结算状态
	void updatePointSettle(String runDate) throws SQLException;
		
	// 更新商城待清算结算状态
	void updateEsHsbSettle(String runDate) throws SQLException;
	
	// 更新线下待清算结算状态
	void updatePosHsbSettle(String runDate) throws SQLException;
	
	// 更新互生币汇总结算状态
	void updateHsbSumSettle(String runDate) throws SQLException;

	// 更新冲正标识
	void updatePosHsbWriteBack(@Param("writeBack")String writeBack,@Param("entryNo")String entryNo) throws SQLException;

	// 更新隔日退退货表状态(是否已分配结算）
	void updateBPointSettle(String runDate) throws SQLException;

}
