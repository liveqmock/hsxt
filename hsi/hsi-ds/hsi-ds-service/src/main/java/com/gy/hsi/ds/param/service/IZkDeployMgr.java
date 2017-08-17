package com.gy.hsi.ds.param.service;

import java.util.List;
import java.util.Map;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.param.beans.ZkDisconfData;
import com.gy.hsi.ds.param.beans.bo.App;

/**
 * @author liaoqiqi
 * @version 2014-9-11
 */
public interface IZkDeployMgr {

    /**
     * @param appId
     * @param envId
     * @param version
     *
     * @return
     */
    String getDeployInfo(String app, String env, String version);

    /**
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    Map<String, ZkDisconfData> getZkDisconfDataMap(String app, String env, String version);

    /**
     * 获取指定的数据
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    ZkDisconfData getZkDisconfData(String app, String env, String version, DisConfigTypeEnum disConfigTypeEnum,
                                   String keyName);
    /**
     * 获取通用配置的数据
     * @param envName
     * @param version
     * @param disConfigTypeEnum
     * @param configName
     * @return
     */
    ZkDisconfData getZkCommonDisconfData(List<App> appList,String envName, String version, DisConfigTypeEnum disConfigTypeEnum,
            String configName);
}
