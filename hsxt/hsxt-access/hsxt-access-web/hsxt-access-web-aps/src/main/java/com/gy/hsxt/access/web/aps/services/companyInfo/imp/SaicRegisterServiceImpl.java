/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.companyInfo.imp;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.companyInfo.ISaicRegisterService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;

@Service
public class SaicRegisterServiceImpl extends BaseServiceImpl implements ISaicRegisterService {
    @Override
    public void updateEntBaseInfo(AsEntBaseInfo srInfo) throws HsException {

        System.out.println("修改在工商局的注册信息:" + JsonUtil.createJsonString(srInfo));

    }

    @Override
    public Object findSaicRegisterInfo(String custId) throws HsException {
        // TODO Auto-generated method stub
        return null;
    }


}
