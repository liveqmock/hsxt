/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.tool.IBSToolStockService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.*;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.bean.tool.resultbean.*;
import com.gy.hsxt.bs.common.ObjectFactory;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.DeviceType;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.StoreStatus;
import com.gy.hsxt.bs.common.enumtype.tool.UseStatus;
import com.gy.hsxt.bs.common.enumtype.tool.UseType;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tool.bean.EnterDetail;
import com.gy.hsxt.bs.tool.bean.KsnEnterInfo;
import com.gy.hsxt.bs.tool.bean.Out;
import com.gy.hsxt.bs.tool.bean.OutDetail;
import com.gy.hsxt.bs.tool.mapper.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具库存API接口实现类
 * 
 * @Package: com.hsxt.bs.btool.service
 * @ClassName: ToolStockService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午2:05:12
 * @company: gyist
 * @version V3.0.0
 */
@Service("toolStockService")
public class ToolStockService implements IBSToolStockService {

	/** 业务服务私有配置参数 **/
	@Autowired
	private BsConfig bsConfig;

	/** 仓库Mapper **/
	@Autowired
	private WarehouseMapper warehouseMapper;

	/** 供应商Mapper **/
	@Autowired
	private SupplierMapper supplierMapper;

	/** 入库Mapper **/
	@Autowired
	private EnterMapper enterMapper;

	/** 刷卡器Mapper **/
	@Autowired
	private KsnInfoMapper ksnInfoMapper;

	/** 设备通用信息Mapper **/
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;

	/** 设备使用记录Mapper **/
	@Autowired
	private DeviceUseRecordMapper deviceUseRecordMapper;

	/** 出库Mapper **/
	@Autowired
	private OutMapper outMapper;

	/** 工具配置单Mapper **/
	@Autowired
	private ToolConfigMapper toolConfigMapper;

	/** 公共Mapper **/
	@Autowired
	private CommonMapper commonMapper;

