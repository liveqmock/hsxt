/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.AfterKeepConfig;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.ImportAfterService;
import com.gy.hsxt.bs.bean.tool.resultbean.EntDeviceInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 *
 */
public interface IAfterPaidToolMakeService extends IBaseService{
	/**
	 * 查询售后配置单详情
	 * @param confNo
	 * @return
	 * @throws HsException
	 */
	public List<AfterServiceDetail> queryAfterConfigDetail(String confNo)throws HsException;
	/**
	 * 配置刷卡器关联
	 * @param asc
	 * @throws HsException
	 */
	public void configMcrRelationAfter(AfterServiceConfig asc)throws HsException;
	
	public void keepDeviceRelationAfter(AfterKeepConfig asc)throws HsException;
}
