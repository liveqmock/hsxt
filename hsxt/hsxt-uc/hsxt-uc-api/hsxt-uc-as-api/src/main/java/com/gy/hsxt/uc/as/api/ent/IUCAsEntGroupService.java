/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.api.ent;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;

/**
 * 用户中心 企业用户组相关接口服务
 * 
 * @Package: com.gy.hsxt.uc.as.api.ent
 * @ClassName: IUCAsEntGroupService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-24 上午10:03:29
 * @version V1.0
 */
public interface IUCAsEntGroupService {

	/**
	 * 查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param groupName
	 *            用户组名
	 * @return
	 * @throws HsException
	 */
	public AsEntGroup findGroup(String entCustId, String groupName) throws HsException;

	/**
	 * 查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param groupName
	 *            企业名称
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<AsEntGroup> listGroup(String entCustId, String groupName, Page page) throws HsException;

	/**
	 * 添加企业用户组
	 * 
	 * @param group
	 *            企业用户组
	 * @param operator
	 *            操作者客户号
	 * 
	 * @throws HsException
	 */
	public void addGroup(AsEntGroup group, String operator) throws HsException;

	/**
	 * 修改企业用户组
	 * 
	 * @param group
	 *            企业用户组
	 * @param operator
	 *            操作者客户号
	 * 
	 * @throws HsException
	 */
	public void updateGroup(AsEntGroup group, String operator) throws HsException;

	/**
	 * 删除企业用户组
	 * 
	 * @param groupId
	 *            用户组编号 主键ID
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void deleteGroup(Long groupId, String operator) throws HsException;

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
	 */
	public void addGroupUser(List<String> operCustIds, Long groupId, String operator)
			throws HsException;

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
	 */
	public void deleteGroupUser(String operCustId, Long groupId, String operator)
			throws HsException;
	
	/**
	 * 清除用户组下的所有用户
	 * @param groupId
	 * @param operator
	 * @throws HsException
	 */
	public void clearAllGroupUser(Long groupId, String operator)
			throws HsException;

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
	 */
	public void resetOperatorGroup(String operCustId, List<Long> groupIds, String operator)
			throws HsException;
	
	/**
	 * 查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 */
	PageData<AsEntGroup> listGroup(String entCustId, Page page)
			throws HsException;


}
