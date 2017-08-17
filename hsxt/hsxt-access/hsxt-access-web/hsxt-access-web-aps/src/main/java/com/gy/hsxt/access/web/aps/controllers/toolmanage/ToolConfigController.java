package com.gy.hsxt.access.web.aps.controllers.toolmanage;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gy.hsi.lc.client.log4j.SystemLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.access.web.aps.services.toolmanage.ICardStyleService;
import com.gy.hsxt.access.web.aps.services.toolmanage.IMcrKsnRecordService;
import com.gy.hsxt.access.web.aps.services.toolmanage.ISupplierService;
import com.gy.hsxt.access.web.aps.services.toolmanage.ToolTypeService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ConsumeKSN;
import com.gy.hsxt.bs.bean.tool.KsnExportRecord;
import com.gy.hsxt.bs.bean.tool.PointKSN;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.bs.bean.tool.ToolCategory;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.toolmanage
 * @className : ToolConfigController.java
 * @description : 工具配置信息
 * @author : maocy
 * @createDate : 2016-02-25
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toolConfigController")
public class ToolConfigController extends BaseController {

	@Resource
	private ToolTypeService toolTypeService;

	@Resource
	private ICardStyleService cardStyleService;

	@Resource
	private ISupplierService supplierService;// 供应商服务类

	@Resource
	private IMcrKsnRecordService mcrKsnRecordService;// 刷卡器KSN服务类

