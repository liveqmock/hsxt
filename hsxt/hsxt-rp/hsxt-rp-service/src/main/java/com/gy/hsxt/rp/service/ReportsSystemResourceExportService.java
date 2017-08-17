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

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.rp.api.IReportsSystemResourceExportService;
import com.gy.hsxt.rp.bean.ExcelExportData;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.mapper.ReportsSystemResourceMapper;
import com.gy.hsxt.rp.util.ExcelUtil;

/** 
 * 
 * @Package: com.gy.hsxt.rp.service  
 * @ClassName: ReportsSystemResourceService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-6 上午10:51:26 
 * @version V1.0 
 

 */

@Service
public class ReportsSystemResourceExportService implements IReportsSystemResourceExportService{

    
    @Autowired
    private ReportsSystemResourceMapper reportsSystemResourceMapper;
    
    /** 地区平台配送Service **/
    @Autowired
    private ILCSBaseDataService baseDataService;
    
    /**
     * 导出最大数量值
     */
    private final static int exportMaxRow = 30000;
    
    /** 
     * 托管企业资源导出
     * @param rpEnterprisResource 企业资源数据对象
     * @return List<ReportsEnterprisResource> 企业资源数据集合
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchEnterprisResource(com.gy.hsxt.rp.bean.ReportsEnterprisResource) 
     */
    @Override
    public String exportTrusEntResource(ReportsEnterprisResource rpEnterprisResource)
            throws HsException {
    	
    	String dirRoot = PropertyConfigurer.getProperty("dir.root");
    	
    	String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    	
    	String timeDate = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());
    	
    	String excelFileName = "trustEntResourceExport" + timeDate;
    	
    	String fileAddress = dirRoot + File.separator + "RP" + File.separator + "EXPORT" + File.separator + newDate + File.separator + excelFileName + ".xls";
    	
