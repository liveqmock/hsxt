package com.gy.hsxt.common.guid;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 用于生成全局唯一交易流水号工具类
 * 
 * 长序列号编码规则：业务编码（2位）+ 机器节点编号（2位）+ 时间缀（YYYYMMDDHHMMSS）+ 序列号（6位）
 * 短序列号编码规则：业务编码（2位）+ 机器节点编号（2位）+ 时间缀（YYYYMMDDHHMMSS）+ 序列号（2位）
 * 
 * @author guiyi147
 * 
 */
public class GuidAgent {
    /**
     * 短序列号位数
     */
    public static final int SHORT_SEQ_NUM = 2;

    /**
     * 各业务前缀的长序列号生成对象
     */
//    private static Map<String, TimeSecondsSeqWorker> seqWorker = new HashMap<String, TimeSecondsSeqWorker>();
    private static ConcurrentMap<String, TimeSecondsSeqWorker> seqWorkerMap = new ConcurrentHashMap<String, TimeSecondsSeqWorker>();

    
    /**
     * 各业务前缀的短序列号生成对象
     */
//    private static Map<String, SeqWorker> shortSeqWorker = new HashMap<String, SeqWorker>();
    private static ConcurrentMap<String, SeqWorker> shortSeqWorkerMap = new ConcurrentHashMap<String, SeqWorker>();
    
    
 
//    private static TimeSecondsSeqWorker getSeqWorker(String key) {
//        if (seqWorker.containsKey(key))
//        {
//            return seqWorker.get(key);
//        }
//        
//        synchronized (seqWorker)
//        {
//            if (!seqWorker.containsKey(key))
//            {
//                return createSeqWorker(key);
//            }
//            return seqWorker.get(key);
//           
//        }
//
//    }
    
    
    private static TimeSecondsSeqWorker getSeqWorker(String key) {
        TimeSecondsSeqWorker seqWorker =   seqWorkerMap.get(key);
        if (null == seqWorker)
        {
            TimeSecondsSeqWorker newSeqWorker =   new TimeSecondsSeqWorker();
            seqWorker = seqWorkerMap.putIfAbsent(key, newSeqWorker);
            if (null == seqWorker){
                seqWorker = newSeqWorker;
            }
           
        }
        
        return seqWorker;


    }

    /**
     * 检查缓存中是否已存在序列号生成器对象，存在则根据前缀获取
     * 
     * @param key
     *            业务前缀+机器节点编号
     * @return
     */
    private static SeqWorker getShortSeqWorker(String key) {
        SeqWorker seqWorker =   shortSeqWorkerMap.get(key);
        if (null == seqWorker)
        {
            SeqWorker newSeqWorker =   new SeqWorker(SHORT_SEQ_NUM);
            seqWorker = shortSeqWorkerMap.putIfAbsent(key, newSeqWorker);
            if (null == seqWorker){
                seqWorker = newSeqWorker;
            }
           
        }
        
        return seqWorker;

    }

//    private static SeqWorker getShortSeqWorker(String key) {
//        if (shortSeqWorker.containsKey(key))
//        {
//            return shortSeqWorker.get(key);
//        }
//        
//        synchronized (shortSeqWorker)
//        {
//            if (!shortSeqWorker.containsKey(key))
//            {
//                return createShortSeqWorker(key);
//               
//            }
//
//            return shortSeqWorker.get(key);
//        }
//
//    }
    
    /**
     * 用于创建根据时间日期作为时间缀生成序列号的对象(时间缀到秒）
     * 
     * @param key
     *            业务前缀+机器节点编号
     * @return
     */
//    private  TimeSecondsSeqWorker createSeqWorker(String key) {
//        TimeSecondsSeqWorker newSeqWorker = new TimeSecondsSeqWorker();
//        seqWorker.put(key, newSeqWorker);
//        return newSeqWorker;
//    }

    /**
     * 用于创建根据时间日期作为时间缀生成序列号的对象(时间缀到秒）
     * 
     * @param key
     *            业务前缀+机器节点编号
     * @return
     */
//    private static SeqWorker createShortSeqWorker(String key) {
//        SeqWorker newSeqWorker = new SeqWorker(SHORT_SEQ_NUM);
//        shortSeqWorker.put(key, newSeqWorker);
//        return newSeqWorker;
//    }

    /**
     * 生成全局唯一交易流水号 编码规则：业务编码（2位）+机器节点编号（2位）+ 时间缀（YYYYMMDDHHMMSS）+ 序列号（6位）
     * 调用该方法必须传入（业务编码+机器节点编号）作为前缀的字符串
     * 
     * @param prefix
     *            序列号前缀
     * @return 返回字符串
     */
    public static String getStringGuid(String prefix) {

        return prefix + getSeqWorker(prefix).nextId();
    }
    
    /**
     * 生成全局唯一交易流水号(id有序增大)。 编码规则：业务编码+唯一id+机器节点编号system.instance.no
     * @param gourpId 组id or 业务编码
     * @param prefix 业务编码
     * @param subfix 机器节点编号
     * @return
     */
    public static String getGuid(String gourpId,String prefix,String subfix) {
    	String id =getSeqWorker(gourpId).nextId();
    	StringBuilder sb =new StringBuilder();
    	sb.append(prefix).append(id).append(subfix);
        return sb.toString();
    }

    /**
     * 生成全局唯一交易流水号 编码规则：业务编码（2位）+机器节点编号（2位）+ 时间缀（YYYYMMDDHHMMSS）+ 序列号（2位）
     * 调用该方法必须传入（业务编码+机器节点编号）作为前缀的字符串， 适用于发生频率比较低的业务
     * 
     * @param prefix
     *            序列号前缀
     * @return 返回字符串
     */
    public static String getShortStringGuid(String prefix) {

        return prefix + getShortSeqWorker(prefix).nextId();
    }

}
