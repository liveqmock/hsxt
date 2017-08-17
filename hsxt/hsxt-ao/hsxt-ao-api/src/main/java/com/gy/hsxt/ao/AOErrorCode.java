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

package com.gy.hsxt.ao;

import java.util.HashSet;
import java.util.Set;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * @Description: AO异常码定义，异常代码范围：43000~43999
 * 
 * @Package: com.gy.hsxt.ao
 * @ClassName: AOErrorCode
 * @author: yangjianguo
 * @date: 2016-1-43 下午2:57:00
 * @version V1.0
 */
public enum AOErrorCode implements IRespCode {
    /** 参数为空 **/
    AO_PARAMS_NULL(43201, "参数为空"),
    /** 参数错误 **/
    AO_PARAM_ERROR(43202, "参数错误"),
    /** 超出最大限制 **/
    AO_MAX_LIMIT(43203, "超出最大限制"),
    /** 调用AC未查询到数据 **/
    AO_INVOKE_AC_NOT_QUERY_DATA(43204, "调用AC未查询到数据"),
    /** 校验失败 **/
    AO_RULE_VERIFY_LIMIT_FAILED(43205, "校验失败"),

    /** 保存银行转账记录异常 **/
    AO_SAVE_TRANS_OUT_ERROR(43206, "保存银行转账记录异常"),
    /** 保存银行转账失败记录异常 **/
    AO_SAVE_TRANS_OUT_FAIL_ERROR(43207, "保存银行转账失败记录异常"),
    /** 查询银行转账详情异常 **/
    AO_QUERY_TRANS_OUT_DETAIL_ERROR(43208, "查询银行转账详情异常"),
    /** 查询银行转账列表异常 **/
    AO_QUERY_TRANS_OUT_LIST_ERROR(43209, "查询银行转账列表异常"),
    /** 银行转账失败处理异常 **/
    AO_TRANS_OUT_FAIL_BACK_ERROR(43210, "银行转账失败处理异常"),

    /** 未查询到银行转账记录异常 **/
    AO_NOT_QUERY_TRANS_OUT_DATA_ERROR(43211, "未查询到银行转账记录异常"),
    /** 调用UC查询持卡人状态异常 **/
    AO_TO_UC_QUERY_PERSON_INFO_ERROR(43212, "调用UC查询持卡人状态异常"),
    /** 调用UC查询企业状态异常 **/
    AO_TO_UC_QUERY_ENT_INFO_ERROR(43213, "调用UC查询企业状态异常"),

    /** 保存积分转互生币异常 **/
    AO_SAVE_PV_TO_HSB_ERROR(43214, "保存积分转互生币异常"),
    /** 保存互生币转货币异常 **/
    AO_SAVE_HSB_TO_CASH_ERROR(43215, "保存互生币转货币异常"),
    /** 查询积分转互生币详情异常 **/
    AO_QUERY_PV_TO_HSB_DETAIL_ERROR(43216, "查询积分转互生币详情异常"),
    /** 查询互生币转货币详情异常 **/
    AO_QUERY_HSB_TO_CASH_DETAIL_ERROR(43217, "查询互生币转货币详情异常"),

    /** 调用支付系统获取支付URL异常 **/
    AO_INVOKE_GP_GET_PAY_URL_ERROR(43218, "调用支付系统获取支付URL异常"),
    /** 调用支付系统获取手机支付TN码异常 **/
    AO_INVOKE_GP_GET_MOBILE_PAY_TN_CODE_ERROR(43219, "调用支付系统获取手机支付TN码异常"),
    /** 调用支付系统获取快捷支付短信码异常 **/
    AO_INVOKE_GP_GET_QUICK_PAY_SMS_CODE_ERROR(43220, "调用支付系统获取快捷支付短信码异常"),
    /** 调用帐务系统查询帐户余额异常 **/
    AO_INVOKE_AC_QUERY_BALANCE_ERROR(43221, "调用帐务系统查询帐户余额异常"),
    /** 调用帐务系统单笔冲正异常 **/
    AO_INVOKE_AC_CORRECT_SINGAL_ERROR(43222, "调用帐务系统单笔冲正异常"),
    /** 调用支付系统单笔转账异常 **/
    AO_INVOKE_GP_SINGAL_TRANS_ERROR(43223, "调用支付系统单笔转账异常"),

