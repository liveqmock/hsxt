/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitQueryParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitStatus;
import com.gy.hsxt.bs.entstatus.bean.CancelAccountParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.mapper
 * @ClassName: MemberQuitMapper
 * @Description: 成员企业销户Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:56:43
 * @version V1.0
 */
@Repository
public interface MemberQuitMapper {

    /**
     * 创建成员企业销户申请
     * 
     * @param memberQuit
     *            成员企业销户申请
     */
    int createMemberQuit(MemberQuit memberQuit);

    /**
     * 查询成员企业销户申请
     * 
     * @param memberQuitQueryParam
     *            查询参数
     * @return 成员企业销户申请列表
     */
    List<MemberQuitBaseInfo> queryMemberQuitListPage(MemberQuitQueryParam memberQuitQueryParam);

    /**
     * 关联工单查询成员企业销户申请
     * 
     * @param memberQuitQueryParam
     *            查询参数
     * @return 成员企业销户申请列表
     */
    List<MemberQuitBaseInfo> queryMemberQuit4ApprListPage(MemberQuitQueryParam memberQuitQueryParam);

    /**
     * 更新成员企业销户状态
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param optCustId
     *            操作员
     * @param reportFile
     *            企业实地考察报告
     * @param statementFile
     *            企业双方声明文件
     */
    void updateQuitStatus(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("optCustId") String optCustId, @Param("reportFile") String reportFile,
            @Param("statementFile") String statementFile);

    /**
     * 查询成员企业销户
     * 
     * @param applyId
     *            申请编号
     * @return 成员企业销户详情
     */
    MemberQuit queryMemberQuitById(String applyId);

    /**
     * 更新销户进度
     * 
     * @param applyId
     *            申请编号
     * @param progress
     *            销户进度
     * @param optCustId
     *            操作员
     */
    void updateProgress(@Param("applyId") String applyId, @Param("progress") Integer progress,
            @Param("optCustId") String optCustId);

    /**
     * 查询未完成销户的申请
     * 
     * @return 未完成销户的申请列表
     */
    List<CancelAccountParam> getUnCompletedCancelAcList();

    /**
     * 根据企业客户号查询成员企业注销状态
     * 
     * @param entCustId
     *            企业客户号
     * @return 成员企业注销状态信息
     */
    MemberQuitStatus queryMemberQuitStatus(String entCustId);

}
