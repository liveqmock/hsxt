package com.gy.hsi.nt.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.DateUtil;
import com.gy.hsi.nt.api.common.MsgChannel;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.server.entity.result.AbandonMsg;
import com.gy.hsi.nt.server.entity.result.ResendMsg;
import com.gy.hsi.nt.server.entity.result.SendedMsg;
import com.gy.hsi.nt.server.service.IDynamicBizSendService;
import com.gy.hsi.nt.server.service.IDynamicSysSendService;
import com.gy.hsi.nt.server.service.IEmailSendService;
import com.gy.hsi.nt.server.service.IMsgResultService;
import com.gy.hsi.nt.server.service.INoteSendService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.StringUtil;
import com.gy.hsi.nt.server.util.enumtype.IsActive;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;

/**
 * 定时任务执行类
 * 
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: TimingTastServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午12:22:07
 * @company: gyist
 * @version V3.0.0
 */
@Service("timingTastService")
public class TimingTastServiceImpl {

	private static final Logger logger = Logger.getLogger(TimingTastServiceImpl.class);

	/**
	 * 高优先级线程池
	 */
	@Resource(name = "highPriorityTaskExecutor")
	private ThreadPoolTaskExecutor highPriorityTaskExecutor;

	/**
	 * 中优先级线程池
	 */
	@Resource(name = "middlePriorityTaskExecutor")
	private ThreadPoolTaskExecutor middlePriorityTaskExecutor;

	/**
	 * 低优先级线程池
	 */
	@Resource(name = "lowPriorityTaskExecutor")
	private ThreadPoolTaskExecutor lowPriorityTaskExecutor;

	/**
	 * 查询消息结果Service
	 */
	@Autowired
	private IMsgResultService msgResultService;

	/**
	 * 发送短信Service
	 */
	@Autowired
	private INoteSendService noteSendService;

	/**
	 * 发送邮件Service
	 */
	@Autowired
	private IEmailSendService emailSendService;

	/**
	 * 发送互动消息Service
	 */
	@Autowired
	private IDynamicSysSendService dynamicSysSendService;

	/**
	 * 业务互动消息Service
	 */
	@Autowired
	private IDynamicBizSendService dynamicBizSendService;

	/**
	 * 默认的重发次数
	 */
	private static int resendCount = 5;

	/**
	 * 默认每页的行数
	 */
	private static int pageSize = 100;

	/**
	 * 是否读取参数
	 */
	private static boolean isReadParam = true;

	/**
	 * 执行重发定时任务
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:20:01
	 * @return : void
	 * @version V3.0.0
	 */
	public void resendMsgTast()
	{
		if (isReadParam)
		{
			Map<String, String> cahceParam = CacheUtil.getConfigCache();
			if (null != cahceParam)
			{
				resendCount = Integer.parseInt(cahceParam.get(ParamsKey.RESEND_COUNT.getKey()));
				pageSize = Integer.parseInt(cahceParam.get(ParamsKey.QUERY_PAGE_SIZE.getKey()));
				isReadParam = false;
			}
		}
		highPriorityResend();
		middlePriorityResend();
		lowPriorityResend();
	}

