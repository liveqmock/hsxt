package com.gy.hsxt.access.web.person.services.hsc.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.CardHolder;
import com.gy.hsxt.access.web.person.services.hsc.ICustomerInfoService;
import com.gy.hsxt.common.exception.HsException;

@Service
public class CustomerInfoServiceImpl extends BaseServiceImpl implements
		ICustomerInfoService {

	private static CardHolder cardHolder = new CardHolder();
	private static Map<String,Object> statusMap = new HashMap<String,Object>();
	static{
		cardHolder.setCertificateFront("certificateFront");
		cardHolder.setCertificateHandheld("certificateHandheld");
		cardHolder.setCertificateOpposite("certificateOppsite");
		cardHolder.setCustId("0100301072820150519");
		cardHolder.setFullName("张三");
		cardHolder.setIdNO("321199003042243");
		cardHolder.setIdType("1");
		cardHolder.setIssuingOrgan("深圳福田区公安局");
		cardHolder.setJob("工程师 ");
		cardHolder.setNationality("中国");
		cardHolder.setResidentAddress("深圳市南景新村民康路28号");
		cardHolder.setResNo("01003010728");
		cardHolder.setSex("1");
		cardHolder.setValidUntil("2022-9-22");
		
		statusMap.put("status", "Y");
	}
	@Override
	public CardHolder findCustomerInfoByCustId(String custId) {
		return cardHolder;
	}
	@Override
	public Map<String, Object> findCustomerImportantStatus(String custId)
			throws HsException {
		return statusMap;
	}
	@Override
	public void changeCustomerImportInfo(CardHolder holder)
			throws HsException {
		cardHolder.setAppReason(holder.getAppReason());
		cardHolder.setCertificateFront(holder.getCertificateFront());
		cardHolder.setCertificateHandheld(holder.getCertificateHandheld());
		cardHolder.setCertificateOpposite(holder.getCertificateOpposite());
		cardHolder.setCustId(holder.getCustId());
		cardHolder.setFullName(holder.getFullName());
		cardHolder.setIdNO(holder.getIdNO());
		cardHolder.setIdType(holder.getIdType());
		cardHolder.setIssuingOrgan(holder.getIssuingOrgan());
		cardHolder.setJob(holder.getJob());
		cardHolder.setLicenceIssuing(holder.getLicenceIssuing());
		cardHolder.setNationality(holder.getNationality());
		cardHolder.setResidentAddress(holder.getResidentAddress());
		cardHolder.setResNo(holder.getResNo());
		cardHolder.setSex(holder.getSex());
		cardHolder.setValidUntil(holder.getValidUntil());
		
		statusMap.put("status", "W");
	}

}
