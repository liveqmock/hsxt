package com.gy.hsi.nt.server.aop;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.DateUtil;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.entity.result.BaseMsg;
import com.gy.hsi.nt.server.entity.result.ResendMsg;
import com.gy.hsi.nt.server.entity.result.SendedMsg;
import com.gy.hsi.nt.server.service.IMsgResultService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.StringUtil;
import com.gy.hsi.nt.server.util.enumtype.IsActive;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * 消息结果统一处理aop
 *
 * @version V3.0.0
 * @Package: com.gy.hsi.nt.server.aop
 * @ClassName: NoticeResultHandle
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午2:08:29
 * @company: gyist
 */
@Aspect
public class NoticeResultHandle {

    /**
     * 消息结果Service
     */
    @Autowired
    private IMsgResultService msgResultService;

    /**
     * aop线程池
     */
    @Resource(name = "aopTaskExecutor")
    private ThreadPoolTaskExecutor aopTaskExecutor;

    private static final Logger logger = Logger.getLogger(NoticeResultHandle.class);

    /**
     * 定义扫描的包和方法名称
     *
     * @return : void
     * @Description:
     * @author: likui
     * @created: 2016年3月26日 下午3:51:08
     * @version V3.0.0
     */
    @Pointcut("execution(* com.gy.hsi.nt.server.service.impl.*.ntSend*(..))")
    public void pointcut() {
    }

    /**
     * 处理返回结果的日志
     *
     * @param joinPoint
     * @param retVal
     * @return : void
     * @Description:
     * @author: likui
     * @created: 2016年3月26日 下午3:51:01
     * @version V3.0.0
     */
    @AfterReturning(pointcut = "pointcut()", returning = "retVal")
    public void ntAfterReturnAdvice(JoinPoint joinPoint, String retVal) {
        logger.info("执行方法:" + joinPoint.getSignature().getName() + "," + "返回结果：" + retVal);
        try {
            Object[] args = joinPoint.getArgs();
            if (null != args && args.length > 0) {
                createResultData(args[0], retVal);
            }
        } catch (Exception ex) {
            logger.error("--------------------aop处理异常:" + ex.getMessage(), ex);
        }
    }

