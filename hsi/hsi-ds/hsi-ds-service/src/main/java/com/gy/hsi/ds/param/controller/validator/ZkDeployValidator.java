package com.gy.hsi.ds.param.controller.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.thirds.dsp.common.exception.FieldException;
import com.gy.hsi.ds.param.beans.ConfigFullModel;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.controller.form.ZkDeployForm;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IEnvMgr;

/**
 * @author liaoqiqi
 * @version 2014-9-11
 */
@Service
public class ZkDeployValidator {

    @Autowired
    private IAppMgr appMgr;

    @Autowired
    private IEnvMgr envMgr;

    /**
     * @param zkDeployForm
     *
     * @return
     */
    public ConfigFullModel verify(ZkDeployForm zkDeployForm) {

        //
        // app
        //
        if (zkDeployForm.getAppId() == null) {
            throw new FieldException("app is empty", null);
        }

        App app = appMgr.getById(zkDeployForm.getAppId());
        if (app == null) {
            throw new FieldException("app " + zkDeployForm.getAppId() + " doesn't exist in db.", null);
        }

        //
        // env
        //
        if (zkDeployForm.getEnvId() == null) {
            throw new FieldException("app is empty", null);
        }

        Env env = envMgr.getById(zkDeployForm.getEnvId());
        if (env == null) {
            throw new FieldException("env " + zkDeployForm.getEnvId() + " doesn't exist in db.", null);
        }

        //
        // version
        //
        if (StringUtils.isEmpty(zkDeployForm.getVersion())) {
            throw new FieldException("version is empty", null);
        }

        return new ConfigFullModel(app, env, zkDeployForm.getVersion(), "");
    }
}
