/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.constraint.validation.PageOrderValidator;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectUtils;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.job.beans.vo.QrtzTriggerVo;
import com.gy.hsi.ds.job.controller.form.TriggerListForm;
import com.gy.hsi.ds.job.controller.form.TriggerNewForm;
import com.gy.hsi.ds.job.controller.validator.QrtzTriggerValidator;
import com.gy.hsi.ds.job.service.IQrtzTriggerMgr;

@Controller
@RequestMapping(WebConstants.API_PREFIX + "/trigger")
public class QrtzTriggerController extends BaseController {
    
    protected static final Logger LOG = LoggerFactory.getLogger(QrtzTriggerController.class);
    
    @Autowired
    private IQrtzTriggerMgr qrtzTriggerMgr;
    
    @Autowired
    private QrtzTriggerValidator qrtzTriggerValidator;
    
    /**
     * 获取列表,有分页的
     * @param businessListForm
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public JsonObjectBase getTriggerList(@Valid TriggerListForm triggerListForm) {

        // 设置排序方式
        triggerListForm.getPage().setOrderBy(Columns.TRIGGER_GROUP);
        triggerListForm.getPage().setOrder(PageOrderValidator.ASC);

        DaoPageResult<QrtzTriggerVo> triggers = qrtzTriggerMgr.getTriggerList(triggerListForm);

        return buildListSuccess(triggers);
    }
    
    
    /**
     * 创建任务
     * @param TriggerNewForm
     * @return
     */
    @RequestMapping(value = "")
    @ResponseBody
    public JsonObjectBase create(@Valid TriggerNewForm triggerNewForm) {
        qrtzTriggerValidator.validatorCreate(triggerNewForm);
        String result=qrtzTriggerMgr.create(triggerNewForm);
        return buildSuccess(result);
    }
    
    
    /**
     *删除指定定时任务
     * @param busId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JsonObjectBase delete(String triggerName,String triggerGroup) {
        qrtzTriggerValidator.validatorNotExist(triggerName, triggerGroup);
        String result=qrtzTriggerMgr.delete(triggerName, triggerGroup);
        return buildSuccess(result);
    }
    
    /**
     * 更新指定定时任务
     * @param busId
     * @param servicePara
     * @param desc
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public JsonObjectBase update(@Valid TriggerNewForm triggerNewForm) {
        qrtzTriggerValidator.validatorUpdate(triggerNewForm);
        String result=qrtzTriggerMgr.update(triggerNewForm);
        return buildSuccess(result);
    }
    
    /**
     * 恢复指定定时任务
     * 
     * @param busId
     * @return
     */
    @RequestMapping(value = "/resume")
    @ResponseBody
    public JsonObjectBase resume(String triggerName,String triggerGroup) {
    	
        qrtzTriggerValidator.validatorNotExist(triggerName, triggerGroup);
        
        String result=qrtzTriggerMgr.resume(triggerName, triggerGroup);
        
        return buildSuccess(result);
    }
    
    
    /**
     *获取单个定时任务
     * @param busId
     * @return
     */
    @RequestMapping(value = "/get")
    @ResponseBody
	public JsonObjectBase get(String triggerName, String triggerGroup,
			HttpServletRequest request) {

		try {
			// 解决get方式中文乱码问题
			byte[] bytes = triggerName.getBytes("ISO-8859-1");

			if (triggerName.equals(new String(bytes, "iso8859-1"))) {
				triggerName = new String(bytes, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}

		qrtzTriggerValidator.validatorNotExist(triggerName, triggerGroup);
		
		QrtzTriggerVo qrtzTriggerVo = qrtzTriggerMgr.get(triggerName,
				triggerGroup);

		return buildSuccess(qrtzTriggerVo);
	}
    
    /**
     *暂停指定定时任务
     * @param busId
     * @return
     */
    @RequestMapping(value = "/pause")
    @ResponseBody
    public JsonObjectBase pause(String triggerName,String triggerGroup) {
        qrtzTriggerValidator.validatorNotExist(triggerName, triggerGroup);
        String result=qrtzTriggerMgr.pause(triggerName, triggerGroup);
        return buildSuccess(result);
    }
    
	/**
	 * 获取下一次执行时间列表
	 * 
	 * @param cronExpression
	 * @return
	 */
	@RequestMapping(value = "/listNextFireTimes")
	@ResponseBody
	public JsonObjectBase getTriggerList(String cronExpression) {
		List<String> nextTriggerDateList = new ArrayList<String>();

		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();

		try {
			cronTriggerImpl.setCronExpression(cronExpression);
		} catch (ParseException e) {
		}

		List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null,
				10);

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		if (null != dates) {
			for (Date d : dates) {
				nextTriggerDateList.add(dateFormat.format(d));
			}
		}

		return JsonObjectUtils.buildSimpleObjectSuccess(nextTriggerDateList);
	}
}
