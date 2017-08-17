/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.JobConstant;
import com.gy.hsi.ds.common.constant.JobConstant.ServiceState;
import com.gy.hsi.ds.common.constant.JobConstant.TaskFlag;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DataTransfer;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.ServiceUtil;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.beans.TmpJobStatus;
import com.gy.hsi.ds.job.beans.bo.FrontPostJob;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.beans.vo.BusinessVo;
import com.gy.hsi.ds.job.controller.form.BusinessListForm;
import com.gy.hsi.ds.job.controller.form.BusinessNewForm;
import com.gy.hsi.ds.job.dao.IFrontPostJobDao;
import com.gy.hsi.ds.job.dao.IJobStatusDao;
import com.gy.hsi.ds.job.service.IBusinessMgr;

/**
 * 业务配置业务处理类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.business.service.impl
 * @ClassName: BusinessMgrImpl
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月12日 下午12:15:21
 * @version V3.0
 */
@Service
public class BusinessMgrImpl implements IBusinessMgr {

	@Autowired
	private IJobStatusDao jobStatusDao;

	@Autowired
	private IFrontPostJobDao frontPostJobDao;

	@Autowired
	private BusExecuteLogicServcie busExecuteLogicServcie;

	@Override
	public void create(BusinessNewForm businessNewForm) {
		JobStatus bus = new JobStatus();

		bus.setServiceName(businessNewForm.getServiceName());
		bus.setServiceGroup(businessNewForm.getServiceGroup());
		bus.setServicePara(businessNewForm.getServicePara());
		bus.setDesc(businessNewForm.getDesc());
		bus.setServiceState(ServiceState.NON_DONE);
		bus.setHasTaskFlag(TaskFlag.NOT_TASK_FLAG);
		bus.setVersion(businessNewForm.getVersion());
		bus.setStateUpdateTime(new Date());

		jobStatusDao.create(bus);
	}

	@Override
	public JobStatus getByNameAndGroup(BusinessNewForm businessNewForm) {
		return jobStatusDao.getByNameAndGroup(businessNewForm.getServiceName(),
				businessNewForm.getServiceGroup());
	}
	
	@Override
	public JobStatus getByNameAndGroup(String serviceName, String group) {
		return jobStatusDao.getByNameAndGroup(serviceName,
				group);
	}

	@Override
	public JobStatus getByName(String serviceName) {
		return jobStatusDao.getByName(serviceName);
	}

	@Override
	public DaoPageResult<BusinessVo> getBusinessList(
			BusinessListForm businessListForm) {
		DaoPageResult<JobStatus> jobStatus = jobStatusDao
				.getBusinessList(businessListForm);

		List<FrontPostJob> frontPostJobList = frontPostJobDao
				.getFrontPostList(JobConstant.FRONT_FLAG);

		// 前置业务
		final Map<String, String> allFrontJobsMap = new HashMap<String, String>();

		if ((null != frontPostJobList) && (0 < frontPostJobList.size())) {
			for (FrontPostJob j : frontPostJobList) {
				String key = j.getServiceName() + j.getServiceGroup();

				String frontServerNames = StringUtils.avoidNull(allFrontJobsMap
						.get(key));

				if (!StringUtils.isEmpty(frontServerNames)) {
					frontServerNames += ", " + j.getFpServiceName();
				} else {
					frontServerNames = j.getFpServiceName();
				}

				allFrontJobsMap.put(key, frontServerNames);
			}
		}
		
		// 后置业务
		final Map<String, String> allPostJobsMap = new HashMap<String, String>();

		if ((null != frontPostJobList) && (0 < frontPostJobList.size())) {
			for (FrontPostJob j : frontPostJobList) {
				String key = j.getFpServiceName() + j.getFpServiceGroup();

				String postServerNames = StringUtils.avoidNull(allPostJobsMap
						.get(key));

				if (!StringUtils.isEmpty(postServerNames)) {
					postServerNames += ", " + j.getServiceName();
				} else {
					postServerNames = j.getServiceName();
				}

				allPostJobsMap.put(key, postServerNames);
			}
		}

		DaoPageResult<BusinessVo> businessVo = ServiceUtil.getResult(jobStatus,
				new DataTransfer<JobStatus, BusinessVo>() {
					@Override
					public BusinessVo transfer(JobStatus input) {

						String frontServerName = allFrontJobsMap.get(input
								.getServiceName() + input.getServiceGroup());

						BusinessVo businessVo = convert(input);

						if (!StringUtils.isEmpty(frontServerName)) {
							businessVo.setFontJobServiceGroup(frontServerName);
						}

						String postServerName = allPostJobsMap.get(input
								.getServiceName() + input.getServiceGroup());

						if (!StringUtils.isEmpty(postServerName)) {
							businessVo.setPostJobServiceGroup(postServerName);
						}

						return businessVo;
					}
				});

		return businessVo;
	}

