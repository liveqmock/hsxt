/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;


import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 消费者重要信息变更 审批服务
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.infomanage  
 * @ClassName: PerImportantInfoChangeService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-10 下午3:52:25 
 * @version V3.0.0
 */
public interface PerImportantInfoChangeService extends InfoManageService {

    /**
     * 通过申请编号查询个人重要信息变更详情
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    public PerChangeInfo queryPerChangeById(String applyId) throws HsException;
    
    /**
     * 审批个人重要信息变更
     * @param apprParam
     * @throws HsException
     */
    public void apprPerChange(ApprParam apprParam) throws HsException;
    
    /**
     * 审批复核个人重要信息变更
     * @param apprParam
     * @throws HsException
     */
    public void reviewApprPerChange(ApprParam apprParam) throws HsException;
    
    /**
     * 查看个人重要信息变更办理状态
     * @param applyId
     * @param Page
     * @return
     * @throws HsException
     */
    public PageData<OptHisInfo> queryPerChangeHis(String applyId, Page page) throws HsException;
    
    /**
     * 修改个人消费者重要信息变更申请
     * @param perChangeInfo
     * @throws HsException
     */
    public void modifyPerChange (PerChangeInfo perChangeInfo) throws HsException;
    
}
