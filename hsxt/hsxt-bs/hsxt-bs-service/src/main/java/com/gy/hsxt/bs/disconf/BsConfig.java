/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.gy.hsxt.bs.disconf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;

/**
 * 业务服务系统私有配置参数
 * 
 * @Package: com.gy.hsxt.bs.disconf
 * @ClassName: BsConfig
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-10-22 下午6:18:37
 * @version V1.0
 */
@Component
@Scope("singleton")
// @DisconfFile(filename = "hsxt-bs.properties")
@DisconfUpdateService(classes = { BsConfig.class })
public class BsConfig implements IDisconfUpdate {

    /**
     * 集群部署应用节点编号
     */
    private String appNode;

    /**
     * 工具订单过期失效天数
     */
    private int toolOrderInvalidDays;

    /**
     * 工具发货单自动签收天数
     */
    private int toolShippingSignDays;

    /**
     * 定制卡样费用
     */
    private String cardStyleFee;

    /**
     * 资源段价格
     */
    private String resSegmentPrice;

    /**
     * 补卡重做卡的单个卡的费用
     */
    private String reformCardFee;

    /**
     * 工单任务打开
     */
    private Boolean workTaskIsOpen;

    /** 申报授权码有效期 **/
    private int authCodeExpiryDays;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 每次查询数量
     */
    private Integer row;

    /**
     * 印章url
     */
    private String sealFileUrl;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 免费开启企业时是否分配资源费
     */
    private boolean allocResFeeForFreeOpen;

    /**
     * 免费开启企业时是否配置工具
     */
    private boolean configToolForFreeOpen;

    @DisconfFileItem(name = "merchant.no", associateField = "merchantNo")
    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    @DisconfFileItem(name = "ac.accountCheck.row", associateField = "row")
    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    @DisconfFileItem(name = "system.id", associateField = "sysName")
    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    @DisconfFileItem(name = "system.instance.no", associateField = "appNode")
    public String getAppNode() {
        return appNode;
    }

    public void setAppNode(String appNode) {
        this.appNode = appNode;
    }

    @DisconfFileItem(name = "tool.order.pay.over.time", associateField = "toolOrderInvalidDays")
    public int getToolOrderInvalidDays() {
        return toolOrderInvalidDays;
    }

    public void setToolOrderInvalidDays(int toolOrderInvalidDays) {
        this.toolOrderInvalidDays = toolOrderInvalidDays;
    }

    @DisconfFileItem(name = "tool.shipping.sign.time", associateField = "toolShippingSignDays")
    public int getToolShippingSignDays() {
        return toolShippingSignDays;
    }

    public void setToolShippingSignDays(int toolShippingSignDays) {
        this.toolShippingSignDays = toolShippingSignDays;
    }

    @DisconfFileItem(name = "custom.card.style.fee", associateField = "cardStyleFee")
    public String getCardStyleFee() {
        return cardStyleFee;
    }

    public void setCardStyleFee(String cardStyleFee) {
        this.cardStyleFee = cardStyleFee;
    }

    @DisconfFileItem(name = "res.segment.price", associateField = "resSegmentPrice")
    public String getResSegmentPrice() {
        return resSegmentPrice;
    }

    public void setResSegmentPrice(String resSegmentPrice) {
        this.resSegmentPrice = resSegmentPrice;
    }

    @DisconfFileItem(name = "reform.card.fee", associateField = "reformCardFee")
    public String getReformCardFee(){return reformCardFee;}

    public void setReformCardFee(String reformCardFee){this.reformCardFee = reformCardFee;}

    @DisconfFileItem(name = "work.task.isopen", associateField = "workTaskIsOpen")
    public Boolean getWorkTaskIsOpen() {
        return workTaskIsOpen;
    }

    public void setWorkTaskIsOpen(Boolean workTaskIsOpen) {
        this.workTaskIsOpen = workTaskIsOpen;
    }

    @DisconfFileItem(name = "apply.authcode.expiry.days", associateField = "authCodeExpiryDays")
    public int getAuthCodeExpiryDays() {
        return authCodeExpiryDays;
    }

    public void setAuthCodeExpiryDays(int authCodeExpiryDays) {
        this.authCodeExpiryDays = authCodeExpiryDays;
    }

    @DisconfFileItem(name = "seal.file.url", associateField = "sealFileUrl")
    public String getSealFileUrl() {
        return sealFileUrl;
    }

    public void setSealFileUrl(String sealFileUrl) {
        this.sealFileUrl = sealFileUrl;
    }

    @DisconfFileItem(name = "free.open.alloc", associateField = "allocResFeeForFreeOpen")
    public boolean isAllocResFeeForFreeOpen() {
        return allocResFeeForFreeOpen;
    }

    public void setAllocResFeeForFreeOpen(boolean allocResFeeForFreeOpen) {
        this.allocResFeeForFreeOpen = allocResFeeForFreeOpen;
    }

    @DisconfFileItem(name = "free.open.tool", associateField = "configToolForFreeOpen")
    public boolean isConfigToolForFreeOpen() {
        return configToolForFreeOpen;
    }

    public void setConfigToolForFreeOpen(boolean configToolForFreeOpen) {
        this.configToolForFreeOpen = configToolForFreeOpen;
    }

    @Override
    public void reload() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
