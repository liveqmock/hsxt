/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.infomanage.EntRealNameIdentificService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.RealNameQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class EntRealNameIdentificServiceImpl extends BaseServiceImpl implements EntRealNameIdentificService {

    @Autowired
    private IBSRealNameAuthService  ibSRealNameAuthService ;
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        RealNameQueryParam  param = new RealNameQueryParam();
        //结束时间
        param.setEndDate(filterMap.get("endDate").toString());
        //企业类型
        Integer entType = filterMap.get("entType") == null || "".equals(filterMap.get("entType").toString())?null:Integer.parseInt(filterMap.get("entType").toString());
        param.setEntType(entType);
        //企业名称
        param.setName(filterMap.get("entName").toString());
        //企业互生号
        param.setResNo(filterMap.get("entResNo").toString());
        //开始时间
        param.setStartDate(filterMap.get("startDate").toString());
        //状态
        Integer status = filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null:Integer.parseInt(filterMap.get("status").toString());
        param.setStatus(status);
        
        return ibSRealNameAuthService.queryEntRealnameAuth(param, page);
    }
    @Override
    public EntRealnameAuth queryEntRealnameAuthById(String applyId) throws HsException {
        
        return ibSRealNameAuthService.queryEntRealnameAuthById(applyId);
    }
    @Override
    public void apprEntRealnameAuth(ApprParam apprParam) throws HsException {
        
        ibSRealNameAuthService.apprEntRealnameAuth(apprParam);
    }
    @Override
    public void reviewApprEntRealnameAuth(ApprParam apprParam) throws HsException {
        ibSRealNameAuthService.reviewApprEntRealnameAuth(apprParam);
    }
   
    @Override
    public void modifyEntRealnameAuth(EntRealnameAuth entRealnameAuth) throws HsException {
        ibSRealNameAuthService.modifyEntRealnameAuth(entRealnameAuth);
    }
    @Override
    public PageData<OptHisInfo> queryEntRealnameAuthHis(String applyId, Page page) throws HsException {
        return ibSRealNameAuthService.queryEntRealnameAuthHis(applyId, page);
    }
    @Override
    public PageData queryApprResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        RealNameQueryParam  param = new RealNameQueryParam();
        //结束时间
        param.setEndDate(filterMap.get("endDate").toString());
        //企业类型
        Integer entType = filterMap.get("entType") == null || "".equals(filterMap.get("entType").toString())?null:Integer.parseInt(filterMap.get("entType").toString());
        param.setEntType(entType);
        //企业名称
        param.setName(filterMap.get("entName").toString());
        //企业互生号
        param.setResNo(filterMap.get("entResNo").toString());
        //开始时间
        param.setStartDate(filterMap.get("startDate").toString());
        //状态
        Integer status = filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null:Integer.parseInt(filterMap.get("status").toString());
        param.setStatus(status);
        //操作员客户号
        param.setOptCustId(filterMap.get("custId").toString());
        
        return ibSRealNameAuthService.queryEntRealnameAuth4Appr(param, page);
    }
    
}