    /** 保存兑换互生币异常 **/
    AO_SAVE_BUY_HSB_ERROR(43224, "保存兑换互生币异常 "),
    /** 保存企业代兑互生币异常 **/
    AO_SAVE_ENT_PROXY_BUY_HSB_ERROR(43225, "保存企业代兑互生币异常 "),
    /** 保存POS兑换互生币异常 **/
    AO_SAVE_POS_BUY_HSB_ERROR(43226, "保存POS兑换互生币异常"),
    /** 保存POS代兑互生币异常 **/
    AO_SAVE_POS_PROXY_BUY_HSB_ERROR(43227, "保存POS代兑互生币异常"),
    /** 冲正POS兑换互生币异常 **/
    AO_REVERSE_POS_BUY_HSB_ERROR(43228, "冲正POS兑换互生币异常"),
    /** 冲正兑换互生币异常 **/
    AO_REVERSE_BUY_HSB_ERROR(43229, "冲正兑换互生币异常"),
    /** 冲正企业代兑互生币异常 **/
    AO_REVERSE_ENT_PROXY_BUY_HSB_ERROR(43230, "冲正企业代兑互生币异常"),
    /** 冲正POS代兑互生币异常 **/
    AO_REVERSE_POS_PROXY_BUY_HSB_ERROR(43231, "冲正POS代兑互生币异常"),
    /** 代兑互生币冲正通知异地异常 **/
    AO_REVERSE_POS_PROXY_BUY_HSB_NOTIFY_ERROR(43232, "代兑互生币冲正通知异地异常"),
    /** 代兑互生币冲正通知异地返回失败 **/
    AO_REVERSE_POS_PROXY_BUY_HSB_NOTIFY_RES_FAIL(43233, "代兑互生币冲正通知异地返回失败"),
    /** 查询兑换互生币详情异常 **/
    AO_QUERY_BUY_HSB_DETAIL_ERROR(43234, "查询兑换互生币详情异常"),
    /** 查询企业代兑互生币详情异常 **/
    AO_QUERY_ENT_PROXY_BUY_HSB_DETAIL_ERROR(43235, "查询企业代兑互生币详情异常"),

    /** 获取网银支付链接异常 **/
    AO_GET_NET_PAY_URL_ERROR(43236, "获取网银支付链接异常"),
    /** 开通快捷支付并获取支付URL链接异常 **/
    AO_OPEN_QUICK_PAY_URL_ERROR(43237, "开通快捷支付并获取支付URL链接异常 "),
    /** 获取快捷支付URL链接异常 **/
    AO_GET_QUICK_PAY_URL_ERROR(43238, "获取快捷支付URL链接异常"),
    /** 获取手机支付TN码异常 **/
    AO_GET_MOBILE_PAY_TN_CODE_ERROR(43239, "获取手机支付TN码异常"),
    /** 获取快捷支付短信码异常 **/
    AO_GET_QUICK_PAY_SMS_CODE_ERROR(43240, "获取快捷支付短信码异常"),
    /** 兑换互生币货币支付异常 **/
    AO_BUY_HSB_PAY_BY_CURRENCY_ERROR(43241, "兑换互生币货币支付异常"),

    /** 兑换互生币更新支付状态异常 **/
    AO_BUY_HSB_UPDATE_PAY_STATUS_ERROR(43242, "兑换互生币更新支付状态异常"),

    /** 兑换互生币订单已超时 **/
    AO_BUY_HSB_ORDER_TIME_OUT(43243, "兑换互生币订单已超时"),

    /** 查询未支付的兑换互生币订单异常 **/
    AO_QUERY_UNPAY_BUY_HSB_ORDER_ERROR(43244, "查询未支付的兑换互生币订单异常"),

    /** 兑换互生币更新支付方式异常 **/
    AO_UPDATE_BUY_HSB_PAY_MODEL_ERROR(43245, "兑换互生币更新支付方式异常"),

    /** 查询POS兑换互生币详情异常 **/
    AO_QUERY_POS_BUY_HSB_DETAIL_ERROR(43246, "查询POS兑换互生币详情异常"),
    /** 查询POS代兑互生币详情异常 **/
    AO_QUERY_POS_PROXY_BUY_HSB_DETAIL_ERROR(43247, "查询POS代兑互生币详情异常"),

    /** 终端设备业务数据批结算保存异常 **/
    AO_SAVE_TERMINAL_BATCH_SETTLE_ERROR(43248, "终端设备业务数据批结算保存异常"),

