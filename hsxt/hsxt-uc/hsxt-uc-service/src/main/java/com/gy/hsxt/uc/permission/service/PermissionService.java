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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.PageUtil;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.permission.bean.Permission;
import com.gy.hsxt.uc.permission.mapper.PermissionMapper;
import com.gy.hsxt.uc.permission.mapper.RoleMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 权限模块-权限管理
 * 
 * @Package: com.gy.hsxt.uc.permission.service
 * @ClassName: PermissionService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-13 上午11:55:05
 * @version V1.0
 */
@Service
public class PermissionService implements IUCAsPermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	CommonCacheService commonCacheService;

	@Autowired
	private RoleMapper roleMapper;

	private String[] ignoreProperties = { "permType", "createDate",
			"updateDate" }; // 类型不匹配，不能自动复制的属性名称

	/**
	 * 增加权限，新增一条权限记录
	 * 
	 * @param vo
	 *            权限信息,非空字段入库
	 * @param operator
	 * @throws HsException
	 */
	public void addPerm(AsPermission vo, String operator) throws HsException {
		try {
			// 封装待入库数据
			Permission permission = vo2bean(vo);
			permission.setCreatedby(operator);
			if (permission.getPermId() == null
					|| "".equals(permission.getPermId())) { // 根据当前毫秒数生成15位随机码
				//主键id
				String id = CustIdGenerator.genSeqId("");
				permission.setPermId(id);
			}

			int count = permissionMapper.insertSelective(permission);
		} catch (Exception e) {
			SystemLog.error("PermissionService", "addPerm", JSON.toJSONString(vo), e);
			throw new HsException(ErrorCodeEnum.PERM_EXIST.getValue(), "操作失败，请检查权限id:" + vo.getPermId()
					+ "是否已经存在." + e.getMessage());
		}

	}

	/**
	 * 删除权限 修改记录状态为N
	 * 
	 * @param permId
	 * @param operator
	 * @throws HsException
	 */
	public void deletePerm(String permId, String operator) throws HsException {
		// 封装数据库操作条件参数
		Permission permission = new Permission();
		permission.setPermId((permId));
		permission.setIsactive("N");
		permission.setUpdatedby(operator);
		// permission.setUpdateDate(new Date());
		try {
			int count = permissionMapper
					.updateByPrimaryKeySelective(permission);
		} catch (Exception e) {
			SystemLog.error("PermissionService", "deletePerm", JSON.toJSONString(permId), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "deletePerm fail!" + e.getMessage());
		}
	}

	/**
	 * 修改权限信息,只修改非null字段
	 * 
	 * @param vo
	 *            权限信息
	 * @param operator
	 * @throws HsException
	 */
	public void updatePerm(AsPermission vo, String operator) throws HsException {
		try {
			// 封装数据库操作条件参数
			Permission permission = vo2bean(vo);
			permission.setUpdatedby(operator);
			// permission.setUpdateDate(new Date());
			int count = permissionMapper
					.updateByPrimaryKeySelective(permission);
		} catch (Exception e) {
			SystemLog.error("PermissionService", "updatePerm", JSON.toJSONString(vo), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "updatePerm fail!" + e.getMessage());
		}
	}

	/**
	 * 分页查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @param parentId
	 *            父权限ID null则忽略该条件
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<AsPermission> listPermByPage(String platformCode,
			String subSystemCode, Short permType, String parentId, Page page)
			throws HsException {
		return this.listPermByPage(platformCode, subSystemCode, permType, parentId, null, null, page);
	}
	/**
	 * 分页查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id,权限代码，权限名称
	 * @param platformCode null则忽略该条件
	 * @param subSystemCode null则忽略该条件
	 * @param permType null则忽略该条件
	 * @param parentId null则忽略该条件
	 * @param permCode null则忽略该条件
	 * @param permName null则忽略该条件
	 * @param page null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public PageData<AsPermission> listPermByPage(String platformCode,
			String subSystemCode, Short permType, String parentId,String permCode,String permName, Page page)
			throws HsException {
		List<AsPermission> dataList = this.listPerm(platformCode,
				subSystemCode, permType, parentId,permCode,permName);
		return (PageData<AsPermission>) PageUtil.subPage(page, dataList);
	}

	/**
	 * 查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @param parentId
	 *            父权限ID null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPerm(String platformCode,
			String subSystemCode, Short permType, String parentId)
			throws HsException {
		return this.listPerm(platformCode, subSystemCode, permType, parentId, null,null);
	}
	
	/**
	 * 查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id,权限编码
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @param parentId
	 *            父权限ID null则忽略该条件
	 * @param permCode
	 *            权限编码 null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPerm(String platformCode,
			String subSystemCode, Short permType, String parentId,String permCode)
			throws HsException {
		return this.listPerm(platformCode, subSystemCode, permType, parentId, permCode, null);
	}
	public List<AsPermission> listPerm(String platformCode,
			String subSystemCode, Short permType, String parentId,String permCode,String permName)
			throws HsException {
		// 封装数据库操作条件参数
		Permission permission = new Permission(); // 查询条件
		permission.setPlatformCode(platformCode);
		permission.setSubSystemCode(subSystemCode);
		permission.setPermType(permType);
		permission.setParentId(parentId);
		permission.setPermCode(permCode);
		permission.setPermName(permName);
		List<Permission> selectResultList = this.getPermLis(permission);
		// 把数据库查询结果封装成接口VO并返回给接口调用方
		ArrayList<AsPermission> retList = beanList2voList(selectResultList);
		return retList;

	}

	public List<Permission> getPermLis(Permission permission) {
		
		try {
			List<Permission> selectResultList = permissionMapper
					.selectByPid(permission);
			return selectResultList;
		} catch (Exception e) {
			SystemLog.error("PermissionService", "getPermLis", JSON.toJSONString(permission), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "listPermByPermId fail!");
		}
	}

	/**
	 * 查询子权限列表，可附加过滤条件：平台，子系统
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permId
	 *            父权限ID
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByPermId(String platformCode,
			String subSystemCode, String permId) throws HsException {
		return listPerm(platformCode, subSystemCode, null, permId);
	}

	/**
	 * 根据权限类型查询权限列表，可附加过滤条件：平台，子系统
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByType(String platformCode,
			String subSystemCode, Short permType) throws HsException {
		return listPerm(platformCode, subSystemCode, permType, null);
	}

	
	/**
	 * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,父权限id,权限类型,权限代码
	 * 
	 * @param platformCode 平台
	 *            null则忽略该条件
	 * @param subSystemCode 子系统
	 *            null则忽略该条件
	 * @param parentId 父id
	 *            null则忽略该条件
	 * @param permType 权限类型
	 *            null则忽略该条件
	 * @param custId 用户id
	 * @param permCode 权限代码，null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByCustId(String platformCode,
			String subSystemCode, String parentId, Short permType, String custId,String permCode)
			throws HsException {
		// 封装数据库操作条件参数
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("platformCode", platformCode);
		conditionMap.put("subSystemCode", subSystemCode);
		conditionMap.put("parentId", parentId);
		conditionMap.put("permType", permType);
		conditionMap.put("custId", custId);
		conditionMap.put("permCode", permCode);
		try {
			List<Permission> selectResultList = permissionMapper
					.selectByCustId(conditionMap);
			// 把数据库查询结果封装成接口VO并返回给接口调用方
			ArrayList<AsPermission> retList = beanList2voList(selectResultList);
			return retList;
		} catch (Exception e) {
			SystemLog.error("PermissionService", "listPermByCustId", JSON.toJSONString(conditionMap), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "listPermByCustId fail!");
		}
	}
	/**
	 * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,父权限id,权限类型
	 * 
	 * @param platformCode 平台
	 *            null则忽略该条件
	 * @param subSystemCode 子系统
	 *            null则忽略该条件
	 * @param parentId 父id
	 *            null则忽略该条件
	 * @param permType 权限类型
	 *            null则忽略该条件
	 * @param custId 用户id
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByCustId(String platformCode,
			String subSystemCode, String parentId, Short permType, String custId)
			throws HsException {
		return  this.listPermByCustId(platformCode, subSystemCode, parentId, permType, custId, null);
	}
	/**
	 * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,权限类型
	 * 
	 * @param platformCode 平台
	 *            null则忽略该条件
	 * @param subSystemCode 子系统
	 *            null则忽略该条件
	 * @param permType 权限类型
	 *            null则忽略该条件
	 * @param custId 用户id
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByCustId(String platformCode,
			String subSystemCode, Short permType, String custId)
			throws HsException {
		return this.listPermByCustId(platformCode, subSystemCode,null,permType, custId,null);
	}

//	/**
//	 * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统
//	 * 
//	 * @param platformCode 平台
//	 *            null则忽略该条件
//	 * @param subSystemCode 子系统
//	 *            null则忽略该条件
//	 * @param custId 用户id
//	 * @return
//	 * @throws HsException
//	 */
//	public List<AsPermission> listPermByCustId(String platformCode,
//			String subSystemCode, String custId) throws HsException {
//		return listPermByCustId(platformCode, subSystemCode,null, null, custId);
//
//	}

	/**
	 * 查询角色拥有的权限，可附加过滤条件：平台，子系统，权限类型
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @param roleId
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByRoleId(String platformCode,
			String subSystemCode, Short permType, String roleId)
			throws HsException {

		List<Permission> selectResultList = this.getRolePermList(platformCode,
				subSystemCode, permType, roleId);
		// 把数据库查询结果封装成接口VO并返回给接口调用方
		ArrayList<AsPermission> retList = beanList2voList(selectResultList);
		return retList;

	}

	public List<Permission> getRolePermList(String platformCode,
			String subSystemCode, Short permType, String roleId) {
		// 封装数据库操作条件参数
		HashMap<String, Object> conditionMap = new HashMap<String, Object>(); // 查询条件
		conditionMap.put("platformCode", platformCode);
		conditionMap.put("subSystemCode", subSystemCode);
		conditionMap.put("permType", permType);
		conditionMap.put("roleId", roleId);
		try {
			List<Permission> selectResultList = permissionMapper
					.selectByRoleId(conditionMap);

			return selectResultList;
		} catch (Exception e) {
			SystemLog.error("PermissionService", "getRolePermList", JSON.toJSONString(conditionMap), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "listPermByRoleId fail!");
		}
	}

	/**
	 * 查询角色拥有的权限，可附加过滤条件：平台，子系统
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param roleId
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByRoleId(String platformCode,
			String subSystemCode, String roleId) throws HsException {
		return listPermByRoleId(platformCode, subSystemCode, null, roleId);
	}

	/**
	 * 数据库bean转换成vo
	 * 
	 * @param beanList
	 * @return
	 */
	private ArrayList<AsPermission> beanList2voList(List<Permission> beanList) {
		ArrayList<AsPermission> retList = new ArrayList<AsPermission>();
		if (beanList != null) {
			for (Permission bean : beanList) {
				retList.add(bean2vo(bean));
			}
		}
		return retList;
	}

	/**
	 * 批量角色权限授权，跳过已有权限，插入未有权限。
	 * 
	 * @param roleId
	 * @param list
	 *            需授权权限id列表
	 * @param operator
	 * @throws HsException
	 */
	public void grantPerm(String roleId, List<String> list, String operator)
			throws HsException {
		if (list == null || list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "list cannot be empty." + list);
		}
		// 封装数据库操作条件参数
		HashMap<String, Object> grantData = new HashMap<String, Object>();
		grantData.put("roleId", roleId);
		grantData.put("operator", operator);
		grantData.put("idList", list);

		try {
			int count = permissionMapper.insertRolePermission(grantData);
		} catch (Exception e) {
			SystemLog.error("PermissionService", "grantPerm", JSON.toJSONString(grantData), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "grantPerm fail!");
		}

		resetRoleUrlCache(roleId);
	}

	/**
	 * 批量回收角色权限,删除角色授权记录
	 * 
	 * @param roleId
	 * @param list
	 *            需删除权限id列表,null则删除全部
	 * @param operator
	 * @throws HsException
	 */
	public void revokePerm(String roleId, List<String> list, String operator)
			throws HsException {
		if (list != null && list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "list cannot be empty." + list);
		}
		// 封装数据库操作条件参数
		HashMap<String, Object> revokeData = new HashMap<String, Object>();
		revokeData.put("roleId", roleId);
		revokeData.put("operator", operator);
		revokeData.put("idList", list);

		try {
			int count = permissionMapper.deleteRolePermission(revokeData);
		} catch (Exception e) {
			SystemLog.error("PermissionService", "revokePerm", JSON.toJSONString(revokeData), e);
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "revokePerm fail!");
		}
		resetRoleUrlCache(roleId);
	}

	/**
	 * 批量角色权限授权，删除已有权限，插入新权限。
	 * 
	 * @param roleId
	 * @param list
	 *            需授权权限id列表
	 * @param operator
	 * @throws HsException
	 */
	public void resetPerm(String roleId, List<String> list, String operator)
			throws HsException {
		if (list != null && list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "list cannot be empty." + list);
		}
		// 清空旧权限
		revokePerm(roleId, null, operator);
		// 授予新权限
		if (list != null && !list.isEmpty()) {
			grantPerm(roleId, list, operator);
		}
		

	}

	/**
	 * 获取角色已经拥有的权限id
	 * 
	 * @param roleId
	 *            角色id
	 * @return 权限id集
	 */
	public Set<String> getRolePermSet(String roleId) {
		List<Permission> selectResultList = this.getRolePermList(null, null,
				null, roleId);
		HashSet<String> ret = new HashSet<String>();
		if (selectResultList != null) {
			for (Permission obj : selectResultList) {
				ret.add(obj.getPermId());
			}
		}
		return ret;
	}
	
	public List<Map<String, Object>> getRolePermTree(String platformCode,
			String subSystemCode, Short permType, String parentId, String roleId){
		return this.getRolePermTree(platformCode, subSystemCode, permType, parentId, roleId,null);
	}
			
	/**
	 * 返回角色授权树
	 * @param platformCode 可授权条件 平台
	 * @param subSystemCode 可授权条件 子系统
	 * @param permType 可授权条件 权限类型
	 * @param parentId 可授权条件 父权限
	 * @param roleId 授权角色
	 * @return 返回角色授权树
	 */
	public List<Map<String, Object>> getRolePermTree(String platformCode,
			String subSystemCode, Short permType, String parentId, String roleId,String permCode) {
		// 封装数据库操作条件参数
				Permission permission = new Permission(); // 查询条件
				permission.setPlatformCode(platformCode);
				permission.setSubSystemCode(subSystemCode);
				permission.setPermType(permType);
				permission.setParentId(parentId);
				permission.setPermCode(permCode);
		List<Permission> allPermList = this.getPermLis(permission);
		// { id:12, pId:1, name:"disabled 1-2", chkDisabled:true, checked:true,
		// open:true},
		
		ArrayList<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		if (allPermList != null) {
			List<Permission> myPermList = this.getRolePermList(platformCode, subSystemCode, permType, roleId);
			//父权限集
			HashMap<String,Permission> pMap=list2map(allPermList);
			//角色已经拥有权限集
			HashMap<String,Permission> myMap=list2map(myPermList);
//			log.info(pMap.keySet());
			HashMap<String, Object> m;
			// 遍历所有可授权权限，生成权限树数据
			for (Permission obj : allPermList) {
				m = new HashMap<String, Object>();				
				String id = obj.getPermId();
				String pId = obj.getParentId();
				if(id.equals(pId)){
					//顶级节点的父id必须为0
					pId="0";
				}

				if (pMap.containsKey(pId)) {
					m.put("pId", pId); //树 父节点id
				}else{
					//父id定义不存在的，统一列到根节点点下
					m.put("pId", 0); //树 父节点id
				}
				m.put("id", id); //树 本节点id
				m.put("name", obj.getPermName());//树 本节点名称
				m.put("open", Boolean.TRUE);//树 是否展开
				if (myMap.containsKey(id)) {// 角色已经拥有该权限
					m.put("checked",  Boolean.TRUE);//树 是否选中
				}
				ret.add(m);
			}
		}
//		log.info(ret);
		return ret;
	}
	
	private HashMap<String,Permission> list2map(List<Permission> list){
		HashMap<String,Permission> ret= new HashMap<String,Permission>(list.size());
		if(ret==null) return ret;
		for (Permission obj : list) {
			ret.put(obj.getPermId(), obj);
		}
		return ret;
	}

	/**
	 * 重置指定角色url权限缓存
	 * 
	 * @param roleId
	 */
	public void resetRoleUrlCache(String roleId) {
		commonCacheService.resetRoleUrlCache(roleId);
	}

	/**
	 * 判断角色是否拥有对应url权限
	 * 
	 * @param roleId
	 * @param url
	 * @return
	 */
	public boolean hashUrlPermission(String roleId, String url) {
		return commonCacheService.hasUrlInRole(roleId, url);

	}

	private Permission vo2bean(AsPermission vo) {

		Permission bean = new Permission();
		BeanUtils.copyProperties(vo, bean, ignoreProperties);
		bean.setPermType(Short.parseShort(vo.getPermType()));
		if (vo.getLayout() != null && !"".equals(vo.getLayout())) {
			bean.setLayout(Short.parseShort(vo.getLayout()));
		}
		if (vo.getSortNum() != null && !"".equals(vo.getSortNum())) {
			bean.setSortNum(Short.parseShort(vo.getSortNum()));
		}
		return bean;
	}

	/**
	 * 数据库bean转换成vo
	 * 
	 * @param bean
	 * @return
	 */
	private AsPermission bean2vo(Permission bean) {
		AsPermission vo = new AsPermission();
		BeanUtils.copyProperties(bean, vo, ignoreProperties);
		vo.setPermType(String.valueOf(bean.getPermType()));
		if (bean.getLayout() != null) {
			vo.setLayout(bean.getLayout().toString());
		}
		if (bean.getSortNum() != null) {
			vo.setSortNum(bean.getSortNum().toString());
		}

		return vo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
