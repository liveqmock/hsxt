package com.gy.hsi.nt.server.util;

import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;

/**
 * 
 * @className:EmailUtil
 * @author:likui
 * @date:2015年7月29日
 * @desc:发送邮件工具类型
 * @company:gyist
 */
public class EmailUtil {

	private static final Logger logger = Logger.getLogger(EmailUtil.class);

	/**
	 * 邮件服务
	 */
	private static String server;
	/**
	 * 服务邮箱
	 */
	private static String serverUserEmail;
	/**
	 * 用户名
	 */
	private static String serverUserName;
	/**
	 * 密码
	 */
	private static String serverUserPassword;

	private static String emailEncoding = "UTF-8";

	private static EmailUtil emailUtil = null;

	private EmailUtil()
	{

	}

	/**
	 * 获取对象
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:26:01
	 * @return
	 * @return : EmailUtil
	 * @version V3.0.0
	 */
	public synchronized static EmailUtil newInstance() throws NoticeException
	{
		try
		{
			if (null == emailUtil)
			{
				emailUtil = new EmailUtil();
				init();
			}
		} catch (NoticeException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		return emailUtil;
	}

	/**
	 * 初始化邮箱参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:26:09
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	private static void init() throws NoticeException
	{
		Map<String, String> param = CacheUtil.getConfigCache();
		if (null == param)
		{
			throw new NoticeException("缓存中邮件服务器参数没有配置，请先配置!");
		}
		server = param.get(ParamsKey.EMAIL_SERVER.getKey());
		serverUserEmail = param.get(ParamsKey.EMAIL_SMTP.getKey());
		serverUserName = param.get(ParamsKey.EMAIL_USERNAME.getKey());
		serverUserPassword = param.get(ParamsKey.EMAIL_PASSWORD.getKey());
	}

	/**
	 * 发送邮件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:31:44
	 * @param toName
	 * @param subject
	 * @param msg
	 * @param toEmail
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public synchronized void sendSimpleEmail(String toName, String subject, String msg, String[] toEmail)
			throws NoticeException
	{
		try
		{
			SimpleEmail email = new SimpleEmail();
			// 邮件服务器
			email.setHostName(server);
			// 登陆邮件服务器的用户名和密码
			email.setAuthentication(serverUserName, serverUserPassword);
			// 设置字符集为UTF-8
			email.setCharset(emailEncoding);
			// 主題
			email.setSubject(subject);
			// 內容
			email.setMsg(msg);
			// 发送人
			email.setFrom(serverUserEmail);
			// 接收人
			for (String em : toEmail)
			{
				email.addTo(em);
			}
			email.send();
		} catch (Exception ex)
		{
			logger.error("execute sendSimpleEmail method error", ex);
			throw new NoticeException("execute sendSimpleEmail method error!");
		}
	}

	/**
	 * 发送附件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:25:46
	 * @param toName
	 * @param attachmentPath
	 * @param subject
	 * @param msg
	 * @param toEmail
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public synchronized void sendMultiPartEmail(String toName, String attachmentPath, String subject, String msg,
			String[] toEmail) throws NoticeException
	{
		String attachmentText = FilenameUtils.getName(attachmentPath);
		try
		{
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName(server);
			email.setAuthentication(serverUserEmail, serverUserPassword);
			email.setCharset(emailEncoding);
			for (String em : toEmail)
			{
				email.addTo(em);
			}
			email.setFrom(serverUserEmail, serverUserName);
			email.setSubject(subject);
			email.setMsg(msg);
			// 附件处理，传附件路径参数
			if (StringUtils.isNotBlank(StringUtils.trimToEmpty(attachmentPath)))
			{
				EmailAttachment emailAttachment = new EmailAttachment();
				emailAttachment.setPath(attachmentPath);
				emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
				emailAttachment.setDescription(attachmentText);
				emailAttachment.setName(MimeUtility.encodeText(attachmentText));
				email.attach(emailAttachment);
			}
			email.send();
		} catch (Exception e)
		{
			logger.error("apache sendMultiPartEmail error", e);
			throw new NoticeException("execute sendMultiPartEmail method error!");
		}
	}

	/**
	 * (html形式发送)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:33:02
	 * @param toName
	 * @param attachmentPath
	 * @param subject
	 * @param msg
	 * @param toEmail
	 * @throws NoticeException
	 * @return : void
	 * @version V3.0.0
	 */
	public synchronized void sendHtmlEmail(String toName, String attachmentPath, String subject, String msg,
			String[] toEmail) throws NoticeException
	{
		String attachmentText = FilenameUtils.getName(attachmentPath);
		try
		{
			HtmlEmail email = new HtmlEmail();
			email.setHostName(server);
			email.setAuthentication(serverUserName, serverUserPassword);
			email.setCharset(emailEncoding);
			email.setFrom(serverUserEmail, serverUserName);
			for (String em : toEmail)
			{
				email.addTo(em);
			}
			email.setSubject(subject);
			// 附件处理，传附件路径参数
			if (StringUtils.isNotBlank(StringUtils.trimToEmpty(attachmentPath)))
			{
				EmailAttachment emailAttachment = new EmailAttachment();
				emailAttachment.setPath(attachmentPath);
				emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
				emailAttachment.setDescription(attachmentText);
				emailAttachment.setName(MimeUtility.encodeText(attachmentText));
				email.attach(emailAttachment);
			}
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception ex)
		{
			logger.error("apache sendHtmlEmail error", ex);
			throw new NoticeException("execute sendHtmlEmail method error!");
		}
	}
}
