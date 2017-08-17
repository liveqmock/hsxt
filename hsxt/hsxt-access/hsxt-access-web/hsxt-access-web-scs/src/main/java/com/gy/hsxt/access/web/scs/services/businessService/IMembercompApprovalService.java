/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.businessService;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitApprParam;

/**
 * 服务公司管理成员企业资格注销申请  相关操作服务接口
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.businessService  
 * @ClassName: IMembercompApprovalService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-3 下午5:33:15 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface IMembercompApprovalService extends IBaseService {
    
    /**
     * 根据申请id查看成员企业注销申请的详细信息
     * @param applyId 申请id
     * @return
     * @throws HsException
     */
    public Map<String,Object> findApprovalDetail(String applyId) throws HsException;
    
    /**
     * 服务公司审批成员企业注销申请
     * @param param 审批内容（包括申请编号、审批员客户编号、是否通过、审批意见）
     * @throws HsException
     */
    public void apprMemberQuit(MemberQuitApprParam param) throws HsException;

}
