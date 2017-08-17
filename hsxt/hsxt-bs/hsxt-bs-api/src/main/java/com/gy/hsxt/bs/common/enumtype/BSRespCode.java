/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

import java.util.HashSet;
import java.util.Set;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * 业务系统错误代码
 * 
 * @Package :com.gy.hsxt.bs.common
 * @ClassName : BSRespCode
 * @Description : BS：业务服务 异常代码范围：12000~12999
 * @Author : chenm
 * @Date : 2015/12/10 17:25
 * @Version V3.0.0.0
 */
public enum BSRespCode implements IRespCode {
    /***************** BS：业务服务 异常代码范围：12000~12999 *******************/

    /***************** BS:公共 12000~12099 *****************/
    BS_PARAMS_NULL(12000, "参数不能为null"),

    BS_DUBBO_INVOKE_UC_FAIL(12001, "dubbo调用UC失败"),

    BS_NOT_QUERY_ENT_INFO(12002, "未查询到企业信息"),

    BS_INVOKE_LCS_FAIL(12003, "调用LCS失败"),

    BS_NOT_QUERY_DATA(12004, "没有查询到数据"),

    BS_RECORD_IS_FINISH(12005, "记录已经完成"),

    BS_QUERY_ERROR(12006, "查询异常"),

    BS_TASK_STATUS_NOT_DEALLING(12007, "任务状态不是办理中"),

    BS_QUERY_AREA_PLAT_CUST_ID_ERROR(12008, "查询地区平台客户号错误"),

    BS_HSB_PAY_OVER_LIMIT(12009, "超出互生币支付限额"),

    BS_PARAMS_EMPTY(12010, "参数不能为空"),

    BS_PARAMS_TYPE_ERROR(12011, "参数类型错误"),

    /** 保存增值节点失败 **/
    BS_SAVE_INCREMENT_FAIL(12030, "保存增值节点失败"),

    /***************** BS:公共 12000~12099 END *****************/

    /***************** 工具业务 12100~12299 *****************/

    BS_ADD_TOOL_PRODUCT_FAIL(12101, "新增工具产品失败"),

    BS_TOOL_UP_OR_DOWN_FAIL(12102, "工具上架或下架失败"),

    BS_TOOL_HAS_WAIT_APPR_PRICE_CHANGE(12103, "工具有价格变更的待审批"),

    BS_TOOL_UP_OR_DOWN_APPR_FAIL(12104, "工具上架或下架审批失败"),

    BS_ADD_TOOL_PRICE_CHANGE_FAIL(12105, "新增工具价格变更失败"),

    BS_TOOL_PRICE_CHANGE_APPR_FAIL(12106, "工具价格变更审批失败"),

    BS_ADD_CARD_STYLE_FAIL(12107, "新增互生卡样失败"),

    BS_CARD_STYLE_ENABLE_OR_STOP_FAIL(12108, "互生卡样启用或停用失败"),

    BS_CARD_STYLE_SET_DEFAULT_FAIL(12109, "互生卡样设置成默认卡样失败"),

    BS_CARD_STYLE_ENABLE_OR_STOP_APPR_FAIL(12110, "互生卡样启用或停用审批失败"),

    BS_TOOL_ORDER_BUY_NUM_ERROR(12111, "工具申购下单购买数量超标"),

    BS_TOOL_ORDERE_SUBMIT_FAIL(12112, "工具订单提交失败"),

    BS_CARD_STYLE_ORDERE_SUBMIT_FAIL(12113, "订制卡样订单提交失败"),

    BS_ADD_PROXY_ORDERE_FAIL(12114, "新增平台代购订单失败"),

    BS_PROXY_ORDERE_APPR_FAIL(12115, "平台代购订单审批失败"),

    BS_QUERY_TOOL_CARD_FAIL(12116, "查询工具互生卡失败"),

    BS_PERSON_MEND_CARD_ORDER_SUBMIT_FAIL(12117, "个人补卡订单提交失败"),

    BS_ENT_MAKING_CARD_ORDER_SUBMIT_FAIL(12118, "企业重做卡订单提交失败"),

    BS_TOOL_ORDER_PAY_PROCESSING(12119, "工具订单支付处理中,稍等支付结果"),

    BS_TOOL_ORDER_PAID(12120, "工具订单已支付"),

    BS_TOOL_ORDER_CLOSED_OR_EXPIRY(12121, "工具订单已关闭或已失效"),

    BS_MODIFY_TOOL_ORDER_PAY_CHANNEL_FAIL(12122, "更新工具订单支付方式失败"),

    BS_MODIFY_CARD_STYLE_LOCK_FAIL(12123, "更新卡样锁定状态失败"),

    BS_UPLOAD_CARD_MARK_FILE_FAIL(12124, "上传卡制作确认文件失败"),

    BS_UPLOAD_CARD_STYLE_MATERIAL_FAIL(12125, "上传卡样素材失败"),

    BS_ENT_CARD_STYLE_CONFIRM_FAIL(12126, "企业确认卡样失败"),

    BS_UPLOAD_CARD_MARK_CONFIRM_FILE_FAIL(12127, "上传互生卡制作确认文件失败"),

    BS_ORDER_IS_NOT_WAIT_CONFIRM(12128, "工具订单不是待确认状态"),

    BS_TOOL_ORDER_NOT_QUERY_CONFIG(12129, "工具订单未查询到配置单"),

