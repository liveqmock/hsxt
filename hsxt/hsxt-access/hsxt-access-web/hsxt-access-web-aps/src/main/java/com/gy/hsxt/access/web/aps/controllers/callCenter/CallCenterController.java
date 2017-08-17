package com.gy.hsxt.access.web.aps.controllers.callCenter;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.controllers.receivingManage.OrderExportPersonalized;
import com.gy.hsxt.access.web.aps.services.callCenter.IAccountSearchService;
import com.gy.hsxt.access.web.aps.services.callCenter.ICompanyService;
import com.gy.hsxt.access.web.aps.services.callCenter.IPersonService;
import com.gy.hsxt.access.web.aps.services.common.ExcelExport;
import com.gy.hsxt.access.web.aps.services.companyInfo.IUCBankfoService;
import com.gy.hsxt.access.web.aps.services.infomanage.ResourceDirectoryService;
import com.gy.hsxt.access.web.aps.services.platformProxy.IAnnualFeeOrderService;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareApplyService;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareQualifyService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.ws.bean.WelfareQualify;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 呼叫中心专用接口
 *
 * @author leiyt
 * @version v0.0.1
 * @category 呼叫中心专用接口
 * @projectName hsxt-access-web-aps
 * @package com.gy.hsxt.access.web.aps.controllers.callCenter.CallCenterController.java
 * @className CallCenterController
 * @description 呼叫中心专用接口, 用于查询 用户邮箱、手机、银行卡绑定，实名认证，业务办理状态查询，用户详情查询
 * 各账户余额查询，积分福利资格查询等
 * @createDate 2016-1-27 下午3:44:45
 * @updateUser leiyt
 * @updateDate 2016-1-27 下午3:44:45
 * @updateRemark 说明本次修改内容
 */
@Controller
@RequestMapping("callCenter")
public class CallCenterController extends BaseController<Object> {

    //消费者信息查询服务接口
    @Autowired
    IPersonService personService;
    //银行卡信息查询服务接口
    @Autowired
    IUCBankfoService iucBankfoService;
    //账务余额查询服务接口
    @Autowired
    IAccountSearchService accountSearchService;
    //积分福利资格查询服务接口
    @Autowired
    IWelfareQualifyService welfareQualifyService;
    //积分福利申请查询接口
    @Autowired
    IWelfareApplyService welfareApplyService;
    //企业信息查询服务接口
    @Autowired
    ICompanyService companyService;
    //查询企业年费信息接口
    @Autowired
    private IAnnualFeeOrderService annualFeeOrderService;
    @Autowired
    ResourceDirectoryService resourceDirectoryService;


