/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemmanage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsec.external.api.EmployeeService;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsec.external.bean.StoreEmployeeBinding;
import com.gy.hsec.external.bean.StoreEmployeeRelation;
import com.gy.hsec.external.bean.StoreInputParam;
import com.gy.hsec.external.bean.StoreReturnResult;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemmanage.StoreEmployeeService;

@Service
@SuppressWarnings("rawtypes")
public class StoreEmployeeServiceImpl extends BaseServiceImpl implements StoreEmployeeService {

    @Autowired
    private EmployeeService employeeService;
    @Override
    public StoreReturnResult<List<StoreEmployeeBinding>> getSalerStoresByEntResNoAndEmpActNo(String entResNo,String custName)
            throws Exception {
        
        StoreInputParam param = new StoreInputParam();
        param.setEmployeeAccountNo(custName);
        param.setEnterpriceResourceNo(entResNo);
        
        return employeeService.getSalerStoresByEntResNoAndEmpActNo(param);
    }
    
    @Override
    public ReturnResult setSalerStoreEmployeeRelation(List<StoreEmployeeRelation> paramList)
            throws Exception {
        return employeeService.setSalerStoreEmployeeRelation(paramList);
    }
    
    @Override
    public ReturnResult deleteStoreEmployeesByEntResNoAndEmpActNo(String entResNo, String custName) throws Exception {
        StoreInputParam param = new StoreInputParam();
        param.setEmployeeAccountNo(custName);
        param.setEnterpriceResourceNo(entResNo);
        return employeeService.deleteStoreEmployeesByEntResNoAndEmpActNo(param);
    }

}