    BS_ORDER_IS_CONFIRM_MARK_FAIL(12130, "工具订单确认制作失败"),

    BS_TOOL_ORDER_WITHDRAWALS_FAIL(12131, "工具订单撤单失败"),

    BS_CREATE_POINT_KSN_FAIL(12132, "生成积分刷卡器KSN失败"),

    BS_IMPORT_CONSUME_KSN_FAIL(12133, "导入消费刷卡器KSN失败"),

    BS_TOOL_DEVICE_NOT_EXIST(12134, "工具设备不存在"),

    BS_DEVICE_IS_NOT_USED(12135, "工具设备不是未使用状态"),

    BS_NOT_QUERY_POS_INFO(12136, "根据配置单和客户号未查询到POS机数据"),

    BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG(12137, "配置单不是待配置状态"),

    BS_DEVICE_RELEVANCE_OTHER_CONFIG(12138, "设备已关联其他配置单"),

    BS_TOOL_DEVICE_IS_CONFIG(12139, "工具设备已经配置"),

    BS_POS_CONFIG_FAIL(12140, "POS机配置关联失败"),

    BS_NOT_QUERY_POS_OR_TABLET_INFO(12141, "根据配置单和客户号未查询到POS机或平板数据"),

    BS_NO_QUERY_DEVICE_CONFIG(12142, "未查询到设备关联"),

    BS_TOOL_DEVICE_CONFIG_IS_USE(12143, "工具设备配置关联已使用 "),

    BS_DEVICE_CONFIG_IS_USE_FAIL(12144, "配置设备关系使用失败"),

    BS_NOT_QUERY_MCR_INFO(12145, "根据配置单和客户号未查询到刷卡器数据"),

    BS_MCR_CONFIG_FAIL(12146, "刷卡器配置关联失败"),

    BS_NOT_QUERY_TABLET_INFO(12147, "根据配置单和客户号未查询到互生平板数据"),

    BS_TABLET_CONFIG_FAIL(12148, "平板配置关联失败"),

    BS_TOOL_CONFIG_IS_NOT_OPEN_CARD(12149, "配置单不是待开卡的状态"),

    BS_BATCH_OPEN_CARD_FAIL(12150, "批量开卡失败"),

    BS_NOT_QUERY_MARKING_CARDINFO(12151, "未查询到重做互生卡信息"),

    BS_RENEWAL_OPEN_CARD_FAIL(12152, "重做互生卡开卡失败"),

    BS_CARD_ENTER_FAIL(12153, "互生卡入库失败"),

    BS_TOOL_CONFIG_IS_NOT_WAIT_ENTER(12154, "配置单不是待入库状态"),

    BS_CARD_MARK_FAIL(12155, "卡制作单制成失败"),

    BS_ADD_SHIPPING_METHOD_FAIL(12156, "新增配置方式失败"),

    BS_MODIFY_SHIPPING_METHOD_FAIL(12157, "修改配置方式失败"),

    BS_REMOVE_SHIPPING_METHOD_FAIL(12158, " 删除配置方式失败"),

    BS_ADD_DELIVERY_CORP_FAIL(12159, "新增快递公司失败"),

    BS_MODIFY_DELIVERY_CORP_FAIL(12160, "修改快递公司失败"),

    BS_REMOVE_DELIVERY_CORP_FAIL(12161, "删除快递公司失败 "),

    BS_TOOL_CONFIG_TYPE_NOT_SAME(12162, "工具配置单类型不相同"),

    BS_IS_NOT_SAME_ENT(12163, "不是同一企业"),

    BS_IS_NOT_SAME_ARRD(12164, "不是同一地址"),

    BS_IS_NOT_S_SAME_ENT(12165, "不是同一服务公司下的企业"),

    BS_TOOL_CONFIG_IS_NOT_WAIT_SEND(12166, "配置单不是待发货状态"),

    BS_SHIPPING_TYPE_ERROR(12167, "发货单类型错误"),

    BS_ADD_SHIPPING_FAIL(12168, "新增发货单失败"),

    BS_TOOL_SIGN_ACCEPT_FAIL(12169, "工具签收失败"),

    BS_ADD_WAREHOUSE_FAIL(12170, "新增仓库失败"),

    BS_MODIFY_WAREHOUSE_FAIL(12171, "修改仓库失败"),

    BS_REMOVE_WAREHOUSE_FAIL(12172, "删除仓库失败"),

    BS_ADD_SUPPLIER_FAIL(12173, "新增供应商失败"),

    BS_MODIFY_SUPPLIER_FAIL(12174, "修改供应商失败"),

    BS_REMOVE_SUPPLIER_FAIL(12175, "删除供应商失败"),

    BS_POS_TABLET_ENTER_SEQNO_NULL(12176, "入库序列号为NULL"),

    BS_DEVICE_SEQNO_HAS_ENTER(12177, "设备序列号有已经入库"),

    BS_KSN_ENTER_SEQNO_NULL(12178, "刷卡器入库数据为NULL"),

    BS_BATCHNO_ENTERD(12179, "该批次号已经入库"),

    BS_TOOL_ENTER_FAIL(12180, "工具入库失败"),

    BS_TOOL_STOCK_INVENTORY_FAIL(12181, "工具库存盘库失败"),

