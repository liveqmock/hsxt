/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.accountManagement;

import java.util.Map;

import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.accountManage.CompanyTaxrateChange;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***
 * 城市税收对接账户
 * @Package: com.gy.hsxt.access.web.scs.services.accountManagement
 * @ClassName: ICityTaxAccountService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-16 下午7:21:32
 * @version V1.0
 */
public interface ICityTaxAccountService extends IBaseService {
    
    /**
     * 获取税率
     * @param scsBase
     * @return
     * @throws HsException
     */
    public String queryTax(CompanyBase companyBase) throws HsException;

    /**
     * 税率调整
     * @param atc
     * @param scsBase
     */
    public void taxAdjustmentApply(CompanyTaxrateChange atc,CompanyBase companyBase)throws HsException;

    /**
     * 税率调整申请查询
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<TaxrateChange> taxAdjustmentApplyPage(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 查询已申请税率调整明细
     * @param applyId
     * @return
     * @throws HsException
     */
    public TaxrateChange queryTaxApplyDetail(String applyId) throws HsException;
    
    /**
     * 查询企业税率调整的最新审批结果
     * @param companyBase
     * @return
     * @throws HsException
     */
    public TaxrateChange getLastTaxApply(CompanyBase companyBase)  throws HsException;
     
}
