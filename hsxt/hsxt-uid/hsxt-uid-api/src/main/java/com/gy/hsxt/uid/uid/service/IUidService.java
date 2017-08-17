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

/** 
 * 
 * @Package: com.gy.hsxt.uid.uid.service  
 * @ClassName: IUidService 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-3-14 下午4:32:15 
 * @version V1.0 
 

 */
public interface IUidService {
	public static final String UID_CACHE_KEY="UID.";
	public static final String UID_MAX_KEY="UID.MAX.";
	/**
	 * 根据不同企业生成唯一流水号
	 * @param entResNo 企业互生号
	 * @return 最小值1，最大值11个9
	 */
	public Long getUid(String entResNo);
	

}
