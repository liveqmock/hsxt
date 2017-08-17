/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeePriceService;
import com.gy.hsxt.bs.api.apply.IBSContractService;
import com.gy.hsxt.bs.apply.bean.ContractContentInfo;
import com.gy.hsxt.bs.apply.interfaces.IContractService;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.apply.interfaces.ITemplateService;
import com.gy.hsxt.bs.apply.mapper.ContractMapper;
import com.gy.hsxt.bs.bean.apply.Contract;
import com.gy.hsxt.bs.bean.apply.ContractBaseInfo;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.bs.bean.apply.ContractMainInfo;
import com.gy.hsxt.bs.bean.apply.ContractSendHis;
import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import com.gy.hsxt.bs.bean.apply.VarContent;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.apply.SealStatus;
import com.gy.hsxt.bs.common.enumtype.apply.TempletTag;
import com.gy.hsxt.bs.common.enumtype.apply.TempletType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.utils.AmountConverter;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.resfee.interfaces.IResFeeService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;

/**
 * 合同管理
 * 
 * @Package : com.gy.hsxt.bs.apply.service
 * @ClassName : ContractService
 * @Description : 合同管理
 * @Author : xiaofl
 * @Date : 2015-12-16 下午3:11:19
 * @Version V1.0
 */
@Service
public class ContractService implements IBSContractService, IContractService {

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private BsConfig bsConfig;

    @Resource
    private IUCBsEntService bsEntService;

    @Resource
    private IDeclareService declareService;

    @Resource
    private ICommonService commonService;

    @Resource
    private IAnnualFeePriceService annualFeePriceService;

    @Resource
    private IResFeeService resFeeService;

    /**
     * 模版业务接口
     */
    @Resource
    private ITemplateService templateService;

    /**
     * 创建合同模板
     * 
     * @param templet
     *            模板
     * @throws HsException
     */
    @Override
    public void createTemplet(Templet templet) throws HsException {
        HsAssert.notNull(templet, BSRespCode.BS_PARAMS_NULL, "合同模板[templet]不能为null");
        HsAssert.notNull(templet.getCustType(), BSRespCode.BS_PARAMS_NULL, "适用企业类型[custType]为null");
        templet.setTempletType(TempletType.CONTRACT.getCode());

        templateService.createTemplet(templet);
    }

    /**
     * 修改合同模板
     * 
     * @param templet
     *            模板
     * @throws HsException
     */
    @Override
    public void modifyTemplet(Templet templet) throws HsException {
        HsAssert.notNull(templet, BSRespCode.BS_PARAMS_NULL, "合同模板[templet]不能为null");
        HsAssert.notNull(templet.getCustType(), BSRespCode.BS_PARAMS_NULL, "适用企业类型[custType]为null");
        templet.setTempletType(TempletType.CONTRACT.getCode());

        templateService.modifyTemplet(templet);
    }

    /**
     * 查看合同模板详情
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
        if (query == null)
        {
            query = new TempletQuery();
        }
        query.setTempletType(TempletType.CONTRACT.getCode());// 合同模版
        return templateService.queryTempletList(query, page);
    }

    /**
     * 分页查询待复核合同模板
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
        query.setTempletType(TempletType.CONTRACT.getCode());// 合同模版
        return templateService.queryTemplet4Appr(query, page);
    }

    /**
     * 复核合同模板
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
     * 启用合同模板
     * 
     * @param templetId
     *            合同模板ID 必填
     * @param operator
     *            操作员
     * @throws HsException
     */
    @Override
    public void enableTemplet(String templetId, String operator) throws HsException {
        templateService.enableTemplet(templetId, operator);
    }

    /**
     * 停用合同模板
     * 
     * @param templetId
     *            合同模板ID 必填
     * @param operator
     *            操作员
     * @throws HsException
     */
    @Override
    public void disableTemplet(String templetId, String operator) throws HsException {
        templateService.disableTemplet(templetId, operator);
    }

