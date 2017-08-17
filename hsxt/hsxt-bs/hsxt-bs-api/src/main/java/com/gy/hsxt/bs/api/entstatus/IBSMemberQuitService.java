/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.entstatus;

import java.util.Map;

import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitApprParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitQueryParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitStatus;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.entstatus
 * @ClassName: IBSMemberQuitService
 * @Description: 成员企业注销管理接口
 * 
 * @author: xiaofl
 * @date: 2015-9-7 下午3:47:17
 * @version V1.0
 */
public interface IBSMemberQuitService {

    /**
     * 申请注销
     * 
     * @param memberQuit
     *            成员企业注销申请 必填
     * @throws HsException
     */
    public void createMemberQuit(MemberQuit memberQuit) throws HsException;

    /**
     * 服务公司查询成员企业注销申请
     * 
     * @param memberQuitQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回成员企业注销列表，没有则返回null
     * @throws HsException
     */
    public PageData<MemberQuitBaseInfo> serviceQueryMemberQuit(MemberQuitQueryParam memberQuitQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台查询成员企业注销审批
     * 
     * @param memberQuitQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回成员企业注销列表，没有则返回null
     * @throws HsException
     */
    public PageData<MemberQuitBaseInfo> platQueryMemberQuit4Appr(MemberQuitQueryParam memberQuitQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台成员企业注销审批查询
     * 
     * @param memberQuitQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回成员企业注销列表，没有则返回null
     * @throws HsException
     */
    public PageData<MemberQuitBaseInfo> platQueryMemberQuit(MemberQuitQueryParam memberQuitQueryParam, Page page)
            throws HsException;

    /**
     * 查询注销申请详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回注销申请详情，没有则返回null Key Value MEMBER_QUIT MemberQuit SER_APPR_HIS
     *         MemberQuitApprHis PLAT_APPR_HIS MemberQuitApprHis PLAT_REVIEW_HIS
     *         MemberQuitApprHis
     */
    public Map<String, Object> queryMemberQuitById(String applyId);

    /**
     * 服务公司审批注销申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void serviceApprMemberQuit(MemberQuitApprParam apprParam) throws HsException;

    /**
     * 地区平台初审注销申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void platApprMemberQuit(ApprParam apprParam) throws HsException;

    /**
     * 地区平台复核注销申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void platReviewApprMemberQuit(ApprParam apprParam) throws HsException;

    /**
     * 根据企业客户号查询成员企业注销状态
     * 
     * @param entCustId
     *            企业客户号
     * @return 成员企业注销状态信息
     */
    public MemberQuitStatus queryMemberQuitStatus(String entCustId);

    /**
     * 查询成员企业销户办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页信息
     * @return 办理状态信息列表
     * @throws HsException
     */
    PageData<OptHisInfo> queryMemberQuitHis(String applyId, Page page) throws HsException;

}
