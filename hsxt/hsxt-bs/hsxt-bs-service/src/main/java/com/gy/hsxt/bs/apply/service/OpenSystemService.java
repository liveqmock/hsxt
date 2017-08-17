/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeePriceService;
import com.gy.hsxt.bs.apply.bean.DeclareEntStatus;
import com.gy.hsxt.bs.apply.interfaces.ICertificateService;
import com.gy.hsxt.bs.apply.interfaces.IContractService;
import com.gy.hsxt.bs.apply.interfaces.IOpenSystemService;
import com.gy.hsxt.bs.apply.interfaces.IResFeeResolveService;
import com.gy.hsxt.bs.apply.mapper.DeclareMapper;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bm.bean.ApplyRecord;
import com.gy.hsxt.bs.bm.interfaces.IBsUfRegionPackService;
import com.gy.hsxt.bs.common.BMConstants;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.apply.*;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.enumtype.quota.ResStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IEntResNoService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.resfee.interfaces.IResFeeService;
import com.gy.hsxt.bs.tool.bean.ApplyOrderConfig;
import com.gy.hsxt.bs.tool.interfaces.IInsideInvokeCall;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo;
import com.gy.hsxt.uc.bs.enumerate.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: OpenSystemService
 * @Description: 开启系统
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:18:39
 * @version V1.0
 */
@Service
public class OpenSystemService implements IOpenSystemService {

    @Resource
    private DeclareMapper declareMapper;

    @Resource
    private IUCBsEntService bsEntService;

    @Resource
    private IBsUfRegionPackService bsUfRegionPackService;

    @Resource
    private IOrderService orderService;

    @Resource
    private IInsideInvokeCall insideInvokeCall;

    @Resource
    private IEntResNoService entResNoService;

    @Resource
    private IOperationService operationService;

    @Resource
    private IContractService contractService;

    @Resource
    private ICertificateService certificateService;

    @Resource
    private ICommonService commonService;

    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    @Resource
    private IResFeeResolveService resFeeResolveService;

    @Resource
    private IResFeeService resFeeService;

    @Resource
    private IAnnualFeePriceService annualFeePriceService;

    @Resource
    private LcsClient lcsClient;

    @Resource
    BsConfig bsConfig;

