/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.infomanage.ResAccountManageService;
import com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bp.api.IBusinessSysBoSettingService;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.api.IReportsSystemResourceExportService;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;

@SuppressWarnings("rawtypes")
@Service
public class ResourceDirectoryServiceImpl extends BaseServiceImpl implements
		ResourceDirectoryService {

	@Autowired
	private IUCAsEntService iuCAsEntService;

	@Autowired
	private IUCAsCardHolderService cardHolderService;

	@Autowired
	private IUCAsBankAcctInfoService asBankAcctInfoService;

	@Autowired
	private IBusinessSysBoSettingService businessSysBoSettingService;

	@Autowired
	private IReportsSystemResourceService reportsSystemResourceService;

	@Resource
	private IReportsSystemResourceExportService reportsSystemResourceExportService;

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		// 创建查询条件类
		ReportsEnterprisResource rpEntResource = this.createRER(filterMap);

		return reportsSystemResourceService.searchEnterprisResourcePage(
				rpEntResource, page);
	}

	/**
	 * 创建查询条件类
	 * 
	 * @param filterMap
	 * @return
	 */
	ReportsEnterprisResource createRER(Map filterMap) {
		ReportsEnterprisResource retRER = new ReportsEnterprisResource();

		retRER.setCustType(CommonUtils.toInteger(filterMap
				.get("blongEntCustType"))); // 企业类型判断
		retRER.setPlatformEntResNo((String) filterMap.get("entResNo")); // 隶属平台
		retRER.setHsResNo((String) filterMap.get("belongEntResNo")); // 企业互生号
		retRER.setEntCustName((String) filterMap.get("belongEntName")); // 企业名称
		retRER.setContactPerson((String) filterMap.get("belongEntContacts")); // 联系人
		retRER.setOpenDateBegin((String) filterMap.get("openDateBegin")); // 系统开启开始时间
		retRER.setOpenDateEnd((String) filterMap.get("openDateEnd")); // 系统开启结束时间

		return retRER;
	}

	/**
	 * 查询消费者资源
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页条件
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService#listConsumerInfo(com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ReportsCardholderResource> listConsumerInfo(Map filterMap,
			Map sortMap, Page page) throws HsException {
		// 查询条件类
		ReportsCardholderResource rcr = this.reateRCR(filterMap);
		PageData<ReportsCardholderResource> pd = reportsSystemResourceService
				.searchCardholderResourcePage(rcr, page);
		return pd;
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
		if (null != consumerInfo.getIdType()) {
			resource.setIdType(consumerInfo.getIdType());
			resource.setIdNo(StringUtils.nullToEmpty(consumerInfo.getIdNo()));
		}

		resource.setBaseStatus(consumerInfo.getCardStatus());
		resource.setCustId(consumerInfo.getCustId());
		return resource;
	}

	private AsQueryConsumerCondition getConsumerCondition(Map filterMap) {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		String perResNo = (String) filterMap.get("perResNo");
		String perName = (String) filterMap.get("realName");
		Integer realnameStatus = CommonUtils.toInteger(filterMap.get("status"));
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
		if (StringUtils.isNotBlank(perResNo)) {
			condition.setPerResNo(perResNo);
		}
		return condition;
	}

	/**
	 * 创建消费者资源查询条件
	 * 
	 * @param filterMap
	 * @return
	 */
	ReportsCardholderResource reateRCR(Map filterMap) {
		ReportsCardholderResource rcr = new ReportsCardholderResource();
		// rcr.setPlatformEntResNo((String)filterMap.get("pointNo"));
		rcr.setHsResNo((String) filterMap.get("perResNo"));
		rcr.setPerName((String) filterMap.get("realName"));
		rcr.setRealnameAuth(CommonUtils.toInteger(filterMap.get("status")));
		rcr.setRealnameAuthDateBegin((String) filterMap.get("startDate"));
		rcr.setRealnameAuthDateEnd((String) filterMap.get("endDate"));
		return rcr;
	}

	/**
	 * 查询消费者所有信息
	 * 
	 * @param custID
	 *            消费者客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService#queryConsumerAllInfo(java.lang.String)
	 */
	public AsCardHolderAllInfo queryConsumerAllInfo(String custID)
			throws HsException {
		return cardHolderService.searchAllInfo(custID);
	}

	/**
	 * 查询绑定银行卡列表
	 * 
	 * @param custID
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            1：非持卡人 2：持卡人 4：企业
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService#listBanksByCustId(java.lang.String,
	 *      java.lang.String)
	 */
	public List<AsBankAcctInfo> listBanksByCustId(String custID, String userType)
			throws HsException {
		return asBankAcctInfoService.listBanksByCustId(custID, userType);
	}

	/**
	 * 查询绑定快捷卡列表
	 * 
	 * @param custID
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            1：非持卡人 2：持卡人 4：企业
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService#listQkBanksByCustId(java.lang.String,
	 *      java.lang.String)
	 */
	public List<AsQkBank> listQkBanksByCustId(String custID, String userType)
			throws HsException {
		return asBankAcctInfoService.listQkBanksByCustId(custID, userType);
	}

	/**
	 * 查询业务操作许可
	 * 
	 * @param custID
	 *            客户号（持卡人、非持卡人、企业）
	 * @return
	 * @throws HsException
	 */
	public Map<String, BusinessSysBoSetting> findBusinessPmInfo(
			BusinessSysBoSetting businessBo) throws HsException {
		return businessSysBoSettingService.searchSysBoSettingList(businessBo);
	}

	/**
	 * 设置业务操作许可
	 * 
	 * @param custID
	 *            客户号（持卡人、非持卡人、企业）
	 * @return
	 * @throws HsException
	 */
	public void setBusinessPmInfo(String custId, String operCustId,
			List<BusinessSysBoSetting> sysBoSettingList) throws HsException {
		businessSysBoSettingService.setSysBoSettingList(custId, operCustId,
				sysBoSettingList);
	}

	/**
	 * 获取消费者资源文件下载路径
	 * 
	 * @param rcr
	 * @return
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService#personResourceDowloadPath(com.gy.hsxt.rp.bean.ReportsCardholderResource)
	 */
	@Override
	public String personResourceDowloadPath(Map filterMap) throws HsException {
		// 查询条件
		ReportsCardholderResource rcr = this.reateRCR(filterMap);
		return reportsSystemResourceExportService.exportCardholderResource(rcr);
		// AsQueryConsumerCondition condition = getConsumerCondition(filterMap);
		// PageData<AsConsumerInfo> data =
		// cardHolderService.listAllConsumerInfo(condition, page);
		// PageData<ReportsCardholderResource> finalData =
		// getConsumerResultList(data);
		// return finalData;
	}

	/**
	 * 获取企业资源文件下载路径
	 * 
	 * @param filterMap
	 * @return
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService#entResourceDowloadPath(java.util.Map)
	 */
	@Override
	public String entResourceDowloadPath(Map filterMap) throws HsException {
		// 创建查询条件类
		ReportsEnterprisResource rer = this.createRER(filterMap);

		if (rer.getCustType() == CustType.MEMBER_ENT.getCode()) { // 成员企业
			return reportsSystemResourceExportService
					.exportMemberEntResource(rer);
		} else if (rer.getCustType() == CustType.TRUSTEESHIP_ENT.getCode()) { // 托管企业
			return reportsSystemResourceExportService
					.exportTrusEntResource(rer);
		} else if (rer.getCustType() == CustType.SERVICE_CORP.getCode()) { // 服务公司
			return reportsSystemResourceExportService
					.exportServiceEntResource(rer);
		}

		return null;
	}
}
