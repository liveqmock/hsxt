/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.cashTransfer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gy.hsxt.access.web.common.service.IBaseService;
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
public interface ICashTransferService extends IBaseService {

    /** 银行转账查询的统计功能 */
    public TransOutCustom getTransOutListCount(TransOutQueryParam param) throws HsException;

    /** 批量提交转账 */
    public void transBatch(Set<String> transNos, Integer commitType, String apprOptId, String apprOptName,
            String apprRemark) throws HsException;

    /** 转账撤单 */
    public void transRevoke(Map<String, String> transNos, String apprOptId, String apprOptName, String apprRemark)
            throws HsException;

    /** 转账失败处理 */
    public List<TransOut> transFailBack(List<String> transNos) throws HsException;

    /** 查询对账列表 */
    public PageData<TransOut> getCheckUpList(Map filterMap, Map sortMap, Page page) throws HsException;

    /** 银行转账对账 */
    public List<TransOut> transCheckUpAccount(List<String> transNos) throws HsException;

    /**
     * 银行转账导出地址
     * @param filterMap
     * @return
     * @throws HsException
     */
    public String transferRecordExportPath(Map<String, Object> filterMap) throws HsException;
}
