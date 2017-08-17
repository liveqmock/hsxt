/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.companyInfo.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.companyInfo.ISafeSetService;
import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;

@Service
public class SafeSetServiceImpl extends BaseServiceImpl implements ISafeSetService {
	 @Resource
	    private IUCAsPwdService ucAsPwdService;
    @Override
    public AsOperatorLoginResult findLoginInfo(String custId) {
            AsOperatorLoginResult info = new AsOperatorLoginResult();
            info.setLastLoginIp("225.153.52.46");
            return info ;
    }

    public void updateLoginPassword(LoginPasswordBean lpb) throws HsException {
        // 1、验证新密相等
        if (!lpb.validateNewPasswordEquals()) {
            throw new HsException(RespCode.PW_LOGIN_PWD_NOT_EQUALS);
        }

        // 2、修改登录 密码
        ucAsPwdService.updateLoginPwd(lpb.getCustId(), UserTypeEnum.OPERATOR.getType(), lpb.getOldPassword(), lpb
                .getNewPassword(), lpb.getRandomToken());

    }
}
