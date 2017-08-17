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

package com.gy.hsxt.uc.permission.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.PageUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.auth.AsRole;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.permission.bean.Permission;
import com.gy.hsxt.uc.permission.bean.Role;
import com.gy.hsxt.uc.permission.mapper.PermissionMapper;
import com.gy.hsxt.uc.permission.mapper.RoleMapper;
import com.gy.hsxt.uc.search.api.IUCUserRoleService;
import com.gy.hsxt.uc.search.bean.SearchUserRole;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 权限模块-角色管理
 * 
 * @Package: com.gy.hsxt.uc.role.service
 * @ClassName: RoleService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-13 上午11:55:05
 * @version V1.0
 */
@Service
public class RoleService implements IUCAsRoleService {
	String moudleName = "RoleService";
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	CommonCacheService commonCacheService;
	@Autowired(required=false)
	IUCUserRoleService userRoleService;

	String[] ignoreProperties = { "roleType", "createDate", "updateDate" }; // 类型不匹配，不能自动复制的属性名称

	/**
	 * 增加角色,插入一条角色记录
	 * 
	 * @param vo
	 *            角色信息,非空字段入库
	 * @param operator
	 * @throws HsException
	 *             errCode 160400到16449
	 */
	public String addRole(AsRole vo, String operator) throws HsException {
		
		// 检查角色名称是否已经存在
		this.checkRoleNameExits(vo, false);
		String roleId = vo.getRoleId();
		// 检查角色是否已存在
		if(isRoleIdExits(roleId)){
			roleId=(null);//生成新的随机id
		}
		
		
		Role role = new Role();

		try { // 组装待入库信息
			BeanUtils.copyProperties(vo, role, ignoreProperties);
			String roletype=vo.getRoleType();
			if(!StringUtils.isBlank(roletype)){
				role.setRoleType(Short.parseShort(roletype));
			}
			role.setCreatedby(operator);
			if (StringUtils.isBlank(roleId)) { // 根据当前毫秒数生成15位随机码
				//主键id
				String id = CustIdGenerator.genSeqId("");
				roleId = id;
				role.setRoleId(roleId);
			}
			int count = roleMapper.insertSelective(role);
			
			
			return role.getRoleId();
		} catch (Exception e) {
			SystemLog.error("RoleService", "addRole", JSON.toJSONString(vo)+vo.getRoleName().length(), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"操作失败，请检查传入参数是否异常，" + vo.getRoleId() + "是否已经存在."+e.getMessage());
		}

	}
	
	private void addSearch(String custId){
		try {
			HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件

			conditionMap.put("custId", custId);
			List<Role> selectResultList = roleMapper
					.selectByCustId(conditionMap);
			if(selectResultList!=null&&!selectResultList.isEmpty()){
				List<SearchUserRole> roles = new ArrayList<SearchUserRole>(2);
				SearchUserRole sRole;
				for(Role r:selectResultList){
					sRole= new SearchUserRole();
					sRole.setCustId(custId);
					sRole.setRoleId(r.getRoleId());
					sRole.setRoleName(r.getRoleName());
					sRole.setRoleDesc(r.getRoleDesc());
					sRole.setId(custId+r.getRoleId());
					if(r.getRoleType()!=null){
						sRole.setRoleType(r.getRoleType().toString());
					}
				roles.add(sRole);
				}
				userRoleService.add(roles);
			}
		} catch (Exception e) {
			SystemLog.error("RoleService", "addSearch", "操作员角色变更，通知搜索引擎失败："+e.getMessage(), e);
		}
		
	}

