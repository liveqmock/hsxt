/**
 * 
 */
package com.gy.hsxt.access.web.aps.controllers.cashTransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.bean.TransOutQueryParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 银行转账管理接口
 * @author weiyq
 *
 */
@Controller
@RequestMapping("cashTransfer")
public class CashTransferController extends BaseController<Object> {
	
	@Autowired
	private ICashTransferService cashTransferService;
	
	/**
	 * 银行转账查询的统计功能 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/getTransferRecordListCount"},method={RequestMethod.GET,RequestMethod.POST},produces ="application/json;charset=UTF-8" )
    public HttpRespEnvelope getTransferRecordListCount(HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            verifySecureToken(request);
            TransOutQueryParam param = new TransOutQueryParam();
            param.setStartDate((String) request.getParameter("startDate"));
            param.setEndDate((String) request.getParameter("endDate"));
            param.setHsResNo((String) request.getParameter("hsResNo"));
            param.setTransStatus(CommonUtils.toInteger(request.getParameter("transStatus")));
            hre = new HttpRespEnvelope(cashTransferService.getTransOutListCount(param));
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;

    }
	
	/**
	 * 批量提交转账
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/transBatch"},method={RequestMethod.GET,RequestMethod.POST},produces ="application/json;charset=UTF-8" )
    public HttpRespEnvelope transBatch(HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            verifySecureToken(request);
            String transNos = request.getParameter("transNos");
            List<String> transNoList = JSON.parseArray(URLDecoder.decode(transNos, "UTF-8"), String.class);
            String apprOptId = request.getParameter("apprOptId");
            String apprOptName = request.getParameter("apprOptName");
            String apprRemark = request.getParameter("apprRemark");
            Set<String> transNoSet = new HashSet<String>();
            for (String transNo : transNoList)
            {
                transNoSet.add(transNo);
            }
            cashTransferService.transBatch(transNoSet, 3, apprOptId, apprOptName, apprRemark);
            hre = new HttpRespEnvelope();
        }
        catch (UnsupportedEncodingException | HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
	
	/**
	 * 转账撤单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/transRevoke"},method={RequestMethod.GET,RequestMethod.POST},produces ="application/json;charset=UTF-8" )
    public HttpRespEnvelope transRevoke(HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            verifySecureToken(request);
            String transNos = request.getParameter("transNos");
            String apprOptId = request.getParameter("apprOptId");
            String apprOptName = request.getParameter("apprOptName");
            String de = URLDecoder.decode(transNos, "UTF-8");
            Map<String, String> transNoMap = JSON.parseObject(de, HashMap.class);

            cashTransferService.transRevoke(transNoMap, apprOptId, apprOptName, "");
            hre = new HttpRespEnvelope();
        }
        catch (UnsupportedEncodingException | HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
	
	/**
	 * 查询对账列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/getCheckUpList"},method={RequestMethod.GET,RequestMethod.POST},produces ="application/json;charset=UTF-8" )
    @ResponseBody
	public HttpRespEnvelope getCheckUpList(HttpServletRequest request,HttpServletResponse response){
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", cashTransferService);
			request.setAttribute("methodName", "getCheckUpList");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}
	
	/**
	 * 银行转账对账 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/transCheckUpAccount"},method={RequestMethod.GET,RequestMethod.POST},produces ="application/json;charset=UTF-8" )
    @ResponseBody
	public HttpRespEnvelope transCheckUpAccount(HttpServletRequest request){
		HttpRespEnvelope hre=null;
		try{
			verifySecureToken(request);
			String transNos = request.getParameter("transNos");
			List<String> transNoList= JSON.parseArray(URLDecoder.decode(transNos, "UTF-8"),String.class);
			List<TransOut> list = cashTransferService.transCheckUpAccount(transNoList);
			hre = new HttpRespEnvelope(list);
		} catch (UnsupportedEncodingException | HsException e) {
            hre=new HttpRespEnvelope(e);
        }
		return hre;
	}
	
	/**
	 * 转账失败处理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/transFailBack"},method={RequestMethod.GET,RequestMethod.POST},produces ="application/json;charset=UTF-8" )
    @ResponseBody
    public HttpRespEnvelope transFailBack(HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            verifySecureToken(request);
            String transNos = request.getParameter("transNos");
            List<String> transNoList = JSON.parseArray(URLDecoder.decode(transNos, "UTF-8"), String.class);
            List<TransOut> list = cashTransferService.transFailBack(transNoList);
            hre = new HttpRespEnvelope(list);
        }
        catch (UnsupportedEncodingException | HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
	
	
	/**
     * 银行转账查询导出
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = { "/transfer_record_export" }, method = { RequestMethod.GET })
    public void transferRecordExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        SystemLog.info(this.getClass().getName(), "transferRecordExport", "银行转账查询导出入参：" + JSON.toJSONString(request.getParameterMap()));
       
        //清除缓冲数据
        response.reset();
        
        try
        {
            // 获取查询条件
            Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");

            // 获取下载文件路径
            String dowloadPath = cashTransferService.transferRecordExportPath(filterMap); //"D:\\11给开发测试环境登录信息20160524.xls";
            SystemLog.info(this.getClass().getName(), "transferRecordExport", "获取文件地址：" + dowloadPath);

            // 初始化文件
            File f = new File(dowloadPath);
            if (!f.exists())
            {
                SystemLog.info(this.getClass().getName(), "transferRecordExport", "文件不存在");

                response.setContentType("text/html");
                response.getOutputStream().write("Download file does not exist".getBytes());
                return;
            }

            try (BufferedInputStream br = new BufferedInputStream(new FileInputStream(f)); OutputStream out = response.getOutputStream();)//自动资源管理
            {
                // 设置输出文件类型
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(f.getName(), "UTF-8"));
                
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = br.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "transferRecordExport", "导出文件错误", e);

                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html");
                if (e instanceof HsException) {
                    response.getOutputStream().write(e.getMessage().getBytes());
                } else  {
                    response.getOutputStream().write("Download file failed".getBytes());
                }
            }
        } catch (IOException e) {
            SystemLog.error(this.getClass().getName(), "transferRecordExport", "导出文件错误", e);

            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            response.getOutputStream().write("Download file failed".getBytes());
        }

    }
	
	@Override
	protected IBaseService getEntityService() {
		return cashTransferService;
	}

}
