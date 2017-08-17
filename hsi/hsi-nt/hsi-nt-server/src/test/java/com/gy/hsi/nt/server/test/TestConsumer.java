package com.gy.hsi.nt.server.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.MsgType;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;

/**
 * 
 * @className:TestConsumer
 * @author:likui
 * @date:2015年8月4日
 * @desc:通知系统测试类
 * @company:gyist
 */
public class TestConsumer {

	private static ApplicationContext context = null;

	private static INtService ntService = null;

	public static void info()
	{
		context = new FileSystemXmlApplicationContext(
				TestConsumer.class.getResource("spring-dubbo-consumer.xml").getPath());
		((AbstractApplicationContext) context).start();
		ntService = (INtService) context.getBean("ntService");
	}

	public static void main(String[] args)
	{
		info();
		// sendNote(1);
		// sendEmail(1);
		// sendDynamic(1);
	}

	public static void sendNote(int count)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 1; i <= count; i++)
		{
			NoteBean note = new NoteBean();
			// note.setTemplateVersion("1.0");
			if (i == 1)
			{
				note.setPriority(Priority.HIGH.getPriority());
			} else if (i == 2)
			{
				note.setPriority(Priority.MIDDLE.getPriority());
			} else
			{
				note.setPriority(Priority.LOW.getPriority());
			}
			note.setSender("person");
			note.setSendDate(new Date());
			param.put("noteParam", note);
			new Thread(new NotectTast(JSONObject.toJSONString(param))).start();
		}
	}

	public static void sendEmail(int count)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 1; i <= count; i++)
		{
			EmailBean email = new EmailBean();
			if (i <= 8)
			{
				if (i % 2 == 0)
				{
					email.setPriority(Priority.HIGH.getPriority());
				} else
				{
					email.setPriority(Priority.MIDDLE.getPriority());
				}
			} else
			{
				email.setPriority(Priority.LOW.getPriority());
			}
			email.setMailTitle("找回密码");
			email.setSender("msc");
			email.setSendDate(new Date());
			param.put("emailParam", email);
			new Thread(new NotectTast(JSONObject.toJSONString(param))).start();
		}
	}

	public static void sendDynamic(int count)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 1; i <= count; i++)
		{
			DynamicSysMsgBean dynamic = new DynamicSysMsgBean();
			dynamic.setMsgId(UUID.randomUUID().toString());
			dynamic.setMsgReceiver(new String[]
			{ "0600" + i + "000000" });
			dynamic.setMsgTitle("你好");
			dynamic.setMsgType(MsgType.PRIVATE_LETTER.getMsgType());
			dynamic.setMsgContent("年呵呵呵呵呵呵呵呵呵呵呵");
			if (i <= 8)
			{
				if (i % 2 == 0)
				{
					dynamic.setPriority(Priority.HIGH.getPriority());
				} else
				{
					dynamic.setPriority(Priority.MIDDLE.getPriority());
				}
			} else
			{
				dynamic.setPriority(Priority.LOW.getPriority());
			}
			dynamic.setSender("06000000000_0000");
			dynamic.setSendDate(new Date());
			param.put("dynamicParam", dynamic);
			new Thread(new NotectTast(JSONObject.toJSONString(param))).start();
		}
	}

	public static class NotectTast extends TimerTask {
		private String param;

		public NotectTast(String param)
		{
			super();
			this.param = param;
		}

		@Override
		public void run()
		{
			boolean falg = false;
			try
			{
				JSONObject json = JSONObject.parseObject(param, JSONObject.class);
				// 短信
				if (StringUtils.isNotBlank(json.getString("noteParam")))
				{
					NoteBean note = JSONObject.parseObject(json.getString("noteParam"), NoteBean.class);
					ntService.sendNote(note);
				}
				// 邮件
				if (StringUtils.isNotBlank(json.getString("emailParam")))
				{
					EmailBean email = JSONObject.parseObject(json.getString("emailParam"), EmailBean.class);
					ntService.sendEmail(email);
				}
				// 互动
				if (StringUtils.isNotBlank(json.getString("dynamicParam")))
				{
					DynamicSysMsgBean dynamic = JSONObject.parseObject(json.getString("dynamicParam"),
							DynamicSysMsgBean.class);
					ntService.sendDynamicSys(dynamic);
				}
				System.out.println("参数：" + param);
				System.out.println("响应结果：---------------> " + falg);
			} catch (NoticeException e)
			{
				e.printStackTrace();
			}
		}
	}
}