    /**
     * 服务公司查询合同
     * 
     * @param serResNo
     *            服务公司互生号 必填
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人
     * @param page
     *            分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<ContractBaseInfo> serviceQueryContract(String serResNo, String resNo, String entName,
            String linkman, Page page) throws HsException {
        HsAssert.hasText(serResNo, BSRespCode.BS_PARAMS_EMPTY, "互生号[serResNo]为空");
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");

        String serResNoPre = serResNo.substring(0, 5);// 服务公司互生号前5位
        // Integer status = SealStatus.TAKE_EFFECT.getCode();// 已生效
        String inStatus = " (" + SealStatus.TAKE_EFFECT.ordinal() + "," + SealStatus.WAIT_TO_RESEAL.ordinal() + ")";
        return this.queryContract(null, serResNoPre, resNo, entName, linkman, null, null, inStatus, page);
    }

    /**
     * 管理公司查询合同
     * 
     * @param manResNo
     *            管理公司互生号 必填
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人
     * @param page
     *            分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<ContractBaseInfo> managerQueryContract(String manResNo, String resNo, String entName,
            String linkman, Page page) throws HsException {
        HsAssert.hasText(manResNo, BSRespCode.BS_PARAMS_EMPTY, "管理互生号[manResNo]为空");
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        String manResNoPre = manResNo.substring(0, 2);// 管理公司互生号前2位
        // Integer status = SealStatus.TAKE_EFFECT.getCode();// 已生效
        String inStatus = " (" + SealStatus.TAKE_EFFECT.ordinal() + "," + SealStatus.WAIT_TO_RESEAL.ordinal() + ")";
        return this.queryContract(manResNoPre, null, resNo, entName, linkman, null, null, inStatus, page);
    }

    /**
     * 地区平台查询合同
     * 
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param linkman
     *            联系人
     * @param page
     *            分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<ContractBaseInfo> platQueryContract(String resNo, String entName, String linkman, Page page)
            throws HsException {
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        // Integer status = SealStatus.TAKE_EFFECT.getCode();// 已生效
        String inStatus = " (" + SealStatus.TAKE_EFFECT.ordinal() + "," + SealStatus.WAIT_TO_RESEAL.ordinal() + ")";
        return this.queryContract(null, null, resNo, entName, linkman, null, null, inStatus, page);
    }

    /**
     * 地区平台查询合同盖章
     * 
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param status
     *            盖章状态
     * @param page
     *            分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<ContractBaseInfo> queryContract4Seal(String resNo, String entName, Integer status, Page page)
            throws HsException {
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        String inStatus = "(" + SealStatus.WAIT_TO_SEAL.ordinal() + "," + SealStatus.TAKE_EFFECT.ordinal() + ","
                + SealStatus.WAIT_TO_RESEAL.ordinal() + ")";
        return this.queryContract(null, null, resNo, entName, null, status, null, inStatus, page);
    }

    /**
     * 地区平台查询合同发放
     * 
     * @param resNo
     *            企业互生号
     * @param entName
     *            企业名称
     * @param sendStatus
     *            发放状态
     * @param page
     *            分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<ContractBaseInfo> queryContract4Send(String resNo, String entName, Boolean sendStatus, Page page)
            throws HsException {
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        Integer status = SealStatus.TAKE_EFFECT.ordinal();// 已生效
        return this.queryContract(null, null, resNo, entName, null, status, sendStatus, null, page);
    }

    private PageData<ContractBaseInfo> queryContract(String manResNoPre, String serResNoPre, String resNo,
            String entName, String linkman, Integer status, Boolean sendStatus, String inStatus, Page page) {
        PageContext.setPage(page);
        List<ContractBaseInfo> contractList = contractMapper.queryContractListPage(manResNoPre, serResNoPre, resNo,
                entName, linkman, status, sendStatus, inStatus);

        return PageData.bulid(PageContext.getPage().getCount(), contractList);
    }

    /**
     * 查看合同信息
     * 
     * @param contractNo
     *            合同编号
     * @return 返回合同信息
     * @throws HsException
     */
    @Override
    public ContractBaseInfo queryContractById(String contractNo) throws HsException {
        HsAssert.hasText(contractNo, BSRespCode.BS_PARAMS_EMPTY, "合同编号[contractNo]为空");
        return contractMapper.queryContractBaseInfoById(contractNo);
    }

