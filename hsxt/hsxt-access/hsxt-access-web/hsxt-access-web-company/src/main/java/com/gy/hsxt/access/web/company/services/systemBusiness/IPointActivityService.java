/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public interface IPointActivityService extends IBaseService {

    /**
     * 积分活动 参与/停止 申请  参与或停止取决于 activity中  applyType类型值
     * @param activity 积分活动申请信息 实体  com.gy.hsxt.bs.bean.entstatus.PointActivity
     * @throws HsException
     * @throws Exception 
     */
    public void pointActivityApply(PointActivity activity) throws Exception;
}
