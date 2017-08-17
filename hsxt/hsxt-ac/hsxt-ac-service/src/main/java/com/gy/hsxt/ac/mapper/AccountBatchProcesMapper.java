package com.gy.hsxt.ac.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.bean.AccountEntry;
		
/**
 * 账务系统批处理任务
 * 
 * @Package: com.gy.hsxt.ac.mapper  
 * @ClassName: AccountBatchProcesMapper 
 * @Description: 账务系统批处理任务记录
 *
 * @author: maocan 
 * @date:  2015-8-31 下午2:32:12
 * @version V1.0
 */
public interface AccountBatchProcesMapper {
	
	
	/**
	 * 新增批处理任务记录
	 * @param accountBatchJob 任务记录数据参数
	 * @throws SQLException 异常类型
	 */
	public void addBatchJob(AccountBatchJob accountBatchJob) throws SQLException;
	
	/**
	 * 修改批处理任务记录
	 * @param accountBatchJob 任务记录数据参数
	 * @throws SQLException 异常类型
	 */
	public void updateBatchJob(AccountBatchJob accountBatchJob) throws SQLException;
	
	/**
	 * 删除批处理任务记录
	 * @param accountBatchJob 任务记录数据参数
	 * @throws SQLException 异常类型
	 */
	public void deleteBatchJob(AccountBatchJob accountBatchJob) throws SQLException;
	
	/**
	 * 新增批处理任务报错记录
	 * @param errorEntry 出错数据
	 * @throws SQLException
	 */
	public void addBatchError(AccountEntry errorEntry) throws SQLException;
	
	/**
	 * 批量新增批处理任务报错记录
	 * @param accountEntry 出错数据参数
	 * @throws SQLException 异常类型
	 */
	public void addBatchErrores(List<AccountEntry> accountEntry) throws SQLException;
	
	
	/**
	 * 删除批处理任务报错记录
	 * @param accountBatchJob 任务记录数据参数
	 * @throws SQLException 异常类型
	 */
	public void deleteBatchError(AccountBatchJob accountBatchJob) throws SQLException;
	
	/**
	 * 根据批处理任务名称与任务文件名称查询批处理任务信息
	 * @param batchJobName	任务名称
	 * @param batchFileName 任务文件名称
	 * @return 任务信息
	 * @throws SQLException 数据库异常
	 */
	public AccountBatchJob searchAccountBatchJobByJobNameAndFileName(@Param("batchJobName")String batchJobName, @Param("batchFileName")String batchFileName) throws SQLException;
}

	