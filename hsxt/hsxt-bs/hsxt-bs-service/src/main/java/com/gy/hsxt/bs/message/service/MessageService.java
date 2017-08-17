/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.message.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.bean.message.MessageQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.message.MessageLevel;
import com.gy.hsxt.bs.common.enumtype.message.MessageStatus;
import com.gy.hsxt.bs.common.enumtype.message.MessageType;
import com.gy.hsxt.bs.common.enumtype.message.Receiptor;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.message.interfaces.IMessageService;
import com.gy.hsxt.bs.message.mapper.MessageMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息与公告业务实现
 *
 * @Package : com.gy.hsxt.bs.message.service
 * @ClassName : MessageService
 * @Description : 消息与公告业务实现
 * @Author : chenm
 * @Date : 2016/1/4 18:51
 * @Version V3.0.0.0
 */
@Service
public class MessageService implements IMessageService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 消息与公告Mapper接口类
     */
    @Resource
    private MessageMapper messageMapper;

    /**
     * 保存实体
     *
     * @param message 实体
     * @return string
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(Message message) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存消息实体[Message]:" + message);

        // 实体参数为null时抛出异常
        HsAssert.notNull(message, RespCode.PARAM_ERROR, "消息实体[Message]为null");
        // 消息类型 11公告 12通知 20 私信
        HsAssert.isTrue(MessageType.checkType(message.getType()), RespCode.PARAM_ERROR, "消息类型[type]参数错误");
        // 消息级别 1为一般，2为重要
        HsAssert.isTrue(MessageLevel.checkLevel(message.getLevel()), RespCode.PARAM_ERROR, "消息级别[level]参数错误");
        HsAssert.hasText(message.getCreatedBy(), RespCode.PARAM_ERROR, "创建者[createdBy]为空");
        HsAssert.hasText(message.getTitle(), RespCode.PARAM_ERROR, "消息标题[title]为空");
        HsAssert.hasText(message.getEntCustId(), RespCode.PARAM_ERROR, "发送企业客户号[entCustId]为空");
        HsAssert.hasText(message.getEntResNo(), RespCode.PARAM_ERROR, "发送企业互生号[entResNo]为空");
        HsAssert.hasText(message.getEntCustName(), RespCode.PARAM_ERROR, "发送企业名称[entCustName]为空");
        HsAssert.hasText(message.getReceiptor(), RespCode.PARAM_ERROR, "接收者[receiptor]为空");
        //校验接收者
        checkReceiptors(message);

        try {
            message.setMsgId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
            // 设置为未发送
            message.setStatus(MessageStatus.NO.getCode());

            messageMapper.insertBean(message);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + message, "保存消息实体成功");

            return message.getMsgId();
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存消息实体失败,参数[message]:" + message, e);

            throw new HsException(BSRespCode.BS_MESSAGE_DB_ERROR, "保存消息实体失败,原因:" + e.getMessage());
        }
    }

    /**
     * 更新实体
     *
     * @param message 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(Message message) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "修改更新消息实体[Message]:" + message);
        // 实体参数为null时抛出异常
        HsAssert.notNull(message, RespCode.PARAM_ERROR, "消息实体为null");
        HsAssert.hasText(message.getMsgId(), RespCode.PARAM_ERROR, "消息ID[msgId]为空");
        HsAssert.hasText(message.getUpdatedBy(), RespCode.PARAM_ERROR, "更新人[updatedBy]为空");
        // 消息类型 0公告 1通知 2 私信
        if (message.getType() != null) {
            HsAssert.isTrue(MessageType.checkType(message.getType()), RespCode.PARAM_ERROR, "消息类型[type]参数错误");
        }
        // 消息级别 1为一般，2为重要
        if (message.getLevel() != null) {
            HsAssert.isTrue(MessageLevel.checkLevel(message.getLevel()), RespCode.PARAM_ERROR, "消息级别[level]参数错误");
        }
        HsAssert.hasText(message.getReceiptor(), RespCode.PARAM_ERROR, "接收者[receiptor]为空");
        //校验接收者
        checkReceiptors(message);

        try {
            int count = messageMapper.updateBean(message);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + message, "修改更新消息实体成功");

            return count;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "修改更新消息实体失败,参数[message]:" + message, e);

            throw new HsException(BSRespCode.BS_MESSAGE_DB_ERROR, "修改更新消息实体失败，原因:" + e.getMessage());
        }
    }

    /**
     * 校验接收者
     *
     * @param message 消息
     * @throws HsException
     */
    private void checkReceiptors(Message message) throws HsException {
        // 接收者互生号转换成数组
        String[] receiptors = StringUtils.split(StringUtils.trimToEmpty(message.getReceiptor()), ",");
        HsAssert.isTrue(receiptors.length > 0, RespCode.PARAM_ERROR, "接收者数量为0");
        for (String receiptor : receiptors) {
            boolean check = false;
            if (!HsResNoUtils.isHsResNo(receiptor)) {//不是互生号
                HsAssert.isTrue(Receiptor.checkName(receiptor), RespCode.PARAM_ERROR, "接收者[receiptor]类型错误");
                check = Receiptor.checkBelong(receiptor, message.getEntResNo());
            } else {
                if (HsResNoUtils.isManageResNo(message.getEntResNo())) {
                    check = HsResNoUtils.isServiceResNo(receiptor) && StringUtils.left(receiptor, 2).equals(StringUtils.left(message.getEntResNo(), 2));
                } else if (HsResNoUtils.isServiceResNo(message.getEntResNo())) {
                    check = (HsResNoUtils.isTrustResNo(receiptor) || HsResNoUtils.isMemberResNo(receiptor)) && StringUtils.left(receiptor, 5).equals(StringUtils.left(message.getEntResNo(), 5));
                } else if (HsResNoUtils.isAreaPlatResNo(message.getEntResNo())) {
                    check = HsResNoUtils.isTrustResNo(receiptor) || HsResNoUtils.isMemberResNo(receiptor)
                            || HsResNoUtils.isServiceResNo(receiptor) || HsResNoUtils.isManageResNo(receiptor)
                            || HsResNoUtils.isPersonResNo(receiptor);
                }
            }
            HsAssert.isTrue(check, BSRespCode.BS_PARAMS_TYPE_ERROR, "接收者[" + receiptor + "]不属于[" + message.getEntResNo() + "]消息发送对象");

        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public Message queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "根据ID查询消息参数[msgId]:" + id);
        try {
            return messageMapper.selectBeanById(id);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "根据ID查询消息失败,参数[msgId]:" + id, e);
            throw new HsException(BSRespCode.BS_MESSAGE_DB_ERROR, "根据ID查询消息失败，原因:" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<Message> queryListByQuery(Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<Message> queryListForPage(Page page, Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询消息与公告参数[query]:" + query);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");

        HsAssert.notNull(query, RespCode.PARAM_ERROR, "查询实体[messageQuery]为null");
        // 查询实体
        MessageQuery messageQuery = ((MessageQuery) query);
        messageQuery.checkDateFormat();
        // 校验客户号
        HsAssert.hasText(messageQuery.getEntCustId(), RespCode.PARAM_ERROR, "客户号[entCustId]为空");

        if (messageQuery.getStatus() != null) {
            // 是否发送 0未发送，1已发送
            HsAssert.isTrue(MessageStatus.check(messageQuery.getStatus()), RespCode.PARAM_ERROR, "发送状态[status]参数错误");
        }

        if (messageQuery.getLevel() != null) {
            HsAssert.isTrue(MessageLevel.checkLevel(messageQuery.getLevel()), RespCode.PARAM_ERROR, "重要级别[level]参数错误");
        }

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return messageMapper.selectBeanListPage(messageQuery);

        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询消息与公告失败,参数[query]:" + query, e);

            throw new HsException(BSRespCode.BS_MESSAGE_DB_ERROR, "分页查询消息与公告失败,原因:" + e.getMessage());
        }
    }

    /**
     * 根据ID删除消息
     *
     * @param msgId 消息ID
     * @throws HsException
     */
    @Override
    @Transactional
    public void removeBeanById(String msgId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:removeBeanById", "根据ID删除消息参数[msgId]:" + msgId);
        try {
            messageMapper.deleteBeanById(msgId);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:removeBeanById", "根据ID删除消息失败,参数[msg]:" + msgId, e);
            throw new HsException(BSRespCode.BS_MESSAGE_DB_ERROR, "根据ID删除消息失败,原因:" + e.getMessage());
        }
    }
}
