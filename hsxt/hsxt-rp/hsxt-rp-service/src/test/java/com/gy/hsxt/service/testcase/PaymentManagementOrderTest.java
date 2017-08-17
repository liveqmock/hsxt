/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.service.testcase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IRPPaymentManagementService;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/**
 * 收款管理业务订单测试类
 * 
 * @Package: com.gy.hsxt.service.testcase
 * @ClassName: PaymentManagementOrderTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-3-1 下午6:50:55
 * @version V3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class PaymentManagementOrderTest {

    @Autowired
    IRPPaymentManagementService paymentManagementService;

    @Test
    public void testPaymentManagementService() {

        PaymentManagementOrder paymentManagementOrder = new PaymentManagementOrder();
        paymentManagementOrder.setStartDate("2016-02-28");
        paymentManagementOrder.setEndDate("2016-12-31");
        paymentManagementOrder.setOrderType("102");
        paymentManagementOrder.setQueryDate();
        Page page = new Page(1, 10000);
        try
        {
            PageData<PaymentManagementOrder> paymentPageDate = paymentManagementService.getPaymentOrderList(
                    paymentManagementOrder, page);
            List<PaymentManagementOrder> paymentOrderList = paymentPageDate.getResult();

            for (int i = 0; i < paymentOrderList.size(); i++)
            {
                System.out.println("平台收款订单：" + paymentOrderList.get(i));
            }
        }
        catch (HsException e)
        {
            System.out.println("HsException =========" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testDataRec() {
        List<String> orderNos = new ArrayList<String>();
        orderNos.add("130120160302152417000000");
        paymentManagementService.dataReconciliation(orderNos);
    }
}
