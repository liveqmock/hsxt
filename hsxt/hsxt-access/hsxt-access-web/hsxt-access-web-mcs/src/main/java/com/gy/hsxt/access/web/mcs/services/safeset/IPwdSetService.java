/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.safeset;

import java.util.Map;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.safeset.LoginPasswordBean;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.company.services.safeSet
 * @ClassName: IPwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-30 下午3:36:08
 * @version V1.0
 */
public interface IPwdSetService extends IBaseService {

    /**
     * 登录密码修改
     * @param lpb
     */
    public void updateLoginPassword(LoginPasswordBean lpb) throws HsException;

    /**
     * 获取预留信息是否设置
     * @param companyBase
     * @return
     */
    public Map<String, Object> getIsSafeSet(MCSBase mcsBase);
}
