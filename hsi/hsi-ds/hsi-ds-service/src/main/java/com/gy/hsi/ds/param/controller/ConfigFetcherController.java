package com.gy.hsi.ds.param.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.json.ValueVo;
import com.gy.hsi.ds.common.thirds.dsp.common.annotation.NoAuth;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.DocumentNotFoundException;
import com.gy.hsi.ds.common.utils.ValuesUtils;
import com.gy.hsi.ds.param.beans.ConfigFullModel;
import com.gy.hsi.ds.param.beans.bo.Config;
import com.gy.hsi.ds.param.controller.form.ConfForm;
import com.gy.hsi.ds.param.controller.validator.ConfigValidator4Fetch;
import com.gy.hsi.ds.param.service.IConfigFetchMgr;

/**
 * 配置获取Controller, Disconf-client专门使用的
 *
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/config")
public class ConfigFetcherController {

    protected static final Logger LOG = Logger.getLogger(ConfigFetcherController.class);

    @Autowired
    private ConfigValidator4Fetch configValidator4Fetch;

    @Autowired
    private IConfigFetchMgr configFetchMgr;

    /**
     * 获取配置项 Item
     *
     * @param confForm
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/item")
    @ResponseBody
    public ValueVo getItem(ConfForm confForm) {

        //
        // 校验
        //
        ConfigFullModel configModel = null;
        try
        {
            configModel = configValidator4Fetch.verifyConfForm(confForm);
        }
        catch (Exception e)
        {
            LOG.warn(e.toString());
            return ValuesUtils.getErrorVo(e.getMessage());
        }

        return configFetchMgr.getConfItemByParameter(configModel.getApp().getId(), configModel.getEnv().getId(),
                configModel.getVersion(), configModel.getKey());
    }

    /**
     * 获取配置文件
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/file")
    @ResponseBody
    public HttpEntity<byte[]> getFile(ConfForm confForm) {

        boolean hasError = false;

        //
        // 校验
        //
        ConfigFullModel configModel = null;
        try
        {
            configModel = configValidator4Fetch.verifyConfForm(confForm);
        }
        catch (Exception e)
        {
            LOG.error(e.toString());
            hasError = true;
        }

        if (hasError == false)
        {
            try
            {
                //
                Config config = configFetchMgr.getConfByParameter(configModel.getApp().getId(), 
                        configModel.getEnv().getId(), configModel.getVersion(), configModel.getKey(), DisConfigTypeEnum.FILE);
                if (config == null)
                {
                    hasError = true;
                    throw new DocumentNotFoundException(configModel.getKey());
                }

                return downloadDspBill(configModel.getKey(), config.getConfigValue());

            }
            catch (Exception e)
            {
                LOG.error(e.toString());
            }
        }

        if (confForm.getKey() != null)
        {
            throw new DocumentNotFoundException(confForm.getKey());
        }
        else
        {
            throw new DocumentNotFoundException("");
        }
    }

    /**
     * 下载
     *
     * @param fileName
     *
     * @return
     */
    public HttpEntity<byte[]> downloadDspBill(String fileName, String value) {

        HttpHeaders header = new HttpHeaders();
        byte[] res = value.getBytes();

        String name = null;

        try
        {
            name = URLEncoder.encode(fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        header.set("Content-Disposition", "attachment; filename=" + name);
        header.setContentLength(res.length);
        return new HttpEntity<byte[]>(res, header);
    }

}
