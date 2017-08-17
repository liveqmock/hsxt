/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.uf;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.bean.ResultNotify;
import com.gy.hsxt.gpf.fss.constant.FSSRespCode;
import com.gy.hsxt.gpf.fss.service.NotifyService;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UF通知处理工厂
 *
 * @Package :com.gy.hsxt.gpf.fss.uf
 * @ClassName : RegionPacketReceiveFactory
 * @Description : UF通知处理工厂
 * @Author : chenm
 * @Date : 2015/11/26 11:55
 * @Version V3.0.0.0
 */
@Service("regionPacketReceiveFactory")
public class RegionPacketReceiveFactory implements IUFRegionPacketDataService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RegionPacketReceiveFactory.class);

    /**
     * 通知业务处理接口
     */
    @Resource
    private NotifyService fssNotifyService;

    @Override
    public Object handleReceived(RegionPacketData regionPacketData) {
        //业务代码
        String bizCode = regionPacketData.getHeader().getBizCode();

        HsAssert.hasText(bizCode, FSSRespCode.PARAM_EMPTY_ERROR, "接收UF的Header参数[bizCode]为空");
        //消息实体
        Object obj = regionPacketData.getBody().getBodyContent();

        logger.info("====接收UF的Body实体内容:{}====", obj);

        boolean success;
        //远程通知，异步返回结果
        if (bizCode.equals(AcrossPlatBizCode.TO_FSS_REMOTE_BACK_NOTIFY.name())) {
            ResultNotify resultNotify = JSON.parseObject(JSON.toJSONString(obj), ResultNotify.class);
            success = fssNotifyService.receiveResultNotify(resultNotify);
        } else {//其他为远程通知
            RemoteNotify remoteNotify = JSON.parseObject(JSON.toJSONString(obj), RemoteNotify.class);
            success = fssNotifyService.remoteSyncNotify(remoteNotify);
        }
        return success;
    }
}
