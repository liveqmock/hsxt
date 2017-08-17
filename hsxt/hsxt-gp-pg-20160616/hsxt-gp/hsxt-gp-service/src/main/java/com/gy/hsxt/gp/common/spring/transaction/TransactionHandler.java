/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.spring.transaction;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.spring.transaction
 * 
 *  File Name       : TransactionHandler.java
 * 
 *  Creation Date   : 2016年4月20日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 自己封装事务处理
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class TransactionHandler<T> {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private int propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED;

	/** 
	 * [Attention]: 由于生产环境配置因素, 如果使用READ_COMMITTED级别, 则会报出如下错误, 所以必须设置为DEFAULT级别:
	 * 
	 * Cause: java.sql.SQLException: Cannot execute statement: impossible to write 
	 * to binary log since BINLOG_FORMAT = STATEMENT and at least one table uses a 
	 * storage engine limited to row-based logging. InnoDB is limited to row-logging 
	 * when transaction isolation level is READ COMMITTED or READ UNCOMMITTED.
	 * 
	 * uncategorized SQLException for SQL []; SQL state [HY000]; error code [1665]; 
	 * Cannot execute statement: impossible to write to binary log since 
	 * BINLOG_FORMAT = STATEMENT and at least one table uses a storage engine 
	 * limited to row-based logging. InnoDB is limited to row-logging 
	 * when transaction isolation level is READ COMMITTED or READ UNCOMMITTED.; 
	 * nested exception is java.sql.SQLException: Cannot execute statement: 
	 * impossible to write to binary log since BINLOG_FORMAT = STATEMENT 
	 * and at least one table uses a storage engine limited to row-based logging. 
	 * InnoDB is limited to row-logging when transaction isolation level is 
	 * READ COMMITTED or READ UNCOMMITTED.
	 */
	private int isolationLevel = TransactionDefinition.ISOLATION_DEFAULT; // TransactionDefinition.ISOLATION_READ_COMMITTED;

	private int timeout = TransactionDefinition.TIMEOUT_DEFAULT;

	private boolean readOnly = false;

	private DataSourceTransactionManager transactionManager;

	public TransactionHandler(DataSourceTransactionManager transactionManager) {

		if (null == transactionManager) {
			throw new HsException(GPErrorCode.INNER_ERROR, "GP支付系统与内部错误!");
		}

		this.transactionManager = transactionManager;
	}

	/**
	 * 在事务中进行处理的业务逻辑
	 * 
	 * @return
	 */
	protected abstract T doInTransaction();

	/**
	 * 事务异常时的处理逻辑
	 * 
	 * @return
	 */
	protected void doWhenException() {
	}

	/**
	 * 在数据库事务中进行处理
	 * 
	 * @return
	 */
	public final T getResult() {

		// 事务定义
		DefaultTransactionDefinition def = this.newDefinition();

		// 获得事务状态
		TransactionStatus tx = transactionManager.getTransaction(def);

		try {
			T result = doInTransaction();

			// 事务提交
			transactionManager.commit(tx);

			return result;
		} catch (Exception e) {
			
			logger.error("", e);
			
			// 事务回滚
			transactionManager.rollback(tx);
			
			// 事务异常时的处理逻辑
			this.actionDoWhenException();

			throw e;
		}
	}

	public void setPropagationBehavior(int propagationBehavior) {
		this.propagationBehavior = propagationBehavior;
	}

	public void setIsolationLevel(int isolationLevel) {
		this.isolationLevel = isolationLevel;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	/**
	 * 事务异常时的处理逻辑
	 */
	private void actionDoWhenException() {
		try {
			doWhenException();
		} catch(Exception ex){}
	}

	/**
	 * 初始化DefaultTransactionDefinition默认值
	 */
	private DefaultTransactionDefinition newDefinition() {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		// 事物传播机制
		def.setPropagationBehavior(propagationBehavior);

		// 事物隔离级别，开启新事务
		def.setIsolationLevel(isolationLevel);

		// 是否只读
		def.setReadOnly(readOnly);

		// 事务等待超时时长
		if (0 < timeout) {
			def.setTimeout(timeout);
		}

		return def;
	}
}
