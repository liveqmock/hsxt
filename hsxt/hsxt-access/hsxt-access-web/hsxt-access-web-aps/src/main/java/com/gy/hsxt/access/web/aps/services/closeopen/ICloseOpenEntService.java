/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.closeopen;

import java.util.Map;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntDetail;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.closeopen
 * @className : ICloseOpenEntService.java
 * @description : 关闭、开启企业系统管理接口类
 * @author : chenli
 * @createDate : 2015-1-19
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ICloseOpenEntService extends IBaseService {

	/**
	 * 申请关闭系统
	 * 
	 * @param closeOpenEnt
	 *            关闭系统信息
	 * @throws HsException
	 */
	public void closeEnt(CloseOpenEnt closeOpenEnt) throws HsException;

	/**
	 * 申请开启系统
	 * 
	 * @param closeOpenEnt
	 *            开启系统信息
	 * @throws HsException
	 */
	public void openEnt(CloseOpenEnt closeOpenEnt) throws HsException;

	/**
	 * 查询关闭、开启系统申请
	 * 
	 * @param closeOpenEntParam
	 *            查询条件
	 * @param page
	 *            分页信息
	 * @return 关闭、开启系统申请列表
	 * @throws HsException
	 */
	public PageData<CloseOpenEnt> queryCloseOpenEnt4Appr(Map filterMap,
			Map sortMap, Page page) throws HsException;

	/**
	 * 查询关闭、开启系统详情
	 * 
	 * @param applyId
	 *            申请编号
	 * @return 关闭、开启系统详情
	 */
	public CloseOpenEntDetail queryCloseOpenEntDetail(String applyId);

	/**
	 * 审批申请关闭、开启系统
	 * 
	 * @param apprParam
	 *            审批参数
	 * @throws HsException
	 */
	public void apprCloseOpenEnt(ApprParam apprParam) throws HsException;

	public PageData getAllEnt(Map filterMap, Map sortMap, Page page)
			throws HsException;

	/**
	 * 查询企业上一次关闭系统信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return 返回企业上一次关闭系统信息
	 */
	public CloseOpenEntInfo queryLastCloseOpenEntInfo(String entCustId,
			Integer applyType);
	
	
	/**
	 * 更新工单
	 * @param bizNo 业务编号
	 * @param taskStatus 工单状态
	 * @param exeCustId 处理人
	 * @throws HsException
	 */
	public void updateTask(String bizNo, Integer taskStatus, String exeCustId)throws HsException;

}
