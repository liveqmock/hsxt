/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.servicemsgmanage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.nt.api.beans.QueryParam;
import com.gy.hsi.nt.api.service.INtQueryService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.servicemsgmanage
 * @className     : ServiceMsgManageService.java
 * @description   : 服务消息管理
 * @author        : maocy
 * @createDate    : 2016-03-11
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class ServiceMsgManageService extends BaseServiceImpl implements IServiceMsgManageService {

    @Autowired
    private IBSMessageTplService bsService;//BS接口服务
    
    @Autowired
    private INtQueryService inService;//IN接口服务
    
    /**
     * 
     * 方法名称：新增消息模版
     * 方法描述：新增消息模版
     * @param msgTemplate 模版
     * @param reqOptId 申请操作员编号
     * @param reqOptName 申请操作员名称
     */
	public void saveMessageTpl(MsgTemplate msgTemplate, String reqOptId, String reqOptName) throws HsException {
		this.bsService.saveMessageTpl(msgTemplate, reqOptId, reqOptName);
	}
	
    /**
     * 
     * 方法名称：修改消息模版
     * 方法描述：修改消息模版
     * @param msgTemplate 模版
     */
	public void modifyMessageTpl(MsgTemplate msgTemplate) throws HsException {
		this.bsService.modifyMessageTpl(msgTemplate);
	}
	
    /**
     * 
     * 方法名称：查询模版详情
     * 方法描述：查询模版详情
     * @param tempId 模版ID
     * @return
     */
	public MsgTemplate findMessageTplInfo(String tempId) throws HsException {
		return this.bsService.getMessageTplInfo(tempId);
	}
	
    /**
     * 
     * 方法名称：复核模版
     * 方法描述：复核模版
     * @param templateAppr 复核信息
     * @param reviewRes 复核状态
     * @return
     */
	public void reviewTemplate(MsgTemplateAppr templateAppr, Integer reviewRes) throws HsException {
		this.bsService.reviewTemplate(templateAppr, reviewRes);
	}
	
    /**
     * 
     * 方法名称：启用/停用模版
     * 方法描述：启用/停用模版
     * @param tempId 模版ID
     * @param optType 操作类型（1：启用， 0：停用）
     * @param reqOptId 申请操作员编号
     * @param reqOptName 申请操作员名称
     */
	public void startOrStopTpl(String tempId, Integer optType, String reqOptId, String reqOptName) throws HsException {
		this.bsService.startOrStopTpl(tempId, optType, reqOptId, reqOptName);
	}
	
	/**
     * 
     * 方法名称：消息模版列表
     * 方法描述：消息模版列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	Integer tplType = CommonUtils.toInteger(filterMap.get("tplType"));//模版类型
    	String tplName = (String) filterMap.get("tplName");//模版名称
    	Integer useCustType = CommonUtils.toInteger(filterMap.get("useCustType"));//适用客户类别
    	Integer tplStatus = CommonUtils.toInteger(filterMap.get("tplStatus"));//模版状态
    	return this.bsService.getMessageTplList(tplType, tplName, useCustType, tplStatus, page);
    }
    
    /**
     * 
     * 方法名称：消息模版审批列表
     * 方法描述：消息模版审批列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findMessageTplApprList(Map filterMap, Map sortMap, Page page) throws HsException {
    	String exeCustId = (String) filterMap.get("custId");//经办人编号
    	Integer tplType = CommonUtils.toInteger(filterMap.get("tplType"));//模版类型
    	String tplName = (String) filterMap.get("tplName");//模版名称
    	Integer useCustType = CommonUtils.toInteger(filterMap.get("useCustType"));//适用客户类别
    	return this.bsService.getMessageTplApprList(exeCustId, tplType, tplName, useCustType, page);
    }
    
    /**
     * 
     * 方法名称：模版审批历史列表
     * 方法描述：模版审批历史列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findMsgTplApprHisList(Map filterMap, Map sortMap, Page page) throws HsException {
    	String tempId = (String) filterMap.get("tempId");//模版编号
    	return this.bsService.getMsgTplApprHisList(tempId, page);
    }
    
    /**
     * 
     * 方法名称：分页查询短信消息发送
     * 方法描述：分页查询短信消息发送
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findNoteByPage(Map filterMap, Map sortMap, Page page) throws HsException {
    	com.gy.hsi.nt.api.beans.PageData<?> pd = this.inService.queryNoteByPage(this.getQueryParam(filterMap, page));
    	if(pd == null){
    		return null;
    	}
    	return new PageData<>(pd.getCount(), pd.getResult());
    }
    
    /**
     * 
     * 方法名称：分页查询邮件消息发送
     * 方法描述：分页查询邮件消息发送
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findEmailByPage(Map filterMap, Map sortMap, Page page) throws HsException {
    	com.gy.hsi.nt.api.beans.PageData<?> pd = this.inService.queryEmailByPage(this.getQueryParam(filterMap, page));
    	if(pd == null){
    		return null;
    	}
    	return new PageData<>(pd.getCount(), pd.getResult());
    }
    
    /**
     * 
     * 方法名称：分页查询业务互动消息发送
     * 方法描述：分页查询业务互动消息发送
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findDynamicBizByPage(Map filterMap, Map sortMap, Page page) throws HsException {
    	com.gy.hsi.nt.api.beans.PageData<?> pd = this.inService.queryDynamicBizByPage(this.getQueryParam(filterMap, page));
    	if(pd == null){
    		return null;
    	}
    	return new PageData<>(pd.getCount(), pd.getResult());
    }
    
    /**
     * 
     * 方法名称：封装查询条件
     * 方法描述：封装查询条件
     * @param filterMap 查询条件
     * @param page 分页属性
     * @return
     */
    public QueryParam getQueryParam(Map filterMap, Page page){
    	QueryParam params = new QueryParam();
    	params.setCustType(CommonUtils.toInteger(filterMap.get("custType")));
    	params.setStatus((String) filterMap.get("status"));
    	params.setPages(page.getCurPage());
    	params.setPageSize(page.getPageSize());
    	params.setHsResNo((String) filterMap.get("enthsResNo"));
    	return params;
    }
    
}