    BS_TOOL_ENTER_INSPECTION_FAIL(12182, "工具入库抽检失败"),

    BS_BATCH_DEVICE_NOT_EXIST(12183, "当前批次的设备不存在"),

    BS_ADD_DEVICE_USE_RECORD_FAIL(12184, "新增工具使用记录失败"),

    BS_MODIFY_TOOL_CONFIGE_WHID_FAIL(12185, "修改配置单的仓库id失败"),

    BS_TOOL_DEVICE_NOT_OUTED(12186, "工具设备不是出库和使用状态"),

    BS_ADD_AFERT_SERVICE_FAIL(12187, "新增售后服务失败 "),

    BS_NOT_QUERY_AFERT_SERVICE(12188, "未查询到售后服务数据"),

    BS_AFERT_SERVICE_ARRP_FAIL(12189, "售后服务审批失败"),

    BS_NOT_QUERY_AFTER_DETAIL(12191, "未查询客户配置单的售后清单数据"),

    BS_AFTER_CONFIG_MCR_FAIL(12192, "配置刷卡器售后关联失败"),

    BS_AFTER_DEVICE_IS_NOT_USED_OR_BEEN_REPAIRED(12193, "售后设备不是未使用或已返修状态"),

    BS_AFTER_CONFIG_SECRETKEY_DEVICE_FAIL(12194, "售后配置秘钥设备失败"),

    BS_ADD_AFTER_SHIPPING_FAIL(12195, "新增售后发货单失败"),

    BS_ADD_APPLY_CONFIG_FAIL(12196, "新增申报配置单失败"),

    BS_TOOL_ORDER_CLOSE_FAIL(12197, "工具订单关闭失败"),

    BS_MODIFY_TOOL_ORDER_MARK_STATUS_FAIL(12198, "工具订单修改制作状态失败"),

    BS_ORDER_IS_NOT_WAIT_PAY_OR_CONFIRM(12199, "订单状态错误，不是待付款或待确认状态"),

    BS_EXPORT_POINT_KSN_FAIL(12200, "导出积分刷卡器ksn失败"),

    BS_EXPORT_CONSUME_KSN_FAIL(12201, "导出消费刷卡器ksn失败"),

    BS_CUST_HAS_NOFINISH_CONFIG(12202, "你已经申请了补办互生卡业务，无需再次申请"),

    BS_IS_NOT_SAME_PRE(12203, "不是同一消费者"),

    BS_PLAT_PROXY_ORDER_TYPE_ERROR(12204, "平台代购订单类型错误"),

    BS_WAREHOUSE_SELECTED_PROVINCENO(12205, "仓库已经选择省份"),

    BS_TOOL_CONFIG_IS_NOT_WAIT_MARK(12206, "配置单不是待制作状态"),

    BS_DEVICE_SEQ_NO_IS_IMPORT(12207, "批次中有设备序列号已经导入"),

    BS_AFTER_IN_HAND_DEVICE_NO(12208, "批量导入售后服务有处理中的序列号"),

    BS_DEVICE_NO_LIST_HAS_REPEAT(12209, "设备序列号列表中有重复的数据"),

    BS_DEVICE_NOT_USED(12210, "设备状态错误,不是已使用状态"),

    BS_KEEP_AFTER_CONFIG_FAIL(12211, "配置刷卡工具售后保持关联失败"),

    BS_CARD_CONFIRM_MARK_EXCEED_MAX(12212, "互生卡已确认制作数量超出最大数量"),

    BS_MODIFY_TOOL_PRODUCT_FAIL(12213, "修改工具产品失败"),

    BS_TOOL_PRODUCT_NAME_EXIST(12214, "新增工具产品名称已经存在"),

    BS_DEVICE_CONIFG_NO_SAME_WH(12215, "设备和配置单不在同一仓库"),

    BS_ADD_RESOURCE_SEGMENT_FAIL(12216, "新增企业的资源段失败"),

    BS_DEVICE_CONIFG_NO_SAME_TYPE(12217, "设备类型和配置单工具类型不一致"),

    BS_DEVICES_HAS_SAME(12218, "设备序列号列表包含相同的序列号"),

    BS_DEVICES_GREATER_THAN_CONFIG_NUM(12219, "进行关联设备的数量大于配置单的数量"),

    BS_SEGMENTID_HAS_ORDER_BOUGHT(12220, "选择资源段含有已下单或已申购"),

    BS_ADD_CARD_PROVIDE_APPLY_FAIL(12221, "新增互生卡发放申请失败"),

    BS_RES_SEGMENT_ORDER_SUBMIT_FAIL(12222, "申购资源段订单提交失败"),

    BS_TOOL_CONFIG_NOT_MODIFY_WH(12223, "配置单状态不对或已经进行配置了，不能修改仓库"),

    BS_SEGMENTID_HAS_PROXY(12224, "选择资源段含有代购中的,请重新选择"),

    /***************** 工具业务 12100~12299 END *****************/

    /***************** 系统业务 12300~12400 *****************/

    // 年费模块 12300 ~ 12320

    BS_ANNUAL_FEE_PRICE_PENDING_EXIST(12300, "年费待审方案已存在"),

    BS_ANNUAL_FEE_PRICE_NOT_EXIST(12301, "年费方案不存在"),

    BS_ANNUAL_FEE_PRICE_STATUS_ERROR(12302, "年费方案状态错误"),

