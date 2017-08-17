/**
 * 
 */
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.aps.services.toolorder.IRecylePointToolsService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ImportAfterService;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("recylePointTools")
public class RecylePointToolsController extends BaseController{
	
	@Autowired
	private IRecylePointToolsService recylePointToolsService;
	
	@ResponseBody
    @RequestMapping(value = { "/queryEntDeviceList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryEntDeviceList(HttpServletRequest request, HttpServletResponse response){
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", recylePointToolsService);
			request.setAttribute("methodName", "queryEntDeviceInfoPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}
	
	@ResponseBody
    @RequestMapping(value = { "/uploadSeqFile" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=UTF-8")
    public HttpRespEnvelope uploadSeqFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpRespEnvelope hre = null;
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			verifySecureToken(request);
			response.setContentType("text/html;charset=UTF-8");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("seqFile");
			String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
            if (!fileType.toLowerCase().equals("txt")) {
            	throw new HsException(ASRespCode.APS_TOOL_UPLOAD_SEQFILE_NOT_TXT);
            }
            List<String> list = new LinkedList<String>();
            
			InputStream in = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s;
			while((s=br.readLine()) != null){
				s = s.trim();
				if(!s.equals("")){
					list.add(s);
				}
			}
			List<ImportAfterService> retList = recylePointToolsService.validBatchImportSeqNo(list);
			 hre=new HttpRespEnvelope(retList);
        	
		}catch(HsException hse){
			 hre=new HttpRespEnvelope(hse);
		}catch(Exception e){
			 hre=new HttpRespEnvelope(e);
		}
		response.getWriter().write(JSON.toJSONString(hre));
		response.getWriter().flush();
		return null;
	}
	
	@ResponseBody
    @RequestMapping(value = { "/batchUpload" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope batchUpload(HttpServletRequest request){
		try
		{
			verifySecureToken(request);
//			String msgId = request.getParameter("msgId");
//            List<String> msgIds= JSON.parseArray(URLDecoder.decode(msgId, "UTF-8"),String.class);
             
			String devices = request.getParameter("devices");
            List<String> deviceList= JSON.parseArray(URLDecoder.decode(devices, "UTF-8"),String.class);
			recylePointToolsService.batchImportAfterService(deviceList);
			return new HttpRespEnvelope();
		}catch(Exception e){
			return new HttpRespEnvelope(e);
		}
	}
	
	@ResponseBody
    @RequestMapping(value = { "/addAfterPaidOrder" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addAfterService(HttpServletRequest request,String custIdEnt, String targetEntResNo,String entCustName,String reqRemark,String reqOperator,String listDetail){
		try
		{
			verifySecureToken(request);
			recylePointToolsService.addAfterService( custIdEnt, targetEntResNo, entCustName, reqRemark, reqOperator, listDetail);
			return new HttpRespEnvelope();
		}
		catch(Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		// TODO Auto-generated method stub
		return null;
	}
}
