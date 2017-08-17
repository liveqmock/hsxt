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

package com.gy.hsxt.uid.uid.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.uid.common.SysConfig;
import com.gy.hsxt.uid.uid.mapper.UidMapper;

/**
 * 
 * @Package: com.gy.hsxt.uid.uid.service
 * @ClassName: UidService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-3-14 下午3:13:01
 * @version V1.0
 */
@Service
public class UidService implements IUidService {

	@Resource
	UidMapper uidMapper;
	@Resource
	RedisUtil changeRedisUtil;

	public static final Object lock = new Object();

	/**
	 * 根据不同企业生成唯一流水号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return 最小值1，最大值11个9,返回null表示系统异常
	 */
	public Long getUid(String entResNo) {
		if (entResNo == null) {
			return null;
		}

		Long minValue = null; //数据库保存的下次起始值
		Long maxValue = this.getMaxValFromCache(entResNo); //缓存保存的当前取值上限
		long addNum = SysConfig.getDefaultAddNum(); //每次取值增量
		Long retValue = null;// 返回值
		if (maxValue == null) {
			// 初次获取或者缓存丢失
			System.out.println(entResNo + "初始获取或者缓存丢失");
			long curValue = getNextValFromCache(entResNo, 1l);// 缓存值加1
			//从数据库获取起始值，并把起始值增加一次增量后，存入数据库及缓存
			minValue = getMinValFromDbAndSetMaxCache(entResNo,curValue);
			//从缓存重新获取最新取值上限
			maxValue = this.getMaxValFromCache(entResNo);
			
			if (minValue.equals( maxValue)) {
				System.out.println(entResNo + "该企业初批获取");
				return curValue;
			} else {// 此时
				// 数据库有最低值要求，但缓存数据丢失，初始化缓存从数据库最低值开始递增
				if (curValue == 1) {
					System.out.println(entResNo
							+ "数据库有最低值["+minValue+"]要求，但缓存数据["+maxValue+"]丢失，初始化缓存从数据库最低值开始递增" );
					getNextValFromCache(entResNo, minValue);// 缓存值增加 minValue
				}
				System.out.println(entResNo + "数据库有最低值要求，从最低值开始递[" + minValue
						+ "]增" + curValue);
				retValue = minValue + curValue;
				return retValue;
			}
		} else {
			long curValue = getNextValFromCache(entResNo, 1l);
			if (addNum == maxValue) {
				// 该企业初批获取
				if(curValue>=maxValue.longValue()){
					System.out.println(entResNo+"取值达到缓存上限，更新数据库下次取值起始值"+maxValue);
					updateNextMaxVal(entResNo, maxValue);
				}
				return curValue;
			} else {// 此时addNum<maxValue
				if (curValue < addNum) {// 数据库有最低值要求，但缓存数据丢失
					System.out.println(entResNo + "缓存异常，将重设缓存[" + curValue
							+ "],maxValue=" + maxValue);
					minValue = getMinValFromDbAndSetMaxCache(entResNo,curValue);
					
					retValue= minValue + curValue;
					setNextValCache(entResNo, retValue);
					return retValue;
				} else {
					if(curValue>=maxValue.longValue()){
						System.out.println(entResNo+"取值达到缓存上限，更新数据库下次取值起始值"+maxValue);
						updateNextMaxVal(entResNo, maxValue);
					}
					return curValue;
				}

			}

		}

	}

	private void updateNextMaxVal(String entResNo, long maxValue) {
		Long addNum = SysConfig.getDefaultAddNum();
		Long nextMax = addNum + maxValue;
		int updateCount = uidMapper.updateUid(entResNo, nextMax);
		if (updateCount == 1) {
			System.out.println(entResNo + "更新数据库成功，更新缓存" + nextMax);
			setMaxValCache(entResNo, nextMax);
		} else {
			System.err.println(entResNo + "并发updateNextMaxVal" + maxValue);
		}
	}

	private synchronized long getMinValFromDbAndSetMaxCache(String entResNo,long nextValue) {
		Long maxValue = this.getMaxValFromCache(entResNo); //缓存保存的当前取值上限
		if(maxValue!=null){ // 取值上限已经被更新
			Long addNum = SysConfig.getDefaultAddNum();
			if (nextValue >= addNum) {
				System.out.println(entResNo + "取值上限并发，已经被更新" + maxValue);
				return maxValue;
			}			
		}
		Long minValue = uidMapper.selectUid(entResNo);
		if (minValue == null) {
			Long addNum = SysConfig.getDefaultAddNum();
			uidMapper.insertUid(entResNo, addNum);
			setMaxValCache(entResNo, addNum);
			return addNum;
		} else {

			updateNextMaxVal(entResNo, minValue);
		}
		return minValue;
	}

	private Long getMaxValFromCache(String entResNo) {
		String maxKey = getUidMaxKey(entResNo);
		String maxVal = changeRedisUtil.getString(maxKey);
		if (maxVal == null) {
			return null;
		} else {
			return Long.valueOf(maxVal);
		}
	}
	private void setMaxValCache(String entResNo, Long num) {
		String key = getUidMaxKey(entResNo);
		changeRedisUtil.setString(key,num.toString());
	}


	private Long getNextValFromCache(String entResNo, long num) {
		String key = getUidKey(entResNo);
		return changeRedisUtil.redisTemplate.opsForValue().increment(key,
				num);
	}
	private void setNextValCache(String entResNo, Long addNum) {
		String key = getUidKey(entResNo);
		changeRedisUtil.setString(key,addNum.toString());
	}

	public static String getUidKey(String entResNo) {
		return UID_CACHE_KEY + entResNo;
	}

	public static String getUidMaxKey(String entResNo) {
		return UID_MAX_KEY + entResNo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Long l = Long.MAX_VALUE; // =9223372036854775807
		String s = l.toString();
		int len = s.length(); // =19
		System.out.println(l);
		System.out.println(len);

	}

}
