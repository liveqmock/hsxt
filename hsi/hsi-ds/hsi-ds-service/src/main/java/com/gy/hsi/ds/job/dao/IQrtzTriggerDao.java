/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.dao;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.job.beans.bo.QrtzTrigger;
import com.gy.hsi.ds.job.controller.form.TriggerListForm;

/**
 * Trigger操作Dao接口
 * 
 * @Package: com.gy.hsi.ds.job.web.service.trigger.dao  
 * @ClassName: QrtzTriggerDao 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月14日 下午4:21:45 
 * @version V3.0
 */
public interface IQrtzTriggerDao  extends BaseDao<String,  QrtzTrigger>{

    /**
     * 根据条件获取Trigger列表
     * @param triggerListForm
     * @return
     */
    DaoPageResult<QrtzTrigger> getTriggerList(TriggerListForm triggerListForm);
    
}
