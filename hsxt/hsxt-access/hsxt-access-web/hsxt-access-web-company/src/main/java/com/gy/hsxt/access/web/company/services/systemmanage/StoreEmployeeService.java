/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemmanage;

import java.util.List;

import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsec.external.bean.StoreEmployeeBinding;
import com.gy.hsec.external.bean.StoreEmployeeRelation;
import com.gy.hsec.external.bean.StoreReturnResult;
import com.gy.hsxt.access.web.common.service.IBaseService;

@SuppressWarnings("rawtypes")
public interface StoreEmployeeService extends IBaseService {

    /**
     * 获取企业营业点列表
     * @param entResNo 企业资源号（企业互生号）
     * @param custName 企业员工账号  length:4
     * @return
     * @throws Exception
     */
    public StoreReturnResult<List<StoreEmployeeBinding>> getSalerStoresByEntResNoAndEmpActNo(
            String entResNo,String custName) throws Exception;
    
    /**
     * 5.1.7 设置企业员工和企业营业点关联
     * @param lstSalerStoreEmployeeRelation
     * @return
     * @throws Exception
     */
    public ReturnResult setSalerStoreEmployeeRelation(
            List<StoreEmployeeRelation> lstSalerStoreEmployeeRelation)
            throws Exception;
    
    /**
     * 5.1.8 删除企业员工和企业营业点所有的关联
     * @param entResNo 企业资源号（企业互生号）
     * @param custName 企业员工账号  length:4
     * @return
     * @throws Exception
     */
    public ReturnResult deleteStoreEmployeesByEntResNoAndEmpActNo(String entResNo,String custName)
            throws Exception;
}
