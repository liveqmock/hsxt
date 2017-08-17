/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.msgtpl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;

/**
 * 消息模版mapper
 * 
 * @Package: com.gy.hsxt.bs.msgtpl.mapper
 * @ClassName: MsgTemplateMapper
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-7 下午6:48:45
 * @version V3.0.0
 */
@Repository
public interface MsgTemplateMapper {
    /**
     * 插入消息模版
     * 
     * @param msgTemplate
     *            消息模版对象
     */
    public int insertMsgTpl(MsgTemplate msgTemplate);

    /**
     * 修改模版
     * 
     * @param msgTemplate
     *            消息模版对象
     */
    public int updateMsgTpl(MsgTemplate msgTemplate);

    /**
     * 更新模版状态
     * 
     * @param status
     *            状态
     * @param tempId
     *            模版编号
     * @return 成功记录数
     */
    public int updateMsgTplStatus(@Param("status") Integer status, @Param("tempId") String tempId);

    /**
     * 更新模版删除标识
     * 
     * @param lastApplyId
     *            审批ID
     * @param tempId
     *            模版编号
     * @return 成功记录数
     */
    public int updateAppyId(@Param("lastApplyId") String lastApplyId, @Param("tempId") String tempId);

    /**
     * 查询消息模版列表
     * 
     * @param tplType
     *            模版类型
     * @param tplName
     *            模版名称
     * @param useCustType
     *            适用客户类型
     * @param status
     *            模版状态
     * @return 模版列表
     */
    public List<MsgTemplate> findMessageTplListPage(@Param("tplType") Integer tplType,
            @Param("tplName") String tplName, @Param("useCustType") Integer useCustType, @Param("status") Integer status);

    /**
     * 查询模版类型是否已存在
     * 
     * @param tempType
     *            模版类型
     * @param bizType
     *            业务类型
     * @return 记录数
     */
    public int findTplTypeExist(@Param("tempType") Integer tempType, @Param("bizType") Integer bizType);

    /**
     * 查询模版是否存在于非当前模版编号
     * 
     * @param tempType
     *            模版类型
     * @param bizType
     *            业务类型
     * @param tempId
     *            模版编号
     * @return 记录数
     */
    public int findTplType(@Param("tempType") Integer tempType, @Param("bizType") Integer bizType,
            @Param("tempId") String tempId);

    /**
     * 查询模版详情
     * 
     * @param tempId
     *            模版编号
     * @return 模版详情
     */
    public MsgTemplate findMessageTplInfo(@Param("tempId") String tempId);

    /**
     * 查询已启用的相同模版
     * 
     * @param bizType
     *            适用业务类别
     * @param custType
     *            适用客户类别
     * @param buyResType
     *            启用资源类别
     * @param tempId
     *            模版编号
     * @return 记录数
     */
    public int findEnabledTplNum(@Param("bizType") Integer bizType, @Param("custType") Integer custType,
            @Param("buyResType") Integer buyResType, @Param("tempId") String tempId);

    /**
     * 查询启用中的模版
     * 
     * @param tempType
     *            模版类型
     * @param bizType
     *            适用业务类型
     * @param custType
     *            适用客户类型
     * @param buyResType
     *            启用资源类型
     * @return 模版对象
     */
    public MsgTemplate findEnabledMsgTplInfo(@Param("tempType") Integer tempType, @Param("bizType") Integer bizType,
            @Param("custType") Integer custType, @Param("buyResType") Integer buyResType);

    /**
     * 查询重复的模版名称
     * 
     * @param tempName
     *            模版名称
     * @return 记录数
     */
    public int findSameTempName(@Param("tempName") String tempName);

    /**
     * 查询非当前模版是否存在同名
     * 
     * @param tempName
     *            模版名称
     * @param tempId
     *            模版编号
     * @return 记录数
     */
    public int findSameTempNameNotId(@Param("tempName") String tempName, @Param("tempId") String tempId);

    /**
     * 查询已开始的模版
     * 
     * @return 开启的模版列表
     */
    public List<MsgTemplate> findEnabledMsgTpl();
}
