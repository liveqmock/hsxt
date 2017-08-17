package com.gy.hsxt.access.web.scs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : IContractManageService.java
 * @description   : 意向客户查询接口
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IIntentionCustService extends IBaseService {
	
	/**
	 * 
	 * 方法名称：查看客户申请资料
	 * 方法描述：依据客户申请编号查看客户申请资料
	 * @param applyId
	 * @return
	 * @throws HsException
	 */
	public IntentCust findIntentCustById(String applyId) throws HsException;
	
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
	public void dealIntentCustById(String applyId, String apprOperator, Integer status, String apprRemark) throws HsException;
	

}