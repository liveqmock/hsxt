/**
 * 
 */
package com.gy.hsxt.access.web.company.services.common;

import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**   
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.common
 * @className     : IPubParamService.java
 * @description   : 
 * @author        : maocy
 * @createDate    : 2015-11-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IPubParamService {
    
    /**
     * 方法名称：获取本平台详细信息
     * 方法描述：获取系统配置信息
     * @return 系统信息
     * @throws HsException
     */
    public LocalInfo findSystemInfo() throws HsException;
    
   

    /**
     * 取随机token 
     * 客户号为空    代表未登录的获取，
     * 客户号不为空    用户已登录使用
     * @param custId 客户号 
     * @return
     */
    public String getRandomToken(String custId);

}