	/**
	 * 
	 * 方法名称：工具配置信息 方法描述：工具配置信息-工具类别信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all_list")
	public HttpRespEnvelope allList(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			List<ToolCategory> result = toolTypeService.queryToolCategoryAll();
			return new HttpRespEnvelope(result);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询刷卡器KSN生成记录 方法描述：查询刷卡器KSN生成记录
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findMcrKsnRecordList")
	public HttpRespEnvelope findMcrKsnRecordList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.mcrKsnRecordService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：分页查询互生卡样 方法描述：分页查询互生卡样
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCardStyleList")
	public HttpRespEnvelope findCardStyleList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.cardStyleService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：添加互生卡样 方法描述：互生卡样-添加互生卡样
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addCardStyle")
	public HttpRespEnvelope addCardStyle(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute CardStyle bean) {
		try {
			String entResNo = super.verifyPointNo(request);// 校验互生卡号
			String custId = request.getParameter("custId");// 操作员ID
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getCardStyleName(),
							ASRespCode.APS_WH_CARD_NAME_INVALID },
					new Object[] { bean.getMicroPic(),
							ASRespCode.APS_WH_MICROPIC_INVALID }, new Object[] {
							bean.getSourceFile(),
							ASRespCode.APS_WH_SOURCEFILE_INVALID },
					new Object[] { bean.getConfirmFile(),
							ASRespCode.APS_WH_CONFIRM_FILE_INVALID },
					new Object[] { bean.getReqOperator(),
							ASRespCode.APS_WH_REQOPERATOR_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] {
					bean.getCardStyleName(), 0, 20,
					ASRespCode.APS_CARD_NAME_LENGTH_INVALID });
			bean.setEntResNo(entResNo);
			bean.setEntCustId(custId);
			bean.setIsSpecial(false);
			bean.setIsDefault(false);
			this.cardStyleService.addCardStyle(bean);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：互生卡样的启用/停用 方法描述：互生卡样信息-互生卡样的启用/停用
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cardStyleEnableOrStop")
	public HttpRespEnvelope cardStyleEnableOrStop(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute CardStyle bean) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getCardStyleId(),
							ASRespCode.APS_WH_CARDID_INVALID }, new Object[] {
							bean.getEnableStatus(),
							ASRespCode.APS_WH_ENABLE_STATUS_INVALID },
					new Object[] { bean.getReqOperator(),
							ASRespCode.APS_WH_REQOPERATOR_INVALID });
			bean.setIsSpecial(false);
			bean.setIsDefault(false);
			this.cardStyleService.cardStyleEnableOrStop(bean);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询互生卡样 方法描述：互生卡样信息-依据互生卡样标识查询互生卡样
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @param cardStyleId
	 *            互生卡样标识
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCardStyleById")
	public HttpRespEnvelope findCardStyleById(HttpServletRequest request,
			HttpServletResponse response, String cardStyleId) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { cardStyleId,
					ASRespCode.APS_WH_CARDID_INVALID });
			CardStyle obj = this.cardStyleService
					.findCardStyleById(cardStyleId);
			return new HttpRespEnvelope(obj);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查看积分KSN信息 方法描述：查看积分KSN信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPointKSNInfo")
	public HttpRespEnvelope findPointKSNInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			String batchNo = request.getParameter("batchNo");// 批次号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { batchNo,
					ASRespCode.APS_WH_BATCHNO_INVALID });
			List<PointKSN> list = this.mcrKsnRecordService
					.queryPointKSNInfo(batchNo);
			return new HttpRespEnvelope(list);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查看消费KSN信息 方法描述：查看消费KSN信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findConsumeKSNInfo")
	public HttpRespEnvelope findConsumeKSNInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			String batchNo = request.getParameter("batchNo");// 批次号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { batchNo,
					ASRespCode.APS_WH_BATCHNO_INVALID });
			List<ConsumeKSN> list = this.mcrKsnRecordService
					.queryConsumeKSNInfo(batchNo);
			return new HttpRespEnvelope(list);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：生成积分KSN信息 方法描述：生成积分KSN信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createPointKSNInfo")
	public HttpRespEnvelope createPointKSNInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			String custName = request.getParameter("custName");// 操作员
			String operName = request.getParameter("operName");// 操作员名称
			custName = custName + "（" + operName + " ）";
			Integer number = CommonUtils.toInteger(request
					.getParameter("number"));// 生成数量
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { number,
					ASRespCode.APS_TOOL_NUMBER_ID_INVALID });
			String res = this.mcrKsnRecordService.createPointKSNInfo(number,
					custName);
			return new HttpRespEnvelope(res);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：导出积分KSN信息 方法描述：导出积分KSN信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @param bean
	 *            导出信息
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/exportPointKSNInfo")
	public void exportPointKSNInfo(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute KsnExportRecord bean)
			throws UnsupportedEncodingException {
		try {
			String custId = request.getParameter("custId");// 操作员ID
			String custName = request.getParameter("custName");// 操作员名称
			String fileName = request.getParameter("fileName");// 文件名称
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					bean.getBahctNo(), ASRespCode.APS_WH_BATCHNO_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(
			// new Object[] {bean.getRemark(), 0, 300,
			// ASRespCode.APS_TOOL_EXP_REMARK_LENGTH_INVALID}
					);
			bean.setCreatedBy(custId);
			bean.setCreatedName(custName);
			List<PointKSN> list = this.mcrKsnRecordService
					.exportPointKSNInfo(bean);
			StringBuffer sb = new StringBuffer();
			if (list != null) {
				for (PointKSN obj : list) {
					sb.append(obj.getDeviceSeqNo() + "," + obj.getKsnCode()
							+ "\r\n");
				}
			}
			this.exportTxt(response, fileName, sb.toString());
		} catch (Exception ex) {
			SystemLog.error(this.className, "exportConsumeKSNInfo",
					"导出积分KSN失败", ex);
		}
	}

	/**
	 * 
	 * 方法名称：查询导出记录数据
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryKsnExportRecord")
	public HttpRespEnvelope queryKsnExportRecord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String batchNo = request.getParameter("batchNo");// 操作员ID
			List<KsnExportRecord> list = this.mcrKsnRecordService
					.queryKsnExportRecord(batchNo);
			return new HttpRespEnvelope(list);
		} catch (Exception e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * //去掉空格、制表符、回车、换行并返回字符串
	 * 
	 * @param str
	 * @return
	 */
	public String replaceBlank(String str) {

		String dest = "";

		if (str != null) {

			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			/*
			 * 注：\n 回车( ) \t 水平制表符( ) \s 空格(\u0008)\r 换行( )
			 */

			Matcher m = p.matcher(str);

			dest = m.replaceAll("");

		}

		return dest;
	}

