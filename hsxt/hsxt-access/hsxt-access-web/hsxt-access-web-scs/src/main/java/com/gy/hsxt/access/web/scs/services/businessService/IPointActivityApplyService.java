/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.businessService;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 成员企业积分活动申请（停止/参与）审批接口
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.businessService  
 * @ClassName: IPointActivityApplyService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-3 下午6:36:18 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public interface IPointActivityApplyService extends IBaseService {

    /**
     * 查询活动申请详情（包括停止申请和参与申请）
     * @param applyId 申请id
     * @return
     * @throws HsException
     */
    public Map<String,Object> findActivityApplyDetail(String applyId) throws HsException;

    /**
     * 积分活动申请（停止/参与）审批
     * @param param 审批内容（包括申请编号、审批员客户编号、是否通过、审批意见）
     * @throws HsException
     */
    public void pointActivityAppr(ApprParam param) throws HsException;
}
