/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.tool.IBSToolProductService;
import com.gy.hsxt.bs.bean.base.ApprInfo;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ToolCategory;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.ToolProductUpDown;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.EnableStatus;
import com.gy.hsxt.bs.common.enumtype.tool.CardStyleApprStatus;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.ToolApprStatus;
import com.gy.hsxt.bs.common.enumtype.tool.ToolStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.util.EnumCheckUtil;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tool.mapper.CardStyleMapper;
import com.gy.hsxt.bs.tool.mapper.ToolProductMapper;
import com.gy.hsxt.bs.tool.mapper.ToolProductUpDownMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工具产品API接口实现类
 * 
 * @Package: com.hsxt.bs.btool.service
 * @ClassName: ToolProductService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 上午11:21:59
 * @company: gyist
 * @version V3.0.0
 */
@Service("toolProductService")
public class ToolProductService implements IBSToolProductService {

	/** 业务服务私有配置参数 **/
	@Autowired
	private BsConfig bsConfig;

	/** 工具产品Mapper **/
	@Autowired
	private ToolProductMapper toolProductMapper;

	/** 工具产品上下架Mapper **/
	@Autowired
	private ToolProductUpDownMapper toolProductUpDownMapper;

	/** 互生卡样Mapper **/
	@Autowired
	private CardStyleMapper cardStyleMapper;

	/** 任务Service **/
	@Autowired
	private ITaskService taskService;

	/** 公共Service **/
	@Autowired
	private ICommonService commonService;

	/**
	 * 查询所有的工具类别
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<ToolCategory> queryToolCategoryAll()
	{
		return toolProductMapper.selectToolCategoryAll();
	}

	/**
	 * 根据id查询工具产品
	 * 
	 * @Description:
	 * @param productId
	 *            工具产品id
	 * @return
	 */
	@Override
	public ToolProduct queryToolProductById(String productId)
	{
		if (StringUtils.isBlank(productId))
		{
			return null;
		}
		return toolProductMapper.selectToolProductById(productId);
	}

