/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 查询企业信息service
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder  
 * @ClassName: EntInfoService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-19 下午3:18:48 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface EntInfoService extends IBaseService {

    /**
     * 根据客户号查询企业重要信息
     * @param entCustId  企业客户号
     * @return
     * @throws HsException
     */
    public AsEntMainInfo searchEntMainInfo(String entCustId) throws HsException;
    
    /**
     * 根据互生号查询企业重要信息
     * @param resNo 企业互生号
     * @return
     * @throws HsException
     */
    public AsEntMainInfo searchEntMainInfoByResNo(String resNo) throws HsException;
    
    /**
     * 查询企业信息
     * @param entCustId 客户id
     * @return
     * @throws HsException
     */
    public AsEntAllInfo searchEntAllInfo(String  entCustId) throws HsException;
    
    /**
     * 根据客户id和客户类型查询客户的银行账户信息
     * @param custId    企业id
     * @param userType  企业类型
     * @return 客户银行账户信息列表
     * @throws HsException
     */
    public List<AsBankAcctInfo> findBanksByCustId(String custId,String userType) throws HsException;
    
    /**
     * 查询企业一般信息
     * @param entCustId
     * @return
     * @throws HsException
     */
    public  AsEntBaseInfo   searchEntBaseInfo(String entCustId) throws HsException;
    
    /**
     * 根据互生号查询企业所有信息
     * @param entResNo
     * @return
     * @throws HsException
     */
    public AsEntAllInfo searchEntAllInfoByResNo(String entResNo) throws HsException;
    
    /**
     * 修改企业一般信息
     * @param entBaseInfo
     * @param operator
     * @return
     * @throws HsException
     */
    public void updateEntBaseInfo(AsEntBaseInfo  entBaseInfo,  String  operator) throws HsException;
}
