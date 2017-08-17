package com.gy.hsxt.ps.querys.mapper;

import java.sql.SQLException;
import java.util.List;

import com.gy.hsxt.ps.bean.*;
import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @description 查询接口
 * @author chenhz
 * @createDate 2015-7-27 下午3:17:43
 * @Company 深圳市归一科技研发有限公司
 * @version v3.0
 */
public interface QuerysMapper {

	// 单笔交易查询
	QueryResult singleQuery(@Param("querySingle") QuerySingle querySingle) throws HsException;
	
	// 查询可撤单积分记录流水
	List<PointRecordResult> pointToCancelRecordPage(@Param("pointRecord") QueryPointRecord pointRecord) throws HsException;

	// 查询可退货积分记录流水
	List<PointRecordResult> pointToBackRecordPage(@Param("pointRecord") QueryPointRecord pointRecord) throws HsException;

	// 单笔交易查询
	QueryResult singlePosQuery(@Param("querySingle") QuerySingle querySingle, @Param("queryPosSingle") QueryPosSingle queryPosSingle) throws HsException;
	
	//	根据流水号查询积分详情
	QueryResult queryPointDetails(@Param("transNo") String transNo) throws HsException;
	
	//	根据流水号查询撤单详情
	QueryResult queryCancelDetails(@Param("transNo") String transNo) throws HsException;

	//	根据流水号查询退货详情
	QueryResult queryBackDetails(@Param("transNo") String transNo) throws HsException;
	
	//	根据流水号查询企业收入详情
	ProceedsResult proceedsDetail(@Param("transNo") String transNo) throws HsException;

	//	根据流水号查询企业收入详情汇总
	ProceedsResult proceedsAllDetail(Proceeds proceeds) throws HsException;

	//	查询企业收入明细(线下)
	List<QueryEntry> proceedsOffLineEntryPage(@Param("queryDetail") QueryDetail queryDetail) throws HsException;

	//	查询企业收入明细（线上）
	List<QueryEntry> proceedsOnlineEntryPage(@Param("queryDetail") QueryDetail queryDetail) throws HsException;

	//	消费积分纳税详单
	TaxResult queryDayTax(@Param("transNo") String transNo) throws HsException;

	//	商业服务费纳税详单
	TaxResult queryCscTax(@Param("transNo") String transNo) throws HsException;

	// 分配其他(扣除税金)
	String queryAllocTaxAmount(@Param("queryDetail") QueryDetail queryDetail) throws HsException;

	// 分配明细汇总
	AllocDetailSum queryPointAllocSum(@Param("hsResNo") String hsResNo, @Param("batchNo") String batchNo, @Param("custType") int custType) throws HsException;

	// 分配明细汇总列表查询
    List<AllocDetailSum> queryPointAllocSumPage(@Param("hsResNo") String hsResNo, @Param("custType") int custType, @Param("beginBatchNo") String beginBatchNo, @Param("endBatchNo") String endBatchNo) throws HsException;
	
	// 分配明细
	List<AllocDetail> queryPointAllocPage(@Param("hsResNo") String hsResNo, @Param("batchNo") String batchNo, @Param("custType") int custType) throws HsException;

	// 查询积分记录流水
	List<PointRecordResult> pointRegisterRecordPage(@Param("pointRecord") QueryPointRecord pointRecord) throws HsException;
		
	/**
	 * POS定金单查询
	 * @param querySingle 查询条件
	 * @return POS定金单
	 * @throws HsException
	 */
	public List<PosEarnest> searchPosEarnestPage(QuerySingle querySingle) throws HsException;
	
	/**
	 * 根据企业custId查询是否有未结单的交易
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	public int queryIsSettleByEntCustId(@Param("entCustId")String entCustId) throws HsException;

	/**
     * 根据custId查询是否有未结算的预付定金的交易
     * @param custId
     * @return
     * @throws HsException
     */
    public int queryPrePayByCustId(@Param("custId")String custId) throws SQLException;


	/**
	 * 积分记录(给平板上用，正常单，退货单，撤单单)
	 * @param  queryPage 参数实体
	 * @param  tableName 表名
	 * @return PointRecordResult 还回对象
	 * @throws HsException
	 */
	List<QueryPageResult> queryPointNBCPage(@Param("queryPage")QueryPage queryPage, @Param("tableName") String tableName) throws HsException;

}
