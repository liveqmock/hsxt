/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.accountManagement;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;


/***************************************************************************
 * <PRE>
 * Description 		: 积分账户服务操作类
 *
 * Project Name   	: hsxt-access-web-person 
 *
 * Package Name     : com.gy.hsxt.access.web.person.services.accountManagement  
 *
 * File Name        : IInvestmentAccountService 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-12-29 下午4:10:16
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-12-29 下午4:10:16
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/
@Service
public interface IInvestmentAccountService extends IBaseService{

    /**
     * 查询积分投资分红信息
     * @param hsResNo       互生号
     * @param dividendYear  年份
     * @return
     * @throws HsException
     */
    public CustomPointDividend findInvestDividendInfo(String hsResNo,String dividendYear) throws HsException;
  
    /**
     * 查询积分投资列表
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<PointInvest> pointInvestPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 查询积分投资分红列表
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<PointDividend> pointDividendPage(Map filterMap, Map sortMap, Page page) throws HsException;
  
    /**
     * 查询积分投资分红计算明细
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<DividendDetail> dividendDetailPage(Map filterMap, Map sortMap, Page page) throws HsException;
    
}
