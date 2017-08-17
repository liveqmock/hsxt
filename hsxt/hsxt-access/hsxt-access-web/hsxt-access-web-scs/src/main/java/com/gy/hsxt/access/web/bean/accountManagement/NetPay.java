/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.accountManagement;

import java.io.Serializable;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;

/***
 * 网上支付实体
 * 
 * @Package: com.gy.hsxt.access.web.bean.accountManagement
 * @ClassName: NetPay
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-7-1 下午12:14:33
 * @version V1.0
 */
public class NetPay implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    private String transNo;

    /**
     * 支付方式
     * 
     * @see PayChannel
     */
    private int payChannel;

    /**
     * 交易购买商品名称
     */
    private String goodsName;
    
    /**
     * 验证非空
     * @throws HsException
     */
    public void vaildNotNull() throws HsException {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[]{this.transNo , ASRespCode.AS_PAY_ORDER_NO_ERROR},
                new Object[]{this.payChannel , ASRespCode.AS_PAY_CHANNEL_ERROR}
                );
    }

    /**
     * @return the 交易流水号
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * @param 交易流水号 the transNo to set
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * @return the 支付方式@seePayChannel
     */
    public int getPayChannel() {
        return payChannel;
    }

    /**
     * @param 支付方式@seePayChannel the payChannel to set
     */
    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * @return the 交易购买商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param 交易购买商品名称 the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

}