    BS_ANNUAL_FEE_PRICE_DB_ERROR(12303, "年费方案DB错误"),

    BS_ANNUAL_FEE_INFO_EXIST(12304, "年费信息已存在"),

    BS_ANNUAL_FEE_INFO_NOT_EXIST(12305, " 费信息不存在"),

    BS_ANNUAL_FEE_INFO_DB_ERROR(12306, "年费信息DB错误"),

    BS_ANNUAL_FEE_DETAIL_EXIST(12307, "年费计费单已存在"),

    BS_ANNUAL_FEE_DETAIL_NOT_EXIST(12308, "年费计费单不存在"),

    BS_ANNUAL_FEE_NOT_TO_PAY_TIME(12310, "此企业还未到缴年费时间"),

    BS_ANNUAL_FEE_ORDER_NOT_EXIST(12311, "年费业务单不存在"),

    BS_ANNUAL_FEE_GENERATE_TXT_ERROR(12312, "年费欠款数据生成记账TXT文件错误"),

    BS_ANNUAL_FEE_PARSE_TXT_ERROR(12313, "年费记账数据解析错误"),

    BS_ANNUAL_FEE_DETAIL_DB_ERROR(12314, "年费计费单DB错误"),

    BS_ANNUAL_FEE_ORDER_DB_ERROR(12315, "年费业务单DB错误"),

    // 重置交易密码 12321 ~ 12330

    BS_TRANS_PWD_PENDING_EXIST(12321, "重置交易密码申请存在"),

    BS_TRANS_PWD_DB_ERROR(12322, "重置交易密码DB错误"),

    // 发票管理 12331 ~ 12340
    BS_INVOICE_NOT_ENOUGH(12331, "开发票的金额不足"),

    BS_INVOICE_POOL_NULL(12332, "发票统计数据为null"),

    BS_INVOICE_AMOUNT_NOT_EQUAL(12333, "发票金额不相等"),

    BS_INVOICE_PLAT_DB_ERROR(12334, "平台发票DB错误"),

    BS_INVOICE_CUST_DB_ERROR(12335, "客户发票DB错误"),

    BS_INVOICE_LIST_DB_ERROR(12336, "客户清单DB错误"),

    BS_INVOICE_POOL_DB_ERROR(12337, "发票统计DB错误"),

    BS_INVOICE_PLAT_NULL(12338, "平台发票不存在"),

    BS_INVOICE_POOL_OVER(12339, "该类发票金额已开完"),

    BS_INVOICE_GREAT_1000(12340, "消费积分扣除累计金额至少1000元才可开发票"),

    BS_INVOICE_ALREADY_SIGN(12344, "发票已签收"),

    // 资源费管理 12341 ~ 12350

    BS_RES_FEE_EXIST(12341, "此类型资源费待审方案已存在，请不要重复提交"),

    BS_RES_FEE_DB_ERROR(12342, "资源费方案DB错误"),
    // 企业税率调整 12351 ~ 12354

    BS_TAXRATE_CHANGE_DB_ERROR(12351, "税率调整DB错误"),

    BS_TAXRATE_CHANGE_PENDING_EXIST(12352, "企业税率调整申请存在"),

    BS_TAXRATE_CHANGE_GENERATE_TXT_ERROR(12353, "生成上一个月税率调整记录TXT文件失败"),

    // 临账管理 12356 ~ 12370

    BS_ACCOUNT_INFO_EXIST(12356, "收款账户信息已存在"),

    BS_TEMP_ACCT_LINK_EXIST(12357, "该业务订单待复核的临账关联已存在"),

    BS_ACCOUNT_NAME_DB_ERROR(12361, "收款账户名称DB错误"),

    BS_ACCOUNT_INFO_DB_ERROR(12362, "收款账户信息DB错误"),

    BS_DEBIT_DB_ERROR(12363, "临账DB错误"),

    BS_DEBIT_NOT_EXIST(12364, "临账不存在"),

    BS_TEMP_ACCT_LINK_ORDER_NULL(12365, "临账关联的业务订单不存在"),

    BS_TEMP_ACCT_LINK_AMOUNT_NOT_EQUAL(12366, "关联总金额与折合货币金额不相等"),

    BS_TEMP_ACCT_LINK_DB_ERROR(12367, "临账关联DB错误"),

    BS_TEMP_ACCT_REFUND_NOT_ALL(12368, "退款金额跟临账未关联金额不相等"),

    BS_TEMP_ACCT_REFUND_DB_ERROR(12369, "临账退款DB错误"),

    BS_ACCOUNT_NAME_EXIST(12370, "收款账户名称已存在"),

    // 消息与公告 12371 ~ 12380

    BS_MESSAGE_DB_ERROR(12371, "消息与公告DB错误"),

    BS_MESSAGE_SEND_ERROR(12372, "消息与公告发送异常"),

    // 其他 12381 ~ 12400
    BS_ACCOUNT_DETAIL_GENERATE_TXT_ERROR(12381, "日终批记账形成TXT文件错误"),

    BS_ACCOUNT_CHECK_GENERATE_TXT_ERROR(12382, "日终账务对账形成TXT文件错误"),

    BS_MLM_DETAIL_DB_ERROR(12383, "增值详情DB错误"),

    BS_MLM_TOTAL_DB_ERROR(12384, "增值汇总DB错误"),

