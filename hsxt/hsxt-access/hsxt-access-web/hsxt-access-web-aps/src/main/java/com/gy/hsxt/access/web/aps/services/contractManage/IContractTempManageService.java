/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.contractManage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 合同模板管理Service接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.contractManage
 * @ClassName: IContractTempManageService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午10:21:12
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface IContractTempManageService extends IBaseService {

	/**
	 * 分页查询合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:25:28
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<Templet>
	 * @version V3.0.0
	 */
	public PageData<Templet> queryContractTempByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page);

	/**
	 * 查询合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:26:36
	 * @param templetId
	 * @return
	 * @return : Templet
	 * @version V3.0.0
	 */
	public Templet queryContractTempById(String templetId);

	/**
	 * 新增合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:27:49
	 * @param templet
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addContractTemp(Templet bean) throws HsException;

	/**
	 * 修改合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:28:47
	 * @param templet
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyContractTemp(Templet bean) throws HsException;

	/**
	 * 启用合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:36:58
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void enableContractTemp(String templetId, String custId) throws HsException;

	/**
	 * 停用合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:37:21
	 * @param custId
	 * @param operator
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void stopContractTemp(String templetId, String custId) throws HsException;

	/**
	 * 分页查询合同模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:38:10
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<Templet>
	 * @version V3.0.0
	 */
	public PageData<Templet> queryContractTempApprByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page);

	/**
	 * 审批合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午10:41:48
	 * @param appr
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void contractTempAppr(TemplateAppr bean) throws HsException;
}
