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

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service.runable
 * @ClassName: BatchRollbackThread
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-1-15 上午11:08:48
 * @version V1.0
 */
public class BatchRollbackThread extends Thread {
	ArrayList<IBatchRollback> servs;

	/**
	 * @param servs
	 */
	public BatchRollbackThread(ArrayList<IBatchRollback> servs) {
		this.servs = servs;
	}

	@Override
	public void run() {
		try {
			for (IBatchRollback serv : servs) {
				try {
					serv.rollback();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
