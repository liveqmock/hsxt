package com.gy.hsxt.access.web.scs.services.businessService;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.businessService
 * @className : ICompanyInforService.java
 * @description : 企业信息查询接口
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ICompanyInforService extends IBaseService {
    /**
     * 根据 resNo 查询企业的重要信息
     * 
     * @param resNo
     *            :企业resNo
     * @param request
     * @return
     */
    public AsEntMainInfo findMainByResNo(String custId) throws HsException;

    /**
     * 根据 custId 查询默认银行账户
     * 
     * @param custId
     *            :企业客户ID
     * @param request
     * @return
     */
    public AsBankAcctInfo searchDefaultBankAcc(String custId) throws HsException;

    /**
     * 通过企业互生号查询企业客户号
     * 
     * @param entResNo
     *            :企业互生号
     * @param request
     * @return
     */
    public String findEntCustIdByEntResNo(String entResNo) throws HsException;

    /**
     * 根据企业客户号查询企业的扩展信息
     * @param entResNo
     * @return
     * @throws HsException
     */
    public AsEntExtendInfo getEntExtExtInfo(String entResNo) throws HsException;
    
    /**
	 * 根据 resNo 查询企业基础信息
	 * 
	 * @param entCustId
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	public AsEntBaseInfo findBaseInfoCustId(String entCustId) throws HsException;
}
