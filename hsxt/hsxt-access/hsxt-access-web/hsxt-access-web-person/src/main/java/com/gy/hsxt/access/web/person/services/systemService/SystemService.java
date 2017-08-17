/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.systemService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.systemservice.SysService;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.as.bean.consumer.AsUserActionLog;

/**
 * 
 * @projectName   : hsxt-access-web-person
 * @package       : com.gy.hsxt.access.web.person.services.systemService
 * @className     : SystemService.java
 * @description   : 系统服务接口实现
 * @author        : maocy
 * @createDate    : 2016-01-21
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class SystemService extends BaseServiceImpl implements ISystemService {
    private static final String MOUDLENAME = "com.gy.hsxt.access.web.person.services.systemService.SystemService";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    @Autowired 
    private IUCAsCardHolderAuthInfoService ucCardHolderAuthInfoService;//用户中心-查询实名注册信息dubbo
    
    @Autowired 
    private IUCAsCardHolderService ucAsCardHolderService;//用户中心
    
    @Autowired
    private IBSToolOrderService orderService;//BS-订单管理
    
    @Autowired
    private IBSRealNameAuthService realNameService;//BS-实名认证
    
    @Autowired
    private IBSChangeInfoService changeService;//BS-重要信息变更

    /**
	 * 
	 * 方法名称：查询实名注册
	 * 方法描述：查询实名注册
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findTruenameregList(Map filterMap, Map sortMap, Page page) throws HsException {
		try {
			String custId = (String) filterMap.get("custId");
			AsRealNameReg reg = this.ucCardHolderAuthInfoService.searchRealNameRegInfo(custId);
			if(null == reg){
				return null;
			}
			String realNameStatus = StringUtils.nullToEmpty(reg.getRealNameStatus());
			if("2".equals(realNameStatus) || "3".equals(realNameStatus)){
				String endDate = (String)filterMap.get("endDate");
				String startDate = (String)filterMap.get("startDate");
				String realnameRegDate = reg.getRealnameRegDate();
				if(isInDateRange(realnameRegDate,startDate, endDate)){
					SysService sys = new SysService(getTime(realnameRegDate), reg.getRealnameRegDate(), "成功",reg.getComment());
					return new PageData<>(1, Arrays.asList(new SysService[]{sys}));
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 判断实名注册日期是否在日期区间
	 * @param realnameRegDate	格式:yyyy-MM-dd hh:mm:ss
	 * @param endDate	格式:yyyy-MM-dd
	 * @return boolean true：在   false：不在
	 */
	private boolean isInDateRange(String realnameRegDate,String startDate,String endDate){
		if(StringUtils.isBlank(endDate)){
			return true;
		}
		if(StringUtils.isBlank(realnameRegDate)){
			return false;
		}
		if(StringUtils.isBlank(startDate)){
			return false;
		}
		String funName = "isInDateRange";
		StringBuffer msg = new StringBuffer();
		msg.append("判断实名注册日期是否在日期区间异常，入参信息：realnameRegDate["+realnameRegDate+"],endDate["+endDate+"]");
		String theEndDate = endDate + " 23:59:59";
		String theStartDate = startDate + " 00:00:00";
		try {
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			Calendar reg = Calendar.getInstance();
			Date startTime = StringToDate(theStartDate, DEFAULT_DATE_TIME_FORMAT);
			Date endTime = StringToDate(theEndDate, DEFAULT_DATE_TIME_FORMAT);
			Date regTime = StringToDate(realnameRegDate,DEFAULT_DATE_TIME_FORMAT);
			start.setTime(startTime);
			end.setTime(endTime);
			reg.setTime(regTime);
			boolean isInDateRande = reg.before(end) && reg.after(start);
			return isInDateRande;
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, funName, msg.toString(), e);
		}
		return false;
	}
	
	private String getTime(String realnameRegDate){
		Date no = StringToDate(realnameRegDate, DEFAULT_DATE_TIME_FORMAT);
		return String.valueOf(no.getTime());
	}
	/**
     * 功能: 将插入的字符串按格式转换成对应的日期对象
     * 
     * @param str
     *            字符串
     * @param pattern
     *            格式
     * @return Date
     */
    private static Date StringToDate(String str, String pattern) {
        Date dateTime = null;
        try
        {
            if (str != null && !str.equals(""))
            {
                SimpleDateFormat formater = new SimpleDateFormat(pattern);
                dateTime = formater.parse(str);
            }
        }
        catch (Exception ex)
        {
        }
        return dateTime;
    }
	
	/**
	 * 
	 * 方法名称：查询实名认证
	 * 方法描述：查询实名认证
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysBizRecord> findTruenameaugList(Map filterMap, Map sortMap, Page page) throws HsException {
		String staData = (String) filterMap.get("startDate");
		String endDate = (String) filterMap.get("endDate");
		PageData<SysBizRecord> pg = realNameService.queryPerAuthRecord((String) filterMap.get("custId"), staData, endDate,  page);
		return pg;
	}
	
	/**
	 * 
	 * 方法名称：查询重要信息变更
	 * 方法描述：查询重要信息变更
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysBizRecord> findImptinfochangeList(Map filterMap, Map sortMap, Page page) throws HsException {
		String staData = (String) filterMap.get("startDate");
		String endDate = (String) filterMap.get("endDate");
		return this.changeService.queryPerChagneRecord((String) filterMap.get("custId"), staData, endDate, page);
	}
	
	/**
	 * 
	 * 方法名称：查询互生卡补办
	 * 方法描述：查询互生卡补办
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findCardapplyList(Map filterMap, Map sortMap, Page page) throws HsException {
		BaseParam param = new BaseParam();
		param.setStartDate((String) filterMap.get("startDate"));
		param.setEndDate((String) filterMap.get("endDate"));
		param.setHsCustId((String) filterMap.get("custId"));
		PageData<Order> pd = this.orderService.queryPersonMendCardOrderPage(param, page);
		if(pd == null || pd.getResult() == null){
			return null;
		}
		List<SysService> temp = new ArrayList<SysService>();
		
		for (Order order : pd.getResult()) {
			temp.add(new SysService(order.getOrderNo(), order.getOrderTime(), order.getOrderStatus(),order.getOrderRemark()));
		}
		return new PageData<>(pd.getCount(), temp);
	}
	
	/**
	 * 
	 * 方法名称：查询互生卡挂失
	 * 方法描述：查询互生卡挂失
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findCardlossList(Map filterMap, Map sortMap, Page page) throws HsException {
		String staData = (String) filterMap.get("startDate");
		String endDate = (String) filterMap.get("endDate");
		String action = StringUtils.isBlank((String) filterMap.get("action"))?"2":"1";//1：互生卡解挂2：互生卡挂失
		PageData<AsUserActionLog> pd = this.ucAsCardHolderService.listUserActionLog((String) filterMap.get("custId"), action, staData, endDate, page);
		if(pd == null){
			return null;
		}
		List<SysService> list = new ArrayList<SysService>();
		for (AsUserActionLog log : pd.getResult()) {
			list.add(new SysService(log.getLogId(), log.getCreateDate(), "成功",log.getRemark()));
		}
		return new PageData<>(pd.getCount(), list);
	}
	
	/**
	 * 
	 * 方法名称：查询互生卡解挂
	 * 方法描述：查询互生卡解挂
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	@SuppressWarnings("unchecked")
	public PageData<SysService> findCarddislossList(Map filterMap, Map sortMap, Page page) throws HsException {
		filterMap.put("action", "1");
		return this.findCardlossList(filterMap, sortMap, page);
	}
	
}
