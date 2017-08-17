/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.receivingManage;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.common.IPubParamService;
import com.gy.hsxt.access.web.bean.reveivingManage.ApsOrderColoseBean;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.api.apply.IBSDebtOrderService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.api.tempacct.IBSTempAcctService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrderQuery;
import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.api.IRPPaymentManagementService;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 订单服务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.receivingManage
 * @ClassName: OrderServiceImpl
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-11 下午12:10:33
 * @version V1.0
 */
@Service("iOrderService")
public class OrderServiceImpl extends BaseServiceImpl implements IOrderService {

    @Resource
    private IBSOrderService BSOrderService;

    @Resource
    private IBSAnnualFeeService ibsAnnualFeeService;

    @Resource
    private IBSDebtOrderService ibsDebtOrderService;

    @Resource
    private IUCLoginService ucLoginService;

    @Resource
    private IBSToolOrderService ibSToolOrderService;
    
    @Resource
    private IRPPaymentManagementService irpPaymentManagementService;
    
    @Resource
    private IBSTempAcctService BSTempAcctService;
    
    @Resource
	private IPubParamService pubParamService;// 查询本平台信息
    
    @Resource
    private IUCAsCardHolderService ucAsCardHolderService;//持卡人
    
    @Resource
    private IUCAsNetworkInfoService ucAsNetworkInfoService;//非持卡人
    
    /***
     * 业务订单分页(包括 网银支付兑换互生币、货币支付兑换互生币)
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#businessAllOrderPage(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<?> businessAllOrderPage(Map filterMap, Map sortMap, Page page)
    		throws HsException {
    	 // 1、构建查询条件
    	PaymentManagementOrder oqp = bulidQueryCondition(filterMap);
    	 // 2、查询分页结果
    	PageData<?> orderPG = irpPaymentManagementService.getPaymentOrderList(oqp, page);
    	return orderPG;
    }
    /**
     * 业务订单分页条件
     * @param filter
     * @return
     */
    private PaymentManagementOrder bulidQueryCondition(Map filter) {
        PaymentManagementOrder retOQP = new PaymentManagementOrder();

        retOQP.setHsResNo((String) filter.get("hsResNo"));
        retOQP.setOrderType((String) filter.get("orderType"));
        retOQP.setCustName((String) filter.get("custNameCondition"));
        retOQP.setPayChannel(CommonUtils.toInteger(filter.get("payChannel")));
        retOQP.setPayStatus(CommonUtils.toInteger(filter.get("payStatus")));
        retOQP.setOrderStatus(CommonUtils.toInteger(filter.get("orderStatus")));
        retOQP.setUnPayStatus((String) filter.get("unPayStatus"));
        
        //订单开始-结束日期
        retOQP.setStartDate((String) filter.get("startDate"));
        retOQP.setEndDate((String) filter.get("endDate"));
        
        //收款开始-结束日期
        retOQP.setPayStartTime((String) filter.get("payStartTime"));
        retOQP.setPayEndTime((String) filter.get("payEndTime"));
        
        return retOQP;
    }

    /**
     * 根据订单号查找明细
     * 
     * @param orderNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#getOrderDetail(java.lang.String)
     */
    @Override
    public PaymentManagementOrder getOrderDetail(String orderNo) throws HsException {
        // 1、获取订单明细                                                                 
        PaymentManagementOrder order = irpPaymentManagementService.getPaymentOrderInfo(orderNo);

        // 2、空对象验证
        if (order == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }

        return order;
    }

    /**
     * 订单关闭
     * 
     * @param aocb
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#closeOrder(com.gy.hsxt.access.web.bean.reveivingManage.ApsOrderColoseBean)
     */
    @Override
    public void closeOrder(ApsOrderColoseBean aocb) {
        // 根据订单类型判断关闭方法
        if (OrderType.checkIsToolOrder(aocb.getOrderType())){ // 工具订单
            ibSToolOrderService.closeOrWithdrawalsToolOrder(aocb.getOrderNo());
        }
      /*  else{ // 订单关闭
            BSOrderService.closeOrder(aocb.getOrderNo());
        }*/
    }

