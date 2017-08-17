package com.gy.hsi.ds.param.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf.core.common.constants.Constants;
import com.baidu.disconf.core.common.json.ValueVo;
import com.gy.hsi.ds.common.thirds.dsp.common.annotation.NoAuth;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.param.beans.ConfigFullModel;
import com.gy.hsi.ds.param.beans.ZooConfig;
import com.gy.hsi.ds.param.controller.form.ZkDeployForm;
import com.gy.hsi.ds.param.controller.validator.ZkDeployValidator;
import com.gy.hsi.ds.param.service.IZkDeployMgr;

/**
 * Zoo API
 *
 * @author liaoqiqi
 * @version 2014-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/zoo")
public class ZooController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(ZooController.class);

    @Autowired
    private ZooConfig zooConfig;

    @Autowired
    private ZkDeployValidator zkDeployValidator;

    @Autowired
    private IZkDeployMgr zkDeployMgr;

    /**
     * 获取Zookeeper地址
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/hosts", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getHosts() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue(zooConfig.getZooHosts());

        return confItemVo;
    }

    /**
     * 获取ZK prefix
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/prefix", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getPrefixUrl() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue(zooConfig.getZookeeperUrlPrefix());

        return confItemVo;
    }

    /**
     * 获取ZK 部署情况
     *
     * @param zkDeployForm
     *
     * @return
     */
    @RequestMapping(value = "/zkdeploy", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase getZkDeployInfo(@Valid ZkDeployForm zkDeployForm) {

        ConfigFullModel configFullModel = zkDeployValidator.verify(zkDeployForm);

        String data = zkDeployMgr.getDeployInfo(configFullModel.getApp().getAppName(), configFullModel.getEnv().getEnvName(),
                                                   zkDeployForm.getVersion());

        return buildSuccess("hostInfo", data);
    }
}