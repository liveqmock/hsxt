/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service.runable;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.uc.consumer.service.UCBsCardHolderService;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;

/**
 * uCAsCardHolderService.batchSaveHsCardInfo(hscardList);
 * 
 * @Package: com.gy.hsxt.uc.consumer.service.runable
 * @ClassName:
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-1-15 上午10:25:19
 * @version V1.0
 */
public class BatchAddSearchUser implements Runnable, IBatchRollback {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.consumer.service.runable.BatchAddSearchUser";
	/** 父业务操作服务类 */
	UCBsCardHolderService parentService;
	/** 业务操作参数 */
	List<SearchUserInfo> dataList;

	IUCUserInfoService service;

	String key;

	CountDownLatch latch;

	/**
	 * @param uCAsCardHolderService
	 * @param cardHolderList
	 */
	public BatchAddSearchUser(String key, UCBsCardHolderService parentService,
			IUCUserInfoService service, List<SearchUserInfo> dataList,
			CountDownLatch latch) {
		this.key = key;
		this.parentService = parentService;
		this.service = service;
		this.dataList = dataList;
		this.latch = latch;
	}

	public void run() {
		// 准备异常数据回滚处理类
		// parentService.addRollbackServ((IBatchRollback)this);
		long begin = System.currentTimeMillis();
		long end = begin;
		try {
			SystemLog.info(MOUDLENAME, "run", "开始通知搜索引擎...");
			service.batchAdd(dataList);
			end = System.currentTimeMillis();
			SystemLog.info(MOUDLENAME, "run", "搜索引擎完成处理成功。耗时：" + (end - begin) + "毫秒");
		} catch (Exception e) {
			end = System.currentTimeMillis();
			SystemLog.info(MOUDLENAME, "run", "通知搜索引擎异常：耗时：" + (end - begin) + "毫秒");
			e.printStackTrace();
		} finally {
			// 通知父线程，本线程执行完毕。
			latch.countDown();
		}
	}

	public void rollback() {
		return;
		// do noting
		// ArrayList<String> list= new ArrayList<String>(dataList.size());
		// for(SearchUserInfo user:dataList){
		// list.add(user.getCustId());
		// }
		// service.batchDelete(list);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
