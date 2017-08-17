package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolStockService;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className : SupplierService.java
 * @description : 供应商信息接口实现
 * @author : maocy
 * @createDate : 2016-02-25
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class SupplierService extends BaseServiceImpl implements
		ISupplierService {

	@Autowired
	private IBSToolStockService bsToolStockService;// BS服务类

	/**
	 * 
	 * 方法名称：分页查询供应商 方法描述：分页查询供应商
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		String supplierName = (String) filterMap.get("supplierName");// 供应商名称
		String corpName = (String) filterMap.get("corpName");// 公司名称
		String linkMan = (String) filterMap.get("linkMan");// 联系人名称
		try {
			return this.bsToolStockService.querySupplierByPage(supplierName,
					corpName, linkMan, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询供应商信息失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：添加供应商 方法描述：供应商信息-添加供应商
	 * 
	 * @param bean
	 *            供应商信息
	 * @throws HsException
	 */
	public void addSupplier(Supplier bean) throws HsException {
		try {
			this.bsToolStockService.addSupplier(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addSupplier",
					"调用BS服务添加供应商失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：修改供应商 方法描述：供应商信息-修改供应商
	 * 
	 * @param bean
	 *            供应商信息
	 * @throws HsException
	 */
	public void modifySupplier(Supplier bean) throws HsException {
		try {
			this.bsToolStockService.modifySupplier(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "modifySupplier",
					"调用BS服务修改供应商失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：删除供应商 方法描述：供应商信息-删除供应商
	 * 
	 * @param supplierId
	 *            供应商标识
	 * @throws HsException
	 */
	public void removeSupplier(String supplierId) throws HsException {
		try {
			this.bsToolStockService.removeSupplier(supplierId);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "removeSupplier",
					"调用BS服务删除供应商失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：查询供应商 方法描述：供应商信息-依据供应商标识查询供应商
	 * 
	 * @param supplierId
	 *            供应商标识
	 * @throws HsException
	 */
	public Supplier querySupplierById(String supplierId) {
		try {
			return this.bsToolStockService.querySupplierById(supplierId);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "querySupplierById",
					"调用BS分页依据供应商标识查询供应商失败", ex);
			return null;
		}
	}

}