/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.pos.constant;

import java.util.HashMap;
import java.util.Map;

import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.lcs.LCSErrorCode;

/**
 * 
 * @Package: com.gy.hsxt.access.pos.constant
 * @ClassName: PosRespCodeMap
 * @Description: 保存各个错误信息在POS Server
 * @author: weiyq
 * @date: 2015-12-28 下午3:37:17
 * @version V1.0
 * 
 */
public class PosRespCodeMap {
    private static Map<Integer, PosRespCode> errorCodeMap = new HashMap<Integer, PosRespCode>();

    public static void init() {
        errorCodeMap.put(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(), PosRespCode.NO_ENT_CARDPOINT);
        errorCodeMap.put(ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), PosRespCode.NO_FOUND_ACCT);
        errorCodeMap.put(ErrorCodeEnum.RES_NO_IS_NOT_FOUND.getValue(), PosRespCode.NO_FOUND_ACCT);
        errorCodeMap.put(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue(), PosRespCode.CHECK_CARDORPASS_FAIL);
        errorCodeMap.put(ErrorCodeEnum.PWD_TRADE_ERROR.getValue(), PosRespCode.CHECK_PASS_FAIL);
        errorCodeMap.put(ErrorCodeEnum.HS_CARD_CODE_IS_WRONG.getValue(), PosRespCode.INVALID_CARD);
        errorCodeMap.put(ErrorCodeEnum.HSCARD_IS_LOSS.getValue(), PosRespCode.INVALID_CARD_LOSS);
        //start--commented by liuzh on 2016-06-04 有多条DEVICE_NOT_SIGN
        //errorCodeMap.put(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(), PosRespCode.AGAIN_SIGNIN);
        //end----commented by liuzh on 2016-06-04
        errorCodeMap.put(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(), PosRespCode.NO_FOUND_DEVICE);
        errorCodeMap.put(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(), PosRespCode.AGAIN_SIGNIN);
        errorCodeMap.put(ErrorCodeEnum.POS_MATCH_MAC_FAILED.getValue(), PosRespCode.MAC_CHECK);
        errorCodeMap.put(RespCode.PS_DB_SQL_ERROR.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_AC_ERROR.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_NO_DATA_EXIST.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_DATA_EXIST.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_PARAM_ERROR.getCode(), PosRespCode.REQUEST_PACK_PARAM_ERR);
        errorCodeMap.put(RespCode.PS_TRANSTYPE_ERROR.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_FILE_NOT_FOUND.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_HAS_THE_CANCELLATION.getCode(), PosRespCode.REPEAT_CANCLE);
        errorCodeMap.put(RespCode.PS_HAS_THE_BACKATION.getCode(), PosRespCode.REPEAT_BACK);
        errorCodeMap.put(RespCode.PS_HAS_BEEN_REVERSED.getCode(), PosRespCode.REPEAT_REVERSE);
        errorCodeMap.put(RespCode.PS_ORDER_NOT_FOUND.getCode(), PosRespCode.NO_FOUND_HSEC_ORDER);
        errorCodeMap.put(RespCode.PS_BATSETTLE_ERROR.getCode(), PosRespCode.CHECK_SETTLE_FAIL);
        errorCodeMap.put(RespCode.PS_POINT_NO_MIN.getCode(), PosRespCode.CHECK_AMT_TOO_SMALL);
        errorCodeMap.put(RespCode.PS_BACK_AMOUNT_ERROR.getCode(), PosRespCode.RA_GREATER_THAN_TA);//退货金额不能大于原订单金额,无法退货
        errorCodeMap.put(AOErrorCode.AO_AMOUNT_MORE_THEN_MAX.getCode(), PosRespCode.HSB_PROXY_RECHARGE_MAX_AMT_ERROR);
        errorCodeMap.put(AOErrorCode.AO_REPET_COMMIT_REVERSE.getCode(), PosRespCode.REPEAT_REVERSE);
        errorCodeMap.put(AOErrorCode.AO_DAILY_TIMES_MORE_THEN_MAX.getCode(), PosRespCode.HSB_ENT_RECHARGE_MAX_TIMES_ADAY_ERROR);
        //start--modified by liuzh on 2016-05-09
//        errorCodeMap.put(RespCode.PS_HSB_CONSUMER_PAYMENT_MAX.getCode(), PosRespCode.HSB_PAY_MAX_OVER_ERROR);//互生币单笔支付超限
//        errorCodeMap.put(RespCode.PS_HSB_CONSUMER_PAYMENT_DAY_MAX.getCode(), PosRespCode.HSB_PAY_DAY_MAX_OVER_ERROR);//互生币支付每日超限
        errorCodeMap.put(RespCode.PS_HSB_CONSUMER_PAYMENT_MAX.getCode(), PosRespCode.HSB_CONSUMER_PAYMENT_MAX);//互生币单笔支付超限
        errorCodeMap.put(RespCode.PS_HSB_CONSUMER_PAYMENT_DAY_MAX.getCode(), PosRespCode.HSB_CONSUMER_PAYMENT_DAY_MAX);//互生币支付每日超限
        //end--modified by liuzh on 2016-05-09
        errorCodeMap.put(RespCode.APS_USER_PWD_USER_NO_EXIST.getCode(), PosRespCode.NO_FOUND_ACCT);
        errorCodeMap.put(ErrorCodeEnum.HSCARD_NOT_FOUND.getValue(), PosRespCode.NO_FOUND_ACCT);
        errorCodeMap.put(RespCode.GL_DATA_NOT_FOUND.getCode(), PosRespCode.NO_FOUND_ACCT);
        errorCodeMap.put(RespCode.AC_BALANCE_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_CUST_HSB);
        errorCodeMap.put(RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_ENT_HSB);
        errorCodeMap.put(RespCode.AC_CORRECTED.getCode(), PosRespCode.SUCCESS);
        errorCodeMap.put(RespCode.PS_EARNEST_SETTLE_FAIL.getCode(), PosRespCode.EARNEST_SETTLE_FAIL);//定金结算失败
        errorCodeMap.put(RespCode.PS_ERROR_IN_TRANSTYPE.getCode(), PosRespCode.TRADE_TYPE_MATCH_ERROR);//撤单，互生币交易选成积分，或相反等
        //start--modified by liuzh 2016-04-27
        //errorCodeMap.put(AOErrorCode.AO_CHANGING_IMPORTENT_INFO.getCode(), PosRespCode.CHECK_ENT_STATUS_FAIL);//重要信息变更期的企业不可进行代兑业务
        errorCodeMap.put(AOErrorCode.AO_CHANGING_IMPORTENT_INFO.getCode(), PosRespCode.AO_CHANGING_IMPORTENT_INFO);   
        //end--modified by liuzh on 2016-04-27
        errorCodeMap.put(LCSErrorCode.DATA_NOT_FOUND.getCode(), PosRespCode.INVALID_CARD);
        //start--added by liuzh
        errorCodeMap.put(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(), PosRespCode.NO_FOUND_ENT);
        errorCodeMap.put(EtErrorCode.AO_DAILY_AMOUNT_MORE_THEN_MAX.getErrorCode(), PosRespCode.AO_DAILY_AMOUNT_MORE_THEN_MAX);
        errorCodeMap.put(RespCode.AC_REFUND_OVER.getCode(), PosRespCode.AC_REFUND_OVER);
        errorCodeMap.put(RespCode.AC_ENT_JF_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_ENT_POINT);
        errorCodeMap.put(RespCode.AC_ENT_HSB_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_ENT_HSB);
        errorCodeMap.put(RespCode.AC_ENT_HB_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_ENT_CASH);
        errorCodeMap.put(RespCode.AC_PER_JF_DEFICIENCY.getCode(), PosRespCode.CHECK_POINT_BALANCE_FAIL);
        errorCodeMap.put(RespCode.AC_PER_HSB_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_CUST_HSB);
        errorCodeMap.put(RespCode.AC_PER_HB_DEFICIENCY.getCode(), PosRespCode.INSUF_BALANCE_PER_CASH);
        errorCodeMap.put(RespCode.AC_REFUND_OVER_AVALIABLE.getCode(), PosRespCode.AC_REFUND_OVER_AVALIABLE);
        errorCodeMap.put(RespCode.PS_HAS_ISSETTLE_CANCELLATION.getCode(), PosRespCode.ISSETTLE_CANCELLATION);
        errorCodeMap.put(RespCode.PS_EARNEST_SETTLE_BACKATION.getCode(), PosRespCode.EARNEST_SETTLE_BACKATION);
        errorCodeMap.put(ErrorCodeEnum.TRANS_PWD_NOT_SET.getValue(), PosRespCode.TRANS_PWD_NOT_SET);
        //start--added by liuzh on 2016-06-07
        errorCodeMap.put(ErrorCodeEnum.TRADEPWD_USER_LOCKED.getValue(), PosRespCode.TRADEPWD_USER_LOCKED);
        //end--added by liuzh on 2016-06-07
        
