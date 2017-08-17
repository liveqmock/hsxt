/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.workflow.McsBizTypeAuthBean;
import com.gy.hsxt.access.web.bean.workflow.McsCustomScheduleOpt;
import com.gy.hsxt.access.web.bean.workflow.McsGroupBean;
import com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.McsMembersScheduleBean;
import com.gy.hsxt.access.web.bean.workflow.McsScheduleBean;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.mcs.services.common.MCSConfigService;
import com.gy.hsxt.ao.enumtype.AccountingStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMBizAuthService;
import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.tm.api.ITMWorkPlanService;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 日程安排服务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.workflow
 * @ClassName: ScheduleServiceImpl
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-18 下午4:13:06
 * @version V1.0
 */
@Service
public class ScheduleServiceImpl extends BaseServiceImpl implements IScheduleService {

    /**
     * 注入操作员服务
     */
    @Resource
    private IUCAsOperatorService iUCAsOperatorService;

    /**
     * 注入值班管理服务
     */
    @Resource
    private ITMOnDutyService itmOnDutyService;

    /**
     * 注入值班计划服务
     */
    @Resource
    private ITMWorkPlanService itmWorkPlanService;
    
    /** 注入 业务权限管理dubbo服务 */
    @Resource
    private ITMBizAuthService itmBizAuthService;

    /**
    /**
     * 平台配置参数服务
     */
    @Resource    
    private MCSConfigService mcsConfigService;
    
    /**
     * 获取值班组信息
     * @param mcsBase
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#getGroupList(com.gy.hsxt.access.web.bean.MCSBase)
     */
    @Override
    public List<Map<String, Object>> getGroupList(MCSBase mcsBase) throws HsException {
        List<Map<String, Object>> retList = new ArrayList<>();

        // 获取所有值班组
        List<Group> glist = itmOnDutyService.getGroupList(mcsBase.getEntCustId());

        // 遍历
        for (Group g : glist)
        {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("id", g.getGroupId());
            tempMap.put("name", g.getGroupName());
            tempMap.put("opened", (g.getOpened() ? 1 : 0));
            retList.add(tempMap);
        }

        return retList;
    }

    /**
     * 获取值班组组员日程安排
     * @param amsb
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#getMembersSchedule(com.gy.hsxt.access.web.bean.workflow.McsMembersScheduleBean)
     */
    @Override
    public Map<String, Object> getMembersSchedule(McsMembersScheduleBean amsb) throws HsException {
        // 获取值班员信息
        Map<String, Object> oMap = itmOnDutyService.getOperatorSchedule(amsb.getGroupId(), amsb.getYear(), amsb
                .getMonth());

        McsMembersScheduleHandle ams = new McsMembersScheduleHandle(amsb, oMap);

        return ams.retMap;
    }

    /**
     * 查询值班组信息
     * @param amsb
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#getGroupInfo(com.gy.hsxt.access.web.bean.workflow.McsMembersScheduleBean)
     */
    @Override
    public List<Group> getGroupInfo(McsMembersScheduleBean amsb) throws HsException {
        return itmOnDutyService.getGroupInfo(amsb.getGroupId());
    }

    /**
     * 保存值班组
     * @param ag
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#groupAdd(com.gy.hsxt.access.web.bean.workflow.McsGroupBean)
     */
    @Override
    public void groupAdd(McsGroupBean ag, MCSBase mcsBase) throws HsException {
        //操作权限验证
       // this.scheduleOperatePrivilege(mcsBase);
        
        // 工作组存在值班主任
        memberOfficerExits(ag);

        // 值班组新增
        itmOnDutyService.saveGroup(ag, ag.getOperators());
    }

    /**
     * 值班组更新
     * @param ag
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#groupUpdate(com.gy.hsxt.access.web.bean.workflow.McsGroupBean)
     */
    @Override
    public void groupUpdate(McsGroupBean ag, MCSBase mcsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        // 工作组存在值班主任
        memberOfficerExits(ag);

        // 值班组修改
        itmOnDutyService.modifyGroup(ag, ag.getOperators(), ag.getScheduleId());
    }

    /**
     * 判断存在值班主任角色
     */
    void memberOfficerExits(McsGroupBean ag) {
        // 存在值班主任总和
        int exitsNum = 0;

        // 遍历值班员工
        for (Operator o : ag.getOperators())
        {
            if (o.getChief())
            {
                exitsNum++;
            }
        }

        // 工作组无值班主任则抛异常
        if (exitsNum == 0)
        {
            throw new HsException(ASRespCode.MW_SELECT_DUTY_OFFICER);
        }
    }

    /**
     * 新增值班员业务节点
     * @param abtab
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#addBizType(com.gy.hsxt.access.web.bean.workflow.McsBizTypeAuthBean)
     */
    @Override
    public void addBizType(McsBizTypeAuthBean abtab, MCSBase mcsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        //增加业务节点
        itmOnDutyService.addOperatorBizType(abtab.getBtaList());
    }

    /**
     * 删除值班员业务节点
     * @param abtab
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#deleteBizType(com.gy.hsxt.access.web.bean.workflow.McsBizTypeAuthBean)
     */
    @Override
    public void deleteBizType(McsBizTypeAuthBean abtab, MCSBase mcsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        //删除业务节点
        itmOnDutyService.deleteOptCustAuth(abtab.bizTypeSet(), abtab.getBtaList().get(0).getOptCustId());
    }

