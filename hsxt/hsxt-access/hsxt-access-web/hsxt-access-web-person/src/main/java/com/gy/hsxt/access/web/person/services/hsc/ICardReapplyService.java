package com.gy.hsxt.access.web.person.services.hsc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.CardReapplyOrder;
import com.gy.hsxt.access.web.person.bean.ReceiveAddress;
import com.gy.hsxt.common.exception.HsException;

@Service
public interface ICardReapplyService extends IBaseService {

	/**
	 * 根据客户ID  获取客户的收件地址信息列表
	 * @param custId 客户ID
	 * @return 收件信息列表的json字符串
	 * @throws HsException
	 */
	public List<ReceiveAddress> getUserAddressList(String custId) throws HsException;
	
	/**
	 * 根据省编码查询下面所有的市 ，根据市编码查询市下所有的县（区）
	 * @param areaCode
	 * @return
	 * @throws HsException
	 */
	public List<Map<String,Object>> getAreaLists(String areaCode) throws HsException;
	
	/**
	 * 添加收件信息
	 * @param receiveAddress
	 * @throws HsException
	 */
	public void addUserAddress(ReceiveAddress receiveAddress) throws HsException;
	
	/**
	 * 获取客户互生币账户余额
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public Map<String,Object> getUserBalace(String custId) throws HsException;
	
	/**
	 * 查询补卡申请
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public CardReapplyOrder getUserApplyOrder(String custId) throws HsException;
	
	/**
	 * 添加补卡申请
	 * @param order
	 * @return
	 * @throws HsException
	 */
	public CardReapplyOrder addUserApplyOrder(CardReapplyOrder order) throws HsException;
	
	/**
	 * 互生卡补办 在线支付
	 * @param order
	 * @return 跳转到支付页面的连接串
	 * @throws HsException
	 */
	public String addUserApplyByPayBtn(CardReapplyOrder order) throws HsException;
}
