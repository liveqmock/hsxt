/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.companyInfo;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.common.exception.HsException;

/***
 * 实名认证
 * 
 * @Package: com.gy.hsxt.access.web.business.systemInfo.service  
 * @ClassName: IRealNameAuthService 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-9-29 下午7:16:47 
 * @version V3.0.0
 */
public interface IRealNameAuthService extends IBaseService{
	
    /***
     * 提交认证数据
     * @param obj
     * @throws HsException
     */
    public void submit(EntRealnameAuth rnAuth) throws HsException;
    
    
    
    /***
     *查询申请单
     * @param pageId 页面的唯一标识
     * @return
     * @throws HsException
     */
    public EntRealnameAuth findApplyOrder(String entCustId) throws HsException;
    
    
}