	/**
	 * 检查角色名称 是否存在
	 * 
	 * @param vo
	 * @param isUpdate
	 *            true更新检查，false新增检查
	 */
	private void checkRoleNameExits(AsRole vo, boolean isUpdate) throws HsException {
		String roleId = vo.getRoleId();
		Role role = new Role();
		String roleName  = vo.getRoleName();
			role.setRoleName(roleName);
	
		String entCustId=vo.getEntCustId();
		if (!StringUtils.isBlank(entCustId)) {
			role.setEntCustId(entCustId);
		}
		role.setSubSystemCode(vo.getSubSystemCode());
		role.setPlatformCode(vo.getPlatformCode());
		role.setIsactive("Y");
		List<Role> list = roleMapper.selectBySelective(role);

		if (list != null && !list.isEmpty()) {
			role = list.get(0);
			int count = list.size();
			if (isUpdate && count == 1 && role.getRoleId().equals(roleId)) {
				return;// 自己更新自己，不改名字,允许已存在一条同名数据
			} else {
				String msg = count + "条记录被发现， vo.id=" + vo.getRoleId()
						+ "vo.name=" + vo.getRoleName() + "vo.sys="
						+ vo.getSubSystemCode()+"EntCustId="+vo.getEntCustId();
				SystemLog.info(moudleName, "checkRoleNameExits", msg);
			}

			String msg = role.getRoleName() + " 角色名称已经存在,id="
					+ role.getRoleId()+ "SubSystemCode="
							+ role.getSubSystemCode()+"EntCustId="+role.getEntCustId();
			SystemLog.info(moudleName, "checkRoleNameExits", msg);
			throw new HsException(ErrorCodeEnum.ROLE_EXIST.getValue(), msg);
		}
		
		//企业私有角色不能跟公共角色重名
		if (!StringUtils.isBlank(entCustId)) {
			role = new Role();
			role.setSubSystemCode(vo.getSubSystemCode());
			role.setPlatformCode(vo.getPlatformCode());
			role.setIsactive("Y");
			role.setRoleName(roleName);
			role.setRoleType((short)1);
			list = roleMapper.selectBySelective(role);
			if (list != null && !list.isEmpty()) {
				role = list.get(0);
				if (isUpdate && list.size() == 1 && role.getRoleId().equals(roleId)) {
					return;// 自己更新自己，不改名字,允许已存在一条同名数据
				}
				String msg ="["+ role.getRoleName() + "]全局角色已经存在,id=["
						+ role.getRoleId()+ "],SubSystemCode=["
								+ role.getSubSystemCode()+"],EntCustId="+role.getEntCustId();
				SystemLog.info(moudleName, "checkRoleNameExits", msg);
				throw new HsException(ErrorCodeEnum.ROLE_EXIST.getValue(), msg);
			}
		}
	}

	private boolean isRoleIdExits(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			return false;
		}
		Role role = new Role();
		role.setRoleId(roleId);

		List<Role> list = roleMapper.selectBySelective(role);

