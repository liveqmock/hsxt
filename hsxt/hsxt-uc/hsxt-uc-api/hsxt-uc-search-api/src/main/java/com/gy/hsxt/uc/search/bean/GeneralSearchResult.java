package com.gy.hsxt.uc.search.bean;

import java.util.List;

import com.gy.hsxt.common.constant.RespCode;

/**
 * 
 * 封装搜索结果
 * @Package: com.gy.hsxt.uc.search.bean  
 * @ClassName: GeneralSearchResult 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2016-1-8 上午10:21:09 
 * @version V1.0 

 * @param <T>
 */
public class GeneralSearchResult<T> extends Search {

	private static final long serialVersionUID = 1447247468567305822L;
	List<T> list;
	
	private int result = RespCode.AS_OPT_SUCCESS.getCode();
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
