/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.aps.services.companyInfo.IUCBankfoService;
import com.gy.hsxt.access.web.aps.services.infomanage.RelateCompanyManageService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;


@SuppressWarnings("rawtypes")
@Service
public class RelateCompanyManageServiceImpl extends BaseServiceImpl implements RelateCompanyManageService {

    @Autowired
    private IUCAsEntService iuCAsEntService;
    
    @Resource
    private IUCBankfoService ucBankService;//银行信息服务类
    
    @Autowired
    private IUCAsCardHolderService iUCAsCardHolderService;
    
    @Autowired
    private IBSChangeInfoService  ibSChangeInfoService;
    
    @Autowired
    private IUCAsPwdService  asPwdService;
    
    // 关联企业信息管理分页查询
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        String entResNo = filterMap.get("entResNo").toString();
        String belongEntResNo = (String) filterMap.get("belongEntResNo");
        String belongEntName = (String) filterMap.get("belongEntName");
        AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
        condition.setEntResNo(entResNo);
        condition.setBelongEntResNo(belongEntResNo);
        condition.setBelongEntName(belongEntName);
        condition.setNoCancel("1");//过滤掉注销掉的企业
        condition.setNoMcs("0");//结果包含管理公司
        PageData pd = iuCAsEntService.listAllBelongEntInfo(condition, page);
       
       return pd;
    }
    
    /**
     * 分页查看企业的修改记录信息
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.common.service.BaseServiceImpl#findScrollResult(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData queryEntInfoBakList(Map filterMap, Map sortMap, Page page) throws HsException {
        String entCustId = (String)filterMap.get("belongEntCustId");
        String updateFieldName = (String)filterMap.get("updateFieldName");
        PageData pd = iuCAsEntService.listEntUpdateLog(entCustId,updateFieldName, page);
        return pd;
    }
    
    /**
     * 企业信息查询
     * @param entCustId
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.infomanage.RelateCompanyManageService#searchEntExtInfo(java.lang.String)
     */
    @Override
    public AsEntExtendInfo searchEntExtInfo(String entCustId) throws HsException {
        return iuCAsEntService.searchEntExtInfo(entCustId);
    }
    
    /**
     * 企业系统信息-查询银行卡
     * @param entCustId
     * @return
     * @throws HsException 
     */
    @Override
    public List<AsBankAcctInfo> findBanksByCustId(String entCustId,String userType) throws HsException {
         return this.ucBankService.findBanksByCustId(entCustId,userType);
    }
    
    // 企业信息修改
    @Override
    public void UpdateEntAndLog(Map conditoanMap) throws HsException {
        
        AsEntExtendInfo updateLog = (AsEntExtendInfo)conditoanMap.get("updateLog");//修改对象
        ArrayList logList = (ArrayList)conditoanMap.get("logList");
        String operCustId = (String)conditoanMap.get("operCustId");//操作员ID
        String regionalResNo = (String)conditoanMap.get("regionalResNo");//操作员互生号
        String userName = (String)conditoanMap.get("userName");//复核员用户名
        String loginPwd = (String)conditoanMap.get("loginPwd");//复核员密码
        String secretKey = (String)conditoanMap.get("secretKey");//秘钥
        String confirmId = asPwdService.checkApsReCheckerLoginPwd(regionalResNo, userName,loginPwd, secretKey, operCustId);
        iuCAsEntService.UpdateEntAndLog(updateLog, logList, operCustId, confirmId);
    }
    
    /**
     * 企业新增、删除银行信息
     * @param conditoanMap
     * @throws HsException
     */
    @Override
    public void UpdateEntBankLog(Map conditoanMap) throws HsException {
        
        AsBankAcctInfo asBankAcctInfo = (AsBankAcctInfo)conditoanMap.get("updateLog");//修改对象
        ArrayList logList = (ArrayList)conditoanMap.get("logList");
        String operCustId = (String)conditoanMap.get("operCustId");//操作员ID
        String regionalResNo = (String)conditoanMap.get("regionalResNo");//操作员互生号
        String userName = (String)conditoanMap.get("userName");//复核员用户名
        String loginPwd = (String)conditoanMap.get("loginPwd");//复核员密码
        String secretKey = (String)conditoanMap.get("secretKey");//秘钥
        int typeEnum = Integer.parseInt((String)conditoanMap.get("typeEnum"));//类型
        String confirmId = asPwdService.checkApsReCheckerLoginPwd(regionalResNo, userName,loginPwd, secretKey, operCustId);
        iuCAsEntService.UpdateEntAccountAndLog(asBankAcctInfo,logList, typeEnum, operCustId, confirmId);
    }
}
