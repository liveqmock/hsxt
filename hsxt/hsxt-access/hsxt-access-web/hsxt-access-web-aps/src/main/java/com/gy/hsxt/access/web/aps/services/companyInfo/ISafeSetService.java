/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.companyInfo;

import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;

/***
 * 安全设置
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.companyInfo  
 * @ClassName: ISafeSetService 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-11-20 下午5:28:42 
 * @version V1.0
 */
public interface ISafeSetService extends IBaseService {
    /**
     * 登录信息
     * @return
     */
    public AsOperatorLoginResult findLoginInfo(String custId);
    
    /**
     * 修改密码
     * @param userName
     * @param newLoginPwd
     * @param userType
     * @param secretKey
     */
    public void updateLoginPassword(LoginPasswordBean lpb) throws HsException ;
}
