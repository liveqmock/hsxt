/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.systemService;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.systemservice.SysService;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-person
 * @package       : com.gy.hsxt.access.web.person.services.systemService
 * @className     : ISystemService.java
 * @description   : 系统服务接口
 * @author        : maocy
 * @createDate    : 2016-01-21
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface ISystemService extends IBaseService {
	
	/**
	 * 
	 * 方法名称：查询实名注册
	 * 方法描述：查询实名注册
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findTruenameregList(Map filterMap, Map sortMap, Page page) throws HsException;
	
	/**
	 * 
	 * 方法名称：查询实名认证
	 * 方法描述：查询实名认证
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysBizRecord> findTruenameaugList(Map filterMap, Map sortMap, Page page) throws HsException;
	
	/**
	 * 
	 * 方法名称：查询重要信息变更
	 * 方法描述：查询重要信息变更
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysBizRecord> findImptinfochangeList(Map filterMap, Map sortMap, Page page) throws HsException;
	
	/**
	 * 
	 * 方法名称：查询互生卡补办
	 * 方法描述：查询互生卡补办
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findCardapplyList(Map filterMap, Map sortMap, Page page) throws HsException;
	
	/**
	 * 
	 * 方法名称：查询互生卡挂失
	 * 方法描述：查询互生卡挂失
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findCardlossList(Map filterMap, Map sortMap, Page page) throws HsException;
	
	/**
	 * 
	 * 方法名称：查询互生卡解挂
	 * 方法描述：查询互生卡解挂
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<SysService> findCarddislossList(Map filterMap, Map sortMap, Page page) throws HsException;
    
}
