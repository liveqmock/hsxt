package com.gy.hsxt.gpf.fss.service.impl;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.service.LocalNotifyService;
import com.gy.hsxt.gpf.fss.service.RemoteNotifyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package : com.gy.hsxt.gpf.fss.service.impl
 * @ClassName : LocalNotifyServiceImplTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/3/30 11:52
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class LocalNotifyServiceImplTest {

    @Resource
    private LocalNotifyService localNotifyService;

    @Resource
    private RemoteNotifyService remoteNotifyService;
    @Test
    public void queryByOther() throws Exception {
        QueryNotify queryNotify = new QueryNotify();
        //没有接收到的
        queryNotify.setReceived(0);
        //远程通知本地回调的
        queryNotify.setToPlat("000");
        //查询符合条件的本地通知
        List<LocalNotify> localNotifies = localNotifyService.queryByOther(queryNotify);

        System.out.println(localNotifies);

        List<RemoteNotify> remoteNotifies = remoteNotifyService.queryByOther(queryNotify);

        System.out.println(remoteNotifies);
    }
}