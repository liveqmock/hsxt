package com.gy.hsxt.lcs.mapper;


import java.util.List;

import com.gy.hsxt.lcs.bean.CountryCurrency;

/**
 * 六种常用货币信息
 * 
 * @Package: com.gy.hsxt.lcs.mapper  
 * @ClassName: CountryCurrencyMapper 
 * @Description: TODO
 *
 * @author: liyh 
 * @date: 2015-12-11 下午12:07:08 
 * @version V1.0
 */
public interface CountryCurrencyMapper {

	
	/**
	 * 查询本地区平台六种常用货币信息
	 * @return
	 */
	public List<CountryCurrency> queryCountryCurrency();
}
