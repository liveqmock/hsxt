/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.systemmanage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.systemmanage.OperatorService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 
 * 操作员控制器
 * 
 * @Package: com.gy.hsxt.access.web.mcs.controllers.systemmanage
 * @ClassName: OperatorController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2016-1-9 下午12:10:20
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("operator")
public class OperatorController extends BaseController {

	@Resource
	private OperatorService operatorService;

	@Resource
	private IUCLoginService ucLoginService;

	@Resource
    private IUCAsPwdService ucAsPwdService;
	
	/**
	 * 添加操作员
	 * 
	 * @param request
	 * @param apprParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope add(HttpServletRequest request, @ModelAttribute AsOperator oper)
	{
		try
		{

			super.verifySecureToken(request);
			// 操作人客户号
			String adminCustId = request.getParameter("adminCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID },
					new Object[] { oper.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID },
					new Object[] { oper.getEntResNo(), RespCode.MW_OPRATOR_ENTRESNO },
					new Object[] { oper.getUserName(), RespCode.MW_OPRATOR_USERNAME },
					new Object[] { oper.getLoginPwd(), RespCode.MW_OPRATOR_LOGINPWD });

			String operId = operatorService.addOper(oper, adminCustId);
			return new HttpRespEnvelope(operId);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 修改操作员
	 * 
	 * @param request
	 * @param oper
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope update(HttpServletRequest request, @ModelAttribute AsOperator oper)
	{
		try
		{

			super.verifySecureToken(request);
			// 操作人
			String adminCustId = request.getParameter("adminCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID },
					new Object[] { oper.getUserName(), RespCode.MW_OPRATOR_USERNAME },
					new Object[] { oper.getOperCustId(), RespCode.MW_OPRATOR_OPTCUSTID });

			operatorService.updateOper(oper, adminCustId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 删除操作员
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope delete(HttpServletRequest request)
	{
		try
		{

			super.verifySecureToken(request);
			// 管理员客户号
			String adminCustId = request.getParameter("adminCustId");
			// 操作员客户号
			String operCustId = request.getParameter("operCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(

			new Object[] { adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID }, new Object[] { operCustId,
					RespCode.MW_OPRATOR_OPTCUSTID }

			);
			operatorService.deleteOper(operCustId, adminCustId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询操作员
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope query(HttpServletRequest request)
	{
		try
		{

			super.verifySecureToken(request);
			// 操作员客户号
			String operCustId = request.getParameter("operCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(

			new Object[] { operCustId, RespCode.MW_OPRATOR_OPTCUSTID }

			);
			AsOperator result = operatorService.searchOperByCustId(operCustId);
			return new HttpRespEnvelope(result);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询操作员列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryList(HttpServletRequest request)
	{
		try
		{

			super.verifySecureToken(request);
			// 企业客户号
			String entCustId = request.getParameter("entCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(

			new Object[] { entCustId, RespCode.MW_OPRATOR_ENTCUSNTID }

			);
			List<AsOperator> result = operatorService.listOperByEntCustId(entCustId);
			return new HttpRespEnvelope(result);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 互生卡绑定
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindcard", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope bindCard(HttpServletRequest request)
	{
		try
		{

			super.verifySecureToken(request);

			// 操作员客户号
			String operCustId = request.getParameter("operCustId");
			// 操作员互生号
			String hsNo = request.getParameter("hsNo");
			// 操作者（管理员）客户号
			String adminCustId = request.getParameter("adminCustId");
			// 操作者（管理员）登录密码
			String loginPwd = request.getParameter("loginPwd");
			// 操作者（管理员）密码加密token
			String randomToken = request.getParameter("randomToken");
			
			//验证管理员 只有管理员验证（登录）通过才能进行下一步操作（绑定互生号）
			ucAsPwdService.checkLoginPwd(adminCustId, loginPwd, "3", randomToken);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(

			new Object[] { operCustId, RespCode.MW_OPRATOR_OPTCUSTID }, new Object[] { hsNo,
					RespCode.MW_OPRATOR_HSNOBIND }, new Object[] { adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID }

			);
			operatorService.bindCard(operCustId, hsNo, adminCustId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/unbindcard", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope unBindCard(HttpServletRequest request)
	{
		try
		{

			super.verifySecureToken(request);
			// 操作员客户号
			String operCustId = request.getParameter("operCustId");

			//操作者（管理员）客户号
            String adminCustId = request.getParameter("custId");
            //操作者（管理员）登录密码
            String loginPwd = request.getParameter("loginPwd");
            //随机token
            String randomToken = request.getParameter("randomToken");
            
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(

			new Object[] { operCustId, RespCode.MW_OPRATOR_OPTCUSTID }

			);
			
            //验证管理员 只有管理员验证（登录）通过才能进行下一步操作（解绑互生号）
			ucAsPwdService.checkLoginPwd(adminCustId, loginPwd, "3", randomToken);
			
			operatorService.unBindCard(operCustId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 操作员信息
	 * 
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("get_operator_login_detail")
	public HttpRespEnvelope operatorDetail(HttpServletRequest request, SCSBase scsBase) throws IOException
	{
		try
		{
			// 操作员登录信息
			Map<String, Object> retMap = operatorService.getOperatorDetail(scsBase);
			return new HttpRespEnvelope(retMap);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService()
	{
		return operatorService;
	}

}
