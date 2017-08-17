package com.gy.hsxt.access.web.aps.controllers.toolmanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.aps.services.systemmanage.RoleService;
import com.gy.hsxt.access.web.aps.services.toolmanage.IWarehouseInventoryService;
import com.gy.hsxt.access.web.bean.toolmanage.ASDeviceSeqNo;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.base.ApprInfo;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.tool.DeviceUseRecord;
import com.gy.hsxt.bs.bean.tool.Enter;
import com.gy.hsxt.bs.bean.tool.InstorageInspection;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.ToolProductUpDown;
import com.gy.hsxt.bs.bean.tool.Warehouse;
import com.gy.hsxt.bs.bean.tool.WhArea;
import com.gy.hsxt.bs.bean.tool.WhInventory;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceSeqNo;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.toolmanage
 * @className : WarehouseInventoryController.java
 * @description : 仓库库存管理
 * @author : maocy
 * @createDate : 2015-12-30
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("warehouseInventoryController")
public class WarehouseInventoryController extends BaseController {

	@Resource
	private IWarehouseInventoryService warehouseInventoryService;// 仓库库存管理服务类

	@Resource
	private RoleService roleService;// 角色服务类

	/**
	 * 
	 * 方法名称：查询所有仓库 方法描述：工具入库-查询所有仓库
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAllWarehouseList")
	public HttpRespEnvelope findAllWarehouseList(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			List<Warehouse> list = this.warehouseInventoryService
					.findAllWarehouseList();
			Map<String, String> tmpMap = new LinkedHashMap<String, String>();
			if (list != null) {
				for (Warehouse obj : list) {
					tmpMap.put(obj.getWhId(), obj.getWhName());
				}
			}
			return new HttpRespEnvelope(tmpMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询所有供应商 方法描述：工具入库-查询所有供应商
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAllupplierList")
	public HttpRespEnvelope findAllupplierList(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			List<Supplier> list = this.warehouseInventoryService
					.findAllupplierList();
			Map<String, String> tmpMap = new LinkedHashMap<String, String>();
			if (list != null) {
				for (Supplier obj : list) {
					tmpMap.put(obj.getSupplierId(), obj.getSupplierName());
				}
			}
			return new HttpRespEnvelope(tmpMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolProductList")
	public HttpRespEnvelope findToolProductList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifySecureToken(request);
			String categoryCode = request.getParameter("categoryCode");// 工具类别
			List<ToolProduct> list = this.warehouseInventoryService
					.findToolProducts(categoryCode);
			Map<String, String> tmpMap = new LinkedHashMap<String, String>();
			if (list != null) {
				for (ToolProduct obj : list) {
					tmpMap.put(obj.getProductId(), obj.getProductName());
				}
			}
			return new HttpRespEnvelope(tmpMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 *
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 *
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolProductAll")
	public HttpRespEnvelope findToolProductAll(HttpServletRequest request,
												HttpServletResponse response) {
		try {
			super.verifySecureToken(request);
			String categoryCode = request.getParameter("categoryCode");// 工具类别
			List<ToolProduct> list = this.warehouseInventoryService
					.findToolProductAll(categoryCode);
			Map<String, String> tmpMap = new LinkedHashMap<String, String>();
			if (list != null) {
				for (ToolProduct obj : list) {
					tmpMap.put(obj.getProductId(), obj.getProductName());
				}
			}
			return new HttpRespEnvelope(tmpMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolProductPage")
	public HttpRespEnvelope findToolProductPage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifySecureToken(request);

			request.setAttribute("serviceName", warehouseInventoryService);
			request.setAttribute("methodName", "findToolProductList");
			return super.doList(request, response);

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：新增工具详情 方法描述：工具入库-新增工具详情
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addToolProduct")
	public HttpRespEnvelope addToolProduct(HttpServletRequest request,
			@ModelAttribute ToolProduct toolProduct) {
		try {
			super.verifySecureToken(request);
			String optId = request.getParameter("custId");
			String optName = request.getParameter("custName");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { optId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID }, new Object[] {
					optName, ASRespCode.APS_SMRZSP_OPTNAME_NOT_NULL },
					new Object[] { toolProduct.getCategoryCode(),
							ASRespCode.APS_WHI_CATEGORYCODE_INVALID },
					new Object[] { toolProduct.getProductName(),
							ASRespCode.APS_WHI_PRODUCTID_INVALID },
					new Object[] { toolProduct.getMicroPic(),
							ASRespCode.APS_TOOL_PICTURE_EMPTY }, new Object[] {
							toolProduct.getPrice(),
							ASRespCode.APS_TOOL_PRICE_NUMBER_EMPTY });
			toolProduct.setOptId(optId);
			toolProduct.setOptName(optName);
			this.warehouseInventoryService.addToolProduct(toolProduct);
			HttpRespEnvelope he = new HttpRespEnvelope();
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 *
	 * 方法名称：修改工具
	 *
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyToolProduct")
	public HttpRespEnvelope modifyToolProduct(HttpServletRequest request,
										   @ModelAttribute ToolProduct toolProduct) {
		try {
			super.verifySecureToken(request);
			this.warehouseInventoryService.modifyToolProduct(toolProduct);
			HttpRespEnvelope he = new HttpRespEnvelope();
			return he;
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具查看详情 方法描述：工具入库-查询工具查看详情
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryToolProductInfo")
	public HttpRespEnvelope queryToolProductById(HttpServletRequest request,
			String productId) {
		try {
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { productId,
					ASRespCode.AS_APPLYID_INVALID });
			ToolProduct toolProduct = this.warehouseInventoryService
					.queryToolProductById(productId);
			HttpRespEnvelope he = new HttpRespEnvelope();
			he.setData(toolProduct);
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具查看详情 方法描述：工具入库-查询工具查看详情
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryToolProductUpDownById")
	public HttpRespEnvelope queryToolProductUpDownById(
			HttpServletRequest request, String productId, String lastApplyId) {
		try {
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { productId,
					ASRespCode.AS_APPLYID_INVALID });
			ToolProductUpDown toolProduct = null;
			if (lastApplyId.isEmpty()) {
				toolProduct = this.warehouseInventoryService
						.queryToolProductUpDownByProductId(productId);
			} else {
				toolProduct = this.warehouseInventoryService
						.queryToolProductUpDownById(lastApplyId);
			}
			HttpRespEnvelope he = new HttpRespEnvelope();
			he.setData(toolProduct);
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：工具价格修改 方法描述：工具入库-工具价格修改
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyChangePrice")
	public HttpRespEnvelope applyChangePrice(HttpServletRequest request,
			String productId, String applyPrice) {
		HttpRespEnvelope he = null;
		try {
			super.verifySecureToken(request);
			String optId = request.getParameter("custId");
			String optName = request.getParameter("custName");
			Operator oprator = new Operator();
			oprator.setOptId(optId);
			oprator.setOptName(optName);
			this.warehouseInventoryService.applyChangePrice(productId,
					applyPrice, oprator);
			he = new HttpRespEnvelope();
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：工具产品下架申请 方法描述：工具入库-工具产品下架申请
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyToolProductToDown")
	public HttpRespEnvelope applyToolProductToDown(HttpServletRequest request,
			String productId, String downReason) {
		HttpRespEnvelope he = null;
		try {
			super.verifySecureToken(request);
			String optId = request.getParameter("custId");
			String optName = request.getParameter("custName");
			Operator oprator = new Operator();
			oprator.setOptId(optId);
			oprator.setOptName(optName);
			this.warehouseInventoryService.applyToolProductToDown(productId,
					downReason, oprator);
			he = new HttpRespEnvelope();
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具下架审批列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolDoawnList")
	public HttpRespEnvelope findToolDoawnList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifySecureToken(request);

			request.setAttribute("serviceName", warehouseInventoryService);
			request.setAttribute("methodName", "findToolDoawnList");
			return super.doList(request, response);

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：工具产品下架审批
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/apprToolProductForDown")
	public HttpRespEnvelope apprToolProductForDown(HttpServletRequest request,
			@ModelAttribute ApprInfo apprInfo) {
		try {
			super.verifySecureToken(request);
			String optId = request.getParameter("custId");
			String optName = request.getParameter("custName");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { optId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID }, new Object[] {
					optName, ASRespCode.APS_SMRZSP_OPTNAME_NOT_NULL },
					new Object[] { apprInfo.getApplyId(),
							ASRespCode.AS_APPLYID_INVALID }, new Object[] {
							apprInfo.isPass(),
							ASRespCode.ASP_DOC_TEMP_APPRSTATUS_INVALID });
			apprInfo.setPass(apprInfo.isPass());
			apprInfo.setOptId(optId);
			apprInfo.setOptName(optName);
			this.warehouseInventoryService.apprToolProductForDown(apprInfo);
			HttpRespEnvelope he = new HttpRespEnvelope();
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具价格审批列表 方法描述：工具入库-查询工具价格审批列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolPriceList")
	public HttpRespEnvelope findToolPriceList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifySecureToken(request);

			request.setAttribute("serviceName", warehouseInventoryService);
			request.setAttribute("methodName", "findToolPriceList");
			return super.doList(request, response);

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：工具产品上架(价格)审批
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/apprToolProductForUp")
	public HttpRespEnvelope apprToolProductForUp(HttpServletRequest request,
			@ModelAttribute ApprInfo apprInfo) {
		try {
			super.verifySecureToken(request);
			String optId = request.getParameter("custId");
			String optName = request.getParameter("custName");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { optId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID }, new Object[] {
					optName, ASRespCode.APS_SMRZSP_OPTNAME_NOT_NULL },
					new Object[] { apprInfo.getApplyId(),
							ASRespCode.AS_APPLYID_INVALID }, new Object[] {
							apprInfo.isPass(),
							ASRespCode.ASP_DOC_TEMP_APPRSTATUS_INVALID });
			apprInfo.setPass(apprInfo.isPass());
			apprInfo.setOptId(optId);
			apprInfo.setOptName(optName);
			this.warehouseInventoryService.apprToolProductForUp(apprInfo);
			HttpRespEnvelope he = new HttpRespEnvelope();
			return he;

		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具价格审批列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refuseAndHoldOperate")
	public HttpRespEnvelope refuseAndHoldOperate(HttpServletRequest request,
			String bizNo, String businessType) {
		try {
			super.verifySecureToken(request);
			String optId = request.getParameter("custId");
			int taskStatus = TaskStatus.UNACCEPT.getCode(); // 未受理
			// 未受理
			if (!StringUtils.isEmpty(businessType)
					&& businessType.equals(TaskStatus.UNACCEPT.getCode()
							.toString())) {
				taskStatus = TaskStatus.UNACCEPT.getCode();
			}
			// 已挂起
			else if (!StringUtils.isEmpty(businessType)
					&& businessType.equals(TaskStatus.HANG_UP.getCode()
							.toString())) {
				taskStatus = TaskStatus.HANG_UP.getCode();
			}

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bizNo,
					ASRespCode.EW_TOOL_ORDERNO_INVALID }, new Object[] { optId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID });

			this.warehouseInventoryService.refuseAndHoldOperate(bizNo,
					taskStatus, optId);

			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：入库提交 方法描述：工具入库-入库提交
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/productEnter")
	public HttpRespEnvelope productEnter(HttpServletRequest request,
			@ModelAttribute Enter bean) {
		try {
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getCategoryCode(),
							ASRespCode.APS_WHI_CATEGORYCODE_INVALID },
					new Object[] { bean.getProductId(),
							ASRespCode.APS_WHI_PRODUCTID_INVALID },
					new Object[] { bean.getSupplierId(),
							ASRespCode.APS_WHI_SUPPLIERID_INVALID },
					new Object[] { bean.getWhId(),
							ASRespCode.APS_WHI_WHID_INVALID });
			// POS机与平板需要检查机器码
			if ("P_POS".equals(bean.getCategoryCode())
					|| "TABLET".equals(bean.getCategoryCode())) {
				List<String> list = CommonUtils.toStringList(
						request.getParameter("deviceSeqNos"), ",");
				if (list.isEmpty()) {
					throw new HsException(
							ASRespCode.APS_WHI_DEVICESEQNOS_INVALID);
				}
				bean.setDeviceSeqNo(list);
			} else if ("POINT_MCR".equals(bean.getCategoryCode())
					|| "CONSUME_MCR".equals(bean.getCategoryCode())) {// 积分刷卡器与消费刷卡器需要检查批次号
				if (StringUtils.isBlank(bean.getEnterBatchNo())) {
					throw new HsException(ASRespCode.APS_WHI_ENTERBATCH_INVALID);
				}
			}
			this.warehouseInventoryService.productEnter(bean);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：解析机器码文件 方法描述：工具入库-解析机器码文件
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeviceSeq", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html;;charset=UTF-8")
	public HttpRespEnvelope getDeviceSeq(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
			MultipartFile multipartFile = multipartRequest.getFile(iter.next());
			// 判断文件名
			String fileName = multipartFile.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.lastIndexOf(".") + 4);
			if (!fileType.toLowerCase().equals("xls")) {
				throw new HsException(ASRespCode.AS_UPLOAD_FILE_NOT_EXCEL);
			}
			// 存入机器码信息
			StringBuffer sb = new StringBuffer();
			try {
				HSSFWorkbook wb = new HSSFWorkbook(
						multipartFile.getInputStream());
				HSSFSheet sheet = wb.getSheetAt(0);
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					// 创建一个行对象
					HSSFRow row = sheet.getRow(i);
					// 把一行里的每一个字段遍历出来
					for (int j = 0; j < 1; j++) {
						// 创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
						HSSFCell cell = row.getCell(j);
						// 设置文件累行
						cell.setCellType(Cell.CELL_TYPE_STRING);
						sb.append(cell.getStringCellValue()).append(",");
					}
				}
			} catch (Exception e) {
				throw new HsException(ASRespCode.AS_PARSE_EXCEL_ERROR);
			}
			// 判断内容是否为空
			if (StringUtils.isBlank(sb)) {
				throw new HsException(ASRespCode.AS_UPLOAD_FILE_BLANK_CONTENT);
			}
			hre = new HttpRespEnvelope(sb.toString());
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		response.getWriter().write(JSON.toJSONString(hre));
		response.getWriter().flush();
		return null;
	}

	/**
	 * 方法名称：保存仓库 方法描述：仓库信息-保存仓库
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveWarehouse")
	public HttpRespEnvelope saveWarehouse(HttpServletRequest request,
			@ModelAttribute Warehouse bean) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 操作员
			String countryNo = request.getParameter("countryNo");// 国家编号
			String provinceNos = request.getParameter("provinceNos");// 仓库配送区域
			String isProvinceList = request.getParameter("isProvinceList");// 修改的时候，本身已经选择的仓库配送区域
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(new Object[] { bean.getWhName(),
							ASRespCode.APS_WH_NAME_INVALID }, new Object[] {
							bean.getWhRoleId(),
							ASRespCode.APS_WH_ROLEID_INVALID },
							new Object[] { bean.getMobile(),
									ASRespCode.APS_WH_MOBILE_INVALID },
							new Object[] { bean.getWhAddr(),
									ASRespCode.APS_WH_ADDR_INVALID },
							new Object[] { provinceNos,
									ASRespCode.APS_WH_AREA_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] { bean.getWhName(), 1,
					64, ASRespCode.APS_WH_NAME_LENGTH_INVALID }, new Object[] {
					bean.getWhAddr(), 1, 128,
					ASRespCode.APS_WH_ADDR_LENGTH_INVALID }, new Object[] {
					bean.getPhone(), 0, 20,
					ASRespCode.APS_TOOL_PHONE_LENGTH_INVALID }, new Object[] {
					bean.getRemark(), 0, 300,
					ASRespCode.APS_WH_REMARK_LENGTH_INVALID });
			List<WhArea> list = new ArrayList<>();
			String[] array = provinceNos.split(",");
			for (String string : array) {
				if (!StringUtils.isBlank(string)) {
					list.add(new WhArea(null, countryNo, string));
				}
			}
			bean.setWhArea(list);
			if (StringUtils.isBlank(bean.getWhId())) {
				bean.setCreatedBy(custId);
				this.warehouseInventoryService.addWarehouse(bean);
			} else {
				bean.setUpdatedBy(custId);
				if (isProvinceList != null && !"".equals(isProvinceList)
						&& isProvinceList.length() > 0) {
					String[] isProvinceArray = isProvinceList.split(",");
					List<String> listisProvinceArray = new ArrayList<String>();
					for (int i = 0; i < isProvinceArray.length; i++) {
						listisProvinceArray.add(isProvinceArray[i]);
					}
					bean.setProvinceNos(listisProvinceArray);
				}
				this.warehouseInventoryService.modifyWarehouse(bean);
			}
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询仓库 方法描述：仓库信息-依据仓库ID查询仓库信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findWarehouseById")
	public HttpRespEnvelope findWarehouseById(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String whId = request.getParameter("whId");// 仓库ID
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { whId,
					ASRespCode.APS_WH_ID_INVALID });
			Warehouse bean = this.warehouseInventoryService
					.queryWarehouseById(whId);
			return new HttpRespEnvelope(bean);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询角色列表 方法描述：仓库信息-查询角色列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listRole")
	public HttpRespEnvelope listRole(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			List<AsRole> list = this.roleService.listRole(null, null, null,
					null, null);
			return new HttpRespEnvelope(list);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：库存预警 方法描述：仓库库存管理-库存预警
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolEnterStockWarningList")
	public HttpRespEnvelope findToolEnterStockWarningList(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.warehouseInventoryService);
			request.setAttribute("methodName", "toolEnterStockWarningPage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：工具查询 方法描述：仓库库存管理-工具查询
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolDeviceUseList")
	public HttpRespEnvelope findToolDeviceUseList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.warehouseInventoryService);
			request.setAttribute("methodName", "queryToolDeviceUsePage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：分页查询工具入库流水 方法描述：仓库库存管理-分页查询工具入库流水
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolEnterSerialList")
	public HttpRespEnvelope findToolEnterSerialList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.warehouseInventoryService);
			request.setAttribute("methodName", "queryToolEnterSerialPage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：分页查询工具出库流水 方法描述：仓库库存管理-分页查询工具出库流水
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findToolOutSerialList")
	public HttpRespEnvelope findToolOutSerialList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.warehouseInventoryService);
			request.setAttribute("methodName", "queryToolOutSerialPage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：统计地区平台仓库工具 方法描述：仓库库存管理-统计地区平台仓库工具
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findStatisticPlatWhToollList")
	public HttpRespEnvelope findStatisticPlatWhToollList(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.warehouseInventoryService);
			request.setAttribute("methodName", "statisticPlatWhTool");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：工具登记 方法描述：仓库库存管理-工具登记
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param bean
	 *            工具对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addDeviceUseRecord")
	public HttpRespEnvelope addDeviceUseRecord(HttpServletRequest request,
			@ModelAttribute DeviceUseRecord bean) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");
			String custName = request.getParameter("custName");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getDeviceSeqNo(),
							ASRespCode.APS_WH_DEVICE_SQENO_INVALID },
					new Object[] { bean.getUseType(),
							ASRespCode.APS_WH_USETYPE_INVALID }, new Object[] {
							bean.getBatchNo(),
							ASRespCode.APS_WH_BATCHNO_INVALID });
			if (bean.getUseType() == 1) {// 领用
											// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(new Object[] {
						bean.getUseName(), ASRespCode.APS_WH_USENAME_INVALID });
				// 长度验证
				RequestUtil.verifyParamsLength(new Object[] {
						bean.getUseName(), 1, 64,
						ASRespCode.APS_WH_USENAME_LENGTH_INVALID },
						new Object[] { bean.getDescription(), 0, 256,
								ASRespCode.APS_WH_DESC_LY_LENGTH_INVALID });
			} else {
				// 长度验证
				RequestUtil.verifyParamsLength(new Object[] {
						bean.getDescription(), 0, 256,
						ASRespCode.APS_WH_DESC_BS_LENGTH_INVALID });
				bean.setUseName(custName);// 报损人
			}
			bean.setCreatedBy(custName);
			this.warehouseInventoryService.addDeviceUseRecord(bean, custId);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：设备序列号查询设备清单 方法描述：仓库库存管理-工具登记-设备序列号查询设备清单
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDeviceDetailByNo")
	public HttpRespEnvelope findDeviceDetailByNo(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String deviceSeqNo = request.getParameter("deviceSeqNo");// 机器码/序列号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { deviceSeqNo,
					ASRespCode.APS_WH_DEVICE_SQENO_INVALID });
			DeviceDetail obj = this.warehouseInventoryService
					.queryDeviceDetailByNo(deviceSeqNo);
			if (obj == null) {
				throw new HsException(ASRespCode.APS_WH_DEVICE_INVALID);
			}
			return new HttpRespEnvelope(obj);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：库存统计 方法描述：库存统计
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findConfigToolStockList")
	public HttpRespEnvelope findConfigToolStockList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifyPointNo(request);// 校验互生卡号
			request.setAttribute("serviceName", this.warehouseInventoryService);
			request.setAttribute("methodName", "findConfigToolStockList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查看机器码 方法描述：库存统计-查看机器码
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPosDeviceSeqNoDetail")
	public HttpRespEnvelope findPosDeviceSeqNoDetail(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String batchNo = request.getParameter("batchNo");// 批次号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { batchNo,
					ASRespCode.APS_WH_BATCHNO_INVALID });
			DeviceSeqNo obj = this.warehouseInventoryService
					.queryPosDeviceSeqNoDetail(batchNo);
			Map<String, Object> map = new HashMap<String, Object>();
			if (obj != null) {
				List<ASDeviceSeqNo> list = new ArrayList<ASDeviceSeqNo>();
				if (obj.getSeqNo() != null) {
					for (String seqNo : obj.getSeqNo()) {
						list.add(new ASDeviceSeqNo(batchNo, seqNo));
					}
				}
				map.put("info", obj.getConfigNum());
				map.put("list", list);
			} else {
				map.put("info", "");
				map.put("list", new ArrayList<ASDeviceSeqNo>());
			}
			return new HttpRespEnvelope(map);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：盘库 方法描述：库存统计-盘库
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param bean
	 *            盘库对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toolEnterInventory")
	public HttpRespEnvelope toolEnterInventory(HttpServletRequest request,
			@ModelAttribute WhInventory bean) {
		try {
			super.verifySecureToken(request);
			String custName = request.getParameter("custName");// 操作员
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					bean.getEnterNo(), ASRespCode.APS_WH_ENTERNO_INVALID },
					new Object[] { bean.getShouldQuantity(),
							ASRespCode.APS_WH_S_QUANTITY_INVALID },
					new Object[] { bean.getQuantity(),
							ASRespCode.APS_WH_P_QUANTITY_INVALID });
			bean.setOperNo(custName);
			this.warehouseInventoryService.toolEnterInventory(bean);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：入库抽检 方法描述：库存统计-入库抽检
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param bean
	 *            入库抽检对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toolEnterCheck")
	public HttpRespEnvelope toolEnterCheck(HttpServletRequest request,
			@ModelAttribute InstorageInspection bean) {
		try {
			super.verifySecureToken(request);
			String custName = request.getParameter("custName");// 操作员
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					bean.getEnterNo(), ASRespCode.APS_WH_ENTERNO_INVALID },
					new Object[] { bean.getQuantity(),
							ASRespCode.APS_WH_RK_QUANTITY_INVALID },
					new Object[] { bean.getPassQuantity(),
							ASRespCode.APS_WH_RK_PASS_QUANTITY_INVALID },
					new Object[] { bean.getPassRate(),
							ASRespCode.APS_WH_RK_PASSRATE_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] { bean.getRemark(), 0,
					300, ASRespCode.APS_WH_REMARK_LENGTH_INVALID });
			bean.setOperNo(custName);
			this.warehouseInventoryService.toolEnterCheck(bean);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return this.warehouseInventoryService;
	}

}
