package com.gy.hsxt.access.web.person.services.consumer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsApplyService;
import com.gy.hsxt.ws.api.IWsWelfareService;
import com.gy.hsxt.ws.bean.AccidentSecurityApply;
import com.gy.hsxt.ws.bean.ApplyQueryCondition;
import com.gy.hsxt.ws.bean.MedicalSubsidiesApply;
import com.gy.hsxt.ws.bean.OthersDieSecurityApply;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareQualify;

/**
 * 
 * 积分福利接口实现
 * 
 * @category 积分福利接口实现
 * @projectName hsxt-access-web-person
 * @package 
 *          com.gy.hsxt.access.web.person.services.consumer.impl.WelfareServiceImpl
 *          .java
 * @className WelfareServiceImpl
 * @description 积分福利接口实现
 * @author leiyt
 * @createDate 2015-12-29 下午4:54:04
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午4:54:04
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Service
public class WelfareServiceImpl extends BaseServiceImpl implements IWelfareService {

	/**
	 * 积分福利资格 积分福系统 dubbo
	 */
	@Autowired
	IWsWelfareService wsWelfareService;
	/**
	 * 积分福利申请 积分福利系统 dubbo
	 */
	@Autowired
	IWsApplyService wsApplyService;

	@Override
	public void applyAccidentSecurity(AccidentSecurityApply record) throws HsException {
		try{
			wsApplyService.applyAccidentSecurity(record);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "applyAccidentSecurity", "申请互生意外伤害保障失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public void applyMedicalSubsidies(MedicalSubsidiesApply record) throws HsException {
		try{
			wsApplyService.applyMedicalSubsidies(record);
		} catch(HsException e){
			throw e;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "applyMedicalSubsidies", "申请互生医疗补贴失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public void applyDieSecurity(OthersDieSecurityApply record) throws HsException {
		try{
			wsApplyService.applyDieSecurity(record);
		} catch(HsException e){
			throw e;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "applyDieSecurity", "代他人申请身故保障金失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		String custId = filterMap.get("custId") + "";
		Integer welfareType = null;
		Integer approvalStatus = null;
		String startTime = null;
		String endTime = null;
		if (filterMap.get("welfareType") != null) {
			welfareType = Integer.parseInt(filterMap.get("welfareType").toString());
		}
		if (filterMap.get("approvalStatus") != null) {
			approvalStatus = Integer.parseInt(filterMap.get("approvalStatus").toString());
		}
		if (filterMap.get("startTime") != null) {
			startTime = filterMap.get("startTime").toString();
		}
		if (filterMap.get("endTime") != null) {
			endTime = filterMap.get("endTime").toString();
		}
		ApplyQueryCondition condition = new ApplyQueryCondition();
		condition.setCustId(custId);
		condition.setWelfareType(welfareType);
		condition.setApprovalStatus(approvalStatus);
		condition.setStartTime(startTime);
		condition.setEndTime(endTime);
		try
		{
			return this.wsApplyService.listWelfareApply(condition, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "积分福利查询失败", ex);
			return null;
		}
	}

	@Override
	public WelfareApplyDetail queryWelfareApplyDetail(String applyWelfareNo) throws HsException {
		try{
			return wsApplyService.queryWelfareApplyDetail(applyWelfareNo);
		} catch(HsException e){
			throw e;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryWelfareApplyDetail", "福利申请明细查询失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public WelfareQualify findWelfareQualify(String hsResNo) throws HsException {
		try{
			return wsWelfareService.findWelfareQualify(hsResNo);
		} catch(HsException e){
			throw e;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findWelfareQualify", "积分福利资格查询失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
