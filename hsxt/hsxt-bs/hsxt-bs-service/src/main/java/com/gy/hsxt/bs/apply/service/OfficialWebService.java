/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.List;

import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.tool.mapper.CardProvideApplyMapper;
import com.gy.hsxt.common.constant.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.apply.IBSCertificateService;
import com.gy.hsxt.bs.api.apply.IBSOfficialWebService;
import com.gy.hsxt.bs.apply.interfaces.IContractService;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.apply.mapper.IntentCustMapper;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.tool.CardProvideApply;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.apply.CreType;
import com.gy.hsxt.bs.common.enumtype.apply.IntentCustStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: OfficialWebService
 * @Description: 官网服务
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:15:57
 * @version V1.0
 */
@Service
public class OfficialWebService implements IBSOfficialWebService {

	/** 业务服务私有配置参数 **/
	@Autowired
	private BsConfig bsConfig;

	@Autowired
	private IntentCustMapper intentCustMapper;

	@Autowired
	private IDeclareService declareService;

	@Autowired
	private IContractService contractService;

	@Autowired
	ICommonService commonService;

	@Autowired
	IBSCertificateService certificateService;

    /**
     * 互生卡发放Mapper
     */
    @Autowired
    private CardProvideApplyMapper cardProvideApplyMapper;

	/**
	 * 添加意向客户
	 * 
	 * @param intentCust
	 *            意向客户详情 必填
	 * @throws HsException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createIntentCust(IntentCust intentCust) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "createIntentCust", "input param:", intentCust + "");
		HsAssert.notNull(intentCust, BSRespCode.BS_PARAMS_NULL, "意向客户[intentCust]为null");
		HsAssert.isTrue(StringUtils.isNoneBlank(intentCust.getSerEntCustId(), intentCust.getSerEntResNo(),
				intentCust.getSerEntCustName(), intentCust.getCountryNo(), intentCust.getProvinceNo(),
				intentCust.getCityNo()), BSRespCode.BS_PARAMS_EMPTY, "参数不能为空");

		HsAssert.hasText(intentCust.getEntCustName(), BSRespCode.BS_PARAMS_EMPTY, "申请企业名称(意向客户名称)[entCustName]为空");
		HsAssert.hasText(intentCust.getLicenseNo(), BSRespCode.BS_PARAMS_EMPTY, "企业执照号码[licenseNo]为空");
		HsAssert.isTrue(intentCust.getAppType() == 2 || intentCust.getAppType() == 3, BSRespCode.BS_PARAMS_TYPE_ERROR,
				"申请类别[appType]:2.成员企业 3.托管企业");

		try
		{
			int count = intentCustMapper.queryUniqueIntentCust(intentCust);
			HsAssert.isTrue(count == 0, BSRespCode.BS_INTENT_CUST_EXIST_SAME, "存在相同意向客户申请");
			String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			intentCust.setApplyId(applyId);
			intentCust.setAcceptNo(applyId);
			intentCust.setStatus(IntentCustStatus.WAIT_TO_HANDLE.getCode());
			// 插入数据到意向客户表
			intentCustMapper.createIntentCust(intentCust);
		} catch (Exception e)
		{
			SystemLog.error(this.getClass().getName(), "createIntentCust",
					BSRespCode.BS_INTENT_CUST_SUBMIT_ERROR.getCode() + ":提交意向客户失败", e);
			throw new HsException(BSRespCode.BS_INTENT_CUST_SUBMIT_ERROR, "提交意向客户失败");
		}
	}

	/**
	 * 查询公告期企业
	 * 
	 * @return 公告期企业列表
	 */
	@Override
	public List<EntBaseInfo> queryPlacardEnt()
	{
		return declareService.queryPlacardEnt();
	}

	/**
	 * 查询企业申报信息
	 * 
	 * @return 企业申报信息
	 */
	@Override
	public List<EntBaseInfo> queryEntInfo(String entName) throws HsException
	{
		if (isBlank(entName))
		{
			throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
		}
		return declareService.queryEntInfo(entName);
	}