	/**
	 * 分页查询工具产品
	 * 
	 * @Description:
	 * @param vo
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolProduct> queryToolProductPage(ToolProductVo vo, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolProduct>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询工具产品列表
		List<ToolProduct> tools = toolProductMapper.selectToolProductListPage(vo);
		if (StringUtils.isNotBlank(tools))
		{
			return new PageData<ToolProduct>(page.getCount(), tools);
		}
		return new PageData<ToolProduct>(0, null);
	}

	/**
	 * 新增工具产品申请
	 * 
	 * @Description:
	 * @param product
	 *            工具产品参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addToolProduct(ToolProduct product) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "新增工具产品:addToolProduct", "params==>", product.toString());
		// 工具编号
		String productId = "";
		HsAssert.notNull(product, RespCode.PARAM_ERROR, "新增工具产品参数为NULL," + product);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(product);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + product);
		// 验证工具类别代码枚举值
		HsAssert.isTrue(EnumCheckUtil.checkEnumIncludeValue(CategoryCode.class, product.getCategoryCode()),
				RespCode.PARAM_ERROR, "参数工具类别代码值错误," + product);

		// 验证工具的名称是否重复
		ToolProduct p = toolProductMapper.queryToolProductByName(product.getProductName());
		HsAssert.isTrue(null == p, BSRespCode.BS_TOOL_PRODUCT_NAME_EXIST, "新增工具产品名称已经存在," + product);

		// 生成上架申报记录申请编号
		String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		ToolProductUpDown toolProductUpDown = new ToolProductUpDown();
		toolProductUpDown.setApplyId(applyId);
		toolProductUpDown.setApplyType(ToolStatus.UP.getCode());
		toolProductUpDown.setApplyPrice(product.getPrice());
		toolProductUpDown.setReqOptId(product.getOptId());
		toolProductUpDown.setReqOptName(product.getOptName());
		toolProductUpDown.setStatus(ToolApprStatus.APP_UP.getCode());
		try
		{
			product.setEnableStatus(ToolStatus.DOWN.getCode());
			product.setStatus(ToolApprStatus.APP_UP.getCode());
			product.setLastApplyId(applyId);
			// 生成工具产品编号
			productId = toolProductMapper.selectMaxProductId();
			if (StringUtils.isNotBlank(productId))
			{
				productId = StringUtil.frontCompWithZore(Integer.parseInt(productId) + 1, 10);
			} else
			{
				productId = StringUtil.frontCompWithZore(1, 10);
			}
			product.setProductId(productId);
			toolProductUpDown.setProductId(productId);
			// 生成一条下架申请记录
			toolProductUpDownMapper.insertToolProductUpDown(toolProductUpDown);
			// 插入工具产品数据
			toolProductMapper.insertToolProduct(product);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addToolProduct",
					BSRespCode.BS_ADD_TOOL_PRODUCT_FAIL.getCode() + ":新增工具产品失败,param：" + product, ex);
			throw new HsException(BSRespCode.BS_ADD_TOOL_PRODUCT_FAIL, "新增工具产品失败,param：" + product);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			LocalInfo info = commonService.getAreaPlatInfo();
			if (null != info)
			{
				// 增加一条工具产品审批任务，属于平台的业务不用区分业务主体
				taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE224.getCode(),
						commonService.getAreaPlatCustId(), info.getPlatResNo(), info.getPlatNameCn()));
			} else
			{
				// 增加一条工具产品审批任务，属于平台的业务不用区分业务主体
				taskService.saveTask(
						new Task(applyId, TaskType.TASK_TYPE224.getCode(), commonService.getAreaPlatCustId()));
			}
		}
	}

	/**
	 * 修改工具产品
	 *
	 * @Description:
	 * @param bean
	 *            工具产品参数实体
	 * @throws HsException
	 */
	@Override
	public void updateToolProduct(ToolProduct bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "修改工具产品:addToolProduct", "params==>", bean.toString());
		int count = 0;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "修改工具产品参数为NULL," + bean);

		try
		{
			count = toolProductMapper.updateToolProductById(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addToolProduct",
					BSRespCode.BS_MODIFY_TOOL_PRODUCT_FAIL.getCode() + ":修改工具产品失败,param：" + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_TOOL_PRODUCT_FAIL, "修改工具产品失败," + bean);
	}

	/**
	 * 申请调价
	 * 
	 * @param productId
	 *            产品编号
	 * @param applyPrice
	 *            申请调整价格
	 * @param operator
	 *            操作员信息
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void applyChangePrice(String productId, String applyPrice, Operator operator) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "申请产品调价:applyChangePrice", "params==>",
				"productId：" + productId + ",applyPrice:" + applyPrice + ",operator:" + operator);
		// 验证参数非null
		HsAssert.notNull(productId, RespCode.PARAM_ERROR, "产品编号不能为空");
		HsAssert.notNull(applyPrice, RespCode.PARAM_ERROR, "申请调整价格不能为空");
		HsAssert.notNull(operator, RespCode.PARAM_ERROR, "操作员不能为空");
		ToolProduct product = toolProductMapper.selectToolProductById(productId);
		// 工具产品不存在或者未上架，申请下架要报错
		if (product == null || product.getEnableStatus() != ToolStatus.DOWN.getCode())
		{
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "申请调价失败,该产品不存在或者处于上架状态,productId：" + productId);
		}
		if (ToolApprStatus.APP_UP.getCode() == product.getStatus())
		{
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL,
					"申请调价失败,该产品已经处于申请中,不能重复申请,productId：" + productId);
		}
		// 生成上架申报记录申请编号
		String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		ToolProductUpDown toolProductUpDown = new ToolProductUpDown();
		toolProductUpDown.setApplyId(applyId);
		toolProductUpDown.setProductId(productId);
		toolProductUpDown.setApplyType(ToolStatus.UP.getCode());
		toolProductUpDown.setOldPrice(product.getPrice());
		toolProductUpDown.setApplyPrice(applyPrice);
		toolProductUpDown.setReqOptId(operator.getOptId());
		toolProductUpDown.setReqOptName(operator.getOptName());
		toolProductUpDown.setStatus(ToolApprStatus.APP_UP.getCode());
		try
		{
			// 生成一条上架申请记录
			toolProductUpDownMapper.insertToolProductUpDown(toolProductUpDown);
			// 更新产品状态
			toolProductMapper.updateToolProduct(productId, "" + ToolApprStatus.APP_UP.getCode(), applyId, null,
					"" + ToolStatus.DOWN.getCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:applyChangePrice",
					BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL.getCode() + ":申请产品调价失败,productId：" + productId + ",applyPrice:"
							+ applyPrice + ",operator:" + operator,
					ex);
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "申请产品调价失败,保存数据出现异常。param：" + product + "\n" + ex);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			LocalInfo info = commonService.getAreaPlatInfo();
			if (null != info)
			{
				// 增加一条工具产品审批任务，属于平台的业务不用区分业务主体
				taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE224.getCode(),
						commonService.getAreaPlatCustId(), info.getPlatResNo(), info.getPlatNameCn()));
			} else
			{
				// 增加一条工具产品审批任务，属于平台的业务不用区分业务主体
				taskService.saveTask(
						new Task(applyId, TaskType.TASK_TYPE224.getCode(), commonService.getAreaPlatCustId()));
			}
		}
	}

	/**
	 * 申请工具下架
	 * 
	 * @param productId
	 *            工具产品编号
	 * @param downReason
	 *            下架原因
	 * @param operator
	 *            操作员信息
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void applyToolProductToDown(String productId, String downReason, Operator operator) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "工具下架申请:applyToolProductToDown", "params==>",
				"productId:" + productId + ",operator:" + operator);
		// 验证参数非null
		HsAssert.notNull(productId, RespCode.PARAM_ERROR, "产品编号不能为空");
		HsAssert.notNull(operator, RespCode.PARAM_ERROR, "操作员不能为空");
		ToolProduct product = toolProductMapper.selectToolProductById(productId);
		// 工具产品不存在或者未上架，申请下架要报错
		if (product == null || product.getEnableStatus() != ToolStatus.UP.getCode())
		{
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "申请下架失败,该产品不存在或者未上架,productId：" + productId);
		}
		if (ToolApprStatus.APP_DOWN.getCode() == product.getStatus())
		{
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "申请下架失败,已经申请了下架,不能重复申请,productId：" + productId);
		}
		ToolProductUpDown toolProductUpDown = new ToolProductUpDown();
		String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		toolProductUpDown.setApplyId(applyId);
		toolProductUpDown.setProductId(productId);
		toolProductUpDown.setApplyType(ToolStatus.DOWN.getCode());
		toolProductUpDown.setDownReason(downReason);
		toolProductUpDown.setReqOptId(operator.getOptId());
		toolProductUpDown.setReqOptName(operator.getOptName());
		toolProductUpDown.setStatus(ToolApprStatus.APP_DOWN.getCode());
		try
		{
			// 生成一条下架申请记录
			toolProductUpDownMapper.insertToolProductUpDown(toolProductUpDown);
			// 更新产品状态及关联最小申请记录
			toolProductMapper.updateToolProduct(productId, "" + ToolApprStatus.APP_DOWN.getCode(), applyId, null, null);
		} catch (Exception e)
		{
			SystemLog.error(bsConfig.getSysName(), "function:applyToolProductToDown",
					"工具下架申请:productId:" + productId + ",operator:" + operator, e);
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL,
					"申请下架失败,保存数据失败,productId：" + productId + "\n" + e);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			LocalInfo info = commonService.getAreaPlatInfo();
			if (null != info)
			{
				// 保存工具上下架审批工单，属于平台的业务不用区分业务主体
				taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE222.getCode(),
						commonService.getAreaPlatCustId(), info.getPlatResNo(), info.getPlatNameCn()));
			} else
			{
				// 保存工具上下架审批工单，属于平台的业务不用区分业务主体
				taskService.saveTask(
						new Task(applyId, TaskType.TASK_TYPE222.getCode(), commonService.getAreaPlatCustId()));
			}
		}
	}

	/**
	 * 工具上架审批
	 * 
	 * @param apprInfo
	 *            审批信息
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void apprToolProductForUp(ApprInfo apprInfo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "工具上架审批:apprToolProductForUp", "params==>", apprInfo.toString());
		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(apprInfo);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + apprInfo);

		ToolProductUpDown toolProductUpDown = toolProductUpDownMapper.getToolProductUpDownById(apprInfo.getApplyId());
		if (toolProductUpDown == null || ToolApprStatus.APP_UP.getCode() != toolProductUpDown.getStatus())
		{
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "工具上架审批失败,申请记录不存在或者审批状态不正确,param:" + apprInfo);
		}
		toolProductUpDown.setApprRemark(apprInfo.getApprRemark());
		toolProductUpDown.setStatus(apprInfo.isPass() ? ToolApprStatus.UP.getCode() : ToolApprStatus.NOT_UP.getCode());
		toolProductUpDown.setApprOptId(apprInfo.getOptId());
		toolProductUpDown.setApprOptName(apprInfo.getOptName());
		toolProductUpDown.setApprRemark(apprInfo.getApprRemark());
		try
		{
			toolProductUpDownMapper.updateToolProductUpDownForAppr(toolProductUpDown);
			if (apprInfo.isPass())
			{
				String newPrice = null;
				if (StringUtils.isNotBlank(toolProductUpDown.getApplyPrice()))
				{
					newPrice = toolProductUpDown.getApplyPrice();
				}
				toolProductMapper.updateToolProduct(toolProductUpDown.getProductId(), "" + ToolApprStatus.UP.getCode(),
						null, newPrice, "" + ToolStatus.UP.getCode());
			} else
			{
				toolProductMapper.updateToolProduct(toolProductUpDown.getProductId(),
						"" + ToolApprStatus.NOT_UP.getCode(), null, null, null);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:apprToolProductForUp",
					BSRespCode.BS_TOOL_UP_OR_DOWN_APPR_FAIL + ":工具上架审批失败,保存数据失败,param:" + apprInfo, ex);
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "工具上架审批失败,保存数据失败,param:" + apprInfo + "\n" + ex);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 获取工具产品审批任务id
			String taskId = taskService.getSrcTask(apprInfo.getApplyId(), apprInfo.getOptId());
			HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询到待办任务,param:" + apprInfo);
			// 修改工具产品审批任务完成
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 工具下架审批
	 * 
	 * @param apprInfo
	 *            审批信息
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void apprToolProductForDown(ApprInfo apprInfo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "工具下架审批:apprToolProductForDown", "params", apprInfo.toString());
		String valid = ValidateParamUtil.validateParam(apprInfo);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + apprInfo);

		ToolProductUpDown toolProductUpDown = toolProductUpDownMapper.getToolProductUpDownById(apprInfo.getApplyId());
		if (toolProductUpDown == null || ToolApprStatus.APP_DOWN.getCode() != toolProductUpDown.getStatus())
		{
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "工具下架审批失败,申请记录不存在或者审批状态不正确,param:" + apprInfo);
		}
		toolProductUpDown.setApprRemark(apprInfo.getApprRemark());
		toolProductUpDown
				.setStatus(apprInfo.isPass() ? ToolApprStatus.DOWN.getCode() : ToolApprStatus.NOT_DOWN.getCode());
		toolProductUpDown.setApprOptId(apprInfo.getOptId());
		toolProductUpDown.setApprOptName(apprInfo.getOptName());
		toolProductUpDown.setApprRemark(apprInfo.getApprRemark());
		try
		{
			toolProductUpDownMapper.updateToolProductUpDownForAppr(toolProductUpDown);
			if (apprInfo.isPass())
			{
				toolProductMapper.updateToolProduct(toolProductUpDown.getProductId(),
						"" + ToolApprStatus.DOWN.getCode(), null, null, "" + ToolStatus.DOWN.getCode());
			} else
			{
				toolProductMapper.updateToolProduct(toolProductUpDown.getProductId(),
						"" + ToolApprStatus.NOT_DOWN.getCode(), null, null, null);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:apprToolProductForDown",
					BSRespCode.BS_TOOL_UP_OR_DOWN_APPR_FAIL + ":工具下架审批失败,保存数据失败,param:" + apprInfo, ex);
			throw new HsException(BSRespCode.BS_TOOL_UP_OR_DOWN_FAIL, "工具下架审批失败,保存数据失败,param:" + apprInfo + "\n" + ex);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 获取工具产品审批任务id
			String taskId = taskService.getSrcTask(apprInfo.getApplyId(), apprInfo.getOptId());
			HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询到待办任务,params==>" + apprInfo);
			// 修改工具产品审批任务完成
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 查询所有工具或根据类别查询工具
	 * 
	 * @Description:
	 * @param categoryCode
	 *            工具类别代码
	 * @return
	 */
	@Override
	public List<ToolProduct> queryToolProduct(String categoryCode)
	{
		return toolProductMapper.selectToolProduct(categoryCode);
	}

	/**
	 * 查询所有工具或根据类别查询工具
	 *
	 * @Description:
	 * @param categoryCode
	 *            工具类别代码
	 * @return
	 */
	@Override
	public List<ToolProduct> selectToolProductNotByStatus(String categoryCode)
	{
		return toolProductMapper.selectToolProductNotByStatus(categoryCode);
	}

	/**
	 * 新增互生卡样
	 * 
	 * @Description:
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addCardStyle(CardStyle bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addCardStyle", "params==>" + bean, "新增互生卡样");
		// 卡样id
		String cardStyleId = "";
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增互生卡样参数为NULL," + bean);
		try
		{
			cardStyleId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			// 设置默认参数
			bean.setCardStyleId(cardStyleId);
			bean.setIsSpecial(false);// 是否个性卡样
			bean.setIsLock(false);// 是锁定
			bean.setIsDefault(bean.getIsDefault() == null ? Boolean.FALSE : bean.getIsDefault());// 是否默认
			bean.setEnableStatus(EnableStatus.STOP.getCode());// 启用状态
			bean.setStatus(CardStyleApprStatus.APP_ENABLE.getCode());// 审批状态
			// 插入卡样数据
			cardStyleMapper.insertCardStyle(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addCardStyle",
					BSRespCode.BS_ADD_CARD_STYLE_FAIL.getCode() + ":新增互生卡样失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_CARD_STYLE_FAIL, "新增互生卡样失败," + bean);
		}
		// 插入卡样审批任务，属于平台的业务不用区分业务主体 by likui 去掉卡样的审批任务
		// taskService.saveTask(new Task(cardStyleId,
		// TaskType.TASK_TYPE227.getCode(), commonService.getAreaPlatCustId()));
	}

	/**
	 * 根据id查询互生卡样
	 * 
	 * @Description:
	 * @param cardStyleId
	 *            卡样id
	 * @return
	 */
	@Override
	public CardStyle queryCardStyleById(String cardStyleId)
	{
		if (StringUtils.isBlank(cardStyleId))
		{
			return null;
		}
		return cardStyleMapper.selectCardStyleById(cardStyleId);
	}

	/**
	 * 互生卡样的启用或停用
	 * 
	 * @Description:
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void cardStyleEnableOrStop(CardStyle bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:cardStyleEnableOrStop", "params==>" + bean, "互生卡样的启用或停用");
		int count = 0;
		// 验证参数非null
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "互生卡样的启用或停用参数为NULL," + bean);
		HsAssert.notNull(bean.getEnableStatus(), RespCode.PARAM_ERROR, "互生卡样启用状态为null," + bean);

		// 默认的标准卡样不可以停用或启用
		HsAssert.isTrue(!bean.getIsDefault(), RespCode.PARAM_ERROR, "默认的标准卡样不可以停用或启用," + bean);
		// 个性卡样不可以停用或启用
		HsAssert.isTrue(!bean.getIsSpecial(), RespCode.PARAM_ERROR, "个性卡样不可以停用或启用," + bean);
		try
		{
			// 修改卡样数据
			count = cardStyleMapper.cardStyleEnableOrStop(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:cardStyleEnableOrStop",
					BSRespCode.BS_CARD_STYLE_ENABLE_OR_STOP_FAIL.getCode() + ":互生卡样的启用或停用失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_CARD_STYLE_ENABLE_OR_STOP_FAIL, "互生卡样的启用或停用失败," + bean);

		// 插入卡样审批任务，属于平台的业务不用区分业务主体 by likui 去掉卡样的审批任务
		// taskService.saveTask(
		// new Task(bean.getCardStyleId(), TaskType.TASK_TYPE227.getCode(),
		// commonService.getAreaPlatCustId()));
	}

	/**
	 * 修改标准卡样为默认卡样
	 * 
	 * @Description:
	 * @param cardStyleId
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void updateCardStyleToDefault(String cardStyleId) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:updateCardStyleToDefault", "params==>" + cardStyleId,
				"修改标准卡样为默认卡样");
		int count = 0;
		// 查询卡样数据
		CardStyle bean = queryCardStyleById(cardStyleId);
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "未查询到卡样id的卡样数据," + cardStyleId);
		// 是否个性卡样
		HsAssert.isTrue(!bean.getIsSpecial(), RespCode.PARAM_ERROR, "个性卡样不能为默认卡样," + cardStyleId);
		// 是否已经启用
		HsAssert.isTrue(bean.getEnableStatus() == EnableStatus.ENABLED.getCode(), RespCode.PARAM_ERROR,
				"卡样未启用," + cardStyleId);
		try
		{
			// 修改原默认卡样为非默认
			count = cardStyleMapper.updateDefaultToNotDefault();
			if (count > 0)
			{
				// 修改非默认卡样为默认
				count = cardStyleMapper.updateCardStyleDefault(cardStyleId);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:updateCardStyleToDefault",
					BSRespCode.BS_CARD_STYLE_SET_DEFAULT_FAIL.getCode() + ":标准卡样设置为默认卡样失败," + cardStyleId, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_CARD_STYLE_SET_DEFAULT_FAIL, "标准卡样设置为默认卡样失败," + cardStyleId);
	}

	/**
	 * 互生卡样审批
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void cardStyleAppr(CardStyle bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:cardStyleAppr", "params==>" + bean, "互生卡样审批");
		int count = 0;
		// 任务id
		String taskId = "";
		// 验证参数非null
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "互生卡样审批参数为NULL," + bean);
		HsAssert.notNull(bean.getStatus(), RespCode.PARAM_ERROR, "互生卡样审批状态为null," + bean);

		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 获取卡样审批任务id
			taskId = taskService.getSrcTask(bean.getCardStyleId(), bean.getExeCustId());
			HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询待办任务," + bean);
		}

		// 验证卡样状态
		HsAssert.isTrue(
				bean.getStatus().intValue() != CardStyleApprStatus.APP_ENABLE.getCode()
						&& bean.getStatus().intValue() != CardStyleApprStatus.APP_STOP.getCode(),
				RespCode.PARAM_ERROR, "互生卡样审批状态错误," + bean);
		try
		{
			// 审批启用
			if (bean.getStatus() == CardStyleApprStatus.ENABLE.getCode())
			{
				bean.setEnableStatus(EnableStatus.ENABLED.getCode());
				// 非个性的默认卡样
				if (bean.getIsDefault() && !bean.getIsSpecial())
				{
					// 修改原默认卡样为非默认
					cardStyleMapper.updateDefaultToNotDefault();
				}
			}
			// 审批停用
			if (bean.getStatus() == CardStyleApprStatus.STOP.getCode())
			{
				bean.setEnableStatus(EnableStatus.STOP.getCode());
			}
			// 启用驳回或停用驳回
			if (bean.getStatus() == CardStyleApprStatus.NOT_ENABLE.getCode()
					|| bean.getStatus() == CardStyleApprStatus.NOT_STOP.getCode())
			{
				bean.setEnableStatus(null);
			}
			// 修改卡样
			count = cardStyleMapper.cardStyleAppr(bean);

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:cardStyleAppr",
					BSRespCode.BS_CARD_STYLE_ENABLE_OR_STOP_APPR_FAIL.getCode() + ":互生卡样启用或停用审批失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_CARD_STYLE_ENABLE_OR_STOP_APPR_FAIL, "互生卡样启用或停用审批失败," + bean);

		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 修改卡样审批任务完成
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 分页查询互生卡样
	 * 
	 * @Description:
	 * @param cardStyleName
	 *            卡样名称
	 * @param exeCustId
	 *            受理人客户号
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<CardStyle> queryCardStylePage(String cardStyleName, String exeCustId, Page page)
	{
		if (null == page)
		{
			return new PageData<CardStyle>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 如果关闭工单，查询时不关联经办人进行查询，需要设置经办人为空 by likui 去掉卡样的审批
		// if (!bsConfig.getWorkTaskIsOpen().booleanValue())
		// {
		// exeCustId = null;
		// }
		// 查询卡样列表
		List<CardStyle> styles = cardStyleMapper.selectCardStyleListPage(cardStyleName, exeCustId);
		if (StringUtils.isNotBlank(styles))
		{
			return new PageData<CardStyle>(page.getCount(), styles);
		}
		return new PageData<CardStyle>(0, null);
	}

	/**
	 * 工具价格(上架)审批分页查询
	 * 
	 * @param productName
	 *            产品名称
	 * @param categoryName
	 *            产品类别名称
	 * @param exeCustId
	 *            审批经办人
	 * @param page
	 *            分页条件
	 * @return
	 * @see com.gy.hsxt.bs.api.tool.IBSToolProductService#queryToolUpForApprListPage(java.lang.String,
	 *      java.lang.String, java.lang.String, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ToolProductUpDown> queryToolUpForApprListPage(String productName, String categoryName,
			String exeCustId, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolProductUpDown>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);

		// 如果关闭工单，查询时不关联经办人进行查询，需要设置经办人为空
		if (!bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			exeCustId = null;
		}

		List<ToolProductUpDown> resultList = null;
		try
		{
			resultList = toolProductUpDownMapper.findToolProductUpDownListPage(ToolStatus.UP.getCode(), productName,
					categoryName, exeCustId);
		} catch (Exception e)
		{
			SystemLog.error(this.getClass().getName(), "function:queryToolUpForApprListPage",
					BSRespCode.BS_QUERY_ERROR.getCode() + ":工具价格(上架)审批查询失败,productName=" + productName
							+ ",categoryName=" + categoryName + ",exeCustId=" + exeCustId + ",page=" + page,
					e);
			return new PageData<ToolProductUpDown>(0, null);
		}
		return new PageData<ToolProductUpDown>(page.getCount(), resultList);
	}

	/**
	 * 工具下架审批分页查询
	 * 
	 * @param productName
	 *            产品名称
	 * @param categoryName
	 *            产品类别名称
	 * @param exeCustId
	 *            审批经办人
	 * @param page
	 *            分页条件
	 * @return
	 * @see com.gy.hsxt.bs.api.tool.IBSToolProductService#queryToolDownForApprListPage(java.lang.String,
	 *      java.lang.String, java.lang.String, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ToolProductUpDown> queryToolDownForApprListPage(String productName, String categoryName,
			String exeCustId, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolProductUpDown>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);

		// 如果关闭工单，查询时不关联经办人进行查询，需要设置经办人为空
		if (!bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			exeCustId = null;
		}

		List<ToolProductUpDown> resultList = null;
		try
		{
			resultList = toolProductUpDownMapper.findToolProductUpDownListPage(ToolStatus.DOWN.getCode(), productName,
					categoryName, exeCustId);
		} catch (Exception e)
		{
			SystemLog.error(this.getClass().getName(), "function:queryToolDownForApprListPage",
					BSRespCode.BS_QUERY_ERROR.getCode() + ":工具下架审批查询失败,productName=" + productName + ",categoryName="
							+ categoryName + ",exeCustId=" + exeCustId + ",page=" + page,
					e);
			return new PageData<ToolProductUpDown>(0, null);
		}
		return new PageData<ToolProductUpDown>(page.getCount(), resultList);
	}

	/**
	 * 根据申报请编号查询工具上下架申请记录
	 * 
	 * @param applyId
	 *            申请编号
	 * @return
	 * @see com.gy.hsxt.bs.api.tool.IBSToolProductService#queryToolProductUpDownById(java.lang.String)
	 */
	@Override
	public ToolProductUpDown queryToolProductUpDownById(String applyId)
	{
		if (StringUtils.isBlank(applyId))
		{
			return null;
		}
		return toolProductUpDownMapper.getToolProductUpDownById(applyId);
	}

	/**
	 * 根据产品编号查询工具上下架申请记录
	 * 
	 * @param productId
	 *            产品编号
	 * @return
	 * @see com.gy.hsxt.bs.api.tool.IBSToolProductService#queryToolProductUpDownByProductId(java.lang.String)
	 */
	@Override
	public ToolProductUpDown queryToolProductUpDownByProductId(String productId)
	{
		if (StringUtils.isBlank(productId))
		{
			return null;
		}
		return toolProductUpDownMapper.getToolProductUpDownByProductId(productId);
	}

}
