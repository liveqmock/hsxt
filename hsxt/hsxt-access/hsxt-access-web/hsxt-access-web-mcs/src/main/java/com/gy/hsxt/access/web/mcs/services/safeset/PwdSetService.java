/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.safeset;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.safeset.LoginPasswordBean;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.pwd.IBSTransPwdService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.company.services.safeSet
 * @ClassName: PwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-30 下午3:35:53
 * @version V1.0
 */
@Service(value = "pwdSetService")
public class PwdSetService extends BaseServiceImpl implements IPwdSetService {

    @Resource
    private IUCAsPwdService ucAsPwdService;

    @Resource
    private IUCAsEntService iuCAsEntService;

    @Resource
    private ISecuritySetService securitySetService;

    @Resource
    private IBSTransPwdService bsTransPwdService;
    
    /**
     * 修改登录密码
     * 
     * @param lpb
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#updateLoginPassword(com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean)
     */
    @Override
    public void updateLoginPassword(LoginPasswordBean lpb) throws HsException {
        // 1、验证新密相等
        if (!lpb.validateNewPasswordEquals())
        {
            throw new HsException(RespCode.PW_LOGIN_PWD_NOT_EQUALS.getCode(), RespCode.PW_LOGIN_PWD_NOT_EQUALS
                    .getDesc());
        }
        // 2、修改登录 密码
        ucAsPwdService.updateLoginPwd(lpb.getCustId(), UserTypeEnum.OPERATOR.getType(), lpb.getOldPassword(), lpb
                .getNewPassword(), lpb.getRandomToken());

    }

   
    /**
     * 获取交易密码和密保问题已设置
     * 
     * @param companyBase
     * @return
     * @see com.gy.hsxt.access.web.company.services.safeSet.IPwdSetService#getIsSafeSet(com.gy.hsxt.access.web.bean.CompanyBase)
     */
    @Override
    public Map<String, Object> getIsSafeSet(MCSBase mcsBase) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        // 1、获取预留信息
        String reserveInfo = securitySetService.getReserveInfo(mcsBase);
        // 2、拼接返回map
        retMap.put("reserveInfo", reserveInfo);
        return retMap;
    }
}
