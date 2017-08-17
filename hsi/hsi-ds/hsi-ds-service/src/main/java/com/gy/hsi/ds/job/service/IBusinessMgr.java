/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.service;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.beans.vo.BusinessVo;
import com.gy.hsi.ds.job.controller.form.BusinessListForm;
import com.gy.hsi.ds.job.controller.form.BusinessNewForm;

/**
 * 业务配置业务处理接口
 * 
 * @Package: com.gy.hsi.ds.job.web.service.business.service
 * @ClassName: BusinessMgr
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月12日 下午12:14:46
 * @version V3.0
 */
public interface IBusinessMgr {
	/**
	 * 创建一个业务
	 * 
	 * @param job
	 */
	public void create(BusinessNewForm businessNewForm);

	/**
	 * 获取业务
	 * 
	 * @param businessNewForm
	 * @return
	 */
	public JobStatus getByNameAndGroup(BusinessNewForm businessNewForm);

	/**
	 * 获取业务
	 * 
	 * @param serviceName
	 * @param group
	 * @return
	 */
	public JobStatus getByNameAndGroup(String serviceName, String group);

	/**
	 * 根据业务名获取业务
	 * 
	 * @param businessNewForm
	 * @return
	 */
	public JobStatus getByName(String serviceName);

	/**
	 * 获得业务列表
	 * 
	 * @param businessListForm
	 * @return
	 */
	public DaoPageResult<BusinessVo> getBusinessList(
			BusinessListForm businessListForm);

	/**
	 * 根据id删除业务
	 * 
	 * @param busId
	 */
	public void delete(long busId);

	/**
	 * 更新业务信息
	 * 
	 * @param businessListForm
	 * @return
	 */
	public String update(long busId, String servicePara, String desc,
			String version);

	/**
	 * 根据id获取业务信息
	 * 
	 * @param busId
	 * @return
	 */
	public BusinessVo getBusVo(long busId);

	/**
	 * 根据id获取业务信息
	 * 
	 * @param busId
	 * @return
	 */
	public JobStatus getBusById(long busId);

	/**
	 * 设置后置业务
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @param postId
	 * @return
	 */
	public String setPostBus(String serviceName, String serviceGroup,
			long postId);

	/**
	 * 设置前置任务
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @param frontIds
	 * @return
	 */
	public String setFrontBus(String serviceName, String serviceGroup,
			String frontIds);

	/**
	 * 单独执行一次业务
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @param tempArgs
	 * @return
	 */
	public String executeOnce(String serviceName, String serviceGroup,
			String tempArgs);
}
