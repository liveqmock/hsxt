/**
 * 
 */
package com.gy.hsxt.access.web.person.services.common;

import java.util.Map;

import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**   
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.common
 * @className     : IPubParamService.java
 * @description   : 
 * @author        : LiZhi Peter
 * @createDate    : 2015-11-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
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
    
    /**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @return
     * @throws HsException
     */
	public Map<String, String> findImageDocList() throws HsException;
	
	/**
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @return
     * @throws HsException
     */
    public Map<String, BizDoc> findBizDocList() throws HsException;
    
    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @return
     * @throws HsException
     */
    public Map<String, NormalDoc> findNormalDocList() throws HsException;

}
