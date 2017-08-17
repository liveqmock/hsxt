/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DaoUtils;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPage;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.AppenderParam;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.LikeParam;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.LteParam;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.NotParam;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.beans.bo.JobExecuteDetails;
import com.gy.hsi.ds.job.controller.form.DetailListForm;
import com.gy.hsi.ds.job.dao.IJobExecuteDetailsDao;

/**
 * 业务执行情况详情表DAO实现类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.dao.impl
 * @ClassName: FrontPostJobDaoImpl
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月9日 下午12:01:41
 * @version V3.0
 */
@Service
public class JobExecuteDetailsDaoImpl extends
		AbstractDao<Long, JobExecuteDetails> implements Serializable,
		IJobExecuteDetailsDao {

	private static final long serialVersionUID = 2070757869923726470L;

	@Override
	public DaoPageResult<JobExecuteDetails> getDetailList(
			DetailListForm detailListForm) throws ParseException {
		Page page = detailListForm.getPage();
		DaoPage daoPage = DaoUtils.daoPageAdapter(page);
		List<Match> matchs = new ArrayList<Match>();

		String serviceName = detailListForm.getServiceName();
		
		if (StringUtils.isNotEmpty(serviceName)) {
			matchs.add(new Match(Columns.SERVICE_NAME, new LikeParam(
					serviceName)));
		}

		String serviceGroup = detailListForm.getServiceGroup();
		if (StringUtils.isNotEmpty(serviceGroup)) {
			matchs.add(new Match(Columns.SERVICE_GROUP, new LikeParam(
					serviceGroup)));
		}

		String serviceStatus = detailListForm.getExecuteState();
		if (StringUtils.isNotEmpty(serviceStatus)) {
			matchs.add(new Match(Columns.EXECUTE_STATUS, serviceStatus));
		}

		String startTime = detailListForm.getStartDate();
		// if (StringUtils.isNotEmpty(startTime)) {
		// matchs.add(new Match(Columns.EXECUTE_STATUS_TIME,
		// new GreaterThanParam(startTime)));
		// }

		String endTime = detailListForm.getEndDate();
		if (StringUtils.isNotEmpty(endTime)) {
			// String tmpTime = DateUtils.getAfterDate(endTime);
			// matchs.add(new Match(Columns.EXECUTE_STATUS_TIME,
			// new LessThanParam(tmpTime)));

			// endTime = DateUtils.getAfterDate(endTime);
		}
		
		StringBuilder buf = new StringBuilder();
		
		if(0 >= matchs.size()) {
			buf.append(" where 1=1 ");
		}
		
		buf.append(" and (EXECUTE_STATUS_TIME is null ");
		buf.append(" or (");
		buf.append("EXECUTE_STATUS_TIME >= '").append(startTime).append("'");
		buf.append(" and EXECUTE_STATUS_TIME <= '").append(endTime).append("')");		
		buf.append(" )");

		matchs.add(new Match(Columns.EXECUTE_STATUS_TIME, new AppenderParam(buf.toString())));

		return page2(matchs, daoPage);
	}

	@Override
	public String getDetailErrDesc(String executeId) {
		List<JobExecuteDetails> list = find(new Match(Columns.EXECUTE_ID,
				executeId), new Match(Columns.EXECUTE_STATUS, new NotParam(
				DSTaskStatus.HANDLING)), new Match(Columns.EXECUTE_STATUS,
				new NotParam(DSTaskStatus.SUCCESS)));

		if (null != list && 0 < list.size()) {
			JobExecuteDetails detail = list.get(0);

			String desc = detail.getDesc();
			
			if (!StringUtils.isEmpty(desc)) {
				int start = desc.lastIndexOf("|-->> 临时参数");

				if (0 > start) {
					start = desc.lastIndexOf("--> 临时参数");
				}

				if (0 < start) {
					desc = desc.substring(0, start);
				}
			}
			
			return desc;
		}

		return "N/A";
	}

	@Override
	public void deleteDetailDataByDate(int daysBeforeToday) {
		Date date = DateUtils.getDateBeforeDays(daysBeforeToday);
		String strDate = DateUtils.getDateByformat(date, "yyyy-MM-dd HH:mm:ss");

		delete(new Match(Columns.EXECUTE_STATUS_TIME, new LteParam(strDate)));
	}

	@Override
	public JobExecuteDetails getDetailByExecuteId(String executeId) {
		List<JobExecuteDetails> list = find(new Match(Columns.EXECUTE_ID,
				executeId));

		if (null != list && 0 < list.size()) {
			return list.get(0);
		}

		return null;
	}
}
