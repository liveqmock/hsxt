/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.api;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.AccidentSecurityApply;
import com.gy.hsxt.ws.bean.ApplyQueryCondition;
import com.gy.hsxt.ws.bean.MedicalSubsidiesApply;
import com.gy.hsxt.ws.bean.OthersDieSecurityApply;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareApplyRecord;

/**
 * 提供积分福利申请、查询服务
 * 
 * @Package: com.gy.hsxt.ws.api
 * @ClassName: IWsApplyService
 * @Description: 供接入层消费者门户调用
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午7:54:45
 * @version V3.0
 */
public interface IWsApplyService {

	/**
	 * 意外伤害保障金申请
	 * 
	 * @param accidentSecurityApply
	 *            意外伤害福利申请实体 必传
	 * 
	 * @throws HsException
	 */
	public void applyAccidentSecurity(AccidentSecurityApply accidentSecurityApply)
			throws HsException;

	/**
	 * 医疗补贴申请
	 * 
	 * @param medicalSubsidiesApply
	 *            医疗补贴申请实体 必传
	 * @throws HsException
	 */
	public void applyMedicalSubsidies(MedicalSubsidiesApply medicalSubsidiesApply)
			throws HsException;

	/**
	 * 代他人身故保障金申请
	 * 
	 * @param othersDieSecurityApply
	 *            代他人身故保障金申请实体 必传
	 * @throws HsException
	 */
	public void applyDieSecurity(OthersDieSecurityApply othersDieSecurityApply) throws HsException;

	/**
	 * 消费者查询积分福利申请
	 * 
	 * @param custId
	 *            申请人客户号 必传
	 * @param welfareType
	 *            申请的福利类型 福利类型 0 意外伤害 1 免费医疗 2 他人身故 选传
	 * @param approvalStatus
	 *            状态 0 受理中 1 已受理 2 驳回 选传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<WelfareApplyRecord> listWelfareApply(String custId, Integer welfareType,
			Integer approvalStatus, Page page) throws HsException;

	/**
	 * 消费者查询积分福利申请
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<WelfareApplyRecord> listWelfareApply(ApplyQueryCondition condition, Page page)
			throws HsException;

	/**
	 * 查询积分福利申请详情
	 * 
	 * @param applyWelfareNo
	 *            申请流水号 必传
	 * @return WelfareApplyDetail
	 * 
	 * @throws HsException
	 */
	public WelfareApplyDetail queryWelfareApplyDetail(String applyWelfareNo) throws HsException;

	/**
	 * 检查是否已存在申请
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            申请的福利类型 福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 */
	public void checkExistApplying(String hsResNo, Integer welfareType) throws HsException;

	/**
	 * 查询最后一次申请记录
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            申请的福利类型 福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 * @return
	 * @throws HsException
	 */
	public WelfareApplyDetail queryLastApplyRecord(String hsResNo, Integer welfareType)
			throws HsException;

}
