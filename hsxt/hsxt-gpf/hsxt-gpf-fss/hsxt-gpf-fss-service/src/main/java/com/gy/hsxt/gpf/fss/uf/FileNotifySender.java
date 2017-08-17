/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.uf;

import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.BaseNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package :com.gy.hsxt.gpf.fss.uf
 * @ClassName : FileNotifySender
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/26 12:03
 * @Version V3.0.0.0
 */
@Service("fileNotifySender")
public class FileNotifySender implements RegionPacketSender {

    private Logger logger = LoggerFactory.getLogger(FileNotifySender.class);

    @Resource
    private IUFRegionPacketService ufRegionPacketService;

    /**
     * 向综合前置发送通知
     *
     * @param notify 通知
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean sendRegionPacketData(BaseNotify notify) throws HsException {
        //打印通知内容
        logger.info("====向综合前置发送通知[notify]:{}====",notify);
        // 组装报文头
        RegionPacketHeader packetHeader = RegionPacketHeader.build();
        packetHeader.setDestPlatformId(notify.getToPlat());//目标平台 id
        //跨平台业务代码
        String bizCode;
        if (FssNotifyType.REMOTE_RESULT.getTypeNo() == notify.getNotifyType()) {
            bizCode = AcrossPlatBizCode.TO_FSS_REMOTE_BACK_NOTIFY.name();
        } else {
            if (GlobalConstant.CENTER_PLAT_NO.equals(notify.getFromPlat())) {
                //总平台通知各个地区平台获取增值系统文件
                bizCode = AcrossPlatBizCode.TO_REGION_NOTIFY_BM_FILE.name();
            } else {
                //各个地区平台通知总平台获取增值系统文件
                bizCode = AcrossPlatBizCode.TO_CENTER_NOTIFY_BM_FILE.name();
            }
        }
        packetHeader.setDestBizCode(bizCode);
        // 组装报文体
        RegionPacketBody packetBody = RegionPacketBody.build(notify);
        // 发送跨地区平台报文
        Object result = ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);

        logger.info("====发送跨地区平台报文结果: {}====" ,result);
        return true;
    }
}