	/**
	 * 增加仓库
	 * 
	 * @Description:
	 * @param bean
	 *            仓库参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addWarehouse(Warehouse bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addWarehouse", "params==>" + bean, "增加仓库");
		// 参考id
		String whId = "";
		// 仓库配送区域参数
		Map<String, Object> params = new HashMap<String, Object>();
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增仓库参数为NULL," + bean);
		try
		{
			// 验证省份是否已选中
			vaildProvinceNoIsSelect(bean.getWhArea(), null);

			// 判断有没有新建成默认仓库
			if (null != bean.getIsDefault() && bean.getIsDefault().booleanValue())
			{
				// 将原有的默认仓库修改成非默认
				warehouseMapper.updateDefaultToNotDefault();
			}

			// 仓库
			whId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			bean.setWhId(whId);
			// 仓库配送区域
			params.put("whId", whId);
			params.put("areas", bean.getWhArea());

			// 插入仓库、仓库配送区域数据
			warehouseMapper.insertWarehouse(bean);
			warehouseMapper.insertWhArea(params);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addWarehouse",
					BSRespCode.BS_ADD_WAREHOUSE_FAIL.getCode() + ":新增仓库失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_WAREHOUSE_FAIL, "新增仓库失败," + bean);
		}
		params = null;
	}

	/**
	 * 修改仓库
	 * 
	 * @Description:
	 * @param bean
	 *            仓库参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void modifyWarehouse(Warehouse bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:modifyWarehouse", "params==>" + bean, "修改仓库");
		int count = 0;
		// 仓库配送区域参数
		Map<String, Object> params = new HashMap<String, Object>();
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "修改仓库参数为NULL," + bean);
		try
		{
			// 验证省份是否已选中
			vaildProvinceNoIsSelect(bean.getWhArea(), bean.getProvinceNos());

			// 判断有没有修改成默认仓库
			if (null != bean.getIsDefault() && bean.getIsDefault().booleanValue())
			{
				// 将原有的默认仓库修改成非默认
				warehouseMapper.updateDefaultToNotDefault();
			}
			// 修改仓库
			count = warehouseMapper.updateWarehouse(bean);

			// 判断要不要修改仓库的配送区域
			if (null != bean.getWhArea() && bean.getWhArea().size() > 0)
			{
				// 先将原有的配送区域删除，再插入新配送区域
				warehouseMapper.deleteWhAreaByWhId(bean.getWhId());
				params.put("whId", bean.getWhId());
				params.put("areas", bean.getWhArea());
				// 插入仓库配送区域
				warehouseMapper.insertWhArea(params);
			}
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifyWarehouse",
					BSRespCode.BS_MODIFY_WAREHOUSE_FAIL.getCode() + ":修改仓库失败," + bean, ex);
			count = 0;
		}
		params = null;
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_WAREHOUSE_FAIL, "修改仓库失败," + bean);
	}

	/**
	 * 验证省份是否已选中
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月12日 下午2:55:05
	 * @param whArea
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildProvinceNoIsSelect(List<WhArea> whArea, List<String> provinceNos) throws HsException
	{
		if (null != whArea && whArea.size() > 0)
		{
			String provinceNo = "";
			if (null == provinceNos || provinceNos.size() <= 0)
			{
				for (WhArea area : whArea)
				{
					provinceNo = provinceNo + area.getProvinceNo() + ",";
				}
			} else
			{
				List<Object> provinceNoObj = ObjectFactory.getListEntityAttr(whArea, "provinceNo");
				for (Object obj : provinceNoObj)
				{
					if (!provinceNos.contains(obj))
					{
						provinceNo = provinceNo + obj.toString() + ",";
					}
				}
			}
			int count = 0;
			if (StringUtils.isNotBlank(provinceNo))
			{
				count = warehouseMapper.selectCountWhAreaByNos(provinceNo.substring(0, provinceNo.length() - 1));
			}
			if (count > 0)
			{
				throw new HsException(BSRespCode.BS_WAREHOUSE_SELECTED_PROVINCENO, "仓库已经选择省份");
			}
		}
	}

	/**
	 * 删除仓库(非默认)
	 * 
	 * @Description:
	 * @param whId
	 *            仓库id
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void removeWarehouse(String whId) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:removeWarehouse", "params==>" + whId, "删除仓库");
		int count = 0;
		HsAssert.hasText(whId, RespCode.PARAM_ERROR, "删除仓库参数为NULL," + whId);
		try
		{
			// 删除仓库(非默认)
			count = warehouseMapper.deleteWarehouseById(whId);
			if (count > 0)
			{
				// 删除仓库配送区域
				warehouseMapper.deleteWhAreaByWhId(whId);
			}
		} catch (Exception ex)
		{
			SystemLog.debug(this.getClass().getName(), "function:removeWarehouse",
					BSRespCode.BS_REMOVE_WAREHOUSE_FAIL.getCode() + ":删除仓库失败," + whId);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_REMOVE_WAREHOUSE_FAIL, "删除仓库失败," + whId);
	}

	/**
	 * 根据id查询仓库
	 * 
	 * @Description:
	 * @param whId
	 *            仓库id
	 * @return
	 */
	@Override
	public Warehouse queryWarehouseById(String whId)
	{
		if (StringUtils.isBlank(whId))
		{
			return null;
		}
		// 查询仓库
		Warehouse wh = warehouseMapper.selectWarehouseById(whId);
		if (null != wh)
		{
			// 查询仓库配送区域
			wh.setWhArea(warehouseMapper.selectWhAreaByWhId(whId));
		}
		return wh;
	}

