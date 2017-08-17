package com.gy.hsi.ds.param.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.param.beans.vo.AppListVo;
import com.gy.hsi.ds.param.controller.form.AppNewForm;
import com.gy.hsi.ds.param.controller.validator.AppValidator;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IConfigMgr;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/app")
public class AppController extends BaseController {

    protected static final Logger LOG = Logger.getLogger(AppController.class);

    @Autowired
    private IAppMgr appMgr;
    
    @Autowired
    private IConfigMgr configMgr;

    @Autowired
    private AppValidator appValidator;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonObjectBase list() {

        List<AppListVo> appListVos = appMgr.getAuthAppVoList();

        return buildListSuccess(appListVos, appListVos.size());
    }

    /**
     * delete
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonObjectBase delete(String appId) {

        appMgr.delete(Long.parseLong(appId));
        configMgr.deleteByAppId(Long.parseLong(appId));        

        return buildSuccess("删除成功");
    }
    
    /**
     * create
     *
     * @return
     */
    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonObjectBase create(@Valid AppNewForm appNewForm) {

        appValidator.validateCreate(appNewForm);

        appMgr.create(appNewForm);

        return buildSuccess("创建成功");
    }

}
