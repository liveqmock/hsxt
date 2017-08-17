package com.gy.hsi.ds.param.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsi.ds.common.thirds.dsp.common.exception.FieldException;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.controller.form.AppNewForm;
import com.gy.hsi.ds.param.service.IAppMgr;

/**
 * 权限验证
 *
 * @author liaoqiqi
 * @version 2014-7-2
 */
@Component
public class AppValidator {

    @Autowired
    private IAppMgr appMgr;

    /**
     * 验证创建
     */
    public void validateCreate(AppNewForm appNewForm) {

        App app = appMgr.getByName(appNewForm.getApp());
        if (app != null) {
            throw new FieldException(AppNewForm.APP, "app.exist", null);
        }

    }

}
