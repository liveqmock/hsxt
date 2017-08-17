/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;


import java.util.Map;

import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameBaseInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 个人（消费者）实名认证审批
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.infomanage  
 * @ClassName: PerRealNameIdentificService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-10 下午12:30:51 
 * @version V3.0.0
 */
public interface PerRealNameIdentificService extends InfoManageService {

    /**
     * 个人（消费者）实名认证审批
     * @param apprParam
     * @throws HsException
     */
    public void apprPerRealnameAuth(ApprParam apprParam) throws HsException;
    
    /**
     * 个人（消费者）实名认证复核
     * @param apprParam
     * @throws HsException
     */
    public void reviewApprPerRealnameAuth (ApprParam apprParam) throws HsException;
    
    /**
     * 通过申请编号查询实名认证业务办理信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    public PerRealnameAuth queryPerRealnameAuthById(String applyId) throws HsException;
    
    /**
     * 查询实名认证 办理状态列表信息
     * @param applyId 申请编号
     * @param Page
     * @return
     * @throws HsException
     */
    public PageData<OptHisInfo> queryPerRealnameAuthHis(String applyId, Page page) throws HsException;
    
    /**
     * 修改个人（消费者）实名认证信息
     * @param perRealnameAuth
     * @throws HsException
     */
    public void modifyPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException;
    
    /**
     * 分页查询消费者实名认证审批记录
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
	public PageData<PerRealnameBaseInfo> pageRealnameAuthRecord(Map filterMap, Map sortMap, Page page) throws HsException;
	
	/**
	 * 分页查询企业实名认证审批记录
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<EntRealnameBaseInfo> pageEntRealnameAuthRecord(Map filterMap, Map sortMap, Page page) throws HsException;
    
}
