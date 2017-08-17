/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.dao;

import java.util.List;

import com.gy.hsi.ds.job.beans.bo.FrontPostJob;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
/**
 * 
 * 前置任务后置任务表DAO接口
 * 
 * @Package: com.gy.hsi.ds.job.web.service.dao  
 * @ClassName: FrontPostJobDao 
 * @Description: TODO
 *
 * @author: yangyp  
 * @date: 2015年10月9日 下午12:01:48 
 * @version V3.0
 */
public interface IFrontPostJobDao extends BaseDao<Long,  FrontPostJob> {
    /**
     * 获取执行失败的前置任务列表
     * @param name
     * @param group
     * @return
     */
    public List<FrontPostJob> getFrontPostList(int flag);
    
    /**
     * 获取执行失败的前置任务列表
     * @param name
     * @param group
     * @return
     */
    public List<FrontPostJob> getFrontPostList(String name, String group, int flag);
    
	/**
	 * 获取前置任务是我的记录
	 * 
	 * @param frontServiceName
	 * @param frontServiceGroup
	 * @return
	 */
	public List<FrontPostJob> getFrontIsMeList(String frontServiceName,
			String frontServiceGroup);
    
    /**
     * 删除业务下的前后置任务
     * @param serviceName
     * @param serviceGroup
     * @param postFlag
     */
    public void delete(String serviceName, String serviceGroup, int postFlag);

}
