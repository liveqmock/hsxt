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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.consumer.service.UCBsCardHolderService;

/** 
 * uCAsCardHolderService.batchSaveHsCardInfo(hscardList);
 * @Package: com.gy.hsxt.uc.consumer.service.runable  
 * @ClassName:  
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-1-15 上午10:25:19 
 * @version V1.0 
 

 */
public class BatchSaveHsCardInfo implements Runnable,IBatchRollback {
	
	private static final String MOUDLENAME = "BatchSaveHsCardInfo";
	/** 业务操作服务类*/
	UCAsCardHolderService uCAsCardHolderService;
	/** 业务操作参数*/
	List<HsCard> dataList;
	
	UCBsCardHolderService parentService;
	
	String key;
	
	CountDownLatch latch;
	/**
	 * @param uCAsCardHolderService
	 * @param cardHolderList
	 */
	public BatchSaveHsCardInfo(String key,UCBsCardHolderService parentService,UCAsCardHolderService uCAsCardHolderService,
			List<HsCard> dataList,CountDownLatch latch) {
		this.key=key;
		this.parentService = parentService;
		this.uCAsCardHolderService = uCAsCardHolderService;
		this.dataList = dataList;
		this.latch=latch;
	}
//	@Transactional(propagation=Propagation.SUPPORTS)
	public void run(){
		//准备异常处理类
//		parentService.addRollbackServ((IBatchRollback)this);
		try {// do 批量入库
			uCAsCardHolderService.batchSaveHsCardInfo(dataList);// 保存持卡人信息
		} catch (Exception e) {
			// 异常通知父线程，父线程会调用 rollback 处理脏数据
			parentService.addRollbackKey(key,e.getMessage());
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "run", "批量入库卡信息报错：", e);
			
		}finally{
			//通知父线程，本线程执行完毕。
			latch.countDown(); 
		}
	}
	
	public void rollback(){
		//do nothing
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
