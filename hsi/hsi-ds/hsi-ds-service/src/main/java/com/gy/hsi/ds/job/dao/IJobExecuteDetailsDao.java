/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.dao;

import java.text.ParseException;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.job.beans.bo.JobExecuteDetails;
import com.gy.hsi.ds.job.controller.form.DetailListForm;
/**
 * 
 * 业务执行情况详情表DAO接口
 * @Package: com.gy.hsi.ds.job.web.service.dao  
 * @ClassName: FrontPostJobDao 
 * @Description: TODO
 *
 * @author: yangyp  
 * @date: 2015年10月9日 下午12:01:48 
 * @version V3.0
 */
public interface IJobExecuteDetailsDao extends BaseDao<Long, JobExecuteDetails> {

	/**
	 * 根据条件查询业务执行情况详表
	 * 
	 * @param detailListForm
	 * @return
	 */
	DaoPageResult<JobExecuteDetails> getDetailList(DetailListForm detailListForm)
			throws ParseException;

	/**
	 * 获取错误详情
	 * 
	 * @param executeId
	 * @return
	 */
	String getDetailErrDesc(String executeId);
	
	/**
	 * 获取错误详情
	 * 
	 * @param executeId
	 * @return
	 */
	JobExecuteDetails getDetailByExecuteId(String executeId);
	
	void deleteDetailDataByDate(int daysBeforeToday);
}
