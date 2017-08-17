/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.uc.as.api.sysoper;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;

/** 
 * 
 * @Package: com.gy.hsxt.uc.as.api.sysoper  
 * @ClassName: IUCAsSysOperInfoService 
 * @Description: 系统用户信息管理（AS系统调用）
 *
 * @author: tianxh
 * @date: 2015-10-30 下午4:09:40 
 * @version V1.0  
 */
public interface IUCAsSysOperInfoService {
	/**
	 *  注册系统用户（新增）
	 * @param asSysOper	系统用户信息
	 * @return 
	 */
	public String regSysOper(AsSysOper asSysOper,String adminCustId);
	
	/**
	 * 更新系统用户信息
	 * @param asSysOper 系统用户信息
	 */
	public void updateSysOper(AsSysOper asSysOper);
	
	/**
	 * 删除系统用户信息
	 * @param custId  客户号
	 */
	public void delSysOper(String custId);
	
	/**
	 * 查询系统用户信息
	 * @param custId  客户号
	 */
	public AsSysOper searchSysOperInfoByCustId(String custId);
	
	/**
	 * 通过用户名查询客户号
	 * @param userName
	 * @return
	 */
	public String findCustIdByUserName(String userName);
	
	/**
	 * 分页查询用户列表，可附加过滤条件：平台，子系统 ,类型，客户号,账号，姓名
	 * @param platformCode 平台
	 * @param subSystemCode 子系统
	 * @param isAdmin 类型
	 * @param operCustId 客户号
	 * @param userName 账号
	 * @param realName 姓名
	 * @param page 分页参数
	 * @return 查询结果分页数据
	 * @throws HsException
	 */
	public PageData<AsSysOper> listPermByPage(String platformCode,
			String subSystemCode, Short isAdmin, String operCustId, String userName, String realName, Page page)
			throws HsException;
	
	/**
	 * 修改管理员登录密码(系统用户的管理员信息)
	 * @param sysOper	系统用户
	 * @throws HsException
	 */
	public  void   modifyAdminLogPwd(AsSysOper  sysOper) throws  HsException;
	
	/**
	 * 修改系统操作员登录密码
	 * @param sysOper	系统用户
	 * @throws HsException
	 */
	public  void   modifySysOperPwd(AsSysOper  sysOper) throws  HsException;
	
	/**
	 * 查询系统用户和双签员用户列表
	 * @param userName	用户账号
	 * @param realName	用户姓名
	 * @return
	 * @throws HsException
	 */
	public List<AsSysOper> listSysOperAndChecker(String userName,String realName,Page page) throws HsException;
}
