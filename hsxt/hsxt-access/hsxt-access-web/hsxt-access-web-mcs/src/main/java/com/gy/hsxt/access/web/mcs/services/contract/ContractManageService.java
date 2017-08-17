package com.gy.hsxt.access.web.mcs.services.contract;

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
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.contract
 * @className     : ContractManageService.java
 * @description   : 合同管理接口实现
 * @author        : chenli
 * @createDate    : 2016-02-19
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
		String entResNo = (String) filterMap.get("pointNo");//管理公司互生号
		String entName = (String) filterMap.get("entCustName");//企业名称
		String linkman = (String) filterMap.get("linkman");//联系人
		String resNo = (String) filterMap.get("companyResNo");//拟用互生号
		return this.bsService.managerQueryContract(entResNo, resNo, entName, linkman, page);
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
	public ContractContent queryContractContentBySeal(String contractNo,boolean realTime) throws HsException{
			return bsService.queryContractContent4Seal(contractNo,realTime);
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
		return bsService.printContract(contractNo);
	}
	
}
