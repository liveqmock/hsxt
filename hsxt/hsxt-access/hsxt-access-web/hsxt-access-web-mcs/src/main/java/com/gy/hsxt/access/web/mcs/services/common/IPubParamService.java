/**
 * 
 */
package com.gy.hsxt.access.web.mcs.services.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**   
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.mcs.services.common
 * @className     : IPubParamService.java
 * @description   : 
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
public interface IPubParamService {
    
    /**
     * 
     * 方法名称：获取系统信息
     * 方法描述：获取系统配置信息
     * @return 系统信息
     * @throws HsException
     */
    public LocalInfo findSystemInfo() throws HsException;

}
