package com.gy.hsxt.access.web.aps.services.closeopen.imp;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.services.closeopen.ICloseOpenEntService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSCloseOpenEntService;
import com.gy.hsxt.bs.api.task.IBSTaskService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntDetail;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntInfo;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;

@Service("closeOpenEntService")
public class CloseOpenEntService extends BaseServiceImpl<CloseOpenEntService>
		implements ICloseOpenEntService {
	@Autowired
	private IBSCloseOpenEntService ibsCloseOpenEntService;// 申请关闭或开启系统的企业

	@Autowired
	private IUCAsEntService iucAsEntService;// 查询平台下所有的企业

	@Autowired
	private IBSTaskService taskService;

	// 开关系统审核查询
	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		CloseOpenEntQueryParam closeOpenEntParam = new CloseOpenEntQueryParam();
		// 企业名称
		String companyName = (String) filterMap.get("companyName");
		if (!StringUtils.isEmpty(companyName)
				&& !companyName.equals("undefined")) {
			closeOpenEntParam.setEntCustName(companyName);
		}

		// 复核类别
		String applyType = (String) filterMap.get("applyType");
		if (!StringUtils.isEmpty(applyType) && !applyType.equals("undefined")) {
			closeOpenEntParam.setApplyType(Integer.parseInt(applyType));
		}
		// 企业类别
		String custType = (String) filterMap.get("custType");
		if (!StringUtils.isEmpty(custType) && !custType.equals("undefined")) {
			closeOpenEntParam.setCustType(Integer.parseInt(custType));
		}
		// 企业互生号
		String companyResNo = (String) filterMap.get("companyResNo");
		if (!StringUtils.isEmpty(companyResNo)
				&& !companyResNo.equals("undefined")) {
			closeOpenEntParam.setEntResNo(companyResNo);
		}
		// 联系人姓名
		String linkman = (String) filterMap.get("linkman");
		if (!StringUtils.isEmpty(linkman) && !linkman.equals("undefined")) {
			closeOpenEntParam.setLinkman(linkman);
		}

		// 操作员客户号
		String custId = (String) filterMap.get("custId");
		if (!StringUtils.isEmpty(custId) && !custId.equals("undefined")) {
			closeOpenEntParam.setOptCustId(custId);
		}

		return ibsCloseOpenEntService
				.queryCloseOpenEnt(closeOpenEntParam, page);
	}

	// 根据条件查询所有企业
	@Override
	public PageData<AsBelongEntInfo> getAllEnt(Map filterMap, Map sortMap,
			Page page) throws HsException {
		AsQueryBelongEntCondition queryBelongEntCondition = new AsQueryBelongEntCondition();
		// 企业名称
		String companyName = (String) filterMap.get("companyName");
		if (!StringUtils.isEmpty(companyName)
				&& !companyName.equals("undefined")) {
			queryBelongEntCondition.setBelongEntName(companyName);
		}

		// 系统开启状态
		String status = (String) filterMap.get("status");
		if (!StringUtils.isEmpty(status) && !status.equals("undefined")) {
			queryBelongEntCondition.setIsClosedEnt(Integer.parseInt(status));
		}
		String noMcs = null;
		// 企业类别
		String custType = (String) filterMap.get("custType");
		if (!StringUtils.isEmpty(custType) && !custType.equals("undefined")) {
			queryBelongEntCondition.setBlongEntCustType(Integer
					.parseInt(custType));
			noMcs = "";
		}else{
			noMcs = "1";
		}
		// 企业互生号
		String companyResNo = (String) filterMap.get("companyResNo");
		if (!StringUtils.isEmpty(companyResNo)
				&& !companyResNo.equals("undefined")) {
			queryBelongEntCondition.setBelongEntResNo(companyResNo);
		}
		// 平台互生号
		String pointNo = (String) filterMap.get("pointNo");
		if (!StringUtils.isEmpty(pointNo) && !pointNo.equals("undefined")) {
			queryBelongEntCondition.setEntResNo(pointNo);
		}
		// 联系人姓名
		String linkman = (String) filterMap.get("linkman");
		if (!StringUtils.isEmpty(linkman) && !linkman.equals("undefined")) {
			queryBelongEntCondition.setBelongEntContacts(linkman);
		}
		queryBelongEntCondition.setNoMcs(noMcs);// 不查询管理公司
		queryBelongEntCondition.setNoCancel("1");//过滤掉已注销的企业
		PageData<AsBelongEntInfo> data = iucAsEntService.listBelongEntInfo(
				queryBelongEntCondition, page);
		return data;
	}

	/**
	 * 申请关闭系统
	 * 
	 * @param closeOpenEnt
	 *            关闭系统信息
	 * @throws HsException
	 */
	@Override
	public void closeEnt(CloseOpenEnt closeOpenEnt) throws HsException {
		ibsCloseOpenEntService.closeEnt(closeOpenEnt);

	}

	/**
	 * 申请开启系统
	 * 
	 * @param closeOpenEnt
	 *            开启系统信息
	 * @throws HsException
	 */
	@Override
	public void openEnt(CloseOpenEnt closeOpenEnt) throws HsException {
		ibsCloseOpenEntService.openEnt(closeOpenEnt);

	}

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
	@Override
	public PageData<CloseOpenEnt> queryCloseOpenEnt4Appr(Map filterMap,
			Map sortMap, Page page) throws HsException {
		// 查询条件类
		CloseOpenEntQueryParam closeOpenEntParam = this.createCOEP(filterMap);
		// 返回分页结果
		return ibsCloseOpenEntService.queryCloseOpenEnt4Appr(closeOpenEntParam,
				page);
	}

	/**
	 * 创建查询实体类
	 * 
	 * @param filterMap
	 * @return
	 */
	CloseOpenEntQueryParam createCOEP(Map filterMap) {
		/**
		 * by wangjg 2016-03-22
		 * 作用：此处删除undefined验证，例如企业名称是”undefined“时，查询条件就会失效。前端页面做控制避免空值即可
		 */
		CloseOpenEntQueryParam closeOpenEntParam = new CloseOpenEntQueryParam();
		// 企业名称
		String companyName = (String) filterMap.get("companyName");
		if (!StringUtils.isEmpty(companyName)) {
			closeOpenEntParam.setEntCustName(companyName);
		}

		// 复核类别
		String applyType = (String) filterMap.get("applyType");
		if (!StringUtils.isEmpty(applyType) && !applyType.equals("undefined")) {
			closeOpenEntParam.setApplyType(Integer.parseInt(applyType));
		}
		// 企业类别
		String custType = (String) filterMap.get("custType");
		if (!StringUtils.isEmpty(custType) && !applyType.equals("undefined")) {
			closeOpenEntParam.setCustType(Integer.parseInt(custType));
		}
		// 互生号
		String companyResNo = (String) filterMap.get("companyResNo");
		if (!StringUtils.isEmpty(companyResNo)) {
			closeOpenEntParam.setEntResNo(companyResNo);
		}
		// 联系人姓名
		String linkman = (String) filterMap.get("linkman");
		if (!StringUtils.isEmpty(linkman)) {
			closeOpenEntParam.setLinkman(linkman);
		}

		// 操作员客户号
		String custId = (String) filterMap.get("custId");
		if (!StringUtils.isEmpty(custId)) {
			closeOpenEntParam.setOptCustId(custId);
		}

		return closeOpenEntParam;
	}

	/**
	 * 查询关闭、开启系统详情
	 * 
	 * @param applyId
	 *            申请编号
	 * @return 关闭、开启系统详情
	 */
	@Override
	public CloseOpenEntDetail queryCloseOpenEntDetail(String applyId) {

		return ibsCloseOpenEntService.queryCloseOpenEntDetail(applyId);
	}

	/**
	 * 审批申请关闭、开启系统
	 * 
	 * @param apprParam
	 *            审批参数
	 * @throws HsException
	 */
	@Override
	public void apprCloseOpenEnt(ApprParam apprParam) throws HsException {
		ibsCloseOpenEntService.apprCloseOpenEnt(apprParam);

	}

	/**
	 * 查询企业上一次关闭系统信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return 返回企业上一次关闭系统信息
	 */
	@Override
	public CloseOpenEntInfo queryLastCloseOpenEntInfo(String entCustId,
			Integer applyType) {
		return ibsCloseOpenEntService.queryLastCloseOpenEntInfo(entCustId,
				applyType);
	}

	/**
	 * 更新工单
	 * 
	 * @param bizNo
	 *            业务编号
	 * @param taskStatus
	 *            工单状态
	 * @param exeCustId
	 *            处理人
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.closeopen.ICloseOpenEntService#updateTask(java.lang.String,
	 *      java.lang.Integer, java.lang.String)
	 */
	@Override
	public void updateTask(String bizNo, Integer taskStatus, String exeCustId)
			throws HsException {
		taskService.updateTaskStatus(bizNo, taskStatus, exeCustId);
	}
}
