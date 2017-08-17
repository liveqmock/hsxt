package com.gy.hsi.nt.server.listener;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.MsgChannel;
import com.gy.hsi.nt.server.service.IDynamicBizSendService;
import com.gy.hsi.nt.server.service.IDynamicSysSendService;
import com.gy.hsi.nt.server.service.IEmailSendService;
import com.gy.hsi.nt.server.service.INoteSendService;

/**
 * 接受中优先级MQ队列消息的监听器
 * 
 * @Package: com.gy.hsi.nt.server.listener
 * @ClassName: MiddleQueueListener
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午2:06:34
 * @company: gyist
 * @version V3.0.0
 */
public class MiddleQueueListener implements MessageListener {

	private static final Logger logger = Logger.getLogger(MiddleQueueListener.class);

	/**
	 * 短信Service
	 */
	@Autowired
	private INoteSendService noteSendService;

	/**
	 * 邮件消息Service
	 */
	@Autowired
	private IEmailSendService emailSendService;

	/**
	 * 互动消息Service
	 */
	@Autowired
	private IDynamicSysSendService dynamicSysSendService;

	/**
	 * 业务互动消息Service
	 */
	@Autowired
	private IDynamicBizSendService dynamicBizSendService;

	@Override
	public void onMessage(Message message)
	{
		JSONObject json = null;
		try
		{
			logger.info("中优先级MQ监听器接收到消息");
			json = JSONObject.parseObject(message.getBody(), JSONObject.class);
			int msgChannel = Integer.parseInt(json.getString("msgChannel"));
			// 短信
			if (msgChannel == MsgChannel.NOTE.getChannel())
			{
				NoteBean bean = JSONObject.parseObject(message.getBody(), NoteBean.class);
				noteSendService.ntSendNote(bean);
			}
			// 邮件
			if (msgChannel == MsgChannel.EMAIIL.getChannel())
			{
				EmailBean bean = JSONObject.parseObject(message.getBody(), EmailBean.class);
				emailSendService.ntSendEmail(bean);
			}
			// 系统互动
			if (msgChannel == MsgChannel.DYNAMIC_SYS.getChannel())
			{
				DynamicSysMsgBean bean = JSONObject.parseObject(message.getBody(), DynamicSysMsgBean.class);
				dynamicSysSendService.ntSendDynamicSys(bean);
			}
			// 业务互生
			if (msgChannel == MsgChannel.DYNAMIC_BIZ.getChannel())
			{
				DynamicBizMsgBean bean = JSONObject.parseObject(message.getBody(), DynamicBizMsgBean.class);
				dynamicBizSendService.ntSendDynamicBiz(bean);
			}
		} catch (Exception e)
		{
			logger.error("消息发送异常,消息实体:" + json, e);
		}
	}
}
