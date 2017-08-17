/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.service;

import org.quartz.SchedulerException;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.job.beans.vo.QrtzTriggerVo;
import com.gy.hsi.ds.job.controller.form.TriggerListForm;
import com.gy.hsi.ds.job.controller.form.TriggerNewForm;
/**
 * 定时任务操作业务处理接口
 * 
 * @Package: com.gy.hsi.ds.job.web.service.trigger.service  
 * @ClassName: QrtzTriggerMgr 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月14日 下午4:26:07 
 * @version V3.0
 */
public interface IQrtzTriggerMgr {
    /**
     * 获得Trigger列表
     * @param triggerListForm
     * @return
     */
    DaoPageResult<QrtzTriggerVo> getTriggerList(TriggerListForm triggerListForm);
    
    
    /**
     * 校验quartz cron 表达式（时间配置）
     * @param cron
     * @return
     * @throws Exception
     */
    boolean checkCron(String cron);

    /**
     * 判断任务是否存在
     * @param triggerName
     * @param triggerGroup
     * @return
     */
    boolean isExist(String triggerName, String triggerGroup)  throws SchedulerException;

    /**
     * 创建定时任务
     * @param triggerNewForm
     */
    String create(TriggerNewForm triggerNewForm);

    /**
     * 更新定时任务
     * @param triggerNewForm
     * @return
     */
    String update(TriggerNewForm triggerNewForm);
    
    /**
     * 删除定时任务
     * @param triggerName
     * @param triggerGroup
     * @return
     */
    String delete(String triggerName, String triggerGroup);
    
    /**
     * 恢复定时任务
     * @param triggerNewForm
     * @return
     */
    String resume(String triggerName, String triggerGroup);
    
    /**
     * 暂停定时任务
     * @param triggerName
     * @param triggerGroup
     * @return
     */
    String pause(String triggerName, String triggerGroup);

    /**
     * 根据triggerName与triggerGroup获取任务信息
     * @param triggerName
     * @param triggerGroup
     * @return
     */
    QrtzTriggerVo get(String triggerName, String triggerGroup);


}
