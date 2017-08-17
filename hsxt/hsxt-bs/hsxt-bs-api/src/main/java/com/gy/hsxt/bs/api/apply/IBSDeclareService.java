/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.apply;

import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.bean.bm.Increment;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.app
 * @ClassName: IBSDeclareService
 * @Description: 申报管理接口
 * 
 * @author: xiaofl
 * @date: 2015-9-1 上午9:29:47
 * @version V1.0
 */
public interface IBSDeclareService {

    /**
     * 创建企业申报
     * 
     * @param declareRegInfo
     *            申报信息
     * @return 返回申请编号
     * @throws HsException
     */
    public String createDeclare(DeclareRegInfo declareRegInfo) throws HsException;

    /**
     * 服务公司修改申报信息
     * 
     * @param declareRegInfo
     *            申报信息
     * @throws HsException
     */
    public void serviceModifyDeclare(DeclareRegInfo declareRegInfo) throws HsException;

    /**
     * 管理公司选号(申报服务公司)
     * 
     * @param declareRegInfo
     *            申报信息
     * @throws HsException
     */
    public void managePickResNo(DeclareRegInfo declareRegInfo) throws HsException;

    /**
     * 管理公司修改申报信息
     * 
     * @param declareRegInfo
     *            申报信息
     * @throws HsException
     */
    public void manageModifyDeclare(DeclareRegInfo declareRegInfo) throws HsException;

    /**
     * 地区平台修改申报信息
     * 
     * @param declareRegInfo
     *            申报信息
     * @throws HsException
     */
    public void platModifyDeclare(DeclareRegInfo declareRegInfo) throws HsException;

    /**
     * 创建申报企业信息
     * 
     * @param declareBizRegInfo
     *            申报企业信息
     * @return 返回申请编号
     * @throws HsException
     */
    public DeclareBizRegInfo createDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException;

    /**
     * 服务公司修改申报企业信息
     * 
     * @param declareBizRegInfo
     *            申报企业信息
     * @throws HsException
     */
    public DeclareBizRegInfo serviceModifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException;

    /**
     * 管理公司修改申报企业信息
     * 
     * @param declareBizRegInfo
     *            申报企业信息
     * @throws HsException
     */
    public DeclareBizRegInfo manageModifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException;

    /**
     * 地区平台修改申报企业信息
     * 
     * @param declareBizRegInfo
     *            申报企业信息
     * @throws HsException
     */
    public DeclareBizRegInfo platModifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException;

    /**
     * 创建联系人信息
     * 
     * @param declareLinkman
     *            联系人信息
     * @throws HsException
     */
    public DeclareLinkman createLinkman(DeclareLinkman declareLinkman) throws HsException;

    /**
     * 服务公司修改联系人信息
     * 
     * @param declareLinkman
     *            联系人信息
     * @throws HsException
     */
    public DeclareLinkman serviceModifyLinkman(DeclareLinkman declareLinkman) throws HsException;

    /**
     * 管理公司修改联系人信息
     * 
     * @param declareLinkman
     *            联系人信息
     * @throws HsException
     */
    public DeclareLinkman manageModifyLinkman(DeclareLinkman declareLinkman) throws HsException;

    /**
     * 地区平台修改联系人信息
     * 
     * @param declareLinkman
     *            联系人信息
     * @throws HsException
     */
    public DeclareLinkman platModifyLinkman(DeclareLinkman declareLinkman) throws HsException;

    /**
     * 创建银行信息
     * 
     * @param declareBank
     *            银行信息
     * @return 银行账户ID
     * @throws HsException
     */
    @Deprecated
    public String createBank(DeclareBank declareBank) throws HsException;

    /**
     * 服务公司修改银行信息
     * 
     * @param declareBank
     *            银行信息
     * @throws HsException
     */
    @Deprecated
    public void serviceModifyBank(DeclareBank declareBank) throws HsException;

    /**
     * 管理公司保存银行信息
     * 
     * @param declareBank
     *            银行信息
     * @return 银行账号ID
     * @throws HsException
     */
    @Deprecated
    public String manageSaveBank(DeclareBank declareBank) throws HsException;

    /**
     * 地区平台修改银行信息
     * 
     * @param declareBank
     *            银行信息
     * @throws HsException
     */
    @Deprecated
    public void platModifyBank(DeclareBank declareBank) throws HsException;

