package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import com.gy.hsxt.gpf.fss.utils.NotifyNoUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : NotifyServiceImplTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 17:50
 * @Version V3.0.0.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring/spring-global.xml")
public class NotifyServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Resource
    private RemoteNotifyService remoteNotifyService;

    @Test
    public void testSave() throws Exception {

        RemoteNotify notify = new RemoteNotify();
        notify.setAllCompleted(0);
        notify.setFileCount(2);
        notify.setNotifyType(0);
        notify.setFromPlat("011");
        notify.setToPlat("000");
        notify.setFromSys("mlm");
        notify.setToSys("bs");
        notify.setPurpose(FssPurpose.BM_BMLM.getCode());
        notify.setNotifyTime(DateUtil.getCurrentDateTime());
        notify.setRemark("测试一下");
        notify.setNotifyNo(NotifyNoUtil.outNotifyNo(notify));

        System.out.println(notify);

        logger.info("===notify: {} ====", notify);

//       int count =  remoteNotifyService.save(notify);
//        System.out.println(count);

    }

    @Test
    public void testRemove() throws Exception {

        Date date = DateUtil.StringToDateTime("2015-02-12");
        System.out.println(date);
        System.out.println(date==null?DateUtil.StringToDate("2015-02-25"):date);

        String lastDate = FssDateUtil.obtainMonthLastDay(FssDateUtil.PREVIOUS_MONTH);
        System.out.println(lastDate);

    }

    @Test
    public void testRemoveById() throws Exception {

    }

    @Test
    public void testModify() throws Exception {

    }

    @Test
    public void testQuery() throws Exception {

    }

    @Test
    public void testQueryById() throws Exception {

    }
}