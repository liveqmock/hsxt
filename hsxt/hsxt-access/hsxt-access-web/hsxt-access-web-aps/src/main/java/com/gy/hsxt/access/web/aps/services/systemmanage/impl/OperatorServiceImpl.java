/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.systemmanage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.systemmanage.OperatorService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition;


@SuppressWarnings("rawtypes")
@Service
public class OperatorServiceImpl extends BaseServiceImpl implements OperatorService {

    @Autowired
    private IUCAsOperatorService ucAsOperatorService;
    
    @Autowired
    private IUCAsEntGroupService iuCAsEntGroupService;
    
    @Resource 
    private IUCAsEntService iuCAsEntService;
    
    @Autowired 
    private ITMOnDutyService tmService;
    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String userName = filterMap.get("userName").toString();
        String realName = filterMap.get("realName").toString();
        String entCustId = filterMap.get("entCustId").toString();
        AsQueryOperatorCondition condition = new AsQueryOperatorCondition();
        condition.setEntCustId(entCustId);
        condition.setRealName(realName);
        condition.setUserName(userName);
        return ucAsOperatorService.listOperators(condition, page);
    }

    @Override
    public String addOper(AsOperator oper, String adminCustId, List<Long> groupIds) throws HsException {
        //新增操作员
        String operId = ucAsOperatorService.addOper(oper,adminCustId);
        //如果选择了用户组 则将操作员加入到用户组中
        if(groupIds != null && groupIds.size() > 0){
            
            List<String> operCustIds = new ArrayList<String>();
            
            operCustIds.add(operId);
            
            for(Long groupId : groupIds){
                
                iuCAsEntGroupService.addGroupUser(operCustIds, groupId, adminCustId);
            }
        }
        return operId;
    }

    @Override
    public AsOperator searchOperByCustId(String operCustId) throws HsException {

        return ucAsOperatorService.searchOperByCustId(operCustId);
    }

    @Override
    public List<AsOperator> listOperByEntCustId(String entCustId) throws HsException {

        return ucAsOperatorService.listOperByEntCustId(entCustId);
    }

    @Override
    public void updateOper(AsOperator oper, String adminCustId ,List<Long> groupIds) throws HsException {
        //如果选择了用户组 则将操作员加入到用户组中
        if(groupIds != null && groupIds.size() > 0){
            //重置用户组
           iuCAsEntGroupService.resetOperatorGroup(oper.getOperCustId(), groupIds, adminCustId);
        }
        ucAsOperatorService.updateOper(oper, adminCustId);
    }

    @Override
    public void deleteOper(String operCustId, String adminCustId) throws HsException {
        //删除操作员
        ucAsOperatorService.deleteOper(operCustId, adminCustId);
        //移除工单中相应的操作员
        tmService.removeOperator(operCustId);
    }
    
    /**
     *  获取操作员详情
     * @param apsBase
     * @return
     */
    @Override
    public Map<String, Object> getOperatorDetail(ApsBase apsBase) {
        //查询操作员信息
       AsOperator ao= ucAsOperatorService.searchOperByCustId(apsBase.getCustId());
       
       //查询企业所有信息
       AsEntAllInfo aea=iuCAsEntService.searchEntAllInfo(apsBase.getEntCustId());
       
       return operRetMap(ao,aea);
    }
    
    /**
     * 操作员信息
     * @param retMap
     * @return
     */
    private Map<String, Object> operRetMap(AsOperator ao, AsEntAllInfo aea) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (null != ao)
        {
            retMap.put("entResNo", StringUtils.trimToBlank(ao.getEntResNo()) );// 企业互生号
            retMap.put("userName", StringUtils.trimToBlank(ao.getUserName()));// 用户帐号
            retMap.put("isAdmin", ao.isAdmin());    //是否管理员
        }
        if (null != aea)
        {
            retMap.put("entName", StringUtils.trimToBlank(aea.getBaseInfo().getEntName()) ); // 企业名称
            retMap.put("entCustType", aea.getBaseInfo().getEntCustType()); // 企业类型
            retMap.put("createDate", StringUtils.trimToBlank(aea.getExtendInfo().getCreateDate()) ); // 企业注册日期
            retMap.put("expireDate", StringUtils.trimToBlank( aea.getStatusInfo().getExpireDate())); //年费缴扣日期
        }
        
        return retMap;
    }
    
    /**
     * 查询所有工单分组及分组下的所有操作员
     * @param entCustId
     * @return
     */
    public List<Group> findTaskGroupInfo(String entCustId) {
    	return this.tmService.getTaskGroupInfo(entCustId, true, true);
    }
    
}
