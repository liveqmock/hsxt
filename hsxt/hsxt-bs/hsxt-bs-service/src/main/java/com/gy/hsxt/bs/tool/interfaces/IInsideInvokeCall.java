/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.interfaces;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.tool.EntResSegment;
import com.gy.hsxt.bs.bean.tool.ResSegment;
import com.gy.hsxt.bs.tool.bean.ApplyOrderConfig;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 内部调用接口
 *
 * @Package: com.gy.hsxt.bs.tool.interfaces
 * @ClassName: IInsideInvokeCall
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月12日 下午3:24:02
 * @company: gyist
 * @version V3.0.0
 */
public interface IInsideInvokeCall {

	/**
	 * 地区平台配额审批
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月8日 上午10:52:32
	 * @param bean
	 *            地区平台配额实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void apprPlatQuota(PlatQuotaApp bean) throws HsException;

	/**
	 * 增加申报工具配置
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月15日 下午3:18:13
	 * @param bean
	 *            申报工具下单实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addApplyOrderToolConfig(ApplyOrderConfig bean) throws HsException;

	/**
	 * 工具订单关闭
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月15日 下午4:40:25
	 * @param bean
	 *            订单实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolOrderClose(Order bean) throws HsException;

	/**
	 * 工具订单撤单
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月7日 下午4:16:53
	 * @param bean
	 *            订单实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolOrderWithdrawals(Order bean) throws HsException;

	/**
	 * 工具网银或临账支付成功
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 下午6:54:27
	 * @param bean
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolEbankOrTempAcctPaySucc(Order bean, String bankTransNo) throws HsException;

	/**
	 * 工具订单互生币支付记账
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 下午2:48:04
	 * @param bean
	 *            订单实体对象
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolOrderHsbPayAccount(Order bean) throws HsException;

	/**
	 * 查询企业未完成的工具订单
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月5日 下午3:21:43
	 * @param entCustId
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public boolean queryNotFinishToolOrder(String entCustId);

	/**
	 * 检查互生币的支付限额
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月12日 上午11:50:58
	 * @param amount
	 *            金额
	 * @param entCustId
	 *            客户号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void checkOrderAmountIsOverLimit(String amount, String entCustId) throws HsException;

	/**
	 * 测试同步卡数据到UC
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月23日 下午4:20:28
	 * @param confNo
	 *            配置单
	 * @param entResNo
	 *            互生号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void testSyncCardInfoToUc(String confNo, String entResNo, String operNo) throws HsException;

	/**
	 * 生成企业消费者资源段
     *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/8 18:09
	 * @param: entCustId 客户好
	 * @eturn: boughtNum 已经购买数量
	 * @eturn: mayBuyNum 还可以购买数量
	 * @return : EntResSegment
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public EntResSegment createEntResourceSegment(String entCustId, int boughtNum, int mayBuyNum);

}