    BS_BMLM_DETAIL_DB_ERROR(12385, "再增值积分DB错误"),
    /***************** 系统业务 12300~12400 END *****************/

    /***************** 投资、订单、交易 12401-12500 *****************/
    BS_CUST_BIZ_FORBIDDEN(12476, "客户此业务已经被禁止"),

    BS_DUBBO_INVOKE_ERROR(12477, "dubbo调用失败"),

    BS_ACC_BALANCE_NOT_ENOUGH(12478, "帐户余额不足"),

    BS_GET_QUICK_PAY_SMS_CODE_ERROR(12479, "获取快捷支付短信验证码异常"),

    BS_QUERY_POINT_DIVIDEND_DETAIL_ERROR(12480, "查询积分投资分红详情异常"),

    BS_QUERY_DIVIDEND_DETAIL_ERROR(12481, "查询积分投资分红计算详情异常"),

    BS_GEN_POINT_INVEST_RECORD_ERROR(12482, "生成投资分红记录异常"),

    BS_GEN_POINT_INVEST_ACCOUNT_DETAIL_ERROR(12483, "投资分红生成记帐分解异常 "),

    BS_GEN_POINT_INVEST_ACC_FILE_ERROR(12484, "投资分红生成记帐文件异常"),

    BS_READ_FILE_ERROR(12411, "读取投资分红对帐文件失败"),

    BS_SAVE_TASK_ERROR(12412, "保存工单异常"),

    BS_UPDATE_TASK_STATUS_ERROR(12413, "更新任务状态异常"),

    BS_FIND_ORDER_DETAIL_ERROR(12416, "查询订单详情异常"),

    BS_PAGE_PARAM_NULL(12422, "分页参数为空"),

    BS_QUERY_ORDER_LIST_ERROR(12423, "查询订单列表异常"),

    BS_SAVE_DIVIDEND_RATE_ERROR(12435, "保存年度分红比率异常"),

    BS_SAVE_POINT_INVEST_ERROR(12438, "保存积分投资异常"),

    BS_QUERY_POINT_INVEST_LIST_ERROR(12440, "查询积分投资列表异常"),

    BS_QUERY_POINT_DIVIDEND_LIST_ERROR(12441, "查询积分投资分红列表异常"),

    BS_QUERY_POINT_DIVIDEND_CALC_LIST_ERROR(12442, "查询积分投资分红计算明细列表异常"),

    BS_UPDATE_ORDER_STATUS_ERROR(12460, "更新订单状态异常"),

    BS_SAVE_COMMON_ORDER_ERROR(12461, "保存通用订单异常"),

    BS_UPDATE_ORDER_CUST_ID(12462, "更新订单客户号异常"),

    BS_UPDATE_RES_FEE_ORDER_ERROR(12464, "更新资源费订单异常"),

    BS_ACT_DETAIL_ERROR(12466, "记帐分解异常"),

    BS_FIND_UN_COMPLETE_ORDER_ERROR(12467, "查询未完成订单异常"),

    BS_GET_INVEST_DIVIDEND_INFO_ERROR(12469, "获取积分投资通用信息异常"),

    BS_CLOSE_ORDER_ERROR(12471, "关闭订单异常"),

    BS_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR(12472, "调用账务实时记账异常"),

    BS_GET_PAY_URL_ERROR(12473, "获取支付URL异常"),

    BS_ORDER_TIME_OUT(12474, "订单已超时"),

    BS_QUICK_PAY_SMS_CODE_ERROR(12475, "获取快捷支付短信码异常"),

    BS_QUERY_DIVIDEND_RAE_LIST_ERROR(12401, "获取年度分红比率列表异常"),

    BS_SYNC_UPDATE_TM_TASK_ERROR(12402, "同步更新工单系统工单状态异常"),

    BS_AUTO_GEN_POINT_DIVIDEND_RECORD_ERROR(12403, "定时生成年度投资分红记录"),

    BS_PV_TO_INVEST_MORE_THAN_LOW(12404, "积分投资金额超过积分保底金额"),

    BS_ORDER_IS_PAY(12405, "订单已支付"),

    BS_ORDER_NOT_WAIT_PAY(12415, "业务订单不是待支付状态"),

    BS_ORDER_NOT_EXIST(12417, "业务订单不存在"),

    BS_CHANGING_IMPORTENT_INFO(12418, "重要信息变更期"),

    BS_TO_UC_QUERY_CUST_STATUS_ERROR(12419, "查询客户状态异常"),

    BS_DVIDEND_YEAR_RATE_IS_EXIST(12420, "年度分红比率已存在"),

    // 业务文档管理start12485
    BS_SAVE_BIZ_FILE_ERROR(12485, "保存示例图片文档异常"),

    BS_EXISTS_USED_DOC(12486, "已存在使用中的文档"),

    BS_UPDATE_BIZ_FILE_STATUS_ERROR(12487, "更新文档状态异常"),

    BS_MODIFY_BIZ_FILE_ERROR(12488, "修改业务文件异常"),

    BS_EXISTS_DOC_CODE(12489, "文档标识已存在"),

    BS_QUERY_BIZ_DOC_LIST_ERROR(12490, "查询办理业务列表异常"),

    BS_QUERY_NORMAL_DOC_LIST_ERROR(12491, "查询常用业务列表异常"),

