/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointRecordResult;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.PosEarnest;
import com.gy.hsxt.ps.bean.QueryPointRecord;
import com.gy.hsxt.ps.bean.QueryPosSingle;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.QuerySingle;
import com.gy.hsxt.ps.bean.ReturnResult;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: PsApiService 
 * @Description: 积分接口封装
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:14:16 
 * @version V1.0
 */
public interface PsApiService {
	/**
	 * 消费积分
	 * @param point
	 * @return
	 * @throws HsException
	 */
	public PointResult point(Point point) throws HsException;
    
	/**
	 * 积分撤单
	 * @param cancel
	 * @return
	 * @throws HsException
	 */
	public CancelResult cancelpoint(Cancel cancel) throws HsException;
	/**
	 * 积分冲正
	 * @param correct
	 * @throws HsException
	 */
	public ReturnResult reversePoint (Correct correct) throws HsException;
	
	/**
	 * 退货
	 * @param back
	 * @return
	 * @throws HsException
	 */
	public  BackResult backPoint (Back back) throws HsException;
	/**
	 * 积分单笔查询
	 * @param transNo
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public QueryResult singlePosQuery (QuerySingle qs) throws HsException;
	/**
	 * 批结算
	 * @param batSettle
	 * @throws HsException
	 */
	public void batSettle (BatSettle batSettle) throws HsException;
	/**
	 * 批上传
	 * @param list
	 * @throws HsException
	 */
	public void batUpload(List<BatUpload> list) throws HsException;

	/**
	 * 检索指定消费者生效中的定金单据
	 * @param querySingle
	 * @param page
	 * @return
	 * @throws HsException
	 */
	PageData<PosEarnest> searchPosEarnest(QuerySingle querySingle, Page page)throws HsException;
	
	
	
	/**
	 * 定金结单
	 * @param point
	 * @return
	 * @throws HsException
	 */
	PointResult earnestSettle(Point point) throws HsException;

	/**
	 * pos机扫描交易单据码后可直接查询该交易结果，此时没有订单号，只根据企业互生号、pos机编号、批次号、pos机流水号、交易时间定位该笔交易
	 * @param queryPosSingle
	 * @return
	 * @throws HsException
	 */
	QueryResult singlePosQuery(QueryPosSingle queryPosSingle)throws HsException;

	//start--added by liuzh on 2016-06-23
	/**
	 * 查询可撤单或可退货的积分交易记录
	 * @param queryPointRecord
	 * @return
	 * @throws HsException
	 */
	List<PointRecordResult> pointRecord(QueryPointRecord queryPointRecord) throws HsException;
	//end--added by liuzh on 2016-06-23
}

	