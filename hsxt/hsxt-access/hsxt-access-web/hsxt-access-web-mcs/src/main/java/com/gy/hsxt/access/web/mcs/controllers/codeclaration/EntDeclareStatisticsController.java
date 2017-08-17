package com.gy.hsxt.access.web.mcs.controllers.codeclaration;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.controllers.common.CompanyConfigService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IBankService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IBizRegInfoService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IDeclareAptitudeService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IEntDeclareStatisticsService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.ILinkService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IRegInfoService;
import com.gy.hsxt.access.web.mcs.services.common.IPubParamService;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.apply.DeclareAptitude;
import com.gy.hsxt.bs.bean.apply.DeclareBank;
import com.gy.hsxt.bs.bean.apply.DeclareBizRegInfo;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.codeclaration
 * @className     : EntDeclareStatisticsController.java
 * @description   : 审批统计查询
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareStatisticsController")
public class EntDeclareStatisticsController extends BaseController{
	
	@Resource
    private IEntDeclareStatisticsService entDeclareStatisticsService;//审批统计服务类
	
	@Resource
    private CompanyConfigService configService;//配置服务类
    
    @Resource
    private IBankService bankService;//企业申报-银行服务类
    
    @Autowired
    private IPubParamService pubService;//系统服务类
    
    @Resource
    private ILinkService linkService;//企业申报-联系人服务类
    
    @Resource
    private IBizRegInfoService bizRegInfoService;//企业申报-工商登记服务类
    
    @Resource
    private IRegInfoService regInfoService;//企业申报-系统注册信息服务类
    
    @Resource
    private IDeclareAptitudeService declareAptitudeService;//企业申报-系统注册信息服务类
    
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			//只在查询企业申报复核才校验日期
            if(StringUtils.isBlank(request.getAttribute("serviceName"))){
                RequestUtil.verifyQueryDate((String) filterMap.get("startDate"), (String) filterMap.get("endDate"));//校验时间
            }
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
     * 
     * 方法名称：查询工商登记信息
     * 方法描述：企业申报-查询工商登记信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDeclareEntByApplyId")
    public HttpRespEnvelope findDeclareEntByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            DeclareBizRegInfo bizInfo = this.bizRegInfoService.queryDeclareEntByApplyId(applyId);
            if(bizInfo == null){
                bizInfo = new DeclareBizRegInfo();
            }
            return new HttpRespEnvelope(bizInfo);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询系统注册信息
     * 方法描述：企业申报-查询系统注册信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDeclareByApplyId")
    public HttpRespEnvelope findDeclareByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            DeclareRegInfo regInfo = this.regInfoService.findDeclareByApplyId(applyId);//查询注册基本信息
            return new HttpRespEnvelope(regInfo);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询企业资料上传
     * 方法描述：企业申报-查询企业资料上传，同时初始化样例图片地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findAptitudeByApplyId")
    public HttpRespEnvelope findAptitudeByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            //查询附件
            Map<String, Object> aptMap = this.declareAptitudeService.queryAptitudeWithRemarkByApplyId(applyId);
            //获取被申报企业客户类型
            DeclareRegInfo regInfo = regInfoService.findDeclareByApplyId(applyId);
            String venBefFlag = "false";//是否需要填写创业帮扶协议
            //如果为托管企业并且选择的是创业资源才需要填写创业帮扶协议
            if(regInfo != null && regInfo.getToCustType() == 3 && regInfo.getToBuyResRange() == 2){
                venBefFlag = "true";
            }
            DeclareBizRegInfo bizRegInfo = bizRegInfoService.queryDeclareEntByApplyId(applyId);
            //存放数据
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("aptRemark", aptMap.get("REMARK"));
			resMap.put("realList", aptMap.get("APTITUDE_LIST"));
            resMap.put("creType", bizRegInfo.getLegalCreType());
            resMap.put("venBefFlag", venBefFlag);
            return new HttpRespEnvelope(resMap);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询联系人信息
     * 方法描述：企业申报-查询联系人信息，同时返回联系人授权委托书文件ID
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findLinkmanByApplyId")
    public HttpRespEnvelope findLinkmanByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            //获取被申报企业客户类型
            DeclareRegInfo regInfo = regInfoService.findDeclareByApplyId(applyId);
            String venBefFlag = "false";//是否需要填写创业帮扶协议
            //如果为托管企业并且选择的是创业资源才需要填写创业帮扶协议
            if(regInfo != null && regInfo.getToCustType() == 3 && regInfo.getToBuyResRange() == 2){
                venBefFlag = "true";
            }
            DeclareLinkman link = this.linkService.findLinkmanByApplyId(applyId);
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("link", link);
            resMap.put("venBefFlag", venBefFlag);
            resMap.put("fileId", configService.getPowerOfAttorneyFileId());
            return new HttpRespEnvelope(resMap);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询银行信息
     * 方法描述：企业申报-查询银行信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findBankByApplyId")
    public HttpRespEnvelope findBankByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            DeclareBank bank = this.bankService.findBankByApplyId(applyId);
            if(bank == null){
                bank = new DeclareBank();
            }
            Map<String, Object> resMap = new HashMap<String, Object>();//存放初始化数据
            LocalInfo info = pubService.findSystemInfo();
            resMap.put("countryNo", info.getCountryNo());
            resMap.put("currencyCode", info.getCurrencyCode());
            resMap.put("currencyName", info.getCurrencyNameCn());
            DeclareBizRegInfo regInfo = this.bizRegInfoService.queryDeclareEntByApplyId(applyId);
            resMap.put("accountName", regInfo==null?"":regInfo.getEntName());
            resMap.put("bank", bank);
            return new HttpRespEnvelope(resMap); 
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询申报信息
     * 方法描述：依据企业申请编号查询申报信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDeclareAppInfoByApplyId")
    public HttpRespEnvelope findDeclareAppInfoByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            DeclareAppInfo obj = this.entDeclareStatisticsService.findDeclareAppInfoByApplyId(applyId);
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询办理状态信息
     * 方法描述：依据企业申请编号查询办理状态信息
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findOperationHisList")
    public HttpRespEnvelope findOperationHisList(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {request.getParameter("search_applyId"), RespCode.MW_APPLYID_INVALID}
            );
            request.setAttribute("serviceName", entDeclareStatisticsService);
            request.setAttribute("methodName", "findOperationHisList");
            return super.doList(request, response);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：构建附件对象
     * 方法描述：构建附件对象
     * @param aptType 附件类型
     * @param fileId 文件地址
     */
    public DeclareAptitude buildDeclareAptitude(Integer aptType, String fileId){
        DeclareAptitude obj = new DeclareAptitude();
        obj.setFileId(fileId);
        obj.setAptitudeType(aptType);
        return obj;
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
		return entDeclareStatisticsService;
	}

}
