/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.annualfee.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeDetailService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeOrderService;
import com.gy.hsxt.bs.annualfee.mapper.AnnualFeeOrderMapper;
import com.gy.hsxt.bs.annualfee.utils.AnnualValidateUtils;
import com.gy.hsxt.bs.bean.annualfee.*;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeOrderService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/11 14:11
 * @Version V3.0.0.0
 */
@Service
public class AnnualFeeOrderService implements IAnnualFeeOrderService {

    /**
     * 业务系统基础配置
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 年费订单MAPPER接口
     */
    @Resource
    private AnnualFeeOrderMapper annualFeeOrderMapper;

    /**
     * 注入记帐分解接口
     */
    @Resource
    private IAccountDetailService accountDetailService;

    /**
     * 注入 年费信息接口
     */
    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    /**
     * 注入 计费单接口
     */
    @Resource
    private IAnnualFeeDetailService annualFeeDetailService;

    /**
     * 公共参数接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 用户中心状态同步接口
     */
    @Resource
    private IUCBsEntService bsEntService;

    @Resource
    private OrderMapper orderMapper;

    /**
     * 保存实体
     *
     * @param annualFeeOrder 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(AnnualFeeOrder annualFeeOrder) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存年费业务订单--实体类参为annualFeeOrder:" + annualFeeOrder);
        // 参数验证
        Order order = annualFeeOrder.getOrder();

        try {
            // ---------------------------创建年费业务单------------------------//
            order.setCustType(HsResNoUtils.getCustTypeByHsResNo(order.getHsResNo()));// 企业类型
            order.setOrderType(OrderType.ANNUAL_FEE.getCode());// 订单类型
            order.setIsProxy(false);// 是否平台代理
            order.setOrderUnit("年");
            order.setOrderRemark("年费业务单");// 订单备注
            order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
            order.setIsInvoiced(1);// 是否需要发票
            order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
            order.setIsNeedInvoice(1);// 是否需要发票
            order.setIsInvoiced(0);// 是否已开发票
            // 生成价格方案主键ID
            order.setOrderNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

            // 创建操作日志
            annualFeeOrderMapper.insertAnnualFeeOrder(annualFeeOrder.getOrder());

            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + annualFeeOrder.getOrder(),
                    "保存年费业务订单成功");

            return order.getOrderNo();
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存年费业务订单错误，参数[annualFeeOrder]:" + annualFeeOrder, e);

            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "保存年费业务订单异常,原因:" + e.getMessage());
        }
    }

    /**
     * 更新实体
     *
     * @param annualFeeOrder 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(AnnualFeeOrder annualFeeOrder) throws HsException {
        return 0;
    }

    /**
     * 根据ID查询实体
     *
     * @param id orderNo
     * @return t
     * @throws HsException
     */
    @Override
    public AnnualFeeOrder queryBeanById(String id) throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询年费业务单的参数[orderNo]：" + id);
        // 校验参数
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "业务订单编号[id]为空");

        try {
            // 执行查询
            Order order = annualFeeOrderMapper.selectBeanById(id);

            return this.assignArea(AnnualFeeOrder.bulid(order));
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "查询年费业务单orderNo[" + id + "]失败", e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "查询年费业务单失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeeOrder> queryListByQuery(Query query) throws HsException {
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询年费业务单列表参数[query]：" + query);

        AnnualFeeOrderQuery annualFeeOrderQuery;
        if (query == null) {
            annualFeeOrderQuery = new AnnualFeeOrderQuery();
        } else {
            HsAssert.isInstanceOf(AnnualFeeOrderQuery.class, query, RespCode.PARAM_ERROR,
                    "查询实体[query]不是AnnualFeeOrderQuery类型");
            annualFeeOrderQuery = ((AnnualFeeOrderQuery) query);
        }
        // 设置订单类型为年费类型
        annualFeeOrderQuery.setOrderType(OrderType.ANNUAL_FEE.getCode());

        List<AnnualFeeOrder> annualFeeOrders = new ArrayList<>();

        try {
            // 查询年费业务订单
            List<Order> orders = annualFeeOrderMapper.selectListByQuery(annualFeeOrderQuery);
            if (CollectionUtils.isNotEmpty(orders)) {
                for (Order order : orders) {
                    annualFeeOrders.add(AnnualFeeOrder.bulid(order));
                }
            }
            return annualFeeOrders;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询年费业务单失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "分页查询年费业务单失败，原因:" + e.getMessage());
        }
    }

    /**
     * 查询某企业未付款的年费业务单的编号
     *
     * @param custId 企业客户号
     * @return orderNo
     */
    @Override
    public AnnualFeeOrder queryBeanForWaitPayByCustId(String custId) throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanForWaitPayByCustId", "查询某企业未付款的年费业务单的参数[custId]："
                + custId);
        // 校验参数
        HsAssert.hasText(custId, RespCode.PARAM_ERROR, "企业客户号[custId]为空");
        try {
            // 执行查询
            AnnualFeeOrderQuery annualFeeOrderQuery = new AnnualFeeOrderQuery();
            annualFeeOrderQuery.setOrderType(OrderType.ANNUAL_FEE.getCode());
            annualFeeOrderQuery.setPayStatus(PayStatus.WAIT_PAY.getCode());
            annualFeeOrderQuery.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
            annualFeeOrderQuery.setCustId(custId);
            Order order = annualFeeOrderMapper.selectOrderForWaitPay(annualFeeOrderQuery);
            return AnnualFeeOrder.bulid(order);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanForWaitPayByCustId", "查询企业[" + custId
                    + "]未付款的年费业务单失败", e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "查询企业[" + custId + "]未付款的年费业务单失败，原因："
                    + e.getMessage());
        }
    }

    /**
     * 修改年费业务单的金额
     *
     * @param annualFeeOrder 年费业务单
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBeanForAmount(AnnualFeeOrder annualFeeOrder) throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBeanForAmount", "修改年费业务单的金额--实体类参为annualFeeOrder:"
                + annualFeeOrder);
        // 检验参数
        Order order = AnnualValidateUtils.modifyAmountValidate(annualFeeOrder);
        try {
            // 执行查询
            return annualFeeOrderMapper.updateAnnualFeeOrderForAmount(order);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBeanForAmount", "修改年费业务单的金额失败,参数[annualFeeOrder]:"
                    + annualFeeOrder, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "修改年费业务单的金额失败,原因:" + e.getMessage());
        }
    }

    /**
     * 支付完成后修改年费业务单
     *
     * @param annualFeeOrder 年费业务单
     * @param note           是否实时记账
     * @param orgEndDate     修改前的截止日期
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBeanForPaid(AnnualFeeOrder annualFeeOrder, String orgEndDate, boolean note) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBeanForPaid", "支付完成后修改年费业务单--参数[annualFeeOrder]:" + annualFeeOrder);
        // 检验参数
        Order order = AnnualValidateUtils.modifyPaidValidate(annualFeeOrder);
        try {
            // 执行查询
            int count = orderMapper.updateOrderAllStatus(order);
            // 支付成功之后年费记账分解与实时记账
            if ((PayStatus.PAY_FINISH.getCode() == order.getPayStatus())) {
                AnnualFeeInfo annualFeeInfo = annualFeeInfoService.queryBeanById(order.getCustId());
                // 同步年费状态
                BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
                bsEntStatusInfo.setEntCustId(order.getCustId());
                bsEntStatusInfo.setIsOweFee(0);
                bsEntStatusInfo.setExpireDate(annualFeeInfo.getEndDate());
                bsEntService.updateEntStatusInfo(bsEntStatusInfo, order.getOrderOperator());
                if (note) {
                    try {
                        parseAndNoteAccountForAnnualFee(order);
                    } catch (HsException e) {
                        bsEntStatusInfo.setIsOweFee(0);
                        bsEntStatusInfo.setExpireDate(orgEndDate);
                        bsEntService.updateEntStatusInfo(bsEntStatusInfo, order.getOrderOperator());
                        throw e;
                    }
                }
            }
            return count;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:modifyBeanForPaid", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBeanForPaid", "支付完成后修改年费业务单失败，参数[annualFeeOrder]:"
                    + annualFeeOrder, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "支付完成后修改年费业务单失败,原因:" + e.getMessage());
        }
    }

    /**
     * 年费记账分解与实时记账
     *
     * @param order 年费业务订单
     * @throws HsException 异常
     */
    private void parseAndNoteAccountForAnnualFee(Order order) throws HsException {

        // 校验基础信息
        LocalInfo localInfo = commonService.getAreaPlatInfo();
        HsAssert.notNull(localInfo, RespCode.PARAM_ERROR, "本地平台信息[localInfo]为空");

        String platCustId = commonService.getAreaPlatCustId();
        HsAssert.hasText(platCustId, RespCode.PARAM_ERROR, "本平台客户号[areaPlatCustId]为空");

        List<AccountDetail> accountDetails = new ArrayList<>();
        // 设置交易类型与账户类型
        TransType transType;
        AccountType accountType;
        if (PayChannel.TRANSFER_REMITTANCE.getCode() == order.getPayChannel().intValue()) {
            // 临账支付
            transType = TransType.C_TEMP_PAY_ANNUAL_FEE;
            accountType = AccountType.ACC_TYPE_POINT50210;// 平台临账收款
        } else if (PayChannel.HS_COIN_PAY.getCode() == order.getPayChannel().intValue()) {
            // 互生币支付
            transType = TransType.C_HSB_PAY_ANNUAL_FEE;
            accountType = AccountType.ACC_TYPE_POINT20110; // 流通币
        } else {
            // 网银支付 手机支付 快键支付
            transType = TransType.C_INET_PAY_ANNUAL_FEE;
            accountType = AccountType.ACC_TYPE_POINT50110;// 平台网银收款
        }
        // -------------------------平台年费收入数据拼装--------------------------
        /**
         * 年费收入 ACC_TYPE_POINT20440("20440"),
         */
        AccountDetail platDetail = new AccountDetail();// 创建对象
        platDetail.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
        platDetail.setBizNo(order.getOrderNo());// 交易流水号
        platDetail.setTransType(transType.getCode());// 交易类型
        platDetail.setCustId(platCustId);// 平台 客户号
        platDetail.setHsResNo(localInfo.getPlatResNo());// 平台互生号
        platDetail.setCustName(localInfo.getPlatNameCn());// 客户名称
        platDetail.setCustType(CustType.AREA_PLAT.getCode());// 客户类型
        platDetail.setAccType(AccountType.ACC_TYPE_POINT20440.getCode());// 账户类型编码
        // 年费收入
        String incomeAmount = (PayChannel.HS_COIN_PAY.getCode() == order.getPayChannel().intValue()) ? order
                .getOrderHsbAmount() : order.getOrderCashAmount();
        platDetail.setAddAmount(incomeAmount);// 增向金额
        platDetail.setDecAmount("0");// 减向金额
        platDetail.setCurrencyCode(order.getCurrencyCode());// 币种
        platDetail.setFiscalDate(DateUtil.getCurrentDateTime());// 会计日期
        platDetail.setRemark("缴纳年费");// 备注内容
        platDetail.setStatus(true);// 记账状态

        accountDetails.add(platDetail);

        // 互生币支付记企业的账
        if (PayChannel.HS_COIN_PAY.getCode() == order.getPayChannel().intValue()) {
            // -------------------------企业数据拼装--------------------------
            /**
             * 流通币 ACC_TYPE_POINT20110("20110"),
             */
            AccountDetail companyDetail = new AccountDetail();// 创建对象
            companyDetail.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
            companyDetail.setBizNo(order.getOrderNo());// 交易流水号
            companyDetail.setTransType(TransType.C_HSB_PAY_ANNUAL_FEE.getCode());// 交易类型
            companyDetail.setCustId(order.getCustId());// 客户号
            companyDetail.setHsResNo(order.getHsResNo());// 互生号
            companyDetail.setCustName(order.getCustName());// 客户名称
            companyDetail.setCustType(order.getCustType());// 客户类型
            companyDetail.setAccType(accountType.getCode());// 账户类型编码 流通币
            companyDetail.setAddAmount("0");// 增向金额
            companyDetail.setDecAmount(order.getOrderHsbAmount());// 减向金额
            companyDetail.setCurrencyCode(order.getCurrencyCode());// 币种
            companyDetail.setFiscalDate(DateUtil.getCurrentDateTime());// 会计日期
            companyDetail.setRemark("缴纳年费");// 备注内容
            companyDetail.setStatus(true);// 记账状态

            accountDetails.add(companyDetail);
        } else {
            // -------------------------平台网银/临账收入数据拼接-------------------------
            /**
             * 平台网银收款(本币) ACC_TYPE_POINT50110("50110"), 平台临账收款(本币)
             * ACC_TYPE_POINT50210("50210")
             */
            AccountDetail platAccount = new AccountDetail();// 创建对象
            platAccount.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
            platAccount.setBizNo(order.getOrderNo());// 交易流水号
            platAccount.setTransType(transType.getCode());// 交易类型
            platAccount.setCustId(platCustId);// 平台 客户号
            platAccount.setHsResNo(localInfo.getPlatResNo());// 平台互生号
            platAccount.setCustName(localInfo.getPlatNameCn());// 客户名称
            platAccount.setCustType(CustType.AREA_PLAT.getCode());// 客户类型
            platAccount.setAccType(accountType.getCode());// 账户类型编码
            // 平台网银收款
            platAccount.setAddAmount(order.getOrderCashAmount());// 增向金额
            platAccount.setDecAmount("0");// 减向金额
            platAccount.setCurrencyCode(order.getCurrencyCode());// 币种
            platAccount.setFiscalDate(DateUtil.getCurrentDateTime());// 会计日期
            platAccount.setRemark("缴纳年费");// 备注内容
            platAccount.setStatus(true);// 记账状态

            accountDetails.add(platAccount);
        }
        // 执行插入
        accountDetailService.saveGenActDetail(accountDetails, "缴纳年费", true);

        // 创建操作日志
        BizLog.biz(bsConfig.getSysName(), "function:parseAndNoteAccountForAnnualFee", "params==>" + accountDetails,
                "缴纳年费收款记帐分解记录成功");
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeeOrder> queryListForPage(Page page, Query query) throws HsException {

        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询年费业务单参数[annualFeeOrderQuery]：" + query);

        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为空");

        AnnualFeeOrderQuery annualFeeOrderQuery;
        if (query == null) {
            annualFeeOrderQuery = new AnnualFeeOrderQuery();
        } else {
            HsAssert.isInstanceOf(AnnualFeeOrderQuery.class, query, RespCode.PARAM_ERROR,
                    "查询实体[query]不是AnnualFeeOrderQuery类型");
            annualFeeOrderQuery = ((AnnualFeeOrderQuery) query);
        }
        // 设置订单类型为年费类型
        annualFeeOrderQuery.setOrderType(OrderType.ANNUAL_FEE.getCode());

        List<AnnualFeeOrder> annualFeeOrders = new ArrayList<>();

        try {
            PageContext.setPage(page);
            // 查询年费业务订单
            List<Order> orders = annualFeeOrderMapper.selectBeanListPage(annualFeeOrderQuery);
            if (CollectionUtils.isNotEmpty(orders)) {
                for (Order order : orders) {
                    annualFeeOrders.add(this.assignArea(AnnualFeeOrder.bulid(order)));
                }
            }
            return annualFeeOrders;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询年费业务单失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_ORDER_DB_ERROR, "分页查询年费业务单失败,原因:" + e.getMessage());
        }
    }

    /**
     * 设置缴费区间
     *
     * @param annualFeeOrder 年费订单
     * @return bean
     * @throws HsException
     */
    private AnnualFeeOrder assignArea(AnnualFeeOrder annualFeeOrder) throws HsException {
        //查询业务订单对应的所有计费单
        AnnualFeeDetailQuery query = AnnualFeeDetailQuery.build();
        HsAssert.notNull(annualFeeOrder.getOrder(), BSRespCode.BS_PARAMS_NULL, "年费业务单为空");
        query.setOrderNo(annualFeeOrder.getOrder().getOrderNo());

        AnnualFeeDetail annualFeeDetail = annualFeeDetailService.queryAnnualAreaForArrear(query);
        if (annualFeeDetail != null) {
            annualFeeOrder.setAreaStartDate(annualFeeDetail.getStartDate());
            annualFeeOrder.setAreaEndDate(annualFeeDetail.getEndDate());
        }
        return annualFeeOrder;
    }
}
