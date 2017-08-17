/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.businessService.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.businessService.IPointActivityApplyService;
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
        param.setApplyType(Integer.parseInt(filterMap.get("applyType").toString()));
        param.setEntName(filterMap.get("entName").toString());
        param.setEntResNo(filterMap.get("entResNo").toString());
        param.setLinkman(filterMap.get("linkman").toString());
        param.setSerResNo(filterMap.get("serResNo").toString());
        param.setStatus((filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null : Integer.parseInt(filterMap.get("status").toString())));
        
        return iBSPointActivityService.serviceQueryPointActivity(param, page);
    }

    @Override
    public Map<String, Object> findActivityApplyDetail(String applyId) throws HsException {
       
        return iBSPointActivityService.queryPointActivityById(applyId);
    }

    @Override
    public void pointActivityAppr(ApprParam param) throws HsException {
        
        iBSPointActivityService.serviceApprPointActivity(param);
    }

}
