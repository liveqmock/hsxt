/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.kafka.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于生成唯一ID工具类，包括客户号、数据库主键等 生成规则：前缀(1位) + 机器节点编号（2位）+ 随机数（15位）
 * 
 * @Package: com.gy.hsxt.common.guid
 * @ClassName: RandomGuidAgent
 * @Description: TODO
 * 
 * @author: guiyi147
 * @date: 2015-10-19 下午2:28:47
 * @version V1.0
 */
public class RandomGuidAgent {

    /**
     * 各业务前缀的序列号生成对象
     */
    private static Map<String, TimeMillsSeqWorker> seqWorker = new HashMap<String, TimeMillsSeqWorker>();

    private static TimeMillsSeqWorker defSeqWorker = new TimeMillsSeqWorker();

    /**
     * 检查缓存中是否已存在序列号生成器对象，存在则根据前缀获取
     * 
     * @param key
     *            业务前缀+机器节点编号
     * @return
     */
    private static TimeMillsSeqWorker getSeqWorker(String key) {
        synchronized (seqWorker)
        {
            if (seqWorker.containsKey(key))
            {
                return seqWorker.get(key);
            }

            return createSeqWorker(key);
        }

    }

    /**
     * 用于创建根据毫秒作为时间缀生成序列号的对象(毫秒）
     * 
     * @param key
     *            业务前缀+机器节点编号
     * @return
     */
    private static TimeMillsSeqWorker createSeqWorker(String key) {
        TimeMillsSeqWorker newSeqWorker = new TimeMillsSeqWorker();
        seqWorker.put(key, newSeqWorker);
        return newSeqWorker;
    }

    /**
     * 生成全局唯 一ID=前缀+随机数（15位） 调用该方法必须传入（业务编码+机器节点编号）作为前缀的字符串 
     * 前缀规则：前缀(1位) + 机器节点编号（2位）
     * 依赖前缀区分机器以保证分布式环境下多台机器生成唯一ID
     * @param prefix
     *            序列号前缀，规则：前缀(1位) + 机器节点编号（2位） 
     * @return 返回字符串=前缀+随机数（15位）
     */
    public static String getStringGuid(String prefix) {

        return prefix + getSeqWorker(prefix).nextId();
    }

    /**
     * 根据当前毫秒数获取(15位递增)唯一数字
     * 分布式多台服务器下不保证唯一
     * @return
     */
    public static String getStringGuid() {

        return "" + defSeqWorker.nextId();
    }

    public static void main(String args[]) {

        for (int i = 0; i < 100; i++)
        {
            System.out.println(RandomGuidAgent.getStringGuid("110"));
        }
    }

}
