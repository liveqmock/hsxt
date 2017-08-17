/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.constant.JobConstant;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.job.beans.bo.FrontPostJob;
import com.gy.hsi.ds.job.dao.IFrontPostJobDao;

/**
 * 
 * 前置任务后置任务表DAO实现类
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
public class FrontPostJobDaoImpl extends AbstractDao<Long, FrontPostJob>
		implements Serializable, IFrontPostJobDao {

	private static final long serialVersionUID = 2867858307838286617L;

	@Override
	public List<FrontPostJob> getFrontPostList(int flag) {
		return find(new Match(Columns.FRONT_POST_FLAG, flag));
	}

	@Override
	public List<FrontPostJob> getFrontPostList(String name, String group,
			int flag) {
		return find(new Match(Columns.SERVICE_NAME, name), new Match(
				Columns.SERVICE_GROUP, group), new Match(
				Columns.FRONT_POST_FLAG, flag));
	}

	@Override
	public void delete(String serviceName, String serviceGroup, int postFlag) {
		delete(new Match(Columns.SERVICE_NAME, serviceName), new Match(
				Columns.SERVICE_GROUP, serviceGroup), new Match(
				Columns.FRONT_POST_FLAG, postFlag));
	}

	@Override
	public List<FrontPostJob> getFrontIsMeList(String frontServiceName,
			String frontServiceGroup) {
		return find(new Match(Columns.FP_SERVICE_NAME, frontServiceName),
				new Match(Columns.FP_SERVICE_GROUP, frontServiceGroup),
				new Match(Columns.FRONT_POST_FLAG, JobConstant.FRONT_FLAG));
	}

}