        //start--added by liuzh on 2016-07-04
        errorCodeMap.put(RespCode.PS_NO_DEDUCTION_VOUCHER.getCode(), PosRespCode.DEDUCTION_NO_DEDUCTION_VOUCHER);
        errorCodeMap.put(RespCode.PS_DEDUCTION_VOUCHER_MAX_NUM.getCode(), PosRespCode.DEDUCTION_VOUCHER_MAX_NUM);
        errorCodeMap.put(RespCode.PS_REBATE_AMOUNT_OVERSIZE.getCode(), PosRespCode.DEDUCTION_REBATE_AMOUNT_OVERSIZE);
        errorCodeMap.put(RespCode.PS_DEDUCTION_VOUCHER_NOT_ENOUGH.getCode(), PosRespCode.DEDUCTION_VOUCHER_NOT_ENOUGH);
        
        errorCodeMap.put(RespCode.PS_USE_COUPON_FAIL.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_USE_COUPON_CANCEL_FAIL.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_USER_RESOURCE_NULL_OR_PARAM_ERROR.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_ENT_RESOURCE_NULL_OR_PARAM_ERROR.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_TRANS_NO_NULL.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_NUMBER_MUST_GREATER_THAN_ZERO.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_TRANS_NO_ERROR.getCode(), PosRespCode.POS_CENTER_FAIL);
        errorCodeMap.put(RespCode.PS_USER_DEDUCTION_VOUCHER_TRANS_NOT_EXIT.getCode(), PosRespCode.POS_CENTER_FAIL);
        //end--added by liuzh on 2016-07-04
        
        //end--added by liuzh
        
    }
    /**
     * 其它系统异常代码 转换成pos使用
     * @param errorCode
     * @return.
     */
    public static PosRespCode codeConverts(int errorCode){
        PosRespCode ret = errorCodeMap.get(errorCode);
        if(ret==null){
            return PosRespCode.OTHER_SERVER_FAIL;
        }else{
            return ret;
        }
    }
}
