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

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.contractManage.IContractManageService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.apply.IBSContractService;
import com.gy.hsxt.bs.bean.apply.ContractBaseInfo;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.bs.bean.apply.ContractSendHis;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 合同管理Service接口实现类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.contractManage.imp
 * @ClassName: ContractManageService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月21日 下午8:10:32
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class ContractManageService extends BaseServiceImpl<ContractManageService> implements IContractManageService {

	/**
	 * BS合同管理Server
	 */
	@Autowired
	private IBSContractService bSContractService;

	/**
	 * 分页查询合同
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<ContractBaseInfo> queryContarctByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page)
	{
		try
		{
			String entResNo = (String) filterMap.get("entResNo");
			String entCustName = (String) filterMap.get("entCustName");
			entCustName = StringUtils.isNotBlank(entCustName) ? URLDecoder.decode(entCustName, "UTF-8") : entCustName;
			String linkMan = (String) filterMap.get("linkMan");
			linkMan = StringUtils.isNotBlank(linkMan) ? URLDecoder.decode(linkMan, "UTF-8") : linkMan;
			return bSContractService.platQueryContract(entResNo, entCustName, linkMan, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContarctByPage", "分页查询打印合同失败", ex);
		}
		return null;
	}

	/**
	 * 查询合同打印内容
	 * 
	 * @Description:
	 * @param contractNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public ContractContent getContractContentByPrint(String contractNo)
	{
		try
		{
			return bSContractService.printContract(contractNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getContractContentByPrint", "查询合同打印内容失败", ex);
		}
		return null;
	}

	/**
	 * 分页查询盖章合同
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<ContractBaseInfo> querySealContarctByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page)
	{
		try
		{
			String entResNo = (String) filterMap.get("entResNo");
			String entCustName = (String) filterMap.get("entCustName");
			entCustName = StringUtils.isNotBlank(entCustName) ? URLDecoder.decode(entCustName, "UTF-8") : entCustName;
			Integer status = null;
			String statusObj = (String)filterMap.get("status");
			if( statusObj != null && !statusObj.equals("")){
				status = Integer.parseInt( statusObj);
			}
			return bSContractService.queryContract4Seal(entResNo, entCustName, status, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySealContarctByPage", "分页查询盖章合同失败", ex);
		}
		return null;
	}

	/**
	 * 查询盖章合同内容
	 * 
	 * @Description:
	 * @param contractNo
	 * @param realTime
	 * @return
	 * @throws HsException
	 */
	@Override
	public ContractContent queryContractContentBySeal(String contractNo,boolean realTime)
	{
		try
		{
			return bSContractService.queryContractContent4Seal(contractNo,realTime);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractContentBySeal", "查询盖章合同内容失败", ex);
			throw ex;
		}
	}

	/**
	 * 查询预览合同内容
	 * 
	 * @Description:
	 * @param contractNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public ContractContent queryContractContentByView(String contractNo)
	{
		try
		{
			return bSContractService.queryContractContent4View(contractNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractContentByView", "查询预览合同内容失败", ex);
		}
		return null;
	}

	/**
	 * 分页查询合同发放
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<ContractBaseInfo> queryContractGiveOutByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page)
	{
		try
		{
			String entResNo = (String) filterMap.get("entResNo");
			String entCustName = (String) filterMap.get("entCustName");
			entCustName = StringUtils.isNotBlank(entCustName) ? URLDecoder.decode(entCustName, "UTF-8") : entCustName;
			Object sendStatusObj = filterMap.get("sendStatus");
			Boolean sendStatus = null;
			if( sendStatusObj != null ){
				sendStatus = Boolean.parseBoolean((String)sendStatusObj);
			}
			return bSContractService.queryContract4Send(entResNo, entCustName, sendStatus, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractGiveOutByPage", "分页查询合同发放失败", ex);
		}
		return null;
	}

	/**
	 * 发放合同
	 * 
	 * @Description:
	 * @param giveOut
	 * @throws HsException
	 */
	@Override
	public void contractGiveOut(ContractSendHis bean) throws HsException
	{
		try
		{
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getUniqueNo(), ASRespCode.ASP_DOC_UNIQUENO_INVALID.getCode(),
							ASRespCode.ASP_DOC_UNIQUENO_INVALID.getDesc() },
					new Object[] { bean.getOperator(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(),
							ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			bSContractService.sendContract(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12666 发放合同失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "contractGiveOut,", "发放合同失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 查询合同发放历史
	 * 
	 * @Description:
	 * @param contractNo
	 * @return
	 */
	@Override
	public PageData<ContractSendHis>  queryContractGiveOutRecode(Map<String, Object> filterMap, Map<String, Object> sortMap, Page page)
			throws HsException{
		try
		{
			String contractNo = (String) filterMap.get("contractNo");
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { contractNo, ASRespCode.ASP_CONTRACTNO_INVALID.getCode(),
					ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
			return bSContractService.queryContractSendHis(contractNo,page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryContractGiveOutRecode", "查询合同发放历史失败", ex);
		}
		return null;
	}
	/**
	 * 合同盖章
	 * @param contractNo
	 * @throws HsException
	 */
	public ContractContent sealContract(String contractNo) throws HsException {
		RequestUtil.verifyParamsIsNotEmpty(new Object[] { contractNo, ASRespCode.ASP_CONTRACTNO_INVALID.getCode(),
				ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
		return bSContractService.sealContract(contractNo);
	}
	/**
	 * 合同打印
	 * @param contractNo
	 * @return
	 * @throws HsException
	 */
	public ContractContent printContract(String contractNo) throws HsException {
		RequestUtil.verifyParamsIsNotEmpty(new Object[] { contractNo, ASRespCode.ASP_CONTRACTNO_INVALID.getCode(),
				ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
		return bSContractService.printContract(contractNo);
	}
}
