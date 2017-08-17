package com.gy.hsi.ds.param.service.impl;

import java.util.List;
import java.util.Map;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.param.beans.ZkDisconfData;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.service.IZooKeeperDriver;

/**
 * Created by knightliao on 15/1/14.
 */
public class ZookeeperDriverMock implements IZooKeeperDriver {

    @Override
    public void notifyNodeUpdate(String app, String env, String version, String key, String value,
                                 DisConfigTypeEnum disConfigTypeEnum) {

    }

    @Override
    public Map<String, ZkDisconfData> getDisconfData(String app, String env, String version) {
        return null;
    }

    @Override
    public ZkDisconfData getDisconfData(String app, String env, String version, DisConfigTypeEnum disConfigTypeEnum,
                                        String keyName) {
        return null;
    }

    @Override
    public List<String> getConf(String groupName) {
        return null;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public ZkDisconfData getDisconfData(List<App> appList,String env, String version, DisConfigTypeEnum disConfigTypeEnum,
            String keyName) {
        return null;
    }
}
