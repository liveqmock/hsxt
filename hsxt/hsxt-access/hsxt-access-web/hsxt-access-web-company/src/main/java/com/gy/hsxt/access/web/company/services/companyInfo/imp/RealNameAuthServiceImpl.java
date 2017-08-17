/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.companyInfo.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.companyInfo.IRealNameAuthService;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;

@Service
public class RealNameAuthServiceImpl extends BaseServiceImpl implements IRealNameAuthService {
    @Autowired
   private IBSRealNameAuthService bsService;

    @Override
    public void submit(EntRealnameAuth rnAuth) throws HsException {
        System.out.println("实名认证信息:" + JsonUtil.createJsonString(rnAuth));
        bsService.createEntRealnameAuth(rnAuth);

    }


    @Override
    public EntRealnameAuth findApplyOrder(String entCustId) throws HsException {
        EntRealnameAuth era = bsService.queryEntRealnameAuthByCustId(entCustId);
        return era;
    }

}
