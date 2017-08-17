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

package com.gy.hsxt.gpf.res.ufclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.gpf.res.bean.QuotaApp;
import com.gy.hsxt.gpf.res.interfaces.IQuotaService;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataHeader;

import javax.annotation.Resource;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.ufclient
 * @ClassName: UFRegionPacketDataService
 * @Description: 综合前置接口本地实现类
 * 
 * @author: yangjianguo
 * @date: 2015-11-20 下午2:14:58
 * @version V1.0
 */
@Service("regionPacketDataService")
public class UFRegionPacketDataService implements IUFRegionPacketDataService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IQuotaService quotaService;

    /**
     * 业务系统接收并处理UF接收到的跨地区平台报文数据
     * 
     * @param regionPacketData
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

        Integer result = null;
        // 根据不同的业务代码进行相应处理
        if (AcrossPlatBizCode.TO_CENTER_APPLY_QUOTA.name().equals(bizCode))
        {
            try
            {
                QuotaApp quotaApp = JSON.parseObject((String) bodyData, QuotaApp.class);
                quotaService.addQuotaApp4AreaPlat(quotaApp);
                result = RespCode.SUCCESS.getCode();
            }
            catch (Exception e)
            {
                result = RespCode.FAIL.getCode();
                log.error("保存地区平台的一级配额申请数据失败[param=" + JSON.toJSONString(bodyData) + "]", e);
            }
        }

        return result;
    }
}
