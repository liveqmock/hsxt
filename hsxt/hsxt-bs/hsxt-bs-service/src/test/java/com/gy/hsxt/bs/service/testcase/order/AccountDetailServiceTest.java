/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.common.enumtype.order.TransInnerType;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.bs.order.service.AccountDetailService;
import com.gy.hsxt.bs.service.testcase.BaseTest;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * 记帐分解单元测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.order
 * @ClassName: AccountDetailServiceTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-12 下午2:41:50
 * @version V1.0
 */
public class AccountDetailServiceTest extends BaseTest {

    @Autowired
    AccountDetailService accountDetailService;

    @Autowired
    AccountDetailMapper accountDetailMapper;

    /** 业务服务私有配置参数 **/
    @Autowired
    BsConfig bsConfig;

    @Test
    public void testSaveAccountDetail() throws HsException {
        List<AccountDetail> details = new ArrayList<AccountDetail>();
        for (int i = 0; i < 2; i++)
        {
            AccountDetail accountDetail = new AccountDetail();
            accountDetail.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            accountDetail.setBizNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            accountDetail.setTransType(TransInnerType.HSB_TO_CURRENCY.getCode());
            accountDetail.setCustId(getHsResNo() + DateUtil.getCurrentDateTime());
            accountDetail.setHsResNo(getHsResNo());
            accountDetail.setCustName("sylvan" + i);
            accountDetail.setCustType(i % 2 == 0 ? 0 : 1);
            accountDetail.setAccType("");
            accountDetail.setAddAmount("88.88");
            accountDetail.setDecAmount("");
            accountDetail.setCurrencyCode("RMB");
            accountDetail.setFiscalDate(DateUtil.getCurrentDateTime());
            accountDetail.setCreatedDate(DateUtil.getCurrentDateTime());
            accountDetail.setRemark("Hello world");
            accountDetail.setStatus(false);
            accountDetail.setUpdatedDate(DateUtil.getCurrentDateTime());
            details.add(accountDetail);
        }
        accountDetailMapper.bathcInsertAccountDetail(details);
    }

}
