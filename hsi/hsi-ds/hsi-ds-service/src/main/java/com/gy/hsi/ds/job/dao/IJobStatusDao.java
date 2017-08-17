/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.dao;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.controller.form.BusinessListForm;
/**
 * 
 * 调度中心管理的所有业务表DAO接口
 * @Package: com.gy.hsi.ds.job.web.service.dao  
 * @ClassName: FrontPostJobDao 
 * @Description: TODO
 *
 * @author: yangyp  
 * @date: 2015年10月9日 下午12:01:48 
 * @version V3.0
 */
public interface IJobStatusDao extends BaseDao<Long,  JobStatus> {
    /**
     * 根据业务名和业务组获取业务信息
     * @param name
     * @param group
     * @return JobStatus
     */
    public JobStatus getByNameAndGroup(String name,String group);
    
    /**
     * 根据业务名获取业务信息
     * @param name
     * @param group
     * @return JobStatus
     */
    public JobStatus getByName(String name);
    
	/**
	 * 更新上报的状态
	 * 
	 * @param name
	 * @param group
	 * @param state
	 * @param lastExecuteId
	 */
	public void updateStatus(String name, String group, int state,
			String lastExecuteId);

	/**
	 * 本地更新状态
	 * 
	 * @param name
	 * @param group
	 * @param state
	 * @param lastExecuteId
	 */
	public void updateStatusBySelf(String name, String group, int state,
			String lastExecuteId);
    
    /**
     * 更新任务是否存在标识
     * 
     * @param name
     * @param group
     */
    public void updateTaskFlag(String name, String group,int flag);
    
    /**
     * 获取业务列表，有分页
     * @param businessListForm
     * @return
     */
    public DaoPageResult<JobStatus> getBusinessList(BusinessListForm businessListForm);
    
    
    /**
     * 修改业务列表
     * @param businessListForm
     */
    public void update(long busId, String servicePara,String desc,String version);

}
