/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.controller.form.BusinessNewForm;
import com.gy.hsi.ds.job.service.IBusinessMgr;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.FieldException;


/**
 * 业务处理验证类
 * 
 * @Package: com.gy.hsi.ds.job.web.web.business.validator  
 * @ClassName: BusinessValidator 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月12日 下午12:11:27 
 * @version V3.0
 */
@Component
public class BusinessValidator {
    @Autowired
    private IBusinessMgr businessMgr;
    
    /**
     * 创建业务的时候验证业务是否存在
     * @param businessNewForm
     */
    public void validateCreate(BusinessNewForm businessNewForm) {

        JobStatus bus = businessMgr.getByName(businessNewForm.getServiceName());
        if (bus != null) {
            throw new FieldException(BusinessNewForm.SERVICENAME, "servicename.exist", null);
        }

    }
    /**
     * 根据id验证业务是否存在
     * @param busId
     */
    public void valideBusExist(long busId) {
        JobStatus bus = businessMgr.getBusById(busId);
        if (bus == null) {
            throw new FieldException(BusinessNewForm.BUSINESS, "business.notexist", null);
        }     
    }
    
}