    /**
     * 移除值班组组员
     * @param groupId
     * @param optCustId
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#removeOperator(java.lang.String, java.lang.String)
     */
    @Override
    public void removeOperator(String groupId, String optCustId, MCSBase mcsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        //移除
        itmOnDutyService.removeOperator(groupId, optCustId);
    }

    /**
     * 值班组开启或关闭
     * @param agub
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#udpateGroupOpenedStatus(com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean)
     */
    @Override
    public void udpateGroupOpenedStatus(McsGroupUpdateBean agub, MCSBase mcsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        //更新状态
        itmOnDutyService.udpateGroupOpenedStatus(agub.getGroupId(), agub.getOpened());
    }

    /**
     * 获取值班员明细
     * @param optCustId
     * @param groupId
     * @param scheduleId
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#getAttendantInfo(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> getAttendantInfo(String optCustId, String groupId, String scheduleId) throws HsException {
        return itmOnDutyService.getOperatorInfo(optCustId, groupId, scheduleId);
    }

    /**
     * 保存值班计划
     * @param asb
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#saveSchedule(com.gy.hsxt.access.web.bean.workflow.McsScheduleBean)
     */
    @Override
    public Map<String, String> saveSchedule(McsScheduleBean asb, MCSBase mcsBase) throws HsException {
        Map<String, String> retMap = new HashMap<String, String>();

        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        // 获取值班计划
        String scheduleId = itmWorkPlanService.saveSchedule(asb.getSchedule());

        retMap.put("scheduleId", scheduleId);

        return retMap;
    }

    /**
     * 执行值班计划
     * @param acso
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#executeSchedule(com.gy.hsxt.access.web.bean.workflow.McsCustomScheduleOpt)
     */
    @Override
    public Map<String, String> executeSchedule(McsCustomScheduleOpt acso, MCSBase mcsBase) throws HsException {
        Map<String, String> retMap = new HashMap<String, String>();
        
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);

        // 获取值班计划
        String scheduleId = itmWorkPlanService.executeSchedule(acso.getCso());

        retMap.put("scheduleId", scheduleId);

        return retMap;

    }

    /**
     * 暂停值班组计划
     * @param agub
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#pauseSchedule(com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean)
     */
    @Override
    public void pauseSchedule(McsGroupUpdateBean agub, MCSBase mcsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(mcsBase);
        
        //暂停
        itmWorkPlanService.pauseSchedule(agub.getGroupId(), agub.getEntCustId(), agub.getCustEntName(), agub
                .getCustId(), agub.getCustName());
    }

    /**
     * 获取企业下操作员
     * @param mcsBase
     * @return 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#getListOperByEntCustId(com.gy.hsxt.access.web.bean.MCSBase)
     */
    @Override
    public List<Map<String, String>> getListOperByEntCustId(MCSBase mcsBase) {
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();

        // 获取操作员
        List<AsOperator> aoList = iUCAsOperatorService.listOperAndRoleByEntCustId(mcsBase.getEntCustId());

        if (retList != null)
        {
            for (AsOperator ao : aoList)
            {
                //过滤角色为空、非启用状态
               if(ao.getRoleList() == null || ao.getRoleList().size() <= 0 || !AccountingStatus.UN_ACC.getCode().equals(ao.getAccountStatus())){
                    continue;
                }
                
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("optCustId", ao.getOperCustId());
                tempMap.put("operatorName", ao.getRealName());
                tempMap.put("workNo", ao.getUserName());
                retList.add(tempMap);
            }
        }

        return retList;
    }

    /**
     * 导出
     * @param amsb
     * @return 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService#export(com.gy.hsxt.access.web.bean.workflow.McsMembersScheduleBean)
     */
    @Override
    public McsScheduleExprotDataInit export(McsMembersScheduleBean amsb) {
        // 获取值班员信息
        Map<String, Object> oMap = itmOnDutyService.getOperatorSchedule(amsb.getGroupId(), amsb.getYear(), amsb
                .getMonth());

        System.out.println(JSON.toJSONString(oMap));

        return new McsScheduleExprotDataInit(amsb, oMap);
    }
    
    /**
     * 获取操作员对应的业务类型
     * @param optCustId
     * @return 
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getBizTypeList(java.lang.String)
     */
    @Override
    public List<BizType> getBizTypeList(String optCustId) {
        return itmBizAuthService.getBizTypeList(optCustId);
    }

    /**
     * 判断操作员是否为值班主任。 
     * 值班员(操纵员)为主任时，才能操作转入待办功能
     * @param mcsBase
     * @return
     * @throws HsException
     */
    @Override
    public boolean isChief(MCSBase mcsBase) throws HsException {
        return itmOnDutyService.isChief(mcsBase.getCustId());
    }
    
    /**
     * 日程安排操作权限判断
     * 当用户为管理员或者操作员为值班主任时，可进行操作，否则一律拒绝
     * @param apsBase
     * @throws HsException
     */
    private void scheduleOperatePrivilege(MCSBase mcsBase) throws HsException {

        boolean chiefStatus = this.isChief(mcsBase);        // 值班主任状态
        String adminUser = mcsConfigService.getAdminUser(); // 管理员帐号

        // 验证满足条件
        if (!adminUser.equals(mcsBase.getCustName()) && !chiefStatus) {
            throw new HsException(ASRespCode.MW_SCHEDULE_NO_PRIVILEGE);
        }
    }
    
}
