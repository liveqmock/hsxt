/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.bs.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.UserInfoVo;

/**
 * Dubbo demo 接口类
 * 
 * @Package: com.gy.hsxt.dubbo.api  
 * @ClassName: IUserService 
 * @Description: TODO
 *
 * @author: guiyi147 
 * @date: 2015-9-18 下午8:20:24 
 * @version V1.0
 */
public interface IUCUserService {

    /**
     * 修改用户信息
     * @param user 用户信息，必须填充用户名，年龄 
     * @throws HsException_bak   异常处理类
     */
    public void updateUser(UserInfoVo user) throws HsException;
}
