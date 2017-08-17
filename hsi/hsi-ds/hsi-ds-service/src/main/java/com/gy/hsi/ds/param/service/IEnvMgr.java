package com.gy.hsi.ds.param.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.beans.vo.EnvListVo;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface IEnvMgr {
    /**
     * @param name
     */
    Env getByName(String name);

    /**
     * @return
     */
    List<Env> getList();

    /**
     * @return
     */
    List<EnvListVo> getVoList();

    /**
     * @param ids
     *
     * @return
     */
    Map<Long, Env> getByIds(Set<Long> ids);

    /**
     * @param id
     *
     * @return
     */
    Env getById(Long id);
}
