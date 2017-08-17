/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.gy.hsxt.access.web.aps.services.toolorder.SwipeCardToolService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolRelationDetail;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AsConstants;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 工具制作管理-刷卡工具制作
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder  
 * @ClassName: SwipeCardToolController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-18 下午2:23:02 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("swipecard")
public class SwipeCardToolController extends BaseController {

    @Resource
    private SwipeCardToolService swipeCardToolService;
    
    /**
     * 查看终端编号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/terminallist" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope toolTerminalList(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                //配置单号
                String confNo = request.getParameter("confNo");
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(), RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc()}
                        
                );
                //将查询参数封装到map中
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("confNo", confNo);
                
                // 获取客户端传递当前页码
                String curPage = (String) request.getParameter("curPage");
                
                Integer no = null; // 当前页码
                
                // 设置当前页默认第一页
                if (StringUtils.isBlank(curPage))
                {
                    no = AsConstants.PAGE_NO;
                }
                else
                {
                    no = Integer.parseInt(curPage);
                }

                
                List<ToolRelationDetail> list = swipeCardToolService.findToolRelationList(map);
                PageData<ToolRelationDetail> result = new PageData<ToolRelationDetail>(list.size(),list);
                httpRespEnvelope = new HttpRespEnvelope();
                httpRespEnvelope.setCurPage(no);
                httpRespEnvelope.setData(result.getResult());
                httpRespEnvelope.setTotalRows(result.getCount());
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     *单个 添加关联之前的验证
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/addrelation" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addRelation(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        ToolRelationDetail toolRelationDetail=null;
        List<ToolRelationDetail> listDetail=null;
        if (httpRespEnvelope == null)
        {
            try
            { 
                Map<String,Object> param = new HashMap<String,Object>();
                //操作人
                String operNo = request.getParameter("operNo");
                
                //客户号
                String entCustId = request.getParameter("apsCustId");
                
                //配置单号
                String confNo = request.getParameter("confNo");
                
                //设备序列号
                String deviceSeqNo = request.getParameter("deviceSeqNo");
                
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                      new Object[] {confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(), RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc()},
                      new Object[] {operNo, RespCode.APS_SKGJZZ_GLXLH_OPERNO.getCode(), RespCode.APS_SKGJZZ_GLXLH_OPERNO.getDesc()},
                      new Object[] {entCustId, RespCode.APS_SKGJZZ_GLXLH_ENTCUSTID.getCode(), RespCode.APS_SKGJZZ_GLXLH_ENTCUSTID.getDesc()},
                      new Object[] {deviceSeqNo, RespCode.APS_SKGJZZ_GLXLH_SEQNO.getCode(), RespCode.APS_SKGJZZ_GLXLH_SEQNO.getDesc()}
                        
                );
                param.put("operNo", operNo);
                param.put("entCustId", entCustId);
                param.put("confNo", confNo);
                param.put("deviceSeqNo", deviceSeqNo);
                listDetail=new ArrayList<ToolRelationDetail>();
                toolRelationDetail = swipeCardToolService.addToolRelation(param);
                if(toolRelationDetail!=null){
                	listDetail.add(toolRelationDetail);
                }
                httpRespEnvelope = new HttpRespEnvelope(listDetail);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    
    
    /**
     * 批量添加关联
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/addbatchrelation" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addbatchrelation(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                Map<String,Object> param = new HashMap<String,Object>();
                //操作人
                String operNo = request.getParameter("operNo");
                
                //客户号
                String entCustId = request.getParameter("apsCustId");
                
                //配置单号
                String confNo = request.getParameter("confNo");
                
                //设备序列号
                String deviceSeqNo = request.getParameter("deviceSeqNo");
              //  ["15630000000003","15630000000004"]
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                      new Object[] {confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(), RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc()},
                      new Object[] {operNo, RespCode.APS_SKGJZZ_GLXLH_OPERNO.getCode(), RespCode.APS_SKGJZZ_GLXLH_OPERNO.getDesc()},
                      new Object[] {entCustId, RespCode.APS_SKGJZZ_GLXLH_ENTCUSTID.getCode(), RespCode.APS_SKGJZZ_GLXLH_ENTCUSTID.getDesc()},
                      new Object[] {deviceSeqNo, RespCode.APS_SKGJZZ_GLXLH_SEQNO.getCode(), RespCode.APS_SKGJZZ_GLXLH_SEQNO.getDesc()}
                        
                );
                param.put("operNo", operNo);
                param.put("entCustId", entCustId);
                param.put("confNo", confNo);
                JSONArray json = JSONArray.fromObject(deviceSeqNo);
                @SuppressWarnings("unchecked")
				List<String> list = (List<String>)JSONArray.toCollection(json, String.class);   
                swipeCardToolService.addToolBatchRelation(param, list);
              
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
     * 查看序列号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryDeviceRelevanceDetail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryDeviceRelevanceDetail(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                //配置单号
                String confNo = request.getParameter("confNo");
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {confNo, RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getCode(), RespCode.APS_SKGJZZ_CKZDBH_CONFNO.getDesc()}
                        
                );
                //将查询参数封装到map中
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("confNo", confNo);
                
                // 获取客户端传递当前页码
                String curPage = (String) request.getParameter("curPage");
                
                Integer no = null; // 当前页码
                
                // 设置当前页默认第一页
                if (StringUtils.isBlank(curPage))
                {
                    no = AsConstants.PAGE_NO;
                }
                else
                {
                    no = Integer.parseInt(curPage);
                }

                
                List<ToolRelationDetail> list = swipeCardToolService.queryDeviceRelevanceDetail(map);
                PageData<ToolRelationDetail> result = new PageData<ToolRelationDetail>(list.size(),list);
                httpRespEnvelope = new HttpRespEnvelope();
                httpRespEnvelope.setCurPage(no);
                httpRespEnvelope.setData(result.getResult());
                httpRespEnvelope.setTotalRows(result.getCount());
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 查看终端编号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryServiceToolConfigPage" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryServiceToolConfigPage(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                //配置单号
                String custName = request.getParameter("search_custName");
                String startDate = request.getParameter("search_startDate");
                String endDate = request.getParameter("search_endDate");
                String hsResNo = request.getParameter("search_hsResNo");
                String hsCustName = request.getParameter("search_hsCustName");
                String type = request.getParameter("search_type");
                //将查询参数封装到map中
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("custName", custName);
                map.put("startDate", startDate);
                map.put("endDate", endDate);
                map.put("hsResNo", hsResNo);
                map.put("hsCustName", hsCustName);
                map.put("type", type);
                // 获取客户端传递当前页码
                String curPage = (String) request.getParameter("curPage");
                String pageSize = request.getParameter("pageSize");
                Integer no = null; // 当前页码
                Integer size=null;//每页显示条数
                // 设置当前页默认第一页
                if (StringUtils.isBlank(curPage))
                {
                    no = AsConstants.PAGE_NO;
                }
                else
                {
                    no = Integer.parseInt(curPage);
                }
                if (StringUtils.isBlank(pageSize)) {
        			size = Integer.valueOf(10);
        		} else {
        			size = Integer.valueOf(Integer.parseInt(pageSize));
        		}
                Page 	page = new Page(no.intValue(), size.intValue());
                
                PageData<ToolConfigPage> result = swipeCardToolService.queryServiceToolConfigPage(map,page);
                httpRespEnvelope = new HttpRespEnvelope();
                httpRespEnvelope.setCurPage(no);
                httpRespEnvelope.setData(result.getResult());
                httpRespEnvelope.setTotalRows(result.getCount());
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @Override
    protected IBaseService getEntityService() {
        return swipeCardToolService;
    }

}
