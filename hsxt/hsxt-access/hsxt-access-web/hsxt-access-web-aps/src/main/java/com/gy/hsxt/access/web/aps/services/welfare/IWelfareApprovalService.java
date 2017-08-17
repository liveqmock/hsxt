package com.gy.hsxt.access.web.aps.services.welfare;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ApprovalDetail;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalRecord;

/**
 * 积分福利--审批及查询
 * 
 * @category 积分福利审批及查询
 * @projectName hsxt-access-web-aps
 * @package com.gy.hsxt.access.web.aps.services.welfare.IWelfareApprovalService.java
 * @className IWsApprovalService
 * @description 积分福利--审批及查询
 * @author leiyt
 * @createDate 2015-12-29 下午3:21:05
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午3:21:05
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public interface IWelfareApprovalService  extends IBaseService{
	/**
	 * 积分福利审批
	 * @category				积分福利审批
	 * @param approvalId 		审批单流水号
	 * @param approvalResult 	审批状态 1 通过 2 不通过驳回
	 * @param replyAmount 		审批批复金额
	 * @param remark 			审批说明
	 * @throws HsException
	 */
	public void approvalWelfare(String approvalId, Integer approvalResult, String replyAmount, String remark) throws HsException;
	/**
	 * 查询待审批记录
	 * @category 				查询待审批记录
	 * @param queryCondition 	查询条件对象
	 * @param page 				分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<ApprovalRecord> listPendingApproval(ApprovalQueryCondition queryCondition, Page page) throws HsException;
	/**
	 * 查询审批记录
	 * @category 查询审批记录
	 * @param queryCondition 	查询条件对象
	 * @param page 				分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<ApprovalRecord> listApprovalRecord(ApprovalQueryCondition queryCondition, Page page) throws HsException;
	/**
	 * 查询审批详情
	 * @category 查询审批详情
	 * @param approvalId 审批单流水号
	 * @return
	 * @throws HsException
	 */
	public ApprovalDetail queryApprovalDetail(String approvalId) throws HsException;
	
	/**
	 * 更新任务
	 * 
	 * @param bizNo
	 *            业务编号
	 * @param taskStatus
	 *            任务状态
	 * @param remark
	 *            备注
	 * @throws HsException
	 */
	public void updateTask(String bizNo, Integer taskStatus, String remark) throws HsException;

}
