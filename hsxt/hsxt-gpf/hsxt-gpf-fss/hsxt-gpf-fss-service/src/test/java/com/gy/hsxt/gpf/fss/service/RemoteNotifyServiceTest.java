package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.gpf.fss.bean.*;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : RemoteNotifyServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/2 11:10
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class RemoteNotifyServiceTest {

    @Resource
    private RemoteNotifyService remoteNotifyService;

    @Resource
    private LocalNotifyService localNotifyService;

    @Test
    public void testQueryByOther() throws Exception {

        QueryNotify queryNotify = new QueryNotify();

//        queryNotify.setNotifyStartDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.PREVIOUS_MONTH));
//        queryNotify.setNotifyEndDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.THIS_MONTH));
        queryNotify.setToSys("BM");
//        queryNotify.setPurpose(FssPurpose.BM_BMLM.getCode());
        queryNotify.setToPlat("000");
//        List<LocalNotify> notifies =  localNotifyService.queryByOther(queryNotify);
//        System.out.println(notifies);
        PageContext.setPageSize(1);
        PageContext.setOffset(0);
        PageData<LocalNotify> pageData = localNotifyService.queryLocalNotifyForPage(queryNotify);

        System.out.println(pageData);
    }


}