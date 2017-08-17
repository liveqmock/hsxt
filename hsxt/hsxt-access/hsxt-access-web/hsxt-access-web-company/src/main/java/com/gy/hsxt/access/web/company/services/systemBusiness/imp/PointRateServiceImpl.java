/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemBusiness.IPointRateService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;


@SuppressWarnings("rawtypes")
@Service
public class PointRateServiceImpl extends BaseServiceImpl implements IPointRateService {
    
    @Autowired
    private IUCAsDeviceService iuCAsDeviceService;

    @Override
    public String[] findPointRate(String entResNo) throws HsException {
        return iuCAsDeviceService.findEntPointRate(entResNo);
    }

    @Override
    public void updatePointRate(String entResNo, String pointRate[], String operator) throws HsException {
        iuCAsDeviceService.updateEntPointRate(entResNo, pointRate, operator);
    }

    @Override
    public void savePointRate(String entResNo, String[] pointRate, String operator) throws HsException {
        iuCAsDeviceService.setEntPointRate(entResNo, pointRate, operator);
    }
  

}
