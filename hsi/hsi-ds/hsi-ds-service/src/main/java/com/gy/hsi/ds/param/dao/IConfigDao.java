package com.gy.hsi.ds.param.dao;

import java.util.List;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.param.beans.bo.Config;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface IConfigDao extends BaseDao<Long, Config> {

    /**
     * @param appId
     * @param envId
     * @param env
     * @param key
     * @param disConfigTypeEnum
     *
     * @return
     */
    Config getByParameter(Long appId, Long envId, String env, String key, DisConfigTypeEnum disConfigTypeEnum);

    /**
     * @param appName
     *
     * @return
     */
    List<Config> getConfByAppEnv(Long appId, Long envId);

    /**
     * @param appId
     * @param envId
     * @param version
     *
     * @return
     */
    DaoPageResult<Config> getConfigList(Long appId, Long envId, String version, Page page);

    /**
     * @param configId
     *
     * @return
     */
    void updateValue(Long configId, String value);

    /**
     *
     */
    String getValue(Long configId);

    /**
     * @param appId
     * @param envId
     * @param version
     *
     * @return
     */
    List<Config> getConfigList(Long appId, Long envId, String version);

}
