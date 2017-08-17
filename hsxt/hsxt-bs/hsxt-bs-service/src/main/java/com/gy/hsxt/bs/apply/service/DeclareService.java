/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.apply.bean.DeclareEntStatus;
import com.gy.hsxt.bs.apply.bean.DeclareParam;
import com.gy.hsxt.bs.apply.bean.ExpiryDeclare;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.apply.interfaces.IFilingService;
import com.gy.hsxt.bs.apply.interfaces.IOpenSystemService;
import com.gy.hsxt.bs.apply.interfaces.IResFeeResolveService;
import com.gy.hsxt.bs.apply.mapper.DeclareMapper;
import com.gy.hsxt.bs.apply.mapper.ResFeeAllocMapper;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.bean.bm.Increment;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeTool;
import com.gy.hsxt.bs.bm.interfaces.IBsUfRegionPackService;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.apply.*;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.enumtype.quota.ResStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IEntResNoService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.resfee.interfaces.IResFeeService;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 申报管理
 *
 * @version V1.0
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: DeclareService
 * @Description: 申报管理
 * @author: xiaofl
 * @date: 2015-11-19 上午9:11:36
 */
@Service
public class DeclareService implements IBSDeclareService, IDeclareService {

    @Resource
    private DeclareMapper declareMapper;

    @Resource
    private IOperationService operationService;

    @Resource
    private IFilingService filingService;

    @Resource
    private IResFeeService resFeeService;

    @Resource
    private IOrderService orderService;

    @Resource
    private INtService ntService;

    @Resource
    private IBsUfRegionPackService bsUfRegionPackService;

    @Resource
    private BsConfig bsConfig;

    @Resource
    private IEntResNoService entResNoService;

    @Resource
    private IOpenSystemService openSystemService;

    @Resource
    private IResFeeResolveService resFeeResolveService;

    @Resource
    private ICommonService commonService;

    @Resource
    private ITaskService taskService;

    @Resource
    private IUCBsEntService bsEntService;

    @Resource
    private ResFeeAllocMapper resFeeAllocMapper;

    /**
     * 创建企业申报
     *
     * @param declareRegInfo 申报信息
     * @return 返回申请编号
     * @throws HsException
     */
    @Override
    @Transactional
    public String createDeclare(DeclareRegInfo declareRegInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "createDeclare", "input param:", declareRegInfo + "");
        HsAssert.notNull(declareRegInfo, BSRespCode.BS_PARAMS_NULL, "申报信息[declareRegInfo]为null");
        boolean check = HsResNoUtils.isServiceResNo(declareRegInfo.getToCustType())
                || HsResNoUtils.isTrustResNo(declareRegInfo.getToCustType())
                || HsResNoUtils.isMemberResNo(declareRegInfo.getToCustType());
        HsAssert.isTrue(check, BSRespCode.BS_PARAMS_TYPE_ERROR, "被申报企业类型[toCustType]错误");
        HsAssert.isTrue(StringUtils.isNoneBlank(declareRegInfo.getToEntCustName(), declareRegInfo.getFrEntCustId(),
                declareRegInfo.getFrEntResNo(), declareRegInfo.getFrEntCustName(), declareRegInfo.getSpreadEntCustId(),
                declareRegInfo.getSpreadEntResNo(), declareRegInfo.getSpreadEntCustName(), declareRegInfo
                        .getCountryNo(), declareRegInfo.getProvinceNo(), declareRegInfo.getCityNo()),
                BSRespCode.BS_PARAMS_EMPTY,
                "参数不能为空[toCustType,toEntCustName,frEntCustId,frEntResNo,frEntCustName,spreadEntCustId,spreadEntResNo,"
                        + "spreadEntCustName,countryNo,provinceNo,cityNo]");
        if (declareRegInfo.getToSelectMode() == null) {
            declareRegInfo.setToSelectMode(PickMode.SEQUENTIAL.ordinal());
        }

        // 服务公司申报服务公司时,只在申报提交时检查企业是否被该服务公司报备过，参考bug16469
        if (HsResNoUtils.isServiceResNo(declareRegInfo.getFrEntResNo()) && declareRegInfo.getToCustType() != null
                && CustType.SERVICE_CORP.getCode() == declareRegInfo.getToCustType()) {
            boolean isFilling = filingService.isFiling(declareRegInfo.getFrEntResNo(), declareRegInfo.getToEntCustName());
            HsAssert.isTrue(isFilling, BSRespCode.BS_DECLARE_NO_FILING, "该服务公司未报备该被申报企业");
        }

        // 校验申报信息
        this.validateDeclare(declareRegInfo);
        // 校验增值设置
        this.validateBMSetting(declareRegInfo);

