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

package com.gy.hsxt.uc.as.api.common;

import java.util.List;

import com.gy.hsxt.uc.as.bean.common.AsSysBoSetting;

/** 
 * 
 * @Package: com.gy.hsxt.uc.as.api.common  
 * @ClassName: IUCAsSysBoSettingService 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-1-14 上午11:38:57 
 * @version V1.0 
 

 */
public interface IUCAsSysBoSettingService {
	/**
	 * 保存用户业务操作限制设置
	 * @param custId 企业客户号or消费者客户号
	 * @param list 设置项列表
	 * @param operator 登陆用户id
	 * @return 生效设置条数 
	 */	
	public int setCustSettings(String custId, List<AsSysBoSetting> list, String operator);
	/**
	 * 查询指定客户所有设置项
	 * 
	 * @param custId
	 *            企业客户号or消费者客户号
	 * @return
	 */
	public List<AsSysBoSetting> getCustSettings(String custId);
	/**
	 * 查询指定客户所有设置项，并保存进缓存
	 * 
	 * @param custId
	 *            企业客户号or消费者客户号
	 * @return
	 */
	public List<AsSysBoSetting> loadCustSettings(String custId);
	
	/**
	 * 修改单挑设置项
	 * @param vo 设置内容
	 * @param operator 登陆用户id
	 * @return 修改成功条数
	 */
	public int updateByPrimaryKeySelective(AsSysBoSetting vo, String operator);

}
