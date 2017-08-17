/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.msgtpl;

import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 消息模版接口定义
 * 
 * @Package: com.gy.hsxt.bs.api.msgtpl
 * @ClassName: IBSMessageTplService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-8 上午9:06:10
 * @version V3.0.0
 */
public interface IBSMessageTplService {

    /**
     * 保存消息模版
     * 
     * @param msgTemplate
     *            消息模版信息
     * @param reqOptId
     *            申请操作员编号
     * @param reqOptName
     *            申请操作员名称
     * @throws HsException
     */
    public void saveMessageTpl(MsgTemplate msgTemplate, String reqOptId, String reqOptName) throws HsException;

    /**
     * 修改消息模版
     * 
     * @param msgTemplate
     *            模版信息
     */
    public void modifyMessageTpl(MsgTemplate msgTemplate) throws HsException;

    /**
     * 查询消息模版列表
     * 
     * @param tplType
     *            模版类型
     * @param tplName
     *            模版名称
     * @param useCustType
     *            适用客户类型
     * @param tplStatus
     *            模版状态
     * @param page
     *            分页信息
     * @return 模版列表
     * @throws HsException
     */
    public PageData<MsgTemplate> getMessageTplList(Integer tplType, String tplName, Integer useCustType,
            Integer tplStatus, Page page) throws HsException;

    /**
     * 查询消息模版审批列表
     * 
     * @param exeCustId
     *            经办人编号
     * @param tplType
     *            模版类型
     * @param tplName
     *            模版名称
     * @param useCustType
     *            适用客户类型
     * @param page
     *            分页信息
     * @return 模版审批列表
     * @throws HsException
     */
    public PageData<MsgTemplateAppr> getMessageTplApprList(String exeCustId, Integer tplType, String tplName,
            Integer useCustType, Page page) throws HsException;

    /**
     * 查询审批历史列表
     * 
     * @param tempId
     *            模版编号
     * @param page
     *            分页信息
     * @return 审批历史列表
     * @throws HsException
     */
    public PageData<MsgTemplateAppr> getMsgTplApprHisList(String tempId, Page page) throws HsException;

    /**
     * 复核模版
     * 
     * @param templateAppr
     *            模版复核信息
     * @param reviewRes
     *            复核结果
     * @throws HsException
     */
    public void reviewTemplate(MsgTemplateAppr templateAppr, Integer reviewRes) throws HsException;

    /**
     * 启用/停用模版
     * 
     * @param tempId
     *            模版编号
     * @param optType
     *            操作类型
     * @param reqOptId
     *            申请操作员编号
     * @param reqOptName
     *            申请操作员名称
     * @throws HsException
     */
    public void startOrStopTpl(String tempId, Integer optType, String reqOptId, String reqOptName) throws HsException;

    /**
     * 查询模版详情
     * 
     * @param tempId
     *            模版编号
     * @return 模版详情
     * @throws HsException
     */
    public MsgTemplate getMessageTplInfo(String tempId) throws HsException;

    /**
     * 获取开启的模版到缓存
     * 
     * @param tempType
     *            模版类型
     * @param custType
     *            适用客户类型
     * @param bizType
     *            适用业务类型
     * @param buyResType
     *            启用资源类型
     * @return JSON模版字段串
     * @throws HsException
     */
    public String getEnabledMessageTplToRedis(Integer tempType, Integer custType, Integer bizType, Integer buyResType)
            throws HsException;
}