    /**
     * 服务公司保存申报企业资质附件信息
     * 
     * @param declareAptitudes
     *            申报企业资质附件信息 必填
     * @param optInfo
     *            操作信息
     * @return 申报企业资质附件列表
     * @throws HsException
     */
    @Deprecated
    public List<DeclareAptitude> serviceSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo)
            throws HsException;

    /**
     * 管理公司保存申报企业资质附件信息
     * 
     * @param declareAptitudes
     *            申报企业资质附件信息 必填
     * @param optInfo
     *            操作信息
     * @throws HsException
     */
    @Deprecated
    public List<DeclareAptitude> manageSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo)
            throws HsException;

    /**
     * 平台保存保存申报企业资质附件信息
     * 
     * @param declareAptitudes
     *            申报企业资质附件信息 必填
     * @param optInfo
     *            操作信息
     * @return 申报企业资质附件列表
     * @throws HsException
     */
    @Deprecated
    public List<DeclareAptitude> platSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo)
            throws HsException;

    /**
     * 提交申报
     * 
     * @param applyId
     *            申请编号 必填
     * @param operatorInfo
     *            操作员信息
     * @throws HsException
     */
    public void submitDeclare(String applyId, OptInfo operatorInfo) throws HsException;

    /**
     * 服务公司查询企业申报
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> serviceQueryDeclare(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 服务公司查询企业申报内审
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> serviceQueryDeclareAppr(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 管理公司查询申报初审
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> manageQueryDeclareAppr(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 管理公司查询申报复核
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> manageQueryDeclareReview(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 管理公司申报审批统计查询
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> manageQueryDeclareList(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台申报审批统计查询
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> platQueryDeclareList(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 查询申报信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报信息
     */
    public DeclareAppInfo queryDeclareAppInfoByApplyId(String applyId);

    /**
     * 查询企业系统注册信息
     * 
     * @param applyId
     *            申请编号
     * @return 企业系统注册信息
     */
    public DeclareRegInfo queryDeclareRegInfoByApplyId(String applyId);

    /**
     * 查询企业工商登记信息
     * 
     * @param applyId
     *            申请编号
     * @return 企业工商登记信息
     */
    public DeclareBizRegInfo queryDeclareBizRegInfoByApplyId(String applyId);

    /**
     * 查询申报企业联系人信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报企业联系人信息
     */
    public DeclareLinkman queryLinkmanByApplyId(String applyId);

    /**
     * 查询申报企业银行信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报企业银行信息
     */
    public DeclareBank queryBankByApplyId(String applyId);

    /**
     * 查询申报企业资质信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报企业资质信息
     */
    public List<DeclareAptitude> queryAptitudeByApplyId(String applyId);

    /**
     * 查询申报详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回申报详情（申报信息、企业工商登记信息、企业联系信息、企业银行账户信息、企业上传资料、办理状态信息） Key value
     *         DECLARE_APP_INFO DeclareAppInfo DECLARE_REG_INFO DeclareRegInfo
     *         DECLARE_BIZ_REG_INFO DeclareBizRegInfo DECLARE_LINKMAN
     *         DeclareLinkman DECLARE_BANK DeclareBank DECLARE_APTITUDE
     *         List<DeclareAptitude> APPROPERATION_HIS List<ApprOperationHis>
     *         没有则返回null
     * @throws HsException
     */
    // public Map<String,Object> queryDeclareById(String applyId) throws
    // HsException;

    /**
     * 服务公司审核申报
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void serviceApprDeclare(ApprParam apprParam) throws HsException;

    /**
     * 管理公司初审
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void manageApprDeclare(ApprParam apprParam) throws HsException;

    /**
     * 管理公司复核
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void manageReviewDeclare(ApprParam apprParam) throws HsException;

    /**
     * 地区平台查询开启系统欠款审核列表
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> platQueryOpenSysAppr(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台查询开启系统列表
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> platQueryOpenSys(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 开启系统
     * 
     * @param apprParam
     *            开启系统参数
     * @throws HsException
     */
    public void openSystem(ApprParam apprParam) throws HsException;

    /**
     * 服务公司查询授权码
     * 
     * @param serResNo
     *            服务公司互生号 必填
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人姓名
     * @param page
     *            分页信息 必填
     * @return 返回授权码列表,没有则返回null
     * @throws HsException
     */
    public PageData<DeclareAuthCode> serviceQueryAuthCode(String serResNo, String resNo, String entName,
            String linkman, Page page) throws HsException;

    /**
     * 管理公司查询授权码
     * 
     * @param manResNo
     *            管理公司互生号 必填
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人姓名
     * @param page
     *            分页信息 必填
     * @return 返回授权码列表,没有则返回null
     * @throws HsException
     */
    public PageData<DeclareAuthCode> manageQueryAuthCode(String manResNo, String resNo, String entName, String linkman,
            Page page) throws HsException;

    /**
     * 地区平台查询授权码
     * 
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人姓名
     * @param page
     *            分页信息 必填
     * @return 返回授权码列表,没有则返回null
     * @throws HsException
     */
    public PageData<DeclareAuthCode> platQueryAuthCode(String resNo, String entName, String linkman, Page page)
            throws HsException;

    /**
     * 查询服务公司配额数
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return 服务公司配额数
     */
    public Integer getServiceQuota(String countryNo, String provinceNo, String cityNo);

    /**
     * 查询成员企业、托管企业配额数
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业或托管企业配额数
     */
    public Integer getNotServiceQuota(String serResNo, Integer custType, Integer resType);

    /**
     * 查询服务公司可用互生号列表(服务公司用)
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return 服务公司可用互生号列表
     */
    public List<String> getSerResNoList(String countryNo, String provinceNo, String cityNo);

    /**
     * 地区平台查询服务公司可用互生号列表(地区平台用)
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return 服务公司可用互生号列表
     */
    public List<String> getSerResNoList4Plat(String countryNo, String provinceNo, String cityNo);

    /**
     * 查询已占用互生号资源详情
     *
     * @param resNo 互生号
     * @return {@code ResNo}
     */
    ResNo queryUsedSerResNoByResNo(String resNo);

    /**
     * 查询成员企业、托管企业可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业或托管企业可用互生号列表
     */
    public List<String> getNotSerResNoList(String serResNo, Integer custType, Integer resType);

    /**
     * 查询管理公司信息
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @return 管理公司信息
     */
    public ManageEnt queryManageEnt(String countryNo, String provinceNo);

    /**
     * 查询管理公司信息和服务公司配额数
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return 管理公司信息和服务公司配额数
     */
    public Map<String, Object> queryManageEntAndQuota(String countryNo, String provinceNo, String cityNo);

    /**
     * 查询成员企业、托管企业配额数和可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业、托管企业配额数和可用互生号列表
     */
    public Map<String, Object> getResNoListAndQuota(String serResNo, Integer custType, Integer resType);

    /**
     * 查询积分增值点信息
     * 
     * @param resNo
     *            互生号
     * @return 积分增值点信息
     */
    public Increment queryIncrement(String resNo);

    /**
     * 查询积分增值点信息(平台用)
     *
     * @param spreadResNo 推广互生号
     * @param subResNo 挂载互生号
     * @return 积分增值点信息
     */
    public Increment queryIncrement(String spreadResNo,String subResNo);

    /**
     * 根据申请编号查询客户类型
     * 
     * @param applyId
     *            申请编号
     * @return 客户类型
     */
    public Integer getCustTypeByApplyId(String applyId);

    /**
     * 开系统欠款审核
     * 
     * @param debtOpenSys
     *            开启系统预审批信息
     * @throws HsException
     */
    public void apprDebtOpenSys(DebtOpenSys debtOpenSys) throws HsException;

    /**
     * 查询申报进行步骤
     * 
     * @param applyId
     *            申请编号
     * @return 返回 0:未找到该申报 1.企业系统注册信息填写完成,2.企业工商登记信息填写完成,
     *         3.企业联系信息填写完成,4.企业银行账户信息填写完成,5.企业上传资料填写完成
     * 
     */
    public Integer queryDeclareStep(String applyId);

    /**
     * 删除未提交的申报申请
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    public void delUnSubmitDeclare(String applyId) throws HsException;

    /**
     * 查询申报办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页参数
     * @return 办理状态信息列表
     * @throws HsException
     */
    public PageData<OptHisInfo> queryDeclareHis(String applyId, Page page) throws HsException;

    /**
     * 查询申报进度
     * 
     * @param entName
     *            企业名称
     * @return 企业申报信息
     */
    public List<DeclareProgress> queryDeclareProgress(String entName);

    /**
     * 查询申报增值点列表
     * 
     * @param declareQueryParam
     *            查询条件
     * @param page
     *            分页信息
     * @return 申报增值点列表
     * @throws HsException
     */
    public PageData<DeclareEntBaseInfo> queryIncrementList(DeclareQueryParam declareQueryParam, Page page)
            throws HsException;

    /**
     * 保存申报增值点信息
     * 
     * @param declareIncrement
     *            申报增值点信息
     * @throws HsException
     */
    public void saveIncrement(DeclareIncrement declareIncrement) throws HsException;

    /**
     * 分页查询服务公司可用互生号列表(服务公司用)
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resNo
     *            拟用互生号
     * @param page
     *            分页
     * @return 服务公司可用互生号列表
     */
    public PageData<ResNo> getSerResNos(String countryNo, String provinceNo, String cityNo, String resNo, Page page)
            throws HsException;

    /**
     * 分页查询成员企业、托管企业可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @param resNo
     *            拟用互生号
     * @param page
     *            分页
     * @return 成员企业或托管企业可用互生号列表
     */
    public PageData<ResNo> getEntResNos(String serResNo, Integer custType, Integer resType, String resNo, Page page)
            throws HsException;

    /**
     * 重新提交申报已被驳回或过期的企业
     * 
     * @param oldApplyId
     *            被驳回的申请编号
     * @param optInfo
     *            操作员信息
     * @return 新的申报信息
     * @throws HsException
     */
    public DeclareRegInfo reApplyDeclare(String oldApplyId, OptInfo optInfo) throws HsException;

    /**
     * 查询申报基本信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报基本信息
     */
    public DeclareEntBaseInfo queryDeclareEntBaseInfoById(String applyId);

    /**
     * 根据Id查询资源费分配记录
     * 
     * @param detailId
     *            记录编号
     * @return 资源费分配记录
     */
    public ResFeeAlloc queryResFeeAllocById(String detailId);

    /**
     * 申报开启系统时同步UC失败重试
     * 
     * @param applyId
     *            申报编号
     * @param operator
     *            操作员
     * @throws HsException
     */
    void syncOpenUC(String applyId, Operator operator) throws HsException;

    /**
     * 申报开启系统时同步BM失败重试
     * 
     * @param applyId
     *            申报编号
     * @param operator
     *            操作员
     * @throws HsException
     */
    void syncOpenBM(String applyId, Operator operator) throws HsException;

    /**
     * 发送授权码
     * 
     * @param applyId
     *            申报编号
     * @throws HsException
     */
    void sendAuthCode(String applyId) throws HsException;

    /**
     * 根据申报编号查询附件列表，带备注说明
     * 
     * @param applyId
     * @return
     * @throws HsException
     */
    Map<String, Object> queryAptitudeWithRemarkByApplyId(String applyId) throws HsException;

    /**
     * 查询申报备注说明
     * 
     * @param applyId
     *            申报编号
     * @return
     * @throws HsException
     */
    String queryDeclareRemarkByApplyId(String applyId) throws HsException;

    /**
     * 服务公司保存附件信息
     * 
     * @param declareAptitudes
     *            附件列表
     * @param optInfo
     *            操作员信息
     * @param remark
     *            附件备注说明
     * @return
     * @throws HsException
     */
    List<DeclareAptitude> serviceSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo,
            String remark) throws HsException;

    /**
     * 管理公司保存附件信息
     * 
     * @param declareAptitudes
     *            附件列表
     * @param optInfo
     *            操作员信息
     * @param remark
     *            附件备注说明
     * @return
     * @throws HsException
     */
    List<DeclareAptitude> manageSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo,
            String remark) throws HsException;

    /**
     * 地区平台保存附件信息
     * 
     * @param declareAptitudes
     *            附件列表
     * @param optInfo
     *            操作员信息
     * @param remark
     *            附件备注说明
     * @return
     * @throws HsException
     */
    List<DeclareAptitude> platSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo, String remark)
            throws HsException;

    /**
     * 修改工商登记信息
     * 
     * @param declareBizRegInfo
     * @throws HsException
     */
    DeclareBizRegInfo modifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException;

    /**
     * 修改申报注册信息
     * 
     * @param declareRegInfo
     * @throws HsException
     */
    void modifyDeclare(DeclareRegInfo declareRegInfo) throws HsException;

    /**
     * 修改联系人信息
     * 
     * @param declareLinkman
     * @throws HsException
     */
    DeclareLinkman modifyLinkman(DeclareLinkman declareLinkman) throws HsException;

    /**
     * 修改申报企业银行账户信息
     * 
     * @param declareBank
     * @throws HsException
     */
    void modifyDeclareBank(DeclareBank declareBank) throws HsException;

    /**
     * 服务公司查询申报办理状态信息,不包含管理公司初审通过，并且前端管理公司初审复核显示文字不一样
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页参数
     * @return 办理状态信息列表
     * @throws HsException
     */
    PageData<OptHisInfo> serviceQueryDeclareHis(String applyId, Page page) throws HsException;

    /**
     * 校验互生号是否可用
     * @param entResNo 企业互生号
     * @return
     * @throws HsException
     */
    Boolean checkValidEntResNo(String entResNo) throws HsException;
}
