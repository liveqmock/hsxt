package com.gy.hsxt.pg.bankadapter.pingan.b2e.orm;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public abstract class BodyDTO implements Iserializer, Cloneable {
	
	public void setXStream(){
		if(xStream == null){
			this.xStream = getXstream();
		}
	}

	protected XStream xStream = getXstream();
	
	/**
	 * 为了兼容平安银行返回的XML中随时可能增加一些新的属性，而在此接口中忽略那些不存在的属性。避免xml2obj（）方法中必须写那些忽略新增的字段代码
	 * 例如： xStream.omitField(QryMaxBatchTransferResDTO.class, "OrigThirdVoucher");
	 *     xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "InAcctBankNode");
	 * @return
	 */
	public XStream getXstream()
	{
		return new XStream(){
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@SuppressWarnings("rawtypes")
					@Override
					public boolean shouldSerializeMember(Class definedIn,String fieldName) {
						//在实现类中，没有使用xStream.aliasField定义的字段，都是Object.class类型的。遇到list的时候，还需要进行解析，否则不能从XML文档反序列化出集合类中的信息
						if (definedIn == Object.class  && !"list".equalsIgnoreCase(fieldName) 
								&& !"HOResultSet4018R".equalsIgnoreCase(fieldName)
								&& !"b2e0005-rq".equalsIgnoreCase(fieldName) 
								&& !"b2e0007-rq".equalsIgnoreCase(fieldName)
								&& !"b2e0009-rq".equalsIgnoreCase(fieldName) 
								&& !"b2e0061-rq".equalsIgnoreCase(fieldName)
								&& !"b2e0005-rs".equalsIgnoreCase(fieldName) 
								&& !"b2e0007-rs".equalsIgnoreCase(fieldName) 
								&& !"b2e0009-rs".equalsIgnoreCase(fieldName) 
								&& !"b2e0061-rs".equalsIgnoreCase(fieldName)) {
							return false;
						}

						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
		}

		return null;
	}
}
