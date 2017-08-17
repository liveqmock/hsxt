/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.common.enumtype.message.MessageLevel;
import com.gy.hsxt.bs.common.enumtype.message.MessageType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.TMErrorCode;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.common.MessageUtils;
import com.gy.hsxt.tm.common.StringUtil;
import com.gy.hsxt.tm.disconf.TmConfig;
import com.gy.hsxt.tm.interfaces.IAutoUrgencyTaskService;
import com.gy.hsxt.tm.interfaces.ICommonService;
import com.gy.hsxt.tm.mapper.TaskMapper;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 自动催办工单服务实现类
 * 
 * @Package: com.gy.hsxt.tm.service
 * @ClassName: AutoUrgencyTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-2-6 下午3:18:20
 * @version V3.0.0
 */
@Service
public class AutoUrgencyTaskService implements IAutoUrgencyTaskService {
    /** 业务服务私有配置参数 **/
    @Autowired
    private TmConfig tmConfig;

    @Autowired
    TaskMapper taskMapper;

    // 公共service
    @Resource
    ICommonService commonService;

    /**
     * 注入通知系统接口
     */
    @Resource
    private INtService ntService;

    /**
     * 用户中心：操作员服务
     */
    @Autowired
    IUCAsOperatorService ucOperatorService;

    /**
     * 调度监听器
     */
    @Autowired
    private IDSBatchServiceListener batchServiceListener;

    @Override
    public boolean excuteBatch(String executeId, Map<String, String> arg0) {
        // SystemLog.debug(this.getClass().getName(),
        // Thread.currentThread().getStackTrace()[1].getMethodName(),
        // "工单系统：定时发送催办通知,params[executeId:" + executeId + ",arg0:" + arg0 +
        // "]");
        StringBuilder receiveSb = null;
        try
        {
            // 设置执行状态
            batchServiceListener.reportStatus(executeId, 2, "工单系统：开始扫描催办中的工单");
            // 系统消息
            Message message = null;
            // 查询出催办工单执行人互生号
            List<TmTask> urgencyTaksList = taskMapper.findUrgencyTask(tmConfig.getTimeOut());
            if (urgencyTaksList != null && urgencyTaksList.size() > 0)
            {
                receiveSb = new StringBuilder();
                message = new Message();
                message.setMsgId(StringUtil.getTmGuid(tmConfig.getAppNode()));
                message.setEntCustId(commonService.getPlatCustId());
                message.setEntResNo(commonService.getPlatResNo());
                message.setEntCustName(commonService.getLocalInfo().getPlatNameCn());
                message.setSummary("催办工单提示");// 摘要
                message.setLevel(MessageLevel.IMPORTANT.getCode());
                message.setType(MessageType.PRIVATE_LETTER.getCode());
                message.setCreatedBy("system");
                message.setTitle("催办工单");
                message.setMsg("您有紧急的工单需要处理");
                message.setSender("0000");//

                int cnt = 1;
                String optHsResNo = "";
                for (TmTask task : urgencyTaksList)
                {
                    optHsResNo = getOperatorHsResNo(task.getExeCustId());
                    if (cnt < urgencyTaksList.size())
                    {
                        if (StringUtils.isNotBlank(optHsResNo))
                        {
                            receiveSb.append(optHsResNo);
                            receiveSb.append(",");
                        }
                    }
                    else
                    {
                        receiveSb.append(optHsResNo);
                    }
                    cnt++;
                }
                // List去重
                List<String> receiveList = new ArrayList<String>(new HashSet<String>(Arrays.asList(receiveSb.toString()
                        .split(","))));
                message.setReceiptor(receiveList.toString());
            }
            // 执行发送
            ntService.sendSelfDynamicBiz(MessageUtils.bulid(message));
            // 返回调度状态
            batchServiceListener.reportStatus(executeId, 0, "工单系统：执行定时发送催办通知完成");
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "自动催办紧急工单异常，ErrorCode:" + e.getErrorCode() + ":" + e.getMessage(), e);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_AUTO_URGENCY_TASK_ERROR.getCode() + ":工单系统：自动催办紧急工单异常", e);
            batchServiceListener.reportStatus(executeId, 1, "工单系统：自动催办紧急工单异常" + e);
            throw new HsException(TMErrorCode.TM_AUTO_URGENCY_TASK_ERROR.getCode(), "工单系统：自动催办紧急工单异常" + e);
        }
        return true;
    }

    /**
     * 查询操作员互生员
     * 
     * @param optCustId
     *            操作员客户号
     * @return 操作员互生号
     */
    private String getOperatorHsResNo(String optCustId) {
        String hsResNo = "";
        if (StringUtils.isNotBlank(optCustId))
        {
            try
            {
                // 查询操作员信息
                AsOperator opertor = ucOperatorService.searchOperByCustId(optCustId);
                if (opertor != null && StringUtils.isNotBlank(opertor.getEntResNo())
                        && StringUtils.isNotBlank(opertor.getUserName()))
                {
                    return hsResNo = opertor.getEntResNo() + "_" + opertor.getUserName();
                }
            }
            catch (Exception e)
            {
                return hsResNo;
            }
        }
        return hsResNo;
    }
}