	/**
	 * 
	 * 方法名称：解析消费刷卡器KSN文件 方法描述：刷卡器KSN码管理-解析消费刷卡器KSN文件
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/parseKSNSeq", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public HttpRespEnvelope parseKSNSeq(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpRespEnvelope hre = null;
		try {
			super.verifySecureToken(request);
			response.setContentType("text/html;charset=UTF-8");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multipartRequest.getFileNames();
			// 判断文件是否存在
			if (!iter.hasNext()) {
				throw new HsException(ASRespCode.AS_UPLOAD_FILE_INVALID);
			}
			List<ConsumeKSN> listY = new ArrayList<ConsumeKSN>();// KSN码1附件
			List<ConsumeKSN> listE = new ArrayList<ConsumeKSN>();// KSN码2附件
			List<ConsumeKSN> listS = new ArrayList<ConsumeKSN>();// KSN码3附件
			List<ConsumeKSN> listQ = new ArrayList<ConsumeKSN>();// 序列号
			while (iter.hasNext()) {
				MultipartFile file = multipartRequest.getFile(iter.next());
				String fileName = file.getOriginalFilename();
				String fileType = fileName.substring(
						fileName.lastIndexOf(".") + 1,
						fileName.lastIndexOf(".") + 4);
				if ("ksnY".equals(file.getName())) {// KSN码1附件
					if (!fileType.toLowerCase().equals("txt")) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_Y_NOT_TXT);
					}
					try {
						InputStream in = file.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in));
						String str;
						while ((str = br.readLine()) != null) {
                            str = replaceBlank(str);
							// 去除空行
							if (!"".equals(str)) {
								String[] array = str.split(",");
								if (array.length != 3) {
									throw new HsException(
											ASRespCode.APS_TOOL_UPLOAD_FILE_Y_ERROR);
								}
								ConsumeKSN sn = new ConsumeKSN();
								sn.setKsnCodeY(array[0]);
								sn.setCiphertextY(array[1]);
								sn.setVaildY(array[2]);
								listY.add(sn);
                             }
						}
   					} catch (Exception e) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_Y_ERROR);
					}
					if (listY.isEmpty()) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_Y_CONTENT_IS_BLANK);
					}
				} else if ("ksnE".equals(file.getName())) {// KSN码2附件
					if (!fileType.toLowerCase().equals("txt")) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_E_NOT_TXT);
					}
					try {
						InputStream in = file.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in));
						String str;
						while ((str = br.readLine()) != null) {
							str = replaceBlank(str);
							// 去除空行
							if (!"".equals(str)) {
							String[] array = str.split(",");
							if (array.length != 3) {
								throw new HsException(
										ASRespCode.APS_TOOL_UPLOAD_FILE_E_ERROR);
							}
							ConsumeKSN sn = new ConsumeKSN();
							sn.setKsnCodeE(array[0]);
							sn.setCiphertextE(array[1]);
							sn.setVaildE(array[2]);
							listE.add(sn);
						  }
						}
					} catch (Exception e) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_E_ERROR);
					}
					if (listE.isEmpty()) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_E_CONTENT_IS_BLANK);
					}
				} else if ("ksnS".equals(file.getName())) {// KSN码3附件
					if (!fileType.toLowerCase().equals("txt")) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_S_NOT_TXT);
					}
					try {
						InputStream in = file.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in));
						String str;
						while ((str = br.readLine()) != null) {
							str = replaceBlank(str);
							// 去除空行
							if (!"".equals(str)) {
							String[] array = str.split(",");
							if (array.length != 3) {
								throw new HsException(
										ASRespCode.APS_TOOL_UPLOAD_FILE_S_ERROR);
							}
							ConsumeKSN sn = new ConsumeKSN();
							sn.setKsnCodeS(array[0]);
							sn.setCiphertextS(array[1]);
							sn.setVaildS(array[2]);
							listS.add(sn);
						}
						}
					} catch (Exception e) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_S_ERROR);
					}
					if (listS.isEmpty()) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_S_CONTENT_IS_BLANK);
					}
				} else if ("ksnQ".equals(file.getName())) {// 序列号
					if (!fileType.toLowerCase().equals("txt")) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_Q_NOT_TXT);
					}
					try {
						InputStream in = file.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in));
						String str;
						while ((str = br.readLine()) != null) {
							str = replaceBlank(str);
							// 去除空行
							if (!"".equals(str)) {
							ConsumeKSN sn = new ConsumeKSN();
							sn.setDeviceSeqNo(str);
							listQ.add(sn);
						}
						}
					} catch (Exception e) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_Q_ERROR);
					}
					if (listQ.isEmpty()) {
						throw new HsException(
								ASRespCode.APS_TOOL_UPLOAD_FILE_Q_CONTENT_IS_BLANK);
					}
				}
			}
			if (listY.size() != listE.size() || listY.size() != listS.size()
					|| listY.size() != listQ.size()) {
				throw new HsException(ASRespCode.APS_TOOL_UPLOAD_FILE_NUMBER);
			}
			for (int index = 0; index < listY.size(); index++) {
				ConsumeKSN obj = listY.get(index);
				obj.setKsnCodeE(listE.get(index).getKsnCodeE());
				obj.setCiphertextE(listE.get(index).getCiphertextE());
				obj.setVaildE(listE.get(index).getVaildE());
				obj.setKsnCodeS(listS.get(index).getKsnCodeS());
				obj.setCiphertextS(listS.get(index).getCiphertextS());
				obj.setVaildS(listS.get(index).getVaildS());
				obj.setDeviceSeqNo(listQ.get(index).getDeviceSeqNo());
			}
			hre = new HttpRespEnvelope(listY);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		response.getWriter().write(JSON.toJSONString(hre));
		response.getWriter().flush();
		return null;
	}

	/**
	 * 
	 * 方法名称：导入消费刷卡器KSN 方法描述：导入消费刷卡器KSN
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/importConsumeKSNInfo")
	public HttpRespEnvelope importConsumeKSNInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			String custName = request.getParameter("custName");// 操作员
			String operName = request.getParameter("operName");// 操作员名称
			custName = custName + "（" + operName + " ）";
			String beanJson = request.getParameter("beanJson");// json数据
			List<JSONObject> temp = JsonUtil.getObject(beanJson,
					ArrayList.class);
			List<ConsumeKSN> list = new ArrayList<ConsumeKSN>();
			for (JSONObject obj : temp) {
				list.add(JsonUtil.getObject(obj.toString(), ConsumeKSN.class));
			}
			this.mcrKsnRecordService.importConsumeKSNInfo(list, custName);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：导出消费KSN信息 方法描述：导出消费KSN信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @param bean
	 *            导出信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exportConsumeKSNInfo")
	public void exportConsumeKSNInfo(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute KsnExportRecord bean) {
		try {
			String custId = request.getParameter("custId");// 操作员ID
			String custName = request.getParameter("custName");// 操作员名称
			String fileName = request.getParameter("fileName");// 文件名称
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					bean.getBahctNo(), ASRespCode.APS_WH_BATCHNO_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(
			// new Object[] {bean.getRemark(), 0, 300,
			// ASRespCode.APS_TOOL_EXP_REMARK_LENGTH_INVALID}
					);
			bean.setCreatedBy(custId);
			bean.setCreatedName(custName);
			List<ConsumeKSN> list = this.mcrKsnRecordService
					.exportConsumeKSNInfo(bean);
			StringBuffer sb = new StringBuffer();
			if (list != null) {
				for (ConsumeKSN obj : list) {
					sb.append(obj.getDeviceSeqNo() + "," + obj.getKsnCodeY()
							+ "," + obj.getCiphertextY() + ","
							+ obj.getKsnCodeE() + "," + obj.getCiphertextE()
							+ "," + obj.getKsnCodeS() + ","
							+ obj.getCiphertextS() + "\r\n");
				}
			}
			this.exportTxt(response, fileName, sb.toString());
		} catch (Exception ex) {
			SystemLog.error(this.className, "exportConsumeKSNInfo",
					"导出消费KSN失败", ex);
		}
	}

	/**
	 * 
	 * 方法名称：分页查询供应商 方法描述：分页查询供应商
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplierList")
	public HttpRespEnvelope findSupplierList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.supplierService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：删除供应商 方法描述：供应商信息-删除供应商
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeSupplier")
	public HttpRespEnvelope removeSupplier(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String supplierId = request.getParameter("supplierId");// 供应商编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { supplierId,
					ASRespCode.APS_TOOL_SUPPLIER_ID_INVALID });
			this.supplierService.removeSupplier(supplierId);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询供应商 方法描述：供应商信息-依据供应商编号查询供应商详情
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplierById")
	public HttpRespEnvelope findSupplierById(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String supplierId = request.getParameter("supplierId");// 供应商编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { supplierId,
					ASRespCode.APS_TOOL_SUPPLIER_ID_INVALID });
			Supplier obj = this.supplierService.querySupplierById(supplierId);
			if (obj == null) {
				throw new HsException(
						ASRespCode.APS_TOOL_SUPPLIER_NOT_FOUND_INVALID);
			}
			return new HttpRespEnvelope(obj);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存供应商 方法描述：供应商信息-保存供应商
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param bean
	 *            供应商对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSupplier")
	public HttpRespEnvelope saveSupplier(HttpServletRequest request,
			@ModelAttribute Supplier bean) {
		try {
			super.verifySecureToken(request);
			String custName = request.getParameter("custName");// 操作员
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getSupplierName(),
							ASRespCode.APS_TOOL_SUPPLIERNAME_INVALID },
					new Object[] { bean.getCorpName(),
							ASRespCode.APS_TOOL_CORPNAME_INVALID },
					new Object[] { bean.getLinkMan(),
							ASRespCode.APS_TOOL_LINKMAN_INVALID },
					new Object[] { bean.getAddr(),
							ASRespCode.APS_TOOL_ADDR_INVALID }, new Object[] {
							bean.getMobile(),
							ASRespCode.APS_TOOL_MOBILE_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(
					new Object[] { bean.getSupplierName(), 1, 64,
							ASRespCode.APS_TOOL_SUPPLIERNAME_LENGTH_INVALID },
					new Object[] { bean.getCorpName(), 1, 256,
							ASRespCode.APS_TOOL_CORPNAME_LENGTH_INVALID },
					new Object[] { bean.getLinkMan(), 1, 64,
							ASRespCode.APS_TOOL_LINKMAN_LENGTH_INVALID },
					new Object[] { bean.getAddr(), 1, 255,
							ASRespCode.APS_TOOL_ADDR_LENGTH_INVALID },
					new Object[] { bean.getPhone(), 0, 20,
							ASRespCode.APS_TOOL_PHONE_LENGTH_INVALID },
					new Object[] { bean.getMobile(), 1, 20,
							ASRespCode.APS_TOOL_MOBILE_LENGTH_INVALID },
					new Object[] { bean.getFax(), 0, 20,
							ASRespCode.APS_TOOL_FAX_LENGTH_INVALID },
					new Object[] { bean.getEmail(), 0, 20,
							ASRespCode.APS_TOOL_EMAIL_LENGTH_INVALID },
					new Object[] { bean.getWebsite(), 0, 32,
							ASRespCode.APS_TOOL_WEBSITE_LENGTH_INVALID },
					new Object[] { bean.getRemark(), 0, 300,
							ASRespCode.APS_TOOL_REMARK_LENGTH_INVALID });
			if (StringUtils.isBlank(bean.getSupplierId())) {
				bean.setCreatedBy(custName);// 创建人
				this.supplierService.addSupplier(bean);
			} else {
				bean.setUpdatedBy(custName);// 修改人
				this.supplierService.modifySupplier(bean);
			}
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 导出TXT
	 * 
	 * @param response
	 * @param fileName
	 * @param result
	 */
	public void exportTxt(HttpServletResponse response, String fileName,
			String result) throws HsException {
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		ServletOutputStream outSTr = null;
		try {
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF-8") + ".txt");
			outSTr = response.getOutputStream();// 建立
			buff = new BufferedOutputStream(outSTr);
			write.append(result);
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (Exception ex) {
			SystemLog.error(this.className, "exportTxt", "文件导出失败", ex);
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception ex) {
				SystemLog.error(this.className, "exportTxt", "文件导出流关闭失败", ex);
			}
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return this.toolTypeService;
	}

}
