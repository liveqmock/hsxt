package com.gy.hsi.ds.param.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.dao.IEnvDao;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class EnvDaoImpl extends AbstractDao<Long, Env> implements IEnvDao {

    @Override
    public Env getByName(String name) {

        return findOne(new Match(Columns.ENV_NAME, name));
    }
    
    @Override
    public List<Env> getByIds(Set<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return findAll();
        }

        return find(match(Columns.ENV_ID, ids));
    }

}
