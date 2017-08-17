package com.gy.hsxt.common.guid;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ID 生成工具类 生成规则：秒时间戳+节点内存中的自增序列
 * 
 */
public class TimeSecondsSeqWorker {

    /**
     * 每秒最多生成序列号
     */
    private final static long MAX_SEQUENCE = 999999L;

    /**
     * 当前序列号
     */
    private long sequence = 0L;

    /**
     * 最后生成时间（秒）
     */
    private long lastTimestamp = -1L;

    /**
     * 根据时间戳生成序列号
     * 
     * @return
     */
    public synchronized String nextId() {
        long timestamp = this.currentTimeSeconds();
        if (this.lastTimestamp == timestamp)
        {
            this.sequence = this.sequence + 1;
            if (this.sequence == MAX_SEQUENCE)
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
        return String.format("%s%06d", timestamp, this.sequence);

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