	/**
	 * 分页查询仓库
	 * 
	 * @Description:
	 * @param whName
	 *            仓库名称
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<Warehouse> queryWarehouseByPage(String whName, Page page)
	{
		if (null == page)
		{
			return new PageData<Warehouse>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询仓库列表
		List<Warehouse> whs = warehouseMapper.selectWarehouseListPage(whName);
		if (StringUtils.isNotBlank(whs))
		{
			return new PageData<Warehouse>(page.getCount(), whs);
		}
		return new PageData<Warehouse>(0, null);
	}

	/**
	 * 查询所有的仓库
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<Warehouse> queryWarehouseAll()
	{
		return warehouseMapper.selectWarehouseAll();
	}

	/**
	 * 新增供应商
	 * 
	 * @Description:
	 * @param bean
	 *            供应商参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addSupplier(Supplier bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addSupplier", "params==>" + bean, "新增供应商");
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增供应商参数为NULL," + bean);
		try
		{
			bean.setSupplierId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			// 插入供应商
			supplierMapper.insertSupplier(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addSupplier",
					BSRespCode.BS_ADD_SUPPLIER_FAIL.getCode() + ":新增供应商失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_SUPPLIER_FAIL, "新增供应商失败," + bean);
		}
	}

	/**
	 * 修改供应商
	 * 
	 * @Description:
	 * @param bean
	 *            供应商参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void modifySupplier(Supplier bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:modifySupplier", "params==>" + bean, "修改供应商");
		int count = 0;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "修改供应商参数为NULL," + bean);
		try
		{
			// 修改供应商
			count = supplierMapper.updateSupplier(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifySupplier",
					BSRespCode.BS_MODIFY_SUPPLIER_FAIL.getCode() + ":修改供应商异常," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_SUPPLIER_FAIL, "修改供应商失败," + bean);
	}

	/**
	 * 删除供应商
	 * 
	 * @Description:
	 * @param supplierId
	 *            供应商id
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void removeSupplier(String supplierId) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:removeSupplier", "params==>" + supplierId, "删除供应商");
		int count = 0;
		HsAssert.hasText(supplierId, RespCode.PARAM_ERROR, "删除供应商参数为NULL," + supplierId);
		try
		{
			// 删除供应商
			count = supplierMapper.deleteSupplierById(supplierId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:removeSupplier",
					BSRespCode.BS_REMOVE_SUPPLIER_FAIL.getCode() + ":删除供应商失败," + supplierId, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_REMOVE_SUPPLIER_FAIL, "删除供应商失败," + supplierId);
	}

	/**
	 * 根据id查询供应商
	 * 
	 * @Description:
	 * @param supplierId
	 *            供应商id
	 * @return
	 */
	@Override
	public Supplier querySupplierById(String supplierId)
	{
		if (StringUtils.isBlank(supplierId))
		{
			return null;
		}
		return supplierMapper.seleteSupplierById(supplierId);
	}

