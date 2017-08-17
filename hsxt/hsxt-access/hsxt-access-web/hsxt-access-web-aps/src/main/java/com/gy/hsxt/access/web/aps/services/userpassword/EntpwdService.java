package com.gy.hsxt.access.web.aps.services.userpassword;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword
 * @ClassName: EntpwdService
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:26:19
 * @version V3.0
 */
public interface EntpwdService extends IBaseService {

	public void resetLogPwdForOperatorByReChecker(String regionalResNo,
			String userName, String loginPwd, String secretKey, String resetEntResNo,String operCustId)
			throws HsException;
}
