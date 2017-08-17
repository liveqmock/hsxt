/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.common;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.ApsDoubleOperator;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.common
 * @className : IPubParamService.java
 * @description :
 * @author : LiZhi Peter
 * @createDate : 2015-11-25
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface IPubParamService {

	/**
	 * 方法名称：获取本平台详细信息 方法描述：获取系统配置信息
	 * 
	 * @return 系统信息
	 * @throws HsException
	 */
	public LocalInfo findSystemInfo() throws HsException;

	/**
	 * 取随机token 客户号为空 代表未登录的获取， 客户号不为空 用户已登录使用
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	public String getRandomToken(String custId);

	/**
	 * 
	 * 方法名称：获取示例图片管理列表 方法描述：获取示例图片管理列表
	 * 
	 * @return
	 * @throws HsException
	 */
	public Map<String, String> findImageDocList() throws HsException;

	/**
	 * 方法名称：查询办理业务文档列表 方法描述：查询办理业务文档列表
	 * 
	 * @return
	 * @throws HsException
	 */
	public Map<String, BizDoc> findBizDocList() throws HsException;

	/**
	 * 方法名称：查询常用业务文档列表 方法描述：查询常用业务文档列表
	 * 
	 * @return
	 * @throws HsException
	 */
	public Map<String, NormalDoc> findNormalDocList() throws HsException;

	/**
	 * 验证双签用户
	 * 
	 * @param ado
	 * @throws HsException
	 */
	public String verifyDoublePwd(ApsDoubleOperator ado) throws HsException;

	/**
	 * 方法名称：依据客户号获取操作员信息 方法描述：依据客户号获取操作员信息
	 * 
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public AsOperator searchOperByCustId(String custId);
	
	/**
	 * 方法名称：依据企业互生号查询企业信息
	 * 方法描述：依据企业互生号查询企业信息
	 * @param companyResNo 企业互生号
	 * @return
	 * @throws HsException
	 */
	public AsEntMainInfo findMainInfoByResNo(String companyResNo);

	/**
	 * 获取企业兑换互生币限额
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月22日 上午11:53:17
	 * @param entCustId
	 * @return
	 * @throws HsException
	 * @return : Map<String,Object>
	 * @version V3.0.0
	 */
	public Map<String, Object> getEntBuyHsbLimit(String entCustId) throws HsException;
	
	/**
     * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,权限类型
     * 
     * @param platformCode 平台代码
     *            null则忽略该条件
     * @param subSystemCode 子系统代码
     *            null则忽略该条件
     * @param permType 权限类型 0：菜单  1：功能
     *            null则忽略该条件
     * @param custId 客户编号
     * @param permCode 权限代码，null则忽略该条件
     * @return
     * @throws HsException
     */
	public List<AsPermission> findPermByCustId(String platformCode,String subSystemCode,String parentId,  Short permType, String custId,String permCode) throws HsException ;
}
