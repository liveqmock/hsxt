package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.CountryCurrency;

/**
 * 
 * 六种常用货币信息接口
 * @Package: com.gy.hsxt.lcs.interfaces  
 * @ClassName: ICountryCurrencyService 
 * @Description: TODO
 *
 * @author: liyh 
 * @date: 2015-12-11 下午2:14:07 
 * @version V1.0
 */
public interface ICountryCurrencyService {

	
	/**
     * 根据国家代码查询常用六种货币
     * @param countryCode
     * @return
     */
    public List<CountryCurrency> queryCountryCurrency();
}
