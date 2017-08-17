/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;

/**
 * 
 * 系统资源接口
 * @Package: com.gy.hsxt.rp.api  
 * @ClassName: IReportsBankrollReserveService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-12-28 下午6:14:32 
 * @version V1.0
 */
public interface IReportsSystemResourceService {
	
	/**
	 * 企业资源查询
	 * @param rpEnterprisResource 企业查询条件
	 * @return List<ReportsEnterprisResource> 企业资源数据集合
	 * @throws HsException
	 */
	public List<ReportsEnterprisResource> searchEnterprisResource(ReportsEnterprisResource rpEnterprisResource) throws HsException;
	
	/**
	 * 消费者资源查询
	 * @param rpCardholderResource 消费者查询条件
	 * @return List<ReportsCardholderResource> 消费者资源数据集合
	 * @throws HsException
	 */
	public List<ReportsCardholderResource> searchCardholderResource(ReportsCardholderResource rpCardholderResource) throws HsException;
	
	/**
	 * 资源统计查询
	 * @param rpPersonalResource 资源统计查询条件
	 * @return List<ReportsPersonalResource> 资源统计数据集合
	 * @throws HsException
	 */
	public List<ReportsResourceStatistics> searchResourceStatistics(ReportsResourceStatistics rpResourceStatistics) throws HsException;
	
	/**
     * 企业资源查询
     * @param rpEnterprisResource 企业查询条件
     * @param page 分页信息
     * @return PageData<ReportsEnterprisResource> 企业资源数据集合
     * @throws HsException
     */
    public PageData<ReportsEnterprisResource> searchEnterprisResourcePage(ReportsEnterprisResource rpEnterprisResource,Page page) throws HsException;
    
    /**
     * 消费者资源查询
     * @param rpCardholderResource 消费者查询条件
     * @param page 分页信息
     * @return PageData<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public PageData<ReportsCardholderResource> searchCardholderResourcePage(ReportsCardholderResource rpCardholderResource,Page page) throws HsException;
    
    /**
     * 消费者资源查询（查询的是RP的消费者资源表）
     * @param rpCardholderResource 消费者查询条件
     * @param page 分页信息
     * @return PageData<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public PageData<ReportsCardholderResource> searchPerResListPage(ReportsCardholderResource rpCardholderResource,Page page) throws HsException;
    
    
    /**
     * 资源统计查询
     * @param rpPersonalResource 资源统计查询条件
     * @param page 分页信息
     * @return PageData<ReportsPersonalResource> 资源统计数据集合
     * @throws HsException
     */
    public PageData<ReportsResourceStatistics> searchResourceStatisticsPage(ReportsResourceStatistics rpResourceStatistics,Page page) throws HsException;
    
}
