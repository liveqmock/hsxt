/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.IAfterPaidToolMakeService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.AfterKeepConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * @author weiyq
 *
 */
@Service
public class AfterPaidToolMakeServiceImpl extends BaseServiceImpl implements IAfterPaidToolMakeService{
	
	@Autowired
	private IBSToolAfterService bsToolAfterService;
	
	@Autowired
	private IUCAsRoleService ucAsRoleService;

	@Autowired
	private UserCenterService userCenterService;
	
	@Override
    public PageData<ToolConfigPage> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		
		String hsResNo = (String)filterMap.get("companyResNo");
		String hsCustName = (String)filterMap.get("companyCustName");
		
		BaseParam bp = new BaseParam();
		String platCustName = (String) filterMap.get("custName");
		if(StringUtils.isNotBlank(platCustName)&&!"0000".equals(platCustName))
		{
			bp.setRoleIds(userCenterService.getRoleIds((String) filterMap.get("custId")));
		}
		bp.setHsResNo(hsResNo);
		bp.setHsCustName(hsCustName);
		
		try
		{
			return bsToolAfterService.queryToolAfterConfigPage(bp,page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS查询分页配置单失败", ex);
			return null;
		}
    }
	
	/**
	 * 查询售后配置单详情
	 * @param confNo
	 * @return
	 * @throws HsException
	 */
	public List<AfterServiceDetail> queryAfterConfigDetail(String confNo)throws HsException{
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] {confNo, ASRespCode.APS_TOOL_CONFNO_INVALID.getCode(), ASRespCode.APS_TOOL_CONFNO_INVALID.getDesc()}
				);
		try
		{
			return bsToolAfterService.queryAfterConfigDetail(confNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryAfterConfigDetail", "调用BS查询售后配置单详情失败", ex);
			return null;
		}
	}
	/**
	 * 配置刷卡器关联
	 * @param asc
	 * @throws HsException
	 */
	public void configMcrRelationAfter(AfterServiceConfig asc)throws HsException{
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] {asc.getEntCustId(), ASRespCode.APS_TOOL_ENTCUSTID_INVALID.getCode(), ASRespCode.APS_TOOL_ENTCUSTID_INVALID.getDesc()},
				new Object[] {asc.getAfterOrderNo(), ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getCode(), ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getDesc()},
				new Object[] {asc.getConfNo(), ASRespCode.APS_TOOL_CONFNO_INVALID.getCode(), ASRespCode.APS_TOOL_CONFNO_INVALID.getDesc()},
				new Object[] {asc.getDeviceSeqNo(), ASRespCode.APS_TOOL_DEVICESEQNO_INVALID.getCode(), ASRespCode.APS_TOOL_DEVICESEQNO_INVALID.getDesc()},
				new Object[] {asc.getTerminalNo(), ASRespCode.APS_TOOL_TERMINALNO_INVALID.getCode(), ASRespCode.APS_TOOL_TERMINALNO_INVALID.getDesc()},
				new Object[] {asc.getNewDeviceSeqNo(), ASRespCode.APS_TOOL_NEWDEVICESEQNO_INVALID.getCode(), ASRespCode.APS_TOOL_NEWDEVICESEQNO_INVALID.getDesc()},
				new Object[] {asc.getOperNo(), ASRespCode.APS_TOOL_OPERNO_INVALID.getCode(), ASRespCode.APS_TOOL_OPERNO_INVALID.getDesc()}
				);
		try
		{
			bsToolAfterService.configMcrRelationAfter(asc);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "configMcrRelationAfter", "配置刷卡器关联失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	/**
	 * 配置刷卡器保持关联
	 * @param asc
	 * @throws HsException
	 */
	public void keepDeviceRelationAfter(AfterKeepConfig asc)throws HsException{
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] {asc.getEntCustId(), ASRespCode.APS_TOOL_ENTCUSTID_INVALID.getCode(), ASRespCode.APS_TOOL_ENTCUSTID_INVALID.getDesc()},
				new Object[] {asc.getAfterOrderNo(), ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getCode(), ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getDesc()},
				new Object[] {asc.getConfNo(), ASRespCode.APS_TOOL_CONFNO_INVALID.getCode(), ASRespCode.APS_TOOL_CONFNO_INVALID.getDesc()},
				new Object[] {asc.getDeviceSeqNo(), ASRespCode.APS_TOOL_DEVICESEQNO_INVALID.getCode(), ASRespCode.APS_TOOL_DEVICESEQNO_INVALID.getDesc()},
				new Object[] {asc.getOperNo(), ASRespCode.APS_TOOL_OPERNO_INVALID.getCode(), ASRespCode.APS_TOOL_OPERNO_INVALID.getDesc()}
				);
		try
		{
			bsToolAfterService.keepDeviceRelationAfter(asc);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "keepDeviceRelationAfter", "配置刷卡器保持关联", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
