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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.consumer.service.UCBsCardHolderService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;

/** 
 * 
 * @Package: com.gy.hsxt.uc.consumer.service.runable  
 * @ClassName: BatchSaveCardHolderInfo 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-1-15 上午10:25:19 
 * @version V1.0 
 

 */
public class BatchSaveCardHolderInfo implements Runnable,IBatchRollback {
	/** 业务操作服务类*/
	UCAsCardHolderService uCAsCardHolderService;
	/** 业务操作参数*/
	List<CardHolder> dataList;
	
	UCBsCardHolderService parentService;
	
	String key;
	
	CountDownLatch latch;
	/**
	 * @param uCAsCardHolderService
	 * @param cardHolderList
	 */
	public BatchSaveCardHolderInfo(String key,UCBsCardHolderService parentService,UCAsCardHolderService uCAsCardHolderService,
			List<CardHolder> dataList,CountDownLatch latch) {
		this.key=key;
		this.parentService = parentService;
		this.uCAsCardHolderService = uCAsCardHolderService;
		this.dataList = dataList;
		this.latch=latch;
	}
	//@Transactional(propagation=Propagation.SUPPORTS)
	public void run(){
		//准备异常数据回滚处理类
		parentService.addRollbackServ(key,(IBatchRollback)this);
		try {// do 批量入库
			uCAsCardHolderService.batchSaveCardHolderInfo(dataList);// 保存持卡人信息
		} catch (Exception e) {
			// 异常通知父线程，父线程会调用 rollback 处理脏数据
			parentService.addRollbackKey(key,e.getMessage());
			e.printStackTrace();
			
		}finally{
			//通知父线程，本线程执行完毕。
			latch.countDown(); 
		}
	}
	
	public void rollback(){
		ArrayList<String> list= new ArrayList<String>(dataList.size());
		for(CardHolder user:dataList){
			list.add(user.getPerCustId());
		}
		batchSaveHsCardInfo(list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void batchSaveHsCardInfo(ArrayList<String> dataList) throws HsException {
		int batchCount = 1000;
		try {
			int len = dataList.size();// 数据长度
			int fromIndex = 0; // 子列表开始位置
			int toIndex = 0;// 子列表结束位置
			if (len > batchCount) {// 数据过多，需要分批多次入库
				int times = len / batchCount; // 循环次数
				for (int i = 0; i <= times; i++) {
					fromIndex = i * batchCount;
					if (i == times) {// 最后一次循环时，子列表末端为列表长度
						toIndex = len;
					} else {
						toIndex = fromIndex + batchCount;
					}

					if (fromIndex < len) {// 刚好 整数1000条时，最后一次 fromIndex==len
						List subList = dataList.subList(fromIndex, toIndex);
						parentService.batchCloseAccout("rollback", subList);
					}
				}
			} else {
				parentService.batchCloseAccout("rollback", dataList);
			}
		} catch (HsException e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.HSCARD_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