	/**
	 * 分页查询供应商
	 * 
	 * @Description:
	 * @param supplierName
	 *            供应商名称
	 * @param corpName
	 *            公司名称
	 * @param linkMan
	 *            联系人
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<Supplier> querySupplierByPage(String supplierName, String corpName, String linkMan, Page page)
	{
		if (null == page)
		{
			return new PageData<Supplier>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询供应商列表
		List<Supplier> sls = supplierMapper.selectSupplierListPage(supplierName, corpName, linkMan);
		if (StringUtils.isNotBlank(sls))
		{
			return new PageData<Supplier>(page.getCount(), sls);
		}
		return new PageData<Supplier>(0, null);
	}

	/**
	 * 查询所有的供应商
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<Supplier> querySupplierAll()
	{
		return supplierMapper.seleteSupplierByAll();
	}

	/**
	 * 工具入库
	 * 
	 * @Description:
	 * @param bean
	 *            工具产品入库参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void productEnter(Enter bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:productEnter", "params==>" + bean, "工具入库");
		// 入库的POS机或平板序列号
		List<String> enterSeqNo = null;
		// 设备通用信息
		List<DeviceInfo> devices = null;
		// 设备入库清单
		List<EnterDetail> details = null;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "工具入库参数为NULL," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 除刷卡器外，设置批次号
		if (!CategoryCode.isKsnCode(bean.getCategoryCode()))
		{
			bean.setEnterBatchNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
		}
		// POS机,平板,刷卡器入库生成设备通用信息和入库详单
		if (CategoryCode.isCreateDeviceInfo(bean.getCategoryCode()))
		{
			devices = new ArrayList<DeviceInfo>();
			details = new ArrayList<EnterDetail>();

			// POS机,平板
			if (CategoryCode.isSecretKeyCode(bean.getCategoryCode()))
			{
				// 设备客户号
				String deviceCustId = "";
				// 验证POS机和平板的设备序列号列表
				HsAssert.isTrue(!CollectionUtils.isEmpty(bean.getDeviceSeqNo()),
						BSRespCode.BS_POS_TABLET_ENTER_SEQNO_NULL, "入库序列号为NULL," + bean);

				// 查询已经入库的POS机或平板序列号
				enterSeqNo = deviceInfoMapper.countDeviceInfoBySeqNo(
						bean.getDeviceSeqNo().toArray(new String[bean.getDeviceSeqNo().size()]));
				HsAssert.isTrue(enterSeqNo.size() == 0, BSRespCode.BS_DEVICE_SEQNO_HAS_ENTER,
						"设备序列号有已经入库的," + JSON.toJSONString(enterSeqNo) + "," + bean);
				// 生成设备数据
				for (String seqNo : bean.getDeviceSeqNo())
				{
					// 设备客户号
					if (bean.getCategoryCode().equals(CategoryCode.P_POS.name()))
					{
						deviceCustId = StringUtil.createDeviceCustId(DeviceType.P_POS.getCode(), bsConfig.getAppNode());
					} else
					{
						deviceCustId = StringUtil.createDeviceCustId(DeviceType.TABLET.getCode(),
								bsConfig.getAppNode());
					}
					// 设备通用信息
					DeviceInfo info = new DeviceInfo(deviceCustId, seqNo, bean.getCategoryCode(), bean.getProductId(),
							StoreStatus.ENTERED.getCode(), bean.getWhId());
					info.setCreatedDate(DateUtil.getCurrentDateTime());
					info.setCreatedBy(bean.getOperNo());
					devices.add(info);

					// 入库清单
					EnterDetail detail = new EnterDetail(bean.getEnterBatchNo(), deviceCustId);
					details.add(detail);
				}
				// 设置入库POS机数据
				bean.setQuantity(bean.getDeviceSeqNo().size());
			}

			// 消费刷卡器和积分刷卡器
			if (CategoryCode.isKsnCode(bean.getCategoryCode()))
			{
				List<KsnEnterInfo> ksnInfos = null;
				// 积分刷卡器
				if (bean.getCategoryCode().equals(CategoryCode.POINT_MCR.name()))
				{
					// 获取积分刷卡器KSN信息
					ksnInfos = ksnInfoMapper.selectPointKsnInfoByEnter(bean.getEnterBatchNo());
				}
				// 消费刷卡器
				if (bean.getCategoryCode().equals(CategoryCode.CONSUME_MCR.name()))
				{
					// 获取消费刷卡器KSN信息
					ksnInfos = ksnInfoMapper.selectConsumeKsnByEnter(bean.getEnterBatchNo());
				}

				// 判断刷卡器的入库数据是否存在
				HsAssert.isTrue(!CollectionUtils.isEmpty(ksnInfos), BSRespCode.BS_KSN_ENTER_SEQNO_NULL,
						"刷卡器入库数据为NULL," + bean);

				// 判断此批次号是否已经入库
				Enter enterd = enterMapper.selectEnterByNo(bean.getEnterBatchNo());
				HsAssert.isTrue(null == enterd, BSRespCode.BS_BATCHNO_ENTERD, "该批次号已经入库," + bean);

				for (KsnEnterInfo ksnInfo : ksnInfos)
				{
					// 设备通用信息
					DeviceInfo info = new DeviceInfo(ksnInfo.getDeviceCustId(), ksnInfo.getDeviceSeqNo(),
							bean.getCategoryCode(), bean.getProductId(), StoreStatus.ENTERED.getCode(), bean.getWhId());
					info.setCreatedBy(bean.getOperNo());
					devices.add(info);

					// 入库清单
					EnterDetail detail = new EnterDetail(ksnInfo.getBatchNo(), ksnInfo.getDeviceCustId());
					details.add(detail);
				}
				// 刷卡器入库数量
				bean.setQuantity(ksnInfos.size());
			}
		}
		try
		{
			// 计算总价
			if (StringUtils.isNotBlank(bean.getPurchasePrice()) && StringUtils.isNotBlank(bean.getQuantity()))
			{
				bean.setTotalAmount(BigDecimalUtils
						.ceilingMul(bean.getPurchasePrice(), String.valueOf(bean.getQuantity())).toString());
			}
			// 插入数据 入库、入库清单、设备信息
			enterMapper.insertEnter(bean);
			if (null != details && details.size() > 0)
			{
				enterMapper.insertEnterDetail(details);
			}
			if (null != devices && devices.size() > 0)
			{
				deviceInfoMapper.batchInsertDeviceInfo(devices);
			}
			// 刷卡器修改KSN记录
			if (CategoryCode.isKsnCode(bean.getCategoryCode()))
			{
				ksnInfoMapper.updateMcrKsnRecordByNo(bean.getEnterBatchNo(), bean.getOperNo());
			}

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:productEnter",
					BSRespCode.BS_TOOL_ENTER_FAIL.getCode() + ":工具入库失败," + bean, ex);
			throw new HsException(BSRespCode.BS_TOOL_ENTER_FAIL, "工具入库失败," + bean);
		}
	}

	/**
	 * 分页查询需要配置工具库存(赠品和配套物品除外)
	 * 
	 * @Description:
	 * @param categoryCode
	 *            工具类别代码
	 * @param productId
	 *            工具产品ID
	 * @param whId
	 *            仓库ID
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolStock> queryConfigToolStockByPage(String categoryCode, String productId, String whId, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolStock>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询工具库存列表
		List<ToolStock> tools = enterMapper.selecConfigToolStockByListPage(categoryCode, productId, whId);
		if (StringUtils.isNotBlank(tools))
		{
			return new PageData<ToolStock>(page.getCount(), tools);
		}
		return new PageData<ToolStock>(0, null);
	}

	/**
	 * 查询POS机设备序列号详情
	 * 
	 * @Description:
	 * @param batchNo
	 *            批次号
	 * @return
	 */
	@Override
	public DeviceSeqNo queryPosDeviceSeqNoDetail(String batchNo)
	{
		if (StringUtils.isBlank(batchNo))
		{
			return null;
		}
		DeviceSeqNo bean = new DeviceSeqNo();
		// 查询POS机序列号
		List<String> seqNo = enterMapper.selectPosDeviceSeqNoByNo(batchNo);
		if (null != seqNo && seqNo.size() > 0)
		{
			bean.setBatchNo(batchNo);
			bean.setSeqNo(seqNo);
			// 查询已配置数量
			bean.setConfigNum(enterMapper.selectPosConfigNumByNo(batchNo));
			return bean;
		}
		return null;
	}

