package com.gy.hsxt.access.web.person.services.hsc;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.CardHolder;
import com.gy.hsxt.common.exception.HsException;

@Service
public interface ICustomerInfoService extends IBaseService {

	/**
	 * 根据客户ID查询客户重要信息
	 * @param custId
	 * @return
	 */
	public CardHolder findCustomerInfoByCustId(String custId) throws HsException;
	
	/**
	 * 根据客户ID查询客户重要信息变更状态
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public Map<String,Object> findCustomerImportantStatus(String custId) throws HsException;
	
	/**
	 * 修改用户重要信息
	 * @param cardHolder
	 * @throws HsException
	 */
	public void changeCustomerImportInfo(CardHolder cardHolder) throws HsException;
}
