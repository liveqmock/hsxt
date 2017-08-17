/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.web.os.ucm.controller;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

/**
 * 权限管理
 * 
 * @Package: com.gy.hsxt.access.web.os.ucm.controller
 * @ClassName: PermissionController
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-12-28 下午4:51:05
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

	@Resource
	IUCAsPermissionService permissionService;

	@ResponseBody
	@RequestMapping(value = "/listPermByPage", method = RequestMethod.POST)
	// 查询权限列表
	public HttpRespEnvelope listPermByPage(@RequestParam(required = false) String platformCode,
			@RequestParam(required = false) String subSystemCode,
			@RequestParam(required = false) Short permType,
			@RequestParam(required = false) String parentId,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer curPage,
			@RequestParam(required = false) String custId) {
		if (isBlank(platformCode)) {
			platformCode = null;
		}
		if (isBlank(subSystemCode)) {
			subSystemCode = null;
		}
		if (isBlank(parentId)) {
			parentId = null;
		}
		if (isBlank(custId)) {
			custId = null;
		}

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			Page page = new Page(curPage, pageSize);
			PageData<AsPermission> data = permissionService.listPermByPage(platformCode,
					subSystemCode, permType, parentId, page);

			hre.setCurPage(curPage);
			hre.setData(data.getResult());
			hre.setTotalRows(data.getCount());
			return hre;
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPerm", method = RequestMethod.POST)
	public HttpRespEnvelope addPerm(AsPermission vo, String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			permissionService.addPerm(vo, custId);
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}

		return hre;
	}

	@ResponseBody
	@RequestMapping(value = "/updatePerm", method = RequestMethod.POST)
	public HttpRespEnvelope updatePerm(AsPermission vo, String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			permissionService.updatePerm(vo, custId);
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
