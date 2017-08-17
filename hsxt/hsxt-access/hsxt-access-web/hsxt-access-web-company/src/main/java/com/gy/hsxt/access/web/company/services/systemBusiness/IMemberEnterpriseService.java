/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness;


import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitStatus;
import com.gy.hsxt.bs.bean.entstatus.PointActivityStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

@SuppressWarnings("rawtypes")
@Service
public interface IMemberEnterpriseService extends IBaseService {

    /**
     * 查询企业状态信息
     * @param custId 企业id
     * @return
     * @throws HsException
     */
    public AsEntStatusInfo searchEntStatusInfo(String custId) throws HsException;
    
    
    /**
     * 根据客户id和客户类型查询客户的银行账户信息
     * @param custId    企业id
     * @param userType  企业类型
     * @return 客户银行账户信息列表
     * @throws HsException
     */
    public List<AsBankAcctInfo> findBanksByCustId(String custId,String userType) throws HsException;
    
    /**
     * 成员企业注销
     * @param member
     * @throws Exception 
     */
    public void createMemberQuit(MemberQuit member) throws Exception;
    
    /**
     * 根据企业客户号查询积分活动状态
     * @param entCustId 企业客户号
     * @return
     */
    public PointActivityStatus queryPointActivityStatus(String entCustId);
    
    /**
     * 根据企业客户号查询成员企业注销状态
     * @param entCustId 企业客户号
     * @return
     */
    public MemberQuitStatus queryMemberQuitStatus(String entCustId);
    
}
