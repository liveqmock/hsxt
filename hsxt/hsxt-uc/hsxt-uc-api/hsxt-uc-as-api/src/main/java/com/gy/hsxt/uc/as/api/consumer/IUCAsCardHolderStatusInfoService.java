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

package com.gy.hsxt.uc.as.api.consumer;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.api.consumer
 * @ClassName: IUCAsCardHolderStatusInfoService
 * @Description: 持卡人状态信息管理（BS业务系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午4:31:07
 * @version V1.0
 */
public interface IUCAsCardHolderStatusInfoService {

	
	/** 互生卡挂失/解挂
	 * @param custId	客户号
	 * @param loginPwd	（AES加密）
	 * @param randomToken	（解密秘钥）
	 * @param status(挂失使用2：挂失    解挂使用1：启用 )
	 * @param lossReason(挂失原因（挂失必填；解挂不填，解挂成功后清空）)
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService#updateHsCardStatus(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateHsCardStatus(String perCustId, String loginPassword,String randomToken,
	        Integer status,String lossReason) throws HsException;
	/**
	 * 持卡人身故状态更新	
	 * @param perCustId 持卡人客户号
	 * @param status 1：启用、2：挂失、3：停用
	 * @param lossReason 挂失原因,可为空
	 * @param operator 操作者id
	 * @throws HsException
	 */
	public void updateHsCardStatus(String perCustId,  Integer status, String lossReason,String operator)
			throws HsException ;
	/**
	 * 查询互生卡信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return Map	key="perResNo"获取互生号，key="cardStatus"获取互生卡状态
	 * @throws HsException	
	 */
	public Map<String,String> searchHsCardStatusInfoBycustId(String custId) throws HsException;

	/**
	 * 手机是否已验证
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @param mobile
	 *            持卡人手机号码
	 * @return true:已验证 false:未验证
	 * @throws HsException
	 */
	public Boolean isVerifiedMobile(String custId, String mobile)
			throws HsException;

	/**
	 * 是否是重要信息变更期间
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return true : 是 false : 不是
	 * @throws HsException
	 */
	public boolean isMainInfoApplyChanging(String custId) throws HsException;
	
	
}
