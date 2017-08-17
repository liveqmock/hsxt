/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.companyInfo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.entstatus.ChangeInfoQueryParam;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.companyinfo
 * @className : CompanyInfoService.java
 * @description : 企业系统信息接口实现
 * @author : maocy
 * @createDate : 2016-01-14
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class CompanyInfoService extends BaseServiceImpl implements ICompanyInfoService {

	@Autowired
	private IUCAsEntService ucService;// UC服务类

	@Autowired
	private IBSRealNameAuthService bsService;// BS服务类

	@Autowired
	private IBSChangeInfoService bsChangeService;// BS服务类

	/**
	 * 
	 * 方法名称：查询企业信息 方法描述：查询企业信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	public AsEntExtendInfo findEntAllInfo(String entCustId) throws HsException
	{
		return this.ucService.searchEntExtInfo(entCustId);
	}

	/**
	 * 
	 * 方法名称：更新企业信息 方法描述：更新企业信息
	 * 
	 * @param entExtInfo
	 *            企业信息
	 * @param operator
	 *            操作用户id
	 * @throws HsException
	 */
	public void updateEntExtInfo(AsEntExtendInfo entExtInfo, String operator) throws HsException
	{
		this.ucService.updateEntExtInfo(entExtInfo, operator);
	}

	/**
	 * 
	 * 方法名称：重新启用成员企业 方法描述：重新启用成员企业
	 * 
	 * @param companyCustId
	 *            企业客户号
	 * @param operator
	 *            操作用户id
	 * @throws HsException
	 */
	public void updateEntStatusInfo(String companyCustId, String operator) throws HsException
	{
		AsEntStatusInfo as = new AsEntStatusInfo();
		as.setBaseStatus(1);
		as.setEntCustId(companyCustId);
		this.ucService.updateEntStatusInfo(as, operator);
	}

	/**
	 * 方法名称：查询企业实名认证信息 方法描述：查询企业实名认证信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 * @throws HsException
	 */
	public AsEntStatusInfo findEntStatusInfo(String entCustId) throws HsException
	{
		return this.ucService.searchEntStatusInfo(entCustId);
	}

	/**
	 * 方法名称：创建企业实名认证信息 方法描述：创建企业实名认证信息
	 * 
	 * @param entRealnameAuth
	 *            企业实名认证信息
	 * @throws HsException
	 */
	public void createEntRealNameAuth(EntRealnameAuth entRealnameAuth) throws HsException
	{
		this.bsService.createEntRealnameAuth(entRealnameAuth);
	}

	/**
	 * 方法名称：查询企业实名认证信息 方法描述：查询企业实名认证信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	public EntRealnameAuth findEntRealnameAuthByCustId(String entCustId) throws HsException
	{
		return this.bsService.queryEntRealnameAuthByCustId(entCustId);
	}

	/**
	 * 方法名称：查询重要信息变更记录 方法描述：查询重要信息变更记录
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param page
	 *            分页信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException
	{
		ChangeInfoQueryParam param = new ChangeInfoQueryParam();
		param.setStartDate((String) filterMap.get("startDate"));
		param.setEndDate((String) filterMap.get("endDate"));
		param.setResNo((String) filterMap.get("pointNo"));
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		return this.bsChangeService.entQueryChange(param, page);
	}

	/**
	 * 方法名称：创建企业重要信息变更 方法描述：创建企业重要信息变更
	 * 
	 * @param entRealnameAuth
	 *            企业重要信息变更
	 * @throws HsException
	 */
	public void createEntChange(EntChangeInfo entChangeInfo) throws HsException
	{
		this.bsChangeService.createEntChange(entChangeInfo);
	}

	/**
	 * 方法名称：查询重要信息变更 方法描述：依据客户号查询重要信息变更
	 * 
	 * @param applyId
	 *            申请编号
	 * @throws HsException
	 */
	public EntChangeInfo findEntChangeByApplyId(String applyId) throws HsException
	{
		return this.bsChangeService.queryEntChangeById(applyId);
	}

	/**
	 * 方法名称：查询重要信息变更 方法描述：依据客户号查询重要信息变更
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	public EntChangeInfo findEntChangeByCustId(String entCustId) throws HsException
	{
		return this.bsChangeService.queryEntChangeByCustId(entCustId);
	}

}