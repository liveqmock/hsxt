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

package com.gy.hsxt.uc.bs.api.consumer;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.api.consumer
 * @ClassName: IUCBsCardHolderStatusInfoService
 * @Description: 持卡人状态信息管理（BS业务系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午2:31:10
 * @version V1.0
 */
public interface IUCBsCardHolderStatusInfoService {
	
	/**
	 * 查询互生卡状态信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return  Map	key="perResNo"获取互生号，key="cardStatus"获取互生卡状态
	 * @throws HsException
	 */
	public Map<String,String> searchHsCardStatusInfoBycustId(String custId) throws HsException;

	/**
	 * 是否是重要信息变更期间
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return true : 是 false : 不是
	 * @throws HsException
	 */
	public boolean isMainInfoApplyChanging(String custId) throws HsException;

	/**
	 * 重要信息变更申请
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @param status
	 *            重要信息变更状态 1：是 0：否
	 * @throws HsException
	 */
	public void changeApplyMainInfoStatus(String custId, String status)
			throws HsException;
	
	/**
	 * 持卡人身故时调用	
	 * @param perCustId 持卡人客户号
	 * @param status 1：启用、2：挂失、3：停用
	 * @param lossReason 挂失原因,可为空
	 * @param operator 操作员id
	 * @throws HsException
	 */
	@Transactional
	public void updateHsCardStatus(String perCustId,  Integer status, String lossReason,String operator)
			throws HsException;

}