    BS_CLOSE_ORDER_IS_APPR(12492, "订单正在审批中"),

    BS_SAVE_MSG_TPL_ERROR(12493, "保存消息模版异常"),

    BS_MSG_TPL_EXSIT(12494, "消息模版已存在"),

    BS_MSG_TPL_EXSIT_USING(12495, "已存在使用中的消息模版"),

    BS_REVIEW_MSG_TPL_ERROR(12496, "复核消息模版异常"),

    BS_MSG_TEMP_NAME_EXSIT(12497, "模版名称已存在"),

    BS_MSG_TPL_ENABLE_ERROR(12498, "启用消息模版异常"),

    BS_MSG_TPL_UNABLE_ERROR(12499, "停用消息模版异常"),

    /***************** 投资、订单、交易 12401-12500 END *****************/

    /****************** 资源业务 12501-12800 *********************/

    BS_QUOTA_NOT_ENOUGH_FOR_ALLOT(12501, "可分配的资源配额数量不足 "),

    BS_QUOTA_NOT_ENAUGH_FOR_RELEASE(12502, "可释放的空闲资源配额数量不足"),

    BS_IS_NOT_FIRST_ALLOT(12503, "不是首次配置"),

    BS_APPR_GREATER_APP_NUM(12504, "审批数量大于申请数量"),

    BS_PROVINCE_HAS_APPLYING_DATA(12505, "有申请中的数据"),

    BS_AREA_PLAT_QUOTA_APPR_FAIL(12506, "地区平台配额审批失败"),

    BS_AREA_PLAT_QUOTA_APPLY_FAIL(12507, "地区平台配额申请失败"),

    BS_CALL_CENTERPLAT_FAIL(12508, "地区平台配额申请调用总平台失败"),

    BS_PROVINCE_QUOTA_APPLY_FAIL(12509, "省配额申请失败"),

    BS_PROVINCE_QUOTA_APPR_FAIL(125010, "省配额审批失败"),

    BS_CITY_QUOTA_APPLY_FAIL(12511, "城市配额申请失败"),

    BS_CITY_QUOTA_APPR_FAIL(12512, "城市配额审批失败"),

    BS_PROVINCE_QUOTA_APPR_STATUS_ERROR(12513, "省配额审批状态错误"),

    BS_CITY_QUOTA_APPR_STATUS_ERROR(12514, "城市配额审批状态错误"),

    BS_CITY_HAS_APPLYING_DATA(12515, "有申请中的数据"),

    BS_INTENT_CUST_SUBMIT_ERROR(12520, "提交意向客户失败"),

    BS_INTENT_CUST_APPR_ERROR(12521, "处理意向客户失败"),

    BS_INTENT_CUST_EXIST_SAME(12522, "存在相同意向客户申请"),

    BS_FILING_SAVE_ENT_ERROR(12526, "保存报备企业基本信息失败"),

    BS_FILING_SAVE_SH_ERROR(12527, "保存报备企业股东信息失败"),

    BS_FILING_SAVE_APT_ERROR(12528, "保存报备企业附件失败"),

    BS_FILING_EXIST_SAME_ENT_NAME(12529, "存在完全相同的报备企业名称"),

    BS_FILING_SUBMIT_ERROR(12530, "提交报备失败"),

    BS_FILING_APPR_ERROR(12531, "审批报备失败"),

    BS_FILING_DISAGREED_REJECT_ERROR(12532, "提出异议失败"),

    BS_FILING_APPR_DISAGREED_ERROR(12533, "审批异议报备失败"),

    BS_FILING_DEL_SH_ERROR(12534, "删除报备企业股东信息失败"),

    BS_FILING_DEL_ERROR(12535, "删除报备失败"),

    BS_DECLARE_ENT_DUPLICATE(12540, "申报系统中已存在相同的企业名"),

    BS_DECLARE_NO_FILING(12541, "该服务公司未报备该被申报企业"),

    BS_DECLARE_RESNO_NULL(12542, "拟用互生号不能为空"),

    BS_DECLARE_INVALID_RESTYPE(12543, "启用资源类型为空或者与客户类型不匹配"),

    BS_DECLARE_INVALID_RES_NO(12544, "企业互生号不可用（已使用或已占用）"),

    BS_DECLARE_INVALID_CUSTTYPE(12545, "客户类型错误"),

    BS_DECLARE_SAVE_REG_INFO_ERROR(12546, "保存申报企业系统注册信息失败"),

    BS_DECLARE_SAVE_BIZ_INFO_ERROR(12547, "保存申报企业工商登记信息失败"),

    BS_DECLARE_SAVE_CONTACT_INFO_ERROR(12548, "保存申报企业联系信息失败"),

    BS_DECLARE_SAVE_BANK_INFO_ERROR(12549, " 保存申报企业银行信息失败"),

    BS_DECLARE_SAVE_APT_INFO_ERROR(12550, "保存申报企业附件信息失败"),

    BS_DECLARE_SUBMIT_ERROR(12551, "提交申报失败"),

    BS_DECLARE_SERVICE_APPR_ERROR(12552, "服务公司审批申报失败"),

    BS_DECLARE_MANAGE_APPR_ERROR(12553, "管理公司审批申报失败"),

    BS_DECLARE_MANAGE_REVIEW_ERROR(12554, "管理公司复核申报失败"),

