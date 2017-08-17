/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.DividendRate;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.TransCodeUtil;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.enumtype.InvestSatus;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.interfaces.IAccountRuleService;
import com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.bs.order.mapper.DividendDetailMapper;
import com.gy.hsxt.bs.order.mapper.DividendRateMapper;
import com.gy.hsxt.bs.order.mapper.PointDividendMapper;
import com.gy.hsxt.bs.order.mapper.PointInvestMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;

/**
 * 投资分红Dubbo Service实现类
 * 
 * @Package: com.gy.hsxt.bs.order.service
 * @ClassName: InvestProfitService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-10-16 上午9:07:00
 * @version V3.0.0
 */
@Service(value = "investProfitService")
public class InvestProfitService implements IBSInvestProfitService {

    // 注入记帐分解mapper
    @Autowired
    AccountDetailMapper accountDetailMapper;

    // 记帐分解service
    @Autowired
    IAccountDetailService accountDetailService;

    /**
     * 参数配置系统：帐户规则服务
     */
    @Resource
    IAccountRuleService accountRuleService;

    // 业务服务私有配置参数
    @Autowired
    BsConfig bsConfig;

    // 注入年度分红比率mapper
    @Autowired
    DividendRateMapper dividendRateMapper;

    // 注入积分投资分红计算明细mapper
    @Autowired
    DividendDetailMapper dividendDetailMapper;

    // 注入积分投资mapper
    @Autowired
    PointInvestMapper pointInvestMapper;

    // 注入积分投资分红mapper
    @Autowired
    PointDividendMapper pointDividendMapper;

    // Dubbo 调用接口
    @Autowired
    IInvokeRemoteService invokeRemoteService;

    /**
     * 用户中心：查询重要信息变更状态
     */
    @Resource
    IUCBsCardHolderStatusInfoService ucCardHolderStatusInfoService;

    /**
     * 用户中心：查询企业重要信息
     */
    @Resource
    IUCBsEntService ucEntService;

