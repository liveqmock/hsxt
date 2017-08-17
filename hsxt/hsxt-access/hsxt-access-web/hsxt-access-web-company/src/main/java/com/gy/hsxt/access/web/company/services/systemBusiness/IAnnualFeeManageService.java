/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness;


import java.text.ParseException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.common.exception.HsException;


/**
 * 缴纳系统年费service
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness  
 * @ClassName: IToolManageService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-13 下午5:14:19 
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Service
public interface IAnnualFeeManageService extends IBaseService {

    /**
     * 查询企业应该交年费时的基本信息  包括  缴费区间  需缴年费  年费有效期  是否欠费
     * @param custId
     * @return
     * @throws HsException
     * @throws ParseException 
     */
    public Map<String,Object> findAnnualFeeShould(String custId) throws HsException, ParseException;
    
    /**
     * 缴纳年费
     * @param annualFee
     * @throws HsException
     * @return 如果是互生币支付 返回null  如果是网银支付 返回跳转到网银支付页面的url
     */
    public String addAnnualFee(AnnualFeeOrder annualFee) throws HsException;
    
    /**
     * 提交年费订单
     * @param annualFeeOrder
     * @return
     * @throws HsException
     */
    public AnnualFeeOrder submitAnnualFeeOrder(AnnualFeeOrder annualFeeOrder) throws HsException;
    
}
