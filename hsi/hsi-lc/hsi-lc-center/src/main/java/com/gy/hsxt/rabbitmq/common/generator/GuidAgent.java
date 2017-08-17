package com.gy.hsxt.rabbitmq.common.generator;





/**
 * 
 * @ClassName: GuidAgent 
 * @Description: ID 生成工具类，生成规则：毫秒时间戳+应用节点编号+节点内存中的自增序列
 * @author Lee 
 * @date 2015-6-25 下午2:24:59
 */
public class GuidAgent {
	
	/**
	 * 每个节点的唯一数字ID
	 */
	private final long workerId;
	private final static long twepoch = 1288834974657L;
	private long sequence = 0L;
	private final static long workerIdBits = 4L;
	public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
	private final static long sequenceBits = 10L;
	 
	private final static long workerIdShift = sequenceBits;
	private final static long timestampLeftShift = sequenceBits + workerIdBits;
	public final static long sequenceMask = -1L ^ -1L << sequenceBits;
	 
	/**
	 * 最后生成时间（毫秒）
	 */
	private long lastTimestamp = -1L;
	 
	
	public GuidAgent(final long workerId) {
	    super();
	    if (workerId > this.maxWorkerId || workerId < 0) {
	        throw new IllegalArgumentException(String.format(
	                "worker Id can't be greater than %d or less than 0",
	                this.maxWorkerId));
	    }
	    this.workerId = workerId;
	}
	 
	
	/**
	 * 根据时间戳及应用节点生成序列号
	 * @return
	 */
	public synchronized long nextId() {
	    long timestamp = this.timeGen();
	    if (this.lastTimestamp == timestamp) {
	        this.sequence = (this.sequence + 1) & this.sequenceMask;
	        if (this.sequence == 0) {
	            timestamp = this.tilNextMillis(this.lastTimestamp);
	        }
	    } else {
	        this.sequence = 0;
	    }
	    if (timestamp < this.lastTimestamp) {
	        try {
	            throw new Exception(
	                    String.format(
	                            "Clock moved backwards.  Refusing to generate id for %d milliseconds",
	                            this.lastTimestamp - timestamp));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    this.lastTimestamp = timestamp;
	    long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << this.workerIdShift) | (this.sequence);

	    return nextId;
	}
	
	
	/**
	 * 在高并发时，如果本毫少内已完成最大序号列的生成，跳转到下一毫秒生成序列号
	 * @param lastTimestamp 最后生成时间戳
	 * @return
	 */
	private long tilNextMillis(final long lastTimestamp) {
	    long timestamp = this.timeGen();
	    while (timestamp <= lastTimestamp) {
	        timestamp = this.timeGen();
	    }
	    return timestamp;
	}
	 
	/**
	 * 当前毫秒时间
	 * @return
	 */
	private long timeGen() {
	    return System.currentTimeMillis();
	}
	 
	 
	public static void main(String[] args){
		GuidAgent worker2 = new GuidAgent(2);
//		GuidAgent worker3 = new GuidAgent(3);
	    for(int i=0; i <1000 ; i++){
	    System.out.println("work2:" + worker2.nextId());
//	    System.out.println("work3:" +  worker3.nextId());
        
	   }
	    
//	    System.out.println( -1L ^ -1L << sequenceBits);
	}
}
