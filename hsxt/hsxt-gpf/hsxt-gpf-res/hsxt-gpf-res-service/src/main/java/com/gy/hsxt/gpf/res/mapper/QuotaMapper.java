/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gpf.res.bean.ApprParam;
import com.gy.hsxt.gpf.res.bean.IdType;
import com.gy.hsxt.gpf.res.bean.QuotaApp;
import com.gy.hsxt.gpf.res.bean.QuotaAppBaseInfo;
import com.gy.hsxt.gpf.res.bean.QuotaAppInfo;
import com.gy.hsxt.gpf.res.bean.QuotaAppParam;
import com.gy.hsxt.gpf.res.bean.QuotaStatOfMent;
import com.gy.hsxt.gpf.res.bean.QuotaStatOfPlat;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.mapper
 * @ClassName: QuotaMapper
 * @Description: 配额管理Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午4:23:12
 * @version V1.0
 */
@Repository
public interface QuotaMapper {

    /**
     * 初始化服务公司资源配额
     * 
     * @param serEntResNoList
     *            服务公司互生号列表
     */
    void initQuota(List<String> serEntResNoList);

    /**
     * 添加配额申请
     * 
     * @param quotaApp
     *            配额申请
     */
    void addQuotaApp(QuotaApp quotaApp);

    /**
     * 添加配额申请 --地区平台同步时用
     * 
     * @param quotaApp
     *            配额申请
     */
    void addQuotaApp4Region(QuotaApp quotaApp);

    /**
     * 分页查询配额申请
     * 
     * @param param
     *            查询参数
     * @return 配额申请列表
     */
    List<QuotaAppBaseInfo> queryQuotaAppListPage(QuotaAppParam param);

    /**
     * 审批配额申请
     * 
     * @param apprParam
     *            审批参数
     */
    void apprQuotaApp(ApprParam apprParam);

    /**
     * 通过Id查询配额申请
     * 
     * @param applyId
     *            申请编号
     * @return 配额申请
     */
    QuotaApp queryQuotaAppById(String applyId);

    /**
     * 更新路由数据同步标识和配额分配同步标识
     * 
     * @param applyId
     *            申请编号
     * @param routeSync
     *            路由数据同步标识
     * @param allotSync
     *            配额分配同步标识
     */
    void updateQuotaAllotSyncFlag(@Param("applyId") String applyId, @Param("routeSync") boolean routeSync,
            @Param("allotSync") boolean allotSync);

    /**
     * 通过申请编号查询申请配额清单
     * 
     * @param applyId
     *            申请编号
     * @return 申请配额清单
     */
    String queryApplyListById(String applyId);

    /**
     * 释放互生号
     * 
     * @param applyId
     *            申请编号
     */
    void releaseResNo(String applyId);

    /**
     * 更新资源配额状态
     * 
     * @param applyId
     *            申请编号
     * @param platNo
     *            平台代码
     * @param resNoList
     *            审批配额清单
     * @param status
     *            状态
     */
    void updateResNoStatus(@Param("applyId") String applyId, @Param("platNo") String platNo,
            @Param("resNoList") List<String> resNoList, @Param("status") Integer status);

    /**
     * 查询管理公司配额统计
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @return 管理公司配额统计列表
     */
    List<QuotaStatOfMent> queryQuotaStatOfMentListPage(@Param("entResNo") String entResNo,
            @Param("entCustName") String entCustName);

    /**
     * 查询管理公司在地区平台上的配额统计
     * 
     * @param entResNo
     *            管理公司互生号
     * @return 管理公司在地区平台上的配额统计
     */
    List<QuotaStatOfPlat> queryQuotaStatOfPlatListPage(@Param("entResNo") String entResNo);

    List<IdType> queryIdTyp(@Param("entResNoPre") String entResNoPre, @Param("applyId") String applyId);

    /**
     * 互生号是否被使用或已占用
     * 
     * @param resNoList
     *            互生号列表
     * @return true被使用或已占用
     */
    boolean resNoIsUsed(@Param("resNoList") List<String> resNoList);

    /**
     * 查询管理公司已分配的配额数
     * 
     * @param entResNo
     *            管理公司配额数
     * @param platNo
     *            地区平台代码
     * @return 管理公司已分配的配额数
     */
    int queryMentApprQuota(@Param("entResNo") String entResNo, @Param("platNo") String platNo);

    QuotaAppInfo queryQuotaAppInfo(String applyId);

}
