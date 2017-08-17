/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.toolorder.HSCardToolApplyService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具制作管理-刷卡工具制作
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder  
 * @ClassName: HSCardToolApplyController 
 * @Description: TODO
 *
 * @author: lyh 
 * @date: 2016-06-28 下午2:23:02 
 * @version V3.0.0
 */
@Controller
@RequestMapping("hscardtoolapply")
public class HSCardToolApplyController extends BaseController {

    @Resource
    private HSCardToolApplyService hSCardToolApplyService;
    
    
	/**
	 * 查询分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryCardProvideApplyByPage" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryCardProvideApplyByPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", hSCardToolApplyService);
			request.setAttribute("methodName", "findScrollResult");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}


	@Override
	protected IBaseService getEntityService() {
		return hSCardToolApplyService;
	}
    
    

}
