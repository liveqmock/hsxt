/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.tool.*;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.TransCodeUtil;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.*;
import com.gy.hsxt.bs.common.enumtype.quota.AppType;
import com.gy.hsxt.bs.common.enumtype.tool.BuyStatus;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.ConfStatus;
import com.gy.hsxt.bs.common.enumtype.tool.ConfType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.bs.quota.mapper.QuotaMapper;
import com.gy.hsxt.bs.tool.bean.ApplyOrderConfig;
import com.gy.hsxt.bs.tool.interfaces.IInsideInvokeCall;
import com.gy.hsxt.bs.tool.mapper.*;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.*;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;

/**
 * 内部调用接口实现类
 *
 * @version V3.0.0
 * @Package: com.gy.hsxt.bs.tool.service
 * @ClassName: InsideInvokeCall
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月12日 下午3:27:42
 * @company: gyist
 */
@Service("insideInvokeCall")
public class InsideInvokeCall implements IInsideInvokeCall {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 工具产品Mapper
     **/
    @Resource
    private ToolProductMapper toolProductMapper;

    /**
     * 互生卡样Mapper
     **/
    @Resource
    private CardStyleMapper cardStyleMapper;

    /**
     * 仓库Mapper
     **/
    @Resource
    private WarehouseMapper warehouseMapper;

    /**
     * 工具配置单Mapper
     **/
    @Resource
    private ToolConfigMapper toolConfigMapper;

    /**
     * 订单Mapper
     **/
    @Resource
    private OrderMapper orderMapper;

    /**
     * 配额Mapper
     **/
    @Resource
    private QuotaMapper quotaMapper;

    /**
     * 互生卡信息Mapper
     **/
    @Resource
    private CardInfoMapper cardInfoMapper;

    /**
     * BS公共Service
     **/
    @Resource
    private ICommonService commonService;

    /**
     * 记帐分解Service
     **/
    @Resource
    private IAccountDetailService accountDetailService;

    /**
     * 用户中心互生卡开卡Service
     **/
    @Resource
    private IUCBsCardHolderService bsCardHolderService;

    /**
     * BS公共Mapper
     **/
    @Resource
    private CommonMapper commonMapper;

    /**
     * 参数配置系统
     **/
    @Resource
    public BusinessParamSearch businessParamSearch;

    /**
     * 资源段Mapper接口
     */
    @Resource
    private ResSegmentMapper resSegmentMapper;