		if (list != null && !list.isEmpty()) {
			role = list.get(0);

			String msg = role.getRoleName() + " 角色ID已经存在,id="
					+ role.getRoleId();
			SystemLog.info(moudleName, "isRoleIdExits", msg);
			return true;
		}
		return false;
	}

	/**
	 * 删除角色 修改记录状态为N
	 * 
	 * @param roleId
	 * @param operator
	 * @throws HsException
	 */
	public void deleteRole(String roleId, String operator) throws HsException {
		//检查角色是否正在被使用
		isRoleUsing(roleId);
		// 组装数据库操作条件
		Role role = new Role();
		role.setRoleId((roleId));
		role.setIsactive("N");
		role.setUpdatedby(operator);
		// role.setUpdateDate(new Date());
		try {
			int count = roleMapper.updateByPrimaryKeySelective(role);
		} catch (Exception e) {
			SystemLog.error("RoleService", "deleteRole",
					JSON.toJSONString(roleId), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"deleteRole fail!");
		}
	}

	boolean isRoleUsing(String roleId) {
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("roleId", roleId);
		List<Permission> permList = permissionMapper
				.selectByRoleId(conditionMap);
		if (permList != null && !permList.isEmpty()) {
			String permName=permList.get(0).getPermName();
			SystemLog
					.warn("RoleService", "isRoleUsing", "role is used by PermName:" + permName);
			throw new HsException(ErrorCodeEnum.ROLE_USE_BY_PERM.getValue(), roleId
					+  ErrorCodeEnum.ROLE_USE_BY_PERM.getDesc()+permName);
//			return true;
		}

		List<Map<String, String>> roleUsers = roleMapper
				.selectRoleUsers(roleId);
		if (roleUsers != null && !roleUsers.isEmpty()) {
			SystemLog.warn("RoleService", "isRoleUsing", "role is used by users" + roleUsers.get(0));
			throw new HsException(ErrorCodeEnum.ROLE_USE_BY_USER.getValue(), roleId
					+ ErrorCodeEnum.ROLE_USE_BY_USER.getDesc()+roleUsers.get(0));
//			return true;
		}
		return false;
	}

	List<Map<String, String>> selectRoleUsers(String roleId) {
		return roleMapper.selectRoleUsers(roleId);
	}

	/**
	 * 修改角色信息,只修改非null字段
	 * 
	 * @param vo
	 *            角色信息
	 * @param operator
	 * @throws HsException
	 */
	public void updateRole(AsRole vo, String operator) throws HsException {
		// 检查角色名称是否已经存在
		this.checkRoleNameExits(vo, true);
		try { // 组装数据库操作条件
			Role role = new Role();
			BeanUtils.copyProperties(vo, role, ignoreProperties);
			role.setRoleId((vo.getRoleId()));
			String roletype=vo.getRoleType();
			if(!StringUtils.isBlank(roletype)){
				role.setRoleType(Short.parseShort(roletype));
			}
			role.setUpdatedby(operator);
			// role.setUpdateDate(new Date());
			int count = roleMapper.updateByPrimaryKeySelective(role);
		} catch (BeansException e) {
			SystemLog.error("RoleService", "updateRole", JSON.toJSONString(vo),
					e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"updateRole fail!");
		}
	}


	public List<AsRole> listRole(String platformCode, String subSystemCode,
			Short roleType, String entCustId) throws HsException {
		return this.listRole(platformCode, subSystemCode, roleType, entCustId,
				null,null);
	}
	public List<AsRole> listRole(String platformCode, String subSystemCode,
			Short roleType, String entCustId, String entCustType)
			throws HsException {
		return this.listRole(platformCode, subSystemCode, roleType, entCustId,
				entCustType,null);
	
	}
			
	/**
	 * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id,企业类型
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @param custType 企业类型， null则忽略该条件
	 * @param roleName 角色名称， null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsRole> listRole(String platformCode, String subSystemCode,
			Short roleType, String entCustId, String entCustType,String roleName)
			throws HsException {
		// 组装数据库操作条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("platformCode", platformCode);
		conditionMap.put("subSystemCode", subSystemCode);
		conditionMap.put("roleType", roleType);
		conditionMap.put("entCustId", entCustId);
		conditionMap.put("entCustType", entCustType);
		conditionMap.put("roleName", roleName);
		

		try {
			List<Role> selectResultList = roleMapper
					.selectByCustType(conditionMap);
			// 把数据库查询结果封装成接口VO并返回给接口调用方
			return beanList2voList(selectResultList);
		} catch (Exception e) {
			SystemLog.error("RoleService", "listRole",
					JSON.toJSONString(conditionMap), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"listRoleByType fail!");
		}
	}

	/**
	 * 分页查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param roleType
	 *            null则忽略该条件
	 * @param entCustId
	 *            null则忽略该条件
	 * @return 根据page分页后单页数据
	 * @throws HsException
	 */
	public PageData<AsRole> listRoleByPage(String platformCode,
			String subSystemCode, Short roleType, String entCustId, Page page)
			throws HsException {
		List<AsRole> dataList = this.listRole(platformCode, subSystemCode,
				roleType, entCustId, null,null);
		return (PageData<AsRole>) PageUtil.subPage(page, dataList);
	}

	public PageData<AsRole> listRoleByPage(String platformCode,
			String subSystemCode, Short roleType, String entCustId,
			String custType, Page page) throws HsException {
		List<AsRole> dataList = this.listRole(platformCode, subSystemCode,
				roleType, entCustId, custType,null);
		return (PageData<AsRole>) PageUtil.subPage(page, dataList);
	}
	/**
	 * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id,企业类型
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @param custType 企业类型， null则忽略该条件
	 * @param roleName 角色名称， null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public PageData<AsRole> listRoleByPage(String platformCode,
			String subSystemCode, Short roleType, String entCustId,
			String custType,String roleName, Page page) throws HsException {
		List<AsRole> dataList = this.listRole(platformCode, subSystemCode,
				roleType, entCustId, custType,roleName);
		return (PageData<AsRole>) PageUtil.subPage(page, dataList);
	}

	/**
	 * 根据角色类型查询角色列表，可附加过滤条件：平台，子系统
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param roleType
	 *            null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	@Deprecated
	public List<AsRole> listRoleByType(String platformCode,
			String subSystemCode, Short roleType) throws HsException {
		return listRole(platformCode, subSystemCode, roleType, null);
	}

	/**
	 * 查询用户拥有的角色 ,排除角色记录状态=N的记录，可附加过滤条件：平台，子系统
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public List<AsRole> listRoleByCustId(String platformCode,
			String subSystemCode, String custId) throws HsException {
		// 组装数据库操作条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("platformCode", platformCode);
		conditionMap.put("subSystemCode", subSystemCode);
		conditionMap.put("custId", custId);
		try {
			List<Role> selectResultList = roleMapper
					.selectByCustId(conditionMap);
			// 把数据库查询结果封装成接口VO并返回给接口调用方
			return beanList2voList(selectResultList);
		} catch (Exception e) {
			SystemLog.error("RoleService", "listRoleByCustId",
					JSON.toJSONString(conditionMap), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"listRoleByCustId fail!");
		}
	}

	/**
	 * 查询企业可分配的角色 ,排除角色记录状态=N的记录，可附加过滤条件：平台，子系统 返回全局角色，平台角色，及企业私有角色
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param entCustId
	 *            企业客户号
	 * @return 返回全局角色，平台角色，及企业私有角色
	 * @throws HsException
	 */
	public List<AsRole> selectByEntCustId(String platformCode,
			String subSystemCode, String entCustId) throws HsException {
		// 组装数据库操作条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("platformCode", platformCode);
		conditionMap.put("subSystemCode", subSystemCode);
		conditionMap.put("entCustId", entCustId);
		try {
			List<Role> selectResultList = roleMapper
					.selectByEntCustId(conditionMap);
			// 把数据库查询结果封装成接口VO并返回给接口调用方
			return beanList2voList(selectResultList);
		} catch (Exception e) {
			SystemLog.error("RoleService", "selectByEntCustId",
					JSON.toJSONString(conditionMap), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"listRoleByCustId fail!");
		}
	}

	/**
	 * 获取用户角色授权树
	 * 
	 * @param platformCode
	 *            平台
	 * @param subSystemCode
	 *            子系统
	 * @param entCustId
	 *            企业客户号
	 * @param userCustId
	 *            用户ID
	 * @return 用户角色授权树
	 */
	public List<Map<String, Object>> getRoleTree(String platformCode,
			String subSystemCode, String entCustId, String userCustId) {
		ArrayList<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		List<AsRole> allRole = this.selectByEntCustId(platformCode,
				subSystemCode, entCustId);
		if (allRole != null) {
			List<AsRole> userRole = this.listRoleByCustId(platformCode,
					subSystemCode, userCustId);
			// 父权限集
			HashMap<String, AsRole> pMap = list2map(allRole);
			// 角色已经拥有权限集
			HashMap<String, AsRole> myMap = list2map(userRole);
			HashMap<String, Object> m;
			// 遍历所有可授权权限，生成权限树数据
			HashSet<String> subSys = new HashSet<String>();
			String sysCode, id;
			for (AsRole obj : allRole) {
				m = new HashMap<String, Object>();
				id = obj.getRoleId();
				sysCode = obj.getSubSystemCode();
				if (sysCode == null || "".equals(sysCode)) {
					m.put("pId", 0); // 树 父节点id
				} else {
					subSys.add(sysCode);
					m.put("pId", sysCode);
				}

				m.put("id", id); // 树 本节点id
				m.put("name", obj.getRoleName());// 树 本节点名称
				// m.put("open", Boolean.TRUE);//树 是否展开
				if (myMap.containsKey(id)) {// 角色已经拥有该权限
					m.put("checked", Boolean.TRUE);// 树 是否选中
				}
				ret.add(m);
			}

			ArrayList<Map<String, Object>> sysTree = getSysNods(subSys);

			sysTree.addAll(ret);
			return sysTree;
		}

		return ret;

	}

	private ArrayList<Map<String, Object>> getSysNods(HashSet<String> subSys) {
		ArrayList<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		if (subSys == null)
			return ret;
		HashMap<String, Object> m;
		for (String sys : subSys) {
			m = new HashMap<String, Object>();
			m.put("id", sys); // 树 本节点id
			m.put("pId", 0); // 树 父节点id
			m.put("name", sys);// 树 本节点名称
			// m.put("chkDisabled", Boolean.TRUE);// 树 本节点名称
			m.put("nocheck", Boolean.TRUE);

			m.put("open", Boolean.TRUE);
			ret.add(m);
			// log.info(m);
		}
		return ret;
	}

	private HashMap<String, AsRole> list2map(List<AsRole> list) {
		HashMap<String, AsRole> ret = new HashMap<String, AsRole>(list.size());
		if (ret == null)
			return ret;
		for (AsRole obj : list) {
			ret.put(obj.getRoleId(), null);
		}
		return ret;
	}

	/**
	 * 把数据库bean封装成接口VO
	 * 
	 * @param selectResultList
	 * @return
	 */
	private List<AsRole> beanList2voList(List<Role> selectResultList) {
		// 把数据库查询结果封装成接口VO并返回给接口调用方
		ArrayList<AsRole> retList = new ArrayList<AsRole>();
		if (selectResultList != null) {
			for (Role bean : selectResultList) {
				retList.add(bean2vo(bean));
			}
		}
		return retList;
	}

	/**
	 * 把数据库bean封装成接口VO
	 * 
	 * @param bean
	 * @return
	 */
	private AsRole bean2vo(Role bean) {
		AsRole vo = new AsRole();
		BeanUtils.copyProperties(bean, vo, ignoreProperties);
		vo.setRoleId(String.valueOf(bean.getRoleId()));
		vo.setRoleType(String.valueOf(bean.getRoleType()));
		return vo;
	}

	/**
	 * 批量用户角色授权，跳过已有角色，插入未有角色。
	 * 
	 * @param custId
	 * @param list
	 *            需授权角色id列表
	 * @param operator
	 * @throws HsException
	 */
	public void grantRole(String custId, List<String> list, String operator)
			throws HsException {
		if (list == null || list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"list cannot be empty");
		}
		// 组装数据库操作条件
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custId", custId);
		map.put("operator", operator);
		map.put("idList", list);

		try {
			int count = roleMapper.insertCustRole(map);
			// 更新缓存
			resetCustRoleCache(custId);
			
			//通知搜索引擎
			addSearch(custId);
		} catch (Exception e) {
			SystemLog.error("RoleService", "grantRole", JSON.toJSONString(map),
					e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"grantRole fail!");
		}
	}

	/**
	 * 批量回收用户角色,删除用户授权记录
	 * 
	 * @param custId
	 * @param list
	 *            需删除角色id列表,null则删除全部
	 * @param operator
	 * @throws HsException
	 */
	public void revokeRole(String custId, List<String> list, String operator)
			throws HsException {
		if (list != null && list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"list cannot be empty." + list);
		}
		// 组装数据库操作条件
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custId", custId);
		map.put("operator", operator);
		map.put("idList", list);

		try {
			int count = roleMapper.deleteCustRole(map);
			// 更新缓存
			resetCustRoleCache(custId);
		} catch (Exception e) {
			SystemLog.error("RoleService", "revokeRole",
					JSON.toJSONString(map), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"revokeRole fail!");
		}
	}

	/**
	 * 批量授权用户角色,删除已有记录，插入新记录
	 * 
	 * @param custId
	 * @param list
	 *            需授权角色id列表
	 * @param operator
	 * @throws HsException
	 */
	public void resetRole(String custId, List<String> list, String operator)
			throws HsException {
		if (list != null && list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					"list cannot be empty." + list);
		}
		// 删除旧记录
		revokeRole(custId, null, operator);
		// 插入新记录
		if (list != null && !list.isEmpty()) {
			grantRole(custId, list, operator);
		}else{
			//通知搜索引擎
			addSearch(custId);
		}
		
		
		// 更新缓存 ,revokeRole,grantRole均已经刷新缓存。这里再刷新就重复刷新了。
