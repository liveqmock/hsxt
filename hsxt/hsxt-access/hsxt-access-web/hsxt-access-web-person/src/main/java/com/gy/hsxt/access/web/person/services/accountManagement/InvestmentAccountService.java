/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.accountManagement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 * Description 		: 投资账户的操作类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.services.accountManagement  
 * 
 * File Name        : InvestmentAccountService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-12-29 下午4:11:33
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-12-29 下午4:11:33
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class InvestmentAccountService extends BaseServiceImpl implements IInvestmentAccountService {

    /**
     * 积分投资分红信息dubbo
     */
    @Autowired
    private IBSInvestProfitService bsInvestProfitService;

    /**
     * 查询积分投资分红信息
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            年份
     * @return
     * @throws HsException
     */
    @Override
    public CustomPointDividend findInvestDividendInfo(String hsResNo, String dividendYear) throws HsException {
        return this.bsInvestProfitService.getInvestDividendInfo(hsResNo, dividendYear);
    }

    /**
     * 查询积分投资列表
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IInvestmentAccountService#pointInvestPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<PointInvest> pointInvestPage(Map filterMap, Map sortMap, Page page) throws HsException {
        // 调用接口查询
        return bsInvestProfitService.getPointInvestList((String) filterMap.get("custId"), (String) filterMap
                .get("beginDate"), (String) filterMap.get("endDate"), page);
    }

    /**
     * 查询积分投资分红列表
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IInvestmentAccountService#pointDividendPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<PointDividend> pointDividendPage(Map filterMap, Map sortMap, Page page) throws HsException {
        // 调用接口查询
    	String custId=(String) filterMap.get("custId");
        return bsInvestProfitService.getPointDividendList(custId, (String) filterMap
                .get("beginDate"), (String) filterMap.get("endDate"), page);
    }

    /**
     * 查询积分投资分红计算明细
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IInvestmentAccountService#dividendDetailPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<DividendDetail> dividendDetailPage(Map filterMap, Map sortMap, Page page) throws HsException {
        // 调用分红明细接口
    	String hsResNo=(String) filterMap.get("hsResNo");
    	return bsInvestProfitService.getDividendDetailList(hsResNo, (String) filterMap
                .get("year"), page);
    }
    /**
     * 查询积分投资分红计算明细统计
     * 
     * @param hsResNo
     * @param hsResNo
     * @return
     * @throws HsException
     */
    @Override
    public CustomPointDividend  getInvestDividendInfo(String hsResNo,String year) throws HsException {
        // 调用分红明细接口
    	 //hsResNo="06002111722";
    	return bsInvestProfitService.getInvestDividendInfo(hsResNo, year);
    }
}
