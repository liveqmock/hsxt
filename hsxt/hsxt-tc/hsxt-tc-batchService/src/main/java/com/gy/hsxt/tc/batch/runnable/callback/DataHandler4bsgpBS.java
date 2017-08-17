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

package com.gy.hsxt.tc.batch.runnable.callback;

import org.springframework.data.redis.core.RedisTemplate;

import com.gy.hsxt.tc.batch.mapper.TcBsgpPayMapper;

/**
 * BSGP对账之BS端数据文件内容拆解及入库
 * 
 * @Package: com.gy.hsxt.tc.batch.runnable.callback
 * @ClassName: DataHandler4bsgpBS
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-11-13 下午2:18:48
 * @version V1.0
 */
public class DataHandler4bsgpBS extends DataHandlerAbstract {
    /**
     * 账单入库表
     */
    public static final String MY_TABLE = "T_TC_BSGP_PAY_BS_TMP";

    /**
     * 账单入库表字段 (与数据文件字段顺序保持一致)： 业务订单号｜订单货币金额｜订单时间｜支付状态
     */
    public static final String[] MY_COLUMNS = { "BS_ORDER_NO", "BS_TRANS_AMOUNT", "BS_TRANS_DATE", "BS_TRANS_STATUS" };

    public DataHandler4bsgpBS(TcBsgpPayMapper batchMapper, RedisTemplate redisTemplate) {
        super(batchMapper, redisTemplate, MY_TABLE, MY_COLUMNS);

    }

    /**
     * 生成对账要素
     * 
     * @param args
     *            一行数据
     * @return
     */
    public String generateCheckKey(String[] args) {
        // 对账文件字段： 业务订单号｜订单货币金额｜订单时间｜支付状态
        // BS-GP 对账要素：业务订单号|订单货币金额|支付状态
        StringBuilder sb = new StringBuilder();
        sb.append(args[0]).append("|");
        sb.append(args[1]).append("|");
        sb.append(args[3]);
        return sb.toString();
    }

}
