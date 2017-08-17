/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.safetyset;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***
 * 预留信息服务类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.safetyset
 * @ClassName: IPwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-10 下午3:29:52
 * @version V1.0
 */
@Service
public interface IReserveInfoService extends IBaseService {

    /**
     * 查询预留信息
     * @param custId 客户编号
     * @param userType 客户类型
     * @return 预留信息
     * @throws HsException
     */
    public String findReserveInfoByCustId (String custId, String userType) throws HsException;

    /**
     * 保存预留信息
     * @param custId 客户id
     * @param userType 客户类型
     * @param reserveInfo 预留信息
     * @return 
     * @throws HsException
     */
    public void saveReserveInfoByCustId (String custId, String userType,String reserveInfo) throws HsException;

}
