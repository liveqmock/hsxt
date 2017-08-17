/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.uf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.Increment;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.service.MlmService;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UF综合前置通知处理工厂
 *
 * @Package :com.gy.hsxt.gpf.bm.uf
 * @ClassName : RegionPacketReceiveFactory
 * @Description : UF综合前置通知处理工厂
 * @Author : chenm
 * @Date : 2015/11/27 10:18
 * @Version V3.0.0.0
 */
@Service
public class RegionPacketReceiveFactory implements IUFRegionPacketDataService {

    private Logger logger = LoggerFactory.getLogger(RegionPacketReceiveFactory.class);

    @Resource
    private MlmService mlmService;

    @Override
    public Object handleReceived(RegionPacketData regionPacketData) {

        //业务代码
        String bizCode = regionPacketData.getHeader().getBizCode();

        HsAssert.hasText(bizCode, BMRespCode.BM_PARAM_EMPTY_ERROR, "接收UF的Header参数[bizCode]为空");
        //消息实体
        Object obj = regionPacketData.getBody().getBodyContent();

        logger.info("=======接收UF的Body实体内容:{}=======", obj);

        HsAssert.notNull(obj, BMRespCode.BM_PARAM_NULL_ERROR, "接收UF的Body实体内容为null");
        HsAssert.hasText(String.valueOf(obj), BMRespCode.BM_PARAM_EMPTY_ERROR, "接收UF的Body实体内容为空");

        Object result = null;
        //查询节点信息
        if (AcrossPlatBizCode.TO_CENTER_GET_BM_NODE_INFO.name().equals(bizCode)) {
            JSONObject json =  JSONObject.parseObject(String.valueOf(obj));
            String spreadResNo = json.getString("spreadResNo");
            String subResNo = json.getString("subResNo");
            Increment increment = mlmService.queryMlmData(subResNo);
            if (StringUtils.isNotEmpty(spreadResNo)) {
                if (spreadResNo.equals(subResNo)) {
                    increment.setSubRelation(true);
                }else{
                    boolean sub = mlmService.checkSubRelation(spreadResNo,subResNo);
                    increment.setSubRelation(sub);
                }
            }else{
                increment.setSubRelation(false);
            }
            result = JSON.toJSONString(increment);
        }
        //保存节点信息
        if (AcrossPlatBizCode.TO_CENTER_BM_OPEN_ENT.name().equals(bizCode)) {
            ApplyRecord record = JSONObject.parseObject(String.valueOf(obj), ApplyRecord.class);
            result = JSON.toJSONString(mlmService.saveApplyRecordToMlm(record));
        }
        //关闭节点
        if (AcrossPlatBizCode.TO_CENTER_BM_CLOSE_ENT.name().equals(bizCode)) {
            result = mlmService.stopResourceNo(String.valueOf(obj));
        }

        //校验上下级关系
        if (AcrossPlatBizCode.TO_CENTER_BM_PLAT_CHECK_SUB.name().equals(bizCode)) {
            JSONObject json =  JSONObject.parseObject(String.valueOf(obj));
            String spreadResNo = json.getString("spreadResNo");
            String subResNo = json.getString("subResNo");
            result = mlmService.checkSubRelation(spreadResNo,subResNo);
        }

        logger.info("==========返回UF的结果为:{}===========", result);
        return result;
    }
}
