/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.dao.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DaoUtils;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPage;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Modify;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.AppenderParam;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.LikeParam;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.controller.form.BusinessListForm;
import com.gy.hsi.ds.job.dao.IJobStatusDao;
/**
 * 
 * 调度中心管理的所有业务表DAO实现类
 * @Package: com.gy.hsi.ds.job.web.service.dao.impl  
 * @ClassName: FrontPostJobDaoImpl 
 * @Description: TODO
 *
 * @author: yangyp  
 * @date: 2015年10月9日 下午12:01:41 
 * @version V3.0
 */
@Service
public class JobStatusDaoImpl extends AbstractDao<Long, JobStatus> implements
		Serializable, IJobStatusDao {

	private static final long serialVersionUID = -5049304146685721722L;

	@Override
	public JobStatus getByNameAndGroup(String name, String group) {
		try {
			return findOne(new Match(Columns.SERVICE_NAME, name), new Match(
					Columns.SERVICE_GROUP, group));
		} catch (Exception e) {
		}

		return null;
	}
    
    @Override
    public JobStatus getByName(String name) {
        return findOne(new Match(Columns.SERVICE_NAME, name));
    }
    
	@Override
	public void updateStatusBySelf(String name, String group, int state,
			String lastExecuteId) {
		List<Modify> modifyList = new ArrayList<Modify>();
		
		// 初始化状态(-1:未执行, -2:未上报, 任务启动时上报)
		modifyList.add(modify(Columns.SERVICE_STATE, state));
		modifyList.add(modify(Columns.STATE_UPDATE_TIME, new Date()));		
		modifyList.add(modify(Columns.LAST_EXECUTE_ID, lastExecuteId));

		update(modifyList, match(Columns.SERVICE_NAME, name),
				match(Columns.SERVICE_GROUP, group));
	}

	@Override
	public void updateStatus(String name, String group, int state,
			String lastExecuteId) {
		List<Modify> modifyList = new ArrayList<Modify>();
		modifyList.add(modify(Columns.SERVICE_STATE, state)); // 其他子系统上报的状态
		modifyList.add(modify(Columns.STATE_UPDATE_TIME, new Date()));

		// 注释掉..., marked by zhangysh, date: 2016-05-16 18:39
		/*
		 * StringBuilder buf = new StringBuilder(); buf.append(" and (");
		 * buf.append(" (LAST_EXECUTE_ID is null) ");
		 * buf.append(" or (LAST_EXECUTE_ID != '"
		 * ).append(lastExecuteId).append("') ");
		 * buf.append(" or (LAST_EXECUTE_ID = '").append(lastExecuteId)
		 * .append("' and SERVICE_STATE != 0 and SERVICE_STATE != 1) ");
		 * buf.append(" )");
		 */

		StringBuilder buf = new StringBuilder();
		buf.append(" and (");
		buf.append("LAST_EXECUTE_ID = '").append(lastExecuteId).append("'")
				.append(" and SERVICE_STATE != 0 and SERVICE_STATE != 1");
		buf.append(")");

		update(modifyList, match(Columns.SERVICE_NAME, name),
				match(Columns.SERVICE_GROUP, group),
				match(null, new AppenderParam(buf.toString())));
	}

    @Override
    public DaoPageResult<JobStatus> getBusinessList(BusinessListForm businessListForm) {
        Page page=businessListForm.getPage();
        DaoPage daoPage = DaoUtils.daoPageAdapter(page);
        List<Match> matchs = new ArrayList<Match>();
        
        String serviceName=businessListForm.getServiceName();
        if(StringUtils.isNotEmpty(serviceName)){
            matchs.add(new Match(Columns.SERVICE_NAME, new LikeParam(serviceName)));
        }
        
        String serviceGroup=businessListForm.getServiceGroup();
        if(StringUtils.isNotEmpty(serviceGroup)){
            matchs.add(new Match(Columns.SERVICE_GROUP, new LikeParam(serviceGroup)));
        }
        
        String serviceStatus=businessListForm.getServiceStatus();
        if(StringUtils.isNotEmpty(serviceStatus)){
            matchs.add(new Match(Columns.SERVICE_STATE, serviceStatus));
        }
        
        String hasTaskFlag=businessListForm.getHasTaskFlag();
        if(StringUtils.isNotEmpty(hasTaskFlag)){
            matchs.add(new Match(Columns.HAS_TASK_FLAG, hasTaskFlag));
        }

        return page2(matchs, daoPage);
    }

    @Override
    public void update(long busId, String servicePara,String desc,String version) {
        List<Modify> modifyList = new ArrayList<Modify>();
        modifyList.add(modify(Columns.SERVICE_PARA, servicePara));
        modifyList.add(modify(Columns.DESCRIPTION, desc));
        modifyList.add(modify(Columns.DUBBO_PROVIDER_VERSION, version));

        update(modifyList, match(Columns.JOB_STATES_ID, busId));
        
    }

    @Override
    public void updateTaskFlag(String name, String group, int flag) {
        List<Modify> modifyList = new ArrayList<Modify>();
        modifyList.add(modify(Columns.HAS_TASK_FLAG, flag));
        update(modifyList, match(Columns.SERVICE_NAME, name), match(Columns.SERVICE_GROUP, group));    
    }
}
