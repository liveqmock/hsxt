package com.gy.hsi.ds.param.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DaoUtils;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPage;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Modify;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Order;
import com.gy.hsi.ds.param.beans.bo.Config;
import com.gy.hsi.ds.param.dao.IConfigDao;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class ConfigDaoImpl extends AbstractDao<Long, Config> implements IConfigDao {

    /**
     *
     */
    @Override
    public Config getByParameter(Long appId, Long envId, String version, String key,
                                 DisConfigTypeEnum disConfigTypeEnum) {

        return findOne(new Match(Columns.APP_ID, appId), new Match(Columns.ENV_ID, envId),
                          new Match(Columns.VERSION, version), new Match(Columns.CONFIG_TYPE, disConfigTypeEnum.getType()),
                          new Match(Columns.CONFIG_NAME, key));
    }

    /**
     *
     */
    @Override
    public List<Config> getConfByAppEnv(Long appId, Long envId) {

        if (envId == null) {
            return find(new Match(Columns.APP_ID, appId));
        } else {

            return find(new Match(Columns.APP_ID, appId), new Match(Columns.ENV_ID, envId));

        }
    }

    /**
     *
     */
    @Override
    public DaoPageResult<Config> getConfigList(Long appId, Long envId, String version, Page page) {

        DaoPage daoPage = DaoUtils.daoPageAdapter(page);
        List<Match> matchs = new ArrayList<Match>();

        matchs.add(new Match(Columns.APP_ID, appId));

		if (null != envId) {
			matchs.add(new Match(Columns.ENV_ID, envId));
		}

		if(!StringUtils.isEmpty(version)) {
			 matchs.add(new Match(Columns.VERSION, version));
		}       

        return page2(matchs, daoPage);
    }

    /**
     *
     */
    @Override
    public List<Config> getConfigList(Long appId, Long envId, String version) {

        List<Match> matchs = new ArrayList<Match>();

        if(null != appId) {
        	matchs.add(new Match(Columns.APP_ID, appId));
        }        

        if(null != envId) {
        	matchs.add(new Match(Columns.ENV_ID, envId));
        }        

        if(!StringUtils.isEmpty(version)) {
        	matchs.add(new Match(Columns.VERSION, version));
        }

        return find(matchs, new ArrayList<Order>());
    }

    /**
     *
     */
    @Override
    public void updateValue(Long configId, String value) {

        // 时间
        //String curTime = DateUtils.format(new Date(), DataFormatConstants.COMMON_TIME_FORMAT);

        List<Modify> modifyList = new ArrayList<Modify>();
        modifyList.add(modify(Columns.CONFIG_VALUE, value));
        modifyList.add(modify(Columns.UPDATE_DATE, new Date()));

        update(modifyList, match(Columns.CONFIG_ID, configId));
    }

    @Override
    public String getValue(Long configId) {
        Config config = get(configId);
        return config.getConfigValue();
    }
}
