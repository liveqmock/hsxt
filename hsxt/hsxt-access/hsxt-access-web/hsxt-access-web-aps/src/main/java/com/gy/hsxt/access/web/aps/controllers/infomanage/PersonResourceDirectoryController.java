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
import java.net.URLEncoder;
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
import com.gy.hsxt.common.exception.HsException;

/**
 * 消费者一览列表查询
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.customermanager
 * @ClassName: ResourceDirectoryController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-12-8 下午2:58:32
 * @version V3.0.0
 */

@Controller
@RequestMapping("person_resour_cedirect")
public class PersonResourceDirectoryController extends BaseController {

    @Resource
    private ResourceDirectoryService resourceDirectoryService;

    @Resource
    private EntInfoService entInfoService;

    @Resource
    private IUCBankfoService ucBankService;// 银行信息服务类

    @Resource
    private ICompanyInfoService companyService;// 企业信息服务类

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
    public HttpRespEnvelope listConsumerInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {

            // Token验证
            super.verifySecureToken(request);

            // 分页查询
            request.setAttribute("serviceName", resourceDirectoryService);
            request.setAttribute("methodName", "listConsumerInfo");
            hre = super.doList(request, response);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 消费者资源一览下载
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = { "/person_resource_dowload" }, method = { RequestMethod.GET })
    public void personResourceDowload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedInputStream br = null;
        OutputStream out = null;

        // 设置输出文件类型
        response.reset();
        response.setContentType("application/x-msdownload");

        try
        {
            SystemLog.info(this.getClass().getName(), "personResourceDowload", "消费者导出入参："
                    + JSON.toJSONString(request.getParameterMap()));

            // 获取查询条件
            Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");

            // 获取下载文件路径
            String dowloadPath = resourceDirectoryService.personResourceDowloadPath(filterMap);
            SystemLog.info(this.getClass().getName(), "personResourceDowload", "获取文件成功，文件地址：" + dowloadPath);

            // 初始化文件
            File f = new File(dowloadPath);
            if (!f.exists())
            {
                SystemLog.info(this.getClass().getName(), "personResourceDowload", "文件不存在");

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
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "personResourceDowload", "导出文件错误", e);

            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            if (e instanceof HsException){
                response.getOutputStream().write(e.getMessage().getBytes());
            }
            else{
                response.getOutputStream().write("Download file failed".getBytes());
            }
        }
        finally {
            if (null != out) {
                try {
                    out.close();
                } catch (Exception e) {
                    SystemLog.error(this.getClass().getName(), "personResourceDowload", "关闭OutputStream对象异常", e);

                    response.reset();
                    response.setContentType("text/html");
                    response.getOutputStream().write("Download file failed".getBytes());
                }
            }

            try {
                if (null != br){
                    br.close();
                }
            } catch (Exception e) {
                SystemLog.error(this.getClass().getName(), "personResourceDowload", "关闭BufferedInputStream对象异常", e);
                response.setCharacterEncoding("utf-8");
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
