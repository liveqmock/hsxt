/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.enumtype.WorkTypeStatus;

/***
 * 组员值班计划处理类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.workflow
 * @ClassName: ApsMembersScheduleHandle
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-4 上午9:27:05
 * @version V1.0
 */
public class ApsMembersScheduleHandle implements Serializable {
    private static final long serialVersionUID = -3195078375292488207L;

    public Map<String, Object> retMap = new HashMap<String, Object>();

    /**
     * 月份天数
     */
    private int monthDays = 31;

    /**
     * 所有组员调休天数
     */
    private Integer[] off;

    /**
     * 值班计划编号
     */
    private String scheduleId;
    
    /**
     * 值班计划状态1：草稿、2：启动、3：暂停
     */
    private Integer scheduleStatus;

    /**
     * 值班员循环数统计
     */
    private int circleNum = 0;

    /**
     * 所有组员
     */
    private List<Map<String, Object>> operationMap = new ArrayList<>();

    /**
     * 所有组员值班计划
     */
    private List<Map<String, Object>> odtList = new ArrayList<Map<String, Object>>();

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

        // 值班计划状态
        this.scheduleStatus = CommonUtils.toInteger(this.oMap.get("scheduleStatus"));
    }

    public ApsMembersScheduleHandle() {
    }

    /**
     * 构造函数初始化
     * 
     * @param _amsb
     * @param _oMap
     */
    public ApsMembersScheduleHandle(ApsMembersScheduleBean _amsb, Map<String, Object> _oMap) {
        this.amsb = _amsb;
        this.oMap = _oMap;

        this.getMonth();

        if (this.oMap != null)
        {
            // 拆分未值班员、值班计划
            this.convertMap();

            // 初始化调休数组长度
            off = new Integer[this.olist.size()];

            // 处理结果
            members();

            // 组装返回结果
            retMap();
        }

    }

    /**
     * 初始化返回结果
     * 
     * @return
     */
    private void retMap() {
        retMap.put("groupId", amsb.getGroupId());
        retMap.put("off", off);
        retMap.put("operationList", operationMap);
        retMap.put("onDutyTypeList", odtList);
        retMap.put("scheduleId", scheduleId);
        retMap.put("scheduleStatus", scheduleStatus);
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
        int offNum = 0; // 调休天数
        Map<String, Object> oMap = null;

        // 循环组员列表
        for (Operator o : this.olist)
        {
            oMap = new HashMap<>();
            oMap.put("id", o.getOptCustId());       //值班员操作号
            oMap.put("name", o.getOperatorName());  //值班员名称
            oMap.put("chief", o.getChief());        //值班主人状态 true:是，false:否
            operationMap.add(oMap);

            // 组员值班计划
            offNum = memberSchedule(o);

            // 组员调休天数
            off[circleNum] = offNum;
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
        String[] msodt = new String[this.monthDays]; // 初始化组员当月值班计划天数
        String[] tempmsodt = new String[this.monthDays]; // 初始化组员临时当月值班计划天数
        
        // 值班员值班计划
        Map<String, Object> odtMap = new HashMap<String, Object>();
        odtMap.put("id", o.getOptCustId());

        if (this.solist != null){
           
            for (ScheduleOpt so : this.solist) {
                
                if (so.getOptCustId().equals(o.getOptCustId())) {
                    // 值班计划编号
                    if (StringUtils.isEmpty(scheduleId)){
                        scheduleId = so.getScheduleId();
                    }

                    // 组员已执行排班计划
                    if (so.getWorkType() != null) {
                        
                        if (so.getWorkType().intValue() == WorkTypeStatus.ON_HOLIDAY.getCode().intValue()) {// 调休状态
                            offNum++;
                        }

                        msodt[Integer.parseInt(so.getPlanDate()) - 1] = WorkTypeStatus.getDescribe(so.getWorkType());
                    }

                    // 组员未执行排班计划
                    if (so.getWorkTypeTemp() != null) {
                        tempmsodt[Integer.parseInt(so.getPlanDate()) - 1] = WorkTypeStatus.getDescribe(so.getWorkTypeTemp());
                    }
                }
            }
        }

        odtMap.put("monthODT", msodt);
        odtMap.put("tempMonthODT", tempmsodt);
        odtList.add(odtMap);

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