    BS_DECLARE_APPR_DEBT_OPEN_SYSY_ERROR(12555, "开系统欠款审核失败"),

    BS_DECLARE_OPEN_UC_ERROR(12556, "用户中心开户失败"),

    BS_DECLARE_OPEN_VAS_ERROR(12557, "开启增值服务失败"),

    BS_DECLARE_OPEN_SYS_LOCAL_ERROR(12558, "开启系统处理本地事务失败"),

    BS_DECLARE_OPEN_SYS_ERROR(12559, "开启系统失败"),

    BS_DECLARE_RESNO_NULL_IN_CITY(12560, "该城市的配额已用完，没有可用的互生号"),

    BS_DECLARE_SYSTEM_OPENED(12561, "该申报企业已开启系统"),

    BS_DECLARE_PICK_RESNO_ERROR(12562, "选互生号失败"),

    BS_DECLARE_AFTER_PAY_UPDATE_ERROR(12563, "付款成功后更新申报相关信息失败"),

    BS_DECLARE_CAN_NOT_DEL(12564, "状态不是待提交，不能删除申报"),

    BS_DECLARE_DEL_ERROR(12565, " 删除申报失败"),

    BS_DECLARE_SAVE_POINT_SETTING_ERROR(12566, "保存积分增值点失败"),

    BS_DECLARE_RESOLVE_RESFEE_ERROR(12567, "分解资源费失败"),

    BS_DECLARE_REJECT_OPEN_SYS_ERROR(12568, "拒绝开启系统失败"),

    BS_DECLARE_REAPPLY_APPLYING(12569, "不能重新提交正在申报的申请"),

    BS_DECLARE_REAPPLY_ERROR(12570, "重新提交申报申请失败"),

    BS_DECLARE_NO_PICK_RESNO(12571, "没有选择互生号"),

    BS_DECLARE_LINK_MAN_NULL(12572, "申报企业联系信息为null"),

    BS_DECLARE_LINK_MAN_NAME_EMPTY(12573, "联系人姓名为空"),

    BS_DECLARE_LINK_MAN_PHONE_EMPTY(12574, "联系人电话为空"),

    BS_DECLARE_LINK_MAN_FILE_EMPTY(12575, "联系人授权委托书为空"),

    BS_AUTH_PER_DUPLICATE(12591, "存在正在审批的个人实名认证申请"),

    BS_AUTH_PER_SUBMIT_ERROR(12592, "申请个人实名认证失败"),

    BS_AUTH_PER_MODIFY_ERROR(12593, "修改个人实名认证失败"),

    BS_AUTH_PER_APPR_ERROR(12594, "审批个人实名认证失败"),

    BS_AUTH_PER_REVIEW_ERROR(12595, "复核个人实名认证失败"),

    BS_AUTH_ENT_DUPLICATE(12596, "存在正在审批的企业实名认证申请"),

    BS_AUTH_ENT_SBUMIT_ERROR(12597, "申请企业实名认证失败"),

    BS_AUTH_ENT_MODIFY_ERROR(12598, "修改企业实名认证失败"),

    BS_AUTH_ENT_APPR_ERROR(12599, "审批企业实名认证失败"),

    BS_AUTH_ENT_REVIEW_ERROR(12600, "复核企业实名认证失败"),

    BS_CHANGE_PER_DUPLICATE(12611, "存在正在审批的个人重要信息变更申请"),

    BS_CHANGE_PER_SUBMIT_ERROR(12612, "申请个人重要信息变更失败"),

    BS_CHANGE_PER_MODIFY_ERROR(12613, "修改个人重要信息变更失败 "),

    BS_CHANGE_PER_APPR_ERROR(12614, "审批个人重要信息变更失败"),

    BS_CHANGE_PER_REVIEW_ERROR(12615, "复核个人重要信息变更失败"),

    BS_CHANGE_ENT_DUPLICATE(12616, "存在正在审批的企业重要信息变更申请"),

    BS_CHANGE_ENT_SUBMIT_ERROR(12617, "申请企业重要信息变更失败"),

    BS_CHANGE_ENT_MODIFY_ERROR(12618, "修改企业重要信息变更失败"),

    BS_CHANGE_ENT_APPR_ERROR(12619, "审批企业重要信息变更失败 "),

    BS_CHANGE_ENT_REVIEW_ERROR(12620, "复核企业重要信息变更失败"),

    BS_QUIT_SUBMIT_ERROR(12631, "申请成员企业注销失败"),

    BS_QUIT_DUPLICATE(12632, "重复提交成员企业注销申请"),

    BS_QUIT_SERVICE_APPR_ERROR(12633, "服务公司审批成员企业注销失败"),

    BS_QUIT_PLAT_APPR_ERROR(12634, "平台审批成员企业注销失败"),

    BS_QUIT_PLAT_REVIEW_ERROR(12635, "平台复核成员企业注销失败"),

    BS_QUIT_EXIST_APPLYING_CHANGE(12636, "存在正在审批的重要信息变更"),

    BS_QUIT_ARREAR_ANNUAL_FEE(12637, "该企业欠年费"),

    BS_QUIT_EXIST_UNCOMPLETE_ORDER(12638, "该企业存在未完成的订单"),

    BS_QUIT_CANCEL_BM_ERROR(12639, "注销增值系统账户失败 "),

