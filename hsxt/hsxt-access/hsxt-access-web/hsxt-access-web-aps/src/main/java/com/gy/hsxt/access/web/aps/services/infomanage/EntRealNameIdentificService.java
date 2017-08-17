/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;


import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 实名认证审批服务
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.infomanage  
 * @ClassName: EntRealNameIdentificService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-9 下午8:30:41 
 * @version V3.0.0
 */
public interface EntRealNameIdentificService extends InfoManageService {

    /**
     * 查询企业实名认证详情
     * @param applyId
     * @return
     * @throws HsException
     */
    public EntRealnameAuth queryEntRealnameAuthById(String applyId) throws HsException;
    
    /**
     * 审批企业实名认证
     * @param apprParam
     * @throws HsException
     */
    public void apprEntRealnameAuth (ApprParam apprParam) throws HsException;
    
    /**
     * 复核企业实名认证
     * @param apprParam
     * @throws HsException
     */
    public void reviewApprEntRealnameAuth (ApprParam apprParam) throws HsException;
    
    /**
     * 查询企业实名认证状态列表
     * @param applyId
     * @param Page
     * @return
     * @throws HsException
     */
    public PageData<OptHisInfo> queryEntRealnameAuthHis(String applyId, Page page) throws HsException;
    
    /**
     * 修改企业实名认证
     * @param entRealnameAuth 修改内容对象
     * @throws HsException
     */
    public void modifyEntRealnameAuth (EntRealnameAuth entRealnameAuth) throws HsException;
    
}
