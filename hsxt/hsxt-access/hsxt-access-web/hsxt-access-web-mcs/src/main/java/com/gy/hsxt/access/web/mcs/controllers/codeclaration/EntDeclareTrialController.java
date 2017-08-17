package com.gy.hsxt.access.web.mcs.controllers.codeclaration;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.codeclaration.*;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.common.enumtype.apply.AptitudeType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.codeclaration
 * @className     : EntDeclareTrialController.java
 * @description   : 初审业务
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareTrialController")
public class EntDeclareTrialController extends BaseController {
	
	@Resource
    private IEntDeclareTrialService entDeclareTrialService;//初审服务类
	
	@Resource
    private IBankService bankService;//企业申报-银行服务类

    @Resource
    private ILinkService linkService;//企业申报-联系人服务类
    
    @Resource
    private IBizRegInfoService bizRegInfoService;//企业申报-工商登记服务类
    
    @Resource
    private IRegInfoService regInfoService;//企业申报-系统注册信息服务类
    
    @Resource
    private IDeclareAptitudeService declareAptitudeService;//企业申报-系统注册信息服务类
    
    @Resource
    private RedisUtil<String> changeRedisUtil;
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request,Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			RequestUtil.verifyQueryDate((String) filterMap.get("startDate"), (String) filterMap.get("endDate"));//校验时间
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
     * 
     * 方法名称：保存银行信息
     * 方法描述：保存银行信息信息
     * @param request HttpServletRequest对象
     * @param bank 银行信息对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveBankInfo")
    public HttpRespEnvelope saveBankInfo(HttpServletRequest request, @ModelAttribute DeclareBank bank) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String accountId = request.getParameter("accountId");//银行账号ID
            String dblOptCustId = request.getParameter("dblOptCustId");//复核员用户名
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //账户非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bank, ASRespCode.MW_BANKINFO_INVALID},
                new Object[] {bank.getAccountName(), ASRespCode.MW_BANKNAME_INVALID},
                new Object[] {bank.getCurrencyCode(), ASRespCode.MW_CUR_CODE_INVALID},
                new Object[] {bank.getBankCode(), ASRespCode.MW_BANKINFO_BANKCODE_INVALID},
                new Object[] {bank.getProvinceNo(), ASRespCode.MW_BANKINFO_PROVINCENO_INVALID},
                new Object[] {bank.getCityNo(), ASRespCode.MW_BANKINFO_CITYNO_INVALID},
                new Object[] {bank.getAccountNo(), ASRespCode.MW_BANK_NO_INVALID}
            );
            //复核非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {dblOptCustId, RespCode.MW_DOULBE_USERID_INVALID},
                new Object[] {bank, RespCode.MW_BANKINFO_INVALID},
                new Object[] {bank.getApplyId(), RespCode.MW_APPLYID_INVALID}
            );
            //校验复核备注长度
            if(!StringUtils.isBlank(bank.getOptRemark())){
                RequestUtil.verifyParamsLength(
                    new Object[] {bank.getOptRemark(), 0, 300, RespCode.MW_VIEW_MARK_LENGTH_INVALID}
                );
            }
            bank.setOptCustId(custId);
            bank.setOptName(custName);
            bank.setOptEntName(optEntName);
            accountId = this.bankService.manageSaveBank(bank);
            return new HttpRespEnvelope(accountId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：修改联系人信息
     * 方法描述：修改联系人信息
     * @param request HttpServletRequest对象
     * @param link 企业联系信息对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveLinkInfo")
    public HttpRespEnvelope saveLinkInfo(HttpServletRequest request, @ModelAttribute DeclareLinkman link) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String dblOptCustId = request.getParameter("dblOptCustId");//复核员用户名
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            String hlepFileId = request.getParameter("hlepFileId");//创业帮扶协议书
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {dblOptCustId, RespCode.MW_DOULBE_USERID_INVALID},
                new Object[] {link, RespCode.MW_ENT_LINKINFO_OBJ_INVALID},
                new Object[] {link.getApplyId(), RespCode.MW_APPLYID_INVALID},
                new Object[] {link.getLinkman(), RespCode.MW_ENT_LINKINFO_LINKMAN_INVALID},
                new Object[] {link.getMobile(), RespCode.MW_ENT_LINKINFO_MOBILE_INVALID},
                new Object[] {link.getCertificateFile(), RespCode.MW_ENT_LINKINFO_CERT_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {link.getLinkman(), 2, 20, RespCode.MW_ENT_LINKINFO_LINKMAN_LENGTH_INVALID}
            );
            //长度验证
            if(!StringUtils.isBlank(link.getAddress())){
                RequestUtil.verifyParamsLength(
                    new Object[] {link.getAddress(), 2, 128, RespCode.MW_ENT_LINKINFO_ADDRESS_LENGTH_INVALID}
                );
            }
            //校验复核备注长度
            if(!StringUtils.isBlank(link.getOptRemark())){
                RequestUtil.verifyParamsLength(
                    new Object[] {link.getOptRemark(), 0, 300, RespCode.MW_VIEW_MARK_LENGTH_INVALID}
                );
            }
            if(isNotBlank(hlepFileId)){
				DeclareAptitude protocolAptitude = new  DeclareAptitude();
				protocolAptitude.setFileId(hlepFileId);
				protocolAptitude.setAptitudeType(AptitudeType.VENTURE_BEFRIEND_PROTOCOL.getCode());//创业帮扶协议书
				link.setProtocolAptitude(protocolAptitude);
			}
            link.setOptCustId(custId);
            link.setOptName(custName);
            link.setOptEntName(optEntName);
            this.linkService.updateLinkInfo(link);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：修改工商登记信息
     * 方法描述：修改工商登记信息
     * @param request HttpServletRequest对象
     * @param bizInfo 工商登记信息对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveDeclareEnt")
    public HttpRespEnvelope saveDeclareEnt(HttpServletRequest request, @ModelAttribute DeclareBizRegInfo bizInfo) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String dblOptCustId = request.getParameter("dblOptCustId");//复核员用户名
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            String licenseFile = request.getParameter("licenseFile");//获取印业执照扫描件
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {dblOptCustId, RespCode.MW_DOULBE_USERID_INVALID},
                new Object[] {bizInfo, RespCode.MW_BIZREG_INVALID},
                new Object[] {bizInfo.getEntName(), RespCode.MW_ENT_FILING_ENTCUSTNAME_INVALID},
                //new Object[] {bizInfo.getEntAddress(), RespCode.MW_ENT_FILING_ENT_ADDRESS_INVALID},
                new Object[] {bizInfo.getLegalName(), RespCode.MW_ENT_FILING_LEGALNAME_INVALID},
                new Object[] {bizInfo.getLicenseNo(), RespCode.MW_ENT_FILING_LICENSENO_INVALID}
                //new Object[] {bizInfo.getOrgNo(), RespCode.MW_ORGNO_INVALID},
                //new Object[] {bizInfo.getTaxNo(), RespCode.MW_TAXNO_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bizInfo.getEntName(), 2, 128, RespCode.MW_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID},
                new Object[] {bizInfo.getEntAddress(), 2, 128, RespCode.MW_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID},
                new Object[] {bizInfo.getLegalName(), 2, 20, RespCode.MW_ENT_FILING_LEGALNAME_LENGTH_INVALID},
                new Object[] {bizInfo.getEntType(), 0, 20, RespCode.MW_BIZREG_ENTTYPE_LENGTH_INVALID},
                new Object[] {bizInfo.getLimitDate(), 0, 50, RespCode.MW_BIZREG_LIMITDATE_LENGTH_INVALID},
                new Object[] {bizInfo.getDealArea(), 0, 300, RespCode.MW_ENT_FILING_DEALAREA_LENGTH_INVALID}
            );
            //校验复核备注长度
            if(!StringUtils.isBlank(bizInfo.getOptRemark())){
                RequestUtil.verifyParamsLength(
                    new Object[] {bizInfo.getOptRemark(), 0, 300, RespCode.MW_VIEW_MARK_LENGTH_INVALID}
                );
            }
            if(isNotBlank(licenseFile)){
				DeclareAptitude licenseAptitude = new  DeclareAptitude();
				licenseAptitude.setFileId(licenseFile);
				licenseAptitude.setAptitudeType(AptitudeType.BIZ_LICENSE_CRE.getCode());//营业执照扫描件
				bizInfo.setLicenseAptitude(licenseAptitude);
			}
            bizInfo.setOptCustId(custId);
            bizInfo.setOptName(custName);
            bizInfo.setOptEntName(optEntName);
            this.bizRegInfoService.updateDeclareEnt(bizInfo);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：修改系统注册信息
     * 方法描述：修改系统注册信息
     * @param request HttpServletRequest对象
     * @param regInfo 系统注册信息对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveDeclare")
    public HttpRespEnvelope saveDeclare(HttpServletRequest request, @ModelAttribute DeclareRegInfo regInfo) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String applyId = request.getParameter("applyId");//申请编号
            String dblOptCustId = request.getParameter("dblOptCustId");//复核员用户名
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {dblOptCustId, RespCode.MW_DOULBE_USERID_INVALID},
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID},
                new Object[] {regInfo, RespCode.MW_BIZREG_INVALID},
                new Object[] {regInfo.getToEntCustName(), RespCode.MW_REGINFO_ENTNAME_INVALID},
                new Object[] {regInfo.getCountryNo(), RespCode.MW_REGINFO_COUNTRYNO_INVALID},
                new Object[] {regInfo.getProvinceNo(), RespCode.MW_REGINFO_PROVINCENO_INVALID},
                new Object[] {regInfo.getCityNo(), RespCode.MW_REGINFO_CITY_INVALID},
                new Object[] {regInfo.getToCustType(), RespCode.MW_REGINFO_TOCUSTTYPE_INVALID},
                new Object[] {regInfo.getToMResNo(), RespCode.MW_REGINFO_TOMRESNO_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {regInfo.getToEntCustName(), 2, 128, RespCode.MW_REGINFO_ENTNAME_LENGTH_INVALID}
            );
            //验证英文名称
            if(!StringUtils.isBlank(regInfo.getToEntEnName())){
                RequestUtil.verifyParamsLength(
                    new Object[] {regInfo.getToEntEnName(), 2, 128, RespCode.MW_REGINFO_ENTENNAME_LENGTH_INVALID}
                );
            }
            //成员企业校验
            if(regInfo.getToCustType() == 2){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {regInfo.getToEntResNo(), RespCode.MW_REGINFO_TOENTRESNO_INVALID},
                    new Object[] {regInfo.getToBuyResRange(), RespCode.MW_REGINFO_TOBUY_RESRANGE_INVALID}
                );
            }
            //托管企业校验
            if(regInfo.getToCustType() == 3){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {regInfo.getToEntResNo(), RespCode.MW_REGINFO_TOENTRESNO_INVALID},
                    new Object[] {regInfo.getToBuyResRange(), RespCode.MW_REGINFO_TOBUY_RESRANGE_INVALID}
                );
            }
            //校验复核备注长度
            if(!StringUtils.isBlank(regInfo.getOptRemark())){
                RequestUtil.verifyParamsLength(
                    new Object[] {regInfo.getOptRemark(), 0, 300, RespCode.MW_VIEW_MARK_LENGTH_INVALID}
                );
            }
            regInfo.setOptCustId(custId);
            regInfo.setOptName(custName);
            regInfo.setOptEntName(optEntName);
            this.regInfoService.updateDeclare(regInfo);
            return new HttpRespEnvelope(applyId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存企业资料上传
     * 方法描述：企业申报新增-保存企业资料上传
     * @param request HttpServletRequest对象
     * @param info 操作员对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveDeclareAptitude")
    public HttpRespEnvelope saveDeclareAptitude(HttpServletRequest request, @ModelAttribute OptInfo info) {
        try {
            super.verifySecureToken(request);
            String dblOptCustId = request.getParameter("dblOptCustId");//复核员用户名
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            String custId = request.getParameter("custId");//获取登陆客户号
            String applyId = request.getParameter("applyId");//报备企业申请编号
            String venBefFlag = request.getParameter("venBefFlag");//是否需要填写创业帮扶协议
            String custName = request.getParameter("custName");//操作员名字
            String cookieOperNoName = request.getParameter("cookieOperNoName");//操作员名字
            String lrCredentialFrontFileId = request.getParameter("lrCredentialFrontFileId");//法人代表证件正面文件ID
            String lrCredentialBackFileId = request.getParameter("lrCredentialBackFileId");//法人代表证件反面文件ID
            String busLicenceFileId = request.getParameter("busLicenceFileId");//营业执照正本扫描件文件ID
            String organizationFileId = request.getParameter("organizationFileId");//组织机构代码证正本扫描件文件ID
            String taxplayerFileId = request.getParameter("taxplayerFileId");//税务登记证正本扫描件文件ID
            String venBefProtocolFileId = request.getParameter("venBefProtocolFileId");//创业帮扶协议件文件ID
            String lrCredentialFrontAptitudeId = request.getParameter("lrCredentialFrontAptitudeId");//法人代表证件正面资质附件ID
            String lrCredentialBackAptitudeId = request.getParameter("lrCredentialBackAptitudeId");//法人代表证件反面资质附件ID
            String busLicenceAptitudeId = request.getParameter("busLicenceAptitudeId");//营业执照正本扫描件资质附件ID
            String organizPicAptitudeId = request.getParameter("organizPicAptitudeId");//组织机构代码证正本扫描件资质附件ID
            String taxRegPicAptitudeId = request.getParameter("taxRegPicAptitudeId");//税务登记证正本扫描件资质附件ID
            String venBefProtocolAptitudeId = request.getParameter("venBefProtocolAptitudeId");//创业帮扶协议件附件ID
            String aptRemark = request.getParameter("aptRemark");// 备注说明
            String creType = request.getParameter("creType");//证件类型
            if (StringUtils.isEmpty(creType) || creType.equals("1")) {
                HsAssert.hasText(lrCredentialBackFileId,RespCode.MW_ENT_FILING_BACK_PIC_FILEID_INVALID);
            }
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {dblOptCustId, RespCode.MW_DOULBE_USERID_INVALID},
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID},
                new Object[] {lrCredentialFrontFileId, RespCode.MW_ENT_FILING_POSI_PIC_FILEID_INVALID},
//                new Object[] {lrCredentialBackFileId, RespCode.MW_ENT_FILING_BACK_PIC_FILEID_INVALID},
                new Object[] {busLicenceFileId, RespCode.MW_ENT_FILING_LICE_PIC_FILEID_INVALID},
                new Object[] {organizationFileId, RespCode.MW_DECLARE_ORG_PIC_FILEID_INVALID},
                new Object[] {taxplayerFileId, RespCode.MW_DECLARE_TAX_PIC_FILEID_INVALID}
            );
            //托管企业创业帮扶协议必填
            if("true".equals(venBefFlag)){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {venBefProtocolFileId, RespCode.MW_DECLARE_PRO_PIC_FILEID_INVALID}
                );
            }
            //校验复核备注长度
            if(!StringUtils.isBlank(info.getOptRemark())){
                RequestUtil.verifyParamsLength(
                    new Object[] {info.getOptRemark(), 0, 300, RespCode.MW_VIEW_MARK_LENGTH_INVALID}
                );
            }
            //校验备注说长度
			RequestUtil.verifyParamsLength(new Object[] {
				aptRemark, 0, 300, ASRespCode.AS_VIEW_MARK_LENGTH_INVALID
			});
            
            //组织数据
            List<DeclareAptitude> aptList = new ArrayList<>();
            aptList.add(this.buildDeclareAptitude(lrCredentialFrontAptitudeId, applyId, 1, lrCredentialFrontFileId, custId, custName));
            if (StringUtils.isEmpty(creType) || creType.equals("1")) {
                aptList.add(this.buildDeclareAptitude(lrCredentialBackAptitudeId, applyId, 2, lrCredentialBackFileId, custId, custName));
            }
            aptList.add(this.buildDeclareAptitude(busLicenceAptitudeId, applyId, 3, busLicenceFileId, custId, custName));
            aptList.add(this.buildDeclareAptitude(organizPicAptitudeId, applyId, 6, organizationFileId, custId, custName));
            aptList.add(this.buildDeclareAptitude(taxRegPicAptitudeId, applyId, 7, taxplayerFileId, custId, custName));
            //托管企业创业帮扶协议必填
            if("true".equals(venBefFlag)){
                aptList.add(this.buildDeclareAptitude(venBefProtocolAptitudeId, applyId, 8, venBefProtocolFileId, custId, custName));
            }
            info.setOptCustId(custId);
            info.setOptName(cookieOperNoName);
            info.setOptEntName(optEntName);
            //保存数据
            List<DeclareAptitude> temp = this.declareAptitudeService.saveAptitude(aptList, info, aptRemark);
            return new HttpRespEnvelope(temp);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
	/**
     * 
     * 方法名称：管理公司初审
     * 方法描述：管理公司初审
     * @param request HttpServletRequest对象
     * @param apprParam 审批内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/managerApprDeclare")
    public HttpRespEnvelope managerApprDeclare(HttpServletRequest request, @ModelAttribute ApprParam apprParam) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID},
                new Object[] {apprParam.getIsPass(), RespCode.MW_ENT_REVIEW_ISPASS_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {apprParam.getApprRemark(), 0, 300, RespCode.MW_ENT_REVIEW_LENGTH_INVALID}
            );
            apprParam.setOptCustId(custId);
            apprParam.setOptEntName(optEntName);
            apprParam.setOptName(custName);
            this.entDeclareTrialService.managerApprDeclare(apprParam);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：构建附件对象
     * 方法描述：构建附件对象
     * @param aptitudeId 资质附件ID
     * @param applyId 报备企业申请编号
     * @param aptType 附件类型
     * @param fileId 文件地址
     * @param custId 操作员客户号
     * @param custName 操作员名字
     */
    public DeclareAptitude buildDeclareAptitude(String aptitudeId, String applyId, Integer aptType, String fileId, String custId, String custName){
        DeclareAptitude obj = new DeclareAptitude();
        obj.setAptitudeId(aptitudeId);
        obj.setApplyId(applyId);
        obj.setAptitudeType(aptType);
        obj.setFileId(fileId);
        obj.setOptName(custName);
        obj.setOptCustId(custId);
        return obj;
    }

	@Override
	protected IBaseService getEntityService() {
		return entDeclareTrialService;
	}

}