/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.message;

import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.bean.message.MessageQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 消息发送接口类
 *
 * @Package : com.gy.hsxt.bs.api.message
 * @ClassName : IMsgSendService
 * @Description : 消息发送接口类
 * @Author : liuhq
 * @Date : 2015-9-16 上午11:16:42
 * @Version V3.0
 */
public interface IBSMsgSendService {

    /**
     * 创建并发送消息
     *
     * @param message 消息
     * @param isSend 是否发送
     * @throws HsException
     */
    void createAndSendMessage(Message message,boolean isSend) throws HsException;


    /**
     * 分页查询某发送企业的所有消息记录
     *
     * @param page         分页对象
     * @param messageQuery 查询实体
     * @return list
     * @throws HsException
     */
    PageData<Message> queryMessageListPage(Page page, MessageQuery messageQuery) throws HsException;

    /**
     * 根据ID查询消息
     *
     * @param msgId 消息ID
     * @return bean
     * @throws HsException
     */
    Message queryMessageById(String msgId) throws HsException;

    /**
     * 修改并发送消息
     *
     * @param message 消息
     * @param isSend 是否发送
     * @throws HsException
     */
    void modifyAndSendMessage(Message message,boolean isSend) throws HsException;

    /**
     * 根据ID删除消息
     * @param msgId 消息ID
     * @throws HsException
     */
    void removeMessageById(String msgId) throws HsException;

    /**
     * 批量删除消息
     * @param msgIds 消息ID列表
     * @throws HsException
     */
    void batchRemoveMessages(List<String> msgIds) throws HsException;

}
