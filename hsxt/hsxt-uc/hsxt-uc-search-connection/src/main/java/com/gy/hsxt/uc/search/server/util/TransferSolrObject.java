package com.gy.hsxt.uc.search.server.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * 转换solr对象为bean
 * 
 * @Package: com.gy.hsxt.uc.search.server.util
 * @ClassName: TransferSolrObject
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-19 下午4:02:20
 * @version V1.0
 * 
 * @param <T>
 */
public class TransferSolrObject<T> {

	/**
	 * 返回字符类型的List
	 * @param records
	 * @param filedName
	 * @return
	 */
	public List<String> toStringList(SolrDocumentList records, String filedName) {
		List<String> list = new ArrayList<>();
		for (SolrDocument record : records) {
			list.add(String.valueOf(record.get(filedName)));
		}
		return list;
	}

	public T toBean(SolrDocument record, Class<T> clazz) {

		T o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Field[] fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			Object value = record.get(f.getName());
			try {
				if (value != null) {
					BeanUtils.setProperty(o, f.getName(), value);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return o;
	}

	public List<T> toBeanList(SolrDocumentList records, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (SolrDocument record : records) {
			list.add(toBean(record, clazz));
		}
		return list;
	}

}
