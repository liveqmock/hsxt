/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.accountManagement;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.bean.accountManagement.NetPay;
import com.gy.hsxt.access.web.person.bean.accountManagement.PayLimit;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;

/***
 * 互生币账户接口类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.accountManagement
 * @ClassName: IHsbAccountService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-4-14 下午12:27:43
 * @version V1.0
 */
@Service
public interface IHsbAccountService extends IBaseService {
    
    /**
     * 查询余额信息
     * 
     * @param personBase
     * @return
     * @throws HsException
     */
    public Map<String, Object> findHsbBlance(PersonBase personBase) throws HsException;

    /**
     * 初始化互生币转货币
     * 
     * @param personBase
     * @return
     * @throws HsException
     */
    public Map<String, Object> initHsbTransferCurrency(PersonBase personBase) throws HsException;

    /**
     * 互生币转货币
     * 
     * @param hsbToCash
     * @param tradePwd
     * @param randomToken
     * @throws HsException
     */
    public void hsbTransferCurrency(HsbToCash hsbToCash, String tradePwd, String randomToken,String hs_isCard) throws HsException;

    /**
     * 初始化兑换互生币
     * 
     * @param perCustId
     * @return
     * @throws HsException
     */
    public Map<String, Object> initTransferHsb(String perCustId,String hs_isCard) throws HsException;

    /**
     * 兑换互生币提交数据
     * 
     * @param order
     * @param tradePwd
     * @param randomToken
     * @return
     * @throws HsException
     */
    public String transferHsb(Order order) throws HsException;

    /**
     * 支付限额设置查询
     * 
     * @param personBase
     * @param entResType
     * @return
     * @throws HsException
     */
    public Map<String, Object> payLimitSetting(PersonBase personBase) throws HsException;

    /**
     * 支付限额修改
     * @param payLimit
     * @throws HsException
     */
    public void payLimitUpdate(PayLimit payLimit,String hs_isCard) throws HsException;
    
    /**
     * 兑换互生币支付
     * @param netPay
     * @return
     * @throws HsException
     */
    public String convertHSBPay(NetPay netPay) throws HsException;
}
