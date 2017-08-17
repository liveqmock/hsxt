/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.PageData;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.scheduler.job.RemoteNotifyJob;
import com.gy.hsxt.gpf.fss.service.RemoteNotifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 远程通知控制层
 *
 * @Package :com.gy.hsxt.gpf.fss.controller
 * @ClassName : RemoteNotifyController
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 17:57
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/remote")
public class RemoteNotifyController {

    /**
     * 远程通知业务接口
     */
    @Resource
    private RemoteNotifyService remoteNotifyService;

    /**
     * 本地通知远程回调任务实现
     */
    @Resource
    private RemoteNotifyJob remoteNotifyJob;

    /**
     * 分页查询远程通知列表
     *
     * @param queryNotify 查询实体
     * @return list
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryRemoteNotifyForPage")
    public PageData<RemoteNotify> queryRemoteNotifyForPage(QueryNotify queryNotify) throws HsException {
        return remoteNotifyService.queryRemoteNotifyForPage(queryNotify);
    }

    /**
     * 查询远程通知详情
     *
     * @param notifyNo 通知编号
     * @return notify
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryRemoteNotifyById")
    public RemoteNotify queryRemoteNotifyById(String notifyNo) throws HsException {
        return remoteNotifyService.queryById(notifyNo);
    }

    /**
     * 重新触发本地通知远程回调任务
     *
     * @param notifyNo 通知编号
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/touchRemoteNotifyJobByNotifyNo")
    public boolean touchRemoteNotifyJobByNotifyNo(String notifyNo) throws HsException {
        return remoteNotifyJob.touch(RemoteNotify.bulid(notifyNo));
    }
}
