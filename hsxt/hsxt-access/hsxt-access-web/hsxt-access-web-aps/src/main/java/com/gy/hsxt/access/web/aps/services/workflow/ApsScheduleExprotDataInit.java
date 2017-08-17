/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.workflow;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.enumtype.WorkTypeStatus;

/***
 * 值班计划导出数据初始化
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.workflow
 * @ClassName: MembersScheduleExprotHandle
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-6 下午8:46:41
 * @version V1.0
 */
public class ApsScheduleExprotDataInit implements Serializable {
    private static final long serialVersionUID = -991171237145063883L;

    /**
     * 月份天数
     */
    public int monthDays = 31;

    /**
     * 前多固定列数
     */
    public int beforeSurplusColumn = 2;

    /**
     * 后多固定列数
     */
    public int afterSurplusColumn = 1;

    /**
     * 标题列行数
     */
    public int titleRowNum = 2;

    /**
     * 总列数
     */
    public int columnNum;

    /**
     * 列标题
     */
    public String[] cellTitle;

    /**
     * 内容列
     */
    public Object[][] content;

    /**
     * 值班员循环数统计
     */
    private int circleNum = 0;

    /**
     * 请求参数类
     */
    private ApsMembersScheduleBean amsb;

    /**
     * 组员计划数据集合
     */
    private Map<String, Object> oMap;

    /**
     * 值班员列表
     */
    private List<Operator> olist;

    /**
     * 值班员值班计划
     */
    private List<ScheduleOpt> solist;

    /**
     * map转换值班员、值班员计划
     */
    void convertMap() {
        this.olist = (List<Operator>) this.oMap.get("operators");
        this.solist = (List<ScheduleOpt>) this.oMap.get("scheduleOpts");
    }

    public ApsScheduleExprotDataInit() {

    }

    /**
     * 构造函数初始化
     * 
     * @param _amsb
     * @param _oMap
     */
    public ApsScheduleExprotDataInit(ApsMembersScheduleBean _amsb, Map<String, Object> _oMap) {
        this.amsb = _amsb;
        this.oMap = _oMap;

        this.getMonth();

        if (this.oMap != null)
        {
            // 拆分未值班员、值班计划
            this.convertMap();

            // 初始化列、行数
            this.initCellRows();

            // 初始化列显示标题
            this.initCellTitle();

            // 处理结果
            members();
        }

    }

    /**
     * 初始化列、行数
     */
    void initCellRows() {

        // 总列数
        columnNum = this.beforeSurplusColumn + this.monthDays + this.afterSurplusColumn;

        // 初始化总列数并给标题命名

        cellTitle = new String[columnNum];

        // 初始化总行数和行列数
        content = new Object[this.olist.size() + this.titleRowNum][columnNum];

        // 初始化固定标题列
        cellTitle[0] = amsb.getYear() + "年" + amsb.getMonth() + "月" + amsb.getGroupName() + "排班计划";
        content[0][0] = "姓名/日期";
        content[0][this.columnNum - 1] = "调休天数";
    }

    /**
     * 初始化列标题
     */
    void initCellTitle() {

        for (int i = 0; i < this.monthDays; i++)
        {
            content[0][this.beforeSurplusColumn + i] = (i + 1) + "";
            content[1][this.beforeSurplusColumn + i] = getWeek(i + 1).substring(2);
        }
    }

    public String getWeek(int day) {
        // 再转换为时间
        Date date = DateUtil.StringToDate(amsb.getYear() + "-" + amsb.getMonth() + "-" + day);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE",Locale.SIMPLIFIED_CHINESE).format(c.getTime());
    }

    /**
     * 获取组员
     * 
     * @param amsb
     * @param o
     * @param odtList
     * @return
     */
    void members() {
        // 调休天数
        int offNum = 0;

        // 循环组员列表
        for (Operator o : this.olist)
        {
            // 组员值班计划
            offNum = memberSchedule(o);

            content[circleNum + this.titleRowNum][0] = circleNum + 1; // 序号
            content[circleNum + this.titleRowNum][1] = o.getOperatorName(); // 值班员
            content[circleNum + this.titleRowNum][this.columnNum - 1] = offNum; // 值班员月份总调休天数

            circleNum++;
        }
    }

    /**
     * 获取组员值班计划
     * 
     * @param amsb
     * @return
     */
    int memberSchedule(Operator o) {

        int offNum = 0; // 调休天数

        if (this.solist != null)
        {
            // 组员值班类型
            for (ScheduleOpt so : this.solist)
            {
                if (so.getOptCustId().equals(o.getOptCustId()))
                {
                    // 组员正式排班
                    if (so.getWorkType() != null)
                    {
                        // 判断为调休
                        if (so.getWorkType().intValue() == WorkTypeStatus.ON_HOLIDAY.getCode().intValue())
                        {
                            offNum++;
                        }

                        // 获取排班类型名称
                        content[circleNum + this.titleRowNum][this.beforeSurplusColumn - 1
                                + Integer.parseInt(so.getPlanDate())] = WorkTypeStatus.getDescribe(so.getWorkType());
                    }
                }
            }
        }
        return offNum;
    }

    /**
     * 计算月份天数
     * 
     * @param amsb
     * @return
     */
    void getMonth() {
        int year = Integer.parseInt(amsb.getYear());
        
        // 判断闰年
        boolean isLeapYear = (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? true : false);

        // 判断月份
        switch (Integer.parseInt(amsb.getMonth()))
        {
        case 4:
        case 6:
        case 9:
        case 11:
            monthDays = 30;
            break;
        case 2:
            if (isLeapYear)
                monthDays = 29;
            else
                monthDays = 28;
            break;
        }
    }
}
