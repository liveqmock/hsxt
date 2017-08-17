package com.gy.hsxt.kafka.util;

/**
 * ID 生成工具类，
 * 生成规则：毫秒时间戳+节点内存中的自增序列
 *
 */
public class TimeMillsSeqWorker {
	
	
	private final static long twepoch = 1288834974657L;
	private long sequence = 0L;

	private final static long sequenceBits = 10L;
	private final static long timestampLeftShift = sequenceBits ;
	public final static long sequenceMask = -1L ^ -1L << sequenceBits; //=1023
	 
	/**
	 * 最后生成时间（毫秒）
	 */
	private long lastTimestamp = -1L;
	 

	
	/**
	 * 根据时间戳生成序列号
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
	                            " Refusing to generate id for %d milliseconds",
	                            this.lastTimestamp - timestamp));
	        } catch (Exception e) {
//	            e.printStackTrace();
	        }
	    }
	 
	    this.lastTimestamp = timestamp;
	    long nextId = ((timestamp - twepoch << timestampLeftShift)) |(this.sequence);

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
	    //twepoch = 1288834974657L;
	    printLong(twepoch);
	    long ctm=System.currentTimeMillis();
	    printLong(ctm); //=1449195859407
	    
	    System.out.println("Long.MAX_VALUE="+Long.MAX_VALUE);//=9223372036854775807
	    //sequenceMask = -1L ^ -1L << sequenceBits;
	   printLong(-1L);//input=-1
	   printLong(-1L ^ -1L); //input=0
	   printLong(-1L ^ -1L << sequenceBits); //input=1023
	   printLong(1023L&1023L);//input=1023
	   printLong(1024L&1023L);//input=0
	   
	   //long nextId = ((timestamp - twepoch << timestampLeftShift)) |(this.sequence);
	   printLong(twepoch<< timestampLeftShift);
	   printLong(ctm-twepoch);//=input=160362840072 ，长度12位
	   printLong(ctm-twepoch<< timestampLeftShift); //input=164211548233728  左移10位 相当于 乘于 1024
	   printLong((ctm-twepoch<< timestampLeftShift)|0); //左移10后，右边最后10位补0， |运算小于1024的数 相当于 加上该数
	   printLong((ctm-twepoch<< timestampLeftShift)|1);

	}
	
	static void printLong(long l){
	    String str01=Long.toBinaryString(l);
	    System.out.println("input="+l);
        System.out.println(str01);
        System.out.println(str01.length());//=41
	}


}
