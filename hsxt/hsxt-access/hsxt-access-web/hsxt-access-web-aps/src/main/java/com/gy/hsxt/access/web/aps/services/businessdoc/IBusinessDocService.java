/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.businessdoc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.businessdoc
 * @className     : IBusinessDocService.java
 * @description   : 业务文件管理接口
 * @author        : maocy
 * @createDate    : 2016-02-27
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IBusinessDocService extends IBaseService {
    
    /**
     * 
     * 方法名称：新增业务文件
     * 方法描述：新增业务文件
     * @param obj obj：示例图片（ImageDoc）、办理业务（BizDoc）、常用业务（NormalDoc）
     * @throws HsException
     */
	public void saveDoc(Object obj) throws HsException;
	
	/**
     * 
     * 方法名称：发布文档
     * 方法描述：发布文档
     * @param docId 文档ID
     * @param fileType 1：图片文件 ，2：业务文件，3：常用文件
     * @throws HsException
     */
	public void releaseDoc(String docId, Integer fileType) throws HsException;
	
    /**
     * 
     * 方法名称：修改文档
     * 方法描述：修改文档
     * @param obj obj：示例图片（ImageDoc）、办理业务（BizDoc）、常用业务（NormalDoc）
     * @throws HsException
     */
	public void modifyDoc(Object obj) throws HsException;
	
	/**
     * 
     * 方法名称：删除文档
     * 方法描述：删除文档
     * @param docId 文档ID
     * @param fileType 1：图片文件 ，2：业务文件，3：常用文件
     * @throws HsException
     */
	public void deleteDoc(String docId, Integer fileType) throws HsException;
	
	/**
     * 
     * 方法名称：获取示例图片版本
     * 方法描述：获取示例图片版本
     * @param docCode 文档ID
     * @return
     * @throws HsException
     */
	public List<ImageDoc> findImageDocHis(String docCode) throws HsException;
	
	/**
     * 
     * 方法名称：停用文档
     * 方法描述：停用文档
     * @param docCode 文档唯一标识
     * @param fileType 1：图片文件 ，2：业务文件，3：常用文件
     * @throws HsException
     */
	public void stopUsedDoc(String docCode, Integer fileType) throws HsException;
	
	/**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @param docStatus 文档状态（2：正常  null：全部）
     * @return
     * @throws HsException
     */
	public List<ImageDoc> findImageDocList(Integer docStatus) throws HsException;
	
	/**
     * 
     * 方法名称：恢复示例图片版本
     * 方法描述：恢复示例图片版本
     * @param docId 文档ID
     * @throws HsException
     */
	public void recoveryImageDoc(String docId) throws HsException;
	
	/**
     * 
     * 方法名称：查询文档详情
     * 方法描述：查询文档详情
     * @param docId 文档ID
     * @param fileType 1：图片文件 ，2：业务文件，3：常用文件
     * @throws HsException
     */
	public Object findDocInfo(String docId, Integer fileType) throws HsException;
	
	/**
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> findBizDocList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> findNormalDocList(Map filterMap, Map sortMap, Page page) throws HsException;
    
}