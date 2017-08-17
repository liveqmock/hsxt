/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.apply.bean.DeclareEntStatus;
import com.gy.hsxt.bs.apply.bean.DeclareParam;
import com.gy.hsxt.bs.apply.bean.ExpiryDeclare;
import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.apply.DeclareAptitude;
import com.gy.hsxt.bs.bean.apply.DeclareAuthCode;
import com.gy.hsxt.bs.bean.apply.DeclareBank;
import com.gy.hsxt.bs.bean.apply.DeclareBizRegInfo;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareIncrement;
import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.bs.bean.apply.DeclareProgress;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.bs.bean.apply.EntBaseInfo;
import com.gy.hsxt.bs.bean.apply.ResFeeDebtOrder;
import com.gy.hsxt.bs.bean.base.OptHisInfo;

@Repository
public interface DeclareMapper {

    /**
     * 创建申报申请
     * 
     * @param declareRegInfo
     *            申报申请信息
     */
    void createDeclare(DeclareRegInfo declareRegInfo);

    /**
     * 提交申报
     * 
     * @param applyId
     *            申报申请单编号
     * @param appStatus
     *            申报单状态
     * @param optCustId
     *            申报操作员编号
     */
    void submitDeclare(@Param("applyId") String applyId, @Param("appStatus") int appStatus,
            @Param("optCustId") String optCustId);

    /**
     * 更新申报申请
     * 
     * @param declareRegInfo
     *            申请申请
     */
    void updateDeclare(DeclareRegInfo declareRegInfo);

    /**
     * 更新申报企业工商登记信息
     * 
     * @param declareBizRegInfo
     *            申报企业工商登记信息
     */
    void updateDeclareEnt(DeclareBizRegInfo declareBizRegInfo);

    /**
     * 创建申报企业联系人信息
     * 
     * @param declareLinkman
     *            申报企业联系人信息
     */
    void createLinkman(DeclareLinkman declareLinkman);

    /**
     * 更新申报企业联系人信息
     * 
     * @param declareLinkman
     *            申报企业联系人信息
     */
    void updateLinkman(DeclareLinkman declareLinkman);

    /**
     * 创建申报企业银行账户信息
     * 
     * @param declareBank
     *            申报企业银行账户信息
     */
    void createBank(DeclareBank declareBank);

    /**
     * 更新申报企业银行账户信息
     * 
     * @param declareBank
     *            申报企业银行账户信息
     */
    void updateBank(DeclareBank declareBank);

    /**
     * 创建申报企业资质信息
     * 
     * @param addList
     *            申报企业资质信息
     */
    void createAptitude(List<DeclareAptitude> addList);

    /**
     * 创建单个申报企业资质信息
     *
     * @param aptitude 申报企业资质信息
     */
    void createOneAptitude(DeclareAptitude aptitude);

    /**
     * 更新申报企业资质信息
     * 
     * @param updateList
     *            申报企业资质信息
     */
    void updateAptitude(List<DeclareAptitude> updateList);

    /**
     * 更新申报企业资质信息
     *
     * @param aptitude
     *            申报企业资质信息
     */
    void updateOneAptitude(DeclareAptitude aptitude);

    /**
     * 审批申报
     * 
     * @param applyId
     *            申请编号
     * @param apprOperator
     *            审批操作员
     * @param apprStatus
     *            状态
     */
    void apprDeclare(@Param("applyId") String applyId, @Param("apprOperator") String apprOperator,
            @Param("apprStatus") int apprStatus);

    /**
     * 查询申报申请列表
     * 
     * @param declareParam
     *            申报申请参数
     * @return 申报申请列表
     */
    List<DeclareEntBaseInfo> queryDeclareListPage(DeclareParam declareParam);

    /**
     * 根据推荐企业来查询申报列表
     * 
     * @param declareParam
     *            申报申请参数
     * @return 申报申请列表
     */
    List<DeclareEntBaseInfo> queryDeclareByRecommendListPage(DeclareParam declareParam);

    /**
     * 关联工单查询申报申请列表
     * 
     * @param declareParam
     *            申报申请参数
     * @return 申报申请列表
     */
    List<DeclareEntBaseInfo> queryDeclare4TaskListPage(DeclareParam declareParam);

    /**
     * 检查是否已存在相同企业名的申报
     * 
     * @param toCustType
     *            被申报企业客户类型
     * @param toEntCustName
     *            被申报企业名称
     * @param applyId
     *            申请编号
     * @return ture or false
     */
    boolean checkDuplicateEntName(@Param("toCustType") Integer toCustType,
            @Param("toEntCustName") String toEntCustName, @Param("applyId") String applyId);

    /**
     * 通过Id查询申报信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报信息
     */
    DeclareInfo queryDeclareById(@Param("applyId") String applyId);

    /**
     * 通过授权码查询申报信息
     * 
     * @param authCode
     *            授权码
     * @return 申报信息
     */
    DeclareInfo queryDeclareByAuthCode(String authCode);

    /**
     * 通过申请编号查询授权码
     * 
     * @param applyId
     *            申请编号
     * @return 授权码信息
     */
    DeclareAuthCode queryAuthCodeByApplyId(String applyId);

    /**
     * 发送授权码后更新发送次数与最后发送时间
     * 
     * @param applyId
     *            申请编号
     * @param authCode
     *            授权码
     * @return 申报信息
     */
    int updateAfterSendAuthCode(@Param("applyId") String applyId, @Param("authCode") String authCode);