        String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
        try {
            // 申报者管理公司资料
            boolean isAreaPlatResNo = HsResNoUtils.isAreaPlatResNo(declareRegInfo.getFrEntResNo());
            if (!isAreaPlatResNo) {// 非平台申报
                String frMEntResNo = declareRegInfo.getFrEntResNo().substring(0, 2) + "000000000";
                BsEntMainInfo bsEntMainInfo = commonService.getEntMainInfoToUc(frMEntResNo, null);
                declareRegInfo.setFrMEntResNo(frMEntResNo);
                declareRegInfo.setFrMCorpName(bsEntMainInfo == null ? "" : bsEntMainInfo.getEntName());
            }
            declareRegInfo.setApplyId(applyId);
            declareMapper.createDeclare(declareRegInfo);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createDeclare", BSRespCode.BS_DECLARE_SAVE_REG_INFO_ERROR
                    .getCode()
                    + ":保存申报企业系统注册信息失败[param=" + declareRegInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_REG_INFO_ERROR, "保存申报企业系统注册信息失败[param=" + declareRegInfo
                    + "]" + e);
        }

        return applyId;
    }

    /**
     * 服务公司修改申报信息
     *
     * @param declareRegInfo 申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void serviceModifyDeclare(DeclareRegInfo declareRegInfo) throws HsException {
//        DeclareInfo info = declareMapper.queryDeclareById(declareRegInfo.getApplyId());
//        HsAssert.notNull(info, BSRespCode.BS_PARAMS_NULL, "对应的申报信息不存在");
        // 服务公司申报服务公司时,只在申报提交时检查企业是否被该服务公司报备过
        if (HsResNoUtils.isServiceResNo(declareRegInfo.getFrEntResNo()) && declareRegInfo.getToCustType() != null
                && CustType.SERVICE_CORP.getCode() == declareRegInfo.getToCustType()) {
            boolean isFilling = filingService.isFiling(declareRegInfo.getFrEntResNo(), declareRegInfo.getToEntCustName());
            HsAssert.isTrue(isFilling, BSRespCode.BS_DECLARE_NO_FILING, "该服务公司未报备该被申报企业");
        }
        modifyDeclare(declareRegInfo);
    }

    @Override
    @Transactional
    public void modifyDeclare(DeclareRegInfo declareRegInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyDeclare", "input param:", declareRegInfo + "");
        if (null == declareRegInfo || StringUtils.isBlank(declareRegInfo.getApplyId())
                || null == declareRegInfo.getToCustType() || StringUtils.isBlank(declareRegInfo.getToEntCustName())
                || StringUtils.isBlank(declareRegInfo.getCountryNo())
                || StringUtils.isBlank(declareRegInfo.getProvinceNo())
                || StringUtils.isBlank(declareRegInfo.getCityNo())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL,
                    "参数不能为空[applyId,toCustType,toEntCustName,countryNo,provinceNo,cityNo]");
        }
        try {
            // 倘若修改了拟用互生号 则需要释放原互生号，占用新互生号
/*            if (StringUtils.isNotEmpty(declareRegInfo.getToEntResNo())) {
               DeclareInfo info =  declareMapper.queryDeclareById(declareRegInfo.getApplyId());
                if (StringUtils.isNotEmpty(info.getToEntResNo())) {
                    if (!info.getToEntResNo().equals(declareRegInfo.getToEntResNo())) {
                        //占用新互生号
                        entResNoService.updateEntResNoStatus(info.getToCustType(), declareRegInfo.getToEntResNo(), ResStatus.OCCUPIED.getCode());
                        //释放原互生号
                        entResNoService.updateEntResNoStatus(info.getToCustType(), info.getToEntResNo(), ResStatus.NOT_USED.getCode());
                    }
                }else{
                    //占用新互生号
                    entResNoService.updateEntResNoStatus(info.getToCustType(), declareRegInfo.getToEntResNo(), ResStatus.OCCUPIED.getCode());
                }
            }*/
            declareMapper.updateDeclare(declareRegInfo);
            createModifyOptHis(declareRegInfo.getApplyId(), declareRegInfo);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "modifyDeclare", BSRespCode.BS_DECLARE_SAVE_REG_INFO_ERROR
                    .getCode()
                    + ":保存申报企业系统注册信息失败[param=" + declareRegInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_REG_INFO_ERROR, "保存申报企业系统注册信息失败[param=" + declareRegInfo
                    + "]" + e);
        }
    }

    /**
     * 管理公司选号（申报服务公司）
     *
     * @param declareRegInfo 申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void managePickResNo(DeclareRegInfo declareRegInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "managePickResNo", "input param:", declareRegInfo + "");
        HsAssert.hasText(declareRegInfo.getApplyId(), BSRespCode.BS_PARAMS_NULL, "申请编号[applyId]为空");
        HsAssert.hasText(declareRegInfo.getToEntResNo(), BSRespCode.BS_DECLARE_NO_PICK_RESNO, "被申报企业互生号[toEntResNo]为空");
        HsAssert.hasText(declareRegInfo.getOptCustId(), BSRespCode.BS_PARAMS_NULL, "操作者客户号[optCustId]为空");
        HsAssert.isTrue(PickMode.checkMode(declareRegInfo.getToSelectMode()), BSRespCode.BS_DECLARE_PICK_RESNO_ERROR,
                "选号方式错误，请使用{PickMode}的ordinal()方法");
        try {
            DeclareInfo info = declareMapper.queryDeclareById(declareRegInfo.getApplyId());
            HsAssert.notNull(info, BSRespCode.BS_PARAMS_NULL, "对应的申报信息不存在");
            if (StringUtils.isBlank(info.getToEntResNo())) {
                if (StringUtils.isBlank(declareRegInfo.getToEntResNo())) {// 默认顺序选择互生号
                    List<String> resNos = entResNoService.getSerResNoList(info.getCountryNo(), info.getProvinceNo(),
                            info.getCityNo(), ResStatus.NOT_USED.getCode());
                    HsAssert.notEmpty(resNos, BSRespCode.BS_DECLARE_RESNO_NULL_IN_CITY, "该城市的配额已用完，没有可用的互生号");
                    String toEntResNo = resNos.get(0);
                    declareRegInfo.setToSelectMode(PickMode.SEQUENTIAL.ordinal());
                    declareRegInfo.setToEntResNo(toEntResNo);
                }
                //占用新互生号
                int count = entResNoService.updateEntResNoStatus(info.getToCustType(), declareRegInfo.getToEntResNo(), ResStatus.OCCUPIED.getCode());
                // 未使用，则占用该互生号
                HsAssert.isTrue(count == 1, BSRespCode.BS_DECLARE_INVALID_RES_NO, "被申报企业互生号[" + declareRegInfo.getToEntResNo() + "]不可用（已使用或已占用）");
            } else {// 倘若修改了拟用互生号 则需要释放原互生号，占用新互生号
                if (!info.getToEntResNo().equals(declareRegInfo.getToEntResNo())) {
                    //占用新互生号
                    int count = entResNoService.updateEntResNoStatus(info.getToCustType(), declareRegInfo.getToEntResNo(), ResStatus.OCCUPIED.getCode());
                    // 未使用，则占用该互生号
                    HsAssert.isTrue(count == 1, BSRespCode.BS_DECLARE_INVALID_RES_NO, "被申报企业互生号[" + declareRegInfo.getToEntResNo() + "]不可用（已使用或已占用）");
                    //释放原互生号
                    entResNoService.updateEntResNoStatus(info.getToCustType(), info.getToEntResNo(), ResStatus.NOT_USED.getCode());
                }
            }
            declareMapper.updateSerResNo(declareRegInfo);
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "managePickResNo", BSRespCode.BS_DECLARE_PICK_RESNO_ERROR
                    .getCode()
                    + ":选互生号失败[param=" + declareRegInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_PICK_RESNO_ERROR, "选互生号失败[param=applyId:" + declareRegInfo
                    + "]" + e);
        }
    }

    /**
     * 管理公司修改申报信息
     *
     * @param declareRegInfo 申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void manageModifyDeclare(DeclareRegInfo declareRegInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "manageModifyDeclare", "input param:", declareRegInfo + "");
        if (null == declareRegInfo || StringUtils.isBlank(declareRegInfo.getApplyId())
                || null == declareRegInfo.getToCustType() || StringUtils.isBlank(declareRegInfo.getToEntCustName())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        try {
            // 管理公司不能修改推荐企业，为了防止前端更新了推荐企业，设为null
            declareRegInfo.setSpreadEntCustId(null);
            declareRegInfo.setSpreadEntCustName(null);
            declareRegInfo.setSpreadEntResNo(null);
            // 修改申报信息
            declareMapper.updateDeclare(declareRegInfo);
            createModifyOptHis(declareRegInfo.getApplyId(), declareRegInfo);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "manageModifyDeclare", BSRespCode.BS_DECLARE_SAVE_REG_INFO_ERROR
                    .getCode()
                    + ":保存申报企业系统注册信息失败[param=" + declareRegInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_REG_INFO_ERROR, "保存申报企业系统注册信息失败[param=" + declareRegInfo
                    + "]" + e);
        }
    }

    /**
     * 地区平台修改申报信息
     *
     * @param declareRegInfo 申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void platModifyDeclare(DeclareRegInfo declareRegInfo) throws HsException {
        modifyDeclare(declareRegInfo);
    }

    /**
     * 创建申报企业信息
     *
     * @param declareBizRegInfo 申报企业信息
     * @return 返回申请编号
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareBizRegInfo createDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "createDeclareEnt", "input param:", declareBizRegInfo+"");
        if (null == declareBizRegInfo || StringUtils.isBlank(declareBizRegInfo.getApplyId())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        DeclareAptitude licenseAptitude = declareBizRegInfo.getLicenseAptitude();

        if(licenseAptitude!=null){
//            HsAssert.notNull(licenseAptitude, BSRespCode.BS_PARAMS_NULL, "营业执照扫描件[licenseAptitude]为null");
            HsAssert.hasText(licenseAptitude.getFileId(), BSRespCode.BS_PARAMS_EMPTY, "资质文件编号[fileId]为空");
            HsAssert.isTrue(AptitudeType.BIZ_LICENSE_CRE.getCode()==licenseAptitude.getAptitudeType(),BSRespCode.BS_PARAMS_TYPE_ERROR, "资质类型[aptitudeType]类型错误,营业执照扫描件BIZ_LICENSE_CRE(3)");
            HsAssert.hasText(declareBizRegInfo.getOptCustId(), BSRespCode.BS_PARAMS_EMPTY, "操作员客户号[optCustId]为空");
            licenseAptitude.setOptCustId(declareBizRegInfo.getOptCustId());
        }

        try {
            //保存工商登记信息
            declareMapper.updateDeclareEnt(declareBizRegInfo);

            if (licenseAptitude != null) {
                licenseAptitude.setAptitudeId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

                licenseAptitude.setApplyId(declareBizRegInfo.getApplyId());

                declareMapper.createOneAptitude(licenseAptitude);
            }

            return declareBizRegInfo;

        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createDeclareEnt", BSRespCode.BS_DECLARE_SAVE_BIZ_INFO_ERROR .getCode()
                    + ":保存申报企业工商登记信息失败[param=" + declareBizRegInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_BIZ_INFO_ERROR, "保存申报企业工商登记信息失败[param="
                    + declareBizRegInfo + "]" + e);
        }
    }

    /**
     * 服务公司修改申报企业信息
     *
     * @param declareBizRegInfo 申报企业信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareBizRegInfo serviceModifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException {
        return modifyDeclareEnt(declareBizRegInfo);
    }

    @Override
    @Transactional
    public DeclareBizRegInfo modifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyDeclareEnt", "input param:", declareBizRegInfo + "");
        HsAssert.notNull(declareBizRegInfo, BSRespCode.BS_PARAMS_NULL, "申报企业工商登记信息[declareBizRegInfo]为null");
        HsAssert.hasText(declareBizRegInfo.getApplyId(), BSRespCode.BS_PARAMS_EMPTY, "申报ID[applyId]为空");

        DeclareAptitude licenseAptitude = declareBizRegInfo.getLicenseAptitude();
        if (licenseAptitude != null) {
            HsAssert.hasText(licenseAptitude.getFileId(), BSRespCode.BS_PARAMS_EMPTY, "营业执照扫描件编号[fileId]为空");
//            HsAssert.hasText(licenseAptitude.getAptitudeId(), BSRespCode.BS_PARAMS_EMPTY, "附件ID[aptitudeId]为空");
            HsAssert.isTrue(AptitudeType.BIZ_LICENSE_CRE.getCode()==licenseAptitude.getAptitudeType(),BSRespCode.BS_PARAMS_TYPE_ERROR, "资质类型[aptitudeType]类型错误,营业执照扫描件(3)");
            HsAssert.hasText(declareBizRegInfo.getOptCustId(), BSRespCode.BS_PARAMS_EMPTY, "操作员客户号[optCustId]为空");
            licenseAptitude.setOptCustId(declareBizRegInfo.getOptCustId());
        }
        try {
            declareMapper.updateDeclareEnt(declareBizRegInfo);
            createModifyOptHis(declareBizRegInfo.getApplyId(), declareBizRegInfo);
            if (licenseAptitude != null) {
                DeclareAptitude licenseAptitudeDB = declareMapper.queryOneAptitude(declareBizRegInfo.getApplyId(), AptitudeType.BIZ_LICENSE_CRE.getCode());
                if (licenseAptitudeDB == null) {
                    licenseAptitude.setApplyId(declareBizRegInfo.getApplyId());
                    licenseAptitude.setAptitudeId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                    declareMapper.createOneAptitude(licenseAptitude);
                }else{
                    licenseAptitude.setAptitudeId(licenseAptitudeDB.getAptitudeId());
                    declareMapper.updateOneAptitude(licenseAptitude);
                }
            }
            return declareBizRegInfo;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "modifyDeclareEnt", "保存申报企业工商登记信息失败[param=" + declareBizRegInfo+ "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_BIZ_INFO_ERROR, "保存申报企业工商登记信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 管理公司修改申报企业信息
     *
     * @param declareBizRegInfo 申报企业信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareBizRegInfo manageModifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException {
      return   modifyDeclareEnt(declareBizRegInfo);
    }

    /**
     * 地区平台修改申报企业信息
     *
     * @param declareBizRegInfo 申报企业信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareBizRegInfo platModifyDeclareEnt(DeclareBizRegInfo declareBizRegInfo) throws HsException {
       return modifyDeclareEnt(declareBizRegInfo);
    }

    /**
     * 创建联系人信息
     *
     * @param declareLinkman 联系人信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareLinkman createLinkman(DeclareLinkman declareLinkman) throws HsException {
        BizLog.biz(this.getClass().getName(), "createLinkman", "input param:", declareLinkman + "");
        HsAssert.notNull(declareLinkman, BSRespCode.BS_PARAMS_NULL, "联系人[declareLinkman]为null");
        HsAssert.isTrue(StringUtils.isNoneBlank(declareLinkman.getApplyId(), declareLinkman.getMobile()), BSRespCode.BS_PARAMS_NULL, "参数[applyId,mobile]不能为空");

        DeclareInfo declareInfo = declareMapper.queryDeclareById(declareLinkman.getApplyId());
        DeclareAptitude protocolAptitude = declareLinkman.getProtocolAptitude();
        if (protocolAptitude != null &&declareInfo.getToBuyResRange() != null && ResType.ENTREPRENEURSHIP_RES.getCode() == declareInfo.getToBuyResRange()) {
//            HsAssert.notNull(protocolAptitude, BSRespCode.BS_PARAMS_NULL, "创业帮扶协议附件[protocolAptitude]为null");
            HsAssert.hasText(protocolAptitude.getFileId(), BSRespCode.BS_PARAMS_EMPTY, "营业执照扫描件编号[fileId]为空");
            HsAssert.isTrue(AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode()==protocolAptitude.getAptitudeType(),BSRespCode.BS_PARAMS_TYPE_ERROR, "资质类型[aptitudeType]类型错误,创业帮扶协议附件(8)");
            HsAssert.hasText(declareLinkman.getOptCustId(), BSRespCode.BS_PARAMS_EMPTY, "操作员客户号[optCustId]为空");
            protocolAptitude.setOptCustId(declareLinkman.getOptCustId());
        }

        try {
            declareMapper.createLinkman(declareLinkman);
            if (protocolAptitude != null &&declareInfo.getToBuyResRange() != null && ResType.ENTREPRENEURSHIP_RES.getCode() == declareInfo.getToBuyResRange()) {
                protocolAptitude.setAptitudeId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

                protocolAptitude.setApplyId(declareLinkman.getApplyId());

                declareMapper.createOneAptitude(protocolAptitude);
            }
            return declareLinkman;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createLinkman", "保存申报企业联系信息失败[param=" + declareLinkman + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_CONTACT_INFO_ERROR, "保存申报企业联系信息失败，原因：" + e);
        }
    }

    /**
     * 修改联系人信息
     *
     * @param declareLinkman 联系人信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareLinkman modifyLinkman(DeclareLinkman declareLinkman) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyLinkman", "input param:", declareLinkman + "");
        HsAssert.notNull(declareLinkman, BSRespCode.BS_PARAMS_NULL, "联系人[declareLinkman]为null");
        HsAssert.isTrue(StringUtils.isNoneBlank(declareLinkman.getApplyId(), declareLinkman.getMobile()),BSRespCode.BS_PARAMS_NULL, "参数[applyId,mobile]不能为空");
        DeclareAptitude protocolAptitude = declareLinkman.getProtocolAptitude();
        if (protocolAptitude != null) {
//            HsAssert.hasText(protocolAptitude.getAptitudeId(), BSRespCode.BS_PARAMS_EMPTY, "附件ID[aptitudeId]为空");
            HsAssert.hasText(protocolAptitude.getFileId(), BSRespCode.BS_PARAMS_EMPTY, "营业执照扫描件编号[fileId]为空");
            HsAssert.isTrue(AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode()==protocolAptitude.getAptitudeType(),BSRespCode.BS_PARAMS_TYPE_ERROR, "资质类型[aptitudeType]类型错误,创业帮扶协议附件(8)");
            HsAssert.hasText(declareLinkman.getOptCustId(), BSRespCode.BS_PARAMS_EMPTY, "操作员客户号[optCustId]为空");
            protocolAptitude.setOptCustId(declareLinkman.getOptCustId());
        }
        try {
            declareMapper.updateLinkman(declareLinkman);
            createModifyOptHis(declareLinkman.getApplyId(), declareLinkman);
            if (protocolAptitude != null) {
                DeclareAptitude protocolAptitudeDB = declareMapper.queryOneAptitude(declareLinkman.getApplyId(), AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode());
                if (protocolAptitudeDB ==null) {
                    protocolAptitude.setAptitudeId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                    protocolAptitude.setApplyId(declareLinkman.getApplyId());
                    declareMapper.createOneAptitude(protocolAptitude);
                }else{
                    protocolAptitude.setAptitudeId(protocolAptitudeDB.getAptitudeId());
                    declareMapper.updateOneAptitude(protocolAptitude);
                }
            }
            return declareLinkman;
        } catch (Exception e) {
            SystemLog
                    .error(this.getClass().getName(), "modifyLinkman", "修改申报企业联系信息失败[param=" + declareLinkman + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_CONTACT_INFO_ERROR, "保存申报企业联系信息失败，原因：" + e);
        }
    }

    /**
     * 服务公司修改联系人信息
     *
     * @param declareLinkman 联系人信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareLinkman serviceModifyLinkman(DeclareLinkman declareLinkman) throws HsException {
       return modifyLinkman(declareLinkman);
    }

    /**
     * 管理公司修改联系人信息
     *
     * @param declareLinkman 联系人信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareLinkman manageModifyLinkman(DeclareLinkman declareLinkman) throws HsException {
        return modifyLinkman(declareLinkman);
    }

    /**
     * 地区平台修改联系人信息
     *
     * @param declareLinkman 联系人信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareLinkman platModifyLinkman(DeclareLinkman declareLinkman) throws HsException {
        return modifyLinkman(declareLinkman);
    }

    /**
     * 创建银行信息,申报时一个申报单只允许设置一个银行账户，所以银行账户ID用申报编号以保证唯一性
     *
     * @param declareBank 银行信息
     * @return 银行账户ID
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public String createBank(DeclareBank declareBank) throws HsException {
        BizLog.biz(this.getClass().getName(), "createBank", "input param:", declareBank + "");
        HsAssert.notNull(declareBank, BSRespCode.BS_PARAMS_NULL, "申报银行信息[declareBank]为null");
        HsAssert.hasText(declareBank.getApplyId(), BSRespCode.BS_PARAMS_EMPTY, "申报ID[applyId]不能为空");
        try {
            declareBank.setAccountId(declareBank.getApplyId());
            declareMapper.createBank(declareBank);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createBank", "保存申报企业银行信息失败[param=" + declareBank + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_BANK_INFO_ERROR, "保存申报企业银行信息失败，原因：" + e.getMessage());
        }
        return declareBank.getApplyId();
    }

    @Override
    @Transactional
    @Deprecated
    public void modifyDeclareBank(DeclareBank declareBank) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyDeclareBank", "input param:", declareBank + "");
        HsAssert.notNull(declareBank, BSRespCode.BS_PARAMS_NULL, "申报银行信息[declareBank]为null");
        HsAssert.hasText(declareBank.getAccountId(), BSRespCode.BS_PARAMS_EMPTY, "账号ID[accountId]为空");
        try {
            declareMapper.updateBank(declareBank);
            createModifyOptHis(declareBank.getApplyId(), declareBank);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "modifyDeclareBank", "保存申报企业银行信息失败[param=" + declareBank + "]",
                    e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_BANK_INFO_ERROR, "保存申报企业银行信息失败，原因：" + e.getMessage());
        }
    }

    /**
     * 服务公司修改银行信息
     *
     * @param declareBank 银行信息
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public void serviceModifyBank(DeclareBank declareBank) throws HsException {
        modifyDeclareBank(declareBank);
    }

    /**
     * 管理公司保存银行信息
     *
     * @param declareBank 银行信息
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public String manageSaveBank(DeclareBank declareBank) throws HsException {
        BizLog.biz(this.getClass().getName(), "manageSaveBank", "input param:", declareBank + "");
        HsAssert.notNull(declareBank, BSRespCode.BS_PARAMS_NULL, "申报银行信息[declareBank]为null");
        HsAssert.hasText(declareBank.getApplyId(), BSRespCode.BS_PARAMS_EMPTY, "申报ID[applyId]为空");
        String acId;
        try {
            if (StringUtils.isBlank(declareBank.getAccountId())) {// 新增
                acId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
                declareBank.setAccountId(acId);
                declareMapper.createBank(declareBank);
            } else {// 更新
                acId = declareBank.getAccountId();
                // 修改企业基本信息
                declareMapper.updateBank(declareBank);
            }
            createModifyOptHis(declareBank.getApplyId(), declareBank);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "manageSaveBank", "保存申报企业银行信息失败[param=" + declareBank + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_BANK_INFO_ERROR, "保存申报企业银行信息失败，原因：" + e.getMessage());
        }
        return acId;
    }

    /**
     * 地区平台修改银行信息
     *
     * @param declareBank 银行信息
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public void platModifyBank(DeclareBank declareBank) throws HsException {
        modifyDeclareBank(declareBank);
    }

    /**
     * 服务公司保存申报企业资质附件信息
     *
     * @param declareAptitudes 申报企业资质附件信息 必填
     * @param optInfo          操作员信息
     * @return 申报企业资质附件列表
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public List<DeclareAptitude> serviceSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo)
            throws HsException {
        return this.serviceSaveDeclareAptitude(declareAptitudes, optInfo, null);
    }

    /**
     * 管理公司保存申报企业资质附件信息
     *
     * @param declareAptitudes 申报企业资质附件信息 必填
     * @param optInfo          操作员信息
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public List<DeclareAptitude> manageSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo)
            throws HsException {
        return manageSaveDeclareAptitude(declareAptitudes, optInfo, null);
    }

    /**
     * 地区平台保存申报企业资质附件信息
     *
     * @param declareAptitudes 申报企业资质附件信息 必填
     * @return 申报企业资质附件列表
     * @throws HsException
     */
    @Override
    @Transactional
    @Deprecated
    public List<DeclareAptitude> platSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo)
            throws HsException {
        return platSaveDeclareAptitude(declareAptitudes, optInfo, null);
    }

    // 保存申报企业资质附件信息
    private List<DeclareAptitude> saveDeclareApt(List<DeclareAptitude> declareAptitudes, OptInfo optInfo,
                                                 String remark, int level) {
        String applyId = "";
        List<DeclareAptitude> addList = new ArrayList<>();
        List<DeclareAptitude> updateList = new ArrayList<>();
        // 变量资质附件
        for (DeclareAptitude declareAptitude : declareAptitudes) {
            applyId = declareAptitude.getApplyId();
            HsAssert.hasText(applyId, BSRespCode.BS_PARAMS_EMPTY, "申报ID[applyId]为空");
            if (StringUtils.isBlank(declareAptitude.getAptitudeId())) {// 新增
                HsAssert.hasText(declareAptitude.getFileId(), BSRespCode.BS_PARAMS_EMPTY, "资质文件编号[fileId]为空");
                HsAssert.isTrue(AptitudeType.checkType(declareAptitude.getAptitudeType()),BSRespCode.BS_PARAMS_TYPE_ERROR, "资质类型[aptitudeType]类型错误");
                HsAssert.hasText(declareAptitude.getOptCustId(), BSRespCode.BS_PARAMS_EMPTY, "操作员客户号[optCustId]为空");
                declareAptitude.setAptitudeId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                addList.add(declareAptitude);
            } else {// 修改
                updateList.add(declareAptitude);
            }
        }

        if (addList.size() > 0) {
            declareMapper.createAptitude(addList);
        }
        if (updateList.size() > 0) {
            declareMapper.updateAptitude(updateList);
        }

        // 保存附件时更新报备信息
        declareMapper.updateDeclareRemark(applyId, remark);

        // 记录操作历史
        createModifyOptHis(applyId, optInfo);

        return declareMapper.queryAptitudeByApplyId(applyId);
    }

    /**
     * 提交申报
     *
     * @param applyId 申请编号 必填
     * @param optInfo 操作员信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void submitDeclare(String applyId, OptInfo optInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "submitDeclare", "input param:", "applyId=" + applyId + "," + optInfo);
        if (StringUtils.isBlank(applyId) || null == optInfo) {
            throw new HsException(RespCode.PARAM_ERROR, "参数不能为空");
        }
        try {
            DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
            // 服务公司申报服务公司时,只在申报提交时检查企业是否被该服务公司报备过，参考bug16469
            if (HsResNoUtils.isServiceResNo(declareInfo.getFrEntResNo()) && declareInfo.getToCustType() != null
                    && CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType()) {
                boolean isFilling = filingService.isFiling(declareInfo.getFrEntResNo(), declareInfo.getToEntCustName());
                HsAssert.isTrue(isFilling, BSRespCode.BS_DECLARE_NO_FILING, "该服务公司未报备该被申报企业");
            }

            DeclareRegInfo declareRegInfo = new DeclareRegInfo();
            BeanUtils.copyProperties(declareInfo, declareRegInfo);
            // 校验申报信息
            this.validateDeclare(declareRegInfo);
            // 检验增值设置
            this.validateBMSetting(declareRegInfo);

            Integer custType = declareInfo.getToCustType();// 客户类型
            String toEntResNo = declareInfo.getToEntResNo();// 被申报企业拟用互生号
            boolean isAreaPlatResNo = HsResNoUtils.isAreaPlatResNo(declareInfo.getFrEntResNo());

            if (CustType.SERVICE_CORP.getCode() == custType) {// 服务公司

                if (!StringUtils.isBlank(toEntResNo)) {// 互生号不为空（驳回后再次提交）
                    // 把拟用互生号设为空
                    declareMapper.emptyToEntResNo(applyId);
                }
            } else {// 成员企业或托管企业
                // 企业互生号使用情况的校验
                // boolean valid = entResNoService.checkValidEntResNo(custType,
                // toEntResNo);
                int count = entResNoService.updateEntResNoStatus(custType, toEntResNo, ResStatus.OCCUPIED.getCode());
                // 未使用，则占用该互生号
                HsAssert.isTrue(count == 1, BSRespCode.BS_DECLARE_INVALID_RES_NO, "企业互生号不可用（已使用或已占用）");
            }

            DeclareResult declareResult;// 业务结果
            int appStatus;
            if (isAreaPlatResNo) {// 平台申报
                declareResult = DeclareResult.SUBMIT_BY_PLAT;
                // 平台申报无需内审
                appStatus = DeclareStatus.WAIT_TO_FIRST_APPR.getCode();// 待管理公司初审
            } else {// 服务公司申报
                declareResult = DeclareResult.SUBMIT_BY_SERVICE;
                appStatus = DeclareStatus.WAIT_TO_INNER_APPR.getCode(); // 待服务公司内审
            }
            List<DeclareAptitude> declareAptitudes = declareMapper.queryAptitudeByApplyId(applyId);
            // 申报时企业资质文件不能为空
            HsAssert.notEmpty(declareAptitudes, BSRespCode.BS_DECLARE_FILE_NOT_UPDATE, "申报时企业资质文件不能为空");
            // 缺少营业执照扫描件
            boolean needBizLicenseFile = true;
            // 缺少创业帮扶文件,仅适用于创业资源VENTURE_BEFRIEND_PROTOCOL
            boolean needVentureBefriendProtocol = true;
            // 遍历申报单所有资质附件，如果某类附件已存在，就标记为不缺少
            for (DeclareAptitude declareAptitude : declareAptitudes) {
                 if (AptitudeType.BIZ_LICENSE_CRE.getCode() == declareAptitude.getAptitudeType()) {
                    needBizLicenseFile = false;
                }  else if (AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode() == declareAptitude.getAptitudeType()) {
                    needVentureBefriendProtocol = false;
                }
            }
            // 所有企业申报提交时校验法人证件正、反面文件，营业执照，组织机构代码，税务登记图片是否已上传,缺少任意一个都报错
            if ( needBizLicenseFile) {
                throw new HsException(BSRespCode.BS_DECLARE_NEED_BASE_FILE);
            }

            if (declareInfo.getToCustType() == CustType.TRUSTEESHIP_ENT.getCode()
                    && declareInfo.getToBuyResRange() == StartResType.PIONEER.getCode()) {
                // 托管企业创业资源申报提交时校验创业帮扶文件是否已上传
                if (needVentureBefriendProtocol) {
                    throw new HsException(BSRespCode.BS_DECLARE_NEED_VENTURE_BEFRIEND_PROTOCOL);
                }
            }

            //添加企业联系人的校验
            DeclareLinkman linkman = declareMapper.queryLinkmanByApplyId(applyId);

            HsAssert.notNull(linkman,BSRespCode.BS_DECLARE_LINK_MAN_NULL);

            HsAssert.hasText(linkman.getLinkman(), BSRespCode.BS_DECLARE_LINK_MAN_NAME_EMPTY);

            HsAssert.hasText(linkman.getMobile(), BSRespCode.BS_DECLARE_LINK_MAN_PHONE_EMPTY);

            HsAssert.hasText(linkman.getCertificateFile(), BSRespCode.BS_DECLARE_LINK_MAN_FILE_EMPTY);


            declareMapper.submitDeclare(applyId, appStatus, optInfo.getOptCustId());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), applyId, DeclareBizAction.SUBMIT.getCode(),
                    declareResult.getCode(), optInfo.getOptCustId(), optInfo.getOptName(), optInfo.getOptEntName(), optInfo
                            .getOptRemark(), optInfo.getDblOptCustId(), optInfo.getChangeContent());

            if (DeclareStatus.WAIT_TO_FIRST_APPR.getCode() == appStatus) {// 待初审
                // 被申报者所属管理公司的客户号
                String toManageEntCustId = bsEntService.findEntCustIdByEntResNo(declareInfo.getToMResNo());
                // 生成申报管理公司初审任务，业务主体是被申报企业
                taskService.saveTask(new Task(declareInfo.getApplyId(), TaskType.TASK_TYPE119.getCode(),
                        toManageEntCustId, declareInfo.getToEntResNo(), declareInfo.getToEntCustName()));
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "submitDeclare", BSRespCode.BS_DECLARE_SUBMIT_ERROR.getCode()
                    + ":提交申报失败[param=applyId:" + applyId + ",optInfo:" + optInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SUBMIT_ERROR, "提交申报失败[param=applyId:" + applyId + ",optInfo:"
                    + optInfo + "]" + e);
        }
    }

    // 校验申报信息
    private void validateDeclare(DeclareRegInfo declareRegInfo) throws HsException{
        if (null != declareRegInfo.getToSelectMode()) {
            HsAssert.isTrue(PickMode.checkMode(declareRegInfo.getToSelectMode()),
                    BSRespCode.BS_DECLARE_PICK_RESNO_ERROR, "选号方式错误，请使用{PickMode}的ordinal()方法");
        }
        HsAssert.hasText(declareRegInfo.getToEntCustName(), BSRespCode.BS_PARAMS_EMPTY, "被申报企业名称[toEntCustName]为空");

        String toMResNo = declareRegInfo.getToMResNo();
        HsAssert.hasText(toMResNo, BSRespCode.BS_PARAMS_EMPTY, "被申报企业的管理公司互生号[toMResNo]为空");

        String spreadEntResNo = declareRegInfo.getSpreadEntResNo();
        HsAssert.hasText(spreadEntResNo, BSRespCode.BS_PARAMS_EMPTY, "推荐企业互生号[spreadEntResNo]为空");

        // 被申报企业是托管企业和成员企业类型
        boolean isEntCustType = HsResNoUtils.isMemberResNo(declareRegInfo.getToCustType())
                || HsResNoUtils.isTrustResNo(declareRegInfo.getToCustType());
        boolean service = HsResNoUtils.isServiceResNo(declareRegInfo.getToCustType());
        HsAssert.isTrue(service || isEntCustType, BSRespCode.BS_DECLARE_INVALID_CUSTTYPE,
                "被申请企业类型[toCustType]类型错误,只能申报服务公司/托管企业/成员企业");

        // 检查被申报企业客户类型和启用资源类型
        if (isEntCustType) {// 申报成员或托管企业
            // 检查互生号是否为空
            HsAssert.hasText(declareRegInfo.getToEntResNo(), BSRespCode.BS_DECLARE_RESNO_NULL,
                    "拟用互生号(被申报企业互生号)[toEntResNo]为空");

            // 检验企业启用资源类型
            boolean check = ResType.checkResType(declareRegInfo.getToCustType(), declareRegInfo.getToBuyResRange());
            HsAssert.isTrue(check, BSRespCode.BS_DECLARE_INVALID_RESTYPE, "启用资源类型为空或者与客户类型不匹配");
        }
    }

    /**
     * 检验增值设置
     *
     * @param declareRegInfo
     */
    private void validateBMSetting(DeclareRegInfo declareRegInfo) throws HsException{
        String toMResNo = declareRegInfo.getToMResNo();
        HsAssert.hasText(toMResNo, BSRespCode.BS_PARAMS_EMPTY, "被申报企业的管理公司互生号[toMResNo]为空");

        String spreadEntResNo = declareRegInfo.getSpreadEntResNo();
        HsAssert.hasText(spreadEntResNo, BSRespCode.BS_PARAMS_EMPTY, "推荐企业互生号[spreadEntResNo]为空");

        // 被申报企业是托管企业和成员企业类型
        boolean isEntCustType = HsResNoUtils.isMemberResNo(declareRegInfo.getToCustType())
                || HsResNoUtils.isTrustResNo(declareRegInfo.getToCustType());
        // 检查被申报企业客户类型和启用资源类型
        if (isEntCustType) {// 申报成员或托管企业
            boolean isFreeMemberEnt = false;
            if (CustType.MEMBER_ENT.getCode() == declareRegInfo.getToCustType()
                    && null != declareRegInfo.getToBuyResRange()
                    && ResType.FREE_MEMBER_ENT.getCode() == declareRegInfo.getToBuyResRange()) {// 免费成员企业
                isFreeMemberEnt = true;
            }
            // 免费成员企业不参与增值分配，所以不需要校验增值点设置
            if (!isFreeMemberEnt) {
                HsAssert.hasText(declareRegInfo.getToInodeResNo(), RespCode.PARAM_ERROR, "增值父节点不能为空");
                // 如果设置了增值父节点，长度必须大于等于11，因为父节点最小的也是11位互生号
                HsAssert.isTrue(declareRegInfo.getToInodeResNo().length() >= 11, RespCode.PARAM_ERROR, "增值父节点长度不对");
                HsAssert.isTrue(InodeArea.checkArea(declareRegInfo.getToInodeLorR()), RespCode.PARAM_ERROR,
                        "被申报企业父节点区域[inodeArea]类型错误，可取值0或1");
                if (HsResNoUtils.isServiceResNo(declareRegInfo.getFrEntResNo())) {//服务公司申报托管或成员企业时，校验增值父节点设置必须与申报服务公司前5位相同，即增值节点往服务公司或下级托管、成员企业下挂
                    HsAssert.isTrue(declareRegInfo.getToEntResNo().substring(0, 5).equals(
                            declareRegInfo.getToInodeResNo().substring(0, 5)), RespCode.PARAM_ERROR,
                            "被申报企业增值父节点只能在同一个服务公司节点之下");
                }
            }
        } else {// 申报服务公司
            String frEntResNo = declareRegInfo.getFrEntResNo();
            HsAssert.hasText(frEntResNo, BSRespCode.BS_PARAMS_EMPTY, "申报企业互生号[frEntResNo]为空");

            if (!HsResNoUtils.isAreaPlatResNo(frEntResNo)) {// 服务公司申报服务公司时,不能设置增值父节点
                HsAssert.isTrue(frEntResNo.equals(spreadEntResNo), RespCode.PARAM_ERROR, "服务公司申报时申报者应该与推荐企业互生号相同");
                // 判断是否跨管理公司申报，如果是跨管理公司申报，增值父节点不能选择
                if (toMResNo.substring(0, 2).equals(frEntResNo.substring(0, 2))) {
                    // 服务公司申报服务公司时，非跨管理公司申报时
                    HsAssert.isTrue(StringUtils.left(frEntResNo,5).equals(StringUtils.left(declareRegInfo.getToInodeResNo(),5)),
                            RespCode.PARAM_ERROR, "被申报服务公司增值父节点必须在申报服务公司节点之下");
                    HsAssert.isTrue(InodeArea.checkArea(declareRegInfo.getToInodeLorR()), RespCode.PARAM_ERROR,
                            "被申报企业父节点区域[inodeArea]类型错误，可取值0或1");
                } else {
                    // 服务公司申报服务公司时，跨管理公司申报时
                    if (StringUtils.isEmpty(declareRegInfo.getToInodeResNo())) {
                        // 跨管理公司，节点选择被申报企业管理公司下最短路径
                        declareRegInfo.setToPnodeResNo(toMResNo);
                        declareRegInfo.setToPnodeCustId(toMResNo);
                        declareRegInfo.setToInodeResNo(toMResNo);
                        declareRegInfo.setToInodeLorR(InodeArea.LEFT.getCode());// 默认左边
                    }
                    HsAssert.isTrue(StringUtils.left(toMResNo,2).equals(StringUtils.left(declareRegInfo.getToInodeResNo(),2)), RespCode.PARAM_ERROR,"被申报服务公司增值父节点必须在其管理公司或同一个管理下服务公司节点之下");
                    HsAssert.isTrue(InodeArea.checkArea(declareRegInfo.getToInodeLorR()), RespCode.PARAM_ERROR,"被申报企业父节点区域[inodeArea]类型错误，可取值0或1");
                }
            } else {// 地区平台申报服务公司时
                if (StringUtils.isEmpty(declareRegInfo.getToInodeResNo())) {
                    // 如果未设置增值父节点，默认目标管理公司
                    declareRegInfo.setToPnodeResNo(toMResNo);
                    declareRegInfo.setToPnodeCustId(toMResNo);
                    declareRegInfo.setToInodeResNo(toMResNo);
                    declareRegInfo.setToInodeLorR(InodeArea.LEFT.getCode());// 默认左边
                } else {
                    // 如果设置了增值父节点，长度必须大于等于11，因为父节点最小的也是11位互生号
                    HsAssert.isTrue(declareRegInfo.getToInodeResNo().length() >= 11, RespCode.PARAM_ERROR, "增值父节点长度不对");
                    // 如果设置了增值父节点，父节点必须在目标管理公司之下
                    HsAssert.isTrue(toMResNo.substring(0, 2).equals(declareRegInfo.getToInodeResNo().substring(0, 2)),
                            RespCode.PARAM_ERROR, "被申报服务公司增值父节点必须在被申报服务公司的管理公司节点之下");
                    HsAssert.isTrue(InodeArea.checkArea(declareRegInfo.getToInodeLorR()), RespCode.PARAM_ERROR,
                            "被申报企业父节点区域[inodeArea]类型错误，可取值0或1");
                }
            }
        }
    }

    /**
     * 服务公司查询企业申报
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> serviceQueryDeclare(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || StringUtils.isBlank(declareQueryParam.getDeclarerResNo()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setManageResNo(null);
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        PageContext.setPage(page);
        PageData<DeclareEntBaseInfo> pageData = null;
        // 服务公司查询申报应该是查询推荐企业是自己的
        List<DeclareEntBaseInfo> list = declareMapper.queryDeclareByRecommendListPage(declareParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 服务公司查询企业申报内审
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> serviceQueryDeclareAppr(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == declareQueryParam.getCustType()
                || StringUtils.isBlank(declareQueryParam.getDeclarerResNo())
                || StringUtils.isBlank(declareQueryParam.getOperatorCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setManageResNo(null);
        declareQueryParam.setStatus(DeclareStatus.WAIT_TO_INNER_APPR.getCode());
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        // 服务公司审批查询申报单时应该查询申报者是自己的记录
        return this.queryDeclare(declareParam, page);
    }

    /**
     * 管理公司查询申报初审
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> manageQueryDeclareAppr(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == declareQueryParam.getCustType()
                || StringUtils.isBlank(declareQueryParam.getManageResNo())
                || StringUtils.isBlank(declareQueryParam.getOperatorCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setStatus(DeclareStatus.WAIT_TO_FIRST_APPR.getCode());
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        return this.queryDeclare4Task(declareParam, page);
    }

    /**
     * 管理公司查询申报复核
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> manageQueryDeclareReview(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == declareQueryParam.getCustType()
                || StringUtils.isBlank(declareQueryParam.getManageResNo())
                || StringUtils.isBlank(declareQueryParam.getOperatorCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setStatus(DeclareStatus.WAIT_TO_APPR_REVIEW.getCode());
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        return this.queryDeclare4Task(declareParam, page);
    }

    /**
     * 申报查询
     *
     * @param declareParam 查询条件
     * @param page         分页信息 必填
     * @return 返回申报列表，没有则返回null
     */
    private PageData<DeclareEntBaseInfo> queryDeclare4Task(DeclareParam declareParam, Page page) {
        PageContext.setPage(page);
        PageData<DeclareEntBaseInfo> pageData = null;
        List<DeclareEntBaseInfo> list = declareMapper.queryDeclare4TaskListPage(declareParam);

        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 管理公司申报审批统计查询
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> manageQueryDeclareList(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == declareQueryParam.getCustType()
                || StringUtils.isBlank(declareQueryParam.getManageResNo()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setOperatorCustId(null);
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        String notInStatus = "(" + DeclareStatus.WAIT_TO_SUBMIT.getCode() + ")";
        declareParam.setNotInStatus(notInStatus);
        return this.queryDeclare(declareParam, page);
    }

    /**
     * 地区平台申报审批统计查询
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> platQueryDeclareList(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == declareQueryParam.getCustType() || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setManageResNo(null);
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setOperatorCustId(null);
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        declareParam.setIsPlatQuery(true);

        return this.queryDeclare(declareParam, page);
    }

    /**
     * 申报查询
     *
     * @param declareParam 查询条件
     * @param page         分页信息 必填
     * @return 返回申报列表，没有则返回null
     */
    private PageData<DeclareEntBaseInfo> queryDeclare(DeclareParam declareParam, Page page) {
        PageContext.setPage(page);
        PageData<DeclareEntBaseInfo> pageData = null;
        List<DeclareEntBaseInfo> list = declareMapper.queryDeclareListPage(declareParam);

        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 查询申报信息
     *
     * @param applyId 申请编号
     * @return 申报信息
     */
    @Override
    public DeclareAppInfo queryDeclareAppInfoByApplyId(String applyId) {
        DeclareAppInfo declareAppInfo = declareMapper.queryDeclareAppInfoByApplyId(applyId);

        if (null != declareAppInfo) {
            Integer quota = 0;
            if (CustType.SERVICE_CORP.getCode() == declareAppInfo.getToCustType()) {// 服务公司
                // List<String> serResNoList =
                // entResNoService.getSerResNoList(declareAppInfo.getCountryNo(),
                // declareAppInfo.getProvinceNo(), declareAppInfo.getCityNo(),
                // ResStatus.NOT_USED.getCode());
                // declareAppInfo.setSerResNoList(serResNoList);
                // if (null != serResNoList)
                // {
                quota = entResNoService.getServiceQuota(declareAppInfo.getCountryNo(), declareAppInfo.getProvinceNo(),
                        declareAppInfo.getCityNo());
                // }
            } else {// 托管、成员企业
                quota = entResNoService.getNotServiceQuota(declareAppInfo.getToEntResNo().substring(0, 5) + "000000",
                        declareAppInfo.getToCustType(), declareAppInfo.getToBuyResRange());
            }
            declareAppInfo.setAvailableQuota(quota);
        }
        return declareAppInfo;
    }

    /**
     * 查询企业系统注册信息
     *
     * @param applyId 申请编号
     * @return 企业系统注册信息
     */
    @Override
    public DeclareRegInfo queryDeclareRegInfoByApplyId(String applyId) {
        DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
        if (null == declareInfo) {
            return null;
        }
        DeclareRegInfo declareRegInfo = new DeclareRegInfo();
        BeanUtils.copyProperties(declareInfo, declareRegInfo);
        return declareRegInfo;
    }

    /**
     * 查询企业工商登记信息
     *
     * @param applyId 申请编号
     * @return 企业工商登记信息
     */
    @Override
    public DeclareBizRegInfo queryDeclareBizRegInfoByApplyId(String applyId) {
        DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
        if (null == declareInfo) {
            return null;
        }
        DeclareBizRegInfo declareBizRegInfo = new DeclareBizRegInfo();
        declareBizRegInfo.setApplyId(applyId);
        declareBizRegInfo.setEntName(declareInfo.getToEntCustName());
        declareBizRegInfo.setEntAddress(declareInfo.getEntAddr());
        declareBizRegInfo.setLegalName(declareInfo.getLegalName());
        declareBizRegInfo.setLegalCreType(declareInfo.getLegalCreType());
        declareBizRegInfo.setLegalCreNo(declareInfo.getLegalCreNo());
        declareBizRegInfo.setLinkmanMobile(declareInfo.getPhone());
        declareBizRegInfo.setEntType(declareInfo.getEntType());
        declareBizRegInfo.setLicenseNo(declareInfo.getLicenseNo());
        declareBizRegInfo.setOrgNo(declareInfo.getOrgNo());
        declareBizRegInfo.setTaxNo(declareInfo.getTaxNo());
        declareBizRegInfo.setEstablishingDate(declareInfo.getEstablishingDate());
        declareBizRegInfo.setLimitDate(declareInfo.getLimitDate());
        declareBizRegInfo.setDealArea(declareInfo.getDealArea());

        DeclareAptitude licenseAptitude = declareMapper.queryOneAptitude(declareBizRegInfo.getApplyId(), AptitudeType.BIZ_LICENSE_CRE.getCode());
        declareBizRegInfo.setLicenseAptitude(licenseAptitude);
        return declareBizRegInfo;
    }

    /**
     * 查询申报企业联系人信息
     *
     * @param applyId 申请编号
     * @return 申报企业联系人信息
     */
    @Override
    public DeclareLinkman queryLinkmanByApplyId(String applyId) throws HsException {
        DeclareLinkman linkman =declareMapper.queryLinkmanByApplyId(applyId);
        if (linkman != null) {
            DeclareAptitude protocolAptitude = declareMapper.queryOneAptitude(linkman.getApplyId(), AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode());
            linkman.setProtocolAptitude(protocolAptitude);
        }
        return linkman;
    }

    /**
     * 查询申报企业银行信息
     *
     * @param applyId 申请编号
     * @return 申报企业银行信息
     */
    @Override
    @Deprecated
    public DeclareBank queryBankByApplyId(String applyId) throws HsException {
        return declareMapper.queryBankByApplyId(applyId);
    }

    /**
     * 查询申报企业资质信息
     *
     * @param applyId 申请编号
     * @return 申报企业资质信息
     */
    @Override
    @Deprecated
    public List<DeclareAptitude> queryAptitudeByApplyId(String applyId) throws HsException {
        return declareMapper.queryAptitudeByApplyId(applyId);
    }

    /**
     * 申报备注说明
     *
     * @param applyId 申请编号
     * @return 申报备注说明
     */
    @Override
    public String queryDeclareRemarkByApplyId(String applyId) throws HsException {
        return declareMapper.getDeclareRemark(applyId);
    }

    /**
     * 查询申报企业资质信息
     *
     * @param applyId 申请编号
     * @return 申报企业资质信息及备注说明
     */
    @Override
    public Map<String, Object> queryAptitudeWithRemarkByApplyId(String applyId) throws HsException {
        String remark = declareMapper.getDeclareRemark(applyId);
        List<DeclareAptitude> list = declareMapper.queryAptitudeByApplyId(applyId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BSConstants.DECLARE_REMARK_KEY, remark);
        map.put(BSConstants.DECLARE_APTITUDE_LIST_KEY, list);
        return map;
    }

    /**
     * 服务公司审核申报
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void serviceApprDeclare(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "serviceApprDeclare", "input param:", apprParam+"");
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try {
            DeclareInfo declareInfo = declareMapper.queryDeclareById(apprParam.getApplyId());
/*            List<DeclareAptitude> list = declareMapper.queryAptitudeByApplyId(apprParam.getApplyId());
            boolean existLegalBackFile = false;
            for (DeclareAptitude aptitude : list) {
                if (AptitudeType.LEGAL_REP_CRE_BACK.getCode() == aptitude.getAptitudeType()) {
                    existLegalBackFile = true;
                }
            }
            if (declareInfo.getLegalCreType() == null || declareInfo.getLegalCreType() != 2) {
                HsAssert.isTrue(existLegalBackFile, BSRespCode.BS_DECLARE_NEED_BASE_FILE);
            }*/
            DeclareResult declarResult;// 业务结果
            int appStatus;
            if (apprParam.getIsPass()) {// 审批通过
                declarResult = DeclareResult.SERVICE_PASS; // 通过进入管理公司审批阶段
                appStatus = DeclareStatus.WAIT_TO_FIRST_APPR.getCode();// 待初审
            } else {// 审批驳回
                declarResult = DeclareResult.SERVICE_REJECTED;// 驳回留在服务公司审批阶段
                appStatus = DeclareStatus.INNER_REJECTED.getCode();// 内审驳回

                if (!StringUtils.isBlank(declareInfo.getToEntResNo())) {
                    // 释放互生号
                    entResNoService.updateEntResNoStatus(declareInfo.getToCustType(), declareInfo.getToEntResNo(),
                            ResStatus.NOT_USED.getCode());
                }
            }

            // 更新申报状态
            declareMapper.apprDeclare(apprParam.getApplyId(), apprParam.getOptCustId(), appStatus);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), apprParam.getApplyId(), DeclareBizAction.SUBMIT
                            .getCode(), declarResult.getCode(), apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(),
                    apprParam.getApprRemark(), apprParam.getDblOptCustId(), null);

            if (DeclareStatus.WAIT_TO_FIRST_APPR.getCode() == appStatus) {// 待初审

                // 被申报者所属管理公司的客户号
                String toManageEntCustId = bsEntService.findEntCustIdByEntResNo(declareInfo.getToMResNo());
                // 生成申报管理公司初审任务，业务主体是被申报企业
                taskService.saveTask(new Task(declareInfo.getApplyId(), TaskType.TASK_TYPE119.getCode(),
                        toManageEntCustId, declareInfo.getToEntResNo(), declareInfo.getToEntCustName()));
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "serviceApprDeclare", BSRespCode.BS_DECLARE_SERVICE_APPR_ERROR
                    .getCode()
                    + ":服务公司审批申报失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SERVICE_APPR_ERROR, "服务公司审批申报失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 管理公司初审
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void manageApprDeclare(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "manageApprDeclare", "input param:", apprParam + "");
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");

        try {
            DeclareInfo declareInfo = declareMapper.queryDeclareById(apprParam.getApplyId());
/*            List<DeclareAptitude> list = declareMapper.queryAptitudeByApplyId(apprParam.getApplyId());
            boolean existLegalBackFile = false;
            for (DeclareAptitude aptitude : list) {
                if (AptitudeType.LEGAL_REP_CRE_BACK.getCode() == aptitude.getAptitudeType()) {
                    existLegalBackFile = true;
                }
            }
            if (declareInfo.getLegalCreType() == null || declareInfo.getLegalCreType() != 2) {
                HsAssert.isTrue(existLegalBackFile, BSRespCode.BS_DECLARE_NEED_BASE_FILE);
            }*/
            DeclareResult daclareResult;
            int appStatus;
            if (apprParam.getIsPass()) {// 初审通过
                appStatus = DeclareStatus.WAIT_TO_APPR_REVIEW.getCode();// 待复核
                daclareResult = DeclareResult.MANAGE_APPR_PASS;
            } else {// 初审驳回
                daclareResult = DeclareResult.MANAGE_APPR_REJECTED;
                appStatus = DeclareStatus.FIRST_APPR_REJECTED.getCode();// 初审驳回
                if (!StringUtils.isBlank(declareInfo.getToEntResNo())) {
                    // 释放互生号
                    entResNoService.updateEntResNoStatus(declareInfo.getToCustType(), declareInfo.getToEntResNo(),
                            ResStatus.NOT_USED.getCode());
                }
            }

            // 更新申报状态
            declareMapper.apprDeclare(apprParam.getApplyId(), apprParam.getOptCustId(), appStatus);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), apprParam.getApplyId(), DeclareBizAction.APPROVED
                            .getCode(), daclareResult.getCode(), apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(),
                    apprParam.getApprRemark(), apprParam.getDblOptCustId(), null);

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());

            if (DeclareStatus.WAIT_TO_APPR_REVIEW.getCode() == appStatus) {// 待复核

                // 被申报者所属管理公司的客户号
                String toManageEntCustId = bsEntService.findEntCustIdByEntResNo(declareInfo.getToMResNo());
                // 生成管理公司复核任务,业务主体是被申报企业
                taskService.saveTask(new Task(declareInfo.getApplyId(), TaskType.TASK_TYPE120.getCode(),
                        toManageEntCustId, declareInfo.getToEntResNo(), declareInfo.getToEntCustName()));
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "manageApprDeclare", BSRespCode.BS_DECLARE_MANAGE_APPR_ERROR
                    .getCode()
                    + ":管理公司审批申报失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_MANAGE_APPR_ERROR, "管理公司审批申报失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 管理公司复核
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void manageReviewDeclare(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "manageReviewDeclare", "input param:", apprParam + "");
        HsAssert.notNull(apprParam, BSRespCode.BS_PARAMS_NULL, "审核信息[apprParam]为null");
        HsAssert.isTrue(StringUtils.isNoneBlank(apprParam.getApplyId(), apprParam.getOptCustId(), apprParam
                .getOptName(), apprParam.getOptEntName()), BSRespCode.BS_PARAMS_EMPTY, "参数不能为空");
        HsAssert.notNull(apprParam.getIsPass(), BSRespCode.BS_PARAMS_NULL, "审核状态[isPass]为null");

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");

        try {
            DeclareInfo declareInfo = declareMapper.queryDeclareById(apprParam.getApplyId());
/*            List<DeclareAptitude> list = declareMapper.queryAptitudeByApplyId(apprParam.getApplyId());
            boolean existLegalBackFile = false;
            for (DeclareAptitude aptitude : list) {
                if (AptitudeType.LEGAL_REP_CRE_BACK.getCode() == aptitude.getAptitudeType()) {
                    existLegalBackFile = true;
                }
            }
            if (declareInfo.getLegalCreType() == null || declareInfo.getLegalCreType() != 2) {
                HsAssert.isTrue(existLegalBackFile, BSRespCode.BS_DECLARE_NEED_BASE_FILE);
            }*/
            String entName = BSConstants.SYS_OPERATOR;
            LocalInfo localInfo = commonService.getAreaPlatInfo();

            // 是否是免费成员企业
            boolean isFreeMemberEnt = CustType.MEMBER_ENT.getCode() == declareInfo.getToCustType()
                    && null != declareInfo.getToBuyResRange()
                    && ResType.FREE_MEMBER_ENT.getCode() == declareInfo.getToBuyResRange();
            DeclareResult bizReuslt; // 业务结果
            Integer appStatus = null;
            if (apprParam.getIsPass()) {// 复核通过

                bizReuslt = DeclareResult.MANAGE_REVIEW_PASS; // 管理公司复核通过
                // 申报服务公司时，在管理公司初审通过后选号的，所以检查有没有选号
                if (CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType()) {
                    HsAssert.hasText(declareInfo.getToEntResNo(),BSRespCode.BS_DECLARE_NO_PICK_RESNO,"请为被申报企业选择互生号");
                }

                appStatus = isFreeMemberEnt ? DeclareStatus.WAIT_TO_OPER_OS.getCode() : DeclareStatus.HANDLING.getCode();// 待开启系统

                // 生成申报订单,更新资源费方案编号、资源费订单号、授权码有效期到申报申请单中
                this.saveDelcareOrder(declareInfo, apprParam.getOptCustId(), isFreeMemberEnt);
                if (!isFreeMemberEnt) {// 非免费成员企业
                    // 通过短信发送办理授权码
                    try {
                        if (HsResNoUtils.isServiceResNo(declareInfo.getToCustType())) {
                            this.sendDynamicAuthCode(declareInfo, apprParam.getOptCustId(), localInfo);
                        } else {
                            this.firstSendAuthCode(declareInfo.getApplyId());
                        }
                    } catch (Exception e) {
                        SystemLog.error(this.getClass().getName(), "manageReviewDeclare", "发送申报办理授权码出现异常 [param="
                                + apprParam + "]", e);
                    }
                }
            } else {// 复核驳回
                bizReuslt = DeclareResult.MANAGE_REVIEW_REJECTED; // 管理公司复核驳回
                appStatus = DeclareStatus.REVIEW_REJECTED.getCode();// 复核驳回
                if (StringUtils.isNotBlank(declareInfo.getToEntResNo())) {
                    // 释放互生号
                    entResNoService.updateEntResNoStatus(declareInfo.getToCustType(), declareInfo.getToEntResNo(),ResStatus.NOT_USED.getCode());
                }
            }

            // 更新申报状态
            declareMapper.apprDeclare(apprParam.getApplyId(), apprParam.getOptCustId(), appStatus);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), apprParam.getApplyId(), DeclareBizAction.APPROVED
                            .getCode(), bizReuslt.getCode(), apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(),
                    apprParam.getApprRemark(), apprParam.getDblOptCustId(), null);

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());

            if (DeclareStatus.WAIT_TO_OPER_OS.getCode() == appStatus) {// 待开启系统
                // 生成申报开启系统审批任务,业务主体是被申报企业
                taskService.saveTask(new Task(declareInfo.getApplyId(), TaskType.TASK_TYPE121.getCode(), commonService
                        .getAreaPlatCustId(), declareInfo.getToEntResNo(), declareInfo.getToEntCustName()));
            } else {
                // 如果不是直接进入待开启系统，则代表进入办理期中，需要多生成一条办理期中公告付款的办理状态
                if (apprParam.getIsPass()) {
                    operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), apprParam.getApplyId(), DeclareBizAction.PAYING
                            .getCode(), DeclareResult.PAYING_PLACARD.getCode(), "0000", null, entName, null, null, null);
                }
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "manageReviewDeclare", BSRespCode.BS_DECLARE_MANAGE_REVIEW_ERROR
                    .getCode()
                    + ":管理公司复核申报失败 [param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_MANAGE_REVIEW_ERROR, "管理公司复核申报失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 发送服务公司授权码的互动消息
     *
     * @param declareInfo 申报信息
     * @param optCustId   操作员客户号
     * @param localInfo   本平台信息
     */
    private void sendDynamicAuthCode(DeclareInfo declareInfo, String optCustId, LocalInfo localInfo) throws HsException {
        DeclareAuthCode declareAuthCode = declareMapper.queryAuthCodeByApplyId(declareInfo.getApplyId());
        String authCode = declareAuthCode.getAuthCode();
        if (StringUtils.isBlank(authCode)) {
            // 生成唯一授权码
            authCode = DateUtil.getCurrentDatetimeNoSign() + RandomCodeUtils.getMixCode(4);
            // authCode = applyId; //可以使用申报编号作为授权码，主键一定唯一
            while (declareMapper.queryDeclareByAuthCode(authCode) != null) {
                authCode = DateUtil.getCurrentDatetimeNoSign() + RandomCodeUtils.getMixCode(4);
            }
        }
        // 更新办理授权码发送次数与最后发送时间，第一次发送设置授权码
        declareMapper.updateAfterSendAuthCode(declareInfo.getApplyId(), authCode);
        /**
         * 您申报的{0}服务公司已通过审核，拟启用企业互生号{1}，
         * 请及时通知办理付款，付款办理有效期为{2}，
         * 平台指定的唯一收款账号信息请登录官网，在“帮助中心”下的“联系我们”中查看。
         */
        Date expiryDate = DateUtil.getMaxDateOfDay(DateUtil.getAfterDay(new Date(), bsConfig.getAuthCodeExpiryDays()));
        Map<String, String> contents = new HashMap<>();
        contents.put("{0}", declareInfo.getToEntCustName());//企业名称
        contents.put("{1}", declareInfo.getToEntResNo());// 拟启用企业互生号
        contents.put("{2}", DateUtil.DateToString(expiryDate));// 有效期
        DynamicBizMsgBean bean = new DynamicBizMsgBean();

        bean.setMsgId(declareInfo.getApplyId());// 消息编号
        bean.setCustType(declareInfo.getToCustType());//客户类型
        if (localInfo != null) {
            bean.setHsResNo(localInfo.getPlatResNo());//发送消息的互生号
            bean.setCustName(localInfo.getPlatNameCn());//发送消息的客户名称
        }
        bean.setMsgCode("203");
        bean.setSubMsgCode("2030101");

        bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE116.getCode()));// 消息类型
        bean.setMsgReceiver(new String[]{declareInfo.getSpreadEntResNo()});//接受者
        bean.setMsgTitle("申报服务公司公告付款通知");//消息标题
        bean.setContent(contents);//消息内容
        bean.setPriority(Priority.HIGH.getPriority());//权限级别
        bean.setSender(optCustId);

        try {
            ntService.sendDynamicBiz(bean);
        } catch (NoticeException e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:sendDynamicAuthCode", "发送服务公司授权码的互动消息---调用通知系统发送消息失败，参数[bean]:" + bean, e);
            throw new HsException(BSRespCode.BS_MESSAGE_SEND_ERROR, "发送服务公司授权码的互动消息--调用通知系统发送消息失败，原因:" + e.getMessage());
        }
    }

    // 生成申报订单,更新资源费方案编号、资源费订单号、授权码有效期到申报申请单中
    private void saveDelcareOrder(DeclareInfo declareInfo, String optCustId, boolean isFreeMemberEnt) {
        // 查询出资源费方案
        ResFee resFee = resFeeService.getEnableResFee(declareInfo.getToCustType(), declareInfo.getToBuyResRange());
        List<ResFeeTool> resFeeToolList = resFeeService.queryResFeeToolList(resFee.getResFeeId());

        String orderNo = "";

        // 免费成员企业，没有工具时不生成订单
        if (isFreeMemberEnt && (resFeeToolList == null || resFeeToolList.size() == 0)) {
        } else {
            // 生成申报订单
            Order order = new Order();
            order.setHsResNo(declareInfo.getToEntResNo());
            order.setCustName(declareInfo.getToEntCustName());
            order.setCustType(declareInfo.getToCustType());
            order.setBizNo(declareInfo.getApplyId());
            order.setOrderType(OrderType.RES_FEE_ALLOT.getCode());
            order.setIsProxy(false);
            order.setOrderOriginalAmount(resFee.getPrice());
            order.setOrderCashAmount(resFee.getPrice());
            order.setCurrencyCode(resFee.getCurrencyCode());
            order.setOrderRemark("系统资源费订单");
            order.setOrderChannel(OrderChannel.WEB.getCode());
            order.setOrderOperator(optCustId);
            if (Double.valueOf(resFee.getPrice()) > 0) {
                order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
                order.setPayStatus(PayStatus.WAIT_PAY.getCode());
                order.setIsNeedInvoice(1);
            } else {// 如果资源金额为0，则设订单状态为已完成，支付状态为已付款
                order.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());
                order.setPayStatus(PayStatus.PAY_FINISH.getCode());
                order.setIsNeedInvoice(0);
            }
            order.setIsInvoiced(0);
            orderNo = orderService.saveCommonOrder(order);
        }

        // 更新资源费方案编号、资源费订单号、授权码有效期到申报申请单中
        DeclareEntStatus declareEntStatus = new DeclareEntStatus();
        declareEntStatus.setApplyId(declareInfo.getApplyId());
        declareEntStatus.setOrderNo(orderNo);
        declareEntStatus.setResFeeId(resFee.getResFeeId());
        if (Double.valueOf(resFee.getPrice()) == 0) {// 如果资源费金额为0，则设申报收费标识为已收费
            declareEntStatus.setChargeFlag(true);
        } else {
            Date expiryDate = DateUtil.getMaxDateOfDay(DateUtil.getAfterDay(new Date(), bsConfig
                    .getAuthCodeExpiryDays()));
            declareEntStatus.setExpiryDate(expiryDate);
        }
        declareEntStatus.setOperator(optCustId);
        declareMapper.updateDeclareStatus(declareEntStatus);
    }

    /**
     * 手动重发办理授权码
     *
     * @param applyId
     * @throws HsException
     * @see com.gy.hsxt.bs.api.apply.IBSDeclareService#sendAuthCode(java.lang.String)
     */
    @Override
    @Transactional
    public void sendAuthCode(String applyId) throws HsException {
        this.sendPayAuthCode(applyId, true);
    }

    /**
     * 第一次进入办理期发送办理授权码
     *
     * @param applyId
     * @throws HsException
     * @see com.gy.hsxt.bs.apply.interfaces.IDeclareService#firstSendAuthCode(java.lang.String)
     */
    @Override
    @Transactional
    public void firstSendAuthCode(String applyId) throws HsException {
        this.sendPayAuthCode(applyId, false);
    }

    /**
     * 发送办理授权码
     *
     * @param applyId        申报单号
     * @param isManualResend 是否手动重发
     * @throws HsException
     */
    private void sendPayAuthCode(String applyId, boolean isManualResend) throws HsException {
        try {
            DeclareAuthCode declareAuthCode = declareMapper.queryAuthCodeByApplyId(applyId);
            if (declareAuthCode == null || StringUtils.isBlank(declareAuthCode.getLinkmanMobile())) {
                throw new HsException(BSRespCode.BS_SEND_AUTH_CODE_ERROR, "发送申报授权码失败，没有手机号码");
            }
            if (isManualResend) { //如果是手动重发,要检查是否是办理期中
                if (DeclareStatus.HANDLING.getCode() != declareAuthCode.getAppStatus()) {
                    throw new HsException(BSRespCode.BS_SEND_AUTH_CODE_ERROR, "办理期才能发送授权码");
                }
            }

            String authCode = declareAuthCode.getAuthCode();
            if (StringUtils.isBlank(authCode)) {
                // 生成唯一授权码
                authCode = DateUtil.getCurrentDatetimeNoSign() + RandomCodeUtils.getMixCode(4);
                // authCode = applyId; //可以使用申报编号作为授权码，主键一定唯一
                while (declareMapper.queryDeclareByAuthCode(authCode) != null) {
                    authCode = DateUtil.getCurrentDatetimeNoSign() + RandomCodeUtils.getMixCode(4);
                }
            }
            // 更新办理授权码发送次数与最后发送时间，第一次发送设置授权码
            declareMapper.updateAfterSendAuthCode(applyId, authCode);
            // 发送办理授权码短信： {0}，您的企业应用办理授权码{1}，有效期{2}，登录平台官网办理。
            Date expiryDate = DateUtil.getMaxDateOfDay(DateUtil.getAfterDay(new Date(), bsConfig
                    .getAuthCodeExpiryDays()));
//            String linkName = declareAuthCode.getLinkman() == null ? "" : declareAuthCode.getLinkman();// 联系人姓名
            Map<String, String> contents = new HashMap<>();
            contents.put("{0}", declareAuthCode.getEntName());
            contents.put("{1}", authCode);// 授权码
            contents.put("{2}", DateUtil.DateToString(expiryDate));// 有效期

            NoteBean noteBean = new NoteBean();
            noteBean.setMsgId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            noteBean.setHsResNo(declareAuthCode.getEntResNo());
            noteBean.setCustName(declareAuthCode.getEntName());
            noteBean.setMsgReceiver(new String[]{declareAuthCode.getLinkmanMobile()});// 收信手机号
            noteBean.setContent(contents);
            noteBean.setCustType(HsResNoUtils.getCustTypeByHsResNo(declareAuthCode.getEntResNo()));
            noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE103.getCode()));
            if (HsResNoUtils.isTrustResNo(declareAuthCode.getEntResNo())
                    || HsResNoUtils.isMemberResNo(declareAuthCode.getEntResNo())) {
                noteBean.setBuyResType(declareAuthCode.getToBuyResRange());
            }
            noteBean.setPriority(Priority.HIGH.getPriority());// 消息级别
            noteBean.setSender("BS");
            ntService.sendNote(noteBean);// 调用通知系统发短信
        } catch (HsException e) {
            throw e;
        } catch (Exception e) {
            throw new HsException(BSRespCode.BS_SEND_AUTH_CODE_ERROR, "发送申报授权码失败，" + e);
        }
    }

    /**
     * 地区平台查询开启系统欠款审核列表
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> platQueryOpenSysAppr(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == declareQueryParam.getCustType()
                || StringUtils.isBlank(declareQueryParam.getOperatorCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        declareQueryParam.setManageResNo(null);
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setStatus(DeclareStatus.HANDLING.getCode());
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);

        PageContext.setPage(page);
        PageData<DeclareEntBaseInfo> pageData = null;
        List<DeclareEntBaseInfo> list = declareMapper.queryOpenSysApprListPage(declareParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<DeclareEntBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 开系统欠款审核
     *
     * @param debtOpenSys 开系统欠款审核信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void apprDebtOpenSys(DebtOpenSys debtOpenSys) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprDebtOpenSys", "input param:", debtOpenSys + "");
        if (null == debtOpenSys || StringUtils.isBlank(debtOpenSys.getApplyId()) || null == debtOpenSys.getFeeFlag()
                || StringUtils.isBlank(debtOpenSys.getOptCustId())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try {
            DeclareEntStatus declareEntStatus = new DeclareEntStatus();
            DeclareInfo declareInfo = declareMapper.queryDeclareById(debtOpenSys.getApplyId());
/*            List<DeclareAptitude> list = declareMapper.queryAptitudeByApplyId(debtOpenSys.getApplyId());
            boolean existLegalBackFile = false;
            for (DeclareAptitude aptitude : list) {
                if (AptitudeType.LEGAL_REP_CRE_BACK.getCode() == aptitude.getAptitudeType()) {
                    existLegalBackFile = true;
                }
            }
            if (declareInfo.getLegalCreType() == null || declareInfo.getLegalCreType() != 2) {
                HsAssert.isTrue(existLegalBackFile, BSRespCode.BS_DECLARE_NEED_BASE_FILE);
            }*/
            if (FeeFlag.DEBT.getCode() == debtOpenSys.getFeeFlag()) {// 欠款
                declareEntStatus.setResFeeAllocMode(ResFeeAllocMode.ALLOCATE_LATER.getCode());// 资源费分配方式:暂缓分配
                declareEntStatus.setResFeeChargeMode(ResFeeChargeMode.DEBT.getCode());// 资源费收费方式:暂欠
                declareEntStatus.setChargeFlag(false);// 暂欠时收费标识：未收费
            } else if (FeeFlag.FREE.getCode() == debtOpenSys.getFeeFlag()) {// 免费
                declareEntStatus.setResFeeAllocMode(ResFeeAllocMode.NO_ALLOCATE.getCode());// 资源费分配方式:无分配
                declareEntStatus.setResFeeChargeMode(ResFeeChargeMode.NONE.getCode());// 资源费收费方式:全免
                declareEntStatus.setChargeFlag(false);// 全免时收费标识：未收费

                // 设订单实收金额为0，订单状态为已完成，支付状态为已付款
                orderService.updateResFeeOrder(declareInfo.getOrderNo());
            }

            declareEntStatus.setAppStatus(DeclareStatus.WAIT_TO_OPER_OS.getCode());// 待开启系统
            declareEntStatus.setOperator(debtOpenSys.getOptCustId());
            declareEntStatus.setApplyId(debtOpenSys.getApplyId());
            declareMapper.updateDeclareStatus(declareEntStatus);

            // 生成申报开启系统审批任务,业务主体是被申报企业
            taskService.saveTask(new Task(declareInfo.getApplyId(), TaskType.TASK_TYPE121.getCode(), commonService
                    .getAreaPlatCustId(), declareInfo.getToEntResNo(), declareInfo.getToEntCustName()));

        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "prepareOpenSys",
                    BSRespCode.BS_DECLARE_APPR_DEBT_OPEN_SYSY_ERROR.getCode() + ":开系统欠款审核失败[param=" + debtOpenSys
                            + debtOpenSys + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_APPR_DEBT_OPEN_SYSY_ERROR, "开系统欠款审核失败[param=" + debtOpenSys
                    + debtOpenSys + "]" + e);
        }
    }

    /**
     * 地区平台查询开启系统列表
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息 必填
     * @return 返回申报列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> platQueryOpenSys(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {

        if (null == declareQueryParam || null == declareQueryParam.getCustType()
                || StringUtils.isBlank(declareQueryParam.getOperatorCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        declareQueryParam.setManageResNo(null);
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setStatus(DeclareStatus.WAIT_TO_OPER_OS.getCode());
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);

        PageContext.setPage(page);
        PageData<DeclareEntBaseInfo> pageData = null;
        List<DeclareEntBaseInfo> list = declareMapper.queryOpenSys4TaskListPage(declareParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 开启系统
     *
     * @param apprParam 开启系统参数
     * @throws HsException
     */
    @Override
    public void openSystem(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "openSystem", "input param:", apprParam + "");
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId()) || null == apprParam.getIsPass()) {
            throw new HsException(RespCode.PARAM_ERROR, "参数不能为空");
        }
        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (StringUtils.isBlank(taskId)) {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        DeclareInfo declareInfo = declareMapper.queryDeclareById(apprParam.getApplyId());
/*        List<DeclareAptitude> list = declareMapper.queryAptitudeByApplyId(apprParam.getApplyId());
        boolean existLegalBackFile = false;
        for (DeclareAptitude aptitude : list) {
            if (AptitudeType.LEGAL_REP_CRE_BACK.getCode() == aptitude.getAptitudeType()) {
                existLegalBackFile = true;
            }
        }
        if (declareInfo.getLegalCreType() == null || declareInfo.getLegalCreType() != 2) {
            HsAssert.isTrue(existLegalBackFile, BSRespCode.BS_DECLARE_NEED_BASE_FILE);
        }*/
        if (DeclareStatus.OPEN_OS_SUCCESS.getCode() == declareInfo.getAppStatus()) {// 已经成功开启系统

            throw new HsException(BSRespCode.BS_DECLARE_SYSTEM_OPENED, "该申报企业已成功开启系统");
        }

        try {
            if (apprParam.getIsPass()) {// 同意开启系统

                DeclareRegInfo declareRegInfo = new DeclareRegInfo();
                BeanUtils.copyProperties(declareInfo, declareRegInfo);

                // 校验申报信息
                this.validateDeclare(declareRegInfo);

                // 检验增值设置
                this.validateBMSetting(declareRegInfo);

                //20160425 by yangjg,业务变更：申报不做唯一性限制
//                boolean existLicenseInUC = bsEntService.isEntBusiLicenseNoExist(declareInfo.getLicenseNo(), declareInfo
//                        .getToCustType());
//                HsAssert.isTrue(!existLicenseInUC, BSRespCode.BS_DUPLICATION_IN_UC, "在UC中存在相同营业执照号的企业");

                boolean isFreeMemberEnt = false;
                if (CustType.MEMBER_ENT.getCode() == declareInfo.getToCustType()
                        && null != declareInfo.getToBuyResRange()
                        && ResType.FREE_MEMBER_ENT.getCode() == declareInfo.getToBuyResRange()) {// 免费成员企业
                    isFreeMemberEnt = true;
                }

                // 1.生成资源费记账分解记录
                // 2.生成合同
                // 3.生成证书(服务公司和托管企业)
                // 4.生成申报工具配置清单
                // 5.更新资源号使用状态
                // 6.更新申报订单客户号
                // 7.保存年费信息
                // 8.更新申报状态
                // 9.记录操作历史
                if (declareInfo.getAppStatus() == DeclareStatus.WAIT_TO_OPER_OS.getCode()) {
                    Date now = new Date();
                    String openSysDate = DateUtil.DateTimeToString(now);
                    declareInfo.setOpenAccDate(openSysDate); // 开启系统日期为当前日期,包含时间
                    declareInfo.setAppStatus(DeclareStatus.OPEN_OS_SUCCESS.getCode());
                    // 企业客户号为企业互生号+开启系统日期
                    declareInfo.setToEntCustId(declareInfo.getToEntResNo() + DateUtil.getDateNoSign(now));
                    openSystemService.handleOpenSysLocal(declareInfo, apprParam, isFreeMemberEnt);
                }
                if (!isFreeMemberEnt) {
                    try {
                        // 非免费成员企业，执行同步BM逻辑
                        this.syncOpenBM(declareInfo, apprParam.getOptCustId());
                    } catch (Exception e) {
                        SystemLog.error(this.getClass().getName(), "openSystem", BSRespCode.BS_DECLARE_OPEN_SYS_ERROR.getCode()+ ":开启系统时同步BM失败[param=" + apprParam + "]", e);
//                        throw new HsException(BSRespCode.BS_SYNC_BM_OPEN_ENT_FAIL, "开启系统同步BM失败," + e);
                    }
                }
                try {
                    // 执行同步UC逻辑
                    this.syncOpenUC(declareInfo, apprParam.getOptCustId());
                } catch (Exception e) {
                    SystemLog.error(this.getClass().getName(), "openSystem", BSRespCode.BS_DECLARE_OPEN_SYS_ERROR.getCode()+ ":开启系统时同步UC开户失败[param=" + apprParam + "]", e);
//                    throw new HsException(BSRespCode.BS_SYNC_UC_OPEN_ENT_FAIL, "开启系统同步UC失败," + e);
                }
            } else {
                // 拒绝开户:释放互生号,更新申报单状态,作废申报订单,记录日志
                openSystemService.rejectOpenSys(declareInfo, apprParam);
            }
            // 结束任务状态
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "openSystem", BSRespCode.BS_DECLARE_OPEN_SYS_ERROR.getCode()
                    + ":开启系统失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_OPEN_SYS_ERROR, "开启系统失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 申报开启系统同步UC
     *
     * @param declareInfo 申报信息
     * @param optCustId   操作员客户号
     * @throws HsException
     */
    private void syncOpenUC(DeclareInfo declareInfo, String optCustId) throws HsException {
        // 未同步UC开户才可以进行同步开户
        if (DeclareStatus.OPEN_OS_SUCCESS.getCode() == declareInfo.getAppStatus() && !declareInfo.getOpenAccFlag()) {
            // 1.调用户中心开户,返回客户号
            // 2.更新企业客户号,设开户状态为"已开户"
            openSystemService.openUc(declareInfo, optCustId);
        }
    }

    /**
     * 申报开启系统时同步UC失败手工重试
     *
     * @param applyId  申报编号
     * @param operator 操作员
     * @throws HsException
     */
    @Override
    public void syncOpenUC(String applyId, Operator operator) throws HsException {
        DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
        if (declareInfo == null) {
            throw new HsException(BSRespCode.BS_NOT_QUERY_DATA, "没有找到申报记录,applyId=" + applyId);
        }
        syncOpenUC(declareInfo, operator.getOptId());
    }

    /**
     * 申报开启系统同步BM
     *
     * @param declareInfo 申报信息
     * @param optCustId   操作员客户号
     * @throws HsException
     */
    private void syncOpenBM(DeclareInfo declareInfo, String optCustId) throws HsException {
        // 未同步开启增值才能进行开启增值
        if (DeclareStatus.OPEN_OS_SUCCESS.getCode() == declareInfo.getAppStatus() && !declareInfo.getOpenVasFlag()) {
            // 1.调用增值系统开启增值
            // 2.更新增值开启标识为"已开启"
            openSystemService.openVas(declareInfo, optCustId);
        }
    }

    /**
     * 申报开启系统时同步BM失败手工重试
     *
     * @param applyId  申报编号
     * @param operator 操作员
     * @throws HsException
     */
    @Override
    public void syncOpenBM(String applyId, Operator operator) throws HsException {
        DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
        if (declareInfo == null) {
            throw new HsException(BSRespCode.BS_NOT_QUERY_DATA, "没有找到申报记录,applyId=" + applyId);
        }
        boolean isFreeMemberEnt = false;
        if (CustType.MEMBER_ENT.getCode() == declareInfo.getToCustType() && null != declareInfo.getToBuyResRange()
                && ResType.FREE_MEMBER_ENT.getCode() == declareInfo.getToBuyResRange()) {// 免费成员企业
            isFreeMemberEnt = true;
        }
        // 免费成员企业不参与增值分配，所以不需要开启增值树排位
        if (!isFreeMemberEnt) {
            syncOpenBM(declareInfo, operator.getOptId());
        }
    }

    /**
     * 服务公司查询授权码
     *
     * @param serResNo 服务公司互生号 必填
     * @param resNo    企业互生号
     * @param entName  企业名称
     * @param linkman  联系人姓名
     * @param page     分页信息 必填
     * @return 返回授权码列表, 没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareAuthCode> serviceQueryAuthCode(String serResNo, String resNo, String entName,
                                                          String linkman, Page page) throws HsException {

        PageContext.setPage(page);
        PageData<DeclareAuthCode> pageData = null;
        // 服务公司授权码查询只能查询办理期的
        List<DeclareAuthCode> declareAuthCodeList = declareMapper.queryAuthCodeListPage(DeclareStatus.HANDLING
                .getCode(), null, serResNo, resNo, entName, linkman);

        if (null != declareAuthCodeList && declareAuthCodeList.size() > 0) {
            pageData = new PageData<DeclareAuthCode>();
            pageData.setResult(declareAuthCodeList);
            pageData.setCount(PageContext.getPage().getCount());
        }

        return pageData;
    }

    /**
     * 管理公司查询授权码
     *
     * @param manResNo 管理公司互生号 必填
     * @param resNo    企业互生号
     * @param entName  企业名称
     * @param linkman  联系人姓名
     * @param page     分页信息 必填
     * @return 返回授权码列表, 没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareAuthCode> manageQueryAuthCode(String manResNo, String resNo, String entName, String linkman,
                                                         Page page) throws HsException {

        PageContext.setPage(page);
        PageData<DeclareAuthCode> pageData = null;
        // 服务公司授权码查询只能查询办理期的
        List<DeclareAuthCode> declareAuthCodeList = declareMapper.queryAuthCodeListPage(DeclareStatus.HANDLING
                .getCode(), manResNo, null, resNo, entName, linkman);

        if (null != declareAuthCodeList && declareAuthCodeList.size() > 0) {
            pageData = new PageData<DeclareAuthCode>();
            pageData.setResult(declareAuthCodeList);
            pageData.setCount(PageContext.getPage().getCount());
        }

        return pageData;
    }

    /**
     * 地区平台查询授权码
     *
     * @param resNo   企业互生号
     * @param entName 企业名称
     * @param linkman 联系人姓名
     * @param page    分页信息 必填
     * @return 返回授权码列表, 没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<DeclareAuthCode> platQueryAuthCode(String resNo, String entName, String linkman, Page page)
            throws HsException {

        PageContext.setPage(page);
        PageData<DeclareAuthCode> pageData = null;
        // 平台可以查询所有状态授权码
        List<DeclareAuthCode> declareAuthCodeList = declareMapper.queryAuthCodeListPage(null, null, null, resNo,
                entName, linkman);

        if (null != declareAuthCodeList && declareAuthCodeList.size() > 0) {
            pageData = new PageData<DeclareAuthCode>();
            pageData.setResult(declareAuthCodeList);
            pageData.setCount(PageContext.getPage().getCount());
        }

        return pageData;
    }

    /**
     * 查询服务公司配额数
     *
     * @param countryNo  国家代码
     * @param provinceNo 省代码
     * @param cityNo     城市代码
     * @return 服务公司配额数
     */
    @Override
    public Integer getServiceQuota(String countryNo, String provinceNo, String cityNo) {
        return entResNoService.getServiceQuota(countryNo, provinceNo, cityNo);
    }

    /**
     * 查询成员企业、托管企业配额数
     *
     * @param serResNo 服务公司互生号
     * @param custType 客户类型
     * @param resType  启用资源类型
     * @return 成员企业或托管企业配额数
     */
    @Override
    public Integer getNotServiceQuota(String serResNo, Integer custType, Integer resType) {
        return entResNoService.getNotServiceQuota(serResNo, custType, resType);
    }

    /**
     * 查询服务公司可用互生号列表(服务公司用)
     *
     * @param countryNo  国家代码
     * @param provinceNo 省代码
     * @param cityNo     城市代码
     * @return 服务公司可用互生号列表
     */
    @Override
    public List<String> getSerResNoList(String countryNo, String provinceNo, String cityNo) {
        return entResNoService.getSerResNoList(countryNo, provinceNo, cityNo, ResStatus.NOT_USED.getCode());
    }

    /**
     * 地区平台查询服务公司可用互生号列表(地区平台用)
     *
     * @param countryNo  国家代码
     * @param provinceNo 省代码
     * @param cityNo     城市代码
     * @return 服务公司可用互生号列表
     */
    @Override
    public List<String> getSerResNoList4Plat(String countryNo, String provinceNo, String cityNo) {
        return entResNoService.getSerResNoList(countryNo, provinceNo, cityNo, ResStatus.USED.getCode());
    }

    /**
     * 查询已占用互生号资源详情
     *
     * @param resNo 互生号
     * @return {@code ResNo}
     */
    @Override
    public ResNo queryUsedSerResNoByResNo(String resNo) {
        return entResNoService.queryUsedSerResNoByResNo(resNo);
    }

    /**
     * 查询成员企业、托管企业可用互生号列表
     *
     * @param serResNo 服务公司互生号
     * @param custType 客户类型
     * @param resType  启用资源类型
     * @return 成员企业或托管企业可用互生号列表
     */
    @Override
    public List<String> getNotSerResNoList(String serResNo, Integer custType, Integer resType) {
        return entResNoService.getNotSerResNoList(serResNo, custType, resType);
    }

    /**
     * 查询管理公司信息
     *
     * @param countryNo  国家代码
     * @param provinceNo 省代码
     * @return 管理公司信息
     */
    @Override
    public ManageEnt queryManageEnt(String countryNo, String provinceNo) {
        String manResNo = entResNoService.getManResNo(countryNo, provinceNo);
        if (StringUtils.isBlank(manResNo)) {
            return null;
        } else {
            // 调用用户中心查询管理公司信息
            BsEntMainInfo bsEntMainInfo = commonService.getEntMainInfoToUc(manResNo, null);
            if (null != bsEntMainInfo) {
                return new ManageEnt(bsEntMainInfo.getEntResNo(), bsEntMainInfo.getEntName());
            } else {
                return null;
            }
        }
    }

    /**
     * 查询管理公司信息和服务公司配额数
     *
     * @param countryNo  国家代码
     * @param provinceNo 省代码
     * @param cityNo     城市代码
     * @return 管理公司信息和服务公司配额数
     */
    @Override
    public Map<String, Object> queryManageEntAndQuota(String countryNo, String provinceNo, String cityNo) {
        Map<String, Object> map = new HashMap<String, Object>();

        String manResNo = entResNoService.getManResNo(countryNo, provinceNo);
        if (StringUtils.isBlank(manResNo)) {
            return null;
        }
        BsEntMainInfo bsEntMainInfo = commonService.getEntMainInfoToUc(manResNo, null);

        ManageEnt manageEnt = null;
        if (null != bsEntMainInfo) {
            manageEnt = new ManageEnt(bsEntMainInfo.getEntResNo(), bsEntMainInfo.getEntName());
        }

        Integer serQuota = entResNoService.getServiceQuota(countryNo, provinceNo, cityNo);
        map.put("quota", serQuota);
        map.put("manageEnt", manageEnt);
        return map;
    }

    /**
     * 查询成员企业、托管企业配额数和可用互生号列表
     *
     * @param serResNo 服务公司互生号
     * @param custType 客户类型
     * @param resType  启用资源类型
     * @return 成员企业、托管企业配额数和可用互生号列表
     */
    @Override
    public Map<String, Object> getResNoListAndQuota(String serResNo, Integer custType, Integer resType) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> resNoList = entResNoService.getNotSerResNoList(serResNo, custType, resType);
        Integer quota = null == resNoList ? 0 : resNoList.size();

        BsEntMainInfo bsEntMainInfo = commonService.getEntMainInfoToUc(serResNo, null);

        map.put("quota", quota);
        map.put("resNoList", resNoList);
        map.put("serCustName", bsEntMainInfo == null ? "" : bsEntMainInfo.getEntName());
        map.put("serCustId", bsEntMainInfo == null ? "" : bsEntMainInfo.getEntCustId());
        return map;
    }

    /**
     * 查询积分增值点信息
     *
     * @param resNo 互生号
     * @return 积分增值点信息
     */
    @Override
    public Increment queryIncrement(String resNo) {
        HsAssert.hasText(resNo, BSRespCode.BS_PARAMS_EMPTY, "挂载互生号[resNo]为空");
        String result = bsUfRegionPackService.queryMlmData(null, resNo);
        Increment increment = null;
        if (StringUtils.isNotBlank(result)) {
            increment = JSONObject.parseObject(result, Increment.class);
        }
        return increment;
    }

    /**
     * 查询积分增值点信息(平台用)
     *
     * @param spreadResNo 推广互生号
     * @param subResNo    挂载互生号
     * @return 积分增值点信息
     */
    @Override
    public Increment queryIncrement(String spreadResNo, String subResNo) {
        HsAssert.hasText(spreadResNo, BSRespCode.BS_PARAMS_EMPTY, "推广互生号[spreadResNo]为空");
        HsAssert.hasText(spreadResNo, BSRespCode.BS_PARAMS_EMPTY, "挂载互生号[subResNo]为空");
        String result = bsUfRegionPackService.queryMlmData(spreadResNo, subResNo);
        Increment increment = null;
        if (StringUtils.isNotBlank(result)) {
            increment = JSONObject.parseObject(result, Increment.class);
        }
        return increment;
    }

    /**
     * 根据申请编号查询客户类型
     *
     * @param applyId 申请编号
     * @return 客户类型
     */
    @Override
    public Integer getCustTypeByApplyId(String applyId) {
        DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
        return declareInfo == null ? null : declareInfo.getToCustType();
    }

    /**
     * 查询申报进行步骤
     *
     * @param applyId 申请编号
     * @return 返回 0:未找到该申报 1.企业系统注册信息填写完成,2.企业工商登记信息填写完成,
     * 3.企业联系信息填写完成,4.企业银行账户信息填写完成,5.企业上传资料填写完成
     */
    @Override
    public Integer queryDeclareStep(String applyId) {
        List<DeclareAptitude> aptList = declareMapper.queryAptitudeByApplyId(applyId);
        if (aptList != null && aptList.size() > 0) {
            // 5.企业上传资料填写完成
            return 5;
        }
        DeclareBank declareBank = declareMapper.queryBankByApplyId(applyId);
        if (null != declareBank) {
            // 4.企业银行账户信息填写完成,
            return 4;
        }
        DeclareLinkman declareLinkman = declareMapper.queryLinkmanByApplyId(applyId);
        if (null != declareLinkman) {
            // 3.企业联系信息填写完成
            return 3;
        }
        DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
        if (declareInfo != null) {
            if (StringUtils.isNotBlank(declareInfo.getEntAddr())) {
                // 2.企业工商登记信息填写完成,企业地址在工商登录页签
                return 2;
            } else {
                // 1.企业系统注册信息填写完成,存在申报信息，但是没有工商登记信息
                return 1;
            }
        }
        // 0:未找到该申报单
        return 0;
    }

    /**
     * 删除未提交的申报申请
     *
     * @param applyId 申请编号
     * @throws HsException
     */
    @Override
    @Transactional
    public void delUnSubmitDeclare(String applyId) throws HsException {
        BizLog.biz(this.getClass().getName(), "delUnSubmitDeclare", "input param:", "applyId=" + applyId);
        if (StringUtils.isBlank(applyId)) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        Integer status = declareMapper.queryDeclareStatusById(applyId);
        if (status == null || status != DeclareStatus.WAIT_TO_SUBMIT.getCode()) {
            throw new HsException(BSRespCode.BS_DECLARE_CAN_NOT_DEL, "状态不是待提交，不能删除申报");
        }
        try {
            declareMapper.delBank(applyId);
            declareMapper.delApt(applyId);
            declareMapper.delLinkman(applyId);
            declareMapper.delDeclareApp(applyId);
            // TODO 删除操作历史
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "delUnSubmitDeclare", BSRespCode.BS_DECLARE_DEL_ERROR.getCode()
                    + ":删除申报失败[param=" + applyId + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_DEL_ERROR, "删除申报失败[param=" + applyId + "]" + e);
        }

    }

    /**
     * 查询申报办理状态信息
     *
     * @param applyId 申请编号
     * @param page    分页参数
     * @return 办理状态信息列表
     * @throws HsException
     */
    @Override
    public PageData<OptHisInfo> queryDeclareHis(String applyId, Page page) throws HsException {

        if (StringUtils.isBlank(applyId) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return operationService.queryOptHisListPage(applyId, OptTable.T_BS_DECLARE_ENT_APPR.getCode(), page);
    }

    /**
     * 服务公司查询申报办理状态信息,不包含管理公司初审通过，并且前端管理公司初审复核显示文字不一样
     *
     * @param applyId 申请编号
     * @param page    分页参数
     * @return 办理状态信息列表
     * @throws HsException
     */
    @Override
    public PageData<OptHisInfo> serviceQueryDeclareHis(String applyId, Page page) throws HsException {

        if (StringUtils.isBlank(applyId) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageContext.setPage(page);
        PageData<OptHisInfo> pageData = null;
        List<OptHisInfo> list = declareMapper.serviceQueryDeclareHisListPage(applyId);
        if (null != list && list.size() > 0) {
            pageData = new PageData<OptHisInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 查询授权码已过期的申报申请单
     *
     * @return 已过期的申报申请单列表
     */
    @Override
    public List<ExpiryDeclare> getExpiryDeclareList() {
        return declareMapper.getExpiryDeclareList();
    }

    /**
     * 更新已过期的申报申请单
     *
     * @param expiryDeclare 已过期的申报申请单
     * @throws HsException
     */
    @Override
    @Transactional
    public void updateExpiryDeclare(ExpiryDeclare expiryDeclare) throws HsException {
        SystemLog.debug(this.getClass().getName(), "updateExpiryDeclare", "input param:" + expiryDeclare);
        // 1.更新申报状态为已过期
        DeclareEntStatus declareEntStatus = new DeclareEntStatus();
        declareEntStatus.setApplyId(expiryDeclare.getApplyId());
        declareEntStatus.setAppStatus(DeclareStatus.EXPIRED.getCode());
        declareMapper.updateDeclareStatus(declareEntStatus);

        // 2.更新资源费订单状态为已过期
        orderService.updateOrderStatus(expiryDeclare.getOrderNo(), OrderStatus.IS_EXPIRE.getCode());
        // 3.释放互生号
        entResNoService.updateEntResNoStatus(expiryDeclare.getCustType(), expiryDeclare.getEntResNo(),
                ResStatus.NOT_USED.getCode());

        // 4.记录操作历史
        String entName = BSConstants.SYS_OPERATOR;
        try {
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            BsEntBaseInfo entBaseInfo = commonService.getEntBaseInfoToUc(localInfo.getPlatResNo(), null);
            entName = entBaseInfo.getEntName();
        } catch (Exception e) {
            SystemLog.warn(bsConfig.getSysName(), "updateExpiryDeclare", "申报订单失效记录办理状态时获取地区平台名称失败。" + e);
        }
        operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), expiryDeclare.getApplyId(),
                DeclareBizAction.PAYING.getCode(), DeclareResult.PAYING_EXPIRED.getCode(), "0000",
                null, entName, null, null, null);
    }

    /**
     * 查询申报信息
     *
     * @param applyId 申请编号
     * @return 申报信息
     */
    @Override
    public DeclareInfo queryDeclareById(String applyId) {
        return declareMapper.queryDeclareById(applyId);
    }

    /**
     * 付款成功后更新申报相关信息
     *
     * @param applyId 申请编号
     * @param order   申请编号
     * @throws HsException
     */
    @Override
    @Transactional
    public void updateDeclareAfterPay(String applyId, Order order) throws HsException {

        try {
            DeclareInfo declareInfo = declareMapper.queryDeclareById(applyId);
            if (DeclareStatus.OPEN_OS_SUCCESS.getCode() == declareInfo.getAppStatus()) {
                // 欠费申报订单付款成功后将订单状态改为“已完成”
                order.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());
            } else {
                order.setOrderStatus(OrderStatus.WAIT_CONFIRM.getCode());
            }
            // 更改申报订单状态
            orderService.updateOrderAllStatus(order);
            // 更新申报状态为"待开启系统",收费标识为"已收费"
            DeclareEntStatus declareEntStatus = new DeclareEntStatus();
            if (DeclareStatus.HANDLING.getCode() == declareInfo.getAppStatus()) {
                // 办理期申报单在支付成功后进入待开启系统状态
                declareEntStatus.setAppStatus(DeclareStatus.WAIT_TO_OPER_OS.getCode());// 待开启系统
                // 生成申报开启系统任务，业务主体是被申报企业
                taskService.saveTask(new Task(declareInfo.getApplyId(), TaskType.TASK_TYPE121.getCode(), commonService
                        .getAreaPlatCustId(), declareInfo.getToEntResNo(), declareInfo.getToEntCustName()));
            } else if (DeclareStatus.OPEN_OS_SUCCESS.getCode() == declareInfo.getAppStatus()) {
                // 欠款的已开启系统的申报单在支付成功后进行资源费分配
                resFeeResolveService.resolveResFee(declareInfo);
            } else if (DeclareStatus.OPEN_OS_REJECTED.getCode() == declareInfo.getAppStatus()) {// 开启系统驳回
                SystemLog.warn(bsConfig.getSysName(), "updateDeclareAfterPay", "已驳回的申报订单付款成功，请线下人工退款！[applyId="
                        + applyId + ",order=" + order + "]");
            } else if (DeclareStatus.EXPIRED.getCode() == declareInfo.getAppStatus()) {// 已过期
                SystemLog.warn(bsConfig.getSysName(), "updateDeclareAfterPay", "已失效的申报订单付款成功，请线下人工退款！[applyId="
                        + applyId + ",order=" + order + "]");
            }
            declareEntStatus.setApplyId(applyId);
            declareEntStatus.setChargeFlag(true);// 已收费
            // 更新申报单收费标识
            declareMapper.updateDeclareStatus(declareEntStatus);
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "updateDeclareAfterPay",
                    BSRespCode.BS_DECLARE_AFTER_PAY_UPDATE_ERROR.getCode() + ":申报单支付成功通知处理失败[applyId=" + applyId
                            + ",order=" + order + "]", e);

            throw new HsException(BSRespCode.BS_DECLARE_AFTER_PAY_UPDATE_ERROR, "申报单支付成功通知处理失败[applyId=" + applyId
                    + ",order=" + order + "]" + e);
        }
    }

    /**
     * 查询公告期企业
     *
     * @return 公告期企业列表
     */
    @Override
    public List<EntBaseInfo> queryPlacardEnt() {
        return declareMapper.queryEntInfo(null, DeclareStatus.HANDLING.getCode(), null);
    }

    /**
     * 查询企业申报信息
     *
     * @return 企业申报信息列表
     */
    @Override
    public List<EntBaseInfo> queryEntInfo(String entName) {
        String notInStatus = "(" + DeclareStatus.EXPIRED.getCode() + ")";
        return declareMapper.queryEntInfo(entName, null, notInStatus);
    }

    /**
     * 通过授权码查询申报企业信息
     *
     * @param authCode 授权码
     * @return 申报企业信息
     */
    @Override
    public EntInfo queryEntInfoByAuthCode(String authCode) {
        DeclareInfo declareInfo = declareMapper.queryDeclareByAuthCode(authCode);
        if (null == declareInfo || DeclareStatus.HANDLING.getCode() != declareInfo.getAppStatus())// 只有在办理期中才可以查
        {
            return null;
        }
        EntInfo entInfo = new EntInfo();
        entInfo.setCustType(declareInfo.getToCustType());
        entInfo.setEntName(declareInfo.getToEntCustName());
        entInfo.setEntNameEn(declareInfo.getToEntEnName());
        entInfo.setEntResNo(declareInfo.getToEntResNo());
        entInfo.setApplyDate(declareInfo.getCreatedDate());
        entInfo.setCountryNo(declareInfo.getCountryNo());
        entInfo.setProvinceNo(declareInfo.getProvinceNo());
        entInfo.setCityNo(declareInfo.getCityNo());
        entInfo.setBizRegAddr(declareInfo.getEntAddr());
        entInfo.setBizRegPhone(declareInfo.getPhone());
        entInfo.setOrderNo(declareInfo.getOrderNo());

        Order order = orderService.getOrderByNo(declareInfo.getOrderNo());
        if (null != order) {
            entInfo.setAmount(order.getOrderCashAmount());
            entInfo.setPayStatus(order.getPayStatus());
        }
        entInfo.setToLimiteDate(declareInfo.getToLimiteDate());
        entInfo.setLegalName(declareInfo.getLegalName());
        entInfo.setEntType(declareInfo.getEntType());
        entInfo.setLicenseNo(declareInfo.getLicenseNo());
        entInfo.setOrgNo(declareInfo.getOrgNo());
        entInfo.setTaxNo(declareInfo.getTaxNo());
        entInfo.setEstablishingDate(declareInfo.getEstablishingDate());
        entInfo.setLimitDate(declareInfo.getLimitDate());
        entInfo.setDealArea(declareInfo.getDealArea());

        DeclareLinkman declareLinkman = declareMapper.queryLinkmanByApplyId(declareInfo.getApplyId());
        if (null != declareLinkman) {
            entInfo.setLinkman(declareLinkman.getLinkman());
            entInfo.setLinkmanMobile(declareLinkman.getMobile());
            entInfo.setLinkmanAddr(declareLinkman.getAddress());
            entInfo.setEmail(declareLinkman.getEmail());
            entInfo.setZipCode(declareLinkman.getZipCode());
            entInfo.setJob(declareLinkman.getJob());
            entInfo.setQq(declareLinkman.getQq());
            entInfo.setWebSite(declareLinkman.getWebSite());
            entInfo.setFax(declareLinkman.getFax());
        }

        return entInfo;
    }

    /**
     * 查询申报进度
     *
     * @param entName 企业名称
     * @return 企业申报信息
     */
    @Override
    public List<DeclareProgress> queryDeclareProgress(String entName) {
        if (StringUtils.isBlank(entName)) {
            return null;
        }
        return declareMapper.queryDeclareProgress(entName);
    }

    /**
     * 查询关联企业申报增值点维护列表
     *
     * @param declareQueryParam 查询条件
     * @param page              分页信息
     * @return 申报增值点列表
     * @throws HsException
     */
    @Override
    public PageData<DeclareEntBaseInfo> queryIncrementList(DeclareQueryParam declareQueryParam, Page page)
            throws HsException {
        if (null == declareQueryParam || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        declareQueryParam.setCustType(null);
        declareQueryParam.setManageResNo(null);
        declareQueryParam.setDeclarerResNo(null);
        declareQueryParam.setOperatorCustId(null);
        DeclareParam declareParam = new DeclareParam();
        BeanUtils.copyProperties(declareQueryParam, declareParam);
        // 不包含状态（0：待提交内审STS,4：内审驳回SAR,5：初审驳回PAR,6：复核驳回PBR,9：开启系统成功RGS,10：开启系统驳回,11：已过期EXP）
        String notInStatus = "(" + DeclareStatus.WAIT_TO_SUBMIT.getCode() + ","
                + DeclareStatus.INNER_REJECTED.getCode() + "," + DeclareStatus.FIRST_APPR_REJECTED.getCode() + ","
                + DeclareStatus.REVIEW_REJECTED.getCode() + "," + DeclareStatus.OPEN_OS_SUCCESS.getCode() + ","
                + DeclareStatus.OPEN_OS_REJECTED.getCode() + "," + DeclareStatus.EXPIRED.getCode() + ")";
        declareParam.setNotInStatus(notInStatus);
        return this.queryDeclare(declareParam, page);
    }

    /**
     * 保存申报增值点信息
     *
     * @param declareIncrement 申报增值点信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void saveIncrement(DeclareIncrement declareIncrement) throws HsException {
        BizLog.biz(this.getClass().getName(), "saveIncrement", "input param:", declareIncrement + "");
        if (null == declareIncrement || StringUtils.isBlank(declareIncrement.getApplyId())
                || StringUtils.isBlank(declareIncrement.getOptCustId())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        try {
            DeclareInfo declareInfo = declareMapper.queryDeclareById(declareIncrement.getApplyId());
            if (declareInfo == null) {
                throw new HsException(BSRespCode.BS_NOT_QUERY_DATA, "申报记录未找到" + declareIncrement);
            }
            if (DeclareStatus.OPEN_OS_SUCCESS.getCode() == declareInfo.getAppStatus()) {// 已经开启系统后不可以修改增值点
                throw new HsException(BSRespCode.BS_DECLARE_SYSTEM_OPENED, "已开启系统后不能修改增值点");
            }
            boolean isFreeMemberEnt = false;
            if (CustType.MEMBER_ENT.getCode() == declareInfo.getToCustType() && null != declareInfo.getToBuyResRange()
                    && ResType.FREE_MEMBER_ENT.getCode() == declareInfo.getToBuyResRange()) {// 免费成员企业
                isFreeMemberEnt = true;
            }
            /**
             * 对增值排位参数做一个简单校验
             */
            if (!isFreeMemberEnt) { // 免费成员企业不参与增值分配，所以不需要校验
                if (declareInfo.getToMResNo().substring(0, 2).equals(declareInfo.getSpreadEntResNo().substring(0, 2))) {// 同管理公司申报
                    // 增值父节点编号至少也有11位
                    if (declareIncrement.getToInodeResNo() == null || declareIncrement.getToInodeResNo().length() < 11) {
                        throw new HsException(RespCode.PARAM_ERROR, "保存增值点失败,父节点值设置有误：declareInfo=" + declareInfo);
                    }

                    if (CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType()) {
                        if (!declareInfo.getToMResNo().substring(0, 2).equals(declareIncrement.getToInodeResNo().substring(0, 2))) {
                            throw new HsException(RespCode.PARAM_ERROR, "保存增值点失败,父节点必须与被申报服务公司同一个管理公司：declareInfo="
                                    + declareInfo);
                        }
                    } else {
                        if (HsResNoUtils.isAreaPlatResNo(declareInfo.getFrEntResNo())) {
                            HsAssert.isTrue(StringUtils.left(declareInfo.getToEntResNo(), 2).equals(StringUtils.left(declareIncrement.getToInodeResNo(), 2)), RespCode.PARAM_ERROR, "[平台]父节点必须与被申报企业同一管理公司");
                        } else {
                            HsAssert.isTrue(StringUtils.left(declareInfo.getToEntResNo(), 5).equals(StringUtils.left(declareIncrement.getToInodeResNo(), 5)), RespCode.PARAM_ERROR, "[服务]父节点必须与被申报企业同一服务公司");
                        }
                    }
                }
                declareMapper.saveIncrement(declareIncrement);
            }
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "saveIncrement", BSRespCode.BS_DECLARE_SAVE_POINT_SETTING_ERROR
                    .getCode()
                    + ":保存积分增值点失败[param=" + declareIncrement + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_POINT_SETTING_ERROR, "保存增值点失败[param=" + declareIncrement
                    + "]");
        }
    }

    /**
     * 查询服务公司可用互生号列表(服务公司用)
     *
     * @param countryNo  国家代码
     * @param provinceNo 省代码
     * @param cityNo     城市代码
     * @param resNo      拟用互生号
     * @param page       分页
     * @return 服务公司可用互生号列表
     */
    @Override
    public PageData<ResNo> getSerResNos(String countryNo, String provinceNo, String cityNo, String resNo, Page page)
            throws HsException {
        if (StringUtils.isBlank(countryNo) || StringUtils.isBlank(provinceNo) || StringUtils.isBlank(cityNo)
                || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        return entResNoService.getSerResNos(countryNo, provinceNo, cityNo, ResStatus.NOT_USED.getCode(), resNo, page);
    }

    /**
     * 查询成员企业、托管企业可用互生号列表
     *
     * @param serResNo 服务公司互生号
     * @param custType 客户类型
     * @param resType  启用资源类型
     * @param resNo    拟用互生号
     * @param page     分页
     * @return 成员企业或托管企业可用互生号列表
     */
    @Override
    public PageData<ResNo> getEntResNos(String serResNo, Integer custType, Integer resType, String resNo, Page page)
            throws HsException {
        if (StringUtils.isBlank(serResNo) || null == custType || null == resType || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return entResNoService.getEntResNos(serResNo, custType, resType, resNo, page);
    }

    /**
     * 重新提交申报已被驳回或过期的企业
     *
     * @param oldApplyId 被驳回的申请编号
     * @param optInfo    操作员信息
     * @return 新的申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public DeclareRegInfo reApplyDeclare(String oldApplyId, OptInfo optInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "reApplyDeclare", "input param:", "oldApplyId=" + oldApplyId
                + ",optInfo=" + optInfo);
        if (StringUtils.isBlank(oldApplyId) || null == optInfo || StringUtils.isBlank(optInfo.getOptCustId())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try {
            String applyId;
            DeclareInfo declareInfo = declareMapper.queryDeclareById(oldApplyId);
            if (declareInfo.isRedoFlag()) {
                // 已经重新申报的申报单不能多次重新申报
                throw new HsException(BSRespCode.BS_DECLARE_REAPPLY_APPLYING, "已经重新申报的申报单不能多次重新申报");
            }
            if (DeclareStatus.INNER_REJECTED.getCode() == declareInfo.getAppStatus()// 内审驳回
                    || DeclareStatus.FIRST_APPR_REJECTED.getCode() == declareInfo.getAppStatus()// 初审驳回
                    || DeclareStatus.REVIEW_REJECTED.getCode() == declareInfo.getAppStatus()// 复核驳回
                    || DeclareStatus.OPEN_OS_REJECTED.getCode() == declareInfo.getAppStatus()// 开启系统驳回
                    || DeclareStatus.EXPIRED.getCode() == declareInfo.getAppStatus())// 已过期
            {
                // 拷贝申报企业申请单T_BS_DECLARE_ENT
                DeclareRegInfo declareRegInfo = new DeclareRegInfo();
                BeanUtils.copyProperties(declareInfo, declareRegInfo);
                declareRegInfo.setApplyId(null);
                declareRegInfo.setCreatedDate(null);
                declareRegInfo.setOptCustId(optInfo.getOptCustId());
                applyId = this.createDeclare(declareRegInfo);

                DeclareBizRegInfo declareBizRegInfo = new DeclareBizRegInfo();
                BeanUtils.copyProperties(declareInfo, declareBizRegInfo);
                declareBizRegInfo.setApplyId(applyId);
                declareBizRegInfo.setEntName(declareInfo.getToEntCustName());
                declareBizRegInfo.setEntAddress(declareInfo.getEntAddr());
                declareBizRegInfo.setLinkmanMobile(declareInfo.getPhone());
                this.createDeclareEnt(declareBizRegInfo);

                // 拷贝申报企业联系信息T_BS_DECLARE_ENT_LINKMAN
                DeclareLinkman declareLinkman = declareMapper.queryLinkmanByApplyId(oldApplyId);
                if (null != declareLinkman) {
                    declareLinkman.setApplyId(applyId);
                    declareLinkman.setOptCustId(optInfo.getOptCustId());
                    this.createLinkman(declareLinkman);
                }

                // 拷贝申报企业资质附件T_BS_DECLARE_CORP_APTITUDE
                List<DeclareAptitude> aptList = declareMapper.queryAptitudeByApplyId(oldApplyId);
                List<DeclareAptitude> aptListNew = new ArrayList<DeclareAptitude>();
                if (null != aptList && aptList.size() > 0) {
                    for (DeclareAptitude declareAptitude : aptList) {
                        declareAptitude.setApplyId(applyId);
                        declareAptitude.setAptitudeId(null);
                        declareAptitude.setOptCustId(optInfo.getOptCustId());
                        aptListNew.add(declareAptitude);
                    }
                    this.serviceSaveDeclareAptitude(aptListNew, optInfo);
                }

                // 拷贝申报企业银行帐号T_BS_DECLARE_BANK_ACCOUNT
                DeclareBank declareBank = declareMapper.queryBankByApplyId(oldApplyId);
                if (null != declareBank) {
                    declareBank.setApplyId(applyId);
                    declareBank.setOptCustId(optInfo.getOptCustId());
                    this.createBank(declareBank);
                }
                // 更新旧申报单重新申报标识
                declareMapper.updateRedoFlag(oldApplyId);
            } else {
                // 不是被驳回或已过期的申请不能重新申报(不能重新提交正在申报的申请)
                throw new HsException(BSRespCode.BS_DECLARE_REAPPLY_APPLYING, "不是被驳回或已过期的申请不能重新申报");
            }
            return this.queryDeclareRegInfoByApplyId(applyId);
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "reApplyDeclare", BSRespCode.BS_DECLARE_REAPPLY_ERROR.getCode()
                    + ":重新提交申报已被驳回或过期的企业失败[param=" + oldApplyId + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_REAPPLY_ERROR, "重新提交申报已被驳回或过期的企业失败[param=" + oldApplyId + "]"
                    + e);
        }

    }

    /**
     * 查询申报基本信息
     *
     * @param applyId 申请编号
     * @return 申报基本信息
     */
    @Override
    public DeclareEntBaseInfo queryDeclareEntBaseInfoById(String applyId) {
        return declareMapper.queryDeclareEntBaseInfoById(applyId);
    }

    /**
     * 根据Id查询资源费分配记录
     *
     * @param detailId 记录编号
     * @return 资源费分配记录
     */
    @Override
    public ResFeeAlloc queryResFeeAllocById(String detailId) {
        return resFeeAllocMapper.queryResFeeAllocById(detailId);
    }

    /**
     * 记录申报编辑操作历史
     *
     * @param applyId 申报业务编号
     * @param optInfo 操作信息
     */
    private void createModifyOptHis(String applyId, OptInfo optInfo) {
        Integer appStatus = declareMapper.queryDeclareStatusById(applyId);
        DeclareBizAction bizAction = null;
        DeclareResult declareResult = null;
        if (DeclareStatus.WAIT_TO_INNER_APPR.getCode() == appStatus) {
            bizAction = DeclareBizAction.SUBMIT;
            declareResult = DeclareResult.SERVICE_MODIFY;
        } else if (DeclareStatus.WAIT_TO_FIRST_APPR.getCode() == appStatus) {
            bizAction = DeclareBizAction.APPROVED;
            declareResult = DeclareResult.MANAGE_APPR_MODIFY;
        } else if (DeclareStatus.WAIT_TO_APPR_REVIEW.getCode() == appStatus) {
            bizAction = DeclareBizAction.APPROVED;
            declareResult = DeclareResult.MANAGE_REVIEW_MODIFY;
        } else if (DeclareStatus.WAIT_TO_OPER_OS.getCode() == appStatus) {
            bizAction = DeclareBizAction.OPEN_SYS;
            declareResult = DeclareResult.OPEN_SYS_MODIFY;
        }
        if (bizAction != null) {
            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_DECLARE_ENT_APPR.getCode(), applyId, bizAction.getCode(),
                    declareResult.getCode(), optInfo.getOptCustId(), optInfo.getOptName(), optInfo.getOptEntName(), optInfo
                            .getOptRemark(), optInfo.getDblOptCustId(), optInfo.getChangeContent());
        }
    }

    /**
     * 服务公司保存申报企业资质附件信息，带备注说明
     *
     * @param declareAptitudes 申报企业资质附件信息 必填
     * @param optInfo          操作员信息
     * @param remark           备注说明
     * @return
     * @throws HsException
     * @see com.gy.hsxt.bs.api.apply.IBSDeclareService#serviceSaveDeclareAptitude(java.util.List,
     * com.gy.hsxt.bs.bean.base.OptInfo, java.lang.String)
     */
    @Override
    public List<DeclareAptitude> serviceSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo,
                                                            String remark) throws HsException {
        BizLog.biz(this.getClass().getName(), "serviceSaveDeclareAptitude", "declareAptitudes=", JSON
                .toJSONString(declareAptitudes)
                + ",optInfo=" + optInfo);
        HsAssert.isTrue(CollectionUtils.isNotEmpty(declareAptitudes), BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        try {
            return this.saveDeclareApt(declareAptitudes, optInfo, remark, 1);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "serviceSaveDeclareAptitude", "保存申报企业附件信息失败[param="
                    + declareAptitudes + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_APT_INFO_ERROR, "保存申报企业附件信息失败，原因：" + e.getMessage());
        }
    }

    /**
     * 管理公司保存申报企业资质附件信息，带备注说明
     *
     * @param declareAptitudes 申报企业资质附件信息 必填
     * @param optInfo          操作员信息
     * @param remark           备注说明
     * @return
     * @throws HsException
     * @see com.gy.hsxt.bs.api.apply.IBSDeclareService#manageSaveDeclareAptitude(java.util.List,
     * com.gy.hsxt.bs.bean.base.OptInfo, java.lang.String)
     */
    @Override
    public List<DeclareAptitude> manageSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo,
                                                           String remark) throws HsException {
        BizLog.biz(this.getClass().getName(), "manageSaveDeclareAptitude", "declareAptitudes=", JSON
                .toJSONString(declareAptitudes)
                + ",optInfo=" + optInfo);
        HsAssert.isTrue(CollectionUtils.isNotEmpty(declareAptitudes), BSRespCode.BS_PARAMS_NULL, "参数不能为空");

        try {
            return this.saveDeclareApt(declareAptitudes, optInfo, remark, 2);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "manageSaveDeclareAptitude", "保存申报企业附件信息失败[param="
                    + declareAptitudes + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_APT_INFO_ERROR, "保存申报企业附件信息失败，原因：" + e.getMessage());
        }
    }

    /**
     * 地区平台保存申报企业资质附件信息，带备注说明
     *
     * @param declareAptitudes 申报企业资质附件信息 必填
     * @param optInfo          操作员信息
     * @param remark           备注说明
     * @return
     * @throws HsException
     * @see com.gy.hsxt.bs.api.apply.IBSDeclareService#platSaveDeclareAptitude(java.util.List,
     * com.gy.hsxt.bs.bean.base.OptInfo, java.lang.String)
     */
    @Override
    public List<DeclareAptitude> platSaveDeclareAptitude(List<DeclareAptitude> declareAptitudes, OptInfo optInfo,
                                                         String remark) throws HsException {
        BizLog.biz(this.getClass().getName(), "platSaveDeclareAptitude", "declareAptitudes=", JSON
                .toJSONString(declareAptitudes)
                + ",optInfo=" + optInfo);
        HsAssert.isTrue(CollectionUtils.isNotEmpty(declareAptitudes), BSRespCode.BS_PARAMS_NULL, "参数不能为空");

        try {
            return this.saveDeclareApt(declareAptitudes, optInfo, remark, 3);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "platSaveDeclareAptitude", "保存申报企业附件信息失败[param="
                    + declareAptitudes + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_SAVE_APT_INFO_ERROR, "保存申报企业附件信息失败,原因：" + e.getMessage());
        }
    }

    /**
     * 重试失败的开启增值与UC开户
     */
    @Override
    public void retryOpenSyncFail() {
        List<DeclareInfo> syncFailList = declareMapper.queryDeclareSyncFail();
        for (DeclareInfo declareInfo : syncFailList) {
            boolean joinBM = true; //是否参与增值分配，  免费成员企业不参与增值分配
            if (declareInfo.getToBuyResRange() != null && CustType.MEMBER_ENT.getCode() == declareInfo.getToCustType()
                    && ResType.FREE_MEMBER_ENT.getCode() == declareInfo.getToBuyResRange()) {
                joinBM = false;
            }
            //参与增值分配并且未开启增值的申报单才需要进行重试
            if (joinBM && !declareInfo.getOpenVasFlag()) {
                try {
                    this.syncOpenBM(declareInfo, declareInfo.getOptCustId());
                } catch (Exception e) {
                    SystemLog.error(this.getClass().getName(), "retryOpenSyncFail", "自动重试开启增值失败,申报单信息:"
                            + declareInfo, e);
                }
            }
            //UC未开启成功的重新开启
            if (!declareInfo.getOpenAccFlag()) {
                try {
                    this.syncOpenUC(declareInfo, declareInfo.getOptCustId());
                } catch (Exception e) {
                    SystemLog.error(this.getClass().getName(), "retryOpenSyncFail", "自动重试UC开户失败,申报单信息:"
                            + declareInfo, e);
                }
            }
        }
    }

    /**
     * 处理失效的申报单
     *
     * @see com.gy.hsxt.bs.apply.interfaces.IDeclareService#autoExpiryDeclare()
     */
    @Override
    public void autoExpiryDeclare() {
        List<ExpiryDeclare> expiryDeclareList = this.getExpiryDeclareList();
        for (ExpiryDeclare expiryDeclare : expiryDeclareList) {
            try {
                this.updateExpiryDeclare(expiryDeclare);
            } catch (Exception e) {
                SystemLog.error(this.getClass().getName(), "updateExpiryDeclare", "更新已过期的申报申请失败[param=" + expiryDeclare + "]", e);
            }
        }
    }

    /**
     * 校验互生号是否可用
     *
     * @param entResNo 企业资源号
     * @return
     * @throws HsException
     */
    @Override
    public Boolean checkValidEntResNo(String entResNo) throws HsException {
        HsAssert.hasText(entResNo,BSRespCode.BS_PARAMS_EMPTY,"企业互生号[entResNo]不能为空");
        return entResNoService.checkValidEntResNo(HsResNoUtils.getCustTypeByHsResNo(entResNo),entResNo);
    }
}