    /**
     * 手机号码归属地查询
     *
     * @param num
     * @return
     */
    @RequestMapping(value = {"/getMobilePhoneCity"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getMobilePhoneCity(String num) {
        return new HttpRespEnvelope(MobilePhone.getMobilePhoneCity(num));
    }

    /**
     * 查询企业年费信息
     * @param resNo
     * @return
     */
    @RequestMapping(value = {"/queryAnnualFeeInfo"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope queryAnnualFeeInfo(String resNo) {
        HttpRespEnvelope hre = null;
        try {
            String custId=null;
            custId = companyService.findEntCustIdByEntResNo(resNo);
            AnnualFeeInfo annualfee = annualFeeOrderService.queryAnnualFeeInfo(custId);
            hre = new HttpRespEnvelope(annualfee);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 呼叫中心报表导出
     *
     * @param fileName
     * @param sheetName
     * @param title
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = {"/excelExport"})

    public void excelExport(String type,String fileName, String sheetName, String title, String jsonStr,HttpServletRequest request,HttpServletResponse response) throws IOException{
        if("1".equals(type)){
            title = "日期,签入时间,签出时间,在线时长";
            fileName="签入签出时间统计";
            sheetName=fileName;
        }
        if("2".equals(type)){
            title = "日期,电话号码,呼入/呼出,开始时间,结束时间,通话时长";
            fileName="通话时间统计";
            sheetName=fileName;
        }
        if("3".equals(type)){
            title = "日期,置忙开始时间,置忙结束时间,置忙时长";
            fileName="置忙时间统计";
            sheetName=fileName;
        }
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8") + ".xls");
        OutputStream ouputStream=null;
        try {
            HSSFWorkbook wb = ExcelExportUtil.excelExport(sheetName,title,jsonStr);
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);

        } catch (Exception e) {
            SystemLog.error("CallCenterController", "excelExport", "导出文件失败", e);
            response.setContentType("text/html;charset=UTF-8");
            response.getOutputStream().write("文件导出失败".getBytes());
        } finally{
            try {
                if (null != ouputStream) {
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (IOException e) {
                SystemLog.error("CallCenterController", "excelExport", "订单收款记录异常关闭流异常", e);
                response.setContentType("text/html;charset=UTF-8");
                response.getOutputStream().write("文件导出失败".getBytes());
            }
        }
    }

    /**
     * 查询消费者信息
     *
     * @param resNo
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getPersonAllInfo"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getPersonAllInfo(String resNo, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try {
            String custId = personService.findCustIdByResNo(resNo);
            AsCardHolderAllInfo personAllInfo = personService.searchAllInfo(custId);
            hre = new HttpRespEnvelope(personAllInfo);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查询银行卡号列表
     *
     * @param resNo
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getBankAccountList"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getBankAccountList(String resNo, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try {
            String custId = null;
            List<AsBankAcctInfo> bankAccountList = null;
            if (getResType(resNo).equals("P")) {
                custId = personService.findCustIdByResNo(resNo);
                bankAccountList = iucBankfoService.findBanksByCustId(custId, "2");
            } else {
                custId = companyService.findEntCustIdByEntResNo(resNo);
                bankAccountList = iucBankfoService.findBanksByCustId(custId, "4");
            }

            hre = new HttpRespEnvelope(bankAccountList);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 快捷支付银行卡列表
     *
     * @param resNo
     * @return
     */
    @RequestMapping(value = {"/getQkBankAccountList"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getQkBankAccountList(String resNo) {
        HttpRespEnvelope hre = null;
        try {
            String custId = null;
            List<AsQkBank> bankAccountList = null;
            if (getResType(resNo).equals("P")) {
                custId = personService.findCustIdByResNo(resNo);
                bankAccountList = iucBankfoService.findPayBanksByCustId(custId, "2");
            } else {
                custId = companyService.findEntCustIdByEntResNo(resNo);
                bankAccountList = iucBankfoService.findPayBanksByCustId(custId, "4");
            }

            hre = new HttpRespEnvelope(bankAccountList);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
    /**
     * 查询业务操作许可
     *
     * @param resNo
     *            客户号（持卡人、非持卡人、企业）
     * @param request
     *
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/queryBusinessPmInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findBusinessPmInfo(String resNo, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try {
            String custId = null;
            if (getResType(resNo).equals("P")) {
                custId = personService.findCustIdByResNo(resNo);
            } else {
                custId = companyService.findEntCustIdByEntResNo(resNo);
            }
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[]{custId, RespCode.APS_SMRZSP_ENTCUSTID_NOT_NULL});
            hre = new HttpRespEnvelope();
            BusinessSysBoSetting businessBo = new BusinessSysBoSetting();
            businessBo.setCustId(custId);
            Map<String, BusinessSysBoSetting> map = resourceDirectoryService.findBusinessPmInfo(businessBo);
            Map<String, String> mapInfo = new HashMap<String, String>();
            String buyhsb = BusinessParam.BUY_HSB.getCode();// 兑换互生币
            String hsbtocash = BusinessParam.HSB_TO_CASH.getCode();// 互生币转货币
            String cashtobank = BusinessParam.CASH_TO_BANK.getCode();// 货币转银行
            String pvtohsb = BusinessParam.PV_TO_HSB.getCode(); // 积分转互生币
            String pvinvest = BusinessParam.PV_INVEST.getCode(); // 积分投资

            if (map == null || map.isEmpty()) {
                mapInfo.put(buyhsb, "0");
                mapInfo.put(hsbtocash, "0");
                mapInfo.put(cashtobank, "0");
                mapInfo.put(pvtohsb, "0");
                mapInfo.put(pvinvest, "0");
            } else {
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
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 重要信息变更查询 消费者，企业通用
     *
     * @param resNo
     * @return
     */
    @RequestMapping(value = {"/getImptChangeInfo"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getImptChangeInfo(String resNo) {
        HttpRespEnvelope hre = null;
        try {
            String custId = null;
            if (getResType(resNo).equals("P")) {
                custId = personService.findCustIdByResNo(resNo);
                hre = new HttpRespEnvelope(personService.queryPerChangeByCustId(custId));
            } else {
                custId = companyService.findEntCustIdByEntResNo(resNo);
                hre = new HttpRespEnvelope(companyService.queryEntChangeByCustId(custId));
            }
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }


    /**
     * 账户余额查询--通用
     *
     * @param resNo   资源号
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getAccountBalance"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getAccountBalance(String resNo, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try {
            String custId = null;
            if (getResType(resNo).equals("P")) {
                custId = personService.findCustIdByResNo(resNo);
            } else {
                custId = companyService.findEntCustIdByEntResNo(resNo);
            }
            //账户类型
            String accType[] = {
                    AccountType.ACC_TYPE_POINT10110.getCode(),//积分余额
                    AccountType.ACC_TYPE_POINT20110.getCode(),//流通币
                    AccountType.ACC_TYPE_POINT20120.getCode(),//定向消费
                    AccountType.ACC_TYPE_POINT20130.getCode(),//慈善救助
                    AccountType.ACC_TYPE_POINT30110.getCode(),//货币
                    AccountType.ACC_TYPE_POINT10410.getCode(),//积分投资
                    AccountType.ACC_TYPE_POINT10510.getCode(),//税收-积分
                    AccountType.ACC_TYPE_POINT20610.getCode(),//税收-互生币
                    AccountType.ACC_TYPE_POINT30310.getCode(),//税收-货币
            };
            hre = new HttpRespEnvelope(accountSearchService.searchAccBalanceByCustIdAndAccType(custId, accType));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 企业昨日积分查询
     *
     * @param resNo
     * @return
     */
    @RequestMapping(value = {"/searchEntIntegralByYesterday"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope searchEntIntegralByYesterday(String resNo) {
        HttpRespEnvelope hre = null;
        try {
            String custId = companyService.findEntCustIdByEntResNo(resNo);
            hre = new HttpRespEnvelope(accountSearchService.searchEntIntegralByYesterday(custId, AccountType.ACC_TYPE_POINT10110.getCode()));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 消费者今日积分数查询
     *
     * @param resNo
     * @return
     */
    @RequestMapping(value = {"/searchPerIntegralByToday"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope searchPerIntegralByToday(String resNo) {
        HttpRespEnvelope hre = null;
        try {
            String custId = personService.findCustIdByResNo(resNo);
            hre = new HttpRespEnvelope(accountSearchService.searchPerIntegralByToday(custId, AccountType.ACC_TYPE_POINT10110.getCode()));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 投资余额查询
     *
     * @param resNo
     * @return
     */
    @RequestMapping(value = {"/findInvestDividendInfo"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findInvestDividendInfo(String resNo) {
        HttpRespEnvelope hre = null;
        try {
            hre = new HttpRespEnvelope(accountSearchService.findInvestDividendInfo(resNo, DateUtil.getAssignYear(-1)));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查询积分福利资格
     *
     * @param resNo    资源号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/getWelfareQualify"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getWelfareQualify(String resNo, HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            WelfareQualify record = welfareQualifyService.findWelfareQualify(resNo);
            hre = new HttpRespEnvelope(record);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 积分福利信息查询，包括历史保单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/getWelfareList"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getWelfareList(String search_resNo,HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            // 分页查询
            request.setAttribute("serviceName", welfareQualifyService);
            request.setAttribute("methodName", "queryHisWelfareQualify");
            hre = super.doList(request, response);
//            hre=new HttpRespEnvelope(welfareQualifyService.queryHisWelfareQualify(search_resNo));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 积分福利申请列表查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/getListWelfareApply"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope getListWelfareApply(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            // 分页查询
            request.setAttribute("serviceName", welfareApplyService);
            request.setAttribute("methodName", "listWelfareApply");
            hre = super.doList(request, response);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }


    /**
     * 互生卡补办记录查询
     *
     * @return
     */
    @RequestMapping(value = {"/findCardapplyList"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findCardapplyList(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            // 分页查询
            request.setAttribute("serviceName", personService);
//            request.setAttribute("methodName", "findCardapplyList");findScrollResult
            request.setAttribute("methodName", "findScrollResult");
            hre = super.doList(request, response);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 企业联系信息查询  用于呼叫中心右上角
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/searchEntContactInfo"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope searchEntContactInfo(String resNo, HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            hre = new HttpRespEnvelope(companyService.searchEntContactInfo(resNo));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 企业所有信息查询  用于呼叫中心右上角
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/searchEntAllInfo"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope searchEntAllInfo(String resNo, HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            hre = new HttpRespEnvelope(companyService.searchEntAllInfo(resNo));
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    protected IBaseService<?> getEntityService() {
        return null;
    }

    @RequestMapping("")
    @ResponseBody
    public String testServerConntion() {
        return "The hsxt-access-web-aps server connection is successful";
    }

    @RequestMapping("test")
    public ModelAndView test() {
        return new ModelAndView("/json");
    }

    static String getResType(String resNo) {
        String resType = null;
        if (!resNo.isEmpty()) {
            int str1 = Integer.parseInt(resNo.substring(0, 2));
            int str2 = Integer.parseInt(resNo.substring(2, 5));
            int str3 = Integer.parseInt(resNo.substring(5, 7));
            int str4 = Integer.parseInt(resNo.substring(7, 11));
            if (str1 > 0 && str2 > 0 && str3 > 0 && str4 > 0) {
                resType = "P";
            }
            if (str1 > 0 && str2 > 0 && str3 == 0 && str4 > 0) {
                resType = "B";
            }
            if (str1 > 0 && str2 > 0 && str3 > 0 && str4 == 0) {
                resType = "T";
            }
            if (str1 > 0 && str2 > 0 && str3 == 0 && str4 == 0) {
                resType = "S";
            }
            if (str1 > 0 && str2 == 0 && str3 == 0 && str4 == 0) {
                resType = "M";
            }

        }
        return resType;
    }
}
