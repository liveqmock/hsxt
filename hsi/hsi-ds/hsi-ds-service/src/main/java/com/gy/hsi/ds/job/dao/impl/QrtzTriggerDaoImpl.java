/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.dao.impl;

import java.util.ArrayList;
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
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.LikeParam;
import com.gy.hsi.ds.job.beans.bo.QrtzTrigger;
import com.gy.hsi.ds.job.controller.form.TriggerListForm;
import com.gy.hsi.ds.job.dao.IQrtzTriggerDao;
/**
 * 
 * Trigger操作Dao实现类
 * @Package: com.gy.hsi.ds.job.web.service.trigger.dao.impl  
 * @ClassName: QrtzTriggerDaoImpl 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月14日 下午4:21:02 
 * @version V3.0
 */
@Service
public class QrtzTriggerDaoImpl extends AbstractDao<String, QrtzTrigger>
		implements IQrtzTriggerDao {

	@Override
	public DaoPageResult<QrtzTrigger> getTriggerList(
			TriggerListForm triggerListForm) {
		Page page = triggerListForm.getPage();
		DaoPage daoPage = DaoUtils.daoPageAdapter(page);

		List<Match> matchs = new ArrayList<Match>();

		String triggerName = triggerListForm.getTriggerName();

		if (StringUtils.isNotEmpty(triggerName)) {
			matchs.add(new Match(Columns.TRIGGER_NAME, new LikeParam(
					triggerName)));
		}

		String triggerGroup = triggerListForm.getTriggerGroup();

		if (StringUtils.isNotEmpty(triggerGroup)) {
			matchs.add(new Match(Columns.TRIGGER_GROUP, new LikeParam(
					triggerGroup)));
		}

		String triggerState = triggerListForm.getTriggerState();

		if (StringUtils.isNotEmpty(triggerState)) {
			matchs.add(new Match(Columns.TRIGGER_STATE, triggerState));
		}

		return page2(matchs, daoPage);
	}
}
