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
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService;
import com.gy.hsxt.uc.as.bean.auth.AsRole;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;

/**
 * 权限管理
 * 
 * @Package: com.gy.hsxt.access.web.os.ucm.controller
 * @ClassName: OperatorController
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-12-28 下午4:51:05
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/operator")
public class OperatorController {
	@Resource
	IUCAsRoleService roleService;

	@Resource
	IUCAsSysOperInfoService sysOperInfoService;

	@ResponseBody
	@RequestMapping(value = "/listOper", method = RequestMethod.POST)
	// 查询用户列表
	public HttpRespEnvelope listOper(
			@RequestParam(required = false) String platformCode,
			@RequestParam(required = false) String subSystemCode,
			@RequestParam(required = false) Short isAdmin,
			@RequestParam(required = false) String operCustId,
			@RequestParam(required = false) String userName,
			@RequestParam(required = false) String realName,
			@RequestParam(required = false, defaultValue = "100") Integer pageSize,
			@RequestParam(required = false, defaultValue = "1") Integer curPage,
			@RequestParam(required = false) String custId) {
		Page page = new Page(curPage, pageSize);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			PageData<AsSysOper> data = sysOperInfoService.listPermByPage(
					platformCode, subSystemCode, isAdmin, operCustId, userName,
					realName, page);
			if (data.getResult() != null) {
				for (AsSysOper o : data.getResult()) {
					o.setPwdLogin("********");
					o.setPwdLoginSaltValue(null);
				}
			}
			hre.setCurPage(curPage);
			if (data != null) {
				hre.setData(data.getResult());
				hre.setTotalRows(data.getCount());
			}
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
	@RequestMapping(value = "/addOper", method = RequestMethod.POST)
	public HttpRespEnvelope addOper(AsSysOper vo, String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			sysOperInfoService.regSysOper(vo, custId);
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
	@RequestMapping(value = "/updateOper", method = RequestMethod.POST)
	public HttpRespEnvelope updateOper(AsSysOper vo, String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			// 如果密码为*，表示未修改密码
			if (vo.getPwdLogin().equals("********")) {
				vo.setPwdLogin(null);
			}
			sysOperInfoService.updateSysOper(vo);
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
	@RequestMapping(value = "/listRole", method = RequestMethod.POST)
	// 查询可分配角色列表
	public HttpRespEnvelope listRole(
			@RequestParam(required = false) String platformCode,
			@RequestParam(required = false) String subSystemCode,
			@RequestParam(required = false) String entCustId,
			String operCustId, @RequestParam(required = false) String custId) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			List data = roleService.getRoleTree(platformCode, subSystemCode,
					entCustId, operCustId);
			System.out.println(operCustId + data);
			hre.setData(data);
			hre.setTotalRows(data.size());
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
	@RequestMapping(value = "/setRole", method = RequestMethod.POST)
	// 给用户设置角色
	public HttpRespEnvelope setRole(@RequestParam("ids[]") String[] ids,
			String operCustId, @RequestParam(required = false) String custId) {

		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			List<String> idList = Arrays.asList(ids);
			// System.out.println(operCustId+idList);
			roleService.resetRole(operCustId, idList, custId);
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
