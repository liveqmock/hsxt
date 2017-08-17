/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.contractManage.imp;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.contractManage.IContractTempManageService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.apply.IBSContractService;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 合同模板管理Service实现类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.contractManage.imp
 * @ClassName: ContractTempManageService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午10:22:15
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class ContractTempManageService extends BaseServiceImpl<ContractTempManageService> implements
		IContractTempManageService {

	/**
	 * BS合同管理Server
	 */
	@Autowired
	private IBSContractService bSContractService;

	/**
	 * 分页查询合同模板
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<Templet> queryContractTempByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page)
	{
		try
		{
			TempletQuery tq = new TempletQuery();
			String tempName = (String) filterMap.get("templetName");
			tempName = StringUtils.isNotBlank(tempName) ? URLDecoder.decode(tempName, "UTF-8") : tempName;
			tq.setTempletName(tempName);
			
			Object custTypeObj = filterMap.get("custType");
			if(custTypeObj!=null){
				tq.setCustType(Integer.parseInt((String)custTypeObj));
			}
			
			Object statusObj = filterMap.get("status");
			if(statusObj!=null){
				tq.setApprStatus(Integer.parseInt((String)statusObj));
			}
			
			//String tempName = (String) filterMap.get("tempName");
			//tempName = StringUtils.isNotBlank(tempName) ? URLDecoder.decode(tempName, "UTF-8") : tempName;
			//Integer custType = (Integer) filterMap.get("custType");
			//Integer status = (Integer) filterMap.get("status");

			return bSContractService.queryTempletList(tq, page);

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractTempByPage", "分页查询合同模板失败", ex);
		}
		return null;
	}

	/**
	 * 根据ID查询合同模板
	 * 
	 * @Description:
	 * @param templetId
	 * @return
	 */
	@Override
	public Templet queryContractTempById(String templetId)
	{
		try
		{
			return bSContractService.queryTempletById(templetId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractTempById", "根据ID查询合同模板失败", ex);
		}
		return null;
	}

	/**
	 * 创建合同模板
	 * 
	 * @Description:
	 * @param templet
	 * @throws HsException
	 */
	@Override
	public void addContractTemp(Templet bean) throws HsException
	{
		try
		{
			vaildContractTempParam(bean);
			bSContractService.createTemplet(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12661 保存合同模板失败
			throw ex;
		} catch (Exception ex)
		{
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 验证模板参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:31:45
	 * @param bean
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildContractTempParam(Templet bean) throws HsException
	{
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] { bean.getCustType(), ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getCode(),
						ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getDesc() }, new Object[] { bean.getTempletName(),
						ASRespCode.ASP_DOC_TEMPNAME_INVALID.getCode(), ASRespCode.ASP_DOC_TEMPNAME_INVALID.getDesc() },
				new Object[] { bean.getTempletContent(), ASRespCode.ASP_DOC_TEMPCONTENT_INVALID.getCode(),
						ASRespCode.ASP_DOC_TEMPCONTENT_INVALID.getDesc() });
	}

	/**
	 * 修改合同模板
	 * 
	 * @Description:
	 * @param templet
	 * @throws HsException
	 */
	@Override
	public void modifyContractTemp(Templet bean) throws HsException
	{
		try
		{
			vaildContractTempParam(bean);
			bSContractService.modifyTemplet(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12661 保存合同模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "modifyContractTemp", "修改合同模板失败," , ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 启用合同模板
	 * 
	 * @Description:
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 */
	@Override
	public void enableContractTemp(String templetId, String custId) throws HsException
	{
		try
		{
			bSContractService.enableTemplet(templetId, custId);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12663 启用合同模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog
					.error(this.getClass().getName(), "enableContractTemp," + templetId + "," + custId, "启用合同模板失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 停用合同模板
	 * 
	 * @Description:
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 */
	@Override
	public void stopContractTemp(String templetId, String custId) throws HsException
	{
		try
		{
			bSContractService.disableTemplet(templetId, custId);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12664 停用合同模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "stopContractTemp," + templetId + "," + custId, "停用合同模板失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 分页查询合同模板审批
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<Templet> queryContractTempApprByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page)
	{
		try
		{
			TempletQuery tq = new TempletQuery();
			String tempName = (String) filterMap.get("templetName");
			tempName = StringUtils.isNotBlank(tempName) ? URLDecoder.decode(tempName, "UTF-8") : tempName;
			tq.setTempletName(tempName);
			Object custTypeObj = filterMap.get("custType");
			if(custTypeObj!=null){
				tq.setCustType(Integer.parseInt((String)custTypeObj));
			}
			
			Object statusObj = filterMap.get("status");
			if(statusObj!=null){
				tq.setApprStatus(Integer.parseInt((String)statusObj));
			}
			
			String optCustId = (String) filterMap.get("optCustId");

			tq.setOptCustId(optCustId);
			return bSContractService.queryTemplet4Appr(tq, page);

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractTempApprByPage", "分页查询合同模板审批失败", ex);
		}
		return null;
	}

	/**
	 * 审批合同模板
	 * 
	 * @Description:
	 * @param appr
	 * @throws HsException
	 */
	@Override
	public void contractTempAppr(TemplateAppr bean) throws HsException
	{
		try
		{
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getTempletId(), ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
							ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc()},
					new Object[] { bean.getApprStatus(), ASRespCode.ASP_CERTIFICATENO_APPRSTATUS_INVALID.getCode(),
							ASRespCode.ASP_CERTIFICATENO_APPRSTATUS_INVALID.getDesc()},
					new Object[] { bean.getApprResult(), ASRespCode.ASP_DOC_TEMP_APPRSTATUS_INVALID.getCode(),
							ASRespCode.ASP_DOC_TEMP_APPRSTATUS_INVALID.getDesc()},
					new Object[] { bean.getApprOperator(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(),
							ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc()});
			bSContractService.apprTemplet(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12662 合同模板审批失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "contractTempAppr,", "审批合同模板失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}
}