    /**
     * 处理异常的日志
     *
     * @param joinPoint
     * @param e
     * @return : void
     * @Description:
     * @author: likui
     * @created: 2016年3月26日 下午3:50:53
     * @version V3.0.0
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void ntThrowingAdvice(JoinPoint joinPoint, Exception e) {
        StringBuilder exceptionStr = new StringBuilder();
        exceptionStr.append("异常通知：执行").append(joinPoint.getTarget().getClass().getName()).append("类的")
                .append(joinPoint.getSignature().getName()).append("方法时发生异常");
        logger.info(exceptionStr.toString());
        try {
            Object[] args = joinPoint.getArgs();
            if (null != args && args.length > 0) {
                createResultData(args[0], RespCode.ERROR.name());
            }
        } catch (Exception ex) {
            logger.error("--------------------aop处理异常:" + ex.getMessage(), ex);
        }
    }

    /**
     * 生成消息结果数据
     *
     * @param obj
     * @param result
     * @return : void
     * @Description:
     * @author: likui
     * @created: 2016年3月26日 下午3:50:45
     * @version V3.0.0
     */
    private void createResultData(final Object obj, final String result) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BaseMsg base = null;
                try {
                    // 短信
                    if (obj instanceof NoteBean) {
                        NoteBean bean = (NoteBean) obj;

                        base = createBaseMsg(bean.getMsgId(), bean.getHsResNo(), bean.getCustName(), bean.getTempName(),
                                bean.getCustType(), bean.getBizType(), bean.getBuyResType(), bean.getMsgChannel(), 0,
                                StringUtil.arrayToString(bean.getMsgReceiver(), ","), null, bean.getMsgContent(), null,
                                null, bean.getPriority(), bean.getSender(), null, null, null);
                    }
                    // 邮件
                    if (obj instanceof EmailBean) {
                        EmailBean bean = (EmailBean) obj;

                        base = createBaseMsg(bean.getMsgId(), bean.getHsResNo(), bean.getCustName(), bean.getTempName(),
                                bean.getCustType(), bean.getBizType(), bean.getBuyResType(), bean.getMsgChannel(), 0,
                                StringUtil.arrayToString(bean.getMsgReceiver(), ","), bean.getMailTitle(),
                                bean.getMsgContent(), null, null, bean.getPriority(), bean.getSender(), null, null, null);
                    }
                    // 系统互动消息
                    if (obj instanceof DynamicSysMsgBean) {
                        DynamicSysMsgBean bean = (DynamicSysMsgBean) obj;

                        base = createBaseMsg(bean.getMsgId(), null, null, null, 0, null, 0, bean.getMsgChannel(),
                                bean.getMsgType(), StringUtil.arrayToString(bean.getMsgReceiver(), ","),
                                bean.getMsgTitle(), bean.getMsgContent(), null, null, bean.getPriority(),
                                bean.getSender(), null, null, null);
                    }
                    // 业务互动消息
                    if (obj instanceof DynamicBizMsgBean) {
                        DynamicBizMsgBean bean = (DynamicBizMsgBean) obj;

                        base = createBaseMsg(bean.getMsgId(), bean.getHsResNo(), bean.getCustName(), bean.getTempName(),
                                bean.getCustType(), bean.getBizType(), bean.getBuyResType(), bean.getMsgChannel(), 0,
                                StringUtil.arrayToString(bean.getMsgReceiver(), ","), StringUtils.isNotBlank(bean.getMsgTitle()) ? bean.getMsgTitle() : bean.getTempName(),
                                bean.getMsgContent(), null, null, bean.getPriority(), bean.getSender(), null, bean.getMsgCode(), bean.getSubMsgCode());
                    }
                    // 根据返回的结果进行出来
                    if (result.equals(RespCode.SUCCESS.name())) {
                        SendedMsg sended = JSONObject.parseObject(JSONObject.toJSONString(base), SendedMsg.class);
                        sended.setLastSendDate(DateUtil.now());
                        msgResultService.saveSendedMsg(sended);
                    } else if (result.equals(RespCode.FAIL.name()) || result.equals(RespCode.ERROR.name())) {
                        ResendMsg resend = JSONObject.parseObject(JSONObject.toJSONString(base), ResendMsg.class);
                        resend.setFailedCounts(1);
                        resend.setLastSendDate(DateUtil.now());
                        msgResultService.saveResendMsg(resend);
                        if (resend.getPriority() == Priority.HIGH.getPriority()) {
                            CacheUtil.put(ParamsKey.HIGH_RESEND_ORDER.getKey(), IsActive.Y.name());
                        }
                        if (resend.getPriority() == Priority.MIDDLE.getPriority()) {
                            CacheUtil.put(ParamsKey.MIDDLE_RESEND_ORDER.getKey(), IsActive.Y.name());
                        }
                        if (resend.getPriority() == Priority.LOW.getPriority()) {
                            CacheUtil.put(ParamsKey.LOW_RESEND_ORDER.getKey(), IsActive.Y.name());
                        }
                    }
                } catch (NoticeException ex) {
                    logger.error("生成消息结果数据异常", ex);
                }
            }

            /**
             * 创建结果数据的基础类
             *
             * @Description:
             * @author: likui
             * @created: 2016年3月26日 下午3:26:28
             * @param msgId
             *            消息ID
             * @param hsResNo
             *            互生号
             * @param custName
             *            客户名称
             * @param tempName
             *            模板名称
             * @param custType
             *            客户类型
             * @param bizType
             *            业务类型
             * @param buyResType
             *            启用资源类型
             * @param msgChannel
             *            消息渠道
             * @param msgType
             *            消息类型
             * @param receiver
             *            接受者
             * @param msgTitle
             *            消息标题
             * @param msgContent
             *            消息内容
             * @param msgSummary
             *            消息摘要
             * @param msgSummaryPicUrl
             *            消息图片
             * @param priority
             *            优先级
             * @param sender
             *            发送者
             * @param attachFileIds
             *            文件ID
             * @param msgCode
             *            消息大类代码
             * @param subMsgCode
             *            消息子类代码
             * @return
             * @throws NoticeException
             * @return : BaseMsg
             * @version V3.0.0
             */
            private BaseMsg createBaseMsg(String msgId, String hsResNo, String custName, String tempName,
                                          Integer custType, String bizType, int buyResType, int msgChannel, int msgType, String receiver,
                                          String msgTitle, String msgContent, String msgSummary, String msgSummaryPicUrl, int priority,
                                          String sender, String attachFileIds, String msgCode, String subMsgCode) throws NoticeException {
                BaseMsg base = new BaseMsg(msgId, hsResNo, custName, tempName, custType, bizType, buyResType,
                        msgChannel, msgType, msgCode, subMsgCode, receiver, msgTitle, msgContent, msgSummary, msgSummaryPicUrl, priority,
                        sender, attachFileIds);
                base.setCreatedby(sender);
                base.setCreatedDate(DateUtil.now());
                return base;
            }
        };
        aopTaskExecutor.execute(runnable);
    }
}
