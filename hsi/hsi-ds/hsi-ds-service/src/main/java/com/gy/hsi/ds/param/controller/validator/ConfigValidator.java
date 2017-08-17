package com.gy.hsi.ds.param.controller.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.FieldException;
import com.gy.hsi.ds.login.service.IAuthMgr;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.bo.Config;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.controller.form.ConfNewForm;
import com.gy.hsi.ds.param.controller.form.ConfNewItemForm;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IConfigFetchMgr;
import com.gy.hsi.ds.param.service.IConfigMgr;
import com.gy.hsi.ds.param.service.IEnvMgr;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class ConfigValidator {

    @Autowired
    private IAppMgr appMgr;

    @Autowired
    private IEnvMgr envMgr;

    @Autowired
    private IConfigMgr configMgr;

    @Autowired
    private IConfigFetchMgr configFetchMgr;

    @Autowired
    private IAuthMgr authMgr;

    /**
     * 校验
     *
     * @param id
     *
     * @return
     */
    public Config valideConfigExist(Long id) {

        //
        // config
        //
        Config config = configMgr.getConfigById(id);
        if (config == null) {
            throw new FieldException("configId", "config.id.not.exist", null);
        }

        //
        // validate app
        //
        validateAppAuth(config.getAppId());

        return config;
    }

    /**
     * 校验更新 配置值
     *
     * @param configId
     * @param value
     */
    public void validateUpdateItem(Long configId, String value) {

        //
        // config
        //
        valideConfigExist(configId);

        //
        // value
        //
        try {

            if (StringUtils.isEmpty(value)) {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new FieldException("value", "conf.item.value.null", e);
        }

    }

    /**
     * 校验更新 配置文件
     *
     * @param configId
     * @param fileName
     */
    public void validateUpdateFile(Long configId, String fileName) {

        //
        // config
        //
        Config config = valideConfigExist(configId);

        //
        // value
        //
        try {

            if (!config.getConfigName().equals(fileName)) {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new FieldException("value", "conf.file.name.not.equal", e);
        }

    }

    /**
     * 判断配置是否更新
     *
     * @return
     */
    public boolean isValueUpdate(Long configId, String newValue) {

        //
        // 判断值有没有更新
        //
        String oldValue = configMgr.getValue(configId);

        if (newValue.equals(oldValue)) {
            return false;
        }
        return true;
    }

    /**
     * @param appId
     */
    private void validateAppAuth(long appId) {

        boolean ret = authMgr.verifyApp4CurrentUser(appId);
        if (ret == false) {
            throw new FieldException(ConfNewForm.APPID, "app.auth.noright", null);
        }

    }

    /**
     * 校验新建 配置
     *
     * @param confNewForm
     * @param disConfigTypeEnum
     */
    public void validateNew(ConfNewItemForm confNewForm, DisConfigTypeEnum disConfigTypeEnum) {

        //
        // app
        //
        App app = appMgr.getById(confNewForm.getAppId());
        if (app == null) {
            throw new FieldException(ConfNewForm.APPID, "app.not.exist", null);
        }

        //
        validateAppAuth(app.getId());

        //
        // env
        //
        Env env = envMgr.getById(confNewForm.getEnvId());
        if (env == null) {
            throw new FieldException(ConfNewForm.ENVID, "env.not.exist", null);
        }

        //
        // key
        //
        Config config = configFetchMgr.getConfByParameter(app.getId(), env.getId(), confNewForm.getVersion(),
                                                             confNewForm.getKey(), disConfigTypeEnum);
        if (config != null) {
            throw new FieldException(ConfNewItemForm.KEY, "key.exist", null);
        }

    }

    /**
     * 验证删除
     */
    public void validateDelete(Long configId) {

        Config config = configMgr.getConfigById(configId);
        if (config == null) {
            throw new FieldException("configId", "config.not.exist", null);
        }

        //
        validateAppAuth(config.getAppId());
    }

}
