/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.interfaces
 * @ClassName: IOperationService
 * @Description: 操作管理
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午5:24:03
 * @version V1.0
 */
public interface IOperationService {

    /**
     * 创建操作历史
     * 
     * @param optHisInfo
     *            操作历史
     * @param tableName
     *            表名
     * @throws HsException
     */
    public void createOptHis(String tableName, OptHisInfo optHisInfo) throws HsException;

    /**
     * 创建操作历史
     * 
     * @param tableName
     *            审批表名称
     * @param applyId
     *            业务编号
     * @param bizAction
     *            业务阶段
     * @param bizStatus
     *            审批状态
     * @param optId
     *            操作员编号
     * @param optName
     *            操作员名称
     * @param optEntName操作单位名称
     * @param optRemark
     *            操作说明
     * @param dblOptId
     *            双签操作员编号
     * @param changeContent
     *            修改内容
     * @throws HsException
     */
    public void createOptHis(String tableName, String applyId, Integer bizAction, Integer bizStatus, String optId,
            String optName, String optEntName, String optRemark, String dblOptId, String changeContent)
            throws HsException;

    /**
     * 查询操作历史
     * 
     * @param applyId
     *            申请编号
     * @param tableName
     *            表名
     * @param page
     *            分页
     * @return 操作历史
     */
    public PageData<OptHisInfo> queryOptHisListPage(String applyId, String tableName, Page page);

    /**
     * 查询全操作历史
     * 
     * @param applyId
     *            申请编号
     * @param tableName
     *            审批表名
     * @return 操作历史
     */
    public List<OptHisInfo> queryOptHisAll(String applyId, String tableName);

}
