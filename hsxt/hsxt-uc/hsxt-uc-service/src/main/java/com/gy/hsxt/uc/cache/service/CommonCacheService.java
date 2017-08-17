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

package com.gy.hsxt.uc.cache.service;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.keyserver.DeviceToken;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.common.AsPosPointRate;
import com.gy.hsxt.uc.as.bean.enumerate.AsEntBaseStatusEumn;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.OperatorStatus;
import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;
import com.gy.hsxt.uc.checker.mapper.DoubleCheckerMapper;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.mapper.NetworkInfoMapper;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.NcRealNameAuth;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.bean.RealNameAuth;
import com.gy.hsxt.uc.consumer.mapper.CardHolderMapper;
import com.gy.hsxt.uc.consumer.mapper.HsCardMapper;
import com.gy.hsxt.uc.consumer.mapper.NcRealNameAuthMapper;
import com.gy.hsxt.uc.consumer.mapper.NoCardHolderMapper;
import com.gy.hsxt.uc.consumer.mapper.RealNameAuthMapper;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.permission.bean.Permission;
import com.gy.hsxt.uc.permission.bean.Role;
import com.gy.hsxt.uc.permission.mapper.PermissionMapper;
import com.gy.hsxt.uc.permission.mapper.RoleMapper;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;
import com.gy.hsxt.uc.sysoper.mapper.SysOperatorMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.RedisCache;

/**
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: CommonCacheService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-12-21 上午11:34:56
 * @version V1.0
 */
