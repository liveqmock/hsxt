package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
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
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceSeqNo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className : IWarehouseInventoryService.java
 * @description : 仓库库存管理接口
 * @author : maocy
 * @createDate : 2015-12-15
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service
public interface IWarehouseInventoryService extends IBaseService {

	/**
	 * 
	 * 方法名称：入库提交 方法描述：工具入库-入库提交
	 * 
	 * @param bean
	 *            入库信息
	 * @throws HsException
	 */
	public void productEnter(Enter bean) throws HsException;

	/**
	 * 
	 * 方法名称：查询所有仓库 方法描述：工具入库-查询所有仓库
	 * 
	 * @throws HsException
	 */
	public List<Warehouse> findAllWarehouseList() throws HsException;

	/**
	 * 
	 * 方法名称：查询所有供应商 方法描述：工具入库-查询所有供应商
	 * 
	 * @throws HsException
	 */
	public List<Supplier> findAllupplierList() throws HsException;

	/**
	 * 根据工具类别查询工具
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月15日 下午5:01:46
	 * @param categoryCode
	 * @return
	 * @return : List<ToolProduct>
	 * @version V3.0.0
	 */
	public List<ToolProduct> findToolProducts(String categoryCode);


	/**
	 * 根据工具类别查询工具
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月15日 下午5:01:46
	 * @param categoryCode
	 * @return
	 * @return : List<ToolProduct>
	 * @version V3.0.0
	 */
	public List<ToolProduct> findToolProductAll(String categoryCode);

	/**
	 * 
	 * 方法名称：查询工具列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public PageData<ToolProduct> findToolProductList(Map filterMap, Map sortMap, Page page);

	/**
	 *
	 * 方法名称：新增工具详情 方法描述：工具入库-新增工具详情
	 *
	 * @param ToolProduct
	 *            工具对象
	 * @return null
	 * @throws HsException
	 */
	public void addToolProduct(ToolProduct bean) throws HsException;

	/**
	 *
	 * 方法名称：修改工具详情 方法描述：工具入库-新增工具详情
	 *
	 * @param bean
	 *            工具对象
	 * @return null
	 * @throws HsException
	 */
	public void modifyToolProduct(ToolProduct bean) throws HsException;

	/**
	 * 
	 * 方法名称：查询工具查看详情 方法描述：工具入库-查询工具查看详情
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public ToolProduct queryToolProductById(String id) throws HsException;

	/**
	 * 
	 * 方法名称：根据申请编号查询上下架详情 方法描述：工具入库-根据申请编号查询上下架详情
	 * 
	 * @param applyId
	 *            申请号
	 * @return 工具列表
	 * @throws HsException
	 */
	public ToolProductUpDown queryToolProductUpDownById(String applyId) throws HsException;

	/**
	 * 根据产品编号查询上下架详情
	 * @param productId 产品编号
	 * @return
	 * @throws HsException
	 */
	public ToolProductUpDown queryToolProductUpDownByProductId(String productId) throws HsException;

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
	public void applyChangePrice(String productId, String applyPrice, Operator oprator) throws HsException;

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
	public void applyToolProductToDown(String productId, String downReason, Operator oprator) throws HsException;

	/**
	 * 
	 * 方法名称：查询工具下架审批列表 方法描述：工具入库-依据工具类别查询工具列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public PageData<ToolProductUpDown> findToolDoawnList(Map filterMap, Map sortMap, Page page);

	/**
	 * 
	 * 方法名称：工具产品下架审批 方法描述：工具入库-工具产品下架审批
	 * 
	 * @param ApprInfo
	 *            工具产品对象
	 * @return void
	 * @throws HsException
	 */
	public void apprToolProductForDown(ApprInfo apprInfo) throws HsException;

	/**
	 * 
	 * 方法名称：查询工具价格审批列表 方法描述：工具入库-查询工具价格审批列表
	 * 
	 * @param categoryCode
	 *            工具类别
	 * @return 工具列表
	 * @throws HsException
	 */
	public PageData<ToolProductUpDown> findToolPriceList(Map filterMap, Map sortMap, Page page);

	/**
	 * 
	 * 方法名称：工具产品上架(价格)审批 方法描述：工具入库-工具产品上架(价格)审批
	 * 
	 * @param ApprInfo
	 *            工具产品对象
	 * @return void
	 * @throws HsException
	 */
	public void apprToolProductForUp(ApprInfo apprInfo) throws HsException;

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
	public void refuseAndHoldOperate(String bizNo, int taskStatus, String exeCustId) throws HsException;

	/**
	 * 
	 * 方法名称：添加仓库 方法描述：仓库信息-添加仓库
	 * 
	 * @param bean
	 *            仓库信息
	 * @throws HsException
	 */
	public void addWarehouse(Warehouse bean) throws HsException;

	/**
	 * 
	 * 方法名称：修改仓库 方法描述：仓库信息-修改仓库
	 * 
	 * @param bean
	 *            仓库信息
	 * @throws HsException
	 */
	public void modifyWarehouse(Warehouse bean) throws HsException;

	/**
	 * 
	 * 方法名称：查询仓库 方法描述：仓库信息-依据仓库ID查询仓库信息
	 * 
	 * @param bean
	 *            仓库信息
	 * @throws HsException
	 */
	public Warehouse queryWarehouseById(String whId);

	/**
	 * 
	 * 方法名称：分页查询库存预警 方法描述：分页查询库存预警
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> toolEnterStockWarningPage(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 
	 * 方法名称：分页工具使用情况 方法描述：分页工具使用情况
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> queryToolDeviceUsePage(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 方法名称：分页查询工具入库流水 方法描述：分页查询工具入库流水
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> queryToolEnterSerialPage(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 方法名称：分页查询工具出库流水 方法描述：分页查询工具出库流水
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> queryToolOutSerialPage(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 方法名称：统计地区平台仓库工具 方法描述：统计地区平台仓库工具
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> statisticPlatWhTool(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 方法名称：设备序列号查询设备清单 方法描述：设备序列号查询设备清单
	 * 
	 * @param deviceSeqNo
	 *            机器码/序列号
	 * @return
	 * @throws HsException
	 */
	public DeviceDetail queryDeviceDetailByNo(String deviceSeqNo);

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
	public void addDeviceUseRecord(DeviceUseRecord bean, String custId) throws HsException;

	/**
	 * 方法名称：库存统计 方法描述：库存统计
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> findConfigToolStockList(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 方法名称：盘库 方法描述：库存统计-盘库
	 * 
	 * @param bean
	 *            盘库对象
	 * @return
	 * @throws HsException
	 */
	public void toolEnterInventory(WhInventory bean) throws HsException;

	/**
	 * 方法名称：入库抽检 方法描述：库存统计-入库抽检
	 * 
	 * @param bean
	 *            入库抽检对象
	 * @return
	 * @throws HsException
	 */
	public void toolEnterCheck(InstorageInspection bean) throws HsException;

	/**
	 * 方法名称：查看机器码 方法描述：库存统计-查看机器码
	 * 
	 * @param bean
	 *            入库抽检对象
	 * @return
	 * @throws HsException
	 */
	public DeviceSeqNo queryPosDeviceSeqNoDetail(String batchNo);

}
