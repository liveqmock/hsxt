package com.gy.hsxt.ac.batch.service.runnable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.ac.batch.mapper.AccountEntryBatchMapper;
import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.common.bean.AccountTransSystem;
import com.gy.hsxt.ac.common.bean.PropertyConfigurer;

/**
 * 批生成对账文件多线程类
 * 
 * @Package: com.gy.hsxt.ac.service.runnable
 * @ClassName: AccountCheckFileRunnable
 * @Description: 批生成对账文件多线程
 * 
 * @author: maocan
 * @date: 2015-8-31 上午10:19:22
 * @version V1.0
 */
public class AccountCheckFileRunnable implements Runnable {

	// 任务记录
	private AccountBatchJob accountBatchJob;

	// 需要对账的系统MAP
	private Map<String, AccountTransSystem> accTransSystemMap;

	// 记账Mapper
	private AccountEntryBatchMapper accountEntryMapper;
	
	private Map<String, Object> accountEntryMap;

	// 查询数据开始行
	private int beginRow;

	public AccountCheckFileRunnable(AccountBatchJob accountBatchJob,
			Map<String, AccountTransSystem> accTransSystemMap,
			AccountEntryBatchMapper accountEntryMapper, Map<String, Object> accountEntryMap, int beginRow) {
		this.accountBatchJob = accountBatchJob;
		this.accTransSystemMap = accTransSystemMap;
		this.accountEntryMapper = accountEntryMapper;
		this.accountEntryMap = accountEntryMap;
		this.beginRow = beginRow;
	}

	public void run() {
		String[] transSyses = (String[])accountEntryMap.get("transSyses");
		
		// 当前线程查询总条数
		int sumRow = Integer.valueOf(PropertyConfigurer
				.getProperty("ac.accountCheck.sumRow"));
		
		// 每次查询条数
		int row = Integer.valueOf(PropertyConfigurer
				.getProperty("ac.accountCheck.row"));
		
		// 查询次数
		int count = sumRow / row;
		
		// 循环查询
		for (int x = 0; x < count; x++) {
			
			// 查询开始行数
			accountEntryMap.put("baginRN", beginRow + 1 + (x * row));
			
			// 查询结束行数
			accountEntryMap.put("endRN", beginRow + (x + 1) * row);
			try {
				List<AccountEntry> accountEntryList = accountEntryMapper
						.seachAccountEntryByFiscalDate(accountEntryMap);

				if (accountEntryList != null && accountEntryList.size() > 0) {
					
					// 写文件数据Map,不同系统根据Map的Key来存放
					Map<String, StringBuilder> writeDateMap = new HashMap<String, StringBuilder>();
					
					// 循环遍历到Map里
					for (int i = 0; i < accountEntryList.size(); i++) {
						
						// 获取一条分录信息
						AccountEntry accountEntry = accountEntryList.get(i);
						
						// 根据分录信息的交易系统字段查看是否存在写文件数据Map里
						StringBuilder writeFileData = writeDateMap
								.get(accountEntry.getTransSys());
						
						// 判断该系统写文件数据是否存在
						if (writeFileData == null) {
							// 不存在创建一个新的StringBuilder存储分录信息
							writeFileData = new StringBuilder();
							// 注入到Map里好在循环里共用
							writeDateMap.put(accountEntry.getTransSys(),
									writeFileData);
						}
						//分录编号
						writeFileData.append(accountEntry.getEntryNo()+"|");
						// 客户号
						writeFileData.append(accountEntry.getCustID() + "|");
						//账户类型
						writeFileData.append(accountEntry.getAccType()+"|");
						// 增向金额
						writeFileData.append(accountEntry.getAddAmount()
								.toString() + "|");
						// 减向金额
						writeFileData.append(accountEntry.getSubAmount()
								.toString() + "|");
						// 交易流水号
						writeFileData.append(accountEntry.getTransNo() + "|");
						//业务记账分录ID
						writeFileData.append(accountEntry.getSysEntryNo() + "|");
						// 交易时间
						writeFileData.append(accountEntry.getFiscalDate() + "|");
						// 交易类型
						writeFileData.append(accountEntry.getTransType() + "|");
						// 原始交易流水号
						writeFileData.append(accountEntry.getSourceTransNo());
						writeFileData.append("\r\n");
					}
					// 循环写入不同系统的对账文件
					for (int i = 0; i < transSyses.length; i++) {
						// 获取一个对账文件系统类
						AccountTransSystem accountTransSystem = accTransSystemMap
								.get(transSyses[i]);
						// 写入文件
						accountTransSystem.write(writeDateMap
								.get(transSyses[i]));
					}
					// 数据不足每次查询条数说明已到最后的数据退出循环
					if (accountEntryList.size() < row) {
						break;
					}
				} else {
					// 查询不到数据说明没有对账数据退出循环
					break;
				}

			} catch (SQLException e) {
				e.getMessage();
			}
		}
	}

}
