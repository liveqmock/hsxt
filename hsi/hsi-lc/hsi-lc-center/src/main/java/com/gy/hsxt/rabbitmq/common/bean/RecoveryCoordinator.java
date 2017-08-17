package com.gy.hsxt.rabbitmq.common.bean;


/**
 * 
* @ClassName: RecoveryCoordinator 
* @Description: 恢复重写策略类
* @author tianxh 
* @date 2015-8-6 下午5:44:55 
*
 */
public class RecoveryCoordinator {

	public final static long BACKOFF_COEFFICIENT_MIN = 20;//回退系数最小值
	static long BACKOFF_COEFFICIENT_MAX = 327680; // BACKOFF_COEFFICIENT_MIN *
													// 4^7  //回退系数最大值
	private long backOffCoefficient = BACKOFF_COEFFICIENT_MIN; //回退系数
	private static long UNSET = -1;//初始值
	private long currentTime = UNSET;//当前时间
	long next = System.currentTimeMillis() + getBackoffCoefficient();//下一次恢复时间
	/**
	 * 
	* @Title: isTooSoon 
	* @Description: 是否太快
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean isTooSoon() {
		long now = getCurrentTime();
		if (now > next) {
			next = now + getBackoffCoefficient();
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 
	* @Title: getCurrentTime 
	* @Description: 获取当前时间
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	private long getCurrentTime() {
		if (currentTime != UNSET) {
			return currentTime;
		}
		return System.currentTimeMillis();
	}
	/**
	 * 
	* @Title: getBackoffCoefficient 
	* @Description: 获取下一次恢复时间
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	private long getBackoffCoefficient() {
		long currentCoeff = backOffCoefficient;
		if (backOffCoefficient < BACKOFF_COEFFICIENT_MAX) {
			backOffCoefficient *= 4;
		}
		return currentCoeff;
	}
}
