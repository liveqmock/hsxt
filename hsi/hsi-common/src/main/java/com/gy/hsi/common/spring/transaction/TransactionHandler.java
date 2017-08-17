/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.common.spring.transaction;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
 *  Purpose         : 自己封装事务处理, 该类已经有几个系统在使用, 不得随意改动方法名称及参数, 引起问题后果自负!!!!!
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class TransactionHandler<T> {

	private final Logger logger = Logger.getLogger(this.getClass());

	private int propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED;

	private int isolationLevel = TransactionDefinition.ISOLATION_READ_COMMITTED;

	private int timeout = TransactionDefinition.TIMEOUT_DEFAULT;

	private boolean readOnly = false;

	private DataSourceTransactionManager transactionManager;

	public TransactionHandler(DataSourceTransactionManager transactionManager) {

		if (null == transactionManager) {
			throw new IllegalArgumentException("传递的参数transactionManager不能为空");
		}

		this.transactionManager = transactionManager;
	}

	/**
	 * 在事务中进行处理的业务逻辑
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract T doInTransaction() throws Exception;

	/**
	 * 事务异常时的处理逻辑
	 * 
	 * @param e
	 * @return
	 */
	protected void doWhenException(Exception e) {
	}

	/**
	 * 在数据库事务中进行处理
	 * 
	 * @return
	 * @throws Exception 
	 */
	public final T getResult() throws Exception {

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
			this.actionDoWhenException(e);

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
	 * 
	 * @param e
	 */
	private void actionDoWhenException(Exception e) {
		try {
			doWhenException(e);
		} catch (Exception ex) {
		}
	}

	/**
	 * 初始化DefaultTransactionDefinition默认值
	 */
	private DefaultTransactionDefinition newDefinition() {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		// 事务传播机制
		def.setPropagationBehavior(propagationBehavior);

		// 事务隔离级别，开启新事务
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