    /**
     * 保存年度分红比率记录接口实现
     * 
     * @param dividendRate
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#saveDividendRate(com.gy.hsxt.bs.bean.order.DividendRate)
     */
    @Override
    @Transactional
    public void saveDividendRate(DividendRate dividendRate) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存年度分红比率记录,params[" + dividendRate + "]");
        // 实体参数为空
        HsAssert.notNull(dividendRate, BSRespCode.BS_PARAMS_NULL, "保存年度分红比率：实体参数为空");
        HsAssert.hasText(dividendRate.getDividendYear(), BSRespCode.BS_PARAMS_NULL, "保存年度分红比率：分红年份参数为空");
        // 返回成功记录数
        int resultNum = 0;
        try
        {
            // 校验是否已存在年度分红比率
            resultNum = dividendRateMapper.findExistsYearRate(dividendRate.getDividendYear());
            HsAssert.isTrue(resultNum <= 0, BSRespCode.BS_DVIDEND_YEAR_RATE_IS_EXIST, "保存年度分红比率："
                    + dividendRate.getDividendYear() + "年度分红比率已存在");

            // 获取一年有多少天
            Calendar caldendar = Calendar.getInstance();
            caldendar.set(Calendar.YEAR, Integer.valueOf(dividendRate.getDividendYear()));
            int dayOfYear = caldendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            // 设置分红天数
            dividendRate.setDividendPeriod(String.valueOf(dayOfYear));
            // 设置分红比率
            dividendRate.setDividendRate(String.valueOf(Double.valueOf(dividendRate.getDividendRate()) / 100));
            // 执行插入年度分红比率
            dividendRateMapper.insertDividendRate(dividendRate);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_SAVE_DIVIDEND_RATE_ERROR.getCode() + "保存年度分红比率记录异常,params[" + dividendRate + "]", e);
            throw new HsException(BSRespCode.BS_SAVE_DIVIDEND_RATE_ERROR.getCode(), "保存年度分红比率记录异常,params["
                    + dividendRate + "]" + e);
        }
    }

    /**
     * 保存积分投资信息接口实现
     * 
     * @param pointInvest
     *            积分投资信息
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#savePointInvest(com.gy.hsxt.bs.bean.order.PointInvest)
     */
    @Override
    @Transactional
    public void savePointInvest(PointInvest pointInvest) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存积分投资信息,params[" + pointInvest + "]");
        // 记帐分解LIST
        List<AccountDetail> actDetails = new ArrayList<AccountDetail>();
        // 记帐分解对象
        AccountDetail detail = null;
        try
        {
            // 实体参数为空
            HsAssert.notNull(pointInvest, BSRespCode.BS_PARAMS_NULL, "保存积分投资信息：实体参数为空");
            // 调用AC获取积分帐户余额
            AccountBalance accountBalance = invokeRemoteService.getAccountBlance(pointInvest.getCustId(),
                    AccountType.ACC_TYPE_POINT10110.getCode());
            HsAssert.notNull(accountBalance, BSRespCode.BS_NOT_QUERY_DATA, "保存积分投资信息：调用AC未查询到帐户当前客户的积分帐户信息");
            // 校验帐户余额
            int blance = invokeRemoteService.isBlanceInsufficient(pointInvest.getCustId(),
                    AccountType.ACC_TYPE_POINT10110.getCode(), pointInvest.getInvestAmount());
            HsAssert.isTrue(blance < 0, BSRespCode.BS_ACC_BALANCE_NOT_ENOUGH, "保存积分投资信息：积分帐户余额不足");

            // 校验服务公司、托管企业是否重要信息变更期与欠年费不可积分投资
            checkCustInfo(pointInvest.getCustType(), pointInvest.getCustId());

            // 校验积分投资是否符合规则
            boolean isAllowInvest = accountRuleService.checkPvToInvestRule(pointInvest.getCustId(), pointInvest
                    .getCustType(), pointInvest.getInvestAmount());

            // 保底积分规则校验（申请积分数 <= 积分账户余数 - 保底积分数）//16.2.15统一由帐务校验

            if (isAllowInvest)
            {
                // 设置渠道默认值
                if (pointInvest.getChannel() == null)
                {
                    pointInvest.setChannel(com.gy.hsxt.common.constant.Channel.WEB.getCode());
                }
                // 生成投资流水号
                pointInvest.setInvestNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                // 生成投资时间
                pointInvest.setInvestDate(DateUtil.getCurrentDateTime());
                // 插入积分投资记录
                pointInvestMapper.insertPointInvest(pointInvest);

                /**
                 * 生成积分投资记帐分解{
                 */
                /******************************* 积分投资：积分帐户减少 ***************************************/
                // 实例化积分帐户记帐分解对象
                detail = new AccountDetail(
                        //
                        GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), // 记帐流水号
                        pointInvest.getInvestNo(),// 交易流水号
                        TransCodeUtil.getInvestTransCode(InvestSatus.POINT_INVEST.getCode(), pointInvest.getCustType()),// 设置交易类型
                        pointInvest.getCustId(), // 客户号
                        pointInvest.getHsResNo(), // 互生号
                        pointInvest.getCustName(), // 客户名称
                        pointInvest.getCustType(), // 客户类型
                        AccountType.ACC_TYPE_POINT10110.getCode(), // 帐户类型:10110积分帐户
                        "0", // 增金额
                        pointInvest.getInvestAmount(),// 减金额
                        "",// 货币币种
                        pointInvest.getInvestDate(),// 投资日期
                        "积分投资", // 备注
                        true// 记账状态
                );
                // 设置保底值
                detail.setGuaranteedValue(accountRuleService.getPvSaveAmount(pointInvest.getCustId(), pointInvest
                        .getCustType()));
                // 添加到集合
                actDetails.add(detail);

                /******************************* 积分投资：积分投资帐户增加 ***************************************/
                // 实例化投资帐户记帐分解对象
                detail = new AccountDetail(
                        //
                        GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), // 记帐流水号
                        pointInvest.getInvestNo(),// 交易流水号
                        TransCodeUtil.getInvestTransCode(InvestSatus.POINT_INVEST.getCode(), pointInvest.getCustType()),// 设置交易类型
                        pointInvest.getCustId(), // 客户号
                        pointInvest.getHsResNo(), // 互生号
                        pointInvest.getCustName(), // 客户名称
                        pointInvest.getCustType(), // 客户类型
                        AccountType.ACC_TYPE_POINT10410.getCode(), // 帐户类型:10410积分投资帐户
                        pointInvest.getInvestAmount(),// 减金额
                        "0", // 增金额
                        "",// 货币币种
                        pointInvest.getInvestDate(),// 投资日期
                        "积分投资", // 备注
                        true// 记账状态
                );
                // 添加到集合
                actDetails.add(detail);

                // 记帐分解并实时记帐
                accountDetailService.saveGenActDetail(actDetails, "积分投资", true);
            }
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_SAVE_POINT_INVEST_ERROR
                    .getCode()
                    + "保存积分投资异常,params[" + pointInvest + "]", e);
            throw new HsException(BSRespCode.BS_SAVE_POINT_INVEST_ERROR.getCode(), "保存积分投资异常,params[" + pointInvest
                    + "]" + e);
        }
    }

    /**
     * 获取年度分红比率列表
     * 
     * @param dividendYear
     *            分红年份
     * @param page
     *            分布信息
     * @return 年度分红比率列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getDividendRateList(java.lang.String,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<DividendRate> getDividendRateList(String dividendYear, Page page) throws HsException {
        PageData<DividendRate> dividendRateListPage = null;
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取年度分红比率列表,params[dividendYear" + dividendYear + "]");
        // 分红年份为空
        HsAssert.hasText(dividendYear, BSRespCode.BS_PARAMS_NULL, "获取年度分红比率列表：分红年份参数为空");
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "获取年度分红比率列表：分页参数为空");
        try
        {
            // 设置分页信息
            PageContext.setPage(page);
            // 执行查询
            List<DividendRate> dividendRateList = dividendRateMapper.findDividendRateListPage(dividendYear);
            // 校验查询结果不为空
            if (dividendRateList != null && dividendRateList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                dividendRateListPage = new PageData<DividendRate>(page.getCount(), dividendRateList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_DIVIDEND_RAE_LIST_ERROR.getCode() + "获取年度分红比率列表异常params[dividendYear:"
                            + dividendYear + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_DIVIDEND_RAE_LIST_ERROR.getCode(), "获取年度分红比率列表异常");
        }
        return dividendRateListPage;
    }

    /**
     * 获取历史分红列表
     * 
     * @param page
     *            分页信息
     * @return 历史分红列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getHisDividendList(com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<CustomPointDividend> getHisDividendList(Page page) throws HsException {
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "获取年度分红比率列表：分页参数为空");
        PageData<CustomPointDividend> hisDividendListPage = null;
        // 设置分页信息
        PageContext.setPage(page);

        List<CustomPointDividend> hisDividendList = dividendRateMapper.findHisDividendListPage();

        // 校验查询结果不为空
        if (hisDividendList != null && hisDividendList.size() > 0)
        {
            // 为公用分页查询设置查询结果集
            hisDividendListPage = new PageData<CustomPointDividend>(page.getCount(), hisDividendList);
        }
        return hisDividendListPage;
    }

    /**
     * 获取积分投资列表接口实现
     * 
     * @param custId
     *            客户号
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @param page
     *            分布信息
     * @return 积分投资列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getPointInvestList(java.lang.String,
     *      java.lang.String, java.lang.String, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<PointInvest> getPointInvestList(String custId, String startDate, String endDate, Page page)
            throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取积分投资列表,params[custId:" + custId + ",startDate:" + startDate + ",endDate:" + endDate + "," + page
                        + "]");
        PageData<PointInvest> pointInvestListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, " 获取积分投资列表：分页参数为空");
        try
        {
            // 设置分页信息
            PageContext.setPage(page);
            // 执行查询
            List<PointInvest> pointInvestList = pointInvestMapper.findPointInvestListPage(custId, DateUtil
                    .getMinDateOfDayStr(startDate), DateUtil.getMaxDateOfDayStr(endDate));

            if (pointInvestList != null && pointInvestList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                pointInvestListPage = new PageData<PointInvest>(page.getCount(), pointInvestList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_POINT_INVEST_LIST_ERROR.getCode() + "获取积分投资列表异常,params[custId:" + custId
                            + ",startDate:" + startDate + ",endDate:" + endDate + "," + page + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_POINT_INVEST_LIST_ERROR.getCode(), "获取积分投资列表异常");
        }
        return pointInvestListPage;
    }

    /**
     * 获取积分投资分红列表
     * 
     * @param custId
     *            非空客户号
     * @param startYear
     *            开始年份
     * @param endYear
     *            结束年份
     * @param page
     *            分页信息
     * @return 返回积分投资分红列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getPointDividendList(java.lang.String,
     *      java.lang.String, java.lang.String, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<PointDividend> getPointDividendList(String custId, String startYear, String endYear, Page page)
            throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取积分投资分红列表,params[custId:" + custId + ",startYear:" + startYear + ",endYear:" + endYear + "," + page
                        + "]");
        PageData<PointDividend> pointDividendListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "获取积分投资分红列表：分页参数为空");
        try
        {
            // 设置分页信息
            PageContext.setPage(page);
            // 执行查询
            List<PointDividend> pointDividendList = pointDividendMapper.findPointDividendListPage(custId, startYear,
                    endYear);
            if (pointDividendList != null && pointDividendList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                pointDividendListPage = new PageData<PointDividend>(page.getCount(), pointDividendList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_POINT_DIVIDEND_LIST_ERROR.getCode() + "查询积分投资分红列表异常,params[custId:" + custId
                            + ",startYear:" + startYear + ",endYear:" + endYear + "," + page + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_POINT_DIVIDEND_LIST_ERROR.getCode(),
                    "查询积分投资分红列表异常,params[custId:" + custId + ",startYear:" + startYear + ",endYear:" + endYear + ","
                            + page + "]" + e);
        }
        return pointDividendListPage;
    }

    /**
     * 地区平台获取积分投资分红列表
     * 
     * @param custType
     *            客户类型
     * @param hsResNo
     *            互生号
     * @param custName
     *            客户名称
     * @param startYear
     *            开始日期
     * @param endYear
     *            结束日期
     * @param page
     *            分布信息
     * @return 分红列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getPlatformDividendList(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<PointDividend> getPlatformDividendList(int custType, String hsResNo, String custName,
            String startYear, String endYear, Page page) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取积分投资分红列表,params[hsResNo:" + hsResNo + ",startYear:" + startYear + ",endYear:" + endYear + "," + page
                        + "]");
        PageData<PointDividend> pointDividendListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "获取积分投资分红列表：分页参数为空");
        try
        {
            // 设置分页信息
            PageContext.setPage(page);
            // 执行查询
            List<PointDividend> pointDividendList = pointDividendMapper.findPlatformPointDividendListPage(custType,
                    hsResNo, custName, startYear, endYear);
            if (pointDividendList != null && pointDividendList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                pointDividendListPage = new PageData<PointDividend>(page.getCount(), pointDividendList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_POINT_DIVIDEND_LIST_ERROR.getCode() + "查询积分投资分红列表异常,params[custId:" + hsResNo
                            + ",startYear:" + startYear + ",endYear:" + endYear + "," + page + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_POINT_DIVIDEND_LIST_ERROR.getCode(),
                    "查询积分投资分红列表异常,params[custId:" + hsResNo + ",startYear:" + startYear + ",endYear:" + endYear + ","
                            + page + "]" + e);
        }
        return pointDividendListPage;
    }

    /**
     * 获取投资分红明细列表
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            分红年份
     * @param page
     *            分页信息
     * @return 返回分红明细
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getDividendDetailList(java.lang.String,
     *      java.lang.String, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<DividendDetail> getDividendDetailList(String hsResNo, String dividendYear, Page page)
            throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取积分投资分红列表,params[hsResNo:" + hsResNo + ",dividendYear:" + dividendYear + "," + page + "]");
        PageData<DividendDetail> dividendDetailListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, " 获取投资分红明细列表：分页参数为空");
        try
        {
            // 设置分页信息
            PageContext.setPage(page);
            // 执行查询
            List<DividendDetail> dividendDetailList = dividendDetailMapper.findDividendDetailListPage(hsResNo,
                    dividendYear);
            if (dividendDetailList != null && dividendDetailList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                dividendDetailListPage = new PageData<DividendDetail>(page.getCount(), dividendDetailList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_POINT_DIVIDEND_CALC_LIST_ERROR.getCode() + "获取投资分红明细列表异常,params[hsResNo:"
                            + hsResNo + ",dividendYear:" + dividendYear + "," + page + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_POINT_DIVIDEND_CALC_LIST_ERROR.getCode(), "获取投资分红明细列表异常");
        }
        return dividendDetailListPage;
    }

    /**
     * 实现查询积分投资分红信息外部接口
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            分红年份
     * @return 积分投资分红信息
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getInvestDividendInfo(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public CustomPointDividend getInvestDividendInfo(String hsResNo, String dividendYear) {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询积分投资分红信息,params[hsResNo:" + hsResNo + ",dividendYear:" + dividendYear + "]");
        HsAssert.hasText(hsResNo, RespCode.PARAM_ERROR, "查询积分投资分红信息：互生号参数为空");
        // 投资分红对象
        PointDividend pointDividend = null;
        // 年度分红比率对象
        DividendRate dividendRate = null;
        // 累计积分投资总数
        String investCount = "0";
        // 分红积分投资总数
        String dividendInvestCount = "0";
        // 包装分红信息
        CustomPointDividend customPointDividend = new CustomPointDividend();
        try
        {
            // 获取最大分红年份
            String maxDividendYear = dividendRateMapper.findMaxDividendYear();

            // 获取累计积分投资总数
            investCount = pointInvestMapper.findInvestCount(hsResNo);

            // 使用传入的年份
            if (StringUtils.isNotBlank(dividendYear))
            {
                // 获取年度分红比率
                dividendRate = dividendRateMapper.findNewDividendRate(dividendYear);
                // 获取最近一年分红信息
                pointDividend = pointDividendMapper.findYearPointDividend(hsResNo, dividendYear);

                // 获取分红投资积分总数
                dividendInvestCount = pointInvestMapper.findDividendInvestCount(hsResNo, dividendYear + "-12-31");
            }
            else
            {// 使用最大年份
             // 获取年度分红比率
                dividendRate = dividendRateMapper.findNewDividendRate(maxDividendYear);
                // 获取最近一年分红信息
                pointDividend = pointDividendMapper.findYearPointDividend(hsResNo, maxDividendYear);

                // 获取分红投资积分总数
                dividendInvestCount = pointInvestMapper.findDividendInvestCount(hsResNo, DateUtil
                        .getMinDateOfDayStr(maxDividendYear + " 12-31"));
                // 设置分红年份为查询出来的最大年份
                dividendYear = maxDividendYear;
            }

            /**
             * 设置分红数据{
             */
            // 如果未查询到分红信息
            if (pointDividend == null)
            {
                pointDividend = new PointDividend();
                // 年度分红总数
                pointDividend.setTotalDividend("0");
                // 流通币帐户分红数
                pointDividend.setNormalDividend("0");
                // 定向帐户分红数
                pointDividend.setDirectionalDividend("0");
            }

            // 设置累计积分投资总数
            if (StringUtils.isBlank(investCount))
            {
                customPointDividend.setAccumulativeInvestCount("0");
            }
            else
            {
                customPointDividend.setAccumulativeInvestCount(investCount);
            }
            // 设置分红投资积分总数
            if (StringUtils.isBlank(dividendInvestCount))
            {
                customPointDividend.setDividendInvestTotal("0");
            }
            else
            {
                customPointDividend.setDividendInvestTotal(dividendInvestCount);
            }

            // 设置最近一年分红年份
            customPointDividend.setDividendYear(dividendYear);

            // 设置年度分红比率
            if (dividendRate != null && StringUtils.isNotBlank(dividendRate.getDividendRate()))
            {
                // 设置年度分红比率
                customPointDividend.setYearDividendRate(dividendRate.getDividendRate());
                // 设置年度分红总数
                customPointDividend.setTotalDividend(StringUtils.isNotBlank(pointDividend.getTotalDividend())
                        ? pointDividend.getTotalDividend() : "");
                // 设置流通币帐户分红数
                customPointDividend.setNormalDividend(StringUtils.isNotBlank(pointDividend.getNormalDividend())
                        ? pointDividend.getNormalDividend() : "");
                // 设置定向帐户分红数
                customPointDividend.setDirectionalDividend(StringUtils.isNotBlank(pointDividend
                        .getDirectionalDividend()) ? pointDividend.getDirectionalDividend() : "");
            }
            else
            {
                // 如果没有年度分红比率，默认设置为0
                customPointDividend.setYearDividendRate("0");
                // 设置年度分红比率
                customPointDividend.setYearDividendRate("0");
                // 设置年度分红总数
                customPointDividend.setTotalDividend("0");
                // 设置流通币帐户分红数
                customPointDividend.setNormalDividend("0");
                // 设置定向帐户分红数
                customPointDividend.setDirectionalDividend("0");
            }
            /**
             * }
             */
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_GET_INVEST_DIVIDEND_INFO_ERROR.getCode() + "获取积分投资分红通用信息异常,params[hsResNo:" + hsResNo
                            + ",dividendYear:" + dividendYear + "]", e);
            throw new HsException(BSRespCode.BS_GET_INVEST_DIVIDEND_INFO_ERROR, "获取积分投资分红通用信息异常,params[hsResNo:"
                    + hsResNo + ",dividendYear:" + dividendYear + "]" + e);
        }
        return customPointDividend;
    }

    /**
     * 查询积分投资详情
     * 
     * @param investNo
     *            投资流水号
     * @return 积分投资详情
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getPointInvestInfo(java.lang.String)
     */
    @Override
    public PointInvest getPointInvestInfo(String investNo) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询积分投资详情,params[investNo:" + investNo + "]");
        // 客户号为空
        HsAssert.hasText(investNo, BSRespCode.BS_PARAMS_NULL, "查询积分投资详情：客户号为空");
        try
        {
            // 根据客户号根据积分投资信息
            return pointInvestMapper.findPointInvestInfo(investNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_QUERY_ERROR.getCode()
                    + "查询积分投资详情异常,params[investNo:" + investNo + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_ERROR.getCode(), "查询积分投资详情异常,params[investNo:" + investNo + "]"
                    + e);
        }
    }

    /**
     * 查询积分投资分红详情
     * 
     * @param dividendNo
     *            分红流水号
     * @return 分红详情
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getPointDividendInfo(java.lang.String)
     */
    @Override
    public PointDividend getPointDividendInfo(String dividendNo) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询积分投资分红详情,params[dividendNo:" + dividendNo + "]");
        HsAssert.hasText(dividendNo, BSRespCode.BS_PARAMS_NULL, "查询积分投资分红详情：分红流水号参数为空");
        PointDividend pointDividend = null;
        try
        {
            // 查询积分投资分红详情
            pointDividend = pointDividendMapper.findPointDividendInfo(dividendNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_POINT_DIVIDEND_DETAIL_ERROR + ":查询积分投资分红详情异常,params[dividendNo:" + dividendNo
                            + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_POINT_DIVIDEND_DETAIL_ERROR, "查询积分投资分红详情异常,params[dividendNo:"
                    + dividendNo + "]" + e);
        }
        return pointDividend;
    }

    /**
     * 查询积分投资分红计算详情
     * 
     * @param dividendNo
     *            分红流水号
     * @return 分红计算详情
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSInvestProfitService#getDividendDetailInfo(java.lang.String)
     */
    @Override
    public DividendDetail getDividendDetailInfo(String dividendNo) throws HsException {
        // 记录业务日志
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "params[dividendNo:" + dividendNo + "]", "查询积分投资分红计算详情");
        HsAssert.hasText(dividendNo, BSRespCode.BS_PARAMS_NULL, "查询积分投资分红计算详情：分红流水号参数为空");
        DividendDetail dividendDetail = null;
        try
        {
            // 查询分红计算详情
            dividendDetail = dividendDetailMapper.findDividendDetailInfo(dividendNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_DIVIDEND_DETAIL_ERROR + ":查询积分投资分红详情异常,params[dividendNo:" + dividendNo + "]",
                    e);
            throw new HsException(BSRespCode.BS_QUERY_DIVIDEND_DETAIL_ERROR, "查询积分投资分红详情异常,params[dividendNo:"
                    + dividendNo + "]" + e);
        }
        return dividendDetail;
    }

    /**
     * 校验客户信息
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     */
    private void checkCustInfo(Integer custType, String custId) {
        // 企业状态信息
        BsEntStatusInfo entStatusInfo = null;
        try
        {
            if (CustType.TRUSTEESHIP_ENT.getCode() == custType// 托管企业
                    || CustType.SERVICE_CORP.getCode() == custType// 服务公司
            )
            {
                // 调用用户中心查询企业状态信息
                entStatusInfo = ucEntService.searchEntStatusInfo(custId);

                // 未查询到企业信息
                HsAssert.notNull(entStatusInfo, BSRespCode.BS_NOT_QUERY_DATA, "保存积分投资信息：未查询企业信息");
                // 企业状态为重要信息变更期
                HsAssert.isTrue(entStatusInfo.getIsMaininfoChanged() == 1 ? false : true,
                        BSRespCode.BS_CHANGING_IMPORTENT_INFO, "保存积分投资信息：重要信息变更期不能积分投资");

                HsAssert.isTrue(entStatusInfo.getIsOweFee() == 1 ? false : true,
                        AOErrorCode.AO_OWE_FEE_CAN_NOT_PROXY_HSB, "保存积分投资信息：尚有年费未缴清");
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_TO_UC_QUERY_CUST_STATUS_ERROR.getCode() + "保存积分投资信息：调用用户中心查询企业状态信息异常|"
                            + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")", e);
            throw new HsException(BSRespCode.BS_TO_UC_QUERY_CUST_STATUS_ERROR.getCode(), "保存积分投资信息：调用用户中心查询企业状态信息异常|"
                    + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")" + "\n" + e);
        }
    }
}