    /** 查询兑互生币总金额总笔数异常 **/
    AO_QUERY_BUY_HSB_TOTAL_AMOUNT_AND_ITEMS_ERROR(43249, "查询兑互生币总金额总笔数异常"),

    /** 查询代兑互生币总金额总笔数异常 **/
    AO_QUERY_PROXY_BUY_HSB_TOTAL_AMOUNT_AND_ITEMS_ERROR(43250, "询代兑互生币总金额总笔数异常"),

    /** 查询终端批结算上传标识异常 **/
    AO_QUERY_TERMINAL_BATCH_UPLOAD_FLAG_ERROR(43251, "查询终端批结算上传标识异常"),

    /** 终端设备业务数据已经上传 **/
    AO_TERMINAL_DIVICE_BIZ_DATA_UPLOADED(43252, "终端设备业务数据已经上传"),

    /** 查询终端批结算批总账编号异常 **/
    AO_QUERY_TERMIANL_BATCH_NUMBER_ERROR(43253, "查询终端批结算批总账编号异常"),

    /** 保存终端批上传异常 **/
    AO_SAVE_TERMINAL_BATCH_UPLOAD_ERROR(43254, "保存终端批上传异常"),

    /** 终端批结算更新上传时间和上传标识异常 **/
    AO_UPDATE_TERMINAL_BATCH_TIME_AND_FLAG_ERROR(43255, "终端批结算更新上传时间和上传标识异常"),

    /** 删除错误(异常) **/
    AO_REMOVE_ERROR(43256, "删除错误(异常)"),

    /** 调用账务实时记账异常 **/
    AO_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR(43257, "调用账务实时记账异常"), // 多用这种异常
    /** 保存记帐分解异常 **/
    AO_SAVE_ACCOUNTING_ERROR(43258, "保存记帐分解异常"),

    /** 银行转帐客户名与帐户名不一致 **/
    AO_TRANS_OUT_CUSTNAME_BANKNAME_DISCORD(43259, "银行转帐客户名与帐户名不一致"),

    /** 撤单异常 **/
    AO_REVOKE_TRANS_ERROR(43260, "撤单异常 "),
    /** 批量转帐异常 **/
    AO_BATCH_TRANS_OUT_ERROR(43261, "批量转帐异常"),
    /** 未查询到数据 **/
    AO_NOT_QUERY_DATA(43262, "未查询到数据"),
    /** 帐户余额不足 **/
    AO_ACC_BALANCE_NOT_ENOUGH(43263, "帐户余额不足"),

    /** 数据迁移失败 **/
    AO_DATA_TRANSFER(43264, "数据迁移失败"),
    /** 生成文件失败 **/
    AO_FILE_GENERATE(43265, "生成文件失败"),
    /** 多线程生成日终生成记账对账文件失败 **/
    AO_GEN_ACOOUNTING_FILE_FAIL(43266, "多线程生成日终生成记账对账文件失败"),
    /** 多线程生成日终生成网银支付对账文件失败 **/
    AO_GEN_NETPAY_FILE_FAIL(43267, "多线程生成日终生成网银支付对账文件失败"),

    /** 客户此业务已经被禁止 **/
    AO_CUST_BIZ_FORBIDDEN(43268, "客户此业务已经被禁止"),
    /** 业务金额小于最小允许金额 **/
    AO_AMOUNT_LESS_THEN_MIN(43269, "业务金额小于单笔最小允许金额"),
    /** 业务金额大于单笔最大允许金额 **/
    AO_AMOUNT_MORE_THEN_MAX(43270, "业务金额大于单笔最大允许金额"),
    /** 当日累计请求次数大于单日允许次数 **/
    AO_DAILY_TIMES_MORE_THEN_MAX(43271, "当日累计请求次数大于单日允许次数"),
    /** 当日累计申请金额大于单日允许金额 **/
    AO_DAILY_AMOUNT_MORE_THEN_MAX(43272, "当日累计申请金额大于单日允许金额"),
    /** 积分转互生币超出保底金额 **/
    AO_PV_TO_HSB_MORE_THAN_LOW(43273, "积分转互生币金额大于积分保底金额"),
    /** 批量自动提交转账异常 **/
    AO_BATCH_AUTO_TRANS_COMMIT_ERROR(43274, "批量自动提交转账异常"),

    /** 调用UC未查询到数据 **/
    AO_INVOKE_UC_NOT_QUERY_DATA(43275, "调用UC未查询到数据"),
    /** 银行转账对帐异常 **/
    AO_TRANS_OUT_CHECK_UP_ACC_ERROR(43276, "银行转账对帐异常"),

