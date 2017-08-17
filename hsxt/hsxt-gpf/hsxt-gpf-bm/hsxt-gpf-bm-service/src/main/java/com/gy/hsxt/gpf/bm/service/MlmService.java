package com.gy.hsxt.gpf.bm.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.Increment;

import java.util.Map;

/**
 * 申报增值数据查询与保存接口
 *
 * @author chenmin
 * @version 1.0
 * @category
 * @create 2015/9/29 20:40
 * @desc
 */
public interface MlmService {

    /**
     * 保存申报企业的增值节点数据
     *
     * @param record 申报数据
     * @return map
     */
    Map<String,Object> saveApplyRecordToMlm(ApplyRecord record) throws HsException;

    /**
     * 查询企业增值分配的节点数据
     *
     * @param resNo 企业互生号
     * @return Increment
     */
    Increment queryMlmData(String resNo) throws HsException;


    /**
     * 停止企业增值节点
     *
     * @param resNo 企业互生号
     * @return boolean
     * @throws HsException
     */
    boolean stopResourceNo(String resNo) throws HsException;

    /**
     * 校验上下级关系(地区平台用)
     *
     * @param spreadResNo 推广互生号
     * @param subResNo 下级互生号
     * @return
     * @throws HsException
     */
    boolean checkSubRelation(String spreadResNo, String subResNo)throws HsException;
}
