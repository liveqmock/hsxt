/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.service;

import com.gy.hsec.external.api.EnterpriceService;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.entstatus.IBSCloseOpenEntService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntDetail;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntInfo;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntQueryParam;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.entstatus.CloseOpenEntStatus;
import com.gy.hsxt.bs.common.enumtype.entstatus.CloseOpenEntType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.entstatus.mapper.CloseOpenEntMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

/**
 * 关闭开启系统
 *
 * @Package : com.gy.hsxt.bs.entstatus.service
 * @ClassName : CloseOpenEntService
 * @Description : 关闭开启系统
 * @Author : xiaofl
 * @Date : 2015-12-16 下午5:33:02
 * @Version V1.0
 */
@Service
public class CloseOpenEntService implements IBSCloseOpenEntService {

    @Resource
    private CloseOpenEntMapper closeOpenEntMapper;

    @Resource
    private BsConfig bsConfig;

    @Resource
    private ICommonService commonService;

    @Resource
    private ITaskService taskService;

    @Resource
    private IUCBsEntService bsEntService;
    
    @Autowired
    EnterpriceService enterpriceService;

    /**
     * 申请关闭系统
     *
     * @param closeOpenEnt 关闭系统信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void closeEnt(CloseOpenEnt closeOpenEnt) throws HsException {
        BizLog.biz(this.getClass().getName(), "closeEnt", "input param:"+closeOpenEnt,null);
        
        //校验参数
        HsAssert.notNull(closeOpenEnt, RespCode.PARAM_ERROR, "申请关闭企业[closeOpenEnt]为null");
        HsAssert.hasText(closeOpenEnt.getEntCustId(), RespCode.PARAM_ERROR, "申请关闭企业[entCustId]为空");
        HsAssert.hasText(closeOpenEnt.getEntResNo(), RespCode.PARAM_ERROR, "申请关闭企业[entResNo]为空");
        
//        // 判断是否存在正在审批的开关系统申请
//        if (closeOpenEntMapper.existApplying(closeOpenEnt.getEntCustId())) {
//            throw new HsException(BSRespCode.BS_CLOSE_OPEN_EXIST_APPLYING, "存在正在审批的开关系统申请");
//        }
        try {
            BsEntMainInfo mainInfo = bsEntService.searchEntMainInfo(closeOpenEnt.getEntCustId());
            if(mainInfo!=null){
                closeOpenEnt.setEntCustName(mainInfo.getEntName());
                closeOpenEnt.setAddress(mainInfo.getEntRegAddr());
                closeOpenEnt.setLinkman(mainInfo.getContactPerson());
                closeOpenEnt.setMobile(mainInfo.getContactPhone());
                closeOpenEnt.setCustType(mainInfo.getEntCustType());
            }
            
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            closeOpenEnt.setApplyId(applyId);
            closeOpenEnt.setApplyType(CloseOpenEntType.CLOSE_SYS.getCode());

            int row = closeOpenEntMapper.createCloseOpenEnt(closeOpenEnt);
            if(row ==1){
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE100.getCode(), commonService.getAreaPlatCustId());
                task.setHsResNo(closeOpenEnt.getEntResNo());
                task.setCustName(closeOpenEnt.getEntCustName());
                taskService.saveTask(task);
            }else{
                // 判断是否存在正在审批的开关系统申请,改成在数据库进行判断
                throw new HsException(BSRespCode.BS_CLOSE_OPEN_EXIST_APPLYING, "存在正在审批的开关系统申请");
            }
        }catch (HsException e) {
            throw e;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "closeEnt", BSRespCode.BS_CLOSE_OPEN_SUBMIT_CLOSE_ERROR
                    .getCode()
                    + ":申请关闭企业系统失败[param=" + closeOpenEnt + "]", e);
            throw new HsException(BSRespCode.BS_CLOSE_OPEN_SUBMIT_CLOSE_ERROR, "申请关闭企业系统失败[param=" + closeOpenEnt + "]"
                    + e);
        }
    }

    /**
     * 申请开启系统
     *
     * @param closeOpenEnt 开启系统信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void openEnt(CloseOpenEnt closeOpenEnt) throws HsException {
        BizLog.biz(this.getClass().getName(), "openEnt", "input param:", null == closeOpenEnt ? "NULL" : closeOpenEnt
                .toString());
        //校验参数
        HsAssert.notNull(closeOpenEnt, RespCode.PARAM_ERROR, "申请开启企业[closeOpenEnt]为null");
        HsAssert.hasText(closeOpenEnt.getEntCustId(), RespCode.PARAM_ERROR, "申请开启企业[entCustId]为空");
        HsAssert.hasText(closeOpenEnt.getEntResNo(), RespCode.PARAM_ERROR, "申请开启企业[entResNo]为空");

//        // 判断是否存在正在审批的开关系统申请
//        if (closeOpenEntMapper.existApplying(closeOpenEnt.getEntCustId())) {
//            throw new HsException(BSRespCode.BS_CLOSE_OPEN_EXIST_APPLYING, "存在正在审批的开关系统申请");
//        }
        try {
            BsEntMainInfo mainInfo = bsEntService.searchEntMainInfo(closeOpenEnt.getEntCustId());
            if(mainInfo!=null){
                closeOpenEnt.setEntCustName(mainInfo.getEntName());
                closeOpenEnt.setAddress(mainInfo.getEntRegAddr());
                closeOpenEnt.setLinkman(mainInfo.getContactPerson());
                closeOpenEnt.setMobile(mainInfo.getContactPhone());
                closeOpenEnt.setCustType(mainInfo.getEntCustType());
            }
            
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            closeOpenEnt.setApplyId(applyId);
            closeOpenEnt.setApplyType(CloseOpenEntType.OPEN_SYS.getCode());

            int row = closeOpenEntMapper.createCloseOpenEnt(closeOpenEnt);
            if(row ==1){
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE100.getCode(), commonService.getAreaPlatCustId());
                task.setHsResNo(closeOpenEnt.getEntResNo());
                task.setCustName(closeOpenEnt.getEntCustName());
                taskService.saveTask(task);
            }else{
                // 判断是否存在正在审批的开关系统申请,改成在数据库进行判断
                throw new HsException(BSRespCode.BS_CLOSE_OPEN_EXIST_APPLYING, "存在正在审批的开关系统申请");
            }
        }catch (HsException e) {
            throw e;
        }catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "closeEnt", BSRespCode.BS_CLOSE_OPEN_SUBMIT_OPEN_ERROR.getCode()
                    + ":申请开启企业系统失败[param=" + closeOpenEnt + "]", e);
            throw new HsException(BSRespCode.BS_CLOSE_OPEN_SUBMIT_OPEN_ERROR, "申请开启企业系统失败[param=" + closeOpenEnt + "]"
                    + e);
        }

    }

    /**
     * 查询关开系统复核
     *
     * @param closeOpenEntParam 查询条件
     * @param page              分页信息
     * @return 关开系统申请列表
     * @throws HsException
     */
    @Override
    public PageData<CloseOpenEnt> queryCloseOpenEnt4Appr(CloseOpenEntQueryParam closeOpenEntParam, Page page)
            throws HsException {
        if (null == closeOpenEntParam || isBlank(closeOpenEntParam.getOptCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageContext.setPage(page);
        PageData<CloseOpenEnt> pageData = null;
        List<CloseOpenEnt> list = closeOpenEntMapper.queryCloseOpenEnt4ApprListPage(closeOpenEntParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 查询开关系统审核查询
     *
     * @param closeOpenEntParam 查询条件
     * @param page              分页信息
     * @return 关开系统列表
     * @throws HsException
     */
    @Override
    public PageData<CloseOpenEnt> queryCloseOpenEnt(CloseOpenEntQueryParam closeOpenEntParam, Page page)
            throws HsException {
        if (null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageContext.setPage(page);
        PageData<CloseOpenEnt> pageData = null;
        List<CloseOpenEnt> list = closeOpenEntMapper.queryCloseOpenEntListPage(closeOpenEntParam);

        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 查询关闭、开启系统详情
     *
     * @param applyId 申请编号
     * @return 关闭、开启系统详情
     */
    @Override
    public CloseOpenEntDetail queryCloseOpenEntDetail(String applyId) {
        if (isBlank(applyId)) {
            return null;
        }

        CloseOpenEntDetail closeOpenEntDetail = closeOpenEntMapper.queryCloseOpenEntDetail(applyId);
        if (null == closeOpenEntDetail) {
            return null;
        }

        CloseOpenEntInfo closeOpenEntInfo = null;
        if (CloseOpenEntType.OPEN_SYS.getCode() == closeOpenEntDetail.getApplyType()) {// 如果是开启系统，则查出该企业上一次申请关闭系统的信息
            closeOpenEntInfo = closeOpenEntMapper.queryLastCloseOpenEntInfo(closeOpenEntDetail.getEntCustId(),
                    CloseOpenEntType.CLOSE_SYS.getCode(), closeOpenEntDetail.getApplyDate());
        } else if (CloseOpenEntType.CLOSE_SYS.getCode() == closeOpenEntDetail.getApplyType()) {// 如果是关闭系统，则查出该企业上一次申请开启系统的信息
            closeOpenEntInfo = closeOpenEntMapper.queryLastCloseOpenEntInfo(closeOpenEntDetail.getEntCustId(),
                    CloseOpenEntType.OPEN_SYS.getCode(), closeOpenEntDetail.getApplyDate());
        }
        if (null != closeOpenEntInfo) {
            closeOpenEntDetail.setLastApplyOptCustId(closeOpenEntInfo.getApplyOptCustId());
            closeOpenEntDetail.setLastApplyOptCustName(closeOpenEntInfo.getApplyOptCustName());
            closeOpenEntDetail.setLastApplyDate(closeOpenEntInfo.getApplyDate());
            closeOpenEntDetail.setLastApplyRemark(closeOpenEntInfo.getApplyRemark());
        }
        return closeOpenEntDetail;
    }

    /**
     * 审批申请关闭、开启系统
     *
     * @param apprParam 审批参数
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprCloseOpenEnt(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprCloseOpenEnt", "input param:", apprParam + "");
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId())
                || isBlank(apprParam.getOptName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");

        try {
            boolean pass = apprParam.getIsPass();

            CloseOpenEntStatus status = pass ? CloseOpenEntStatus.PASS : CloseOpenEntStatus.REJECT;

            closeOpenEntMapper.apprCloseOpenEnt(apprParam.getApplyId(), status.getCode(), apprParam.getApprRemark(), apprParam.getOptCustId(), apprParam.getOptName());

            CloseOpenEnt closeOpenEnt = closeOpenEntMapper.queryCloseEntById(apprParam.getApplyId());

            // 调用用户中心接口更改状态
            if (pass) {
                BsEntStatusInfo entStatusInfo = new BsEntStatusInfo();
                entStatusInfo.setEntCustId(closeOpenEnt.getEntCustId());
                Integer isClosedEnt = 0;// 企业是否关闭（冻结）1:是 0：否
                if (CloseOpenEntType.CLOSE_SYS.getCode() == closeOpenEnt.getApplyType()) {
                    isClosedEnt = 1;
                }
                entStatusInfo.setIsClosedEnt(isClosedEnt);
                bsEntService.updateEntStatusInfo(entStatusInfo, apprParam.getOptCustId());
                
                //成员企业或托管企业关闭系统审批通过后通知电商
                if(isClosedEnt == 1 &&(CustType.TRUSTEESHIP_ENT.getCode() == closeOpenEnt.getCustType() 
                        || CustType.MEMBER_ENT.getCode() == closeOpenEnt.getCustType() )){ 
                    try{
                        ReturnResult  result = enterpriceService.closeSystem(closeOpenEnt.getEntResNo());
                        if(200 == result.getRetCode()){
                            SystemLog.debug(enterpriceService.getClass().getName(), "closeSystem", "关闭系统后通知互商成功。"+closeOpenEnt);
                        }else{
                            SystemLog.error(enterpriceService.getClass().getName(), "closeSystem", "关闭系统后通知互商失败，[param="+closeOpenEnt+"],互商返回错误消息："+result.getRetMsg(),null);
                        }
                    }catch(Exception e){
                        SystemLog.error(enterpriceService.getClass().getName(), "closeSystem",
                                "关闭系统后通知互商调用接口失败[" + closeOpenEnt.getEntResNo() + "]", e);
                    }
                }
            }

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "apprCloseOpenEnt", BSRespCode.BS_CLOSE_OPEN_APPR_ERROR
                    .getCode()
                    + ":审批开关系统失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_CLOSE_OPEN_APPR_ERROR, "审批开关系统失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 查询企业上一次关闭或开启系统信息
     *
     * @param entCustId 企业客户号
     * @param applyType 申请类别
     * @return 返回企业上一次关闭系统信息
     */
    @Override
    public CloseOpenEntInfo queryLastCloseOpenEntInfo(String entCustId, Integer applyType) {
        return closeOpenEntMapper
                .queryLastCloseOpenEntInfo(entCustId, applyType, DateUtil.DateTimeToString(new Date()));
    }
}
