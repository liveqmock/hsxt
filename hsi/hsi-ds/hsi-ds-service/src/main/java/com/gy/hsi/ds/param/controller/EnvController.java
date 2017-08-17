package com.gy.hsi.ds.param.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.param.beans.vo.EnvListVo;
import com.gy.hsi.ds.param.service.IEnvMgr;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/env")
public class EnvController extends BaseController {

    protected static final Logger LOG = Logger.getLogger(EnvController.class);

    @Autowired
    private IEnvMgr envMgr;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public JsonObjectBase list() {

        List<EnvListVo> envListVos = envMgr.getVoList();

        return buildListSuccess(envListVos, envListVos.size());
    }

}