    /** 调用UC查询银行帐户信息异常 **/
    AO_INVOKE_UC_QUERY_BANK_INFO_ERROR(43277, "调用UC查询银行帐户信息异常 "),

    /** 银行转账对账异常 **/
    AO_TRANS_OUT_CHECK_UP_ACCOUNT_ERROR(43278, "银行转账对账异常"),

    /** 更新过期且未支付的兑换互生币订单异常 **/
    AO_UPDATE_EXPIRE_BUY_HSB_ERROR(43279, "更新过期且未支付的兑换互生币订单异常"),

    AO_UN_BATCH_SETTLE(43280, "未批结算不可做批上传"),

    AO_WRITE_FILE_ERROR(43281, "写入文件错误"),

    AO_READ_FILE_ERROR(43282, "读取文件错误"),

    AO_NOT_DIR_ERROR(43283, "不是文件夹"),

    /** 重复提交冲正 **/
    AO_REPET_COMMIT_REVERSE(43284, "重复提交冲正"),

    /** 非法的冲正请求 **/
    AO_ILLEGAL_REVERSE_REQUEST(43285, "非法的冲正请求"),

    AO_UPDATE_BANK_CARD_VALID_STATUS_ERROR(43286, "更新银行帐户验证状态时异常"),

    AO_OWE_FEE_CAN_NOT_TRANS_OUT(43287, "欠年费不能进行银行转账"),

    AO_EXISTS_SAME_DATA(43288, "存在相同的记录"),

    AO_OWE_FEE_CAN_NOT_PROXY_HSB(43289, "欠年费不能进行代兑互生币"),

    AO_HSB_PAY_MORE_THAN_SINGAL_AMOUNT(43290, "互生币支付金额大于单笔最大金额"),

    AO_HSB_PAY_MORE_THAN_DAY_SINGAL_AMOUNT(43291, "互生币支付金额超出单日最大限额"),

    AO_CLOSE_ACCOUNT_PV_TO_HSB_ERROR(43292, "销户积分转互生币异常"),

    AO_CLOSE_ACCOUNT_HSB_TO_CASH_ERROR(43293, "销户互生币转货币异常"),

    AO_HSB_TO_CASH_MORE_THAN_LOW(43294, "互生币转货币金额超出保底互生币限制"),

    AO_CHANGING_IMPORTENT_INFO(43295, "重要信息变更期"),

    AO_TO_GP_CALC_FEE_AMOUNT_ERROR(43296, "调用支付系统计算手续费异常"),

    AO_TO_BP_GET_PRIVATE_PARAM_ERROR(43297, "获取客户私有参数项异常"),

    AO_TO_UC_NO_REAL_NAME_REG(43298, "未实名注册"),

    AO_EXPORT_EXCEL_MORE_THAN_MAX_NUM(43299, "导出Excel超出最大条数"),

    AO_EXPORT_TRANS_OUT_EXCEL_DATA_ERROR(43300, "导出银行转账Excel数据异常"),

    AO_TO_UC_NOT_QUERY_AUTH_DATA(43301, "调用UC未查询到认证信息"),

    AO_TRANS_OUT_CARDNAME_CERTIFNAME_DISCORD(43302, "银行账户名称与证件姓名不一致"),

    AO_TO_UC_QUERY_CERTIF_INFO_ERROR(43303, "调用UC查询认证信息异常"),

    AO_NOT_QUERY_REVOKE_DATA(43304, "未查询到撤单记录"),

    AO_TO_GP_GET_QUICK_PAY_BIND_URL_ERROR(43400, "调用支付系统获取快捷支付绑定URL异常"),
    //
    ;
    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误描述
     */
    private String erroDesc;

    AOErrorCode(int code, String desc) {
        this.errorCode = code;
        this.erroDesc = desc;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getCode()
     */
    @Override
    public int getCode() {
        return errorCode;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getDesc()
     */
    @Override
    public String getDesc() {
        return erroDesc;
    }

    public static void main(String[] args) {
        /**
         * 检查代码是否有重复
         */
        Set<Integer> codeSet = new HashSet<Integer>();
        for (AOErrorCode item : AOErrorCode.values())
        {
            if (codeSet.contains(item.getCode()))
            {
                System.err.println(item.name() + ":" + item.getCode() + " 代码有重复冲突，请修改！");
            }
            else
            {
                codeSet.add(item.getCode());
            }
        }
    }
}
