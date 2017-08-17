

package com.gy.hsxt.ac.api;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 批记账处理接口
 * @Package: com.gy.hsxt.ac.api  
 * @ClassName: IAccountBatchProcesService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-10-20 下午12:13:26 
 * @version V1.0
 */
public interface IAccountBatchProcesService {

	/**
	 * 批记账
	 * @param  fileAddress 记账文件读取路径
	 * @throws HsException   异常处理类
	 */
	public void batchChargeAccount(String batchJobName, String fileAddress, String batchDate, boolean isReturnInforFile,boolean flag) throws HsException;
	
	
	
}
