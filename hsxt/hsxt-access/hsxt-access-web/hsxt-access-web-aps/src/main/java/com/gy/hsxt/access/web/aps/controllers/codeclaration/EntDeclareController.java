package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import com.gy.hsxt.access.web.aps.services.codeclaration.*;
import com.gy.hsxt.access.web.bean.codeclaration.AptitudeBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.bean.bm.Increment;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : EntDeclareController.java
 * @description   : 企业申报
 * @author        : maocy
 * @createDate    : 2015-12-18
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareController")
public class EntDeclareController extends BaseController {
	
	@Resource
    private IBankService bankService;//企业申报-银行服务类
    
    @Resource
    private ILinkService linkService;//企业申报-联系人服务类
    
    @Resource
    private IBizRegInfoService bizRegInfoService;//企业申报-工商登记服务类
    
    @Resource
    private IRegInfoService regInfoService;//企业申报-系统注册信息服务类
    
    @Resource
    private IDeclareAptitudeService declareAptitudeService;//企业申报-附件信息服务类
    
    @Resource
    private IEntDeclareService entDeclareService;//企业申报-企业申报服务类
    
    /**
     * 
     * 方法名称：保存系统注册信息
     * 方法描述：企业申报新增-保存系统注册信息
     * @param request HttpServletRequest对象
     * @param regInfo 系统注册信息对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveDeclare")
    public HttpRespEnvelope saveDeclare(HttpServletRequest request, @ModelAttribute DeclareRegInfo regInfo) {
        try {
            super.verifySecureToken(request);
            String pointNo = super.verifyPointNo(request);
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String applyId = request.getParameter("applyId");//申请编号
            String entCustId = request.getParameter("entCustId");//获取登陆企业客户号
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {regInfo, RespCode.APS_REGINFO_INVALID},
                new Object[] {regInfo.getToEntCustName(), RespCode.APS_REGINFO_ENTNAME_INVALID},
                new Object[] {regInfo.getCountryNo(), RespCode.APS_REGINFO_COUNTRYNO_INVALID},
                new Object[] {regInfo.getProvinceNo(), RespCode.APS_REGINFO_PROVINCENO_INVALID},
                new Object[] {regInfo.getCityNo(), RespCode.APS_REGINFO_CITY_INVALID},
                new Object[] {regInfo.getToCustType(), RespCode.APS_REGINFO_TOCUSTTYPE_INVALID},
                new Object[] {regInfo.getToMResNo(), RespCode.APS_REGINFO_TOMRESNO_INVALID}
            );
            //校验双签
            this.verifyDoublePwd(request, regInfo.getOptRemark(), regInfo.getDblOptCustId());
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {regInfo.getToEntCustName(), 2, 128, RespCode.APS_REGINFO_ENTNAME_LENGTH_INVALID}
            );
            //成员企业校验
            if(regInfo.getToCustType() == 2 || regInfo.getToCustType() == 3){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {regInfo.getToEntResNo(), RespCode.APS_REGINFO_TOENTRESNO_INVALID},
                    new Object[] {regInfo.getSpreadEntResNo(), RespCode.APS_FWGS_RESNO_INVALID},
                    new Object[] {regInfo.getToBuyResRange(), RespCode.APS_REGINFO_TOBUY_RESRANGE_INVALID}
                );
                //设置推广企业信息
                try {
					BsEntMainInfo info = this.entDeclareService.findEntInfo(regInfo.getSpreadEntResNo());
					regInfo.setSpreadEntCustId(info.getEntCustId());
	                regInfo.setSpreadEntCustName(info.getEntName());
				} catch (Exception e) {
					throw new HsException(RespCode.APS_DEC_RESNOINFO_NOT_FOUND);
				}
            }else if(regInfo.getToCustType() == 4){//服务公司校验
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {regInfo.getSpreadEntResNo(), ASRespCode.APS_DEC_RESNO_INVALID},
                    new Object[] {regInfo.getToBusinessType(), ASRespCode.AS_BUSINESSTYPE_INVALID }
                );
                //设置推广企业信息
                try {
					BsEntMainInfo info = this.entDeclareService.findEntInfo(regInfo.getSpreadEntResNo());
					regInfo.setSpreadEntCustId(info.getEntCustId());
	                regInfo.setSpreadEntCustName(info.getEntName());
	                regInfo.setSpreadEntResNo(regInfo.getSpreadEntResNo());
				} catch (Exception e) {
					throw new HsException(RespCode.APS_DEC_RESNOINFO_NOT_FOUND);
				}
            }
            regInfo.setOptCustId(custId);
            regInfo.setOptName(custName);
            regInfo.setOptEntName(optEntName);
            if(StringUtils.isBlank(applyId)){
            	regInfo.setFrEntCustId(entCustId);
                regInfo.setFrEntCustName(optEntName);
                regInfo.setFrEntResNo(pointNo);
                applyId = this.regInfoService.createDeclare(regInfo);
            }else{
                this.regInfoService.updateDeclare(regInfo);
            }
            return new HttpRespEnvelope(applyId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }

    /**
     * 校验互生号是否被占用
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkValidEntResNo")
    public HttpRespEnvelope checkValidEntResNo(HttpServletRequest request) {
        try {
            String entResNo = request.getParameter("toEntResNo");//获取登陆用户名称
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {entResNo, RespCode.APS_DEC_RESNO_INVALID}
            );
            Boolean validate = this.entDeclareService.checkValidEntResNo(entResNo);
            return new HttpRespEnvelope(validate);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存银行信息
     * 方法描述：企业申报新增-保存银行信息信息
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
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //账户非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bank, ASRespCode.APS_BANKINFO_INVALID},
                new Object[] {bank.getApplyId(), RespCode.APS_APPLYID_INVALID},
                new Object[] {bank.getAccountName(), ASRespCode.APS_BANKNAME_INVALID},
                new Object[] {bank.getCurrencyCode(), ASRespCode.APS_CUR_CODE_INVALID},
                new Object[] {bank.getBankCode(), ASRespCode.APS_BANKINFO_BANKCODE_INVALID},
                new Object[] {bank.getProvinceNo(), ASRespCode.APS_PROVINCENO_INVALID},
                new Object[] {bank.getCityNo(), ASRespCode.APS_CITYNO_INVALID},
                new Object[] {bank.getAccountNo(), ASRespCode.AS_BANKCARDNO_INVALID}
            );
            //校验双签
            this.verifyDoublePwd(request, bank.getOptRemark(), bank.getDblOptCustId());
            bank.setOptCustId(custId);
            bank.setOptName(custName);
            bank.setOptEntName(optEntName);
            if(StringUtils.isBlank(accountId)){
                accountId = this.bankService.createBank(bank);
            }else{
                this.bankService.updateBank(bank);
            }
            return new HttpRespEnvelope(accountId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存工商登记信息
     * 方法描述：企业申报新增-保存工商登记信息
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
            String isNew = request.getParameter("isNew");//新增标志
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            String busLicenceFileiId = request.getParameter("busLicenceFileiId");//营业执照文件ID
            String busLicenceApitId = request.getParameter("busLicenceApitId");//营业执照附件ID
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bizInfo, RespCode.APS_BIZREG_INVALID},
                new Object[] {bizInfo.getApplyId(), RespCode.APS_APPLYID_INVALID},
                new Object[] {bizInfo.getEntName(), RespCode.APS_ENT_FILING_ENTCUSTNAME_INVALID},
                new Object[] {bizInfo.getEntAddress(), RespCode.APS_ENT_FILING_ENT_ADDRESS_INVALID},
                new Object[] {bizInfo.getLegalName(), RespCode.APS_ENT_FILING_LEGALNAME_INVALID},
                new Object[] {bizInfo.getLicenseNo(), RespCode.APS_ENT_FILING_LICENSENO_INVALID},
                new Object[] {busLicenceFileiId, ASRespCode.APS_ENT_FILING_LICE_PIC_FILEID_INVALID}
            );
            //校验双签
            this.verifyDoublePwd(request, bizInfo.getOptRemark(), bizInfo.getDblOptCustId());
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bizInfo.getEntName(), 2, 128, RespCode.APS_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID},
                new Object[] {bizInfo.getEntAddress(), 2, 128, RespCode.APS_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID},
                new Object[] {bizInfo.getLegalName(), 2, 20, RespCode.APS_ENT_FILING_LEGALNAME_LENGTH_INVALID},
                new Object[] {bizInfo.getEntType(), 0, 20, RespCode.APS_BIZREG_ENTTYPE_LENGTH_INVALID},
                new Object[] {bizInfo.getLimitDate(), 0, 50, RespCode.APS_BIZREG_LIMITDATE_LENGTH_INVALID},
                new Object[] {bizInfo.getDealArea(), 0, 300, RespCode.APS_ENT_FILING_DEALAREA_LENGTH_INVALID}
            );
            bizInfo.setLicenseAptitude(this.buildDeclareAptitude(busLicenceApitId, bizInfo.getApplyId(), 3, busLicenceFileiId, custId, custName));
            bizInfo.setOptCustId(custId);
            bizInfo.setOptName(custName);
            bizInfo.setOptEntName(optEntName);
            if("true".equals(isNew)){
            	bizInfo = this.bizRegInfoService.createDeclareEnt(bizInfo);
            }else{
            	bizInfo = this.bizRegInfoService.updateDeclareEnt(bizInfo);
            }
            return new HttpRespEnvelope(bizInfo);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存联系人信息
     * 方法描述：企业申报新增-保存联系人信息
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
            String isNew = request.getParameter("isNew");//是否为新增
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            String venBefFlag = request.getParameter("venBefFlag");//是否需要填写创业帮扶协议
            String venBefProtocolFileId = request.getParameter("venBefProtocolFileId");//创业帮扶协议文件ID
            String venBefProtocolAptId = request.getParameter("venBefProtocolAptId");//创业帮扶协议附件ID
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {link, RespCode.APS_ENT_LINKINFO_OBJ_INVALID},
                new Object[] {link.getApplyId(), RespCode.APS_APPLYID_INVALID},
                new Object[] {link.getLinkman(), RespCode.APS_ENT_LINKINFO_LINKMAN_INVALID},
                new Object[] {link.getMobile(), RespCode.APS_ENT_LINKINFO_MOBILE_INVALID},
                new Object[] {link.getCertificateFile(), RespCode.APS_ENT_LINKINFO_CERT_INVALID}
            );
            //校验双签
            this.verifyDoublePwd(request, link.getOptRemark(), link.getDblOptCustId());
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {link.getLinkman(), 2, 20, RespCode.APS_ENT_LINKINFO_LINKMAN_LENGTH_INVALID}
            );
            //长度验证
            if(!StringUtils.isBlank(link.getAddress())){
                RequestUtil.verifyParamsLength(
                    new Object[] {link.getAddress(), 2, 128, RespCode.APS_ENT_LINKINFO_ADDRESS_LENGTH_INVALID}
                );
            }
            //托管企业创业帮扶协议必填
            if("true".equals(venBefFlag)){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {venBefProtocolFileId, RespCode.APS_DECLARE_PRO_PIC_FILEID_INVALID}
                );
                link.setProtocolAptitude(this.buildDeclareAptitude(venBefProtocolAptId, link.getApplyId(), 8, venBefProtocolFileId, custId, custName));
            }
            link.setOptCustId(custId);
            link.setOptName(custName);
            link.setOptEntName(optEntName);
            if("true".equals(isNew)){
            	link = this.linkService.createLinkInfo(link);
            }else{
            	link = this.linkService.updateLinkInfo(link);
            }
            return new HttpRespEnvelope(link);
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
    public HttpRespEnvelope saveDeclareAptitude(HttpServletRequest request, @ModelAttribute OptInfo info, @ModelAttribute AptitudeBean aptBean) {
        try {
            super.verifySecureToken(request);
//            super.verifyCodes(request);//校验验证码
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            String custId = request.getParameter("custId");//获取登陆客户号
            String applyId = request.getParameter("applyId");//报备企业申请编号
            String venBefFlag = request.getParameter("venBefFlag");//是否需要填写创业帮扶协议
            String custName = request.getParameter("custName");//操作员名字
            String cookieOperNoName = request.getParameter("cookieOperNoName");//操作员名字
            String aptRemark = request.getParameter("aptRemark");// 备注说明
            String creType = request.getParameter("creType");//证件类型
            if (StringUtils.isEmpty(creType) || creType.equals("1")) {
                HsAssert.hasText(aptBean.getLrCredentialBackFileId(),RespCode.APS_ENT_FILING_BACK_PIC_FILEID_INVALID);
            }
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
                new Object[] {aptBean.getLrCredentialFrontFileId(), RespCode.APS_ENT_FILING_POSI_PIC_FILEID_INVALID},
//                new Object[] {aptBean.getLrCredentialBackFileId(), RespCode.APS_ENT_FILING_BACK_PIC_FILEID_INVALID},
                new Object[] {aptBean.getBusLicenceFileId(), ASRespCode.APS_ENT_FILING_LICE_PIC_FILEID_INVALID},
                new Object[] {aptBean.getOrganizationFileId(), RespCode.APS_DECLARE_ORG_PIC_FILEID_INVALID},
                new Object[] {aptBean.getTaxplayerFileId(), RespCode.APS_DECLARE_TAX_PIC_FILEID_INVALID}
            );
            //校验双签
            this.verifyDoublePwd(request, info.getOptRemark(), info.getDblOptCustId());
            //托管企业创业帮扶协议必填
            if("true".equals(venBefFlag)){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {aptBean.getVenBefProtocolFileId(), RespCode.APS_DECLARE_PRO_PIC_FILEID_INVALID}
                );
            }
            //校验备注长度
            if(!StringUtils.isBlank(aptBean.getRemark())){
                RequestUtil.verifyParamsLength(
                    new Object[] {aptBean.getRemark(), 0, 300, RespCode.APS_REMARK_LENGTH_INVALID}
                );
            }
            //校验备注说长度
			RequestUtil.verifyParamsLength(new Object[] {
				aptRemark, 0, 300, ASRespCode.AS_VIEW_MARK_LENGTH_INVALID
			});
            //组织数据
            List<DeclareAptitude> aptList = new ArrayList<DeclareAptitude>();
            aptList.add(this.buildDeclareAptitude(aptBean.getLrCredentialFrontAptitudeId(), applyId, 1, aptBean.getLrCredentialFrontFileId(), custId, custName));
            if (StringUtils.isEmpty(creType) || creType.equals("1")) {
                aptList.add(this.buildDeclareAptitude(aptBean.getLrCredentialBackAptitudeId(), applyId, 2, aptBean.getLrCredentialBackFileId(), custId, custName));
            }
            aptList.add(this.buildDeclareAptitude(aptBean.getBusLicenceAptitudeId(), applyId, 3, aptBean.getBusLicenceFileId(), custId, custName));
            aptList.add(this.buildDeclareAptitude(aptBean.getOrganizPicAptitudeId(), applyId, 6, aptBean.getOrganizationFileId(), custId, custName));
            aptList.add(this.buildDeclareAptitude(aptBean.getTaxRegPicAptitudeId(), applyId, 7, aptBean.getTaxplayerFileId(), custId, custName));
            //托管企业创业帮扶协议必填
            if("true".equals(venBefFlag)){
                aptList.add(this.buildDeclareAptitude(aptBean.getVenBefProtocolAptitudeId(), applyId, 8, aptBean.getVenBefProtocolFileId(), custId, custName));
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
     * 方法名称：查询成员企业、托管企业配额数和可用互生号列表
     * 方法描述：企业申报-成员企业、托管企业配额数和可用互生号列表
     * @param request HttpServletRequest对象
     * @return
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/findResNoListAndQuota")
    public HttpRespEnvelope findResNoListAndQuota(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            super.verifyPointNo(request);
            String serResNo = request.getParameter("serResNo");//服务公司互生号
            String buyResRange = request.getParameter("buyResRange");//启用消费者资源类型
            String toCustType = request.getParameter("toCustType");//申请类别
            Map<String, Object> resMap = new HashMap<String, Object>();//存放数据
            Map<String, Object> map = this.regInfoService.getResNoListAndQuota(serResNo, CommonUtils.toInteger(toCustType), CommonUtils.toInteger(buyResRange));
            List<String> resNoList = (List<String>) map.get("resNoList");
            resMap.put("availQuota", CommonUtils.toInteger(map.get("quota")));
            if(resNoList != null && resNoList.size() > 0){
                resMap.put("defaultEntResNo", resNoList.get(0));
            }else{
                resMap.put("defaultEntResNo", "");
            }
            resMap.put("entResNoList", resNoList);
            resMap.put("serCustId", map.get("serCustId"));
            resMap.put("serCustName", map.get("serCustName"));
            return new HttpRespEnvelope(resMap);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：校验双签操作
     * 方法描述：校验双签操作
     * @param request HttpServletRequest对象
     * @param optRemark 复核备注长度
     * @param dblOptCustId 复核员ID
     * @return
     */
    public void verifyDoublePwd(HttpServletRequest request, String optRemark, String dblOptCustId) throws HsException {
        String verifyDouble = request.getParameter("verifyDouble");//是否需要验证双签操作
        if(StringUtils.isBlank(verifyDouble)){
        	return;
        }
        if("true".equals(verifyDouble)){
        	//校验复核备注长度
            RequestUtil.verifyParamsLength(
                new Object[] {optRemark, 0, 300, RespCode.APS_VIEW_MARK_LENGTH_INVALID}
            );
            //检查复核员ID
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {dblOptCustId, RespCode.AS_DOULBE_USERID_INVALID}
            );
        }
    }
    
    /**
     * 
     * 方法名称：查询管理公司信息和服务公司配额数
     * 方法描述：企业申报-查询系统注册信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findManageEntAndQuota")
    public HttpRespEnvelope findManageEntAndQuota(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String countryNo = request.getParameter("countryNo");//所属国家
            String provinceNo = request.getParameter("provinceNo");//所属省份
            String cityNo = request.getParameter("cityNo");//所属城市
            //查询管理公司信息和服务公司配额数
            Map<String, Object> map = this.regInfoService.queryManageEntAndQuota(countryNo, provinceNo, cityNo);
            return new HttpRespEnvelope(map);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询企业服务公司互生号列表
     * 方法描述：企业申报-查询企业服务公司互生号列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSerResNoList")
    public HttpRespEnvelope findSerResNoList(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String countryNo = request.getParameter("countryNo");//所属国家
            String provinceNo = request.getParameter("provinceNo");//所属省份
            String cityNo = request.getParameter("cityNo");//所属城市
            List<String> list = this.regInfoService.findSerResNoList(countryNo, provinceNo, cityNo);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询增值信息
     * 方法描述：企业申报-查询增值信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findIncrement")
    public HttpRespEnvelope findIncrement(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String spreadEntResNo = request.getParameter("spreadEntResNo");//企业互生号
            String subResNo = request.getParameter("subResNo");//企业互生号
            Increment increment = this.regInfoService.queryIncrement(spreadEntResNo,subResNo);
            return new HttpRespEnvelope(increment);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：依据互生号查询企业信息
     * 方法描述：企业申报-依据互生号查询企业信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findEntInfo")
    public HttpRespEnvelope findEntInfo(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String resNo = request.getParameter("spreadEntResNo");//企业互生号
            BsEntMainInfo info = this.entDeclareService.findEntInfo(resNo);
            if(info == null){
                throw new HsException(RespCode.APS_DEC_RESNOINFO_NOT_FOUND);
            }else{
                return new HttpRespEnvelope(info);
            }
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }

    /**
     * 查询推广服务公司互生号资源详情
     *
     * @param request 请求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findResNoDetail")
    public HttpRespEnvelope findResNoDetail(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String resNo = request.getParameter("spreadEntResNo");//企业互生号
            AsEntExtendInfo info = this.entDeclareService.findResNoDetail(resNo);
            if(info == null){
                throw new HsException(RespCode.APS_DEC_RESNOINFO_NOT_FOUND);
            }else{
                return new HttpRespEnvelope(info);
            }
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：提交申报
     * 方法描述：企业申报-提交申报
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submitDeclare")
    public HttpRespEnvelope submitDeclare(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            String optCustId = request.getParameter("custId");//获取登陆客户号
            String optName = request.getParameter("cookieOperNoName");//获取登陆客户名称
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.APS_APPLYID_INVALID}
            );
            OptInfo operator = new OptInfo();
            operator.setOptCustId(optCustId);
            operator.setOptName(optName);
            operator.setOptEntName(optEntName);
            this.entDeclareService.submitDeclare(applyId, operator);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSerResNos")
    public HttpRespEnvelope findSerResNos(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            request.setAttribute("serviceName", entDeclareService);
            request.setAttribute("methodName", "findSerResNos");
            return super.doList(request, response);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询成员企业、托管企业可用互生号
     * 方法描述：企业申报-查询成员企业、托管企业可用互生号
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findEntResNos")
    public HttpRespEnvelope findEntResNos(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            request.setAttribute("serviceName", entDeclareService);
            request.setAttribute("methodName", "findEntResNos");
            return super.doList(request, response);
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
        obj.setOptCustId(custId);
        obj.setOptName(custName);
        return obj;
    }
	
	@Override
	protected IBaseService getEntityService() {
		return null;
	}

}
