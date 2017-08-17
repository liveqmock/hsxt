package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.ISpecialCardService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.toolorder
 * @className     : SpecialCardController.java
 * @description   : 个性卡定制服务
 * @author        : maocy
 * @createDate    : 2016-03-03
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("specialCardController")
public class SpecialCardController extends BaseController {

    @Resource
    private ISpecialCardService specialCardService;
    
    /**
     * 
     * 方法名称：根据订单号查询互生卡样
     * 方法描述：工具制作管理-根据订单号查询互生卡样
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findCardStyleByOrderNo")
    public HttpRespEnvelope findCardStyleByOrderNo(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String orderNo = request.getParameter("orderNo");
            if(StringUtils.isBlank(orderNo)){
				throw new HsException(ASRespCode.APS_ORDERNO_NOT_NULL);
			}
            CardStyle obj = this.specialCardService.queryCardStyleByOrderNo(orderNo);
            if(obj == null){
				throw new HsException(ASRespCode.AS_DETAIL_NOT_FOUND);
			}
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：上传卡样制作文件
     * 方法描述：工具制作管理-上传卡样制作文件
     * @param request HttpServletRequest对象
     * @param bean 卡样对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadCardStyleMarkFile")
    public HttpRespEnvelope uploadCardStyleMarkFile(HttpServletRequest request, @ModelAttribute CardStyle bean) {
        try {
            super.verifySecureToken(request);
            String custName = request.getParameter("custName");//获取登陆用户名称
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getReqRemark(), 0, 300, ASRespCode.APS_WH_REMARK_LENGTH_INVALID}
            );
            bean.setReqOperator(custName);
            this.specialCardService.uploadCardStyleMarkFile(bean);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
	@Override
	protected IBaseService getEntityService() {
		return this.specialCardService;
	}

}
