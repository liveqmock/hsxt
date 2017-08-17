/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.resourceManage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.knightliao.apollo.utils.common.StringUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;

/***************************************************************************
 * <PRE>
 * Description 		: 资源统计处理类
 * 
 * Project Name   	: hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.controllers.resourceController  
 * 
 * File Name        : ResourceController 
 * 
 * Author           : 韦兴中 
 * 
 * Create Date      : 2016-01-19 下午16:22:16
 * 
 * Update User      : 韦兴中
 * 
 * Update Date      : 2016-01-19 下午5:22:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("resource")
public class ResourceController extends BaseController {

    @Resource
    private IReportsSystemResourceService iReportsSystemResourceService; // 报表服务类

    /**
     * 资源统计查询
     * 
     * @param pointNo
     *            互生号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/resource_unify_search" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope searchResourceUnifyInfo(String pointNo, String entCustId, HttpServletRequest request) {

        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, RespCode.PW_HSRESNO_INVALID.getCode(),
                    RespCode.PW_HSRESNO_INVALID.getDesc() }, // 企业客户号不准为空
                    new Object[] { pointNo, RespCode.PW_HSRESNO_INVALID.getCode(),
                            RespCode.PW_HSRESNO_INVALID.getDesc() } // 互生卡号不准为空
                    );
            ReportsResourceStatistics rpResourceStatistics = new ReportsResourceStatistics();
            rpResourceStatistics.setHsResNo(pointNo);
            List<ReportsResourceStatistics> list = iReportsSystemResourceService
                    .searchResourceStatistics(rpResourceStatistics);

            hre = new HttpRespEnvelope(list);

        }
        catch (HsException e)
        {
            e.printStackTrace();
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 消费者资源查询
     * 
     * @param pointNo
     *            互生号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/consumer_resource_search" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope searchConsumerResourceInfo(String search_hsResNo, HttpServletRequest request) {

        // 变量声明
        HttpRespEnvelope hre = null;

        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String beginCard = request.getParameter("beginCard");
        String endCard = request.getParameter("endCard");

        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { search_hsResNo, RespCode.PW_HSRESNO_INVALID.getCode(),
                    RespCode.PW_HSRESNO_INVALID.getDesc() } // 互生卡号不准为空
                    );

            Page page = new Page(curPage, pageSize);

            ReportsCardholderResource reportsCardholderResource = new ReportsCardholderResource();
            reportsCardholderResource.setEntResNo(search_hsResNo);
            if (!StringUtils.isBlank(beginDate))
            {
                reportsCardholderResource.setRealnameAuthDateBegin(beginDate);
            }
            if (!StringUtils.isBlank(beginDate))
            {
                reportsCardholderResource.setRealnameAuthDateEnd(endDate);
            }
            if (!StringUtils.isBlank(beginCard))
            {
                reportsCardholderResource.setBeginCard(beginCard);
            }
            if (!StringUtils.isBlank(endCard))
            {
                reportsCardholderResource.setEndCard(endCard);
            }
            String consumer = request.getParameter("consumer");
            if (CommonUtils.toInteger(consumer) != null)
            {
                reportsCardholderResource.setRealnameAuth(CommonUtils.toInteger(consumer));
            }
            String cardStatus = request.getParameter("cardStatus");

            if (CommonUtils.toInteger(cardStatus) != null)
            {
                reportsCardholderResource.setBaseStatus(CommonUtils.toInteger(cardStatus));
            }
            PageData<ReportsCardholderResource> pd = iReportsSystemResourceService.searchPerResListPage(
                    reportsCardholderResource, page);

            hre = new HttpRespEnvelope();
            hre.setData(pd.getResult());
            hre.setTotalRows(pd.getCount());
            hre.setCurPage(page.getCurPage());
        }
        catch (HsException e)
        {
            e.printStackTrace();
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * @return
     * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
     */
    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

}
