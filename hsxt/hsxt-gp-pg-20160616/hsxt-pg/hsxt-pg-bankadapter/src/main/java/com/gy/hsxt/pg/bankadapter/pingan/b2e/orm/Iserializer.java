package com.gy.hsxt.pg.bankadapter.pingan.b2e.orm;

/**
 * @author jbli 目前根据协议仅用于XML文档与Object对象之间的转换，可以继续在此接口上予以扩展，添加诸如obj2json
 *         json2obj, json2xml xml2json
 */
public interface Iserializer {

	public String obj2xml();

	public Object xml2obj(String strXml);
}
