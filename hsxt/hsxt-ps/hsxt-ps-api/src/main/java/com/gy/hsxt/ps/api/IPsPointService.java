/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.api;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.ReturnResult;

/**
 * @Package: com.gy.hsxt.ps.api
 * @ClassName: IPsPointService
 * @Description: 积分/互生币服务接口
 * 
 * @author: chenhongzhi
 * @date: 2015-10-13 上午9:48:38
 * @version V3.0
 */

public interface IPsPointService
{
	// 消费积分
	PointResult point(Point point) throws HsException;
	
	// 消费积分含跨地区交易
	PointResult point(Point point, JSONObject cardParams) throws HsException;

	// 定金结单
	PointResult earnestSettle(Point point) throws HsException;

	// 网上积分登记
	PointResult pointRegister(Point point) throws HsException;

	// 退货
	BackResult backPoint(Back back) throws HsException;

	// 撤单
	CancelResult cancelPoint(Cancel cancel) throws HsException;

	// 冲正
	ReturnResult returnPoint(Correct correct) throws HsException;
	
	/**
     * 消费积分网银支付生成交易单接口
     *
     * @param point 积分入参对象
     * @return PointResult 返回值
     */
    PointResult pointBanking(Point point) throws HsException;

    /**
     * 获取快捷支付短信验证码
     * @param pointResult
     * @throws HsException
     */
    public void pointQuickPaySmsCode(PointResult pointResult) throws HsException;
    
    /**
     * 验证手机短信验证码
     * @param pointResult
     * @return 
     * @throws HsException
     */
    public String pointQuickPayUrl(PointResult pointResult) throws HsException;
}
