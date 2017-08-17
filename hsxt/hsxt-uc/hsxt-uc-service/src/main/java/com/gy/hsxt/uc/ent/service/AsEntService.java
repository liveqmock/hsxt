/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.ent.service;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.PageUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntTaxRate;
import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLog;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;
import com.gy.hsxt.uc.as.bean.ent.AsQueryEntCondition;
import com.gy.hsxt.uc.as.bean.enumerate.AsEntUpdateLogTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsBankAcctInfoService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.ent.bean.BelongEntInfo;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.bean.EntTaxRate;
import com.gy.hsxt.uc.ent.mapper.AsEntUpdateLogMapper;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.ent.mapper.EntTaxRateMapper;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.service.OperatorService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 用户中心供接入调用的企业相关服务接口
 * 
 * @Package: com.gy.hsxt.uc.ent.service
 * @ClassName: AsEntService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-21 下午12:30:22
 * @version V1.0
 */
@Service
public class AsEntService implements IUCAsEntService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.ent.service.AsEntService";
	@Autowired
	private EntExtendInfoMapper entExtendInfoMapper;
	@Autowired
	private EntStatusInfoMapper entStatusInfoMapper;

	@Autowired
	private OperatorService operatorService;
	@Autowired
	private IUCAsMobileService mobileService;
	@Autowired
	private IUCAsReceiveAddrInfoService receiveAddrInfoService;
	@Autowired
	CommonCacheService commonCacheService;
	@Autowired
	EntTaxRateMapper entTaxRateMapper;
	@Autowired
	UCAsPwdService asPwdService;

	@Autowired
	AsEntUpdateLogMapper asEntUpdateLogMapper;

	@Autowired
	UCAsBankAcctInfoService asBankAcctInfoService;
	
	@Autowired
	CommonService commonService;

	/**
	 * 查询企业一般信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}

		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		return entExtendInfo.bulidAsEntBaseInfo();
	}

	/**
	 * 查询企业扩展信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return 企业扩展信息
	 * @throws HsException
	 */
	public AsEntExtendInfo searchEntExtInfo(String entCustId)
			throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		AsEntExtendInfo ret = entExtendInfo.buildAsEntExtInfo();
		ret.setAsEntStatusInfo(entStatusInfo.buildAsEntStatusInfo());

		return ret;
	}

	/**
	 * 查询企业重要信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public AsEntMainInfo searchEntMainInfo(String entCustId) throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		return entExtendInfo.buildAsEntMainInfo();
	}

	/**
	 * 查询企业所有信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public AsEntAllInfo searchEntAllInfo(String entCustId) throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
//优化企业扩展信息只需一次查询
		// 查询企业基本信息
		AsEntBaseInfo asBaseInfo = null;//searchEntBaseInfo(entCustId);
		// 查询企业重要信息
		AsEntMainInfo asMainInfo =null;// searchEntMainInfo(entCustId);
		// 查询企业状态信息
		AsEntStatusInfo asStatusInfo =null;// searchEntStatusInfo(entCustId);

		AsEntExtendInfo asExtendInfo = null;//searchEntExtInfo(entCustId);
		
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		CustIdGenerator.isEntExtendExist(entExtendInfo, entCustId);
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		asStatusInfo=entStatusInfo.buildAsEntStatusInfo();
		
		asExtendInfo = entExtendInfo.buildAsEntExtInfo();
		asExtendInfo.setAsEntStatusInfo(asStatusInfo);
		asMainInfo=entExtendInfo.buildAsEntMainInfo();
		asBaseInfo=entExtendInfo.bulidAsEntBaseInfo();
		
		AsEntAllInfo allInfo = new AsEntAllInfo();
		allInfo.setBaseInfo(asBaseInfo);
		allInfo.setMainInfo(asMainInfo);
		allInfo.setStatusInfo(asStatusInfo);
		allInfo.setExtendInfo(asExtendInfo);
		return allInfo;
	}

	/**
	 * 查询企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public AsEntStatusInfo searchEntStatusInfo(String entCustId)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(entStatusInfo, entCustId);
		return entStatusInfo.buildAsEntStatusInfo();
	}

	/**
	 * 查询隶属企业信息
	 * 
	 * @param entResNo
	 *            企业互生号， 必传
	 * 
	 * @param blongEntCustType
	 *            隶属企业的客户类型：2 成员企业、3 托管企业、4 服务公司、5 管理公司，必传
	 * 
	 * @param belongEntResNo
	 *            隶属企业的互生号 ，可选
	 * @param belongEntName
	 *            隶属企业的名称 ，可选
	 * @param belongEntContacts
	 *            隶属企业的联系人，可选
	 * @param page
	 *            分页信息 ， 必传
	 * @return
	 * @throws HsException
	 */
	@Deprecated
	public PageData<AsBelongEntInfo> listBelongEntInfo(String entResNo,
			Integer blongEntCustType, String belongEntResNo,
			String belongEntName, String belongEntContacts, Page page)
			throws HsException {
		AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
		condition.setEntResNo(entResNo);
		condition.setBelongEntContacts(belongEntContacts);
		condition.setBelongEntName(belongEntName);
		condition.setBelongEntResNo(belongEntResNo);
		condition.setBlongEntCustType(blongEntCustType);
		return listBelongEntInfo(condition, page);
	}

	/**
	 * 查询隶属企业信息
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listBelongEntInfo(
			AsQueryBelongEntCondition condition, Page page) throws HsException {
		// 验证数据
		if (isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"分页对象为空");
		}
		if (isBlank(condition)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"查询条件参数为空");
		}
		condition.validParam();

		// 查询企业信息
		String entCustId = commonCacheService.findEntCustIdByEntResNo(condition
				.getEntResNo());
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ condition.getEntResNo() + "]");
		}
		AsEntBaseInfo entBaseInfo = searchEntBaseInfo(entCustId);
		int myEntCustType = entBaseInfo.getEntCustType();
		Integer subEntCustType = condition.getBlongEntCustType();
		if (myEntCustType == 4 && subEntCustType != null && subEntCustType == 4) {
			// 服务公司查询 服务公司 团队资源
		} else {// 上级企业查询下属企业
			// 设置下属企业互生号前缀
			condition.setPidLikeByType(myEntCustType);
		}
		// 查询隶属企业列表
		int size = entExtendInfoMapper.countBlongEnt(condition, myEntCustType);

		if (size == 0) {
			return null;
		}

		// 分页查询隶属企业信息
		List<BelongEntInfo> list = entExtendInfoMapper.listBelongEnt(condition,
				entBaseInfo.getEntCustType(), page);
		// 构建返回结果
		List<AsBelongEntInfo> belongEntList = new ArrayList<AsBelongEntInfo>();
		for (BelongEntInfo belongEnt : list) {
			AsBelongEntInfo asBelongEnt = new AsBelongEntInfo();
			BeanUtils.copyProperties(belongEnt, asBelongEnt);
			asBelongEnt.setOpenDate(DateUtil.timestampToString(
					belongEnt.getOpenDate(), "yyyy-MM-dd HH:mm:ss"));
			String reamNameAuthSatus = belongEnt.getRealNameAuthSatus();
			if (StringUtils.isNotBlank(reamNameAuthSatus)
					&& StringUtils.isNumer(reamNameAuthSatus)) {
				asBelongEnt.setRealNameAuthSatus(Integer
						.valueOf(reamNameAuthSatus));
			}
			belongEntList.add(asBelongEnt);
		}
		return new PageData<AsBelongEntInfo>(size, belongEntList);
	}
	
	/**
	 * 查询隶属企业信息
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listAllBelongEntInfo(
			AsQueryBelongEntCondition condition, Page page) throws HsException {
		// 验证数据
		if (isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"分页对象为空");
		}
		if (isBlank(condition)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"查询条件参数为空");
		}
		condition.validParam();

		// 查询企业信息
		String entCustId = commonCacheService.findEntCustIdByEntResNo(condition
				.getEntResNo());
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ condition.getEntResNo() + "]");
		}
		AsEntBaseInfo entBaseInfo = searchEntBaseInfo(entCustId);
		int myEntCustType = entBaseInfo.getEntCustType();
		Integer subEntCustType = condition.getBlongEntCustType();
		if (myEntCustType == 4 && subEntCustType != null && subEntCustType == 4) {
			// 服务公司查询 服务公司 团队资源
		} else {// 上级企业查询下属企业
			// 设置下属企业互生号前缀
			condition.setPidLikeByType(myEntCustType);
		}
		// 查询隶属企业列表
		int size = entExtendInfoMapper.countAllBlongEnt(condition, myEntCustType);

		if (size == 0) {
			return null;
		}

		// 分页查询隶属企业信息
		List<BelongEntInfo> list = entExtendInfoMapper.listAllBelongEnt(condition,
				entBaseInfo.getEntCustType(), page);
		// 构建返回结果
		List<AsBelongEntInfo> belongEntList = new ArrayList<AsBelongEntInfo>();
		for (BelongEntInfo belongEnt : list) {
			AsBelongEntInfo asBelongEnt = new AsBelongEntInfo();
			BeanUtils.copyProperties(belongEnt, asBelongEnt);
			asBelongEnt.setOpenDate(DateUtil.timestampToString(
					belongEnt.getOpenDate(), "yyyy-MM-dd HH:mm:ss"));
			String reamNameAuthSatus = belongEnt.getRealNameAuthSatus();
			if (StringUtils.isNotBlank(reamNameAuthSatus)
					&& StringUtils.isNumer(reamNameAuthSatus)) {
				asBelongEnt.setRealNameAuthSatus(Integer
						.valueOf(reamNameAuthSatus));
			}
			belongEntList.add(asBelongEnt);
		}
		return new PageData<AsBelongEntInfo>(size, belongEntList);
	}
	
	
	
	/**
	 * 查询企业信息
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listEntInfoByCondition(
			AsQueryEntCondition condition, Page page) throws HsException {
		// 验证数据
		if (isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"分页对象为空");
		}
		if(StringUtils.isBlank(condition.getBeginDate())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"起始日期为空");
		}
		
		if(StringUtils.isBlank(condition.getEndDate())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"结束日期为空");
		}

		// 查询隶属企业列表
		int size = entExtendInfoMapper.selectEntCountByCondition(condition,page);

		if (size == 0) {
			return null;
		}

		// 分页查询隶属企业信息
		List<BelongEntInfo> list = entExtendInfoMapper.selectEntByCondition(condition,
			page);
		// 构建返回结果
		List<AsBelongEntInfo> belongEntList = new ArrayList<AsBelongEntInfo>();
		for (BelongEntInfo belongEnt : list) {
			AsBelongEntInfo asBelongEnt = new AsBelongEntInfo();
			BeanUtils.copyProperties(belongEnt, asBelongEnt);
			asBelongEnt.setOpenDate(DateUtil.timestampToString(
					belongEnt.getOpenDate(), "yyyy-MM-dd HH:mm:ss"));
			String reamNameAuthSatus = belongEnt.getRealNameAuthSatus();
			if (StringUtils.isNotBlank(reamNameAuthSatus)
					&& StringUtils.isNumer(reamNameAuthSatus)) {
				asBelongEnt.setRealNameAuthSatus(Integer
						.valueOf(reamNameAuthSatus));
			}
			belongEntList.add(asBelongEnt);
		}
		return new PageData<AsBelongEntInfo>(size, belongEntList);
	}

	/**
	 * 修改企业一般信息
	 * 
	 * @param entBaseInfo
	 *            企业基本信息
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void updateEntBaseInfo(AsEntBaseInfo entBaseInfo, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作者客户号不能为空");
		}
		if (isBlank(entBaseInfo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业基本信息对象为空");
		}
		if (isBlank(entBaseInfo.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		// 更新企业一般信息
		EntExtendInfo extendInfo = EntExtendInfo
				.generateEntendInfo(entBaseInfo);
		String entCustName = entBaseInfo.getEntName();
		entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo);
		if (StringUtils.isNotBlank(entCustName)) {
			updatePosEnt(entCustName,
					entBaseInfo.getEntCustType(), entBaseInfo.getEntResNo(),
					entBaseInfo.getEntCustId());
			EntStatusInfo statusInfo = new EntStatusInfo();
			statusInfo.setEntCustId(extendInfo.getEntCustId());
			statusInfo.setEntCustName(entCustName);
			commonCacheService.updateEntStatusInfo(statusInfo, operator);
		}
		commonCacheService.removeEntExtendInfoCache(extendInfo.getEntCustId());// 清除缓存

	}

	/**
	 * 更新POS的企业信息
	 * 
	 * @param ent
	 *            企业信息，如果参数为null，不会更新，企业互生号不能为空
	 */
	private void updatePosEnt(String entName, Integer entType, String entResNo,
			String entCustId) {
		// 如果没有修改企业名称和企业类型，不需更新POS的信息
		if (StringUtils.isBlank(entName) && entType == null) {
			SystemLog.info(MOUDLENAME, "updatePosEnt", "POS企业信息缓存不需更新");
			return;
		}

		if (StringUtils.isBlank(entResNo) && StringUtils.isBlank(entCustId)) {
			SystemLog.warn(MOUDLENAME, "updatePosEnt",
					"POS企业信息更新失败，由于客户号和企业资源号同时为空，无法查询到相关企业信息.");
			return;
		}

		// 获取POS企业信息
		AsPosEnt oldEnt = null;
		if (StringUtils.isNotBlank(entCustId)) {
			EntExtendInfo info = commonCacheService
					.searchEntExtendInfo(entCustId);
			if (info == null) {
				SystemLog.warn(MOUDLENAME, "updatePosEnt",
						"POS企业信息更新失败，由于客户号无法查询到相关企业信息.企业客户号：" + entCustId);
				return;
			}
			entResNo = info.getEntResNo();
		}

		oldEnt = commonCacheService.getPosEntCache(entResNo);

		if (oldEnt == null) {
			oldEnt = new AsPosEnt();
			// 如果企业名称或类型为空，获取企业信息
			if (StringUtils.isBlank(entName) || entType == null) {
				String custId = commonCacheService
						.findEntCustIdByEntResNo(entResNo);
				EntExtendInfo entInfo = commonCacheService
						.searchEntExtendInfo(custId);
				oldEnt.setEntName(entInfo.getEntCustName());
				oldEnt.setEntType(String.valueOf(entInfo.getCustType()));
			} else {
				// 如果不为空直接赋值
				oldEnt.setEntName(entName);
				oldEnt.setEntType(String.valueOf(entType));
			}
			oldEnt.setEntResNo(entResNo);
			oldEnt.setVersion("1");
			commonCacheService.addPosEntCache(entResNo, oldEnt);
			SystemLog.info(MOUDLENAME, "updatePosEnt", "POS 企业信息缓存添加成功");
			return;
		}

		// 比对是否修改了企业名称和企业类型
		if (StringUtils.isNotBlank(entName)) {
			oldEnt.setEntName(entName);
		}

		if (entType != null) {
			oldEnt.setEntType(String.valueOf(entType));
		}
		String version = genVersion(oldEnt.getVersion());
		oldEnt.setVersion("" + version);
		// 重设缓存数据
		commonCacheService.addPosEntCache(entResNo, oldEnt);
		SystemLog.info(MOUDLENAME, "updatePosEnt", "POS 企业信息缓存更新成功");
		return;
	}

	/**
	 * 生成基础信息版本号
	 * 
	 * @param vs
	 * @return
	 */
	public String genVersion(String vs) {
		int version = Integer.valueOf(vs);
		version = version >= 99 ? 1 : version + 1;

		return String.format("%02d", version);
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
	public void updateEntExtInfo(AsEntExtendInfo entExtInfo, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operator参数为空");
		}
		if (isBlank(entExtInfo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业基本信息对象为空");
		}
		String entCustId=entExtInfo.getEntCustId();
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		EntExtendInfo source = commonCacheService
				.searchEntExtendInfo(entCustId);
		Timestamp now = getNowTimestamp();
		EntExtendInfo extendInfo = EntExtendInfo.generateEntExtInfo(entExtInfo);
		extendInfo.setUpdatedby(operator);
		extendInfo.setUpdateDate(now);
		commonCacheService.updateEntExtInfo(extendInfo, operator);
		
		String entCustName = entExtInfo.getEntCustName();
		if(StringUtils.isNotBlank(entCustName)){
			updatePosEnt(entExtInfo.getEntCustName(), entExtInfo.getCustType(),
					entExtInfo.getEntResNo(), entCustId);
			EntStatusInfo entStatusInfo = new EntStatusInfo();
			entStatusInfo.setEntCustId(extendInfo.getEntCustId());
			entStatusInfo.setEntCustName(entCustName);
			commonCacheService.updateEntStatusInfo(entStatusInfo, operator);
		}
		//如果企业名称或联系电话有变，需通知电商系统
		commonService.isEntNameOrPhoneChag(source, extendInfo);
	}

	/**
	 * 修改企业状态信息
	 * 
	 * @param entStatusInfo
	 *            企业状态信息
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#updateEntStatusInfo(com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo,
	 *      java.lang.String)
	 */
	@Transactional
	public void updateEntStatusInfo(AsEntStatusInfo entStatusInfo,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作员客户号为空");
		}
		if (isBlank(entStatusInfo) || isBlank(entStatusInfo.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业状态对象或企业客户号为空");
		}

		// 更新企业状态信息

		EntStatusInfo dbEntStatusInfo = EntStatusInfo
				.generateEntStatusInfo(entStatusInfo);
		dbEntStatusInfo.setUpdatedby(operator);
		dbEntStatusInfo.setUpdateDate(StringUtils.getNowTimestamp());
		commonCacheService.updateEntStatusInfo(dbEntStatusInfo, operator);
		Integer isClosedEnt = dbEntStatusInfo.getIsClosedEnt();
		if(null != isClosedEnt && 1 == isClosedEnt){//
			commonService.kickedOut(entStatusInfo.getEntCustId());
			commonCacheService.removeEntExtendInfoCache(entStatusInfo.getEntCustId());
		}
	}

	
	/**
	 * 绑定手机（手机信息入库）
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param mobile
	 *            手机号码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#bindMobile(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional
	public void bindMobile(String entCustId, String mobile) throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(mobile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号或手机号为空");
		}

		// 更新企业手机信息
		EntExtendInfo extendInfo = new EntExtendInfo();
		extendInfo.setEntCustId(entCustId);
		extendInfo.setContactPhone(mobile);
		Timestamp updateDate = new Timestamp(new Date().getTime());
		extendInfo.setUpdateDate(updateDate);
		extendInfo.setUpdatedby(entCustId);
		entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo);
		commonCacheService.removeEntExtendInfoCache(extendInfo.getEntCustId());// 清除缓存
	}

	/**
	 * 手机认证
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param mobile
	 *            手机号码
	 * @param smsCode
	 *            手机验证码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#authMobile(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public void authMobile(String entCustId, String mobile, String smsCode)
			throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(mobile) || isBlank(smsCode)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 验证手机验证码
		String smsCodeCache = commonCacheService.getSmsCodeCache(mobile);
		String isnoreCheckCode = StringUtils.nullToEmpty(SysConfig
				.getIgnoreCheckCode());
		if (!"1".equals(isnoreCheckCode)) {// 是否忽略手机短信验证码验证（0：不忽略 1：忽略）
			if (null == smsCodeCache) {
				throw new HsException(
						ErrorCodeEnum.SM_CODE_NOT_MATCH.getValue(),
						ErrorCodeEnum.SM_CODE_NOT_MATCH.getDesc());
			}
			if (!smsCodeCache.equals(smsCode)) {
				throw new HsException(
						ErrorCodeEnum.SM_CODE_NOT_MATCH.getValue(),
						ErrorCodeEnum.SM_CODE_NOT_MATCH.getDesc());
			}
		}
		// 验证通过，更新手机认证状态
		EntStatusInfo statusInfo = new EntStatusInfo();
		statusInfo.setEntCustId(entCustId);
		statusInfo.setIsAuthMobile(1);
		Timestamp updateDate = new Timestamp(new Date().getTime());
		statusInfo.setUpdateDate(updateDate);
		statusInfo.setUpdatedby(entCustId);
		entStatusInfoMapper.updateByPrimaryKeySelective(statusInfo);
		commonCacheService.removeEntStatusInfoCache(entCustId);// 清除缓存

	}

	/**
	 * 绑定邮箱
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param email
	 *            绑定邮箱
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#bindEmail(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional
	public void bindEmail(String entCustId, String email) throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(email)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 更新企业邮箱信息
		EntExtendInfo extendInfo = new EntExtendInfo();
		extendInfo.setEntCustId(entCustId);
		extendInfo.setContactEmail(email);
		Timestamp updateDate = new Timestamp(new Date().getTime());
		extendInfo.setUpdateDate(updateDate);
		extendInfo.setUpdatedby(entCustId);
		entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo);
		commonCacheService.removeEntExtendInfoCache(extendInfo.getEntCustId());// 清除缓存
	}

	/**
	 * 企业重要信息状态变更
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param status
	 *            企业重要信息变更状态 是否重要信息变更期间1:是 0：否
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#changeEntMainInfoStatus(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public void changeEntMainInfoStatus(String entCustId, String status,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(status) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 更新企业 重要信息 变更 状态字段
		EntStatusInfo statusInfo = new EntStatusInfo();
		statusInfo.setEntCustId(entCustId);
		statusInfo.setIsKeyinfoChanged(Integer.parseInt(status));
		Timestamp updateDate = new Timestamp(new Date().getTime());
		statusInfo.setUpdateDate(updateDate);
		statusInfo.setUpdatedby(operator);
		entStatusInfoMapper.updateByPrimaryKeySelective(statusInfo);
		commonCacheService.removeEntStatusInfoCache(entCustId);// 清除缓存
	}

	/**
	 * 启用、停止企业积分活动
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param pointStatus
	 *            企业状态 5：已注销 成员企 6：申请停止积分活动中 托管企业用 7：停止积分活动 托管企业用
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#updateEntPointStatus(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public void updateEntPointStatus(String entCustId, String pointStatus,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(pointStatus) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 更新企业状态字段
		EntStatusInfo statusInfo = new EntStatusInfo();
		statusInfo.setEntCustId(entCustId);
		statusInfo.setBaseStatus(Integer.parseInt(pointStatus));
		Timestamp updateDate = new Timestamp(new Date().getTime());
		statusInfo.setUpdateDate(updateDate);
		statusInfo.setUpdatedby(operator);
		entStatusInfoMapper.updateByPrimaryKeySelective(statusInfo);

		// 更新缓存
		commonCacheService.removeEntStatusInfoCache(entCustId);
	}

	/**
	 * 批量查询企业客户号
	 * 
	 * @param entResnoList
	 *            企业互生号列表
	 * @return
	 * @throws HsException
	 */
	public Map<String, String> findEntCustIdByEntResNo(List<String> entResnoList)
			throws HsException {
		return commonCacheService.findEntCustIdByEntResNo(entResnoList);
	}

	/**
	 * 批量查询企业当前税率
	 * 
	 * @param entResnoList
	 *            企业互生号列表
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#listEntTaxRate(java.util.List)
	 */
	@Override
	public Map<String, String> listEntTaxRate(List<String> entResnoList)
			throws HsException {
		return commonCacheService.listEntTaxRate(entResnoList);
	}

	/**
	 * 企业是否激活状态
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#isActived(java.lang.String)
	 */
	@Override
	public boolean isActived(String entResNo) throws HsException {
		// 验证数据
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "]");
		}
		AsEntStatusInfo asEntStatusInfo = searchEntStatusInfo(entCustId);
		Integer isClosedEnt = asEntStatusInfo.getIsClosedEnt();
		if(null != isClosedEnt && 1 == isClosedEnt){
			return false;
		}
		if (asEntStatusInfo.getBaseStatus().intValue() == 1
				|| asEntStatusInfo.getBaseStatus().intValue() == 2) {
			return true;
		}
		
		return false;
	}

	/**
	 * 查询企业重要信息通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#getMainInfoByResNo(java.lang.String)
	 */
	@Override
	public AsEntMainInfo getMainInfoByResNo(String entResNo) throws HsException {
		// 验证数据
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "]");
		}
		return searchEntMainInfo(entCustId);
	}

	/**
	 * 匹配企业版本号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param entVersion
	 *            企业版本号(重要信息修改计数)
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#matchEntVersion(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean matchEntVersion(String entResNo, String entVersion)
			throws HsException {
		// 验证数据
		if (isBlank(entResNo) || isBlank(entVersion)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		AsEntMainInfo info = getMainInfoByResNo(entResNo);
		if (info.getEntVersion().trim().equals(entVersion.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 设置企业交易密码
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param tradePwd
	 *            交易密码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#setTradePwd(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setTradePwd(String entCustId, String tradePwd)
			throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(tradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 设置修改交易密码
		EntStatusInfo statusInfo = new EntStatusInfo();
		statusInfo.setEntCustId(entCustId);
		String saltValue = CSPRNG.generateRandom(8);
		String pwd = StringEncrypt.sha256(tradePwd + saltValue);
		statusInfo.setPwdTrans(pwd);
		statusInfo.setPwdTransSaltValue(saltValue);
		statusInfo.setUpdateDate(getNowTimestamp());
		entStatusInfoMapper.updateByPrimaryKeySelective(statusInfo);
		// 更新缓存
		commonCacheService.removeEntStatusInfoCache(entCustId);

	}

	/**
	 * 修改企业交易密码
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param oldTradePwd
	 *            旧交易密码 已还原的md5密码
	 * @param newTradePwd
	 *            新交易密码 已还原的md5密码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#updateTradePwd(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateTradePwd(String entCustId, String oldTradePwd,
			String newTradePwd) throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(oldTradePwd) || isBlank(newTradePwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		// 验证企业老交易密码
		EntStatusInfo entStatusInfo = commonCacheService
				.getEntStatusInfoCache(entCustId);
		entStatusInfo = entStatusInfoMapper.selectByPrimaryKey(entCustId);
		if (entStatusInfo == null) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getDesc());
		}
		String sha256Pwd = StringEncrypt.sha256(oldTradePwd
				+ entStatusInfo.getPwdTransSaltValue());
		if (!sha256Pwd.equals(entStatusInfo.getPwdTrans())) {
			throw new HsException(ErrorCodeEnum.PWD_TRADE_ERROR.getValue(),
					ErrorCodeEnum.PWD_TRADE_ERROR.getDesc());
		}
		// 设置密码
		setTradePwd(entCustId, newTradePwd);
	}

	/**
	 * 上传企业联系人授权委托书
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param authProxyFile
	 *            授权委托书附件 id，必传
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#uploadAuthProxyFile(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void uploadAuthProxyFile(String entCustId, String authProxyFile,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(authProxyFile)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 修改联系人授权委托书
		EntExtendInfo extendInfo = new EntExtendInfo();
		extendInfo.setEntCustId(entCustId);
		extendInfo.setContactProxy(authProxyFile);
		extendInfo.setUpdateDate(getNowTimestamp());
		extendInfo.setUpdatedby(operator);
		entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo);
		commonCacheService.removeEntExtendInfoCache(extendInfo.getEntCustId());// 更新缓存
	}

	/**
	 * 
	 * 通过企业的客户号类型查询企业列表
	 * 
	 * @param custType
	 *            (企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台; 8
	 *            其它地区平台 11：操作员 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台51 非持卡人; 52
	 *            非互生格式化企业)
	 * @param custType
	 * @return
	 */
	@Override
	public List<AsEntInfo> listEntInfo(int custType) throws HsException {
		List<AsEntInfo> resultList = new ArrayList<AsEntInfo>();
		List<EntExtendInfo> list = null;
		try {
			list = entExtendInfoMapper.listEntendsInfo(custType);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.QUERY_CUSTTYPE_ENTINFO_ERROR.getValue(),
					e.getMessage());
		}

		AsEntInfo entInfo = null;
		if (null != list && !list.isEmpty()) {
			for (EntExtendInfo extendInfo : list) {
				entInfo = new AsEntInfo();
				BeanUtils.copyProperties(extendInfo, entInfo);
				entInfo.setEntCustName(StringUtils.nullToEmpty(extendInfo
						.getEntCustName()));
				entInfo.setEntCustNameEn(StringUtils.nullToEmpty(extendInfo
						.getEntCustNameEn()));
				resultList.add(entInfo);
			}
		}
		return resultList;
	}

	
	/**
	 * 通过企业的客户号类型查询服务公司列表(专门给李葵官网调用)
	 * @param cityList
	 * @return
	 * @throws HsException
	 */
	public List<AsEntInfo> listCityListEntInfo(List<String> cityList) throws HsException {
		List<AsEntInfo> resultList = new ArrayList<AsEntInfo>();
		List<EntExtendInfo> list = null;
		try {
			list = entExtendInfoMapper.listCityListEntInfo(cityList);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.QUERY_CUSTTYPE_ENTINFO_ERROR.getValue(),
					e.getMessage());
		}

		AsEntInfo entInfo = null;
		if (null != list && !list.isEmpty()) {
			for (EntExtendInfo extendInfo : list) {
				entInfo = new AsEntInfo();
				BeanUtils.copyProperties(extendInfo, entInfo);
				entInfo.setEntCustName(StringUtils.nullToEmpty(extendInfo
						.getEntCustName()));
				entInfo.setEntCustNameEn(StringUtils.nullToEmpty(extendInfo
						.getEntCustNameEn()));
				resultList.add(entInfo);
			}
		}
		return resultList;
	}
	
	/**
	 * 查询地区平台信息
	 * 
	 * @return
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#searchRegionalPlatform(int)
	 */
	@Override
	public AsEntInfo searchRegionalPlatform() throws HsException {
		List<AsEntInfo> list = listEntInfo(CustType.AREA_PLAT.getCode());
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询企业的联系信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#searchEntContactInfo(java.lang.String)
	 */
	@Override
	public AsEntContactInfo searchEntContactInfo(String entCustId)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号为空");
		}
		AsEntContactInfo entContactInfo = new AsEntContactInfo();
		String custId = entCustId.trim();
		AsEntAllInfo entAllInfo = searchEntAllInfo(custId);
		List<AsReceiveAddr> list = receiveAddrInfoService
				.listReceiveAddrByCustId(custId);
		entContactInfo.setReceiveAddrList(list);
		AsEntBaseInfo baseInfo = entAllInfo.getBaseInfo();
		AsEntMainInfo mainInfo = entAllInfo.getMainInfo();
		if (null != baseInfo) {
			entContactInfo.setContactAddr(StringUtils.nullToEmpty(baseInfo
					.getContactAddr()));
			entContactInfo.setEntResNo(StringUtils.nullToEmpty(baseInfo
					.getEntResNo()));
			entContactInfo.setEntCustId(StringUtils.nullToEmpty(baseInfo
					.getEntCustId()));
			if (null != baseInfo.getEntCustType()) {
				entContactInfo.setEntCustType(baseInfo.getEntCustType());
			}
			entContactInfo.setPostCode(StringUtils.nullToEmpty(baseInfo
					.getPostCode()));
		}
		if (null != mainInfo) {
			entContactInfo.setContactPerson(StringUtils.nullToEmpty(mainInfo
					.getContactPerson()));
			entContactInfo.setContactPhone(StringUtils.nullToEmpty(mainInfo
					.getContactPhone()));
			entContactInfo.setEntName(StringUtils.nullToEmpty(mainInfo
					.getEntName()));
			entContactInfo.setEntNameEn(StringUtils.nullToEmpty(mainInfo
					.getEntNameEn()));
			entContactInfo.setEntRegAddr(StringUtils.nullToEmpty(mainInfo
					.getEntRegAddr()));
		}
		return entContactInfo;
	}

	/**
	 * 批量查询企业的客户号
	 * 
	 * @param list
	 *            企业互生号列表
	 * @return 企业的客户号列表
	 * @throws HsException
	 */
	@Override
	public Map<String, String> listEntCustId(List<String> list)
			throws HsException {
		if (null == list || list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业互生号为空");
		}
		Map<String, String> finalMap = new HashMap<String, String>();
		List<EntStatusInfo> resultList = batchSearchEntCustId(list);
		String entResNo = "";
		String entCustId = "";
		for (EntStatusInfo statusInfo : resultList) {
			entResNo = StringUtils.nullToEmpty(statusInfo.getEntResNo());
			entCustId = StringUtils.nullToEmpty(statusInfo.getEntCustId());
			if (!StringUtils.isEmpty(entCustId)
					&& !StringUtils.isEmpty(entResNo)) {
				finalMap.put(entResNo, entCustId);
			}
		}
		return finalMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<EntStatusInfo> batchSearchEntCustId(List<String> dataList)
			throws HsException {
		List<EntStatusInfo> resultList = new ArrayList<EntStatusInfo>();
		List<EntStatusInfo> tempList = null;
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
						tempList = entStatusInfoMapper
								.batchSelectEntCustId(subList);
						if (null != tempList && !tempList.isEmpty()) {
							resultList.addAll(tempList);
							tempList.clear();
						}
					}
				}
			} else {
				tempList = entStatusInfoMapper.batchSelectEntCustId(dataList);
				if (null != tempList && !tempList.isEmpty()) {
					resultList.addAll(tempList);
					tempList.clear();
				}
			}
			return resultList;
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "batchSearchEntCustId",
					"批量查询企业客户号失败：\r\n", e);
			throw new HsException(
					ErrorCodeEnum.BATCH_QUERY_ENTCUSTID_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 查询企业客户号通过操作员互生号
	 * 
	 * @param operResNo
	 *            操作员互生号
	 * @return 企业客户号
	 * @throws HsException
	 */
	@Override
	public String findEntCustIdByOperResNo(String operResNo) throws HsException {
		if (StringUtils.isBlank(operResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员互生号为空");
		}
		return operatorService.findEntCustIdByOperResNo(operResNo);
	}

	/**
	 * 通过企业的互生号查询企业的客户号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return 企业客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntService#findEntCustIdByEntResNo(java.lang.String)
	 */
	@Override
	public String findEntCustIdByEntResNo(String entResNo) throws HsException {
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "]");
		}
		return entCustId;
	}

	/**
	 * 查询未生效的企业税率信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 * @throws HsException
	 */
	public AsEntTaxRate searchNoEffectEntTaxRateInfo(String entCustId)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号不能为空");
		}
		EntTaxRate entTaxRate = null;
		try {
			entTaxRate = entTaxRateMapper.selectByPrimaryKey(entCustId);
		} catch (HsException e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "searchNoEffectEntTaxRateInfo",
					"查询未生效的企业税率信息失败：\r\n", e);
			throw new HsException(
					ErrorCodeEnum.QUERY_NOEFFECT_ENTTAXRATE_ERROR.getValue(),
					e.getMessage());
		}
		if (null == entTaxRate) {
			return null;
		}
		AsEntTaxRate asEntTaxRate = new AsEntTaxRate();
		BeanUtils.copyProperties(entTaxRate, asEntTaxRate);
		return asEntTaxRate;
	}

	/**
	 * 删除未生效的企业税率信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	public void deleteNoEffectEntTaxRateInfo(String entCustId, String operCustId)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号不能为空");
		}
		if (isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作员客户号不能为空");
		}
		EntTaxRate entTaxRate = new EntTaxRate();
		entTaxRate.setEntCustId(entCustId.trim());
		entTaxRate.setUpdatedby(operCustId.trim());
		entTaxRate.setIsactive("N");
		updateNoEffectEntTaxRateInfo(entTaxRate);
	}

	/**
	 * 企业修改税率审核通过
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	@Override
	public void updateNoEffectEntTaxRateInfo(AsEntTaxRate asEntTaxRate,
			String operCustId) throws HsException {
		if (null == asEntTaxRate) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业税率信息不能为空");
		}
		if (isBlank(asEntTaxRate.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号不能为空");
		}
		if (isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"操作员客户号不能为空");
		}
		String entCustId = asEntTaxRate.getEntCustId().trim();
		int count = selecTaxRatetCounts(entCustId);
		EntTaxRate entTaxRate = new EntTaxRate();
		BeanUtils.copyProperties(asEntTaxRate, entTaxRate);
		if (count > 0) {// 更新企业税率信息
			entTaxRate.setUpdatedby(operCustId);
			entTaxRate.setUpdateDate(StringUtils.getNowTimestamp());
			updateNoEffectEntTaxRateInfo(entTaxRate);
		} else {// 插入企业税率信息
			if (isBlank(asEntTaxRate.getEntTaxRate())) {
				throw new HsException(
						ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "企业税率不能为空");
			}
			if (isBlank(asEntTaxRate.getActiveDate())) {
				throw new HsException(
						ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "生效日期不能为空");
			}
			if (isBlank(asEntTaxRate.getEntResNo())) {
				throw new HsException(
						ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "企业互生号不能为空");
			}
			entTaxRate.setCreatedby(operCustId);
			saveNoEffectEntTaxRateInfo(entTaxRate);
		}
	}

	/**
	 * 根据企业客户号查询企业记录数
	 * 
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	private int selecTaxRatetCounts(String entCustId) throws HsException {
		int count = 0;
		try {
			count = entTaxRateMapper.selectCounts(entCustId);
		} catch (HsException e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "selecTaxRatetCounts",
					"插入未生效的企业税率信息失败：\r\n", e);
			throw new HsException(
					ErrorCodeEnum.QUERY_NOEFFECT_ENTTAXRATE_COUNT_ERROR
							.getValue(),
					e.getMessage());
		}
		return count;
	}

	/**
	 * 保存企业税率
	 * 
	 * @param entTaxRate
	 * @throws HsException
	 */
	private void saveNoEffectEntTaxRateInfo(EntTaxRate entTaxRate)
			throws HsException {
		try {
			entTaxRateMapper.insertSelective(entTaxRate);
		} catch (HsException e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "saveNoEffectEntTaxRateInfo",
					"插入未生效的企业税率信息失败：\r\n", e);
			throw new HsException(
					ErrorCodeEnum.SAVE_NOEFFECT_ENTTAXRATE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 更新企业税率
	 * 
	 * @param entTaxRate
	 * @throws HsException
	 */
	private void updateNoEffectEntTaxRateInfo(EntTaxRate entTaxRate)
			throws HsException {
		try {
			entTaxRateMapper.updateByPrimaryKeySelective(entTaxRate);
		} catch (HsException e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "updateNoEffectEntTaxRateInfo",
					"更新未生效的企业税率信息失败：\r\n", e);
			throw new HsException(
					ErrorCodeEnum.UPDATE_NOEFFECT_ENTTAXRATE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 地区平台修改企业银行账户并记录修改日志
	 * 
	 * @param asBankAcctInfo
	 *            银行账户信息
	 * @param logList
	 *            修改字段列表
	 * @param logType
	 *            修改类型 0删除银行账号，1新增银行账号
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复核员客户号
	 * @return 企业客户号
	 */
	@Transactional
	public String UpdateEntAccountAndLog(AsBankAcctInfo asBankAcctInfo,
			List<AsEntUpdateLog> logList, Integer logType, String operCustId,
			String confirmerCustId) {
		// 参数校验
		if (logType == null || logList == null || logList.isEmpty()
				|| asBankAcctInfo == null || asBankAcctInfo.getCustId() == null) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数不能为空,logList==null || logList.isEmpty()||asBankAcctInfo==null||logType==null|| asBankAcctInfo.getCustId()==null");
		}

		// 修改日志入库
		String entCustId = asBankAcctInfo.getCustId();
		this.saveEntUpdateLog(logList, entCustId, operCustId, confirmerCustId);

		// 修改银行账户信息
		// 修改类型 0删除银行账号，1新增银行账号，2修改注册信息，
		int type = logType.intValue();
		if (type == AsEntUpdateLogTypeEnum.DEL_ACC.ordinal()) { // 删除银行账户
			// 删除银行账户
			long accId = asBankAcctInfo.getAccId();
			asBankAcctInfoService.unBindBank(accId, UserTypeEnum.ENT.getType());
		} else if (type == AsEntUpdateLogTypeEnum.ADD_ACC.ordinal()) {// 新增银行账户
			// 新增银行账户
			asBankAcctInfoService.bindEntBank(asBankAcctInfo);
		}

		return entCustId;
	}

	/**
	 * 保存企业信息修改记录
	 * 
	 * @param logList
	 *            日志列表
	 * @param entCustId
	 *            企业客户号
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复核员客户号
	 * @return 企业客户号
	 */
	@Transactional
	public String saveEntUpdateLog(List<AsEntUpdateLog> logList,
			String entCustId, String operCustId, String confirmerCustId) {
		// 参数校验
		if (entCustId == null || logList == null || logList.isEmpty()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数不能为空,logList==null || logList.isEmpty()");
		}
		// String entCustId = null;
		String operName = null;
		String checkerName = null;
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		if (operator != null) {
			//0000（系统操作员）
			StringBuilder sb=new StringBuilder();
			sb.append(operator.getOperNo()).append("（");
			sb.append(operator.getOperName()).append("）");
			operName = sb.toString();
		}
		operator = commonCacheService.searchOperByCustId(confirmerCustId);
		if (operator != null) {
			StringBuilder sb=new StringBuilder();
			sb.append(operator.getOperNo()).append("（");
			sb.append(operator.getOperName()).append("）");
			checkerName = sb.toString();
		}
		Timestamp now = StringUtils.getNowTimestamp();
		// 修改日志入库
		for (AsEntUpdateLog asEntUpdateLog : logList) {
			asEntUpdateLog.setEntCustId(entCustId);
			asEntUpdateLog.setUpdatedby(operCustId);
			asEntUpdateLog.setConfirmId(confirmerCustId);
			asEntUpdateLog.setOperName(operName);
			asEntUpdateLog.setConfirmName(checkerName);
			asEntUpdateLog.setUpdateDate(now);
			asEntUpdateLogMapper.insertSelective(asEntUpdateLog);
			entCustId = asEntUpdateLog.getEntCustId();
		}
		return entCustId;
	}

	/**
	 * 地区平台修改关联企业信息并记录修改日志
	 * 
	 * @param asEntExtendInfo
	 *            企业信息
	 * @param logList
	 *            修改字段列表
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复核员客户号
	 * @return 企业客户号
	 */
	@Transactional
	public String UpdateEntAndLog(AsEntExtendInfo asEntExtendInfo,
			List<AsEntUpdateLog> logList, String operCustId,
			String confirmerCustId) {
		// 参数校验
		if (logList == null || logList.isEmpty() || asEntExtendInfo == null
				|| asEntExtendInfo.getEntCustId() == null) {
			throw new HsException(
					ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数不能为空,logList==null || logList.isEmpty()||asEntExtendInfo==null||asEntExtendInfo.getEntCustId()==null");
		}
		// 修改日志入库
		String entCustId = asEntExtendInfo.getEntCustId();

		this.saveEntUpdateLog(logList, entCustId, operCustId, confirmerCustId);

		// 修改企业信息
		asEntExtendInfo.setUpdatedby(operCustId);
		updateEntExtInfo(asEntExtendInfo, operCustId);
		entCustId = asEntExtendInfo.getEntCustId();
		return entCustId;
	}

	/**
	 * 获取企业修改日志列表
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param updateFieldName
	 *            修改信息
	 * @param page
	 *            分页条件，null则查询全部
	 * @return
	 */
	public PageData<AsEntUpdateLog> listEntUpdateLog(String entCustId,
			String updateFieldName, Page page) throws HsException {
		AsEntUpdateLog info = new AsEntUpdateLog();
		info.setEntCustId(entCustId);
		info.setUpdateFieldName(updateFieldName);
		List<AsEntUpdateLog> dataList = asEntUpdateLogMapper.selectList(info);
		for(AsEntUpdateLog entUpdateLog : dataList){
			if(null != entUpdateLog.getUpdateDate()){
				entUpdateLog.setUpdateTime(DateUtil.timestampToString(entUpdateLog.getUpdateDate(),"yyyy-MM-dd HH:mm:ss"));
			}
		}
		return (PageData<AsEntUpdateLog>) PageUtil.subPage(page, dataList);
	}

	/**
	 * 检查企业名称是否存在，可附加条件：客户类型
	 * 
	 * @param entName
	 *            企业名称
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	public boolean isEntExist(String entName, Integer custType) {
		int count = 0;
		if(entName==null){
			entName="";
		}
		if (custType == null) {
			count = entExtendInfoMapper.countEntByEntName(entName);
		} else {
			count = entExtendInfoMapper.countEntByEntNameAndType(entName,
					custType);
		}

		return count >=1 ? true : false;
	}
	
	/**
	 * 检查企业营业执照号是否存在，可附加条件：客户类型
	 * 
	 * @param busiLicenseNo
	 *            营业执照号
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	public boolean isEntBusiLicenseNoExist(String busiLicenseNo, Integer custType) {
		int count = 0;
		if(busiLicenseNo==null){
			busiLicenseNo="";
		}
		if (custType == null) {
			count = entExtendInfoMapper.countEntLn(busiLicenseNo);
		} else {
			count = entExtendInfoMapper.countEntLnByType(busiLicenseNo,
					custType);
		}

		return count >=1 ? true : false;
	}
	
	/**
	 * 清除企业redis缓存
	 * @param entCustId
	 */
	public void cleanEntCache(String entCustId){
		commonCacheService.removeEntExtendInfoCache(entCustId);// 清除缓存中的扩展信息
		commonCacheService.removeEntStatusInfoCache(entCustId);// 清除缓存中的扩展信息
	}

	@Override
	public Integer getTransFailTimes(String entResNo) {
		return commonCacheService.getEntTradeFailTimesCache(entResNo);
	}

	@Override
	public Integer getPwdQuestionFailTimes(String entResNo) {
		return commonCacheService.getEntPwdQuestionFailTimesCache(entResNo);
	}

}
