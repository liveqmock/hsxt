/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.operator.service;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.PageUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.auth.AsRole;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.mapper.NetworkInfoMapper;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsMobileService;
import com.gy.hsxt.uc.consumer.mapper.HsCardMapper;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.mapper.GroupUserMapper;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.password.PasswordService;
import com.gy.hsxt.uc.password.bean.PasswordBean;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.api.IUCUserRoleService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 用户中心对接入提供操作员信息服务实现类
 * 
 * @Package: com.gy.hsxt.uc.operator.service
 * @ClassName: OperatorService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-20 下午2:14:57
 * @version V1.0
 */
@Service
public class OperatorService implements IUCAsOperatorService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.operator.service.IUCAsOperatorService";
	/**
	 * 操作员数据库操作接口
	 */
	@Autowired
	private OperatorMapper operatorMapper;

	@Autowired
	private HsCardMapper hsCardMapper;

	@Autowired
	CommonService commonService;

	@Autowired
	NetworkInfoMapper networkInfoMapper;
	
	@Autowired
	IUCUserRoleService userRoleService;
	/**
	 * 角色管理接口
	 */
	@Autowired
	private IUCAsRoleService roleService;
	/**
	 * 企业管理接口
	 */
	@Autowired
	private IUCAsEntService asEntService;

	@Autowired
	CommonCacheService commonCacheService;

	@Autowired
	PasswordService passwordService;
	@Autowired
	GroupUserMapper groupUserMapper;
	@Autowired
	private IUCUserInfoService userInfoService;
	@Autowired
	ITMOnDutyService dutyService;
	@Autowired
	UCAsMobileService asMobileService;

	/**
	 * 添加操作员信息
	 * 
	 * @param asOper
	 *            操作员对象
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 *             java.lang.String)
	 */
	@Transactional
	public String addOper(AsOperator asOper, String adminCustId)
			throws HsException {
		// 验证数据
		String operCustId = checkAddOper(asOper); // 返回客户号

		// 添加操作信息到数据库
		Operator oper = new Operator();
		oper.setOperCustId(operCustId);// 设置客户号
		oper.fillParams(asOper);
		// 解密aes
		String saltValue = CSPRNG.generateRandom(SysConfig.getCsprLen());
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setVersion("3");
		passwordBean.setSaltValue(saltValue);
		passwordBean.setPwd(asOper.getLoginPwd());
		passwordBean.setRandomToken(asOper.getRandomToken());
		String pwd = passwordService.getDesByAesPwd(passwordBean);
		oper.setPwdLogin(pwd);
		oper.setPwdLoginSaltValue(saltValue);
		oper.setCreatedby(adminCustId);
		oper.setUpdatedby(adminCustId);
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(oper.getEntCustId());
		oper.setEnterpriseResourceType(entExtendInfo.getCustType().toString());
		operatorMapper.insertSelective(oper);
		NetworkInfo record = new NetworkInfo();
		record.setPerCustId(operCustId);
		record.setCreatedby(operCustId);
		record.setNickname(oper.getOperName());
		networkInfoMapper.insertSelective(record);
		// 添加至缓存
		updateOperatorCache(oper);
		try {						
			// 通知搜索引擎
//			SearchUserInfo user = composeSearchUserInfo(oper);
			commonService.solrAddOper(oper);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "regEnt", "通知搜索引擎失败：", e);
		}

		return oper.getOperCustId();
	}

	private SearchUserInfo composeSearchUserInfo(Operator oper){
		if(oper==null ){
			SystemLog.warn(MOUDLENAME, "composeSearchUserInfo", "param is null");
			return null;
		}
		String entCustId=oper.getEntCustId();
		EntExtendInfo extendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		if (extendInfo==null||extendInfo.getCustType() == null) {
			throw new HsException(
					ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					entCustId+"客户类型(custType)为空");
		}
		SearchUserInfo user = new SearchUserInfo();
		
		user.setUsername(oper.getOperNo());
		user.setCustId(oper.getOperCustId());
		user.setEntResNo(oper.getEntResNo());
		user.setEntCustId(entCustId);
		user.setParentEntResNo(extendInfo.getSuperEntResNo());
		user.setUserType(extendInfo.getCustType());		
		user.setOperEmail(oper.getEmail());
		user.setOperName(oper.getOperName());
		user.setOperPhone(oper.getPhone());
		user.setOperDuty(oper.getOperDuty());
		user.setIsLogin(1);
		user.setResNo(oper.getOperResNo());//绑定互生卡号
		
		// 获取操作员网络信息
		NetworkInfo net = commonCacheService.searchNetworkInfo(oper.getOperCustId());
		if(net != null){
			user.setAge(net.getAge());
			user.setArea(net.getArea());
			user.setCity(net.getCityNo());
			user.setHeadImage(net.getHeadShot());
			user.setHobby(net.getHobby());
			user.setMobile(net.getMobile());
			user.setName(net.getName());
			user.setNickName(net.getNickname());
			user.setProvince(net.getProvinceNo());
			if(net.getSex() != null){
				user.setSex(String.valueOf(net.getSex()));
			}
			user.setSign(net.getIndividualSign());
		}
		userInfoService.add(user);
		return user;
	}
	/**
	 * 更新操作员缓存
	 * 
	 * @param operator
	 */
	private void updateOperatorCache(Operator operator) {
		// 设置操作员信息缓存
		commonCacheService.addOperCache(operator.getOperCustId(), operator);
		// 设置操作员ID缓存
		commonCacheService.addOperCustIdCache(operator.getEntResNo(),
				operator.getOperNo(), operator.getOperCustId());

	}

	private String checkAddOper(AsOperator asOper) throws HsException {
		// 验证基础参数是否为空
		String entResNo = asOper.getEntResNo();
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entResNo isBlank");
		}
		String userName = asOper.getUserName();
		if (isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"userName isBlank");
		}
		if (isBlank(asOper.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"loginPwd isBlank");
		}
		// 验证企业是否存在
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "]");
		}
		asOper.setEntCustId(entCustId);

		String oldOperCustId = null; // 正在使用的客户号
		// 验证是否存在重复用户名
		try {
			oldOperCustId = commonCacheService.findOperCustId(entResNo,
					userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(oldOperCustId)) {
			throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(), userName
					+ ErrorCodeEnum.USER_EXIST.getDesc() + ",OperCustId="
					+ oldOperCustId);
		}
		// 操作号不存在,检查客户号是否重复
		return commonService.checkAndGetOperCustId(entResNo, userName);
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
	@Override
	public AsOperator searchOperByCustId(String operCustId) throws HsException {
		// 验证数据
		if (isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operCustId" + ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		Operator oper = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(oper, operCustId);
		AsOperator asOper = oper.buildAsOperator();
		// 要不要查询权限
		return asOper;
	}

	/**
	 * 根据客户号查询操作员
	 * 
	 * @param operCustIds
	 *            多个客户号使用逗号分隔
	 * @return
	 * @throws HsException
	 */
	public List<AsOperator> searchOpersByCustIds(String roleId, String name,
			String phone, String operCustIds) throws HsException {
		if (isBlank(operCustIds)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operCustIds" + ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		Map<String, Object> map = new HashMap<>();
		if (roleId != null && roleId.equals("")) {
			roleId = null;
		}
		map.put("roleId", roleId);
		map.put("name", name);
		map.put("phone", phone);
		map.put("ids", operCustIds.split(","));

		List<Operator> operList = operatorMapper.selectByIds(map);
		List<AsOperator> asOperList = new ArrayList<AsOperator>();
		for (Operator oper : operList) {
			AsOperator ao = oper.buildAsOperator();
			List<AsRole> roleList = roleService.listRoleByCustId(null,null, oper.getOperCustId());
			ao.setRoleList(roleList);
			asOperList.add(ao);
		}
		return asOperList;
	}

	/**
	 * 获取企业管理员第二个密码信息
	 * 
	 * @param operCustId
	 * @return
	 */
	public AsOperator searchOperSecondPwd(String operCustId) {
		HashMap<String, Object> oper = operatorMapper
				.selectOperatorSecondPwd(operCustId);
		if (oper == null) {
			return null;
		}
		Operator op = new Operator();
		op.setOperCustId(operCustId);
		op.setPwdLogin((String) oper.get("PWD_LOGIN"));
		op.setPwdLoginSaltValue((String) oper.get("PWD_LOGIN_SALT_VALUE"));
		op.setPwdLoginVer((String) oper.get("PWD_LOGIN_VER"));
		return op.buildAsOperator();
	}

	/**
	 * 查询操作员信息 通过用户名
	 * 
	 * @param entResNo
	 * @param userName
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#searchOperByUserName(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AsOperator searchOperByUserName(String entResNo, String userName)
			throws HsException {
		if (isBlank(entResNo) || isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		String operCustId = findOperCustId(entResNo, userName);
		CustIdGenerator.isOperExist(operCustId, entResNo, userName);
		Operator oper = null;
		if (!StringUtils.isBlank(operCustId)) {
			oper = commonCacheService.searchOperByCustId(operCustId);
		}
		CustIdGenerator.isOperExist(oper, operCustId);
		return oper.buildAsOperator();
	}

	/**
	 * 查询企业隶属操作员
	 * 
	 * @param entCustId
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#listOperByEntCustId(java.lang.String)
	 */
	@Override
	// 验证数据
	public List<AsOperator> listOperByEntCustId(String entCustId)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 查询企业操作员
		List<Operator> operList = operatorMapper.listOperByEntCustId(entCustId);
		List<AsOperator> asOperList = new ArrayList<AsOperator>();
		for (Operator oper : operList) {
			asOperList.add(oper.buildAsOperator());
		}
		return asOperList;
	}

	/**
	 * 修改操作员
	 * 
	 * @param oper
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#updateOper(com.gy.hsxt.uc.as.bean.operator.AsOperator,
	 *      java.lang.String)
	 */
	@Transactional
	public void updateOper(AsOperator asOper, String adminCustId)
			throws HsException {
		// 验证数据
		if (isBlank(adminCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"adminCustId为空");
		}
		if (isBlank(asOper) || isBlank(asOper.getOperCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operCustId为空");
		}
		// 修改操作员信息
		Operator oper = new Operator();
		oper.fillParams(asOper);
		if (StringUtils.isNotBlank(asOper.getLoginPwd())) {
			// 生成登录密码
//			String salt = CSPRNG.generateRandom(8);
			String salt=DateUtil.getCurrentDateNoSign();// yyyymmdd
			PasswordBean passwordBean = new PasswordBean();
			passwordBean.setEnt(true);
			passwordBean.setVersion("3");
			passwordBean.setSaltValue(salt);
			passwordBean.setPwd(asOper.getLoginPwd());
			passwordBean.setRandomToken(asOper.getRandomToken());
			String pwd = passwordService.getDesByAesPwd(passwordBean);
			oper.setPwdLogin(pwd);
			oper.setPwdLoginSaltValue(salt);
			oper.setPwdLoginVer("3");
			SystemLog.debug(MOUDLENAME, "updateOper",asOper.getOperCustId()+"操作员被重置密码["+pwd+"]salt["+salt+"]");
		}
		oper.setOperCustId(asOper.getOperCustId());
		oper.setUpdateDate(getNowTimestamp());
		oper.setUpdatedby(adminCustId);
		this.updateOper(oper);
		
		if (StringUtils.isNotBlank(asOper.getLoginPwd())) {
			//清除登陆密码连续错误次数
		}
		// operatorMapper.updateByPrimaryKeySelective(oper);
		//
		// // 清除缓存
		// this.cleanOperCache(oper.getOperCustId(), oper.getEntResNo(),
		// oper.getOperNo());

	}

	/**
	 * 更新操作员信息
	 * 
	 * @param oper
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#updateOper(com.gy.hsxt.uc.as.bean.operator.AsOperator,
	 *      java.lang.String)
	 */
	@Transactional
	public void updateOper(Operator oper) throws HsException {
		// 检查操作号修改是否合法
		commonCacheService.updateOper(oper);
		try {
			Operator newOperator = commonCacheService.searchOperByCustId(oper
					.getOperCustId());
			// 通知搜索引擎
//			composeSearchUserInfo(newOperator);
			commonService.solrUpdateOper(newOperator);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateOper", "通知搜索引擎失败：", e);
		}
		// 通知TM
		try {
			// 如果操作员名称不为空，就更新TM
			if (StringUtils.isNotBlank(oper.getOperName())) {
				dutyService.synOperatorName(oper.getOperCustId(),
						oper.getOperName());
			}
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateOper", "通知TM失败：", e);
		}
	}

	/**
	 * 删除操作员
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#deleteOper(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional
	public void deleteOper(String operCustId, String adminCustId)
			throws HsException {
		// 验证数据
		if (isBlank(operCustId) || isBlank(adminCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		commonCacheService.deleteOper(operCustId, adminCustId);
		// 删除 用户组-组员关系
		// groupUserMapper.deleteGroupUser(null, operCustId);
		groupUserMapper.removeUserGroup(operCustId);
		// 删除操作员角色信息
		roleService.revokeRole(operCustId, null, adminCustId);

		try {
			// System.out.println("通知搜索引擎删除索引");
			userInfoService.delete(operCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "deleteOper", "通知搜索引擎失败：", e);
		}
		
		// 通知TM删除操作员
		try{
			dutyService.removeOperator(operCustId);
		}
		catch(Exception e){
			SystemLog.error(MOUDLENAME, "deleteOper", "通知TM失败：", e);
		}
		//删除操作员角色时需要同步搜索引擎
		try {
			userRoleService.deleteByCustId(operCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "deleteOper", "调用搜索引擎失败：", e);
		}
		
	}
	
	
	/**
	 * 删除企业所有操作员，企业销户时使用
	 * @param entCustId
	 * @param operCustId
	 */
	@Transactional
	public void deleteOpersByEntCustId(String entCustId,String operCustId){
		// 查询企业操作员
		List<Operator> operList = operatorMapper.listOperByEntCustId(entCustId);
		if(operList==null || operList.isEmpty()){
			return;
		}
		for(Operator oper:operList){
			deleteOper(oper.getOperCustId(),operCustId);
		}
	}

	/**
	 * 管理员给操作员绑定互生卡
	 * 
	 * @param operCustId
	 *            待绑定操作员客户号
	 * @param operResNo
	 *            待绑定操作员互生号
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 */
	@Transactional
	public void bindCard(String operCustId, String perResNo, String adminCustId)
			throws HsException {
		// 验证数据
		if (isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operCustId为空");
		}
		if (isBlank(adminCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"adminCustId为空");
		}
		if (isBlank(perResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"perResNo为空");
		}
		// 验证互生号是否存在
		checkHsCardExsit(perResNo);
		Operator oper=commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(oper, operCustId);
		String operNo=oper.getOperNo();
		String entResNo=oper.getEntResNo();
		// 验证互生号是否已经被绑定
		String chkOperCustId = commonCacheService.findOperCustId(perResNo);
		if (StringUtils.isBlank(chkOperCustId)) {// 互生卡未绑定
			bindHsCard(perResNo, operCustId, adminCustId);
			asMobileService.sendHsCardBindMsg(entResNo, operNo, perResNo,adminCustId);
			return;
		}
		Operator op = commonCacheService.searchOperByCustId(chkOperCustId);
		CustIdGenerator.isOperExist(op, chkOperCustId);
		if (op.getIsBindResNo() != 0) {// 互生卡状态处于绑定或者绑定中状态
			if (op.getIsBindResNo() == 1) { // 互生卡已被绑定
				throw new HsException(ErrorCodeEnum.RES_NO_IS_BIND.getValue(),
						perResNo + "该互生号已被绑定");
			} else if (!chkOperCustId.equals(operCustId)) {// 互生卡处于绑定中状态（已绑定至其他操作员客户号）
				throw new HsException(ErrorCodeEnum.RES_NO_IS_BIND.getValue(),
						perResNo + "该互生号已被使用");
			}
		} else {// 互生卡未绑定
			bindHsCard(perResNo, operCustId, adminCustId);
			
			asMobileService.sendHsCardBindMsg(entResNo, operNo, perResNo,adminCustId);
		}

	}

	/**
	 * 操作员绑定互生卡
	 * 
	 * @param perResNo
	 * @param operCustId
	 * @param adminCustId
	 */
	private void bindHsCard(String perResNo, String operCustId,
			String adminCustId) {
		// 绑定互生卡
		Operator oper = new Operator();
		oper.setOperCustId(operCustId);
		oper.setIsBindResNo(-1);// 设置绑定状态为绑定中
		oper.setOperResNo(perResNo);
		oper.setUpdateDate(getNowTimestamp());
		oper.setUpdatedby(adminCustId);
		try {
			operatorMapper.updateByPrimaryKeySelective(oper);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "bindHsCard", "操作员绑定互生卡异常：\r\n", e);
		}
		commonCacheService.removeOperCache(oper.getOperCustId());// 更新缓存
		commonCacheService.removeOperCustIdCache(perResNo); //删除操作员互生号-客户号缓存
		
	}

	/**
	 * 验证互生卡是否已失效
	 * 
	 * @param perResNo
	 */
	private void checkHsCardExsit(String perResNo) throws HsException {
		String perCustId = commonCacheService.findCustIdByResNo(perResNo);
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.RES_NO_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.RES_NO_IS_NOT_FOUND.getDesc() + ",resNo["
							+ perResNo + "]");
		}
	}

	/**
	 * 确认互生卡绑定
	 * 
	 * @param perResNo
	 *            待绑定消费者互生号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#confirmBindCard(java.lang.String,
	 *      com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum)
	 */
	@Transactional
	public void confirmBindCard(String perResNo) throws HsException {
		// 验证数据
		if (isBlank(perResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"perResNo为空");
		}
		//删除操作员互生号-客户号缓存,清除旧缓存影响，修复绑定不成功bug
		commonCacheService.removeOperCustIdCache(perResNo); 
		String operCustId = commonCacheService.findOperCustId(perResNo);
		CustIdGenerator.isOperExist(operCustId, perResNo);
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		//1：绑定  , 0：未绑定,  -1绑定中
		int bindStatus= operator.getIsBindResNo();
		if(0 == bindStatus){
			String msg="操作员未绑定互生号,resNo["
					+ perResNo + "]"+ ",entResNo["
					+ operator.getEntResNo() + "]"+ ",operNo["
					+ operator.getOperNo() + "]";
			SystemLog.warn(MOUDLENAME, "confirmBindCard", msg);
			throw new HsException(ErrorCodeEnum.RES_NO_IS_NOT_BINDING.getValue(),"该互生号未绑定操作员"
					); 
		}else if(1==bindStatus){
			String msg="操作员已经绑定互生号,resNo["
					+ perResNo + "]"+ ",entResNo["
					+ operator.getEntResNo() + "]"+ ",operNo["
					+ operator.getOperNo() + "]";
			SystemLog.info(MOUDLENAME, "confirmBindCard", msg);
			throw new HsException(ErrorCodeEnum.RES_NO_IS_NOT_BINDING.getValue(),"该互生号已经绑定操作员"
					); 
		}
		Operator oper = new Operator();
		oper.setOperCustId(operCustId);
		// 设置互生卡绑定状态为已绑定
		oper.setIsBindResNo(1); // 1：绑定 0：未绑定,-1绑定中
		// oper.setOperResNo(operResNo);
		oper.setApplyBindDate(getNowTimestamp());
		oper.setUpdateDate(getNowTimestamp());
		oper.setUpdatedby(operCustId);
		
//		operatorMapper.updateByPrimaryKeySelective(oper);
//		commonCacheService.removeOperCache(oper.getOperCustId());// 更新缓存
		updateOper(oper);
	}

	/**
	 * 操作员解绑互生卡绑定
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#unBindCard(java.lang.String)
	 */
	@Transactional
	public void unBindCard(String operCustId) throws HsException {
		// 验证数据
		if (isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operCustId为空");
		}

		Operator oper = new Operator();
		oper.setOperCustId(operCustId);
		// 设置互生卡绑定状态为未绑定 ,
		oper.setIsBindResNo(0);// 1：绑定    0：未绑定,  -1绑定中
		oper.setOperResNo("");
		// oper.setApplyBindDate(getNowTimestamp());
		oper.setUpdateDate(getNowTimestamp());
		// oper.setUpdatedby(operator);
		
//		operatorMapper.updateByPrimaryKeySelective(oper);
//		// 更新缓存
//		commonCacheService.removeOperCache(oper.getOperCustId());
//		commonCacheService.removeOperCustIdCache(perResNo); //删除操作员互生号-客户号缓存
		updateOper(oper);
	}

	@Override
	public String findOperCustId(String entResNo, String userName)
			throws HsException {
		if (isBlank(entResNo) || isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entResNo或userName为空");
		}
		String operCustId = commonCacheService.findOperCustId(entResNo,
				userName);
		CustIdGenerator.isOperExist(operCustId, entResNo, userName);
		return operCustId;
	}

	/**
	 * 查询操作员客户号通过操作员资源号
	 * 
	 * @param operResNo
	 *            操作员互生号
	 * @return
	 * @throws HsException
	 */
	@Override
	public String findOperCustId(String operResNo) throws HsException {
		if (isBlank(operResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		String operCustId = commonCacheService.findOperCustId(operResNo);
		CustIdGenerator.isOperExist(operCustId, operResNo);
		return operCustId;
	}

	/**
	 * 设置登陆密码
	 * 
	 * @param operResNo
	 *            操作者客户号
	 * @throws HsException
	 */
	@Transactional
	public void setLoginPwd(String custId, String newPwd) throws HsException {
		Operator record = new Operator();
		record.setPwdLogin(newPwd);
		record.setOperCustId(custId);
		operatorMapper.updateByPrimaryKeySelective(record);
		// 更新缓存
		commonCacheService.removeOperCache(custId);
		
	}

	/**
	 * 校验登陆密码 是否正确
	 * 
	 * @param entResNo
	 *            企业互生号 必传
	 * @param username
	 *            操作员账号 (用户名)
	 * @param loginPwd
	 *            登陆密码 (AES加密后的登录密码)
	 * @param secretKey
	 *            AES秘钥（随机登录token）
	 */
	@Override
	public void validLoginPwd(String entResNo, String username,
			String loginPwd, String secretKey) {
		// 验证数据
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业资源号为空");
		}
		if (isBlank(loginPwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码为空");
		}
		if (isBlank(secretKey)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"秘钥为空");
		}
		if (isBlank(username)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名为空");
		}

		// 还原为密码为 md5(密码)
		loginPwd = StringEncrypt.decrypt(loginPwd, secretKey);

		// 验证登陆密码
		AsOperator oper = searchOperByUserName(entResNo, username);
		loginPwd = StringEncrypt.sha256(loginPwd + oper.getLoginPWdSaltValue());
		if (!loginPwd.equals(oper.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.USER_PWD_IS_WRONG.getValue(),
					ErrorCodeEnum.USER_PWD_IS_WRONG.getDesc());
		}
	}

	/**
	 * 查询企业用户组的组员（操作员）列表
	 * 
	 * @param entGroupId
	 *            企业用户组编号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#listOperByGroupId(java.lang.Long)
	 */
	@Override
	public List<AsOperator> listOperByGroupId(Long entGroupId)
			throws HsException {
		// 验证数据
		if (isBlank(entGroupId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业用户组编号为空");
		}

		// 查询企业用户组的组员（操作员）列表
		List<Operator> operList = operatorMapper
				.listOperByEntGroupId(entGroupId);
		List<AsOperator> asOperList = new ArrayList<AsOperator>();
		for (Operator oper : operList) {
			asOperList.add(oper.buildAsOperator());
		}
		return asOperList;
	}

	/**
	 * 查询操作员信息和角色信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsOperator searchOperAndRoleInfoByCustId(String operCustId,
			String platformCode, String subSystemCode) throws HsException {
		if (isBlank(platformCode)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数台代码为空");
		}
		if (isBlank(subSystemCode)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数子系统代码为空");
		}
		String custId = operCustId.trim();
		AsOperator operator = searchOperByCustId(custId);

		List<AsRole> list = roleService.listRoleByCustId(platformCode,
				subSystemCode, custId);
		if (null != list) {
			operator.setRoleList(list);
		}
		return operator;
	}

	@Override
	public List<AsOperator> listOperAndRoleByEntCustId(String entCustId)
			throws HsException {
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		AsQueryOperatorCondition condition = new AsQueryOperatorCondition();
		condition.setEntCustId(entCustId);
		List<AsOperator> list = operatorMapper.pageOperators(condition, null);
		return list;
	}

	/**
	 * 分页查询企业操作员信息
	 * 
	 * @param condition
	 *            分页查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#listOperators(com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	public PageData<AsOperator> listOperators(
			AsQueryOperatorCondition condition, Page page) throws HsException {
		// 数据验证
		if (isBlank(condition)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[condition]为null");
		}
		if (isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[page]为null");
		}

		int count = operatorMapper.countOperator(condition);

		if (count == 0) {
			return null;
		}

		List<AsOperator> result = operatorMapper.pageOperators(condition, page);

		if (result != null && !result.isEmpty()) {
			return PageUtil.subPage(page, result);
		}

		return new PageData<>(count, result);

	}

	/**
	 * 查询企业客户号通过操作员互生号
	 * 
	 * @param operResNo
	 *            操作员互生号
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByOperResNo(String operResNo) throws HsException {
		String operCustId = findOperCustId(operResNo);
		CustIdGenerator.isOperExist(operCustId, operResNo);
		Operator operator = commonCacheService.searchOperByCustId(operCustId);
		CustIdGenerator.isOperExist(operator, operCustId);
		String entCustId = "";
		if (null != operator) {
			entCustId = operator.getEntCustId();
		}
		return entCustId;
	}

	@Override
	public Integer getLoginFailTimes(String entResNo, String operNo) {
		return commonCacheService.getOperLoginFailTimesCache(entResNo, operNo);
	}
	
	
}
