/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.PageData;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import com.gy.hsxt.gpf.fss.scheduler.job.LocalNotifyJob;
import com.gy.hsxt.gpf.fss.service.LocalNotifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 本地通知控制层
 *
 * @Package :com.gy.hsxt.gpf.fss.controller
 * @ClassName : LocalNotifyController
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/3 15:40
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/local")
public class LocalNotifyController {

    /**
     * 本地通知业务接口
     */
    @Resource
    private LocalNotifyService localNotifyService;

    /**
     * 远程通知本地回调任务
     */
    @Resource
    private LocalNotifyJob localNotifyJob;

    /**
     * 分页查询本地通知信息列表
     * @param queryNotify 查询实体
     * @return pageDate
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryLocalNotifyForPage")
    public PageData<LocalNotify> queryLocalNotifyForPage(QueryNotify queryNotify) throws HsException {
        return localNotifyService.queryLocalNotifyForPage(queryNotify);
    }

    /**
     * 触发远程通知本地回调任务
     * @param notifyNo 通知编号
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/touchLocalNotifyJobByNotifyNo")
    public boolean touchLocalNotifyJobByNotifyNo(String notifyNo) throws HsException {
        return localNotifyJob.touch(LocalNotify.build(notifyNo));
    }

}
