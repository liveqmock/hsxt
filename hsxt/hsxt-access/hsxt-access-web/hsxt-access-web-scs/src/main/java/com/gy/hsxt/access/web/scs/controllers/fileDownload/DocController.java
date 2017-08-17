/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.fileDownload;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.fileDownload.DownloadFile;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.scs.services.common.PubParamService;
import com.gy.hsxt.access.web.scs.services.filedownload.IBusinessDocService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 
 * 文档下载
 * 
 * @Package: com.gy.hsxt.access.web.scs.controllers.fileDownload
 * @ClassName: DocController
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-27 下午12:09:37
 * @version V1.0
 */
@Controller
@RequestMapping("fileDownload")
public class DocController extends BaseController {
	
	@Autowired
	private IBusinessDocService docService;
	
	@Autowired
	PubParamService pubService;

	/**
	 * 合同文档列表
	 * 
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "listDownloadFile", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope listDownloadFile(Page page, HttpServletResponse res) throws IOException {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			LocalInfo systemInfo = pubService.findSystemInfo();
			List<DownloadFile> list = DownloadFile.bulidDownloadFileList();
			for (DownloadFile df : list) {
				df.setPlatName(systemInfo.getPlatNameCn());
			}
			hre.setData(list);
			hre.setTotalRows(list.size());
			hre.setCurPage(1);
		} catch (Exception e) {
			// 异常信息封装
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	@Override
	protected IBaseService getEntityService() {
		return this.docService;
	}
}
