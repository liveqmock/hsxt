/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.service.impl;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.JobConstant;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DataTransfer;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.ServiceUtil;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.beans.bo.JobExecuteDetails;
import com.gy.hsi.ds.job.beans.vo.ExecuteDetailVo;
import com.gy.hsi.ds.job.controller.form.DetailListForm;
import com.gy.hsi.ds.job.dao.IJobExecuteDetailsDao;
import com.gy.hsi.ds.job.service.IExecuteDetailMgr;

/**
 * 业务执行详情业务处理实现类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.detail.service.impl
 * @ClassName: ExecuteDetailMgrImpl
 * @Description: none
 *
 * @author: yangyp
 * @date: 2015年10月20日 上午10:58:56
 * @version V3.0
 */
@Service
public class ExecuteDetailMgrImpl implements IExecuteDetailMgr {
	
	protected static final Logger LOG = Logger
			.getLogger(ExecuteDetailMgrImpl.class);

	@Autowired
	private IJobExecuteDetailsDao jobExecuteDetailsDao;

	@Override
	public DaoPageResult<ExecuteDetailVo> getDetailList(
			DetailListForm detailListForm) {
		DaoPageResult<ExecuteDetailVo> executeDetailVos = new DaoPageResult<ExecuteDetailVo>();
		
		try {
			DaoPageResult<JobExecuteDetails> jobExecuteDetails = jobExecuteDetailsDao
					.getDetailList(detailListForm);
			
			executeDetailVos = ServiceUtil.getResult(jobExecuteDetails,
					new DataTransfer<JobExecuteDetails, ExecuteDetailVo>() {
						@Override
						public ExecuteDetailVo transfer(JobExecuteDetails input) {
							return convert(input);
						}
					});
		} catch (ParseException e) {
			LOG.error("获取业务信息列表失败！", e);
		}
		
		return executeDetailVos;
	}

	private ExecuteDetailVo convert(JobExecuteDetails input) {
		
		Integer serviceStatus = input.getServiceState();
		
		String strTimeEclipsed = DateUtils.getTimeDiffByMinutes(
				input.getCreateDate(), input.getStatusDate());
		
		// 只有成功或失败才显示耗时时间
		if((0 != serviceStatus) && (1 != serviceStatus)) {
			strTimeEclipsed = "";
		}
		
		if("0.00".equals(strTimeEclipsed)) {
			strTimeEclipsed = "0.01";
		}
		
		String inputDesc = StringUtils.avoidNull(input.getDesc());
		String state = JobConstant.SERVICE_STATE.get(serviceStatus);
		String date = DateUtils.formatDate(input.getStatusDate());
		Integer executeMethod = input.getExecuteMethod();
		
		if(null == executeMethod) {
			executeMethod = 0;
		}
		
		int maxLen = 380;
		
		ExecuteDetailVo vo = new ExecuteDetailVo();
		vo.setServiceName(input.getServiceName());
		vo.setServiceGroup(input.getServiceGroup());
		vo.setServiceState(state);
		vo.setReportDate(date);
		vo.setTimeEclipsed(strTimeEclipsed);
		vo.setDesc(StringUtils.cut2SpecialLength(inputDesc, maxLen, " ..."));
		vo.setExecuteId(input.getExecuteId());
		vo.setExecuteMethod(String.valueOf(executeMethod));
		
		String executeParam = "";

		if (!StringUtils.isEmpty(inputDesc)) {
			int start = inputDesc.lastIndexOf("|-->> 临时参数");

			if (0 > start) {
				start = inputDesc.lastIndexOf("--> 临时参数");
			}

			if (0 < start) {
				executeParam = inputDesc.substring(start).replace("|-->>", "").replace("-->", "");
				inputDesc = inputDesc.substring(0, start);
			}
		}

		vo.setDesc(StringUtils.cut2SpecialLength(inputDesc, maxLen, " ..."));
		vo.setFullDesc(maxLen >= inputDesc.length());
		vo.setExecuteParam(executeParam);

		return vo;
	}

}
