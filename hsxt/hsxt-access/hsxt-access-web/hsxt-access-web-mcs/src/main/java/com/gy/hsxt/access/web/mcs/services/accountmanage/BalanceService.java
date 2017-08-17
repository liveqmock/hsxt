/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.accountmanage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.api.deduction.IBSHsbDeductionService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.AllocDetail;
import com.gy.hsxt.ps.bean.AllocDetailSum;
import com.gy.hsxt.ps.bean.QueryDetail;
import com.gy.hsxt.tc.api.ITcCheckBalanceService;


/**
 * *************************************************************************
 * 
 * <PRE>
 * Description      : 账户余额查询服务
 * 
 * Project Name     : hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.accountManagement  
 * 
 * File Name        : BalanceService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-8 下午4:11:39
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-8 下午4:11:39
 * 
 * UpdateRemark     : 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ************************************************************************** 
 */
@Service
public class BalanceService extends BaseServiceImpl implements IBalanceService {
	// 账户系统查询服务
	@Autowired
	private IAccountSearchService accountSearchService;

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口

	@Autowired
	private IPsQueryService ipsQueryService;
	@Autowired
	private IAOBankTransferService iaoBankTransferService;// 转账详情
	@Autowired
	private ITcCheckBalanceService itcCheckBalanceService;// 系统调账
	@Autowired
	private IBSHsbDeductionService ibsHsbDeductionService;// 平台业务扣款
	
	@Resource
    private IPsQueryService psQueryService;// 消费积分查询服务
	
	/**
	 * 账户余额单查询
	 * 
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService#findAccNormal(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AccountBalance findAccNormal(String custId, String accType)
			throws HsException {
		return accountSearchService.searchAccNormal(custId, accType);
	}

	/**
	 * 企业昨日积分查询
	 * 
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService#findEntIntegralByYesterday(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AccountEntrySum findEntIntegralByYesterday(String custId,
			String accType) throws HsException {
		return accountSearchService.searchEntIntegralByYesterday(custId,
				accType);
	}

	/**
	 * 账户明细分页查询(积分、互生币、货币)
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.IBalanceService#searchAccEntryPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AccountEntry> searchAccEntryPage(Map filterMap,
			Map sortMap, Page page) throws HsException {
		// 获取查询参数类
		AccountEntryInfo aei = createAccountEntryInfo(filterMap);

		// 查询货币明细
		return accountSearchService.searchAccEntryList(aei, page);
	}

	/**
	 * 获取保底积分
	 * 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService#baseIntegral()
	 */
	@Override
	public String baseIntegral() throws HsException {
		// 配置参数（组）code
		String code = BusinessParam.CONFIG_PARA.getCode();
		// 保底积分设置key
		String key = BusinessParam.HS_POIT_MIN.getCode();

		BusinessSysParamItemsRedis business = businessParamSearch
				.searchSysParamItemsByCodeKey(code, key);

		return business.getSysItemsValue();
	}

	/**
	 * 创建账户明细查询类
	 * 
	 * @param filter
	 * @return
	 */
	AccountEntryInfo createAccountEntryInfo(Map filter) {
		AccountEntryInfo aei = new AccountEntryInfo();
		// 客户号
		String custId = (String) filter.get("entCustId");
		if (!StringUtils.isEmpty(custId)) {
			aei.setCustID(custId);
		}
		// 交易流水号
		String transNo = (String) filter.get("transNo");
		if (!StringUtils.isEmpty(transNo)) {
			aei.setTransNo(transNo);
		}
		// 账户类型编码
		String accType = (String) filter.get("accType");
		if (!StringUtils.isEmpty(accType)) {
			aei.setAccType(accType);
		}
		// 业务类型
		String businessType = (String) filter.get("businessType");
		if (!StringUtils.isEmpty(businessType)) {
			aei.setBusinessType(Integer.parseInt(businessType));
		}
		// 开始时间
		String beginDate = (String) filter.get("beginDate");
		if (!StringUtils.isEmpty(beginDate)) {
			aei.setBeginDate(beginDate);
		}
		// 结束时间
		String endDate = (String) filter.get("endDate");
		if (!StringUtils.isEmpty(endDate)) {
			aei.setEndDate(endDate);
		}
		return aei;
	}

	/**
	 * 查询积分分配明细汇总
	 * 
	 * @return
	 * @throws HsException
	 */

	public AllocDetailSum pointDetailSum(String batchNo, String entResNo)
			throws HsException {
		QueryDetail qd = new QueryDetail();
		qd.setBatchNo(batchNo);
		qd.setResNo(entResNo);
		return ipsQueryService.queryPointDetailSum(qd);
	}

	/**
	 * 查询积分分配明细列表
	 * 
	 * @return
	 * @throws HsException
	 */

	public PageData<AllocDetail> pointDetailList(Map filterMap,
			Map sortMap, Page page) throws HsException {
		QueryDetail bean = new QueryDetail();
		String entResNo = (String) filterMap.get("entResNo");
		bean.setBatchNo((String) filterMap.get("batchNo"));
		bean.setResNo(entResNo);
		bean.setNumber(page.getCurPage());
		bean.setCount(page.getPageSize());
		return ipsQueryService.queryPointDetail(bean);
	}
	/**
	 * 账户操作明细详情(积分、货币)
	 * 
	 * @Description:
	 * @param transNo
	 * @param transType
	 * @return
	 * @throws HsException
	 */
	@Override
	public Map<String, Object> queryAccOptDetailed(String transNo,
			String transType) throws HsException {
		Map<String, Object> ret = new HashMap<String, Object>();
		Object obj = null;
		switch (TransType.getTransType(transType)) {
		case C_PRETRANS_CASH_CANCEL:// 企业银行转账撤单
		case C_PRETRANS_CASH:// 企业转账预转出
		case C_TRANS_CASH:
			obj = iaoBankTransferService.getTransOutInfo(transNo);
			break;
		case C_TRANS_REFUND:// 企业转账失败退回
			obj = iaoBankTransferService.getTransOutFailInfo(transNo);
			break;
		case C_TRANS_BANK_REFUND:// 企业转账银行退票退回
			obj = iaoBankTransferService.getTransOutFailInfo(transNo);
			break;
		case CHECK_BALANCE_IN:// 调账
		case CHECK_BALANCE_OUT:// 调账
			obj = itcCheckBalanceService.searchCheckBalanceById(transNo);
			break;
		case DEDUCT_HSB_FROM_CUST:// 平台业务扣款
			obj = ibsHsbDeductionService.queryDetailById(transNo);
			break;
		default:
			break;
		}
		ret.put("data", obj);
		return ret;
	}
	
	/**
	 * 分页查询消费积分分配汇总列表
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException 
	 * @see com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService#queryPointDetailSumPage(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
    @Override
    public PageData<AllocDetailSum> queryPointDetailSumPage(Map filterMap, Map sortMap,
            Page page) throws HsException {
        QueryDetail qd = this.createConditon(filterMap);
        return psQueryService.queryPointDetailSumPage(qd, page);
    }
    
    /**
     * 封装查询条件
     * @param filterMap
     * @return
     */
    QueryDetail createConditon(Map filterMap){
        QueryDetail qd = new QueryDetail();
        qd.setResNo((String)filterMap.get("entResNo"));//互生号
        qd.setBeginBatchNo((String)filterMap.get("beginBatchNo"));//开始批次号
        qd.setEndBatchNo((String)filterMap.get("endBatchNo"));//结束批次号
        return qd;
    }
}
