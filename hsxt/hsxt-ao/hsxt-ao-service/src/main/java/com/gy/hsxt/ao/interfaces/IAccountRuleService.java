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

package com.gy.hsxt.ao.interfaces;

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
    public boolean checkHsbToPayRule(String custId, Integer custType, String amount);

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
    public void afterHsbToPay(String custId, Integer custType, String amount);

    /**
     * 检查货币转银行是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            转账货币金额(本币)
     * @return
     */
    boolean checkCashToBankRule(String custId, Integer custType, String amount);

    /**
     * 货币转银行后续处理，比如更新累计转账金额、转账次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            转账货币金额(本币)
     */
    void afterCashToBank(String custId, Integer custType, String amount);

    /**
     * 检查积分转互生币是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            转换积分数
     * @return
     */
    boolean checkPvToHsbRule(String custId, Integer custType, String amount);

    /**
     * 校验积分转互生币是否符合保底规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            帐户余额
     * @return true/false
     */
    public boolean checkPvToHsbLowRule(String custId, Integer custType, String amount);

    /**
     * 积分转互生币后续处理，比如更新累计转换积分数、累计转换次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            转换积分数
     */
    void afterPvToHsb(String custId, Integer custType, String amount);

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
     * 检查互生币转货币是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            转换互生币金额
     * @return
     */
    boolean checkHsbToCashRule(String custId, Integer custType, String amount);

    /**
     * 校验互生币转货币保底互生币规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            转换互生币金额
     * @return
     */
    public boolean checkHsbToCashLowRule(String custId, Integer custType, String amount);

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
     * 互生币转货币后续处理，比如更新累计转换金额、转换次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            互生币支付金额
     */
    void afterHsbToCash(String custId, Integer custType, String amount);

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
     * 冲正缓存
     * 
     * @param custId
     *            客户号
     * @param sysItemsKey
     *            参数key
     * @param amount
     *            金额
     */
    void reverseSysItemsValue(String custId, String sysItemsKey, String amount);

    /**
     * 清空缓存：测试用
     * 
     * @param custId
     *            客户号
     * @param businessParamCode
     *            业务参数代码
     */
    void resetBusinessParamNull(String custId, String businessParamCode);

}
