package com.gy.hsi.ds.param.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.knightliao.apollo.utils.io.OsUtil;
import com.github.knightliao.apollo.utils.time.DateUtils;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.constant.DataFormatConst;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.constraint.validation.PageOrderValidator;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.DocumentNotFoundException;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.utils.MyZipUtils;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.vo.ConfListVo;
import com.gy.hsi.ds.param.beans.vo.MachineListVo;
import com.gy.hsi.ds.param.controller.form.ConfListForm;
import com.gy.hsi.ds.param.controller.form.VersionListForm;
import com.gy.hsi.ds.param.controller.validator.ConfigValidator;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IConfigMgr;

/**
 * 专用于配置读取
 *
 * @author liaoqiqi
 * @version 2014-6-22
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/web/config")
public class ConfigReadController extends BaseController {

	protected static final Logger LOG = LoggerFactory
			.getLogger(ConfigReadController.class);

	@Autowired
	private IConfigMgr configMgr;

	@Autowired
	private IAppMgr appMgr;

	@Autowired
	private ConfigValidator configValidator;

	/**
	 * 获取版本的List
	 *
	 * @return
	 */
	@RequestMapping(value = "/versionlist")
	@ResponseBody
	public JsonObjectBase getVersionList(@Valid VersionListForm versionListForm) {

		List<String> versionList = configMgr.getVersionListByAppEnv(
				versionListForm.getAppId(), versionListForm.getEnvId());

		return buildListSuccess(versionList, versionList.size());
	}

	/**
	 * 获取列表,有分页的
	 *
	 * @param confListForm
	 *
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonObjectBase getConfigList(@Valid ConfListForm confListForm) {

		// 设置排序方式
		confListForm.getPage().setOrderBy(Columns.CONFIG_NAME);
		confListForm.getPage().setOrder(PageOrderValidator.ASC);

		DaoPageResult<ConfListVo> configs = configMgr.getConfigList(
				confListForm, true, false);

		return buildListSuccess(configs);
	}

	/**
	 * 获取列表,有分页的, 没有ZK信息
	 *
	 * @param confListForm
	 *
	 * @return
	 */
	@RequestMapping(value = "/simple/list")
	@ResponseBody
	public JsonObjectBase getSimpleConfigList(@Valid ConfListForm confListForm) {

		// 设置排序方式
		confListForm.getPage().setOrderBy(Columns.CONFIG_NAME);
		confListForm.getPage().setOrder(PageOrderValidator.ASC);

		DaoPageResult<ConfListVo> configs = configMgr.getConfigList(
				confListForm, false, false);

		return buildListSuccess(configs);
	}

	/**
	 * 获取某个
	 *
	 * @param configId
	 *
	 * @return
	 */
	@RequestMapping(value = "/{configId}")
	@ResponseBody
	public JsonObjectBase getConfig(@PathVariable long configId) {

		// 业务校验
		configValidator.valideConfigExist(configId);

		ConfListVo config = configMgr.getConfVo(configId);

		return buildSuccess(config);
	}

	/**
	 * 获取 zk
	 *
	 * @param configId
	 *
	 * @return
	 */
	@RequestMapping(value = "/zk/{configId}")
	@ResponseBody
	public JsonObjectBase getZkInfo(@PathVariable long configId) {

		// 业务校验
		configValidator.valideConfigExist(configId);

		MachineListVo machineListVo = configMgr.getConfVoWithZk(configId);

		return buildSuccess(machineListVo);
	}

	/**
	 * 下载
	 *
	 * @param configId
	 *
	 * @return
	 */
	@RequestMapping(value = "/download/{configId}")
	public HttpEntity<byte[]> downloadDspBill(@PathVariable long configId) {

		// 业务校验
		configValidator.valideConfigExist(configId);

		ConfListVo config = configMgr.getConfVo(configId);
		HttpHeaders header = new HttpHeaders();

		byte[] res = null;

		try {
			res = config.getValue().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
		}

		if (res == null) {
			throw new DocumentNotFoundException(config.getKey());
		}

		String name = null;

		try {
			name = URLEncoder.encode(config.getKey(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		header.set("Content-Disposition", "attachment; filename=" + name);
		header.setContentLength(res.length);

		return new HttpEntity<byte[]>(res, header);
	}

	/**
	 * 批量下载配置文件
	 *
	 * @param confListForm
	 *
	 * @return
	 */
	@RequestMapping(value = "/downloadfilebatch")
	public HttpEntity<byte[]> downloadOneApp(@Valid ConfListForm confListForm) {

		App app = appMgr.getById(confListForm.getAppId());

		String prefixString = app.getAppName().toUpperCase() + "_" + "VERSION"
				+ confListForm.getVersion();

		String targetFileString = "./tmp" + File.separator + "zipped";

		OsUtil.makeDirs(targetFileString);

		targetFileString += File.separator + prefixString + ".zip";

		File targetFile = null;

		byte[] res = null;

		try {
			String inputFileRoot = configMgr.getDisonfFileListByApp(confListForm.getAppId(),
					confListForm.getEnvId(), confListForm.getVersion());

			// 将目录压缩
			MyZipUtils.zipFile(inputFileRoot, "*", targetFileString);

			targetFile = new File(targetFileString);

			res = IOUtils.toByteArray(new FileInputStream(targetFile));
		} catch (Exception e) {
			throw new DocumentNotFoundException("下载发生异常：" + e.getMessage());
		}
		
		HttpHeaders header = new HttpHeaders();

		header.set("Content-Disposition",
				"attachment; filename=" + targetFile.getName());
		header.setContentLength(res.length);

		return new HttpEntity<byte[]>(res, header);
	}
	
//	@RequestMapping(value = "/downloadfilebatch")
//	public HttpEntity<byte[]> downloadOneApp(@Valid ConfListForm confListForm) {
//
//		//
//		// get files
//		//
//		List<File> fileList = configMgr.getDisonfFileList(confListForm);
//
//		//
//		// prefix
//		//
//		App app = appMgr.getById(confListForm.getAppId());
//		
//		String prefixString = app.getAppName().toUpperCase() + "_" + "VERSION"
//				+ confListForm.getVersion();
//
//		HttpHeaders header = new HttpHeaders();
//
//		String targetFileString = "";
//		File targetFile = null;
//		byte[] res = null;
//
//		try {
//			targetFileString = TarUtils.tarFiles("tmp", prefixString, fileList);
//
//			targetFile = new File(targetFileString);
//
//			res = IOUtils.toByteArray(new FileInputStream(targetFile));
//		} catch (Exception e) {
//			throw new DocumentNotFoundException("");
//		}
//
//		header.set("Content-Disposition",
//				"attachment; filename=" + targetFile.getName());
//		header.setContentLength(res.length);
//
//		return new HttpEntity<byte[]>(res, header);
//	}

	/**
	 * 批量下载配置文件(下载所有APP配置文件)
	 *
	 * @return
	 */
	@RequestMapping(value = "/downloadAllAppFiles")
	public HttpEntity<byte[]> downloadAllApp() {

		String targetFileString = "./tmp" + File.separator + "zipped";

		OsUtil.makeDirs(targetFileString);

		targetFileString += File.separator
				+ "ALL_APP_VERSION1.0.0.0_"
				+ DateUtils.format(new Date(),
						DataFormatConst.COMMON_TIME_FORMAT) + ".zip";

		File targetFile = null;

		byte[] res = null;

		try {
			String inputFileRoot = configMgr.getAllDisonfFileList();

			// 将目录压缩
			MyZipUtils.zipFile(inputFileRoot, "*", targetFileString);

			targetFile = new File(targetFileString);

			res = IOUtils.toByteArray(new FileInputStream(targetFile));
		} catch (Exception e) {
			throw new DocumentNotFoundException("下载发生异常：" + e.getMessage());
		}
		
		HttpHeaders header = new HttpHeaders();

		header.set("Content-Disposition",
				"attachment; filename=" + targetFile.getName());
		header.setContentLength(res.length);

		return new HttpEntity<byte[]>(res, header);
	}
}
