package com.gy.hsi.nt.api.common;
/**
 * 
 * @className:Priority
 * @author:likui
 * @date:2015年7月27日
 * @desc:优先级枚举类
 * @company:gyist
 */
public enum Priority {

	/**
	 * 高
	 */
	HIGH(0),
	
	/**
	 * 中
	 */
	MIDDLE(1),
	
	/**
	 * 低
	 */
	LOW(2);
	private int priority;
	
	Priority(int priority){
        this.priority=priority;
    }
	
    public int getPriority(){
        return priority;
    }
}
