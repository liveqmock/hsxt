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
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;
import com.gy.hsxt.rp.bean.ReportsWelfareScore;

/** 
 * 系统资源信息查询
 * @Package: com.gy.hsxt.rp.mapper  
 * @ClassName: ReportsSystemResourceMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-1-6 上午10:50:25 
 * @version V1.0 
 

 */
public interface ReportsSystemResourceMapper {

    /**
     * 企业资源查询
     * @param rpEnterprisResource 企业查询条件
     * @return List<ReportsEnterprisResource> 企业资源数据集合
     * @throws HsException
     */
    public List<ReportsEnterprisResource> searchEnterprisResource(ReportsEnterprisResource rpEnterprisResource) throws SQLException;
    
    /**
     * 企业资源导出查询
     * @param rpEnterprisResource 企业查询条件
     * @return List<ReportsEnterprisResource> 企业资源数据集合
     * @throws HsException
     */
    public List<ReportsEnterprisResource> searchEntResourceExport(ReportsEnterprisResource rpEnterprisResource) throws SQLException;
    
    /**
     * 消费者资源查询
     * @param rpCardholderResource 消费者查询条件
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public List<ReportsCardholderResource> searchCardholderResource(ReportsCardholderResource rpCardholderResource) throws SQLException;
    
    /**
     * 消费者资源报表导出查询
     * @param rpCardholderResource 消费者查询条件
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public List<ReportsCardholderResource> searchCardholderResourceExport(ReportsCardholderResource rpCardholderResource) throws SQLException;
    
    /**
     * 消费者资源报表导出查询总数
     * @param rpCardholderResource 消费者查询条件
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public int searchCardholderResourceCount(ReportsCardholderResource rpCardholderResource) throws SQLException;
    
    /**
     * 资源统计查询
     * @param rpPersonalResource 资源统计查询条件
     * @return List<ReportsPersonalResource> 资源统计数据集合
     * @throws HsException
     */
    public List<ReportsResourceStatistics> searchResourceStatistics(ReportsResourceStatistics rpResourceStatistics) throws SQLException;
    
    /**
     * 企业资源查询
     * @param rpEnterprisResource 企业查询条件
     * @return List<ReportsEnterprisResource> 企业资源数据集合
     * @throws HsException
     */
    public List<ReportsEnterprisResource> searchEnterprisResourceListPage(ReportsEnterprisResource rpEnterprisResource) throws SQLException;
    
    /**
     * 生成系统资源数据
     * @throws SQLException
     */
    public void generateResourceStatistics() throws SQLException;
    
    /**
     * 消费者资源查询
     * @param rpCardholderResource 消费者查询条件
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public List<ReportsCardholderResource> searchCardholderResourceListPage(ReportsCardholderResource rpCardholderResource) throws SQLException;
    
    /**
     * 消费者资源查询(查询的RP的消费者资源表)
     * @param rpCardholderResource 消费者查询条件
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException
     */
    public List<ReportsCardholderResource> searchPerResListPage(ReportsCardholderResource rpCardholderResource) throws SQLException;
    
    /**
     * 资源统计查询
     * @param rpPersonalResource 资源统计查询条件
     * @return List<ReportsPersonalResource> 资源统计数据集合
     * @throws HsException
     */
    public List<ReportsResourceStatistics> searchResourceStatisticsListPage(ReportsResourceStatistics rpResourceStatistics) throws SQLException;
    
    /**
     * 日积分福利数据条数
     * @param welfareScoreMap 日积分服务数据条数查询条件
     * @return 数据条数
     * @throws SQLException
     */
    public int searchWelfareScoreCount(Map<String, Object> welfareScoreMap) throws SQLException;
    
    
    /**
     * 日积分福利数据查询
     * @param welfareScoreMap 日积分服务数据查询条件
     * @return 日积分福利数据
     * @throws SQLException
     */
    public List<ReportsWelfareScore> searchWelfareScoreListPage(Map<String, Object> welfareScoreMap) throws SQLException;
}
