/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : FileDetailService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 12:41
 * @Version V3.0.0.0
 */
public interface FileDetailService extends BaseService<FileDetail> {

    /**
     * 批量保存文件详情
     * @param details
     * @return
     * @throws HsException
     */
    int batchSave(List<FileDetail> details) throws HsException;

    /**
     * 根据通知编号查询对应的所有文件详情
     * @param notifyNo
     * @return
     * @throws HsException
     */
    List<FileDetail> queryByNotifyNo(String notifyNo) throws HsException;
}
