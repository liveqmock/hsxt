/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import com.alibaba.fastjson.JSON;

/**
 * 业务订单扩展类，用于扩展原始实体类实现复杂业务需求
 *
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: OrderCustomBean
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-8-31 下午3:12:55
 * @version V1.0
 */
public class OrderCustom extends Order {

    private static final long serialVersionUID = -2841417783451388443L;

    /** 收货信息 **/
    private DeliverInfo deliverInfoBean;

    /** 工具状态（取自工具配置单） **/
    private Integer toolStatus;

    public Integer getToolStatus() {
        return toolStatus;
    }

    public void setToolStatus(Integer toolStatus) {
        this.toolStatus = toolStatus;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public DeliverInfo getDeliverInfoBean() {
        return deliverInfoBean;
    }

    public void setDeliverInfoBean(DeliverInfo deliverInfoBean) {
        this.deliverInfoBean = deliverInfoBean;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
