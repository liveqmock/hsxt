package com.gy.apply.web.increment.service.impl;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.service.BmlmService;
import com.gy.hsxt.gpf.bm.utils.TimeUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class BmlmServiceImplTest {
    @Autowired
    private BmlmService bmlmService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCalcBmlmPv() {
        Map<String, Bmlm> parMap = new HashMap<String, Bmlm>();

        Bmlm bmlm = new Bmlm();
        bmlm.setPoint("100");
        bmlm.setType(CustType.TRUSTEESHIP_ENT.getCode());
        bmlm.setResNo("01002010000");
        String dateStr = DateFormatUtils.format(new Date(), Constants.DATE_TIME_FORMAT_SSS);
        StringBuilder rowKey = new StringBuilder(dateStr);
        rowKey.append("01002010000");

        System.out.println("--------------" + rowKey);

        parMap.put(rowKey.toString(), bmlm);

		/*bmlm = new Bmlm();
		bmlm.setPoint(99999L);
		bmlm.setType("T");
		parMap.put("01001010000", bmlm);
		
		bmlm = new Bmlm();
		bmlm.setPoint(12345L);
		bmlm.setType("B");
		parMap.put("01001000001", bmlm);
		
		
		bmlm = new Bmlm();
		bmlm.setPoint(100L);
		bmlm.setType("T");
		parMap.put("01002010000", bmlm);
		
		
		bmlm = new Bmlm();
		bmlm.setPoint(9L);
		bmlm.setType("S");
		parMap.put("01003000000", bmlm);*/


        //bmlmService.calcBmlmPv(parMap);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindByRowKeyRange() throws Exception {
        TimeUtil tt = new TimeUtil();
        String startRowKey = tt.getMondayOFWeek();
        String endRowKey = tt.getCurrentWeekday();

        Collection<Bmlm> collection = bmlmService.findByRowKeyRange(startRowKey, endRowKey);
        Iterator<Bmlm> ite = collection.iterator();

        while (ite.hasNext()) {
            Bmlm bmlm = ite.next();
            System.out.println(bmlm.getResNo() + ":" + bmlm.getPoint());
        }

    }
}
