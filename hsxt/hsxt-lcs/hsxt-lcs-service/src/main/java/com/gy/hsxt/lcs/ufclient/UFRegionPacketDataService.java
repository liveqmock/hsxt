/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.lcs.ufclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.lcs.interfaces.INotifyService;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataHeader;

/**
 * 
 * @Package: com.gy.hsxt.lcs.ufclient
 * @ClassName: UFRegionPacketDataService
 * @Description: 综合前置接口本地实现类
 * 
 * @author: yangjianguo
 * @date: 2015-11-20 下午2:14:58
 * @version V1.0
 */
@Service("regionPacketDataService")
public class UFRegionPacketDataService implements IUFRegionPacketDataService {

    @Autowired
    INotifyService notifyService;

    /**
     * 业务系统接收并处理UF接收到的跨地区平台报文数据
     * 
     * @param 收到的消息报文
     * @return
     * @see com.gy.hsxt.uf.api.IUFRegionPacketDataService#handleReceived(com.gy.hsxt.uf.bean.packet.data.RegionPacketData)
     */
    @Override
    public Object handleReceived(RegionPacketData regionPacketData) {
        // UF报文头对象
        RegionPacketDataHeader header = regionPacketData.getHeader();

        // UF报文体内容对象
        Object bodyData = regionPacketData.getBody().getBodyContent();

        // 跨平台业务代码
        String bizCode = header.getBizCode();
        // 根据不同的业务代码进行相应处理
        if (AcrossPlatBizCode.TO_REGION_NOTIFY_UPDATE.name().equals(bizCode))
        { // TODO 通知地区平台进行更新同步
            JSONObject param = JSON.parseObject((String) bodyData);
            notifyService.notifyChange(param.getString("tableCode"), param.getLong("version"));
        }
        return "ok";
    }
}
