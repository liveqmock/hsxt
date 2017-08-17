package com.gy.hsi.nt.server.service;

import com.gy.hsi.nt.api.beans.EmailBean;

/**
 * 邮件处理接口
 * 
 * @Package: com.gy.hsi.nt.server.service
 * @ClassName: IEmailSendService
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午12:28:05
 * @company: gyist
 * @version V3.0.0
 */
public interface IEmailSendService {

	/**
	 * 发送邮件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:28:12
	 * @param email
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String ntSendEmail(EmailBean email);

	/**
	 * 失败后重发
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午12:28:19
	 * @param email
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public boolean resendEmail(EmailBean email);
}
