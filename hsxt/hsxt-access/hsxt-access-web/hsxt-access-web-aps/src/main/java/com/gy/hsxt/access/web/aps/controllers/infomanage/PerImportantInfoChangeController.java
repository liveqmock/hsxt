/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.infomanage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.infomanage.InfoManageService;
import com.gy.hsxt.access.web.aps.services.infomanage.PerImportantInfoChangeService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AsConstants;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 消费者重要信息变更 审批
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.infomanage  
 * @ClassName: PerImportantInfoChangeController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-10 下午3:52:53 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("perimportantinfochange")
public class PerImportantInfoChangeController extends InfoManageBaseController {

    @Resource
    private PerImportantInfoChangeService perImportantInfoChangeService;
    
    /**
     * 个人（消费者）重要信息审批
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/appr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope appr(HttpServletRequest request, @ModelAttribute ApprParam apprParam) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            {
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {apprParam.getApplyId(), RespCode.APS_SMRZSP_APPLYID_NOT_NULL},
                        new Object[] {apprParam.getOptCustId(), RespCode.APS_SMRZSP_OPTCUSTID_NOT_NULL},
                        new Object[] {apprParam.getOptName(), RespCode.APS_SMRZSP_OPTNAME_NOT_NULL},
                        new Object[] {apprParam.getOptEntName(), RespCode.APS_SMRZSP_OPTENTNAME_NOT_NULL},
                        new Object[] {apprParam.getIsPass(), RespCode.APS_SMRZSP_ISPASS_NOT_NULL}
                        
                );
                
                perImportantInfoChangeService.apprPerChange(apprParam);
                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     *个人（消费者）重要信息复核
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/review" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope review(HttpServletRequest request, @ModelAttribute ApprParam apprParam) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            {
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {apprParam.getApplyId(), RespCode.APS_SMRZSP_APPLYID_NOT_NULL},
                        new Object[] {apprParam.getOptCustId(), RespCode.APS_SMRZSP_OPTCUSTID_NOT_NULL},
                        new Object[] {apprParam.getOptName(), RespCode.APS_SMRZSP_OPTNAME_NOT_NULL},
                        new Object[] {apprParam.getOptEntName(), RespCode.APS_SMRZSP_OPTENTNAME_NOT_NULL},
                        new Object[] {apprParam.getIsPass(), RespCode.APS_SMRZSP_ISPASS_NOT_NULL}
                        
                );
                perImportantInfoChangeService.reviewApprPerChange(apprParam);
                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 个人（消费者）重要信息详细查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_detail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryDetail(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            {
                //申请编号
                String applyId = request.getParameter("applyId");
                
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {applyId, RespCode.APS_SMRZSP_APPLYID_NOT_NULL}
                        
                );
                //根据编号查询重要信息 业务办理信息
                PerChangeInfo per = perImportantInfoChangeService.queryPerChangeById(applyId);
                
                //以上两项共同组成页面审批、复核、查询页面中的详细信息（包括业务办理信息，办理状态信息两个TAB页的信息）
                Map<String,Object> result = new HashMap<String,Object>();
                result.put("per", per);
                
                httpRespEnvelope = new HttpRespEnvelope(result);
            }
            catch (HsException e)
            {
                e.printStackTrace();
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /** 
     * 消费者重要信息变更修改
     * @param request
     * @param perChangeInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/modify" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope modify(HttpServletRequest request, @ModelAttribute PerChangeInfo perChangeInfo) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            {
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {perChangeInfo.getApplyId(), RespCode.APS_SMRZSP_APPLYID_NOT_NULL},
                        new Object[] {perChangeInfo.getChangeItem(), RespCode.APS_IMPORTANT_CHANGITEM_NOT_NULL},
                        new Object[] {perChangeInfo.getOptCustId(), RespCode.APS_SMRZSP_OPTCUSTID_NOT_NULL},
                        new Object[] {perChangeInfo.getOptName(), RespCode.APS_SMRZSP_OPTNAME_NOT_NULL},
                        new Object[] {perChangeInfo.getOptEntName(), RespCode.APS_SMRZSP_OPTENTNAME_NOT_NULL},
                        new Object[] {perChangeInfo.getDblOptCustId(), RespCode.APS_SMRZSP_DBLOPTCUSTID_NOT_NULL}
                        
                );
                
                perImportantInfoChangeService.modifyPerChange(perChangeInfo);
                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    /**
     * 消费者重要信息状态列表查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/status_list")
    public HttpRespEnvelope statusList(HttpServletRequest request) {
        // 声明变量
        Page page = null; // 分页对象
        PageData<?> pd = null;
        Integer no = null; // 当前页码
        Integer size = null; // 每页显示条数
        HttpRespEnvelope hre = null; // 返回界面数据封装对象

        // 获取客户端传递每页显示条数
        String pageSize = (String) request.getParameter("pageSize");

        // 获取客户端传递当前页码
        String curPage = (String) request.getParameter("curPage");

        // 设置当前页默认第一页
        if (StringUtils.isBlank(curPage))
        {
            no = AsConstants.PAGE_NO;
        }
        else
        {
            no = Integer.parseInt(curPage);
        }

        // 设置页显示行数
        if (StringUtils.isBlank(pageSize))
        {
            size = AsConstants.PAGE_SIZE;
        }
        else
        {
            size = Integer.parseInt(pageSize);
        }

        // 构造分页对象
        page = new Page(no, size);

        try
        {
           String applyId = request.getParameter("applyId");
           pd = perImportantInfoChangeService.queryPerChangeHis(applyId, page);
        }
        catch (Exception e)
        {
            return new HttpRespEnvelope(e);
        }

        // 封装返回界面json字符串
        hre = new HttpRespEnvelope();
        if(pd != null && pd.getCount() > 0)
        {
            hre.setData(pd.getResult());
            hre.setTotalRows(pd.getCount());
        }
        hre.setCurPage(page.getCurPage());
        return hre;

    }
    @Override
    protected IBaseService getEntityService() {
        return perImportantInfoChangeService;
    }

    @Override
    protected InfoManageService getInfoManageService() {
        return perImportantInfoChangeService;
    }

}
