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

package com.gy.hsxt.common.constant;

/**
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: AcrossPlatBizCode
 * @Description: 跨平台业务代码定义 TO_CENTER_开头的代表向总平台发送请求的业务
 *               TO_REGION_开头的代表向地区平台发送请求的业务
 *               建议在注释上加上目标子系统代码，方便配置初始化数据：业务代码与子系统路由关系
 * 
 * @author: yangjianguo
 * @date: 2015-11-20 下午2:24:19
 * @version V1.0
 */
public enum AcrossPlatBizCode {
    /** 向总平台申请一级区域配额,目标子系统：RES **/
    TO_CENTER_APPLY_QUOTA,
    /** 向总平台申请释放一级区域配额,目标子系统：RES **/
    TO_CENTER_RELEASE_QUOTA,
    /** 向地区平台分配一级区域配额,目标子系统：BS **/
    TO_REGION_ALLOT_QUOTA,
    /** 向地区平台同步管理公司最大配额数,目标子系统：BS **/
    TO_REGION_INIT_MAX_QUOTA,

    /** 向地区平台通知开启管理公司,目标子系统：UC **/
    TO_REGION_INIT_M_ENT,

    /** 向地区平台通知开启平台企业信息,目标子系统：UC **/
    TO_REGION_INIT_PLAT_ENT,

    /** 向地区平台通知异地登陆,目标子系统：UC **/
    TO_UC_LOGIN,

    /** 向总平台获取增量更新数据,目标子系统：GCS **/
    TO_CENTER_GET_NEW_DATA,
    /** 向地区平台通知更新,目标子系统：LCS **/
    TO_REGION_NOTIFY_UPDATE,

    /** 向总平台查询增值节点信息,目标子系统：BM **/
    TO_CENTER_GET_BM_NODE_INFO,
    /** 向总平台发起开户请求,目标子系统：BM **/
    TO_CENTER_BM_OPEN_ENT,
    /** 向总平台发起销户请求,目标子系统：BM **/
    TO_CENTER_BM_CLOSE_ENT,
    /** 通知总平台获取增值文件,目标子系统：FSS **/
    TO_CENTER_NOTIFY_BM_FILE,
    /** 通知地区平台获取增值分配文件,目标子系统：FSS **/
    TO_REGION_NOTIFY_BM_FILE,
    /** 文件同步系统远程回调通知，目标子系统：FSS **/
    TO_FSS_REMOTE_BACK_NOTIFY,
    /** 本地卡异地积分，目标子系统：PS **/
    LOCAL_CARD_REMOTE_POINT,
    /** 本地卡异地积分撤单，目标子系统：PS **/
    LOCAL_CARD_REMOTE_POINT_CANCEL,
    /** 本地卡异地积分退货，目标子系统：PS **/
    LOCAL_CARD_REMOTE_POINT_BACK,
    /** 本地卡异地积分冲正，目标子系统：PS **/
    LOCAL_CARD_REMOTE_POINT_REVERSE,
    /** 跨平台代兑互生币(异地消费者)，目标子系统：AO **/
    PROXY_HSB_FOR_REMOTE_P,
    /** 跨平台代兑互生币冲正(异地消费者)，目标子系统：AO **/
    PROXY_HSB_FOR_REMOTE_P_REVERSE,
    /** 校验推广节点和挂载节点的上下级关系 **/
    TO_CENTER_BM_PLAT_CHECK_SUB,
    ;
}