	/**
	 * 删除彻底失败和成功的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:22:44
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeAbandonAndSended()
	{
		logger.info("进入删除彻底失败和成功的消息任务");
		try
		{
			msgResultService.removeSendMsg();
		} catch (Exception ex)
		{
			logger.error("删除时间超过一个月的成功和彻底失败的数据异常", ex);
		}
	}

	/**
	 * 高优先级重发
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:22:54
	 * @return : void
	 * @version V3.0.0
	 */
	private void highPriorityResend()
	{
		String isResend = CacheUtil.getValue(ParamsKey.HIGH_RESEND_ORDER.getKey(), String.class);
		logger.info("进入高优先级重发,重发的权限:" + isResend + (isResend.equals(IsActive.Y.name()) ? "重发次数:" + resendCount : ""));
		if (isResend.equals(IsActive.N.name()))
		{
			return;
		}
		Integer count = msgResultService.queryResendCountByPriority(Priority.HIGH.getPriority());
		logger.info("高优先级重发的总行数:" + count);
		if (count.intValue() <= 0)
		{
			CacheUtil.put(ParamsKey.HIGH_RESEND_ORDER.getKey(), IsActive.N.name());
			return;
		}
		for (int[] page : computePageCount(count))
		{
			List<ResendMsg> resendList = msgResultService.queryResendMsgList(Priority.HIGH.getPriority(), page[0],
					page[1]);
			if (null == resendList || resendList.size() <= 0)
			{
				continue;
			}
			for (final ResendMsg resend : resendList)
			{
				Runnable runnable = new Runnable()
				{
					@Override
					public void run()
					{
						boolean falg = false;
						try
						{
							// 短信
							if (resend.getMsgChannel() == MsgChannel.NOTE.getChannel())
							{
								NoteBean bean = new NoteBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								falg = noteSendService.resendNote(bean);
							}
							// 邮件
							if (resend.getMsgChannel() == MsgChannel.EMAIIL.getChannel())
							{
								EmailBean bean = new EmailBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setMailTitle(resend.getMsgTitle());
								bean.setSender(resend.getSender());
								falg = emailSendService.resendEmail(bean);
							}
							// 系统互动
							if (resend.getMsgChannel() == MsgChannel.DYNAMIC_SYS.getChannel())
							{
								DynamicSysMsgBean bean = new DynamicSysMsgBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgSummary(resend.getMsgSummary());
								bean.setMsgSummaryPicUrl(StringUtil.stringToArray(resend.getMsgSummaryPicUrl(), ","));
								bean.setMsgTitle(resend.getMsgTitle());
								bean.setMsgType(resend.getMsgType());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								bean.setMsgId(resend.getMsgId());
								falg = dynamicSysSendService.resendDynamicSys(bean);
							}
							// 业务互动
							if (resend.getMsgChannel() == MsgChannel.DYNAMIC_BIZ.getChannel())
							{
								DynamicBizMsgBean bean = new DynamicBizMsgBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgTitle(resend.getMsgTitle());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								bean.setMsgId(resend.getMsgId());
								bean.setMsgCode(resend.getMsgCode());
								bean.setSubMsgCode(resend.getSubMsgCode());
								falg = dynamicBizSendService.resendDynamicBiz(bean);
							}
							if (falg)
							{
								SendedMsg sended = JSONObject.parseObject(JSONObject.toJSONString(resend),
										SendedMsg.class);
								sended.setLastSendDate(DateUtil.now());
								sended.setCreatedDate(DateUtil.now());
								sended.setCreatedby("ntSystem");
								msgResultService.resendMsgSended(sended, resend.getMsgId());
							} else
							{
								if (resendCount == resend.getFailedCounts())
								{
									AbandonMsg abandon = JSONObject.parseObject(JSONObject.toJSONString(resend),
											AbandonMsg.class);
									abandon.setPriority(abandon.getPriority());
									abandon.setFailedCounts(resendCount);
									abandon.setLastSendDate(DateUtil.now());
									abandon.setCreatedDate(DateUtil.now());
									abandon.setCreatedby("ntSystem");
									msgResultService.resendMsgAbandon(abandon, resend.getMsgId());
								} else
								{
									ResendMsg newResend = new ResendMsg();
									newResend.setMsgId(resend.getMsgId());
									newResend.setFailedCounts(resend.getFailedCounts() + 1);
									newResend.setLastSendDate(DateUtil.now());
									msgResultService.modifyResendMsg(newResend);
								}
							}
						} catch (Exception ex)
						{
							logger.error("高优先级发送失败,MsgId:" + resend.getMsgId(), ex);
						}
					}
				};
				highPriorityTaskExecutor.execute(runnable);
			}
		}
	}

