package com.gy.hsi.ds.param.service;

import java.io.File;
import java.util.List;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.param.beans.bo.Config;
import com.gy.hsi.ds.param.beans.vo.ConfListVo;
import com.gy.hsi.ds.param.beans.vo.MachineListVo;
import com.gy.hsi.ds.param.controller.form.ConfListForm;
import com.gy.hsi.ds.param.controller.form.ConfNewItemForm;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface IConfigMgr {

    /**
     * @param appName
     *
     * @return
     */
    List<String> getVersionListByAppEnv(Long appId, Long envId);

    /**
     * @param appId
     * @param envId
     * @param version
     *
     * @return
     */
    DaoPageResult<ConfListVo> getConfigList(ConfListForm confListForm, boolean fetchZk, final boolean getErrorMessage);

	public List<Config> getConfigList(Long appId);
    
    /**
     * @param configId
     *
     * @return
     */
    ConfListVo getConfVo(Long configId);

    MachineListVo getConfVoWithZk(Long configId);

    /**
     * @param configId
     *
     * @return
     */
    Config getConfigById(Long configId);

    /**
     * 更新 配置项/配置文件
     *
     * @param configId
     *
     * @return
     */
    String updateItemValue(Long configId, String value);

    /**
     * @param configId
     *
     * @return
     */
    String getValue(Long configId);

    void notifyZookeeper(Long configId);

    /**
     * @param confNewForm
     * @param disConfigTypeEnum
     */
    void newConfig(ConfNewItemForm confNewForm, DisConfigTypeEnum disConfigTypeEnum);

    void delete(Long configId);
    
    void deleteByAppId(Long appId);
    
    void deleteByAppIdAndConfigName(Long appId, String configName);

    /**
     * @param confListForm
     *
     * @return
     */
    List<File> getDisonfFileList(ConfListForm confListForm);
    
    /**
     * getAllDisonfFileList
     * 
     * @return
     */
    String getAllDisonfFileList();
    
    String getDisonfFileListByApp(Long appId, Long envId, String version);

}
