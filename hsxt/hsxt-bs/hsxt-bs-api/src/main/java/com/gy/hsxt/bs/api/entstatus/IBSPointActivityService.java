/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.entstatus;

import java.util.Map;

import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.bs.bean.entstatus.PointActivityBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PointActivityQueryParam;
import com.gy.hsxt.bs.bean.entstatus.PointActivityStatus;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.entstatus
 * @ClassName: IBSPointActivityService
 * @Description: 积分活动管理接口
 * 
 * @author: xiaofl
 * @date: 2015-9-8 上午11:46:50
 * @version V1.0
 */
public interface IBSPointActivityService {

    /**
     * 申请停止/参与积分活动
     * 
     * @param pointActivity
     *            积分活动信息 必填
     * @throws HsException
     */
    public void createPointActivity(PointActivity pointActivity) throws HsException;

    /**
     * 服务公司查询积分活动申请
     * 
     * @param pointActivityQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回积分活动申请列表，没有则返回null
     * @throws HsException
     */
    public PageData<PointActivityBaseInfo> serviceQueryPointActivity(PointActivityQueryParam pointActivityQueryParam,
            Page page) throws HsException;

    /**
     * 地区平台查询积分活动审批
     * 
     * @param pointActivityQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回积分活动申请列表，没有则返回null
     * @throws HsException
     */
    public PageData<PointActivityBaseInfo> platQueryPointActivity4Appr(PointActivityQueryParam pointActivityQueryParam,
            Page page) throws HsException;

    /**
     * 地区平台查询积分活动申请
     * 
     * @param pointActivityQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回积分活动申请列表，没有则返回null
     * @throws HsException
     */
    public PageData<PointActivityBaseInfo> platQueryPointActivity(PointActivityQueryParam pointActivityQueryParam,
            Page page) throws HsException;

    /**
     * 查询积分活动详情
     * 
     * @param applyId
     *            申请编号
     * @return 返回积分活动详情，没有则返回null key:pointActivity value:PointActivity key:his
     *         value:List<PointActivityHis>
     * @throws HsException
     */
    public Map<String, Object> queryPointActivityById(String applyId) throws HsException;

    /**
     * 服务公司审批积分活动申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void serviceApprPointActivity(ApprParam apprParam) throws HsException;

    /**
     * 地区平台审批积分活动申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void platApprPointActivity(ApprParam apprParam) throws HsException;

    /**
     * 地区平台复核积分活动申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void platReviewPointActivity(ApprParam apprParam) throws HsException;

    /**
     * 根据企业客户号查询积分活动状态
     * 
     * @param entCustId
     *            企业客户号
     * @return 积分活动状态信息
     */
    public PointActivityStatus queryPointActivityStatus(String entCustId);

}
