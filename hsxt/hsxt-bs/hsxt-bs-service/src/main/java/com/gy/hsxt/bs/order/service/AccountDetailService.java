/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.TransCodeUtil;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 记帐分解service
 * 
 * @Package: com.gy.hsxt.bs.order.service
 * @ClassName: AccountDetailService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-4 下午4:58:40
 * @version V3.0.0
 */
@Service(value = "accountDetailService")
public class AccountDetailService implements IAccountDetailService {

    /** 业务服务私有配置参数 **/
    @Autowired
    BsConfig bsConfig;

    // 注入记帐分解mapper
    @Autowired
    AccountDetailMapper accountDetailMapper;

    // 注入订单service
    @Autowired
    IOrderService orderService;

    // 远程调用service
    @Autowired
    InvokeRemoteService invokeRemoteService;

    // 公共service
    @Autowired
    ICommonService commonService;

    /**
     * 保存通用记帐分解
     * 
     * @param accountDetails
     *            记帐分解信息列表
     * @param bizName
     *            业务名称
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IAccountDetailService#saveGenActDetail(List,
     *      String, boolean)
     */
    @Override
    @Transactional
    public void saveGenActDetail(List<AccountDetail> accountDetails, String bizName, boolean note) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "保存"
                + bizName + "记帐分解,params[" + JSON.toJSONString(accountDetails) + ",bizName:" + bizName + ",note:"
                + note + "]");
        try
        {
            // 分解列表为空
            HsAssert.notNull(accountDetails, BSRespCode.BS_PARAMS_NULL, "保存" + bizName + "记帐分解：分解列表为空");
            for (AccountDetail accountDetail : accountDetails)
            {
                if (StringUtils.isBlank(accountDetail.getAccountNo()))
                {
                    accountDetail.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                }
            }
            // 保存记帐分解
            accountDetailMapper.bathcInsertAccountDetail(accountDetails);
            // 获取本次记帐状态：true实时记帐，false非实时
            if (note)
            {
                // 调用AC实时记帐
                invokeRemoteService.actualAccount(accountDetails);
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
            // 记录错误日志
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_ACT_DETAIL_ERROR
                    .getCode()
                    + ":保存" + bizName + "记帐分解异常", e);
            throw new HsException(BSRespCode.BS_ACT_DETAIL_ERROR.getCode(), "保存" + bizName + "记帐分解异常" + e);
        }
    }

    /**
     * 实现保存兑换互生币临帐记帐分解内部接口
     * 
     * @param orderNo
     *            订单号
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IAccountDetailService#saveTempActDetail(java.lang.String)
     */
    @Override
    @Transactional
    public void saveTempActDetail(String orderNo) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "临帐记帐分解,params[orderNo:" + orderNo + "]");
        // 检验订单号
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "临帐记帐分解：订单号参数为空");
        try
        {
            // 查询订单
            Order order = orderService.getOrderByNo(orderNo);

            if (order != null)
            {
                // 临帐支付
                if (order.getPayChannel() == PayChannel.TRANSFER_REMITTANCE.getCode().intValue())
                {
                    // 获取交易码
                    String transCode = TransCodeUtil.getOrderTransCode(order.getOrderType(), order.getCustType(), order
                            .getPayChannel());
                    if (order.getOrderType().equals(OrderType.BUY_HSB.getCode()))
                    {
                        // 临帐支付方式
                        payChannelByTemp(order, transCode);
                    }
                }
            }
        }
        catch (Exception e)
        {
            SystemLog.error(bsConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_ACT_DETAIL_ERROR
                    .getCode()
                    + ":兑换互生币临帐保存记帐分解异常,params[orderNo:" + orderNo + "]", e);
            throw new HsException(BSRespCode.BS_ACT_DETAIL_ERROR.getCode(), "兑换互生币临帐保存记帐分解异常,params[orderNo:" + orderNo
                    + "]" + e);
        }
    }

    /**
     * 临帐支付方式
     * 
     * @param order
     *            订单对象
     * @param transCode
     *            记帐分解对象
     */
    @Transactional
    private void payChannelByTemp(Order order, String transCode) {
        // 记帐分解LIST
        List<AccountDetail> actDetails = new ArrayList<AccountDetail>();
        // 记帐分解对象
        AccountDetail detail = null;

        /******************************* 互生币帐户增加 ***************************************/
        // 实例化记帐分解对象
        detail = new AccountDetail(//
                GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), // 记帐流水号
                order.getOrderNo(),// 交易流水号
                transCode,// 设置交易类型
                order.getCustId(), // 客户号
                order.getHsResNo(), // 互生号
                order.getCustName(), // 客户名称
                order.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通互生币帐户
                order.getOrderCashAmount(), // 增金额
                "0",// 减金额
                order.getCurrencyCode(),// 货币币种
                DateUtil.getCurrentDateTime(),// 记帐时间
                "兑换互生币业务：临帐支付方式互生币帐户增加金额", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台临账收款(本币)帐户增加 ***************************************/
        // 实例化记帐分解对象
        detail = new AccountDetail(//
                GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), // 记帐流水号
                order.getOrderNo(),// 交易流水号
                transCode,// 设置交易类型
                commonService.getAreaPlatCustId(), // 客户号
                commonService.getAreaPlatInfo().getPlatResNo(), // 互生号
                commonService.getAreaPlatInfo().getPlatNameCn(), // 客户名称
                CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
                AccountType.ACC_TYPE_POINT50210.getCode(), // 帐户类型:50210平台临账收款(本币)帐户
                order.getOrderCashAmount(), // 增金额
                "0",// 减金额
                order.getCurrencyCode(),// 货币币种
                DateUtil.getCurrentDateTime(),// 记账时间
                "兑换互生币业务：临帐支付方式平台临账收款(本币)帐户增加金额", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        try
        {
            // 批量新增网银兑换互生币分解记录
            accountDetailMapper.bathcInsertAccountDetail(actDetails);
            // 调用帐务系统记帐
            invokeRemoteService.actualAccount(actDetails);
        }
        catch (Exception e)
        {
            SystemLog.error(bsConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_ACT_DETAIL_ERROR
                    .getCode()
                    + ":兑换互生币业务：临帐支付方式记帐时异常", e);
            throw new HsException(BSRespCode.BS_ACT_DETAIL_ERROR.getCode(), "兑换互生币业务：临帐支付方式记帐时异常");
        }
    }
}
