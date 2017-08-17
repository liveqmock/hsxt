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

package com.gy.hsxt.rp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.common.bean.RpTools;
import com.gy.hsxt.rp.mapper.ReportsSystemResourceMapper;

/** 
 * 
 * @Package: com.gy.hsxt.rp.service  
 * @ClassName: ReportsSystemResourceService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-1-6 上午10:51:26 
 * @version V1.0 
 

 */

@Service
public class ReportsSystemResourceService implements IReportsSystemResourceService{

    
    @Autowired
    private ReportsSystemResourceMapper reportsSystemResourceMapper;
    
    /** 
     * 企业资源查询
     * @param rpEnterprisResource 企业资源数据对象
     * @return List<ReportsEnterprisResource> 企业资源数据集合
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchEnterprisResource(com.gy.hsxt.rp.bean.ReportsEnterprisResource) 
     */
    @Override
    public List<ReportsEnterprisResource> searchEnterprisResource(ReportsEnterprisResource rpEnterprisResource)
            throws HsException {
        
        List<ReportsEnterprisResource> list = null;
        try
        {
            if (rpEnterprisResource == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchEnterprisResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpEnterprisResource:企业资源数据对象为空");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpEnterprisResource:企业资源数据对象为空");
            }
            String beginDate = rpEnterprisResource.getOpenDateBegin();// 开始时间
            String endDate = rpEnterprisResource.getOpenDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpEnterprisResource.setOpenDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpEnterprisResource.setOpenDateEndTim(dateMap.get("endDate"));
            }
            
            list = reportsSystemResourceMapper.searchEnterprisResource(rpEnterprisResource);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

    /**  
     * 消费者资源查询
     * @param rpCardholderResource 消费者资源数据对象
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchCardholderResource(com.gy.hsxt.rp.bean.ReportsCardholderResource) 
     */
    @Override
    public List<ReportsCardholderResource> searchCardholderResource(ReportsCardholderResource rpCardholderResource)
            throws HsException {
        
        List<ReportsCardholderResource> list = null;
        try
        {
            if (rpCardholderResource == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchCardholderResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpCardholderResource:消费者资源数据对象");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpCardholderResource:消费者资源数据对象");
            }
            String beginDate = rpCardholderResource.getRealnameAuthDateBegin();// 开始时间
            String endDate = rpCardholderResource.getRealnameAuthDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateEndTim(dateMap.get("endDate"));
            }
            list = reportsSystemResourceMapper.searchCardholderResource(rpCardholderResource);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchCardholderResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

