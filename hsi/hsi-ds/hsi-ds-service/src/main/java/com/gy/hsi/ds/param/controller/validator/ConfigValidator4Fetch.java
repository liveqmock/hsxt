package com.gy.hsi.ds.param.controller.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.param.beans.ConfigFullModel;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.controller.form.ConfForm;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IEnvMgr;

/**
 * @author knightliao
 */
@Service
public class ConfigValidator4Fetch {

    @Autowired
    private IAppMgr appMgr;

    @Autowired
    private IEnvMgr envMgr;
    
    /**
     * 此接口是客户的接口，非常 重要，目前没有权限的控制
     *
     * @param confForm
     */
    public ConfigFullModel verifyConfForm(ConfForm confForm) throws Exception {

        //
        // app
        //
        if (StringUtils.isEmpty(confForm.getApp())) {
            throw new Exception("app is empty");
        }

        App app = appMgr.getByName(confForm.getApp());
        if (app == null) {
            throw new Exception("app " + confForm.getApp() + " doesn't exist in db.");
        }

        //
        // env
        //
        if (StringUtils.isEmpty(confForm.getEnv())) {
            throw new Exception("app is empty");
        }

        Env env = envMgr.getByName(confForm.getEnv());
        if (env == null) {
            throw new Exception("env " + confForm.getEnv() + " doesn't exist in db.");
        }

        //
        // key
        //
        if (StringUtils.isEmpty(confForm.getKey())) {
            throw new Exception("key is empty");
        }

        //
        // version
        //
        if (StringUtils.isEmpty(confForm.getVersion())) {
            throw new Exception("version is empty");
        }

        return new ConfigFullModel(app, env, confForm.getVersion(), confForm.getKey());
    }
}
