/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 管理企业信息管理接口
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.businessService  
 * @ClassName: IMembercompApprovalService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-3 下午5:33:15 
 * @version V3.0.0
 */
public interface RelateCompanyManageService extends IBaseService {
    
    /**
     * 查询地区平台下企业资源
     * @param applyId 申请id
     * @return
     * @throws HsException
     */
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 企业信息查询
     * @param entCustId
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.infomanage.RelateCompanyManageService#searchEntExtInfo(java.lang.String)
     */
    public AsEntExtendInfo searchEntExtInfo(String entCustId) throws HsException;
    
    /**
     * 分页查看企业的修改记录信息
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.common.service.BaseServiceImpl#findScrollResult(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    public PageData queryEntInfoBakList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
      * 企业信息修改
      * @param conditoanMap
      * @return
      * @throws HsException
      */
    public void UpdateEntAndLog(Map conditoanMap) throws HsException;

    /**
     * 企业新增、删除银行信息
     * @param conditoanMap
     * @throws HsException
     */
    public void UpdateEntBankLog(Map conditoanMap) throws HsException;
    
    /**
     * 企业系统信息-查询银行卡
     * @param entCustId
     * @return
     * @throws HsException 
     */
    public List<AsBankAcctInfo> findBanksByCustId(String entCustId,String userType) throws HsException;
}
