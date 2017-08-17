package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.codeclaration.IIntentionCustService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : IntentionCustController.java
 * @description   : 意向客户查询
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("intentionCustController")
public class IntentionCustController extends BaseController{
	
	@Resource
    private IIntentionCustService intentionCustService; //意向客户服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			RequestUtil.verifyQueryDate((String) filterMap.get("startDate"), (String) filterMap.get("endDate"));//校验时间
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
	 * 
	 * 方法名称：查看客户申请资料
	 * 方法描述：依据客户申请编号查看客户申请资料
	 * @param request HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/findIntentCustById")
	protected HttpRespEnvelope findIntentCustById(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");//申请编号
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_INTENTION_CUST_APPLYID_INVALID}
	        );
			IntentCust cust = this.intentionCustService.findIntentCustById(applyId);
			if(cust == null){
				throw new HsException(RespCode.APS_INTENTION_CUST_INVALID);
			}
			return new HttpRespEnvelope(cust);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return intentionCustService;
	}

}
