/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.api;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ApprovalDetail;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalRecord;

/**
 * 提供积分福利审批相关服务
 * 
 * @Package: com.gy.hsxt.ws.api
 * @ClassName: IWsApprovalService
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午7:55:07
 * @version V3.0
 */
public interface IWsApprovalService {

	/**
	 * 审批积分福利
	 * 
	 * @param approvalId
	 *            审批记录编号（主键ID） 必传
	 * @param approvalResult
	 *            审批结果 1 通过 2 不通过驳回 必传
	 * @param replyAmount
	 *            批复金额
	 * @param remark
	 *            审批备注信息 选传
	 * @throws HsException
	 */
	public void approvalWelfare(String approvalId, Integer approvalResult, String replyAmount,
			String remark) throws HsException;

	/**
	 * 查询审批记录
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<ApprovalRecord> listApprovalRecord(ApprovalQueryCondition queryCondition,
			Page page) throws HsException;

	/**
	 * 查询待审批记录
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<ApprovalRecord> listPendingApproval(ApprovalQueryCondition queryCondition,
			Page page) throws HsException;

	/**
	 * 查询积分福利审批记录详情
	 * 
	 * @param approvalId
	 *            审批记录编号 必传
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
