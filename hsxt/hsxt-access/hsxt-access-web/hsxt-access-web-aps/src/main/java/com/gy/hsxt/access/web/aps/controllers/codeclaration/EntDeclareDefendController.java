package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.codeclaration.IEntDeclareDefendService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.DeclareIncrement;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : EntDeclareDefendController.java
 * @description   : 申报信息维护
 * @author        : maocy
 * @createDate    : 2015-12-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareDefendController")
public class EntDeclareDefendController extends BaseController{
	
	@Resource
    private IEntDeclareDefendService entDeclareDefendService; //申报信息维护服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			RequestUtil.verifyQueryDate(request.getParameter("search_startDate"), request.getParameter("search_endDate"));//校验时间
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
     * 
     * 方法名称：保存申报增值点信息
     * 方法描述：申报信息维护-保存申报增值点信息
     * @param request HttpServletRequest对象
     * @param info 积分增值信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveIncrement")
    public HttpRespEnvelope saveIncrement(HttpServletRequest request, @ModelAttribute DeclareIncrement info) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            String optCustId = request.getParameter("custId");//获取登陆客户号
            String optName = request.getParameter("cookieOperNoName");//获取登陆客户名称
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
                new Object[] {info.getToPnodeCustId(), RespCode.APS_PNODE_CUSTID_INVALID},
                new Object[] {info.getToPnodeResNo(), RespCode.APS_PNODE_RESNO_INVALID},
                new Object[] {info.getToInodeResNo(), RespCode.APS_INODE_RESON_INVALID},
                new Object[] {info.getToInodeLorR(), RespCode.APS_INODE_LORR_INVALID},
                new Object[] {info.getDblOptCustId(), RespCode.AS_DOULBE_USERID_INVALID}
            );
            info.setOptCustId(optCustId);
            info.setOptName(optName);
            info.setOptEntName(optEntName);
            this.entDeclareDefendService.saveIncrement(info);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
	
	@Override
	protected IBaseService getEntityService() {
		return entDeclareDefendService;
	}

}
