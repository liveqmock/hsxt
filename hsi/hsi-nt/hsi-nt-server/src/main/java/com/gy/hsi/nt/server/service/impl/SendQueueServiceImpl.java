package com.gy.hsi.nt.server.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.service.ISendQueueService;
import com.gy.hsi.nt.server.util.enumtype.RoutingKey;

/**
 * 发送信息到MQ的接口实现类
 * 
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: SendQueueServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午12:00:56
 * @company: gyist
 * @version V3.0.0
 */
@Service("sendQueueService")
public class SendQueueServiceImpl implements ISendQueueService {

	private static final Logger logger = Logger.getLogger(SendQueueServiceImpl.class);

	/**
	 * 短信MQ模板
	 */
	@Autowired
	private AmqpTemplate amqpTemplateNote;

	/**
	 * 邮件MQ模板
	 */
	@Autowired
	private AmqpTemplate amqpTemplateEmail;

	/**
	 * 系统互动消息MQ模板
	 */
	@Autowired
	private AmqpTemplate amqpTemplateDynamicSys;

	/**
	 * 业务互动消息MQ模板
	 */
	@Autowired
	private AmqpTemplate amqpTemplateDynamicBiz;

	/**
	 * 发送短信到MQ队列
	 * 
	 * @Description:
	 * @param bean
	 *            短信参数
	 * @throws NoticeException
	 */
	@Override
	public void mqSendNote(NoteBean bean) throws NoticeException
	{
		String routingKey = null;
		switch (bean.getPriority())
		{
		case 0:
			routingKey = RoutingKey.RK_NOTE_HIGH.getKey();
			break;
		case 1:
			routingKey = RoutingKey.RK_NOTE_MIDDLE.getKey();
			break;
		case 2:
			routingKey = RoutingKey.RK_NOTE_LOW.getKey();
			break;
		default:
			break;
		}
		if (StringUtils.isBlank(routingKey))
		{
			throw new NoticeException("通知系统异常,未获取到MQ路由key名称", RespCode.SYSTEM_ERROR.name());
		}
		try
		{
			amqpTemplateNote.convertAndSend(routingKey, bean);
		} catch (Exception e)
		{
			logger.error("发送短信到MQ队列异常,路由key名称:" + routingKey, e);
			throw new NoticeException("通知系统异常", RespCode.SYSTEM_ERROR.name());
		}
	}

	/**
	 * 发送邮件到MQ队列
	 * 
	 * @Description:
	 * @param bean
	 *            邮件实体
	 * @throws NoticeException
	 */
	@Override
	public void mqSendEmail(EmailBean bean) throws NoticeException
	{
		String routingKey = null;
		switch (bean.getPriority())
		{
		case 0:
			routingKey = RoutingKey.RK_EMAIL_HIGH.getKey();
			break;
		case 1:
			routingKey = RoutingKey.RK_EMAIL_MIDDLE.getKey();
			break;
		case 2:
			routingKey = RoutingKey.RK_EMAIL_LOW.getKey();
			break;
		default:
			break;
		}
		if (StringUtils.isBlank(routingKey))
		{
			throw new NoticeException("通知系统异常,未获取到MQ路由key名称", RespCode.SYSTEM_ERROR.name());
		}
		try
		{
			amqpTemplateEmail.convertAndSend(routingKey, bean);
		} catch (Exception e)
		{
			logger.error("发送邮件到MQ队列异常,路由key名称:" + routingKey, e);
			throw new NoticeException("通知系统异常", RespCode.SYSTEM_ERROR.name());
		}
	}

	/**
	 * 发送系统互动消息到MQ队列
	 * 
	 * @Description:
	 * @param bean
	 *            互动消息实体
	 * @throws NoticeException
	 */
	@Override
	public void mqSendDynamicSys(DynamicSysMsgBean bean) throws NoticeException
	{
		String routingKey = null;
		switch (bean.getPriority())
		{
		case 0:
			routingKey = RoutingKey.RK_DYNAMIC_SYS_HIGH.getKey();
			break;
		case 1:
			routingKey = RoutingKey.RK_DYNAMIC_SYS_MIDDLE.getKey();
			break;
		case 2:
			routingKey = RoutingKey.RK_DYNAMIC_SYS_LOW.getKey();
			break;
		default:
			break;
		}
		if (StringUtils.isBlank(routingKey))
		{
			throw new NoticeException("通知系统异常,未获取到MQ路由key名称", RespCode.SYSTEM_ERROR.name());
		}
		try
		{
			amqpTemplateDynamicSys.convertAndSend(routingKey, bean);
		} catch (Exception e)
		{
			logger.error("发送系统互动消息到MQ队列异常,路由key名称:" + routingKey, e);
			throw new NoticeException("通知系统异常", RespCode.SYSTEM_ERROR.name());
		}
	}

	/**
	 * 发送业务互动消息到MQ队列
	 * 
	 * @Description:
	 * @param bean
	 * @throws NoticeException
	 */
	@Override
	public void mqSendDynamicBiz(DynamicBizMsgBean bean) throws NoticeException
	{
		String routingKey = null;
		switch (bean.getPriority())
		{
		case 0:
			routingKey = RoutingKey.RK_DYNAMIC_BIZ_HIGH.getKey();
			break;
		case 1:
			routingKey = RoutingKey.RK_DYNAMIC_BIZ_MIDDLE.getKey();
			break;
		case 2:
			routingKey = RoutingKey.RK_DYNAMIC_BIZ_LOW.getKey();
			break;
		default:
			break;
		}
		if (StringUtils.isBlank(routingKey))
		{
			throw new NoticeException("通知系统异常,未获取到MQ路由key名称", RespCode.SYSTEM_ERROR.name());
		}
		try
		{
			amqpTemplateDynamicBiz.convertAndSend(routingKey, bean);
		} catch (Exception e)
		{
			logger.error("发送业务互动消息到MQ队列异常,路由key名称:" + routingKey, e);
			throw new NoticeException("通知系统异常", RespCode.SYSTEM_ERROR.name());
		}
	}
}
