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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsPointDeductionSearchService;
import com.gy.hsxt.rp.bean.ReportsPointDeduction;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.mapper.ReportsPointDeductionMapper;

/** 
 * 消费积分扣除统计查询
 * @Package: com.gy.hsxt.rp.service  
 * @ClassName: ReportsSystemResourceService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-6 上午10:51:26 
 * @version V1.0 
 */

@Service
public class ReportsPointDeductionSearchService implements IReportsPointDeductionSearchService {

    
    @Autowired
    private ReportsPointDeductionMapper reportsPointDeductionMapper;
    
    /**
     * 消费积分扣除统计查询
     * @param reportsPointDeduction 查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return 消费积分扣除统计信息
     * @throws HsException
     */
    @Override
    public PageData<ReportsPointDeduction> searchReportsPointDeduction(ReportsPointDeduction reportsPointDeduction,  Page page) throws HsException{
    	//分页信息
    	PageContext.setPage(page);
    	
    	String custId = reportsPointDeduction.getCustId();
    	if (custId == null || "".equals(custId))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeduction", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "custId:客户全局编号为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "custId:客户全局编号为空");
    	}
    	
    	// 开始时间
    	String beginDate = reportsPointDeduction.getBeginDate();
    	if (beginDate == null || "".equals(beginDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeduction", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "beginDate:开始时间为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
    	}
    	        
    	// 结束时间
    	String endDate = reportsPointDeduction.getEndDate();
    	if (endDate == null || "".equals(endDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeduction", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
    		throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
    	}
    	        
    	//验证开始、结束时间日期格式
    	validateDateFormat(beginDate, endDate);
    	
    	List<ReportsPointDeduction> reportsPointDeductionList = null;
		try {
			reportsPointDeductionList = reportsPointDeductionMapper.searchReportsPointDeductionListPage(reportsPointDeduction);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchReportsPointDeduction", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		PageData<ReportsPointDeduction> pageData = new PageData<ReportsPointDeduction>(page.getCount(), reportsPointDeductionList);
		return pageData;
    }
    
    /**
     * 消费积分扣除统计统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除统计汇总信息
     * @throws HsException
     */
    @Override
    public ReportsPointDeduction searchReportsPointDeductionSum(ReportsPointDeduction reportsPointDeduction) throws HsException{
    	
    	String custId = reportsPointDeduction.getCustId();
    	if (custId == null || "".equals(custId))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionSum", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "custId:客户全局编号为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "custId:客户全局编号为空");
    	}
    	
    	// 开始时间
    	String beginDate = reportsPointDeduction.getBeginDate();
    	if (beginDate == null || "".equals(beginDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionSum", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "beginDate:开始时间为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
    	}
    	        
    	// 结束时间
    	String endDate = reportsPointDeduction.getEndDate();
    	if (endDate == null || "".equals(endDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionSum", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
    		throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
    	}
    	        
    	//验证开始、结束时间日期格式
    	validateDateFormat(beginDate, endDate);
    	
    	ReportsPointDeduction reportsPointDeductionSum = null;
		try {
			reportsPointDeductionSum = reportsPointDeductionMapper.searchReportsPointDeductionSum(reportsPointDeduction);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchReportsPointDeductionSum", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		return reportsPointDeductionSum;
    }
    
    /**
     * 消费积分扣除终端统计查询
     * @param reportsPointDeduction 查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return 消费积分扣除终端统计信息
     * @throws HsException
     */
    @Override
    public PageData<ReportsPointDeduction> searchReportsPointDeductionByChannel(ReportsPointDeduction reportsPointDeduction,  Page page) throws HsException{
    	//分页信息
    	PageContext.setPage(page);
    	
    	String custId = reportsPointDeduction.getCustId();
    	if (custId == null || "".equals(custId))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByChannel", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "custId:客户全局编号为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "custId:客户全局编号为空");
    	}
    	
    	// 开始时间
    	String beginDate = reportsPointDeduction.getBeginDate();
    	if (beginDate == null || "".equals(beginDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByChannel", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "beginDate:开始时间为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
    	}
    	        
    	// 结束时间
    	String endDate = reportsPointDeduction.getEndDate();
    	if (endDate == null || "".equals(endDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByChannel", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
    		throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
    	}
    	        
    	//验证开始、结束时间日期格式
    	validateDateFormat(beginDate, endDate);
    	
    	List<ReportsPointDeduction> reportsPointDeductionList = null;
		try {
			reportsPointDeductionList = reportsPointDeductionMapper.searchReportsPointDeductionByChannelListPage(reportsPointDeduction);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchReportsPointDeductionByChannel", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		PageData<ReportsPointDeduction> pageData = new PageData<ReportsPointDeduction>(page.getCount(), reportsPointDeductionList);
		return pageData;
    }
    
    /**
     * 消费积分扣除终端统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除终端统计汇总信息
     * @throws HsException
     */
    @Override
    public ReportsPointDeduction searchReportsPointDeductionByChannelSum(ReportsPointDeduction reportsPointDeduction) throws HsException{
    	
    	String custId = reportsPointDeduction.getCustId();
    	if (custId == null || "".equals(custId))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByChannelSum", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "custId:客户全局编号为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "custId:客户全局编号为空");
    	}
    	
    	// 开始时间
    	String beginDate = reportsPointDeduction.getBeginDate();
    	if (beginDate == null || "".equals(beginDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByChannelSum", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "beginDate:开始时间为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
    	}
    	        
    	// 结束时间
    	String endDate = reportsPointDeduction.getEndDate();
    	if (endDate == null || "".equals(endDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByChannelSum", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
    		throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
    	}
    	        
    	//验证开始、结束时间日期格式
    	validateDateFormat(beginDate, endDate);
    	
    	ReportsPointDeduction reportsPointDeductionSum = null;
		try {
			reportsPointDeductionSum = reportsPointDeductionMapper.searchReportsPointDeductionByChannelSum(reportsPointDeduction);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchReportsPointDeductionByChannelSum", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		return reportsPointDeductionSum;
    }
    
    
    /**
     * 消费积分扣除操作员统计查询
     * @param reportsPointDeduction 查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return 消费积分扣除操作员统计信息
     * @throws HsException
     */
    @Override
    public PageData<ReportsPointDeduction> searchReportsPointDeductionByOper(ReportsPointDeduction reportsPointDeduction,  Page page) throws HsException{
    	//分页信息
    	PageContext.setPage(page);
    	
    	String custId = reportsPointDeduction.getCustId();
    	if (custId == null || "".equals(custId))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByOper", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "custId:客户全局编号为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "custId:客户全局编号为空");
    	}
    	
    	// 开始时间
    	String beginDate = reportsPointDeduction.getBeginDate();
    	if (beginDate == null || "".equals(beginDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByOper", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "beginDate:开始时间为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
    	}
    	        
    	// 结束时间
    	String endDate = reportsPointDeduction.getEndDate();
    	if (endDate == null || "".equals(endDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByOper", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
    		throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
    	}
    	        
    	//验证开始、结束时间日期格式
    	validateDateFormat(beginDate, endDate);
    	
    	List<ReportsPointDeduction> reportsPointDeductionList = null;
		try {
			reportsPointDeductionList = reportsPointDeductionMapper.searchReportsPointDeductionByOperListPage(reportsPointDeduction);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchReportsPointDeductionByOper", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		PageData<ReportsPointDeduction> pageData = new PageData<ReportsPointDeduction>(page.getCount(), reportsPointDeductionList);
		return pageData;
    }
    
    /**
     * 消费积分扣除操作员统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除操作员统计汇总信息
     * @throws HsException
     */
    @Override
    public ReportsPointDeduction searchReportsPointDeductionByOperSum(ReportsPointDeduction reportsPointDeduction) throws HsException{
    	
    	String custId = reportsPointDeduction.getCustId();
    	if (custId == null || "".equals(custId))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByOperSum", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "custId:客户全局编号为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "custId:客户全局编号为空");
    	}
    	
    	// 开始时间
    	String beginDate = reportsPointDeduction.getBeginDate();
    	if (beginDate == null || "".equals(beginDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByOperSum", RespCode.RP_PARAMETER_NULL.getCode()
    				+ "beginDate:开始时间为空");
    	    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
    	}
    	        
    	// 结束时间
    	String endDate = reportsPointDeduction.getEndDate();
    	if (endDate == null || "".equals(endDate))
    	{
    		SystemLog.debug("HSXT_RP", "方法：searchReportsPointDeductionByOperSum", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
    		throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
    	}
    	        
    	//验证开始、结束时间日期格式
    	validateDateFormat(beginDate, endDate);
    	
    	ReportsPointDeduction reportsPointDeductionSum = null;
		try {
			reportsPointDeductionSum = reportsPointDeductionMapper.searchReportsPointDeductionByOperSum(reportsPointDeduction);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchReportsPointDeductionByOperSum", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		return reportsPointDeductionSum;
    }
    
    /**
     * 验证开始、结束时间日期格式，开始时间时分秒为00:00:00,结束时间时分秒为23:59:59
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return Map<String,String> 封装时间格式后的开始和结束时间
     * @throws HsException
     *             异常处理
     */
    public Map<String, Timestamp> validateDateFormat(String beginDate, String endDate) throws HsException {
        Map<String, Timestamp> dateMap = new HashMap<String, Timestamp>();

        // 格式化日期并转换日期格式String-->TimeStamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 开始时间
        if (beginDate != null && !"".equals(beginDate))
        {
            try
            {
                Date date = sdf.parse(beginDate);
                beginDate = sdf.format(date) + " 00:00:00";
                dateMap.put("beginDate", Timestamp.valueOf(beginDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + beginDate + " = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), beginDate + " = " + beginDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }

        // 结束时间
        if (endDate != null && !"".equals(endDate))
        {
            try
            {
                Date date = sdf.parse(endDate);
                endDate = sdf.format(date) + " 23:59:59";
                dateMap.put("endDate", Timestamp.valueOf(endDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + endDate + " = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), endDate + " = " + endDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }
        return dateMap;
    }
    
}
