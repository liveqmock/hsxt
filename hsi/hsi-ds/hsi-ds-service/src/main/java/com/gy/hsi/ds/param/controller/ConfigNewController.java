package com.gy.hsi.ds.param.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.ErrorCode;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.FileUploadException;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.common.utils.FileReadUtils;
import com.gy.hsi.ds.common.utils.FileUtils;
import com.gy.hsi.ds.common.utils.MyZipUtils;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.controller.form.AppNewForm;
import com.gy.hsi.ds.param.controller.form.ConfNewForm;
import com.gy.hsi.ds.param.controller.form.ConfNewItemForm;
import com.gy.hsi.ds.param.controller.validator.ConfigValidator;
import com.gy.hsi.ds.param.controller.validator.FileUploadValidator;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IConfigMgr;

/**
 * 专用于配置新建
 *
 * @author liaoqiqi
 * @version 2014-6-24
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/web/config")
public class ConfigNewController extends BaseController {

    protected static final Logger LOG = Logger.getLogger(ConfigNewController.class);

    @Autowired
    private IConfigMgr configMgr;
    
    @Autowired
    private IAppMgr appMgr;

    @Autowired
    private ConfigValidator configValidator;

    @Autowired
    private FileUploadValidator fileUploadValidator;

    /**
     * 配置项的新建
     *
     * @param confNewForm
     *
     * @return
     */
    @RequestMapping(value = "/item")
    @ResponseBody
    public JsonObjectBase newItem(@Valid ConfNewItemForm confNewForm) {

        // 业务校验
        configValidator.validateNew(confNewForm, DisConfigTypeEnum.ITEM);

        //
        configMgr.newConfig(confNewForm, DisConfigTypeEnum.ITEM);

        return buildSuccess("创建成功");
    }

    /**
     * 配置文件的新建(使用上传配置文件)
     *
     * @param confNewForm
     * @param file
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file")
    public JsonObjectBase updateFile(@Valid ConfNewForm confNewForm, @RequestParam("myfilerar") MultipartFile file) {

        //
        // 校验
        //
        Long fileSize = (long) (1024 * 1024 * 4);
        
        String[] allowExtName = {".properties", ".xml"};
        
        fileUploadValidator.validateFile(file, fileSize, allowExtName);

        //
        // 更新
        //
        String fileContent = "";
        try {

            fileContent = new String(file.getBytes(), "UTF-8");
            
            LOG.info("receive file: " + fileContent);
        } catch (Exception e) {
            LOG.error(e.toString());
            
            throw new FileUploadException("upload file error", e);
        }

        // 创建配置文件表格
        ConfNewItemForm confNewItemForm = new ConfNewItemForm(confNewForm);
        confNewItemForm.setKey(file.getOriginalFilename());
        confNewItemForm.setValue(fileContent);

        // 业务校验
        configValidator.validateNew(confNewItemForm, DisConfigTypeEnum.FILE);

        //
        configMgr.newConfig(confNewItemForm, DisConfigTypeEnum.FILE);

        return buildSuccess("创建成功");
    }
    
	/**
	 * 配置文件的新建(使用上传配置文件)
	 *
	 * @param zipFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUploadByBatch")
	public JsonObjectBase uploadFileByBatch(
			@RequestParam("myfilerar") MultipartFile zipFile) {
		// 校验
		fileUploadValidator.validateFile(zipFile, null, new String[] { ".zip",
				".ZIP" });

		StringBuilder errorInfo = new StringBuilder();

		String rootUnzipDir = FileUtils
				.assembleFilePathByUserDir("./tmp_upload_dir_unziped/"
						+ UUID.randomUUID().toString());

		try {
			MyZipUtils.unZip(zipFile.getInputStream(), rootUnzipDir);
		} catch (IOException e) {
			LOG.error("", e);
		}

		File rootPathName = new File(rootUnzipDir);
		File[] subFolders = rootPathName.listFiles();

		if (null == subFolders || 0 >= subFolders.length) {
			return buildSuccess("不能上传空的zip文件！");
		}

		for (File subFolder : subFolders) {
			if (!subFolder.isDirectory()) {
				continue;
			}

			String folderName = formatAppName(subFolder.getName());
			
			// 调度自己的参数不上传
			if ("HSI-DS".equalsIgnoreCase(folderName)
					|| "HSI_DS".equalsIgnoreCase(folderName)) {
				continue;
			}
			
			App app = this.doNewApp2Db(folderName);

			// 列出文件
			File[] confFiles = subFolder.listFiles();

			for (File confFile : confFiles) {
				if (!confFile.isFile()) {
					continue;
				}

				try {
					this.doNewConfigFile2Db(confFile, app.getId());
				} catch (FileUploadException e) {
					errorInfo.append(e.getErrorMessage()).append(
							System.lineSeparator());
				}
			}
		}

		if (0 < errorInfo.length()) {
			return buildGlobalError("创建失败:" + errorInfo.toString(),
					ErrorCode.UN_EXPECTED);
		}

		return buildSuccess("上传成功。");
	}
    
    /**
     * 配置文件的新建(使用文本)
     *
     * @param confNewForm
     * @param fileContent
     * @param fileName
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/filetext")
    public JsonObjectBase updateFileWithText(@Valid ConfNewForm confNewForm, @NotNull String fileContent,
                                             @NotNull String fileName) {

        // 创建配置文件表格
        ConfNewItemForm confNewItemForm = new ConfNewItemForm(confNewForm);
        confNewItemForm.setKey(fileName);
        confNewItemForm.setValue(fileContent);

        // 业务校验
        configValidator.validateNew(confNewItemForm, DisConfigTypeEnum.FILE);

        //
        configMgr.newConfig(confNewItemForm, DisConfigTypeEnum.FILE);

        return buildSuccess("创建成功");
    }

	/**
	 * 新建app
	 * 
	 * @param appName
	 * @return
	 */
	private App doNewApp2Db(String appName) {
		App app = appMgr.getByName(appName);
		
		if(null == app) {
			AppNewForm appNew = new AppNewForm();
			appNew.setApp(appName);
			appNew.setDesc(appName + ", 通过批量上传方式自动产生。");
			
			app = appMgr.create(appNew);
		}
		
		return app;
	}

	/**
	 * 将文件内容插入数据库
	 * 
	 * @param file
	 * @param appId
	 */
	private void doNewConfigFile2Db(File file, Long appId) {
	
		if (file.isDirectory()) {
			return;
		}
		
		String fileName = file.getName();

		// 过滤掉非法文件类型
		if ("disconf.properties".equals(fileName)
				|| (!fileName.endsWith(".properties") && !fileName.endsWith(".xml"))) {
			return;
		}
	
		// 文件内容
		String fileContent = "";
	
		String filePath = "/" + file.getParentFile().getName() + "/"
				+ file.getName();
	
		try {
			fileContent = new String(
					FileReadUtils.fileToLines(file).getBytes(), "UTF-8");
		} catch (IOException e) {
			LOG.error(e.toString(), e);
	
			throw new FileUploadException("文件" + filePath + "上传发生异常, 原因："
					+ e.toString());
		}	
	
		ConfNewForm confNewForm = new ConfNewForm();
		confNewForm.setAppId(appId);
		confNewForm.setVersion("1.0.0.0");
		confNewForm.setEnvId((long) 4); // Local环境
	
		// 创建配置文件表格
		ConfNewItemForm confNewItemForm = new ConfNewItemForm(confNewForm);
		confNewItemForm.setKey(fileName);
		confNewItemForm.setValue(fileContent);
	
		try {
			configMgr.deleteByAppIdAndConfigName(appId, fileName);
			configMgr.newConfig(confNewItemForm, DisConfigTypeEnum.FILE);
		} catch (Exception e) {
			throw new FileUploadException("文件" + filePath
					+ "上传发生异常, 原因：数据库发生异常, " + e.toString());
		}
	}

	/**
	 * 格式化app名称
	 * 
	 * @param folderName
	 * @return
	 */
	private String formatAppName(String folderName) {
		// if (folderName.contains("-")) {
		// return folderName.replaceAll("\\-", "_").toUpperCase();
		// }
	
		return folderName.toUpperCase();
	}
}