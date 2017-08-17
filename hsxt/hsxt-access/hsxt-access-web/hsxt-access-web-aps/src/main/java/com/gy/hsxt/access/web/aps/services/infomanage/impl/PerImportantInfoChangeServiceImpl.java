/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.infomanage.PerImportantInfoChangeService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.ChangeInfoQueryParam;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;


@SuppressWarnings("rawtypes")
@Service
public class PerImportantInfoChangeServiceImpl extends BaseServiceImpl implements PerImportantInfoChangeService {

    @Autowired
    private IBSChangeInfoService  ibSChangeInfoService ;
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        ChangeInfoQueryParam param = new ChangeInfoQueryParam();
        //结束时间
        param.setEndDate((String)filterMap.get("endDate"));
        //消费者名称
        param.setName((String)filterMap.get("perName"));
        //消费者互生号
        param.setResNo((String)filterMap.get("perResNo"));
        //开始时间
        param.setStartDate((String)filterMap.get("startDate"));
        //状态
        Integer status = filterMap.get("status") == null || "".equals((String)filterMap.get("status"))?null:Integer.parseInt((String)filterMap.get("status"));
        param.setStatus(status);
       
        return ibSChangeInfoService.queryPerChange(param, page);
        
    }
    
    @Override
    public PerChangeInfo queryPerChangeById(String applyId) throws HsException {
        return ibSChangeInfoService.queryPerChangeById(applyId);
    }
    
    @Override
    public void apprPerChange(ApprParam apprParam) throws HsException {
        ibSChangeInfoService.apprPerChange(apprParam);
    }
    
    @Override
    public void reviewApprPerChange(ApprParam apprParam) throws HsException {
        ibSChangeInfoService.reviewApprPerChange(apprParam);
    }
    
    @Override
    public void modifyPerChange(PerChangeInfo perChangeInfo) throws HsException {
        ibSChangeInfoService.modifyPerChange(perChangeInfo);
    }

    @Override
    public PageData<OptHisInfo> queryPerChangeHis(String applyId, Page page) throws HsException {
       
        return ibSChangeInfoService.queryPerChangeHis(applyId, page);
    }

    @Override
    public PageData queryApprResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        ChangeInfoQueryParam param = new ChangeInfoQueryParam();
        //结束时间
        param.setEndDate(filterMap.get("endDate").toString());
        //消费者名称
        param.setName(filterMap.get("entName").toString());
        //消费者互生号
        param.setResNo(filterMap.get("entResNo").toString());
        //开始时间
        param.setStartDate(filterMap.get("startDate").toString());
        //状态
        Integer status = filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null:Integer.parseInt(filterMap.get("status").toString());
        param.setStatus(status);
        //操作员客户号
        param.setOptCustId(filterMap.get("custId").toString());
       
        return ibSChangeInfoService.queryPerChange4Appr(param, page);
    }
}
