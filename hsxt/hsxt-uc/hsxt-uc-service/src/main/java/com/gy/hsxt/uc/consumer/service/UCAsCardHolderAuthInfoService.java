/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianConnectionException;
import com.gy.hsec.external.api.UserService;
import com.gy.hsec.external.bean.QueryResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.RealNameAuth;
import com.gy.hsxt.uc.consumer.mapper.CardHolderMapper;
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
public class UCAsCardHolderAuthInfoService implements IUCAsCardHolderAuthInfoService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.consumer.service.UCAsCardHolderAuthInfoService";
	
	@Autowired
	private UCAsCardHolderService cardHolderService;
	@Autowired
	private CardHolderMapper cardHolderMapper;
	@Autowired
	private CommonCacheService commonCacheService;
	@Autowired
	private UserService userService;
	/**
	 * 持卡人实名注册
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
		RealNameAuth realNameAuth = new RealNameAuth();
		realNameAuth.setAsRealNameRegInfoParams(asRealNameReg);
		cardHolderService.isRealNameRegistered(realNameAuth, custId);// 检查证件是否已实名注册
		cardHolderService.saveRealNameAuthInfo(realNameAuth);// 实名注册信息入库
		// 更新实名状态为已实名注册
		CardHolder cardHolder = new CardHolder();
		cardHolder.setPerCustId(custId);
		cardHolder.setUpdateDate(now);
		cardHolder.setUpdatedby(custId);
		cardHolder.setIsRealnameAuth(2);// 设置实名状态为“已实名注册”
		cardHolder.setRealnameRegDate(now);
		cardHolder.setHsCardActiveStatus(1);//设置互生卡的激活状态为1（0:启用：已经开卡状态（系统资源使用数即为此状态的数据） 1:激活：有积分 或 消费者已实名注册）
		commonCacheService.updateCardHolderInfo(cardHolder);
		cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		QueryResult result = null; 
		try {
			result = userService.applyCoupon(custId, cardHolder.getPerResNo());//注册成功后领取抵扣券
			int retCode = result.getRetCode();
			if(200 != retCode){
//				200        成功、201        失败、204        数据不存在、219        企业互生号为空、780        订单不存在、851        企业员工为空、856        员工关联实体店关系为空
				SystemLog.error(MOUDLENAME, "regByRealName", "注册成功后领取抵扣券失败：\r\n", new HsException(retCode, result.getRetMsg()));
			}
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "regByRealName", "注册成功后领取抵扣券异常：\r\n", e);
		}
		
	
	}

	/**
	 * 查询持卡人实名认证状态
	 * 
	 * @param custId
	 *            客户号
	 * @return String 实名状态
	 * @throws HsException
	 */
	@Override
	public String findAuthStatusByCustId(String perCustId) throws HsException {
		return cardHolderService.findAuthStatusByCustId(perCustId);
	}

	
	public AsRealNameReg searchRealNameRegInfo(CardHolder cardHolder)throws HsException {
		if(cardHolder == null){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "持卡人对象为空");
		}
		RealNameAuth realNameAuth = commonCacheService.searchRealNameAuthInfo(cardHolder.getPerCustId());
		AsRealNameReg asRealNameReg = new AsRealNameReg();
		if (null != realNameAuth) {
			asRealNameReg.setCerNo(realNameAuth.getIdNo());
			if(realNameAuth.getIdType() != null){
				asRealNameReg.setCertype(String.valueOf(realNameAuth.getIdType()));
			}
			asRealNameReg.setCountryCode(realNameAuth.getCountryName());
			asRealNameReg.setCountryName(realNameAuth.getCountryName());
			asRealNameReg.setCustId(cardHolder.getPerCustId());
			asRealNameReg.setRealName(StringUtils.nullToEmpty(realNameAuth.getPerName()));
			asRealNameReg.setEntName(StringUtils.nullToEmpty(realNameAuth.getEntName()));
			asRealNameReg.setEntRegAddr(StringUtils.nullToEmpty(realNameAuth.getEntRegAddr()));
		}
		asRealNameReg.setRealnameRegDate(DateUtil.timestampToString(cardHolder.getRealnameRegDate(), "yyyy-MM-dd HH:mm:ss"));
		asRealNameReg.setRealNameStatus(String.valueOf(cardHolder.getIsRealnameAuth()));
		return asRealNameReg;
	}
	
	/**
	 * 通过客户号查询实名注册信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return 实名注册信息
	 * @throws HsException
	 */
	@Override
	public AsRealNameReg searchRealNameRegInfo(String perCustId) throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号为空");
		}
		String custId = perCustId.trim();
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		RealNameAuth realNameAuth = commonCacheService.searchRealNameAuthInfo(custId);;
		AsRealNameReg asRealNameReg = new AsRealNameReg();
		if (null != realNameAuth) {
			realNameAuth.fillBsRealNameRegInfo(asRealNameReg);
		}
		asRealNameReg.setRealnameRegDate(DateUtil.timestampToString(cardHolder.getRealnameRegDate(), "yyyy-MM-dd HH:mm:ss"));
		asRealNameReg.setRealNameStatus(String.valueOf(cardHolder.getIsRealnameAuth()));
		return asRealNameReg;
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
		String custId = perCustId.trim();
		AsRealNameAuth asRealNameAuth = new AsRealNameAuth();
		RealNameAuth realNameAuth = commonCacheService.searchRealNameAuthInfo(custId);
		if (null != realNameAuth) {
			realNameAuth.fillAsRealNameAuthInfo(asRealNameAuth);
		}
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(perCustId);
		CustIdGenerator.isCarderExist(cardHolder, perCustId);
		if(null != cardHolder.getRealnameAuthDate()){
			asRealNameAuth.setRealnameAuthDate(DateUtil.timestampToString(cardHolder.getRealnameAuthDate(), "yyyy-MM-dd"));
		}
		if(null != cardHolder.getRealnameRegDate()){
			asRealNameAuth.setRealnameRegDate(DateUtil.timestampToString(cardHolder.getRealnameRegDate(), "yyyy-MM-dd"));
		}
		if(null != cardHolder.getIsRealnameAuth()){
			asRealNameAuth.setRealNameStatus(String.valueOf(cardHolder.getIsRealnameAuth()));
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
		if (StringUtils.isBlank(asRealNameReg.getCerNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数证件号码为空");
		}
		String cerType = asRealNameReg.getCertype().trim();
		if (cerType.equals("1") || cerType.equals("2")) {
			if (StringUtils.isBlank(asRealNameReg.getCountryCode())) {
				throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数国家代码为空");
			}
		} else if (!cerType.equals("3")) {
			throw new HsException(ErrorCodeEnum.CERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.CERTYPE_IS_ILLEGAL.getDesc());
		}
	}

	/**
	 * 批量查询持卡人的实名状态
	 * 
	 * @param list
	 *            持卡人的客户号列表
	 * @return 持卡人的实名状态
	 * @throws HsException
	 */
	@Override
	public Map<String, String> listAuthStatus(List<String> list) throws HsException {
		Map<String, String> authMap = new HashMap<String, String>();
		if (null == list || 0 == list.size()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "输入参数客户号列表为空");
		}
		List<CardHolder> cardHolderList = null;
		try {
			cardHolderList = cardHolderMapper.listAuthStatus(list);
		} catch (HsException e) {
			throw new HsException(ErrorCodeEnum.BATCH_QUERY_AUTHINFO_ERROR.getValue(),
					e.getMessage());
		}
		String isRealnameAuth = "";
		String perCustId = "";
		for (CardHolder cardHolder : cardHolderList) {
			perCustId = StringUtils.nullToEmpty(cardHolder.getPerCustId());
			if (null != cardHolder.getIsRealnameAuth()) {
				isRealnameAuth = String.valueOf(cardHolder.getIsRealnameAuth());
			}
			authMap.put(perCustId, isRealnameAuth);
		}
		return authMap;
	}

}
