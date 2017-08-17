/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.PageData;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;

import java.util.List;

/**
 * 远程通知的业务层接口
 *
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : RemoteNotifyService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 16:37
 * @Version V3.0.0.0
 */
public interface RemoteNotifyService extends BaseService<RemoteNotify> {

    /**
     * 检查文件详情是否全部校验通过
     *
     * @param notifyNo
     * @return
     * @throws HsException
     */
    boolean checkAllPass(String notifyNo) throws HsException;

    /**
     * 根据查询实体查询列表
     *
     * @param queryNotify
     * @return
     * @throws HsException
     */
    List<RemoteNotify> queryByOther(QueryNotify queryNotify) throws HsException;

    /**
     * 分页查询
     *
     * @param queryNotify 查询实体
     * @return pageData
     * @throws HsException
     */
    PageData<RemoteNotify> queryRemoteNotifyForPage(QueryNotify queryNotify) throws HsException;
}
