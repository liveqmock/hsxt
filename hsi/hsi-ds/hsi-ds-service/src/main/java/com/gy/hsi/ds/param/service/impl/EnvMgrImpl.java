package com.gy.hsi.ds.param.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.login.service.IUserInnerMgr;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.beans.vo.EnvListVo;
import com.gy.hsi.ds.param.dao.IEnvDao;
import com.gy.hsi.ds.param.service.IEnvMgr;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class EnvMgrImpl implements IEnvMgr {

    @Autowired
    private IEnvDao envDao;
    
    @Autowired
    private IUserInnerMgr userInnerMgr;

    @Override
    public Env getByName(String name) {

        return envDao.getByName(name);
    }

    /**
     *
     */
    @Override
    public List<EnvListVo> getVoList() {
        List<Env> envs = envDao.getByIds(userInnerMgr.getVisitorEnvIds());
        List<EnvListVo> envListVos = new ArrayList<EnvListVo>();
        for (Env env : envs) {
            EnvListVo envListVo = new EnvListVo();
            envListVo.setId(env.getId());
            envListVo.setName(env.getEnvName());

            envListVos.add(envListVo);
        }

        return envListVos;
    }

    @Override
    public Map<Long, Env> getByIds(Set<Long> ids) {

        if (ids.size() == 0) {
            return new HashMap<Long, Env>();
        }

        List<Env> envs = envDao.get(ids);

        Map<Long, Env> map = new HashMap<Long, Env>();
        for (Env env : envs) {
            map.put(env.getId(), env);
        }

        return map;
    }

    @Override
    public Env getById(Long id) {
        return envDao.get(id);
    }

    /**
     *
     */
    @Override
    public List<Env> getList() {
        return envDao.findAll();
    }

}
