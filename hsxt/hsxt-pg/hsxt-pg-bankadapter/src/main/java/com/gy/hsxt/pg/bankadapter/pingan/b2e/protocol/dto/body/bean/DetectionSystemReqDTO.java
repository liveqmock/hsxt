package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author huke.zhang
 * 
 *         系统状态探测[S001]
 */
@Component("bodyReq_S001")
public class DetectionSystemReqDTO extends BodyDTO {

	public DetectionSystemReqDTO() {
	}

	private DetectionSystemReqDTO(Builder builder) {
	}

	public static class Builder {

		public Builder() {
		}

		public DetectionSystemReqDTO build() {
			return new DetectionSystemReqDTO(this);
		}
	}

	@Override
	public String obj2xml() {

		this.preHandleAliasFields();

		return PackageConstants.XML_SCHEMA_TITLE + xStream.toXML(this);
	}

	@Override
	public Object xml2obj(String strXml) {

		if (StringUtils.isEmpty(strXml)) {
			return null;
		}

		this.preHandleAliasFields();

		return xStream.fromXML(strXml);
	}

	/**
	 * 字段名称预处理
	 */
	private void preHandleAliasFields() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", DetectionSystemReqDTO.class);
	}

	public static void main(String[] args) {
		DetectionSystemReqDTO.Builder build = new DetectionSystemReqDTO.Builder();

		System.out.println(build.build().obj2xml());
	}
}