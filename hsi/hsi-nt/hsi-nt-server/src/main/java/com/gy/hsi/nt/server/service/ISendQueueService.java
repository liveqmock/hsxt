package com.gy.hsi.nt.server.service;

import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.exception.NoticeException;

/**
 * 发送信息到MQ的接口
 * 
 * @Package: com.gy.hsi.nt.server.service
 * @ClassName: ISendQueueService
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午12:01:33
 * @company: gyist
 * @version V3.0.0
 */
public interface ISendQueueService {

	/**
	 * 发送短信到MQ队列
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:02:09
	 * @param bean
	 *            短信实体
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void mqSendNote(NoteBean bean) throws NoticeException;

	/**
	 * 发送邮件到MQ队列
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:02:55
	 * @param bean
	 *            邮件实体
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void mqSendEmail(EmailBean bean) throws NoticeException;

	/**
	 * 发送系统互动消息到MQ队列
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:03:15
	 * @param bean
	 *            互生消息实体
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void mqSendDynamicSys(DynamicSysMsgBean bean) throws NoticeException;

	/**
	 * 发送业务互动消息到MQ队列
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午4:44:01
	 * @param bean
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void mqSendDynamicBiz(DynamicBizMsgBean bean) throws NoticeException;
}