    /**
     * 地区平台配额审批
     *
     * @param bean 地区平台申请配额参数
     * @throws HsException
     * @Description:
     */
    @Override
    @Transactional
    public void apprPlatQuota(PlatQuotaApp bean) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprPlatQuota", "input param:" + bean, "一级区域配额审批");
        try {
            // 验证参数非null
            HsAssert.notNull(bean, RespCode.PARAM_ERROR, "一级区域配额审批参数为null," + bean);
            HsAssert.notNull(bean.getStatus(), RespCode.PARAM_ERROR, "一级区域配额审批状态为null," + bean);
            HsAssert.notNull(bean.getApplyType(), RespCode.PARAM_ERROR, "一级区域配额申请类型为null," + bean);
            // 根据申请编号查询记录是否存
            PlatQuotaApp param = quotaMapper.getPlatQuotaById(bean.getApplyId());
            // 记录不存在就插入数据，不存在就审批数据
            if (null == param) {
                // 插入申请数据
                quotaMapper.applyPlatQuota(bean);
            } else {
                // 判断是否审批过，审批数据
                HsAssert.isTrue(param.getStatus().intValue() == ApprStatus.WAIT_APPR.getCode(),
                        BSRespCode.BS_RECORD_IS_FINISH, "申请记录已经审批," + bean);
                quotaMapper.apprPlatQuota(bean);
            }
            // 审批通过
            if (bean.getStatus().intValue() == ApprStatus.PASS.getCode()) {
                // 首次分配和新增分配
                if (AppType.FIRST.getCode() == bean.getApplyType().intValue()
                        || AppType.ADD.getCode() == bean.getApplyType().intValue()) {
                    String[] resNoArr = bean.getResNoList().split(",");
                    // 判断批复数量是否等于互生号列表
                    HsAssert.notNull(bean.getApprNum().intValue() == resNoArr.length, RespCode.PARAM_ERROR,
                            "参数错误,实际分配的资源号数量与批复的数据不一致," + bean);
                    // 获取地区平台信息
                    LocalInfo localInfo = commonService.getAreaPlatInfo();
                    // 插入资源分配数据
                    quotaMapper.insertPlatQuota(resNoArr, localInfo.getCountryNo());
                    // TODO 目前不支持减少
                } else {
                    HsAssert.isTrue(bean.getApprNum().intValue() <= quotaMapper.countFreePlatQuota(),
                            BSRespCode.BS_QUOTA_NOT_ENAUGH_FOR_RELEASE, "可减少资源配额不足," + bean);
                    // 降序查询
                    List<String> resNoList = quotaMapper.queryFreeQuotaOfPlat(bean.getApprNum());
                    if (StringUtils.isNotBlank(resNoList)) {
                        int size = resNoList.size();
                        // 设置实际释放的资源号列表，保存起来以便查询
                        bean.setResNoList(
                                StringUtil.arrayToString((String[]) resNoList.toArray(new String[size]), ","));
                        quotaMapper.deletePlatQuota(resNoList);
                    }
                }
            }
        } catch (Exception ex) {
            SystemLog.error(this.getClass().getName(), "apprPlatQuota",
                    BSRespCode.BS_AREA_PLAT_QUOTA_APPR_FAIL.getCode() + ":地区平台配额审批失败," + bean, ex);
            throw new HsException(BSRespCode.BS_AREA_PLAT_QUOTA_APPR_FAIL, "地区平台配额审批失败," + bean);
        }
    }

    /**
     * 增加申报工具配置
     *
     * @param bean 申报订单配置
     * @throws HsException
     * @Description:
     */
    @Override
    @Transactional
    public void addApplyOrderToolConfig(ApplyOrderConfig bean) throws HsException {
        SystemLog.info(this.getClass().getName(), "function:addApplyOrderToolConfig", "params==>" + bean, "增加申报工具配置");
        // 订单号
        String orderNo = "";
        // 订单实体
        Order order = null;
        // 申报订单
        Order applyOrder = null;
        // 配置单集合
        List<ToolConfig> confs = null;
        // 仓库
        Warehouse wh = null;
        // 申报需要配置的工具
        List<ToolProduct> products = null;
        // 默认卡样
        CardStyle style = null;
        // 卡样id
        String cardStyleId = "";
        // 地区平台信息
        LocalInfo info = null;
        //企业资源段
        List<ResSegment> segment = null;
        // 配置单号
        String confNo = null;
        // 目前时间
        String now = DateUtil.getCurrentDateTime();
        // 验证参数null
        HsAssert.notNull(bean, RespCode.PARAM_ERROR, "增加申报工具配置参数为NULL," + bean);
        // 根据注解验证参数
        String valid = ValidateParamUtil.validateParam(bean);
        HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);
        // 订单号
        orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
        // 地区平台信息
        info = commonService.getAreaPlatInfo();
        HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);
        // 查询申报订单
        applyOrder = orderMapper.findOrderByNo(bean.getOrderNo());
        HsAssert.notNull(applyOrder, BSRespCode.BS_ORDER_NOT_EXIST, "查询申报订单失败," + bean);
        try {
            // 根据企业省编号获取到仓库
            wh = warehouseMapper.selectWarehouseByNo(bean.getProvinceNo());
            if (null == wh) {
                // 省地区未配置参数则获取地区平台默认参数
                wh = warehouseMapper.selectWarehouseByDefault();
            }

            // 查询申报时需要配置的工具
            products = toolProductMapper.selectApplyToolProduct(bean.getResFeeId());

            // 测试
            // products = toolProductMapper.selectApplyToolProductTest();

            // 没有工具时不添加配置单
            if (StringUtils.isBlank(products)) {
                return;
            }
            confs = new ArrayList<ToolConfig>();

            // 设置订单参数
            order = new Order();
            order.setOrderNo(orderNo);// 订单号
            order.setHsResNo(applyOrder.getHsResNo());// 互生号
            order.setCustId(bean.getEntCustId());// 客户号
            order.setCustName(applyOrder.getCustName());// 客户名称
            order.setBizNo(bean.getOrderNo());// 原业务编号
            order.setCustType(HsResNoUtils.getCustTypeByHsResNo(applyOrder.getHsResNo()));// 客户类型
            order.setOrderType(OrderType.APPLY_BUY_TOOL.getCode());// 订单类型
            order.setIsProxy(false);// 是否平台代购
            order.setOrderTime(now);// 订单时间
            order.setPayOvertime(null);// 支付超时时间
            order.setOrderStatus(OrderStatus.WAIT_CONFIG_GOODS.getCode());// 订单状态
            order.setPayStatus(PayStatus.PAY_FINISH.getCode());// 支付状态
            order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票
            order.setOrderOriginalAmount("0");// 原始金额
            order.setOrderDerateAmount("0");
            order.setOrderCashAmount("0");// 货币金额
            order.setOrderHsbAmount("0");// 互生币金额
            order.setExchangeRate(info.getExchangeRate().toString());// 货币转换比率
            order.setOrderRemark("申报工具订单下单");// 备注
            order.setOrderChannel(OrderChannel.WEB.getCode());// 订单渠道
            order.setCurrencyCode(info.getCurrencyCode());// 币种
            order.setOrderOperator(bean.getOperNo());// 订单操作员
            order.setPayChannel(StringUtils.isNotBlank(applyOrder.getPayChannel()) ? applyOrder.getPayChannel()
                    : PayChannel.TRANSFER_REMITTANCE.getCode());// 支付方式(没有则默认临账)

            // 设置配置单的参数
            for (ToolProduct product : products) {
                confNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
                // 工具类别是互生卡时，获取地区平台的默认卡样
                if (product.getCategoryCode().equals(CategoryCode.P_CARD.name())) {
                    // 获取默认卡样
                    style = cardStyleMapper.selectCardStyleByDefault();
                    cardStyleId = style != null ? style.getCardStyleId() : "";
                    //获取企业资源段
                    segment = this.createResourceSegment(bean.getEntCustId(), product.getQuantity().intValue(), 0, confNo).getSegment();
                }
                // 配置单对象
                ToolConfig conf = new ToolConfig(confNo,
                        bean.getEntCustId(), applyOrder.getHsResNo(), null, product.getCategoryCode(),
                        product.getProductId(), product.getProductName(), product.getUnit(), "0", product.getQuantity(),
                        "0", cardStyleId, orderNo, ConfStatus.WAIT_CONFIG.getCode(), null, "申报工具订单配置", bean.getOperNo(),
                        now, ConfType.APPLY_CONFIG.getCode(), wh.getWhId());
                confs.add(conf);
            }
            // 插入订单、配置单数据
            orderMapper.insertOrder(order);
            toolConfigMapper.batchInsertToolConfig(confs);
            //插入企业资源段
            if (null != segment && segment.size() > 0 && false)
            {
                resSegmentMapper.batchInsertResSegment(segment);
            }
        } catch (Exception ex) {
            SystemLog.error(this.getClass().getName(), "function:addApplyOrderToolConfig",
                    BSRespCode.BS_ADD_APPLY_CONFIG_FAIL.getCode() + ":增加申报工具配置失败," + bean, ex);
            throw new HsException(BSRespCode.BS_ADD_APPLY_CONFIG_FAIL, "增加申报工具配置失败," + bean);
        }
    }

    /**
     * 工具订单关闭
     *
     * @param bean 订单实体
     * @throws HsException
     * @Description:
     */
    @Override
    @Transactional
    public void toolOrderClose(Order bean) throws HsException {
        SystemLog.info(this.getClass().getName(), "function:toolOrderClose", "params==>" + bean, "工具订单关闭");
        int count = 0;

        HsAssert.notNull(OrderType.mayCloseToolOrder(bean.getOrderType()), RespCode.PARAM_ERROR,
                "订单号错误或不是可以关闭的订单类型," + bean);
        try {
            // 修改订单状态
            count = orderMapper.updateOrderStatus(bean.getOrderNo(), OrderStatus.IS_CLOSED.getCode());
            // 包含配置单的工具
            if (count > 0 && OrderType.isHasConfigToolOrder(bean.getOrderType())) {
                // 修改配置单取消
                count = toolConfigMapper.updateConfStatusByOrderNo(bean.getOrderNo(), ConfStatus.CANCELED.getCode());
            }
            // 定制卡样订单
            if (OrderType.CARD_STYLE_FEE.getCode().equals(bean.getOrderType())) {
                // 删除卡样
                cardStyleMapper.deleteCardStyleByNo(bean.getOrderNo());
            }
            // 申购消费者资源段订单类型撤单修改资源为待购买
            if(OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType()) && false)
            {
                resSegmentMapper.updateResSegmentStatusByOrderNo(bean.getOrderNo(), BuyStatus.MAY_BUY.getCode());
            }
        } catch (Exception ex) {
            SystemLog.error(this.getClass().getName(), "function:toolOrderClose",
                    BSRespCode.BS_TOOL_ORDER_CLOSE_FAIL.getCode() + ":工具订单关闭失败," + bean, ex);
            count = 0;
        }
        HsAssert.isTrue(count > 0, BSRespCode.BS_TOOL_ORDER_CLOSE_FAIL, "工具订单关闭失败");
    }

    /**
     * 工具订单撤单
     *
     * @param bean
     * @throws HsException
     * @Description:
     */
    @Override
    @Transactional
    public void toolOrderWithdrawals(Order bean) throws HsException {
        BizLog.biz(this.getClass().getName(), "function:toolOrderWithdrawals", "params==>" + bean, "工具订单撤单");
        int count = 0;
        // 地区平台信息
        LocalInfo info = commonService.getAreaPlatInfo();
        // 地区平台客户号
        String platCustId = commonService.getAreaPlatCustId();
        // 交易类型
        String transType = "";
        // 记账交易流水号
        String accountTransNo = "";
        // 内部记账分解参数
        List<AccountDetail> details = null;

        // 验证订单的状态(是否待确认)
        HsAssert.notNull(OrderType.isConfirmToolOrder(bean.getOrderType()), RespCode.PARAM_ERROR,
                "订单号错误或不是可以撤单的订单类型," + bean);

        // 获取交易类型
        transType = TransCodeUtil.getToolOrderCancelTransCode(bean.getOrderType(), bean.getPayChannel());
        accountTransNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
        try {
            // 内部记账分解
            details = new ArrayList<AccountDetail>();
            AccountDetail detail = null;
            // 客户
            detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
                    transType, bean.getCustId(), bean.getHsResNo(), bean.getCustName(), bean.getCustType(),
                    AccountType.ACC_TYPE_POINT20110.getCode(), bean.getOrderHsbAmount(), "0", info.getHsbCode(),
                    DateUtil.getCurrentDateTime(), getToolAccountRemark(bean.getOrderType(), false), true);
            detail.setAccountTransNo(accountTransNo);
            details.add(detail);
            // 地区平台
            detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
                    transType, platCustId, info.getPlatResNo(), info.getPlatNameCn(), CustType.AREA_PLAT.getCode(),
                    getAccountType(bean.getOrderType()), "0", bean.getOrderHsbAmount(), info.getHsbCode(),
                    DateUtil.getCurrentDateTime(), getToolAccountRemark(bean.getOrderType(), false), true);
            detail.setAccountTransNo(accountTransNo);
            details.add(detail);

            // 修改订单撤单
            count = orderMapper.updateOrderStatus(bean.getOrderNo(), OrderStatus.CANCELED.getCode());
            if (count > 0) {
                // 修改配置单取消
                count = toolConfigMapper.updateConfStatusByOrderNo(bean.getOrderNo(), ConfStatus.CANCELED.getCode());
            }
            // 申购消费者资源段订单类型撤单修改资源为待购买
            if(OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType()) && false)
            {
                resSegmentMapper.updateResSegmentStatusByOrderNo(bean.getOrderNo(), BuyStatus.MAY_BUY.getCode());
            }
            if (count > 0) {
                // 调用内部记账分解
                accountDetailService.saveGenActDetail(details, "工具订单撤单记账", true);
            }
        } catch (HsException ex) {
            throw ex;
        } catch (Exception ex) {
            SystemLog.error(this.getClass().getName(), "function:toolOrderWithdrawals",
                    BSRespCode.BS_TOOL_ORDER_WITHDRAWALS_FAIL.getCode() + ":工具订单撤单失败", ex);
            count = 0;
        }
        HsAssert.isTrue(count > 0, BSRespCode.BS_TOOL_ORDER_WITHDRAWALS_FAIL, "工具订单撤单失败," + bean);
    }

    /**
     * 工具网银或临账支付成功
     *
     * @param bean 订单实体
     * @throws HsException
     * @Description:
     */
    @Override
    public void toolEbankOrTempAcctPaySucc(Order bean, String bankTransNo) throws HsException {
        BizLog.biz(this.getClass().getName(), "function:tooolEbankOrTempAcctPaySucc", "params==>" + bean,
                "工具订单网银或临账支付成功");
        // 交易类型
        String transType = "";
        // 地区平台信息
        LocalInfo info = commonService.getAreaPlatInfo();
        // 地区平台客户号
        String platCustId = commonService.getAreaPlatCustId();
        // 账户类型
        String accountType = "";
        // 备注
        String remark = "";
        // 部记账分解
        List<AccountDetail> details = null;
        AccountDetail detail = null;

        // 验证参数非null
        HsAssert.notNull(bean, RespCode.PARAM_ERROR, "订单实体参数为null," + bean);
        HsAssert.notNull(bean.getPayChannel(), RespCode.PARAM_ERROR, "订单支付方式为null," + bean);
        HsAssert.hasText(bean.getOrderType(), RespCode.PARAM_ERROR, "订单类型为null," + bean);
        HsAssert.notNull(bean.getCustType(), RespCode.PARAM_ERROR, "订单客户类型为null," + bean);
        HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "从LCS获取地区平台信息为null," + bean);
        HsAssert.hasText(platCustId, BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "从用UC地区平台客户号为null," + bean);

        // 交易类型
        transType = TransCodeUtil.getOrderTransCode(bean.getOrderType(), bean.getCustType(),
                bean.getPayChannel().intValue());

        details = new ArrayList<AccountDetail>();
        // 网银(网银、快捷、手机)
        if (PayChannel.isGainPayAddress(bean.getPayChannel().intValue())) {
            accountType = AccountType.ACC_TYPE_POINT50110.getCode();
            remark = "网银";
        } else
        // 临账
        {
            accountType = AccountType.ACC_TYPE_POINT50210.getCode();
            remark = "临账";
        }
        // 平台 销售工具收入或资源费收入
        detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
                transType, platCustId, info.getPlatResNo(), info.getPlatNameCn(), CustType.AREA_PLAT.getCode(),
                getAccountType(bean.getOrderType()), bean.getOrderCashAmount(), "0", info.getCurrencyCode(),
                DateUtil.getCurrentDateTime(), remark + getToolAccountRemark(bean.getOrderType(), true), true);
        details.add(detail);

        // 平台 平台网银收款(本币)-- 网银、快捷、手机
        detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
                transType, platCustId, info.getPlatResNo(), info.getPlatNameCn(), CustType.AREA_PLAT.getCode(),
                accountType, bean.getOrderCashAmount(), "0", info.getCurrencyCode(), DateUtil.getCurrentDateTime(),
                remark + getToolAccountRemark(bean.getOrderType(), true), true);
        details.add(detail);

        // 修改工具的制作状态并记账
        modifyToolOrderMarkStatus(bean.getOrderNo(), bean.getOrderType(), details, bankTransNo,
                "工具订单" + remark + "支付记账");
    }

    /**
     * 工具订单互生币支付记账
     *
     * @param bean 订单参数实体
     * @throws HsException
     * @Description:
     */
    @Override
    public void toolOrderHsbPayAccount(Order bean) throws HsException {
        BizLog.biz(this.getClass().getName(), "function:toolOrderHsbPayAccount", "params==>" + bean, "工具订单临账支付记账");
        // 地区平台信息
        LocalInfo info = commonService.getAreaPlatInfo();
        // 地区平台客户号
        String platCustId = commonService.getAreaPlatCustId();
        // 内部记账分解
        List<AccountDetail> details = null;
        // 交易类型
        String transType = TransCodeUtil.getOrderTransCode(bean.getOrderType(), bean.getCustType(),
                PayChannel.HS_COIN_PAY.getCode());
        // 内部记账分解
        details = new ArrayList<AccountDetail>();
        AccountDetail detail = null;
        // 客户
        detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
                transType, bean.getCustId(), bean.getHsResNo(), bean.getCustName(), bean.getCustType(),
                AccountType.ACC_TYPE_POINT20110.getCode(), "0", bean.getOrderHsbAmount(), info.getHsbCode(),
                DateUtil.getCurrentDateTime(), getToolAccountRemark(bean.getOrderType(), true), true);
        details.add(detail);
        // 地区平台 销售工具收入或资源费收入
        detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
                transType, platCustId, info.getPlatResNo(), info.getPlatNameCn(), CustType.AREA_PLAT.getCode(),
                getAccountType(bean.getOrderType()), bean.getOrderHsbAmount(), "0", info.getHsbCode(),
                DateUtil.getCurrentDateTime(), getToolAccountRemark(bean.getOrderType(), true), true);
        details.add(detail);

        // 修改工具的制作状态
        modifyToolOrderMarkStatus(bean.getOrderNo(), bean.getOrderType(), details, null, "工具订单互生币支付记账");
    }

    /**
     * 获取账户类型
     *
     * @param orderType
     * @return : String
     * @Description:
     * @author: likui
     * @created: 2016年4月16日 下午3:49:15
     * @version V3.0.0
     */
    private String getAccountType(String orderType) {
        if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(orderType)) {
            return AccountType.ACC_TYPE_POINT20520.getCode();
        }else if(OrderType.CARD_STYLE_FEE.getCode().equals(orderType))
        {
            return AccountType.ACC_TYPE_POINT20460.getCode();
        }
        return AccountType.ACC_TYPE_POINT20430.getCode();
    }

    /**
     * 获取工具订单记账备注
     *
     * @param orderType 订单类型
     * @param acctType  记账类型 true:支付 false:撤单
     * @return : String
     * @Description:
     * @author: likui
     * @created: 2016年4月5日 下午2:40:52
     * @version V3.0.0
     */
    private String getToolAccountRemark(String orderType, boolean acctType) {
        String remark = "";
        switch (OrderType.getOrderType(orderType)) {
            case BUY_TOOL:
                remark = acctType ? "申购工具" : "申购工具退款";
                break;
            case APPLY_PERSON_RESOURCE:
                remark = acctType ? "申购消费者系统资源" : "申购消费者系统资源退款";
                break;
            case AFTER_SERVICE:
                remark = acctType ? "售后工具维修费" : "售后工具维修费退款";
                break;
            case REMAKE_MY_CARD:
                remark = acctType ? "补办互生卡" : "补办互生卡退款";
                break;
            case REMAKE_BATCH_CARD:
                remark = acctType ? "重购消费者系统资源" : "重购消费者系统资源退款";
                break;
            case CARD_STYLE_FEE:
                remark = acctType ? "个性卡定制服务费" : "个性卡定制服务费退款";
                break;
            default:
                break;
        }
        return remark;
    }

    /**
     * 修改工具的制作状态
     *
     * @param orderNo     订单号
     * @param orderType   订单类型
     * @param details     记账实体列表
     * @param bankTransNo 银行交易流水号
     * @param bizName     业务名称
     * @return : void
     * @throws HsException
     * @Description:
     * @author: likui
     * @created: 2015年12月23日 上午11:23:19
     * @version V3.0.0
     */
    @Transactional
    private void modifyToolOrderMarkStatus(String orderNo, String orderType, List<AccountDetail> details,
                                           String bankTransNo, String bizName) throws HsException {
        String inParam = "orderNo:" + orderNo + "orderType:" + orderType + "details:" + details + "bankTransNo:"
                + bankTransNo + "bizName:" + bizName;
        BizLog.biz(this.getClass().getName(), "function:modifyToolOrderMarkStatus", "params==>" + inParam, "修改工具的制作状态");
        int count = 0;
        // 是否包含配置单
        boolean hasConfig = OrderType.isHasConfigToolOrder(orderType);
        try {
            // 需要 确认订单类型的
            if (OrderType.isConfirmToolOrder(orderType)) {
                // 修改订支付成功
                count = orderMapper.updateOrderPaySuccess(orderNo, bankTransNo);
                if (count > 0) {
                    // 修改配置单
                    count = toolConfigMapper.updateConfStatusByOrderNo(orderNo, ConfStatus.WAIT_CONFIRM.getCode());
                }
            } else {// 不需要确认的订单类型
                // 修改订单状态
                count = orderMapper.updateNoConfirmOrderPaySuccess(orderNo, bankTransNo,
                        hasConfig ? OrderStatus.WAIT_CONFIG_GOODS.getCode() : OrderStatus.IS_COMPLETE.getCode());
                // 包含工具配置单的工具订单
                if (count > 0 && hasConfig) {
                    // 修改配置单
                    count = toolConfigMapper.updateConfStatusByOrderNo(orderNo, ConfStatus.WAIT_CONFIG.getCode());
                }
            }
            if (count > 0) {
                // 调用内部记账分解
                accountDetailService.saveGenActDetail(details, bizName, true);
            }
        } catch (HsException ex) {
            throw ex;
        } catch (Exception ex) {
            SystemLog.error(this.getClass().getName(), "function:modifyToolOrderMarkStatus",
                    BSRespCode.BS_MODIFY_TOOL_ORDER_MARK_STATUS_FAIL.getCode() + ":工具订单修改制作状态失败," + inParam, ex);
            count = 0;
        }
        HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_TOOL_ORDER_MARK_STATUS_FAIL, "工具订单修改制作状态失败," + inParam);
    }

    /**
     * 查询未完成的工具订单
     *
     * @param entCustId 企业客户号
     * @return
     * @Description:
     */
    @Override
    public boolean queryNotFinishToolOrder(String entCustId) {
        return StringUtils.isNotBlank(commonMapper.selectNotFinishToolOrder(entCustId, new String[]
                {OrderType.APPLY_BUY_TOOL.getCode(), OrderType.BUY_TOOL.getCode(), OrderType.AFTER_SERVICE.getCode(),
                        OrderType.REMAKE_BATCH_CARD.getCode(), OrderType.CARD_STYLE_FEE.getCode(),
                        OrderType.APPLY_PERSON_RESOURCE.getCode()}));
    }

    /**
     * 检查互生币的支付限额
     *
     * @param amount   金额
     * @param hsCustId 客户号
     * @Description:
     */
    public void checkOrderAmountIsOverLimit(String amount, String hsCustId) {
        String hsResNo = hsCustId.substring(0, 11);
        // 互生币支付code
        String code = BusinessParam.HSB_PAYMENT.getCode();
        // 客户单天已支付金额code
        String dayPayCountCode = BusinessParam.HAD_PAYMENT_DAY.getCode();
        // 互生币支付单笔限额code
        String codeMin = null;
        // 互生币支付当日限额code
        String codeDayMax = null;
        // 客户单天已支付金额
        String dayPayCount = null;
        if (HsResNoUtils.isPersonResNo(hsResNo)) {
            codeMin = BusinessParam.CONSUMER_PAYMENT_MAX.getCode();
            codeDayMax = BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode();

            BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(hsCustId, code,
                    codeMin);
            // 互生币支付单笔限额
            String amountmin = (null == business || StringUtils.isBlank(business.getSysItemsValue())) ? "0"
                    : business.getSysItemsValue();
            business = businessParamSearch.searchCustParamItemsByIdKey(hsCustId, code, codeDayMax);
            // 互生币支付当日限额code
            String maxAmountMax = (null == business || StringUtils.isBlank(business.getSysItemsValue())) ? "0"
                    : business.getSysItemsValue();
            if (BigDecimalUtils.compareTo(amountmin, "0") > 0) {
                if (BigDecimalUtils.compareTo(amount, amountmin) > 0) {
                    throw new HsException(BSRespCode.BS_HSB_PAY_OVER_LIMIT);
                }
            }
            if (BigDecimalUtils.compareTo(maxAmountMax, "0") > 0) {
                if (BigDecimalUtils.compareTo(amount, maxAmountMax) > 0) {
                    throw new HsException(BSRespCode.BS_HSB_PAY_OVER_LIMIT);
                }
            }
            // 个人客户单天已支付金额
            business = businessParamSearch.searchCustParamItemsByIdKey(hsCustId, code, dayPayCountCode);
            // 参数为空、日期、已发生数量、不是今天的数量
            if (StringUtils.isBlank(business) || StringUtils.isBlank(business.getDueDate())
                    || StringUtils.isBlank(business.getSysItemsValue())
                    || DateUtil.compare_date(DateUtil.DateToString(DateUtil.StringToDate(business.getDueDate())),
                    DateUtil.DateToString(DateUtil.today())) != 0) {
                dayPayCount = "0";
            } else {
                dayPayCount = business.getSysItemsValue();
            }
            if (BigDecimalUtils.compareTo(BigDecimalUtils.ceilingAdd(dayPayCount, amount).toString(), maxAmountMax) > 0) {
                throw new HsException(BSRespCode.BS_HSB_PAY_OVER_LIMIT);
            }
            business.setSysItemsValue(BigDecimalUtils.ceilingAdd(dayPayCount, amount).toString());
            business.setDueDate(DateUtil.DateToString(new Date()));
            businessParamSearch.setBusinessCustParamItemsRed(business);
        }
    }

    /**
     * 测试同步卡数据到UC
     *
     * @param confNo
     * @param entResNo
     * @param operNo
     * @throws HsException
     * @Description:
     */
    @Override
    public void testSyncCardInfoToUc(String confNo, String entResNo, String operNo) throws HsException {
        String inParam = "confNo:" + confNo + "entResNo:" + entResNo + "operNo:" + operNo;
        // 同步卡信息用户中心
        long start = System.currentTimeMillis();
        try {
            bsCardHolderService.batchAddCards(operNo, cardInfoMapper.selectCardInfoByUcSync(confNo), entResNo);
        } catch (HsException ex) {
            throw ex;
        } catch (Exception ex) {
            SystemLog.error(this.getClass().getName(), "function:cardConfigEnter",
                    BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用UC同步互生卡数据失败," + inParam, ex);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "dubbo调用UC同步互生卡数据失败 ," + inParam);
        }
        System.out.println("同步消费者数据到UC消耗时间：" + (System.currentTimeMillis() - start));
    }

    /**
     * 生成企业消费者资源段
     *
     * @param entCustId 客户号
     * @param boughtNum 已购买数量
     * @param orderNum  下单数量
     * @return List<ResSegment>
     */
    @Override
    public EntResSegment createEntResourceSegment(String entCustId, int boughtNum, int orderNum) {
        return this.createResourceSegment(entCustId, boughtNum, orderNum, null);
    }

    /**
     * 生成企业消费者资源段(私有的)
     *
     * @param entCustId 客户号
     * @param boughtNum 已购买数量
     * @param orderNum  下单数量
     * @param confNo  配置单编号
     * @Desc:
     * @author: likui
     * @created: 2016/6/13 17:04
     * @eturn: EntResSegment
     * @copany: gyist
     * @version V3.0.0
     */
    private EntResSegment createResourceSegment(String entCustId, int boughtNum, int orderNum, String confNo) {
        // 总数
        int totalNum = 9999;
        // 没段的起始互生号
        String[] resNos = null;
        // 可以购买数量
        int mayBuyNum = 0;
        // 已经购买段数
        int boughtSegment = 0;
        // 已经下单段数
        int orderSegment = 0;
        // 可以购买段数
        int mayBuySegment = 0;
        // 计算可以购买的数量
        mayBuyNum = totalNum - boughtNum - orderNum;
        // 判断已购买、下单、可以购买那个包含999的数量
        int falg = boughtNum % 1000 != 0 ? 1 : orderNum % 1000 != 0 ? 2 : 3;

        // 计算已经购买段数
        if (boughtNum > 0) {
            boughtSegment = falg != 1 ? boughtNum / 1000 : (boughtNum / 1000) + 1;
        }
        // 计算已经下单段数
        if (orderNum > 0) {
            orderSegment = falg != 2 ? orderNum / 1000 : (orderNum / 1000) + 1;
        }
        // 计算可以购买段数
        if (mayBuyNum > 0) {
            mayBuySegment = falg != 3 ? mayBuyNum / 1000 : (mayBuyNum / 1000) + 1;
        }
        // 获取企业资源段的每段起始互生号
        Map<String, String[]> originResNo = StringUtil.getResSegmentStartAndEndResNo(entCustId.substring(0, 11));

        // 企业系统资源
        EntResSegment bean = new EntResSegment();
        List<ResSegment> segment = new ArrayList<ResSegment>();

        // 设置开始购买段
        bean.setStartBuyRes(boughtSegment + orderSegment + 1);
        // 生成已经购买资源段
        for (int i = 1; i <= boughtSegment; i++) {
            String segmentDesc = i == 1 ? "首" : "第" + i;
            String cardCount = (i == boughtSegment && falg == 1) ? "999" : "1000";
            int count = i;
            if (i == boughtSegment && falg == 1) {
                count = 10;
            }
            resNos = originResNo.get(String.valueOf(count));
            segment.add(new ResSegment(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), entCustId, confNo,
                    count, segmentDesc + "段托管企业系统消费者资源", BuyStatus.BOUGHT.getCode(), bsConfig.getResSegmentPrice(),
                    cardCount, resNos[0], resNos[1]));
        }

        // 生成已经下单的资源段
        for (int i = 1; i <= orderSegment; i++) {
            String cardCount = (i == orderSegment && falg == 2) ? "999" : "1000";
            int count = boughtSegment + i;
            if (i == orderSegment && falg == 2) {
                count = 10;
            }
            resNos = originResNo.get(String.valueOf(falg == 1 ? count - 1 : count));
            segment.add(new ResSegment(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), entCustId,
                    (falg == 1 ? count - 1 : count), "第" + count + "段托管企业系统消费者资源", BuyStatus.ORDER.getCode(),
                    bsConfig.getResSegmentPrice(), cardCount, resNos[0], resNos[1]));
        }

        // 生成可以购买资源段
        for (int i = 1; i <= mayBuySegment; i++) {
            String cardCount = (i == mayBuySegment && falg == 3) ? "999" : "1000";
            int count = boughtSegment + orderSegment + i;
            resNos = originResNo.get(String.valueOf(falg != 3 ? count - 1 : count));
            segment.add(new ResSegment(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), entCustId,
                    (falg != 3 ? count - 1 : count), "第" + count + "段托管企业系统消费者资源", BuyStatus.MAY_BUY.getCode(),
                    bsConfig.getResSegmentPrice(), cardCount, resNos[0], resNos[1]));
        }
        bean.setSegment(segment);
        return bean;
    }
}
