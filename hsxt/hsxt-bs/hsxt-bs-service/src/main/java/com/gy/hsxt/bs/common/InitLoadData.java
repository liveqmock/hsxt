/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgTplRedisKey;
import com.gy.hsxt.bs.msgtpl.mapper.MsgTemplateMapper;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 初始化加载数据
 * 
 * @Package: com.gy.hsxt.bs.common
 * @ClassName: InifLoadData
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月13日 上午10:17:22
 * @company: gyist
 * @version V3.0.0
 */
public class InitLoadData implements Serializable {

	private static final long serialVersionUID = 3376855477726084930L;

	@SuppressWarnings("rawtypes")
	@Resource
	RedisUtil fixRedisUtil;

	@Autowired
	MsgTemplateMapper tplMapper;

	/**
	 * 初始化加载数据
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月13日 上午10:20:34
	 * @return : void
	 * @version V3.0.0
	 */
	public void init()
	{
		loadMsgTpl();
	}

	/**
	 * 加载消息模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月13日 上午10:21:39
	 * @return : void
	 * @version V3.0.0
	 */
	@SuppressWarnings("unchecked")
	private void loadMsgTpl()
	{
		List<MsgTemplate> listTPl = tplMapper.findEnabledMsgTpl();

		String tplCatchKey = "";
		for (MsgTemplate msgTemp : listTPl)
		{
			tplCatchKey = msgTemp.getTempType() + "_" + msgTemp.getCustType() + "_" + msgTemp.getBizType() + "_"
					+ (StringUtils.isNotBlank(msgTemp.getBuyResType()) ? msgTemp.getBuyResType() : "");
			fixRedisUtil.add(MsgTplRedisKey.MSG_TEMPLATE, tplCatchKey, JSONObject.toJSONString(msgTemp));
		}
	}
}
