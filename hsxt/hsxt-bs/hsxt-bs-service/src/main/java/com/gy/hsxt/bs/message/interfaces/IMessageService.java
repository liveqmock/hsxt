/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.message.interfaces;

import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 消息与公告业务接口
 *
 * @Package : com.gy.hsxt.bs.message.interfaces
 * @ClassName : IMessageService
 * @Description : 消息与公告业务接口
 * @Author : chenm
 * @Date : 2016/1/4 18:42
 * @Version V3.0.0.0
 */
public interface IMessageService extends IBaseService<Message> {

    /**
     * 根据ID删除消息
     *
     * @param msgId 消息ID
     * @throws HsException
     */
    void removeBeanById(String msgId) throws HsException;
}
