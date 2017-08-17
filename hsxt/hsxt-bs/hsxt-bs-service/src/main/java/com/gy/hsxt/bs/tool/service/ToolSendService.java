/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.tool.IBSToolSendService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.tool.CardInOut;
import com.gy.hsxt.bs.bean.tool.DeliveryCorp;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.bs.bean.tool.ShippingMethod;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.ConfStatus;
import com.gy.hsxt.bs.common.enumtype.tool.ConfType;
import com.gy.hsxt.bs.common.enumtype.tool.DeliveryStatus;
import com.gy.hsxt.bs.common.enumtype.tool.ShippingType;
import com.gy.hsxt.bs.common.enumtype.tool.ToolOut;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tool.bean.DeviceConfig;
import com.gy.hsxt.bs.tool.bean.Out;
import com.gy.hsxt.bs.tool.bean.OutDetail;
import com.gy.hsxt.bs.tool.mapper.CardInOutMapper;
import com.gy.hsxt.bs.tool.mapper.CommonMapper;
import com.gy.hsxt.bs.tool.mapper.DeliveryCorpMapper;
import com.gy.hsxt.bs.tool.mapper.DeviceInfoMapper;
import com.gy.hsxt.bs.tool.mapper.EnterMapper;
import com.gy.hsxt.bs.tool.mapper.OutMapper;
import com.gy.hsxt.bs.tool.mapper.ShippingMapper;
import com.gy.hsxt.bs.tool.mapper.ShippingMethodMapper;
import com.gy.hsxt.bs.tool.mapper.ToolConfigMapper;
import com.gy.hsxt.bs.tool.mapper.ToolProductMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;

/**
 * 工具发货API接口实现类
 * 
 * @Package: com.hsxt.bs.btool.service
 * @ClassName: ToolSendService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午2:04:09
 * @company: gyist
 * @version V3.0.0
 */
@Service("toolSendService")
public class ToolSendService implements IBSToolSendService {

	/** 业务服务私有配置参数 **/
	@Autowired
	private BsConfig bsConfig;

	/** 配送方式Mapper **/
	@Autowired
	private ShippingMethodMapper shippingMethodMapper;

	/** 快递公司Mapper **/
	@Autowired
	private DeliveryCorpMapper deliveryCorpMapper;

	/** 发货单Mapper **/
	@Autowired
	private ShippingMapper shippingMapper;

	/** 配置单Mapper **/
	@Autowired
	private ToolConfigMapper toolConfigMapper;

	/** 公共Mapper **/
	@Autowired
	private CommonMapper commonMapper;

	/** 工具产品Mapper **/
	@Autowired
	private ToolProductMapper toolProductMapper;

	/** 工具入库Mapper **/
	@Autowired
	private EnterMapper enterMapper;

	/** 设备Mapper **/
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;

	/** 出库Mapper **/
	@Autowired
	private OutMapper outMapper;

	/** 互生卡出库Mapper **/
	@Autowired
	private CardInOutMapper cardInOutMapper;

	/** BS公共Service **/
	@Autowired
	private ICommonService commonService;

