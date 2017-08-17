package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author jbli
 *
 */
@Component("bodyRes_4018")  
public class MaxBatchTransferResDTO   extends BodyDTO{
	
	public MaxBatchTransferResDTO(){};
	
	//批量转账凭证号 同上送的批次号
	private String thirdVoucher = "";	

	public String getThirdVoucher() {
		return thirdVoucher;
	}

	private MaxBatchTransferResDTO(String thirdVoucher){
		this.thirdVoucher = thirdVoucher;
	}
	
	public static MaxBatchTransferResDTO getInstance(String thirdVoucher){
		return new MaxBatchTransferResDTO(thirdVoucher);
	}
	

	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", MaxBatchTransferResDTO.class);
		xStream.aliasField("thirdVoucher", MaxBatchTransferResDTO.class, "ThirdVoucher");
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", MaxBatchTransferResDTO.class);
		xStream.aliasField("ThirdVoucher", MaxBatchTransferResDTO.class, "thirdVoucher");
		return  xStream.fromXML(strXml);
	}
}
