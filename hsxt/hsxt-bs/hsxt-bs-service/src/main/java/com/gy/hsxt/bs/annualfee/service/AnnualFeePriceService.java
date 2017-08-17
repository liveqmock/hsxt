/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.annualfee.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeePriceService;
import com.gy.hsxt.bs.annualfee.mapper.AnnualFeePriceMapper;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeePrice;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeePriceQuery;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.annualfee.PriceStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 年费方案业务层实现
 * 
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeePriceService
 * @Description : 年费方案业务层实现
 * @Author : chenm
 * @Date : 2015/12/10 15:30
 * @Version V3.0.0.0
 */
@Service
public class AnnualFeePriceService implements IAnnualFeePriceService {

    /**
     * 业务系统基础数据
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 年费方案Mapper接口
     */
    @Resource
    private AnnualFeePriceMapper annualFeePriceMapper;

    /**
     * 工单业务接口
     */
    @Resource
    private ITaskService taskService;

    /**
     * 平台数据接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 保存实体
     * 
     * @param annualFeePrice
     *            实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(AnnualFeePrice annualFeePrice) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "创建年费价格方案的参数[annualFeePrice]：" + annualFeePrice);

        // 实体参数为null时抛出异常
        HsAssert.notNull(annualFeePrice, RespCode.PARAM_ERROR, "年费价格方案[annualFeePrice]参数为空");

        HsAssert.isTrue(CustType.checkType(annualFeePrice.getCustType()), RespCode.PARAM_ERROR, "企业类型[custType]参数错误");

        HsAssert.hasText(annualFeePrice.getPrice(), RespCode.PARAM_ERROR, "方案价格[price]为空");

        HsAssert.hasText(annualFeePrice.getProgramName(), RespCode.PARAM_ERROR, "方案名称[programName]为空");

        HsAssert.hasText(annualFeePrice.getReqOperator(), RespCode.PARAM_ERROR, "申请者[reqOperator]为空");

        try
        {
            // 检查是否有待审核的年费方案
            String exist = annualFeePriceMapper.selectPriceIdForPending(annualFeePrice.getCustType());

            // 待审核的年费方案已存在，请不要重复提交
            HsAssert.isTrue(StringUtils.isBlank(exist), BSRespCode.BS_ANNUAL_FEE_PRICE_PENDING_EXIST,
                    "创建年费价格方案--待审核的年费方案已存在，请不要重复提交");

            // 生成价格方案主键ID
            annualFeePrice.setPriceId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            // 执行创建
            int count = annualFeePriceMapper.insertAnnualFeePrice(annualFeePrice);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createAnnualFeePrice", "params==>" + annualFeePrice,
                    "创建年费价格方案成功");

            // 保存申请后，派送工单
            if (count == 1)
            {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                // 生成年费价格方案审批任务，平台业务不需要指定业务主体
                taskService
                        .saveTask(new Task(annualFeePrice.getPriceId(), TaskType.TASK_TYPE205.getCode(), platCustId,"",""));
            }

            return annualFeePrice.getPriceId();
        }
        catch (HsException hse)
        {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:createAnnualFeePrice", hse.getMessage(), hse);
            // 抛出异常
            throw hse;
        }
        catch (Exception e)
        {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:createAnnualFeePrice", "创建年费价格方案失败，参数[annualFeePrice]："
                    + annualFeePrice, e);

            throw new HsException(BSRespCode.BS_ANNUAL_FEE_PRICE_DB_ERROR, "创建年费价格方案失败，原因:" + e.getMessage());
        }
    }

    /**
     * 更新实体
     * 
     * @param annualFeePrice
     *            实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(AnnualFeePrice annualFeePrice) throws HsException {
        return 0;
    }

    /**
     * 根据ID查询实体
     * 
     * @param id
     *            key
     * @return t
     * @throws HsException
     */
    @Override
    public AnnualFeePrice queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "获取年费方案的参数[priceId]：" + id);
        // 方案编号为空时
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "年费方案ID[priceId]为空");
        try
        {
            // 执行查询
            return annualFeePriceMapper.selectById(id);
        }
        catch (Exception e)
        {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "获取年费方案异常,ID:" + id, e);
            // 抛出异常
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_PRICE_DB_ERROR, "获取年费方案异常,原因：" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     * 
     * @param query
     *            查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeePrice> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询年费价格方案列表的参数[query]：" + query);

        AnnualFeePriceQuery annualFeePriceQuery = null;
        if (query != null)
        {
            HsAssert.isInstanceOf(AnnualFeePriceQuery.class, query, RespCode.PARAM_ERROR,
                    "所传查询参数不是[AnnualFeePriceQuery]类型");
            annualFeePriceQuery = ((AnnualFeePriceQuery) query);
        }
        try
        {
            // 执行查询
            return annualFeePriceMapper.selectAnnualFeePriceList(annualFeePriceQuery);
        }
        catch (Exception e)
        {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "查询年费价格方案列表失败,参数[query]:" + query, e);

            throw new HsException(BSRespCode.BS_ANNUAL_FEE_PRICE_DB_ERROR, "查询年费价格方案列表失败,原因:" + e.getMessage());
        }

    }

    /**
     * 审核年费方案
     * 
     * @param annualFeePrice
     *            年费方案
     * @return boolean
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean apprAnnualFeePrice(AnnualFeePrice annualFeePrice) throws HsException {
        try
        {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:apprAnnualFeePrice", "审批年费价格方案的参数[annualFeePrice]："
                    + annualFeePrice);

            // 实体参数为null时抛出异常
            HsAssert.notNull(annualFeePrice, RespCode.PARAM_ERROR, "年费价格方案[annualFeePrice]参数为null");

            // 方案编号参数为空时抛出异常
            HsAssert.hasText(annualFeePrice.getPriceId(), RespCode.PARAM_ERROR, "年费价格方案编号[priceId]为空");

            // 方案状态审核类型参数错误 方案状态 1：已启用 2：审批驳回 3：已停用
            HsAssert.isTrue(PriceStatus.check(annualFeePrice.getStatus()), BSRespCode.BS_ANNUAL_FEE_PRICE_STATUS_ERROR,
                    "审批状态[status]参数类型错误");

            // 如果审批结果为启用，则先禁用之前同类型有效的方案
            if (annualFeePrice.getStatus() == PriceStatus.ENABLE.ordinal())
            {
                // 禁用之前启用的方案
                int num = annualFeePriceMapper.disableAnnualFeePrice(annualFeePrice.getCustType());
                // 创建操作日志
                BizLog.biz(bsConfig.getSysName(), "function:apprAnnualFeePrice", "禁用[" + annualFeePrice.getCustType()
                        + "]类型企业的年费方案所影响的记录数：" + num, "审批年费价格方案成功");
            }
            // 执行审批 更新审批结果
            int count = annualFeePriceMapper.apprAnnualFeePrice(annualFeePrice);

            BizLog.biz(bsConfig.getSysName(), "function:createAnnualFeePrice", "params==>" + annualFeePrice, "审核年费方案成功");
            if (count == 1)
            {
                // 审批操作完成时，更新工单状态
                String taskId = taskService.getSrcTask(annualFeePrice.getPriceId(), annualFeePrice.getApprOperator());
                HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
                taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            }
            return count == 1;
        }
        catch (HsException hse)
        {
            SystemLog.error(bsConfig.getSysName(), "function:apprAnnualFeePrice", hse.getMessage(), hse);
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(bsConfig.getSysName(), "function:apprAnnualFeePrice", "审批年费价格方案失败，参数[annualFeePrice]:"
                    + annualFeePrice, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_PRICE_DB_ERROR, "审批年费价格方案失败，原因：" + e.getMessage());
        }
    }

    /**
     * 查询某类型企业的可用年费方案之价
     * 
     * @param custType
     *            企业类型
     * @return 方案价格
     */
    @Override
    public String queryPriceForEnabled(Integer custType) {
        try
        {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:queryPriceForEnabled", "查询某类型企业的可用年费方案之价的参数[query]："
                    + custType);

            HsAssert.isTrue(CustType.checkType(custType), RespCode.PARAM_ERROR, "客户类型[custType]错误");

            // 执行查询
            return annualFeePriceMapper.selectPriceForEnabled(custType);
        }
        catch (HsException hse)
        {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryPriceForEnabled", hse.getMessage(), hse);
            throw hse;
        }
        catch (Exception e)
        {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryPriceForEnabled", "查询某类型企业的可用年费方案之价失败,参数[custType]:"
                    + custType, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_PRICE_DB_ERROR, "查询某类型企业的可用年费方案之价失败，原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询列表
     * 
     * @param page
     *            分页对象
     * @param query
     *            查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeePrice> queryListForPage(Page page, Query query) throws HsException {
        return null;
    }
}
