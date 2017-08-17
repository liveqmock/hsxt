/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.ImportAfterService;
import com.gy.hsxt.bs.bean.tool.resultbean.EntDeviceInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 *
 */
public interface IRecylePointToolsService  extends IBaseService {
	
	/**
	 * 分页查询企业设备信息
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<EntDeviceInfo> queryEntDeviceInfoPage(Map filterMap, Map sortMap, Page page) 
			throws HsException;
	
	/**
	 * 验证批量导入设备序列号
	 * @param list
	 * @return
	 * @throws HsException
	 */
	public List<ImportAfterService> validBatchImportSeqNo(List<String> list)throws HsException;
	
	/**
	 * 批量导入售后
	 * @param paramList
	 * @throws HsException
	 */
	public void batchImportAfterService(List<String> deviceList)throws HsException;
	/**
	 * 添加售后单
	 * @param obj
	 * @throws HsException
	 */
	public void addAfterService(String entCustId,String entResNo,String entCustName,String reqRemark,String reqOperator,String listDetail )throws HsException;
}
