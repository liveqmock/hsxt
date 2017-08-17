package com.gy.hsxt.ws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ws.bean.AccumulativePoint;

/**
 * 累计积分统计 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: AccumulativePointMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 上午10:15:42
 * @version V1.0
 */
public interface AccumulativePointMapper {
	/**
	 * 清空临时数据
	 * 
	 * @return
	 */
	int clearTempData(String accountingDate);

	/**
	 * 查询已存在累计积分的客户号
	 * 
	 * @param custIds
	 * @return
	 */
	List<String> queryExistCustId(List<String> custIds);

	/**
	 * 批处理数据插入临时表
	 * 
	 * @param list
	 *            列表数据
	 * @return
	 */
	int batchInsertPoint(List<AccumulativePoint> list);

	/**
	 * 调用统计累计积分的 存储过程
	 */
	void countAccumulativePoint(@Param("executeDate")String executeDate);

}