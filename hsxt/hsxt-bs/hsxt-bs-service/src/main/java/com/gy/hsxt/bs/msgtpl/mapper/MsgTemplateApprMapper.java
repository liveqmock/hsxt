/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.msgtpl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;

/**
 * 消息模版审批mapper
 * 
 * @Package: com.gy.hsxt.bs.msgtpl.mapper
 * @ClassName: MsgTemplateApprMapper
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-7 下午6:50:04
 * @version V3.0.0
 */
@Repository
public interface MsgTemplateApprMapper {
    /**
     * 插入消息模版审批
     * 
     * @param msgTemplateAppr
     *            消息模版审批信息
     * @return 成功记录数
     */
    public int insertMsgTplAppr(MsgTemplateAppr msgTemplateAppr);

    /**
     * 修改审批状态
     * 
     * @param status
     *            状态
     * @param apprOptId
     *            审批操作员编号
     * @param apprOptName
     *            审批操作员名称
     * @param apprRemark
     *            审批信息
     * @param applyId
     *            申请编号
     * @return 成功记录数
     */
    public int updateApprStatus(@Param("reviewResult") Integer status, @Param("apprOptId") String apprOptId,
            @Param("apprOptName") String apprOptName, @Param("apprRemark") String apprRemark,
            @Param("applyId") String applyId);

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
     * @return 模版列表
     */
    public List<MsgTemplateAppr> findMessageTplApprListPage(@Param("exeCustId") String exeCustId,
            @Param("tplType") Integer tplType, @Param("tplName") String tplName,
            @Param("useCustType") Integer useCustType);

    /**
     * 查询审批历史列表
     * 
     * @param tempId
     *            模版编号
     * @return 历史列表
     */
    public List<MsgTemplateAppr> findApprHisListPage(@Param("tempId") String tempId);

    /**
     * 查询模版审批详情
     * 
     * @param applyId
     *            申请编号
     * @return 审批详情
     */
    public MsgTemplateAppr findMsgTplApprInfo(@Param("applyId") String applyId);
}
