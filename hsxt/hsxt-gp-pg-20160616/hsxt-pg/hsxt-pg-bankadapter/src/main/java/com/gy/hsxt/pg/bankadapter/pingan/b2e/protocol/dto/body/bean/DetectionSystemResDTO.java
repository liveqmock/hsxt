package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author huke.zhang
 * 
 *         '系统状态探测[S001]'的响应结果对象
 */
@Component("bodyRes_S001")
public class DetectionSystemResDTO extends BodyDTO {

	public DetectionSystemResDTO() {
	}

	// 状态描述
	private String desc;

	private DetectionSystemResDTO(Builder builder) {
		this.desc = builder.desc;
	}

	public String getDesc() {
		return desc;
	}

	public static class Builder {
		public Builder() {
		}

		// 状态描述
		private String desc;

		public Builder setDesc(String desc) {
			this.desc = desc;
			return this;
		}

		/**
		 * 返回一个不带任何默认值的实例引用，用于解码
		 * 
		 * @return
		 */
		public DetectionSystemResDTO build() {
			return new DetectionSystemResDTO(this);
		}
	}

	@Override
	public String obj2xml() {
		this.preHandleAliasFields();

		String xml = PackageConstants.XML_SCHEMA_TITLE + xStream.toXML(this);

		return this.reFormatXmlStr(xml);
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
		xStream.alias("Result", DetectionSystemResDTO.class);

		xStream.aliasField("Desc", DetectionSystemResDTO.class, "desc");
	}

	private String reFormatXmlStr(String xml) {
		// 规避xStream bug
		return xml.replaceAll("SOA__VOUCHER>", "SOA_VOUCHER>");
	}

	public static void main(String[] args) {
		DetectionSystemResDTO.Builder build = new DetectionSystemResDTO.Builder();
		build.setDesc("test");

		System.out.println(build.build().obj2xml());
	}
}
