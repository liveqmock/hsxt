/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.utils;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetail;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 年费区间工具类
 *
 * @Package :com.gy.hsxt.bs.annualfee.utils
 * @ClassName : AnnualAreaUtils
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/13 17:43
 * @Version V3.0.0.0
 */
public abstract class AnnualAreaUtils {

    /**
     * 根据目前年费有效期解析所有的年费计费单的年费区间
     * 如：2015-01-01 ~ 2015-12-31(2016-01-01 减一天)
     *
     * @param annualFeeInfo 年费信息实体
     * @param ahead         是否预缴
     * @return entAnnualFeeDetail 年费计费单实体
     * @throws HsException 异常
     */
    public static List<AnnualFeeDetail> parseAnnualAreas(AnnualFeeInfo annualFeeInfo, boolean ahead) throws HsException {
        //校验参数实体
        HsAssert.notNull(annualFeeInfo, RespCode.PARAM_ERROR, "解析年费区间--所传参数[annualFeeInfo]不能为空");
        // 日期字符为空返回null
        HsAssert.hasText(annualFeeInfo.getEndDate(), RespCode.PARAM_ERROR, "年费截止日期[endDate]为空");

        List<AnnualFeeDetail> annualFeeDetails = new ArrayList<>();

        int arrearYeas = arrearYeas(annualFeeInfo.getEndDate());

        int len = arrearYeas;
        //可以缴年费，说明大于0年的年费提示日期;当天小于年费提示日期，则设置上一年的区间
        if (compareToWarnDate(annualFeeInfo.getWarningDate(), arrearYeas) >= 0 && ahead) {
            len++;
        }
        for (int i = 0; i < len; i++) {
            AnnualFeeDetail annualFeeDetail = new AnnualFeeDetail();

            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.StringToDate(annualFeeInfo.getEndDate()));// 年费截止日期转换
            cal.add(Calendar.YEAR, i);

            cal.add(Calendar.DATE, +1);// 日期加1天
            Date newStartDate = cal.getTime();
            annualFeeDetail.setStartDate(DateUtil.DateToString(newStartDate));// 年费区间-开始日期

            cal.add(Calendar.YEAR, +1);// 日期加1年
            cal.add(Calendar.DATE, -1);// 日期减1天
            Date newEndDate = cal.getTime();
            annualFeeDetail.setEndDate(DateUtil.DateToString(newEndDate));// 年费区间-结束日期

            annualFeeDetails.add(annualFeeDetail);
        }

        return annualFeeDetails;
    }

    /**
     * 根据目前年费有效期获取年费区间的开始日期
     *
     * @param annualFeeInfo 年费信息
     * @return String 开始日期
     * @throws HsException 异常
     */
    public static String obtainAnnualAreaStartDate(AnnualFeeInfo annualFeeInfo) throws HsException {
        //校验参数实体
        HsAssert.notNull(annualFeeInfo, RespCode.PARAM_ERROR, "解析年费区间--所传参数[annualFeeInfo]不能为空");
        // 日期字符为空返回null
        HsAssert.hasText(annualFeeInfo.getEndDate(), RespCode.PARAM_ERROR, "年费截止日期[endDate]为空");

        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.StringToDate(annualFeeInfo.getEndDate()));// 年费截止日期转换
        cal.add(Calendar.DATE, +1);// 日期加1天
        if (compareToWarnDate(annualFeeInfo.getWarningDate(), 0) < 0) {
            cal.add(Calendar.YEAR, -1);
        }

        return DateUtil.DateToString(cal.getTime());
    }

    /**
     * 根据目前年费有效期获取年费区间的结束日期
     *
     * @param annualFeeInfo 年费信息
     * @param ahead         是否预缴
     * @return 日期
     * @throws HsException
     */
    public static String obtainAnnualAreaEndDate(AnnualFeeInfo annualFeeInfo, boolean ahead) throws HsException {
        //校验参数实体
        HsAssert.notNull(annualFeeInfo, RespCode.PARAM_ERROR, "解析年费区间--所传参数[annualFeeInfo]不能为空");
        // 日期字符为空返回null
        HsAssert.hasText(annualFeeInfo.getEndDate(), RespCode.PARAM_ERROR, "年费截止日期[endDate]为空");

        int arrearYeas = arrearYeas(annualFeeInfo.getEndDate());

        int len = arrearYeas;
        //可以缴年费，说明大于0年的年费提示日期;当天小于年费提示日期，则设置上一年的区间
        if (compareToWarnDate(annualFeeInfo.getWarningDate(), arrearYeas) >= 0 && ahead) {
            len++;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.StringToDate(annualFeeInfo.getEndDate()));// 年费截止日期转换
        cal.add(Calendar.YEAR, len);// 日期加1天

        return DateUtil.DateToString(cal.getTime());
    }

    /**
     * 比较当天与年费提示日期的大小
     * 可以缴年费，说明大于0年的年费提示日期。
     * 若 当天比提示日期 大， 则可交下一年的年费；不然只能交年费欠款。
     *
     * @param warnDate 年费提示日期
     * @param years    欠费年数
     * @return int 比较结果
     */
    public static int compareToWarnDate(String warnDate, int years) {
        //校验所传参数
        HsAssert.hasText(warnDate, RespCode.PARAM_ERROR, "年费提示日期为空");
        //获取当天日期
        Date today = DateUtil.getCurrentDate(DateUtil.DEFAULT_DATE_FORMAT);
        //转换提示日期
        Date toDate = DateUtil.StringToDate(warnDate);
        if (years > 0) {
            //添加欠费年数
            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            cal.add(Calendar.YEAR, years);
            toDate = cal.getTime();
        }
        return today.compareTo(toDate);
    }

    /**
     * 日期比较
     *
     * @param desDate 比较日期
     * @param toDate  被比较日期
     * @return int
     */
    public static int compareToDate(String desDate, String toDate) {
        //校验所传参数
        HsAssert.hasText(desDate, RespCode.PARAM_ERROR, "比较日期为空");
        HsAssert.hasText(toDate, RespCode.PARAM_ERROR, "被比较日期为空");

        Date des = DateUtil.StringToDate(desDate);
        Date to = DateUtil.StringToDate(toDate);
        return des.compareTo(to);
    }

    /**
     * 今天的相邻日期
     *
     * @param days 天数
     * @return date
     */
    public static String nextToday(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getCurrentDate());// 年费截止日期转换
        cal.add(Calendar.DATE, days);// 日期加几天

        return DateUtil.DateToString(cal.getTime());
    }


    /**
     * 欠年费数
     *
     * @param endDate 截至日期
     * @return number
     */
    public static int arrearYeas(String endDate) {
        //截止日期
        Date end = DateUtil.StringToDate(endDate);

        int endYear = Integer.valueOf(StringUtils.left(endDate, 4));

        //获取当天日期
        Date today = DateUtil.getCurrentDate(DateUtil.DEFAULT_DATE_FORMAT);

        int todayYear = Integer.valueOf(StringUtils.left(DateUtil.getCurrentDateTime(), 4));

        int years = todayYear - endYear;

        if (years < 0) {//不欠年费
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(end);
        cal.add(Calendar.YEAR, years);

        return cal.getTime().compareTo(today) >= 0 ? years : (years + 1);
    }
}