    BS_QUIT_HSB_TO_HB_ERROR(12640, "互生币转货币失败"),

    BS_QUIT_BANK_TRANS_OUT_ERROR(12641, "提交银行转账失败"),

    BS_QUIT_CANCEL_UC_ERROR(12642, "注销用户中心账户失败"),

    BS_QUIT_RELEASE_RESNO_ERROR(12643, "释放互生号失败"),

    BS_POINT_ACT_DUPLICATE(12651, "存在正在审批的积分活动申请"),

    BS_POINT_ACT_SUBMIT_ERROR(12652, "申请积分活动失败"),

    BS_POINT_ACT_SERVICE_APPR_ERROR(12653, "服务公司审批积分活动失败"),

    BS_POINT_ACT_PLAT_APPR_ERROR(12654, "平台审批积分活动失败"),

    BS_POINT_ACT_PLAT_REVIEW_ERROR(12655, "平台复核积分活动失败"),

    BS_POINT_ACT_UNCOMPLETE_TOOL_ORDER(12656, "存在未完成的工具订单"),

    BS_DEBIT_RES_FEE_NO_RETURN(12657, "欠款开启系统资源费未归还"),

    BS_CONTRACT_TPL_SAVE_ERROR(12661, "保存合同模板失败"),

    BS_CONTRACT_TPL_APPR_ERROR(12662, "审批合同模板失败"),

    BS_CONTRACT_TPL_ENABLE_ERROR(12663, "启用合同模板失败"),

    BS_CONTRACT_TPL_DISABLE_ERROR(12664, "停用合同模板失败"),

    BS_CONTRACT_SEAL_ERROR(12665, "合同盖章失败"),

    BS_CONTRACT_SEND_ERROR(12666, "合同发放失败"),

    BS_CONTRACT_GENERATE_ERROR(12667, "生成合同失败"),

    BS_CONTRACT_TMP_NOT_FOUND(12668, "未找到可用的合同模板"),

    BS_CRE_TPL_SAVE_ERROR(12685, "保存证书模板失败"),

    BS_CRE_TPL_APPR_ERROR(12686, "审批证书模板失败"),

    BS_CRE_TPL_ENABLE_ERROR(12687, "启用证书模板失败"),

    BS_CRE_TPL_DISABLE_ERROR(12688, "停用证书模板失败"),

    BS_CRE_SEAL_ERROR(12689, "证书盖章失败"),

    BS_CRE_SEND_ERROR(12690, "证书发放失败"),

    BS_CRE_GENERATE_ERROR(12691, "生成证书失败"),

    BS_CRE_TMP_NOT_FOUND(12692, "未找到可用的证书模板"),

    BS_CLOSE_OPEN_SUBMIT_CLOSE_ERROR(12696, "申请关闭企业系统失败"),

    BS_CLOSE_OPEN_SUBMIT_OPEN_ERROR(12697, "申请开启企业系统失败"),

    BS_CLOSE_OPEN_APPR_ERROR(12698, "审批开关系统失败"),

    BS_CLOSE_OPEN_EXIST_APPLYING(12699, "存在正在审批的开关系统申请"),

    BS_SHAREHOLDER_IS_EXIST(12700, "股东已经存在"),

    BS_BM_PNODE_NO_DATA(12701, "增值父节点未设置"),

    BS_DECLARE_FILE_NOT_UPDATE(12702, "申报企业未上传文件资料"),

    BS_DECLARE_NEED_VENTURE_BEFRIEND_PROTOCOL(12703, "申报企业缺少创业帮扶文件"),

    BS_DECLARE_NEED_BASE_FILE(12704, "申报企业缺少文件资料"),

    BS_ID_NO_EXIST(12705, "证件已被使用"),

    BS_TEMPLATE_DB_ERROR(12706, "证书模板数据库操作异常"),

    BS_SEND_AUTH_CODE_ERROR(12707, "发送申报办理授权码出现异常"),

    BS_SYNC_UC_OPEN_ENT_FAIL(12708, "开启系统同步UC失败"),

    BS_SYNC_BM_OPEN_ENT_FAIL(12709, "开启系统同步BM失败"),

    BS_RES_FEE_ALLOC_FAIL(12710, "资源费分配记账失败"),

    BS_CONTRACT_DB_ERROR(12711, "合同DB错误"),

    BS_DUPLICATION_IN_UC(12712, "在UC中存在重复数据"),

    BS_TEMPLATE_EXIST_WAIT_DEAL(12713, "存在同类型处于已启用状态或停用待复核状态的模版"),

    BS_CASH_TRANS_OUT_DOING(12714, "存在正在办理的银行转账申请"),

    /****************** 资源业务 12501-12800 END *********************/
    BS_HSB_DEDUCTION_DB_ERROR(12901, "互生币扣除DB错误"),

    BS_TRANS_PWD_SEND_FAIL(12902, "重置交易密码发送短信失败"),

    /**
     * }
     */

    ;
    /**
     * 错误代码
     */
    private int code;

    /**
     * 错误描述
     */
    private String erroDesc;

    BSRespCode(int code, String desc) {
        this.code = code;
        this.erroDesc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return erroDesc;
    }

    public static void main(String[] args)

    {
        /**
         * 检查代码是否有重复
         */
        Set<Integer> codeSet = new HashSet<Integer>();
        for (BSRespCode item : BSRespCode.values())
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