    /**
     * 分页查询年费业务单
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#queryAnnualFeeOrderForPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<?> queryAnnualFeeOrderForPage(Map filterMap, Map sortMap, Page page) throws HsException {
        // 1、构建查询对象
        AnnualFeeOrderQuery afoq = createAFOQ(filterMap);
        
        //20160423 只查询代付款状态信息
        afoq.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
        
        // 2、分页查询
        return ibsAnnualFeeService.queryAnnualFeeOrderForPage(page, afoq);
    }

    /**
     * 取年费订单详情
     * 
     * @param orderNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#queryAnnualFeeOrder(java.lang.String)
     */
    @Override
    public AnnualFeeOrder queryAnnualFeeOrder(String orderNo) throws HsException {
        // 1、查找明细
        AnnualFeeOrder afo = ibsAnnualFeeService.queryAnnualFeeOrder(orderNo);

        // 2、判断空数据
        if (afo == null)
        {
            throw new HsException(RespCode.GL_DATA_NOT_FOUND.getCode(), RespCode.GL_DATA_NOT_FOUND.getDesc());
        }

        return afo;
    }

    /**
     * 构建年费业务查询实体
     * 
     * @return
     */
    AnnualFeeOrderQuery createAFOQ(Map filter) {
        AnnualFeeOrderQuery afoq = new AnnualFeeOrderQuery();
       
        afoq.setResNo((String) filter.get("resNoCondition"));
        afoq.setCustName((String) filter.get("custNameCondition"));
        afoq.setStartDate((String) filter.get("startDate"));
        afoq.setEndDate((String) filter.get("endDate"));

        return afoq;
    }

    /**
     * 申报欠款单分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#queryDebtOrder(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<?> queryDebtOrder(Map filterMap, Map sortMap, Page page) throws HsException {
        // 1、查询条件
        DebtOrderParam dop = createDebtOrderParam(filterMap);
        // 2、分页结果
        return ibsDebtOrderService.queryDebtOrder(dop, page);
    }
    
    /**
     * 业务订单导出
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#exportBusinessOrder(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public String exportBusinessOrder(Map filterMap) throws HsException {
        //构造查询类
        PaymentManagementOrder oqp = bulidQueryCondition(filterMap);
        //返回下载地址
        return irpPaymentManagementService.exportPaymentOrderData(oqp);
    }

    /**
     * 创建申报欠款单查询条件类
     * 
     * @param filter
     * @return
     */
    public DebtOrderParam createDebtOrderParam(Map filter) {
        DebtOrderParam dop = new DebtOrderParam();

        dop.setEntResNo((String) filter.get("resNoCondition"));
        dop.setEntName((String) filter.get("custNameCondition"));
        dop.setStartDate((String) filter.get("startDate"));
        dop.setEndDate((String) filter.get("endDate"));
        return dop;
    }
    
    /**
     * 获取临帐支付详情
     * @param orderNo
     * @return 
     * @see com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService#getTempAcctPayInfo(java.lang.String)
     */
    @Override
    public Order getTempAcctPayInfo(String orderNo) {
        // 获取临帐订单信息
        return BSTempAcctService.queryTempSuccessOrderByOrderNo(orderNo);
    }
    
    /***
     * 查询操作员信息
     * @param orderCustType 类型
     * @param orderOperator 操作员编号
     * @return
     */
    public AsOperator findOrderOperator(String orderCustType, String orderOperator) {
    	try {
			if("51".equals(orderCustType)){//非持卡人
				AsNetworkInfo obj = this.ucAsNetworkInfoService.searchByCustId(orderOperator);
				if(obj != null){
					return newAsOperator(obj.getMobile(), obj.getName(), orderOperator);
				}
			}else if("1".equals(orderCustType)){//持卡人
				AsCardHolderAllInfo obj = this.ucAsCardHolderService.searchAllInfo(orderOperator);
				if(obj != null){
					return newAsOperator(obj.getCardInfo().getPerResNo(), obj.getAuthInfo().getUserName(), orderOperator);
				}
			}else{//内部操作员
				AsOperator obj = this.pubParamService.searchOperByCustId(orderOperator);
				if(obj != null){
					return newAsOperator(obj.getUserName(), obj.getRealName(), orderOperator);
				}
			}
			return newAsOperator(orderOperator, "", orderOperator);
		} catch (HsException e) {
			return newAsOperator(orderOperator, "", orderOperator);
		}
    }
    
    /**
     * 构建操作员信息
     * @param userName
     * @param realName
     * @return
     */
    public AsOperator newAsOperator(String userName, String realName, String orderOperator){
    	if(StringUtils.isBlank(userName) && StringUtils.isBlank(realName)){
    		realName = orderOperator;
    	}
    	AsOperator as = new AsOperator();
    	as.setUserName(userName);
    	as.setRealName(realName);
    	return as;
    }

}