	/**
	 * 中优先级重发
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:23:04
	 * @return : void
	 * @version V3.0.0
	 */
	private void middlePriorityResend()
	{
		String isResend = CacheUtil.getValue(ParamsKey.MIDDLE_RESEND_ORDER.getKey(), String.class);
		logger.info("进入中优先级重发,重发的权限:" + isResend + (isResend.equals(IsActive.Y.name()) ? "重发次数:" + resendCount : ""));
		if (isResend.equals(IsActive.N.name()))
		{
			return;
		}
		Integer count = msgResultService.queryResendCountByPriority(Priority.MIDDLE.getPriority());
		logger.info("中优先级重发的总行数:" + count);
		if (count.intValue() <= 0)
		{
			CacheUtil.put(ParamsKey.MIDDLE_RESEND_ORDER.getKey(), IsActive.N.name());
			return;
		}
		for (int[] page : computePageCount(count))
		{
			List<ResendMsg> resendList = msgResultService.queryResendMsgList(Priority.MIDDLE.getPriority(), page[0],
					page[1]);
			if (null == resendList || resendList.size() <= 0)
			{
				continue;
			}
			for (final ResendMsg resend : resendList)
			{
				Runnable runnable = new Runnable()
				{
					@Override
					public void run()
					{
						boolean falg = false;
						try
						{
							// 短信
							if (resend.getMsgChannel() == MsgChannel.NOTE.getChannel())
							{
								NoteBean bean = new NoteBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								falg = noteSendService.resendNote(bean);
							}
							// 邮件
							if (resend.getMsgChannel() == MsgChannel.EMAIIL.getChannel())
							{
								EmailBean bean = new EmailBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setMailTitle(resend.getMsgTitle());
								bean.setSender(resend.getSender());
								falg = emailSendService.resendEmail(bean);
							}
							// 系统互动
							if (resend.getMsgChannel() == MsgChannel.DYNAMIC_SYS.getChannel())
							{
								DynamicSysMsgBean bean = new DynamicSysMsgBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgSummary(resend.getMsgSummary());
								bean.setMsgSummaryPicUrl(StringUtil.stringToArray(resend.getMsgSummaryPicUrl(), ","));
								bean.setMsgTitle(resend.getMsgTitle());
								bean.setMsgType(resend.getMsgType());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								bean.setMsgId(resend.getMsgId());
								falg = dynamicSysSendService.resendDynamicSys(bean);
							}
							// 业务互动
							if (resend.getMsgChannel() == MsgChannel.DYNAMIC_BIZ.getChannel())
							{
								DynamicBizMsgBean bean = new DynamicBizMsgBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgTitle(resend.getMsgTitle());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								bean.setMsgId(resend.getMsgId());
								bean.setMsgCode(resend.getMsgCode());
								bean.setSubMsgCode(resend.getSubMsgCode());
								falg = dynamicBizSendService.resendDynamicBiz(bean);
							}
							if (falg)
							{
								SendedMsg sended = JSONObject.parseObject(JSONObject.toJSONString(resend),
										SendedMsg.class);
								sended.setLastSendDate(DateUtil.now());
								sended.setCreatedDate(DateUtil.now());
								sended.setCreatedby("ntSystem");
								msgResultService.resendMsgSended(sended, resend.getMsgId());
							} else
							{
								if (resendCount == resend.getFailedCounts())
								{
									AbandonMsg abandon = JSONObject.parseObject(JSONObject.toJSONString(resend),
											AbandonMsg.class);
									abandon.setPriority(abandon.getPriority());
									abandon.setFailedCounts(resendCount);
									abandon.setLastSendDate(DateUtil.now());
									abandon.setCreatedDate(DateUtil.now());
									abandon.setCreatedby("ntSystem");
									msgResultService.resendMsgAbandon(abandon, resend.getMsgId());
								} else
								{
									ResendMsg newResend = new ResendMsg();
									newResend.setMsgId(resend.getMsgId());
									newResend.setFailedCounts(resend.getFailedCounts() + 1);
									newResend.setLastSendDate(DateUtil.now());
									msgResultService.modifyResendMsg(newResend);
								}
							}
						} catch (Exception ex)
						{
							logger.error("中优先级发送失败,MsgId:" + resend.getMsgId(), ex);
						}
					}
				};
				middlePriorityTaskExecutor.execute(runnable);
			}
		}
	}

