/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.controllers.messageCenter;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.messageCenter.IMessageCenterService;
import com.gy.hsxt.bs.bean.message.Message;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("messagecenter")
public class MessageCenterControler extends BaseController {

    @Resource
    private IMessageCenterService imessageCenterService;
    
    
    /**
     * 发送消息信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/sendMsg" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope sendMsg(HttpServletRequest request,@ModelAttribute Message msg,boolean isSend, String token) {
    	// Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        msg.setEntCustName(request.getParameter("custEntName"));
        if (httpRespEnvelope == null)
        {
            try{
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                	new Object[] {msg.getTitle(), ASRespCode.SW_ENT_MSG_TITIL.getCode(), ASRespCode.SW_ENT_MSG_TITIL.getDesc()},
                    new Object[] {msg.getMsg(), ASRespCode.SW_ENT_MSG_CONTENT.getCode(), ASRespCode.SW_ENT_MSG_CONTENT.getDesc()},
                    new Object[] {msg.getSummary(), ASRespCode.SW_ENT_MSG_SUMMARY.getCode(), ASRespCode.SW_ENT_MSG_TITIL.getDesc()},
                    new Object[] {msg.getType(), ASRespCode.SW_ENT_MSG_TYPE.getCode(), ASRespCode.SW_ENT_MSG_TYPE.getDesc()},
                    new Object[] {msg.getLevel(), ASRespCode.SW_ENT_MSG_LEVEL.getCode(), ASRespCode.SW_ENT_MSG_LEVEL.getDesc()},
                    new Object[] {msg.getReceiptor(), ASRespCode.SW_ENT_MSG_RECEIPTOR.getCode(), ASRespCode.SW_ENT_MSG_RECEIPTOR.getDesc()},
                    new Object[] {msg.getEntResNo(), ASRespCode.SW_ENT_MSG_ENTRESNO.getCode(), ASRespCode.SW_ENT_MSG_ENTRESNO.getDesc()},
                    new Object[] {msg.getEntCustId(), ASRespCode.SW_ENT_MSG_ENTCUSTID.getCode(), ASRespCode.SW_ENT_MSG_ENTCUSTID.getDesc()},
                    new Object[] {msg.getEntCustName(), ASRespCode.SW_ENT_MSG_ENTCUSTNAME.getCode(), ASRespCode.SW_ENT_MSG_ENTCUSTNAME.getDesc()},
                    new Object[] {msg.getCreatedBy(), ASRespCode.SW_ENT_MSG_CREATEDBY.getCode(), ASRespCode.SW_ENT_MSG_CREATEDBY.getDesc()},
                    new Object[] {isSend, ASRespCode.SW_ENT_MSG_ISSEND.getCode(), ASRespCode.SW_ENT_MSG_ISSEND.getDesc()}
                    
                );
                //限制长度验证
                RequestUtil.verifyParamsLength(
                	new Object[]{msg.getTitle(),1,100,ASRespCode.SW_ENT_MSG_TITLE_MAXLENGTH.getCode(),ASRespCode.SW_ENT_MSG_TITLE_MAXLENGTH.getDesc()},
                	new Object[]{msg.getSummary(),1,100,ASRespCode.SW_ENT_MSG_SUMMARY_MAXLENGTH.getCode(),ASRespCode.SW_ENT_MSG_SUMMARY_MAXLENGTH.getDesc()}
                );
                imessageCenterService.createMsgSend(msg,isSend, token);
                httpRespEnvelope = new HttpRespEnvelope();
            }catch(Exception e){
            	SystemLog.error("服务公司", "创建消息", "失败", e);
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 修改并发送消息信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/editMsg" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope editMsg(HttpServletRequest request,@ModelAttribute Message msg,boolean isSend, String token) {
    	// Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        msg.setEntCustName(request.getParameter("custEntName"));
        if (httpRespEnvelope == null)
        {
            try{
            	//非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                	new Object[] {msg.getMsgId(), ASRespCode.SW_ENT_MSG_ID.getCode(), ASRespCode.SW_ENT_MSG_ID.getDesc()},
                    new Object[] {msg.getUpdatedBy(), ASRespCode.SW_ENT_MSG_UPDATEDBY.getCode(), ASRespCode.SW_ENT_MSG_UPDATEDBY.getDesc()},
                    new Object[] {isSend, ASRespCode.SW_ENT_MSG_ISSEND.getCode(), ASRespCode.SW_ENT_MSG_ISSEND.getDesc()}
                    
                );
                //限制长度验证
                RequestUtil.verifyParamsLength(
                	new Object[]{msg.getTitle(),1,100,ASRespCode.SW_ENT_MSG_TITLE_MAXLENGTH.getCode(),ASRespCode.SW_ENT_MSG_TITLE_MAXLENGTH.getDesc()},
                	new Object[]{msg.getSummary(),1,100,ASRespCode.SW_ENT_MSG_SUMMARY_MAXLENGTH.getCode(),ASRespCode.SW_ENT_MSG_SUMMARY_MAXLENGTH.getDesc()}
                );
                imessageCenterService.editMsg(msg, isSend, token);
                httpRespEnvelope = new HttpRespEnvelope();
            }catch(Exception e){
            	SystemLog.error("服务公司", "修改消息", "失败", e);
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    /**
     * 查看消息信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope query(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try{
                String msgId = request.getParameter("msgId");
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                     new Object[] {msgId, ASRespCode.SW_ENT_MSG_ID.getCode(), ASRespCode.SW_ENT_MSG_ID.getDesc()}
                );
                Message msg = imessageCenterService.query(msgId);
                httpRespEnvelope = new HttpRespEnvelope(msg);
            }catch(Exception e){
            	SystemLog.error("服务公司", "单笔查询", "失败", e);
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    /**
     * 删除消息信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/delete" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope delete(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try{
                String msgId = request.getParameter("msgId");
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                     new Object[] {msgId, ASRespCode.SW_ENT_MSG_ID.getCode(), ASRespCode.SW_ENT_MSG_ID.getDesc()}
                );
                imessageCenterService.deleteMsgSend(msgId);
                httpRespEnvelope = new HttpRespEnvelope();
            }catch(Exception e){
            	SystemLog.error("服务公司", "单笔删除", "失败", e);
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 删除消息信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/deleteMessages" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope deleteMessages(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try{
                String msgId = request.getParameter("msgId");
                List<String> msgIds= JSON.parseArray(URLDecoder.decode(msgId, "UTF-8"),String.class);
                 
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                     new Object[] {msgId, ASRespCode.SW_ENT_MSG_ID.getCode(), ASRespCode.SW_ENT_MSG_ID.getDesc()}
                );
                
                imessageCenterService.deleteMessages(msgIds);
                httpRespEnvelope = new HttpRespEnvelope();
                
            }catch(Exception e){
            	SystemLog.error("服务公司", "批量删除", "失败", e);
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @Override
    protected IBaseService getEntityService() {
        
        return imessageCenterService;
    }
    
}
