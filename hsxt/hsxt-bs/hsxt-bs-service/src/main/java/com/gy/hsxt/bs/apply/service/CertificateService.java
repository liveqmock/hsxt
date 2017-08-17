/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.apply.IBSCertificateService;
import com.gy.hsxt.bs.apply.bean.CertificateParam;
import com.gy.hsxt.bs.apply.interfaces.ICertificateService;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.apply.interfaces.ITemplateService;
import com.gy.hsxt.bs.apply.mapper.CertificateMapper;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.apply.CreType;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.apply.SealStatus;
import com.gy.hsxt.bs.common.enumtype.apply.TempletType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

/**
 * 证书管理业务实现
 * 
 * @Package : com.gy.hsxt.bs.apply.service
 * @ClassName : CertificateService
 * @Description : 证书管理业务实现
 * @Author : xiaofl
 * @Date : 2015-12-16 下午3:05:44
 * @Version V1.0
 */
@Service
public class CertificateService implements IBSCertificateService, ICertificateService {

    @Resource
    private CertificateMapper certificateMapper;

    @Resource
    private BsConfig bsConfig;

    @Resource
    private ICommonService commonService;

    @Resource
    private IDeclareService declareService;

    @Resource
    private IUCBsEntService bsEntService;

    /**
     * 模版业务接口
     */
    @Resource
    private ITemplateService templateService;

    /**
     * 创建证书模板
     * 
     * @param templet
     *            证书模板
     * @throws HsException
     */
    @Override
    public void createTemplet(Templet templet) throws HsException {
        HsAssert.notNull(templet, BSRespCode.BS_PARAMS_NULL, "证书模版[templet]不能为null");
        HsAssert.notNull(templet.getCustType(), BSRespCode.BS_PARAMS_NULL, "适用企业类型[custType]为null");
        if (HsResNoUtils.isServiceResNo(templet.getCustType()))
        {// 服务公司：第三方业务证书
            templet.setTempletType(TempletType.THIRD_PARTY_CRE.getCode());
        }
        else if (HsResNoUtils.isTrustResNo(templet.getCustType()))
        {// 托管企业：销售资格证书(创业资源)
            HsAssert.isTrue(templet.getResType() == ResType.ENTREPRENEURSHIP_RES.getCode(),
                    BSRespCode.BS_PARAMS_TYPE_ERROR, "托管企业目前只有创业资源类型可以创建销售资格证书模版");
            templet.setTempletType(TempletType.SALES_CRE.getCode());
        }
        else
        {
            throw new HsException(BSRespCode.BS_PARAMS_TYPE_ERROR, "只有服务公司和托管企业两种类型有证书");
        }
        templateService.createTemplet(templet);
    }

    /**
     * 修改证书模板
     * 
     * @param templet
     *            证书模板
     * @throws HsException
     */
    @Override
    public void modifyTemplet(Templet templet) throws HsException {
        HsAssert.notNull(templet, BSRespCode.BS_PARAMS_NULL, "证书模版[templet]不能为null");
        HsAssert.notNull(templet.getCustType(), BSRespCode.BS_PARAMS_NULL, "适用企业类型[custType]为null");
        if (HsResNoUtils.isServiceResNo(templet.getCustType()))
        {// 服务公司：第三方业务证书
            templet.setTempletType(TempletType.THIRD_PARTY_CRE.getCode());
        }
        else if (HsResNoUtils.isTrustResNo(templet.getCustType()))
        {// 托管企业：销售资格证书
            templet.setTempletType(TempletType.SALES_CRE.getCode());
        }
        else
        {
            throw new HsException(BSRespCode.BS_PARAMS_TYPE_ERROR, "只有服务公司和托管企业两种类型才有证书");
        }
        templateService.modifyTemplet(templet);
    }

    /**
     * 查看证书模板详情
     * 
     * @param templetId
     *            合同模板ID
     * @return 返回合同模板详情，没有则返回null
     * @throws HsException
     */
    @Override
    public Templet queryTempletById(String templetId) throws HsException {
        return templateService.queryTempletById(templetId);
    }

