package com.gy.hsxt.access.web.person.controllers.welfare;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.services.consumer.IWelfareService;
import com.gy.hsxt.access.web.person.services.hsc.ICardHolderAuthInfoService;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.ws.api.IWsApplyService;
import com.gy.hsxt.ws.bean.AccidentSecurityApply;
import com.gy.hsxt.ws.bean.BaseApply;
import com.gy.hsxt.ws.bean.MedicalSubsidiesApply;
import com.gy.hsxt.ws.bean.OthersDieSecurityApply;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareQualify;

/**
 * 积分福利控制类
 * 
 * @category 积分福利控制类
 * @projectName hsxt-access-web-person
 * @package 
 *          com.gy.hsxt.access.web.person.controllers.welfare.WelfareController.java
 * @className WelfareController
 * @description 积分福利控制类
 * @author leiyt
 * @createDate 2015-12-29 下午12:08:59
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午12:08:59
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Controller
@RequestMapping("welfare")
public class WelfareController extends BaseController<Object> {

    @Autowired
    private IWelfareService welfareService;

    @Autowired
    private IWsApplyService applyService;

    @Autowired
    private IUCAsCardHolderService ucAsCardHolderService;

    @Resource
    private ICardHolderAuthInfoService cardHolderAuthInfoService;// 查询实名注册信息服务类

    /**
     * 意外伤害保障金申请
     * 
     * @param record
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/applyAccidentSecurity" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyAccidentSecurity(@ModelAttribute AccidentSecurityApply record,
            HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			resetUserInfo(record);
			String[] ary = request.getParameterValues("otherProvesPath[]");
			List<String> otherProvePath = new ArrayList<String>(5);
			if (ary != null && ary.length > 0) {
				for (String path : ary) {
					if (path != null && !path.equals("")) {
						otherProvePath.add(path);
					}
				}
			}
			record.setOtherProvePath(otherProvePath);
			record.setProposerPapersNo(getCreNo(record.getCustId()));
			welfareService.applyAccidentSecurity(record);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
    }

    private String getCreNo(String custId) {
        PerRealnameAuth realnameAuth = cardHolderAuthInfoService.queryPerRealnameAuthByCustId(custId);
        if (realnameAuth == null)
            return "";
        return realnameAuth.getCredentialsNo();
    }
    
    /**
     * 
     * 重设申请人姓名和证件号码
     * @param record
     */
    private void resetUserInfo(BaseApply record) {
    	AsRealNameAuth info = cardHolderAuthInfoService.searchRealNameAuthInfo(record.getCustId());
        if (info == null){
        	throw new HsException();
        }
        record.setProposerPapersNo(info.getCerNo());
        record.setProposerName(info.getUserName());
    }
    
    /**
     * 医疗补贴申请
     * 
     * @param record
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/applyMedicalSubsidies" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyMedicalSubsidies(@ModelAttribute MedicalSubsidiesApply record,
            HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
        	resetUserInfo(record);
            record.setProposerPapersNo(getCreNo(record.getCustId()));
            String[] ary = request.getParameterValues("otherProvesPath[]");
            
            List<String> otherProvePath = new ArrayList<String>(5);
            if(ary!=null && ary.length>0){
	            for(String path : ary){
		            if( path != null && !path.equals("")){
		            	otherProvePath.add(path);
		            }
	            }
            }
            record.setOtherProvePath(otherProvePath);
            welfareService.applyMedicalSubsidies(record);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 代他人身故保障金申请
     * 
     * @param record
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/applyDieSecurity" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope applyDieSecurity(@ModelAttribute OthersDieSecurityApply record, HttpServletRequest request,
            HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            AsCardHolder cardHolder = ucAsCardHolderService.searchCardHolderInfoByResNo(record.getDeathResNo());
            AsCardHolderAllInfo allInfo = ucAsCardHolderService.searchAllInfo(cardHolder.getPerCustId());
            
            // 验证身故人证件类型是否为企业
            if ("3".equals(allInfo.getAuthInfo().getCerType()))
            {
                throw new HsException(ASRespCode.PW_WELFARE_CRE_TYPE_ERROR);
            }
            
            // 验证身故人是否存在
            if (cardHolder == null)
            {
                throw new HsException(ASRespCode.PW_WELFARE_CONSUMER_NOT_EXIST);
            }
            // 验证身故人是否实名认证
            if (3 != cardHolder.getIsRealnameAuth())
            {
                throw new HsException(ASRespCode.PW_WELFARE_NOT_REALNAME_AUTH);
            }
            /*
            // 验证身故人是否绑定手机
            if (isBlank(cardHolder.getMobile()))
            {
                throw new HsException(ASRespCode.PW_WELFARE_NOT_BIND_MOBLIE);
            }
            */
            // 验证身故人姓名跟互生号是否一致
            if (!record.getDiePeopleName().equals(allInfo.getAuthInfo().getUserName()))
            {
                throw new HsException(ASRespCode.PW_WELFARE_REALANME_IS_ERROR);
            }
            resetUserInfo(record);
            
            String[] ary = request.getParameterValues("otherProvesPath[]");
            List<String> otherProvePath = new ArrayList<String>(5);
            if(ary!=null && ary.length>0){
	            for(String path : ary){
		            if( path != null && !path.equals("")){
		            	otherProvePath.add(path);
		            }
	            }
            }
            record.setOtherProvePath(otherProvePath);
            welfareService.applyDieSecurity(record);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 积分福利申请详情查询
     * 
     * @param applyWelfareNo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryWelfareApplyDetail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryWelfareApplyDetail(String applyWelfareNo, HttpServletRequest request,
            HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        WelfareApplyDetail record = null;
        try
        {
            record = welfareService.queryWelfareApplyDetail(applyWelfareNo);
            hre = new HttpRespEnvelope(record);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 积分福利资格查询
     * 
     * @param hsResNo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findWelfareQualify" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findWelfareQualify(String hsResNo, Integer welfareType, HttpServletRequest request,
            HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            WelfareQualify welfareQualify = welfareService.findWelfareQualify(hsResNo);
            WelfareApplyDetail applyDetail = applyService.queryLastApplyRecord(hsResNo, welfareType);
            java.util.Map<String, Object> map = new HashMap<String, Object>();
            map.put("welfareQualify", welfareQualify);
            map.put("applyDetail", applyDetail);
            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
    
    private void resetApplyInfo(BaseApply record){
		AsCardHolder cardHolder = ucAsCardHolderService.searchCardHolderInfoByResNo(record.getHsResNo());
        AsCardHolderAllInfo allInfo = ucAsCardHolderService.searchAllInfo(cardHolder.getPerCustId());
        record.setProposerName("ff");
	}
    
    @Override
    protected IBaseService<?> getEntityService() {
        return welfareService;
    }
}
