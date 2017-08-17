/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.infomanage.IPointActivityApplyService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSPointActivityService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.PointActivityQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;


@SuppressWarnings("rawtypes")
@Service
public class PointActivityApplyServiceImpl extends BaseServiceImpl implements IPointActivityApplyService {

    @Autowired
    private IBSPointActivityService iBSPointActivityService;
    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
       
        PointActivityQueryParam param = new PointActivityQueryParam ();
        //申请类别 0 为停止积分活动申请,1为参与积分活动申请
        param.setApplyType(Integer.parseInt(filterMap.get("applyType").toString()));
        //企业名称
        param.setEntName(filterMap.get("entName").toString());
        //服务公司互生号
        param.setEntResNo(filterMap.get("entResNo").toString());
        //联系人
        param.setLinkman(filterMap.get("linkman").toString());
        //申请状态
        param.setStatus((filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null : Integer.parseInt(filterMap.get("status").toString())));
        
        return iBSPointActivityService.platQueryPointActivity(param, page);
    }
    
    @Override
    public Map<String, Object> findActivityApplyDetail(String applyId) throws HsException {
        return iBSPointActivityService.queryPointActivityById(applyId);
    }

    @Override
    public void pointActivityAppr(ApprParam param) throws HsException {
        iBSPointActivityService.platApprPointActivity(param);
    }

    @Override
    public void reviewActivityAppr(ApprParam param) throws HsException {
        iBSPointActivityService.platReviewPointActivity(param);
    }

    @Override
    public PageData queryApprResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
            PointActivityQueryParam param = new PointActivityQueryParam ();
            //申请类别 0 为停止积分活动申请,1为参与积分活动申请
            param.setApplyType(Integer.parseInt(filterMap.get("applyType").toString()));
            //企业名称
            param.setEntName(filterMap.get("entName").toString());
            //服务公司互生号
            param.setEntResNo(filterMap.get("entResNo").toString());
            //联系人
            param.setLinkman(filterMap.get("linkman").toString());
            //申请状态
            param.setStatus((filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null : Integer.parseInt(filterMap.get("status").toString())));
            //操作员客户号
            param.setOptCustId(filterMap.get("custId").toString());
            
            return iBSPointActivityService.platQueryPointActivity4Appr(param, page);
    }

}
