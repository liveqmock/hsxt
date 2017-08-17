package com.gy.hsi.ds.param.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Order;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.dao.IAppDao;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class AppDaoImpl extends AbstractDao<Long, App> implements IAppDao {

	@Override
	public App getByName(String name) {

		return findOne(new Match(Columns.APP_NAME, name));
	}

	@Override
	public List<App> getByIds(Set<Long> ids) {

		if (CollectionUtils.isEmpty(ids)) {
			return findAll();
		}

		Order order = new Order(Columns.APP_NAME, true);

		List<Match> matchs = new ArrayList<Match>(1);
		matchs.add(match(Columns.APP_ID, ids));

		return find(matchs, order);
	}

}
