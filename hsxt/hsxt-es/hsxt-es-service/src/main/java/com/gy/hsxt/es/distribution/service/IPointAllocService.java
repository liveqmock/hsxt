package com.gy.hsxt.es.distribution.service;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.CancellationDetail;
import com.gy.hsxt.es.points.bean.CorrectDetail;
import com.gy.hsxt.es.points.bean.PointDetail;
  

/**
 * @description 积分分配服务接口
 * @author 		chenhz
 * @createDate 	2015-7-27 上午10:13:12
 * @Company 	深圳市归一科技研发有限公司
 * @version 	v0.0.1
 */

public interface IPointAllocService {

	/**
	 *  消费者的积分实时分配处理
	 * @param pointDetail
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> allocPoint(PointDetail pointDetail) throws HsException;
	
	/**
	 *  消费者的退货实时分配处理
	 * @param back
	 * @param pointDetail
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> allocBackPoint(BackDetail back, PointDetail pointDetail) throws HsException;
		
	/**
	 *  消费者的撤单实时分配处理
	 * @param Cancel
	 * @param pointDetail
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> allocCancelPoint(CancellationDetail Cancel, PointDetail pointDetail) throws HsException;
	
	/**
	 *  消费者的冲正处理
	 * @param correct
	 * @param pointDetail
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> allocReturnPoint(CorrectDetail correct, PointDetail pointDetail) throws HsException;
}
