package com.gy.hsxt.uc.as.api.checker;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.checker.AsDoubleChecker;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;

public interface IUCAsDoubleCheckerInfoService {
	/**
	 * 添加双签用户
	 * @param doubleChecker	双签用户
	 * @throws HsException
	 */
	public  void  regDoubleChecker (AsSysOper asSysOper) throws  HsException;
	
	/**
	 * 查询双签用户信息(单个)
	 * @param checkCustId	双签员客户号
	 * @return
	 * @throws HsException
	 */
	public  AsDoubleChecker  searchDoubleCheckerByCustId (String  checkCustId) throws  HsException;
	
	/**
	 * 查询双签用户信息（列表）
	 * @param platformCode	子系统code(非必填)
	 * @param subSystemCode	平台code(非必填)
	 * @return
	 * @throws HsException
	 */
	public  List<AsDoubleChecker> ListDoubleChecker (String platformCode,String subSystemCode,Page page) throws  HsException;
	
	/**
	 * 修改双签用户信息
	 * @param doubleChecker	双签用户
	 * @throws HsException
	 */
	public  void  updateDoubleCheckerInfo (AsDoubleChecker doubleChecker) throws  HsException;
	
	/**
	 * 修改管理员登录密码（双签用户管理员）
	 * @param doubleChecker	双签用户
	 * @throws HsException
	 */
	public  void  modifyAdminLogPwd (AsDoubleChecker doubleChecker) throws  HsException;
	
	/**
	 * 修改双签员登录密码
	 * @param doubleChecker	双签用户
	 * @throws HsException
	 */
	public  void  modifyDouboleCheckerLogPwd (AsDoubleChecker doubleChecker) throws  HsException;
}
