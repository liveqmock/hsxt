/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.resoucemanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsec.external.api.EnterpriceService;
import com.gy.hsec.external.bean.EnterpriceInputParam;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.entstatus.IBSMemberQuitService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.NumbericUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;

/***
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.sysres
 * @ClassName: EntResService
 * @Description: 企业资源管理服务类
 * @author: wangjg
 * @date: 2015-11-4 下午4:06:40
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Service("entResService")
public class EntResService extends BaseServiceImpl implements IEntResService {
    
	@Autowired
	private IUCAsEntService ucService;// 企业管理服务类

	@Autowired
	private IBSMemberQuitService bsService;// 成员企业服务类

	@Autowired
	private IReportsSystemResourceService reportsSystemResourceService; // 报表服务

	@Autowired
    private EnterpriceService enterpriceService;
	
	@Autowired
    private IPsQueryService psQueryService;
	
	@Override
	public PageData<AsBelongEntInfo> findScrollResult(Map filterMap,
			Map sortMap, Page page) throws HsException {
		AsQueryBelongEntCondition params = new AsQueryBelongEntCondition();
		params.setEntResNo((String) filterMap.get("pointNo"));// 服务公司互生号
		params.setBelongEntResNo((String) filterMap.get("entResNo"));// 企业互生号
		params.setBeginDate((String) filterMap.get("startDate"));// 查询开始日期
		params.setEndDate((String) filterMap.get("endDate"));// 查询结束日期
		params.setBlongEntCustType(CommonUtils.toInteger(filterMap
				.get("custType")));// 客户类型
		params.setBelongEntContacts((String) filterMap.get("linkman"));// 联系人
		params.setBelongEntName((String) filterMap.get("entName"));// 企业名称
		params.setBelongEntBaseStatus(CommonUtils.toInteger(filterMap
				.get("status")));// 状态
		params.setProvinceNo((String) filterMap.get("provinceNo"));// 省份
		params.setCityNo((String) filterMap.get("cityNo"));// 城市
		return this.ucService.listBelongEntInfo(params, page);
	}

	/**
	 * 
	 * 方法名称：查询成员企业资格维护 方法描述：查询成员企业资格维护
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param page
	 *            分页条件
	 * @return
	 * @throws HsException
	 */
	public PageData<ReportsEnterprisResource> findQualMainList(Map filterMap,
			Map sortMap, Page page) throws HsException {

		// 创建查询条件bean
		AsQueryBelongEntCondition condition = getCondition(filterMap);
		// 查询资源一览
		PageData<ReportsEnterprisResource> data = new PageData<ReportsEnterprisResource>();
		PageData<AsBelongEntInfo> ucData = ucService.listBelongEntInfo(
				condition, page);
		List<ReportsEnterprisResource> dataList = new ArrayList<>();
		if (null != ucData && null != ucData.getResult()) {
			data.setCount(ucData.getCount());
			ReportsEnterprisResource resource = null;
			for (AsBelongEntInfo asBelongEnt : ucData.getResult()) {
				resource = buildResource(asBelongEnt);
				dataList.add(resource);
			}
		}
		data.setResult(dataList);
		return data;
	}

	private ReportsEnterprisResource buildResource(AsBelongEntInfo asBelongEnt) {
		ReportsEnterprisResource resourece = new ReportsEnterprisResource();
		resourece
				.setHsResNo(StringUtils.nullToEmpty(asBelongEnt.getEntResNo()));
		resourece.setEntCustName(StringUtils.nullToEmpty(asBelongEnt
				.getEntName()));
		resourece.setEntRegAddr(StringUtils.nullToEmpty(asBelongEnt
				.getEntAddr()));
		resourece.setContactPerson(StringUtils.nullToEmpty(asBelongEnt
				.getContactPerson()));
		resourece.setContactPhone(StringUtils.nullToEmpty(asBelongEnt
				.getContactPhone()));
		resourece
				.setOpenDate(StringUtils.nullToEmpty(asBelongEnt.getOpenDate()));
		if (null != asBelongEnt.getRealNameAuthSatus()) {
			resourece.setRealnameAuth(asBelongEnt.getRealNameAuthSatus());
		}
		if (null != asBelongEnt.getPointStatus()) {
			resourece.setBaseStatus(asBelongEnt.getPointStatus());
			resourece.setParticipationScore(asBelongEnt.getPointStatus());
		}
		if (null != asBelongEnt.getCustNum()) {
			resourece.setEnabledCardholderNumber(asBelongEnt.getCustNum()
					.intValue());
		}
		if(StringUtils.isNotBlank(asBelongEnt.getCountryNo())){
			resourece.setCountryNo(asBelongEnt.getCountryNo());
		}
		if(StringUtils.isNotBlank(asBelongEnt.getProvinceNo())){
			resourece.setProvinceNo(asBelongEnt.getProvinceNo());
		}
		if(StringUtils.isNotBlank(asBelongEnt.getCityNo())){
			resourece.setCityNo(asBelongEnt.getCityNo());
		}if(StringUtils.isNotBlank(asBelongEnt.getEntCustId())){
			resourece.setCustId(asBelongEnt.getEntCustId());
		}
		return resourece;
	}

	/**
	 * 创建资源一览查询条件
	 * 
	 * @param filterMap
	 * @return
	 */
	ReportsEnterprisResource createRER(Map filterMap) {
		ReportsEnterprisResource retRER = new ReportsEnterprisResource();

		// 判断是否团队资源号
		String applyEntResNo = (String) filterMap.get("applyEntResNo");
		if (!StringUtils.isEmpty(applyEntResNo)) {
			retRER.setApplyEntResNo((String) filterMap.get("applyEntResNo"));
			retRER.setCustType(CustType.SERVICE_CORP.getCode());
		} else {
			retRER.setServiceEntResNo((String) filterMap.get("pointNo"));// 服务公司互生号
			retRER.setCustType(NumbericUtils.isNumber((String) filterMap
					.get("custType")) ? Integer.parseInt(filterMap.get(
					"custType").toString()) : null);// 客户类型
		}

		retRER.setProvinceNo((String) filterMap.get("provinceNo"));// 所在省份
		retRER.setCityNo((String) filterMap.get("cityNo"));// 所在城市
		retRER.setOpenDateBegin((String) filterMap.get("startDate"));// 查询开始日期
		retRER.setOpenDateEnd((String) filterMap.get("endDate"));// 查询结束日期
		retRER.setBaseStatus(NumbericUtils.isNumber((String) filterMap
				.get("status")) ? Integer.parseInt(filterMap.get("status")
				.toString()) : null);// 基本状态
		retRER.setHsResNo((String) filterMap.get("entResNo"));// 企业互生号
		retRER.setEntCustName((String) filterMap.get("entName"));// 企业名称
		retRER.setContactPerson((String) filterMap.get("linkman"));// 企业联系人
		return retRER;
	}

	private AsQueryBelongEntCondition getCondition(Map filterMap) {
		AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
		String serviceEntResNo = (String) filterMap.get("hsResNo");
		if (StringUtils.isBlank(serviceEntResNo)) {
			serviceEntResNo = (String) filterMap.get("pointNo");
		}
		if (StringUtils.isNotBlank(serviceEntResNo)) {
			condition.setEntResNo(serviceEntResNo);
		}
		String belongResNo = (String) filterMap.get("entResNo");
		if(StringUtils.isBlank(belongResNo)){
			
		}
		if (StringUtils.isNotBlank(belongResNo)) {
			condition.setBelongEntResNo(belongResNo);
		}
		Integer custType = null;
		Integer staus = null;
		String tempStatus = (String) filterMap.get("status");
		String tempCustType = (String) filterMap.get("custType");
		try {
			if(StringUtils.isNotBlank(tempStatus)){
				staus = Integer.parseInt(tempStatus);
			}
			if(StringUtils.isNotBlank(tempCustType)){
				custType = Integer.parseInt(tempCustType);
			}
			
		} catch (Exception e) {
			SystemLog.error("ResouceQuotaService", "getCondition", "转换Integer类型出错：staus["+tempStatus+",custType["+tempCustType+"]", e);
		}
		String belongEntName = (String) filterMap.get("entName");
		if (StringUtils.isNotBlank(belongEntName)) {
			condition.setBelongEntName(belongEntName);
		}
		String linkman = (String) filterMap.get("linkman");
		if (StringUtils.isNotBlank(linkman)) {
			condition.setBelongEntContacts(linkman);
		}
		if(null != custType){
			condition.setBlongEntCustType(custType);
		}
		String provinceNo = (String) filterMap.get("provinceNo");
		if(StringUtils.isNotBlank(provinceNo)){
			condition.setProvinceNo(provinceNo);
		}
		String cityNo = (String) filterMap.get("cityNo");
		if(StringUtils.isNotBlank(cityNo)){
			condition.setCityNo(cityNo);
		}
		condition.setBeginDate((String) filterMap.get("startDate"));
		condition.setEndDate((String) filterMap.get("endDate"));
		condition.setBelongEntBaseStatus(staus);
		return condition;
	
	}

	/**
	 * 
	 * 方法名称：注销成员企业 方法描述：注销成员企业
	 * 
	 * @param memberQuit
	 *            注销信息
	 * @throws Exception 
	 */
	public void createMemberQuit(MemberQuit memberQuit) throws Exception {
	    
	    /**
         * 提交时要检查下列业务，存在任一业务则不能提交申请：
         *   a、网上商城是否有未完结订单
         *   b、是否有未完结工具申购订单（配送单作成前）
         *   c、是否有未完结账款（检查欠款及货币转银行账户申请）
         *   d、是否有提交重要信息变更申请
         *   
         *   bcd在BS创建是自己做判断
         */
	    EnterpriceInputParam inputParam = new EnterpriceInputParam();
        inputParam.setEnterpriceResourceNo(memberQuit.getEntResNo());
        //网上商城是否有未完结订单
        ReturnResult result = enterpriceService.checkEnterpriceNotCompleteOrderAndRefund(inputParam);
        
        if(result.getRetCode() == 201 || result.getRetCode() == 509)
        {
            throw new HsException(ASRespCode.EW_MEMBER_APPLY_QUIT_HAVE_NOTCOMPLETEORDER);
        }
        
        /*** 根据企业custId查询是否有未结单的交易  账务系统*/
        if(!psQueryService.queryIsSettleByEntCustId(memberQuit.getEntCustId())){
            throw new HsException(ASRespCode.EW_ENT_HAVE_ORDER);
        }
        
		this.bsService.createMemberQuit(memberQuit);
		
		EnterpriceInputParam _inputParam = new EnterpriceInputParam();
        _inputParam.setEnterpriceResourceNo(memberQuit.getEntResNo());
        
        /** 通知电商  发起（成员企业资质注销   做如下五件事：
         * 1、变更网上商城以及网上商城下的营业点为冻结状态
         * 2、通知搜索引擎删除网上商城以及网上商城下的营业点（零售、餐饮）的索引
         * 3、删除网上商城下营业点（餐饮）对应的菜单、菜品关联关系
         * 4、按照商品状态非（待发布、待上架、审核失败、已下架、违规下架、删除）查询零售商品，批量更新零售商品为发布状态，同时通知搜索引擎删除网上商城下的商品
         * 5、按照商品状态非（待发布、待上架、审核失败、已下架、违规下架、删除）查询餐饮商品，批量更新餐饮菜品为发布状态，同时通知搜索引擎删除网上商城下的菜品
         **/
        enterpriceService.applyEnterpriceStopPointActivity(inputParam);
        
	}

}
