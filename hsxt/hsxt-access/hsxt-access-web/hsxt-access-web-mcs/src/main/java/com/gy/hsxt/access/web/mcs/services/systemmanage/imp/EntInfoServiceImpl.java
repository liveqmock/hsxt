/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.services.systemmanage.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.mcs.services.systemmanage.EntInfoService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

@SuppressWarnings("rawtypes")
@Service
public class EntInfoServiceImpl extends BaseServiceImpl implements EntInfoService {

    @Autowired
    private IUCAsEntService iuCAsEntService;
    
    @Override
    public AsEntInfo searchRegionalPlatform() throws HsException {
        return iuCAsEntService.searchRegionalPlatform();
    }

}
