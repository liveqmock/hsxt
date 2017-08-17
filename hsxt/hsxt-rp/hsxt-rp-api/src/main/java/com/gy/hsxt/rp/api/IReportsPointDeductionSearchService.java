/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.api;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsPointDeduction;

/**
 * 
 * 消费积分扣除统计接口
 * @Package: com.gy.hsxt.rp.api  
 * @ClassName: IReportsBankrollReserveService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-12-28 下午6:14:32 
 * @version V1.0
 */
public interface IReportsPointDeductionSearchService {
	
	/**
     * 消费积分扣除统计查询
     * @param reportsPointDeduction 查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return 消费积分扣除统计信息
     * @throws HsException
     */
    public PageData<ReportsPointDeduction> searchReportsPointDeduction(ReportsPointDeduction reportsPointDeduction,  Page page) throws HsException;
    
    /**
     * 消费积分扣除终端统计查询
     * @param reportsPointDeduction 查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return 消费积分扣除终端统计信息
     * @throws HsException
     */
    public PageData<ReportsPointDeduction> searchReportsPointDeductionByChannel(ReportsPointDeduction reportsPointDeduction,  Page page) throws HsException;
    
    /**
     * 消费积分扣除操作员统计查询
     * @param reportsPointDeduction 查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return 消费积分扣除操作员统计信息
     * @throws HsException
     */
    public PageData<ReportsPointDeduction> searchReportsPointDeductionByOper(ReportsPointDeduction reportsPointDeduction,  Page page) throws HsException;
    
    /**
     * 消费积分扣除统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除统计汇总信息
     * @throws HsException
     */
    public ReportsPointDeduction searchReportsPointDeductionSum(ReportsPointDeduction reportsPointDeduction) throws HsException;
    
    /**
     * 消费积分扣除终端统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除终端统计汇总信息
     * @throws HsException
     */
    public ReportsPointDeduction searchReportsPointDeductionByChannelSum(ReportsPointDeduction reportsPointDeduction) throws HsException;
    
    /**
     * 消费积分扣除操作员统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除操作员统计汇总信息
     * @throws HsException
     */
    public ReportsPointDeduction searchReportsPointDeductionByOperSum(ReportsPointDeduction reportsPointDeduction) throws HsException;

}
