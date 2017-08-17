package com.gy.hsxt.access.web.person.services.hsc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.CardReapplyOrder;
import com.gy.hsxt.access.web.person.bean.ReceiveAddress;
import com.gy.hsxt.access.web.person.services.hsc.ICardReapplyService;
import com.gy.hsxt.common.exception.HsException;

@Service
public class CardReapplyServiceImpl extends BaseServiceImpl implements ICardReapplyService{

	public static List<ReceiveAddress> addressList = new ArrayList<ReceiveAddress>();
	
	public static Map<String,List<Map<String,Object>>> areaList = new HashMap<String,List<Map<String,Object>>>();
	
	public static CardReapplyOrder order = new CardReapplyOrder();
	static{
		//收件信息初始化
		ReceiveAddress map1 = new ReceiveAddress();
		map1.setId("1");
		map1.setAddress("AAAA大厦");
		map1.setArea("福田区");
		map1.setCity("深圳");
		map1.setFixedTelephone("13212341234");
		map1.setPhone("13212341234");
		map1.setPostcode("12345");
		map1.setProvince("广东");
		map1.setReceiverName("上帝");
		
		ReceiveAddress map2 = new ReceiveAddress();
		map2.setId("2");
		map2.setAddress("人才大厦");
		map2.setArea("福田区");
		map2.setCity("深圳");
		map2.setFixedTelephone("13212341234");
		map2.setPhone("13212341234");
		map2.setPostcode("12345");
		map2.setProvince("广东");
		map2.setReceiverName("上帝");
		
		addressList.add(map1);
		addressList.add(map2);
		
		//省市区信息初始化
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map11 = new HashMap<String,Object>();
		map11.put("areaNo", "11");
		map11.put("areaName", "北京");
		Map<String,Object> map12 = new HashMap<String,Object>();
		map12.put("areaNo", "12");
		map12.put("areaName", "广东");
		list1.add(map11);
		list1.add(map12);
		areaList.put("0", list1);
		
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map21 = new HashMap<String,Object>();
		map21.put("areaNo", "111");
		map21.put("areaName", "北京");
		list2.add(map21);
		areaList.put("11", list2);
		
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map31 = new HashMap<String,Object>();
		map31.put("areaNo", "1111");
		map31.put("areaName", "朝阳区");
		list3.add(map31);
		Map<String,Object> map32 = new HashMap<String,Object>();
		map32.put("areaNo", "1112");
		map32.put("areaName", "海淀区");
		list3.add(map32);
		areaList.put("111", list3);
		
		List<Map<String,Object>> list4 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map41 = new HashMap<String,Object>();
		map41.put("areaNo", "121");
		map41.put("areaName", "东莞市");
		list4.add(map41);
		Map<String,Object> map42 = new HashMap<String,Object>();
		map42.put("areaNo", "122");
		map42.put("areaName", "深圳市");
		list4.add(map42);
		areaList.put("12", list4);
		
		List<Map<String,Object>> list5 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map51 = new HashMap<String,Object>();
		map51.put("areaNo", "1211");
		map51.put("areaName", "莞城区");
		list5.add(map51);
		Map<String,Object> map52 = new HashMap<String,Object>();
		map52.put("areaNo", "1212");
		map52.put("areaName", "东城区");
		list5.add(map52);
		areaList.put("121", list5);
		
		List<Map<String,Object>> list6 = new ArrayList<Map<String,Object>>();
		Map<String,Object> map61 = new HashMap<String,Object>();
		map61.put("areaNo", "1221");
		map61.put("areaName", "福田区");
		list6.add(map61);
		Map<String,Object> map62 = new HashMap<String,Object>();
		map62.put("areaNo", "1222");
		map62.put("areaName", "龙华区");
		list6.add(map62);
		areaList.put("122", list6);
		
		
		order.setAddress("人才大厦");
		order.setApplyCode("NDK093883");
		order.setPayPrice("123");
		order.setPayType("1");
		order.setPhone("15801235342");
		order.setPostcode("022001");
		order.setReason("掉了");
		order.setReceiverName("张三");
		order.setResNo("111111111111");
		order.setStatus("1");
	}
	
	@Override
	public List<ReceiveAddress> getUserAddressList(String custId) throws HsException {
		//"{'data':[{'id':'xxx001','receiverName':'张三','phone':'13212341234','province':'广东','city':'深圳','area':'福田区','address':'AAAA大厦','postcode':'123456','isDefault':'0'},{'id':'xxx001','receiverName':'张三','phone':'13212341234','province':'广东','city':'深圳','area':'福田区','address':'深圳市勘察所','postcode':'123456','isDefault':'0'}]}";
		return addressList;
	}

	@Override
	public List<Map<String, Object>> getAreaLists(String areaCode)
			throws HsException {
		return areaList.get(areaCode);
	}

	@Override
	public void addUserAddress(ReceiveAddress receiveAddress)
			throws HsException {
		addressList.add(receiveAddress);
	}

	@Override
	public Map<String, Object> getUserBalace(String custId) throws HsException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hsbBalance",12306);
		return map;
	}

	@Override
	public CardReapplyOrder getUserApplyOrder(String custId) throws HsException {
		
		return order;
	}

	@Override
	public CardReapplyOrder addUserApplyOrder(CardReapplyOrder ord)throws HsException {
		
		return ord;
	}

	@Override
	public String addUserApplyByPayBtn(CardReapplyOrder order)throws HsException {
		
		return "http://www.baidu.com";
	}

}