    /**
     * 用户中心开户
     * 
     * @param declareInfo
     *            申报信息
     * @param optCustId
     *            操作员客户号
     * @return 被申报企业客户号
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String openUc(DeclareInfo declareInfo, String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), "openUc", "input param:" + declareInfo.toString());
        try
        {
            DeclareLinkman declareLinkman = declareMapper.queryLinkmanByApplyId(declareInfo.getApplyId());
            List<DeclareAptitude> declareAptitudeList = declareMapper.queryAptitudeByApplyId(declareInfo.getApplyId());
            DeclareBank declareBank = declareMapper.queryBankByApplyId(declareInfo.getApplyId());
            String busiLicenseImg = "";// 营业执照照片
            String orgCodeImg = "";// 组织机构代码证图片
            String taxRegImg = "";// 税务登记证正面扫描图片
            String creFaceImg = "";// 法人证件正面图片
            String creBackImg = "";// 法人证件反面图片
            String ventureImg = "";// 创业帮扶协议
            for (DeclareAptitude declareAptitude : declareAptitudeList)
            {
                if (AptitudeType.BIZ_LICENSE_CRE.getCode() == declareAptitude.getAptitudeType())
                {
                    busiLicenseImg = declareAptitude.getFileId();
                }
                else if (AptitudeType.ORGANIZER_CRE.getCode() == declareAptitude.getAptitudeType())
                {
                    orgCodeImg = declareAptitude.getFileId();
                }
                else if (AptitudeType.TAXPAYER_CRE.getCode() == declareAptitude.getAptitudeType())
                {
                    taxRegImg = declareAptitude.getFileId();
                }
                else if (AptitudeType.LEGAL_REP_CRE_FACE.getCode() == declareAptitude.getAptitudeType())
                {
                    creFaceImg = declareAptitude.getFileId();
                }
                else if (AptitudeType.LEGAL_REP_CRE_BACK.getCode() == declareAptitude.getAptitudeType())
                {
                    creBackImg = declareAptitude.getFileId();
                }
                else if (AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode() == declareAptitude.getAptitudeType())
                {
                    ventureImg = declareAptitude.getFileId();
                }
            }

            BsEntRegInfo bsEntRegInfo = new BsEntRegInfo();
            bsEntRegInfo.setEntCustId(declareInfo.getToEntCustId()); // 企业客户号
            bsEntRegInfo.setEntName(declareInfo.getToEntCustName());// 企业名称(注册名)
            bsEntRegInfo.setEntNameEn(declareInfo.getToEntEnName());// 企业英文名称
            bsEntRegInfo.setEntResNo(declareInfo.getToEntResNo());// 企业互生号
            bsEntRegInfo.setCustType(declareInfo.getToCustType());// 客户类型
            bsEntRegInfo.setStartResType(declareInfo.getToBuyResRange());// 启用资源类型
            bsEntRegInfo.setBusinessType(declareInfo.getToBusinessType());// 经营类型
            bsEntRegInfo.setApplyEntResNo(declareInfo.getSpreadEntResNo());// 推荐企业（平台、服务公司）申报企业的服务公司资源号
            String superEntResNo = "";// 上级企业互生号
            if (CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType())
            {
                superEntResNo = declareInfo.getToEntResNo().substring(0, 2) + "000000000";
            }
            else
            {
                superEntResNo = declareInfo.getToEntResNo().substring(0, 5) + "000000";
            }
            bsEntRegInfo.setSuperEntResNo(superEntResNo);// 上级企业（管理公司、平台）
            bsEntRegInfo.setEntRegAddr(declareInfo.getEntAddr());// 企业地址
            bsEntRegInfo.setBusiLicenseNo(declareInfo.getLicenseNo());// 企业营业执照号码
            bsEntRegInfo.setBusiLicenseImg(busiLicenseImg);// 营业执照照片
            bsEntRegInfo.setOrgCodeNo(declareInfo.getOrgNo());// 组织机构代码证号
            bsEntRegInfo.setOrgCodeImg(orgCodeImg);// 组织机构代码证图片
            bsEntRegInfo.setTaxNo(declareInfo.getTaxNo());// 纳税人识别号
            bsEntRegInfo.setTaxRegImg(taxRegImg);// 税务登记证正面扫描图片
            if (StringUtils.isNotEmpty(declareInfo.getEstablishingDate())) {
                Date date = DateUtil.StringToDate(StringUtils.trimToEmpty(declareInfo.getEstablishingDate()));
                if (date != null) {
                    bsEntRegInfo.setBuildDate(DateUtil.DateTimeToString(date));// 成立日期
                }
            }
            bsEntRegInfo.setEndDate(declareInfo.getLimitDate());// 营业期限（现在单位是年）
            bsEntRegInfo.setNature(declareInfo.getEntType());// 企业性质（以前是企业类型）
            bsEntRegInfo.setCreName(declareInfo.getLegalName());// 法人代表
            bsEntRegInfo.setCreType(declareInfo.getLegalCreType());// 法人证件类型
            bsEntRegInfo.setCreNo(declareInfo.getLegalCreNo());// 法人证件号码
            bsEntRegInfo.setCreFaceImg(creFaceImg);// 法人身份证正面图片
            bsEntRegInfo.setCreBackImg(creBackImg);// 法人身份证反面图片
            bsEntRegInfo.setCountryCode(declareInfo.getCountryNo());// 所在国家
            bsEntRegInfo.setProvinceCode(declareInfo.getProvinceNo());// 所在省份
            bsEntRegInfo.setCityCode(declareInfo.getCityNo());// 所在城市
            // bsEntRegInfo.setBusiArea("");//经营面积
            // bsEntRegInfo.setEntEmpNum(null);//员工数量
            bsEntRegInfo.setBusinessScope(declareInfo.getDealArea());// 经营范围
            bsEntRegInfo.setCerDeposit(declareInfo.getRegisterFee());// 注册资本
            // bsEntRegInfo.setIntroduction("");//企业简介
            bsEntRegInfo.setContactPerson(declareLinkman.getLinkman());// 联系人
            bsEntRegInfo.setContactDuty(declareLinkman.getJob());// 联系人职务
            bsEntRegInfo.setContactPhone(declareLinkman.getMobile());// 联系人电话
            bsEntRegInfo.setContactAddr(declareLinkman.getAddress());// 联系人地址
            bsEntRegInfo.setContactEmail(declareLinkman.getEmail());// 企业邮箱
            bsEntRegInfo.setOfficeQq(declareLinkman.getQq());// 企业QQ
            bsEntRegInfo.setOfficeTel(declareInfo.getPhone());// 办公电话
            bsEntRegInfo.setOfficeFax(declareLinkman.getFax());// 传真号码
            // bsEntRegInfo.setLogo("");//企业LOGO图片ID
            // bsEntRegInfo.setIndustry("");//所属行业
            bsEntRegInfo.setPostCode(declareLinkman.getZipCode());// 邮政编码
            bsEntRegInfo.setWebSite(declareLinkman.getWebSite());// 企业网址
            // bsEntRegInfo.setReserveInfo("");//预留信息
            // bsEntRegInfo.setLongitude(""); //企业经度
            // bsEntRegInfo.setLatitude("");//企业纬度
            bsEntRegInfo.setAuthProxyFile(declareLinkman.getCertificateFile());// 联系人授权委托书
            bsEntRegInfo.setHelpAgreement(ventureImg);// 创业帮扶协议
            bsEntRegInfo.setOperator(optCustId);// 操作者客户号

            // 开启系统日期
            String openAccDate = declareInfo.getOpenAccDate();
            bsEntRegInfo.setOpenDate(openAccDate);// 开启系统日期，带时间

            // 年费截止日期
            String annualFeeDates[] = this.getAnnaulFeeEndDate(declareInfo.getResFeeId(), openAccDate);
            if (null != annualFeeDates)
            {
                bsEntRegInfo.setExpireDate(annualFeeDates[0]);// 年费截止日期
            }

            BsBankAcctInfo bankAcctInfo = null;
            if (declareBank != null)
            {
                bankAcctInfo = new BsBankAcctInfo();
                bankAcctInfo.setResNo(bsEntRegInfo.getEntResNo());
                bankAcctInfo.setBankAccName(declareBank.getAccountName());
                bankAcctInfo.setBankAccNo(declareBank.getAccountNo());
                bankAcctInfo.setBankCode(declareBank.getBankCode());
                // 获取银行名称
                String bankName = lcsClient.getBankName(declareBank.getBankCode());
                bankAcctInfo.setBankName(bankName);
                bankAcctInfo.setCountryNo(declareBank.getCountryNo());
                bankAcctInfo.setProvinceNo(declareBank.getProvinceNo());
                bankAcctInfo.setCityNo(declareBank.getCityNo());
                bankAcctInfo.setIsDefaultAccount(declareBank.getIsDefault() ? "1" : "0");
            }
            try {
                bsEntService.regEntAddBankCard(bsEntRegInfo, bankAcctInfo);
            } catch (HsException e) {
                //如果客户已在UC存在，不抛异常，使支持重复同步请求以保证更新本地同步标志
                if(ErrorCodeEnum.ENT_IS_EXIST.getValue() == e.getErrorCode()){
                    SystemLog.warn(this.getClass().getName(), "openUc", 
                             "用户中心企业客户已开户[param=" + declareInfo + "]", e);
                }else{
                    throw e;
                }
            }

            // 更新UC开户标识
            DeclareEntStatus declareEntStatus = new DeclareEntStatus();
            declareEntStatus.setApplyId(declareInfo.getApplyId());
            declareEntStatus.setOpenAccFlag(true);// 设用户中心开启标识为已开户
            declareEntStatus.setOperator(optCustId);
            declareMapper.updateDeclareStatus(declareEntStatus);
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "openUc", BSRespCode.BS_DECLARE_OPEN_UC_ERROR.getCode()
                    + "用户中心开户失败[param=" + declareInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_OPEN_UC_ERROR, "用户中心开户失败[param=" + declareInfo + "]" + e);
        }
        return declareInfo.getToEntCustId();
    }

    /**
     * 增值系统开户
     * 
     * @param declareInfo
     *            申报信息
     * @param optCustId
     *            操作员客户号
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void openVas(DeclareInfo declareInfo, String optCustId) throws HsException {
        try
        {
            // 调用增值系统，开启增值服务
            ApplyRecord applyRecord = new ApplyRecord();
            // 申报日期(一定要有时分秒yyyy-MM-dd HH:mm:ss)
            applyRecord.setAppDate(declareInfo.getOpenAccDate());
            applyRecord.setAppCustId(declareInfo.getToInodeResNo());// 设置增值树父节点编号
            applyRecord.setPopNo(declareInfo.getToEntResNo());// 被申报企业的互生号
            applyRecord.setPopCustId(declareInfo.getToEntCustId());// 被申报企业的客户号
            if (null == declareInfo.getToInodeLorR() || InodeArea.LEFT.getCode() == declareInfo.getToInodeLorR())
            {// 默认或选择了左增值区域
                applyRecord.setArea(BMConstants.LEFT);
            }
            else
            {// 右增值区域
                applyRecord.setArea(BMConstants.RIGHT);
            }

            // 是否跨管理公司申报（0 同管理公司 1 跨管理公司），平台推荐的也要算跨管理公司
            if (declareInfo.getToEntResNo().substring(0, 2).equals(declareInfo.getSpreadEntResNo().substring(0, 2)))
            {// 同管理公司申报
                applyRecord.setFlag(BMConstants.SAME_MANAGE_CORP);
                HsAssert.hasText(declareInfo.getToInodeResNo(), BSRespCode.BS_BM_PNODE_NO_DATA, "开启增值失败,尚未设置增值父节点编号");
                if (declareInfo.getToInodeResNo().length() < 11)
                {
                    throw new HsException(RespCode.PARAM_ERROR, "开启增值失败,父节点值设置有误：declareInfo=" + declareInfo);
                }
                applyRecord.setAppCustId(declareInfo.getToInodeResNo());// 设置增值树父节点编号
            }
            else
            {// 跨管理公司申报
                applyRecord.setFlag(BMConstants.DIFF_MANAGE_CORP);
                if (StringUtils.isEmpty(declareInfo.getToInodeResNo()))
                {// 跨管理公司未设置增值父节点时默认以目标管理公司作为父节点
                    applyRecord.setAppCustId(declareInfo.getToMResNo());// 设置增值树父节点编号
                }
                else
                {
                    if (declareInfo.getToInodeResNo().length() < 11)
                    {
                        throw new HsException(RespCode.PARAM_ERROR, "开启增值失败,父节点值设置有误：declareInfo=" + declareInfo);
                    }
                }
                if (StringUtils.left(declareInfo.getToInodeResNo(), 2).equals(StringUtils.left(declareInfo.getToMResNo(), 2)) && !HsResNoUtils.isManageResNo(StringUtils.left(declareInfo.getToInodeResNo(),11))) {
                    applyRecord.setFlag(BMConstants.SAME_MANAGE_CORP);
                }
            }

            boolean isSuccess = bsUfRegionPackService.saveApplyRecordToMlm(applyRecord);
            if (isSuccess)
            {// 开户增值服务成功
             // 更新OpenVasFlag标识
                DeclareEntStatus declareEntStatus = new DeclareEntStatus();
                declareEntStatus.setApplyId(declareInfo.getApplyId());
                declareEntStatus.setOpenVasFlag(true);
                declareEntStatus.setOperator(optCustId);
                declareMapper.updateDeclareStatus(declareEntStatus);
            }
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "openVas", BSRespCode.BS_DECLARE_OPEN_VAS_ERROR.getCode()
                    + ":开启增值服务失败[param=" + declareInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_OPEN_VAS_ERROR, "开启增值服务失败[param=" + declareInfo + "]" + e);
        }
    }

    /**
     * 处理开启系统本地事务
     * 
     * @param declareInfo
     *            申报信息
     * @param apprParam
     *            开启系统信息
     * @param isFreeMemberEnt
     *            是否是免费的成员企业
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleOpenSysLocal(DeclareInfo declareInfo, ApprParam apprParam, boolean isFreeMemberEnt)
            throws HsException {
        try
        {
            // 免费成员企业不用记账，不用配置工具
            if (!isFreeMemberEnt)
            {
                // 全免时根据配置开关决定是否分配资源费
                if (ResFeeChargeMode.NONE.getCode() == declareInfo.getResFeeChargeMode())
                {
                    if (bsConfig.isAllocResFeeForFreeOpen())
                    {
                        // 生成资源费记账分解记录
                        resFeeResolveService.resolveResFee(declareInfo);
                    }
                }
                else
                {
                    // 欠费和全额时根据是否已付款来决定是否分配资源费
                    // 判断申报单是否已收费，已收费的才进行资源费分配及申报工具配置
                    if (declareInfo.getChargeFlag())
                    {
                        // 生成资源费记账分解记录
                        resFeeResolveService.resolveResFee(declareInfo);
                    }
                }
                if (!isBlank(declareInfo.getOrderNo()))
                {
                    // 更新申报订单客户号
                    Order order = orderService.getOrderByNo(declareInfo.getOrderNo());
                    HsAssert.notNull(order, BSRespCode.BS_NOT_QUERY_DATA, "没有查询到申报订单,declareInfo=" + declareInfo);
                    if (order.getPayStatus() == PayStatus.PAY_FINISH.getCode())
                    {
                        // 开启系统是申报订单如果是已支付成功的，更新订单状态为“已完成”
                        order.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());
                    }
                    // 开启系统时生成的客户号，更新申报订单的客户号
                    order.setCustId(declareInfo.getToEntCustId());
                    order.setPayChannel(null);
                    order.setPayStatus(null); // 不更新时设null，防止更新了支付时间
                    order.setBankTransNo(null);
                    // 开启系统时如果申报订单已付款,更新订单状态为已完成
                    orderService.updateOrderAllStatus(order);

                    if (ResFeeChargeMode.NONE.getCode() == declareInfo.getResFeeChargeMode()
                            && !bsConfig.isConfigToolForFreeOpen())
                    {
                        // 全免开启系统并且配置了全免时不配送工具，则不生成工具配置单
                    }
                    else
                    {
                        // 欠款开启、正常开启、全免但送工具
                        // 生成申报工具配置清单
                        ApplyOrderConfig applyOrderConfig = new ApplyOrderConfig();
                        applyOrderConfig.setOrderNo(declareInfo.getOrderNo());
                        applyOrderConfig.setEntCustId(declareInfo.getToEntCustId());
                        applyOrderConfig.setResFeeId(declareInfo.getResFeeId());
                        applyOrderConfig.setProvinceNo(declareInfo.getProvinceNo());
                        applyOrderConfig.setOperNo(apprParam.getOptName());
                        insideInvokeCall.addApplyOrderToolConfig(applyOrderConfig);
                    }
                }
            }

            DeclareLinkman declareLinkman = declareMapper.queryLinkmanByApplyId(declareInfo.getApplyId());

            // 生成合同
            this.buildContract(declareInfo, declareLinkman);

            // 生成证书
            if (CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType()
                    || (CustType.TRUSTEESHIP_ENT.getCode() == declareInfo.getToCustType() && ResType.ENTREPRENEURSHIP_RES
                            .getCode() == declareInfo.getToBuyResRange()))
            {// 服务公司或者托管企业创业资源
                this.buildCertificate(declareInfo, declareLinkman);
            }

            // 更新资源号使用状态
            if (CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType())
            {// 申报服务公司
             // 更新服务公司互生号使用状态及公司信息
                int count = entResNoService.updateServiceResNo(declareInfo.getToEntResNo(), ResStatus.USED.getCode(),
                        declareInfo.getToEntCustId(), declareInfo.getToEntCustName());
                HsAssert.isTrue(count == 1, BSRespCode.BS_DECLARE_INVALID_RES_NO, "企业互生号不可用（只有被占用的互生号才能启用）");
                // 生成该服务公司下的所有互生号（T_BS_ENT_RES）
                entResNoService.createEntResNo(declareInfo.getToEntResNo());
            }
            else
            {// 申报成员、托管企业
             // 更新成员、托管企业互生号使用状态
                int count = entResNoService.updateEntResNoStatus(declareInfo.getToCustType(), declareInfo
                        .getToEntResNo(), ResStatus.USED.getCode());
                HsAssert.isTrue(count == 1, BSRespCode.BS_DECLARE_INVALID_RES_NO, "企业互生号不可用（只有被占用的互生号才能启用）");
            }

            // 保存年费信息
            AnnualFeeInfo annualFeeInfo = new AnnualFeeInfo();
            annualFeeInfo.setEntCustId(declareInfo.getToEntCustId());
            annualFeeInfo.setEntResNo(declareInfo.getToEntResNo());
            annualFeeInfo.setEntCustName(declareInfo.getToEntCustName());
            annualFeeInfo.setCustType(declareInfo.getToCustType());
            annualFeeInfo.setCreatedby(apprParam.getOptCustId());
            String annualFeeDates[] = this.getAnnaulFeeEndDate(declareInfo.getResFeeId(), declareInfo.getOpenAccDate());
            if (null != annualFeeDates)
            {
                annualFeeInfo.setEndDate(annualFeeDates[0]);// 年费截止日期
                annualFeeInfo.setWarningDate(annualFeeDates[1]);// 缴费提示日期
            }
            annualFeeInfoService.saveBean(annualFeeInfo);

            // 更新申报状态
            DeclareEntStatus declareEntStatus = new DeclareEntStatus();
            declareEntStatus.setApplyId(declareInfo.getApplyId());
            declareEntStatus.setAppStatus(DeclareStatus.OPEN_OS_SUCCESS.getCode());
            declareEntStatus.setOperator(apprParam.getOptCustId());
            declareEntStatus.setToEntCustId(declareInfo.getToEntCustId());
            declareEntStatus.setOpenAccDate(declareInfo.getOpenAccDate());
            if (isFreeMemberEnt)
            { // 免费成员企业收费方式为全免
                declareEntStatus.setResFeeChargeMode(ResFeeChargeMode.NONE.getCode());
            }
            declareMapper.updateDeclareStatus(declareEntStatus);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), apprParam.getApplyId(),
                    DeclareBizAction.OPEN_SYS.getCode(), DeclareResult.OPEN_SYS_SUCCESS.getCode(), apprParam
                            .getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), null);
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "handleOpenSysLocal", BSRespCode.BS_DECLARE_OPEN_SYS_LOCAL_ERROR
                    .getCode()
                    + ":开启系统处理本地事务失败[param=declareInfo:" + declareInfo + ",apprParam:" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_OPEN_SYS_LOCAL_ERROR, "开启系统处理本地事务失败[param=declareInfo:"
                    + declareInfo + ",apprParam:" + apprParam + "]" + e);
        }
    }

    // 生成合同
    private void buildContract(DeclareInfo declareInfo, DeclareLinkman declareLinkman) {
        Contract contract = new Contract();
        contract.setApplyId(declareInfo.getApplyId());// 申报申请编号
        contract.setEntResNo(declareInfo.getToEntResNo());// 合同编号
        contract.setEntCustId(declareInfo.getToEntCustId());// 被申报者客户号
        contract.setEntCustName(declareInfo.getToEntCustName());// 被申报者名称
        contract.setCustType(declareInfo.getToCustType());// 被申报者客户类型
        contract.setLinkman(declareLinkman.getLinkman());// 被申报者联系人
        contract.setMobile(declareLinkman.getMobile());// 被申报者客户类型
        contract.setRegDate(StringUtils.left(declareInfo.getOpenAccDate(),10));// 注册日期

        VarContent varContent = contractService.buildVarContent(declareInfo, declareLinkman);

        contractService.genContract(contract, varContent, declareInfo.getToBuyResRange());
    }

    // 生成证书
    private void buildCertificate(DeclareInfo declareInfo, DeclareLinkman declareLinkman) {
        Certificate certificate = new Certificate();
        certificate.setEntResNo(declareInfo.getToEntResNo());
        certificate.setEntCustId(declareInfo.getToEntCustId());
        certificate.setEntCustName(declareInfo.getToEntCustName());
        certificate.setLinkman(declareLinkman.getLinkman());
        certificate.setMobile(declareLinkman.getMobile());
        certificate.setApplyId(declareInfo.getApplyId());

        LocalInfo localInfo = commonService.getAreaPlatInfo();

        //地区平台
        BsEntMainInfo platInfo = bsEntService.getMainInfoByResNo(localInfo.getPlatResNo());
        // 证书占位符替换内容
        CerVarContent cerVarContent = new CerVarContent();
        cerVarContent.setPlatName(platInfo.getEntName());// 发证单位
        cerVarContent.setSerResNo(declareInfo.getSpreadEntResNo());// 所属服务机构互生号
        cerVarContent.setSerEntName(declareInfo.getSpreadEntCustName());// 所属服务机构
        cerVarContent.setEntResNo(declareInfo.getToEntResNo());// 证书编号
        cerVarContent.setEntCustName(declareInfo.getToEntCustName());// 企业名称
        cerVarContent.setLicenseNo(declareInfo.getLicenseNo());// 证件号码
        Date sendDate = isBlank(declareInfo.getOpenAccDate()) ? new Date() : DateUtil.StringToDate(declareInfo
                .getOpenAccDate(), DateUtil.DEFAULT_DATE_TIME_FORMAT);
        cerVarContent.setSendDate(DateUtil.DateToString(sendDate, "yyyy 年 MM 月 dd 日"));// 发证日期
        cerVarContent.setCheckAddress(localInfo.getContractVerifyAddr());// 验证地址
        certificateService.buildCertificate(certificate, cerVarContent, declareInfo);
    }

    /**
     * 拒绝开启系统
     * 
     * @param declareInfo
     *            申报信息
     * @param apprParam
     *            开启系统信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void rejectOpenSys(DeclareInfo declareInfo, ApprParam apprParam) throws HsException {

        try
        {
            // 释放互生号
            entResNoService.updateEntResNoStatus(declareInfo.getToCustType(), declareInfo.getToEntResNo(),
                    ResStatus.NOT_USED.getCode());

            // 更新申报状态
            DeclareEntStatus declareEntStatus = new DeclareEntStatus();
            declareEntStatus.setApplyId(declareInfo.getApplyId());
            declareEntStatus.setAppStatus(DeclareStatus.OPEN_OS_REJECTED.getCode());
            declareEntStatus.setOperator(apprParam.getOptCustId());
            declareMapper.updateDeclareStatus(declareEntStatus);

            if (!isBlank(declareInfo.getOrderNo()))
            {
                // 更新申报订单状态为"已过期"
                orderService.updateOrderStatus(declareInfo.getOrderNo(), OrderStatus.IS_EXPIRE.getCode());
            }
            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), apprParam.getApplyId(),
                    DeclareBizAction.OPEN_SYS.getCode(), DeclareResult.OPEN_SYS_REJECTED.getCode(), apprParam
                            .getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), null);
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "rejectOpenSys", BSRespCode.BS_DECLARE_REJECT_OPEN_SYS_ERROR
                    .getCode()
                    + ":拒绝开启系统失败[param=declareInfo:" + declareInfo + ",apprParam:" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_REJECT_OPEN_SYS_ERROR, "拒绝开启系统失败[param=declareInfo:"
                    + declareInfo + ",apprParam:" + apprParam + "]" + e);
        }
    }

    // 获取年费截止日期
    private String[] getAnnaulFeeEndDate(String resFeeId, String openAccDate) {
        String dates[] = new String[2];
        String annualFeeEndDate;// 年费截止日期
        String annualFeeWarningDate;// 年费缴费提示日期
        ResFee resFee = resFeeService.getResFeeBean(resFeeId);
        if (resFee.getFreeAnnualFeeCnt() == -1)
        {// 不需要缴年费
            return null;
        }
        else
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.StringToDateTime(openAccDate));// 开启系统时间
            cal.add(Calendar.YEAR, +resFee.getFreeAnnualFeeCnt());// 日期加免年费年数
            cal.add(Calendar.DAY_OF_YEAR, -1);// 减一天
            Date endDate = cal.getTime();
            annualFeeEndDate = DateUtil.DateTimeToString(endDate);// 年费截止日期

            cal.add(Calendar.MONTH, -2);// 日期减2个月
            Date warningDate = cal.getTime();
            annualFeeWarningDate = DateUtil.DateTimeToString(warningDate);// 年费缴费提示日期
            dates[0] = annualFeeEndDate;
            dates[1] = annualFeeWarningDate;

            return dates;
        }
    }
}