    /**  
     * 资源统计查询
     * @param rpResourceStatistics 资源统计数据对象
     * @return List<ReportsResourceStatistics> 资源统计数据集合
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchResourceStatistics(com.gy.hsxt.rp.bean.ReportsResourceStatistics) 
     */
    @Override
    public List<ReportsResourceStatistics> searchResourceStatistics(ReportsResourceStatistics rpResourceStatistics)
            throws HsException {
        
        List<ReportsResourceStatistics> list = null;
        try
        {
            if (rpResourceStatistics == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchCardholderResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpResourceStatistics:消费者资源数据对象");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpResourceStatistics:消费者资源数据对象");
            }
            String beginDate = rpResourceStatistics.getOpenDateBegin();// 开始时间
            String endDate = rpResourceStatistics.getOpenDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpResourceStatistics.setOpenDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpResourceStatistics.setOpenDateEndTim(dateMap.get("endDate"));
            }
            list = reportsSystemResourceMapper.searchResourceStatistics(rpResourceStatistics);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchCardholderResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }
    
    
    
    
    /** 企业资源查询
     * @param rpEnterprisResource
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchEnterprisResourcePage(com.gy.hsxt.rp.bean.ReportsEnterprisResource, com.gy.hsxt.common.bean.Page) 
     */
    @Override
    public PageData<ReportsEnterprisResource> searchEnterprisResourcePage(ReportsEnterprisResource rpEnterprisResource,
            Page page) throws HsException {
        
        PageData<ReportsEnterprisResource> pageData = null;
        try
        {
            PageContext.setPage(page);
            if (rpEnterprisResource == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchEnterprisResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpEnterprisResource:企业资源数据对象为空");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpEnterprisResource:企业资源数据对象为空");
            }
            String beginDate = rpEnterprisResource.getOpenDateBegin();// 开始时间
            String endDate = rpEnterprisResource.getOpenDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpEnterprisResource.setOpenDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpEnterprisResource.setOpenDateEndTim(dateMap.get("endDate"));
            }
            
            List<ReportsEnterprisResource> list = reportsSystemResourceMapper.searchEnterprisResourceListPage(rpEnterprisResource);
            pageData = new PageData<>(page.getCount(), list);
            
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageData;
    }

    /**  
     * @param rpCardholderResource
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchCardholderResourcePage(com.gy.hsxt.rp.bean.ReportsCardholderResource, com.gy.hsxt.common.bean.Page) 
     */
    @Override
    public PageData<ReportsCardholderResource> searchCardholderResourcePage(
            ReportsCardholderResource rpCardholderResource, Page page) throws HsException {
        
        PageData<ReportsCardholderResource> pageData = null;
        try
        {
            PageContext.setPage(page);
            if (rpCardholderResource == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchCardholderResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpCardholderResource:消费者资源数据对象");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpCardholderResource:消费者资源数据对象");
            }
            String beginDate = rpCardholderResource.getRealnameAuthDateBegin();// 开始时间
            String endDate = rpCardholderResource.getRealnameAuthDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateEndTim(dateMap.get("endDate"));
            }
            List<ReportsCardholderResource> list = reportsSystemResourceMapper.searchCardholderResourceListPage(rpCardholderResource);
            pageData = new PageData<>(page.getCount(), list);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchCardholderResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageData;
    }

    /**  
     * @param rpResourceStatistics
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchResourceStatisticsPage(com.gy.hsxt.rp.bean.ReportsResourceStatistics, com.gy.hsxt.common.bean.Page) 
     */
    @Override
    public PageData<ReportsResourceStatistics> searchResourceStatisticsPage(
            ReportsResourceStatistics rpResourceStatistics, Page page) throws HsException {
        
        PageData<ReportsResourceStatistics> pageData = null;
        try
        {
            PageContext.setPage(page);
            if (rpResourceStatistics == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchCardholderResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpResourceStatistics:消费者资源数据对象");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpResourceStatistics:消费者资源数据对象");
            }
            String beginDate = rpResourceStatistics.getOpenDateBegin();// 开始时间
            String endDate = rpResourceStatistics.getOpenDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpResourceStatistics.setOpenDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpResourceStatistics.setOpenDateEndTim(dateMap.get("endDate"));
            }
            List<ReportsResourceStatistics> list = reportsSystemResourceMapper.searchResourceStatisticsListPage(rpResourceStatistics);
            pageData = new PageData<>(page.getCount(), list);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchCardholderResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageData;
    }

    /**  
     * 消费者资源查询(查询的是RP表中的消费者资源表)
     * @param rpCardholderResource
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchPerResListPage(com.gy.hsxt.rp.bean.ReportsCardholderResource, com.gy.hsxt.common.bean.Page) 
     */
    @Override
    public PageData<ReportsCardholderResource> searchPerResListPage(ReportsCardholderResource rpCardholderResource,
            Page page) throws HsException {
       
        PageData<ReportsCardholderResource> pageData = null;
        try
        {
            PageContext.setPage(page);
            if (rpCardholderResource == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchPerResListPage", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpCardholderResource:消费者资源数据对象");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpCardholderResource:消费者资源数据对象");
            }
            String beginDate = rpCardholderResource.getRealnameAuthDateBegin();// 开始时间
            String endDate = rpCardholderResource.getRealnameAuthDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = RpTools.validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateEndTim(dateMap.get("endDate"));
            }
            List<ReportsCardholderResource> list = reportsSystemResourceMapper.searchPerResListPage(rpCardholderResource);
            pageData = new PageData<>(page.getCount(), list);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_BP", "方法：searchPerResListPage", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageData;
    }

}
