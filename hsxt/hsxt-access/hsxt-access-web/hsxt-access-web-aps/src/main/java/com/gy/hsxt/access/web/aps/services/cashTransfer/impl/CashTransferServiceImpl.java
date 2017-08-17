/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.cashTransfer.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.bean.TransOutCustom;
import com.gy.hsxt.ao.bean.TransOutQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 * 
 */
@Service
public class CashTransferServiceImpl extends BaseServiceImpl implements ICashTransferService {

    @Autowired
    private IAOBankTransferService aoBankTransferService;

    /**
     * 根据条件查询分页数据
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.common.service.BaseServiceImpl#findScrollResult(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TransOut> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        // 查询条件
        TransOutQueryParam param = this.createTOQP(filterMap);
        // 返回查询结果
        return aoBankTransferService.getTransOutList(param, page);
    }

    /**
     * 创建银行转账列表条件
     * @param filterMap
     * @return
     */
    public TransOutQueryParam createTOQP(Map filterMap) {
        TransOutQueryParam param = new TransOutQueryParam();

        param.setStartDate((String) filterMap.get("startDate")); // 开始时间
        param.setEndDate((String) filterMap.get("endDate")); // 结束时间
        param.setHsResNo((String) filterMap.get("hsResNo")); // 互生号或手机号
        param.setCustName((String) filterMap.get("entName")); // 企业名称
        param.setTransReason(CommonUtils.toInteger(filterMap.get("transReason"))); // 说明（备注）
        param.setTransStatus(CommonUtils.toInteger(filterMap.get("transStatus"))); // 转账状态
        param.setCustType(CommonUtils.toInteger(filterMap.get("companyState"))); // 单位类型

        return param;
    }
    

    /**
     * 查询对账列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#getCheckUpList(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TransOut> getCheckUpList(Map filterMap, Map sortMap, Page page) throws HsException {
        TransOutQueryParam param = new TransOutQueryParam();
        
        param.setStartDate((String) filterMap.get("startDate"));                //开始时间
        param.setEndDate((String) filterMap.get("endDate"));                    //结束时间
        param.setHsResNo((String) filterMap.get("hsResNo"));                    //互生号
        param.setCustName((String) filterMap.get("entName"));                   //企业名称
        param.setCustType(CommonUtils.toInteger(filterMap.get("custType")));    //类型
        
        PageData<TransOut> pd = aoBankTransferService.getCheckUpList(param, page);
        return pd;
    }

    /**
     * 批量提交转账
     * @param transNos
     * @param commitType
     * @param apprOptId
     * @param apprOptName
     * @param apprRemark
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#transBatch(java.util.Set, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void transBatch(Set<String> transNos, Integer commitType, String apprOptId, String apprOptName,
            String apprRemark) throws HsException {
        aoBankTransferService.transBatch(transNos, commitType, apprOptId, apprOptName, apprRemark);
    }

    /**
     * 银行转账查询的统计功能 
     * @param param
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#getTransOutListCount(com.gy.hsxt.ao.bean.TransOutQueryParam)
     */
    @Override
    public TransOutCustom getTransOutListCount(TransOutQueryParam param) throws HsException {
        TransOutCustom toc = aoBankTransferService.getTransOutListCount(param);
        return toc;
    }

    /**
     * 转账撤单 
     * @param transNos
     * @param apprOptId
     * @param apprOptName
     * @param apprRemark
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#transRevoke(java.util.Map, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void transRevoke(Map<String, String> transNos, String apprOptId, String apprOptName, String apprRemark)
            throws HsException {
        aoBankTransferService.transRevoke(transNos, apprOptId, apprOptName, apprRemark);
    }

    /**
     * 转账失败处理
     * @param transNos
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#transFailBack(java.util.List)
     */
    @Override
    public List<TransOut> transFailBack(List<String> transNos) throws HsException {
        return aoBankTransferService.transFailBack(transNos);
    }

    /**
     * 银行转账对账
     * @param transNos
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#transCheckUpAccount(java.util.List)
     */
    @Override
    public List<TransOut> transCheckUpAccount(List<String> transNos) throws HsException {
        return aoBankTransferService.transCheckUpAccount(transNos);
    }

    /**
     * 银行转账导出地址
     * @param filterMap
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.cashTransfer.ICashTransferService#transferRecordExportPath(java.util.Map)
     */
    @Override
    public String transferRecordExportPath(Map<String, Object> filterMap) throws HsException {
        return aoBankTransferService.exportTransOutData(this.createTOQP(filterMap));
    }

}
