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
import java.util.Map;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit
 * @className : ITempDebitSumService.java
 * @description : 临账统计服务接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ITempDebitSumService extends IBaseService {

	/**
	 * 临账统计详情分页查询
	 * 
	 * @param page
	 *            分页对象
	 * @param debitQuery
	 *            查询实体 必须 非空
	 * @return 返回分页的数据列表
	 * @throws HsException
	 */
	public PageData<Debit> queryDebitCountDetailListPage(Map filterMap,
			Map sortMap, Page paramPage) throws HsException;

	/**
	 * 导出临账统计详情
	 * 
	 * @param receiveAccountId
	 *            收款账户Id
	 * @return 返回某个收款账户信息下所有临账记录
	 * @throws HsException
	 */
	List<Debit> exportDebitData(String receiveAccountId, String startDate, String endDate) throws HsException;
}
