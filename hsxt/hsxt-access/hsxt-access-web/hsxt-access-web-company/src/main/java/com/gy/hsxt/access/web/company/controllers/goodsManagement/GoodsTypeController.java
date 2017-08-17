/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.controllers.goodsManagement;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.goodsManage.GoodsType;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.company.services.goodsManagement.IGoodsTypeService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 商品管理中商品分类管理的control
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.goodsManagement  
 * @ClassName: GoodsTypeController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-13 上午10:13:37 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes") 
@Controller
@RequestMapping("goodsmanage")
public class GoodsTypeController extends BaseController {

    @Resource
    private IGoodsTypeService igoodsTypeService;
    
    /**
     * 查询商家所有商品分类
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_goodstype_tree" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryGoodsType(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String custId = request.getParameter("custId");
            try
            {
                List<GoodsType> result = igoodsTypeService.findAllGoodsType(custId);
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
     * 根据条件查询商品分类关联商品
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_spfl_goods" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryGoods(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String custId = request.getParameter("custId");
            try
            {
                List<GoodsType> result = igoodsTypeService.findAllGoodsType(custId);
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
    protected IBaseService getEntityService() {
        return igoodsTypeService;
    }

}
