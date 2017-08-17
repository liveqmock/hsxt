package com.gy.hsxt.ps.settlement.handle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.settlement.bean.BatchSettle;
import com.gy.hsxt.ps.settlement.bean.BatchUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author chenhz
 * @version v3.0
 * @description 批结算
 * @createDate 2015-7-29 下午6:30:38
 * @Company 深圳市归一科技研发有限公司
 */
public class PosSettleHandle {

    /**
     * 对比批结算
     *
     * @param batSettle
     * @param bs
     * @return
     * @throws HsException
     */
    public static int settleCompare(BatSettle batSettle, BatchSettle bs) {
        // 对账结果1 一致 ，2 不一致
        int settleResult = Constants.SETTLE_RESULT1;
        if (batSettle.getPointCnt().intValue() != bs.getDetPoinsCnt().intValue() //积分笔数
                || new BigDecimal(batSettle.getPointSum()).compareTo(bs.getDetPoinsSum()) != 0//积分总数
                || batSettle.getPointCancelCnt().intValue() != bs.getDetPcancelCnt().intValue()+bs.getDetPbackCnt().intValue()//积分撤单笔数
                || new BigDecimal(batSettle.getPointCancelSum()).compareTo(Compute.add(bs.getDetPcancelSum(),bs.getDetPbackSum())) != 0//积分撤单总数
                //|| batSettle.getPointBackCnt().intValue() != bs.getDetPbackCnt().intValue()//积分退货笔数
                //|| new BigDecimal(batSettle.getPointBackSum()).compareTo(bs.getDetPbackSum()) != 0//积分退货总数
                ) {
            // 对账结果1 一致 ，2 不一致
            settleResult = Constants.SETTLE_RESULT2;
        }
        bs.setSettleResult(settleResult);
        //BeanCopierUtils.copy(batSettle, bs);
        return settleResult;
    }

    /**
     * 对比批上送
     *
     * @param batUpload
     * @param list
     */
    public static void uploadCompare(List<BatUpload> batUpload, List<BatchUpload> list) {
        for (BatchUpload bu : list) {
            // 检查单笔交易单是否金额一致
            checkUpload(bu, batUpload);
        }
        // 端设备有、消费积分数据库不存在的订单记录
        uploadConvert(batUpload, list);
    }

    /**
     * 检查单笔交易单是否金额一致
     *
     * @param batUpload
     * @param list
     */
    private static void checkUpload(BatchUpload batUpload, List<BatUpload> list) {
        int j = 0;
        // 对账结果1 一致 ，2 不一致
        int settleResult = Constants.SETTLE_RESULT1;
        for (; j < list.size(); j++) {
            BatUpload bu = list.get(j);
            if (batUpload.getTransNo().equals(bu.getTransNo())) {
                if (batUpload.getTransAmount().compareTo(bu.getTransAmount()) != 0
                        || batUpload.getEntPoint().compareTo(bu.getEntPoint()) != 0
                        || batUpload.getPerPoint().compareTo(bu.getPerPoint()) != 0) {
                    settleResult = Constants.SETTLE_RESULT2;
                    noticeWarn(batUpload.getTransNo());
                }
                list.remove(j);
                break;
            }
        }
        if (j == list.size()) {
            // 对账结果1 一致 ，2 不一致
            settleResult = Constants.SETTLE_RESULT2;
            noticeWarn(batUpload.getTransNo());
        }
        batUpload.setSettleResult(settleResult);

    }

    /**
     * 终端设备有、消费积分数据库不存在的订单记录
     *
     * @param batUpload
     * @param list
     */
    private static void uploadConvert(List<BatUpload> batUpload, List<BatchUpload> list) {
        for (BatUpload bu : batUpload) {
            // 批上送实体类
            BatchUpload bt = new BatchUpload();
            // 复制对象
            BeanCopierUtils.copy(bu, bt);
            // 设置明细金额对比是否一致,2代表 不一致
            bt.setSettleResult(Constants.SETTLE_RESULT2);
            list.add(bt);
            noticeWarn(bu.getTransNo());
        }
    }

    /**
     * 发送通知告警
     *
     * @param transNo
     */
    public static void noticeWarn(String transNo) {
        System.out.println("日志警告"+transNo);
    }


    public static String getJobDate(Map<String, String> args) {
        // 获得前一天日期
        String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));

        if (!CollectionUtils.isEmpty(args)) {
            String batchDate = args.get("BATCH_DATE");

            if (StringUtils.isNotEmpty(batchDate)) {
                Date date = DateUtil.StringToDate(batchDate, DateUtil.DATE_FORMAT);
                runDate = DateUtil.DateToString(DateUtils.addDays(date, -1));
            }
        }
        return runDate;
    }

    public static String getJobMonthDate(Map<String, String> args) {
        // 获得前一天日期
        String runDateMonth = DateUtil.DateToString(DateUtil.addMonths(DateUtil.today(), -1));

        if (!CollectionUtils.isEmpty(args)) {
            String batchDateMonth = args.get("BATCH_DATE");
            if (StringUtils.isNotEmpty(batchDateMonth)) {
                Date date = DateUtil.StringToDate(batchDateMonth, DateUtil.DATE_FORMAT);
                runDateMonth = DateUtil.DateToString(DateUtils.addMonths(date, -1));
            }
        }
        return runDateMonth;
    }

}
