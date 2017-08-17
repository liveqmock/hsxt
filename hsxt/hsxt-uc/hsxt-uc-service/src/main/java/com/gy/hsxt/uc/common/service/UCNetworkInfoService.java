/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.common.AsCustPrivacy;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfoMini;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.bean.CustPrivacy;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.mapper.CustPrivacyMapper;
import com.gy.hsxt.uc.common.mapper.NetworkInfoMapper;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 用户网络信息处理类
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCNetworkInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-19 下午5:06:31
 * @version V1.0
 */
@Service
public class UCNetworkInfoService implements IUCAsNetworkInfoService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCNetworkInfoService";
	@Autowired
	NetworkInfoMapper networkInfoMapper;
	@Autowired
	IUCAsCardHolderService cardHolderService;
	@Autowired
	IUCAsNoCardHolderService noCardHolderService;
	@Autowired
	IUCAsOperatorService operatorService;
	@Autowired
	IUCAsEntService entService;
	@Autowired
	private CustIdGenerator generator;
//	@Autowired
//	IUCUserInfoService searchUserService;

	@Autowired
	CommonCacheService commonCacheService;

	@Autowired
	CustPrivacyMapper custPrivacyMapper;
	
	@Autowired
	CommonService commonService;

	/**
	 * 根据客户号搜索网络信息
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService#searchByCustId(java.lang.String,
	 *      com.gy.hsxt.uc.as.bean.enumerate.UserTypeEnum)
	 */
	@Override
	public AsNetworkInfo searchByCustId(String custId) throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为空");
		}
		String cid = custId.trim();
		NetworkInfo net = commonCacheService.searchNetworkInfo(cid);
		if (net == null) {
			return null;
		}
		// 昵称为空的话，非持卡人用手机号替换，持卡人用互生号替换，操作员用操作员姓名替换，企业无网络信息
		String nickName = "";
		if (StringUtils.isBlank(net.getNickname())) {
			nickName = getNiceNameByCustId(cid);
			net.setNickname(nickName);
		}
		AsNetworkInfo info = new AsNetworkInfo();
		BeanUtils.copyProperties(net, info);
		return info;
	}

	/**
	 * 根据客户号查询昵称
	 * 
	 * @param custId
	 *            客户号
	 * @return 昵称
	 */
	private String getNiceNameByCustId(String custId) {
		String userType = generator.getUserTypeByCustId(custId);
		String nickName = "";
		if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			AsNoCardHolder noCardHolder = noCardHolderService
					.searchNoCardHolderInfoByCustId(custId);
			nickName = StringUtils.nullToEmpty(noCardHolder.getMobile());
		} else if (UserTypeEnum.CARDER.getType().equals(userType)) {
			AsCardHolder cardHolder = cardHolderService
					.searchCardHolderInfoByCustId(custId);
			nickName = StringUtils.nullToEmpty(cardHolder.getPerResNo());
		} else if (UserTypeEnum.OPERATOR.getType().equals(userType)) {
			AsOperator operator = operatorService.searchOperByCustId(custId);
			nickName = StringUtils.nullToEmpty(operator.getUserName());
		}
		return nickName;
	}

	/**
	 * 根据客户号查询网络信息
	 * 
	 * @param custIdList
	 *            客户号ID
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService#listNetWorkInfo(java.util.List,
	 *      com.gy.hsxt.uc.as.bean.enumerate.UserTypeEnum)
	 */
	@Override
	public List<AsNetworkInfoMini> listNetWorkInfo(List<String> custIdList)
			throws HsException {
		List<NetworkInfo> list = networkInfoMapper.searchByCustIds(custIdList);
		List<AsNetworkInfoMini> result = new ArrayList<AsNetworkInfoMini>();
		for (NetworkInfo in : list) {
			AsNetworkInfoMini info = new AsNetworkInfoMini();
			BeanUtils.copyProperties(in, info);
			result.add(info);
		}

		return result;

	}

	/**
	 * 修改网络信息
	 * 
	 * @param netWorkInfo
	 *            网络信息
	 * @throws HsException
	 */
	@Override
	public void updateNetworkInfo(AsNetworkInfo netWorkInfo) throws HsException {
		String custId = netWorkInfo.getPerCustId();
		if (netWorkInfo == null
				|| StringUtils.isEmpty(netWorkInfo.getPerCustId())) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		NetworkInfo info = new NetworkInfo();
		BeanUtils.copyProperties(netWorkInfo, info);

		int count = networkInfoMapper.updateByPrimaryKeySelective(info);
		if (count != 1) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc()
							+ JSON.toJSONString(info));
		}
		// 移除缓存中的网络信息
		commonCacheService.removeNetworkInfoCache(custId);
		// 通知搜索引擎更新
		commonService.solrUpdateNetworkInfo(custId);
		
	}

	/**
	 * 获取个人隐私设置
	 * @param custId 客户号
	 * @return
	 * @throws HsException
	 */
	public AsCustPrivacy searchPrivacy(String custId) throws HsException {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为空");
		}
		CustPrivacy ret = custPrivacyMapper.selectByPrimaryKey(custId);
		if (ret == null) {
			return null;
		}
		AsCustPrivacy info = new AsCustPrivacy();
		BeanUtils.copyProperties(ret, info);
		return info;
	}

	/**
	 * 更新个人隐私设置
	 * @param asInfo 隐私设置信息
	 * @throws HsException
	 */
	public void updatePrivacy(AsCustPrivacy asInfo) throws HsException {
		if (asInfo == null || StringUtils.isEmpty(asInfo.getPerCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数为空or客户号为空");
		}
		CustPrivacy info = new CustPrivacy();
		BeanUtils.copyProperties(asInfo, info);
		info.setUpdateDate(StringUtils.getNowTimestamp());
		int count = custPrivacyMapper.updateByPrimaryKeySelective(info);
		if (count == 0) { //不存在则插入
			count = custPrivacyMapper.insertSelective(info);
		} 
		// 通知搜索引擎更新
		try {
			commonService.solrUpdateSearchMode(asInfo.getPerCustId(), asInfo.getSearchMe());
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updatePrivacy", "通知搜索引擎失败", e);
		}

	}

}
