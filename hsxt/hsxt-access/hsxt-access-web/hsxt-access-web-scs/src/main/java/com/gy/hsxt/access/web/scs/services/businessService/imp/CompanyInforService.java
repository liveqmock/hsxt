package com.gy.hsxt.access.web.scs.services.businessService.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.businessService.ICompanyInforService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.businessService.imp
 * @className : CompanyInforService.java
 * @description : 企业信息查询
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("invoiceCompanyInforService")
public class CompanyInforService extends BaseServiceImpl<CompanyInforService> implements ICompanyInforService {
    @Autowired
    private IUCAsEntService iuCAsEntService;

    @Autowired
    private IUCAsBankAcctInfoService iuCAsBankInfoService;

    /**
     * 根据 resNo 查询企业的重要信息
     * 
     * @param resNo
     *            :企业resNo
     * @param request
     * @return
     */
    @Override
    public AsEntMainInfo findMainByResNo(String resNo) throws HsException {
        return iuCAsEntService.getMainInfoByResNo(resNo);
    }

    /**
     * 根据 custId 查询默认银行账户
     * 
     * @param custId
     *            :企业客户ID
     * @param request
     * @return
     */
    @Override
    public AsBankAcctInfo searchDefaultBankAcc(String custId) throws HsException {
        String userType = "4";
        AsBankAcctInfo bankAcctInfo = iuCAsBankInfoService.searchDefaultBankAcc(custId, userType);
//        if (bankAcctInfo == null)
//        {
//            List<AsBankAcctInfo> listBank = iuCAsBankInfoService.listBanksByCustId(custId, userType);
//            if (listBank != null && listBank.size() > 0)
//            {
//                bankAcctInfo = listBank.get(0);
//            }
//        }
        return bankAcctInfo;
    }

    /**
     * 通过企业互生号查询企业客户号
     * 
     * @param entResNo
     *            :企业互生号
     * @param request
     * @return
     */
    @Override
    public String findEntCustIdByEntResNo(String entResNo) throws HsException {
        return iuCAsEntService.findEntCustIdByEntResNo(entResNo);
    }

    /**
     * 根据企业客户号查询企业的扩展信息
     * @param entResNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.scs.services.businessService.ICompanyInforService#getEntExtExtInfo(java.lang.String)
     */
    @Override
    public AsEntExtendInfo getEntExtExtInfo(String entResNo) throws HsException {
        return iuCAsEntService.searchEntExtInfo(this.findEntCustIdByEntResNo(entResNo));
    }
    
    /**
	 * 根据 resNo 查询企业基础信息
	 * 
	 * @param entCustId
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@Override
	public AsEntBaseInfo findBaseInfoCustId(String entCustId) throws HsException {
		return iuCAsEntService.searchEntBaseInfo(entCustId);
	}
	
}
