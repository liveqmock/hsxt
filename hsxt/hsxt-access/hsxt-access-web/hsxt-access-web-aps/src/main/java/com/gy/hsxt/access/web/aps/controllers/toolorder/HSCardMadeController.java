/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService;
import com.gy.hsxt.access.web.aps.services.toolorder.HSCardMadeService;
import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.CardInOut;
import com.gy.hsxt.bs.bean.tool.CardInfo;
import com.gy.hsxt.bs.bean.tool.CardMarkConfig;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.CardMarkData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 互生卡制作配置单
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder
 * @ClassName: HSCardMadeController
 * @Description: TODO
 *
 * @author: zhangcy
 * @date: 2015-11-19 下午4:01:13
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("hscardmade")
public class HSCardMadeController extends BaseController {

	@Resource
	private HSCardMadeService hSCardMadeService;

	@Resource
	private UserCenterService userCenterService;

	@Resource
	private EntInfoService entInfoService;

	@Resource
	private IUCAsCardHolderService asCardHolderService;

	/**
	 * 互生卡初始密码查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findcardpwd" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCardPwd(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 配置单号
				String confNo = request.getParameter("confNo");
				// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(

						new Object[] { confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(),
								RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc() }

				);

				List<CardInfo> result = hSCardMadeService.findCardPwd(confNo);

				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 开卡
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/opencard" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope openCard(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{

				// 配置单编号
				String confNo = request.getParameter("confNo");
				// 当前操作员编号
				String operNo = request.getParameter("operNo");
				// 配置单编号
				String orderType = request.getParameter("orderType");

				// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(

						new Object[] { confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(),
								RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc() },
						new Object[] { operNo, RespCode.APS_SKGJZZ_GLXLH_OPERNO.getCode(),
								RespCode.APS_SKGJZZ_GLXLH_OPERNO.getDesc() }

				);

				// 开卡
				hSCardMadeService.openCard(confNo, operNo, orderType);

				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 关闭订单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/closeorder" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope closeOrder(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 订单编号
				String orderNo = request.getParameter("orderNo");

				hSCardMadeService.closeToolOrder(orderNo);

				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 制作单作成查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/zzdzc" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope zzdzc(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 订单编号
				String orderNo = request.getParameter("orderNo");
				// 企业客户号
				String hsCustId = request.getParameter("targetEntCustId");
				// 配置单号
				String confNo = request.getParameter("confNo");
				// 互生互生号
				String hsResNo = request.getParameter("targetEntResNo");

				// 查询制作单作成信息
				CardMarkData card = hSCardMadeService.queryCardConfigMarkData(orderNo, confNo, hsResNo);
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("card", card);
				if (HsResNoUtils.isPersonResNo(hsResNo))
				{
					// 消费者信息
					AsCardHolderAllInfo preInfo = asCardHolderService.searchAllInfo(hsCustId);
					result.put("preInfo", preInfo);
				} else
				{
					// 查询企业重要信息
					AsEntMainInfo entinfo = entInfoService.searchEntMainInfo(hsCustId);
					result.put("entinfo", entinfo);
				}
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 互生卡配置单制成
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/cardmark" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope cardMark(HttpServletRequest request, @ModelAttribute CardMarkConfig bean)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 用户名
				// String userName = request.getParameter("userName");
				// //密码
				// String password = request.getParameter("password");
				// //企业互生号
				// String entResNo = request.getParameter("entResNo");
				// //随机秘钥
				// String secretKey = request.getParameter("secretKey");

				// 双签鉴权 抛出异常则验证失败
				// userCenterService.doubleSign(userName, password,
				// entResNo,secretKey);
				// 卡制作
				hSCardMadeService.cardConfigMark(bean);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查看卡制作数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/querycardmark" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryCardMark(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 订单编号
				String orderNo = request.getParameter("orderNo");
				// 企业客户号
				String hsCustId = request.getParameter("targetEntCustId");
				// 配置单号
				String confNo = request.getParameter("confNo");
				// 互生互生号
				String hsResNo = request.getParameter("targetEntResNo");
				// 查询制作单作成信息
				CardMarkData card = hSCardMadeService.queryCardMarkData(orderNo, confNo, hsResNo);

				Map<String, Object> result = new HashMap<String, Object>();
				result.put("card", card);
				if (HsResNoUtils.isPersonResNo(hsResNo))
				{
					// 消费者信息
					// 消费者信息
					AsCardHolderAllInfo preInfo = asCardHolderService.searchAllInfo(hsCustId);
					result.put("preInfo", preInfo);
				} else
				{
					// 查询企业重要信息
					AsEntMainInfo entinfo = entInfoService.searchEntMainInfo(hsCustId);
					result.put("entinfo", entinfo);
				}
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询互生卡配置卡样
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/querycardstyle" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryCardStyle(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 客户id
				String custId = request.getParameter("custId");
				// 配置单编号
				String confNo = request.getParameter("confNo");
				// 订单编号
				// String orderNo = request.getParameter("orderNo");
				// 企业互生号
				// String entResNo = request.getParameter("entResNo");
				// 查询历史卡样
				// UploadCardStyleData result =
				// hSCardMadeService.findConfigCarStyle(orderNo, entResNo);
				// 获取随机秘钥
				String randKey = userCenterService.getRandomToken(custId);
				// 查询卡样锁定状态
				ToolConfig tool = hSCardMadeService.queryToolConfigDetail(confNo);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("card", null);
				map.put("randKey", randKey);
				map.put("tool", tool);
				httpRespEnvelope = new HttpRespEnvelope(map);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@ResponseBody
	@RequestMapping(value = { "/uploadcardstyle" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope uploadCardStyle(HttpServletRequest request, @ModelAttribute CardStyle card)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 用户名
				String userName = request.getParameter("userName");
				// 密码
				String password = request.getParameter("password");
				// 企业互生号
				String entResNo = request.getParameter("entResNo");
				// 配置单编号
				String confNo = request.getParameter("confNo");
				// 当前操作员编号
				String reqOperator = request.getParameter("reqOperator");
				// 随机秘钥
				String randKey = request.getParameter("randKey");
				// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(

						new Object[] { confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(),
								RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc() },
						new Object[] { reqOperator, RespCode.APS_SKGJZZ_GLXLH_OPERNO.getCode(),
								RespCode.APS_SKGJZZ_GLXLH_OPERNO.getDesc() },
						new Object[] { userName, RespCode.APS_HSKZZ_DOUBLESIGN_USERNAME.getCode(),
								RespCode.APS_HSKZZ_DOUBLESIGN_USERNAME.getDesc() },
						new Object[] { password, RespCode.APS_HSKZZ_DOUBLESIGN_PASSWORD.getCode(),
								RespCode.APS_HSKZZ_DOUBLESIGN_PASSWORD.getDesc() },
						new Object[] { entResNo, RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getCode(),
								RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getDesc() }

				);
				// 双签鉴权 抛出异常则验证失败
				userCenterService.doubleSign(userName, password, entResNo, randKey);
				// 上传卡样
				hSCardMadeService.uploadConfigCardStyle(card);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 互生卡入库查询
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/querycardin" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryCardIn(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 订单编号
				String orderNo = request.getParameter("orderNo");
				// 客户id
				// String custId = request.getParameter("custId");
				// 查询卡入库信息
				CardInOut card = hSCardMadeService.queryCardInOutDetail(orderNo);
				// 随机token
				// String randKey = userCenterService.getRandomToken(custId);

				Map<String, Object> result = new HashMap<String, Object>();
				result.put("card", card);
				// result.put("randKey", randKey);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 互生卡入库
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/cardin" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope cardIn(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 用户名
				// String userName = request.getParameter("userName");
				// 密码
				// String password = request.getParameter("password");
				// 企业互生号
				// String entResNo = request.getParameter("entResNo");
				// 配置单编号
				String confNo = request.getParameter("confNo");
				// 当前操作员编号
				String operNo = request.getParameter("operNo");
				// 随机秘钥
				// String randKey = request.getParameter("randKey");
				// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(

						new Object[] { confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(),
								RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc() },
						new Object[] { operNo, RespCode.APS_SKGJZZ_GLXLH_OPERNO.getCode(),
								RespCode.APS_SKGJZZ_GLXLH_OPERNO.getDesc() }
				// new Object[] {userName,
				// RespCode.APS_HSKZZ_DOUBLESIGN_USERNAME.getCode(),
				// RespCode.APS_HSKZZ_DOUBLESIGN_USERNAME.getDesc()},
				// new Object[] {password,
				// RespCode.APS_HSKZZ_DOUBLESIGN_PASSWORD.getCode(),
				// RespCode.APS_HSKZZ_DOUBLESIGN_PASSWORD.getDesc()},
				// new Object[] {entResNo,
				// RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getCode(),
				// RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getDesc()}

				);

				// 双签鉴权 抛出异常则验证失败
				// userCenterService.doubleSign(userName, password,
				// entResNo,randKey);
				hSCardMadeService.cardConfigEnter(confNo, operNo);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 修改卡样锁定状态
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/modifycarylock" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope modifyCaryLock(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 配置单编号
				String confNo = request.getParameter("confNo");
				// 锁状态 1：锁定 非1：解锁
				String islock = request.getParameter("islock");

				hSCardMadeService.modifyCaryStyleLockStatus(confNo, islock.equals("1"));
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 导出密码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportmm")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response)
	{
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try
		{
			// 配置单编号
			String confNo = request.getParameter("confNo");
			String companyResNo = request.getParameter("companyResNo");

			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode(companyResNo + "_密码", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
			// response.addHeader("Content-Disposition", "attachment; filename="
			// + codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();

			// 查询密码
			List<CardInfo> list = hSCardMadeService.findCardPwd(confNo);
			if (list != null && list.size() > 0)
			{
				HSSFRow row = sheet.createRow((int) 0);// 创建一行

				HSSFCell cell = row.createCell((int) 0);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("互生卡号");

				cell = row.createCell((int) 1);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("互生卡密码");
				for (int i = 0; i < list.size(); i++)
				{
					row = sheet.createRow((int) (i + 1));

					cell = row.createCell((int) 0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(list.get(i).getPerResNo());

					cell = row.createCell((int) 1);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(list.get(i).getInitPwd());
				}
			}
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (fOut != null)
				{
					fOut.flush();
					fOut.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出暗码(制作卡数据下载)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportdark")
	public void exportDark(HttpServletRequest request, HttpServletResponse response)
	{
		// 生成提示信息
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try
		{
			// 配置单编号
			String confNo = request.getParameter("confNo");
			String companyResNo = request.getParameter("companyResNo");

			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode(companyResNo + "_制作卡数据", "UTF-8");

			response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();

			// 查询暗码
			List<CardInfo> list = hSCardMadeService.exportCardDarkCode(confNo);
			if (list != null && list.size() > 0)
			{
				HSSFRow row = sheet.createRow((int) 0);// 创建一行
				HSSFCell cell = row.createCell((int) 0);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("互生卡号及暗码");
//				cell = row.createCell((int) 1);// 创建一列
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue("互生卡暗码");
				for (int i = 0; i < list.size(); i++)
				{
					row = sheet.createRow((int) (i + 1));

					cell = row.createCell((int) 0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(list.get(i).getPerResNo()+"="+list.get(i).getDarkCode());

//					cell = row.createCell((int) 1);// 创建一列
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(list.get(i).getDarkCode());
				}
			}
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (fOut != null)
				{
					fOut.flush();
					fOut.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	protected IBaseService getEntityService()
	{
		return hSCardMadeService;
	}

}
