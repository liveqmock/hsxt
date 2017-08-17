/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.servicemsgmanage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.servicemsgmanage
 * @className     : IServiceMsgManageService.java
 * @description   : 服务消息管理
 * @author        : maocy
 * @createDate    : 2016-03-11
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
public interface IServiceMsgManageService extends IBaseService {
	
    /**
     * 
     * 方法名称：新增消息模版
     * 方法描述：新增消息模版
     * @param msgTemplate 模版
     * @param reqOptId 申请操作员编号
     * @param reqOptName 申请操作员名称
     */
	public void saveMessageTpl(MsgTemplate msgTemplate, String reqOptId, String reqOptName) throws HsException;
	
    /**
     * 
     * 方法名称：修改消息模版
     * 方法描述：修改消息模版
     * @param msgTemplate 模版
     */
	public void modifyMessageTpl(MsgTemplate msgTemplate) throws HsException;
	
    /**
     * 
     * 方法名称：查询模版详情
     * 方法描述：查询模版详情
     * @param tempId 模版ID
     * @return
     */
	public MsgTemplate findMessageTplInfo(String tempId) throws HsException;
	
    /**
     * 
     * 方法名称：复核模版
     * 方法描述：复核模版
     * @param templateAppr 复核信息
     * @param reviewRes 复核状态
     * @return
     */
	public void reviewTemplate(MsgTemplateAppr templateAppr, Integer reviewRes) throws HsException;
	
    /**
     * 
     * 方法名称：启用/停用模版
     * 方法描述：启用/停用模版
     * @param tempId 模版ID
     * @param optType 操作类型（1：启用， 0：停用）
     * @param reqOptId 申请操作员编号
     * @param reqOptName 申请操作员名称
     */
	public void startOrStopTpl(String tempId, Integer optType, String reqOptId, String reqOptName) throws HsException;
	
	/**
     * 
     * 方法名称：消息模版列表
     * 方法描述：消息模版列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：消息模版审批列表
     * 方法描述：消息模版审批列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findMessageTplApprList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：模版审批历史列表
     * 方法描述：模版审批历史列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findMsgTplApprHisList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：分页查询短信消息发送
     * 方法描述：分页查询短信消息发送
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findNoteByPage(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：分页查询邮件消息发送
     * 方法描述：分页查询邮件消息发送
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findEmailByPage(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：分页查询业务互动消息发送
     * 方法描述：分页查询业务互动消息发送
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findDynamicBizByPage(Map filterMap, Map sortMap, Page page) throws HsException;
	
}
