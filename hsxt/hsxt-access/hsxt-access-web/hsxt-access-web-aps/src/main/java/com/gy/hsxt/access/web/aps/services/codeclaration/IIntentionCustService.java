package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IIntentionCustService.java
 * @description   : 意向客户查询接口
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IIntentionCustService extends IBaseService {
	
	/**
	 * 
	 * 方法名称：查看客户申请资料
	 * 方法描述：依据客户申请编号查看客户申请资料
	 * @param applyId
	 * @return
	 * @throws HsException
	 */
	public IntentCust findIntentCustById(String applyId) throws HsException;
	
}