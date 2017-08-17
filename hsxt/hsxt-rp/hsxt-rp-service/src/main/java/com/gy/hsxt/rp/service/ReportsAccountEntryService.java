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
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.api.IReportsAccountEntryService;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.bean.ReportsAccountEntryInfo;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.mapper.ReportsAccountEntryMapper;

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
public class ReportsAccountEntryService implements IReportsAccountEntryService{

	/**
	 * 账户流水Mapper
	 */
	@Autowired
	ReportsAccountEntryMapper reportsAccountEntryMapper;
	
	/**
	 * 企业账户流水查询
	 * @param rpEnterprisResource 企业查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return PageData<ReportsAccountEntry> 企业账户流水数据集合
	 * @throws HsException
	 */
	@Override
	public PageData<ReportsAccountEntry> searchEntAccountEntry(
			ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException {
		//分页信息
		PageContext.setPage(page);
		
		// 开始时间
		String beginDate = reportsAccountEntryInfo.getBeginDate();
        if (beginDate == null || "".equals(beginDate))
        {
            SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntry", RespCode.RP_PARAMETER_NULL.getCode()
                    + "beginDate:开始时间为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
        }
        
        // 结束时间
        String endDate = reportsAccountEntryInfo.getEndDate();
        if (endDate == null || "".equals(endDate))
        {
            SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntry", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
        }
        
        String[] accTypes = reportsAccountEntryInfo.getAccTypes();
        if (accTypes == null || accTypes.length == 0)
        {
            SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntry", RespCode.RP_PARAMETER_NULL.getCode() + "accTypes:账户类型为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "accTypes:账户类型为空");
        }
        
        //验证开始、结束时间日期格式
        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
        reportsAccountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
        reportsAccountEntryInfo.setEndDateTim(dateMap.get("endDate"));
		
		
		String accType = "";
    	for(int i=0;i<accTypes.length;i++){
    		 accType = accType+"'"+accTypes[i]+"'";
    		 if(i != accTypes.length - 1)
    		 {
    			 accType = accType + ", ";
    		 }
    	}
    	reportsAccountEntryInfo.setAccType(accType);
    	List<ReportsAccountEntry> accountEntryeList = null;
		try {
			accountEntryeList = reportsAccountEntryMapper.searchEntAccountEntryListPage(reportsAccountEntryInfo);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		PageData<ReportsAccountEntry> pageData = new PageData<ReportsAccountEntry>(page.getCount(), accountEntryeList);
		return pageData;
	}
	
	/**
	 * 企业分红账户流水查询
	 * @param rpEnterprisResource 企业查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return PageData<ReportsAccountEntry> 企业分红流水数据集合
	 * @throws HsException
	 */
	@Override
	public PageData<ReportsAccountEntry> searchEntAccountEntryDividend(
			ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException {
		//分页信息
		PageContext.setPage(page);
		
		// 开始时间
		String beginDate = reportsAccountEntryInfo.getBeginDate();
        if (beginDate == null || "".equals(beginDate))
        {
            SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntryDividend", RespCode.RP_PARAMETER_NULL.getCode()
                    + "beginDate:开始时间为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
        }
        
        // 结束时间
        String endDate = reportsAccountEntryInfo.getEndDate();
        if (endDate == null || "".equals(endDate))
        {
            SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntryDividend", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
        }
        
        //交易类型
        String transType = reportsAccountEntryInfo.getTransType();
        if(transType == null || "".equals(transType))
        {
        	SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntryDividend", RespCode.RP_PARAMETER_NULL.getCode() + "transType:交易类型为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "transType:交易类型为空");
        }
        else if(!TransType.C_INVEST_BONUS.getCode().equals(transType))
        {
        	SystemLog.debug("HSXT_RP", "方法：searchEntAccountEntryDividend", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                    + "transType = " + transType + " ,交易类型错误，正确交易类型为："+TransType.C_INVEST_BONUS.getCode());
            throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "transType = " + transType
                    + " ,交易类型错误，正确交易类型为："+TransType.C_INVEST_BONUS.getCode());
        }
        
        //验证开始、结束时间日期格式
        Map<String, Timestamp> dateMap = validateYearFormat(beginDate, endDate);
        reportsAccountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
        reportsAccountEntryInfo.setEndDateTim(dateMap.get("endDate"));
		
    	List<ReportsAccountEntry> accountEntryeList = null;
		try {
			accountEntryeList = reportsAccountEntryMapper.searchEntAccountEntryDividendListPage(reportsAccountEntryInfo);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		PageData<ReportsAccountEntry> pageData = new PageData<ReportsAccountEntry>(page.getCount(), accountEntryeList);
		return pageData;
	}

	/**
	 * 消费者账户流水查询
	 * @param rpCardholderResource 消费者查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return PageData<ReportsAccountEntry> 消费者账户流水数据集合
	 * @throws HsException
	 */
	@Override
	public PageData<ReportsAccountEntry> searchCarAccountEntry(
			ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException {
			//分页信息
				PageContext.setPage(page);
				
				// 开始时间
				String beginDate = reportsAccountEntryInfo.getBeginDate();
		        if (beginDate == null || "".equals(beginDate))
		        {
		            SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_PARAMETER_NULL.getCode()
		                    + "beginDate:开始时间为空");
		            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
		        }
		        
		        // 结束时间
		        String endDate = reportsAccountEntryInfo.getEndDate();
		        if (endDate == null || "".equals(endDate))
		        {
		            SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
		            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
		        }
		        
		        //验证开始、结束时间日期格式
		        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
		        reportsAccountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
		        reportsAccountEntryInfo.setEndDateTim(dateMap.get("endDate"));
				
				String[] accTypes = reportsAccountEntryInfo.getAccTypes();
				if(accTypes == null || accTypes.length == 0)
				{
					SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_PARAMETER_NULL.getCode() + "accTypes:账户类型为空");
		            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:账户类型为空");
				}
				String accType = "";
		    	for(int i=0;i<accTypes.length;i++){
		    		 accType = accType+"'"+accTypes[i]+"'";
		    		 if(i != accTypes.length - 1)
		    		 {
		    			 accType = accType + ", ";
		    		 }
		    	}
		    	
		    	reportsAccountEntryInfo.setAccType(accType);
		    	List<ReportsAccountEntry> accountEntryeList = null;
				try {
					accountEntryeList = reportsAccountEntryMapper.searchCarAccountEntryListPage(reportsAccountEntryInfo);
				} catch (Exception e) {
					SystemLog.error("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
		            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
				}
				
				PageData<ReportsAccountEntry> pageData = new PageData<ReportsAccountEntry>(page.getCount(), accountEntryeList);
				return pageData;
	}

	/**
	 * 消费者分红账户流水查询
	 * @param rpEnterprisResource 消费者查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return PageData<ReportsAccountEntry> 消费者分红流水数据集合
	 * @throws HsException
	 */
	@Override
	public PageData<ReportsAccountEntry> searchCarAccountEntryDividend(
			ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException {
		//分页信息
		PageContext.setPage(page);
		
		// 开始时间
		String beginDate = reportsAccountEntryInfo.getBeginDate();
        if (beginDate == null || "".equals(beginDate))
        {
            SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntryDividend", RespCode.RP_PARAMETER_NULL.getCode()
                    + "beginDate:开始时间为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
        }
        
        // 结束时间
        String endDate = reportsAccountEntryInfo.getEndDate();
        if (endDate == null || "".equals(endDate))
        {
            SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntryDividend", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
        }
        
        //交易类型
        String transType = reportsAccountEntryInfo.getTransType();
        if(transType == null || "".equals(transType))
        {
        	SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntryDividend", RespCode.RP_PARAMETER_NULL.getCode() + "transType:交易类型为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "transType:交易类型为空");
        }
        else if(!TransType.P_INVEST_BONUS.getCode().equals(transType))
        {
        	SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntryDividend", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                    + "transType = " + transType + " ,交易类型错误，正确交易类型为："+TransType.P_INVEST_BONUS.getCode());
            throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "transType = " + transType
                    + " ,交易类型错误，正确交易类型为："+TransType.P_INVEST_BONUS.getCode());
        }
        
        //验证开始、结束时间日期格式
        Map<String, Timestamp> dateMap = validateYearFormat(beginDate, endDate);
        reportsAccountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
        reportsAccountEntryInfo.setEndDateTim(dateMap.get("endDate"));
		
    	List<ReportsAccountEntry> accountEntryeList = null;
		try {
			accountEntryeList = reportsAccountEntryMapper.searchCarAccountEntryDividendListPage(reportsAccountEntryInfo);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：searchCarAccountEntryDividend", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		PageData<ReportsAccountEntry> pageData = new PageData<ReportsAccountEntry>(page.getCount(), accountEntryeList);
		return pageData;
	}
	
	/**
     * 消费者账户流水查询
     * @param rpCardholderResource 消费者查询条件
     * @param page 
     *              分页信息（curPage 当前页,pageSize 每页记录数）
     * @return PageData<ReportsAccountEntry> 消费者账户流水数据集合
     * @throws HsException
     */
    @Override
    public PageData<ReportsAccountEntry> searchNoCarAccountEntry(
            ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException {
                //分页信息
                PageContext.setPage(page);
                // 开始时间
                String beginDate = reportsAccountEntryInfo.getBeginDate();
                if(StringUtils.isBlank(beginDate))
                {
                    SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_PARAMETER_NULL.getCode()
                            + "beginDate:开始时间为空");
                    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
                }
                
                // 结束时间
                String endDate = reportsAccountEntryInfo.getEndDate();
                if (StringUtils.isBlank(beginDate))
                {
                    SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
                    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
                }
                
                //验证开始、结束时间日期格式
                Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
                reportsAccountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
                reportsAccountEntryInfo.setEndDateTim(dateMap.get("endDate"));
                
                String[] accTypes = reportsAccountEntryInfo.getAccTypes();
                if(StringUtils.isBlank(accTypes))
                {
                    SystemLog.debug("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_PARAMETER_NULL.getCode() + "accTypes:账户类型为空");
                    throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "endDate:账户类型为空");
                }
                String accType = "";
                for(int i=0;i<accTypes.length;i++){
                     accType = accType+"'"+accTypes[i]+"'";
                     if(i != accTypes.length - 1)
                     {
                         accType = accType + ", ";
                     }
                }
                
                reportsAccountEntryInfo.setAccType(accType);
                List<ReportsAccountEntry> accountEntryeList = null;
                try {
                    accountEntryeList = reportsAccountEntryMapper.searchNoCarAccountEntryListPage(reportsAccountEntryInfo);
                } catch (Exception e) {
                    SystemLog.error("HSXT_RP", "方法：searchCarAccountEntry", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
                    throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
                }
                PageData<ReportsAccountEntry> pageData = new PageData<ReportsAccountEntry>(page.getCount(), accountEntryeList);
                return pageData;
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
                SystemLog.debug("HSXT_RP", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + "beginDate = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "beginDate = " + beginDate
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
                SystemLog.debug("HSXT_RP", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + "endDate = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "endDate = " + endDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }
        return dateMap;
    }
    
    /**
     * 验证开始、结束年日期格式，开始年时分秒为XXXX-01-01 00:00:00,结束年时分秒为XXXX-12-31 23:59:59
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return Map<String,String> 封装时间格式后的开始和结束时间
     * @throws HsException
     *             异常处理
     */
    public Map<String, Timestamp> validateYearFormat(String beginDate, String endDate) throws HsException {
        Map<String, Timestamp> dateMap = new HashMap<String, Timestamp>();

        // 格式化日期并转换日期格式String-->TimeStamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        // 开始时间
        if (beginDate != null && !"".equals(beginDate))
        {
            try
            {
                Date date = sdf.parse(beginDate);
                beginDate = sdf.format(date) + "-01-01 00:00:00";
                dateMap.put("beginDate", Timestamp.valueOf(beginDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_RP", "方法：validateYearFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + "beginDate = " + beginDate + " ,年份格式错误，正确格式 yyyy");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "beginDate = " + beginDate
                        + " ,年份格式错误，正确格式 yyyy");
            }
        }

        // 结束时间
        if (endDate != null && !"".equals(endDate))
        {
            try
            {
                Date date = sdf.parse(endDate);
                endDate = sdf.format(date) + "-12-31 23:59:59";
                dateMap.put("endDate", Timestamp.valueOf(endDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_RP", "方法：validateYearFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + "endDate = " + endDate + " ,年份格式错误，正确格式 yyyy");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "endDate = " + endDate
                        + " ,年份格式错误，正确格式 yyyy");
            }
        }
        return dateMap;
    }

}
