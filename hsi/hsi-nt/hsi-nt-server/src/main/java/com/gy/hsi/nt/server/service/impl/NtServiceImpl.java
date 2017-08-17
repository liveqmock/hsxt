package com.gy.hsi.nt.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.*;
import com.gy.hsi.nt.api.common.MsgChannel;
import com.gy.hsi.nt.api.common.MsgType;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.api.common.ValidateParamUtil;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsi.nt.server.service.ISendQueueService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.StringUtil;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;
import com.gy.hsi.redis.client.api.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * dubbo接收消息接口实现类
 * 
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: NtServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 上午10:29:49
 * @company: gyist
 * @version V3.0.0
 */
@Component("ntService")
public class NtServiceImpl implements INtService {

	private static final Logger logger = Logger.getLogger(NtServiceImpl.class);

	/**
	 * 发送信息到MQService
	 */
	@Autowired
	ISendQueueService sendQueueService;

	/**
	 * 获取缓存
	 */
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisUtil fixRedisUtil;

	/**
	 * 发送短信
	 * 
	 * @Description:
	 * @param bean
	 *            短信参数
	 * @throws NoticeException
	 */
	@Override
	public void sendNote(NoteBean bean) throws NoticeException
	{
		logger.info("NoticeSystem接收到短信,param==>:" + bean);
		if (null == bean)
		{
			throw new NoticeException("短信对象为空", RespCode.RARAM_IS_NULL.name());
		}
		// 短信消息渠道
		bean.setMsgChannel(MsgChannel.NOTE.getChannel());
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, null);
			// 获取模板名称、消息内容
			String[] temp = replaceSendContent(bean.getContent(), bean.getMsgChannel(), bean.getCustType(),
					bean.getBizType(), bean.getBuyResType());
			bean.setTempName(temp[0]);
			bean.setMsgContent(temp[1]);
			sendQueueService.mqSendNote(bean);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送短信失败," + ex.getMessage(), ex.getErrorCode());
		}
	}

	/**
	 * 发送自定义内容短信
	 * 
	 * @Description:
	 * @param bean
	 * @throws NoticeException
	 */
	@Override
	public void sendSelfNote(SelfNoteBean bean) throws NoticeException
	{
		logger.info("接收到自定义内容短信,param==>:" + bean);
		if (null == bean)
		{
			throw new NoticeException("短信对象为空", RespCode.RARAM_IS_NULL.name());
		}
		// 短信消息渠道
		bean.setMsgChannel(MsgChannel.NOTE.getChannel());
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, null);
			// 验证手机号码是否合法
			NoteBean note = JSONObject.parseObject(JSONObject.toJSONString(bean), NoteBean.class);
			sendQueueService.mqSendNote(note);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送自定义内容短信失败," + ex.getMessage(), ex.getErrorCode());
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @Description:
	 * @param bean
	 *            邮件参数
	 * @throws NoticeException
	 */
	@Override
	public void sendEmail(EmailBean bean) throws NoticeException
	{
		logger.info("接收到邮件,param==>:" + bean);
		if (null == bean)
		{
			throw new NoticeException("邮件对象为空", RespCode.RARAM_IS_NULL.name());
		}
		// 邮件消息渠道
		bean.setMsgChannel(MsgChannel.EMAIIL.getChannel());
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, null);
			// 获取模板名称、消息内容
			String[] temp = replaceSendContent(bean.getContent(), bean.getMsgChannel(), bean.getCustType(),
					bean.getBizType(), bean.getBuyResType());
			bean.setTempName(temp[0]);
			bean.setMsgContent(temp[1]);
			sendQueueService.mqSendEmail(bean);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送邮件失败," + ex.getMessage(), ex.getErrorCode());
		}
	}

	/**
	 * 发送自定义内容邮件
	 * 
	 * @Description:
	 * @param bean
	 *            邮件参数
	 * @throws NoticeException
	 */
	@Override
	public void sendSelfEmail(SelfEmailBean bean) throws NoticeException
	{
		logger.info("接收到自定义内容邮件,param==>:" + bean);
		if (null == bean)
		{
			throw new NoticeException("邮件对象为空", RespCode.RARAM_IS_NULL.name());
		}
		// 邮件消息渠道
		bean.setMsgChannel(MsgChannel.EMAIIL.getChannel());
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, null);
			EmailBean email = JSONObject.parseObject(JSONObject.toJSONString(bean), EmailBean.class);
			sendQueueService.mqSendEmail(email);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送自定义内容邮件失败," + ex.getMessage(), ex.getErrorCode());
		}
	}

	/**
	 * 发送业务互动消息
	 * 
	 * @Description:
	 * @param bean
	 *            业务互动实体
	 * @throws NoticeException
	 */
	@Override
	public void sendDynamicBiz(DynamicBizMsgBean bean) throws NoticeException
	{
		logger.info("接收到业务互动消息,param==>:" + bean);
		if (null == bean)
		{
			throw new NoticeException("业务互动消息对象为空", RespCode.RARAM_IS_NULL.name());
		}
		// 业务互动消息渠道
		bean.setMsgChannel(MsgChannel.DYNAMIC_BIZ.getChannel());
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, null);
			// 获取模板名称、消息内容
			String[] temp = replaceSendContent(bean.getContent(), bean.getMsgChannel(), bean.getCustType(),
					bean.getBizType(), bean.getBuyResType());
			bean.setTempName(temp[0]);
			bean.setMsgContent(temp[1]);
			sendQueueService.mqSendDynamicBiz(bean);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送业务互动失败," + ex.getMessage(), ex.getErrorCode());
		}
	}

	/**
	 * 发送自定义内容互动业务消息
	 * 
	 * @Description:
	 * @param bean
	 * @throws NoticeException
	 */
	@Override
	public void sendSelfDynamicBiz(SelfDynamicBizMsgBean bean) throws NoticeException
	{
		logger.info("接收到自定义内容互动业务消息,param==>:" + bean);
		if (null == bean)
		{
			throw new NoticeException("互动业务消息对象为空", RespCode.RARAM_IS_NULL.name());
		}
		// 互动业务消息渠道
		bean.setMsgChannel(MsgChannel.DYNAMIC_BIZ.getChannel());
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, null);
			DynamicBizMsgBean dynamicBiz = JSONObject.parseObject(JSONObject.toJSONString(bean),
					DynamicBizMsgBean.class);
			sendQueueService.mqSendDynamicBiz(dynamicBiz);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送自定义内容互动业务消息失败," + ex.getMessage(), ex.getErrorCode());
		}
	}

	/**
	 * 发送系统互动消息
	 * 
	 * @Description:
	 * @param bean
	 *            系统互生参数
	 * @throws NoticeException
	 */
	@Override
	public void sendDynamicSys(DynamicSysMsgBean bean) throws NoticeException
	{
		logger.info("接收到系统互动信息，参数:" + bean);
		if (null == bean)
		{
			throw new NoticeException("互动消息对象为空", RespCode.RARAM_IS_NULL.name());
		}
		try
		{
			bean.setMsgId(UUID.randomUUID().toString());
			// 去掉接受者的空元素
			bean.setMsgReceiver(StringUtil.deleteArrayEmptyElement(bean.getMsgReceiver()));
			// 验证参数
			validateParams(bean, bean.getMsgType());
			sendQueueService.mqSendDynamicSys(bean);
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw new NoticeException("发送系统互生消息失败", ex.getErrorCode());
		}
	}

	/**
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:33:00
	 * @param obj
	 *            参数实体
	 * @param msgType
	 *            互动消息类型
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	private void validateParams(Object obj, Integer msgType) throws NoticeException
	{
		String result = null;
		/**
		 * 验证参数
		 */
		result = ValidateParamUtil.validateParam(obj);
		if (StringUtils.isNotBlank(result))
		{
			throw new NoticeException("验证实体参数错误,Msg ----- >" + result, RespCode.RARAM_ERROR.name());
		}
		/**
		 * 验证互动消息类型
		 */
		if (null != msgType)
		{
			boolean falg = true;
			for (MsgType type : MsgType.values())
			{
				if (msgType.intValue() == type.getMsgType())
				{
					falg = false;
				}
			}
			if (falg)
			{
				throw new NoticeException("互动消息类型错误", RespCode.DYNAMIC_MSG_TYEP_ERROR.name());
			}
		}
	}

	/**
	 * 验证邮箱或手机
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:31:08
	 * @param receivers
	 *            消息接收者
	 * @param msgChannel
	 *            消息渠道
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	private void validEmailOrMobile(String[] receivers, int msgChannel) throws NoticeException
	{
		String patt = "";
		String result = "";
		if (MsgChannel.NOTE.getChannel() == msgChannel)
		{
			patt = CacheUtil.getValue(ParamsKey.PHONE_PATTERN.getKey(), String.class);
			result = RespCode.PHONE_ERROR.name();
		}
		if (MsgChannel.EMAIIL.getChannel() == msgChannel)
		{
			patt = CacheUtil.getValue(ParamsKey.EMAIL_PATTERN.getKey(), String.class);
			result = RespCode.EMAIL_ERROR.name();
		}
		/**
		 * 验证接受者是否为空
		 */
		if (StringUtils.isBlank(StringUtil.arrayToString(receivers, ",")))
		{
			throw new NoticeException("接收者为空", RespCode.RECEIVERS_IS_NULL.name());
		}
		Pattern pattern = null;
		Matcher matcher = null;
		for (int i = 0; i < receivers.length; i++)
		{
			if (StringUtils.isNotBlank(receivers[i]))
			{
				pattern = Pattern.compile(patt);
				matcher = pattern.matcher(receivers[i]);
				if (!matcher.matches())
				{
					throw new NoticeException("邮箱或手机号码错误", result);
				}
			}
		}
	}

	/**
	 * 替换模板占位符
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:27:42
	 * @param content
	 *            发送内容
	 * @param msgChannel
	 *            消息渠道
	 * @param custType
	 *            客户类型
	 * @param bizType
	 *            业务类型
	 * @param buyResType
	 *            启用资源类型
	 * @return
	 * @throws NoticeException
	 * @return : String[]
	 * @version V3.0.0
	 */
	@SuppressWarnings("unchecked")
	private String[] replaceSendContent(Map<String, String> content, int msgChannel, int custType, String bizType,
			int buyResType) throws NoticeException
	{
		// 缓存key
		String catchKey = null;
		// 模板对象
		Object obj = null;
		// 模板json对象
		JSONObject json = null;
		// 模板内容
		String tempContent = null;
		// 模板名称
		String tempName = null;
		catchKey = msgChannel + "_" + custType + "_" + bizType + "_" + (buyResType != 0 ? buyResType : "");
		try
		{
			obj = fixRedisUtil.get(ParamsKey.MSG_TEMPLATE.getKey(), catchKey, Object.class);

			if (null == obj)
			{
				throw new NoticeException("获取缓存模板失败", RespCode.TEMP_NOT_FIND.name());
			}
			json = JSONObject.parseObject(obj.toString());
			tempContent = json.getString("tempContent");
			tempName = json.getString("tempName");
			if (StringUtils.isBlank(tempContent))
			{
				throw new NoticeException("获取缓存模板失败", RespCode.TEMP_CONTENT_NULL.name());
			}
			for (String key : content.keySet())
			{
				String value = content.get(key);
				tempContent = tempContent.replace(key, value);
			}
			return new String[] { tempName, tempContent };
		} catch (Exception e)
		{
			throw new NoticeException("获取缓存模板失败", RespCode.TEMP_NOT_FIND.name());
		}
	}
}
