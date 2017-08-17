package com.gy.hsi.ds.param.controller;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.FileUploadException;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.param.controller.validator.ConfigValidator;
import com.gy.hsi.ds.param.controller.validator.FileUploadValidator;
import com.gy.hsi.ds.param.service.IConfigMgr;

/**
 * 专用于配置更新、删除
 *
 * @author liaoqiqi
 * @version 2014-6-24
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/web/config")
public class  ConfigUpdateController extends BaseController {

    protected static final Logger LOG = Logger.getLogger(ConfigUpdateController.class);

    @Autowired
    private IConfigMgr configMgr;

    @Autowired
    private ConfigValidator configValidator;

    @Autowired
    private FileUploadValidator fileUploadValidator;

    /**
     * 配置项的更新
     *
     * @param configId
     * @param value
     *
     * @return
     */
    @RequestMapping(value = "/item/{configId}")
    @ResponseBody
    public JsonObjectBase updateItem(@PathVariable long configId, String value) {

        // 业务校验
        configValidator.validateUpdateItem(configId, value);

        LOG.info("start to update config: " + configId);

        //
        // 更新, 并写入数据库
        //
        String emailNotification = "";
        emailNotification = configMgr.updateItemValue(configId, value);

        //
        // 通知ZK
        //
        configMgr.notifyZookeeper(configId);

        return buildSuccess(emailNotification);
    }

    /**
     * 配置文件的更新
     *
     * @param configId
     * @param file
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file/{configId}")
    public JsonObjectBase updateFile(@PathVariable long configId, @RequestParam("myfilerar") MultipartFile file) {

        //
        // 校验
        //
        Long fileSize = (long) (1024 * 1024 * 4);
        String[] allowExtName = {".properties", ".xml"};
        fileUploadValidator.validateFile(file, fileSize, allowExtName);

        // 业务校验
        configValidator.validateUpdateFile(configId, file.getOriginalFilename());

        //
        // 更新
        //
        String emailNotification = "";
        try {

            String str = new String(file.getBytes(), "UTF-8");
            LOG.info("receive file: " + str);

            emailNotification = configMgr.updateItemValue(configId, str);
            LOG.info("update " + configId + " ok");

        } catch (Exception e) {

            LOG.error(e.toString());
            throw new FileUploadException("upload file error", e);
        }

        //
        // 通知ZK
        //
        configMgr.notifyZookeeper(configId);

        return buildSuccess(emailNotification);
    }

    /**
     * 配置文件的更新(文本修改)
     *
     * @param configId
     * @param fileContent
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/filetext/{configId}")
    public JsonObjectBase updateFileWithText(@PathVariable long configId, @NotNull String fileContent) {

        //
        // 更新
        //
        String emailNotification = "";
        try {

            String str = new String(fileContent.getBytes(), "UTF-8");
            LOG.info("receive file: " + str);

            emailNotification = configMgr.updateItemValue(configId, str);
            LOG.info("update " + configId + " ok");

        } catch (Exception e) {
            LOG.error("upload file error", e);
            
            throw new FileUploadException("upload file error", e);
        }

        //
        // 通知ZK
        //
        configMgr.notifyZookeeper(configId);

        return buildSuccess(emailNotification);
    }

    /**
     * delete
     *
     * @return
     */
    @RequestMapping(value = "/delete/{configId}")
    @ResponseBody
    public JsonObjectBase delete(@PathVariable long configId) {

        configValidator.validateDelete(configId);

        configMgr.delete(configId);

        return buildSuccess("删除成功");
    }
}
