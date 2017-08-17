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

package com.gy.hsxt.uc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsReserveInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;

/** 
 * 预留信息管理实现类
 * @Package: com.gy.hsxt.uc.common.service  
 * @ClassName: UCAsReserveInfoService 
 * @Description: TODO
 *
 * @author: tianxh
 * @date: 2015-11-9 下午2:17:34 
 * @version V1.0  
 */
@Service
public class UCAsReserveInfoService implements IUCAsReserveInfoService {
	/**
	 * 持卡人管理类
	 */
@Autowired private UCAsCardHolderService cardHolderService;
/**
 * 非持卡人管理类
 */
@Autowired private UCAsNoCardHolderService noCardHolderService;
/**
 * 操作员管理类
 */
@Autowired private IUCAsOperatorService iUCAsOperatorService;
/**
 * 企业管理类
 */
@Autowired private IUCAsEntService iUCAsEntService;

@Autowired private CommonCacheService commonCacheService;
	/**  
	 * 根据客户号查找预留信息
	 * @param custId 客户号
	 * @param userType 用户类型
	 * @return
	 * @throws HsException 
	 */
	@Override
	public String findReserveInfoByCustId(String perCustId, String userType)
			throws HsException {
		if(StringUtils.isBlank(perCustId)){
		    throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号为空");
		}
		if(null == userType){
		    throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数用户类型为空");
		}
		String custId = perCustId.trim();
		String ensureInfo = "";
		// 查找预留信息   非持卡人 1  持卡人2  企业3
		if(userType.equals(UserTypeEnum.CARDER.getType())){
			CardHolder cardHolder = new CardHolder();
			cardHolder.setPerCustId(custId);
			AsCardHolder asCardHolder = cardHolderService.searchCardHolderInfoByCustId(custId);
			ensureInfo = StringUtils.nullToEmpty(asCardHolder.getEnsureInfo());
		}else if(userType.equals(UserTypeEnum.NO_CARDER.getType())){
			AsNoCardHolder asNoCardHolder  = noCardHolderService.searchNoCardHolderInfoByCustId(perCustId);
			ensureInfo = StringUtils.nullToEmpty(asNoCardHolder.getEnsureInfo());
		}else if(userType.equals(UserTypeEnum.ENT.getType())){
			AsEntBaseInfo asEntBaseInfo = iUCAsEntService.searchEntBaseInfo(custId);
			ensureInfo =  StringUtils.nullToEmpty(asEntBaseInfo.getEnsureInfo());
		}else{
		    throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(), ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		return ensureInfo;
	}

	/**  设置预留信息
	 * @param custId   客户号
	 * @param reserveInfo  预留信息
	 * @param userType     用户类型  非持卡人 1  持卡人2  企业3
	 * @throws HsException 
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReserveInfoService#setReserveInfo(java.lang.String, java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum) 
	 */
	@Override
	public void setReserveInfo(String perCustId, String reserveInfo,
			String userType) throws HsException {
		if(StringUtils.isBlank(perCustId)){
		    throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号为空");
		}
		if(StringUtils.isBlank(reserveInfo)){
		    throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数预留信息为空");
		}
		if(null == userType){
		    throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数用户类型为空");
		}
		String custId = perCustId.trim();
		String reseInfo = reserveInfo.trim();
		// 设置预留信息
		if(userType.equals(UserTypeEnum.CARDER.getType())){
			CardHolder cardHolder = new CardHolder();
			cardHolder.setPerCustId(custId);
			cardHolder.setEnsureInfo(reseInfo);
			cardHolder.setCreatedby(custId);
			commonCacheService.updateCardHolderInfo(cardHolder);
		}else if(userType.equals(UserTypeEnum.NO_CARDER.getType())){
			NoCardHolder noCardHolder = new NoCardHolder();
			noCardHolder.setPerCustId(custId);
			noCardHolder.setEnsureInfo(reseInfo);
			noCardHolder.setCreatedby(custId);
			noCardHolderService.updateNoCardHolderInfo(noCardHolder);
		}else if(userType.equals(UserTypeEnum.ENT.getType())){
			AsEntBaseInfo asEntBaseInfo = new AsEntBaseInfo();
			asEntBaseInfo.setEnsureInfo(reserveInfo.trim());
			asEntBaseInfo.setEntCustId(custId);
			iUCAsEntService.updateEntBaseInfo(asEntBaseInfo, custId);
		}
	}
	
}
