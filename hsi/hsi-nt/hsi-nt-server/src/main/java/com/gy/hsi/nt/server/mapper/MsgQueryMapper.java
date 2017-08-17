/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.server.mapper;

import java.util.List;

import com.gy.hsi.nt.api.beans.MsgQueryBean;
import com.gy.hsi.nt.api.beans.QueryParam;

/**
 * 查询消息Mapper
 * 
 * @Package: com.gy.hsi.nt.server.mapper
 * @ClassName: MsgQueryMapper
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午5:47:20
 * @company: gyist
 * @version V3.0.0
 */
public interface MsgQueryMapper {

	/**
	 * 查询短信行数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午6:00:54
	 * @param param
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int queryNoteByCount(QueryParam param);

	/**
	 * 分页查询短信
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午6:01:10
	 * @param param
	 * @return
	 * @return : List<MsgQueryBean>
	 * @version V3.0.0
	 */
	public List<MsgQueryBean> queryNoteByPage(QueryParam param);

	/**
	 * 查询邮件行数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午6:01:18
	 * @param param
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int queryEmailByCount(QueryParam param);

	/**
	 * 分页查询邮件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午6:01:21
	 * @param param
	 * @return
	 * @return : List<MsgQueryBean>
	 * @version V3.0.0
	 */
	public List<MsgQueryBean> queryEmailByPage(QueryParam param);

	/**
	 * 查询业务互动消息行数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午6:01:24
	 * @param param
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int queryDynamicBizByCount(QueryParam param);

	/**
	 * 分页查询业务互动消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午6:01:28
	 * @param param
	 * @return
	 * @return : List<MsgQueryBean>
	 * @version V3.0.0
	 */
	public List<MsgQueryBean> queryDynamicBizByPage(QueryParam param);
}
