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

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsInvoiceValueStatistics;

/** 
 * 发票金额统计查询
 * @Package: com.gy.hsxt.rp.mapper  
 * @ClassName: ReportsInvoiceValueStatisticsMapper 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-2-18 上午10:50:25 
 * @version V1.0 
 

 */
public interface ReportsInvoiceValueStatisticsMapper {

	/**
	 * 企业发票金额数据数量查询
	 * @param welfareScoreMap 企业发票金额数据数量查询条件
	 * @return 企业发票金额数据数量
	 * @throws HsException
	 */
    public int searchEntInvoiceValueStatisticsCount(Map<String, Object> welfareScoreMap) throws SQLException;
	
	/**
	 * 企业发票金额查询
	 * @param reportsInvoiceValueStatisticsInfo 企业发票金额查询条件
	 * @return List<ReportsInvoiceValueStatistics> 企业发票金额数据集合
	 * @throws HsException
	 */
    public List<ReportsInvoiceValueStatistics> searchEntInvoiceValueStatisticsListPage(Map<String, Object> welfareScoreMap) throws SQLException;
    
}
