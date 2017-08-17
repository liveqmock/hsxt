package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSIntentCustService;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.bs.bean.apply.IntentCustBaseInfo;
import com.gy.hsxt.bs.bean.apply.IntentCustQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : IntentionCustService.java
 * @description   : 意向客户查询接口实现
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class IntentionCustService extends BaseServiceImpl implements IIntentionCustService {
	
    @Autowired
    private IBSIntentCustService bcService;//意向客户服务类

    /**
     * 
     * 方法名称：查询意向客户
     * 方法描述：查询意向客户
     * @return PageData 分页信息
     * @throws HsException
     */
	@Override
	public PageData<IntentCustBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        IntentCustQueryParam param = new IntentCustQueryParam();
		param.setAppType(CommonUtils.toInteger(filterMap.get("appType")));
		param.setEndDate((String) filterMap.get("endDate"));
		param.setEntCustName((String) filterMap.get("entCustName"));
		param.setSerEntResNo((String) filterMap.get("pointNo"));
		param.setStartDate((String) filterMap.get("startDate"));
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		return this.bcService.serviceEntQueryIntentCust(param, page);
	}

	/**
	 * 
	 * 方法名称：查看客户申请资料
	 * 方法描述：依据客户申请编号查看客户申请资料
	 * @param applyId 客户申请编号
	 * @return IntentCust 客户申请资料
	 * @throws HsException
	 */
	@Override
	public IntentCust findIntentCustById(String applyId) throws HsException {
		return this.bcService.queryIntentCustById(applyId);
	}

	/**
	 * 
	 * 方法名称：处理意向客户申请
	 * 方法描述：依据客户申请编号处理意向客户申请
	 * @param applyId 申请编号
	 * @param apprOperator 操作员客户号
	 * @param status 处理状态
	 * @param apprRemark 处理意见
	 * @throws HsException
	 */
	@Override
	public void dealIntentCustById(String applyId, String apprOperator, Integer status, String apprRemark) throws HsException {
		this.bcService.apprIntentCust(applyId, apprOperator, status, apprRemark);
	}
	
}
