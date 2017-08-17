package com.gy.hsxt.bs.bm.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.bs.bean.bm.Increment;
import com.gy.hsxt.bs.bm.bean.ApplyRecord;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Package :com.gy.hsxt.bs.bm.interfaces
 * @ClassName : IBsUfRegionPackServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/27 15:17
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class IBsUfRegionPackServiceTest {

    @Resource
    private IBsUfRegionPackService bsUfRegionPackService;

    @Test
    public void testSaveApplyRecordToMlm() throws Exception {
        ApplyRecord record = new ApplyRecord();
        record.setAppCustId("06000000000");
        record.setArea("right");
        record.setFlag("0");
        record.setManageCustId("0600000000020150101");
        record.setPopNo("06010000000");
        record.setPopCustId("0601000000020151231");
        record.setType(CustType.SERVICE_CORP.getCode());
        boolean success = bsUfRegionPackService.saveApplyRecordToMlm(record);
        System.out.println(success);
    }

    @Test
    public void testQueryMlmData() throws Exception {
        try {
            String result = bsUfRegionPackService.queryMlmData("06001000008","");
            Increment increment = JSONObject.parseObject(result, Increment.class);
            System.out.println(increment);
//            System.out.println(result);
        } catch (HsException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode());
        }

    }

    @Test
    public void testStopResourceNo() throws Exception {

        boolean success = bsUfRegionPackService.stopResourceNo("06001000006");

        System.out.println(success);
    }
}