    /**
     * 查看合同内容--预览
     * 
     * @param contractNo
     *            合同编号
     * @return 合同内容
     * @throws HsException
     */
    @Override
    public ContractContent queryContractContent4View(String contractNo) throws HsException {
        HsAssert.hasText(contractNo, BSRespCode.BS_PARAMS_EMPTY, "合同编号[contractNo]为空");
        return this.getContractContent(contractNo, false);
    }

    /**
     * 查看合同内容--盖章
     * 
     * @param contractNo
     *            合同编号
     * @return 合同内容
     * @throws HsException
     */
    @Override
    public ContractContent queryContractContent4Seal(String contractNo,boolean realTime) {
        HsAssert.hasText(contractNo, BSRespCode.BS_PARAMS_EMPTY, "合同编号[contractNo]为空");
        return this.getContractContent(contractNo, realTime);
    }

    /**
     * 打印合同
     * 
     * @param contractNo
     *            合同编号
     * @return 合同内容
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ContractContent printContract(String contractNo) throws HsException {
        if (isBlank(contractNo))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 更新合同打印次数
        contractMapper.updatePrintCount(contractNo);
        return this.getContractContent(contractNo, false);
    }

    /**
     * 合同盖章
     * 
     * @param contractNo
     *            合同编号 必填
     * @throws HsException
     */
    @Override
    @Transactional
    public ContractContent sealContract(String contractNo) throws HsException {
        if (isBlank(contractNo))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            String contNo = contractNo;
            // 如果已盖章，把此合同设为已失效，然后再生成一个新合同，设新合同为已生效
            Contract contract = contractMapper.queryContractById(contractNo);
            if (SealStatus.TAKE_EFFECT.ordinal() == contract.getStatus()
                    || SealStatus.WAIT_TO_RESEAL.ordinal() == contract.getStatus())
            {// 合同已盖章

                // 把原合同设为已失效
                contractMapper.updateSealStatus(contractNo,null, SealStatus.LOSE_EFFICACY.ordinal());

                // 被申报者信息
                BsEntMainInfo entInfo = bsEntService.searchEntMainInfo(contract.getEntCustId());

                // 生成新合同
                Contract contractNew = new Contract();
                contractNew.setApplyId(contract.getApplyId());// 申报申请编号
                contractNew.setEntResNo(contract.getEntResNo());// 合同编号
                contractNew.setEntCustId(contract.getEntCustId());// 被申报者客户号
                contractNew.setEntCustName(entInfo.getEntName());// 被申报者名称
                contractNew.setCustType(contract.getCustType());// 被申报者客户类型
                contractNew.setLinkman(entInfo.getContactPerson());// 被申报者联系人
                contractNew.setMobile(entInfo.getContactPhone());// 被申报者客户类型
                contractNew.setRegDate(contract.getRegDate());// 注册日期
                contractNew.setTempletId(contract.getTempletId());// 合同模板ID

                // 合同占位符内容
                VarContent varContent = this.genVarContent(contract.getApplyId());
                this.genContract(contractNew, varContent, null);
                contNo = contractNew.getContractNo();
            }
            else if (SealStatus.WAIT_TO_SEAL.ordinal() == contract.getStatus())
            {// 未盖章
                // 合同占位符内容
                VarContent varContent = this.genVarContent(contract.getApplyId());
                varContent.setContractNo(StringUtils.substring(contract.getContractNo(), 11));
                contractMapper.updateSealStatus(contractNo, varContent.toString(),SealStatus.TAKE_EFFECT.ordinal());
            }
            return this.getContractContent(contNo,false);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "sealContract", BSRespCode.BS_CONTRACT_SEAL_ERROR.getCode()
                    + ":合同盖章失败 [contractNo=" + contractNo + "]", e);
            throw new HsException(BSRespCode.BS_CONTRACT_SEAL_ERROR, "合同盖章失败[contractNo=" + contractNo + "]" + e);
        }
    }

    /**
     * 发放合同
     * 
     * @param contractSendHis
     *            合同发放信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendContract(ContractSendHis contractSendHis) throws HsException {
        if (null == contractSendHis)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            String contractNo = contractSendHis.getUniqueNo();
            // 更新发放状态
            contractMapper.updateSendStatus(contractNo);
            // 记录发放历史
            contractSendHis.setBizNo(GuidAgent.getStringGuid(bsConfig.getAppNode()));
            contractMapper.createSendHis(contractSendHis);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "apprTemplet", BSRespCode.BS_CONTRACT_SEND_ERROR.getCode()
                    + ":合同发放失败[param=" + contractSendHis + "]", e);
            throw new HsException(BSRespCode.BS_CONTRACT_SEND_ERROR, "合同发放失败[param=" + contractSendHis + "]" + e);
        }
    }

    /**
     * 查询合同发放历史
     * 
     * @param contractNo
     *            合同编号 必填
     * @param page
     *            分页
     * @return {@code PageData<ContractSendHis>}
     * @throws HsException
     */
    @Override
    public PageData<ContractSendHis> queryContractSendHis(String contractNo, Page page) throws HsException {
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        try
        {
            PageContext.setPage(page);
            List<ContractSendHis> sendHisList = contractMapper.querySendHisListPage(contractNo);
            return PageData.bulid(PageContext.getPage().getCount(), sendHisList);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "queryContractSendHis", "查询合同发放历史失败[contractNo=" + contractNo
                    + "]", e);
            throw new HsException(BSRespCode.BS_CONTRACT_DB_ERROR, "查询合同发放历史失败，原因：" + e.getMessage());
        }
    }

    /**
     * 生成合同
     * 
     * @param contract
     *            合同内容
     * @param resType
     *            启用资源类型
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void genContract(Contract contract, VarContent varContect, Integer resType) throws HsException {
        HsAssert.notNull(contract, BSRespCode.BS_PARAMS_NULL, "合同内容[contract]为null");

        Integer tmpType = TempletType.CONTRACT.getCode();// 合同
        String tmpId;// 合同模板ID
        Integer status;
        if (!isBlank(contract.getTempletId()))
        {// 重新生成新合同
            tmpId = contract.getTempletId();
            status = SealStatus.TAKE_EFFECT.ordinal();// 已生效
        }
        else
        {// 第一次生成合同

            // 根据客户类型、资源类型找出相应的证书模板
            TempletQuery query = new TempletQuery();
            query.setTempletType(tmpType);
            query.setCustType(contract.getCustType());
            query.setResType(resType);
            Templet currentTpl = templateService.queryCurrentTplByQuery(query);
            HsAssert.notNull(currentTpl, BSRespCode.BS_CONTRACT_TMP_NOT_FOUND, "未找到可用的合同模板");
            tmpId = currentTpl.getTempletId();
            status = SealStatus.WAIT_TO_SEAL.ordinal();// 待盖章
        }

        try
        {
            String randonCode = RandomCodeUtils.getMixCode(10);// 10位随机码
            String contractNo = contract.getEntResNo() + randonCode;// 互生号+10位随机码
            contract.setContractNo(contractNo);
            contract.setTempletId(tmpId);
            contract.setStatus(status);
            varContect.setContractNo(randonCode);// 合同唯一识别码
            contract.setVarContent(varContect.toString());
            contractMapper.createContract(contract);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "genContract", BSRespCode.BS_CONTRACT_GENERATE_ERROR.getCode()
                    + ":生成合同失败[contract=" + contract + ",varContect=" + varContect + "]", e);
            throw new HsException(BSRespCode.BS_CONTRACT_GENERATE_ERROR, "生成合同失败[contract=" + contract + ",varContect="
                    + varContect + "]" + e);
        }
    }

    /**
     * 生成完整合同内容
     * 
     * @param contractNo
     *            合同编号
     * @return 完整合同内容
     */
    private ContractContent getContractContent(String contractNo, boolean isRealTime) {
        try
        {
            
            ContractContentInfo contractContentInfo = contractMapper.queryContractContent(contractNo);
            if (null == contractContentInfo)
            {
                return null;
            }
            String varContentStr = contractContentInfo.getVarContent();
            ContractContent contractContent = new ContractContent();
            contractContent.setCustType(contractContentInfo.getCustType());
            contractContent.setSealStatus(contractContentInfo.getSealStatus());
            contractContent.setSealFileUrl(bsConfig.getSealFileUrl());
            if (isRealTime)
            {// 从用户中心查实时数据
                //原合同替换内容
                VarContent orgContent =  StringUtils.isNotEmpty(varContentStr)?JSON.parse(varContentStr, VarContent.class):null;
                // 生成合同占位符替换内容
                VarContent varContent = genVarContent(contractContentInfo.getApplyId());
                varContent.setContractNo(contractNo.substring(11));// 合同唯一识别码
                varContentStr = varContent.toString();
                //未盖章状态下，检查是否进行过企业信息修改
                if (orgContent!=null&&(!StringUtils.trimToEmpty(orgContent.getToCorpAddr()).equals(varContent.getToCorpAddr())// 被申报企业地址(乙方)
                        || !StringUtils.trimToEmpty(orgContent.getToCorpContacts()).equals(varContent.getToCorpContacts())// 被申报企业授权代表(乙方)
                        || !StringUtils.trimToEmpty(orgContent.getToCorpContactsTel()).equals(varContent.getToCorpContactsTel())// 被申报企业联系电话(乙方)
                        || !StringUtils.trimToEmpty(orgContent.getToCorpName()).equals(varContent.getToCorpName()))) {// 被申报企业名称(乙方)
                    Contract contract = new Contract();
                    contract.setContractNo(contractNo);
                    contract.setStatus(contractContentInfo.getSealStatus());
                    if (SealStatus.WAIT_TO_SEAL.ordinal() == contractContentInfo.getSealStatus()) {
                        contract.setVarContent(varContentStr);
                        contract.setEntCustName(varContent.getToCorpName());
                        contract.setLinkman(varContent.getToCorpContacts());
                        contract.setMobile(varContent.getToCorpContactsTel());
                        contractMapper.updateContractForChangeInfo(contract);
                    }
                }
            }

            String templetContent = contractContentInfo.getTempletContent();
            VarContent varContentBean = JSON.parse(varContentStr, VarContent.class);
            if(StringUtils.isNotEmpty(varContentBean.getToAnnualFeeStr()) && StringUtils.isEmpty(varContentBean.getToAnnualFeeAmt())){
                varContentBean.setToAnnualFeeAmt(AmountConverter.digitUppercase(varContentBean.getToAnnualFeeStr()));
            }
            if(StringUtils.isNotEmpty(varContentBean.getToPointAdvanceStr()) && StringUtils.isEmpty(varContentBean.getToPointAdvanceAmt())){
                varContentBean.setToPointAdvanceAmt(AmountConverter.digitUppercase(varContentBean.getToPointAdvanceStr()));
            }
            if(StringUtils.isNotEmpty(varContentBean.getToResFeeStr()) && StringUtils.isEmpty(varContentBean.getToResFeeAmt())){
                varContentBean.setToResFeeAmt(AmountConverter.digitUppercase(varContentBean.getToResFeeStr()));
            }
            Class<VarContent> clazz = VarContent.class;
            String value;
            for (TempletTag tag : TempletTag.values())
            {
                value = (String) clazz.getDeclaredMethod(tag.toString()).invoke(varContentBean);
                if(value==null){
                    value = "";
                }
                templetContent = templetContent.replaceAll("\\$\\{" + tag.toString() + "\\}", value);
            }
            contractContent.setContent(templetContent);
            return contractContent;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "getContractContent", BSRespCode.BS_CONTRACT_GENERATE_ERROR
                    .getCode()
                    + ":生成合同失败[contractNo=" + contractNo + ",isRealTime=" + isRealTime + "]", e);
            throw new HsException(BSRespCode.BS_CONTRACT_GENERATE_ERROR, "生成合同失败[contractNo=" + contractNo
                    + ",isRealTime=" + isRealTime + "]" + e);
        }
    }

    /**
     * 查询合同主要信息
     * 
     * @param contractNo
     *            合同唯一识别码
     * @param entResNo
     *            合同编号
     * @return 合同主要信息
     */
    @Override
    public ContractMainInfo queryContractMainInfo(String contractNo, String entResNo) {
        HsAssert.hasText(contractNo, BSRespCode.BS_PARAMS_EMPTY, "合同编号[contractNo]为空");
        HsAssert.hasText(entResNo, BSRespCode.BS_PARAMS_EMPTY, "企业互生号[entResNo]为空");
        if (contractNo.contains(entResNo))
        {
            return contractMapper.queryContractMainInfo(contractNo, entResNo);
        }
        else
        {
            return contractMapper.queryContractMainInfo(entResNo + contractNo, entResNo);
        }
    }

    /**
     * 查询合同内容
     * 
     * @param contractNo
     *            合同唯一识别码
     * @param entResNo
     *            合同编号
     * @return 合同内容
     */
    @Override
    public ContractContent queryContractContent(String contractNo, String entResNo) {
        return this.getContractContent(entResNo + contractNo, false);
    }

    /**
     * 生成合同占位符替换内容
     * 
     * @param applyId
     *            申报申请编号
     * @return 合同占位符替换内容
     */
    private VarContent genVarContent(String applyId) {

        // 申报信息
        DeclareInfo declareInfo = declareService.queryDeclareById(applyId);
        // 被申报者信息
        BsEntMainInfo entInfo = bsEntService.getMainInfoByResNo(declareInfo.getToEntResNo());
        // 推广企业
        BsEntMainInfo frCorInfo = bsEntService.getMainInfoByResNo(declareInfo.getSpreadEntResNo());
        // 被申报者管理公司
//        BsEntMainInfo mdCorpInfo = bsEntService.getMainInfoByResNo(declareInfo.getToEntResNo().substring(0, 2)+ "000000000");

        LocalInfo localInfo = commonService.getAreaPlatInfo();
        // 合同占位符内容
        VarContent varContent = new VarContent();
        varContent.setHsCorpAddr(localInfo.getTechSupportAddress());// 互生系统技术支持方地址(甲方)
        varContent.setHsCorpContactsTel(localInfo.getTechSupportPhone());// 互生系统技术支持方联系电话(甲方)
        varContent.setHsCorpContacts(localInfo.getTechSupportContact());// 互生系统技术支持方授权代表(甲方)
        varContent.setHsCorpName(localInfo.getTechSupportCorpName());// 互生系统技术支持方名称(甲方)

        varContent.setToCorpAddr(entInfo.getEntRegAddr());// 被申报企业地址(乙方)
        varContent.setToCorpContactsTel(entInfo.getContactPhone());// 被申报企业联系电话(乙方)
        varContent.setToCorpContacts(entInfo.getContactPerson());// 被申报企业授权代表(乙方)
        varContent.setToCorpName(entInfo.getEntName());// 被申报企业名称(乙方)

        //丙方：3、凡是合同里面涉及到服务公司的盖章的地方，其电话、授权代表、等两项内容为空，不填写。
        //4、凡是合同里面涉及到管理公司盖章的地方，其相应地方的所有资料（丙方、地址、电话、授权代表、签约日期）都为空，不填写。
        varContent.setFrCorpName(HsResNoUtils.isServiceResNo(declareInfo.getToCustType())?StringUtils.EMPTY:frCorInfo.getEntName());// 申报企业名称(丙方)
        varContent.setFrCorpAddr(HsResNoUtils.isServiceResNo(declareInfo.getToCustType())?StringUtils.EMPTY:frCorInfo.getEntRegAddr());// 申报企业地址(丙方)
//        varContent.setFrCorpContactsTel(frCorInfo.getContactPhone());// 申报企业联系电话(丙方)
//        varContent.setFrCorpContacts(frCorInfo.getCreName());// 申报企业授权代表(丙方)

/*        varContent.setMdCorpAddr(mdCorpInfo.getEntRegAddr());// 管理公司地址
        varContent.setMdCorpContactsTel(mdCorpInfo.getContactPhone());// 管理公司联系电话
        varContent.setMdCorpContacts(mdCorpInfo.getCreName());// 管理公司授权代表
        varContent.setMdCorpName(mdCorpInfo.getEntName());// 管理公司授名称*/

        ResFee resFee = resFeeService.getResFeeBean(declareInfo.getResFeeId());
        String price = annualFeePriceService.queryPriceForEnabled(declareInfo.getToCustType());
        varContent.setToAnnualFeeStr(price);// 年费
        varContent.setToAnnualFeeAmt(AmountConverter.digitUppercase(price)); //年费大写金额
        varContent.setToResFeeStr(resFee.getPrice());// 资源费用
        varContent.setToResFeeAmt(AmountConverter.digitUppercase(resFee.getPrice())); //资源费大写金额
        varContent.setToPointAdvanceStr(resFee.getIncludePrepayAmount());// 积分预付款
        varContent.setToPointAdvanceAmt(AmountConverter.digitUppercase(resFee.getIncludePrepayAmount())); //积分预付款大写金额
        varContent.setToCorpResNo(declareInfo.getToEntResNo());// 合同编号
        varContent.setSigningDate(StringUtils.left(declareInfo.getOpenAccDate(),10));// 签约日期
        varContent.setVerifyAddr(localInfo.getContractVerifyAddr());// 验证地址

        return varContent;
    }
    
    /**
     * 生成合同占位符替换内容
     * @param declareInfo 申报信息
     * @param declareLinkman 企业联系人信息
     * @return 占位符替换内容对象
     */
    @Override
    public VarContent buildVarContent(DeclareInfo declareInfo,DeclareLinkman declareLinkman) {

        LocalInfo localInfo = commonService.getAreaPlatInfo();
        // 推广企业信息
        BsEntMainInfo frCorInfo = bsEntService.getMainInfoByResNo(declareInfo.getSpreadEntResNo());

        // 合同占位符内容
        VarContent varContent = new VarContent();
        varContent.setHsCorpAddr(localInfo.getTechSupportAddress());// 互生系统技术支持方地址(甲方)
        varContent.setHsCorpContactsTel(localInfo.getTechSupportPhone());// 互生系统技术支持方联系电话(甲方)
        varContent.setHsCorpContacts(localInfo.getTechSupportContact());// 互生系统技术支持方授权代表(甲方)
        varContent.setHsCorpName(localInfo.getTechSupportCorpName());// 互生系统技术支持方名称(甲方)

        varContent.setToCorpAddr(declareInfo.getEntAddr());// 被申报企业地址(乙方)
        varContent.setToCorpContactsTel(declareLinkman.getMobile());// 被申报企业联系电话(乙方)
        varContent.setToCorpContacts(declareLinkman.getLinkman());// 被申报企业授权代表(乙方)
        varContent.setToCorpName(declareInfo.getToEntCustName());// 被申报企业名称(乙方)

        //丙方：3、凡是合同里面涉及到服务公司的盖章的地方，其电话、授权代表、等两项内容为空，不填写。
        //4、凡是合同里面涉及到管理公司盖章的地方，其相应地方的所有资料（丙方、地址、电话、授权代表、签约日期）都为空，不填写。
        varContent.setFrCorpName(HsResNoUtils.isServiceResNo(declareInfo.getToCustType())?StringUtils.EMPTY:frCorInfo.getEntName());// 申报企业名称(丙方)
        varContent.setFrCorpAddr(HsResNoUtils.isServiceResNo(declareInfo.getToCustType())?StringUtils.EMPTY:frCorInfo.getEntRegAddr());// 申报企业地址(丙方)
//        varContent.setFrCorpContactsTel(frCorInfo.getContactPhone());// 申报企业联系电话(丙方)
//        varContent.setFrCorpContacts(frCorInfo.getCreName());// 申报企业授权代表(丙方)

        /*varContent.setMdCorpAddr(mdCorpInfo.getEntRegAddr());// 管理公司地址
        varContent.setMdCorpContactsTel(mdCorpInfo.getContactPhone());// 管理公司联系电话
        varContent.setMdCorpContacts(mdCorpInfo.getCreName());// 管理公司授权代表
        varContent.setMdCorpName(mdCorpInfo.getEntName());// 管理公司授名称*/

        ResFee resFee = resFeeService.getResFeeBean(declareInfo.getResFeeId());
        String price = annualFeePriceService.queryPriceForEnabled(declareInfo.getToCustType());
        varContent.setToAnnualFeeStr(price);// 年费
        varContent.setToAnnualFeeAmt(AmountConverter.digitUppercase(price)); //年费大写金额
        varContent.setToResFeeStr(resFee.getPrice());// 资源费用
        varContent.setToResFeeAmt(AmountConverter.digitUppercase(resFee.getPrice())); //资源费大写金额
        varContent.setToPointAdvanceStr(resFee.getIncludePrepayAmount());// 积分预付款
        varContent.setToPointAdvanceAmt(AmountConverter.digitUppercase(resFee.getIncludePrepayAmount())); //积分预付款大写金额
        varContent.setToCorpResNo(declareInfo.getToEntResNo());// 合同编号
        varContent.setSigningDate(StringUtils.left(declareInfo.getOpenAccDate(),10));// 签约日期
        varContent.setVerifyAddr(localInfo.getContractVerifyAddr());// 验证地址

        return varContent;
    }


    /**
     * 重要信息变更后修改合同
     *
     * @param entChangeInfo 重要信息
     * @throws HsException
     */
    @Override
    public void updateContractForChangeInfo(EntChangeInfo entChangeInfo) throws HsException {

        Contract contractInDB = contractMapper.queryEffectContractByCustId(entChangeInfo.getEntCustId());
        Contract contract = new Contract();
        contract.setContractNo(contractInDB.getContractNo());
        contract.setStatus(contractInDB.getStatus());
        if (SealStatus.WAIT_TO_SEAL.ordinal() == contractInDB.getStatus()) {//未盖章
            String randonCode = StringUtils.substring(contractInDB.getContractNo(),11);// 10位随机码
            VarContent varContent = this.genVarContent(contractInDB.getApplyId());
            varContent.setContractNo(randonCode);// 合同唯一识别码
            if (StringUtils.isNotEmpty(entChangeInfo.getCustAddressNew())) {
                varContent.setToCorpAddr(entChangeInfo.getCustAddressNew());// 被申报企业地址(乙方)
            }
            if (StringUtils.isNotEmpty(entChangeInfo.getLinkmanMobileNew())) {
                varContent.setToCorpContactsTel(entChangeInfo.getLinkmanMobileNew());// 被申报企业联系电话(乙方)
                contract.setMobile(entChangeInfo.getLinkmanMobileNew());
            }
            if (StringUtils.isNotEmpty(entChangeInfo.getLinkmanNew())) {
                varContent.setToCorpContacts(entChangeInfo.getLinkmanNew());// 被申报企业授权代表(乙方)
                contract.setLinkman(entChangeInfo.getLinkmanNew());
            }
            if (StringUtils.isNotEmpty(entChangeInfo.getCustNameNew())) {
                varContent.setToCorpName(entChangeInfo.getCustNameNew());// 被申报企业名称(乙方)
                contract.setEntCustName(entChangeInfo.getCustNameNew());
            }
            contract.setVarContent(varContent.toString());
            contractMapper.updateContractForChangeInfo(contract);
        }
//        if (SealStatus.TAKE_EFFECT.ordinal() == contractInDB.getStatus()) {
//            contract.setStatus(SealStatus.WAIT_TO_RESEAL.ordinal());
//        }
    }
}
