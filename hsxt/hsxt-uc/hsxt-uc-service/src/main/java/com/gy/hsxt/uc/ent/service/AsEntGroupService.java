/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.ent.service;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.ent.bean.EntGroup;
import com.gy.hsxt.uc.ent.bean.GroupUser;
import com.gy.hsxt.uc.ent.mapper.EntGroupMapper;
import com.gy.hsxt.uc.ent.mapper.GroupUserMapper;

/**
 * 用户中心 企业用户组相关接口服务实现类
 * 
 * @Package: com.gy.hsxt.uc.ent.service
 * @ClassName: AsEntGroupService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-24 下午4:34:16
 * @version V1.0
 */
@Service
public class AsEntGroupService implements IUCAsEntGroupService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.ent.service.AsEntGroupService";
	@Autowired
	private EntGroupMapper groupMapper;

	@Autowired
	private GroupUserMapper groupUserMapper;

	@Autowired
	private IUCAsOperatorService operatorService;

	/**
	 * 查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#listGroup(java.lang.String,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AsEntGroup> listGroup(String entCustId, Page page)
			throws HsException{
		return this.listGroup(entCustId, null, page);
	}
			
	/**
	 * 查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param groupName
	 *            用户组名称
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#listGroup(java.lang.String,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AsEntGroup> listGroup(String entCustId,String groupName, Page page)
			throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(page)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		if(groupName!=null){
			groupName="%"+groupName+"%"; //模糊查询
		}

		int totalSize = groupMapper.countGroup(entCustId,groupName, page);
		List<EntGroup> list = groupMapper.pageGroup(entCustId,groupName, page);

		List<AsEntGroup> asGroupList = EntGroup.buildAsEntGroup(list);
		// 查询用户组组员（操作员）
		for (AsEntGroup group : asGroupList) {
			List<AsOperator> opers = operatorService.listOperByGroupId(group
					.getGroupId());
			group.setOpers(opers);
		}
		return new PageData<>(totalSize, asGroupList);
	}

	/**
	 * 查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param groupName
	 *            用户组名
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#findGroup(java.lang.String,
	 *      java.lang.String)
	 */
	public AsEntGroup findGroup(String entCustId, String groupName)
			throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户号不能为空");
		}
		if (isBlank(groupName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户组名不能为空");
		}
		EntGroup group = groupMapper.selectByGroupName(entCustId, groupName);
		if (group != null) {
			AsEntGroup asGroup = new AsEntGroup();
			BeanUtils.copyProperties(group, asGroup);
			List<AsOperator> opers = operatorService.listOperByGroupId(group
					.getGroupId());
			asGroup.setOpers(opers);
			return asGroup;
		}
		return null;
	}

	/**
	 * 添加企业用户组
	 * 
	 * @param group
	 *            企业用户组
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#addGroup(com.gy.hsxt.uc.as.bean.ent.AsEntGroup,
	 *      java.lang.String)
	 */
	@Override
	public void addGroup(AsEntGroup group, String operator) throws HsException {
		// 验证数据
		if (isBlank(group) || isBlank(group.getEntCustId())
				|| isBlank(group.getGroupName()) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 判断是否已存在相同组名
		EntGroup entGroup = groupMapper.selectByGroupName(group.getEntCustId(),
				group.getGroupName());
		if (entGroup != null) {
			throw new HsException(ErrorCodeEnum.ENT_GROUP_EXIST.getValue(),
					ErrorCodeEnum.ENT_GROUP_EXIST.getDesc());
		}

		// 添加用户组入库
		entGroup = EntGroup.generateEntGroup(group);
		entGroup.setCreatedby(operator);
		entGroup.setUpdatedby(operator);
		groupMapper.insert(entGroup);
	}

	/**
	 * 修改企业用户组
	 * 
	 * @param group
	 *            企业用户组
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#updateGroup(com.gy.hsxt.uc.as.bean.ent.AsEntGroup,
	 *      java.lang.String)
	 */
	@Override
	public void updateGroup(AsEntGroup group, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(group) || isBlank(group.getGroupId())
				|| isBlank(group.getEntCustId()) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 判断是否已存在相同组名
		EntGroup entGroup = groupMapper.selectByGroupName(group.getEntCustId(),
				group.getGroupName());
//		long groupId = entGroup.getGroupId().longValue(); // 有 nullPoinException 
		if (entGroup != null &&  !(entGroup.getGroupId().equals( group.getGroupId()))) {
			throw new HsException(ErrorCodeEnum.ENT_GROUP_EXIST.getValue(),
					group.getGroupName()+ErrorCodeEnum.ENT_GROUP_EXIST.getDesc()+group.getGroupId());
		}

		// 修改用户组
		entGroup = EntGroup.generateEntGroup(group);
		entGroup.setUpdatedby(operator);
		entGroup.setUpdateDate(getNowTimestamp());
		groupMapper.updateByGroupId(entGroup);
	}

	/**
	 * 删除企业用户组
	 * 
	 * @param groupId
	 *            用户组编号 主键ID
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#deleteGroup(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public void deleteGroup(Long groupId, String operator) throws HsException {
		// 验证数据
		if (isBlank(groupId) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		int count = groupMapper.countCurrentGroup(groupId);
		if (count > 0) {
			throw new HsException(ErrorCodeEnum.CANOT_DELETE_GROUP.getValue(),
					ErrorCodeEnum.CANOT_DELETE_GROUP.getDesc());
		}
		// 删除用户组
		groupMapper.deleteByGroupId(groupId);
	}

	/**
	 * 添加组员
	 * 
	 * @param operCustIds
	 *            操作员客户号
	 * @param groupId
	 *            用户组编号 主键ID
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#addGroupUser(java.util.List,
	 *      java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public void addGroupUser(List<String> operCustIds, Long groupId,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(operCustIds) || operCustIds.isEmpty() || isBlank(groupId)
				|| isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 数据入库
		for (String operCustId : operCustIds) {
			GroupUser groupUser = new GroupUser();
			groupUser.setGroupId(groupId);
			groupUser.setOperCustId(operCustId);
			groupUser.setCreatedby(operator);
			groupUser.setUpdatedby(operator);
			groupUserMapper.insert(groupUser);
		}
	}

	/**
	 * 删除组员
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param groupId
	 *            用户组编号 主键ID
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#deleteGroupUser(java.lang.String,
	 *      java.lang.Long, java.lang.String)
	 */
	@Override
	public void deleteGroupUser(String operCustId, Long groupId, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(operCustId) || isBlank(groupId) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 删除组员
		groupUserMapper.deleteGroupUser(groupId, operCustId);
	}

	/**
	 * 重置操作员用户组 （删除原有的组关联、新增用户组关联）
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param groupIds
	 *            用户组编号列表
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#resetOperatorGroup(java.lang.String,
	 *      java.util.List, java.lang.String)
	 */
	@Transactional
	public void resetOperatorGroup(String operCustId, List<Long> groupIds,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"必传参数[operCustId]为空");
		}
		// 移除原有组
		groupUserMapper.removeUserGroup(operCustId);

		if (groupIds != null && !groupIds.isEmpty()) {
			for (Long groupId : groupIds) {
				GroupUser groupUser = new GroupUser();
				groupUser.setGroupId(groupId);
				groupUser.setOperCustId(operCustId);
				groupUser.setCreatedby(operator);
				groupUser.setUpdatedby(operator);
				groupUserMapper.insert(groupUser);
			}
		}

	}
	/**
	 * 清除用户组下的所有用户
	 * @param groupId
	 * @param operator
	 * @throws HsException 
	 * @see com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService#clearAllGroupUser(java.lang.Long, java.lang.String)
	 */
	@Override
	public void clearAllGroupUser(Long groupId, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(groupId) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		try {
			groupUserMapper.clearAllGroupUser(groupId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "clearAllGroupUser", "清除用户组下的所有用户异常：\r\n", e);
		}
		
	}

}
