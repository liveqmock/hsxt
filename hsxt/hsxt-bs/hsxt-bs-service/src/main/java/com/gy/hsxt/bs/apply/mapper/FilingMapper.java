/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.apply.bean.FilingParam;
import com.gy.hsxt.bs.apply.bean.SameItemParam;
import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingAppInfo;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.mapper
 * @ClassName: FilingMapper
 * @Description: 企业报备Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-15 下午3:55:59
 * @version V1.0
 */
@Repository
public interface FilingMapper {

    /**
     * 创建企业报备申请
     * 
     * @param filingApp
     *            企业报备申请
     */
    public void createFilingApp(FilingApp filingApp);

    /**
     * 更新企业报备申请
     * 
     * @param filingApp
     *            企业报备申请
     */
    public void updateFilingApp(FilingApp filingApp);

    /**
     * 创建报备企业股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息
     */
    public void createShareHolder(FilingShareHolder filingShareHolder);

    /**
     * 更新报备企业股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息
     */
    public void updateShareHolder(FilingShareHolder filingShareHolder);

    /**
     * 删除股东
     * 
     * @param id
     *            股东唯一编号
     */
    public void deleteShareHolder(@Param("id") String id);

    /**
     * 通过ID查询股东
     * 
     * @param id
     *            股东唯一编号
     * @return 股东信息
     */
    public FilingShareHolder queryShById(@Param("id") String id);

    /**
     * 创建报备企业资质
     * 
     * @param addList
     *            报备企业资质
     */
    public void createAptitude(List<FilingAptitude> addList);

    /**
     * 更新报备企业资质
     * 
     * @param updateList
     *            报备企业资质
     */
    public void updateAptitude(List<FilingAptitude> updateList);

    /**
     * 查询企业报备
     * 
     * @param applyId
     *            申请编号
     * @return 企业报备申请信息
     */
    public FilingApp queryFilingAppById(String applyId);

    /**
     * 是否存在完全相同企业名
     * 
     * @param applyId
     *            申请编号
     * @param entCustName
     *            企业名称
     * @return true or false
     */
    public Boolean existSameEntName(@Param("applyId") String applyId, @Param("entCustName") String entCustName);

    /**
     * 审批报备
     * 
     * @param applyId
     *            申请编号
     * @param apprOperator
     *            审批操作员
     * @param status
     *            状态
     * @param apprRemark
     *            审批意见
     */
    public void apprFiling(@Param("applyId") String applyId, @Param("apprOperator") String apprOperator,
            @Param("status") Integer status, @Param("apprRemark") String apprRemark);

    /**
     * 查询企业报备
     * 
     * @param filingParam
     *            查询参数
     * @return 企业报备列表
     */
    public List<FilingAppInfo> queryFilingListPage(FilingParam filingParam);

    /**
     * 查询企业报备（工单）
     * 
     * @param filingParam
     *            查询参数
     * @return 企业报备列表
     */
    public List<FilingAppInfo> queryFiling4ApprListPage(FilingParam filingParam);

    /**
     * 查询股东列表
     * 
     * @param applyId
     *            申请编号
     * @return 股东列表
     */
    public List<FilingShareHolder> queryShByApplyId(String applyId);

    /**
     * 查询报备企业资质
     * 
     * @param applyId
     *            申请编号
     * @return 报备企业资质列表
     */
    public List<FilingAptitude> queryAptitudeByApplyId(String applyId);

    /**
     * 查询相同项
     * 
     * @param sameItemParam
     *            相同项参数
     * @return 相同项列表
     */
    public List<SameItemParam> querySameItem(SameItemParam sameItemParam);

    /**
     * 查询相同股东
     * 
     * @param sameSh
     *            相同项参数
     * @return 相同股东列表
     */
    public List<SameItemParam> querySameSh(SameItemParam sameSh);

    /**
     * 异议报备
     * 
     * @param applyId
     *            申请编号
     * @param operator
     *            操作员
     * @param status
     *            状态
     * @param remark
     *            备注
     */
    public void disagreedReject(@Param("applyId") String applyId, @Param("operator") String operator,
            @Param("status") Integer status, @Param("remark") String remark);

    /**
     * 企业是否被服务公司报备过
     * 
     * @param serResNo
     *            服务公司互生号
     * @param entCustName
     *            企业名称
     * @return true or false
     */
    public boolean isFiling(@Param("serResNo") String serResNo, @Param("entCustName") String entCustName);

    /**
     * 删除报备
     * 
     * @param applyId
     *            申请编号
     */
    public void deleteFilingApp(String applyId);

    /**
     * 删除股东
     * 
     * @param applyId
     *            申请编号
     */
    public void deleteShareHolderByApplyId(String applyId);

    /**
     * 删除资质附件
     * 
     * @param applyId
     *            申请编号
     */
    public void deleteAptByApplyId(String applyId);

    /**
     * 更新相同项标识
     * 
     * @param applyId
     *            申请编号
     * @param existSameItem
     *            是否存在相同项
     * @param operator
     *            操作员
     */
    public void updateSameItemFlag(@Param("applyId") String applyId, @Param("existSameItem") Boolean existSameItem,
            @Param("operator") String operator);

    /**
     * 根据报备申请单编号+股东证件类型+股东证件号码查询股东信息编号，用于判重
     * 
     * @param applyId
     *            报备申请单编号
     * @param idType
     *            证件类型
     * @param idNo
     *            证件号码
     * @return 股东编号
     */
    public String findShareHolder(@Param("applyId") String applyId, @Param("idType") Integer idType,
            @Param("idNo") String idNo);

}
