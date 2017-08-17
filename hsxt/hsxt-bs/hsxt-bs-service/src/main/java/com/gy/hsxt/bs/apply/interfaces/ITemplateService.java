/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.apply.interfaces;

import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 模版业务接口
 *
 * @Package : com.gy.hsxt.bs.apply.interfaces
 * @ClassName : ITemplateService
 * @Description : 模版业务接口
 * @Author : chenm
 * @Date : 2016/3/14 16:23
 * @Version V3.0.0.0
 */
public interface ITemplateService {

    /**
     * 创建证书模板
     *
     * @param templet 证书模板
     * @throws HsException
     */
    void createTemplet(Templet templet) throws HsException;

    /**
     * 修改证书模板
     *
     * @param templet 证书模板
     * @throws HsException
     */
    void modifyTemplet(Templet templet) throws HsException;

    /**
     * 查看证书模板详情
     *
     * @param templetId 合同模板ID
     * @return 返回合同模板详情，没有则返回null
     * @throws HsException
     */
    Templet queryTempletById(String templetId) throws HsException;

    /**
     * 分页查询证书模板
     *
     * @param query 模板名称
     * @param page  分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    PageData<Templet> queryTempletList(TempletQuery query, Page page) throws HsException;

    /**
     * 分页查询待复核证书模板
     *
     * @param query 模板查询实体
     * @param page  分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    PageData<Templet> queryTemplet4Appr(TempletQuery query, Page page) throws HsException;

    /**
     * 复核证书模板
     *
     * @param templateAppr 审批内容
     * @throws HsException
     */
    void apprTemplet(TemplateAppr templateAppr) throws HsException;

    /**
     * 启用证书模板
     *
     * @param templetId 模板ID 必填
     * @param operator  操作员客户号
     * @throws HsException
     */
    void enableTemplet(String templetId, String operator) throws HsException;

    /**
     * 停用证书模板
     *
     * @param templetId 模板ID 必填
     * @param operator  操作员客户号
     * @throws HsException
     */
    void disableTemplet(String templetId, String operator) throws HsException;

    /**
     * 查询当前模板
     *
     * @param templetQuery 模板查询实体
     * @return 当前模板
     */
    Templet queryCurrentTplByQuery(TempletQuery templetQuery) throws HsException;

    /**
     * 查询模版最新审核记录
     *
     * @param templetId 模版ID
     * @return TemplateAppr
     * @throws HsException
     */
    TemplateAppr queryLastTemplateAppr(String templetId) throws HsException;
}
