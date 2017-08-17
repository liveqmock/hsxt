/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.msgtpl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgOptType;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgTempType;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgTplRedisKey;
import com.gy.hsxt.bs.common.enumtype.msgtpl.ReviewResult;
import com.gy.hsxt.bs.msgtpl.mapper.MsgTemplateMapper;
import com.gy.hsxt.bs.msgtpl.service.MessageTplService;
import com.gy.hsxt.bs.service.testcase.BaseTest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 消息模版单元测试
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.msgtpl
 * @ClassName: MessageTplServiceTest
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-9 下午6:41:44
 * @version V3.0.0
 */
public class MessageTplServiceTest extends BaseTest {
    /**
     * 缓存工具
     */
    @SuppressWarnings("rawtypes")
    @Resource
    RedisUtil fixRedisUtil;

    @Autowired
    MsgTemplateMapper tplMapper;

    @Autowired
    MessageTplService messageTplService;

    @Test
    public void testSaveMessageTpl() {
        MsgTemplate msgTpl = new MsgTemplate();
        msgTpl.setTempName("单元测试手机模版11211");
        msgTpl.setTempType(MsgTempType.SMS.getCode());
        msgTpl.setBizType(MsgBizType.MSG_BIZ_TYPE100.getCode());
        msgTpl.setCustType(CustType.PERSON.getCode());
        msgTpl.setBuyResType(ResType.FIRST_SECTION_RES.getCode());
        msgTpl.setTempFmt("TXT");
        msgTpl.setTempContent("这是模版内容啊。。。这是模版内容啊。。。这是模版内容啊。。。重要的东西要写三次！！！");
        messageTplService.saveMessageTpl(msgTpl, getOptCustId(), getOptCustName());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTplRedis() {
        List<MsgTemplate> listTPl = tplMapper.findEnabledMsgTpl();

        String tplCatchKey = "";
        for (MsgTemplate msgTemplate : listTPl)
        {
            System.out.println("===========" + String.valueOf(msgTemplate));
            tplCatchKey = msgTemplate.getTempType() + "_" + msgTemplate.getCustType() + "_" + msgTemplate.getBizType()
                    + "_" + (StringUtils.isNotBlank(msgTemplate.getBuyResType()) ? msgTemplate.getBuyResType() : "");
            fixRedisUtil.add(MsgTplRedisKey.MSG_TEMPLATE, tplCatchKey, JSONObject.toJSONString(msgTemplate));
        }
    }

    @Test
    public void testGetTplRedis() {
        String tplCatchKey = "1_1_107_";
        Object tpl = fixRedisUtil.get(MsgTplRedisKey.MSG_TEMPLATE, tplCatchKey, Object.class);
        System.out.println(tpl);
    }

    @Test
    public void testModifyMessageTpl() {
        MsgTemplate msgTpl = new MsgTemplate();
        msgTpl.setTempId("110120160310095108000000");
        msgTpl.setTempName("呵呵模版11");
        msgTpl.setTempType(MsgTempType.SMS.getCode());
        msgTpl.setBizType(MsgBizType.MSG_BIZ_TYPE100.getCode());
        msgTpl.setCustType(CustType.SERVICE_CORP.getCode());
        // msgTpl.setCustType(CustType.PERSON.getCode());
        msgTpl.setBuyResType(ResType.FIRST_SECTION_RES.getCode());
        msgTpl.setTempFmt("TXT");
        msgTpl.setTempContent("这是模版内容啊。。。这是模版内容啊。。。这是模版内容啊。。。重要的东西要写三次！！！");
        messageTplService.modifyMessageTpl(msgTpl);
    }

    @Test
    public void testGetMessageTplList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<MsgTemplate> smgTplList = messageTplService.getMessageTplList(3, null, null, 1, page);

            List<MsgTemplate> tplList = smgTplList.getResult();
            if (tplList != null && tplList.size() > 0)
            {
                for (MsgTemplate msgTpl : tplList)
                {
                    System.out.println("模版 = " + msgTpl);
                }
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testStartOrStopTpl() {
        messageTplService.startOrStopTpl("110120160315163343000000", MsgOptType.STOP.getCode(), getOptCustId(),
                getOptCustName());
    }

    @Test
    public void testGetMessageTplInfo() {
        System.out.println(messageTplService.getMessageTplInfo("110120160310093213000000"));
    }

    @Test
    public void testGetMessageTplApprList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<MsgTemplateAppr> smgTplList = messageTplService.getMessageTplApprList(
                    "00000000156163438271051776", MsgTempType.E_MAIL.getCode(), null, null, page);

            List<MsgTemplateAppr> tplList = smgTplList.getResult();
            if (tplList != null && tplList.size() > 0)
            {
                for (MsgTemplateAppr msgTpl : tplList)
                {
                    System.out.println("模版 = " + msgTpl);
                }
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMsgTplApprHisList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<MsgTemplateAppr> smgTplList = messageTplService.getMsgTplApprHisList("110120160312110350000000",
                    page);
            List<MsgTemplateAppr> tplList = smgTplList.getResult();
            if (tplList != null && tplList.size() > 0)
            {
                for (MsgTemplateAppr msgTpl : tplList)
                {
                    System.out.println("模版 = " + msgTpl);
                }
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testReviewTemplate() {
        MsgTemplateAppr tplAppr = new MsgTemplateAppr();
        tplAppr.setApplyId("110120160315165541000001");
        tplAppr.setTempId("110120160315163343000000");
        tplAppr.setApprOptId("00000000156000820160217");
        tplAppr.setApprOptName("超级管理员");
        tplAppr.setApprRemark("超级管理员_复核通过");
        messageTplService.reviewTemplate(tplAppr, ReviewResult.PASS.getCode());
    }

    @Test
    public void testGetEnabledMessageTplToRedis() {
        String str = messageTplService.getEnabledMessageTplToRedis(1, 1, 107, null);
        System.out.println(str);
    }
}