    /**
     * 查询授权码
     * 
     * @param appStatus
     *            申报单状态
     * @param manResNo
     *            管理公司互生号
     * @param serResNo
     *            服务公司互生号
     * @param entResNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人
     * @return 授权码列表
     */
    List<DeclareAuthCode> queryAuthCodeListPage(@Param("appStatus") Integer appStatus,
            @Param("manResNo") String manResNo, @Param("serResNo") String serResNo, @Param("entResNo") String entResNo,
            @Param("entName") String entName, @Param("linkman") String linkman);

    /**
     * 更新申报状态
     * 
     * @param declareEntStatus
     *            申报状态
     */
    void updateDeclareStatus(DeclareEntStatus declareEntStatus);

    /**
     * 通过申请编号查询联系人
     * 
     * @param applyId
     *            申请编号
     * @return 联系人信息
     */
    DeclareLinkman queryLinkmanByApplyId(String applyId);

    /**
     * 查询申报企业资质
     * 
     * @param applyId
     *            申请编号
     * @return 申报企业资质列表
     */
    List<DeclareAptitude> queryAptitudeByApplyId(String applyId);

    /**
     * 查询申报企业资质
     *
     * @param applyId  申请编号
     * @param aptitudeType  附件类型
     * @return 申报企业资质列表
     */
    DeclareAptitude queryOneAptitude(@Param("applyId") String applyId, @Param("aptitudeType") Integer aptitudeType);

    /**
     * 查询申报企业银行账户
     * 
     * @param applyId
     *            申请编号
     * @return 申报企业银行账户
     */
    DeclareBank queryBankByApplyId(String applyId);

    /**
     * 查询已过期的申报申请
     * 
     * @return 已过期的申报申请列表
     */
    List<ExpiryDeclare> getExpiryDeclareList();

    /**
     * 查询是否存申报
     * 
     * @param applyId
     *            申请编号
     * @return true or false
     */
    boolean existDeclare(String applyId);

    /**
     * 查询申报申请信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报申请信息
     */
    DeclareAppInfo queryDeclareAppInfoByApplyId(String applyId);

    /**
     * 通过申请编号查询状态
     * 
     * @param applyId
     *            申请编号
     * @return 申报状态
     */
    Integer queryDeclareStatusById(String applyId);

    /**
     * 删除申报企业银行账户信息
     * 
     * @param applyId
     *            申请编号
     */
    void delBank(String applyId);

    /**
     * 删除申报企业资质
     * 
     * @param applyId
     *            申请编号
     */
    void delApt(String applyId);

    /**
     * 删除申报企业联系人
     * 
     * @param applyId
     *            申请编号
     */
    void delLinkman(String applyId);

    /**
     * 删除申报申请
     * 
     * @param applyId
     *            申请编号
     */
    void delDeclareApp(String applyId);

    /**
     * 查询申报企业信息
     * 
     * @param entName
     *            企业名称
     * @param status
     *            状态
     * @return 申报企业信息列表
     */
    List<EntBaseInfo> queryEntInfo(@Param("entName") String entName, @Param("status") Integer status,
            @Param("notInStatus") String notInStatus);

    /**
     * 更新申报服务公司互生号
     * 
     * @param declareRegInfo
     *            申请信息
     */
    void updateSerResNo(DeclareRegInfo declareRegInfo);

    /**
     * 查询待开启系统申报列表
     * 
     * @param declareParam
     *            查询参数
     * @return 待开启系统申报列表
     */
    List<DeclareEntBaseInfo> queryOpenSysListPage(DeclareParam declareParam);

    /**
     * 关联工单查询待开启系统申报列表
     * 
     * @param declareParam
     *            查询参数
     * @return 待开启系统申报列表
     */
    List<DeclareEntBaseInfo> queryOpenSys4TaskListPage(DeclareParam declareParam);

    /**
     * 查询开启系统欠款审核列表
     * 
     * @param declareParam
     *            查询参数
     * @return 开启系统欠款审核列表
     */
    List<DeclareEntBaseInfo> queryOpenSysApprListPage(DeclareParam declareParam);

    /**
     * 查询系统销售费欠款单
     * 
     * @param debtOrderParam
     *            查询参数
     * @return 系统销售费欠款单
     */
    List<ResFeeDebtOrder> queryResFeeDebtOrderListPage(DebtOrderParam debtOrderParam);

    /**
     * 更新申报备注
     * 
     * @param applyId
     *            申请编号
     * @param remark
     *            备注
     */
    void updateDeclareRemark(@Param("applyId") String applyId, @Param("remark") String remark);

    /**
     * 查询申报备注
     * 
     * @param applyId
     *            申报备注
     * @return 申报备注
     */
    String getDeclareRemark(@Param("applyId") String applyId);

    /**
     * 查询申报进度
     * 
     * @param entName
     *            企业名称
     * @return 企业申报信息
     */
    List<DeclareProgress> queryDeclareProgress(@Param("entName") String entName);

    /**
     * 保存申报增值点信息
     * 
     * @param declareIncrement
     *            申报增值点信息
     */
    void saveIncrement(DeclareIncrement declareIncrement);

    /**
     * 把拟用互生号设为空
     * 
     * @param applyId
     *            申请编号
     */
    void emptyToEntResNo(@Param("applyId") String applyId);

    /**
     * 查询申报基本信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报基本信息
     */
    DeclareEntBaseInfo queryDeclareEntBaseInfoById(@Param("applyId") String applyId);

    /**
     * 更新重新申报标识
     * 
     * @param applyId
     *            申报编号
     */
    void updateRedoFlag(@Param("applyId") String applyId);
    
    /**
     * 查询开启系统成功但是同步开启UC或BM失败的申报单
     * @return
     */
    List<DeclareInfo> queryDeclareSyncFail();
    
    
    /**
     * 服务公司查询办理状态
     * @param applyId 申报编号
     * @return
     */
    List<OptHisInfo> serviceQueryDeclareHisListPage(String applyId);

}
