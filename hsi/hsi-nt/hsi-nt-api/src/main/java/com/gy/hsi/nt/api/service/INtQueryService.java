/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.service;

import com.gy.hsi.nt.api.beans.MsgQueryBean;
import com.gy.hsi.nt.api.beans.PageData;
import com.gy.hsi.nt.api.beans.QueryParam;

/**
 * 消息查询接口
 * 
 * @Package: com.gy.hsi.nt.api.service
 * @ClassName: INtQueryService
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 上午11:48:46
 * @company: gyist
 * @version V3.0.0
 */
public interface INtQueryService {

	/**
	 * 分页查询短信信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:52:15
	 * @param param
	 * @return
	 * @return : PageData<MsgQueryBean>
	 * @version V3.0.0
	 */
	public PageData<MsgQueryBean> queryNoteByPage(QueryParam param);

	/**
	 * 分页查询邮件信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:52:37
	 * @param param
	 * @return
	 * @return : PageData<MsgQueryBean>
	 * @version V3.0.0
	 */
	public PageData<MsgQueryBean> queryEmailByPage(QueryParam param);

	/**
	 * 分页查询系统互动信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 上午11:52:49
	 * @param param
	 * @return
	 * @return : PageData<MsgQueryBean>
	 * @version V3.0.0
	 */
	public PageData<MsgQueryBean> queryDynamicBizByPage(QueryParam param);
}
