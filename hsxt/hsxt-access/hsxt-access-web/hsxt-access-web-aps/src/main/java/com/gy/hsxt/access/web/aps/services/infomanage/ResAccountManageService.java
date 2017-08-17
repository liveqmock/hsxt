package com.gy.hsxt.access.web.aps.services.infomanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsQueryEntCondition;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.infomanage
 * @className : ResAccountManageService.java
 * @description : 账户资源管理
 * @author : maocy
 * @createDate : 2016-02-19
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service
@SuppressWarnings("rawtypes")
public class ResAccountManageService extends BaseServiceImpl implements
		IResAccountManageService {

	@Autowired
	private IUCAsEntService ucEntService;// 企业申报服务类

	@Autowired
	private IUCAsCardHolderService ucCardService;// 企业申报服务类

	@Autowired
	private IReportsSystemResourceService reportsSystemResourceService;

	@Autowired
	private IUCAsCardHolderService cardHolderService;

	/**
	 * 
	 * 方法名称：账户资源管理-企业资源 方法描述：账户资源管理-企业资源
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件listConsumerInfolistConsumerInfolistConsumerInfo
	 * @param page
	 *            分页条件
	 * @return PageData 分页对象
	 * @throws HsException
	 */
	@Override
	public PageData<ReportsEnterprisResource> findScrollResult(Map filterMap,
			Map sortMap, Page page) throws HsException {
		// ReportsEnterprisResource rpEntResource = new
		// ReportsEnterprisResource();
		// rpEntResource.setCustType(CommonUtils.toInteger(filterMap.get("blongEntCustType")));
		// rpEntResource.setHsResNo((String) filterMap.get("belongEntResNo"));
		// rpEntResource.setEntCustName((String)
		// filterMap.get("belongEntName"));
		// rpEntResource.setContactPerson((String)
		// filterMap.get("belongEntContacts"));
		// rpEntResource.setOpenDateBegin((String) filterMap.get("startDate"));
		// rpEntResource.setOpenDateEnd((String) filterMap.get("endDate"));
		// PageData<ReportsEnterprisResource> pd =
		// reportsSystemResourceService.searchEnterprisResourcePage(rpEntResource,
		// page);

		/** 原UC */
		AsQueryEntCondition condition = getCondition(filterMap);// 组装查询参数
		PageData<AsBelongEntInfo> data = ucEntService.listEntInfoByCondition(
				condition, page);// 实时从用户中心查询数据
		PageData<ReportsEnterprisResource> pd = getResultList(data);// 转换成页面需要的数据结构
		return pd;
	}

	private PageData<ReportsEnterprisResource> getResultList(
			PageData<AsBelongEntInfo> data) {
		ReportsEnterprisResource resource = null;
		int count = 0;
		List<ReportsEnterprisResource> result = new ArrayList<>();
		if (null != data && null != data.getResult()) {
			count = data.getCount();
			for (AsBelongEntInfo entInfo : data.getResult()) {
				resource = buildReportsEnterprisResource(entInfo);
				result.add(resource);
			}
		}
		PageData<ReportsEnterprisResource> pd = new PageData<ReportsEnterprisResource>();
		pd.setCount(count);
		pd.setResult(result);
		return pd;
	}

	private AsQueryEntCondition getCondition(Map filterMap) {
		String entResNo = (String) filterMap.get("belongEntResNo");
		String entCustName = (String) filterMap.get("belongEntName");
		String contactor = (String) filterMap.get("belongEntContacts");
		String beginDate = (String) filterMap.get("startDate");
		String endDate = (String) filterMap.get("endDate");
		if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
			beginDate = (String) filterMap.get("openDateBegin");
			endDate = (String) filterMap.get("openDateEnd");
		}
		Integer custType = CommonUtils.toInteger(filterMap
				.get("blongEntCustType"));
		AsQueryEntCondition condition = new AsQueryEntCondition();
		if (StringUtils.isNotBlank(beginDate)
				&& StringUtils.isNotBlank(endDate)) {
			condition.setBeginDate(beginDate);
			condition.setEndDate(endDate);//
		}
		if (StringUtils.isNotBlank(contactor)) {
			condition.setContactor(contactor);// 联系人姓名
		}
		if (StringUtils.isNotBlank(entCustName)) {
			condition.setCustName(entCustName);// 企业名称
		}
		if (null != custType) {
			condition.setCustType(custType);
		}
		if (StringUtils.isNotBlank(entResNo)) {
			condition.setEntResNo(entResNo);// 企业互生号
		}
		return condition;
	}

	private ReportsEnterprisResource buildReportsEnterprisResource(
			AsBelongEntInfo entInfo) {
		ReportsEnterprisResource resource = new ReportsEnterprisResource();
		resource.setHsResNo(entInfo.getEntResNo());
		resource.setContactPhone(entInfo.getContactPhone());
		resource.setContactPerson(entInfo.getContactPerson());
		resource.setEntCustName(entInfo.getEntName());
		resource.setRealnameAuth(entInfo.getRealNameAuthSatus());
		resource.setOpenDate(entInfo.getOpenDate());
		resource.setEntRegAddr(entInfo.getEntAddr());
		resource.setCustId(entInfo.getEntCustId());
		resource.setCustType(entInfo.getEntCustType());
		if (null != entInfo.getArtResTypest()) {
			resource.setArtResType(entInfo.getArtResTypest());
		}
		if (null != entInfo.getBusinessType()) {
			resource.setBusinessType(entInfo.getBusinessType());
		}

		if (null != entInfo.getPointStatus()) {
			resource.setParticipationScore(entInfo.getPointStatus());
			resource.setBaseStatus(entInfo.getPointStatus());
		}
		if (null != entInfo.getCustNum()) {
			resource.setEnabledCardholderNumber(entInfo.getCustNum().intValue());
		}

		return resource;
	}

	/**
	 * 
	 * 方法名称：账户资源管理-消费者资源 方法描述：账户资源管理-消费者资源
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param page
	 *            分页属性
	 * @return
	 */
	public PageData<?> findConsumerInfoList(Map filterMap, Map sortMap,
			Page page) throws HsException {
		/*
		 * AsQueryConsumerCondition params = new AsQueryConsumerCondition();
		 * params.setEntResNo((String) filterMap.get("entResNo"));//互生卡号
		 * params.setRealName((String) filterMap.get("realName"));//姓名
		 * params.setRealnameStatus
		 * (CommonUtils.toInteger(filterMap.get("realnameStatus")));//消费者状态
		 * params.setBeginDate((String) filterMap.get("startDate"));//状态日期起
		 * params.setEndDate((String) filterMap.get("endDate"));//状态日期止 return
		 * this.ucCardService.listConsumerInfo(params, page);
		 */

		// ReportsCardholderResource rpCardholderResource = new
		// ReportsCardholderResource();
		// rpCardholderResource.setHsResNo((String) filterMap.get("entResNo"));
		// rpCardholderResource.setPerName((String) filterMap.get("realName"));
		// // if(CommonUtils.toInteger(filterMap.get("status")) != null){
		// rpCardholderResource.setRealnameAuth(CommonUtils.toInteger(filterMap
		// .get("realnameStatus")));
		// // }
		// rpCardholderResource.setRealnameAuthDateBegin((String) filterMap
		// .get("startDate"));
		// rpCardholderResource.setRealnameAuthDateEnd((String) filterMap
		// .get("endDate"));
		// PageData<ReportsCardholderResource> needData =
		// reportsSystemResourceService
		// .searchCardholderResourcePage(rpCardholderResource, page);
		// return
		// reportsSystemResourceService.searchCardholderResourcePage(rpCardholderResource,
		// page);
		AsQueryConsumerCondition condition = getConsumerCondition(filterMap);
		PageData<AsConsumerInfo> data = cardHolderService.listAllConsumerInfo(
				condition, page);
		PageData<ReportsCardholderResource> pageData = getConsumerResultList(data);
		return pageData;
	}

	private PageData<ReportsCardholderResource> getConsumerResultList(
			PageData<AsConsumerInfo> data) {
		PageData<ReportsCardholderResource> pageData = new PageData<ReportsCardholderResource>();
		List<ReportsCardholderResource> resultList = new ArrayList<>();
		ReportsCardholderResource resource = null;
		for (AsConsumerInfo consumerInfo : data.getResult()) {
			resource = buildReportsCardholderResource(consumerInfo);
			resultList.add(resource);
		}
		pageData.setResult(resultList);
		pageData.setCount(data.getCount());
		return pageData;
	}

	private AsQueryConsumerCondition getConsumerCondition(Map filterMap) {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		String entResNo = (String) filterMap.get("entResNo");
		String perResNo = (String) filterMap.get("perResNo");
		String perName = (String) filterMap.get("realName");
		Integer realnameStatus = CommonUtils.toInteger(filterMap
				.get("realnameStatus"));
		String startDate = (String) filterMap.get("startDate");
		String endDate = (String) filterMap.get("endDate");
		if (StringUtils.isNotBlank(realnameStatus)) {
			condition.setRealnameStatus(realnameStatus);
			if (StringUtils.isNotBlank(startDate)
					&& StringUtils.isNotBlank(endDate)) {
				condition.setBeginDate(startDate);
				condition.setEndDate(endDate);
			}
		}

		if (StringUtils.isNotBlank(perName)) {
			condition.setRealName(perName);
		}
		if (StringUtils.isNotBlank(entResNo)) {
			condition.setPerResNo(entResNo);
		}
		return condition;
	}

	private ReportsCardholderResource buildReportsCardholderResource(
			AsConsumerInfo consumerInfo) {
		Integer realNameStatus = consumerInfo.getRealnameStatus();
		ReportsCardholderResource resource = new ReportsCardholderResource();
		resource.setHsResNo(StringUtils.nullToEmpty(consumerInfo.getPerResNo())); // check
		resource.setPerName(StringUtils.nullToEmpty(consumerInfo.getRealName()));
		if (!StringUtils.isBlank(consumerInfo.getCountryCode())) {
			resource.setCountryName(consumerInfo.getCountryCode());
		}
		resource.setMobile(StringUtils.nullToEmpty(consumerInfo.getMobile()));
		resource.setRealnameAuth(consumerInfo.getRealnameStatus());
		if (null == realNameStatus || 1 == realNameStatus) {
			resource.setRealnameAuthDate(consumerInfo.getCreateDate());
		} else if (2 == realNameStatus) {
			resource.setRealnameAuthDate(consumerInfo.getRealnameRegDate());
		} else if (3 == realNameStatus) {
			resource.setRealnameAuthDate(consumerInfo.getRealnameAuthDate());
		}

		resource.setBaseStatus(consumerInfo.getBaseStatus());
		// resource.setAdminEntResNo("06000000000");
		resource.setCustId(consumerInfo.getCustId());
		// resource.setCustType(1);
		// resource.setEntResNo("06033110000");
		// resource.setIdNo(consumerInfo.getIdNo());
		// resource.setIdType(consumerInfo.getIdType());
		// resource.setServiceEntResNo("06033000000");
		// resource.setCountryName("004");

		return resource;
	}

	/**
	 * 
	 * 方法名称：资源名录-消费者资源
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param page
	 *            分页属性
	 * @return
	 */
	public PageData<?> findReportConsumerInfoList(Map filterMap, Map sortMap,
			Page page) throws HsException {
		return null;
	}

	/**
	 * 
	 * 方法名称：资源名录-企业资源
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param page
	 *            分页属性
	 * @return
	 */
	public PageData<?> findReportResEntInfoList(Map filterMap, Map sortMap,
			Page page) throws HsException {
		ReportsEnterprisResource rpEntResource = new ReportsEnterprisResource();
		rpEntResource.setCustType(CommonUtils.toInteger(filterMap
				.get("blongEntCustType")));
		rpEntResource.setHsResNo((String) filterMap.get("belongEntResNo"));
		rpEntResource.setEntCustName((String) filterMap.get("belongEntName"));
		rpEntResource.setContactPerson((String) filterMap
				.get("belongEntContacts"));
		rpEntResource.setOpenDateBegin((String) filterMap.get("openDateBegin"));
		rpEntResource.setOpenDateEnd((String) filterMap.get("openDateEnd"));
		PageData<ReportsEnterprisResource> pd = reportsSystemResourceService
				.searchEnterprisResourcePage(rpEntResource, page);
		return pd;
	}

}