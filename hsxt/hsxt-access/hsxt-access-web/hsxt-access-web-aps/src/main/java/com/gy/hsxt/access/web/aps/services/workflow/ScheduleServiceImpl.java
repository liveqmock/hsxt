/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.common.ApsConfigService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.workflow.ApsBizTypeAuthBean;
import com.gy.hsxt.access.web.bean.workflow.ApsCustomScheduleOpt;
import com.gy.hsxt.access.web.bean.workflow.ApsGroupBean;
import com.gy.hsxt.access.web.bean.workflow.ApsGroupUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean;
import com.gy.hsxt.access.web.bean.workflow.ApsScheduleBean;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.ao.enumtype.AccountingStatus;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMBizAuthService;
import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.tm.api.ITMTaskService;
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
    private IUCAsOperatorService ucAsOperatorService;

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
    
    /**
     * 注入业务权限管理服务 
     */
    @Resource
    private ITMBizAuthService itmBizAuthService;
    
    /**
     * 注入任务工单服务 
     */
    @Resource
    private ITMTaskService itmTaskService;
    
    /**
     * 平台配置参数服务
     */
    @Resource    
    private ApsConfigService apsConfigService;
    /**
     * 获取值班组信息
     * 
     * @param apsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getGroupList(com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public List<Map<String, Object>> getGroupList(ApsBase apsBase) throws HsException {
        List<Map<String, Object>> retList = new ArrayList<>();

        // 获取所有值班组
        List<Group> glist = itmOnDutyService.getGroupList(apsBase.getEntCustId());

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
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getMembersSchedule(com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean)
     */
    @Override
    public Map<String, Object> getMembersSchedule(ApsMembersScheduleBean amsb) throws HsException {

        // 获取值班员信息
        Map<String, Object> oMap = itmOnDutyService.getOperatorSchedule(amsb.getGroupId(), amsb.getYear(), amsb
                .getMonth());

        ApsMembersScheduleHandle ams = new ApsMembersScheduleHandle(amsb, oMap);

        return ams.retMap;
    }

    /**
     * 查询值班组信息
     * @param amsb
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getGroupInfo(com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean)
     */
    @Override
    public List<Group> getGroupInfo(ApsMembersScheduleBean amsb) throws HsException {
        return itmOnDutyService.getGroupInfo(amsb.getGroupId());
    }

    /**
     * 保存值班组
     * 
     * @param ag
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#groupAdd(com.gy.hsxt.access.web.bean.workflow.ApsGroupBean)
     */
    @Override
    public void groupAdd(ApsGroupBean ag, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        // 工作组存在值班主任
        memberOfficerExits(ag);

        // 值班组新增
        itmOnDutyService.saveGroup(ag, ag.getOperators());
    }

    /**
     * 值班组更新
     * 
     * @param ag
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#groupUpdate(com.gy.hsxt.access.web.bean.workflow.ApsGroupBean)
     */
    @Override
    public void groupUpdate(ApsGroupBean ag, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        // 工作组存在值班主任
        memberOfficerExits(ag);

        // 值班组修改
        itmOnDutyService.modifyGroup(ag, ag.getOperators(), ag.getScheduleId());
    }

    /**
     * 判断存在值班主任角色
     */
    void memberOfficerExits(ApsGroupBean ag) {
        // 存在值班主任总和
        int exitsNum = 0;

        // 遍历值班员工
        for (Operator o : ag.getOperators())
        {
            if (o.getChief()) {
                exitsNum++;
            }
        }
        
        // 工作组无值班主任则抛异常
        if (exitsNum == 0) {
            throw new HsException(RespCode.APS_SELECT_DUTY_OFFICER);
        }
    }

    /**
     * 新增值班员业务节点
     * 
     * @param abtab
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#addBizType(com.gy.hsxt.access.web.bean.workflow.ApsBizTypeAuthBean)
     */
    @Override
    public void addBizType(ApsBizTypeAuthBean abtab, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        //增加业务节点
        itmOnDutyService.addOperatorBizType(abtab.getBtaList());
    }

    /**
     * 删除值班员业务节点
     * 
     * @param abtab
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#deleteBizType(com.gy.hsxt.access.web.bean.workflow.ApsBizTypeAuthBean)
     */
    @Override
    public void deleteBizType(ApsBizTypeAuthBean abtab, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        //删除业务节点
        itmOnDutyService.deleteOptCustAuth(abtab.bizTypeSet(), abtab.getBtaList().get(0).getOptCustId());
    }

    /**
     * 移除值班组组员
     * 
     * @param groupId
     * @param optCustId
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#removeOperator(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void removeOperator(String groupId, String optCustId, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        //移除
        itmOnDutyService.removeOperator(groupId, optCustId);
    }

    /**
     * 值班组开启或关闭
     * 
     * @param agub
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#udpateGroupOpenedStatus(com.gy.hsxt.access.web.bean.workflow.ApsGroupUpdateBean)
     */
    @Override
    public void udpateGroupOpenedStatus(ApsGroupUpdateBean agub, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        //更新状态
        itmOnDutyService.udpateGroupOpenedStatus(agub.getGroupId(), agub.getOpened());
    }

    /**
     * 获取值班员明细
     * 
     * @param optCustId
     * @param groupId
     * @param scheduleId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getAttendantInfo(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> getAttendantInfo(String optCustId, String groupId, String scheduleId) throws HsException {
        return itmOnDutyService.getOperatorInfo(optCustId, groupId, scheduleId);
    }

    /**
     * 保存值班计划
     * 
     * @param asb
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#saveSchedule(com.gy.hsxt.access.web.bean.workflow.ApsScheduleBean)
     */
    @Override
    public Map<String, String> saveSchedule(ApsScheduleBean asb, ApsBase apsBase) throws HsException {
        Map<String, String> retMap = new HashMap<String, String>();

        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        // 获取值班计划
        String scheduleId = itmWorkPlanService.saveSchedule(asb.getSchedule());

        retMap.put("scheduleId", scheduleId);

        return retMap;
    }

    /**
     * 执行值班计划
     * 
     * @param acso
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#executeSchedule(com.gy.hsxt.access.web.bean.workflow.ApsCustomScheduleOpt)
     */
    @Override
    public Map<String, String> executeSchedule(ApsCustomScheduleOpt acso, ApsBase apsBase) throws HsException {
        Map<String, String> retMap = new HashMap<String, String>();
        
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);

        // 获取值班计划
        String scheduleId = itmWorkPlanService.executeSchedule(acso.getCso());

        retMap.put("scheduleId", scheduleId);

        return retMap;

    }

    /**
     * 暂停值班组计划
     * 
     * @param agub
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#pauseSchedule(com.gy.hsxt.access.web.bean.workflow.ApsGroupUpdateBean)
     */
    @Override
    public void pauseSchedule(ApsGroupUpdateBean agub, ApsBase apsBase) throws HsException {
        //操作权限验证
        //this.scheduleOperatePrivilege(apsBase);
        
        //暂停
        itmWorkPlanService.pauseSchedule(agub.getGroupId(), agub.getEntCustId(), agub.getCustEntName(), agub
                .getCustId(), agub.getCustName());
    }

    /**
     * 获取企业下操作员
     * 
     * @param apsBase
     * @return
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getListOperByEntCustId(com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public List<Map<String, String>> getListOperByEntCustId(ApsBase apsBase) {
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();

        // 获取操作员
        List<AsOperator> aoList = ucAsOperatorService.listOperAndRoleByEntCustId(apsBase.getEntCustId());

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
     * 
     * @param amsb
     * @return
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#export(com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean)
     */
    @Override
    public ApsScheduleExprotDataInit export(ApsMembersScheduleBean amsb) {
        // 获取值班员信息
        Map<String, Object> oMap = itmOnDutyService.getOperatorSchedule(amsb.getGroupId(), amsb.getYear(), amsb
                .getMonth());

        return new ApsScheduleExprotDataInit(amsb, oMap);

    }

    /**
     * 获取操作员对应的业务类型
     * @param optCustId
     * @return 
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#getBizTypeList(java.lang.String)
     */
    @Override
    public List<BizType> getBizTypeList(String optCustId) throws HsException  {
        return itmBizAuthService.getBizTypeList(optCustId);
    }

    /**
     * 判断操作员是否为值班主任。 
     * 值班员(操纵员)为主任时，才能操作转入待办功能
     * @param apsBase
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#isChief(com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public boolean isChief(ApsBase apsBase) throws HsException {
        return itmOnDutyService.isChief(apsBase.getCustId());
    }
    
    /**
     * 日程安排操作权限判断
     * 当用户为管理员或者操作员为值班主任时，可进行操作，否则一律拒绝
     * @param apsBase
     * @throws HsException
     */
    private void scheduleOperatePrivilege(ApsBase apsBase) throws HsException {

        boolean chiefStatus = this.isChief(apsBase);        // 值班主任状态
        String adminUser = apsConfigService.getAdminUser(); // 管理员帐号

        // 验证满足条件
        if (!adminUser.equals(apsBase.getCustName()) && !chiefStatus) {
            throw new HsException(ASRespCode.APS_SCHEDULE_NO_PRIVILEGE);
        }
    }
    
}
