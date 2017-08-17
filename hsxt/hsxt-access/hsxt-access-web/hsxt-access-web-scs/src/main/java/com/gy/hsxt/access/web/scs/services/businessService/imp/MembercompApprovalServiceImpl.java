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
import com.gy.hsxt.access.web.scs.services.businessService.IMembercompApprovalService;
import com.gy.hsxt.bs.api.entstatus.IBSMemberQuitService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitApprParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class MembercompApprovalServiceImpl extends BaseServiceImpl implements IMembercompApprovalService{

    @Autowired
    private IBSMemberQuitService iBSMemberQuitService;
    @Override
    public PageData<MemberQuitBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        MemberQuitQueryParam param = new MemberQuitQueryParam();
        param.setEntName(filterMap.get("entName").toString());
        param.setEntResNo(filterMap.get("entResNo").toString());
        param.setLinkman(filterMap.get("linkman").toString());
        param.setSerResNo(filterMap.get("serResNo").toString());
        param.setStatus((filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null : Integer.parseInt(filterMap.get("status").toString())));
        return iBSMemberQuitService.serviceQueryMemberQuit(param, page);
    }


    @Override
    public Map<String, Object> findApprovalDetail(String applyId) throws HsException {
        return iBSMemberQuitService.queryMemberQuitById(applyId);
    }

    @Override
    public void apprMemberQuit(MemberQuitApprParam param) throws HsException {
        iBSMemberQuitService.serviceApprMemberQuit(param);
    }
}
