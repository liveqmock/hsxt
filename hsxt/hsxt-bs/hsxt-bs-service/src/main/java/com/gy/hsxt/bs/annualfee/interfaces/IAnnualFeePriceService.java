/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.interfaces;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeePrice;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 企业年费方案业务接口
 *
 * @Package :com.gy.hsxt.bs.annualfee.interfaces
 * @ClassName : IAnnualFeePriceService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/10 15:28
 * @Version V3.0.0.0
 */
public interface IAnnualFeePriceService extends IBaseService<AnnualFeePrice> {

    /**
     * 审核年费方案
     *
     * @param annualFeePrice 年费方案
     * @return boolean
     * @throws HsException
     */
    boolean apprAnnualFeePrice(AnnualFeePrice annualFeePrice) throws HsException;

    /**
     * 查询某类型企业的可用年费方案之价
     *
     * @param custType 企业类型
     * @return 方案价格
     */
    String queryPriceForEnabled(Integer custType);
}
