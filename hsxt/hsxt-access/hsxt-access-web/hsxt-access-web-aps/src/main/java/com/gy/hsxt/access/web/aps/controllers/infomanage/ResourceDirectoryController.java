/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.infomanage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.companyInfo.ICompanyInfoService;
import com.gy.hsxt.access.web.aps.services.companyInfo.IUCBankfoService;
import com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService;
import com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/**
 * 资源名录 托管企业、成员企业、服务公司 一览列表查询
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.customermanager
 * @ClassName: ResourceDirectoryController
 * @Description: 
 * 
 * @author: zhangcy
 * @date: 2015-12-8 下午2:58:32
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("resourcedirect")
public class ResourceDirectoryController extends BaseController {

    @Resource
    private ResourceDirectoryService resourceDirectoryService;

    @Resource
    private EntInfoService entInfoService;

    @Resource
    private IUCBankfoService ucBankService;// 银行信息服务类

    @Resource
    private ICompanyInfoService companyService;// 企业信息服务类

    @ResponseBody
    @RequestMapping(value = { "/queryentinfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryEntInfo(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

        
        if (httpRespEnvelope == null)
        {
            try
            {
                // 企业互生号
                String entCustId = request.getParameter("entCustId");
                // 企业类型
                String userType = request.getParameter("userType");
                // 企业信息
                AsEntAllInfo entInfo = entInfoService.searchEntAllInfo(entCustId);
                // 企业银行账户列表
                List<AsBankAcctInfo> accList = entInfoService.findBanksByCustId(entCustId, userType);
                // 业务操作许可

                Map<String, Object> result = new HashMap<String, Object>();
                result.put("entInfo", entInfo);
                result.put("accList", accList);
                httpRespEnvelope = new HttpRespEnvelope(result);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }

    @Override
    protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
        try
        {
            // 地区平台企业互生号
            String entResNo = (String) filterMap.get("entResNo");
            // 要查询的企业类型（托管企业、成员企业、服务公司...）
            Integer blongEntCustType = filterMap.get("blongEntCustType") == null
                    || "".equals(filterMap.get("blongEntCustType")) ? null : Integer.parseInt((String) filterMap
                    .get("blongEntCustType"));
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { entResNo,
                    RespCode.ASP_CONTRACT_ENTCUSTID_INVALID.getCode(),
                    RespCode.ASP_CONTRACT_ENTCUSTID_INVALID.getDesc() });
            // 关联企业信息管理查询，需要查询全部企业，不需要校验企业类型
            String allEnt = (String) filterMap.get("allEnt");
            if (!StringUtils.isEmpty(allEnt))
            {
                return null;
            }
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { blongEntCustType,
                    RespCode.APS_SKGJZZ_GLXLH_OPERNO.getCode(), RespCode.APS_SKGJZZ_GLXLH_OPERNO.getDesc() });
        }
        catch (Exception e)
        {
            return new HttpRespEnvelope(e);
        }
        return null;
    }

    /**
     * 查询消费者资源
     * 
     * @param condition
     *            查询条件
     * @param page
     *            分页条件
     * @param condition
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/listConsumerInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope listConsumerInfo(HttpServletResponse response, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            
            
            // Token验证
            super.verifySecureToken(request);

            // 分页查询
            request.setAttribute("serviceName", resourceDirectoryService);
            request.setAttribute("methodName", "listConsumerInfo");
            hre = super.doList(request, response);
             

           /* hre = new HttpRespEnvelope();
            Map<String, String> filterMap = new HashMap<String, String>();
            filterMap.put("perResNo", request.getParameter("perResNo"));
            filterMap.put("status", request.getParameter("search_status"));
            filterMap.put("startDate", request.getParameter("search_startDate"));
            filterMap.put("endDate", request.getParameter("search_endDate"));
            filterMap.put("realName", request.getParameter("realName"));

            Map sortMap = new HashMap<>();
            PageData<ReportsCardholderResource> pd = resourceDirectoryService
                    .listConsumerInfo(filterMap, sortMap, page);
            if (pd != null)
            {
                hre.setData(pd.getResult());
                hre.setTotalRows(pd.getCount());
            }
            hre.setCurPage(page.getCurPage());*/
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查询消费者（持卡人）所有信息
     * 
     * @param custID
     *            消费者客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryConsumerAllInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryConsumerAllInfo(String custID, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            hre = new HttpRespEnvelope();
            AsCardHolderAllInfo consumerAllInfo = resourceDirectoryService.queryConsumerAllInfo(custID);
            hre.setData(consumerAllInfo);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查询绑定银行卡列表
     * 
     * @param custID
     *            客户号（持卡人、非持卡人、企业）
     * @param request
     * 
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/listBanksByCustId" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope listBanksByCustId(String custID, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            hre = new HttpRespEnvelope();
            List<AsBankAcctInfo> bankList = resourceDirectoryService.listBanksByCustId(custID, UserTypeEnum.CARDER
                    .getType());
            if (bankList != null && bankList.size() > 0)
            {
                for (AsBankAcctInfo bankInfo : bankList)
                {
                    String bankNo = bankInfo.getBankAccNo().substring(0, 4) + " **** **** "
                            + bankInfo.getBankAccNo().substring(bankInfo.getBankAccNo().length() - 4);
                    bankInfo.setBankAccNo(bankNo);
                }
            }
            hre.setData(bankList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查询绑定快捷卡列表
     * 
     * @param custID
     *            客户号（持卡人、非持卡人、企业）
     * @param request
     * 
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/listQkBanksByCustId" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope listQkBanksByCustId(String custID, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            hre = new HttpRespEnvelope();
            List<AsQkBank> qkBankList = resourceDirectoryService.listQkBanksByCustId(custID, UserTypeEnum.CARDER
                    .getType());
            if (qkBankList != null && qkBankList.size() > 0)
            {
                for (AsQkBank qkBank : qkBankList)
                {
                    String bankNo = qkBank.getBankCardNo().substring(0, 4) + " **** **** "
                            + qkBank.getBankCardNo().substring(qkBank.getBankCardNo().length() - 4);
                    qkBank.setBankCardNo(bankNo);
                }
            }
            hre.setData(qkBankList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 
     * 方法名称：查询信息 方法描述：企业系统信息
     * 
     * @param request
     *            HttpServletRequest对象
     * @param custID
     *            企业客户号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resourceFindEntAllInfo")
    public HttpRespEnvelope findEntAllInfo(HttpServletRequest request, String custID) {
        try
        {
            super.verifySecureToken(request);
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { custID, RespCode.APS_SMRZSP_ENTCUSTID_NOT_NULL });
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("entInfo", this.companyService.findEntAllInfo(custID));
            map.put("bakInfo", this.resourceDirectoryService.listBanksByCustId(custID, UserTypeEnum.ENT.getType()));
            map.put("payInfo", this.resourceDirectoryService.listQkBanksByCustId(custID, UserTypeEnum.ENT.getType()));
            return new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            return new HttpRespEnvelope(e);
        }
    }

    /**
     * 查询业务操作许可
     * 
     * @param custID
     *            客户号（持卡人、非持卡人、企业）
     * @param request
     * 
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/findBusinessPmInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findBusinessPmInfo(String custID, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            super.verifySecureToken(request);
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { custID, RespCode.APS_SMRZSP_ENTCUSTID_NOT_NULL });

            hre = new HttpRespEnvelope();
            BusinessSysBoSetting businessBo = new BusinessSysBoSetting();
            businessBo.setCustId(custID);
            Map<String, BusinessSysBoSetting> map = resourceDirectoryService.findBusinessPmInfo(businessBo);
            Map<String, String> mapInfo = new HashMap<String, String>();
            String buyhsb = BusinessParam.BUY_HSB.getCode();// 兑换互生币
            String hsbtocash = BusinessParam.HSB_TO_CASH.getCode();// 互生币转货币
            String cashtobank = BusinessParam.CASH_TO_BANK.getCode();// 货币转银行
            String pvtohsb = BusinessParam.PV_TO_HSB.getCode(); // 积分转互生币
            String pvinvest = BusinessParam.PV_INVEST.getCode(); // 积分投资

            if (map == null || map.isEmpty())
            {
                mapInfo.put(buyhsb, "0");
                mapInfo.put(hsbtocash, "0");
                mapInfo.put(cashtobank, "0");
                mapInfo.put(pvtohsb, "0");
                mapInfo.put(pvinvest, "0");
            }
            else
            {
                mapInfo.put(buyhsb, map.get(buyhsb) == null ? "0" : map.get(buyhsb).getSettingValue());
                mapInfo.put(hsbtocash, map.get(hsbtocash) == null ? "0" : map.get(hsbtocash).getSettingValue());
                mapInfo.put(cashtobank, map.get(cashtobank) == null ? "0" : map.get(cashtobank).getSettingValue());
                mapInfo.put(pvtohsb, map.get(pvtohsb) == null ? "0" : map.get(pvtohsb).getSettingValue());
                mapInfo.put(pvinvest, map.get(pvinvest) == null ? "0" : map.get(pvinvest).getSettingValue());
                //添加remark
                mapInfo.put(buyhsb + "Remark", map.get(buyhsb) == null ? "" : map.get(buyhsb).getSettingRemark());
                mapInfo.put(hsbtocash + "Remark", map.get(hsbtocash) == null ? "" : map.get(hsbtocash).getSettingRemark());
                mapInfo.put(cashtobank + "Remark", map.get(cashtobank) == null ? "" : map.get(cashtobank).getSettingRemark());
                mapInfo.put(pvtohsb + "Remark", map.get(pvtohsb) == null ? "" : map.get(pvtohsb).getSettingRemark());
                mapInfo.put(pvinvest + "Remark", map.get(pvinvest) == null ? "" : map.get(pvinvest).getSettingRemark());
                
            }
            hre.setData(mapInfo);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 设置业务操作许可
     * 
     * @param custID
     *            客户号（持卡人、非持卡人、企业）
     * @param request
     * 
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/setBusinessPmInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope setBusinessPmInfo(String custID, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            super.verifySecureToken(request);
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { custID, RespCode.APS_SMRZSP_ENTCUSTID_NOT_NULL });
            hre = new HttpRespEnvelope();
            String buyhsb = BusinessParam.BUY_HSB.getCode();// 兑换互生币
            String hsbtocash = BusinessParam.HSB_TO_CASH.getCode();// 互生币转货币
            String cashtobank = BusinessParam.CASH_TO_BANK.getCode();// 货币转银行
            String pvtohsb = BusinessParam.PV_TO_HSB.getCode(); // 积分转互生币
            String pvinvest = BusinessParam.PV_INVEST.getCode(); // 积分投资
            String buyhsb_val = request.getParameter("dhhsb");
            String buyhsb_info = request.getParameter("dhhsb_info");
            String hsbtocash_val = request.getParameter("hsbzhb");
            String hsbtocash_info = request.getParameter("hsbzhb_info");
            String cashtobank_val = request.getParameter("hbzyh");
            String cashtobank_info = request.getParameter("hbzyh_info");
            String pvtohsb_val = request.getParameter("jfzhsb");
            String pvtohsb_info = request.getParameter("jfzhsb_info");
            String pvinvest_val = request.getParameter("jftz");
            String pvinvest_info = request.getParameter("jftz_info");
            String operCustId = request.getParameter("custId");
            List<BusinessSysBoSetting> sysBoSettingList = new ArrayList<>();
            sysBoSettingList.add(this.setBusinessSysBoSetting(custID, buyhsb, buyhsb_val, buyhsb_info));
            sysBoSettingList.add(this.setBusinessSysBoSetting(custID, hsbtocash, hsbtocash_val, hsbtocash_info));
            sysBoSettingList.add(this.setBusinessSysBoSetting(custID, cashtobank, cashtobank_val, cashtobank_info));
            sysBoSettingList.add(this.setBusinessSysBoSetting(custID, pvtohsb, pvtohsb_val, pvtohsb_info));
            sysBoSettingList.add(this.setBusinessSysBoSetting(custID, pvinvest, pvinvest_val, pvinvest_info));
            resourceDirectoryService.setBusinessPmInfo(custID, operCustId, sysBoSettingList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    private BusinessSysBoSetting setBusinessSysBoSetting(String custID, String name, String value, String info) {
        BusinessSysBoSetting bss = new BusinessSysBoSetting();
        bss.setCustId(custID);
        bss.setSettingName(name);
        bss.setSettingValue(value);
        bss.setSettingRemark(info);
        return bss;
    }
    
    
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = { "/ent_resource_dowload" }, method = { RequestMethod.GET })
    public void entResourceDowload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedInputStream br = null;
        OutputStream out = null;

        // 设置输出文件类型
        response.reset();
        response.setContentType("application/x-msdownload");

        try
        {
            SystemLog.info(this.getClass().getName(), "entResourceDowload", "企业资源导出入参："
                    + JSON.toJSONString(request.getParameterMap()));

            // 获取查询条件
            Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");
            
            // 获取下载文件路径
            String dowloadPath = resourceDirectoryService.entResourceDowloadPath(filterMap);
            SystemLog.info(this.getClass().getName(),"entResourceDowload", "获取文件成功，文件地址："+dowloadPath);

            // 初始化文件
            File f = new File(dowloadPath);
            if (!f.exists())
            {
                SystemLog.info(this.getClass().getName(), "entResourceDowload", "文件不存在");

                response.reset();
                response.setContentType("text/html");
                response.getOutputStream().write("Download file does not exist".getBytes());
                return;
            }

            // 设置下载文件
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(f.getName(), "UTF-8"));
            br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;
            out = response.getOutputStream();
            while ((len = br.read(buf)) > 0){
                out.write(buf, 0, len);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "entResourceDowload", "导出文件错误", e);

            response.reset();
            response.setContentType("text/html");
            if (e instanceof HsException) {
                response.getOutputStream().write(e.getMessage().getBytes());
            } else {
                response.getOutputStream().write("Download file failed".getBytes());
            }
        }
        finally
        {
            if (null != out)
            {
                try{
                    out.close();
                }
                catch (Exception e){
                    SystemLog.error(this.getClass().getName(), "entResourceDowload", "关闭OutputStream对象异常", e);

                    response.reset();
                    response.setContentType("text/html");
                    response.getOutputStream().write("Download file failed".getBytes());
                }
            }

            try
            {
                if (null != br)
                {
                    br.close();
                }
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "entResourceDowload", "关闭BufferedInputStream对象异常", e);

                response.reset();
                response.setContentType("text/html");
                response.getOutputStream().write("Download file failed".getBytes());
            }
        }
    }

    @Override
    protected IBaseService getEntityService() {
        return resourceDirectoryService;
    }

}
