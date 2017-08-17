/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.task;

import java.util.Calendar;

import javax.annotation.Resource;

import org.junit.Test;

import com.gy.hsxt.bs.api.task.IBSTaskService;
import com.gy.hsxt.bs.service.testcase.BaseTest;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.guid.GuidAgent;

/**
 * 工单单元测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.task
 * @ClassName: ITaskServiceTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-29 下午9:28:48
 * @version V3.0.0
 */
public class ITaskServiceTest extends BaseTest {

    @Resource
    IBSTaskService bsTaskService;

    @Resource
    ITaskService taskService;

    /**
     * 保存工单单元测试
     */
    @Test
    public void testSaveTask() {
        Task task = null;
        for (int i = 0; i < 1; i++)
        {
            task = new Task();
            task.setBizType(i % 2 == 0 ? "119" : "120");
            task.setEntCustId("00000000156163438270977024");
            task.setHsResNo("00000000156");
            task.setCustName("prgms22");
            task.setRemark("单元测试工单");
            task.setBizNo(GuidAgent.getStringGuid(BizGroup.BS + "01"));
            taskService.saveTask(task);
        }

        // task.setTaskSrc("1");
        // task.setEntResNo(getHsResNo());
        // task.setEntCustName("hsxt");
        // task.setCustType(CustType.MEMBER_ENT.getCode());
        //
        // task.setTaskType(TaskType.TOOL_PRODUCT_UP_APPR.getCode());
        // task.setBizNo("111111111111");
        // task.setTaskLevel(1);

    }

    @Test
    public void testUpdateTaskStatus() {
        bsTaskService.updateTaskStatus("110120151128091229000000", 2, "", "");
    }

    @Test
    public void testGetSrcTask() {
        System.out.println("**********************************************"
                + taskService.getSrcTask("00000000000", "888"));
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000); // 2010年
        c.set(Calendar.MONTH, 5); // 6 月
        System.out.println("------------" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1)
                + "月的天数和周数-------------");
        System.out.println("天数：" + c.getActualMaximum(Calendar.DAY_OF_YEAR));
        System.out.println("周数：" + c.getActualMaximum(Calendar.WEEK_OF_MONTH));
    }
}
