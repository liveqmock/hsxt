/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.bean.*;

/**
 * @Package: com.gy.hsxt.es.api
 * @ClassName: IPsPointService
 * @Description: 消费积分接口
 * 
 * @author: chenhongzhi
 * @date: 2015-10-13 上午9:48:38
 * @version V3.0
 */

public interface IEsPointService
{
	// 消费积分预留
	public List<PointResult> reservedPoint(List<Point> point) throws HsException;
	
	// 消费积分结单
	public List<PointResult> statementPoint(List<Point> point) throws HsException;
	
	// 货到付款
	public PointResult point(Point point) throws HsException; 

	// 退货(全部退、部分退)
	public BackResult backPoint(Back back) throws HsException;

	// 撤单
	public CancelResult cancelPoint(Cancel cancel) throws HsException;
	
	// 冲正
	public void returnPoint(Correct correct) throws HsException;

	//积分，撤单，退货组合接口。
	public List<BonusPointsResult>  bonusPoints(List<BonusPoints> list) throws HsException;
	
	// 退货退积分
	public BackResult returnBackPoint(Back back) throws HsException;
	
	// 退货退款
	public BackResult returnBackAmount(Back back) throws HsException;
	
	// 批量预扣结单、批量确认收货
	// public void batchConfirmReceipt() throws HsException;
}
