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

import java.util.Arrays;
import java.util.List;

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
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * 角色管理
 * 
 * @Package: com.gy.hsxt.access.web.os.ucm.controller
 * @ClassName: RoleController
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-12-28 下午4:51:05
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Resource
	IUCAsPermissionService permissionService;

	@Resource
	IUCAsRoleService roleService;

	@ResponseBody
	@RequestMapping(value = "/listRoleByPage", method = RequestMethod.POST)
	// 查询角色列表
	public HttpRespEnvelope listRoleByPage(@RequestParam(required = false) String platformCode,
			@RequestParam(required = false) String subSystemCode,
			@RequestParam(required = false) Short roleType,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer curPage,
			@RequestParam(required = false) String entCustId) {
		if (isBlank(platformCode)) {
			platformCode = null;
		}
		if (isBlank(subSystemCode)) {
			subSystemCode = null;
		}
		if (isBlank(entCustId)) {
			entCustId = null;
		}

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			Page page = new Page(curPage, pageSize);
			PageData<AsRole> data = roleService.listRoleByPage(platformCode, subSystemCode,
					roleType, entCustId, page);

			hre.setCurPage(curPage);
			hre.setData(data.getResult());
			hre.setTotalRows(data.getCount());
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
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public HttpRespEnvelope addRole(AsRole vo, String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			roleService.addRole(vo, custId);
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
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public HttpRespEnvelope updateRole(AsRole vo, String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			roleService.updateRole(vo, custId);
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
	@RequestMapping(value = "/listPerm", method = RequestMethod.POST)
	// 查询可分配权限列表
	public HttpRespEnvelope listPerm(@RequestParam(required = false) String platformCode,
			@RequestParam(required = false) String subSystemCode,
			@RequestParam(required = false) Short permType,
			@RequestParam(required = false) String parentId,
			@RequestParam(required = false) String roleId,
			@RequestParam(required = false) String custId) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			List data = permissionService.getRolePermTree(platformCode, subSystemCode, permType, parentId, roleId);
//System.out.println(data);
			hre.setData(data);
			hre.setTotalRows(data.size());
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
	@RequestMapping(value = "/setPerm", method = RequestMethod.POST)
	// 给角色设置权限 @RequestParam("idList[]") List<String> idList
	public HttpRespEnvelope setPerm(@RequestParam("ids[]") String[] ids, @RequestParam String roleId,
			@RequestParam String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			List<String> idList = Arrays.asList(ids);
			permissionService.resetPerm(roleId, idList, custId);
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
