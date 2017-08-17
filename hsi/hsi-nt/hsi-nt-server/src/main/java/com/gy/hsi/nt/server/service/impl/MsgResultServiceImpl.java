package com.gy.hsi.nt.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.nt.api.common.DateUtil;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.entity.result.AbandonMsg;
import com.gy.hsi.nt.server.entity.result.ResendMsg;
import com.gy.hsi.nt.server.entity.result.SendedMsg;
import com.gy.hsi.nt.server.mapper.MsgResultMapper;
import com.gy.hsi.nt.server.service.IMsgResultService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;

/**
 * 消息结果service层接口实现类
 * 
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: MsgResultServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午5:26:23
 * @company: gyist
 * @version V3.0.0
 */
@Service("msgResultService")
public class MsgResultServiceImpl implements IMsgResultService {

	private static final Logger logger = Logger.getLogger(MsgResultServiceImpl.class);

	/**
	 * 消息结果Mapper
	 */
	@Autowired
	MsgResultMapper msgResultMapper;

	/**
	 * 增加成功消息
	 * 
	 * @Description:
	 * @param sended
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSendedMsg(SendedMsg sended) throws NoticeException
	{
		try
		{
			msgResultMapper.insertSendedMsg(sended);
		} catch (Exception ex)
		{
			logger.error("新增成功消息异常", ex);
		}
	}

	/**
	 * 删除成功消息彻底失败
	 * 
	 * @Description:
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeSendMsg() throws NoticeException
	{
		try
		{
			String date = DateUtil.dateTimeToString(DateUtil.addMonth(new Date(), -1));
			msgResultMapper.deleteSendedMsg(date);
			msgResultMapper.deleteAbandonMsg(date);
		} catch (Exception ex)
		{
			logger.error("删除成功消息彻底失败异常", ex);
		}
	}

	/**
	 * 增加彻底失败的消息
	 * 
	 * @Description:
	 * @param abandon
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAbandonMsg(AbandonMsg abandon) throws NoticeException
	{
		try
		{
			msgResultMapper.insertAbandonMsg(abandon);
		} catch (Exception ex)
		{
			logger.error("增加彻底失败的消息异常", ex);
		}
	}

	/**
	 * 增加重发的消息
	 * 
	 * @Description:
	 * @param resend
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveResendMsg(ResendMsg resend) throws NoticeException
	{
		try
		{
			msgResultMapper.insertResendMsg(resend);
		} catch (Exception ex)
		{
			logger.error("增加重发的消息异常", ex);
		}
	}

	/**
	 * 删除重发消息
	 * 
	 * @Description:
	 * @param msgId
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeResendMsg(String msgId) throws NoticeException
	{
		msgResultMapper.deleteResendMsg(msgId);
	}

	/**
	 * 修改重发消息
	 * 
	 * @Description:
	 * @param resend
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void modifyResendMsg(ResendMsg resend) throws NoticeException
	{
		try
		{
			msgResultMapper.updateResendMsg(resend);
		} catch (Exception ex)
		{
			logger.error("修改重发消息异常", ex);
		}
	}

	/**
	 * 重发成功
	 * 
	 * @Description:
	 * @param sended
	 * @param msgId
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void resendMsgSended(SendedMsg sended, String msgId) throws NoticeException
	{
		try
		{
			msgResultMapper.insertSendedMsg(sended);
			msgResultMapper.deleteResendMsg(msgId);
		} catch (Exception ex)
		{
			logger.error("重发成功消息异常", ex);
		}
	}

	/**
	 * 重发到最大次数
	 * 
	 * @Description:
	 * @param abandon
	 * @param msgId
	 * @throws NoticeException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void resendMsgAbandon(AbandonMsg abandon, String msgId) throws NoticeException
	{
		try
		{
			msgResultMapper.insertAbandonMsg(abandon);
			msgResultMapper.deleteResendMsg(msgId);
		} catch (Exception ex)
		{
			logger.error("重发到最大次数异常", ex);
		}
	}

	/**
	 * 查询可以重发的消息
	 * 
	 * @Description:
	 * @param priority
	 * @param startCount
	 * @param endCount
	 * @return
	 */
	@Override
	public List<ResendMsg> queryResendMsgList(int priority, int startCount, int endCount)
	{
		String defaultValue = "5,10,15,30,60";
		String[] times = null;
		try
		{
			Map<String, String> cahceParam = CacheUtil.getConfigCache();
			Map<String, Object> param = new HashMap<String, Object>();
			if (null != cahceParam)
			{
				times = cahceParam.get(ParamsKey.RESEND_TIME.getKey()).split(",");
			} else
			{
				times = defaultValue.split(",");
			}
			for (String time : times)
			{
				param.put("min" + time, DateUtil.getDateStringMaxSecond(DateUtil.addMinutes(-Integer.parseInt(time))));
			}
			param.put("priority", priority);
			param.put("startCount", startCount);
			param.put("endCount", endCount);
			return msgResultMapper.selectResendMsgList(param);
		} catch (Exception e)
		{
			logger.error("查询可以重发数据异常,优先级:" + priority, e);
		}
		return null;
	}

	/**
	 * 查询此优先级的待重发的数量
	 * 
	 * @Description:
	 * @param priority
	 * @return
	 */
	@Override
	public Integer queryResendCountByPriority(int priority)
	{
		return msgResultMapper.selectResendCountByPriority(priority);
	}
}
