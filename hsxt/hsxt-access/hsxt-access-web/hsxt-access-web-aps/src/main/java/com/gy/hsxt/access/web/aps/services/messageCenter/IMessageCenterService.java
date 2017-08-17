/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.messageCenter;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.common.exception.HsException;

/**
 * 消息中心服务接口
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.messageCenter  
 * @ClassName: IMessageCenterService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-9 下午5:44:16 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface IMessageCenterService extends IBaseService {
    
    /**
     * 增加消息
     * @param msg
     * @throws HsException
     */
    public void createMsgSend(Message msg,boolean isSend,String token) throws HsException;
    
    /**
     * 修改消息
     * @param msg
     * @throws HsException
     */
    public void editMsg(Message msg,boolean isSend,String token) throws HsException;
    
    /**
     * 删除消息
     * @param msgId
     * @throws HsException
     */
    public void deleteMsgSend(String msgId) throws HsException;
    
    /**
     * 删除多条消息
     * @param msgId
     * @throws HsException
     */
    public void deleteMessages(List<String> msgIds) throws HsException;
    
    /**
     * 查询消息
     * @param msgId
     * @return
     * @throws HsException
     */
    public Message query(String msgId) throws HsException;

}
