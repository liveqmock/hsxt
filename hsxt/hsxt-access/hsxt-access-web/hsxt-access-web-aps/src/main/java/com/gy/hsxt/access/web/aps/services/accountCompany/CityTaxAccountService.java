/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.accountCompany;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/***
 * 城市税收对接账户
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.accountManagement
 * @ClassName: CityTaxAccountService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-16 下午7:26:24
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Service
public class CityTaxAccountService extends BaseServiceImpl implements ICityTaxAccountService {

    /** 注入 企业服务接口 */
    @Resource
    private IUCAsEntService iuCAsEntService;

    
    /**
     * 获取税率
     * 
     * @param scsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.scs.services.accountManagement.ICityTaxAccountService#queryTax(com.gy.hsxt.access.web.bean.SCSBase)
     */
    @Override
    public String queryTax(String entCustId) throws HsException {
        // 获取企业明细
        AsEntExtendInfo aeei = iuCAsEntService.searchEntExtInfo(entCustId);
        // 非空验证
        if (null == aeei)
        {
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }

        if (StringUtils.isEmpty(aeei.getTaxRate().toString()))
        {
            return "";
        }

        return aeei.getTaxRate().toString();
    }

}