	/**
	 * 低优先级重发
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:18:55
	 * @return : void
	 * @version V3.0.0
	 */
	private void lowPriorityResend()
	{
		String isResend = CacheUtil.getValue(ParamsKey.LOW_RESEND_ORDER.getKey(), String.class);
		logger.info("进入低优先级重发,重发的权限:" + isResend + (isResend.equals(IsActive.Y.name()) ? "重发次数:" + resendCount : ""));
		if (isResend.equals(IsActive.N.name()))
		{
			return;
		}
		Integer count = msgResultService.queryResendCountByPriority(Priority.LOW.getPriority());
		logger.info("低优先级重发的总行数:" + count);
		if (count.intValue() <= 0)
		{
			CacheUtil.put(ParamsKey.LOW_RESEND_ORDER.getKey(), IsActive.N.name());
			return;
		}
		for (int[] page : computePageCount(count))
		{
			List<ResendMsg> resendList = msgResultService.queryResendMsgList(Priority.LOW.getPriority(), page[0],
					page[1]);
			if (null == resendList || resendList.size() == 0)
			{
				continue;
			}
			for (final ResendMsg resend : resendList)
			{
				Runnable runnable = new Runnable()
				{
					@Override
					public void run()
					{
						boolean falg = false;
						try
						{
							// 短信
							if (resend.getMsgChannel() == MsgChannel.NOTE.getChannel())
							{
								NoteBean bean = new NoteBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								falg = noteSendService.resendNote(bean);
							}
							// 邮件
							if (resend.getMsgChannel() == MsgChannel.EMAIIL.getChannel())
							{
								EmailBean bean = new EmailBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setMailTitle(resend.getMsgTitle());
								bean.setSender(resend.getSender());
								falg = emailSendService.resendEmail(bean);
							}
							// 系统互动
							if (resend.getMsgChannel() == MsgChannel.DYNAMIC_SYS.getChannel())
							{
								DynamicSysMsgBean bean = new DynamicSysMsgBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgSummary(resend.getMsgSummary());
								bean.setMsgSummaryPicUrl(StringUtil.stringToArray(resend.getMsgSummaryPicUrl(), ","));
								bean.setMsgTitle(resend.getMsgTitle());
								bean.setMsgType(resend.getMsgType());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								bean.setMsgId(resend.getMsgId());
								falg = dynamicSysSendService.resendDynamicSys(bean);
							}
							// 业务互动
							if (resend.getMsgChannel() == MsgChannel.DYNAMIC_BIZ.getChannel())
							{
								DynamicBizMsgBean bean = new DynamicBizMsgBean();
								bean.setMsgContent(resend.getMsgContent());
								bean.setMsgTitle(resend.getMsgTitle());
								bean.setMsgReceiver(StringUtil.stringToArray(resend.getMsgReceiver(), ","));
								bean.setSender(resend.getSender());
								bean.setMsgId(resend.getMsgId());
								bean.setMsgCode(resend.getMsgCode());
								bean.setSubMsgCode(resend.getSubMsgCode());
								falg = dynamicBizSendService.resendDynamicBiz(bean);
							}
							if (falg)
							{
								SendedMsg sended = JSONObject.parseObject(JSONObject.toJSONString(resend),
										SendedMsg.class);
								sended.setLastSendDate(DateUtil.now());
								sended.setCreatedDate(DateUtil.now());
								sended.setCreatedby("ntSystem");
								msgResultService.resendMsgSended(sended, resend.getMsgId());
							} else
							{
								if (resendCount == resend.getFailedCounts())
								{
									AbandonMsg abandon = JSONObject.parseObject(JSONObject.toJSONString(resend),
											AbandonMsg.class);
									abandon.setPriority(abandon.getPriority());
									abandon.setFailedCounts(resendCount);
									abandon.setLastSendDate(DateUtil.now());
									abandon.setCreatedDate(DateUtil.now());
									abandon.setCreatedby("ntSystem");
									msgResultService.resendMsgAbandon(abandon, resend.getMsgId());
								} else
								{
									ResendMsg newResend = new ResendMsg();
									newResend.setMsgId(resend.getMsgId());
									newResend.setFailedCounts(resend.getFailedCounts() + 1);
									newResend.setLastSendDate(DateUtil.now());
									msgResultService.modifyResendMsg(newResend);
								}
							}
						} catch (Exception ex)
						{
							logger.error("低优先级发送失败,MsgId:" + resend.getMsgId(), ex);
						}
					}
				};
				lowPriorityTaskExecutor.execute(runnable);
			}
		}
	}

	/**
	 * 组装分页参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:18:30
	 * @param count
	 *            结束行
	 * @return
	 * @return : List<int[]>
	 * @version V3.0.0
	 */
	private List<int[]> computePageCount(int count)
	{
		List<int[]> pageParam = new ArrayList<int[]>();
		if (count > pageSize)
		{
			int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
			for (int i = 0; i < pages; i++)
			{
				pageParam.add(new int[] { pageSize * i, pageSize });
			}
		} else
		{
			pageParam.add(new int[] { 0, count });
		}
		return pageParam;
	}
}
