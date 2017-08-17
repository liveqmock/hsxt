/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.api;

import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.common.exception.HsException;

/**
 * 货币转换接口定义
 * 
 * @Package: com.gy.hsxt.ao.api
 * @ClassName: IAOCurrencyConvertService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 上午11:47:50
 * @version V3.0.0
 */
public interface IAOCurrencyConvertService {

    /**
     * 保存积分转互生币
     * 
     * @param pvToHsb
     *            积分转互生币信息
     * @return 交易流水号
     * @throws HsException
     */
    public String savePvToHsb(PvToHsb pvToHsb) throws HsException;

    /**
     * 销户积分转互生币
     * 
     * @param pvToHsb
     *            积分转互生币信息
     * @throws HsException
     */
    public void closeAccountPvToHsb(PvToHsb pvToHsb) throws HsException;

    /**
     * 保存互生币转货币
     * 
     * @param transNo
     *            交易流水号
     * @return 交易流水号
     * @throws HsException
     */
    public String saveHsbToCash(HsbToCash hsbToCash) throws HsException;

    /**
     * 销户互生币转货币
     * 
     * @param hsbToCash
     *            互生币转货币信息
     * @throws HsException
     */
    public void closeAccountHsbToCash(HsbToCash hsbToCash) throws HsException;

    /**
     * 查询积分转互生币
     * 
     * @param transNo
     *            交易流水号
     * @return 积分转互生币详情
     * @throws HsException
     */
    public PvToHsb getPvToHsbInfo(String transNo) throws HsException;

    /**
     * 查询互生币转货币
     * 
     * @param transNo
     *            交易流水号
     * @return 互生币转货币信息
     * @throws HsException
     */
    public HsbToCash getHsbToCashInfo(String transNo) throws HsException;
}
