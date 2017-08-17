package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSFilingService;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : ShareholderService.java
 * @description   : 股东信息接口实现
 * @author        : maocy
 * @createDate    : 2015-11-06
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class ShareholderService extends BaseServiceImpl implements IShareholderService {
	
	@Autowired
    private IBSFilingService bsService;//企业报备服务类

	@Override
	public PageData<FilingShareHolder> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
	    List<FilingShareHolder> result = this.bsService.queryShByApplyId((String)filterMap.get("applyId"));
	    if(result == null){
	        return null;
	    }
		return new PageData<>(result.size(), result);
	}
	
	/**
	 * 构建模拟数据
	 * @param shName 股东名称
	 * @param shType 股东性质
	 * @param idType 证件类型
	 * @param idNo 证件号码
	 * @param phone 申报日期
	 * @return
	 */
	public FilingShareHolder buildObj(String shName, Integer shType, Integer idType, String idNo, String phone){
		FilingShareHolder obj = new FilingShareHolder();
		obj.setFilingShId("1002");
		obj.setShName(shName);
		obj.setShType(shType);
		obj.setIdType(idType);
		obj.setIdNo(idNo);
		obj.setPhone(phone);
		return obj;
	}

	/**
	 * 
	 * 方法名称：创建股东信息
	 * 方法描述：创建股东信息
	 * @param shareHolder 股东信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public void createShareholder(FilingShareHolder shareHolder) throws HsException {
		this.bsService.createShareHolder(shareHolder);
	}

	/**
	 * 
	 * 方法名称：修改股东信息
	 * 方法描述：修改股东信息
	 * @param shareHolder 股东信息
	 * @throws HsException
	 */
	@Override
	public void updateShareholder(FilingShareHolder shareHolder) throws HsException {
		this.bsService.serviceModifyShareHolder(shareHolder);
	}

	/**
	 * 
	 * 方法名称：删除股东信息
	 * 方法描述：删除股东信息
	 * @param filingShId 股东编号
	 * @param operator 操作员客户
	 * @throws HsException
	 */
	@Override
	public void deleteShareholder(String filingShId, String operator) throws HsException {
		this.bsService.deleteShareHolder(filingShId, operator);
	}
	
}
