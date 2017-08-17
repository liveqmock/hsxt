package com.gy.hsxt.access.web.aps.services.callCenter;

import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;

/**
 * 查询企业信息接口
 * @category      账户余额查询服务接口
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.callCenter.ICompanyService.java
 * @className     ICompanyService
 * @description   账户余额查询服务接口
 * @author        leiyt
 * @createDate    2016-1-30 上午10:05:09
 * @updateUser    leiyt
 * @updateDate    2016-1-30 上午10:05:09
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
public interface ICompanyService {
    /**
     * 查询企业联系信息
     * @param resNo
     * @return
     */
    AsEntContactInfo searchEntContactInfo(String resNo)  throws HsException;

    /**
     * 查询企业所有信息
     * @param resNo
     * @return
     * @throws HsException
     */
    AsEntAllInfo searchEntAllInfo(String  resNo) throws HsException;

    /**
     * 查询企业客户号
     * @param resNo
     * @throws HsException
     */
    String findEntCustIdByEntResNo(String  resNo) throws HsException;

    EntChangeInfo queryEntChangeByCustId(String custId) throws HsException;

}
