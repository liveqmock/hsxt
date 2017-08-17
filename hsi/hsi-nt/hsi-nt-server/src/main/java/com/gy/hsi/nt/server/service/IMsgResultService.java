package com.gy.hsi.nt.server.service;

import java.util.List;

import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.entity.result.AbandonMsg;
import com.gy.hsi.nt.server.entity.result.ResendMsg;
import com.gy.hsi.nt.server.entity.result.SendedMsg;

/**
 * 消息结果service层接口
 * 
 * @Package: com.gy.hsi.nt.server.service
 * @ClassName: IMsgResultService
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午5:24:43
 * @company: gyist
 * @version V3.0.0
 */
public interface IMsgResultService {

	/**
	 * 增加成功消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:24:50
	 * @param sended
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void saveSendedMsg(SendedMsg sended) throws NoticeException;

	/**
	 * 删除成功消息彻底失败
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:24:59
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeSendMsg() throws NoticeException;

	/**
	 * 增加彻底失败的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:06
	 * @param abandon
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void saveAbandonMsg(AbandonMsg abandon) throws NoticeException;

	/**
	 * 增加重发的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:14
	 * @param resend
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void saveResendMsg(ResendMsg resend) throws NoticeException;

	/**
	 * 删除重发消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:21
	 * @param msgId
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeResendMsg(String msgId) throws NoticeException;

	/**
	 * 修改重发消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:27
	 * @param resend
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyResendMsg(ResendMsg resend) throws NoticeException;

	/**
	 * 查询可以重发的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:33
	 * @param priority
	 * @param startCount
	 * @param endCount
	 * @return
	 * @return : List<ResendMsg>
	 * @version V3.0.0
	 */
	public List<ResendMsg> queryResendMsgList(int priority, int startCount, int endCount);

	/**
	 * 重发成功
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:42
	 * @param sended
	 * @param msgId
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void resendMsgSended(SendedMsg sended, String msgId) throws NoticeException;

	/**
	 * 重发到最大次数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:49
	 * @param abandon
	 * @param msgId
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void resendMsgAbandon(AbandonMsg abandon, String msgId) throws NoticeException;

	/**
	 * 查询此优先级的待重发的数量
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:25:59
	 * @param priority
	 * @return
	 * @return : Integer
	 * @version V3.0.0
	 */
	public Integer queryResendCountByPriority(int priority);

}
