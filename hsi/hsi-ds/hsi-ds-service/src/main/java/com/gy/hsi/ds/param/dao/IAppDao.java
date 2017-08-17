package com.gy.hsi.ds.param.dao;

import java.util.List;
import java.util.Set;

import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface IAppDao extends BaseDao<Long, App> {

    /**
     * @param name
     *
     * @return
     */
    App getByName(String name);

    /**
     * @param ids
     *
     * @return
     */
    List<App> getByIds(Set<Long> ids);

}
