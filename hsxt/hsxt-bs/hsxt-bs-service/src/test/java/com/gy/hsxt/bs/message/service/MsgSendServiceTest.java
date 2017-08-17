package com.gy.hsxt.bs.message.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.bean.message.MessageQuery;
import com.gy.hsxt.bs.common.enumtype.message.MessageLevel;
import com.gy.hsxt.bs.common.enumtype.message.MessageStatus;
import com.gy.hsxt.bs.common.enumtype.message.MessageType;
import com.gy.hsxt.bs.common.enumtype.message.Receiptor;
import com.gy.hsxt.bs.message.interfaces.IMsgSendService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package : com.gy.hsxt.bs.message.service
 * @ClassName : MsgSendServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/5 15:17
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class MsgSendServiceTest {

    @Resource
    private IMsgSendService msgSendService;

    @Test
    public void testCreateAndSendMessage() throws Exception {
        Message message = new Message();
        message.setEntCustId("0800100000020151222");
        message.setEntResNo("08001000000");
        message.setEntCustName("太原市服务公司");
//        message.setSummary("天大的好消息11");
        message.setTitle("好消息");
        message.setMsg("no news is a good news !");
        message.setLevel(MessageLevel.IMPORTANT.getCode());
        message.setType(MessageType.NOTICE.getCode());
        message.setCreatedBy("system");
        message.setReceiptor("06001000000,08000000000");
        message.setSender("0000");
        msgSendService.createAndSendMessage(message,true);
    }

    @Test
    public void testQueryMessageListPage() throws Exception {
        Page page = new Page(0, 10);
        MessageQuery query = new MessageQuery();
        query.setEntCustId("0601000000020151231");
        query.setStatus(MessageStatus.YES.getCode());
        PageData<Message> pageData = msgSendService.queryMessageListPage(page, query);

        System.out.println(pageData);
    }

    @Test
    public void testQueryMessageById() throws Exception {

        Message message = msgSendService.queryMessageById("110120160105165915000000");
        System.out.println(message);
    }

    @Test
    public void testModifyAndSendMessage() throws Exception {
        Message message = new Message();
        message.setMsgId("110120160105165915000000");
        message.setLevel(MessageLevel.GENERAL.getCode());
        message.setUpdatedBy("system");
        message.setSender("0000");

        msgSendService.modifyAndSendMessage(message,true);
    }

    @Test
    public void testRemoveMessageById() throws Exception {

        msgSendService.removeMessageById("110120160105173658000000");
    }

    @Test
    public void testBatchRemoveMessages() throws Exception {

    }
}