@Service
public class CommonCacheService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.cache.service.CommonCacheService";

	private static final String NEWLINE = "\r\n";
	private static final String FLAG_RECORD_ACTIVED = "N";

	/**
	 * 角色权限数据数据库操作接口
	 */
	@Autowired
	private RoleMapper roleMapper;
	/**
	 * 权限数据数据库操作接口
	 */
	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private SysOperatorMapper sysOperatorMapper;
	/**
	 * 缓存操作类
	 */
	@Autowired
	private RedisCache<Object> cacheUtil;

	@Autowired
	private CardHolderMapper cardHolderMapper;

	@Autowired
	private NoCardHolderMapper noCardHolderMapper;

	@Autowired
	private EntStatusInfoMapper entStatusInfoMapper;

	@Autowired
	private EntExtendInfoMapper entExtendInfoMapper;

	@Autowired
	private NetworkInfoMapper networkInfoMapper;
	/**
	 * 操作员数据库操作接口
	 */
	@Autowired
	private OperatorMapper operatorMapper;

	@Autowired
	private PwdQuestionMapper pwdQuestionMapper;

	@Autowired
	private HsCardMapper hsCardMapper;

	@Autowired
	private RealNameAuthMapper realNameAuthMapper;

	@Autowired
	private NcRealNameAuthMapper ncRealNameAuthMapper;

	@Autowired
	private DoubleCheckerMapper doubleCheckerMapper;

	/**
	 * 查询操作员客户号通过用户名
	 * 
	 * @param entResNo
	 * @param userName
	 * @return
	 * @throws HsException
	 */
	public String getOperCustId(String entResNo, String userName)
			throws HsException {
		String operCustId = getOperCustIdCache(entResNo, userName);// 从缓存里面查找
		if (StringUtils.isNotBlank(operCustId)) {
			return operCustId;
		}
		Operator oper = null;
		try {
			if (entResNo == null || userName == null) {
				return null;
			}
			oper = operatorMapper.selectByUserName(entResNo, userName);// 缓存里面没有查到
																		// ，从数据库查询
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "getOperCustId", "查询操作员客户号通过用户名异常："
					+ NEWLINE, e);
		}
		if (null != oper) {
			operCustId = oper.getOperCustId();
			addOperCustIdCache(entResNo, userName, operCustId);// 客户号放入缓存
			addOperCache(operCustId, oper);// 操作员信息放入缓存
		}
		return operCustId;
	}

	/**
	 * 通过客户号查询互生卡信息
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return
	 */
	public HsCard searchHsCardInfo(String custId) {
		HsCard hsCard = getCardCache(custId);
		if (null != hsCard) {// 缓存中存在，则直接返回
			return hsCard;
		}
		try {
			hsCard = hsCardMapper.selectByPrimaryKey(custId);// 缓存中不存在，则从数据库中获取
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchHsCardInfo", "通过客户号查询互生卡信息异常："
					+ NEWLINE, e);
		}
		if (null != hsCard) {
			addCardCache(custId, hsCard);// 将值放入缓存中
		}
		return hsCard;
	}

	/**
	 * 通过持卡人查询实名认证信息
	 * 
	 * @param custId
	 *            客户号
	 * @return 实名认证信息
	 * @throws HsException
	 */
	public RealNameAuth searchRealNameAuthInfo(String custId) {
		RealNameAuth realNameAuth = getRealAuthCache(custId);
		if (null != realNameAuth) {
			return realNameAuth;// 缓存中存在，则直接返回
		}
		try {
			realNameAuth = realNameAuthMapper.selectByPrimaryKey(custId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchRealNameAuthInfo",
					"通过查询实名认证信息异常：" + NEWLINE, e);
		}
		if (null != realNameAuth) {
			addRealAuthCache(custId, realNameAuth);
		}
		return realNameAuth;
	}

	/**
	 * 查询非持卡人实名认证信息
	 * 
	 * @param custId
	 *            客户号
	 * @return 实名认证信息
	 * @throws HsException
	 */
	public NcRealNameAuth searchNoCarderRealNameAuthInfo(String custId) {
		NcRealNameAuth ncRealNameAuth = getNoCarderRealAuthCache(custId);
		if (null != ncRealNameAuth) {
			return ncRealNameAuth;// 缓存中存在，则直接返回
		}
		try {
			ncRealNameAuth = ncRealNameAuthMapper.selectByPrimaryKey(custId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchRealNameAuthInfo",
					"通过查询实名认证信息异常：" + NEWLINE, e);
		}
		if (null != ncRealNameAuth) {
			addNoCarderRealAuthCache(custId, ncRealNameAuth);
		}
		return ncRealNameAuth;
	}

	/**
	 * 更新持卡人实名认证信息
	 * 
	 * @param realNameAuth
	 *            实名认证信息
	 * @param isDel
	 *            删除标识 true:删除 false:更新
	 * @throws HsException
	 */
	public void updateRealNameAuthInfo(RealNameAuth realNameAuth) {
		try {
			realNameAuthMapper.updateByPrimaryKeySelective(realNameAuth);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateRealNameAuthInfo", "更新实名认证信息异常："
					+ NEWLINE, e);
		}
		removeRealAuthCache(realNameAuth.getPerCustId());
	}

	/**
	 * 更新非持卡人实名认证信息
	 * 
	 * @param realNameAuth
	 *            实名认证信息
	 * @param isDel
	 *            删除标识 true:删除 false:更新
	 * @throws HsException
	 */
	public void updateNoCarderRealNameAuthInfo(NcRealNameAuth ncRealNameAuth) {
		try {
			ncRealNameAuthMapper.updateByPrimaryKeySelective(ncRealNameAuth);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateRealNameAuthInfo", "更新实名认证信息异常："
					+ NEWLINE, e);
		}
		removeRealAuthCache(ncRealNameAuth.getPerCustId());
	}

	/**
	 * 查询企业客户号通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByEntResNo(String entResNo) {
		String entCustId = getEntCustIdCache(entResNo);
		if (entCustId != null) {
			return entCustId;
		}
		try {
			entCustId = entStatusInfoMapper.findEntCustIdByEntResNo(entResNo);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findEntCustIdByEntResNo", entResNo
					+ "查询企业客户号通过企业互生号异常：" + NEWLINE, e);
		}
		if (!StringUtils.isBlank(entCustId)) {
			addEntCustIdCache(entResNo, entCustId);// 添加企业客户号到缓存中
		}
		return entCustId;
	}

	public void CheckEntClose(String entCustId) throws HsException {
		EntStatusInfo statusInfo = searchEntStatusInfo(entCustId);
		if (statusInfo.getBaseStatus().intValue() == AsEntBaseStatusEumn.CANCELED
				.getstatus()) {// 判断企业状态
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					statusInfo.getEntResNo() + "企业已注销，不能登录");
		}
		if (statusInfo.getIsClosedEnt().intValue() == 1) {
			throw new HsException(ErrorCodeEnum.ENT_IS_CLOSED.getValue(),
					statusInfo.getEntResNo()
							+ ErrorCodeEnum.ENT_IS_CLOSED.getDesc());
		}
	}

	/**
	 * 批量查询企业的客户号
	 * 
	 * @param entResnoList
	 *            企业互生号列表
	 * @return
	 * @throws HsException
	 */
	public Map<String, String> findEntCustIdByEntResNo(List<String> entResnoList)
			throws HsException {
		if (entResnoList == null || entResnoList.isEmpty()) { // 入参为空
			return null;
		}
		HashSet<Object> entResNoSet = new HashSet<Object>(entResnoList);
		String key = CacheKeyGen.genEntCustId();
		// //获取缓存中对应entResnoList的客户号
		List<Object> entCustIdList = cacheUtil.getFixRedisUtil().redisTemplate
				.opsForHash().multiGet(key, entResNoSet);
		HashMap<String, String> custIdMap = new HashMap<String, String>();
		String entCustId;
		String entResNo;
		if (entCustIdList != null && !entCustIdList.isEmpty()) {
			for (Object o : entCustIdList) {
				entCustId = (String) o;
				if (!StringUtils.isBlank(entCustId)) {
					custIdMap.put(entCustId.substring(0, 11), entCustId);
				}
			}
			if (custIdMap.size() == entResNoSet.size()) {// 如果从缓存中获取参数中的企业互生号对应的全部的客户号，则返回
				return custIdMap;
			} else {// 否则，缓存中未获取到的客户号，则从库中获取，并将库里面的客户号放入缓存

				for (Object res : entResNoSet) {
					entResNo = (String) res;
					if (!custIdMap.containsKey(entResNo)) {
						entCustId = findEntCustIdByEntResNo(entResNo);
						if (StringUtils.isNotBlank(entCustId)) {
							custIdMap.put(entResNo, entCustId);
						}
					}
				}
				return custIdMap;
			}
		} else {// 缓存里一个也没有，全部从数据库加载
			for (Object res : entResNoSet) {
				entResNo = (String) res;
				entCustId = findEntCustIdByEntResNo(entResNo);
				custIdMap.put(entResNo, entCustId);
			}
		}
		comareReport(entResNoSet, custIdMap);
		return custIdMap;
	}

	
	private void comareReport(Set<Object> entResNoSet,Map<String, String> custIdMap){
		if(entResNoSet.size() == custIdMap.size()){
			return ;
		}
		String entResNo = null;
		Set<String> result = new HashSet<>();
		for(Object o : entResNoSet){
			entResNo = (String)o;
			if(!custIdMap.containsKey(entResNo)){
				result.add(entResNo);
			}
		}
		SystemLog.warn(MOUDLENAME, "comareReport", "未查询到客户号的企业互生号列表[***"+JSON.toJSONString(result)+"***]");
		
	}
	/**
	 * 批量查询企业当前税率
	 * 
	 * @param entResnoList
	 *            企业互生号列表
	 * @return
	 * @throws HsException
	 */
	public Map<String, String> listEntTaxRate(List<String> entResnoList)
			throws HsException {
		if (null == entResnoList || entResnoList.isEmpty()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业互生号为空");
		}
		String key = CacheKeyGen.genEntTaxRate();
		Map<String, String> finalMap = new HashMap<String, String>();
		List<EntExtendInfo> resultList = batchSearchEntTaxRate(entResnoList);
		String entResNo = "";
		String entTaxTate = "";
		for (EntExtendInfo entExtendInfo : resultList) {
			entResNo = StringUtils.nullToEmpty(entExtendInfo.getEntResNo());
			if (null != entExtendInfo.getTaxRate()) {
				entTaxTate = String.valueOf(entExtendInfo.getTaxRate());
			}
			if (!StringUtils.isEmpty(entResNo)
					&& !StringUtils.isEmpty(entTaxTate)) {
				finalMap.put(entResNo, entTaxTate);
			}
		}
		cacheUtil.getFixRedisUtil().redisTemplate.opsForHash().putAll(key,
				finalMap);
		return finalMap;
	}

	/**
	 * 批量查询非持卡人的实名状态
	 * 
	 * @param list
	 *            非持卡人的客户号列表
	 * @return 非持卡人的实名状态
	 * @throws HsException
	 */
	public Map<String, String> listNoCarderAuthStatus(List<String> list) {
		Map<String, String> authMap = new HashMap<String, String>();
		List<NoCardHolder> noCardHolderList = null;
		try {
			noCardHolderList = noCardHolderMapper.listAuthStatus(list);
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "listNoCarderAuthStatus",
					ErrorCodeEnum.BATCH_QUERY_AUTHINFO_ERROR.getDesc()
							+ ",list[" + list.toString() + "]", e);
		}
		String isRealnameAuth = "";
		String perCustId = "";
		for (NoCardHolder noCardHolder : noCardHolderList) {
			perCustId = StringUtils.nullToEmpty(noCardHolder.getPerCustId());
			if (null != noCardHolder.getIsRealnameAuth()) {
				isRealnameAuth = String.valueOf(noCardHolder
						.getIsRealnameAuth());
			}
			authMap.put(perCustId, isRealnameAuth);
		}
		return authMap;
	}

	/**
	 * 查询企业扩展信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	public EntExtendInfo searchEntExtendInfo(String entCustId) {
		EntExtendInfo entExtendInfo = getEntExtendInfoCache(entCustId);// 从缓存里面取企业扩展信息
		if (entExtendInfo != null)
			return entExtendInfo;
		// 缓存没有从数据库里面查询
		try {
			entExtendInfo = entExtendInfoMapper.selectByPrimaryKey(entCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchEntExtendInfo", "查询企业扩展信息异常："
					+ NEWLINE, e);
		}
		if (null != entExtendInfo) {
			addEntExtendInfoCache(entCustId, entExtendInfo);
		}
		return entExtendInfo;
	}

	/**
	 * 查询企业扩展信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	public NetworkInfo searchNetworkInfo(String custId) {
		NetworkInfo networkInfo = getNetworkInfoCache(custId);// 从缓存里面取网络信息
		if (networkInfo != null)
			return networkInfo;
		// 缓存没有从数据库里面查询
		try {
			networkInfo = networkInfoMapper.selectByPrimaryKey(custId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchNetworkInfo", "查询网络信息异常："
					+ NEWLINE, e);
		}
		if (null != networkInfo) {
			addNetworkInfoCache(custId, networkInfo);
		}
		return networkInfo;
	}

	/**
	 * 查询企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public EntStatusInfo searchEntStatusInfo(String entCustId) {
		// 从缓存里面取企业状态信息
		EntStatusInfo entStatusInfo = getEntStatusInfoCache(entCustId);
		if (entStatusInfo != null) {
			return entStatusInfo;
		}
		// 缓存没有取到，从数据库查询
		try {
			entStatusInfo = entStatusInfoMapper.selectByPrimaryKey(entCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchEntStatusInfo", "查询企业状态信息："
					+ NEWLINE, e);
		}
		if (null != entStatusInfo) {
			addEntStatusInfoCache(entCustId, entStatusInfo);// 放入缓存
		}
		return entStatusInfo;
	}

	/**
	 * 更新企业扩展信息(管理公司及地区平台使用)
	 * 
	 * @param entExtInfo
	 *            企业扩展信息
	 * @param operator
	 *            操作用户id
	 * @throws HsException
	 */
	public void updateEntExtInfo(EntExtendInfo extendInfo, String operator) {
		try {
			entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo); // 更新企业扩展信息
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateEntExtInfo",
					ErrorCodeEnum.ENTEXTENDINFO_UPDATE_ERROR.getDesc()
							+ ",entCustId[" + extendInfo.getEntCustId() + "]"
							+ NEWLINE, e);
		}
		// 修改邮箱，置邮箱验证状态为未验证
		if (!StringUtils.isBlank(extendInfo.getContactEmail())) {
			EntStatusInfo entStatusInfo = new EntStatusInfo();
			entStatusInfo.setEntCustId(extendInfo.getEntCustId());
			entStatusInfo.setUpdateDate(extendInfo.getUpdateDate());
			entStatusInfo.setUpdatedby(operator);
			boolean result = checkEntEmailIsRegister(
					extendInfo.getContactEmail(), extendInfo.getEntCustId());
			if (!result) {
				entStatusInfo.setIsAuthEmail(0);
			}
			updateEntStatusInfo(entStatusInfo, operator);// 更新企业状态信息
		}
		removeEntExtendInfoCache(extendInfo.getEntCustId());// 清除缓存中的扩展信息
	}

	/**
	 * 检查邮箱是否已实名注册
	 * 
	 * @param entEmail
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	private boolean checkEntEmailIsRegister(String entEmail, String entCustId)
			throws HsException {
		EntExtendInfo entExtendInfo = searchEntExtendInfo(entCustId);
		String contactEmail = StringUtils.nullToEmpty(entExtendInfo
				.getContactEmail());
		EntStatusInfo entStatusInfo = searchEntStatusInfo(entCustId);
		Integer isAuthEmail = entStatusInfo.getIsAuthEmail();
		if (contactEmail.equals(entEmail) && 1 == isAuthEmail) {
			return true;
		}
		return false;
	}

	/**
	 * 修改企业状态信息
	 * 
	 * @param entStatusInfo
	 *            企业状态信息
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	@Transactional
	public void updateEntStatusInfo(EntStatusInfo entStatusInfo, String operator) {
		try {
			entStatusInfoMapper.updateByPrimaryKeySelective(entStatusInfo);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateEntExtInfo",
					ErrorCodeEnum.UPDATE_ENTSTATUSINFO_ERROR.getDesc()
							+ ",entCustId[" + entStatusInfo.getEntCustId()
							+ "]" + NEWLINE, e);
		}
		removeEntStatusInfoCache(entStatusInfo.getEntCustId());// 清除缓存中的状态信息
	}

	/**
	 * 修改企业状态信息
	 * 
	 * @param entStatusInfo
	 *            企业状态信息
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	@Transactional
	public void updateEntStatusInfoChagTradePwd(EntStatusInfo entStatusInfo,
			String operator) {
		try {
			entStatusInfoMapper.updateByPrimaryKeySelective(entStatusInfo);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateEntStatusInfoChagTradePwd",
					ErrorCodeEnum.UPDATE_ENTSTATUSINFO_ERROR.getDesc()
							+ ",entCustId[" + entStatusInfo.getEntCustId()
							+ "]" + NEWLINE, e);
		}
		removeEntStatusInfoCache(entStatusInfo.getEntCustId());// 删除缓存中的企业状态信息
	}

	/**
	 * 查询操作员客户号 通过操作员互生号
	 * 
	 * @param operResNo
	 *            操作员互生号
	 * @return 操作员客户号
	 * @throws HsException
	 */
	public String findOperCustId(String operResNo) {
		String operCustId = getOperCustIdCache(operResNo);// 从缓存里面查找
		if (StringUtils.isNotBlank(operCustId)) {
			return operCustId;
		}
		try {
			operCustId = operatorMapper.findOperCustIdByOperResNo(operResNo);// 缓存里面没有找到
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findOperCustId", "查询操作员客户号异常"
					+ ",operResNo[" + operResNo + "]" + NEWLINE, e);
		}
		if (StringUtils.isNotBlank(operCustId)) {
			addOperCustIdCache(operResNo, operCustId);// 放入缓存
		}
		return operCustId;
	}

	/**
	 * 查询操作员客户号通过用户名
	 * 
	 * @param entResNo
	 * @param userName
	 * @return
	 * @throws HsException
	 */
	public String findOperCustId(String entResNo, String userName)
			throws HsException {
		String operCustId = getOperCustIdCache(entResNo, userName);// 从缓存里面查找
		if (null != operCustId) {
			return operCustId;
		}
		Operator oper = null;
		try {
			oper = operatorMapper.selectByUserName(entResNo, userName);// 缓存里面没有查到
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findOperCustId", "查询操作员客户号异常："
					+ NEWLINE, e);
		}
		if (null != oper) {
			operCustId = oper.getOperCustId();
			addOperCustIdCache(entResNo, userName, operCustId);// 放入缓存
			addOperCache(operCustId, oper);
		} else {
			SystemLog.info(MOUDLENAME, "findOperCustId", "entResNo[" + entResNo
					+ "]" + "userName[" + userName + "]" + "DB查询操作员客户号无数据"
					+ NEWLINE);
		}
		return operCustId;
	}

	/**
	 * 查询操作员信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#searchOperByCustId(java.lang.String)
	 */
	public Operator searchOperByCustId(String operCustId) {
		Operator oper = getOperCache(operCustId);// 从缓存里面取
		if (oper != null) {
			return oper;
		}
		try {
			oper = operatorMapper.selectByPrimaryKey(operCustId);// 缓存没有 从数据库查询
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchOperByCustId", "查询操作员信息异常："
					+ NEWLINE, e);
		}
		if (null != oper) {
			addOperCache(operCustId, oper);// 放入缓存
		}
		return oper;
	}

	/**
	 * 更新操作员信息
	 * 
	 * @param oper
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 */
	@Transactional
	public void updateOperChgPwd(Operator oper) throws HsException {
		// 检查操作号修改是否合法
		checkUpdate(oper);
		try {
			operatorMapper.updateByPrimaryKeySelective(oper);
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "updateOperChgPwd",
					ErrorCodeEnum.OPERATOR_UPDATE_ERROR.getDesc() + ",oper["
							+ JSON.toJSONString(oper) + "]" + NEWLINE, e);
		}
		String operCustId = oper.getOperCustId();
		Operator operator = searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String operResNo = StringUtils.nullToEmpty(operator.getOperResNo());
		String entResNo = operator.getEntResNo();
		String operNo = operator.getOperNo();
		removeOperCustIdCache(entResNo, operNo);// 删除缓存中的客户号
		if (!StringUtils.isBlank(operResNo)) {
			removeOperCustIdCache(operResNo);// 删除缓存中的客户号
		}
		removeOperCache(operCustId);// 删除缓存中的操作员实体信息
	}

	/**
	 * 更新操作员信息
	 * 
	 * @param oper
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 */
	@Transactional
	public void updateOper(Operator oper) {
		// 检查操作号修改是否合法
		checkUpdate(oper);
		try {
			operatorMapper.updateByPrimaryKeySelective(oper);
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "updateOper",
					ErrorCodeEnum.OPERATOR_UPDATE_ERROR.getDesc() + ",oper["
							+ JSON.toJSONString(oper) + "]" + NEWLINE, e);
		}
		String operCustId = oper.getOperCustId();
		Operator operator = searchOperByCustId(operCustId);
		String operNo = operator.getOperNo();
		String entResNo = operator.getEntResNo();
		CustIdGenerator.isOperExist(operator, operCustId);
		String operResNo = StringUtils.nullToEmpty(operator.getOperResNo());
		removeOperCustIdCache(entResNo, operNo);// 删除缓存中的客户号
		Integer noBindResNo = 0; // 未绑定互生卡
		if (!StringUtils.isBlank(operResNo)
				|| noBindResNo.equals(oper.getIsBindResNo())) {
			// 绑定互生号有修改 或者 解绑互生卡
			removeOperCustIdCache(operResNo);// 删除缓存中的客户号
		}
		removeOperCache(operCustId);// 删除缓存中的操作员实体信息
	}

	/**
	 * 删除操作员信息
	 * 
	 * @param operCustId
	 * @param adminCustId
	 * @throws HsException
	 */
	@Transactional
	public void deleteOper(String operCustId, String adminCustId)
			throws HsException {
		Operator asOper = searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(asOper, operCustId);
		// 执行删除操作
		Operator oper = new Operator();
		oper.setOperCustId(operCustId);
		oper.setIsactive(FLAG_RECORD_ACTIVED);
		oper.setAccountStatus(OperatorStatus.DELETED.getStatus());
		oper.setUpdatedby(adminCustId);
		oper.setUpdateDate(getNowTimestamp());
		try {
			operatorMapper.updateByPrimaryKeySelective(oper);
		} catch (Exception e) {
			SystemLog
					.error(MOUDLENAME, "deleteOper", "删除操作员信息异常：" + NEWLINE, e);
		}
		// 清除缓存...
		String entResNo = asOper.getEntResNo();
		String userName = asOper.getOperNo();
		String operResNo = StringUtils.nullToEmpty(asOper.getOperResNo());
		removeOperCustIdCache(entResNo, userName);// 删除缓存中的客户号
		if (!StringUtils.isBlank(operResNo)) {
			removeOperCustIdCache(operResNo);// 删除缓存中的客户号
		}
		removeOperCache(operCustId);// 删除缓存中的操作员实体信息
		removeOperLoginFailTimesCache(entResNo, userName);// 移除登录密码失败次数
	}

	/**
	 * 根据用户名查询客户号
	 * 
	 * @param userName
	 *            用户名
	 * @return 客户号
	 */
	public String findSysCustIdByUserName(String userName) {
		String custId = getSysCustIdCache(userName);// 从缓存中获取
		if (StringUtils.isNotBlank(custId)) {
			return custId;
		}
		try {
			custId = sysOperatorMapper.selectByUserName(userName, "Y");// 缓存中查询不到，从数据库获取
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findSysCustIdByUserName",
					"根据用户名查询客户号异常：" + NEWLINE, e);
		}
		if (StringUtils.isNotBlank(custId)) {
			addSysCustIdCache(userName, custId);
		}
		return custId;
	}

	/**
	 * 根据用户名查询客户号
	 * 
	 * @param userName
	 *            用户名
	 * @return 客户号
	 */
	public String findSysCustIdSecondByUserName(String userName) {
		String custId = getSysCustIdSecondCache(userName);// 从缓存中获取
		if (StringUtils.isNotBlank(custId)) {
			return custId;
		}
		try {
			custId = sysOperatorMapper.selectOperCustIdSecondByUserName(
					userName, "Y");// 缓存中查询不到，从数据库获取
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findSysCustIdSecondByUserName",
					"根据用户名2查询客户号异常：" + NEWLINE, e);
		}
		if (StringUtils.isNotBlank(custId)) {
			addSysCustIdSecondCache(userName, custId);
		}
		return custId;
	}

	/**
	 * 通过手机号码查询客户号
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 客户号
	 * @throws HsException
	 */
	public String findCustIdByMobile(String mobile) throws HsException {
		String custId = getNoCarderCustIdCache(mobile);
		if (!StringUtils.isBlank(custId)) {// 缓存中存在，则直接返回
			return custId;
		}
		try {
			custId = StringUtils.nullToEmpty(noCardHolderMapper
					.findCustIdByMobile(mobile, "Y"));// 缓存中不存在，则去数据库获取
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findCustIdByMobile", "通过手机号码查询客户号异常："
					+ NEWLINE, e);
		}
		if (StringUtils.isNotBlank(custId)) {
			addNoCarderCustIdCache(mobile, custId);// 放入缓存
		}
		return custId;
	}

	/**
	 * 通过客户号查询非持卡人信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return 非持卡人信息
	 * @throws HsException
	 */
	public NoCardHolder searchNoCardHolderInfo(String perCustId)
			throws HsException {
		NoCardHolder noCardHolder = getNoCarderCache(perCustId);// 先从缓存中获取
		if (null != noCardHolder) {
			return noCardHolder;// 如果存在，则直接返回
		}
		try {
			noCardHolder = noCardHolderMapper.selectByPrimaryKey(perCustId);// 从数据库中查询
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchNoCardHolderInfo",
					"通过客户号查询非持卡人信息异常：" + NEWLINE, e);
		}
		if (null != noCardHolder) {
			addNoCarderCache(perCustId, noCardHolder);// 将数据库中的值存入缓存
		}
		return noCardHolder;
	}

	/**
	 * 更新非持卡人信息
	 * 
	 * @param srcNoCardHolder
	 *            非持卡人信息
	 * @param isDel
	 *            是否删除 true:是 false:否
	 */
	public void updateNoCardHolderInfo(NoCardHolder noCardHolder) {
		String custId = noCardHolder.getPerCustId();
		NoCardHolder nc = searchNoCardHolderInfo(custId);
		CustIdGenerator.isNoCarderExist(nc, custId);
		String mobile = StringUtils.nullToEmpty(nc.getMobile());
		try {
			noCardHolderMapper.updateByPrimaryKeySelective(noCardHolder);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateNoCardHolderInfo", "更新非持卡人信息异常："
					+ NEWLINE, e);
		}
		removeNoCarderCache(custId);// 移除缓存中非持卡人的实体信息
		removeNoCarderCustIdCache(mobile);// 移除缓存中非持卡人的客户号信息
	}

	/**
	 * 持卡人通过互生号查询客户号
	 * 
	 * @param perResNo
	 *            持卡人互生号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	public String findCustIdByResNo(String perResNo) throws HsException {
		String custId = getCarderCustIdCache(perResNo);
		if (!StringUtils.isBlank(custId)) {// 缓存中有值，则直接返回
			return custId;
		}
		try {
			custId = StringUtils.nullToEmpty(hsCardMapper.selectByResNo(
					perResNo, "Y"));// 缓存中没有，则去库中查询
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "findCustIdByResNo", "持卡人通过互生号查询客户号异常："
					+ NEWLINE, e);
		}
		if (StringUtils.isNotBlank(custId)) {
			addCarderCustIdCache(perResNo, custId);// 将值放入缓存
		}
		return custId;
	}

	/**
	 * 通过客户号查询持卡人的信息
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	public CardHolder searchCardHolderInfo(String perCustId) throws HsException {
		CardHolder cardHolder = getCarderCache(perCustId);
		if (null != cardHolder) {
			return cardHolder;// 缓存中存在，则直接返回
		}
		try {
			cardHolder = cardHolderMapper.selectByPrimaryKey(perCustId);// 缓存中不存在，则从数据库中获取
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchCardHolderInfo",
					"通过客户号查询持卡人的信息异常：" + NEWLINE, e);
		}
		if (null != cardHolder) {
			addCarderCache(perCustId, cardHolder);// 将值放入缓存
		}
		return cardHolder;
	}

	/**
	 * 更新持卡人信息
	 * 
	 * @param srcCardHolder
	 *            持卡人信息
	 */
	public void updateCardHolderInfo(CardHolder cardHolder) {
		try {
			cardHolderMapper.updateByPrimaryKeySelective(cardHolder);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateCardHolderInfo",
					ErrorCodeEnum.CARDHOLDER_UPDATE_ERROR.getDesc() + NEWLINE,
					e);
		}
		String custId = cardHolder.getPerCustId();
		removeCarderCustIdCache(cardHolder.getPerResNo());// 删除缓存中的客户号
		removeCardCache(custId);// 删除缓存中的互生卡的信息
		removeCarderCache(custId);// 删除缓存中的持卡人信息
		removeRealAuthCache(custId);// 删除缓存中的实名认证信息
	}

	/**
	 * 检查操作号不能重复
	 * 
	 * @param oper
	 */
	private void checkUpdate(Operator oper) throws HsException {
		String operNo = oper.getOperNo();// 新操作号
		String entResNo = oper.getEntResNo();
		Operator oper_old = null;// 旧操作号
		if (isBlank(operNo)) {
			return;
		}
		if (isBlank(oper.getOperCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc() + ",operCustId["
							+ oper.getOperCustId() + "]");
		}
		oper_old = searchOperByCustId(oper.getOperCustId());
		CustIdGenerator.isOperExist(oper_old, oper.getOperCustId());
		if (isBlank(entResNo)) {
			entResNo = oper_old.getEntResNo();
		}
		String operNo_old = oper_old.getOperNo();
		if (!operNo_old.equals(operNo)) {// 操作号有更改
			if ("0000".equals(operNo_old) && !"0000".equals(operNo)) {
				throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(),
						"0000管理员操作号不能修改");
			}
			Operator oper_other = null;
			try {
				if (isBlank(entResNo) || isBlank(operNo)) {
					throw new HsException(
							ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
							ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
				}
				String operCustId = findOperCustId(entResNo, operNo);
				CustIdGenerator.isOperExist(operCustId, entResNo, operNo);
				oper_other = searchOperByCustId(operCustId);
			} catch (HsException e) {
			}
			if (oper_other != null) {
				throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(),
						operNo + "操作号已经存在");
			}
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<EntExtendInfo> batchSearchEntTaxRate(List<String> dataList)
			throws HsException {
		List<EntExtendInfo> resultList = new ArrayList<EntExtendInfo>();
		List<EntExtendInfo> tempList = null;
		int batchCount = 1000;
		try {
			int len = dataList.size();// 数据长度
			int fromIndex = 0; // 子列表开始位置
			int toIndex = 0;// 子列表结束位置
			if (len > batchCount) {// 数据过多，需要分批多次入库
				int times = len / batchCount; // 循环次数
				for (int i = 0; i <= times; i++) {
					fromIndex = i * batchCount;
					if (i == times) {// 最后一次循环时，子列表末端为列表长度
						toIndex = len;
					} else {
						toIndex = fromIndex + batchCount;
					}
					if (fromIndex < len) {// 刚好 整数1000条时，最后一次 fromIndex==len
						List subList = dataList.subList(fromIndex, toIndex);
						tempList = entExtendInfoMapper
								.batchSelectEntTaxRate(subList);
						if (null != tempList && !tempList.isEmpty()) {
							resultList.addAll(tempList);
							tempList.clear();
						}
					}
				}
			} else {
				tempList = entExtendInfoMapper.batchSelectEntTaxRate(dataList);
				resultList.addAll(tempList);
				if (null != tempList && !tempList.isEmpty()) {
					resultList.addAll(tempList);
					tempList.clear();
				}
			}
			return resultList;
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "batchSearchEntTaxRate",
					"批量查询企业税率失败：\r\n", e);
			throw new HsException(
					ErrorCodeEnum.BATCH_QUERY_ENTTAXRATE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 移除缓存中所有渠道的已登录token
	 * 
	 * @param custId
	 */
	public void removeLoginTokenCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "removeLoginTokenCache",
					"custId is null");
			return;
		}
		ChannelTypeEnum[] ChannelTypes = ChannelTypeEnum.values();
		RedisUtil<Object> changeRedisUtil = cacheUtil.getChangeRedisUtil();
		String loginTokenKey = "";
		for (ChannelTypeEnum ct : ChannelTypes) {
			loginTokenKey = CacheKeyGen.genLoginKey(ct, custId);
			changeRedisUtil.remove(loginTokenKey);
		}
	}

	/**
	 * 设置非持卡人登录密码失败次数的过期时间
	 * 
	 * @param mobile
	 * @param timeout
	 */
	public void setNoCarderLoginFailTimesCacheKeyExpireInSecond(String mobile,
			Long timeout) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME,
					"setNoCarderLoginFailTimesCacheKeyExpireInSecond",
					"mobile is null");
			return;
		}
		if (null == timeout) {
			SystemLog.warn(MOUDLENAME,
					"setNoCarderLoginFailTimesCacheKeyExpireInSecond",
					"timeout is null");
			return;
		}
		String objKey = CacheKeyGen.genNoCardLoginFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(objKey, timeout);
	}

	/**
	 * 设置非持卡人交易密码失败次数的过期时间
	 * 
	 * @param mobile
	 * @param timeout
	 */
	public void setNoCarderTradeFailTimesCacheKeyExpireInSecond(String mobile,
			Long timeout) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME,
					"setNoCarderTradeFailTimesCacheKeyExpireInSecond",
					"mobile is null");
			return;
		}
		if (null == timeout) {
			SystemLog.warn(MOUDLENAME,
					"setNoCarderTradeFailTimesCacheKeyExpireInSecond",
					"timeout is null");
			return;
		}
		String objKey = CacheKeyGen.genNoCardTradeFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(objKey, timeout);
	}

	/**
	 * 设置非持卡人密保失败次数的过期时间
	 * 
	 * @param mobile
	 * @param timeout
	 */
	public void setNoCarderPwdQuestionFailTimesCacheKeyExpireInSecond(
			String mobile, Long timeout) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME,
					"setNoCarderPwdQuestionFailTimesCacheKeyExpireInSecond",
					"mobile is null");
			return;
		}
		if (null == timeout) {
			SystemLog.warn(MOUDLENAME,
					"setNoCarderPwdQuestionFailTimesCacheKeyExpireInSecond",
					"timeout is null");
			return;
		}
		String objKey = CacheKeyGen.genNoCardPwdQuestionFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(objKey, timeout);
	}

	/**
	 * 设置持卡人登录密码失败次数的过期时间（单位秒）
	 * 
	 * @param resNo
	 * @param second
	 */
	public void setCarderLoginFailTimesCacheKeyExpireInSecond(String resNo,
			Long second) {
		if (StringUtils.isBlank(resNo)) {
			SystemLog.warn(MOUDLENAME,
					"setCarderLoginFailTimesCacheKeyExpireInSecond",
					"resNo is null");
			return;
		}
		if (null == second) {
			SystemLog.warn(MOUDLENAME,
					"setCarderLoginFailTimesCacheKeyExpireInSecond",
					"second is null");
			return;
		}
		String objKey = CacheKeyGen.genCarderLoginFailTimesKey(resNo);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(objKey, second);
	}

	/**
	 * 设置持卡人交易密码失败次数的过期时间（单位秒）
	 * 
	 * @param resNo
	 * @param second
	 */
	public void setCarderTradeFailTimesCacheKeyExpireInSecond(String resNo,
			Long second) {
		if (StringUtils.isBlank(resNo)) {
			SystemLog.warn(MOUDLENAME,
					"setCarderTradeFailTimesCacheKeyExpireInSecond",
					"resNo is null");
			return;
		}
		if (null == second) {
			SystemLog.warn(MOUDLENAME,
					"setCarderTradeFailTimesCacheKeyExpireInSecond",
					"second is null");
			return;
		}
		String objKey = CacheKeyGen.genCarderTradeFailTimesKey(resNo);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(objKey, second);
	}

	/**
	 * 设置持卡人密保失败次数的过期时间（单位秒）
	 * 
	 * @param resNo
	 * @param second
	 */
	public void setCarderPwdQuestionFailTimesCacheKeyExpireInSecond(
			String resNo, Long second) {
		if (StringUtils.isBlank(resNo)) {
			SystemLog.warn(MOUDLENAME,
					"setCarderPwdQuestionFailTimesCacheKeyExpireInSecond",
					"resNo is null");
			return;
		}
		if (null == second) {
			SystemLog.warn(MOUDLENAME,
					"setCarderPwdQuestionFailTimesCacheKeyExpireInSecond",
					"second is null");
			return;
		}
		String objKey = CacheKeyGen.genCarderPwdQuestionFailTimesKey(resNo);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(objKey, second);
	}

	/**
	 * 删除缓存中的操作员客户号
	 * 
	 * @param operResNo
	 *            操作员的互生号
	 */
	public void removeOperCustIdCache(String operResNo) {
		if (StringUtils.isBlank(operResNo)) {
			SystemLog.warn(MOUDLENAME, "removeOperCustIdCache",
					"operResNo is null");
			return;
		}
		String custIdKey = CacheKeyGen.genOperCustId(operResNo);
		cacheUtil.getFixRedisUtil().remove(custIdKey);
	}

	/**
	 * 查询缓存中的操作员客户号
	 * 
	 * @param operResNo
	 *            操作员的互生号
	 */
	public String getOperCustIdCache(String operResNo) {
		if (StringUtils.isBlank(operResNo)) {
			SystemLog.warn(MOUDLENAME, "getOperCustIdCache",
					"operResNo is null");
			return null;
		}
		String custIdKey = CacheKeyGen.genOperCustId(operResNo);
		return (String) cacheUtil.getFixRedisUtil()
				.get(custIdKey, String.class);
	}

	/**
	 * 添加操作员客户号到缓存中
	 * 
	 * @param operResNo
	 *            操作员的互生号
	 */
	public void addOperCustIdCache(String operResNo, String operCustId) {
		if (StringUtils.isBlank(operResNo)) {
			SystemLog.warn(MOUDLENAME, "getOperCustIdCache",
					"operResNo is null");
			return;
		}
		if (StringUtils.isBlank(operCustId)) {
			SystemLog.warn(MOUDLENAME, "getOperCustIdCache",
					"operCustId is null");
			return;
		}
		String custIdKey = CacheKeyGen.genOperCustId(operResNo);
		cacheUtil.getFixRedisUtil().add(custIdKey, operCustId);
	}

	/**
	 * 设置缓存中的操作员登录失败次数key失效时间（单位：秒）
	 * 
	 * @param operCustId
	 * @param second
	 */
	public void setOperFailTimesCacheKeyExpireInSecond(String entResNo,
			String userName, Long second) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME,
					"setOperFailTimesCacheKeyExpireInSecond",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME,
					"setOperFailTimesCacheKeyExpireInSecond",
					"userName is null");
			return;
		}
		if (null == second) {
			SystemLog.warn(MOUDLENAME,
					"setOperFailTimesCacheKeyExpireInSecond", "second is null");
			return;
		}
		String operLoginKey = CacheKeyGen.genOperLoginFailTimesKey(entResNo,
				userName);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(operLoginKey, second);
	}

	/**
	 * 设置缓存中的系统操作员登录失败次数key失效时间（单位：秒）
	 * 
	 * @param operCustId
	 * @param second
	 */
	public void setSysOperLoginFailTimesCacheKeyExpireInSecond(String userName,
			Long second) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME,
					"setSysOperLoginFailTimesCacheKeyExpireInSecond",
					"userName is null");
			return;
		}
		if (null == second) {
			SystemLog.warn(MOUDLENAME,
					"setSysOperLoginFailTimesCacheKeyExpireInSecond",
					"second is null");
			return;
		}
		String operLoginKey = CacheKeyGen.genSystemLoginFailTimesKey(userName);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(operLoginKey, second);
	}

	/**
	 * 移除缓存中的持卡人实体信息
	 * 
	 * @param perCustId
	 */
	public void removeCarderCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog
					.warn(MOUDLENAME, "removeCarderCache", "perCustId is null");
			return;
		}
		String objKey = CacheKeyGen.genExtendCarderInfoKey(perCustId);
		cacheUtil.getFixRedisUtil().remove(objKey);
	}

	/**
	 * 添加持卡人实体信息到缓存中
	 * 
	 * @param perCustId
	 */
	public void addCarderCache(String perCustId, CardHolder cardHolder) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "addCarderCache", "perCustId is null");
			return;
		}
		if (null == cardHolder) {
			SystemLog.warn(MOUDLENAME, "addCarderCache", "cardHolder is null");
			return;
		}
		String objKey = CacheKeyGen.genExtendCarderInfoKey(perCustId);
		cacheUtil.getFixRedisUtil().add(objKey, cardHolder);
	}

	/**
	 * 查询缓存中的持卡人实体信息
	 * 
	 * @param perCustId
	 */
	public CardHolder getCarderCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "getCarderCache", "perCustId is null");
			return null;
		}
		String objKey = CacheKeyGen.genExtendCarderInfoKey(perCustId);
		return (CardHolder) cacheUtil.getFixRedisUtil().get(objKey,
				CardHolder.class);
	}

	/**
	 * 移除缓存中的持卡人的客户号
	 * 
	 * @param perResNo
	 */
	public void removeCarderCustIdCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "removeCarderCustIdCache",
					"perResNo is null");
			return;
		}
		String custIdKey = CacheKeyGen.genPerCustId(perResNo);
		cacheUtil.getFixRedisUtil().remove(custIdKey);
	}

	/**
	 * 添加持卡人的客户号到缓存中
	 * 
	 * @param perResNo
	 */
	public void addCarderCustIdCache(String perResNo, String perCustId) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "addCarderCustIdCache",
					"perResNo is null");
			return;
		}
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "addCarderCustIdCache",
					"perCustId is null");
			return;
		}
		String custIdKey = CacheKeyGen.genPerCustId(perResNo);
		cacheUtil.getFixRedisUtil().add(custIdKey, perCustId);
	}

	/**
	 * 移除缓存中的持卡人的客户号
	 * 
	 * @param perResNo
	 */
	public String getCarderCustIdCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "getCarderCustIdCache",
					"perResNo is null");
			return null;
		}
		String custIdKey = CacheKeyGen.genPerCustId(perResNo);
		return (String) cacheUtil.getFixRedisUtil()
				.get(custIdKey, String.class);
	}

	/**
	 * 设置缓存中的企业交易密码的失败次数key的失效时间（单位：秒）
	 * 
	 * @param entResNo
	 * @param timeout
	 */
	public void setEntTradePwdFailTimesKeyExpireInSecond(String entResNo,
			Long timeout) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME,
					"setEntTradePwdFailTimesKeyExpireInSecond",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(entResNo) || null == timeout) {
			SystemLog.warn(MOUDLENAME,
					"setEntTradePwdFailTimesKeyExpireInSecond",
					"timeout is null");
			return;
		}
		String tradePwdKey = CacheKeyGen.genEntTradeFailTimesKey(entResNo);
		SystemLog.debug(MOUDLENAME, "setEntTradePwdFailTimesKeyExpireInSecond",
				entResNo + "的交易密码锁定时间：" + timeout);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(tradePwdKey, timeout);
	}

	/**
	 * 设置缓存中的企业密保的失败次数key的失效时间（单位：秒）
	 * 
	 * @param entResNo
	 * @param timeout
	 */
	public void setEntCyptFailTimesKeyExpireInSecond(String entResNo,
			Long timeout) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "setEntCyptFailTimesKeyExpireInSecond",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(entResNo) || null == timeout) {
			SystemLog.warn(MOUDLENAME, "setEntCyptFailTimesKeyExpireInSecond",
					"timeout is null");
			return;
		}
		String key = CacheKeyGen.genEntPwdQuestionFailTimesKey(entResNo);
		SystemLog.debug(MOUDLENAME, "setEntCyptFailTimesKeyExpireInSecond",
				entResNo + "的密保锁定时间：" + timeout);
		cacheUtil.getChangeRedisUtil().setExpireInSecond(key, timeout);
	}

	/**
	 * 添加互生卡的信息到缓存
	 * 
	 * @param perCustId
	 */
	public void addCardCache(String perCustId, HsCard hsCard) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "addCardCache", "perCustId is null");
			return;
		}
		if (null == hsCard) {
			SystemLog.warn(MOUDLENAME, "addCardCache", "hsCard is null");
			return;
		}
		String cardKey = CacheKeyGen.genCardKey(perCustId);
		cacheUtil.getFixRedisUtil().add(cardKey, hsCard);
	}

	/**
	 * 查询缓存中的互生卡的信息
	 * 
	 * @param perCustId
	 */
	public HsCard getCardCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "getCardCache", "perCustId is null");
			return null;
		}
		String cardKey = CacheKeyGen.genCardKey(perCustId);
		return (HsCard) cacheUtil.getFixRedisUtil().get(cardKey, HsCard.class);
	}

	/**
	 * 移除缓存中的互生卡的信息
	 * 
	 * @param perCustId
	 */
	public void removeCardCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "removeCardCache", "perCustId is null");
			return;
		}
		String cardKey = CacheKeyGen.genCardKey(perCustId);
		cacheUtil.getFixRedisUtil().remove(cardKey);
	}

	/**
	 * 移除缓存中的实名认证信息
	 * 
	 * @param perCustId
	 */
	public void removeRealAuthCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "removeRealAuthCache",
					"perCustId is null");
			return;
		}
		String realAuthKey = CacheKeyGen.genRealAuthKey(perCustId);
		cacheUtil.getFixRedisUtil().remove(realAuthKey);
	}

	/**
	 * 移除缓存中的实名认证信息
	 * 
	 * @param perCustId
	 */
	public void removeNoCarderRealAuthCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "removeNoCarderRealAuthCache",
					"perCustId is null");
			return;
		}
		String realAuthKey = CacheKeyGen.genRealAuthKey(perCustId);
		cacheUtil.getFixRedisUtil().remove(realAuthKey);
	}

	/**
	 * 添加持卡人实名认证信息到缓存中
	 * 
	 * @param perCustId
	 */
	public void addRealAuthCache(String perCustId, RealNameAuth realNameAuth) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "addRealAuthCache", "perCustId is null");
			return;
		}
		if (null == realNameAuth) {
			SystemLog.warn(MOUDLENAME, "addRealAuthCache",
					"realNameAuth is null");
			return;
		}
		String realAuthKey = CacheKeyGen.genRealAuthKey(perCustId);
		cacheUtil.getFixRedisUtil().add(realAuthKey, realNameAuth);
	}

	/**
	 * 添加非持卡人实名认证信息到缓存中
	 * 
	 * @param perCustId
	 */
	public void addNoCarderRealAuthCache(String perCustId,
			NcRealNameAuth ncRealNameAuth) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "addNoCarderRealAuthCache",
					"perCustId is null");
			return;
		}
		if (null == ncRealNameAuth) {
			SystemLog.warn(MOUDLENAME, "addNoCarderRealAuthCache",
					"ncRealNameAuth is null");
			return;
		}
		String realAuthKey = CacheKeyGen.genRealAuthKey(perCustId);
		cacheUtil.getFixRedisUtil().add(realAuthKey, ncRealNameAuth);
	}

	/**
	 * 查询缓存中的实名认证信息
	 * 
	 * @param perCustId
	 */
	public RealNameAuth getRealAuthCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "getRealAuthCache", "perCustId is null");
			return null;
		}
		String realAuthKey = CacheKeyGen.genRealAuthKey(perCustId);
		return (RealNameAuth) cacheUtil.getFixRedisUtil().get(realAuthKey,
				RealNameAuth.class);
	}

	/**
	 * 查询缓存中的实名认证信息
	 * 
	 * @param perCustId
	 */
	public NcRealNameAuth getNoCarderRealAuthCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "getNoCarderRealAuthCache",
					"perCustId is null");
			return null;
		}
		String realAuthKey = CacheKeyGen.genRealAuthKey(perCustId);
		return (NcRealNameAuth) cacheUtil.getFixRedisUtil().get(realAuthKey,
				NcRealNameAuth.class);
	}

	/**
	 * 删除缓存中的企业客户号信息
	 * 
	 * @param entResNo
	 */
	public void removeEntCustIdCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removeEntCustIdCache",
					"entResNo is null");
			return;
		}
		String key = CacheKeyGen.genEntCustId();
		cacheUtil.getFixRedisUtil().delStringFromHash(key, entResNo);
	}

	/**
	 * 添加企业客户号到缓存中
	 * 
	 * @param entResNo
	 */
	public void addEntCustIdCache(String entResNo, String entCustId) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addEntCustIdCache", "entResNo is null");
			return;
		}
		if (StringUtils.isBlank(entCustId)) {
			SystemLog
					.warn(MOUDLENAME, "addEntCustIdCache", "entCustId is null");
			return;
		}
		String key = CacheKeyGen.genEntCustId();
		cacheUtil.getFixRedisUtil().setStringToHash(key, entResNo, entCustId);
	}

	/**
	 * 从缓存中查询企业客户号
	 * 
	 * @param entResNo
	 * @return
	 */
	public String getEntCustIdCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getEntCustIdCache", "entResNo is null");
			return null;
		}
		String key = CacheKeyGen.genEntCustId();
		return cacheUtil.getFixRedisUtil().getStringFromHash(key, entResNo);
	}

	/**
	 * 移除缓存中的企业扩展信息
	 * 
	 * @param entCustId
	 */
	public void removeEntExtendInfoCache(String entCustId) {
		if (StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "removeEntExtendInfoCache",
					"entCustId is null");
			return;
		}
		String objKey = CacheKeyGen.genExtendEntInfoKey(entCustId);
		cacheUtil.getFixRedisUtil().remove(objKey);
	}

	/**
	 * 移除缓存中的网络信息
	 * 
	 * @param entCustId
	 */
	public void removeNetworkInfoCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "removeNetworkInfoCache",
					"custId is null");
			return;
		}
		String objKey = CacheKeyGen.genNetworkKey(custId);
		cacheUtil.getFixRedisUtil().remove(objKey);
	}

	/**
	 * 添加企业扩展信息到缓存中
	 * 
	 * @param entCustId
	 */
	public void addEntExtendInfoCache(String entCustId,
			EntExtendInfo entExtendInfo) {
		if (StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "addEntExtendInfoCache",
					"entCustId is null");
			return;
		}
		if (null == entExtendInfo) {
			SystemLog.warn(MOUDLENAME, "addEntExtendInfoCache",
					"entExtendInfo is null");
			return;
		}
		String objKey = CacheKeyGen.genExtendEntInfoKey(entCustId);
		cacheUtil.getFixRedisUtil().add(objKey, entExtendInfo);
	}

	/**
	 * 添加企业扩展信息到缓存中
	 * 
	 * @param entCustId
	 */
	public void addNetworkInfoCache(String custId, NetworkInfo networkInfo) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addNetworkInfoCache", "custId is null");
			return;
		}
		if (null == networkInfo) {
			SystemLog.warn(MOUDLENAME, "addNetworkInfoCache",
					"networkInfo is null");
			return;
		}
		String objKey = CacheKeyGen.genNetworkKey(custId);
		cacheUtil.getFixRedisUtil().add(objKey, networkInfo);
	}

	/**
	 * 从缓存中的查询企业扩展信息
	 * 
	 * @param entCustId
	 */
	public EntExtendInfo getEntExtendInfoCache(String entCustId) {
		if (StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "getEntExtendInfoCache",
					"entCustId is null");
			return null;
		}
		String objKey = CacheKeyGen.genExtendEntInfoKey(entCustId);
		return (EntExtendInfo) cacheUtil.getFixRedisUtil().get(objKey,
				EntExtendInfo.class);
	}

	/**
	 * 从缓存中的查询网络信息
	 * 
	 * @param entCustId
	 */
	public NetworkInfo getNetworkInfoCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getNetworkInfoCache", "custId is null");
			return null;
		}
		String objKey = CacheKeyGen.genNetworkKey(custId);
		return (NetworkInfo) cacheUtil.getFixRedisUtil().get(objKey,
				NetworkInfo.class);
	}

	/**
	 * 移除缓存中的企业状态信息
	 * 
	 * @param entCustId
	 */
	public void removeEntStatusInfoCache(String entCustId) {
		if (StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "removeEntStatusInfoCache",
					"entCustId is null");
			return;
		}
		String objKey = CacheKeyGen.genStatusInfoKey(entCustId);
		cacheUtil.getChangeRedisUtil().remove(objKey);
	}

	/**
	 * 添加企业状态信息到缓存
	 * 
	 * @param entCustId
	 * @param entStatusInfo
	 */
	public void addEntStatusInfoCache(String entCustId,
			EntStatusInfo entStatusInfo) {
		if (StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "addEntStatusInfoCache",
					"entCustId is null");
			return;
		}
		if (null == entStatusInfo) {
			SystemLog.warn(MOUDLENAME, "addEntStatusInfoCache",
					"entStatusInfo is null");
			return;
		}
		String objKey = CacheKeyGen.genStatusInfoKey(entCustId);
		cacheUtil.getChangeRedisUtil().add(objKey, entStatusInfo);
	}

	/**
	 * 从缓存中查询企业状态信息到
	 * 
	 * @param entCustId
	 * @param entStatusInfo
	 */
	public EntStatusInfo getEntStatusInfoCache(String entCustId) {
		if (StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "getEntStatusInfoCache",
					"entCustId is null");
			return null;
		}
		String objKey = CacheKeyGen.genStatusInfoKey(entCustId);
		return (EntStatusInfo) cacheUtil.getChangeRedisUtil().get(objKey,
				EntStatusInfo.class);
	}

	/**
	 * 移除缓存中操作员的客户号
	 * 
	 * @param entResNo
	 * @param userName
	 */
	public void removeOperCustIdCache(String entResNo, String userName) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removeOperCustIdCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "removeOperCustIdCache",
					"userName is null");
			return;
		}
		String custIdKey = CacheKeyGen.genOperCustId(entResNo, userName);
		cacheUtil.getFixRedisUtil().remove(custIdKey);
	}

	/**
	 * 查询缓存中操作员的客户号
	 * 
	 * @param entResNo
	 * @param userName
	 */
	public String getOperCustIdCache(String entResNo, String userName) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getOperCustIdCache",
					"entResNo is null,userName=" + userName);
			// 打印调用堆栈信息以便跟踪错误原因
			Throwable ex = new Throwable();
			ex.printStackTrace();
			return null;
		}
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "getOperCustIdCache",
					"userName is null,entResNo=" + entResNo);
			// 打印调用堆栈信息以便跟踪错误原因
			Throwable ex = new Throwable();
			ex.printStackTrace();
			return null;
		}
		String custIdKey = CacheKeyGen.genOperCustId(entResNo, userName);
		return (String) cacheUtil.getFixRedisUtil()
				.get(custIdKey, String.class);
	}

	/**
	 * 添加操作员的客户号到缓存中
	 * 
	 * @param entResNo
	 * @param userName
	 */
	public void addOperCustIdCache(String entResNo, String userName,
			String operCustId) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog
					.warn(MOUDLENAME, "addOperCustIdCache", "entResNo is null");
			return;
		}
		if (StringUtils.isBlank(userName)) {
			SystemLog
					.warn(MOUDLENAME, "addOperCustIdCache", "userName is null");
			return;
		}
		if (StringUtils.isBlank(operCustId)) {
			SystemLog.warn(MOUDLENAME, "addOperCustIdCache", "operCustId");
			return;
		}
		String custIdKey = CacheKeyGen.genOperCustId(entResNo, userName);
		cacheUtil.getFixRedisUtil().add(custIdKey, operCustId);
	}

	/**
	 * 删除缓存中的操作员信息
	 * 
	 * @param operCustId
	 */
	public void removeOperCache(String operCustId) {
		if (StringUtils.isBlank(operCustId)) {
			SystemLog.warn(MOUDLENAME, "removeOperCache", "operCustId is null");
			return;
		}
		String extendKey = CacheKeyGen.genExtendOperKey(operCustId);
		cacheUtil.getFixRedisUtil().remove(extendKey);
	}

	/**
	 * 查询缓存中的操作员信息
	 * 
	 * @param operCustId
	 */
	public Operator getOperCache(String operCustId) {
		if (StringUtils.isBlank(operCustId)) {
			SystemLog.warn(MOUDLENAME, "getOperCache", "operCustId is null");
			return null;
		}
		String extendKey = CacheKeyGen.genExtendOperKey(operCustId);
		return (Operator) cacheUtil.getFixRedisUtil().get(extendKey,
				Operator.class);
	}

	/**
	 * 添加操作员信息到缓存中
	 * 
	 * @param operCustId
	 * @param operator
	 */
	public void addOperCache(String operCustId, Operator operator) {
		if (StringUtils.isBlank(operCustId)) {
			SystemLog.warn(MOUDLENAME, "addOperCache", "operCustId is null");
			return;
		}
		if (null == operator) {
			SystemLog.warn(MOUDLENAME, "addOperCache", "operator is null");
			return;
		}
		String extendKey = CacheKeyGen.genExtendOperKey(operCustId);
		cacheUtil.getFixRedisUtil().add(extendKey, operator);
	}

	/**
	 * 移除缓存中系统操作员的客户号
	 * 
	 * @param userName
	 */
	public void removeSysCustIdCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "removeSysCustIdCache",
					"userName is null");
			return;
		}
		String custIdKey = CacheKeyGen.genSysCustId(userName);
		cacheUtil.getFixRedisUtil().remove(custIdKey);
	}

	/**
	 * 添加系统操作员的客户号到缓存中
	 * 
	 * @param userName
	 */
	public void addSysCustIdCache(String userName, String custId) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "addSysCustIdCache", "userName is null");
			return;
		}
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addSysCustIdCache", "custId is null");
			return;
		}
		String custIdKey = CacheKeyGen.genSysCustId(userName);
		cacheUtil.getFixRedisUtil().add(custIdKey, custId);
	}

	/**
	 * 添加系统操作员2的客户号到缓存中
	 * 
	 * @param userName
	 */
	public void addSysCustIdSecondCache(String userName, String custId) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "addSysCustIdCache", "userName is null");
			return;
		}
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addSysCustIdCache", "custId is null");
			return;
		}
		String custIdKey = CacheKeyGen.genSysCustIdSecond(userName);
		cacheUtil.getFixRedisUtil().add(custIdKey, custId);
	}

	/**
	 * 查询缓存中系统操作员的客户号
	 * 
	 * @param userName
	 */
	public String getSysCustIdCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "getSysCustIdCache", "userName is null");
			return null;
		}
		String custIdKey = CacheKeyGen.genSysCustId(userName);
		return (String) cacheUtil.getFixRedisUtil()
				.get(custIdKey, String.class);
	}

	/**
	 * 添加系统操作员2的客户号到缓存中
	 * 
	 * @param userName
	 */
	public String getSysCustIdSecondCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "getSysCustIdSecondCache",
					"userName is null");
			return null;
		}
		String custIdKey = CacheKeyGen.genSysCustIdSecond(userName);
		return (String) cacheUtil.getFixRedisUtil()
				.get(custIdKey, String.class);
	}

	/**
	 * 移除缓存中的系统用户信息
	 * 
	 * @param custId
	 */
	public void removeSysOperCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "removeSysOperCache", "custId is null");
			return;
		}
		String key = CacheKeyGen.genExtendSysInfoKey(custId);
		cacheUtil.getFixRedisUtil().remove(key);
	}

	/**
	 * 移除缓存中的双签用户信息
	 * 
	 * @param custId
	 */
	public void removeDoubleCheckerCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "removeDoubleCheckerCache",
					"custId is null");
			return;
		}
		String key = CacheKeyGen.genExtendCheckerInfoKey(custId);
		cacheUtil.getFixRedisUtil().remove(key);
	}

	/**
	 * 查询缓存中的双签用户信息
	 * 
	 * @param custId
	 */
	public DoubleChecker getDoubleCheckerCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getDoubleCheckerCache",
					"custId is null");
			return null;
		}
		String key = CacheKeyGen.genExtendCheckerInfoKey(custId);
		return (DoubleChecker) cacheUtil.getFixRedisUtil().get(key,
				DoubleChecker.class);
	}

	/**
	 * 添加双签用户信息到缓存中
	 * 
	 * @param custId
	 */
	public void addDoubleCheckerCache(String custId, DoubleChecker checker) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addDoubleCheckerCache",
					"custId is null");
			return;
		}
		if (StringUtils.isBlank(checker)) {
			SystemLog.warn(MOUDLENAME, "addDoubleCheckerCache",
					"checker is null");
			return;
		}
		String key = CacheKeyGen.genExtendCheckerInfoKey(custId);
		cacheUtil.getFixRedisUtil().add(key, checker);
	}

	/**
	 * 添加缓存中的系统用户信息
	 * 
	 * @param custId
	 */
	public void addSysOperCache(String custId, SysOperator sysOperator) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addSysOperCache", "custId is null");
			return;
		}
		if (null == sysOperator) {
			SystemLog
					.warn(MOUDLENAME, "addSysOperCache", "sysOperator is null");
			return;
		}
		String key = CacheKeyGen.genExtendSysInfoKey(custId);
		cacheUtil.getFixRedisUtil().add(key, sysOperator);
	}

	/**
	 * 添加缓存中的系统用户信息
	 * 
	 * @param custId
	 */
	public void addSysOperSecondCache(String custId, SysOperator sysOperator) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addSysOperCache", "custId is null");
			return;
		}
		if (null == sysOperator) {
			SystemLog
					.warn(MOUDLENAME, "addSysOperCache", "sysOperator is null");
			return;
		}
		DoubleChecker checker = new DoubleChecker();
		BeanUtils.copyProperties(sysOperator, checker);
		String key = CacheKeyGen.genExtendCheckerInfoKey(custId);
		cacheUtil.getFixRedisUtil().add(key, checker);
	}

	/**
	 * 查询缓存中的系统用户信息
	 * 
	 * @param custId
	 */
	public SysOperator getSysOperCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getSysOperCache", "custId is null");
			return null;
		}
		String key = CacheKeyGen.genExtendSysInfoKey(custId);
		return (SysOperator) cacheUtil.getFixRedisUtil().get(key,
				SysOperator.class);
	}

	/**
	 * 查询缓存中的系统用户信息
	 * 
	 * @param custId
	 */
	public SysOperator getSysOperSecondCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getSysOperCache", "custId is null");
			return null;
		}
		String key = CacheKeyGen.genExtendCheckerInfoKey(custId);
		DoubleChecker checker = (DoubleChecker) cacheUtil.getFixRedisUtil()
				.get(key, DoubleChecker.class);
		if (null == checker) {
			return null;
		}
		SysOperator sysOper = new SysOperator();
		BeanUtils.copyProperties(checker, sysOper);
		return sysOper;
	}

	/**
	 * 通过客户号查询系统用户信息
	 * 
	 * @param sysCustId
	 *            客户号
	 * @return 系统用户信息
	 */
	public SysOperator searchSysOperInfo(String sysCustId) {
		SysOperator sysOperator = getSysOperCache(sysCustId);
		if (null != sysOperator) {
			return sysOperator;
		}
		try {
			sysOperator = sysOperatorMapper.selectByPrimaryKey(sysCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchSysOperInfoByCustIdNoWhere",
					"通过客户号查询系统用户信息异常：" + NEWLINE, e);
		}
		if (null != sysOperator) {
			addSysOperCache(sysCustId, sysOperator);
		}
		return sysOperator;
	}

	/**
	 * 通过客户号查询系统用户信息
	 * 
	 * @param sysCustId
	 *            客户号
	 * @return 系统用户信息
	 */
	public SysOperator searchSysOperSecondInfo(String sysCustId) {
		SysOperator sysOperator = getSysOperSecondCache(sysCustId);
		if (null != sysOperator) {
			return sysOperator;
		}
		try {
			sysOperator = sysOperatorMapper
					.selectOperatorSecondPwdByCustId(sysCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchSysOperSecondInfo",
					"通过客户号查询系统用户信息异常：" + NEWLINE, e);
		}
		if (null != sysOperator) {
			DoubleChecker checker = new DoubleChecker();
			BeanUtils.copyProperties(sysOperator, checker);
			addDoubleCheckerCache(sysCustId, checker);
			// addSysOperSecondCache(sysCustId, sysOperator);
		}
		return sysOperator;
	}

	/**
	 * 更新系统用户信息
	 * 
	 * @param srcCardHolder
	 *            系统用户信息
	 */
	public void updateSysOperInfo(SysOperator srcSysOperator) {
		String custId = srcSysOperator.getOperCustId();
		try {
			sysOperatorMapper.updateByPrimaryKeySelective(srcSysOperator);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateSysOperInfo",
					ErrorCodeEnum.SYSOPER_UPDATE_ERROR.getDesc() + ",custId["
							+ custId + "]" + NEWLINE, e);
		}
		removeSysOperCache(custId);
	}

	/**
	 * 更新系统用户信息
	 * 
	 * @param srcCardHolder
	 *            系统用户信息
	 */
	public void updateDoubleCheckerInfo(DoubleChecker doubleChecker) {
		String custId = doubleChecker.getOperCustId();
		try {
			doubleCheckerMapper.updateByPrimaryKeySelective(doubleChecker);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateDoubleCheckerInfo",
					ErrorCodeEnum.SYSOPER_UPDATE_ERROR.getDesc() + ",custId["
							+ custId + "]" + NEWLINE, e);
		}
		removeSysOperCache(custId);
	}

	/**
	 * 查询双签员信息
	 * 
	 * @param operCustId
	 * @return
	 */
	public DoubleChecker searchDoubleCheckerInfo(String operCustId) {
		DoubleChecker checker = getDoubleCheckerCache(operCustId);
		if (null != checker) {
			return checker;// 缓存中存在，则直接返回
		}
		try {
			checker = doubleCheckerMapper.selectByPrimaryKey(operCustId);// 缓存中不存在，则从数据库中获取
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "searchDoubleCheckerInfo",
					"通过客户号查询双签员信息异常：" + NEWLINE, e);
		}
		// if (null != checker) {
		// addDoubleCheckerCache(operCustId, checker);//
		// 将值放入缓存，因管理员在2张表的custId是一样的，所以双签的信息不存缓存
		// }
		return checker;
	}

	/**
	 * 移除缓存中非持卡人的客户号
	 * 
	 * @param mobile
	 */
	public void removeNoCarderCustIdCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeNoCarderCustIdCache",
					"mobile is null");
			return;
		}
		String mobileKey = CacheKeyGen.genPerCustIdByMobile(mobile);
		cacheUtil.getFixRedisUtil().remove(mobileKey);
	}

	/**
	 * 添加非持卡人的客户号到缓存中
	 * 
	 * @param mobile
	 */
	public void addNoCarderCustIdCache(String mobile, String custId) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "addNoCarderCustIdCache",
					"mobile is null");
			return;
		}
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addNoCarderCustIdCache",
					"custId is null");
			return;
		}
		String mobileKey = CacheKeyGen.genPerCustIdByMobile(mobile);
		cacheUtil.getFixRedisUtil().add(mobileKey, custId);
	}

	/**
	 * 查询缓存中非持卡人的客户号
	 * 
	 * @param mobile
	 */
	public String getNoCarderCustIdCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "getNoCarderCustIdCache",
					"mobile is null");
			return null;
		}
		String mobileKey = CacheKeyGen.genPerCustIdByMobile(mobile);
		return (String) cacheUtil.getFixRedisUtil()
				.get(mobileKey, String.class);
	}

	/**
	 * 移除缓存中持卡人实体信息
	 * 
	 * @param perCustId
	 */
	public void removeNoCarderCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "removeNoCarderCache",
					"perCustId is null");
			return;
		}
		String objKey = CacheKeyGen.genExtendNoCarderInfoKey(perCustId);
		cacheUtil.getFixRedisUtil().remove(objKey);
	}

	/**
	 * 添加持卡人实体信息到缓存中
	 * 
	 * @param perCustId
	 */
	public void addNoCarderCache(String perCustId, NoCardHolder noCardHolder) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "removeNoCarderCache",
					"perCustId is null");
			return;
		}
		if (null == noCardHolder) {
			SystemLog.warn(MOUDLENAME, "removeNoCarderCache",
					"noCardHolder is null");
			return;
		}
		String objKey = CacheKeyGen.genExtendNoCarderInfoKey(perCustId);
		cacheUtil.getFixRedisUtil().add(objKey, noCardHolder);
	}

	/**
	 * 移除缓存中持卡人通过交易密码身份验证的凭证信息
	 * 
	 * @param cerType
	 * @param cerNo
	 */
	public void removeCertificateCache(String cerType, String cerNo) {
		if (StringUtils.isBlank(cerType)) {
			SystemLog.warn(MOUDLENAME, "removeCertificateCache",
					"cerType is null");
			return;
		}
		if (StringUtils.isBlank(cerNo)) {
			SystemLog.warn(MOUDLENAME, "removeCertificateCache",
					"cerNo is null");
			return;
		}
		String key = CacheKeyGen.genConsumerCertificate(cerType, cerNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加持卡人通过交易密码身份验证的凭证信息到缓存中
	 * 
	 * @param cerType
	 * @param cerNo
	 */
	public void addCertificateCache(String cerType, String cerNo,
			String certificate) {
		if (StringUtils.isBlank(cerType)) {
			SystemLog
					.warn(MOUDLENAME, "addCertificateCache", "cerType is null");
			return;
		}
		if (StringUtils.isBlank(cerNo)) {
			SystemLog.warn(MOUDLENAME, "addCertificateCache", "cerNo is null");
			return;
		}
		if (StringUtils.isBlank(certificate)) {
			SystemLog.warn(MOUDLENAME, "addCertificateCache",
					"certificate is null");
			return;
		}
		String key = CacheKeyGen.genConsumerCertificate(cerType, cerNo);
		cacheUtil.getChangeRedisUtil().add(key, certificate);
		cacheUtil.getChangeRedisUtil().setExpireInHours(key,
				SysConfig.getEmailValidTime());
		StringBuffer msg = new StringBuffer();
		msg.append("key[" + key + "],value[" + certificate
				+ ",setExpireInHours[" + SysConfig.getEmailValidTime() + "]");
		SystemLog.info(MOUDLENAME, "addCertificateCache", msg.toString());
	}

	/**
	 * 查询缓存中持卡人通过交易密码身份验证的凭证信息
	 * 
	 * @param cerType
	 * @param cerNo
	 */
	public String getCertificateCache(String cerType, String cerNo) {
		if (StringUtils.isBlank(cerType)) {
			SystemLog
					.warn(MOUDLENAME, "getCertificateCache", "cerType is null");
			return null;
		}
		if (StringUtils.isBlank(cerNo)) {
			SystemLog.warn(MOUDLENAME, "getCertificateCache", "cerNo is null");
			return null;
		}
		String key = CacheKeyGen.genConsumerCertificate(cerType, cerNo);
		String certificate = (String) cacheUtil.getChangeRedisUtil().get(key,
				String.class);
		StringBuffer msg = new StringBuffer();
		msg.append("key[" + key + ",value[" + certificate + "]");
		SystemLog.info(MOUDLENAME, "getCertificateCache", msg.toString());
		return certificate;
	}

	/**
	 * 移除缓存中的短信验证码（验证手机）
	 * 
	 * @param mobile
	 */
	public void removeSmsCodeCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeSmsCodeCache", "mobile is null");
			return;
		}
		String key = CacheKeyGen.genSmsCode(mobile);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加短信验证码到缓存中（验证手机）
	 * 
	 * @param mobile
	 */
	public void addSmsCodeCache(String mobile, String code) {
		String methodName = "addSmsCodeCache";
		StringBuffer msg = new StringBuffer();
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, methodName, "mobile is null");
			return;
		}
		if (StringUtils.isBlank(code)) {
			SystemLog.warn(MOUDLENAME, methodName, "code is null");
			return;
		}

		String key = CacheKeyGen.genSmsCode(mobile);
		cacheUtil.getChangeRedisUtil().add(key, code);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getSmCodeValidTime(), TimeUnit.SECONDS);// 设置过期时间
		msg.append("key[").append(key).append("],code[").append(code)
				.append("],mobile[").append(mobile).append("]");
		SystemLog.debug(MOUDLENAME, methodName, msg.toString());
	}

	/**
	 * 查询缓存中的短信验证码（验证手机）
	 * 
	 * @param mobile
	 */
	public String getSmsCodeCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "getSmsCodeCache", "mobile is null");
			return null;
		}
		String key = CacheKeyGen.genSmsCode(mobile);
		return (String) cacheUtil.getChangeRedisUtil().get(key, String.class);
	}

	/**
	 * 移除缓存中POS机的积分
	 * 
	 * @param entResNo
	 * @param posNo
	 */
	public void removePosPointRateCache(String entResNo, String posNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removePosPointRateCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(posNo)) {
			SystemLog.warn(MOUDLENAME, "removePosPointRateCache",
					"posNo is null");
			return;
		}
		String key = CacheKeyGen.genPosPointRateVersion(entResNo, posNo);
		cacheUtil.getFixRedisUtil().remove(key);
	}

	/**
	 * 添加缓存中POS机的积分
	 * 
	 * @param entResNo
	 * @param posNo
	 */
	public void addPosPointRateCache(String entResNo, String posNo,
			AsPosPointRate posPointRate) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addPosPointRateCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(posNo)) {
			SystemLog.warn(MOUDLENAME, "addPosPointRateCache", "posNo is null");
			return;
		}
		if (null == posPointRate) {
			SystemLog.warn(MOUDLENAME, "addPosPointRateCache",
					"posPointRate is null");
			return;
		}
		String key = CacheKeyGen.genPosPointRateVersion(entResNo, posNo);
		cacheUtil.getFixRedisUtil().add(key, posPointRate);
	}

	/**
	 * 查询缓存中POS机的积分
	 * 
	 * @param entResNo
	 * @param posNo
	 */
	public AsPosPointRate getPosPointRateCache(String entResNo, String posNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getPosPointRateCache",
					"entResNo is null");
			return null;
		}
		if (StringUtils.isBlank(posNo)) {
			SystemLog.warn(MOUDLENAME, "getPosPointRateCache", "posNo is null");
			return null;
		}
		String key = CacheKeyGen.genPosPointRateVersion(entResNo, posNo);
		return (AsPosPointRate) cacheUtil.getFixRedisUtil().get(key,
				AsPosPointRate.class);
	}

	/**
	 * 移除缓存中的PosEnt
	 * 
	 * @param entResNo
	 */
	public void removePosEntCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getPosPointRateCache",
					"entResNo is null");
			return;
		}
		String key = CacheKeyGen.genPosEntKey(entResNo);
		cacheUtil.getFixRedisUtil().remove(key);
	}

	/**
	 * 添加缓存中的PosEnt
	 * 
	 * @param entResNo
	 */
	public void addPosEntCache(String entResNo, AsPosEnt posEnt) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addPosEntCache", "entResNo is null");
			return;
		}
		if (null == posEnt) {
			SystemLog.warn(MOUDLENAME, "addPosEntCache", "posEnt is null");
			return;
		}
		String key = CacheKeyGen.genPosEntKey(entResNo);
		cacheUtil.getFixRedisUtil().add(key, posEnt);
	}

	/**
	 * 查询缓存中的PosEnt
	 * 
	 * @param entResNo
	 */
	public AsPosEnt getPosEntCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getPosEntCache", "entResNo is null");
			return null;
		}
		String key = CacheKeyGen.genPosEntKey(entResNo);
		return (AsPosEnt) cacheUtil.getFixRedisUtil().get(key, AsPosEnt.class);
	}

	/**
	 * 移除缓存中的短信验证码（重置登录密码）
	 * 
	 * @param mobile
	 */
	public void removeSmsResetCodeCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeSmsResetCodeCache",
					"mobile is null");
			return;
		}
		String key = CacheKeyGen.genSmsReset(mobile);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加缓存中的短信验证码（重置登录密码）
	 * 
	 * @param mobile
	 */
	public void addSmsResetCodeCache(String mobile, String code) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog
					.warn(MOUDLENAME, "addSmsResetCodeCache", "mobile is null");
			return;
		}
		if (StringUtils.isBlank(code)) {
			SystemLog.warn(MOUDLENAME, "addSmsResetCodeCache", "code is null");
			return;
		}
		String key = CacheKeyGen.genSmsReset(mobile);
		cacheUtil.getChangeRedisUtil().add(key, code);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getSmCodeValidTime(), TimeUnit.SECONDS);// 设置过期时间
	}

	/**
	 * 查询缓存中的短信验证码（重置登录密码）
	 * 
	 * @param mobile
	 */
	public String getSmsResetCodeCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog
					.warn(MOUDLENAME, "addSmsResetCodeCache", "mobile is null");
			return null;
		}
		String key = CacheKeyGen.genSmsReset(mobile);
		return (String) cacheUtil.getChangeRedisUtil().get(key, String.class);
	}

	/**
	 * 移除缓存中短信授权码（企业重置交易密码）
	 * 
	 * @param mobile
	 */
	public void removeSmsAuthCodeCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeSmsAuthCodeCache",
					"mobile is null");
			return;
		}
		String key = CacheKeyGen.genSmsAuthCode(mobile);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加短信授权码到缓存中（企业重置交易密码）
	 * 
	 * @param mobile
	 */
	public void addSmsAuthCodeCache(String mobile, String code) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "addSmsAuthCodeCache", "mobile is null");
			return;
		}
		if (StringUtils.isBlank(code)) {
			SystemLog.warn(MOUDLENAME, "addSmsAuthCodeCache", "code is null");
			return;
		}
		String key = CacheKeyGen.genSmsAuthCode(mobile);
		cacheUtil.getChangeRedisUtil().add(key, code);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getSmAuthCodeValidTime(), TimeUnit.HOURS);// 设置过期时间
	}

	/**
	 * 查询缓存中短信授权码（企业重置交易密码）
	 * 
	 * @param mobile
	 */
	public String getSmsAuthCodeCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "getSmsAuthCodeCache", "mobile is null");
			return null;
		}
		String key = CacheKeyGen.genSmsAuthCode(mobile);
		return (String) cacheUtil.getChangeRedisUtil().get(key, String.class);
	}

	/**
	 * 移除缓存中的邮箱的CODE（绑定邮箱）
	 * 
	 * @param email
	 */
	public void removeBindEmailCodeCache(String email) {
		if (StringUtils.isBlank(email)) {
			SystemLog.warn(MOUDLENAME, "removeBindEmailCodeCache",
					"email is null");
			return;
		}
		String key = CacheKeyGen.genBindEmailCode(email);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加邮箱的CODE到缓存中（绑定邮箱）
	 * 
	 * @param email
	 */
	public void addBindEmailCodeCache(String email, String code) {
		if (StringUtils.isBlank(email)) {
			SystemLog
					.warn(MOUDLENAME, "addBindEmailCodeCache", "email is null");
			return;
		}
		if (StringUtils.isBlank(code)) {
			SystemLog.warn(MOUDLENAME, "addBindEmailCodeCache", "code is null");
			return;
		}
		String key = CacheKeyGen.genBindEmailCode(email);
		cacheUtil.getChangeRedisUtil().add(key, code);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getEmailValidTime(), TimeUnit.HOURS);// 设置过期时间
	}

	/**
	 * 查询缓存中邮箱的CODE（绑定邮箱）
	 * 
	 * @param email
	 */
	public String getBindEmailCodeCache(String email) {
		if (StringUtils.isBlank(email)) {
			SystemLog
					.warn(MOUDLENAME, "getBindEmailCodeCache", "email is null");
			return null;
		}
		String key = CacheKeyGen.genBindEmailCode(email);
		return cacheUtil.getChangeRedisUtil().getToString(key);
	}

	/**
	 * 移除缓存中的邮箱code(重置登录密码)
	 * 
	 * @param email
	 */
	public void removeEmailResetCodeCache(String email) {
		if (StringUtils.isBlank(email)) {
			SystemLog.warn(MOUDLENAME, "removeEmailResetCodeCache",
					"email is null");
			return;
		}
		String key = CacheKeyGen.genEmailResetCode(email);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加邮箱code到缓存中(重置登录密码)
	 * 
	 * @param email
	 */
	public void addEmailResetCodeCache(String email, String code) {
		if (StringUtils.isBlank(email)) {
			SystemLog.warn(MOUDLENAME, "addEmailResetCodeCache",
					"email is null");
			return;
		}
		if (StringUtils.isBlank(code)) {
			SystemLog
					.warn(MOUDLENAME, "addEmailResetCodeCache", "code is null");
			return;
		}
		String key = CacheKeyGen.genEmailResetCode(email);
		cacheUtil.getChangeRedisUtil().add(key, code);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getEmailValidTime(), TimeUnit.HOURS);// 设置过期时间
	}

	/**
	 * 查询缓存中的邮箱code(重置登录密码)
	 * 
	 * @param email
	 */
	public String getEmailResetCodeCache(String email) {
		if (StringUtils.isBlank(email)) {
			SystemLog.warn(MOUDLENAME, "getEmailResetCodeCache",
					"email is null");
			return null;
		}
		String key = CacheKeyGen.genEmailResetCode(email);
		return cacheUtil.getChangeRedisUtil().getToString(key);
	}

	/**
	 * 移除缓存中通过密保验证的凭证code
	 * 
	 * @param custId
	 */
	public void removeCryptCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "removeCryptCache", "custId is null");
			return;
		}
		String key = CacheKeyGen.genCrypt(custId);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加通过密保验证的凭证code到缓存中
	 * 
	 * @param custId
	 */
	public void addCryptCache(String custId, String code) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addCryptCache", "custId is null");
			return;
		}
		if (StringUtils.isBlank(code)) {
			SystemLog.warn(MOUDLENAME, "addCryptCache", "code is null");
			return;
		}
		String key = CacheKeyGen.genCrypt(custId);
		cacheUtil.getChangeRedisUtil().add(key, code);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getSmCodeValidTime(), TimeUnit.SECONDS);// 设置过期时间
	}

	/**
	 * 查询缓存中通过密保验证的凭证code
	 * 
	 * @param custId
	 */
	public String getCryptCache(String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getCryptCache", "custId is null");
			return null;
		}
		String key = CacheKeyGen.genCrypt(custId);
		return cacheUtil.getChangeRedisUtil().getToString(key);
	}

	/**
	 * 移除缓存中登录token
	 * 
	 * @param channelType
	 * @param custId
	 */
	public void removeLoginTokenCache(ChannelTypeEnum channelType, String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getCryptCache", "custId is null");
			return;
		}
		if (null == channelType) {
			SystemLog.warn(MOUDLENAME, "getCryptCache", "channelType is null");
			return;
		}
		String loginKey = CacheKeyGen.genLoginKey(channelType, custId);
		cacheUtil.getChangeRedisUtil().remove(loginKey);
	}

	/**
	 * 添加缓存中登录token
	 * 
	 * @param channelType
	 * @param custId
	 */
	public void addLoginTokenCache(ChannelTypeEnum channelType, String custId,
			String loginToken) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "addLoginTokenCache", "custId is null");
			return;
		}
		if (StringUtils.isBlank(loginToken)) {
			SystemLog.warn(MOUDLENAME, "addLoginTokenCache",
					"loginToken is null");
			return;
		}
		if (null == channelType) {
			SystemLog.warn(MOUDLENAME, "addLoginTokenCache",
					"channelType is null");
			return;
		}
		String loginKey = CacheKeyGen.genLoginKey(channelType, custId);
		cacheUtil.getChangeRedisUtil().setString(loginKey, loginToken);

		// 设置超时时间
		if (channelType == ChannelTypeEnum.WEB) {
			cacheUtil.getChangeRedisUtil().setExpire(loginKey,
					SysConfig.getWebLoginValidTime(), TimeUnit.SECONDS);
		} else {
			cacheUtil.getChangeRedisUtil().setExpire(loginKey,
					SysConfig.getOtherLoginValidTime(), TimeUnit.SECONDS);
		}

	}

	/**
	 * 查询缓存中登录token
	 * 
	 * @param channelType
	 * @param custId
	 */
	public String getLoginTokenCache(ChannelTypeEnum channelType, String custId) {
		if (StringUtils.isBlank(custId)) {
			SystemLog.warn(MOUDLENAME, "getLoginTokenCache", "custId is null");
			return null;
		}
		if (null == channelType) {
			SystemLog.warn(MOUDLENAME, "getLoginTokenCache",
					"channelType is null");
			return null;
		}
		String loginKey = CacheKeyGen.genLoginKey(channelType, custId);
		return (String) cacheUtil.getChangeRedisUtil().getString(loginKey);
	}

	/**
	 * 添加缓存中的随机token
	 * 
	 * @param randomToken
	 */
	public void addRandomTokenCache(String custId, String randomToken) {
		String key = CacheKeyGen.genRandomTokenKey(custId, randomToken);
		cacheUtil.getChangeRedisUtil().setString(key, randomToken);
		cacheUtil.getChangeRedisUtil().setExpire(key,
				SysConfig.getRandomTokenTimeout(), TimeUnit.SECONDS);
	}

	/**
	 * 验证随机token是否存在，如果存在则删除
	 * 
	 * @param str
	 *            客户号或随机token
	 * @param isCustId
	 *            是否是客户号
	 * @return
	 */
	public boolean isRandomTokenExists(String custId, String randomToken) {
		String key = CacheKeyGen.genRandomTokenKey(custId, randomToken);
		return cacheUtil.getChangeRedisUtil().isExists(key, true);
	}

	/**
	 * 移除缓存中的POS的token
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public void removePosTokenCache(String entResNo, String deviceNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removePosTokenCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "removePosTokenCache",
					"deviceNo is null");
			return;
		}
		String key = CacheKeyGen.genPosTokenKey(entResNo, deviceNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加缓存中的POS的token
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public void addPosTokenCache(String entResNo, String deviceNo,
			DeviceToken deviceToken, Long second) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addPosTokenCache", "entResNo is null");
			return;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "addPosTokenCache", "deviceNo is null");
			return;
		}
		if (null == deviceToken) {
			SystemLog.warn(MOUDLENAME, "addPosTokenCache",
					"deviceToken is null");
			return;
		}
		String key = CacheKeyGen.genPosTokenKey(entResNo, deviceNo);
		cacheUtil.getChangeRedisUtil().add(key, deviceToken);
		// 超时设置
		if(second != null){
			cacheUtil.getChangeRedisUtil().setExpireInSecond(key, second);
		}
	}

	/**
	 * 查询缓存中的POS的token
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public DeviceToken getPosTokenCache(String entResNo, String deviceNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getPosTokenCache", "entResNo is null");
			return null;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "getPosTokenCache", "deviceNo is null");
			return null;
		}
		String key = CacheKeyGen.genPosTokenKey(entResNo, deviceNo);
		return (DeviceToken) cacheUtil.getChangeRedisUtil().get(key,
				DeviceToken.class);
	}

	/**
	 * 查询缓存中的随机token
	 * 
	 * @param randomToken
	 */
	public String getRandomToken(String randomToken) {
		if (StringUtils.isBlank(randomToken)) {
			SystemLog.warn(MOUDLENAME, "getRandomToken", "randomToken is null");
			return null;
		}
		return cacheUtil.getChangeRedisUtil().getToString(randomToken);
	}

	/**
	 * 移除缓存中POS比对的MAC
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public void removePosMacMatchCache(String entResNo, String deviceNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removePosMacMatchCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "removePosMacMatchCache",
					"deviceNo is null");
			return;
		}
		String key = CacheKeyGen.genPosMacMatch(entResNo, deviceNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加缓存中POS比对的MAC
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public void addPosMacMatchCache(String entResNo, String deviceNo,
			Integer failedTimes) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addPosMacMatchCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "addPosMacMatchCache",
					"deviceNo is null");
			return;
		}
		if (null == failedTimes) {
			SystemLog.warn(MOUDLENAME, "addPosMacMatchCache",
					"failedTimes is null");
			return;
		}
		String key = CacheKeyGen.genPosMacMatch(entResNo, deviceNo);
		cacheUtil.getChangeRedisUtil().add(key, failedTimes);
	}

	/**
	 * 查询缓存中POS比对的MAC
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public Integer getPosMacMatchCache(String entResNo, String deviceNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getPosMacMatchCache",
					"entResNo is null");
			return null;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "getPosMacMatchCache",
					"deviceNo is null");
			return null;
		}
		String key = CacheKeyGen.genPosMacMatch(entResNo, deviceNo);
		return (Integer) cacheUtil.getChangeRedisUtil().get(key, Integer.class);
	}

	/**
	 * 移除缓存中的刷卡器token
	 * 
	 * @param entResNo
	 * @param deviceNo
	 */
	public void removeCardReaderTokenCache(String entResNo, String deviceNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removeCardReaderTokenCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "removeCardReaderTokenCache",
					"deviceNo is null");
			return;
		}
		String key = CacheKeyGen.genCardReaderTokenKey(entResNo, deviceNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * 添加缓存中的刷卡器token
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @param token
	 */
	public void addCardReaderTokenCache(String entResNo, String deviceNo,
			DeviceToken token) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addCardReaderTokenCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "addCardReaderTokenCache",
					"deviceNo is null");
			return;
		}
		if (null == token) {
			SystemLog.warn(MOUDLENAME, "addCardReaderTokenCache",
					"token is null");
			return;
		}
		String key = CacheKeyGen.genCardReaderTokenKey(entResNo, deviceNo);
		cacheUtil.getChangeRedisUtil().add(key, token);
	}

	/**
	 * 查询缓存中的刷卡器token
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @param token
	 */
	public DeviceToken getCardReaderTokenCache(String entResNo, String deviceNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getCardReaderTokenCache",
					"entResNo is null");
			return null;
		}
		if (StringUtils.isBlank(deviceNo)) {
			SystemLog.warn(MOUDLENAME, "getCardReaderTokenCache",
					"deviceNo is null");
			return null;
		}
		String key = CacheKeyGen.genCardReaderTokenKey(entResNo, deviceNo);
		return (DeviceToken) cacheUtil.getChangeRedisUtil().get(key,
				DeviceToken.class);
	}

	/**
	 * 查询缓存中持卡人实体信息
	 * 
	 * @param perCustId
	 */
	public NoCardHolder getNoCarderCache(String perCustId) {
		if (StringUtils.isBlank(perCustId)) {
			SystemLog.warn(MOUDLENAME, "getNoCarderCache", "perCustId is null");
			return null;
		}
		String objKey = CacheKeyGen.genExtendNoCarderInfoKey(perCustId);
		return (NoCardHolder) cacheUtil.getFixRedisUtil().get(objKey,
				NoCardHolder.class);
	}

	/**
	 * 根据用户id获取用户角色ID集合 先从缓存获取，若无，从数据库获取
	 * 
	 * @param custId
	 * @return Set<String roleId>
	 */
	public Set<String> getCustRoleIdSet(String custId) {
		// 从缓存获取用户拥有的角色
		String key = UcCacheKey.genCustRoleCacheKey(custId);
		Set<String> custRoleIdSet = null;
		try {
			custRoleIdSet = cacheUtil.getFixRedisUtil().redisTemplate
					.opsForSet().members(key);
		} catch (Exception e) {
			SystemLog.error("CommonCacheService", "getCustRoleIdSet",
					e.getMessage(), e);
		}

		if (custRoleIdSet == null || custRoleIdSet.isEmpty()) { // 缓存无数据
			// 从数据库查询用户拥有的角色并更新缓存
			custRoleIdSet = loadCustRoles(custId);

			if (custRoleIdSet == null || custRoleIdSet.isEmpty()) {
				// 数据库无数据 打印日志
				SystemLog.warn(MOUDLENAME, "getCustRoleIdSet", custId
						+ "=custId has not a role");
			} else { // 数据库有数据 更新缓存
				resetCustRoleCache(custId, (Set<String>) custRoleIdSet);
			}
		}
		return (Set<String>) custRoleIdSet;
	}

	/**
	 * 从数据库加载用户角色信息
	 * 
	 * @param custId
	 * @return
	 */
	public Set<String> loadCustRoles(String custId) {
		// 组装数据库操作条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("custId", custId);
		List<Role> roleList = roleMapper.selectByCustId(conditionMap);

		if (roleList != null && !roleList.isEmpty()) {
			HashSet<String> ret = new HashSet<String>();
			for (Role vo : roleList) {
				ret.add(vo.getRoleId());
			}
			return ret;
		}
		return null;

	}

	public Set<String> resetCustRoleCache(String custId,
			Set<String> custRoleIdSet) {
		if (custRoleIdSet != null) {
			String key = CacheKeyGen.genCustRoleCacheKey(custId);
			try {
				cacheUtil.getFixRedisUtil().redisTemplate.delete(key);
				if (!custRoleIdSet.isEmpty()) {
					String[] roldIds = new String[custRoleIdSet.size()];
					int i = 0;
					for (String id : custRoleIdSet) {
						roldIds[i++] = id;
					}
					cacheUtil.getFixRedisUtil().redisTemplate.opsForSet().add(
							key, roldIds);
				}
			} catch (Exception e) {
				SystemLog.error("CommonCacheService", "resetCustRoleCache",
						String.valueOf(custRoleIdSet), e);
			}
		}
		return custRoleIdSet;
	}

	/**
	 * 判断用户是否拥有对应roleId
	 * 
	 * @param custId
	 * @param roleId
	 * @return
	 */
	public boolean hasRoleId(String custId, String roleId) {
		// 用户拥有的角色集
		Set<String> custRoleCache = getCustRoleIdSet(custId);
		if (custRoleCache == null || custRoleCache.isEmpty()) {
			SystemLog.warn(MOUDLENAME, "hasRoleId", custId
					+ "=custId has not a role");
			return false;
		}
		boolean ret = custRoleCache.contains(roleId);
		if (!ret) {
			SystemLog.warn(MOUDLENAME, "hasRoleId", custId
					+ " custId has not roleid" + roleId + "\r\n"
					+ custRoleCache);
		}
		return ret;

	}

	/**
	 * 判断用户是否拥有对应url权限
	 * 
	 * @param custId
	 * @param url
	 * @return
	 */
	public boolean hashUrlPermission(String custId, String url) {
		// 用户拥有的角色集
		Set<String> custRoleCache = getCustRoleIdSet(custId);
		if (custRoleCache == null || custRoleCache.isEmpty()) {
			SystemLog.warn(MOUDLENAME, "hashUrlPermission", custId
					+ "=custId has not a role");
			return false;
		}

		String urlCacheKey = ""; // roleUrl权限缓存key
		for (String roleId : custRoleCache) {
			urlCacheKey = CacheKeyGen.genRoleUrlCacheKey(roleId);
			if (hasUrlInRole((roleId), url)) {// 该角色拥有指定
												// url权限
				return true;
			}
		}
		SystemLog.error(MOUDLENAME, "hashUrlPermission", "return false,custId="
				+ custId + ",url=" + url, null);
		return false;

	}

	/**
	 * 获取角色的url权限列表
	 * 
	 * @param roleId
	 * @return
	 */
	public ArrayList<String> getUrlByRole(String roleId) {
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("roleId", roleId);
		List<Permission> permList = permissionMapper
				.selectByRoleId(conditionMap);
		if (permList != null && !permList.isEmpty()) {
			ArrayList<String> ret = new ArrayList<String>();
			String url;
			for (Permission p : permList) {
				url = p.getPermUrl();
				if (url != null) {
					ret.add(url);
				}
			}
			return ret;
		}
		return null;
	}

	/**
	 * 重置指定角色url权限缓存
	 * 
	 * @param roleId
	 */
	public void resetRoleUrlCache(String roleId) {
		ArrayList<String> urlList = getUrlByRole(roleId);
		if (urlList != null && !urlList.isEmpty()) {

			// 添加新缓存值
			// try {
			HashSet<String> set = new HashSet<String>();
			set.addAll(urlList);// 给set填充 剔除重复数据
			// if (set.size() < urlList.size()) {
			// SystemLog.warn(MOUDLENAME, "resetRoleUrlCache:" + roleId,
			// set.size() + "存在重复权限url" + urlList.size());
			// }
			// System.out.println(set);

			int len = set.size();
			int i = 0;
			String[] values = new String[len];// (String[])
												// set.toArray();//强制类型转换有时会报错
			for (String o : set) {
				// System.out.println(o.getClass().getName() + " " + o);
				values[i++] = (String) o;
			}
			String key = UcCacheKey.genRoleUrlCacheKey(roleId);
			// 删除旧缓存

			cacheUtil.getFixRedisUtil().redisTemplate.delete(key);
			cacheUtil.getFixRedisUtil().redisTemplate.opsForSet().add(key,
					values);
			// } catch (Exception e) {
			// e.printStackTrace();
			// SystemLog.error(MOUDLENAME, "resetRoleUrlCache", "初始化角色URL关系失败"
			// + urlList, e);
			// }
			// SystemLog.info(MOUDLENAME, "resetRoleUrlCache", "" + urlList);

		}
	}

	/**
	 * 判断角色是否拥有对应url权限
	 * 
	 * @param roleId
	 * @param url
	 * @return
	 */
	public boolean hasUrlInRole(String roleId, String url) {
		String key = CacheKeyGen.genRoleUrlCacheKey(roleId);
		if (!cacheUtil.getFixRedisUtil().redisTemplate.hasKey(key)) {
			// 缓存不存在，刷新缓存
			resetRoleUrlCache(roleId);
		}
		boolean ret = cacheUtil.getFixRedisUtil().redisTemplate.opsForSet()
				.isMember(key, url);

		return ret;
	}

	/**
	 * select系统用户的登录密码失败次数
	 * 
	 * @param userName
	 * @param loginFailTimes
	 */
	public Integer getSysOperLoginFailTimesCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "getSysOperLoginFailTimesCache",
					"userName is null");
			return null;
		}
		String objKey = CacheKeyGen.genSystemLoginFailTimesKey(userName);
		Integer loginFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				objKey, Integer.class);
		if (null == loginFailTimes) {
			return 0;
		}
		return loginFailTimes;
	}

	/**
	 * add系统用户的登录密码失败次数到缓存
	 * 
	 * @param userName
	 * @param loginFailTimes
	 */
	public void addSysOperLoginFailTimesCache(String userName,
			Integer loginFailTimes) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "addSysOperLoginFailTimesCache",
					"userName is null");
			return;
		}
		if (StringUtils.isBlank(loginFailTimes)) {
			SystemLog.warn(MOUDLENAME, "addSysOperLoginFailTimesCache",
					"loginFailTimes is null");
			return;
		}
		String objKey = CacheKeyGen.genSystemLoginFailTimesKey(userName);
		cacheUtil.getChangeRedisUtil().add(objKey, loginFailTimes);
	}

	/**
	 * add系统用户的登录密码失败次数到缓存
	 * 
	 * @param userName
	 * @param loginFailTimes
	 */
	public void removeSysOperLoginFailTimesCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "removeSysOperLoginFailTimesCache",
					"userName is null");
			return;
		}
		String objKey = CacheKeyGen.genSystemLoginFailTimesKey(userName);
		cacheUtil.getChangeRedisUtil().remove(objKey);
	}

	@PostConstruct
	/**spring 启动时初始化数据
	 * 从数据库加载所有角色及其拥有的url权限，存入redis缓存中
	 */
	public void init() {
		SystemLog.info(MOUDLENAME, "init", "begin init by @PostConstruct");
		Role role = new Role();// 查询条件
		List<Role> selectResultList = roleMapper.selectByType(role);
		if (selectResultList != null && !selectResultList.isEmpty()) {
			for (Role r : selectResultList) {// 把角色拥有的url权限，存入redis缓存中
				resetRoleUrlCache(r.getRoleId());
			}
		}
		SystemLog.info(MOUDLENAME, "init", "end   init by @PostConstruct ");

	}

	/**
	 * 设置用户业务操作限制，先删除旧数据，再保存新增
	 * 
	 * @param custId
	 * @param map
	 */
	public void setBoSetting(String custId, Map<String, String> map) {
		// String key="UC.BO_SET_"+custId;
		String key = CacheKeyGen.genBoSettingKey(custId);
		cacheUtil.getFixRedisUtil().redisTemplate.delete(key);
		if (map != null && !map.isEmpty()) {
			cacheUtil.getFixRedisUtil().redisTemplate.opsForHash().putAll(key,
					map);
		}

	}

	public Map<String, String> getBoSetting(String custId) {
		// String key="UC.BO_SET_"+custId;
		String key = CacheKeyGen.genBoSettingKey(custId);

		Map ret = cacheUtil.getFixRedisUtil().redisTemplate.opsForHash()
				.entries(key);
		return (Map<String, String>) ret;

	}

	/**
	 * 业务操作限制检查
	 * 
	 * @param custId
	 *            企业客户号or个人客户号
	 * @param opName
	 *            操作名，see BoNameEnum
	 * @return
	 */
	public boolean isForbidden(String custId, String opName) {
		// String key="UC.BO_SET_"+custId;
		// opName=BoNameEnum.HB2BANK.name();
		String key = CacheKeyGen.genBoSettingKey(custId);
		return cacheUtil.getFixRedisUtil().redisTemplate.opsForHash().hasKey(
				key, opName);
	}

	/**
	 * 更新持卡人的最近的登录IP和日期
	 * 
	 * @param cardHolder
	 */
	public void updateCarderLoginIn(CardHolder carderHolder, String loginIp)
			throws HsException {
		CardHolder nc = new CardHolder();
		nc.setLastLoginIp(loginIp);
		nc.setLastLoginDate(StringUtils.getNowTimestamp());
		nc.setPerCustId(carderHolder.getPerCustId());
		try {
			cardHolderMapper.updateByPrimaryKeySelective(nc);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateCarderLoginIn", "更新持卡人信息异常："
					+ NEWLINE, e);
		}
		CardHolder ch = new CardHolder();
		BeanUtils.copyProperties(carderHolder, ch);
		ch.setLastLoginDate(nc.getLastLoginDate());
		ch.setLastLoginIp(loginIp);
		// 更新缓存
		addCarderCache(ch.getPerCustId(), ch);
	}

	/**
	 * 更新非持卡人的最近的登录IP和日期
	 * 
	 * @param cardHolder
	 */
	public void updateNoCarderLoginIn(String perCustId, String lastLoginIp,
			Timestamp lastLoginDate) throws HsException {
		NoCardHolder nc = new NoCardHolder();
		nc.setLastLoginIp(lastLoginIp);
		nc.setLastLoginDate(lastLoginDate);
		nc.setUpdatedby(perCustId);
		nc.setUpdateDate(lastLoginDate);
		nc.setPerCustId(perCustId);
		try {
			noCardHolderMapper.updateByPrimaryKeySelective(nc);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateNoCardHolderInfo", "更新非持卡人信息异常："
					+ NEWLINE, e);
		}
		nc = searchNoCardHolderInfo(perCustId);
		if (nc != null) {
			nc.setLastLoginIp(lastLoginIp);
			nc.setLastLoginDate(lastLoginDate);
			addNoCarderCache(perCustId, nc);
		}
	}

	/**
	 * 更新持卡人的最近的登录IP和日期
	 * 
	 * @param cardHolder
	 */
	public void updateOperLoginIn(String operCustId, String lastLoginIp,
			Timestamp lastLoginDate) throws HsException {
		Operator operator = new Operator();
		operator.setLastLoginIp(lastLoginIp);
		operator.setLastLoginDate(lastLoginDate);
		operator.setUpdateDate(lastLoginDate);
		operator.setUpdatedby(operCustId);
		operator.setOperCustId(operCustId);
		try {
			operatorMapper.updateByPrimaryKeySelective(operator);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateNoCardHolderInfo", "更新非持卡人信息异常："
					+ NEWLINE, e);
		}
		operator = searchOperByCustId(operCustId);
		if (null != operator) {
			operator.setLastLoginIp(lastLoginIp);
			operator.setLastLoginDate(lastLoginDate);
			addOperCache(operCustId, operator);
		}
	}

	/**
	 * add缓存中的非持卡人登录密码失败次数
	 * 
	 * @param mobile
	 */
	public void addNoCardLoginFailTimesCache(String mobile,
			Integer loginFailTimes) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "addNoCardLoginFailTimesCache",
					"mobile is null");
			return;
		}
		if (null == loginFailTimes) {
			SystemLog.warn(MOUDLENAME, "addNoCardLoginFailTimesCache",
					"loginFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genNoCardLoginFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().add(key, loginFailTimes);
	}

	/**
	 * remove缓存中的非持卡人登录密码失败次数
	 * 
	 * @param mobile
	 */
	public void removeNoCardLoginFailTimesCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeNoCardLoginFailTimesCache",
					"mobile is null");
			return;
		}
		String key = CacheKeyGen.genNoCardLoginFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的非持卡人登录密码失败次数
	 * 
	 * @param mobile
	 */
	public Integer getNoCardLoginFailTimesCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "getNoCardLoginFailTimesCache",
					"mobile is null");
			return null;
		}
		String key = CacheKeyGen.genNoCardLoginFailTimesKey(mobile);
		Integer loginFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == loginFailTimes) {
			loginFailTimes = 0;
		}
		return loginFailTimes;
	}

	/**
	 * add缓存中的非持卡人交易密码失败次数
	 * 
	 * @param mobile
	 */
	public void addNoCardTradeFailTimesCache(String mobile,
			Integer tradeFailTimes) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "addNoCardTradeFailTimesCache",
					"mobile is null");
			return;
		}
		if (null == tradeFailTimes) {
			SystemLog.warn(MOUDLENAME, "addNoCardTradeFailTimesCache",
					"tradeFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genNoCardTradeFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().add(key, tradeFailTimes);
		// 设置解锁时间
//		if (tradeFailTimes >= 0) {
			long timeout = computeUnlockSecond(SysConfig
					.getTransPwdFailUnlockTime());
			setNoCarderTradeFailTimesCacheKeyExpireInSecond(mobile, timeout);
//		}
	}

	/**
	 * remove缓存中的非持卡人交易密码失败次数
	 * 
	 * @param mobile
	 */
	public void removeNoCardTradeFailTimesCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeNoCardTradeFailTimesCache",
					"mobile is null");
			return;
		}
		String key = CacheKeyGen.genNoCardTradeFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的非持卡人交易密码失败次数
	 * 
	 * @param mobile
	 */
	public Integer getNoCardTradeFailTimesCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "getNoCardTradeFailTimesCache",
					"mobile is null");
			return null;
		}
		String key = CacheKeyGen.genNoCardTradeFailTimesKey(mobile);
		Integer tradeFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == tradeFailTimes) {
			tradeFailTimes = 0;
		}
		return tradeFailTimes;
	}

	/**
	 * add缓存中的非持卡人密保失败次数
	 * 
	 * @param mobile
	 */
	public void addNoCardPwdQuestionFailTimesCache(String mobile,
			Integer questionFailTimes) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "addNoCardPwdQuestionFailTimesCache",
					"mobile is null");
			return;
		}
		if (null == questionFailTimes) {
			SystemLog.warn(MOUDLENAME, "addNoCardPwdQuestionFailTimesCache",
					"tradeFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genNoCardPwdQuestionFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().add(key, questionFailTimes);
	}

	/**
	 * remove缓存中的非持卡人密保失败次数
	 * 
	 * @param mobile
	 */
	public void removeNoCardPwdQuestionFailTimesCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "removeNoCardPwdQuestionFailTimesCache",
					"mobile is null");
			return;
		}
		String key = CacheKeyGen.genNoCardPwdQuestionFailTimesKey(mobile);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的非持卡人密保失败次数
	 * 
	 * @param mobile
	 */
	public Integer getNoCardPwdQuestionFailTimesCache(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			SystemLog.warn(MOUDLENAME, "getNoCardPwdQuestionFailTimesCache",
					"mobile is null");
			return null;
		}
		String key = CacheKeyGen.genNoCardPwdQuestionFailTimesKey(mobile);
		Integer questionFailTimes = (Integer) cacheUtil.getChangeRedisUtil()
				.get(key, Integer.class);
		if (null == questionFailTimes) {
			questionFailTimes = 0;
		}
		return questionFailTimes;
	}

	/**
	 * add缓存中的持卡人登录密码失败次数
	 * 
	 * @param perResNo
	 */
	public void addCarderLoginFailTimesCache(String perResNo,
			Integer loginFailTimes) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "addCarderLoginFailTimesCache",
					"perResNo is null");
			return;
		}
		if (null == loginFailTimes) {
			SystemLog.warn(MOUDLENAME, "addCarderLoginFailTimesCache",
					"loginFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genCarderLoginFailTimesKey(perResNo);
		cacheUtil.getChangeRedisUtil().add(key, loginFailTimes);
	}

	/**
	 * remove缓存中的持卡人登录密码失败次数
	 * 
	 * @param perResNo
	 */
	public void removeCarderLoginFailTimesCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "removeCarderLoginFailTimesCache",
					"perResNo is null");
			return;
		}
		String key = CacheKeyGen.genCarderLoginFailTimesKey(perResNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的持卡人登录密码失败次数
	 * 
	 * @param perResNo
	 */
	public Integer getCarderLoginFailTimesCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "getCarderLoginFailTimesCache",
					"perResNo is null");
			return null;
		}
		String key = CacheKeyGen.genCarderLoginFailTimesKey(perResNo);
		Integer loginFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == loginFailTimes) {
			loginFailTimes = 0;
		}
		return loginFailTimes;
	}

	/**
	 * add缓存中的持卡人交易密码失败次数
	 * 
	 * @param perResNo
	 */
	public void addCarderTradeFailTimesCache(String perResNo,
			Integer tradeFailTimes) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "addCarderTradeFailTimesCache",
					"perResNo is null");
			return;
		}
		if (null == tradeFailTimes) {
			SystemLog.warn(MOUDLENAME, "addCarderTradeFailTimesCache",
					"tradeFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genCarderTradeFailTimesKey(perResNo);
		cacheUtil.getChangeRedisUtil().add(key, tradeFailTimes);
		//设置过期时间
		long timeout = computeUnlockSecond(SysConfig
				.getTransPwdFailUnlockTime());
		setCarderTradeFailTimesCacheKeyExpireInSecond(
						perResNo, timeout);
	}

	/**
	 * remove缓存中的持卡人交易密码失败次数
	 * 
	 * @param perResNo
	 */
	public void removeCarderTradeFailTimesCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "removeCarderTradeFailTimesCache",
					"perResNo is null");
			return;
		}
		String key = CacheKeyGen.genCarderTradeFailTimesKey(perResNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的持卡人交易密码失败次数
	 * 
	 * @param perResNo
	 */
	public Integer getCarderTradeFailTimesCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "getCarderTradeFailTimesCache",
					"perResNo is null");
			return null;
		}
		String key = CacheKeyGen.genCarderTradeFailTimesKey(perResNo);
		Integer tradeFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == tradeFailTimes) {
			tradeFailTimes = 0;
		}
		return tradeFailTimes;
	}

	/**
	 * add缓存中的持卡人密保失败次数
	 * 
	 * @param perResNo
	 */
	public void addCarderPwdQuestionFailTimesCache(String perResNo,
			Integer questionFailTimes) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "addCarderPwdQuestionFailTimesCache",
					"perResNo is null");
			return;
		}
		if (null == questionFailTimes) {
			SystemLog.warn(MOUDLENAME, "addCarderPwdQuestionFailTimesCache",
					"tradeFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genCarderPwdQuestionFailTimesKey(perResNo);
		cacheUtil.getChangeRedisUtil().add(key, questionFailTimes);
	}

	/**
	 * remove缓存中的持卡人密保失败次数
	 * 
	 * @param perResNo
	 */
	public void removeCarderPwdQuestionFailTimesCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "removeCarderPwdQuestionFailTimesCache",
					"perResNo is null");
			return;
		}
		String key = CacheKeyGen.genCarderPwdQuestionFailTimesKey(perResNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的非持卡人密保失败次数
	 * 
	 * @param perResNo
	 */
	public Integer getCarderPwdQuestionFailTimesCache(String perResNo) {
		if (StringUtils.isBlank(perResNo)) {
			SystemLog.warn(MOUDLENAME, "getCarderPwdQuestionFailTimesCache",
					"perResNo is null");
			return null;
		}
		String key = CacheKeyGen.genCarderPwdQuestionFailTimesKey(perResNo);
		Integer questionFailTimes = (Integer) cacheUtil.getChangeRedisUtil()
				.get(key, Integer.class);
		if (null == questionFailTimes) {
			questionFailTimes = 0;
		}
		return questionFailTimes;
	}

	/**
	 * add缓存中的操作员登录密码失败次数
	 * 
	 * @param entResNo
	 * @param operNo
	 * @param loginFailTimes
	 * 
	 */
	public void addOperLoginFailTimesCache(String entResNo, String operNo,
			Integer loginFailTimes) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addOperLoginFailTimesCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(operNo)) {
			SystemLog.warn(MOUDLENAME, "addOperLoginFailTimesCache",
					"operNo is null");
			return;
		}
		if (null == loginFailTimes) {
			SystemLog.warn(MOUDLENAME, "addOperLoginFailTimesCache",
					"loginFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genOperLoginFailTimesKey(entResNo, operNo);
		cacheUtil.getChangeRedisUtil().add(key, loginFailTimes);
	}

	/**
	 * remove缓存中的操作员登录密码失败次数
	 * 
	 * @param entResNo
	 * @param operNo
	 */
	public void removeOperLoginFailTimesCache(String entResNo, String operNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removeOperLoginFailTimesCache",
					"entResNo is null");
			return;
		}
		if (StringUtils.isBlank(operNo)) {
			SystemLog.warn(MOUDLENAME, "removeOperLoginFailTimesCache",
					"operNo is null");
			return;
		}
		String key = CacheKeyGen.genOperLoginFailTimesKey(entResNo, operNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的操作员登录密码失败次数
	 * 
	 * @param entResNo
	 * @param operNo
	 */
	public Integer getOperLoginFailTimesCache(String entResNo, String operNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getOperLoginFailTimesCache",
					"entResNo is null");
			return null;
		}
		if (StringUtils.isBlank(operNo)) {
			SystemLog.warn(MOUDLENAME, "getOperLoginFailTimesCache",
					"operNo is null");
			return null;
		}
		String key = CacheKeyGen.genOperLoginFailTimesKey(entResNo, operNo);
		Integer loginFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == loginFailTimes) {
			loginFailTimes = 0;
		}
		return loginFailTimes;
	}

	/**
	 * add缓存中的企业交易密码失败次数
	 * 
	 * @param entResNo
	 * @param tradeFailTimes
	 */
	public void addEntTradeFailTimesCache(String entResNo,
			Integer tradeFailTimes) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addEntTradeFailTimesCache",
					"entResNo is null");
			return;
		}
		if (null == tradeFailTimes) {
			SystemLog.warn(MOUDLENAME, "addEntTradeFailTimesCache",
					"tradeFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genEntTradeFailTimesKey(entResNo);
		cacheUtil.getChangeRedisUtil().add(key, tradeFailTimes);
//		if (tradeFailTimes > 0) {
		//设置过期时间
			long timeout = computeUnlockSecond(SysConfig
					.getTransPwdFailUnlockTime());
			setEntTradePwdFailTimesKeyExpireInSecond(entResNo, timeout);
//		}
	}

	/**
	 * remove缓存中的企业交易密码失败次数
	 * 
	 * @param entResNo
	 */
	public void removeEntTradeFailTimesCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removeEntTradeFailTimesCache",
					"entResNo is null");
			return;
		}
		String key = CacheKeyGen.genEntTradeFailTimesKey(entResNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的企业交易密码失败次数
	 * 
	 * @param entResNo
	 */
	public Integer getEntTradeFailTimesCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getEntTradeFailTimesCache",
					"entResNo is null");
			return null;
		}
		String key = CacheKeyGen.genEntTradeFailTimesKey(entResNo);
		Integer tradeFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == tradeFailTimes) {
			tradeFailTimes = 0;
		}
		return tradeFailTimes;
	}

	/**
	 * add缓存中的企业密保失败次数
	 * 
	 * @param entResNo
	 */
	public void addEntPwdQuestionFailTimesCache(String entResNo,
			Integer questionFailTimes) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "addEntPwdQuestionFailTimesCache",
					"entResNo is null");
			return;
		}
		if (null == questionFailTimes) {
			SystemLog.warn(MOUDLENAME, "addEntPwdQuestionFailTimesCache",
					"questionFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genEntPwdQuestionFailTimesKey(entResNo);
		cacheUtil.getChangeRedisUtil().add(key, questionFailTimes);
	}

	/**
	 * remove缓存中的企业密保失败次数
	 * 
	 * @param entResNo
	 */
	public void removeEntPwdQuestionFailTimesCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "removeEntPwdQuestionFailTimesCache",
					"entResNo is null");
			return;
		}
		String key = CacheKeyGen.genEntPwdQuestionFailTimesKey(entResNo);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的企业密保失败次数
	 * 
	 * @param entResNo
	 */
	public Integer getEntPwdQuestionFailTimesCache(String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			SystemLog.warn(MOUDLENAME, "getEntPwdQuestionFailTimesCache",
					"entResNo is null");
			return null;
		}
		String key = CacheKeyGen.genCarderPwdQuestionFailTimesKey(entResNo);
		Integer questionFailTimes = (Integer) cacheUtil.getChangeRedisUtil()
				.get(key, Integer.class);
		if (null == questionFailTimes) {
			questionFailTimes = 0;
		}
		return questionFailTimes;
	}

	/**
	 * add缓存中的双签员登录密码失败次数
	 * 
	 * @param userName
	 * @param loginFailTimes
	 */
	public void addCheckerLoginFailTimesCache(String userName,
			Integer loginFailTimes) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "addCheckerLoginFailTimesCache",
					"userName is null");
			return;
		}
		if (null == loginFailTimes) {
			SystemLog.warn(MOUDLENAME, "addCheckerLoginFailTimesCache",
					"loginFailTimes is null");
			return;
		}
		String key = CacheKeyGen.genCheckerLoginFailTimesKey(userName);
		cacheUtil.getChangeRedisUtil().add(key, loginFailTimes);
	}

	/**
	 * remove缓存中的双签员登录密码失败次数
	 * 
	 * @param userName
	 */
	public void removeCheckerLoginFailTimesCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "removeCheckerLoginFailTimesCache",
					"userName is null");
			return;
		}
		String key = CacheKeyGen.genCheckerLoginFailTimesKey(userName);
		cacheUtil.getChangeRedisUtil().remove(key);
	}

	/**
	 * select缓存中的双签员登录密码失败次数
	 * 
	 * @param entResNo
	 * @param operNo
	 */
	public Integer getCheckerLoginFailTimesCache(String userName) {
		if (StringUtils.isBlank(userName)) {
			SystemLog.warn(MOUDLENAME, "getCheckerLoginFailTimesCache",
					"userName is null");
			return null;
		}

		String key = CacheKeyGen.genCheckerLoginFailTimesKey(userName);
		Integer loginFailTimes = (Integer) cacheUtil.getChangeRedisUtil().get(
				key, Integer.class);
		if (null == loginFailTimes) {
			loginFailTimes = 0;
		}
		return loginFailTimes;
	}

	/**
	 * 计算解锁的倒计时时间，单位：秒
	 * 
	 * @param time
	 *            解锁时间，格式：hh:mm:ss
	 * @return
	 */
	public static long computeUnlockSecond(String time) {
		long seconds = 86400;
		if (StringUtils.isBlank(time) || !time.contains(":")
				|| 8 != time.trim().length()) {
			return seconds;
		}
		String[] timeInfo = time.split(":");
		try {
			if (3 == timeInfo.length) {
				seconds = diffSeconds(Integer.parseInt(timeInfo[0]),
						Integer.parseInt(timeInfo[1]),
						Integer.parseInt(timeInfo[2]));
			}
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "computeUnlockSecond",
					"String convert Int error： time[" + time + "]", e);
		}
		return seconds+1;
	}

	private static long diffSeconds(int hours, int minute, int seconds) {
		Calendar future = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		future.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
				now.get(Calendar.DATE), hours, minute, seconds);
		long timeOne = future.getTimeInMillis();
		long timeTwo = now.getTimeInMillis();
		long diffTime = (timeOne - timeTwo) / 1000;
		return diffTime;
	}
}
