package com.gy.hsi.nt.api.service;

import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.beans.SelfDynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.SelfEmailBean;
import com.gy.hsi.nt.api.beans.SelfNoteBean;
import com.gy.hsi.nt.api.exception.NoticeException;

/**
 * 发送消息接口
 * 
 * @Package: com.gy.hsi.nt.api.service
 * @ClassName: INtService
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 上午11:49:06
 * @company: gyist
 * @version V3.0.0
 */
public interface INtService {

	/**
	 * 发送短信
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:43:38
	 * @param bean
	 *            短信参数
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendNote(NoteBean bean) throws NoticeException;

	/**
	 * 发送自定义内容短信
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月7日 下午5:24:58
	 * @param bean
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendSelfNote(SelfNoteBean bean) throws NoticeException;

	/**
	 * 发送邮件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:44:20
	 * @param bean
	 *            邮件参数
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendEmail(EmailBean bean) throws NoticeException;

	/**
	 * 发送自定义内容邮件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月6日 下午4:02:49
	 * @param bean
	 *            邮件参数
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendSelfEmail(SelfEmailBean bean) throws NoticeException;

	/**
	 * 互动推送业务消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:45:31
	 * @param bean
	 *            互动业务参数
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendDynamicBiz(DynamicBizMsgBean bean) throws NoticeException;

	/**
	 * 发送自定义内容互动业务消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月7日 下午5:26:01
	 * @param bean
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendSelfDynamicBiz(SelfDynamicBizMsgBean bean) throws NoticeException;

	/**
	 * 互动推送系统消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:46:12
	 * @param bean
	 *            互生系统参数
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendDynamicSys(DynamicSysMsgBean bean) throws NoticeException;

}