    	List<String[]> columNames = new ArrayList<String[]>();
        columNames.add(new String[] { "企业互生号", "企业名称", "企业地址", "联系人姓名", "联系人手机", "系统开启日期","启用资源类型", "企业认证状态", "参与积分状态", "已启用消费者数","开启系统类型" });

        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "hsResNo", "entCustName", "entRegAddr", "contactPerson", "contactPhone", "openDate","exportArtResType", "exportRealnameAuth", "exportBaseStatus", "enabledCardholderNumber","exportResFeeChargeMode" });

        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        try {
            this.validateEntResourceInfo(rpEnterprisResource);
            List list = reportsSystemResourceMapper.searchEntResourceExport(rpEnterprisResource);
            this.exportMaxRow(list);
			map.put("托管企业资源", list);
        }
        catch (Exception e)
	    {
        	SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
	    }
        
        
        ExcelExportData setInfo = new ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[] { "托管企业资源报表"});
        setInfo.setColumnNames(columNames);
        
        try {
			ExcelUtil.export2File(setInfo, fileAddress);
		} catch (Exception e) {
			SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
        return fileAddress;
    }
    
    
    /** 
     * 成员企业资源导出
     * @param rpEnterprisResource 企业资源数据对象
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchEnterprisResource(com.gy.hsxt.rp.bean.ReportsEnterprisResource) 
     */
    @Override
    public String exportMemberEntResource(ReportsEnterprisResource rpEnterprisResource)
            throws HsException {
    	
    	String dirRoot = PropertyConfigurer.getProperty("dir.root");
    	
    	String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    	
    	String timeDate = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());
    	
    	String excelFileName = "membEntResourceExport" + timeDate;
    	
    	String fileAddress = dirRoot + File.separator + "RP" + File.separator + "EXPORT" + File.separator + newDate + File.separator + excelFileName + ".xls";
    	
    	List<String[]> columNames = new ArrayList<String[]>();
        columNames.add(new String[] { "企业互生号", "企业名称", "企业地址", "联系人姓名", "联系人手机", "系统开启日期","启用资源类型", "企业认证状态", "企业状态","开启系统类型" });

        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "hsResNo", "entCustName", "entRegAddr", "contactPerson", "contactPhone", "openDate", "exportArtResType", "exportRealnameAuth", "exportBaseStatus","exportResFeeChargeMode"});

        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        try {
            this.validateEntResourceInfo(rpEnterprisResource);
            List list = reportsSystemResourceMapper.searchEntResourceExport(rpEnterprisResource);
            this.exportMaxRow(list);
			map.put("成员企业资源", list);
        }
        catch (Exception e)
	    {
        	SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
	    }
        
        
        ExcelExportData setInfo = new ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[] { "成员企业资源报表"});
        setInfo.setColumnNames(columNames);
        
        try {
			ExcelUtil.export2File(setInfo, fileAddress);
		} catch (Exception e) {
			SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
        
        return fileAddress;
    }
    
    
    /** 
     * 服务公司资源导出
     * @param rpEnterprisResource 服务公司资源数据对象
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchEnterprisResource(com.gy.hsxt.rp.bean.ReportsEnterprisResource) 
     */
    @Override
    public String exportServiceEntResource(ReportsEnterprisResource rpEnterprisResource)
            throws HsException {
    	
    	String dirRoot = PropertyConfigurer.getProperty("dir.root");
    	
    	String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    	
    	String timeDate = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());
    	
    	String excelFileName = "serviceEntResourceExport" + timeDate;
    	
    	String fileAddress = dirRoot + File.separator + "RP" + File.separator + "EXPORT" + File.separator + newDate + File.separator + excelFileName + ".xls";
    	
    	List<String[]> columNames = new ArrayList<String[]>();
        columNames.add(new String[] { "企业互生号", "企业名称", "企业地址", "联系人姓名", "联系人手机", "系统开启日期","经营类型", "企业认证状态","开启系统类型" });

        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "hsResNo", "entCustName", "entRegAddr", "contactPerson", "contactPhone", "openDate","exportBusinessType", "exportRealnameAuth","exportResFeeChargeMode" });

        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        try {
            this.validateEntResourceInfo(rpEnterprisResource);
            List list = reportsSystemResourceMapper.searchEntResourceExport(rpEnterprisResource);
            this.exportMaxRow(list);
			map.put("服务公司资源", list);
        }
        catch (Exception e)
	    {
        	SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
	    }
        
        
        ExcelExportData setInfo = new ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[] { "服务公司资源报表"});
        setInfo.setColumnNames(columNames);
        
        try {
			ExcelUtil.export2File(setInfo, fileAddress);
		} catch (Exception e) {
			SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
        
        return fileAddress;
    }
    

    /**  
     * 消费者资源导出 
     * @param rpCardholderResource 消费者资源数据对象
     * @return List<ReportsCardholderResource> 消费者资源数据集合
     * @throws HsException 
     * @see com.gy.hsxt.rp.api.IReportsSystemResourceService#searchCardholderResource(com.gy.hsxt.rp.bean.ReportsCardholderResource) 
     */
    @Override
    public String exportCardholderResource(ReportsCardholderResource rpCardholderResource)
            throws HsException {
        
    	String dirRoot = PropertyConfigurer.getProperty("dir.root");
    	
    	String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    	
    	String timeDate = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());
    	
    	String excelFileName = "consumerEntResourceExport" + timeDate;
    	
    	//文件路径
    	String fileAddress = dirRoot + File.separator + "RP" + File.separator + "EXPORT" + File.separator + newDate + File.separator + excelFileName + ".xls";
    	
    	List<String[]> columNames = new ArrayList<String[]>();
        //columNames.add(new String[] { "互生卡号", "联系人姓名", "国籍", "手机号码", "户籍地址", "消费者状态", "消费者状态完成时间", "互生卡状态" });
    	columNames.add(new String[] { "互生卡号", "单位名称", "证件类型", "手机号码", "证件号码", "消费者状态", "消费者状态完成时间", "互生卡状态" });
        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "hsResNo", "perName", "exportIdType", "mobile", "idNo", "exportRealnameAuth", "realnameAuthDate", "exportBaseStatus" });

        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        try {
            if (rpCardholderResource == null)
            {
                SystemLog.debug("HSXT_RP", "方法：searchCardholderResource", RespCode.RP_PARAMETER_NULL.getCode()
                        + "rpCardholderResource:消费者资源数据对象");
                throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpCardholderResource:消费者资源数据对象");
            }
            String beginDate = rpCardholderResource.getRealnameAuthDateBegin();// 开始时间
            String endDate = rpCardholderResource.getRealnameAuthDateEnd();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateBeginTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                rpCardholderResource.setRealnameAuthDateEndTim(dateMap.get("endDate"));
            }
            //System.out.println(new Timestamp(System.currentTimeMillis()));
            //查询当前导出数量是否大于3W条
            int count = reportsSystemResourceMapper.searchCardholderResourceCount(rpCardholderResource);
            if(count > exportMaxRow){
                throw new HsException(RespCode.RP_EXPORT_MAX_NUM.getCode(), "导出数据量不能大于"+exportMaxRow+"条");
            }
            //消费者报表查询的集合
            List<ReportsCardholderResource> list = reportsSystemResourceMapper.searchCardholderResourceExport(rpCardholderResource);
            //System.out.println(new Timestamp(System.currentTimeMillis()));
            //国家列表集合
            /*List<Country> countryList = baseDataService.findContryAll();
            Map<String,String> countryMap = new HashMap<String,String>();
            //List集合转Map
            if(!countryList.isEmpty()){
                for(Country country : countryList){
                    countryMap.put(country.getCountryNo(), country.getCountryNameCn());
                }
                //把报表中的国家Code编码转Name国家名称
                if(!list.isEmpty()){
                    for(ReportsCardholderResource rcr : list){
                        rcr.setCountryName(countryMap.get(rcr.getCountryName()));
                    }
                }
            }*/
            //this.exportMaxRow(list);      
			map.put("消费者资源",list);
        }
        catch (Exception e)
	    {
        	SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
	    }
        
        //把报表查询的集合写入到exel文档中
        ExcelExportData setInfo = new ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[] { "消费者资源报表"});
        setInfo.setColumnNames(columNames);
        
        try {
			ExcelUtil.export2File(setInfo, fileAddress);
		} catch (Exception e) {
			SystemLog.error("HSXT_BP", "方法：searchEnterprisResource", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
	        throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
        //System.out.println(new Timestamp(System.currentTimeMillis()));
        return fileAddress;
      
    }

    /**
     * 导出数量上限
     */
    public void exportMaxRow(List list) throws HsException{
        if(list !=null){
            if(list.size() > exportMaxRow){
                throw new HsException(RespCode.RP_EXPORT_MAX_NUM.getCode(), "导出数据量不能大于"+exportMaxRow+"条");
            }
        }
    }
    
    public ReportsEnterprisResource validateEntResourceInfo(ReportsEnterprisResource rpEnterprisResource) throws HsException{
        if (rpEnterprisResource == null)
        {
            SystemLog.debug("HSXT_RP", "方法：searchEnterprisResource", RespCode.RP_PARAMETER_NULL.getCode()
                    + "rpEnterprisResource:企业资源数据对象为空");
            throw new HsException(RespCode.RP_PARAMETER_NULL.getCode(), "rpEnterprisResource:企业资源数据对象为空");
        }
        String beginDate = rpEnterprisResource.getOpenDateBegin();// 开始时间
        String endDate = rpEnterprisResource.getOpenDateEnd();// 结束时间

        // 验证日期的格式
        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
        if (dateMap.get("beginDate") != null)
        {
            rpEnterprisResource.setOpenDateBeginTim(dateMap.get("beginDate"));
        }
        if (dateMap.get("endDate") != null)
        {
            rpEnterprisResource.setOpenDateEndTim(dateMap.get("endDate"));
        }
        return rpEnterprisResource;
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
                SystemLog.debug("HSXT_RP", "方法：validateDateFormat", RespCode.RP_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + beginDate + " = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.RP_PARAMETER_FORMAT_ERROR.getCode(), beginDate + " = " + beginDate
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
                SystemLog.debug("HSXT_RP", "方法：validateDateFormat", RespCode.RP_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + endDate + " = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.RP_PARAMETER_FORMAT_ERROR.getCode(), endDate + " = " + endDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }
        return dateMap;
    }
    
    
}
