/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.services.systemmanage.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.mcs.services.systemmanage.EntGroupService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;

@Service
@SuppressWarnings("rawtypes")
public class EntGroupServiceImpl extends BaseServiceImpl implements EntGroupService {

    @Autowired
    private IUCAsEntGroupService iuCAsEntGroupService;

    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String entCustId = filterMap.get("entCustId").toString();
        return iuCAsEntGroupService.listGroup(entCustId, page);
    }

    @Override
    public Long addGroup(AsEntGroup group, String operator) throws HsException {
        iuCAsEntGroupService.addGroup(group, operator);
        return findGroup(group.getEntCustId(), group.getGroupName()).getGroupId();
    }

    @Override
    public void updateGroup(AsEntGroup group, String operator) throws HsException {
        iuCAsEntGroupService.updateGroup(group, operator);
    }

    @Override
    public void deleteGroup(Long groupId, String operator) throws HsException {
        iuCAsEntGroupService.deleteGroup(groupId, operator);
    }

    @Override
    public void addGroupUser(List<String> operCustIds, Long groupId, String operator) throws HsException {
        iuCAsEntGroupService.addGroupUser(operCustIds, groupId, operator);
    }

    @Override
    public void deleteGroupUser(String operCustId, Long groupId, String operator) throws HsException {
        iuCAsEntGroupService.deleteGroupUser(operCustId, groupId, operator);
    }

    @Override
    public AsEntGroup findGroup(String entCustId, String groupName) throws HsException {
        
        return iuCAsEntGroupService.findGroup(entCustId, groupName);
    }

    @Override
    public void resetGroupOperator(List<String> operCustIds, Long groupId, String operator) throws HsException {
        cleanGroupOperator(groupId, operator);
        addGroupUser(operCustIds, groupId, operator);
    }

    @Override
    public void cleanGroupOperator(Long groupId, String operator) throws HsException {
        iuCAsEntGroupService.clearAllGroupUser(groupId, operator);
    }
    
    
}