	/**
	 * 通过授权码查询申报企业信息
	 * 
	 * @param authCode
	 *            授权码
	 * @return 申报企业信息
	 */
	@Override
	public EntInfo queryEntInfoByAuthCode(String authCode) throws HsException
	{
		if (isBlank(authCode))
		{
			throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
		}
		return declareService.queryEntInfoByAuthCode(authCode);
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
	public ContractContent queryContractContent(String contractNo, String entResNo) throws HsException
	{
		if (isBlank(contractNo) || isBlank(entResNo))
		{
			throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
		}
		return contractService.queryContractContent(contractNo, entResNo);
	}

	/**
	 * 查询销售资格证书
	 * 
	 * @param certificateNo
	 *            唯一识别码
	 * @param entResNo
	 *            证书编号
	 * @return 销售资格证书
	 * @throws HsException
	 */
	@Override
	public CertificateContent querySaleCre(String certificateNo, String entResNo) throws HsException
	{
		if (isBlank(certificateNo) || isBlank(entResNo))
		{
			throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
		}
		CertificateContent certificateContent = certificateService.queryCreContentById(entResNo + certificateNo, false);
		if (certificateContent != null && CreType.SALES_CRE.getCode() == certificateContent.getCertificateType())
		{
			return certificateContent;
		}
		return null;
	}

	/**
	 * 查询第三方业务证书
	 * 
	 * @param certificateNo
	 *            唯一识别码
	 * @param entResNo
	 *            证书编号
	 * @return 第三方业务证书
	 * @throws HsException
	 */
	@Override
	public CertificateContent queryBizCre(String certificateNo, String entResNo) throws HsException
	{
		if (isBlank(certificateNo) || isBlank(entResNo))
		{
			throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
		}
		CertificateContent certificateContent = certificateService.queryCreContentById(entResNo + certificateNo, false);
		if (certificateContent != null && CreType.THIRD_PARTY_CRE.getCode() == certificateContent.getCertificateType())
		{
			return certificateContent;
		}
		return null;
	}

	/**
	 * 添加互生卡发放申请
	 * 
	 * @param bean
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addCardProvideApply(CardProvideApply bean) throws HsException
	{
        BizLog.biz(this.getClass().getName(), "function:addCardProvideApply", "params==>" + bean, "互生卡发放申请");
        HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增互生卡发放申请参数为NULL," + bean);
        try
        {
            bean.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            // 插入互生卡发放申请
            cardProvideApplyMapper.insertCardProvideApply(bean);
        } catch (Exception ex)
        {
            SystemLog.error(this.getClass().getName(), "function:addCardProvideApply",
                    BSRespCode.BS_ADD_CARD_PROVIDE_APPLY_FAIL.getCode() + ":新增互生卡发放申请失败," + bean, ex);
            throw new HsException(BSRespCode.BS_ADD_CARD_PROVIDE_APPLY_FAIL, "新增互生卡发放申请失败," + bean);
        }
	}

	/**
	 * 分页查询互生卡发放申请
	 * 
	 * @param: receiver
	 *             收件人
	 * @param: mobile
	 *             收件人手机
	 * @param: page
	 *             分页参数
	 * @return PageData<CardProvideApply>
	 */
	@Override
	public PageData<CardProvideApply> queryCardProvideApplyByPage(String receiver, String mobile, Page page)
	{
        if (null == page)
        {
            return new PageData<CardProvideApply>(0, null);
        }
        // 设置分页参数
        PageContext.setPage(page);
        // 查询互生卡发放申请列表
        List<CardProvideApply> apps = cardProvideApplyMapper.selectCardProvideApplyListPage(receiver, mobile);
        if (com.gy.hsxt.common.utils.StringUtils.isNotBlank(apps))
        {
            return new PageData<CardProvideApply>(page.getCount(), apps);
        }
        return new PageData<CardProvideApply>(0, null);
	}
}
