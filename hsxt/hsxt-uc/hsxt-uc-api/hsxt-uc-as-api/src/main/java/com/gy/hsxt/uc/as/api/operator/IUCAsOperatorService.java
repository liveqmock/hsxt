/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.api.operator;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition;

/**
 * 用户中心对接入提供的操作员相关服务接口类
 * 
 * @Package: com.gy.hsxt.uc.as.api.operator
 * @ClassName: IUCAsOperatorService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午4:05:41
 * @version V1.0
 */
public interface IUCAsOperatorService {

	/**
	 * 添加操作员
	 * 
	 * @param oper
	 *            操作员信息
	 * @param adminCustId
	 *            管理员客户号
	 * 
	 * @throws HsException
	 */
	public String addOper(AsOperator oper, String adminCustId) throws HsException;

	/**
	 * 查询操作员信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @return
	 * @throws HsException
	 */
	public AsOperator searchOperByCustId(String operCustId) throws HsException;
	
	/**
	 * 查询操作员信息通过用户名
	 * 
	 * @param entResNo
	 *            企业客户号 不能为空
	 * @param userName
	 *            用户名（操作员账户名）
	 * @return
	 * @throws HsException
	 */
	public AsOperator searchOperByUserName(String entResNo, String userName) throws HsException;

	/**
	 * 根据客户号查询操作员
	 * @param roleId 角色ID
	 * @param name 操作员名称
	 * @param phone 手机号 
	 * @param operCustIds 多个客户号使用逗号分隔
	 * @return
	 * @throws HsException
	 */
	public List<AsOperator> searchOpersByCustIds(String roleId, String name, String phone, String operCustIds)throws HsException  ;
	/**
	 * 查询企业操作员列表
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public List<AsOperator> listOperByEntCustId(String entCustId) throws HsException;

	/**
	 * 修改操作员信息
	 * 
	 * @param oper
	 *            操作员信息
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 */
	public void updateOper(AsOperator oper, String adminCustId) throws HsException;

	/**
	 * 删除操作员信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param adminCustId
	 *            管理员客户号
	 * @throws HsException
	 */
	public void deleteOper(String operCustId, String adminCustId) throws HsException;

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
	public void bindCard(String operCustId, String perResNo, String adminCustId) throws HsException;

	/**
	 * 操作员确定绑定互生卡
	 * 
	 * @param operResNo
	 *            待绑定消费者互生号
	 * 
	 * @throws HsException
	 */
	public void confirmBindCard(String perResNo) throws HsException;

	/**
	 * 操作员解绑互生卡绑定
	 * 
	 * @param operCustId
	 *            待解绑操作员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#unBindCard(java.lang.String)
	 */
	@Transactional
	public void unBindCard(String operCustId) throws HsException;

	/**
	 * 查询操作员客户号通过企业互生号和用户名
	 * 
	 * @param entResNo
	 *            企业客户号 不能为空
	 * @param userName
	 *            用户名（操作员账户名）
	 * @return
	 * @throws HsException
	 */
	public String findOperCustId(String entResNo, String userName) throws HsException;

	/**
	 * 查询操作员客户号通过操作员互生号
	 * 
	 * @param operResNo
	 *            操作员客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public String findOperCustId(String operResNo) throws HsException;

	/**
	 * 设置密码，用于第一次设置或重置密码
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param newLoginPwd
	 *            登陆密码
	 * @throws HsException
	 */
	public void setLoginPwd(String operCustId, String newLoginPwd) throws HsException;

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
	 * @throws HsException
	 */
	public void validLoginPwd(String entResNo, String username, String loginPwd, String secretKey);

	/**
	 * 查询企业用户组的组员（操作员）列表
	 * 
	 * @param entGroupId
	 *            企业用户组编号
	 * @return
	 * @throws HsException
	 */
	public List<AsOperator> listOperByGroupId(Long entGroupId) throws HsException;

	/**
	 * 查询操作员信息和角色信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param platformCode
	 *            平台代码
	 * @param subSystemCode
	 *            子系统代码
	 * @return
	 * @throws HsException
	 */
	public AsOperator searchOperAndRoleInfoByCustId(String operCustId, String platformCode,
			String subSystemCode) throws HsException;


	/**
	 * 查询企业操作员信息和角色信息列表
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public List<AsOperator> listOperAndRoleByEntCustId(String entCustId) throws HsException;

	/**
	 * 分页查询企业操作员信息
	 * 
	 * @param condition
	 *            分页查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsOperator> listOperators(AsQueryOperatorCondition condition, Page page)
			throws HsException;
	
	/**
	 * 获取操作员登录失败的次数
	 * @param entResNo
	 * @param operNo
	 * @return
	 */
	public Integer getLoginFailTimes(String entResNo,String operNo);
}
