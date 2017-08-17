/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.service;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.job.beans.vo.ExecuteDetailVo;
import com.gy.hsi.ds.job.controller.form.DetailListForm;

/**
 * 业务执行详情业务实现接口
 * 
 * @Package: com.gy.hsi.ds.job.web.service.detail.service  
 * @ClassName: ExecuteDetailMgr 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月20日 上午10:59:45 
 * @version V3.0
 */
public interface IExecuteDetailMgr {
    
    /**
     * 获取满足条件的执行情况列表
     * @param detailListForm
     * @return
     */
    DaoPageResult<ExecuteDetailVo> getDetailList(DetailListForm detailListForm);
}
