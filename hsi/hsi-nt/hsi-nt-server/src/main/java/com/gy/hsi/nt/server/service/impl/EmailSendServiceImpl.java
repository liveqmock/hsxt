package com.gy.hsi.nt.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gy.hsi.nt.api.beans.EmailBean;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.service.IEmailSendService;
import com.gy.hsi.nt.server.util.EmailUtil;

/**
 * 邮件处理实现类
 * 
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: EmailSendServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午12:26:16
 * @company: gyist
 * @version V3.0.0
 */
@Service("emailSendService")
public class EmailSendServiceImpl implements IEmailSendService {

	private static final Logger logger = Logger.getLogger(EmailSendServiceImpl.class);

	@Override
	public String ntSendEmail(EmailBean email)
	{
		try
		{
			EmailUtil.newInstance().sendHtmlEmail("hsxt", null, email.getMailTitle(), email.getMsgContent(),
					email.getMsgReceiver());
			logger.info("邮件发送成功");
			return RespCode.SUCCESS.name();
		} catch (NoticeException ex)
		{
			logger.error("邮件发送异常," + ex.getMessage(), ex);
		}
		return RespCode.ERROR.name();
	}

	@Override
	public boolean resendEmail(EmailBean email)
	{
		try
		{
			EmailUtil.newInstance().sendHtmlEmail("hsxt", null, email.getMailTitle(), email.getMsgContent(),
					email.getMsgReceiver());
			logger.info("邮件重发成功");
			return true;
		} catch (NoticeException ex)
		{
			logger.error("邮件重发异常," + ex.getMessage(), ex);
		}
		return false;
	}
}
