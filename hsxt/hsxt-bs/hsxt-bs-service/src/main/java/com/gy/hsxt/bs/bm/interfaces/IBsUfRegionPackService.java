/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.interfaces;

import com.gy.hsxt.bs.bm.bean.ApplyRecord;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.bm.interfaces
 * @ClassName : IUfRegionPackService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/27 12:07
 * @Version V3.0.0.0
 */
public interface IBsUfRegionPackService {

    /**
     * 保存申报增值数据到增值系统
     *
     * @param record 申报增值数据
     * @return boolean
     * @throws HsException
     */
    boolean saveApplyRecordToMlm(ApplyRecord record) throws HsException;

    /**
     * 查询某企业下的增值分配信息
     *
     * @param spreadResNo 推广互生号
     * @param subResNo    挂载互生号
     * @return String json格式
     * @throws HsException
     */
    String queryMlmData(String spreadResNo, String subResNo) throws HsException;

    /**
     * 停止某企业的增值业务
     *
     * @param resNo 企业互生号
     * @return boolean
     * @throws HsException
     */
    boolean stopResourceNo(String resNo) throws HsException;

    /**
     * 校验上下级关系
     *
     * @param spreadResNo 推广节点
     * @param subResNo       挂载节点
     * @return {@code boolean}
     * @throws HsException
     */
    @SuppressWarnings("unused")
    boolean checkSubRelation(String spreadResNo, String subResNo) throws HsException;
}