//		resetCustRoleCache(custId);
	}

	/**
	 * 从数据库获取数据，重置指定用户角色缓存
	 * 
	 * @param custId
	 * @return Set<String roleId>
	 */
	public Set<String> resetCustRoleCache(String custId) {
		Set<String> custRoleIdSet = getRoleByCustId(custId);
		if (custRoleIdSet != null) {
			commonCacheService.resetCustRoleCache(custId, custRoleIdSet);
			// String key = CacheKeyGen.genCustRoleCacheKey(custId);
			// cacheUtil.getFixRedisUtil().redisTemplate.delete(key);
			// cacheUtil.getFixRedisUtil().redisTemplate.opsForSet().add(key,
			// custRoleIdSet.toArray());
		}
		return custRoleIdSet;
	}

	/**
	 * 判断用户是否拥有对应url权限
	 * 
	 * @param custId
	 * @param url
	 * @return
	 */
	public boolean hashUrlPermission(String custId, String url) {
		return commonCacheService.hashUrlPermission(custId, url);

	}

	/**
	 * 判断用户是否拥有对应roleId
	 * 
	 * @param custId
	 * @param roleId
	 * @return
	 */
	public boolean hasRoleId(String custId, String roleId) {
		return commonCacheService.hasRoleId(custId, roleId);

	}

	/**
	 * 根据用户id获取用户角色ID集合 先从缓存获取，若无，从数据库获取
	 * 
	 * @param custId
	 * @return Set<String roleId>
	 */
	public Set<String> getCustRoleIdSet(String custId) {

		return (Set<String>) commonCacheService.getCustRoleIdSet(custId);
	}

	/**
	 * 从数据库获取用户的角色ID集合
	 * 
	 * @param custId
	 * @return Set<String roleId>
	 */
	private Set<String> getRoleByCustId(String custId) {
		List<AsRole> permList = this.listRoleByCustId(null, null, custId);
		if (permList != null && !permList.isEmpty()) {
			HashSet<String> ret = new HashSet<String>();
			for (AsRole vo : permList) {
				ret.add(vo.getRoleId());
			}
			return ret;
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
