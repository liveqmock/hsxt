/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.common.mapper.OperationMapper;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.service
 * @ClassName: OperationService
 * @Description: 操作管理
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午5:28:00
 * @version V1.0
 */
@Service
public class OperationService implements IOperationService {

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private BsConfig bsConfig;

    /**
     * 创建操作历史
     * 
     * @param tableName
     *            审批操作历史表名称
     * @param applyId
     *            业务编号
     * @param bizAction
     *            业务阶段
     * @param bizStatus
     *            业务状态
     * @param optId
     *            操作员编号
     * @param optName
     *            操作员名称
     * @param optEntName
     *            操作企业名称
     * @param optRemark
     *            操作备注
     * @param dblOptId
     *            双签操作员编号
     * @param changeContent
     *            变更内容
     * @throws HsException
     * @see com.gy.hsxt.bs.common.interfaces.IOperationService#createOptHis(java.lang.String,
     *      java.lang.String, java.lang.Integer, java.lang.Integer,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void createOptHis(String tableName, String applyId, Integer bizAction, Integer bizStatus, String optId,
            String optName, String optEntName, String optRemark, String dblOptId, String changeContent)
            throws HsException {
        OptHisInfo optHis = new OptHisInfo();
        optHis.setOptHisId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
        optHis.setApplyId(applyId);
        optHis.setBizAction(bizAction);
        optHis.setBizStatus(bizStatus);
        optHis.setOptId(optId);
        optHis.setOptName(optName);
        optHis.setOptEntName(optEntName);
        optHis.setOptRemark(optRemark);
        optHis.setDblOptId(dblOptId);
        optHis.setChangeContent(changeContent);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableName);
        map.put("his", optHis);
        operationMapper.createOptHis(map);

    }

    /**
     * 创建操作历史
     * 
     * @param optHisInfo
     *            操作历史
     * @param tableName
     *            表名
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOptHis(String tableName, OptHisInfo optHisInfo) throws HsException {
        optHisInfo.setOptHisId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableName);
        map.put("his", optHisInfo);
        operationMapper.createOptHis(map);
    }

    /**
     * 分页查询操作历史
     * 
     * @param applyId
     *            申请编号
     * @param tableName
     *            表名
     * @param page
     *            分页
     * @return 操作历史
     */
    @Override
    public PageData<OptHisInfo> queryOptHisListPage(String applyId, String tableName, Page page) {
        PageContext.setPage(page);
        PageData<OptHisInfo> pageData = null;
        List<OptHisInfo> hisList = operationMapper.queryOptHisListPage(applyId, tableName);
        if (null != hisList && hisList.size() > 0)
        {
            pageData = new PageData<OptHisInfo>();
            pageData.setResult(hisList);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 不分页查询操作历史
     * 
     * @param applyId
     *            业务申请编号
     * @param tableName
     *            表名
     * @return
     * @see com.gy.hsxt.bs.common.interfaces.IOperationService#queryOptHisAll(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public List<OptHisInfo> queryOptHisAll(String applyId, String tableName) {
        return operationMapper.queryOptHisAll(applyId, tableName);
    }

}
