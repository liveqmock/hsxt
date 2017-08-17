/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.bean.Increment;
import com.gy.hsxt.gpf.bm.cache.CacheApplyQueue;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.service.ApplyRecordService;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.service.MlmService;
import com.gy.hsxt.gpf.bm.utils.ParamsUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增值系统对外接口实现类
 *
 * @Package :com.gy.apply.web.increment.service.impl
 * @ClassName : MlmServiceImpl
 * @Description : 增值系统对外接口实现类
 * @Author : chenm
 * @Date : 2015/9/30 10:27
 * @Version V3.0.0.0
 */
@Service("mlmService")
public class MlmServiceImpl implements MlmService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(MlmServiceImpl.class);

    /**
     * 增值业务实现注入
     */
    @Resource
    private IncrementService incrementService;

    /**
     * 申报记录
     */
    @Resource
    private ApplyRecordService applyRecordService;

    /**
     * 保存申报企业的增值节点数据
     *
     * @param record 申报数据
     * @return map
     */
    @Override
    public Map<String,Object> saveApplyRecordToMlm(ApplyRecord record) throws HsException {
        logger.info("保存申报企业的增值节点数据的参数：{}", record);
        Map<String, Object> map = new HashMap<>();
        try {
            //校验及配置申报记录
            ParamsUtil.validateApplyRecord(record);
            //查询增值节点
            IncNode incNode = incrementService.getValueByKey(record.getPopCustId());
            if (incNode == null || Constants.INCREMENT_ISACTIVE_N.equals(incNode.getIsActive())) {
                //保存申报记录
                applyRecordService.save(record.getPopCustId(), record);
                //将申报数据添加到列队中
                boolean success = CacheApplyQueue.nonExist(record) && CacheApplyQueue.add(record);
                map.put("success", success);
                map.put("message", "已添加到列队中");
            }else {
                map.put("success", true);
                map.put("message", "["+record.getPopCustId()+"]对应的有效增值节点已存在");
            }
        } catch (Exception e) {
            logger.error("========添加增值节点数据失败=========", e);
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * 查询企业增值分配的节点数据
     *
     * @param resNo 企业互生号
     * @return Increment
     */
    @Override
    public Increment queryMlmData(String resNo) throws HsException {
        logger.info("查询企业增值分配的节点数据的参数[resNo]：{}", resNo);
        HsAssert.hasText(resNo, BMRespCode.BM_PARAM_EMPTY_ERROR, "企业互生号不能为空");
        return incrementService.getChildrenTB(resNo);
    }

    /**
     * 停止企业增值节点
     *
     * @param resNo 企业互生号
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean stopResourceNo(String resNo) throws HsException {
        logger.info("=====停止企业增值节点的参数[resNo]：{} =====", resNo);
        HsAssert.hasText(resNo, BMRespCode.BM_PARAM_EMPTY_ERROR, "企业互生号不能为空");
        boolean success = true;
        List<IncNode> incNodeList = incrementService.queryIncNodesByResNo(resNo);
        if (CollectionUtils.isNotEmpty(incNodeList)) {
            for (IncNode v : incNodeList) {
                if (Constants.INCREMENT_ISACTIVE_Y.equals(v.getIsActive())) {
                    v.setIsActive(Constants.INCREMENT_ISACTIVE_N);
                    incrementService.save(v.getCustId(), v);
                }
            }
        } else {
            logger.info("===========企业互生号[{}]没有找到相关节点=========");
            success = false;
        }
        return success;
    }

    /**
     * 校验上下级关系(地区平台用)
     *
     * @param spreadResNo 推广互生号
     * @param subResNo    下级互生号
     * @return
     * @throws HsException
     */
    @Override
    public boolean checkSubRelation(String spreadResNo, String subResNo) throws HsException {
        logger.info("=====校验上下级关系的参数[spreadResNo]：{} , [subResNo]:{}=====", spreadResNo,subResNo);
        return incrementService.checkSubRelation(spreadResNo,subResNo);
    }
}