	/**
	 * 工具库存盘库
	 * 
	 * @Description:
	 * @param bean
	 *            工具库存盘库参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void toolEnterInventory(WhInventory bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:toolEnterInventory", "params==>" + bean, "工具库存盘库");
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "工具库存盘库参数为NULL," + bean);
		try
		{
			bean.setInventoryId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			// 插入工具库存盘库
			enterMapper.insertWhInventory(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:toolEnterInventory",
					BSRespCode.BS_TOOL_STOCK_INVENTORY_FAIL.getCode() + ":工具库存盘库失败," + bean, ex);
			throw new HsException(BSRespCode.BS_TOOL_STOCK_INVENTORY_FAIL, "工具库存盘库失败," + bean);
		}
	}

	/**
	 * 工具入库抽检
	 * 
	 * @Description:
	 * @param bean
	 *            工具入库抽检参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void toolEnterCheck(InstorageInspection bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:toolEnterCheck", "params==>" + bean, "工具入库抽检");
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "工具入库抽检参数为NULL," + bean);
		try
		{
			bean.setInspectionId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			// 插入工具入库抽检
			enterMapper.insertEnterInspection(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:toolEnterCheck",
					BSRespCode.BS_TOOL_ENTER_INSPECTION_FAIL.getCode() + ":工具入库抽检失败," + bean, ex);
			throw new HsException(BSRespCode.BS_TOOL_ENTER_INSPECTION_FAIL, "工具入库抽检失败," + bean);
		}
	}

	/**
	 * 分页查询工具库存预警
	 * 
	 * @Description:
	 * @param productName
	 *            工具产品名称
	 * @param categoryCode
	 *            工具类别代码
	 * @param warningStatus
	 *            预警状态
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolStockWarning> toolEnterStockWarningPage(String productName, String categoryCode,
			Integer warningStatus, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolStockWarning>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询工具预警列表
		List<ToolStockWarning> warnings = enterMapper.toolEnterStockWarningListPage(productName, categoryCode,
				warningStatus);
		if (StringUtils.isNotBlank(warnings))
		{
			return new PageData<ToolStockWarning>(page.getCount(), warnings);
		}
		return new PageData<ToolStockWarning>(0, null);
	}

	/**
	 * 分页查询工具设备使用
	 * 
	 * @Description:
	 * @param deviceSeqNo
	 *            设备序列号
	 * @param useStatus
	 *            使用状态
	 * @param batchNo
	 *            批次号
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<DeviceInfoPage> queryToolDeviceUsePage(String deviceSeqNo, Integer useStatus, String batchNo,
			Page page)
	{
		if (null == page)
		{
			return new PageData<DeviceInfoPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询工具使用记录列表
		List<DeviceInfoPage> devices = deviceUseRecordMapper.selectToolDeviceUseListPage(deviceSeqNo, useStatus,
				batchNo);
		if (StringUtils.isNotBlank(devices))
		{
			return new PageData<DeviceInfoPage>(page.getCount(), devices);
		}
		return new PageData<DeviceInfoPage>(0, null);
	}

	/**
	 * 根据设备序列号查询配置详情(领用和报损除外)
	 * 
	 * @Description:
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 */
	@Override
	public ToolConfig queryDeviceConfigDetail(String deviceSeqNo)
	{
		if (StringUtils.isBlank(deviceSeqNo))
		{
			return null;
		}
		return toolConfigMapper.selectDeviceConfigDetail(deviceSeqNo);
	}

