package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.apply.IBSContractService;
import com.gy.hsxt.bs.bean.apply.ContractBaseInfo;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : ContractManageService.java
 * @description   : 合同管理接口实现
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class ContractManageService extends BaseServiceImpl implements IContractManageService {
	
    @Autowired
    private IBSContractService bsService;//企业申报服务类

	@Override
	public PageData<ContractBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		String entResNo = (String) filterMap.get("pointNo");//服务公司互生号
		String entName = (String) filterMap.get("entCustName");//企业名称
		String linkman = (String) filterMap.get("linkman");//联系人
		String resNo = (String) filterMap.get("entResNo");//拟用互生号
		return this.bsService.serviceQueryContract(entResNo, resNo, entName, linkman, page);
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
	public ContractContent getContractContentByPrint(String contractNo) throws HsException 
	{
		try
		{
			return bsService.printContract(contractNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getContractContentByPrint", "查询合同打印内容失败", ex);
		}
		return null;
	}

	/**
	 * 合同打印
	 * @param contractNo
	 * @return
	 * @throws HsException
	 */
	public ContractContent printContract(String contractNo) throws HsException {
		
		return bsService.printContract(contractNo);
	}
}
