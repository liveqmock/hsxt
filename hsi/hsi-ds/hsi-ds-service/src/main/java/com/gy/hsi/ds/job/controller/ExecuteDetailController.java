/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.constraint.validation.PageOrderValidator;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.job.beans.vo.ExecuteDetailVo;
import com.gy.hsi.ds.job.controller.form.DetailListForm;
import com.gy.hsi.ds.job.dao.IJobExecuteDetailsDao;
import com.gy.hsi.ds.job.service.IExecuteDetailMgr;

/**
 * 业务执行详情Controller类
 * 
 * @Package: com.gy.hsi.ds.job.web.web.detail.controller  
 * @ClassName: ExecuteDetailController 
 * @Description: none
 *
 * @author: yangyp 
 * @date: 2015年10月20日 上午11:00:50 
 * @version V3.0
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/detail")
public class ExecuteDetailController extends BaseController {
	
    protected static final Logger LOG = LoggerFactory.getLogger(ExecuteDetailController.class);
    
    @Autowired
    private IExecuteDetailMgr executeDetailMgr;
    
	@Autowired
	private IJobExecuteDetailsDao jobExecuteDetailsDao;
    
    /**
     * 获取列表,有分页的
     * 
     * @param businessListForm
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonObjectBase getDetailList(@Valid DetailListForm detailListForm) {
    	
    	Page page = new Page();
    	page.setPageNo(1);
    	page.setPageSize(StringUtils.str2Int(detailListForm.getMaxCounts()));
    	
    	detailListForm.setPage(page);

        // 设置排序方式
        // detailListForm.getPage().setOrderBy(Columns.EXECUTE_STATUS_TIME);
    	detailListForm.getPage().addOrderBy(Columns.DETAILS_ID);
    	detailListForm.getPage().addOrderBy(Columns.EXECUTE_ID);
    	detailListForm.getPage().addOrderBy(Columns.SERVICE_NAME);
		// detailListForm.getPage().addOrderBy(Columns.EXECUTE_STATUS_TIME);
        detailListForm.getPage().setOrder(PageOrderValidator.DESC);
        DaoPageResult<ExecuteDetailVo> details = executeDetailMgr.getDetailList(detailListForm);

        return buildListSuccess(details);
    }
    
	@RequestMapping(value = "/get_detail_err_desc/{executeId}", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonObjectBase getDetailErrDesc(@PathVariable String executeId) {
		
		// 为了规避ajax无法传递#井号的问题
		executeId = executeId.replace("@@", ".").replace("@", "#");

		String desc = jobExecuteDetailsDao.getDetailErrDesc(executeId);

		return buildSuccess(desc);
	}
}
