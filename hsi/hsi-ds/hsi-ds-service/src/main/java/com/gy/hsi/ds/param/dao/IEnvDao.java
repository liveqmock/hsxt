package com.gy.hsi.ds.param.dao;

import java.util.List;
import java.util.Set;

import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.param.beans.bo.Env;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface IEnvDao extends BaseDao<Long, Env> {

    /**
     * @param name
     *
     * @return
     */
    Env getByName(String name);
    
    /**
     * @param ids
     *
     * @return
     */
    List<Env> getByIds(Set<Long> ids);
}
