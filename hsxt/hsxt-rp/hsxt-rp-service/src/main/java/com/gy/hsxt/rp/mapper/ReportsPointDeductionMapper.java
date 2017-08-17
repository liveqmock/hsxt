/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.rp.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.rp.bean.ReportsPointDeduction;

/** 
 * 消费积分扣除统计查询
 * @Package: com.gy.hsxt.rp.mapper  
 * @ClassName: ReportsPointDeductionMapper 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-6 上午10:50:25 
 * @version V1.0 
 

 */
public interface ReportsPointDeductionMapper {

	/**
	 * 生成消费积分扣除每日统计数据
	 * @throws SQLException
	 */
	public void generatePointDeduction(Map<String,Object> pointDeductionMap) throws SQLException;
	
	/**
	 * 消费积分扣除统计查询
	 * @return
	 * @throws SQLException
	 */
	public List<ReportsPointDeduction> searchReportsPointDeductionListPage(ReportsPointDeduction reportsPointDeduction) throws SQLException;
	
	/**
	 * 消费积分扣除统计汇总查询
	 * @param reportsPointDeduction
	 * @return
	 * @throws SQLException
	 */
	public ReportsPointDeduction searchReportsPointDeductionSum(ReportsPointDeduction reportsPointDeduction) throws SQLException;
	
	/**
	 * 消费积分扣除终端统计查询
	 * @return
	 * @throws SQLException
	 */
	public List<ReportsPointDeduction> searchReportsPointDeductionByChannelListPage(ReportsPointDeduction reportsPointDeduction) throws SQLException;
	
	/**
	 * 消费积分扣除终端统计汇总查询
	 * @return
	 * @throws SQLException
	 */
	public ReportsPointDeduction searchReportsPointDeductionByChannelSum(ReportsPointDeduction reportsPointDeduction) throws SQLException;
	
	
	/**
	 * 消费积分扣除操作员统计查询
	 * @return
	 * @throws SQLException
	 */
	public List<ReportsPointDeduction> searchReportsPointDeductionByOperListPage(ReportsPointDeduction reportsPointDeduction) throws SQLException;
	
	/**
	 * 消费积分扣除操作员统计汇总查询
	 * @return
	 * @throws SQLException
	 */
	public ReportsPointDeduction searchReportsPointDeductionByOperSum(ReportsPointDeduction reportsPointDeduction) throws SQLException;
	
}
