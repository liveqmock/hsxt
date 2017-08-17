/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.bs.api.task.IBSTaskService;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 公共Service接口实现类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.common
 * @ClassName: CommonService
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月11日 上午11:32:43
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class CommonService implements ICommonService {

	/**
	 * 工单Service
	 */
	@Autowired
	private IBSTaskService iBSTaskService;

	/**
	 * UCService
	 */
	@Autowired
	private IUCAsEntService iuCAsEntService;

	/**
	 * 工单拒绝办理
	 * 
	 * @Description:
	 * @param bizNo
	 *            业务编号
	 * @param exeCustId
	 *            经办人
	 * @throws HsException
	 */
	@Override
	public void workTaskRefuseAccept(String bizNo, String exeCustId) throws HsException
	{
		try
		{
			iBSTaskService.updateTaskStatus(bizNo, TaskStatus.UNACCEPT.getCode(), exeCustId, "");
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "wordTaskHangUp", "调用BS工单拒绝办理失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 工单挂起
	 * 
	 * @Description:
	 * @param bizNo
	 *            业务编号
	 * @param exeCustId
	 *            经办人
	 * @throws HsException
	 */
	@Override
	public void workTaskHangUp(String bizNo, String exeCustId, String remark) throws HsException
	{
		try
		{
			iBSTaskService.updateTaskStatus(bizNo, TaskStatus.HANG_UP.getCode(), exeCustId, remark);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "wordTaskHangUp", "调用BS工单挂起失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业扩展信息
	 * 
	 * @Description:
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntExtendInfo findCompanyExtInfo(String companyCustId) throws HsException
	{
		try
		{
			return iuCAsEntService.searchEntExtInfo(companyCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业扩展信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业一般信息
	 * 
	 * @Description:
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntBaseInfo findCompanyBaseInfo(String companyCustId) throws HsException
	{
		try
		{
			return iuCAsEntService.searchEntBaseInfo(companyCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业一般信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业重要信息
	 * 
	 * @Description:
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntMainInfo findCompanyMainInfo(String companyCustId) throws HsException
	{
		try
		{
			return iuCAsEntService.searchEntMainInfo(companyCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业重要信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业所有信息
	 * 
	 * @Description:
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntAllInfo findCompanyAllInfo(String companyCustId) throws HsException
	{
		try
		{
			return iuCAsEntService.searchEntAllInfo(companyCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业所有信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业状态信息
	 * 
	 * @Description:
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntStatusInfo findCompanyStatusInfo(String companyCustId) throws HsException
	{
		try
		{
			return iuCAsEntService.searchEntStatusInfo(companyCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业状态信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 根据企业资源号获取企业重要信息
	 * 
	 * @Description:
	 * @param companyResNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntMainInfo findCompanyMainInfoByResNo(String companyResNo) throws HsException
	{
		try
		{
			return iuCAsEntService.getMainInfoByResNo(companyResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC根据企业资源号获取企业重要信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业的联系信息
	 * 
	 * @Description:
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntContactInfo findCompanyContactInfo(String companyCustId) throws HsException
	{
		try
		{
			return iuCAsEntService.searchEntContactInfo(companyCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业的联系信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业客户号 ,通过企业互生号
	 * 
	 * @Description:
	 * @param companyResNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public String findCompanyCustIdByEntResNo(String companyResNo) throws HsException
	{
		try
		{
			return iuCAsEntService.findEntCustIdByEntResNo(companyResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCompanyExtInfo", "调用UC查询企业客户号 通过企业互生号失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
