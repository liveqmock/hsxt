/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tempacct.AccountName;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit
 * @className : IReceiveAccountNameService.java
 * @description : 收款账户名称服务接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
public interface IReceiveAccountNameService extends IBaseService {
	/**
	 * 创建 收款账户名称
	 * 
	 * @param account
	 *            实体类 非null
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	void create(AccountName accountName);

	/**
	 * 获取 某一条收款账户名称
	 * 
	 * @param receiveAccountId
	 *            账户户名编号 必须 非null
	 * @return 返回一个实体类
	 * @throws HsException
	 */
	AccountName findById(String receiveAccountId) throws HsException;

	/**
	 * 更新 收款账户名称
	 * 
	 * @param account
	 *            实体类 非null
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	void update(AccountName accountName) throws HsException;

	/**
	 * 获取 收款账户名称下拉菜单列表
	 * 
	 * @return 返回分好页的数据列表
	 * @throws HsException
	 */
	List<AccountName> getByMenu() throws HsException;
}
