/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCodeMap;
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: PosEngine 
 * @Description: Pos服务请求分发
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:59:32 
 * @version V1.0
 */

@Component("posEngine")
public class PosEngine {
	
	private static Map<String, IBasePos> posStrategyMap = new HashMap<String, IBasePos>();
	
	@Autowired
	@Qualifier("signinAction")
	private SigninAction signinAction;
	
	@Autowired
	@Qualifier("signOffAction")
	private SignOffAction signOffAction;
	
	@Autowired
	@Qualifier("pointAction")
	private PointAction pointAction;
	
	@Autowired
	@Qualifier("pointCancelAction")
	private PointCancelAction pointCancelAction;
	
	@Autowired
	@Qualifier("pointReverseAction")
	private PointReverseAction pointReverseAction;
	
	@Autowired
	@Qualifier("pointSearchAction")
	private PointSearchAction pointSearchAction;
	
	@Autowired
	@Qualifier("singlePointOrderSearchAction")
	private SinglePointOrderSearchAction singlePointOrderSearchAction;
	
	@Autowired
	@Qualifier("paramSyncAction")
	private ParamSyncAction paramSyncAction;
	
	@Autowired
	@Qualifier("paramUploadAction")
	private ParamUploadAction paramUploadAction;
	
	@Autowired
	@Qualifier("hsOrderPayAction")
	private HsOrderPayAction hsOrderPayAction;
	
	@Autowired
	@Qualifier("hsOrderReverseAction")
	private HsOrderReverseAction hsOrderReverseAction;
	
	@Autowired
	@Qualifier("hsOrderSearchAction")
	private HsOrderSearchAction hsOrderSearchAction;
	
	@Autowired
	@Qualifier("hsbEntRechargeAction")
	private HsbEntRechargeAction hsbEntRechargeAction;
	
	@Autowired
	@Qualifier("hsbProxyRechargeAction")
	private HsbProxyRechargeAction hsbProxyRechargeAction;
	
	@Autowired
	@Qualifier("hsbRechargeReverseAction")
	private HsbRechargeReverseAction hsbRechargeReverseAction;
	
	@Autowired
	@Qualifier("hsbPayAction")
	private HsbPayAction hsbPayAction;
	
	@Autowired
	@Qualifier("hsbPayCancelAction")
	private HsbPayCancelAction hsbPayCancelAction;
	
	@Autowired
    @Qualifier("hsbPayReturnAction")
    private HsbPayReturnAction hsbPayReturnAction;
	
	@Autowired
	@Qualifier("hsbPayReverseAction")
	private HsbPayReverseAction hsbPayReverseAction;
	
	
	@Autowired
	@Qualifier("batchSettleAction")
	private BatchSettleAction batchSettleAction;
	
	@Autowired
	@Qualifier("batchUploadAction")
	private BatchUploadAction batchUploadAction;
	
	@Autowired
	@Qualifier("accSearchAction")
	private AccSearchAction accSearchAction;
	
	@Autowired
	@Qualifier("cardInfoSearchAction")
	private CardInfoSearchAction cardInfoSearchAction;
	
	@Autowired
	@Qualifier("cardCheckAction")
	private CardCheckAction cardCheckAction;
	
	
	@Autowired
	@Qualifier("earnestPrepayAction")
	private EarnestPrepayAction earnestPrepayAction;
	
	@Autowired
	@Qualifier("earnestSettleAccAction")
	private EarnestSettleAccAction earnestSettleAccAction;
	
	@Autowired
	@Qualifier("earnestSearchAction")
	private EarnestSearchAction earnestSearchAction;
	
	@Autowired
	@Qualifier("earnestCancelAction")
	private EarnestCancelAction earnestCancelAction;
	
	@Autowired
	@Qualifier("earnestReverseAction")
	private EarnestReverseAction earnestReverseAction;
	
	@Autowired
	@Qualifier("posUpgradeCheckAction")
	private PosUpgradeCheckAction posUpgradeCheckAction;	
	
	//start--added by liuzh on 2016-06-23
	@Autowired
	@Qualifier("pointOrdersSearchAction")
	private PointOrdersSearchAction pointOrderSearchAction;
	//end--added by liuzh on 2016-06-23
	
