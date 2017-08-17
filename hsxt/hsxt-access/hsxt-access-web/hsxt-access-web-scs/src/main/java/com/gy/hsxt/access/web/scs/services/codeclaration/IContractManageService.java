package com.gy.hsxt.access.web.scs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : IContractManageService.java
 * @description   : 合同管理接口
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IContractManageService extends IBaseService{
	/**
	 * 查询打印合同内容
	 * 
	 * @param contractNo
	 * @return
	 * @return : ContractContent
	 * @version V3.0.0
	 */
	public ContractContent getContractContentByPrint(String contractNo);
	/**
	 * 合同打印
	 * @param contractNo
	 * @return
	 * @throws HsException
	 */
	public ContractContent printContract(String contractNo) throws HsException;
}