	/**
	 * 使用设备查询设备清单(报损和领用)
	 * 
	 * @Description:
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 */
	@Override
	public DeviceDetail queryDeviceDetailByNo(String deviceSeqNo)
	{
		if (StringUtils.isBlank(deviceSeqNo))
		{
			return null;
		}
		return commonMapper.selectDeviceDetailByNo(deviceSeqNo);
	}

	/**
	 * 添加工具使用记录
	 * 
	 * @Description:
	 * @param bean
	 *            工具使用记录参数实体
	 * @param custId
	 *            客户号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addDeviceUseRecord(DeviceUseRecord bean, String custId) throws HsException
	{
		String inParam = "record:" + bean + "custId:" + custId;
		BizLog.biz(this.getClass().getName(), "function:addDeviceUseRecord", "params==>" + inParam, "添加工具使用记录");
		// 记录id
		String recordId = "";
		// 备注
		String remark = "";
		// 使用状态
		int useStatus = 0;
		// 入库实体
		Enter enter = null;
		// 设备信息
		DeviceInfo device = null;
		int count = 0;
		// 验证参数
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增工具使用记录参数为NULLL" + inParam);
		HsAssert.hasText(custId, RespCode.PARAM_ERROR, "新增工具使用记录客户号NULL," + inParam);
		// 查询入库批次是否存在
		enter = enterMapper.selectEnterByNo(bean.getBatchNo());
		HsAssert.notNull(enter, RespCode.PARAM_ERROR, "批次号错误,查询不到相应的入库记录," + inParam);
		// 验证设备是否存在和状态是否未使用
		device = deviceInfoMapper.selectDeviceInfoByNo(bean.getDeviceSeqNo(), bean.getBatchNo());
		HsAssert.notNull(device, BSRespCode.BS_BATCH_DEVICE_NOT_EXIST, "当前批次的设备不存在," + inParam);
		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用状态," + inParam);
		try
		{
			recordId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			bean.setRecordId(recordId);
			// 插入设备使用记录
			deviceUseRecordMapper.insertDeviceUseRecord(bean);

			if (bean.getUseType() == UseType.USED.getCode())
			{
				remark = enter.getCategoryCode() + "领用出库";
				useStatus = UseStatus.BEEN_USED.getCode();
			} else
			{
				remark = enter.getCategoryCode() + "报损出库";
				useStatus = UseStatus.REPORTED_LOSS.getCode();
				deviceInfoMapper.deleteDeviceConfigByNo(bean.getDeviceSeqNo());
			}
			// 出库
			String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			// 出库明细
			outMapper.insertOut(new Out(batchNo, enter.getCategoryCode(), enter.getProductId(), enter.getWhId(),
					bean.getUseType(), recordId, 1, null, bean.getCreatedBy(), remark));
			// 出库明细清单
			outMapper.insertOutDetail(new OutDetail(batchNo, device.getDeviceCustId()));

			// 修改设备使用状态
			count = deviceInfoMapper.updateDeviceUseStatusById(device.getDeviceCustId(), useStatus,
					StoreStatus.OUTED.getCode(), bean.getCreatedBy());

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addDeviceUseRecord",
					BSRespCode.BS_ADD_DEVICE_USE_RECORD_FAIL.getCode() + ":新增工具使用记录异常," + inParam, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_ADD_DEVICE_USE_RECORD_FAIL, "新增工具使用记录失败," + inParam);
	}

	/**
	 * 分页查询工具入库流水
	 * 
	 * @Description:
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolEnterOutPage> queryToolEnterSerialPage(ToolProductVo param, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolEnterOutPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		param.setQueryDate();
		// 分页查询工具入库流水列表
		List<ToolEnterOutPage> enters = enterMapper.selectToolEnterSerialListPage(param);
		if (StringUtils.isNotBlank(enters))
		{
			return new PageData<ToolEnterOutPage>(page.getCount(), enters);
		}
		return new PageData<ToolEnterOutPage>(0, null);
	}

	/**
	 * 分页查询工具出库流水
	 * 
	 * @Description:
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolEnterOutPage> queryToolOutSerialPage(ToolProductVo param, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolEnterOutPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		param.setQueryDate();
		// 查询工具出库流水列表
		List<ToolEnterOutPage> outs = outMapper.selectToolOutSerialListPage(param);
		if (StringUtils.isNotBlank(outs))
		{
			return new PageData<ToolEnterOutPage>(page.getCount(), outs);
		}
		return new PageData<ToolEnterOutPage>(0, null);
	}

	/**
	 * 分页统计地区平台仓库工具
	 * 
	 * @Description:
	 * @param categoryCode
	 *            工具类别代码
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolStock> statisticPlatWhTool(String categoryCode, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolStock>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询平台工具列表
		List<ToolStock> stocks = enterMapper.statisticPlatWhToolListPage(categoryCode);
		if (StringUtils.isNotBlank(stocks))
		{
			return new PageData<ToolStock>(page.getCount(), stocks);
		}
		return new PageData<ToolStock>(0, null);
	}

	/**
	 * 分页查询配置单
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfig> queryToolConfigPage(BaseParam bean, Page page)
	{
		if (null == page || null == bean || StringUtils.isBlank(bean.getRoleIds()))
		{
			return new PageData<ToolConfig>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置角色列表
		bean.setRoleId();
		// 查询配置单列表
		List<ToolConfig> confs = toolConfigMapper.selectToolConfigListPage(bean);
		if (StringUtils.isNotBlank(confs))
		{
			return new PageData<ToolConfig>(page.getCount(), confs);
		}
		return new PageData<ToolConfig>(0, null);
	}

	/**
	 * 修改配置单仓库id
	 * 
	 * @Description:
	 * @param bean
	 *            配置单参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void modifyToolConfigWhId(ToolConfig bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:modifyToolConfigWhId", "params==>" + bean, "修改配置单仓库id");
		int count = 0;
		// 验证参数实体、配置单号、仓库id
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "修改配置单仓库id参数为NULL," + bean);
		HsAssert.hasText(bean.getConfNo(), RespCode.PARAM_ERROR, "修改配置单配置单号为NULL," + bean);
		HsAssert.hasText(bean.getWhId(), RespCode.PARAM_ERROR, "修改配置单仓库id为NULL," + bean);

		// 验证配置单是否可以转仓库
		count = toolConfigMapper.toolConfigIsUpdateWh(bean.getConfNo());
		HsAssert.isTrue(count > 0, BSRespCode.BS_TOOL_CONFIG_NOT_MODIFY_WH, "配置单状态不对或已经进行配置了，不能修改仓库," + bean);
		try
		{
			// 设置配置状态、卡样id、确认文件为空,防止参数错误填入
			bean.setConfStatus(null);
			bean.setCardStyleId(null);
			bean.setConfirmFile(null);
			// 修改配置单的仓库id
			count = toolConfigMapper.updateToolConfigByConfNo(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifySupplier",
					BSRespCode.BS_MODIFY_TOOL_CONFIGE_WHID_FAIL.getCode() + ":修改配置单仓库id失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_TOOL_CONFIGE_WHID_FAIL, "修改配置单仓库id失败," + bean);
	}
}