	@PostConstruct
	public void init(){
		SystemLog.debug("PosEngine", "init()", "entering method");

		//管理类
	    
	    //签到
		posStrategyMap.put(SigninAction.reqType, signinAction);
		//签退
		posStrategyMap.put(SignOffAction.reqType, signOffAction);
		//参数同步
		posStrategyMap.put(ParamSyncAction.reqType, paramSyncAction);
		//参数上传
		posStrategyMap.put(ParamUploadAction.reqType, paramUploadAction);
		
		//积分相关
		//消费积分
		posStrategyMap.put(PointAction.reqType, pointAction);
		//消费积分撤单
		posStrategyMap.put(PointCancelAction.reqType, pointCancelAction);
		//消费积分冲正
		posStrategyMap.put(PointReverseAction.reqType, pointReverseAction);
		//消费积分撤单冲正
		posStrategyMap.put(PosConstant.REQ_POINT_CANCEL_REVERSE_ID, pointReverseAction);
		//消费积分查询（当日查询，实际上没有该功能）
		posStrategyMap.put(PointSearchAction.reqType, pointSearchAction);
		//消费积分查询（单笔查询）
		posStrategyMap.put(SinglePointOrderSearchAction.reqType, singlePointOrderSearchAction);
		
		//HS 订单
		//互生订单支付 互生币
		posStrategyMap.put(HsOrderPayAction.reqType, hsOrderPayAction);
		//互生订单支付 现金
		posStrategyMap.put(PosConstant.REQ_HSORDER_CASH_PAY_ID, hsOrderPayAction);
		//互生订单支付冲正 互生币
		posStrategyMap.put(HsOrderReverseAction.reqType, hsOrderReverseAction);
		//互生订单支付冲正 现金
		posStrategyMap.put(PosConstant.REQ_HSORDER_CASH_PAY_REVERSE_ID, hsOrderReverseAction);
		//互生订单查询 列表查询
		posStrategyMap.put(HsOrderSearchAction.reqType, hsOrderSearchAction);
		//互生订单查询 单笔查询
		posStrategyMap.put(PosConstant.REQ_HSORDER_SEARCH_ID, hsOrderSearchAction);
		
		//HSB 充值
		//企业兑换互生币
		posStrategyMap.put(HsbEntRechargeAction.reqType, hsbEntRechargeAction);
		//企业代兑互生币
		posStrategyMap.put(HsbProxyRechargeAction.reqType, hsbProxyRechargeAction);
		//企业代兑互生币冲正
		posStrategyMap.put(HsbRechargeReverseAction.reqType, hsbRechargeReverseAction);
		//企业兑换互生币冲正
		posStrategyMap.put(PosConstant.REQ_HSB_ENT_RECHARGE_REVERSE_ID, hsbRechargeReverseAction);
		
		//HSB支付
		//互生币支付
		posStrategyMap.put(HsbPayAction.reqType, hsbPayAction);
		//互生币支付撤单
		posStrategyMap.put(HsbPayCancelAction.reqType, hsbPayCancelAction);
		//互生币支付退货
		posStrategyMap.put(PosConstant.REQ_HSB_PAY_RETURN_ID, hsbPayReturnAction);
		//互生币支付冲正
		posStrategyMap.put(HsbPayReverseAction.reqType, hsbPayReverseAction);
		//互生币支付退货冲正
		posStrategyMap.put(PosConstant.REQ_HSB_PAY_RETURN_REVERSE_ID, hsbPayReverseAction);
		//互生币支付撤单冲正
		posStrategyMap.put(PosConstant.REQ_HSB_PAY_CANCEL_REVERSE_ID, hsbPayReverseAction);
		
		//批结算
		posStrategyMap.put(BatchSettleAction.reqType, batchSettleAction);
		//批上传
		posStrategyMap.put(BatchUploadAction.reqType, batchUploadAction);
		//账户查询
		posStrategyMap.put(AccSearchAction.reqType, accSearchAction);
		//卡信息查询
		posStrategyMap.put(CardInfoSearchAction.reqType, cardInfoSearchAction);
		//卡信息检查
		posStrategyMap.put(CardCheckAction.reqType, cardCheckAction);
		
		/**定金业务*/
		//预付定金
		posStrategyMap.put(EarnestPrepayAction.reqType, earnestPrepayAction);
		//定金结算
		posStrategyMap.put(EarnestSettleAccAction.reqType, earnestSettleAccAction);
		//定金撤销
		posStrategyMap.put(EarnestCancelAction.reqType, earnestCancelAction);
		//定金业务检索
		posStrategyMap.put(EarnestSearchAction.reqType, earnestSearchAction);		
		//预付定金冲正
		posStrategyMap.put(EarnestReverseAction.reqType, earnestReverseAction);
		//定金结算冲正
		posStrategyMap.put(PosConstant.REQ_EARNEST_SETTLE_REVERSE_ID, earnestReverseAction);
		//定金撤销冲正
		posStrategyMap.put(PosConstant.REQ_EARNEST_CANCEL_REVERSE_ID, earnestReverseAction);
		//固件升级版本检测
		posStrategyMap.put(PosConstant.REQ_POS_UPGRADE_CHECK_ID, posUpgradeCheckAction);
		
		//start--added by liuzh on 2016-06-23
		//积分卡可撤单积分交易单
		posStrategyMap.put(PosConstant.REQ_POINT_ORDER_CANCEL_SEARCH_ID, pointOrderSearchAction);
		//积分卡可退货积分交易单
		posStrategyMap.put(PosConstant.REQ_POINT_ORDER_BACK_SEARCH_ID, pointOrderSearchAction);
		//end--added by liuzh on 2016-06-23
		
		//错误码初始化
		PosRespCodeMap.init();
	}

	public void addPosBus(IBasePos posBus)
	{
		posStrategyMap.put(posBus.getReqType(), posBus);
	}
	
	
	public IBasePos getPosBus(String reqType)
	{
		return posStrategyMap.get(reqType);
	}
}
