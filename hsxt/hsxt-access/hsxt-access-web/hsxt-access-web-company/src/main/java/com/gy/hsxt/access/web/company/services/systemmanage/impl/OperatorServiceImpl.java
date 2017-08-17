/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemmanage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemmanage.OperatorService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition;


@SuppressWarnings("rawtypes")
@Service
public class OperatorServiceImpl extends BaseServiceImpl implements OperatorService {

    @Autowired
    private IUCAsOperatorService iUCAsOperatorService;
    
    @Resource
    private IUCAsEntService iuCAsEntService;
    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String userName = filterMap.get("userName").toString();
        String realName = filterMap.get("realName").toString();
        String entCustId = filterMap.get("entCustId").toString();
        AsQueryOperatorCondition condition = new AsQueryOperatorCondition();
        condition.setEntCustId(entCustId);
        condition.setRealName(realName);
        condition.setUserName(userName);
        return iUCAsOperatorService.listOperators(condition, page);
    }

    @Override
    public String addOper(AsOperator oper, String adminCustId) throws HsException {
        //新增操作员
        String operId = iUCAsOperatorService.addOper(oper,adminCustId);
        
        return operId;
    }

    @Override
    public AsOperator searchOperByCustId(String operCustId) throws HsException {

        return iUCAsOperatorService.searchOperByCustId(operCustId);
    }

    @Override
    public List<AsOperator> listOperByEntCustId(String entCustId) throws HsException {

        return iUCAsOperatorService.listOperByEntCustId(entCustId);
    }

    @Override
    public void updateOper(AsOperator oper, String adminCustId) throws HsException {
        
        iUCAsOperatorService.updateOper(oper, adminCustId);
    }

    @Override
    public void deleteOper(String operCustId, String adminCustId) throws HsException {
       
        iUCAsOperatorService.deleteOper(operCustId, adminCustId);
    }

    @Override
    public void bindCard(String operCustId, String perResNo, String adminCustId) throws HsException {
        iUCAsOperatorService.bindCard(operCustId, perResNo, adminCustId);
    }

    @Override
    public void confirmBindCard(String perResNo) throws HsException {
        iUCAsOperatorService.confirmBindCard(perResNo);
    }

    @Override
    public void unBindCard(String operCustId) throws HsException {
        iUCAsOperatorService.unBindCard(operCustId);
    }
    
    /**
     * 获取操作员详情
     * @param scsBase
     * @return 
     * @see com.gy.hsxt.access.web.scs.services.systemmanage.OperatorService#getOperatorDetail(com.gy.hsxt.access.web.bean.SCSBase)
     */
    @Override
    public Map<String, Object> getOperatorDetail(CompanyBase companyBase) {
        //查询操作员信息
       AsOperator ao= iUCAsOperatorService.searchOperByCustId(companyBase.getCustId());
       
       //查询企业所有信息
       AsEntAllInfo aea=iuCAsEntService.searchEntAllInfo(companyBase.getEntCustId());
       
       return operRetMap(ao,aea);
    }
    
    /**
     * 操作员信息
     * @param retMap
     * @return
     */
    private Map<String, Object> operRetMap(AsOperator ao, AsEntAllInfo aea) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (null == ao)
            {ao = new AsOperator(); }
        if (null == aea)
            {aea = new AsEntAllInfo();}

        //retMap.put("lastLoginDate", StringUtils.trimToBlank(ao.getLastLoginDate()) ); // 最后登录时间
        //retMap.put("lastLoginIp", StringUtils.trimToBlank(ao.getLastLoginIp()) ); // 最后登录ip
        retMap.put("entResNo", StringUtils.trimToBlank(ao.getEntResNo()) );// 企业互生号
        retMap.put("userName", StringUtils.trimToBlank(ao.getUserName()));// 用户帐号
        retMap.put("entName", StringUtils.trimToBlank(aea.getBaseInfo().getEntName()) ); // 企业名称
        retMap.put("entCustType", aea.getBaseInfo().getEntCustType()); // 企业类型
        retMap.put("createDate", StringUtils.trimToBlank(aea.getExtendInfo().getCreateDate()) ); // 企业注册日期
        retMap.put("expireDate", StringUtils.trimToBlank( aea.getStatusInfo().getExpireDate())); //年费缴扣日期
        retMap.put("openDate", StringUtils.trimToBlank( aea.getStatusInfo().getOpenDate())); //企业注册日期  ---用这个
        return retMap;
    }

}
