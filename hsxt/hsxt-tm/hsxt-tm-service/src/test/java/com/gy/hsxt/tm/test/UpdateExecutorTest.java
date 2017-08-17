/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMSyncTaskService;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.interfaces.ITaskService;

public class UpdateExecutorTest extends BaseTest {

    @Autowired
    ITMSyncTaskService tmTaskService;

    @Autowired
    ITaskService taskService;

    @Test
    public void testSaveTMTask() {
        tmTaskService.updateSrcTaskExecutor("888", "张三疯33", "张三疯3333", "110120151118051802000000");
    }

    @Test
    public void testUpdateTaskStatus() {
        taskService.updateTaskStatus("120120151110054012000000", TaskStatus.STOPPED.getCode(), "");
    }

    @Test
    public void testGetTurnTaskListPage() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10);
        try
        {
            PageData<TmTask> taskList = taskService.getTurnTaskListPage(page);

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
        List<TmTask> urgencyTaskList = taskService.getUrgencyTask();
        if (urgencyTaskList != null && urgencyTaskList.size() > 0)
        {
            for (TmTask task : urgencyTaskList)
            {
                System.out.println(task);
            }
        }
    }

    @Test
    public void testUpdateTaskPriorityStat() {
        taskService.updateTaskPriorityStat("120120151111042514000000");
    }
}
