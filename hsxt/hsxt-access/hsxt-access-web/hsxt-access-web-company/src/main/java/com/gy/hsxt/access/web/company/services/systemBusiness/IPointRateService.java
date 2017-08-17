/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
public interface IPointRateService extends IBaseService {

    /**
     * 查询积分比例
     * @param entResNo 企业互生号
     * @return
     * @throws HsException
     */
    public String[] findPointRate(String entResNo)throws HsException;
    
    /**
     * 修改积分比例
     * @param entResNo 企业互生号
     * @param pointRate 积分比例数字
     * @param operator 操作员客户号
     * @throws HsException
     */
    public void updatePointRate(String entResNo,String[] pointRate,String operator)throws HsException;
    
    /**
     * 设置积分比例
     * @param entResNo 企业互生号
     * @param pointRate 积分比例数字
     * @param opreator 操作员客户号
     * @throws HsException
     */
    public void savePointRate(String entResNo,String[] pointRate,String opreator) throws HsException;
}
