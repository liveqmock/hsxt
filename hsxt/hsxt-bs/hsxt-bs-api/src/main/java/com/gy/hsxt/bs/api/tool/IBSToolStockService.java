/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tool;

import java.util.List;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.DeviceUseRecord;
import com.gy.hsxt.bs.bean.tool.Enter;
import com.gy.hsxt.bs.bean.tool.InstorageInspection;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.Warehouse;
import com.gy.hsxt.bs.bean.tool.WhInventory;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceSeqNo;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolEnterOutPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStock;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStockWarning;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具库存API接口
 * 
 * @Package: com.gy.hsxt.bs.api.tool
 * @ClassName: IBSToolStockService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:23:49
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSToolStockService {

	/**
	 * 添加仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:06
	 * @param bean
	 *            仓库参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addWarehouse(Warehouse bean) throws HsException;

	/**
	 * 修改仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:16
	 * @param bean
	 *            仓库参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyWarehouse(Warehouse bean) throws HsException;

	/**
	 * 删除仓库(非默认)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:22
	 * @param whId
	 *            仓库id
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeWarehouse(String whId) throws HsException;

	/**
	 * 根据id查询仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:27
	 * @param whId
	 *            仓库id
	 * @return
	 * @return : Warehouse
	 * @version V3.0.0
	 */
	public Warehouse queryWarehouseById(String whId);

	/**
	 * 分页查询仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午4:57:26
	 * @param whName
	 *            仓库名称
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<Warehouse>
	 * @version V3.0.0
	 */
	public PageData<Warehouse> queryWarehouseByPage(String whName, Page page);

	/**
	 * 查询所有的仓库
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月3日 下午2:10:54
	 * @return
	 * @return : List<Warehouse>
	 * @version V3.0.0
	 */
	public List<Warehouse> queryWarehouseAll();

	/**
	 * 添加供应商
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:37
	 * @param bean
	 *            供应商参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addSupplier(Supplier bean) throws HsException;

	/**
	 * 修改供应商
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:42
	 * @param bean
	 *            供应商参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifySupplier(Supplier bean) throws HsException;

	/**
	 * 删除供应商
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:46
	 * @param supplierId
	 *            供应商id
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeSupplier(String supplierId) throws HsException;

	/**
	 * 根据id查询供应商
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:25:52
	 * @param supplierId
	 *            供应商id
	 * @return
	 * @return : Supplier
	 * @version V3.0.0
	 */
	public Supplier querySupplierById(String supplierId);

	/**
	 * 分页查询供应商
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:37:55
	 * @param supplierName
	 *            供应商名称
	 * @param corpName
	 *            公司名称
	 * @param linkMan
	 *            联系人
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<Supplier>
	 * @version V3.0.0
	 */
	public PageData<Supplier> querySupplierByPage(String supplierName, String corpName, String linkMan, Page page);

	/**
	 * 查询所有的供应商
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月3日 下午2:22:16
	 * @return
	 * @return : List<Supplier>
	 * @version V3.0.0
	 */
	public List<Supplier> querySupplierAll();

	/**
	 * 产品入库(互生卡除外)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:38:10
	 * @param bean
	 *            工具产品入库参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void productEnter(Enter bean) throws HsException;

	/**
	 * 分页查询需要配置工具库存
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午5:37:50
	 * @param categoryCode
	 *            工具类别代码
	 * @param productId
	 *            工具产品ID
	 * @param whId
	 *            仓库ID
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolStock>
	 * @version V3.0.0
	 */
	public PageData<ToolStock> queryConfigToolStockByPage(String categoryCode, String productId, String whId,
			Page page);

	/**
	 * 查询POS机设备序列号详情
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 上午9:10:20
	 * @param batchNo
	 *            批次号
	 * @return
	 * @return : DeviceSeqNo
	 * @version V3.0.0
	 */
	public DeviceSeqNo queryPosDeviceSeqNoDetail(String batchNo);

	/**
	 * 工具库存盘库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午7:51:38
	 * @param bean
	 *            工具库存盘库参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolEnterInventory(WhInventory bean) throws HsException;

	/**
	 * 工具入库抽检
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午7:51:55
	 * @param bean
	 *            工具入库抽检参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolEnterCheck(InstorageInspection bean) throws HsException;

	/**
	 * 分页查询工具库存预警
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 上午11:11:52
	 * @param productName
	 *            工具产品名称
	 * @param categoryCode
	 *            工具类别代码
	 * @param warningStatus
	 *            预警状态
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolStockWarning>
	 * @version V3.0.0
	 */
	public PageData<ToolStockWarning> toolEnterStockWarningPage(String productName, String categoryCode,
			Integer warningStatus, Page page);

	/**
	 * 分页查询工具设备使用
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午1:58:45
	 * @param deviceSeqNo
	 *            设备序列号
	 * @param useStatus
	 *            使用状态
	 * @param batchNo
	 *            批次号
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<DeviceInfoPage>
	 * @version V3.0.0
	 */
	public PageData<DeviceInfoPage> queryToolDeviceUsePage(String deviceSeqNo, Integer useStatus, String batchNo,
			Page page);

	/**
	 * 根据设备序列号查询配置详情(领用和报损除外)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 上午10:26:29
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig queryDeviceConfigDetail(String deviceSeqNo);

	/**
	 * 使用设备查询设备清单(报损和领用)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 上午11:33:49
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @return : DeviceDetail
	 * @version V3.0.0
	 */
	public DeviceDetail queryDeviceDetailByNo(String deviceSeqNo);

	/**
	 * 添加工具使用记录
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午4:11:01
	 * @param bean
	 *            工具使用记录参数实体
	 * @param custId
	 *            客户号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addDeviceUseRecord(DeviceUseRecord bean, String custId) throws HsException;

	/**
	 * 分页查询工具入库流水
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 下午3:42:19
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolEnterOutPage>
	 * @version V3.0.0
	 */
	public PageData<ToolEnterOutPage> queryToolEnterSerialPage(ToolProductVo param, Page page);

	/**
	 * 分页查询工具出库流水
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 下午3:43:59
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolEnterOutPage>
	 * @version V3.0.0
	 */
	public PageData<ToolEnterOutPage> queryToolOutSerialPage(ToolProductVo param, Page page);

	/**
	 * 分页统计地区平台仓库工具
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 下午5:37:34
	 * @param categoryCode
	 *            工具类别代码
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolStock>
	 * @version V3.0.0
	 */
	public PageData<ToolStock> statisticPlatWhTool(String categoryCode, Page page);

	/**
	 * 分页查询配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月3日 下午12:29:32
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfig>
	 * @version V3.0.0
	 */
	public PageData<ToolConfig> queryToolConfigPage(BaseParam bean, Page page);

	/**
	 * 修改配置单仓库id
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月18日 上午11:56:48
	 * @param bean
	 *            配置单参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyToolConfigWhId(ToolConfig bean) throws HsException;
}
