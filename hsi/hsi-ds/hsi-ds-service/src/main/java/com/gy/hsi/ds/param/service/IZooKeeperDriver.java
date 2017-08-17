package com.gy.hsi.ds.param.service;

import java.util.List;
import java.util.Map;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.param.beans.ZkDisconfData;
import com.gy.hsi.ds.param.beans.bo.App;

/**
 * @author liaoqiqi
 * @version 2014-6-24
 */
public interface IZooKeeperDriver {

    /**
     * 通知某个Node更新
     *
     * @param app
     * @param env
     * @param version
     * @param disConfigTypeEnum
     */
    void notifyNodeUpdate(String app, String env, String version, String key, String value,
                          DisConfigTypeEnum disConfigTypeEnum);

    /**
     * 获取分布式配置 Map
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    Map<String, ZkDisconfData> getDisconfData(String app, String env, String version);

    /**
     * 获取分布式配置 Map
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    ZkDisconfData getDisconfData(String app, String env, String version, DisConfigTypeEnum disConfigTypeEnum,
                                 String keyName);

    /**
     * 返回groupName结点向下的所有zookeeper信息
     */
    List<String> getConf(String groupName);

    void destroy() throws Exception;
    
    /**
     * 获得通用配置Map
     * @param env
     * @param version
     * @param disConfigTypeEnum
     * @param keyName
     * @return
     */
    ZkDisconfData getDisconfData(List<App> appList,String env, String version, DisConfigTypeEnum disConfigTypeEnum, String keyName);

}
