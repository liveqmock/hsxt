package com.gy.hsxt.common.guid;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 自定义自增序列长度 ID 生成工具类
 * 
 * 生成规则：秒时间戳+节点内存中的自增序列， 自增序列长度需要构造时指定
 * 
 */
public class SeqWorker {

    /**
     * 当前序列号
     */
    private long sequence = 0L;

    /**
     * 最后生成时间（秒）
     */
    private long lastTimestamp = -1L;

    /**
     * 序列号最大值
     */
    private long maxSequence = 1L;

    /**
     * 序列号长度,最小为1
     */
    private int seqNum = 1;

    /**
     * 初始化序列号生成器
     * 
     * @param seqNum
     *            随机序列号最大位数
     */
    public SeqWorker(int seqNum) {
        // 至少一位长度序列号，如果低于1为，默认使用1位
        if (seqNum > 1)
        {
            this.seqNum = seqNum;
        }
        for (int i = 0; i < this.seqNum; i++)
        {
            maxSequence = maxSequence * 10;
        }
    }

    /**
     * 根据时间戳生成序列号
     * 
     * @return
     */
    public synchronized String nextId() {
        long timestamp = this.currentTimeSeconds();
        if (this.lastTimestamp == timestamp)
        {
            this.sequence++;
            if (this.sequence >= maxSequence)
            {
                timestamp = this.tilNextSeconds(this.lastTimestamp);
                this.sequence = 0;
            }
        }
        else
        {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp)
        {
            try
            {
                throw new Exception(String.format(" Refusing to generate id for %d seconds", this.lastTimestamp
                        - timestamp));
            }
            catch (Exception e)
            {
                // e.printStackTrace();
            }
        }
        this.lastTimestamp = timestamp;
        return String.format("%s%0" + seqNum + "d", timestamp, this.sequence);

    }

    /**
     * 在高并发时，如果本秒内已完成最大序号列的生成，跳转到下一秒生成序列号
     * 
     * @param lastTimestamp
     *            最后生成时间戳
     * 
     */
    private long tilNextSeconds(final long lastTimestamp) {
        long timestamp = this.currentTimeSeconds();
        while (timestamp <= lastTimestamp)
        {
            timestamp = this.currentTimeSeconds();
        }
        return timestamp;
    }

    /**
     * 当前秒时间缀
     * 
     * @return
     */
    private long currentTimeSeconds() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return Long.parseLong(sdf.format(Calendar.getInstance().getTime()));
    }

}
