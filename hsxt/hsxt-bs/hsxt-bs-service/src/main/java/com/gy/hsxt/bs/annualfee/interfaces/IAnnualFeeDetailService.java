/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.interfaces;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetail;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetailQuery;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.annualfee.interfaces
 * @ClassName : IAnnualFeeDetailService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/10 20:18
 * @Version V3.0.0.0
 */
public interface IAnnualFeeDetailService extends IBaseService<AnnualFeeDetail> {

    /**
     * 根据年费区间查询计费单
     *
     * @param annualFeeDetail 计费单实体
     * @return 实体
     * @throws HsException
     */
    AnnualFeeDetail queryBeanByAnnualArea(AnnualFeeDetail annualFeeDetail) throws HsException;

    /**
     * 查询未支付年费区间
     *
     * @param annualFeeDetailQuery 查询实体
     * @return bean
     */
    AnnualFeeDetail queryAnnualAreaForArrear(AnnualFeeDetailQuery annualFeeDetailQuery) throws HsException;


    /**
     * 支付完成后修改计费单
     *
     * @param orderNo 相关的业务订单号
     * @return int
     * @throws HsException
     */
    int modifyBeanForPaid(String orderNo) throws HsException;
}
