/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.message.mapper;

import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.bean.message.MessageQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息与公告业Mapper接口
 *
 * @Package : com.gy.hsxt.bs.message.mapper
 * @ClassName : MessageMapper
 * @Description : 消息与公告业Mapper接口
 * @Author : liuhq
 * @Date : 2015-10-22 上午9:15:39
 * @Version V3.0.0.0
 */
@Repository
public interface MessageMapper {
    /**
     * 创建 消息发送记录
     *
     * @param message 实体类 必须 非null
     */
    int insertBean(Message message);

    /**
     * 分页查询 某一个发送者的所有记录
     *
     * @param messageQuery 条件查询实体
     * @return 返回分好页的数据列表
     */
    List<Message> selectBeanListPage(MessageQuery messageQuery);

    /**
     * 根据ID查询消息
     *
     * @param msgId 发送记录编号 非空 必须
     * @return 实体
     */
    Message selectBeanById(String msgId);

    /**
     * 删除 当前某一条记录（非物理删除）
     *
     * @param msgId 发送记录编号 非空 必须
     * @return int
     */
    int deleteBeanById(String msgId);

    /**
     * 更新 消息发送记录
     *
     * @param message 实体类 必须 非null
     */
    int updateBean(Message message);


}