    /**
     * 分页查询证书模板
     * 
     * @param query
     *            模板名称
     * @param page
     *            分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<Templet> queryTempletList(TempletQuery query, Page page) throws HsException {
        return templateService.queryTempletList(query, page);
    }

    /**
     * 分页查询待复核证书模板
     * 
     * @param query
     *            模板查询实体
     * @param page
     *            分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<Templet> queryTemplet4Appr(TempletQuery query, Page page) throws HsException {
        HsAssert.notNull(query, BSRespCode.BS_PARAMS_NULL, "分页对象[query]为空");
        return templateService.queryTemplet4Appr(query, page);
    }

    /**
     * 复核证书模板
     * 
     * @param templateAppr
     *            审批内容
     * @throws HsException
     */
    @Override
    public void apprTemplet(TemplateAppr templateAppr) throws HsException {
        templateService.apprTemplet(templateAppr);
    }

    /**
     * 查询模版最新审核记录
     * 
     * @param templetId
     *            模版ID
     * @return TemplateAppr
     * @throws HsException
     */
    @Override
    public TemplateAppr queryLastTemplateAppr(String templetId) throws HsException {
        return templateService.queryLastTemplateAppr(templetId);
    }

    /**
     * 启用证书模板
     * 
     * @param templetId
     *            模板ID 必填
     * @param operator
     *            操作员
     * @throws HsException
     */
    @Override
    public void enableTemplet(String templetId, String operator) throws HsException {
        templateService.enableTemplet(templetId, operator);
    }

    /**
     * 停用证书模板
     * 
     * @param templetId
     *            模板ID 必填
     * @param operator
     *            操作员
     * @throws HsException
     */
    @Override
    public void disableTemplet(String templetId, String operator) throws HsException {
        templateService.disableTemplet(templetId, operator);
    }

