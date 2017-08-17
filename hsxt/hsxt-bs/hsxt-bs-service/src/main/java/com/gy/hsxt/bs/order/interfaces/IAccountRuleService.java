/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.order.interfaces;

/**
 * 账户操作限额限次等规则检查，及操作后续处理，比如累计金额、累计次数更新等
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: IAccountRuleService
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-17 下午6:10:52
 * @version V1.0
 */
public interface IAccountRuleService {

    /**
     * 检查兑换互生币是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            兑换互生币金额
     * @param isReg
     *            是否已实名注册，消费者需要区分
     * @return
     */
    boolean checkBuyHsbRule(String custId, Integer custType, String amount, boolean isReg);

    /**
     * 兑换互生币后续处理，，比如更新累计兑换互生币金额、累计兑换次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            兑换互生币金额
     */
    void afterBuyHsb(String custId, Integer custType, String amount);

    /**
     * 检查互生币支付是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            互生币支付金额
     * @return
     */
    boolean checkHsbToPayRule(String custId, Integer custType, String amount);

    /**
     * 互生币支付后续处理，比如更新累计互生币支付金额、支付次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            支付互生币金额
     */
    void afterHsbToPay(String custId, Integer custType, String amount);

    /**
     * 校验积分投资是否符合规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            投资积分数
     * @return true/false
     */
    boolean checkPvToInvestRule(String custId, Integer custType, String amount);

    /**
     * 校验积分保底规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            积分余额数
     * @return true/false
     */
    public boolean checkPvLowRule(String custId, Integer custType, String amount);

    /**
     * 获取保底积分数
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @return 保底积分数
     */
    public String getPvSaveAmount(String custId, Integer custType);

    /**
     * 获取互生币转货币手续费比率
     * 
     * @return
     */
    String getHsbToCashFeeRate();

    /**
     * 获取个人货币转银行复核阀值
     * 
     * @return
     */
    String getPerCheckLimitForTransOut();

    /**
     * 获取企业货币转银行复核阀值
     * 
     * @return
     */
    String getEntCheckLimitForTransOut();

    /**
     * 获取代兑互生币手续费比例
     * 
     * @return
     */
    String getEntFeeRateForProxyBuyHsb();

    /**
     * 获取个人投资分红流通币比率
     * 
     * @return
     */
    String getPerInvesDividHsbScale();

    /**
     * 获取企业投资分红流通币比率
     * 
     * @return
     */
    String getEntInvesDividHsbScale();

    /**
     * 获取个人投资分红定向消费币比率
     * 
     * @return
     */
    String getPerInvesDirectHsbScale();

    /**
     * 获取企业投资分红慈善救助基金比率
     * 
     * @return
     */
    String getEntInvesDirectHsbScale();

    /**
     * 获取互生币保底值
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @return 互生币保底值
     */
    public String getHsbSaveAmount(String custId, Integer custType);

    /**
     * 判断银行转账申请是否需要复核
     * 
     * @param custType
     *            客户类型
     * @param amount
     *            申请金额
     * @return true：需要复核 fasle： 不需要复核
     */
    boolean isNeedReviewForTransOut(Integer custType, String amount);

    /**
     * 清空缓存：测试用
     * 
     * @param custId
     *            客户号
     */
    void setSysItemsValue(String custId);
}
