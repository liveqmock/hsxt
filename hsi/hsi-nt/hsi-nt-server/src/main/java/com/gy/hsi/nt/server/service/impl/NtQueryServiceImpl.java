/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.nt.api.beans.MsgQueryBean;
import com.gy.hsi.nt.api.beans.PageData;
import com.gy.hsi.nt.api.beans.QueryParam;
import com.gy.hsi.nt.api.service.INtQueryService;
import com.gy.hsi.nt.server.mapper.MsgQueryMapper;
import com.gy.hsi.nt.server.util.StringUtil;

/**
 * 消息查询接口实现类
 * 
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: NtQueryServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月28日 下午12:20:01
 * @company: gyist
 * @version V3.0.0
 */
@Service("ntQueryService")
public class NtQueryServiceImpl implements INtQueryService {

	/**
	 * 消息查询Mapper
	 */
	@Autowired
	private MsgQueryMapper msgqueryMapper;

	/**
	 * 分页查询短信
	 * 
	 * @Description:
	 * @param param
	 * @return
	 */
	@Override
	public PageData<MsgQueryBean> queryNoteByPage(QueryParam param)
	{
		if (null == param || param.getPages() <= 0)
		{
			return new PageData<MsgQueryBean>(0, null);
		}
		int count = msgqueryMapper.queryNoteByCount(param);
		if (count > 0)
		{
			param.setStartCount(StringUtil.countPageStaticCount(param.getPages(), param.getPageSize()));
			List<MsgQueryBean> beans = msgqueryMapper.queryNoteByPage(param);
			if (null != beans && beans.size() > 0)
			{
				return new PageData<MsgQueryBean>(count, beans);
			}
		}
		return new PageData<MsgQueryBean>(0, null);
	}

	/**
	 * 分页查询邮件消息
	 * 
	 * @Description:
	 * @param param
	 * @return
	 */
	@Override
	public PageData<MsgQueryBean> queryEmailByPage(QueryParam param)
	{
		if (null == param || param.getPages() <= 0)
		{
			return new PageData<MsgQueryBean>(0, null);
		}
		int count = msgqueryMapper.queryEmailByCount(param);
		if (count > 0)
		{
			param.setStartCount(StringUtil.countPageStaticCount(param.getPages(), param.getPageSize()));
			List<MsgQueryBean> beans = msgqueryMapper.queryEmailByPage(param);
			if (null != beans && beans.size() > 0)
			{
				return new PageData<MsgQueryBean>(count, beans);
			}
		}
		return new PageData<MsgQueryBean>(0, null);
	}

	/**
	 * 分页查询业务互动消息
	 * 
	 * @Description:
	 * @param param
	 * @return
	 */
	@Override
	public PageData<MsgQueryBean> queryDynamicBizByPage(QueryParam param)
	{
		if (null == param || param.getPages() <= 0)
		{
			return new PageData<MsgQueryBean>(0, null);
		}
		int count = msgqueryMapper.queryDynamicBizByCount(param);
		if (count > 0)
		{
			param.setStartCount(StringUtil.countPageStaticCount(param.getPages(), param.getPageSize()));
			List<MsgQueryBean> beans = msgqueryMapper.queryDynamicBizByPage(param);
			if (null != beans && beans.size() > 0)
			{
				return new PageData<MsgQueryBean>(count, beans);
			}
		}
		return new PageData<MsgQueryBean>(0, null);
	}
}
