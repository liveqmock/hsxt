package com.gy.hsxt.ps.reconciliation.mapper;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointPage;
import org.apache.ibatis.annotations.Param;


/**   
 * @description  对账接口
 * @author       chenhz
 * @createDate   2015-7-27 下午3:17:43  
 * @Company      深圳市归一科技研发有限公司
 * @version      v0.0.1
 */
public interface ReconciliationMapper {
	
	// 日终查询对账
	List<Alloc> queryDayOfEnd(PointPage pointPage) throws HsException;

	// 日终查询对账条数统计
	int countDayOfEnd(@Param("runDate") String runDate) throws HsException;

}

