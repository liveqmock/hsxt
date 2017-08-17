package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.common.IPubParamService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.task.IBSTaskService;
import com.gy.hsxt.bs.api.tool.IBSToolProductService;
import com.gy.hsxt.bs.api.tool.IBSToolStockService;
import com.gy.hsxt.bs.bean.base.ApprInfo;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.tool.DeviceUseRecord;
import com.gy.hsxt.bs.bean.tool.Enter;
import com.gy.hsxt.bs.bean.tool.InstorageInspection;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.ToolProductUpDown;
import com.gy.hsxt.bs.bean.tool.Warehouse;
import com.gy.hsxt.bs.bean.tool.WhInventory;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceSeqNo;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolEnterOutPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStockWarning;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className : WarehouseInventoryService.java
 * @description : 仓库库存管理接口实现
 * @author : maocy
 * @createDate : 2015-12-30
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service
public class WarehouseInventoryService extends BaseServiceImpl implements
		IWarehouseInventoryService {

	@Autowired
	private IBSToolProductService bsService;// 工具服务类

	@Autowired
	private IBSToolStockService bSToolStockService;// 仓库服务类

	@Autowired
	private IBSTaskService iBSTaskService;// 工单管理

	@Resource
	private IPubParamService pubParamService;// 查询本平台信息

	/**
	 * 
	 * 方法名称：入库提交 方法描述：工具入库-入库提交
	 * 
	 * @param bean
	 *            入库信息
	 * @throws HsException
	 */
	public void productEnter(Enter bean) throws HsException {
		try {
			this.bSToolStockService.productEnter(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "productEnter",
					"调用BS服务工具入库失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：查询所有仓库 方法描述：工具入库-查询所有仓库
	 * 
	 * @throws HsException
	 */
	public List<Warehouse> findAllWarehouseList() throws HsException {
		try {
			return this.bSToolStockService.queryWarehouseAll();
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findAllWarehouseList",
					"调用BS查询所有仓库失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：查询所有供应商 方法描述：工具入库-查询所有供应商
	 * 
	 * @throws HsException
	 */
	public List<Supplier> findAllupplierList() throws HsException {
		try {
			return this.bSToolStockService.querySupplierAll();
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findAllupplierList",
					"调用BS查询所有供应商失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public List<ToolProduct> findToolProducts(String categoryCode) {
		try {
			return this.bsService.queryToolProduct(categoryCode);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findToolProducts",
					"调用BS查询工具列表失败", ex);
			return null;
		}
	}

	/**
	 *
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 *
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public List<ToolProduct> findToolProductAll(String categoryCode) {
		try {
			return this.bsService.selectToolProductNotByStatus(categoryCode);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findToolProductAll",
					"调用BS查询工具列表失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public PageData<ToolProduct> findToolProductList(Map filterMap,
			Map sortMap, Page page) throws HsException {
		ToolProductVo vo = new ToolProductVo();
		vo.setProductName((String) filterMap.get("productName"));
		vo.setCategoryCode((String) filterMap.get("categoryCode"));
		vo.setEnableStatus(CommonUtils.toInteger(filterMap.get("enableStatus")));
		vo.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		vo.setExeCustId((String) filterMap.get("exeCustId"));
		try {
			return bsService.queryToolProductPage(vo, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findToolProductList",
					"调用BS分页查询工具列表信息失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：新增工具详情 方法描述：工具入库-新增工具详情
	 * 
	 * @param ToolProduct
	 *            工具对象
	 * @return null
	 * @throws HsException
	 */
	public void addToolProduct(ToolProduct bean) throws HsException {
		try {
			bsService.addToolProduct(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addToolProduct",
					"调用BS服务新增工具失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 *
	 * 方法名称：修改工具详情
	 *
	 * @param bean
	 *            工具对象
	 * @return null
	 * @throws HsException
	 */
	public void modifyToolProduct(ToolProduct bean) throws HsException {
		try {
			bsService.updateToolProduct(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "modifyToolProduct",
					"调用BS服务修改工具失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具查看详情 方法描述：工具入库-查询工具查看详情
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public ToolProduct queryToolProductById(String id) throws HsException {
		try {
			return bsService.queryToolProductById(id);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "queryToolProductById",
					"调用BS查询工具详情失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：根据申请编号查询上下架详情 方法描述：工具入库-根据申请编号查询上下架详情
	 * 
	 * @param applyId
	 *            申请号
	 * @return 工具列表
	 * @throws HsException
	 */
	public ToolProductUpDown queryToolProductUpDownById(String applyId)
			throws HsException {
		try {
			return bsService.queryToolProductUpDownById(applyId);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryToolProductUpDownById", "调用BS根据申请编号查询上下架详情失败", ex);
			return null;
		}
	}

	@Override
	public ToolProductUpDown queryToolProductUpDownByProductId(String productId)
			throws HsException {
		try {
			return bsService.queryToolProductUpDownByProductId(productId);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryToolProductUpDownByProductId", "调用BS根据申请编号查询上下架详情失败",
					ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：工具价格修改 方法描述：工具价格修改
	 * 
	 * @param productId
	 *            工具编号
	 * @param applyPrice
	 *            申请调整后价格
	 * @return void
	 * @throws HsException
	 */
	public void applyChangePrice(String productId, String applyPrice,
			Operator oprator) throws HsException {
		try {
			bsService.applyChangePrice(productId, applyPrice, oprator);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "applyChangePrice",
					"调用BS服务工具价格修改失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：工具产品下架申请 方法描述：工具产品下架申请
	 * 
	 * @param productId
	 *            工具编号
	 * @param downReason
	 *            下架原因
	 * @return void
	 * @throws HsException
	 */
	public void applyToolProductToDown(String productId, String downReason,
			Operator oprator) throws HsException {
		try {
			bsService.applyToolProductToDown(productId, downReason, oprator);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"applyToolProductToDown", "调用BS服务工具产品下架申请失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：查询工具下架审批列表 方法描述：工具入库-查询工具下架审批列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public PageData<ToolProductUpDown> findToolDoawnList(Map filterMap,
			Map sortMap, Page page) throws HsException {
		String productName = (String) filterMap.get("productName");
		String categoryName = (String) filterMap.get("categoryCode");
		String exeCustId = (String) filterMap.get("exeCustId");
		try {
			return bsService.queryToolDownForApprListPage(productName,
					categoryName, exeCustId, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findToolDoawnList",
					"调用BS分页查询工具下架审批失败", ex);
			return null;
		}

	}

	/**
	 * 
	 * 方法名称：工具产品下架审批 方法描述：工具入库-工具产品下架审批
	 * 
	 * @param ApprInfo
	 *            工具产品对象
	 * @return void
	 * @throws HsException
	 */
	public void apprToolProductForDown(ApprInfo apprInfo) throws HsException {
		try {
			bsService.apprToolProductForDown(apprInfo);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"apprToolProductForDown", "调用BS服务工具产品下架审批失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	};

	/**
	 * 
	 * 方法名称：查询工具价格审批列表 方法描述：工具入库-查询工具价格审批列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public PageData<ToolProductUpDown> findToolPriceList(Map filterMap,
			Map sortMap, Page page) throws HsException {

		String productName = (String) filterMap.get("productName");
		String categoryName = (String) filterMap.get("categoryCode");
		String exeCustId = (String) filterMap.get("exeCustId");
		try {
			return bsService.queryToolUpForApprListPage(productName,
					categoryName, exeCustId, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findToolPriceList",
					"调用BS分页查询工具价格审批失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：工具产品上架(价格)审批 方法描述：工具入库-工具产品上架(价格)审批
	 * 
	 * @param ApprInfo
	 *            工具产品对象
	 * @return void
	 * @throws HsException
	 */
	public void apprToolProductForUp(ApprInfo apprInfo) throws HsException {
		try {
			bsService.apprToolProductForUp(apprInfo);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "apprToolProductForUp",
					"调用BS服务工具产品上架(价格)审批失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：工具价格审批，工具下架审批，拒绝受理，挂起 方法描述：工具入库-工具价格审批，工具下架审批，拒绝受理，挂起
	 * 
	 * @param bizNo
	 *            工具类别
	 * @param taskStatus
	 *            工具类别
	 * @param exeCustId
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public void refuseAndHoldOperate(String bizNo, int taskStatus,
			String exeCustId) throws HsException {
		try {
			iBSTaskService.updateTaskStatus(bizNo, taskStatus, exeCustId);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "refuseAndHoldOperate",
					"调用BS服务工具价格审批，工具下架审批，拒绝受理，挂起失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 分页查询仓库
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		try {
			return this.bSToolStockService.queryWarehouseByPage(
					(String) filterMap.get("whName"), page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询仓库失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：添加仓库 方法描述：仓库信息-添加仓库
	 * 
	 * @param bean
	 *            仓库信息
	 * @throws HsException
	 */
	public void addWarehouse(Warehouse bean) throws HsException {
		try {
			this.bSToolStockService.addWarehouse(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addWarehouse",
					"调用BS服务添加仓库失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：修改仓库 方法描述：仓库信息-修改仓库
	 * 
	 * @param bean
	 *            仓库信息
	 * @throws HsException
	 */
	public void modifyWarehouse(Warehouse bean) throws HsException {
		try {
			this.bSToolStockService.modifyWarehouse(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "modifyWarehouse",
					"调用BS服务修改仓库失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：查询仓库 方法描述：仓库信息-依据仓库ID查询仓库信息
	 * 
	 * @param bean
	 *            仓库信息
	 * @throws HsException
	 */
	public Warehouse queryWarehouseById(String whId) {
		try {
			return this.bSToolStockService.queryWarehouseById(whId);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "queryWarehouseById",
					"调用BS依据仓库ID查询仓库信息失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：分页查询库存预警 方法描述：分页查询库存预警
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<ToolStockWarning> toolEnterStockWarningPage(Map filterMap,
			Map sortMap, Page page) throws HsException {
		String productName = (String) filterMap.get("productName");// 工具名称
		String categoryCode = (String) filterMap.get("categoryCode");// 工具类型
		String warningState = (String) filterMap.get("warningState");// 预警状态 1
																		// true预警，0正常false
		Integer warningStateInt = null;
		if (warningState.equals("true")) {
			warningStateInt = 1;
		} else if (warningState.equals("false")) {
			warningStateInt = 0;
		}
		try {
			return this.bSToolStockService.toolEnterStockWarningPage(
					productName, categoryCode, warningStateInt, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"toolEnterStockWarningPage", "调用BS分页查询库存预警失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：分页工具使用情况 方法描述：分页工具使用情况
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> queryToolDeviceUsePage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		String deviceSeqNo = (String) filterMap.get("deviceSeqNo");// 设备序列号
		String batchNo = (String) filterMap.get("batchNo");// 批次号
		Integer useStatus = CommonUtils.toInteger(filterMap.get("useStatus"));// 设备使用状态
		try {
			return this.bSToolStockService.queryToolDeviceUsePage(deviceSeqNo,
					useStatus, batchNo, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryToolDeviceUsePage", "调用BS分页工具使用情况失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：分页查询工具入库流水 方法描述：分页查询工具入库流水
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> queryToolEnterSerialPage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		ToolProductVo params = new ToolProductVo();
		params.setStartDate((String) filterMap.get("startDate"));// 开始日期
		params.setEndDate((String) filterMap.get("endDate"));// 开始日期
		params.setCategoryCode((String) filterMap.get("categoryCode"));// 工具类别
		try {
			return this.bSToolStockService.queryToolEnterSerialPage(params,
					page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryToolEnterSerialPage", "调用BS分页查询工具入库流水失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：分页查询工具出库流水 方法描述：分页查询工具出库流水
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> queryToolOutSerialPage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		ToolProductVo params = new ToolProductVo();
		params.setStartDate((String) filterMap.get("startDate"));// 开始日期
		params.setEndDate((String) filterMap.get("endDate"));// 开始日期
		params.setCategoryCode((String) filterMap.get("categoryCode"));// 工具类别
		try {
			PageData<ToolEnterOutPage> pageObject = this.bSToolStockService
					.queryToolOutSerialPage(params, page);
			return pageObject;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryToolOutSerialPage", "调用BS分页查询工具出库流水失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：统计地区平台仓库工具 方法描述：统计地区平台仓库工具
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> statisticPlatWhTool(Map filterMap, Map sortMap, Page page)
			throws HsException {
		try {
			return this.bSToolStockService.statisticPlatWhTool(
					(String) filterMap.get("categoryCode"), page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "statisticPlatWhTool",
					"调用BS统计地区平台仓库工具失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：设备序列号查询设备清单 方法描述：设备序列号查询设备清单
	 * 
	 * @param deviceSeqNo
	 *            机器码/序列号
	 * @return
	 * @throws HsException
	 */
	public DeviceDetail queryDeviceDetailByNo(String deviceSeqNo) {
		try {
			return this.bSToolStockService.queryDeviceDetailByNo(deviceSeqNo);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "statisticPlatWhTool",
					"调用BS根据设备序列号查询设备清单失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：添加工具使用记录 方法描述：添加工具使用记录
	 * 
	 * @param bean
	 *            工具对象
	 * @param custId
	 *            客户ID
	 * @return
	 * @throws HsException
	 */
	public void addDeviceUseRecord(DeviceUseRecord bean, String custId)
			throws HsException {
		try {
			this.bSToolStockService.addDeviceUseRecord(bean, custId);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addDeviceUseRecord",
					"调用BS添加工具使用记录失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 方法名称：库存统计 方法描述：库存统计
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> findConfigToolStockList(Map filterMap, Map sortMap,
			Page page) throws HsException {
		String categoryCode = (String) filterMap.get("categoryCode");// 工具类别
		String productId = (String) filterMap.get("productId");// 工具名称
		String whId = (String) filterMap.get("whId");// 仓库名称
		try {
			return this.bSToolStockService.queryConfigToolStockByPage(
					categoryCode, productId, whId, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"findConfigToolStockList", "调用BS库存统计失败", ex);
			return null;
		}
	}

	/**
	 * 方法名称：盘库 方法描述：库存统计-盘库
	 * 
	 * @param bean
	 *            盘库对象
	 * @return
	 * @throws HsException
	 */
	public void toolEnterInventory(WhInventory bean) throws HsException {
		try {
			this.bSToolStockService.toolEnterInventory(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "toolEnterInventory",
					"调用BS库存统计-盘库失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 方法名称：入库抽检 方法描述：库存统计-入库抽检
	 * 
	 * @param bean
	 *            入库抽检对象
	 * @return
	 * @throws HsException
	 */
	public void toolEnterCheck(InstorageInspection bean) throws HsException {
		try {
			this.bSToolStockService.toolEnterCheck(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "toolEnterCheck",
					"调用BS库存统计-入库抽检失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 方法名称：查看机器码 方法描述：库存统计-查看机器码
	 * 
	 * @param bean
	 *            入库抽检对象
	 * @return
	 * @throws HsException
	 */
	public DeviceSeqNo queryPosDeviceSeqNoDetail(String batchNo) {
		try {
			return this.bSToolStockService.queryPosDeviceSeqNoDetail(batchNo);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryPosDeviceSeqNoDetail", "调用BS查看机器码失败", ex);
			return null;
		}
	}

}