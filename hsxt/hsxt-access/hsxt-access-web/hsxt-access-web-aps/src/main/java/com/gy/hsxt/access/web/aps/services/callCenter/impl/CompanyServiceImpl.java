package com.gy.hsxt.access.web.aps.services.callCenter.impl;

import com.gy.hsxt.access.web.aps.services.callCenter.ICompanyService;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 企业信息查询服务接口实现
 * @category      企业信息查询服务接口实现
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.callCenter.impl.CompanyServiceImpl.java
 * @className     CompanyServiceImpl
 * @description   用于查询企业联系信息，企业所有信息，企业基本信息，重要信息等
 * @author        leiyt
 * @createDate    2016-1-30 下午2:45:22  
 * @updateUser    leiyt
 * @updateDate    2016-1-30 下午2:45:22
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private IUCAsEntService iuCAsEntService;
    @Autowired
    private IBSChangeInfoService ibSChangeInfoService;//重要信息变更查询
    @Autowired
    private IUCAsBankAcctInfoService iuCAsBankInfoService;

    @Override
    public AsEntContactInfo searchEntContactInfo(String resNo) throws HsException {
        String custId=findEntCustIdByEntResNo(resNo);
        return iuCAsEntService.searchEntContactInfo(custId);
    }

    @Override
    public AsEntAllInfo searchEntAllInfo(String resNo) throws HsException {
        String custId=findEntCustIdByEntResNo(resNo);
        return iuCAsEntService.searchEntAllInfo(custId);
    }
    @Override
    public String findEntCustIdByEntResNo(String  resNo) throws HsException {
        return iuCAsEntService.findEntCustIdByEntResNo(resNo);
    }

    @Override
    public EntChangeInfo queryEntChangeByCustId(String custId) throws HsException {
        return ibSChangeInfoService.queryEntChangeByCustId(custId);
    }
}
