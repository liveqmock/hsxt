package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.bean.tool.ConsumeKSN;
import com.gy.hsxt.bs.bean.tool.KsnExportRecord;
import com.gy.hsxt.bs.bean.tool.PointKSN;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className     : McrKsnRecordService.java
 * @description   : 刷卡器KSN接口实现
 * @author        : maocy
 * @createDate    : 2016-02-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class McrKsnRecordService extends BaseServiceImpl implements IMcrKsnRecordService {
	
    @Autowired
    private IBSToolMarkService bsToolMarkService;//BS服务类

    /**
     * 
     * 方法名称：分页查询刷卡器KSN生成记录
     * 方法描述：分页查询刷卡器KSN生成记录
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	Integer storeStatus = CommonUtils.toInteger(filterMap.get("storeStatus"));//状态
    	Integer mcrType = CommonUtils.toInteger(filterMap.get("mcrType"));//刷卡器类型
    	try {
    		return this.bsToolMarkService.queryMcrKsnRecordPage(storeStatus, mcrType, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询刷卡器KSN码失败", ex);
			return null;
		}
    }
    
    /**
     * 
     * 方法名称：生成积分KSN信息
     * 方法描述：生成积分KSN信息
     * @param number 生成数量
     * @param operNo 操作员
     * @return
     * @throws HsException
     */
	public String createPointKSNInfo(Integer number, String operNo)throws HsException {
		try {
			return this.bsToolMarkService.createPointKSNInfo(number, operNo);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "createPointKSNInfo",
					"调用BS服务生成积分KSN信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	/**
     * 
     * 方法名称：导出积分KSN信息
     * 方法描述：导出积分KSN信息
     * @param bean 导出信息
     * @return
     * @throws HsException
     */
	public List<PointKSN> exportPointKSNInfo(KsnExportRecord bean) {
		try {
			return this.bsToolMarkService.exportPointKSNInfo(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "exportPointKSNInfo",
					"调用BS服务导出积分KSN信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	
	/**
     * 
     * 方法名称：查询导出记录信息
     * 方法描述：查询导出记录信息
     * @param bean 导出信息
     * @return
     * @throws HsException
     */
	public List<KsnExportRecord> queryKsnExportRecord(String batchNo) {
		try
		{
			return this.bsToolMarkService.queryKsnExportRecord(batchNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryKsnExportRecord", "调用BS查询导出记录信息失败", ex);
			return null;
		}
	}
	
	/**
     * 
     * 方法名称：导入消费刷卡器KSN
     * 方法描述：导入消费刷卡器KSN
     * @param beans 刷卡器序列号
     * @param operNo 操作员
     * @return
     * @throws HsException
     */
	public void importConsumeKSNInfo(List<ConsumeKSN> beans, String operNo) throws HsException {
		try {
			this.bsToolMarkService.importConsumeKSNInfo(beans, operNo);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "importConsumeKSNInfo",
					"调用BS服务导入消费刷卡器KSN失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	/**
     * 
     * 方法名称：导出消费KSN信息
     * 方法描述：导出消费KSN信息
     * @param bean 导出信息
     * @return
     * @throws HsException
     */
	public List<ConsumeKSN> exportConsumeKSNInfo(KsnExportRecord bean) {
		try {
			return this.bsToolMarkService.exportConsumeKSNInfo(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "exportConsumeKSNInfo",
					"调用BS服务导出消费KSN信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
		
	}
	
	/**
     * 
     * 方法名称：查看积分刷卡器KSN
     * 方法描述：查看积分刷卡器KSN
     * @param batchNo 批次号
     * @return
     * @throws HsException
     */
	public List<PointKSN> queryPointKSNInfo(String batchNo) throws HsException {
		try {
			return this.bsToolMarkService.queryPointKSNInfo(batchNo);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "queryPointKSNInfo",
					"调用BS服务查看积分刷卡器KSN失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	/**
     * 
     * 方法名称：查看消费KSN信息
     * 方法描述：查看消费KSN信息
     * @param batchNo 批次号
     * @return
     * @throws HsException
     */
	public List<ConsumeKSN> queryConsumeKSNInfo(String batchNo) {
		try
		{
			return this.bsToolMarkService.queryConsumeKSNInfo(batchNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryConsumeKSNInfo", "调用BS查看消费KSN信息失败", ex);
			return null;
		}
	}
    
}