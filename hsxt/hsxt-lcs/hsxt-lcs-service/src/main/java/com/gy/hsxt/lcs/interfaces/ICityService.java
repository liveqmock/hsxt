package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.City;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : ICityService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 城市代码ICityService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

public interface ICityService {

	/**
	 * 根据主键获取城市信息
	 * @param countryNo 国家代码，非null
	 * @param provinceNo 省代码，非null
	 * @param cityNo 城市代码 必须，非null
	 * @return 返回实体类City
	 */
	public City getCity(String countryNo, String provinceNo, String cityNo);

	/**
	 * 读取某个省份的所以城市记录
	 * @param countryNo 国家代码，非null
	 * @param provinceNo 省份代码 必须，非null
	 * 
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> queryCityByParent(String countryNo,String provinceNo);

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 *            获取变的版本号的数据表示 必须
	 * @param version
	 *            版本号
	 * @return 返回int 1成功，其他失败
	 */
	public int addOrUpdateCity(List<City> list, Long version);
}
