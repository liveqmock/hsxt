/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bm.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bm.bean.ApplyRecord;
import com.gy.hsxt.bs.bm.interfaces.IBsUfRegionPackService;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/**
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : UfRegionPackService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/27 12:08
 * @Version V3.0.0.0
 */
@Service("bsUfRegionPackService")
public class BsUfRegionPackService implements IBsUfRegionPackService {

    /**
     * 业务系统基础配置
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入UF信息发送接口
     */
    @Resource
    private IUFRegionPacketService ufRegionPacketService;

    /**
     * 保存申报增值数据到增值系统
     * 
     * @param record
     *            申报增值数据
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean saveApplyRecordToMlm(ApplyRecord record) throws HsException {
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveApplyRecordToMlm", "申报增值数据[record]:" + record);
        // 组装报文头
        RegionPacketHeader packetHeader = RegionPacketHeader.build();
        packetHeader.setDestPlatformId(GlobalConstant.CENTER_PLAT_NO);// 目标平台 id
        // 跨平台业务代码
        packetHeader.setDestBizCode(AcrossPlatBizCode.TO_CENTER_BM_OPEN_ENT.name());
        // 组装报文体
        RegionPacketBody packetBody = RegionPacketBody.build(JSON.toJSONString(record));
        // 发送跨地区平台报文
        Object result = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveApplyRecordToMlm", "发送跨地区平台报文结果[result]:" + result);
        // 返回结果不能为空
        HsAssert.notNull(result, BSRespCode.BS_CALL_CENTERPLAT_FAIL, "通过综合前置[UF]调用增值系统[BM]失败");
        // 解析调用增值系统的结果
        Map map = JSON.parseObject(result.toString(), HashMap.class);
        // boolean结果
        Object success = map.get("success");
        // 获取结果信息
        Object message = map.get("message");
        // 通过异常抛出失败原因
        HsAssert.isTrue(success != null && Boolean.valueOf(String.valueOf(success)), BSRespCode.BS_SAVE_INCREMENT_FAIL,
                message == null ? "保存增值节点失败" : String.valueOf(message));
        return true;
    }

    /**
     * 查询某企业下的增值分配信息
     *
     * @param spreadResNo 推广互生号
     * @param subResNo    挂载互生号
     * @return String json格式
     * @throws HsException
     */
    @Override
    public String queryMlmData(String spreadResNo, String subResNo) throws HsException {

        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryMlmData", "查询某企业下的增值分配信息[spreadResNo]:" + spreadResNo+"[subResNo]:"+subResNo);
        // 组装报文头
        RegionPacketHeader packetHeader = RegionPacketHeader.build();
        packetHeader.setDestPlatformId(GlobalConstant.CENTER_PLAT_NO);// 目标平台 id
        // 跨平台业务代码
        packetHeader.setDestBizCode(AcrossPlatBizCode.TO_CENTER_GET_BM_NODE_INFO.name());
        //组装参数
        Map<String, String> map = new HashMap<>();
        map.put("spreadResNo", spreadResNo);
        map.put("subResNo", subResNo);
        // 组装报文体
        RegionPacketBody packetBody = RegionPacketBody.build(JSON.toJSONString(map));
        // 发送跨地区平台报文
        Object result = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryMlmData", "发送跨地区平台报文结果[result]:" + result);

        return result == null ? null : result.toString();
    }

    /**
     * 停止某企业的增值业务
     * 
     * @param resNo
     *            企业互生号
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean stopResourceNo(String resNo) throws HsException {
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:stopResourceNo", "停止某企业的增值业务[resNo]:" + resNo);
        // 组装报文头
        RegionPacketHeader packetHeader = RegionPacketHeader.build();
        packetHeader.setDestPlatformId(GlobalConstant.CENTER_PLAT_NO);// 目标平台 id
        // 跨平台业务代码
        packetHeader.setDestBizCode(AcrossPlatBizCode.TO_CENTER_BM_CLOSE_ENT.name());
        // 组装报文体
        RegionPacketBody packetBody = RegionPacketBody.build(resNo);
        // 发送跨地区平台报文
        Object result = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:stopResourceNo", "发送跨地区平台报文结果[result]:" + result);

        return result != null && Boolean.parseBoolean(result.toString());
    }

    /**
     * 校验上下级关系
     *
     * @param spreadResNo 推广节点
     * @param subResNo       挂载节点
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    public boolean checkSubRelation(String spreadResNo, String subResNo) throws HsException {
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:checkSubRelation", "校验上下级关系[spreadResNo]:" + spreadResNo+"[subResNo]:"+subResNo);
        // 组装报文头
        RegionPacketHeader packetHeader = RegionPacketHeader.build();
        packetHeader.setDestPlatformId(GlobalConstant.CENTER_PLAT_NO);// 目标平台 id
        // 跨平台业务代码
        packetHeader.setDestBizCode(AcrossPlatBizCode.TO_CENTER_BM_PLAT_CHECK_SUB.name());
        //组装参数
        Map<String, String> map = new HashMap<>();
        map.put("spreadResNo", spreadResNo);
        map.put("subResNo", subResNo);
        // 组装报文体
        RegionPacketBody packetBody = RegionPacketBody.build(JSON.toJSONString(map));
        // 发送跨地区平台报文
        Object result = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
        // 写请求参数日志
        SystemLog.debug(bsConfig.getSysName(), "function:checkSubRelation", "发送跨地区平台报文结果[result]:" + result);

        return result != null && Boolean.parseBoolean(result.toString());
    }
}
