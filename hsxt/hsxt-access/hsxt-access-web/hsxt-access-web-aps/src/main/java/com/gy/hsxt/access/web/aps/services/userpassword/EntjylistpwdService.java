package com.gy.hsxt.access.web.aps.services.userpassword;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword
 * @ClassName: EntjypwdService
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:26:07
 * @version V3.0
 */
@SuppressWarnings("rawtypes")
public interface EntjylistpwdService extends IBaseService {

	public void apprResetTranPwdApply(ResetTransPwd resetTransPwd) throws HsException;

}
