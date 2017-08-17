/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.companyInfo;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/***
 * 企业重要信息的操作
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.companyInfo  
 * @ClassName: IEntBaseInfoService 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-11-17 下午12:03:22 
 * @version V1.0
 */
public interface IEntMainInfoService {
    
    /***
     * 查找企业的重要信息
     * @param entCustId
     * @return
     */
    public AsEntMainInfo findEntMainInfo(String entCustId);
    
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
     * 更新授权委托书
     * @param info
     */
    public void updateAuthProxyFile(String authProxyFile ,String operator,String entCustId);
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