	/**
	 * 将JobStatus转换成BusinessVo
	 * 
	 * @param input
	 * @return
	 */
	private BusinessVo convert(JobStatus input) {
		BusinessVo businessVo = new BusinessVo();
		businessVo.setBusId(input.getId());
		businessVo.setServiceName(input.getServiceName());
		businessVo.setServiceGroup(input.getServiceGroup());
		businessVo.setServicePara(input.getServicePara());
		businessVo.setServiceState(JobConstant.SERVICE_STATE.get(input
				.getServiceState()));
		businessVo.setDesc(input.getDesc());
		businessVo.setHasTaskFlag(input.getHasTaskFlag());
		businessVo.setStateUpdateTime(DateUtils.formatDate(input
				.getStateUpdateTime()));
		businessVo.setVersion(input.getVersion());
		businessVo.setLastExcecuteId(input.getLastExecuteId());

		if (input instanceof TmpJobStatus) {
			String fontJobServiceGroup = ((TmpJobStatus) input)
					.getFontJobServiceGroup();

			businessVo.setFontJobServiceGroup(fontJobServiceGroup);
		}

		return businessVo;
	}

	@Override
	public void delete(long busId) {

		JobStatus jobStatus = jobStatusDao.get(busId);

		String serviceName = jobStatus.getServiceName();
		String serviceGroup = jobStatus.getServiceGroup();

		jobStatusDao.delete(busId);

		frontPostJobDao.delete(serviceName, serviceGroup,
				JobConstant.FRONT_FLAG);

		List<FrontPostJob> frontIsMeList = frontPostJobDao.getFrontIsMeList(
				serviceName, serviceGroup);

		if ((null != frontIsMeList) && (0 < frontIsMeList.size())) {
			for (FrontPostJob frontPostJob : frontIsMeList) {
				frontPostJobDao.delete(frontPostJob.getId());
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public String update(long busId, String servicePara, String desc,
			String version) {
		jobStatusDao.update(busId, servicePara, desc, version);
		
		return "修改成功";
	}

	@Override
	public BusinessVo getBusVo(long busId) {
		TmpJobStatus tmpJobStatus = this.queryTmpJobStatus(busId);

		return convert(tmpJobStatus);
	}

	@Override
	public TmpJobStatus getBusById(long busId) {
		return this.queryTmpJobStatus(busId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public String setPostBus(String serviceName, String serviceGroup,
			long postId) {
		frontPostJobDao
				.delete(serviceName, serviceGroup, JobConstant.POST_FLAG);
		
		if (postId != TaskFlag.NOT_POST_TASK_FLAG) {
			JobStatus jobStatus = jobStatusDao.get(postId);
			
			FrontPostJob frontPostJob = new FrontPostJob();
			frontPostJob.setServiceName(serviceName);
			frontPostJob.setServiceGroup(serviceGroup);
			frontPostJob.setFrontPostFlag(JobConstant.POST_FLAG);
			frontPostJob.setFpServiceName(jobStatus.getServiceName());
			frontPostJob.setFpServiceGroup(jobStatus.getServiceGroup());
			
			frontPostJobDao.create(frontPostJob);
		}
		
		return "设置后置任务成功";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public String setFrontBus(String serviceName, String serviceGroup,
			String frontIds) {
		// List<Long> ids = getFrontIdList(frontIds);
		// List<JobStatus> jobs = jobStatusDao.get(ids);
		
		Map<String, String> jobServiceNameAndGroupMap = new HashMap<String, String>();
		
		DaoPageResult<JobStatus> allJobstatus = jobStatusDao.getBusinessList(new BusinessListForm());		
		List<JobStatus> allJobStatusListInDB = allJobstatus.getResult();
		
		if(null != allJobStatusListInDB) {
			for(JobStatus j : allJobStatusListInDB) {
				jobServiceNameAndGroupMap.put(j.getServiceName(), j.getServiceGroup());
			}
		}
		
		List<JobStatus> jobs = new ArrayList<JobStatus>();

		List<String> serviceNames = getFrontIdList(frontIds);

		for (String name : serviceNames) {
			JobStatus job = new JobStatus();
			job.setServiceName(name);
			job.setServiceGroup(jobServiceNameAndGroupMap.get(name));

			jobs.add(job);
		}
		
		List<FrontPostJob> frontList = getFrontJobList(serviceName,
				serviceGroup, jobs);
		
		frontPostJobDao.delete(serviceName, serviceGroup,
				JobConstant.FRONT_FLAG);
		frontPostJobDao.create(frontList);
		
		return "设置前置任务成功";
	}

	/**
	 * 转换为前置任务后置任务列表
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @param jobs
	 * @return
	 */
	private List<FrontPostJob> getFrontJobList(String serviceName,
			String serviceGroup, List<JobStatus> jobs) {
		List<FrontPostJob> frontJobs = new ArrayList<FrontPostJob>();
		FrontPostJob frontJob = null;
		JobStatus job = null;
		
		if (jobs != null && jobs.size() > 0) {
			
			for (int i = 0; i < jobs.size(); i++) {
				job = jobs.get(i);
				
				if (job != null) {
					frontJob = new FrontPostJob();
					frontJob.setServiceName(serviceName);
					frontJob.setServiceGroup(serviceGroup);
					frontJob.setFrontPostFlag(JobConstant.FRONT_FLAG);
					frontJob.setFpServiceName(job.getServiceName());
					frontJob.setFpServiceGroup(job.getServiceGroup());
					frontJobs.add(frontJob);
				}
			}
		}
		
		return frontJobs;
	}
	
	/**
	 * 转换为查询用的数组
	 * 
	 * @param frontIds
	 * @return
	 */
	private List<String> getFrontIdList(String frontIds) {
		List<String> result = new ArrayList<String>();
		
		if (!StringUtils.isEmpty(frontIds)) {
			String[] ids = frontIds.split(JobConstant.SELECT_SPLIT_FLAG);

			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				
				if (!StringUtils.isEmpty(id) && !result.contains(id)) {
					result.add(id);
				}
			}
		}
		
		return result;
	}

	@Override
	public String executeOnce(String serviceName, String serviceGroup,
			String tempArgs) {

		try {
			// 手工执行
			busExecuteLogicServcie.recursiveServiceByManual(serviceName,
					serviceGroup, tempArgs);
		} catch (Exception e) {
			return e.getMessage();
		}

		return "手工触发成功";
	}
	
	/**
	 * queryTmpJobStatus
	 * 
	 * @param busId
	 * @return
	 */
	private TmpJobStatus queryTmpJobStatus(Long busId) {
		JobStatus jobStatus = jobStatusDao.get(busId);

		List<FrontPostJob> frontJobs = frontPostJobDao.getFrontPostList(
				jobStatus.getServiceName(), jobStatus.getServiceGroup(),
				JobConstant.FRONT_FLAG);

		TmpJobStatus tmpJobStatus = new TmpJobStatus();

		BeanUtils.copyProperties(jobStatus, tmpJobStatus);

		if ((null != frontJobs) && (0 < frontJobs.size())) {

			String frontJobServices = "";

			for (FrontPostJob j : frontJobs) {
				frontJobServices += j.getFpServiceName() + ", ";
			}

			if (0 < frontJobServices.length()) {
				frontJobServices = frontJobServices.trim();
				frontJobServices = frontJobServices.substring(0,
						frontJobServices.length() - 1);
			}

			tmpJobStatus.setFontJobServiceGroup(frontJobServices);
		}

		return tmpJobStatus;
	}
	
	@SuppressWarnings("unused")
	private String queryFrontServiceGroupList(String name, String group) {
		List<FrontPostJob> frontPostJobList = frontPostJobDao
				.getFrontPostList(JobConstant.FRONT_FLAG);
				
		Map<String, FrontPostJob> tmpMap = new HashMap<String, FrontPostJob>();
		
		if ((null != frontPostJobList) && (0 < frontPostJobList.size())) {
			for (FrontPostJob j : frontPostJobList) {
				tmpMap.put(j.getServiceName() + j.getServiceGroup(), j);
			}
			
			FrontPostJob me = tmpMap.get(name + group);
			
			if(null == me) {
				return "";
			}
									
			for (FrontPostJob j : frontPostJobList) {
				tmpMap.get(j.getFpServiceName() + j.getServiceGroup());
			}
		}
		
		return "";
	}
}
