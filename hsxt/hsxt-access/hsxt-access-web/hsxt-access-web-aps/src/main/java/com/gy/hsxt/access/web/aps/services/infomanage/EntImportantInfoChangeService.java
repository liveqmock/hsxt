/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;


import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 地区平台查询企业重要信息变更 、审批
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.infomanage  
 * @ClassName: EntImportantInfoChangeService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-10 下午5:28:29 
 * @version V3.0.0
 */
public interface EntImportantInfoChangeService extends InfoManageService {

    /**
     * 通过申请编号查询企业重要信息变更详情
     * @param applyId
     * @return
     * @throws HsException
     */
    public EntChangeInfo queryEntChangeById(String applyId) throws HsException;
    
    /**
     * 审批重要信息变更
     * @param apprParam
     * @throws HsException
     */
    public void apprEntChange(ApprParam apprParam) throws HsException;
    
    /**
     * 审批复核重要信息变更
     * @param apprParam
     * @throws HsException
     */
    public void reviewApprEntChange(ApprParam apprParam) throws HsException;
    
    /**
     * 查看企业重要信息变更办理状态
     * @param applyId
     * @param Page
     * @return
     * @throws HsException
     */
    public PageData<OptHisInfo> queryEntChangeHis(String applyId, Page page) throws HsException;
    
    /**
     * 修改企业重要信息变更申请
     * @param entChangeInfo
     * @throws HsException
     */
    public void modifyEntChange (EntChangeInfo entChangeInfo) throws HsException;
    
}