	/**
	 * 新增配送方式
	 * 
	 * @Description:
	 * @param bean
	 *            配送方式参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addShippingMethod(ShippingMethod bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addShippingMethod", "params==>" + bean, "新增配送方式");
		// 验证非null
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增配送方式参数为NULL," + bean);
		try
		{
			bean.setSmId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			// 插入配置方式
			shippingMethodMapper.insertShippingMethod(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addShippingMethod",
					BSRespCode.BS_ADD_SHIPPING_METHOD_FAIL.getCode() + ":新增配送方式失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_SHIPPING_METHOD_FAIL, "新增配送方式失败," + bean);
		}
	}

	/**
	 * 修改配送方式
	 * 
	 * @Description:
	 * @param bean
	 *            配送方式
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void modifyShippingMethod(ShippingMethod bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:modifyShippingMethod", "params==>" + bean, "修改配送方式");
		int count = 0;
		// 验证非null
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "修改配送方式参数为NULL," + bean);
		try
		{
			// 修改配送方式
			count = shippingMethodMapper.updateShippingMethodById(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifyShippingMethod",
					BSRespCode.BS_MODIFY_SHIPPING_METHOD_FAIL.getCode() + ":修改配送方式失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_SHIPPING_METHOD_FAIL, "修改配送方式失败," + bean);
	}

	/**
	 * 删除配送方式
	 * 
	 * @Description:
	 * @param smId
	 *            配送方式id
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void removeShippingMethod(String smId) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:removeShippingMethod", "params==>" + smId, "删除配送方式");
		int count = 0;
		// 验证非null
		HsAssert.hasText(smId, RespCode.PARAM_ERROR, "删除配送方式参数为null," + smId);
		try
		{
			// 删除配送方式
			count = shippingMethodMapper.deleteShippingMethodById(smId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifyShippingMethod",
					BSRespCode.BS_REMOVE_SHIPPING_METHOD_FAIL.getCode() + ":删除配送方式失败," + smId, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_REMOVE_SHIPPING_METHOD_FAIL, "删除配送方式失败," + smId);
	}

	/**
	 * 分页查询配送方式
	 * 
	 * @Description:
	 * @param smName
	 *            配送方式名称
	 * @param page
	 *            分页参数实体
	 * @param :
	 * @return
	 */
	@Override
	public PageData<ShippingMethod> queryShippingMethodByPage(String smName, Page page)
	{
		if (null == page)
		{
			return new PageData<ShippingMethod>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询配送方式列表
		List<ShippingMethod> methods = shippingMethodMapper.selectShippingMethodListPage(smName);
		if (StringUtils.isNotBlank(methods))
		{
			return new PageData<ShippingMethod>(page.getCount(), methods);
		}
		return new PageData<ShippingMethod>(0, null);
	}

	/**
	 * 根据id查询配送方式
	 * 
	 * @Description:
	 * @param smId
	 *            配送方式id
	 * @return
	 */
	@Override
	public ShippingMethod queryShippingMethodById(String smId)
	{
		if (StringUtils.isBlank(smId))
		{
			return null;
		}
		return shippingMethodMapper.selectShippingMeghodById(smId);
	}

	/**
	 * 新增快递公司
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addDeliveryCorp(DeliveryCorp bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addDeliveryCorp", "params==>" + bean, "新增快递公司");
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增快递公司参数为NULL," + bean);
		try
		{
			bean.setDcId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			deliveryCorpMapper.insertDeliveryCorp(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addDeliveryCorp",
					BSRespCode.BS_ADD_DELIVERY_CORP_FAIL.getCode() + ":新增快递公司失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_DELIVERY_CORP_FAIL, "新增快递公司失败," + bean);
		}
	}

	/**
	 * 修改快递公司
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void modifyDeliveryCorp(DeliveryCorp bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:modifyDeliveryCorp", "params==>" + bean, "修改快递公司");
		int count = 0;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "修改快递公司参数为NULL," + bean);
		try
		{
			count = deliveryCorpMapper.updateDeliveryCorp(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifyDeliveryCorp",
					BSRespCode.BS_MODIFY_DELIVERY_CORP_FAIL.getCode() + ":修改快递公司失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_DELIVERY_CORP_FAIL, "修改快递公司失败," + bean);
	}

	/**
	 * 删除快递公司
	 * 
	 * @Description:
	 * @param dcId
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void removeDeliveryCorp(String dcId) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:removeDeliveryCorp", "params==>" + dcId, "删除快递公司");
		int count = 0;
		HsAssert.hasText(dcId, RespCode.PARAM_ERROR, "删除快递公司参数为NULL," + dcId);
		try
		{
			count = deliveryCorpMapper.deleteDeliveryCorpById(dcId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:removeDeliveryCorp",
					BSRespCode.BS_REMOVE_DELIVERY_CORP_FAIL.getCode() + ":删除快递公司失败," + dcId, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_REMOVE_DELIVERY_CORP_FAIL, "删除快递公司失败," + dcId);
	}

	/**
	 * 分页查询快递公司
	 * 
	 * @Description:
	 * @param corpName
	 * @param page
	 * @return
	 */
	@Override
	public PageData<DeliveryCorp> queryDeliveryCorpByPage(String corpName, Page page)
	{
		if (null == page)
		{
			return new PageData<DeliveryCorp>(0, null);
		}
		PageContext.setPage(page);
		List<DeliveryCorp> corps = deliveryCorpMapper.selectDeliveryCorpListPage(corpName);
		if (StringUtils.isNotBlank(corps))
		{
			return new PageData<DeliveryCorp>(page.getCount(), corps);
		}
		return new PageData<DeliveryCorp>(0, null);
	}

	/**
	 * 根据id查询快递公司
	 * 
	 * @Description:
	 * @param dcId
	 * @return
	 */
	@Override
	public DeliveryCorp queryDeliveryCorpById(String dcId)
	{
		if (StringUtils.isBlank(dcId))
		{
			return null;
		}
		return deliveryCorpMapper.selectDeliveryCorpById(dcId);
	}

	/**
	 * 查询发货单的数据(页面显示)
	 * 
	 * @Description:
	 * @param orderType
	 *            订单类型
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 */
	@Override
	public ShippingData queryShipingData(String orderType, String[] confNo) throws HsException
	{
		String inParam = "orderType:" + orderType + "confNo:" + JSON.toJSONString(confNo);
		// 收货信息
		List<DeliverInfo> delivers = null;
		// 收货信息
		DeliverInfo deliver = null;
		// 互生号
		List<String> resNo = null;
		int confCount = 0;
		// 企业名称
		String entName = "";
		// 企业信息
		BsEntAllInfo entInfo = null;

		HsAssert.hasText(orderType, RespCode.PARAM_ERROR, "查询发货单的数据订单类型为NULL," + inParam);
		// 验证订单类型
		HsAssert.isTrue(normalSendConfType(orderType) > 0, RespCode.PARAM_ERROR, "查询发货单的数据订单类型错误," + inParam);
		// 验证配置单号
		HsAssert.isTrue(StringUtils.isNotBlank(confNo), RespCode.PARAM_ERROR, "查询发货单的数据配置单为NULL," + inParam);

		// 验证配置单的类型是否一致
		confCount = commonMapper.vaildToolConfigType(confNo, normalSendConfType(orderType));
		// 验证配置单的配置类型是否一致
		HsAssert.isTrue(confNo.length == confCount, BSRespCode.BS_TOOL_CONFIG_TYPE_NOT_SAME, "工具配置单类型不相同," + inParam);
		// 个人补卡
		if (orderType.equals(OrderType.REMAKE_MY_CARD.getCode()))
		{
			// 验证是否是同一个消费者的配置单
			resNo = commonMapper.vaildShippingIsSameEnt(confNo);
			HsAssert.isTrue(null != resNo && resNo.size() == 1, BSRespCode.BS_IS_NOT_SAME_PRE, "不是同一消费者," + inParam);
			// 查询收货信息
			delivers = commonMapper.vaildShippingIsSameAddr(confNo);
			// 新增申购(新增申购工具、系统资源申购、企业重做卡)
		} else if (orderType.equals(OrderType.BUY_TOOL.getCode())
				|| orderType.equals(OrderType.APPLY_PERSON_RESOURCE.getCode())
				|| orderType.equals(OrderType.REMAKE_BATCH_CARD.getCode()))
		{
			// 验证是否是同一个企业的配置单
			resNo = commonMapper.vaildShippingIsSameEnt(confNo);
			HsAssert.isTrue(null != resNo && resNo.size() == 1, BSRespCode.BS_IS_NOT_SAME_ENT, "不是同一企业," + inParam);

			// 验证是否配置单的所有地址是否一样
			delivers = commonMapper.vaildShippingIsSameAddr(confNo);
			HsAssert.isTrue(delivers.size() == 1, BSRespCode.BS_IS_NOT_SAME_ARRD, "不是同一地址," + inParam);
			// 申报申购
		} else if (orderType.equals(OrderType.APPLY_BUY_TOOL.getCode())
				|| orderType.equals(OrderType.RES_FEE_ALLOT.getCode()))
		{
			// 验证是否是否同一个服务公司下的企业
			List<String> resNos = commonMapper.vaildShippingIsSameEntApp(confNo);

			HsAssert.isTrue(null != resNos && resNos.size() == 1, BSRespCode.BS_IS_NOT_S_SAME_ENT,
					"不是同一服务公司下的企业," + inParam);
			String sEntResNo = resNos.get(0) + "000000";
			// 用户中心查询收货信息
			entInfo = commonService.getEntAllInfoToUc(sEntResNo, null);

			// 验证调用企业信息
			HsAssert.notNull(entInfo, BSRespCode.BS_NOT_QUERY_ENT_INFO, "申报订单类型,未查询到服务公司信息," + inParam);
			HsAssert.notNull(entInfo.getMainInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO, "申报订单类型,未查询到服务公司信息," + inParam);
			HsAssert.notNull(entInfo.getBaseInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO, "申报订单类型,未查询到服务公司信息," + inParam);

			// 企业名称
			entName = StringUtils.isNotBlank(entInfo.getBaseInfo().getEntName()) ? entInfo.getBaseInfo().getEntName()
					: entInfo.getBaseInfo().getEntNameEn();

			delivers = new ArrayList<DeliverInfo>();
			// 联系人地址
			deliver = new DeliverInfo();
			deliver.setLinkman(entInfo.getMainInfo().getContactPerson());// 联系人
			deliver.setMobile(entInfo.getMainInfo().getContactPhone());// 联系人电话
			String contactAddr = entInfo.getBaseInfo().getContactAddr();
			deliver.setFullAddr(StringUtils.isNotBlank(contactAddr) ? contactAddr : "" + "(" + entName + ")");// 联系人地址
			deliver.setZipCode(entInfo.getBaseInfo().getPostCode());// 邮编
			deliver.setAddrType("contact");// 地址类型
			delivers.add(deliver);
			// 企业地址
			deliver = new DeliverInfo();
			deliver.setLinkman(entInfo.getMainInfo().getContactPerson());// 联系人
			deliver.setMobile(entInfo.getMainInfo().getContactPhone());// 联系人电话
			deliver.setFullAddr(entInfo.getMainInfo().getEntRegAddr() + "(" + entName + ")");// 企业地址
			deliver.setZipCode(entInfo.getBaseInfo().getPostCode());// 邮编
			deliver.setAddrType("ent");// 地址类型
			delivers.add(deliver);
		}

		ShippingData bean = new ShippingData();
		List<ToolConfig> confs = toolConfigMapper.selectToolConfigByIds(confNo, ConfStatus.WAIT_SEND.getCode());
		// 验证配置单状态
		HsAssert.isTrue(confs != null ? confs.size() == confNo.length : false,
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_SEND, "配置单号列表有不是待发货的配置单");
		// 工具id
		bean.setWhId(confs.get(0).getWhId());
		// 配置单
		bean.setConfigs(confs);
		// 个人补卡不需要
		if (!orderType.equals(OrderType.REMAKE_MY_CARD.getCode()))
		{
			// 赠品工具
			bean.setGiftProduct(toolProductMapper.selectToolProductByCode(new String[] { CategoryCode.GIFT.name() }));
			// 配套工具
			bean.setSupportProduct(
					toolProductMapper.selectToolProductByCode(new String[] { CategoryCode.SUPPORT.name() }));
		}
		// 收货信息
		bean.setDelivers(delivers);
		// 配送方式
		bean.setMothods(shippingMethodMapper.selectShippingMethodAll());
		return bean;
	}

	/**
	 * 正常发货的配置类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月11日 下午7:17:51
	 * @param orderType
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int normalSendConfType(String orderType)
	{
		int confType = 0;
		switch (OrderType.getOrderType(orderType))
		{
		case RES_FEE_ALLOT:
			confType = 1;
			break;
		case APPLY_BUY_TOOL:
			confType = 1;
			break;
		case BUY_TOOL:
			confType = 2;
			break;
		case APPLY_PERSON_RESOURCE:
			confType = 2;
			break;
		case REMAKE_MY_CARD:
			confType = 3;
			break;
		case REMAKE_BATCH_CARD:
			confType = 3;
			break;
		default:
			break;
		}
		return confType;
	}

	/**
	 * 查询入库的免费工具
	 * 
	 * @Description:
	 * @param productId
	 *            工具产品id
	 * @param whId
	 *            仓库id
	 * @return
	 */
	@Override
	public String queryToolEnterByForFree(String productId, String whId)
	{
		if (StringUtils.isBlank(productId) || StringUtils.isBlank(whId))
		{
			return null;
		}
		return enterMapper.selectToolEnterByForFree(productId, whId);
	}

	/**
	 * 分页查询工具配置单(生成发货单)--新增申购
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryToolConfigDeliveryPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 设置角色列表
		bean.setRoleId();
		// 设置订单类型
		bean.setOrderType(StringUtil.assembleQueryInIf(
				new String[] { OrderType.BUY_TOOL.getCode(), OrderType.APPLY_PERSON_RESOURCE.getCode(),
						OrderType.REMAKE_MY_CARD.getCode(), OrderType.REMAKE_BATCH_CARD.getCode() }));
		// 查询配置单列表
		List<ToolConfigPage> confs = toolConfigMapper.selectToolConfigShippingAddListPage(bean);
		if (StringUtils.isNotBlank(confs))
		{
			return new PageData<ToolConfigPage>(page.getCount(), confs);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 分页查询工具配送单(生成发货单)申报申购
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryToolConfigDeliveryAppPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 设置角色列表
		bean.setRoleId();
		// 设置订单类型
		bean.setOrderType(StringUtil.assembleQueryInIf(new String[] { OrderType.APPLY_BUY_TOOL.getCode() }));
		// 查询配置单列表
		List<ToolConfigPage> confs = toolConfigMapper.selectToolConfigShippingAppListPage(bean);
		if (StringUtils.isNotBlank(confs))
		{
			return new PageData<ToolConfigPage>(page.getCount(), confs);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 添加发货单
	 * 
	 * @Description:
	 * @param bean
	 *            发货单参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addToolShipping(Shipping bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addToolShipping", "params==>" + bean, "添加发货单");
		// 赠品和配套物品
		List<ToolConfig> configs = null;
		// 主产品
		List<ToolConfig> hostConfigs = null;
		// 除互生卡外的主产品出库单
		List<Out> outs = null;
		// 除互生卡外的主产品出库清单
		List<OutDetail> outDetails = null;
		// 互生卡出库
		List<CardInOut> inOuts = null;
		// 发货单编号
		String shippingId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		// 设备客户号
		List<String> deviceCustIds = null;
		// 验证非null、发货单类型
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "添加发货单参数为NULL," + bean);
		HsAssert.isTrue(bean.getShippingType().intValue() != ShippingType.AFTER_TOOL.getCode(),
				BSRespCode.BS_SHIPPING_TYPE_ERROR, "发货单类型错误," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 设置赠品和配套物品配置单参数
		configs = bean.getConfigs();
		// 出库流水、清单、互生卡出入库流水、设备客户号列表
		outs = new ArrayList<Out>();
		outDetails = new ArrayList<OutDetail>();
		inOuts = new ArrayList<CardInOut>();
		deviceCustIds = new ArrayList<String>();
		// 配置单
		for (ToolConfig config : configs)
		{
			// 批次号
			String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

			config.setConfNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			config.setConfStatus(ConfStatus.SENDED.getCode());
			// 新增
			if (bean.getShippingType() == ShippingType.BUY_TOOL.getCode())
			{
				config.setConfType(ConfType.ADD_CONFIG.getCode());
				// 申报
			} else if (bean.getShippingType() == ShippingType.APPLY_TOOL.getCode())
			{
				config.setConfType(ConfType.APPLY_CONFIG.getCode());
			}
			// 设置配置单参数
			config.setPrice("0");
			config.setTotalAmount("0");
			config.setShippingId(shippingId);
			config.setConfUser(bean.getConsignor());
			config.setStoreOutNo(batchNo);
			// 赠品或配套物品出库
			outs.add(new Out(batchNo, config.getCategoryCode(), config.getProductId(), config.getWhId(),
					ToolOut.BUY.getCode(), null, config.getQuantity(), null, bean.getConsignor(),
					config.getCategoryCode() + "出库"));

		}
		// 获取主产品配置单
		hostConfigs = toolConfigMapper.selectToolConfigByIds(bean.getConfNos(), ConfStatus.WAIT_SEND.getCode());
		// 验证配置单状态
		HsAssert.isTrue(hostConfigs != null ? hostConfigs.size() == bean.getConfNos().length : false,
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_SEND, "配置单号列表有不是待发货的配置单," + bean);

		// 主产品
		for (ToolConfig conf : hostConfigs)
		{
			// 互生卡
			if (conf.getCategoryCode().equals(CategoryCode.P_CARD.name()))
			{
				inOuts.add(new CardInOut(conf.getOrderNo(), bean.getConsignor()));
				// POS机、积分|消费刷卡器、平板
			} else
			{
				// 出库
				String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				outs.add(new Out(batchNo, conf.getCategoryCode(), conf.getProductId(), conf.getWhId(),
						ToolOut.BUY.getCode(), null, conf.getQuantity(), null, bean.getConsignor(),
						conf.getCategoryCode() + "出库"));
				// 出库清单
				List<DeviceConfig> dConfs = deviceInfoMapper.selectDeviceConfigByNo(conf.getConfNo());
				for (DeviceConfig dConf : dConfs)
				{
					// 出库清单
					outDetails.add(new OutDetail(batchNo, dConf.getDeviceCustId()));
					// 设备客户号
					deviceCustIds.add(dConf.getDeviceCustId());
				}
				conf.setStoreOutNo(batchNo);
			}
			conf.setShippingId(shippingId);
		}

		bean.setShippingId(shippingId);
		bean.setDeliveryStatus(DeliveryStatus.SENDED.getCode());
		try
		{
			// 插入发货单
			shippingMapper.insertShipping(bean);
			if (configs.size() > 0)
			{
				// 插入赠品或配套物品的配置单
				toolConfigMapper.batchInsertToolConfig(configs);
			}
			// 插入出库单
			if (outs.size() > 0)
			{
				outMapper.batchInsertOut(outs);

			}
			// 插入出库清单
			if (outDetails.size() > 0)
			{
				outMapper.batchInsertOutDetail(outDetails);
			}
			// 插入互生卡出库
			if (inOuts.size() > 0)
			{
				cardInOutMapper.batchUpdateCardInOut(inOuts);
			}
			// 批量修改配置单的状态
			toolConfigMapper.updateToolConfigByShipping(hostConfigs);
			if (deviceCustIds.size() > 0)
			{
				// 批量修改设备已使用出库
				deviceInfoMapper.batchUpdateDeviceOutStock(deviceCustIds, bean.getConsignor());
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addToolShipping",
					BSRespCode.BS_ADD_SHIPPING_FAIL.getCode() + ":新增发货单失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_SHIPPING_FAIL, "新增发货单失败," + bean);
		}
	}

	/**
	 * 分页查询配送单(新增申报打印配送单)
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolShippingPage> queryShippingPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolShippingPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 查询发货单列表
		List<ToolShippingPage> shipps = shippingMapper.selectShippingListPage(bean);
		if (StringUtils.isNotBlank(shipps))
		{
			return new PageData<ToolShippingPage>(page.getCount(), shipps);
		}
		return new PageData<ToolShippingPage>(0, null);
	}

	/**
	 * 根据id查询发货单
	 * 
	 * @Description:
	 * @param shippingId
	 *            发货单id
	 * @return
	 */
	@Override
	public Shipping queryShippingById(String shippingId)
	{
		if (StringUtils.isBlank(shippingId))
		{
			return null;
		}
		// 获取发货单
		Shipping bean = shippingMapper.selectShippingById(shippingId);
		if (null != bean && bean.getShippingType().intValue() != ShippingType.AFTER_TOOL.getCode())
		{
			// 获取配置单
			bean.setConfigs(toolConfigMapper.selectToolConfigByShippingId(shippingId));

			// 申报发货
			if (bean.getShippingType().intValue() == ShippingType.APPLY_TOOL.getCode())
			{
				bean.setApplyEntInfo(commonMapper.selectApplyShippingHavEnt(shippingId));
			}
		}
		return bean;
	}

	/**
	 * 工具签收
	 * 
	 * @Description:
	 * @param bean
	 *            发货单参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	@Deprecated
	public void toolSignAccept(Shipping bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:toolSignAccept", "params==>" + bean, "工具签收");
		int count = 0;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "签收发货单参数为null," + bean);
		try
		{
			// 签收发货单
			bean.setDeliveryStatus(DeliveryStatus.SIGNED.getCode());
			count = shippingMapper.updateShippingById(bean);
			if (count > 0)
			{
				// 签收配置单
				count = toolConfigMapper.updateToolConfigBySign(bean.getShippingId());
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:toolSignAccept",
					BSRespCode.BS_TOOL_SIGN_ACCEPT_FAIL.getCode() + ":签收发货单失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_TOOL_SIGN_ACCEPT_FAIL, "签收发货单失败," + bean);
	}
}
