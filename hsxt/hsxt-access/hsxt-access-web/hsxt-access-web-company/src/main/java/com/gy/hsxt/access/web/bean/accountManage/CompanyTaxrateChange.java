/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.accountManage;

import java.io.Serializable;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.common.exception.HsException;

/***
 * 地区平台企业税率调整实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.accountManagement
 * @ClassName: ApsTaxrateChange
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-18 上午10:26:22
 * @version V1.0
 */
public class CompanyTaxrateChange extends TaxrateChange implements Serializable {
    private static final long serialVersionUID = -2823992659992392505L;

    /**
     * 验证调整数据
     */
    public void checkAdjustmentData() throws HsException {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[]{ super.getApplyTaxrate(), ASRespCode.SW_APPLY_TAXRATE_ERROR},
                new Object[]{ super.getTaxpayerType(), ASRespCode.SW_TAXPAYER_TYPE_ERROR},
                new Object[]{ super.getProveMaterialFile(), ASRespCode.SW_PROOF_ERROR},
                new Object[]{ super.getApplyReason(), ASRespCode.SW_APPLY_REASON_ERROR}
                );
    }
    
    
}
