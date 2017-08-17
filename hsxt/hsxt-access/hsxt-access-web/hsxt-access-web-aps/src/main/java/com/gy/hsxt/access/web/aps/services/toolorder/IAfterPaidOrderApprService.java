/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 *
 */
public interface IAfterPaidOrderApprService extends IBaseService {

	/**
	 * 分页查询售后订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月22日 下午5:44:57
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<ToolOrderPage>
	 * @version V3.0.0
	 */
	public PageData<ToolOrderPage> queryAfterOrderPlatPage(Map filterMap, Map sortMap, Page page);

	/**
	 * 查询工具售后单
	 * 
	 * @param orderNo
	 * @return
	 * @throws HsException
	 */
	public AfterService searchOrderDetail(String orderNo) throws HsException;

	/**
	 * 工具售后单审批
	 * 
	 * @param obj
	 * @throws HsException
	 */
	public void apprAfterService(AfterService asObj, String listDetail) throws HsException;
}
