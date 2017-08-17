/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.PageData;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : LocalNotifyService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 19:42
 * @Version V3.0.0.0
 */
public interface LocalNotifyService extends BaseService<LocalNotify> {

    /**
     * 检查文件详情是否全部校验通过
     *
     * @param notifyNo
     * @return
     * @throws HsException
     */
    boolean checkAllPass(String notifyNo) throws HsException;

    /**
     * 根据查询实体进行列表查询
     *
     * @param queryNotify
     * @return
     * @throws HsException
     */
    List<LocalNotify> queryByOther(QueryNotify queryNotify) throws HsException;

    /**
     * 分页查询
     *
     * @param queryNotify 查询实体
     * @return pageData
     * @throws HsException
     */
    PageData<LocalNotify> queryLocalNotifyForPage(QueryNotify queryNotify) throws HsException;
}
