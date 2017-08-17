package com.gy.hsi.ds.param.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.login.service.IUserInnerMgr;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.vo.AppListVo;
import com.gy.hsi.ds.param.controller.form.AppNewForm;
import com.gy.hsi.ds.param.dao.IAppDao;
import com.gy.hsi.ds.param.service.IAppMgr;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class AppMgrImpl implements IAppMgr {

    @Autowired
    private IAppDao appDao;

    @Autowired
    private IUserInnerMgr userInnerMgr;

    /**
     *
     */
    @Override
    public App getByName(String name) {

        return appDao.getByName(name);
    }

    /**
     *
     */
    @Override
	public List<AppListVo> getAuthAppVoList() {
		List<AppListVo> appListVos = new ArrayList<AppListVo>();

		List<App> apps = appDao.getByIds(userInnerMgr.getVisitorAppIds());

		if (null != apps) {
			Collections.sort(apps);
			
			for (App app : apps) {
				AppListVo appListVo = new AppListVo();
				appListVo.setId(app.getId());
				appListVo.setName(app.getAppName());
				appListVo.setDesc(app.getDesc());
				appListVos.add(appListVo);
			}
		}

		return appListVos;
	}

    @Override
    public Map<Long, App> getByIds(Set<Long> ids) {

        if (ids.size() == 0) {
            return new HashMap<Long, App>();
        }

        List<App> apps = appDao.get(ids);

        Map<Long, App> map = new HashMap<Long, App>();
        for (App app : apps) {
            map.put(app.getId(), app);
        }

        return map;
    }

    @Override
    public App getById(Long id) {

        return appDao.get(id);
    }

    @Override
    public App create(AppNewForm appNew) {

        App app = new App();
        app.setAppName(appNew.getApp());
        app.setDesc(appNew.getDesc());
        app.setEmails(appNew.getEmails());
        // 时间
        Date curTime = new Date();
        app.setCreateDate(curTime);
        app.setUpdateDate(curTime);
        return appDao.create(app);
    }

    @Override
    public void delete(Long appId) {
        appDao.delete(appId);
    }

    @Override
    public String getEmails(Long id) {

        App app = getById(id);

        if (app == null) {
            return "";
        } else {
            return app.getEmails();
        }
    }

    @Override
    public List<App> getAppList() {

        return appDao.findAll();
    }
}
