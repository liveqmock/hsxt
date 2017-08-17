package com.gy.hsxt.access.web.aps.services.welfare.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareApprovalService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsApprovalService;
import com.gy.hsxt.ws.bean.ApprovalDetail;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalRecord;
import com.gy.hsxt.ws.bean.WelfareQualify;

/**
 * 积分福利审批服务实现类
 * 
 * @category 积分福利审批服务实现类
 * @projectName hsxt-access-web-aps
 * @package 
 *          com.gy.hsxt.access.web.aps.services.welfare.impl.WelfareApprovalServiceImpl
 *          .java
 * @className WsApprovalServiceImpl
 * @description 积分福利审批服务实现类
 * @author leiyt
 * @createDate 2015-12-29 下午4:12:59
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午4:12:59
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Service
public class WelfareApprovalServiceImpl implements IWelfareApprovalService {
	/** 积分福利申请审批 WS dubbo */
	@Autowired
	IWsApprovalService wsApprovalService;

	@Override
	public void approvalWelfare(String approvalId, Integer approvalResult, String replyAmount,
			String remark) throws HsException {
		try{
			wsApprovalService.approvalWelfare(approvalId, approvalResult, replyAmount, remark);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "approvalWelfare", "积分福利申请审批失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public PageData<ApprovalRecord> listPendingApproval(ApprovalQueryCondition queryCondition,
			Page page) throws HsException {
		try{
			return wsApprovalService.listPendingApproval(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listPendingApproval", "查询待审批记录失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public PageData<ApprovalRecord> listApprovalRecord(ApprovalQueryCondition queryCondition,
			Page page) throws HsException {
		try{
			return wsApprovalService.listApprovalRecord(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listApprovalRecord", "查询审批记录失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public ApprovalDetail queryApprovalDetail(String approvalId) throws HsException {
         try
		{
			ApprovalDetail approvalDetail = wsApprovalService.queryApprovalDetail(approvalId);
			if (approvalDetail.getFirstApprovalInfo() == null) {
				approvalDetail.setFirstApprovalInfo(new ApprovalRecord());
			}
			if (approvalDetail.getWelfareQualify() == null) {
				approvalDetail.setWelfareQualify(new WelfareQualify());
			}
			return approvalDetail;
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryApprovalDetail", "查询积分福利审批详情失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
		
	}

	/**
	 * 积分福利审批分页查询
	 */
	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		ApprovalQueryCondition queryCondition = new ApprovalQueryCondition();
		// welfareType 0意外伤害1免费医疗2他人身故
		queryCondition.setWelfareType(CommonUtils.toInteger(filterMap.get("welfareType")));
		// 审批人客户号
		queryCondition.setApprovalCustId((String) filterMap.get("approvalCustId"));
		// 审批步骤：
		queryCondition.setApprovalStep(CommonUtils.toInteger(filterMap.get("appovalStep")));
		queryCondition.setStartTime((String) filterMap.get("startTime"));
		queryCondition.setEndTime((String) filterMap.get("endTime"));
		// 申请人姓名
		queryCondition.setProposerName((String) filterMap.get("proposerName"));
		// 申请人互生号
		queryCondition.setProposerResNo((String) filterMap.get("proposerResNo"));
		try{
			return wsApprovalService.listPendingApproval(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listPendingApproval", "积分福利审批分页查询失败", ex);
			return null;
		}
	}

	public PageData findScrollResult2(Map filterMap, Map sortMap, Page page) throws HsException {
		ApprovalQueryCondition queryCondition = new ApprovalQueryCondition();
		// welfareType 0意外伤害1免费医疗2他人身故
		queryCondition.setWelfareType(CommonUtils.toInteger(filterMap.get("welfareType")));
		queryCondition.setStartTime((String) filterMap.get("startTime"));
		queryCondition.setEndTime((String) filterMap.get("endTime"));
		// 申请人姓名
		queryCondition.setProposerName((String) filterMap.get("proposerName"));
		// 申请人互生号
		queryCondition.setProposerResNo((String) filterMap.get("proposerResNo"));
		try{
			return wsApprovalService.listApprovalRecord(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult2", "查询审批记录失败", ex);
			return null;
		}
	}

	@Override
	public Object findById(Object id) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(String entityJsonStr) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkVerifiedCode(String custId, String verifiedCode) throws HsException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void updateTask(String bizNo, Integer taskStatus, String remark) throws HsException {
		try{
			wsApprovalService.updateTask(bizNo, taskStatus, remark);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "updateTask", "更新任务失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	} 

}
