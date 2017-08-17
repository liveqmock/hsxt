/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.constant.JobConstant.TempArgsKey;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.constraint.validation.PageOrderValidator;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.utils.ValuesUtils;
import com.gy.hsi.ds.job.beans.bo.JobTempArgs;
import com.gy.hsi.ds.job.beans.vo.BusinessVo;
import com.gy.hsi.ds.job.controller.form.BusinessListForm;
import com.gy.hsi.ds.job.controller.form.BusinessNewForm;
import com.gy.hsi.ds.job.controller.validator.BusinessValidator;
import com.gy.hsi.ds.job.dao.IJobTempArgsDao;
import com.gy.hsi.ds.job.service.IBusinessMgr;
/**
 * 参数配置Controller类
 * 
 * @Package: com.gy.hsi.ds.job.web.web.business.controller  
 * @ClassName: BusinessController 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月12日 下午12:12:40 
 * @version V3.0
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/bus")
public class BusinessController extends BaseController {
    
    protected static final Logger LOG = LoggerFactory.getLogger(BusinessController.class);
    
    @Autowired
    private IBusinessMgr businessMgr;
    
    @Autowired
    private BusinessValidator businessValidator;
    
    @Autowired
    private IJobTempArgsDao jobTempArgsDao;
    
    /**
     * 创建业务
     * 
     * @param businessNewForm
     * @return
     */
    @RequestMapping(value = "")
    @ResponseBody
    public JsonObjectBase create(@Valid BusinessNewForm businessNewForm) {

        businessValidator.validateCreate(businessNewForm);

        businessMgr.create(businessNewForm);

        return buildSuccess("创建成功");
    }
    
    
    /**
     * 获取列表,有分页的
     * 
     * @param businessListForm
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
	public JsonObjectBase getBusList(@Valid BusinessListForm businessListForm) {

		// 设置排序方式
		businessListForm.getPage().setOrderBy(Columns.SERVICE_GROUP);
		businessListForm.getPage().setOrder(PageOrderValidator.ASC);

		DaoPageResult<BusinessVo> jobs = businessMgr
				.getBusinessList(businessListForm);

		return buildListSuccess(jobs);
	}
    
    /**
     * 删除指定id业务
     * 
     * @param busId
     * @return
     */
    @RequestMapping(value = "/delete/{busId}")
    @ResponseBody
    public JsonObjectBase delete(@PathVariable long busId) {

        businessMgr.delete(busId);

        return buildSuccess("删除成功");
    }
    
    /**
     * 更新指定id业务
     * 
     * @param busId
     * @param servicePara
     * @param desc
     * @return
     */
    @RequestMapping(value = "/update/{busId}")
    @ResponseBody
    public JsonObjectBase updateBus(@PathVariable long busId,String servicePara,String desc,String version) {
        businessValidator.valideBusExist(busId);
        //
        // 更新, 并写入数据库
        //
        String result = businessMgr.update(busId,servicePara,desc,version);

        return buildSuccess(result);
    }
    
    /**
     * 设置后置业务
     * 
     * @param serviceName
     * @param serviceGroup
     * @param postId
     * @return
     */
    @RequestMapping(value = "/setPost")
    @ResponseBody
    public JsonObjectBase setPost(String serviceName,String serviceGroup,long postId) {
        String result=businessMgr.setPostBus(serviceName,serviceGroup,postId);
        return buildSuccess(result);
    }
    
  
    /**
     * 设置前置业务
     * 
     * @param serviceName
     * @param serviceGroup
     * @param postId
     * @return
     */
    @RequestMapping(value = "/setFront")
    @ResponseBody
    public JsonObjectBase setFront(String serviceName,String serviceGroup,String frontIds) {
        String result=businessMgr.setFrontBus(serviceName,serviceGroup,frontIds);
        return buildSuccess(result);
    }
    
    /**
     * 设置前置业务
     * 
     * @param serviceName
     * @param serviceGroup
     * @param tempArgs 临时参数传递(键值对方式)
     * @return
     */
	@RequestMapping(value = "/executeOnce")
	@ResponseBody
	public JsonObjectBase executeOnce(String serviceName, String serviceGroup,
			String tempArgs) {
		String result = businessMgr.executeOnce(serviceName, serviceGroup,
				tempArgs);

		return buildSuccess(result);
	}
	
    /**
     * 获取历史输入的临时参数
     * 
     * @param serviceName
     * @return
     */
	@RequestMapping(value = "/getHistoryTempArgs")
	@ResponseBody
	public JsonObjectBase getHistoryTempArgs(String serviceName) {
		try {
			// 解决get方式中文乱码问题
			byte[] bytes = serviceName.getBytes("ISO-8859-1");

			if (serviceName.equals(new String(bytes, "iso8859-1"))) {
				serviceName = new String(bytes, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}

		JobTempArgs result = jobTempArgsDao
				.getManualTempArgsByServiceName(serviceName);

		if ((null == result) || StringUtils.isEmpty(result.getTempArgs())
				|| StringUtils.isEmpty(result.getTempArgsKeys())) {
			String args = TempArgsKey.KEY_DS_BATCH_DATE + "=;";

			result = new JobTempArgs();
			result.setServiceName(serviceName);
			result.setTempArgs(args);
			result.setTempArgsKeys(args);
		} else {
			String[] ignoreKeys = new String[] { TempArgsKey.KEY_IGNORE_FRONT,
					TempArgsKey.KEY_IS_MANUAL, TempArgsKey.KEY_RECUR_POST };

			String tempArgsKeyValues = ValuesUtils.filterParams(
					result.getTempArgs(), ignoreKeys);
			
			String tempArgsKeys = ValuesUtils.filterParams(
					result.getTempArgsKeys(), ignoreKeys);

			result.setTempArgs(tempArgsKeyValues);
			result.setTempArgsKeys(tempArgsKeys);
		}

		return buildSuccess(result);
	}
	
    /**
     * 获取某个
     *  
     * @param busId
     * @return
     */
    @RequestMapping(value = "/get/{busId}")
    @ResponseBody
    public JsonObjectBase getBusiness(@PathVariable long busId) {

        // 业务校验
        businessValidator.valideBusExist(busId);

        BusinessVo bus = businessMgr.getBusVo(busId);

        return buildSuccess(bus);
    }
}
