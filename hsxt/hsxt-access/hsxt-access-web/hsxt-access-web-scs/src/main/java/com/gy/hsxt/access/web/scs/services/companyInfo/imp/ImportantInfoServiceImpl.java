/**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.companyInfo.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.companyInfo.IImportantInfoService;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.bean.entstatus.ChangeInfoQueryParam;
import com.gy.hsxt.bs.bean.entstatus.EntChangeBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

@Service
public class ImportantInfoServiceImpl extends BaseServiceImpl implements IImportantInfoService {
    @Autowired
    private IBSChangeInfoService bsService;
    
    public EntChangeInfo findEntChangeInfo(String entCustId){
        
        return bsService.queryEntChangeByCustId(entCustId);
    }

    @Override
    public Object submitChangeApply(EntChangeInfo info) throws HsException {
        bsService.createEntChange(info);
        return info;
    }

    @Override
    public PageData<EntChangeBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        ChangeInfoQueryParam changeInfoQueryParam = new ChangeInfoQueryParam();
        changeInfoQueryParam.setEndDate(filterMap.get("endDate").toString());
        changeInfoQueryParam.setEntType(Integer.parseInt(filterMap.get("entType").toString()));
        changeInfoQueryParam.setResNo(filterMap.get("resNo").toString());
        changeInfoQueryParam.setStartDate(filterMap.get("startDate").toString());
        if(!StringUtils.isEmpty(filterMap.get("status"))){
            changeInfoQueryParam.setStatus(Integer.parseInt(filterMap.get("status").toString()));
        }
       
        return  bsService.entQueryChange(changeInfoQueryParam, page);
    }

    @Override
    public EntChangeInfo findApplyDetail(String applyId) throws HsException {
        
        return bsService.queryEntChangeById(applyId);
        
    }

	@Override
	public EntChangeInfo findApplyOrder(String entCustId) throws HsException {
		EntChangeInfo info = bsService.queryEntChangeByCustId(entCustId);
		return info;
	}


}