    /**
     * 查询第三方业务证书发放
     * 
     * @param certificateQueryParam
     *            查询条件
     * @param page
     *            分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<CertificateBaseInfo> queryBizCre4Send(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException {
        if (null == certificateQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        CertificateParam certificateParam = new CertificateParam();
        BeanUtils.copyProperties(certificateQueryParam, certificateParam);

        certificateParam.setCertificateType(CreType.THIRD_PARTY_CRE.getCode());// 第三方业务证书
        certificateParam.setSealStatus(SealStatus.TAKE_EFFECT.ordinal());// 已生效
        certificateParam.setInStatus(null);

        return this.queryCre(certificateParam, page);
    }

    /**
     * 查询第三方业务证书历史
     * 
     * @param certificateQueryParam
     *            查询条件
     * @param page
     *            分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<CertificateBaseInfo> queryBizCre4His(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException {
        if (null == certificateQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        CertificateParam certificateParam = new CertificateParam();
        BeanUtils.copyProperties(certificateQueryParam, certificateParam);
        certificateParam.setCertificateType(CreType.THIRD_PARTY_CRE.getCode());// 第三方业务证书
        certificateParam.setInStatus(null);

        return this.queryCre(certificateParam, page);
    }

    /**
     * 查询销售证书盖章
     * 
     * @param certificateQueryParam
     *            查询条件
     * @param page
     *            分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<CertificateBaseInfo> querySaleCre4Seal(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException {
        if (null == certificateQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        CertificateParam certificateParam = new CertificateParam();
        BeanUtils.copyProperties(certificateQueryParam, certificateParam);

        certificateParam.setCertificateType(CreType.SALES_CRE.getCode());// 销售资格证书
        String inStatus = "(" + SealStatus.WAIT_TO_SEAL.ordinal() + "," + SealStatus.TAKE_EFFECT.ordinal() + ","
                + SealStatus.WAIT_TO_RESEAL.ordinal() + ")";// 待盖章、已生效
        certificateParam.setInStatus(inStatus);
        certificateParam.setCertificateNo(null);
        certificateParam.setSendStatus(null);

        return this.queryCre(certificateParam, page);
    }

    /**
     * 查询销售证书发放
     * 
     * @param certificateQueryParam
     *            查询条件
     * @param page
     *            分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<CertificateBaseInfo> querySaleCre4Send(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException {
        if (null == certificateQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        CertificateParam certificateParam = new CertificateParam();
        BeanUtils.copyProperties(certificateQueryParam, certificateParam);

        certificateParam.setCertificateType(CreType.SALES_CRE.getCode());// 销售资格证书
        certificateParam.setSealStatus(SealStatus.TAKE_EFFECT.ordinal());// 已生效
        certificateParam.setInStatus(null);

        return this.queryCre(certificateParam, page);
    }

    /**
     * 查询销售证书历史
     * 
     * @param certificateQueryParam
     *            查询条件
     * @param page
     *            分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<CertificateBaseInfo> querySaleCre4His(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException {
        if (null == certificateQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        CertificateParam certificateParam = new CertificateParam();
        BeanUtils.copyProperties(certificateQueryParam, certificateParam);

        certificateParam.setCertificateType(CreType.SALES_CRE.getCode());// 销售资格证书
        certificateParam.setInStatus(null);

        return this.queryCre(certificateParam, page);
    }

    private PageData<CertificateBaseInfo> queryCre(CertificateParam certificateParam, Page page) {
        PageContext.setPage(page);
        List<CertificateBaseInfo> creList = certificateMapper.queryCreListPage(certificateParam);
        return PageData.bulid(PageContext.getPage().getCount(), creList);
    }

    /**
     * 通过ID查询证书
     * 
     * @param certificateNo
     *            证书唯一识别码
     * @return 返回证书信息
     * @throws HsException
     */
    @Override
    public Certificate queryCreById(String certificateNo) throws HsException {
        HsAssert.hasText(certificateNo, BSRespCode.BS_PARAMS_EMPTY, "证书ID[certificateNo]不能为空");
        try
        {
            Certificate certificate = certificateMapper.queryCreById(certificateNo);
            // 查询证书模版
            if (certificate != null)
            {
                Templet templet = queryTempletById(certificate.getTempletId());
                certificate.setTemplet(templet);
            }
            return certificate;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "queryCreById", "通过ID查询证书失败[certificateNo=" + certificateNo
                    + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "通过ID查询证书失败,原因:" + e.getMessage());
        }
    }

    /**
     * 通过ID查询证书内容
     * 
     * @param certificateNo
     *            证书唯一识别码
     * @return 返回证书内容
     * @param realTime 是否实时
     * @throws HsException
     */
    @Override
    public CertificateContent queryCreContentById(String certificateNo,boolean realTime) throws HsException {
        if (isBlank(certificateNo))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        return this.getCertificateContent(certificateNo, realTime);
    }

    /**
     * 打印证书
     * 
     * @param certificateNo
     *            证书编号 必填
     * @param operator
     *            操作员 必填
     * @return 返回证书内容，没有则返回null
     * @throws HsException
     */
    @Override
    public CertificateContent printCertificate(String certificateNo, String operator) throws HsException {
        HsAssert.hasText(certificateNo,BSRespCode.BS_PARAMS_NULL, "证书编号[certificateNo]不能为空");

        // 更新证书打印次数
        certificateMapper.updatePrintCount(certificateNo, operator);

        return this.getCertificateContent(certificateNo, true);
    }

    /**
     * 证书盖章
     * 
     * @param certificateNo
     *            证书编号 必填
     * @param operator
     *            操作员 必填
     * @throws HsException
     */
    @Override
    @Transactional
    public void sealCertificate(String certificateNo, String operator) throws HsException {
        if (isBlank(certificateNo) || isBlank(operator))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            // 如果已盖章，把此证书设为已失效，然后再生成一个新证书，设新证书为已生效
            Certificate certificate = certificateMapper.queryCreById(certificateNo);
            if (SealStatus.TAKE_EFFECT.ordinal() == certificate.getStatus()
                    || SealStatus.WAIT_TO_RESEAL.ordinal() == certificate.getStatus())
            {// 证书已盖章，重新盖章时需要获取企业当前最新资料
             // 获取企业当前名称与联系人，联系电话
                BsEntMainInfo mainInfo = bsEntService.getMainInfoByResNo(certificate.getEntResNo());
                if (mainInfo != null)
                {
                    // 把证书设为已失效
                    certificateMapper.updateSealStatus(certificateNo, SealStatus.LOSE_EFFICACY.ordinal(), operator);
                    // 生成新证书
                    Certificate certificateNew = new Certificate();
                    certificateNew.setEntResNo(certificate.getEntResNo());
                    certificateNew.setEntCustId(certificate.getEntCustId());
                    certificateNew.setApplyId(certificate.getApplyId());
                    certificateNew.setTempletId(certificate.getTempletId());
                    certificateNew.setRegDate(certificate.getRegDate());
                    certificateNew.setCertificateType(certificate.getCertificateType());
                    certificateNew.setEntCustName(mainInfo.getEntName());
                    certificateNew.setLinkman(mainInfo.getContactPerson());
                    certificateNew.setMobile(mainInfo.getContactPhone());
                    certificateNew.setSealDate(DateUtil.DateTimeToString(new Date()));
                    certificateNew.setSealOperator(operator);
                    CerVarContent cerVarContent = this.getCurrentVarContent(certificate.getApplyId());
                    this.buildCertificate(certificateNew, cerVarContent, null);
                }
            }
            else if (SealStatus.WAIT_TO_SEAL.ordinal() == certificate.getStatus())
            {// 未盖章
                certificateMapper.updateSealStatus(certificateNo, SealStatus.TAKE_EFFECT.ordinal(), operator);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "sealCertificate", BSRespCode.BS_CRE_SEAL_ERROR.getCode()
                    + ":证书盖章失败[certificateNo=" + certificateNo + "]", e);
            throw new HsException(BSRespCode.BS_CRE_SEAL_ERROR, "证书盖章失败[certificateNo=" + certificateNo + "]" + e);
        }
    }

    /**
     * 发放证书
     * 
     * @param certificateSendHis
     *            证书发放信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendCertificate(CertificateSendHis certificateSendHis) throws HsException {
        if (null == certificateSendHis)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            String certificateNo = certificateSendHis.getCertificateNo();
            // 更新发放状态
            certificateMapper.updateSendStatus(certificateNo);
            // 记录发放历史
            certificateSendHis.setBizNo(GuidAgent.getStringGuid(""));
            certificateMapper.createSendHis(certificateSendHis);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "sendCertificate", BSRespCode.BS_CRE_SEND_ERROR.getCode()
                    + ":证书发放失败[param=" + certificateSendHis + "]", e);
            throw new HsException(BSRespCode.BS_CRE_SEND_ERROR, "证书发放失败[param=" + certificateSendHis + "]" + e);
        }
    }

    /**
     * 查询证书发放历史
     * 
     * @param certificateNo
     *            证书唯一识别码
     * @param page
     *            分页信息
     * @return {@code PageData}
     */
    @Override
    public PageData<CertificateSendHis> queryCreSendHis(String certificateNo, Page page) throws HsException {
        HsAssert.hasText(certificateNo, BSRespCode.BS_PARAMS_EMPTY, "证书编号[certificateNo]为空");
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");

        PageContext.setPage(page);
        PageData<CertificateSendHis> pageData = null;
        List<CertificateSendHis> sendHisList = certificateMapper.querySendHisListPage(certificateNo);
        if (null != sendHisList && sendHisList.size() > 0)
        {
            pageData = new PageData<>();
            pageData.setResult(sendHisList);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 生成证书
     * 
     * @param certificate
     *            证书内容
     * @param cerVarContent
     *            证书占位符替换内容
     * @param declareInfo 申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void buildCertificate(Certificate certificate, CerVarContent cerVarContent,DeclareInfo declareInfo)
            throws HsException {
        HsAssert.notNull(certificate, BSRespCode.BS_PARAMS_NULL, "参数不能为空");

        String tmpId;
        Integer certificateType = null;
        if (StringUtils.isNotBlank(certificate.getTempletId()))
        {// 重新生成新证书,直接是已生效的
            tmpId = certificate.getTempletId();
            certificateType = certificate.getCertificateType();
            certificate.setStatus(SealStatus.TAKE_EFFECT.ordinal());// 已生效
        }
        else
        {// 第一次生成证书
            HsAssert.notNull(declareInfo, BSRespCode.BS_PARAMS_NULL, "申报信息[declareInfo]为空");
            if (CustType.SERVICE_CORP.getCode() == declareInfo.getToCustType())
            {// 服务公司
                certificateType = TempletType.THIRD_PARTY_CRE.getCode();// 第三方业务证书
                certificate.setStatus(SealStatus.TAKE_EFFECT.ordinal());// 已生效
            }
            else if (CustType.TRUSTEESHIP_ENT.getCode() == declareInfo.getToCustType())
            {// 托管企业
                certificateType = TempletType.SALES_CRE.getCode();// 销售资格证书
                certificate.setStatus(SealStatus.WAIT_TO_SEAL.ordinal());// 待盖章
            }
            certificate.setRegDate(declareInfo.getOpenAccDate());
            // 根据客户类型、资源类型找出相应的证书模板
            TempletQuery query = new TempletQuery();
            query.setTempletType(certificateType);
            query.setCustType(declareInfo.getToCustType());
            query.setResType(declareInfo.getToBuyResRange());
            Templet currentTpl = templateService.queryCurrentTplByQuery(query);

            HsAssert.notNull(currentTpl, BSRespCode.BS_CRE_TMP_NOT_FOUND, "未找到可用的证书模板");
            tmpId = currentTpl.getTempletId();
        }

        try
        {
            String randonCode = RandomCodeUtils.getMixCode(10);// 10位随机码
            String certificateNo = certificate.getEntResNo() + randonCode;// 互生号+10位随机码
            certificate.setCertificateNo(certificateNo);
            certificate.setTempletId(tmpId);
            certificate.setCertificateType(certificateType);
            cerVarContent.setUniqueCode(randonCode);
            certificate.setVarContent(cerVarContent.toString());
            certificateMapper.createCertificate(certificate);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "genCertificate", BSRespCode.BS_CRE_GENERATE_ERROR.getCode()
                    + ":生成证书失败[param:certificate=" + certificate + ",cerVarContent=" + cerVarContent + "]", e);
            throw new HsException(BSRespCode.BS_CRE_GENERATE_ERROR, "生成证书失败[param:certificate=" + certificate
                    + ",cerVarContent=" + cerVarContent + "]" + e);
        }
    }

    /**
     * 重要信息变更之后修改证书记录
     *
     * @param entChangeInfo 证书
     * @throws HsException
     */
    @Override
    public void updateCertificateForChangeInfo(EntChangeInfo entChangeInfo) throws HsException {

        Certificate certificateInDB = certificateMapper.queryEffectCerByCustId(entChangeInfo.getEntCustId());
        //更新待盖章的记录
        if (certificateInDB != null && ((certificateInDB.getStatus() == SealStatus.WAIT_TO_SEAL.ordinal()&&certificateInDB.getCertificateType()==CreType.SALES_CRE.getCode())
                ||certificateInDB.getCertificateType()==CreType.THIRD_PARTY_CRE.getCode()&&certificateInDB.getPrintCount()==null)) {
            Certificate certificate = new Certificate();
            certificate.setCertificateNo(certificateInDB.getCertificateNo());
            certificate.setStatus(certificateInDB.getStatus());
            String randonCode = StringUtils.substring(certificateInDB.getCertificateNo(), 11);// 10位随机码
            CerVarContent cerVarContent = this.getCurrentVarContent(certificateInDB.getApplyId());
            cerVarContent.setUniqueCode(randonCode);// 唯一识别码
            if (StringUtils.isNotEmpty(entChangeInfo.getLinkmanMobileNew())) {
                // 被申报企业联系电话(乙方)
                certificate.setMobile(entChangeInfo.getLinkmanMobileNew());
                cerVarContent.setMobile(entChangeInfo.getLinkmanMobileNew());
            }
            if (StringUtils.isNotEmpty(entChangeInfo.getLinkmanNew())) {
                // 被申报企业授权代表(乙方)
                certificate.setLinkman(entChangeInfo.getLinkmanNew());
                cerVarContent.setLinkman(entChangeInfo.getLinkmanNew());
            }
            if (StringUtils.isNotEmpty(entChangeInfo.getCustNameNew())) {
                cerVarContent.setEntCustName(entChangeInfo.getCustNameNew());// 被申报企业名称(乙方)
                certificate.setEntCustName(entChangeInfo.getCustNameNew());
            }
            certificate.setVarContent(cerVarContent.toString());
            certificateMapper.updateCerForChangeInfo(certificate);
        }
    }

    /**
     * 查询销售证书主要信息
     * 
     * @param certificateNo
     *            证书唯一识别码
     * @param entResNo
     *            证书编号
     * @return 销售证书主要信息
     */
    @Override
    public CertificateMainInfo querySaleCer(String certificateNo, String entResNo) {
        if (isBlank(certificateNo) || isBlank(entResNo))
        {
            return null;
        }
        // 做一个查询兼容处理，兼容证书唯一识别码包含互生号和不包含互生号两种情况
        if (!certificateNo.contains(entResNo))
        {
            certificateNo = entResNo + certificateNo;
        }
        return certificateMapper.queryCerMainInfo(CreType.SALES_CRE.getCode(), certificateNo, entResNo);
    }

    /**
     * 查询第三方证书主要信息
     * 
     * @param certificateNo
     *            证书唯一识别码
     * @param entResNo
     *            证书编号
     * @return 第三方证书主要信息
     */
    @Override
    public CertificateMainInfo queryThirdPartCer(String certificateNo, String entResNo) {
        if (isBlank(certificateNo) || isBlank(entResNo))
        {
            return null;
        }
        // 做一个查询兼容处理，兼容证书唯一识别码包含互生号和不包含互生号两种情况
        if (!certificateNo.contains(entResNo))
        {
            certificateNo = entResNo + certificateNo;
        }
        return certificateMapper.queryCerMainInfo(CreType.THIRD_PARTY_CRE.getCode(), certificateNo, entResNo);
    }

    /**
     * 根据申报单号查询当前证书占位符最新内容
     * 
     * @param applyId
     * @return
     */
    private CerVarContent getCurrentVarContent(String applyId) {
        // 申报信息
        DeclareInfo declareInfo = declareService.queryDeclareById(applyId);
        // 被申报者信息
        BsEntMainInfo entInfo = bsEntService.getMainInfoByResNo(declareInfo.getToEntResNo());

        // 推广企业
        BsEntMainInfo spreadEntInfo = bsEntService.getMainInfoByResNo(declareInfo.getSpreadEntResNo());

        LocalInfo localInfo = commonService.getAreaPlatInfo();
        //地区平台
        BsEntMainInfo platInfo = bsEntService.getMainInfoByResNo(localInfo.getPlatResNo());
        CerVarContent cerVarContent = new CerVarContent();
        cerVarContent.setPlatName(platInfo.getEntName());// 发证单位

        cerVarContent.setSerResNo(declareInfo.getSpreadEntResNo());// 所属服务机构互生号
        cerVarContent.setSerEntName(spreadEntInfo.getEntName());// 所属服务机构

        cerVarContent.setEntResNo(declareInfo.getToEntResNo());// 证书编号
        cerVarContent.setEntCustName(entInfo.getEntName());// 企业名称
        cerVarContent.setLicenseNo(entInfo.getBusiLicenseNo());// 证件号码
        Date sendDate = DateUtil.StringToDate(declareInfo.getOpenAccDate(), DateUtil.DEFAULT_DATE_TIME_FORMAT);
        cerVarContent.setSendDate(DateUtil.DateToString(sendDate, "yyyy 年 MM 月 dd 日"));// 发证日期
        cerVarContent.setCheckAddress(localInfo.getContractVerifyAddr());// 验证地址

        cerVarContent.setLinkman(entInfo.getContactPerson());
        cerVarContent.setMobile(entInfo.getContactPhone());
        return cerVarContent;
    }

    private CertificateContent getCertificateContent(String certificateNo, boolean isRealTime) {

        try
        {
            CertificateContent certificateContent = certificateMapper.queryCreContentById(certificateNo);
            if (null == certificateContent)
            {
                return null;
            }
            // 印章文件URL
            certificateContent.setSealFileUrl(bsConfig.getSealFileUrl());

            if (isRealTime)
            {// 获取当前最新的占位符内容
             // 生成证书占位符替换内容
                CerVarContent cerVarContent = getCurrentVarContent(certificateContent.getApplyId());
                cerVarContent.setUniqueCode(certificateNo.substring(11));// 证书唯一识别码
                //原证书内容
                CerVarContent orgContent = StringUtils.isNotEmpty(certificateContent.getVarContent()) ? JSON.parseObject(certificateContent.getVarContent(), CerVarContent.class) : null;
                //检查一下用户中心是否有更新
                if (orgContent != null && (
                        !StringUtils.trimToEmpty(orgContent.getEntCustName()).equals(StringUtils.trimToEmpty(cerVarContent.getEntCustName()))
                                || !StringUtils.trimToEmpty(orgContent.getSerEntName()).equals(StringUtils.trimToEmpty(cerVarContent.getSerEntName()))
                                || !StringUtils.trimToEmpty(orgContent.getLinkman()).equals(StringUtils.trimToEmpty(cerVarContent.getLinkman()))
                                || !StringUtils.trimToEmpty(orgContent.getMobile()).equals(StringUtils.trimToEmpty(cerVarContent.getMobile()))
                                || !StringUtils.trimToEmpty(orgContent.getLicenseNo()).equals(StringUtils.trimToEmpty(cerVarContent.getLicenseNo()))
                )) {

                    //未盖章的销售证书，需要更新BS中证书记录
                    if (certificateContent.getCertificateType() == CreType.SALES_CRE.getCode() && SealStatus.WAIT_TO_SEAL.ordinal() == certificateContent.getSealStatus()) {
                        Certificate certificate = new Certificate();
                        certificate.setCertificateNo(certificateNo);
                        certificate.setStatus(certificateContent.getSealStatus());
                        certificate.setEntCustName(cerVarContent.getEntCustName());
                        certificate.setLinkman(cerVarContent.getLinkman());
                        certificate.setMobile(cerVarContent.getMobile());
                        certificate.setVarContent(cerVarContent.toString());

                        certificateMapper.updateCerForChangeInfo(certificate);
                    }
                    if (certificateContent.getCertificateType() == CreType.THIRD_PARTY_CRE.getCode()) {
                        Certificate cerInDb = certificateMapper.queryCreById(certificateNo);
                        //已经打印的，以重新打印处理
                        if (cerInDb.getPrintCount() != null && cerInDb.getPrintCount() > 0) {
                            certificateMapper.setLostEfficacyForThird(cerInDb.getEntResNo());
                            Certificate certificate = new Certificate();
                            BeanUtils.copyProperties(cerInDb, certificate);
                            String randonCode = RandomCodeUtils.getMixCode(10);// 10位随机码
                            String cerNo = certificate.getEntResNo() + randonCode;// 互生号+10位随机码
                            certificate.setCertificateNo(cerNo);
                            certificate.setEntCustName(cerVarContent.getEntCustName());
                            certificate.setLinkman(cerVarContent.getLinkman());
                            certificate.setMobile(cerVarContent.getMobile());
                            certificate.setStatus(SealStatus.TAKE_EFFECT.ordinal());
                            certificate.setSealOperator(BSConstants.SYS_OPERATOR);
                            cerVarContent.setUniqueCode(randonCode);
                            certificate.setVarContent(cerVarContent.toString());
                            certificateMapper.createCertificate(certificate);
                        }
                    }
                }
                // 证书占位符内容
                certificateContent.setVarContent(cerVarContent.toString());
            }
            return certificateContent;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "getCertificateContent", BSRespCode.BS_CRE_GENERATE_ERROR
                    .getCode()
                    + ":生成证书失败[certificateNo=" + certificateNo + ",isRealTime=" + isRealTime + "]", e);
            throw new HsException(BSRespCode.BS_CRE_GENERATE_ERROR, "生成证书失败[certificateNo=" + certificateNo
                    + ",isRealTime=" + isRealTime + "]" + e);
        }
    }
}
