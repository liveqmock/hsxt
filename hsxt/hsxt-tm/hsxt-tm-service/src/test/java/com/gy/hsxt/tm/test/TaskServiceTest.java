/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.tm.bean.TmBaseParam;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.common.StringUtil;
import com.gy.hsxt.tm.disconf.TmConfig;
import com.gy.hsxt.tm.enumtype.TaskSrc;
import com.gy.hsxt.tm.interfaces.ITaskService;
import com.gy.hsxt.tm.mapper.TaskMapper;

public class TaskServiceTest extends BaseTest {

    @Autowired
    ITMTaskService taskService;

    @Autowired
    ITaskService iTaskService;

    @Autowired
    private TmConfig tmConfig;

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testSaveTMTask() {
        TmTask task = null;
        for (int i = 0; i < 1; i++)
        {
            task = new TmTask();
            task.setTaskId(StringUtil.getTmGuid(tmConfig.getAppNode()));
            task.setBizType(TaskType.TASK_TYPE171.getCode());
            task.setBizNo("1111111111");
            task.setEntCustId("222222222");
            task.setPriority(i);
            task.setTaskSrc(TaskSrc.BS.getCode());

            taskService.saveTMTask(task);
        }

        // fail("Not yet implemented");
    }

    @Test
    public void testSaveBatchTMTask() {
        List<TmTask> tasks = new ArrayList<TmTask>();
        TmTask task = null;
        for (int i = 0; i < 5; i++)
        {
            task = new TmTask();
            task.setTaskId(StringUtil.getTmGuid(tmConfig.getAppNode()));
            task.setBizType(TaskType.TASK_TYPE171.getCode());
            task.setBizNo("1111111111");
            task.setEntCustId("222222222");
            task.setPriority(i);
            task.setWarnFlag(0);
            task.setTaskSrc(TaskSrc.BS.getCode());
            tasks.add(task);
        }
        taskService.saveTMTask(tasks);
    }

    @Test
    public void testUpdateTaskStatus() {
        taskService.updateTaskStatus("110120160314120843000002", TaskStatus.COMPLETED.getCode(), "办好了");
    }

    @Test
    public void testUpdateTaskExcutor() {
        // 更新工单执行人
        // taskMapper.updateTaskExecutor("3333", "33333",
        // "110120160314103655000000");
    }

    @Test
    public void testGetTurnTaskListPage() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10);
        try
        {
            PageData<TmTask> taskList = iTaskService.getTurnTaskListPage(page);

            List<TmTask> tasks = taskList.getResult();
            for (TmTask o : tasks)
            {
                System.out.println("任务ID = " + o.getTaskId());
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUrgencyTask() {
        List<TmTask> urgencyTaskList = iTaskService.getUrgencyTask();
        if (urgencyTaskList != null && urgencyTaskList.size() > 0)
        {
            for (TmTask task : urgencyTaskList)
            {
                System.out.println(task);
            }
        }
    }

    @Test
    public void testGetTaskList() {
        TmBaseParam baseParam = new TmBaseParam();
        Page page = new Page(1);
        baseParam.setStartDate("2016-01-01");
        baseParam.setEndDate("2016-02-29");
        baseParam.setStatus(3);
        baseParam.setEntCustId("00000000156163438270977024");
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<TmTask> taskList = taskService.getTaskList(baseParam, page);

            if (taskList != null && taskList.getCount() > 0)
            {
                List<TmTask> task = taskList.getResult();
                for (TmTask o : task)
                {
                    System.out.println("订单号 = " + o);
                }
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateTaskPriorityStat() {
        iTaskService.updateTaskPriorityStat("120120151111042514000000");
    }

    /**
     * 手动派单
     */
    @Test
    public void testSendOrder() {
        List<String> taskIds = new ArrayList<String>();
        taskIds.add("110120160130143343000001");
        List<String> optCustIds = new ArrayList<String>();
        optCustIds.add("06000000000163439192419328");
        taskService.sendOrder(2, taskIds, optCustIds, null);
    }

    @Test
    public void testUpdateWarnFlag() {
        taskService.updateWarnFlag("110120160314111400000001", true);
    }

}
