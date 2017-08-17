package com.gy.hsxt.ps.settlement.mapper;

import java.util.List;

import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.settlement.bean.BatchSettle;
import com.gy.hsxt.ps.settlement.bean.BatchUpload;

import com.gy.hsxt.common.exception.HsException;
import org.apache.ibatis.annotations.Param;

/**
 * @description 查询接口
 * @author chenhz
 * @createDate 2015-7-27 下午3:17:43
 * @Company 深圳市归一科技研发有限公司
 * @version v3.0
 */
public interface PosSettleMapper {

	// 查询积分、撤单、退货总数、笔数
	BatchSettle querySettle(BatSettle batSettle) throws HsException;

	// 批结算结果记录
	void insertSettle(@Param("batSettle")BatSettle batSettle,@Param("bs")BatchSettle bs) throws HsException;

	// 查询同批次、设备等消费订单明细
	List<BatchUpload> queryUpload(@Param("inSQL")String inSQL,@Param("entResNo")String entResNo);

	// 批量插入明细对比结果
	void insertUpload(List<BatchUpload> batUpload) throws HsException;
	
}
