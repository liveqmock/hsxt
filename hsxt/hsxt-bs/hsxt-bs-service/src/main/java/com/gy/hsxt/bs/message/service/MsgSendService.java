/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.message.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.bean.message.MessageQuery;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.message.MessageLevel;
import com.gy.hsxt.bs.common.enumtype.message.MessageStatus;
import com.gy.hsxt.bs.common.enumtype.message.MessageType;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.message.interfaces.IMessageService;
import com.gy.hsxt.bs.message.interfaces.IMsgSendService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息与公告的业务实现类
 *
 * @Package : com.gy.hsxt.bs.message.service
 * @ClassName : MsgSendService
 * @Description : 消息与公告的业务实现类
 * @Author : liuhq
 * @Date : 2015-10-22 上午9:12:46
 * @Version V1.0
 */
@Service
public class MsgSendService implements IMsgSendService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入通知系统接口
     */
    @Resource
    private INtService ntService;

    /**
     * 消息与公告业务接口
     */
    @Resource
    private IMessageService messageService;

    /**
     * 持卡人接口
     */
    @Resource
    private IUCBsCardHolderService bsCardHolderService;

    /**
     * 创建并发送消息
     *
     * @param message 消息
     * @param isSend  是否发送
     * @throws HsException
     */
    @Override
    public void createAndSendMessage(Message message, boolean isSend) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createAndSendMessage", "创建并发送消息参数[message]:" + message);
        // 保存消息
        messageService.saveBean(message);
        // 发送消息
        if (isSend) {
            sendDynamicMessage(message);
        }
    }

    /**
     * 分页查询某公司或平台的所有记录
     *
     * @param page         分页对象
     * @param messageQuery 查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<Message> queryMessageListPage(Page page, MessageQuery messageQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryMessageListPage", "分页查询某公司或平台的所有记录参数[query]:"
                + messageQuery);
        // 获取当前页数据列表
        List<Message> list = messageService.queryListForPage(page, messageQuery);
        // 返回当前页列表
        return PageData.bulid(page.getCount(), list);
    }

    /**
     * 根据ID查询消息
     *
     * @param msgId 消息ID
     * @return bean
     * @throws HsException
     */
    @Override
    public Message queryMessageById(String msgId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryMessageById", "根据ID查询消息参数[msgId]:" + msgId);
        // 根据ID查询消息
        return messageService.queryBeanById(msgId);
    }

    /**
     * 修改并发送消息
     *
     * @param message 消息
     * @param isSend  是否发送
     * @throws HsException
     */
    @Override
    public void modifyAndSendMessage(Message message, boolean isSend) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyAndSendMessage", "修改并发送消息参数[message]:" + message);
        // 修改消息
        messageService.modifyBean(message);
        // 发送消息
        if (isSend) {
            message = queryMessageById(message.getMsgId());
            sendDynamicMessage(message);
        }
    }

    /**
     * 根据ID删除消息
     *
     * @param msgId 消息ID
     * @throws HsException
     */
    @Override
    public void removeMessageById(String msgId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:removeMessageById", "根据ID删除消息参数[msgId]:" + msgId);
        // 删除消息
        messageService.removeBeanById(msgId);
    }

    /**
     * 批量删除消息
     *
     * @param msgIds 消息ID列表
     * @throws HsException
     */
    @Override
    public void batchRemoveMessages(List<String> msgIds) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:removeMessageById", "根据ID删除消息参数[msgIds]:" + msgIds);

        HsAssert.notNull(msgIds, RespCode.PARAM_ERROR, "消息ID列表[msgIds]为null");

        for (String msgId : msgIds) {
            // 删除消息
            messageService.removeBeanById(msgId);
        }
    }

    /**
     * 发送消息选择通知或私信时调用通知系统
     *
     * @param message 实体类 非空
     */
    private void sendDynamicMessage(Message message) {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:sendDynamicMessage", "消息实体[message]:" + message);
        try {
            HsAssert.hasText(message.getSender(), RespCode.PARAM_ERROR, "发送者[sender]为空");
            HsAssert.hasText(message.getFileId(),BSRespCode.BS_PARAMS_EMPTY,"Html文件地址[fileId]为空");
            // 执行发送
            DynamicSysMsgBean dynamicMsg = this.bulid(message);
            SystemLog.debug(this.getClass().getName(),"sendDynamicMessage","通过消息中心发送消息："+dynamicMsg);
            ntService.sendDynamicSys(dynamicMsg);
            // 新建的情况下没有更新操作人
            if (StringUtils.isBlank(message.getUpdatedBy())) {
                message.setUpdatedBy(message.getCreatedBy());
            }
            message.setStatus(MessageStatus.YES.getCode());
            messageService.modifyBean(message);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:sendDynamicMessage", "", "调用通知系统发送消息成功");
        } catch (NoticeException e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:sendDynamicMessage", "消息发送:调用通知系统发送消息失败，参数[message]:"
                    + message, e);
            throw new HsException(BSRespCode.BS_MESSAGE_SEND_ERROR, "消息发送:调用通知系统发送消息失败，原因:" + e.getMessage());
        }
    }

    /**
     * 构建消息中心的互动信息
     *
     * @param message 信息
     * @return bean
     * @throws HsException
     */
    private DynamicSysMsgBean bulid(Message message) throws HsException {
        DynamicSysMsgBean bean = new DynamicSysMsgBean();

        bean.setMsgId(message.getMsgId());// guid
        bean.setMsgType(message.getType());// 消息类型
        bean.setMsgSummary(message.getSummary());
        bean.setMsgSummaryPicUrl(StringUtils.splitByWholeSeparatorPreserveAllTokens(message.getImages(), ","));

        // 接收者互生号转换成数组
        String[] receiptors = StringUtils.split(StringUtils.trimToEmpty(message.getReceiptor()), ",");// 创建数组对象

        // 私信 遍历多个接收者并且组装成互与号加发送者
        if (MessageType.PRIVATE_LETTER.getCode() == message.getType()) {
            for (int i = 0; i < receiptors.length; i++) {
                if (HsResNoUtils.isPersonResNo(receiptors[i])) {
                    receiptors[i] = bsCardHolderService.findCustIdByResNo(receiptors[i]);
                }
            }
        }
        bean.setMsgReceiver(receiptors);// 消息接收者
        bean.setSender(message.getSender());// 消息发送者
        bean.setMsgTitle(message.getTitle());// 标题

        //设置权重
        Priority priority = (message.getLevel() == MessageLevel.IMPORTANT.ordinal()) ? Priority.HIGH : Priority.MIDDLE;
        bean.setPriority(priority.getPriority());// 消息组别
        bean.setMsgContent(message.getFileId());// 消息内容
        return bean;
    }
}
