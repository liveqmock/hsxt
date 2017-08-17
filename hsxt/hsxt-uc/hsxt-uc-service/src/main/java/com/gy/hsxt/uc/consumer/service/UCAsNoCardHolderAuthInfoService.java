/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.NcRealNameAuth;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.mapper.NcRealNameAuthMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: UCAsCardHolderAuthInfoService
 * @Description: 持卡人证件信息管理（AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-27 下午3:03:27
 * @version V1.0
 */
@Service
public class UCAsNoCardHolderAuthInfoService implements IUCAsNoCardHolderAuthInfoService {

	private static final String MOUDLENAME = "com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderAuthInfoService";
	@Autowired
	private UCAsNoCardHolderService noCardHolderService;
	@Autowired
	private CommonCacheService commonCacheService;
	
	@Autowired
	private NcRealNameAuthMapper ncRealNameAuthMapper;
	
	/**
	 * 查询持卡人实名认证状态
	 * 
	 * @param custId
	 *            客户号
	 * @return String 实名状态
	 * @throws HsException
	 */
	private String findAuthStatus(String perCustId) throws HsException {
		NoCardHolder noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		String realNameStatus = "";
		if (null != noCardHolder.getIsRealnameAuth()) {
			realNameStatus = String.valueOf(noCardHolder.getIsRealnameAuth());
		}
		return realNameStatus;
	}
	/**
	 * 非持卡人实名注册
	 * 
	 * @param asRealNameRegInfo
	 *            实名注册信息
	 * @throws HsException
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void regByRealName(AsRealNameReg asRealNameReg) throws HsException {
		checkParams(asRealNameReg);
		String custId = asRealNameReg.getCustId().trim();
		Timestamp now = StringUtils.getNowTimestamp();
		NcRealNameAuth ncRealNameAuth = new NcRealNameAuth();
		ncRealNameAuth.setAsRealNameRegInfoParams(asRealNameReg);
		isRealNameRegistered(ncRealNameAuth, custId);// 检查证件是否已实名注册
		noCardHolderService.saveRealNameAuthInfo(ncRealNameAuth);// 实名注册信息入库
		// 更新实名状态为已实名注册
		NoCardHolder nocardHolder = new NoCardHolder();
		nocardHolder.setPerCustId(custId);
		nocardHolder.setUpdateDate(now);
		nocardHolder.setUpdatedby(custId);
		nocardHolder.setIsRealnameAuth(2);// 设置实名状态为“已实名注册”
		nocardHolder.setRealnameRegDate(now);
		commonCacheService.updateNoCardHolderInfo(nocardHolder);
	}

	/**
	 * 查询非持卡人实名认证状态
	 * 
	 * @param custId
	 *            客户号
	 * @return String 实名状态
	 * @throws HsException
	 */
	@Override
	public String findAuthStatusByCustId(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		return findAuthStatus(perCustId);
	}
	/**
	 * 通过客户号查询实名认证信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return 实名认证信息
	 * @throws HsException
	 */
	@Override
	public AsRealNameAuth searchRealNameAuthInfo(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号为空");
		}
		AsRealNameAuth asRealNameAuth = new AsRealNameAuth();
		NcRealNameAuth ncRealNameAuth = commonCacheService.searchNoCarderRealNameAuthInfo(perCustId);
		if (null != ncRealNameAuth) {
			ncRealNameAuth.fillAsRealNameAuthInfo(asRealNameAuth);
		}
		NoCardHolder noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		if(null != noCardHolder.getRealnameAuthDate()){
			asRealNameAuth.setRealnameAuthDate(DateUtil.timestampToString(noCardHolder.getRealnameAuthDate(), "yyyy-MM-dd"));
		}
		if(null != noCardHolder.getRealnameRegDate()){
			asRealNameAuth.setRealnameRegDate(DateUtil.timestampToString(noCardHolder.getRealnameRegDate(), "yyyy-MM-dd"));
		}
		if(null != noCardHolder.getIsRealnameAuth()){
			asRealNameAuth.setRealNameStatus(String.valueOf(noCardHolder.getIsRealnameAuth()));
		}
		return asRealNameAuth;
	}

	/**
	 * 检查输入参数
	 * 
	 * @param asRealNameReg
	 *            实名注册信息
	 */
	private void checkParams(AsRealNameReg asRealNameReg) throws HsException {
		if (null == asRealNameReg) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数实名注册信息为空");
		}
		if (StringUtils.isBlank(asRealNameReg.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号为空");
		}
		if (StringUtils.isBlank(asRealNameReg.getRealName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数姓名为空");
		}
		if (StringUtils.isBlank(asRealNameReg.getCertype())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数证件类型为空");
		}
		if("3".equals(asRealNameReg.getCertype())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(), "非持卡人不支持证件类型为营业执照的实名注册");
		}
		if (StringUtils.isBlank(asRealNameReg.getCerNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数证件号码为空");
		}
		String cerType = asRealNameReg.getCertype().trim();
		if (cerType.equals("1") || cerType.equals("2")) {
			if (StringUtils.isBlank(asRealNameReg.getCountryCode())) {
				throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数国家代码为空");
			}
		} else {
			throw new HsException(ErrorCodeEnum.CERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.CERTYPE_IS_ILLEGAL.getDesc());
		}
	}

	/**
	 * 批量查询非持卡人的实名状态
	 * 
	 * @param list
	 *            非持卡人的客户号列表
	 * @return 非持卡人的实名状态
	 * @throws HsException
	 */
	@Override
	public Map<String, String> listAuthStatus(List<String> list) throws HsException {
		if (null == list || 0 == list.size()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号列表为空");
		}
		Map<String, String> authMap = commonCacheService.listNoCarderAuthStatus(list);
		return authMap;
	}
	
	/**
	 * 检查证件是否已注册
	 * 
	 * @param realNameAuth
	 * @param perCustId
	 */
	private void isRealNameRegistered(NcRealNameAuth ncRealNameAuth, String perCustId)
			throws HsException {
		if (checkIDCardIsExist(ncRealNameAuth) || checkRealNameStatus(perCustId)) {
			throw new HsException(ErrorCodeEnum.ID_IS_REGISTERED.getValue(),
					ErrorCodeEnum.ID_IS_REGISTERED.getDesc());
		}
	}
	
	/**
	 * 检查用户的实名状态 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 * 
	 * @param perCustId
	 *            客户号
	 * @return false: 1：未实名注册 true : 其他
	 */
	private boolean checkRealNameStatus(String perCustId) {
		NoCardHolder noCardHolder = commonCacheService.searchNoCardHolderInfo(perCustId);
		CustIdGenerator.isNoCarderExist(noCardHolder, perCustId);
		Integer isRealNameStatus = noCardHolder.getIsRealnameAuth();
		return 1 != isRealNameStatus ? true : false;
	}
	
	/**
	 * 检查证件是否已被他人使用
	 * 
	 * @param perCustId
	 *            个人客户号,null则忽略该条件
	 * @param idType
	 *            证件类型1：身份证 2：护照 3：营业执照
	 * @param idNo
	 *            证件号码
	 * @param perName
	 *            ,null则忽略该条件 姓名
	 * @return 证件已被他人实名注册则返回true
	 */
	public boolean isIdNoExist(String perCustId, Integer idType, String idNo,
			String perName) {
		int count = 0;
		String isactive = "Y";
		try {
			count = ncRealNameAuthMapper.getIdCardNumer(idType, idNo, isactive,
					perCustId, perName);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "isIdNoExist",
					ErrorCodeEnum.CHECK_ID_REGISTERED_FAIL.getDesc()
							+ ",perCustId[" + perCustId + ",idType[" + idType
							+ "],idNo" + idNo + "]", e);
		}
		return count >= 1 ? true : false;
	}
	
	
	/**
	 * 检查证件是否已实名注册
	 * 
	 * @param realNameAuth
	 */
	private boolean checkIDCardIsExist(NcRealNameAuth ncRealNameAuth)
			throws HsException {
		Integer idType = ncRealNameAuth.getIdType();
		String idNo = ncRealNameAuth.getIdNo();
		String perName = ncRealNameAuth.getPerName();
		return isIdNoExist(null, idType, idNo, perName);
	}
}
