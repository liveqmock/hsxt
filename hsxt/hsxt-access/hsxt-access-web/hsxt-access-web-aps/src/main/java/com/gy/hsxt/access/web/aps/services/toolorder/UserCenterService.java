/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 用户中心鉴权
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder  
 * @ClassName: UserCenterService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-20 下午2:55:08 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface UserCenterService extends IBaseService {

    /**
     * 双签
     * @param userName  用户名
     * @param password  密码
     * @param entHsResNo 企业互生号
     * @param secretKey AES秘钥（随机登录token）
     * @throws HsException
     */
    public void doubleSign(String userName,String password,String entHsResNo,String secretKey) throws HsException;
    
    /**
     * 获取AES随机登录秘钥
     * @return AES随机登录秘钥
     * @throws HsException
     */
    public String getsecretKey() throws HsException;
    
    /**
     * 获取随机token
     * @param custId
     * @return
     * @throws HsException
     */
    public String getRandomToken(String custId) throws HsException;
    
    /**
     * 
     * 方法名称：查询用户拥有的角色ID
     * 方法描述：查询用户拥有的角色ID
     * @param operCustId 操作员ID
     * @return
     */
    public String[] getRoleIds(String operCustId)throws HsException;
}
