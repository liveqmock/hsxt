/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.common;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsi.nt.api.beans.SelfDynamicBizMsgBean;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.common.enumtype.message.MessageLevel;
import com.gy.hsxt.common.exception.HsException;

/**
 * 消息与公告辅助工具类
 * 
 * @Package : com.gy.hsxt.bs.message.utils
 * @ClassName : MessageUtils
 * @Description : 消息与公告辅助工具类
 * @Author : chenm
 * @Date : 2016/1/4 20:11
 * @Version V3.0.0.0
 */
public abstract class MessageUtils {

    public static SelfDynamicBizMsgBean bulid(Message message) throws HsException {
        SelfDynamicBizMsgBean bean = new SelfDynamicBizMsgBean();

        bean.setMsgId(message.getMsgId());// guid
        bean.setHsResNo("");
        bean.setCustName("");

        // 接收者互生号转换成数组
        String[] receiptors = StringUtils.split(StringUtils.trimToEmpty(message.getReceiptor()), ",");// 创建数组对象
        //
        // // 私信 遍历多个接收者并且组装成互与号加发送者
        // if (MessageType.PRIVATE_LETTER.getCode() == message.getType())
        // {
        // for (int i = 0; i < receiptors.length; i++)
        // {
        // receiptors[i] = receiptors[i] + "_" + message.getSender();
        // }
        // }
        bean.setMsgReceiver(receiptors);// 消息接收者
        bean.setMsgTitle(message.getTitle());// 标题
        bean.setMsgContent(message.getMsg());// 消息内容
        bean.setSender(message.getEntResNo() + "_" + message.getSender());// 消息发送者

        // 设置权重
        Priority priority = (message.getLevel() == MessageLevel.IMPORTANT.ordinal()) ? Priority.HIGH : Priority.MIDDLE;
        bean.setPriority(priority.getPriority());// 消息组别
        return bean;
    }
}
