package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ConsumeKSN;
import com.gy.hsxt.bs.bean.tool.KsnExportRecord;
import com.gy.hsxt.bs.bean.tool.PointKSN;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className     : IMcrKsnRecordService.java
 * @description   : 刷卡器KSN接口
 * @author        : maocy
 * @createDate    : 2016-02-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IMcrKsnRecordService extends IBaseService {
	
    /**
     * 
     * 方法名称：生成积分KSN信息
     * 方法描述：生成积分KSN信息
     * @param number 生成数量
     * @param operNo 操作员
     * @return
     * @throws HsException
     */
	public String createPointKSNInfo(Integer number, String operNo)throws HsException;
	
	/**
     * 
     * 方法名称：导出积分KSN信息
     * 方法描述：导出积分KSN信息
     * @param bean 导出信息
     * @return
     * @throws HsException
     */
	public List<PointKSN> exportPointKSNInfo(KsnExportRecord bean);
	
	/**
     * 
     * 方法名称：导入消费刷卡器KSN
     * 方法描述：导入消费刷卡器KSN
     * @param beans 刷卡器序列号
     * @param operNo 操作员
     * @return
     * @throws HsException
     */
	public void importConsumeKSNInfo(List<ConsumeKSN> beans, String operNo) throws HsException;
	
	/**
     * 
     * 方法名称：导出消费KSN信息
     * 方法描述：导出消费KSN信息
     * @param bean 导出信息
     * @return
     * @throws HsException
     */
	public List<ConsumeKSN> exportConsumeKSNInfo(KsnExportRecord bean);
	
	/**
     * 
     * 方法名称：查看积分刷卡器KSN
     * 方法描述：查看积分刷卡器KSN
     * @param batchNo 批次号
     * @return
     * @throws HsException
     */
	public List<PointKSN> queryPointKSNInfo(String batchNo) throws HsException;
	
	/**
     * 
     * 方法名称：查看消费KSN信息
     * 方法描述：查看消费KSN信息
     * @param batchNo 批次号
     * @return
     * @throws HsException
     */
	public List<ConsumeKSN> queryConsumeKSNInfo(String batchNo);
	
	/**
	 * 查询导出记录
	 * @param batchNo
	 * @return
	 */
	public List<KsnExportRecord> queryKsnExportRecord(String batchNo);
    
}
