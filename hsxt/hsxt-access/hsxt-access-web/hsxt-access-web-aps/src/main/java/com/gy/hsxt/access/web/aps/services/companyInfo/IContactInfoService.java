/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.companyInfo;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 企业联系信息
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.service  
 * @ClassName: IContactInfoService 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-9-29 下午5:57:06 
 * @version V3.0.0
 */
public interface IContactInfoService extends IBaseService{
	/**
     * 校验邮件
     * @param custId  客户号
     * @param randomToken 邮箱验证token
     * @param email 邮箱地址
     * @param userType 用户类型
     * @return
     * @throws HsException
     */
    public void checkEmailCode(String param) throws HsException;
	
	
	/**
	 * 方法名称：发送验证邮件
	 * 方法描述： 发送验证邮件
	 * @param email
	 * @param userName
	 * @param entResNo
	 * @param userType
	 */
	public void validEmail(String email, String userName, String entResNo, String userType) throws HsException;
}
