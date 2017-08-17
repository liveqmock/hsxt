package com.gy.hsxt.es.querys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.bean.*;
import com.gy.hsxt.es.bean.PointRecordResult;
import com.gy.hsxt.es.bean.QueryPointRecord;

/**
 * @description 查询对账接口
 * @author chenhz
 * @createDate 2015-7-27 下午3:17:43
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public interface QueryMapper {

	
	// 单笔交易查询
	public QueryResult singleQuery(@Param("querySingle")QuerySingle querySingle) throws HsException;
	
	// 查询积分/互生币明细
	/*public QueryDetailResult queryDetail(@Param("queryDetail")QueryDetail queryDetail, @Param("accountType")String accountType) throws HsException;*/

	// 分配汇总
	public QueryDetailResult queryAllocSum(@Param("queryDetail")QueryDetail queryDetail, @Param("accountType")String accountType) throws HsException;

	// 分配其他(扣除税金)
	public QueryDetailResult queryAllocTaxAmount(@Param("queryDetail")QueryDetail queryDetail, @Param("accountType")String accountType) throws HsException;

	// 分配明细
	public List<AllocDetail> queryEntryPage(@Param("queryDetail")QueryDetail queryDetail, @Param("accountType")String accountType) throws HsException;

	// 分配明细撤单
	public QueryDetailResult queryAllocBack(@Param("queryDetail")QueryDetail queryDetail, @Param("accountType")String accountType) throws HsException;


	//	根据流水号查询积分详情
	public QueryResult queryPointDetails(@Param("transNo")String transNo) throws HsException;
	
	//	根据流水号查询撤单详情
	public QueryResult queryCancelDetails(@Param("transNo")String transNo) throws HsException;

	//	根据流水号查询退货详情
	public QueryResult queryBackDetails(@Param("transNo")String transNo) throws HsException;
	
	//	根据流水号查询企业收入详情
	public ProceedsResult proceedsDetail(@Param("transNo")String transNo) throws HsException;

	//	根据流水号查询企业收入详情汇总
	public ProceedsResult proceedsAllDetail(Proceeds proceeds) throws HsException;

	//	消费积分纳税详单
	public TaxResult queryDayTax(@Param("transNo")String transNo) throws HsException;


	//	商业服务费纳税详单
	public TaxResult queryCscTax(@Param("transNo")String transNo) throws HsException;
	
	// 查询积分记录流水
	public List<PointRecordResult> pointRegisterRecordPage(@Param("pointRecord")QueryPointRecord pointRecord) throws HsException;
	
	
}
