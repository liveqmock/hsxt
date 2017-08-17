/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.contractManage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.ContractBaseInfo;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.bs.bean.apply.ContractSendHis;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 合同管理Service接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.contractManage
 * @ClassName: IContractManageService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月21日 下午8:09:34
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface IContractManageService extends IBaseService {

	/**
	 * 分页查询合同
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午8:22:05
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<ContractBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<ContractBaseInfo> queryContarctByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page);

	/**
	 * 查询打印合同内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午8:44:37
	 * @param contractNo
	 * @return
	 * @return : ContractContent
	 * @version V3.0.0
	 */
	public ContractContent getContractContentByPrint(String contractNo);

	/**
	 * 分页查询合同盖章
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午8:50:09
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<ContractBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<ContractBaseInfo> querySealContarctByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);

	/**
	 * 查询盖章合同内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午8:54:07
	 * @param contractNo
	 * @param realTime
	 * @return
	 * @return : ContractContent
	 * @version V3.0.0
	 */
	public ContractContent queryContractContentBySeal(String contractNo,boolean realTime);

	/**
	 * 查询合同预览内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午8:54:21
	 * @param contractNo
	 * @return
	 * @return : ContractContent
	 * @version V3.0.0
	 */
	public ContractContent queryContractContentByView(String contractNo);

	/**
	 * 分页查询合同发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午8:55:54
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<ContractBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<ContractBaseInfo> queryContractGiveOutByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);

	/**
	 * 合同发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午9:00:20
	 * @param giveOut
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void contractGiveOut(ContractSendHis bean) throws HsException;

	/**
	 * 查询合同发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午9:00:16
	 * @param contractNo
	 * @return
	 * @return : Map<String,Object>
	 * @version V3.0.0
	 */
	public PageData<ContractSendHis>  queryContractGiveOutRecode(Map<String, Object> filterMap, Map<String, Object> sortMap, Page page)
			throws HsException;
	/**
	 * 合同盖章
	 * @param contractNo
	 * @throws HsException
	 */
	public ContractContent sealContract(String contractNo) throws HsException;
	/**
	 * 合同打印
	 * @param contractNo
	 * @return
	 * @throws HsException
	 */
	public ContractContent printContract(String contractNo) throws HsException;
}
