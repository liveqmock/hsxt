package com.gy.hsxt.access.web.aps.services.userpassword;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword
 * @ClassName: ConsumerPwdService
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:25:46
 * @version V3.0
 */
public interface ConsumerPwdService extends IBaseService {

	public void resetLogPwdForCarderByReChecker(String regionalResNo,
			String userName, String loginPwd, String secretKey, String perCustId,String operCustId)
			throws HsException;

	public void resetTradePwdForCarderByReChecker(String regionalResNo,
			String userName, String loginPwd, String secretKey, String perCustId,String operCustId)
			throws HsException;
}
