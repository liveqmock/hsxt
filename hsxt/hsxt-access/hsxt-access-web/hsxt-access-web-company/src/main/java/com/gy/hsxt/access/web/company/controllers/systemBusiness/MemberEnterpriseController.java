/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.common.PubParamService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitStatus;
import com.gy.hsxt.bs.bean.entstatus.PointActivityStatus;
import com.gy.hsxt.bs.common.enumtype.entstatus.QuitType;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 成员企业资格维护
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness  
 * @ClassName: MemberEnterpriseController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-20 下午9:06:31 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("memberenterprise")
public class MemberEnterpriseController extends BaseController {
    
    @Resource
    private IMemberEnterpriseService imemberEnterpriseService;
    
    @Resource
    private IBalanceService balanceService; // 账户信息服务类
    
    @Resource
    private PubParamService pubParamService;
    /**
     * 查询企业状态
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_enterprise_status" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryEnterStatus(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {   
            String custId = request.getParameter("entCustId");
            String type = request.getParameter("searchType");  //"1"为查询积分活动状态  "2"为查询注销状态
            try
            {
                Map<String,Object> result = new HashMap<String,Object>();
                AsEntStatusInfo info = imemberEnterpriseService.searchEntStatusInfo(custId);
                result.put("info", info);
                if("1".equals(type)){
                    PointActivityStatus poin = imemberEnterpriseService.queryPointActivityStatus(custId);
                    result.put("poin", poin);
                }else{
                    MemberQuitStatus memb = imemberEnterpriseService.queryMemberQuitStatus(custId);
                    result.put("memb", memb);
                }
                httpRespEnvelope = new HttpRespEnvelope(result);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 查询企业积分预付款账户余额、货币转换比率、货币转换费、银行账户、账户名称、开户银行、开户地区、银行账号、结算币种
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_business_accountinfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryBusinessAccount(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {   
            String custId = request.getParameter("entCustId");
            String userType = request.getParameter("userType");
            try
            {
                Map<String,Object> result = new HashMap<String,Object>();
                //查询企业账户信息（银行账户、账户名称、开户银行、开户地区、银行账号）
                List<AsBankAcctInfo> account = imemberEnterpriseService.findBanksByCustId(custId, userType);
                result.put("acountlist", account);
                //查询货币转换费
                AccountBalance tran = balanceService.findAccNormal(custId, AccountType.ACC_TYPE_POINT20410.getCode());
                
                result.put("transfee", tran == null?0:tran.getAccBalance());
                
                //查询企业积分预付款账户余额
                AccountBalance balance = balanceService.findAccNormal(custId, AccountType.ACC_TYPE_POINT30210.getCode());
               
                result.put("pointfee", balance == null?0:balance.getAccBalance());
                
                //获取本地信息
                LocalInfo local = pubParamService.findSystemInfo();
                //结算币种
                result.put("currence", local.getCurrencyNameCn());
                //货币转换比率
                result.put("transrat", local.getExchangeRate());
                httpRespEnvelope = new HttpRespEnvelope(result);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    /**
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/member_apply_quit" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addMemberQuit(HttpServletRequest request,@ModelAttribute MemberQuit member) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {   
            try
            {
                // 非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        new Object[] {member.getApplyReason(), ASRespCode.EW_SYSBUSINESS_APPLY_REASON.getCode()} // 申请原因不准为空
                );
                member.setApplyType(QuitType.APPLY_QUIT.getCode());
                
                imemberEnterpriseService.createMemberQuit(member);
                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (Exception e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    @Override
    protected IBaseService getEntityService() {
        return imemberEnterpriseService;
    }

}
