/**
 * 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 *
 */
public interface IExchangeHsbService {
	/**
	 * 代兑互生币
	 * @param pbh
	 * @param custId
	 * @param loginPwd
	 * @param secretKey
	 */
	public void exchangeHsb(ProxyBuyHsb pbh);
	/**
	 * 根据互生号查询客户号
	 * @param perResNo
	 * @return
	 * @throws HsException
	 */
	public String findCustIdByResNo(String perResNo)throws HsException;
	
	/**
	 * 判断该互生号是否可以兑换当前金额
	 * @param request
	 * @return Map
	 * @throws HsException
	 */
	public Map getExchangeHsbValidate(String cardNo,String exchangeAmount,String entCustId)throws HsException;
}
