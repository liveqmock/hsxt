/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.safetyset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsReserveInfoService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***
 * 预留信息管理服务类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.safetyset
 * @ClassName: ReserveInfoService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-10 下午3:38:07
 * @version V1.0
 */
@Service(value = "reserveInfoService")
public class ReserveInfoService extends BaseServiceImpl implements IReserveInfoService {

    // 预留信息服务dubbo接口
    @Autowired private IUCAsReserveInfoService ucAsReserveInfoService;

    /**
     * @param custId
     * @param userType
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.safetyset.IReserveInfoService#findReserveInfoByCustId(java.lang.String,
     *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
     */
    @Override
    public String findReserveInfoByCustId(String custId, String userType) throws HsException {
        return this.ucAsReserveInfoService.findReserveInfoByCustId(custId, userType);
    }

    /**  
     * @param custId
     * @param userType
     * @param reserveInfo
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IReserveInfoService#saveReserveInfoByCustId(java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum, java.lang.String) 
     */
    @Override
    public void saveReserveInfoByCustId(String custId, String userType, String reserveInfo) throws HsException {
       this.ucAsReserveInfoService.setReserveInfo(custId, reserveInfo, userType);
        
    }

}
